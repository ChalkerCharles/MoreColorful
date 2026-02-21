package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.util.WeatherUtils;
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

    default boolean moreColorful$supportQuadLeashAsHolder() {
        return this instanceof Ghast;
    }

    default Vec3[] moreColorful$getQuadLeashHolderOffsets() {
        return ILeashableExtension.createQuadLeashOffsets(self(), 0.0, 0.5, 0.5, 0.0);
    }

    default void moreColorful$notifyLeashRemoved(Leashable leashable) {}

    default void moreColorful$checkFallDistanceAccumulation() {
        if (self().getDeltaMovement().y() > -0.5 && self().fallDistance > 1.0F) {
            self().fallDistance = 1.0F;
        }
    }

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

    static void notifyLeashRemoved(Entity entity, Leashable leashable) {
        self(entity).moreColorful$notifyLeashRemoved(leashable);
    }

    static void checkFallDistanceAccumulation(Entity entity) {
        self(entity).moreColorful$checkFallDistanceAccumulation();
    }
}
