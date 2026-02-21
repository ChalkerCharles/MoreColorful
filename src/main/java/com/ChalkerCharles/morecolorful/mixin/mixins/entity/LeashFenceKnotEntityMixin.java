package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.decoration.BlockAttachedEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeashFenceKnotEntity.class)
public abstract class LeashFenceKnotEntityMixin extends BlockAttachedEntity implements IEntityExtension {
    private LeashFenceKnotEntityMixin(EntityType<? extends BlockAttachedEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "interact", at = @At("HEAD"), cancellable = true)
    private void interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (!Config.enhancedLeash) return;
        if (this.level().isClientSide()) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        } else {
            if (ItemUtils.isShears(player.getItemInHand(hand))) {
                InteractionResult result = super.interact(player, hand);
                if (result.indicateItemUse()) {
                    cir.setReturnValue(result);
                    return;
                }
            }

            boolean attachedMob = false;

            for (Leashable leashable : ILeashableExtension.leashableLeashedTo(player)) {
                if (ILeashableExtension.canHaveALeashAttachedTo(leashable, this)) {
                    leashable.setLeashedTo(this, true);
                    attachedMob = true;
                }
            }

            boolean anyDropped = false;
            if (!attachedMob && !player.isSecondaryUseActive()) {
                for(Leashable mob : ILeashableExtension.leashableLeashedTo(this)) {
                    if (ILeashableExtension.canHaveALeashAttachedTo(mob, player)) {
                        mob.setLeashedTo(player, true);
                        anyDropped = true;
                    }
                }
            }

            if (attachedMob || anyDropped) {
                this.gameEvent(GameEvent.BLOCK_ATTACH, player);
                this.playSound(ModSounds.LEAD_TIED.get());
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                cir.setReturnValue(super.interact(player, hand));
            }
        }
    }

    @Override
    public void moreColorful$notifyLeashRemoved(Leashable leashable) {
        if (ILeashableExtension.leashableLeashedTo(this).isEmpty()) {
            this.discard();
        }
    }
}
