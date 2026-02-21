package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.common.block.entity.PennantBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class PennantRenderer implements BlockEntityRenderer<PennantBlockEntity> {
    public static final Map<Block, Material> TEXTURES;

    public PennantRenderer(BlockEntityRendererProvider.Context ignore) {}

    @Override
    public void render(PennantBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockState = pBlockEntity.getBlockState();
        VertexConsumer consumer = TEXTURES.get(blockState.getBlock()).buffer(pBufferSource, RenderType::entityCutout);
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.0F, 0.5F);
        pPoseStack.scale(0.0625F, 0.0625F, -0.0625F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRot(pPartialTick)));
        PoseStack.Pose pose = pPoseStack.last();
        buildPennantModel(pose, consumer, pPackedLight, pBlockEntity.frame);
        pPoseStack.popPose();
    }

    private static void buildPennantModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, int frame) {
        int i = frame % 8;
        float v0 = (float) i / 8;
        float v1 = (float) (i + 1) / 8;
        vertex(pose, consumer, 16, -1, 0.0F, v0, packedLight);
        vertex(pose, consumer, 0, -1, 0.0F, v1, packedLight);
        vertex(pose, consumer, 0, 15, 1.0F, v1, packedLight);
        vertex(pose, consumer, 16, 15, 1.0F, v0, packedLight);
        vertex(pose, consumer, 16, -1, 0.0F, v0, packedLight);
        vertex(pose, consumer, 16, 15, 1.0F, v0, packedLight);
        vertex(pose, consumer, 0, 15, 1.0F, v1, packedLight);
        vertex(pose, consumer, 0, -1, 0.0F, v1, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int y, int z, float u, float v, int packedLight) {
        consumer.addVertex(pose, 0, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 1, 0, 0);
    }

    static {
        ImmutableMap.Builder<Block, Material> builder = ImmutableMap.builder();
        for (Block block : PennantBlock.ALL_BLOCKS.get()) {
            String name = BuiltInRegistries.BLOCK.getKey(block).getPath();
            builder.put(block, new Material(Atlases.BLOCK_SHEET, MoreColorful.location("block/" + name)));
        }
        TEXTURES = builder.build();
    }
}
