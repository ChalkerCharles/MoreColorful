package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.PinwheelBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PinwheelBlock;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

import java.util.Map;

public class PinwheelRenderer implements BlockEntityRenderer<PinwheelBlockEntity> {
    public static final ResourceLocation STICK_TEXTURE = MoreColorful.location("textures/block/pinwheel_stick.png");
    public static final Map<Block, ResourceLocation> TEXTURES = ImmutableMap.<Block, ResourceLocation>builder()
            .put(ModBlocks.WHITE_PINWHEEL.get(), MoreColorful.location("textures/block/white_pinwheel.png"))
            .put(ModBlocks.ORANGE_PINWHEEL.get(), MoreColorful.location("textures/block/orange_pinwheel.png"))
            .put(ModBlocks.MAGENTA_PINWHEEL.get(), MoreColorful.location("textures/block/magenta_pinwheel.png"))
            .put(ModBlocks.LIGHT_BLUE_PINWHEEL.get(), MoreColorful.location("textures/block/light_blue_pinwheel.png"))
            .put(ModBlocks.YELLOW_PINWHEEL.get(), MoreColorful.location("textures/block/yellow_pinwheel.png"))
            .put(ModBlocks.LIME_PINWHEEL.get(), MoreColorful.location("textures/block/lime_pinwheel.png"))
            .put(ModBlocks.PINK_PINWHEEL.get(), MoreColorful.location("textures/block/pink_pinwheel.png"))
            .put(ModBlocks.GRAY_PINWHEEL.get(), MoreColorful.location("textures/block/gray_pinwheel.png"))
            .put(ModBlocks.LIGHT_GRAY_PINWHEEL.get(), MoreColorful.location("textures/block/light_gray_pinwheel.png"))
            .put(ModBlocks.CYAN_PINWHEEL.get(), MoreColorful.location("textures/block/cyan_pinwheel.png"))
            .put(ModBlocks.PURPLE_PINWHEEL.get(), MoreColorful.location("textures/block/purple_pinwheel.png"))
            .put(ModBlocks.BLUE_PINWHEEL.get(), MoreColorful.location("textures/block/blue_pinwheel.png"))
            .put(ModBlocks.BROWN_PINWHEEL.get(), MoreColorful.location("textures/block/brown_pinwheel.png"))
            .put(ModBlocks.GREEN_PINWHEEL.get(), MoreColorful.location("textures/block/green_pinwheel.png"))
            .put(ModBlocks.RED_PINWHEEL.get(), MoreColorful.location("textures/block/red_pinwheel.png"))
            .put(ModBlocks.BLACK_PINWHEEL.get(), MoreColorful.location("textures/block/black_pinwheel.png"))
            .put(ModBlocks.MULTICOLORED_PINWHEEL.get(), MoreColorful.location("textures/block/multicolored_pinwheel.png"))
            .build();

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
        VertexConsumer wheelConsumer = pBufferSource.getBuffer(RenderType.entityCutout(TEXTURES.get(blockstate.getBlock())));
        buildWheelModel(pose, wheelConsumer, pPackedLight, blockEntity.frames, blockEntity.lerpFrame(pPartialTick));
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
    }

    private static void buildWheelModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, int frames, int currentFrame) {
        int i = currentFrame % frames;
        float v0 = (float) i / frames;
        float v1 = (float) (i + 1) / frames;
        vertex(pose, consumer, 8, 18, -1, 0.0F, v0, 0, -1, packedLight);
        vertex(pose, consumer, -8, 18, -1, 1.0F, v0, 0, -1, packedLight);
        vertex(pose, consumer, -8, 2, -1, 1.0F, v1, 0, -1, packedLight);
        vertex(pose, consumer, 8, 2, -1, 0.0F, v1, 0, -1, packedLight);
        vertex(pose, consumer, 8, 18, -1, 0.0F, v0, 0, 1, packedLight);
        vertex(pose, consumer, 8, 2, -1, 0.0F, v1, 0, 1, packedLight);
        vertex(pose, consumer, -8, 2, -1, 1.0F, v1, 0, 1, packedLight);
        vertex(pose, consumer, -8, 18, -1, 1.0F, v0, 0, 1, packedLight);
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
