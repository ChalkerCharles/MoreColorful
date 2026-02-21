package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin extends Projectile implements WindSensitive {
    @Shadow
    protected boolean inGround;

    private AbstractArrowMixin(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @WrapOperation(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;deflect(Lnet/minecraft/world/entity/projectile/ProjectileDeflection;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;Z)Z"))
    private boolean onHitEntity(AbstractArrow instance, ProjectileDeflection deflection, Entity entity, Entity owner, boolean b, Operation<Boolean> original) {
        if (entity instanceof LivingEntity living && UmbrellaItem.canBlock(living, this)) {
            deflection = UmbrellaItem.DEFLECTION;
        }
        return original.call(instance, deflection, entity, owner, b);
    }

    @Override
    public boolean moreColorful$isWindSensitive() {
        return !this.inGround;
    }
}
