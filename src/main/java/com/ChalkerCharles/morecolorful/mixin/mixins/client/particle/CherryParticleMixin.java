package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CherryParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CherryParticle.class)
public abstract class CherryParticleMixin extends TextureSheetParticle implements WindSensitive, IParticleExtension {
    private CherryParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @Override
    public void moreColorful$applyWind() {
        if (WeatherUtils.canApplyWind(level, this.getPos())) {
            Vector2f globalWind = LevelSavedData.getGlobalWindSpeed(this.level);
            double windX = globalWind.x() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            double windZ = globalWind.y() * 0.05 * WeatherUtils.getRandomSpeedMultiplier(random);
            this.xd = Mth.clamp(xd + windX * 0.02, Math.min(xd, windX), Math.max(xd, windX));
            this.zd = Mth.clamp(zd + windZ * 0.02, Math.min(zd, windZ), Math.max(zd, windZ));
        }
    }
}
