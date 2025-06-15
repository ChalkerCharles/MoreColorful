package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses", remap = false)
public abstract class DefaultTerrainRenderPassesMixin {
    @Shadow
    @Final
    @Mutable
    public static TerrainRenderPass[] ALL;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void modifyPasses(CallbackInfo ci) {
        ArrayList<TerrainRenderPass> passes = new ArrayList<>(Arrays.asList(ALL));
        passes.add(SodiumCompat.WAVY_CUTOUT_PASS);
        ALL = passes.toArray(TerrainRenderPass[]::new);
    }
}
