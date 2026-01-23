package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.PaperBoat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PaperBoatRenderer extends EntityRenderer<PaperBoat> {
    public static final ResourceLocation PAPER_BOAT_TEXTURE = MoreColorful.location("textures/entity/paper_boat.png");

    public PaperBoatRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.25F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(PaperBoat pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pEntityYaw - 90.0F));
        pPoseStack.translate(0, 0.01F + pEntity.getFloatOffset() + pEntity.lerpWobble(pPartialTick), 0);
        pPoseStack.scale(0.05625F, 0.05625F, 0.05625F);
        VertexConsumer consumer = pBufferSource.getBuffer(RenderType.entityCutout(PAPER_BOAT_TEXTURE));
        PoseStack.Pose pose = pPoseStack.last();
        buildPaperBoatModel(pose, pPoseStack, consumer, pPackedLight);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PaperBoat pEntity) {
        return PAPER_BOAT_TEXTURE;
    }

    private static void buildPaperBoatModel(PoseStack.Pose pose, PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, 4.5F, 0, 2.5F, 0.0F, 0.0F, 0, -1, 0, packedLight);
        vertex(pose, consumer, -4.5F, 0, 2.5F, 0.5625F, 0.0F, 0, -1, 0, packedLight);
        vertex(pose, consumer, -4.5F, 0, -2.5F, 0.5625F, 0.3125F, 0, -1, 0, packedLight);
        vertex(pose, consumer, 4.5F, 0, -2.5F, 0.0F, 0.3125F, 0, -1, 0, packedLight);
        vertex(pose, consumer, 4.5F, 0, 2.5F, 0.0F, 0.0F, 0, 1, 0, packedLight);
        vertex(pose, consumer, 4.5F, 0, -2.5F, 0.0F, 0.3125F, 0, 1, 0, packedLight);
        vertex(pose, consumer, -4.5F, 0, -2.5F, 0.5625F, 0.3125F, 0, 1, 0, packedLight);
        vertex(pose, consumer, -4.5F, 0, 2.5F, 0.5625F, 0.0F, 0, 1, 0, packedLight);

        vertex(pose, consumer, 4.5F, 6, 0, 0.0F, 0.5F, 0, 0, 1, packedLight);
        vertex(pose, consumer, -4.5F, 6, 0, 0.5625F, 0.5F, 0, 0, 1, packedLight);
        vertex(pose, consumer, -4.5F, 0, 0, 0.5625F, 0.875F, 0, 0, 1, packedLight);
        vertex(pose, consumer, 4.5F, 0, 0, 0.0F, 0.875F, 0, 0, 1, packedLight);
        vertex(pose, consumer, 4.5F, 6, 0, 0.0F, 0.5F, 0, 0, -1, packedLight);
        vertex(pose, consumer, 4.5F, 0, 0, 0.0F, 0.875F, 0, 0, -1, packedLight);
        vertex(pose, consumer, -4.5F, 0, 0, 0.5625F, 0.875F, 0, 0, -1, packedLight);
        vertex(pose, consumer, -4.5F, 6, 0, 0.5625F, 0.5F, 0, 0, -1, packedLight);

        poseStack.pushPose();
        poseStack.translate(0, 0, 2.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(22.5F));
        PoseStack.Pose pose1 = poseStack.last();
        vertex(pose1, consumer, 4.5F, 3, 0, 0.5625F, 0.3125F, 0, -0.0156231F, 0.9998778F, packedLight);
        vertex(pose1, consumer, -4.5F, 3, 0, 0.0F, 0.3125F, 0, -0.0156231F, 0.9998778F, packedLight);
        vertex(pose1, consumer, -4.5F, 0, 0, 0.0F, 0.5F, 0, -0.0156231F, 0.9998778F, packedLight);
        vertex(pose1, consumer, 4.5F, 0, 0, 0.5625F, 0.5F, 0, -0.0156231F, 0.9998778F, packedLight);
        vertex(pose1, consumer, 4.5F, 3, 0, 0.0F, 0.3125F, 0, 0.0156231F, -0.9998778F, packedLight);
        vertex(pose1, consumer, 4.5F, 0, 0, 0.0F, 0.5F, 0, 0.0156231F, -0.9998778F, packedLight);
        vertex(pose1, consumer, -4.5F, 0, 0, 0.5625F, 0.5F, 0, 0.0156231F, -0.9998778F, packedLight);
        vertex(pose1, consumer, -4.5F, 3, 0, 0.5625F, 0.3125F, 0, 0.0156231F, -0.9998778F, packedLight);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(0, 0, -2.5F);
        poseStack.mulPose(Axis.XP.rotationDegrees(-22.5F));
        PoseStack.Pose pose2 = poseStack.last();
        vertex(pose2, consumer, 4.5F, 3, 0, 0.5625F, 0.3125F, 0, 0.0156231F, 0.9998778F, packedLight);
        vertex(pose2, consumer, -4.5F, 3, 0, 0.0F, 0.3125F, 0, 0.0156231F, 0.9998778F, packedLight);
        vertex(pose2, consumer, -4.5F, 0, 0, 0.0F, 0.5F, 0, 0.0156231F, 0.9998778F, packedLight);
        vertex(pose2, consumer, 4.5F, 0, 0, 0.5625F, 0.5F, 0, 0.0156231F, 0.9998778F, packedLight);
        vertex(pose2, consumer, 4.5F, 3, 0, 0.0F, 0.3125F, 0, -0.0156231F, -0.9998778F, packedLight);
        vertex(pose2, consumer, 4.5F, 0, 0, 0.0F, 0.5F, 0, -0.0156231F, -0.9998778F, packedLight);
        vertex(pose2, consumer, -4.5F, 0, 0, 0.5625F, 0.5F, 0, -0.0156231F, -0.9998778F, packedLight);
        vertex(pose2, consumer, -4.5F, 3, 0, 0.5625F, 0.3125F, 0, -0.0156231F, -0.9998778F, packedLight);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.translate(4.5F, 0, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(-22.5F));
        PoseStack.Pose pose3 = poseStack.last();
        vertex(pose3, consumer, 0, 3, -2.5F, 0.875F, 0.0F, 0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 3, 2.5F, 0.5625F, 0.0F, 0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 0, 2.5F, 0.5625F, 0.1875F, 0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 0, -2.5F, 0.875F, 0.1875F, 0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 3, -2.5F, 0.5625F, 0.0F, -0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 0, -2.5F, 0.5625F, 0.1875F, -0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 0, 2.5F, 0.875F, 0.1875F, -0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose3, consumer, 0, 3, 2.5F, 0.875F, 0.0F, -0.9998778F, 0.0156231F, 0, packedLight);
        poseStack.popPose();

        poseStack.translate(-4.5F, 0, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(22.5F));
        vertex(pose, consumer, 0, 3, -2.5F, 0.875F, 0.0F, 0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 3, 2.5F, 0.5625F, 0.0F, 0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 0, 2.5F, 0.5625F, 0.1875F, 0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 0, -2.5F, 0.875F, 0.1875F, 0.9998778F, 0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 3, -2.5F, 0.5625F, 0.0F, -0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 0, -2.5F, 0.5625F, 0.1875F, -0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 0, 2.5F, 0.875F, 0.1875F, -0.9998778F, -0.0156231F, 0, packedLight);
        vertex(pose, consumer, 0, 3, 2.5F, 0.875F, 0.0F, -0.9998778F, -0.0156231F, 0, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v,
                               float normalX, float normalY, float normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, normalX, normalY, normalZ);
    }
}
