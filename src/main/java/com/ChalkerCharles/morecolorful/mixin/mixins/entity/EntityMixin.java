package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.common.entity.misc.SandbagEntity;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.misc.BalloonItem;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.ChalkerCharles.morecolorful.util.Self;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityExtension, Self<Entity> {
    @Shadow
    private Level level;
    @Shadow
    private BlockPos blockPosition;
    @Shadow
    public abstract boolean isAlive();
    @Shadow
    public abstract void playSound(SoundEvent pSound);

    @ModifyExpressionValue(method = "getGravity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDefaultGravity()D"))
    private double getGravity(double original) {
        Entity self = this.moreColorful$self();
        if (self.isPassenger()) return original;
        if (self instanceof SandbagEntity) return original;
        double factor = EntityUtils.getBalloonGravityFactor(self);
        double numerator = original - factor * this.moreColorful$weightFactor() * 0.016;
        double denominator = factor + 1;
        for (Entity entity : self.getIndirectPassengers()) {
            if (entity instanceof SandbagEntity) {
                numerator += original;
                denominator += 1;
            } else {
                double f = EntityUtils.getBalloonGravityFactor(entity);
                numerator += original - f * IEntityExtension.weightFactor(entity) * 0.016;
                denominator += f + 1;
            }
        }
        return numerator / denominator;
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Entity self = this.moreColorful$self();
        if (this.isAlive() && this instanceof Leashable leashable && leashable.canBeLeashed() && player.isSecondaryUseActive()) {
            ItemStack itemstack = player.getItemInHand(hand);
            if (itemstack.getItem() instanceof BalloonItem item && this.moreColorful$balloonAttachable()) {
                if (!this.level.isClientSide()) {
                    this.level.addFreshEntity(new Balloon(this.level, player, self, item.variant));
                    level.gameEvent(GameEvent.ENTITY_PLACE, self.position(), GameEvent.Context.of(player));
                    this.playSound(ModSounds.LEAD_TIED.get());
                }
                player.awardStat(Stats.ITEM_USED.get(item));
                itemstack.consume(1, player);
                cir.setReturnValue(InteractionResult.sidedSuccess(this.level.isClientSide));
                return;
            }
        }
        if (!Config.enhancedLeash) return;
        if (!this.level.isClientSide() && player.isSecondaryUseActive() && this instanceof Leashable leashable && leashable.canBeLeashed() && this.isAlive()) {
            if (!(self instanceof LivingEntity living && living.isBaby())) {
                List<Leashable> mobsToLeash = ILeashableExtension.leashableInArea(self, l -> l.getLeashHolder() == player);
                if (!mobsToLeash.isEmpty()) {
                    boolean anyLeashed = false;

                    for (Leashable mob : mobsToLeash) {
                        if (ILeashableExtension.canHaveALeashAttachedTo(mob, self)) {
                            mob.setLeashedTo(self, true);
                            anyLeashed = true;
                        }
                    }

                    if (anyLeashed) {
                        this.level.gameEvent(GameEvent.ENTITY_ACTION, this.blockPosition, GameEvent.Context.of(player));
                        this.playSound(ModSounds.LEAD_TIED.get());
                        cir.setReturnValue(InteractionResult.SUCCESS_NO_ITEM_USED);
                        return;
                    }
                }
            }
        }

        ItemStack item = player.getItemInHand(hand);
        if (ItemUtils.isShears(item) && EntityUtils.shearOffAllLeashConnections(self, player)) {
            item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;gameEvent(Lnet/minecraft/core/Holder;Lnet/minecraft/world/entity/Entity;)V"))
    private void interact$playUntiedSound(CallbackInfoReturnable<InteractionResult> cir) {
        if (Config.enhancedLeash) {
            this.playSound(ModSounds.LEAD_UNTIED.get());
        }
    }

    @WrapOperation(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;canHaveALeashAttachedToIt()Z"))
    private boolean interact$canAttachTo(Leashable instance, Operation<Boolean> original, Player player) {
        if (Config.enhancedLeash) {
            return ILeashableExtension.canHaveALeashAttachedTo(instance, player);
        } else {
            return original.call(instance);
        }
    }

    @Inject(method = "interact", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;setLeashedTo(Lnet/minecraft/world/entity/Entity;Z)V"))
    private void interact$dropLeashIfLeashed(CallbackInfoReturnable<InteractionResult> cir, @Local Leashable leashable) {
        if (Config.enhancedLeash) {
            if (leashable.isLeashed()) {
                leashable.dropLeash(true, true);
            }
            this.playSound(ModSounds.LEAD_TIED.get());
        }
    }
}
