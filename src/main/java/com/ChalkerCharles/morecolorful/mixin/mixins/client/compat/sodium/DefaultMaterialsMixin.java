package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.shader.ModRenderTypes;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.DefaultMaterials", remap = false)
public abstract class DefaultMaterialsMixin {
    @Inject(method = "forRenderLayer", at = @At("HEAD"), cancellable = true)
    private static void forRenderLayer(RenderType layer, CallbackInfoReturnable<Material> cir) {
        if (layer == ModRenderTypes.WAVY_CUTOUT_MIPPED)
            cir.setReturnValue(SodiumCompat.WAVY_CUTOUT_MIPPED);
        else if (layer == ModRenderTypes.WAVY_CUTOUT)
            cir.setReturnValue(SodiumCompat.WAVY_CUTOUT);
    }
}
