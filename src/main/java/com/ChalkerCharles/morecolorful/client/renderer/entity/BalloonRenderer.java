package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.BalloonModel;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
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
    private static final ResourceLocation[] TEXTURES;
    private final BalloonModel model;

    public BalloonRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new BalloonModel(pContext.bakeLayer(ModModelLayers.BALLOON));
        this.shadowRadius = 0.3125F;
    }

    @Override
    public void render(Balloon pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        ResourceLocation location = this.getTextureLocation(pEntity);
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        pPoseStack.pushPose();
        pPoseStack.translate(0.0F, 1.5F, 0.0F);
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        VertexConsumer consumer = pBufferSource.getBuffer(this.model.renderType(location));
        this.model.renderToBuffer(pPoseStack, consumer, pPackedLight, OverlayTexture.NO_OVERLAY);
        pPoseStack.popPose();
        pPoseStack.pushPose();
        pPoseStack.scale(0.05625F, 0.05625F, 0.05625F);
        VertexConsumer consumer1 = pBufferSource.getBuffer(RenderType.entitySolid(location));
        buildOpeningModel(pPoseStack, consumer1, pPackedLight);
        pPoseStack.popPose();
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Balloon entity) {
        return TEXTURES[entity.getVariant().getIndex()];
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

    static {
        Balloon.Variant[] values = Balloon.Variant.values();
        int size = values.length;
        ResourceLocation[] arr = new ResourceLocation[size];
        for (int i = 0; i < size; i++) {
            Balloon.Variant variant = values[i];
            String path = "textures/entity/balloons/" + variant.getName() + ".png";
            arr[i] = MoreColorful.location(path);
        }
        TEXTURES = arr;
    }
}
