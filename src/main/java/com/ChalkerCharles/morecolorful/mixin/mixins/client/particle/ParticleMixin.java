package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.Predicates;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector2f;
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
    protected double xd;

    @Shadow
    protected double zd;

    @Shadow
    public abstract Vec3 getPos();

    @Shadow
    @Final
    protected RandomSource random;

    @Override
    public void moreColorful$applyWind() {
        if (!(this instanceof WindSensitive windSensitive && windSensitive.isWindSensitive())) return;
        if (!this.stoppedByCollision) {
            WeatherUtils.canApplyWind(level, this.getPos()).thenAccept(Predicates.ifTrueThen(() -> {
                Vector2f globalWind = LevelSavedData.getGlobalWindSpeed(this.level);
                double windX = globalWind.x() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
                double windZ = globalWind.y() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
                this.xd = Mth.clamp(xd + windX * 0.02, -Math.abs(windX), Math.abs(windX));
                this.zd = Mth.clamp(zd + windZ * 0.02, -Math.abs(windZ), Math.abs(windZ));
            }));
        }
    }
}
