package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
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
    private double render$x(double original, @Local(ordinal = 1) float tickDelta) {
        if (Config.windAndCloud) {
            return original - RenderUtils.getCloudMovementX(tickDelta);
        }
        return original;
    }

    @ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;z()D"))
    private double render$z(double original, @Local(ordinal = 1) float tickDelta) {
        if (Config.windAndCloud) {
            return original - RenderUtils.getCloudMovementZ(tickDelta);
        }
        return original;
    }
}
