package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.util.Self;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileMixin implements WindSensitive, Self<Projectile> {
    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void onHitEntity(EntityHitResult result, CallbackInfo ci) {
        Entity entity = result.getEntity();
        Projectile self = this.moreColorful$self();
        if (entity instanceof LivingEntity living
                && self instanceof ThrowableItemProjectile projectile) {
            if (UmbrellaItem.canBlock(living, projectile)) {
                UmbrellaItem.playBlockingSound(living);
            }
        }
    }
}
