package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.client.particle.Particle;

public interface IParticleExtension {
    void moreColorful$applyWind();

    private static IParticleExtension self(Particle particle) {
        return (IParticleExtension) particle;
    }

    static void applyWind(Particle particle) {
        self(particle).moreColorful$applyWind();
    }
}
