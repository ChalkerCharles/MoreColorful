package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.client.texture.DynamicTextureAtlas;
import com.ChalkerCharles.morecolorful.client.texture.PapercuttingTextureManager;
import com.ChalkerCharles.morecolorful.common.block.entity.PapercuttingBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.state.BlockState;

public class PapercuttingRenderer implements BlockEntityRenderer<PapercuttingBlockEntity> {
    public PapercuttingRenderer(BlockEntityRendererProvider.Context ignore) {}

    @Override
    public void render(PapercuttingBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState state = pBlockEntity.getBlockState();
        float f = state.getValue(PapercuttingBlock.FACING).getOpposite().toYRot();
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.0F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180 - f));
        pPoseStack.scale(-0.0625F, 0.0625F, 0.0625F);
        PoseStack.Pose pose = pPoseStack.last();
        DynamicTextureAtlas.Sprite sprite = PapercuttingTextureManager.get(state.getBlock(), pBlockEntity.stencil);
        buildPapercuttingModel(pBufferSource.getBuffer(PapercuttingTextureManager.RENDER_TYPE), pose, sprite, pPackedLight);
        pPoseStack.popPose();
    }

    private static void buildPapercuttingModel(VertexConsumer consumer, PoseStack.Pose pose, DynamicTextureAtlas.Sprite sprite, int pPackedLight) {
        float u0 = sprite.u0();
        float v0 = sprite.v0();
        float u1 = sprite.u1();
        float v1 = sprite.v1();

        vertex(pose, consumer, 8, 16, u0, v0, -1, pPackedLight);
        vertex(pose, consumer, 8, 0, u0, v1, -1, pPackedLight);
        vertex(pose, consumer, -8, 0, u1, v1, -1, pPackedLight);
        vertex(pose, consumer, -8, 16, u1, v0, -1, pPackedLight);

        vertex(pose, consumer, 8, 16, u0, v0, 1, pPackedLight);
        vertex(pose, consumer, -8, 16, u1, v0, 1, pPackedLight);
        vertex(pose, consumer, -8, 0, u1, v1, 1, pPackedLight);
        vertex(pose, consumer, 8, 0, u0, v1, 1, pPackedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, float u, float v, int normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, -7.2F)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 0, normalZ);
    }
}
