package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

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
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, x, y, z);
        if (wind != null) {
            double windX = wind.x * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z * 0.04 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd = Mth.clamp(xd + windX * 0.02, Math.min(xd, windX), Math.max(xd, windX));
            this.yd += windY * 0.02;
            this.zd = Mth.clamp(zd + windZ * 0.02, Math.min(zd, windZ), Math.max(zd, windZ));
        }
    }
}
