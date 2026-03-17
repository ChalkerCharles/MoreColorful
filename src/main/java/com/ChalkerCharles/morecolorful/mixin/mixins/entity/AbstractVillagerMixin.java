package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {
    private AbstractVillagerMixin(EntityType<? extends AgeableMob> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyExpressionValue(method = "getRopeHoldPosition", at = @At(value = "CONSTANT", args = "doubleValue=1.0"))
    public double getRopeHoldPosition$y(double original) {
        return this.isBaby() ? original * 0.5 : original;
    }

    @ModifyExpressionValue(method = "getRopeHoldPosition", at = @At(value = "CONSTANT", args = "doubleValue=0.2"))
    public double getRopeHoldPosition$z(double original) {
        return this.isBaby() ? original * 0.5 : original;
    }
}
