package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.world.entity.Entity;

public interface IEntityExtension {
    void moreColorful$applyWind();

    double moreColorful$windResistance();

    private static IEntityExtension self(Entity entity) {
        return (IEntityExtension) entity;
    }

    static void applyWind(Entity entity) {
        self(entity).moreColorful$applyWind();
    }
}
