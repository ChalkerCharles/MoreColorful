package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.entity.PinwheelBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PinwheelBlock;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

import java.util.List;

import static com.ChalkerCharles.morecolorful.client.renderer.item.PinwheelRenderer.TEXTURES;

public class PinwheelRenderer implements BlockEntityRenderer<PinwheelBlockEntity> {
    public static final ResourceLocation STICK_TEXTURE = MoreColorful.location("textures/block/pinwheel_stick.png");

    public PinwheelRenderer(BlockEntityRendererProvider.Context ignore) {}

    @Override
    public void render(PinwheelBlockEntity blockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        BlockState blockstate = blockEntity.getBlockState();
        float f1 = 180.0F - RotationSegment.convertToDegrees(blockstate.getValue(PinwheelBlock.ROTATION));
        VertexConsumer stickConsumer = pBufferSource.getBuffer(RenderType.entitySolid(STICK_TEXTURE));
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.0F, 0.5F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(f1));
        pPoseStack.scale(0.0625F, 0.0625F, 0.0625F);
        PoseStack.Pose pose = pPoseStack.last();
        buildStickModel(pose, stickConsumer, pPackedLight);
        List<DyeColor> colors = blockEntity.getColors();
        int frame = blockEntity.lerpFrame(pPartialTick) - 2;
        for (int i = 0; i < 4; i++) {
            DyeColor color = colors.get(i);
            int j = color.getId();
            if (j > 15) j = 0;
            int k = (frame + (i << 2)) & 15;
            VertexConsumer wheelConsumer = TEXTURES[k][j].buffer(pBufferSource, RenderType::entityCutout);
            buildWheelModel(pose, wheelConsumer, pPackedLight);
        }
        pPoseStack.popPose();
    }

    private static void buildStickModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, 1, 10, 0, 0.0F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, 1, 0, 0, 0.0F, 0.625F, 0, -1, packedLight);
        vertex(pose, consumer, -1, 0, 0, 0.125F, 0.625F, 0, -1, packedLight);
        vertex(pose, consumer, -1, 10, 0, 0.125F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, 1, 10, 0, 0.125F, 0.0F, 0, 1, packedLight);
        vertex(pose, consumer, -1, 10, 0, 0.0F, 0.0F, 0, 1, packedLight);
        vertex(pose, consumer, -1, 0, 0, 0.0F, 0.625F, 0, 1, packedLight);
        vertex(pose, consumer, 1, 0, 0, 0.125F, 0.625F, 0, 1, packedLight);

        vertex(pose, consumer, -1, 10, -1, 0.125F, 0.0F, 1, 0, packedLight);
        vertex(pose, consumer, -1, 10, 0, 0.125F, 0.0625F, 1, 0, packedLight);
        vertex(pose, consumer, 1, 10, 0, 0.25F, 0.0625F, 1, 0, packedLight);
        vertex(pose, consumer, 1, 10, -1, 0.25F, 0.0F, 1, 0, packedLight);
        vertex(pose, consumer, -1, 10, -1, 0.25F, 0.0F, -1, 0, packedLight);
        vertex(pose, consumer, 1, 10, -1, 0.125F, 0.0F, -1, 0, packedLight);
        vertex(pose, consumer, 1, 10, 0, 0.125F, 0.0625F, -1, 0, packedLight);
        vertex(pose, consumer, -1, 10, 0, 0.25F, 0.0625F, -1, 0, packedLight);

        vertex(pose, consumer, 1, 11, -1, 0.25F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, 1, 9, -1, 0.25F, 0.125F, 0, -1, packedLight);
        vertex(pose, consumer, -1, 9, -1, 0.375F, 0.125F, 0, -1, packedLight);
        vertex(pose, consumer, -1, 11, -1, 0.375F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, 1, 11, -1, 0.25F, 0.0F, 0, 1, packedLight);
        vertex(pose, consumer, -1, 11, -1, 0.375F, 0.0F, 0, 1, packedLight);
        vertex(pose, consumer, -1, 9, -1, 0.375F, 0.125F, 0, 1, packedLight);
        vertex(pose, consumer, 1, 9, -1, 0.25F, 0.125F, 0, 1, packedLight);
    }

    private static void buildWheelModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, 9, 17, -1, 0.0F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, -7, 17, -1, 1.0F, 0.0F, 0, -1, packedLight);
        vertex(pose, consumer, -7, 1, -1, 1.0F, 1.0F, 0, -1, packedLight);
        vertex(pose, consumer, 9, 1, -1, 0.0F, 1.0F, 0, -1, packedLight);
        vertex(pose, consumer, 9, 17, -1, 0.0F, 0.0F, 0, 1, packedLight);
        vertex(pose, consumer, 9, 1, -1, 0.0F, 1.0F, 0, 1, packedLight);
        vertex(pose, consumer, -7, 1, -1, 1.0F, 1.0F, 0, 1, packedLight);
        vertex(pose, consumer, -7, 17, -1, 1.0F, 0.0F, 0, 1, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, int z, float u, float v, int normalY, int normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, normalY, normalZ);
    }
}
