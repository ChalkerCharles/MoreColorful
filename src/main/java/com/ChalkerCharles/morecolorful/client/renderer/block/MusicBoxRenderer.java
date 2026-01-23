package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.entity.MusicBoxBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.musical.MusicBoxBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class MusicBoxRenderer implements BlockEntityRenderer<MusicBoxBlockEntity> {
    public static final ResourceLocation CRANK_TEXTURE = MoreColorful.location("textures/block/music_box.png");

    public MusicBoxRenderer(BlockEntityRendererProvider.Context ignore) {}

    @Override
    public void render(MusicBoxBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState state = pBlockEntity.getBlockState();
        float f = state.getValue(MusicBoxBlock.FACING).getOpposite().toYRot();
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entityCutout(CRANK_TEXTURE));
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.15625F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-f));
        pPoseStack.scale(0.0625F, 0.0625F, 0.0625F);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pBlockEntity.getRot(pPartialTick)));
        PoseStack.Pose pose = pPoseStack.last();
        buildCrankModel(pose, vertexconsumer, pPackedLight);
        pPoseStack.popPose();
    }

    private static void buildCrankModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, -5, 0.5F, 0.75F, 0.8125F, -1, packedLight);
        vertex(pose, consumer, -5, -2.5F, 0.75F, 1.0F, -1, packedLight);
        vertex(pose, consumer, -9, -2.5F, 1.0F, 1.0F, -1, packedLight);
        vertex(pose, consumer, -9, 0.5F, 1.0F, 0.8125F, -1, packedLight);
        vertex(pose, consumer, -5, 0.5F, 0.75F, 0.8125F, 1, packedLight);
        vertex(pose, consumer, -9, 0.5F, 1.0F, 0.8125F, 1, packedLight);
        vertex(pose, consumer, -9, -2.5F, 1.0F, 1.0F, 1, packedLight);
        vertex(pose, consumer, -5, -2.5F, 0.75F, 1.0F, 1, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, float y, float u, float v, int normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, 0)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 0, normalZ);
    }
}
