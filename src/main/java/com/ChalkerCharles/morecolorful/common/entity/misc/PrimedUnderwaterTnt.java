package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class PrimedUnderwaterTnt extends PrimedTnt {
    public PrimedUnderwaterTnt(EntityType<? extends PrimedTnt> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PrimedUnderwaterTnt(Level pLevel, double pX, double pY, double pZ, @Nullable LivingEntity owner) {
        this(ModEntities.UNDERWATER_TNT.get(), pLevel);
        this.setPos(pX, pY, pZ);
        double d0 = pLevel.random.nextDouble() * (float) (Math.PI * 2);
        this.setDeltaMovement(-Math.sin(d0) * 0.02, 0.2F, -Math.cos(d0) * 0.02);
        this.setFuse(80);
        this.xo = pX;
        this.yo = pY;
        this.zo = pZ;
        this.owner = owner;
    }
}
