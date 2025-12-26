package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.immediate.CloudRenderer", remap = false)
public abstract class CloudRendererMixin {
    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;x()D"))
    private double render$x(double original, @Local double cloudTime, @Local(ordinal = 1) float tickDelta) {
        if (!RenderUtils.isCalm && RenderUtils.windAndCloud) {
            return original - cloudTime * RenderUtils.lerpWindSpeedX(tickDelta) * 0.03125;
        }
        return original;
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;z()D"))
    private double render$z(double original, @Local double cloudTime, @Local(ordinal = 1) float tickDelta) {
        if (!RenderUtils.isCalm && RenderUtils.windAndCloud) {
            return original - cloudTime * RenderUtils.lerpWindSpeedZ(tickDelta) * 0.03125;
        }
        return original;
    }
}
