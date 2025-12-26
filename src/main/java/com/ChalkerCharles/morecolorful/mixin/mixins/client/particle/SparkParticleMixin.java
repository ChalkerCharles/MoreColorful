package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.client.particle.FireworkParticles$SparkParticle")
public abstract class SparkParticleMixin extends SimpleAnimatedParticle implements WindSensitive, IParticleExtension {
    private SparkParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ, SpriteSet pSprites, float pGravity) {
        super(pLevel, pX, pY, pZ, pSprites, pGravity);
    }

    @Override
    public void moreColorful$applyWind() {
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, x, y, z);
        if (wind != null) {
            double windX = wind.x() * 0.001 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windY = wind.y() * 0.001 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = wind.z() * 0.001 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd += windX;
            this.yd += windY;
            this.zd += windZ;
        }
    }
}
