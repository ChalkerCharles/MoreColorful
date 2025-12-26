package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ArmorStand.class)
public abstract class ArmorStandMixin implements WindSensitive {
    @Shadow
    protected abstract boolean hasPhysics();

    @Override
    public boolean moreColorful$isWindSensitive() {
        return this.hasPhysics();
    }
}
