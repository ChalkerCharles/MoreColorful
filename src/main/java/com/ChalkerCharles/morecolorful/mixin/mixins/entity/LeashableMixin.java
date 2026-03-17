package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface LeashableMixin extends ILeashableExtension {
    @WrapWithCondition(method = "restoreLeashFromSave", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private static <E extends Entity & Leashable> boolean restoreLeashFromSave(E entity, ItemLike item) {
        return ILeashableExtension.canDropLeash(entity);
    }

    @Inject(method = "dropLeash(Lnet/minecraft/world/entity/Entity;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;setLeashData(Lnet/minecraft/world/entity/Leashable$LeashData;)V", shift = At.Shift.AFTER))
    private static <E extends Entity & Leashable> void dropLeash(E entity, boolean broadcastPacket, boolean dropItem, CallbackInfo ci, @Local Leashable.LeashData leashData) {
        if (Config.enhancedLeash && !entity.level().isClientSide) {
            IEntityExtension.notifyLeashRemoved(leashData.leashHolder, entity);
        }
    }

    @WrapWithCondition(method = "dropLeash(Lnet/minecraft/world/entity/Entity;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private static <E extends Entity & Leashable> boolean dropLeash(E entity, ItemLike item) {
        return ILeashableExtension.canDropLeash(entity);
    }

    @Inject(method = "tickLeash", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;distanceTo(Lnet/minecraft/world/entity/Entity;)F"), cancellable = true)
    private static <E extends Entity & Leashable> void tickLeash(E entity, CallbackInfo ci, @Local(ordinal = 1) Entity leashHolder, @Local Leashable.LeashData leashData) {
        if (!Config.enhancedLeash) return;
        ci.cancel();
        Level level = entity.level();
        double distance = ILeashableExtension.leashDistanceTo(entity, leashHolder);
        ILeashableExtension.whenLeashedTo(entity, leashHolder);
        if (distance > ILeashableExtension.leashSnapDistance(entity)) {
            level.playSound(null, leashHolder.getX(), leashHolder.getY(), leashHolder.getZ(), ModSounds.LEAD_BREAK.get(), SoundSource.NEUTRAL);
            entity.leashTooFarBehaviour();
        } else if (distance > ILeashableExtension.leashElasticDistance(entity) - leashHolder.getBbWidth() - entity.getBbWidth()
                && ILeashableExtension.checkElasticInteractions(entity, leashHolder, leashData)) {
            ILeashableExtension.onElasticLeashPull(entity);
        } else {
            entity.closeRangeLeashBehaviour(leashHolder);
        }
        double angularMomentum = LeashData.angularMomentum(leashData);
        entity.setYRot((float) (entity.getYRot() - angularMomentum));
        LeashData.setAngularMomentum(leashData, angularMomentum * ILeashableExtension.angularFriction(entity));
    }

    @ModifyExpressionValue(method = "tickLeash", at = @At(value = "CONSTANT", args = "doubleValue=10.0"))
    private static <E extends Entity & Leashable> double tickLeash$snapDist(double original, E entity) {
        return ILeashableExtension.leashSnapDistance(entity);
    }

    @ModifyExpressionValue(method = "tickLeash", at = @At(value = "CONSTANT", args = "doubleValue=6.0"))
    private static <E extends Entity & Leashable> double tickLeash$elasticDist(double original, E entity) {
        return ILeashableExtension.leashElasticDistance(entity);
    }

    @WrapWithCondition(method = "setLeashedTo(Lnet/minecraft/world/entity/Entity;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;setLeashedTo(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Z)V"))
    default boolean setLeashedTo(Entity entity, Entity leashHolder, boolean broadcast) {
        return entity != leashHolder;
    }

    @Inject(method = "setLeashedTo(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable$LeashData;setLeashHolder(Lnet/minecraft/world/entity/Entity;)V"))
    private static void setLeashedTo(CallbackInfo ci, @Local Leashable.LeashData leashData, @Share("oldHolder")LocalRef<Entity> oldHolder) {
        oldHolder.set(leashData.leashHolder);
    }

    @Inject(method = "setLeashedTo(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable$LeashData;setLeashHolder(Lnet/minecraft/world/entity/Entity;)V", shift = At.Shift.AFTER))
    private static <E extends Entity & Leashable> void setLeashedTo(E entity, Entity leashHolder, boolean broadcastPacket, CallbackInfo ci, @Share("oldHolder")LocalRef<Entity> oldHolder) {
        Entity holder = oldHolder.get();
        if (holder != null && holder != leashHolder) {
            if (Config.enhancedLeash) {
                IEntityExtension.notifyLeashRemoved(holder, entity);
            }
        }
    }

    @Mixin(Leashable.LeashData.class)
    abstract class LeashDataMixin implements LeashData {
        @Unique
        private double moreColorful$angularMomentum;

        @Override
        public double moreColorful$angularMomentum() {
            return this.moreColorful$angularMomentum;
        }

        @Override
        public void moreColorful$setAngularMomentum(double momentum) {
            this.moreColorful$angularMomentum = momentum;
        }
    }
}
