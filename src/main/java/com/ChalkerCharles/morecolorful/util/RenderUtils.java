package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.block.nature.DuckweedsBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.HangingBlock;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor.IQuadLighterMixin;
import com.google.common.base.Suppliers;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import net.neoforged.neoforge.common.util.Lazy;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@OnlyIn(Dist.CLIENT)
public final class RenderUtils {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static final float[] WHITE = new float[] { 1.0f, 1.0f, 1.0f };
    public static final Lazy<Object2IntMap<Block>> VERTEX_TYPE = Lazy.of(Suppliers.memoize(
            () -> {
                Object2IntMap<Block> map = new Object2IntOpenHashMap<>();
                BuiltInRegistries.BLOCK.forEach(block -> {
                    if (block instanceof PinkPetalsBlock)
                        map.put(block, 1);
                    else if (block instanceof VineBlock)
                        map.put(block, 2);
                    else if (block instanceof WaterlilyBlock
                            || block instanceof DuckweedsBlock)
                        map.put(block, 3);
                    else if (block instanceof ChainBlock
                            || block instanceof LanternBlock)
                        map.put(block, 4);
                    else if (block instanceof HangingBlock)
                        map.put(block, 5);
                    else if (block instanceof LeavesBlock)
                        map.put(block, 6);
                    else if (WeatherUtils.isWindSensitiveBlock(block))
                        map.put(block, 7);
                });
                return map;
            }
    ));
    public static final Map<SectionPos, ConcurrentMap<VertexPos, Vector4f>> VERTICES = new ConcurrentHashMap<>();

    public static void renderModelFaceAO(
            ModelBlockRenderer renderer,
            BlockAndTintGetter pLevel,
            BlockState pState,
            BlockPos pPos,
            PoseStack pPoseStack,
            VertexConsumer pConsumer,
            List<BakedQuad> pQuads,
            float[] pShape,
            BitSet pShapeFlags,
            ModelBlockRenderer.AmbientOcclusionFace pAoFace) {
        List<CompletableFuture<Void>> list = new ArrayList<>(pQuads.size());
        for (BakedQuad bakedquad : pQuads) {
            renderer.calculateShape(pLevel, pState, pPos, bakedquad.getVertices(), bakedquad.getDirection(), pShape, pShapeFlags);
            if (!ClientHooks.calculateFaceWithoutAO(pLevel, pState, pPos, bakedquad, pShapeFlags.get(0), pAoFace.brightness, pAoFace.lightmap))
                pAoFace.calculate(pLevel, pState, pPos, bakedquad.getDirection(), pShape, pShapeFlags, bakedquad.isShade());
            list.add(CompletableFuture.runAsync(
                    () -> putQuadData(
                            renderer,
                            pLevel,
                            pState,
                            pPos,
                            pConsumer,
                            pPoseStack.last(),
                            bakedquad,
                            pAoFace.brightness[0],
                            pAoFace.brightness[1],
                            pAoFace.brightness[2],
                            pAoFace.brightness[3],
                            pAoFace.lightmap[0],
                            pAoFace.lightmap[1],
                            pAoFace.lightmap[2],
                            pAoFace.lightmap[3]
                    ),
                    ThreadUtils.VERTEX_EXECUTOR
            ));
        }
        CompletableFuture.allOf(list.toArray(CompletableFuture[]::new)).join();
    }

    public static void renderModelFaceFlat(
            ModelBlockRenderer renderer,
            BlockAndTintGetter pLevel,
            BlockState pState,
            BlockPos pPos,
            int pPackedLight,
            boolean pRepackLight,
            PoseStack pPoseStack,
            VertexConsumer pConsumer,
            List<BakedQuad> pQuads,
            BitSet pShapeFlags) {
        List<CompletableFuture<Void>> list = new ArrayList<>(pQuads.size());
        for (BakedQuad bakedquad : pQuads) {
            if (pRepackLight) {
                renderer.calculateShape(pLevel, pState, pPos, bakedquad.getVertices(), bakedquad.getDirection(), null, pShapeFlags);
                BlockPos blockpos = pShapeFlags.get(0) ? pPos.relative(bakedquad.getDirection()) : pPos;
                pPackedLight = LevelRenderer.getLightColor(pLevel, pState, blockpos);
            }
            int packedLight = pPackedLight;
            float f = pLevel.getShade(bakedquad.getDirection(), bakedquad.isShade());
            list.add(CompletableFuture.runAsync(
                    () -> putQuadData(
                            renderer, pLevel, pState, pPos, pConsumer, pPoseStack.last(), bakedquad, f, f, f, f, packedLight, packedLight, packedLight, packedLight
                    ),
                    ThreadUtils.VERTEX_EXECUTOR
            ));
        }
        CompletableFuture.allOf(list.toArray(CompletableFuture[]::new)).join();
    }

    private static void putQuadData(
            ModelBlockRenderer renderer,
            BlockAndTintGetter pLevel,
            BlockState pState,
            BlockPos pPos,
            VertexConsumer pConsumer,
            PoseStack.Pose pPose,
            BakedQuad pQuad,
            float pBrightness0,
            float pBrightness1,
            float pBrightness2,
            float pBrightness3,
            int pLightmap0,
            int pLightmap1,
            int pLightmap2,
            int pLightmap3) {
        float f;
        float f1;
        float f2;
        if (pQuad.isTinted()) {
            int i = renderer.blockColors.getColor(pState, pLevel, pPos, pQuad.getTintIndex());
            f = (float)(i >> 16 & 0xFF) / 255.0F;
            f1 = (float)(i >> 8 & 0xFF) / 255.0F;
            f2 = (float)(i & 0xFF) / 255.0F;
        } else {
            f = 1.0F;
            f1 = 1.0F;
            f2 = 1.0F;
        }
        putBulkData(
                pConsumer,
                pPose,
                pQuad,
                new float[]{pBrightness0, pBrightness1, pBrightness2, pBrightness3},
                f,
                f1,
                f2,
                1.0F,
                new int[]{pLightmap0, pLightmap1, pLightmap2, pLightmap3},
                true,
                pState,
                pPos
        );
    }

    @SuppressWarnings("SameParameterValue")
    private static void putBulkData(
            VertexConsumer consumer,
            PoseStack.Pose pPose,
            BakedQuad pQuad,
            float[] pBrightness,
            float pRed,
            float pGreen,
            float pBlue,
            float pAlpha,
            int[] pLightmap,
            boolean pReadAlpha,
            BlockState state,
            BlockPos pPos) {
        int[] vertices = pQuad.getVertices();
        Vec3i vec3i = pQuad.getDirection().getNormal();
        Matrix4f matrix4f = pPose.pose();
        Vector3f vector3f = pPose.transformNormal((float) vec3i.getX(), (float) vec3i.getY(), (float) vec3i.getZ(), new Vector3f());
        int j = vertices.length / 8;
        int k = (int) (pAlpha * 255.0F);
        List<CompletableFuture<Void>> list = new ArrayList<>(j);

        try (MemoryStack memorystack = MemoryStack.stackPush()) {
            ByteBuffer bytebuffer = memorystack.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
            IntBuffer intbuffer = bytebuffer.asIntBuffer();

            for (int l = 0; l < j; l++) {
                intbuffer.clear();
                intbuffer.put(vertices, l * 8, 8);
                float f = bytebuffer.getFloat(0);
                float f1 = bytebuffer.getFloat(4);
                float f2 = bytebuffer.getFloat(8);
                int type = VERTEX_TYPE.get().getOrDefault(state.getBlock(), 0);
                CompletableFuture<Vector4f> future = CompletableFuture.supplyAsync(() -> getWaveData(pPos, state, f, f1, f2, type), ThreadUtils.WAVE_EXECUTOR);
                float f3;
                float f4;
                float f5;
                if (pReadAlpha) {
                    float f6 = (float) (bytebuffer.get(12) & 255);
                    float f7 = (float) (bytebuffer.get(13) & 255);
                    float f8 = (float) (bytebuffer.get(14) & 255);
                    f3 = f6 * pBrightness[l] * pRed;
                    f4 = f7 * pBrightness[l] * pGreen;
                    f5 = f8 * pBrightness[l] * pBlue;
                } else {
                    f3 = pBrightness[l] * pRed * 255.0F;
                    f4 = pBrightness[l] * pGreen * 255.0F;
                    f5 = pBrightness[l] * pBlue * 255.0F;
                }
                int vertexAlpha = pReadAlpha ? (int) ((pAlpha * (float) (bytebuffer.get(15) & 255) / 255.0F) * 255) : k;
                int i1 = FastColor.ARGB32.color(vertexAlpha, (int) f3, (int) f4, (int) f5);
                int j1 = consumer.applyBakedLighting(pLightmap[l], bytebuffer);
                float f10 = bytebuffer.getFloat(16);
                float f9 = bytebuffer.getFloat(20);
                Vector3f vector3f1 = matrix4f.transformPosition(f, f1, f2, new Vector3f());
                consumer.applyBakedNormals(vector3f, bytebuffer, pPose.normal());
                list.add(future.thenAccept(wave ->
                        addVertex((BufferBuilder) consumer, vector3f1.x(), vector3f1.y(), vector3f1.z(), i1, f10, f9, j1, vector3f.x(), vector3f.y(), vector3f.z(), wave)
                ));
            }
        }
        CompletableFuture.allOf(list.toArray(CompletableFuture[]::new)).join();
    }

    private static void addVertex(
            BufferBuilder builder,
            float pX,
            float pY,
            float pZ,
            int pColor,
            float pU,
            float pV,
            int pPackedLight,
            float pNormalX,
            float pNormalY,
            float pNormalZ,
            Vector4f wave) {
        long i = builder.beginVertex();
        MemoryUtil.memPutFloat(i, pX);
        MemoryUtil.memPutFloat(i + 4L, pY);
        MemoryUtil.memPutFloat(i + 8L, pZ);
        BufferBuilder.putRgba(i + 12L, pColor);
        MemoryUtil.memPutFloat(i + 16L, pU);
        MemoryUtil.memPutFloat(i + 20L, pV);
        BufferBuilder.putPackedUv(i + 24L, pPackedLight);
        MemoryUtil.memPutByte(i + 28L, BufferBuilder.normalIntValue(pNormalX));
        MemoryUtil.memPutByte(i + 29L, BufferBuilder.normalIntValue(pNormalY));
        MemoryUtil.memPutByte(i + 30L, BufferBuilder.normalIntValue(pNormalZ));
        MemoryUtil.memPutFloat(i + 31L, wave.x);
        MemoryUtil.memPutFloat(i + 35L, wave.y);
        MemoryUtil.memPutFloat(i + 39L, wave.z);
        MemoryUtil.memPutFloat(i + 43L, wave.w);
    }

    public static void quadLighterProcess(QuadLighter lighter, VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quad, BlockState state, BlockPos pos) {
        lighter.computeLightingForQuad(quad);
        IQuadLighterMixin lighterMixin = ((IQuadLighterMixin) lighter);
        float[] color = quad.isTinted() ? lighterMixin.invokeGetColorFast(quad.getTintIndex()) : WHITE;
        putBulkData(consumer, pose, quad, lighterMixin.brightness(), color[0], color[1], color[2], 1.0f, lighterMixin.lightmap(), true, state, pos);
    }

    public static void fluidVertex(
            VertexConsumer consumer,
            float pX,
            float pY,
            float pZ,
            float red,
            float green,
            float blue,
            float alpha,
            float pU,
            float pV,
            int pPackedLight,
            Vector4f wave) {
        long i = ((BufferBuilder) consumer).beginVertex();
        MemoryUtil.memPutFloat(i, pX);
        MemoryUtil.memPutFloat(i + 4L, pY);
        MemoryUtil.memPutFloat(i + 8L, pZ);
        MemoryUtil.memPutByte(i + 12L, (byte) (red * 255.0F));
        MemoryUtil.memPutByte(i + 13L, (byte) (green * 255.0F));
        MemoryUtil.memPutByte(i + 14L, (byte) (blue * 255.0F));
        MemoryUtil.memPutByte(i + 15L, (byte) (alpha * 255.0F));
        MemoryUtil.memPutFloat(i + 16L, pU);
        MemoryUtil.memPutFloat(i + 20L, pV);
        BufferBuilder.putPackedUv(i + 24L, pPackedLight);
        MemoryUtil.memPutByte(i + 28L, BufferBuilder.normalIntValue(0.0F));
        MemoryUtil.memPutByte(i + 29L, BufferBuilder.normalIntValue(1.0F));
        MemoryUtil.memPutByte(i + 30L, BufferBuilder.normalIntValue(0.0F));
        MemoryUtil.memPutFloat(i + 31L, wave.x);
        MemoryUtil.memPutFloat(i + 35L, wave.y);
        MemoryUtil.memPutFloat(i + 39L, wave.z);
        MemoryUtil.memPutFloat(i + 43L, 0);
    }

    public static Vector4f getWaveData(BlockPos pos, BlockState state, float x, float y, float z, int type) {
        if (Config.WIND_EFFECT_CLIENT.isTrue()) {
            SectionPos sectionPos = SectionPos.of(pos);
            VertexPos vertexPos = VertexPos.of(pos, x, y, z);
            return VERTICES.computeIfAbsent(sectionPos, k -> new ConcurrentHashMap<>())
                    .computeIfAbsent(vertexPos, k -> WavyBlockUtils.getWindSpeedByVertex(minecraft.level, state, pos, x, y, z, type));
        }
        return Constants.ZERO_VEC4;
    }

    public static Vector4f getFluidWaveData(BlockPos pos, float x, float y, float z) {
        if (Config.WIND_EFFECT_CLIENT.isTrue()) {
            SectionPos sectionPos = SectionPos.of(pos);
            VertexPos vertexPos = VertexPos.of(pos, x, y, z);
            return VERTICES.computeIfAbsent(sectionPos, k -> new ConcurrentHashMap<>())
                    .computeIfAbsent(vertexPos, k -> WavyBlockUtils.getWindSpeedByLiquidVertex(minecraft.level, pos, x, y, z).join());
        }
        return Constants.ZERO_VEC4;
    }

    public record VertexPos(BlockPos blockPos, float x, float y, float z) {
        public static VertexPos of(BlockPos blockPos, float x, float y, float z) {
            return new VertexPos(blockPos, x, y, z);
        }
    }
}
