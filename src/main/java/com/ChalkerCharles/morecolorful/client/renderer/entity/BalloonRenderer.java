package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.client.model.BalloonModel;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.client.texture.BalloonTextureManager;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
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
public class BalloonRenderer extends EntityRenderer<Balloon> {
    private final BalloonModel model;
    private final BalloonModel heart;
    private final BalloonModel star;
    private final BalloonModel rabbit;

    public BalloonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BalloonModel(pContext.bakeLayer(ModModelLayers.BALLOON));
        this.heart = new BalloonModel(pContext.bakeLayer(ModModelLayers.BALLOON_HEART));
        this.star = new BalloonModel(pContext.bakeLayer(ModModelLayers.BALLOON_STAR));
        this.rabbit = new BalloonModel(pContext.bakeLayer(ModModelLayers.BALLOON_RABBIT));
        this.shadowRadius = 0.3125F;
    }

    @Override
    public void render(Balloon pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        Balloon.Variant variant = pEntity.getVariant();
        BalloonTextureManager manager = BalloonTextureManager.INSTANCE;
        BalloonModel model = this.getModel(variant);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        pPoseStack.pushPose();
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer consumer = manager.buffer(variant, pBufferSource, model::renderType);
        model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.scale(0.0625F, 0.0625F, 0.0625F);
        VertexConsumer consumer1 = manager.buffer(variant, pBufferSource, RenderType::entitySolid);
        buildOpeningModel(pPoseStack, consumer1, pPackedLight);
        pPoseStack.popPose();
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    private BalloonModel getModel(Balloon.Variant variant) {
        if (variant == Balloon.SpecialVariant.HEART) {
            return this.heart;
        } else if (variant == Balloon.SpecialVariant.STAR) {
            return this.star;
        } else if (variant == Balloon.SpecialVariant.RABBIT) {
            return this.rabbit;
        }
        return this.model;
    }

    @Override
    public ResourceLocation getTextureLocation(Balloon entity) {
        return Atlases.BALLOON_SHEET;
    }

    private static void buildOpeningModel(PoseStack poseStack, VertexConsumer consumer, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(45));
        PoseStack.Pose pose0 = poseStack.last();
        vertex(pose0, consumer, -1.5F, -1, 0.046875F, 0.03125F, -1, packedLight);
        vertex(pose0, consumer, -1.5F, 0, 0.046875F, 0.0F, -1, packedLight);
        vertex(pose0, consumer, 1.5F, 0, 0.0F, 0.0F, -1, packedLight);
        vertex(pose0, consumer, 1.5F, -1, 0.0F, 0.03125F, -1, packedLight);

        vertex(pose0, consumer, -1.5F, -1, 0.09375F, 0.03125F, 1, packedLight);
        vertex(pose0, consumer, 1.5F, -1, 0.046875F, 0.03125F, 1, packedLight);
        vertex(pose0, consumer, 1.5F, 0, 0.046875F, 0.0F, 1, packedLight);
        vertex(pose0, consumer, -1.5F, 0, 0.09375F, 0.0F, 1, packedLight);
        poseStack.popPose();

        poseStack.mulPose(Axis.YP.rotationDegrees(-45));
        PoseStack.Pose pose1 = poseStack.last();
        vertex(pose1, consumer, -1.5F, -1, 0.046875F, 0.03125F, -1, packedLight);
        vertex(pose1, consumer, -1.5F, 0, 0.046875F, 0.0F, -1, packedLight);
        vertex(pose1, consumer, 1.5F, 0, 0.0F, 0.0F, -1, packedLight);
        vertex(pose1, consumer, 1.5F, -1, 0.0F, 0.03125F, -1, packedLight);

        vertex(pose1, consumer, -1.5F, -1, 0.09375F, 0.03125F, -1, packedLight);
        vertex(pose1, consumer, 1.5F, -1, 0.046875F, 0.03125F, -1, packedLight);
        vertex(pose1, consumer, 1.5F, 0, 0.046875F, 0.0F, -1, packedLight);
        vertex(pose1, consumer, -1.5F, 0, 0.09375F, 0.0F, -1, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float u, float v, float normalZ, int packedLight) {
        consumer.addVertex(pose, x, y, 0)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, 0, normalZ);
    }
}
