package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
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
            double d = this.moreColorful$windSensitivity();
            double x = windX * 0.02 * d;
            double y = windY * 0.02 * d;
            double z = windZ * 0.02 * d;
            self().push(x, y, z);
        }
    }

    default double moreColorful$windSensitivity() {
        return 1.0;
    }

    private static IEntityExtension self(Entity entity) {
        return (IEntityExtension) entity;
    }

    static void applyWind(Entity entity) {
        self(entity).moreColorful$applyWind();
    }
}
