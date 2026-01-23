package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import org.joml.Quaternionf;

public interface IParticleExtension {
    default void moreColorful$applyWind() {}

    default void moreColorful$whenStoppedByCollision() {}

    default void moreColorful$floatOnFluid() {}

    default void moreColorful$groundFacingCameraMode(Quaternionf quaternion, Camera camera, float partialTick) {}

    private static IParticleExtension self(Particle particle) {
        return (IParticleExtension) particle;
    }

    static void applyWind(Particle particle) {
        self(particle).moreColorful$applyWind();
    }
}
