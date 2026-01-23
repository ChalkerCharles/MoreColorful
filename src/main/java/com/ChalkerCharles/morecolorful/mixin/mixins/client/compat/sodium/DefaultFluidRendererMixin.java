package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.compat.SodiumWavyVertices;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.material.Material;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.impl.CompactChunkVertex;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer", remap = false)
public abstract class DefaultFluidRendererMixin {
    @Unique
    private final long[] moreColorful$waveData = new long[4];
    @Unique
    private final float[] moreColorful$posData = new float[12];

    @Inject(method = "writeQuad", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;light:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void writeQuad$0(CallbackInfo ci, @Local int i, @Local ChunkVertexEncoder.Vertex out, @Local(argsOnly = true) boolean flip) {
        if (!Config.wavyBlocks) return;
        float x = out.x, y = out.y, z = out.z;
        long wave = RenderUtils.getFluidWaveData(x, y, z);
        int idx = flip ? (3 - i + 1) & 3 : i;
        this.moreColorful$waveData[idx] = wave;
        int j = idx * 3;
        this.moreColorful$posData[j] = x;
        this.moreColorful$posData[j + 1] = y;
        this.moreColorful$posData[j + 2] = z;
    }

    @Inject(method = "writeQuad", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/builder/ChunkMeshBufferBuilder;push([Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/material/Material;)V"))
    private void writeQuad$1(CallbackInfo ci, @Local(argsOnly = true) Material material, @Local(argsOnly = true) ModelQuadFacing facing, @Local ChunkMeshBufferBuilder vertexBuffer) {
        if (!Config.wavyBlocks) return;
        SodiumWavyVertices vertices = SodiumCompat.getWavyVertices(material.pass);
        if (vertices == null) return;
        boolean translucent = material.isTranslucent();
        int stride = CompactChunkVertex.STRIDE;
        int index = vertexBuffer.count() * stride;
        for (int i = 0; i < 4; i++, index += stride) {
            long wave = this.moreColorful$waveData[i];
            if (!translucent && wave == 0L) continue;
            int j = i * 3;
            float x = this.moreColorful$posData[j];
            float y = this.moreColorful$posData[j + 1];
            float z = this.moreColorful$posData[j + 2];
            vertices.addVertex(facing, index, wave, x, y, z);
        }
    }
}
