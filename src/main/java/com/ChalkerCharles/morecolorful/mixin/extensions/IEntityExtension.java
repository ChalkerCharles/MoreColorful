package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public interface IEntityExtension {
    private Entity self() {
        return (Entity) this;
    }

    default void moreColorful$applyWind() {
        if (!WeatherUtils.isWindSensitive(this) || self().isPassenger()) return;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAffectingEntity(self());
        if (wind != null) {
            RandomSource random = self().getRandom();
            double windX = wind.x * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double h = this.moreColorful$horizontalWindage();
            double v = this.moreColorful$verticalWindage();
            double x = windX * 0.02 * h;
            double y = windY * 0.02 * v;
            double z = windZ * 0.02 * h;
            if (this.moreColorful$hasLift()) {
                double d = Mth.length(windX, windZ) * 0.1 * v;
                y += Math.min(d, 0.03125);
                if (d > self().getGravity()) {
                    this.moreColorful$onLift();
                }
            }
            self().push(x, y, z);
        }
    }

    default double moreColorful$horizontalWindage() {
        return 1.0;
    }

    default double moreColorful$verticalWindage() {
        return this.moreColorful$horizontalWindage();
    }

    default double moreColorful$weight() {
        return 1.0;
    }

    default double moreColorful$weightFactor() {
        return 1.0;
    }

    default void moreColorful$stopSlightMovement() {
        Vec3 vec3 = self().getDeltaMovement();
        if (vec3.equals(Vec3.ZERO)) return;
        double d0 = vec3.x;
        double d1 = vec3.y;
        double d2 = vec3.z;
        boolean changed = false;
        if (Math.abs(vec3.x) < 0.003) {
            d0 = 0.0;
            changed = true;
        }
        if (Math.abs(vec3.y) < 0.003) {
            d1 = 0.0;
            changed = true;
        }
        if (Math.abs(vec3.z) < 0.003) {
            d2 = 0.0;
            changed = true;
        }
        if (changed) {
            self().setDeltaMovement(d0, d1, d2);
        }
    }

    default boolean moreColorful$hasLift() {
        return false;
    }

    default void moreColorful$onLift() {}

    default boolean moreColorful$supportQuadLeashAsHolder() {
        return this instanceof Ghast;
    }

    default Vec3[] moreColorful$getQuadLeashHolderOffsets() {
        return ILeashableExtension.createQuadLeashOffsets(self(), 0.0, 0.5, 0.5, 0.0);
    }

    default boolean moreColorful$balloonAttachable() {
        return true;
    }

    default void moreColorful$notifyLeashRemoved(Leashable leashable) {}

    private static IEntityExtension self(Entity entity) {
        return (IEntityExtension) entity;
    }

    static void applyWind(Entity entity) {
        self(entity).moreColorful$applyWind();
    }

    static double weightFactor(Entity entity) {
        return self(entity).moreColorful$weightFactor();
    }

    static boolean supportQuadLeashAsHolder(Entity entity) {
        return self(entity).moreColorful$supportQuadLeashAsHolder();
    }

    static Vec3[] getQuadLeashHolderOffsets(Entity entity) {
        return self(entity).moreColorful$getQuadLeashHolderOffsets();
    }

    static boolean balloonAttachable(Entity entity) {
        return self(entity).moreColorful$balloonAttachable();
    }

    static void notifyLeashRemoved(Entity entity, Leashable leashable) {
        self(entity).moreColorful$notifyLeashRemoved(leashable);
    }
}
