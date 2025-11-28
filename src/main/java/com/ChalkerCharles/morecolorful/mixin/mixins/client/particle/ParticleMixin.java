package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Particle.class)
public abstract class ParticleMixin implements IParticleExtension {
    @Shadow
    private boolean stoppedByCollision;
    @Shadow
    @Final
    protected ClientLevel level;
    @Shadow
    protected double x;
    @Shadow
    protected double y;
    @Shadow
    protected double z;
    @Shadow
    protected double xd;
    @Shadow
    protected double yd;
    @Shadow
    protected double zd;
    @Shadow
    @Final
    protected RandomSource random;

    @Override
    public void moreColorful$applyWind() {
        if (!WeatherUtils.isWindSensitive(this) || this.stoppedByCollision) return;
        boolean global = WeatherUtils.canApplyWind(level, x, y, z);
        if (global || LevelSavedData.isInWindZone(level, x, y, z)) {
            Vector3f wind = WeatherUtils.getWindSpeedAt(level, x, y, z, global);
            double windX = wind.x * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd = Mth.clamp(xd + windX * 0.02, -Math.abs(windX), Math.abs(windX));
            this.yd += windY * 0.02;
            this.zd = Mth.clamp(zd + windZ * 0.02, -Math.abs(windZ), Math.abs(windZ));
        }
    }
}
