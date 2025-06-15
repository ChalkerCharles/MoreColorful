package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion$DeviceResources", remap = false)
public abstract class DeviceResourcesMixin {
    @ModifyReceiver(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexType;getVertexFormat()Lnet/caffeinemc/mods/sodium/client/gl/attribute/GlVertexFormat;"))
    private ChunkVertexType constructor(ChunkVertexType original) {
        return SodiumCompat.WAVY;
    }
}
