package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.entity.misc.SandbagEntity;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {
    @WrapWithCondition(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/FishingHook;setHookedEntity(Lnet/minecraft/world/entity/Entity;)V"))
    private boolean onHitEntity(FishingHook instance, Entity hookedEntity) {
        if (hookedEntity instanceof LivingEntity living && UmbrellaItem.canBlock(living, instance)) {
            UmbrellaItem.playBlockingSound(living);
            return false;
        }
        return true;
    }

    @Inject(method = "pullEntity", at = @At("HEAD"), cancellable = true)
    private void pullEntity(Entity entity, CallbackInfo ci) {
        if (entity instanceof LeashFenceKnotEntity || entity instanceof SandbagEntity) {
            ci.cancel();
        }
    }
}
