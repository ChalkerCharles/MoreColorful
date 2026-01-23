package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.compat.SodiumWavyVertices;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.builder.ChunkMeshBufferBuilder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.impl.CompactChunkVertex;
import org.joml.Vector3f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.BlockRenderer", remap = false)
public abstract class BlockRendererMixin {
    @Unique
    private final long[] moreColorful$waveData = new long[4];
    @Unique
    private final float[] moreColorful$posData = new float[12];

    @Inject(method = "bufferQuad", at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;light:I", opcode = Opcodes.PUTFIELD, shift = At.Shift.AFTER))
    private void bufferQuad$0(CallbackInfo ci, @Local(ordinal = 0) int i, @Local ChunkVertexEncoder.Vertex out, @Local Vector3f offset) {
        if (!Config.wavyBlocks) return;
        float x = out.x - offset.x;
        float y = out.y - offset.y;
        float z = out.z - offset.z;
        long wave = RenderUtils.getWaveData(x, y, z);
        this.moreColorful$waveData[i] = wave;
        int j = i * 3;
        this.moreColorful$posData[j] = out.x;
        this.moreColorful$posData[j + 1] = out.y;
        this.moreColorful$posData[j + 2] = out.z;
    }

    @Inject(method = "bufferQuad", at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/builder/ChunkMeshBufferBuilder;push([Lnet/caffeinemc/mods/sodium/client/render/chunk/vertex/format/ChunkVertexEncoder$Vertex;I)V"))
    private void bufferQuad$1(CallbackInfo ci, @Local(ordinal = 0) TerrainRenderPass pass, @Local ModelQuadFacing facing, @Local ChunkMeshBufferBuilder vertexBuffer) {
        if (!Config.wavyBlocks) return;
        SodiumWavyVertices vertices = SodiumCompat.getWavyVertices(pass);
        if (vertices == null) return;
        boolean translucent = pass.isTranslucent();
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
