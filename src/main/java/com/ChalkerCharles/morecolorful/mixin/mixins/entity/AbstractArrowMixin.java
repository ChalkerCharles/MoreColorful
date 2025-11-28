package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractArrow.class)
public abstract class AbstractArrowMixin implements WindSensitive {
    @Shadow
    protected boolean inGround;

    @Override
    public boolean moreColorful$isWindSensitive() {
        return !this.inGround;
    }
}
