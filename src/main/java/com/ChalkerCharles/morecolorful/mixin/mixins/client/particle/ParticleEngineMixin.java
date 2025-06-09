package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.IParticleExtension;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleEngineMixin {
    @Inject(method = "tickParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;tick()V", shift = At.Shift.AFTER))
    private void tickParticle(Particle pParticle, CallbackInfo ci) {
        if (Config.WIND_EFFECT_CLIENT.isTrue()) {
            ((IParticleExtension) pParticle).moreColorful$applyWind();
        }
    }
}
