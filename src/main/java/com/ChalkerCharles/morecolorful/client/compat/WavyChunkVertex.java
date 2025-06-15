package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IVertexExtension;
import net.caffeinemc.mods.sodium.api.util.ColorARGB;
import net.caffeinemc.mods.sodium.client.gl.attribute.GlVertexAttributeFormat;
import net.caffeinemc.mods.sodium.client.gl.attribute.GlVertexFormat;
import net.caffeinemc.mods.sodium.client.render.chunk.shader.ChunkShaderBindingPoints;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexEncoder;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.ChunkVertexType;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.impl.DefaultChunkMeshAttributes;
import net.caffeinemc.mods.sodium.client.render.vertex.VertexFormatAttribute;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryUtil;

@OnlyIn(Dist.CLIENT)
public class WavyChunkVertex implements ChunkVertexType {
    public static final int STRIDE = 36;
    public static final VertexFormatAttribute WAVE = new VertexFormatAttribute("MORECOLORFUL_WAVE", GlVertexAttributeFormat.FLOAT, 4, false, false);
    public static final GlVertexFormat VERTEX_FORMAT = GlVertexFormat.builder(STRIDE)
        .addElement(DefaultChunkMeshAttributes.POSITION, ChunkShaderBindingPoints.ATTRIBUTE_POSITION, 0)
        .addElement(DefaultChunkMeshAttributes.COLOR, ChunkShaderBindingPoints.ATTRIBUTE_COLOR, 8)
        .addElement(DefaultChunkMeshAttributes.TEXTURE, ChunkShaderBindingPoints.ATTRIBUTE_TEXTURE, 12)
        .addElement(DefaultChunkMeshAttributes.LIGHT_MATERIAL_INDEX, ChunkShaderBindingPoints.ATTRIBUTE_LIGHT_MATERIAL_INDEX, 16)
        .addElement(WAVE, 4, 20)
        .build();

    @Override
    public GlVertexFormat getVertexFormat() {
        return VERTEX_FORMAT;
    }

    @Override
    public ChunkVertexEncoder getEncoder() {
        return (ptr, materialBits, vertices, section) -> {
            float texCentroidU = 0.0F;
            float texCentroidV = 0.0F;

            for(ChunkVertexEncoder.Vertex vertex : vertices) {
                texCentroidU += vertex.u;
                texCentroidV += vertex.v;
            }

            texCentroidU *= 0.25F;
            texCentroidV *= 0.25F;

            for (int i = 0; i < 4; ++i) {
                ChunkVertexEncoder.Vertex vertex = vertices[i];
                int x = quantizePosition(vertex.x);
                int y = quantizePosition(vertex.y);
                int z = quantizePosition(vertex.z);
                int u = encodeTexture(texCentroidU, vertex.u);
                int v = encodeTexture(texCentroidV, vertex.v);
                int light = encodeLight(vertex.light);
                Vector4f wave = ((IVertexExtension) vertex).moreColorful$getWave();
                MemoryUtil.memPutInt(ptr, packPositionHi(x, y, z));
                MemoryUtil.memPutInt(ptr + 4L, packPositionLo(x, y, z));
                MemoryUtil.memPutInt(ptr + 8L, ColorARGB.mulRGB(vertex.color, vertex.ao));
                MemoryUtil.memPutInt(ptr + 12L, packTexture(u, v));
                MemoryUtil.memPutInt(ptr + 16L, packLightAndData(light, materialBits, section));
                MemoryUtil.memPutFloat(ptr + 20L, wave.x);
                MemoryUtil.memPutFloat(ptr + 24L, wave.y);
                MemoryUtil.memPutFloat(ptr + 28L, wave.z);
                MemoryUtil.memPutFloat(ptr + 32L, wave.w);
                ptr += STRIDE;
            }

            return ptr;
        };
    }

    private static int packPositionHi(int x, int y, int z) {
        return (x >>> 10 & 1023) | (y >>> 10 & 1023) << 10 | (z >>> 10 & 1023) << 20;
    }

    private static int packPositionLo(int x, int y, int z) {
        return (x & 1023) | (y & 1023) << 10 | (z & 1023) << 20;
    }

    private static int quantizePosition(float position) {
        return (int)(normalizePosition(position) * 1048576.0F) & 1048575;
    }

    private static float normalizePosition(float v) {
        return (8.0F + v) / 32.0F;
    }

    private static int packTexture(int u, int v) {
        return (u & 65535) | (v & 65535) << 16;
    }

    private static int encodeTexture(float center, float x) {
        int bias = x < center ? 1 : -1;
        int quantized = Math.round(x * 32768.0F) + bias;
        return quantized & 32767 | sign(bias) << 15;
    }

    private static int encodeLight(int light) {
        int sky = Mth.clamp(light >>> 16 & 255, 8, 248);
        int block = Mth.clamp(light & 255, 8, 248);
        return block | sky << 8;
    }

    private static int packLightAndData(int light, int material, int section) {
        return (light & 65535) | (material & 255) << 16 | (section & 255) << 24;
    }

    private static int sign(int x) {
        return x >>> 31;
    }
}
