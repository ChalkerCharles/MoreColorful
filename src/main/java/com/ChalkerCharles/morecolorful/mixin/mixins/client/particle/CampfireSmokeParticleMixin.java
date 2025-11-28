package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.CampfireSmokeParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CampfireSmokeParticle.class)
public abstract class CampfireSmokeParticleMixin extends TextureSheetParticle implements WindSensitive {
    private CampfireSmokeParticleMixin(ClientLevel pLevel, double pX, double pY, double pZ) {
        super(pLevel, pX, pY, pZ);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        if (this.age < 20) return;
        if (RenderUtils.isClientWindOn) {
            boolean global = WeatherUtils.canApplyWind(level, x, y, z);
            if (global || LevelSavedData.isInWindZone(level, x, y, z)) {
                Vector3f vec = WeatherUtils.getWindSpeedAt(level, x, y, z, global);
                float wind = vec.length();
                float i = this.lifetime > 150 ? 0.015F : 0.025F;
                float j = Math.max(0.25F, wind * 0.1F);
                this.alpha = Math.max(0, alpha - (float) Math.tanh(wind * 0.5) * i * j);
            }
        }
    }
}
