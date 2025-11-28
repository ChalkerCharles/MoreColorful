package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin implements IEntityExtension {
    @Shadow
    private Level level;
    @Shadow
    @Final
    protected RandomSource random;
    @Shadow
    private Vec3 position;
    @Shadow
    public abstract boolean isPassenger();
    @Shadow
    public abstract void push(double pX, double pY, double pZ);

    @Override
    public void moreColorful$applyWind() {
        if (!WeatherUtils.isWindSensitive(this) || this.isPassenger()) return;
        Vec3 pos = this.position;
        boolean global = WeatherUtils.canApplyWind(level, pos.x, pos.y + 0.5, pos.z);
        if (global || LevelSavedData.isInWindZoneAffectingEntity(level, pos)) {
            Vector3f wind = WeatherUtils.getWindSpeedAffectingEntity(level, pos, global);
            double windX = wind.x * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z * 0.008 * WeatherUtils.getRandomSpeedMultiplier(random);
            double d = this.moreColorful$windResistance();
            double x = Mth.clamp(windX * 0.02, -Math.abs(windX), Math.abs(windX)) * d;
            double y = windY * 0.02 * d;
            double z = Mth.clamp(windZ * 0.02, -Math.abs(windZ), Math.abs(windZ)) * d;
            this.push(x, y, z);
        }
    }

    @Override
    public double moreColorful$windResistance() {
        return 1.0;
    }
}
