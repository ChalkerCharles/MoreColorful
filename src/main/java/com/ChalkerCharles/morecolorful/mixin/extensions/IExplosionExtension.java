package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.world.level.Explosion;

public interface IExplosionExtension {
    boolean moreColorful$ignoreFluid();

    void moreColorful$setIgnoreFluid();

    private static IExplosionExtension self(Explosion explosion) {
        return (IExplosionExtension) explosion;
    }

    static boolean ignoreFluid(Explosion explosion) {
        return self(explosion).moreColorful$ignoreFluid();
    }

    static void setIgnoreFluid(Explosion explosion) {
        self(explosion).moreColorful$setIgnoreFluid();
    }
}
