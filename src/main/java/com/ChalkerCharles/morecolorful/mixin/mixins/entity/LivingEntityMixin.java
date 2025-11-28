package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements WindSensitive {
    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> pAttribute);

    @Override
    public boolean moreColorful$isWindSensitive() {
        return this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE) < 1.0;
    }

    @Override
    public double moreColorful$windResistance() {
        return 1.0 - this.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
    }
}
