package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.misc.PaperPlane;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PaperPlaneRenderer extends EntityRenderer<PaperPlane> {
    public static final ResourceLocation PAPER_PLANE_TEXTURE = MoreColorful.location("textures/entity/paper_plane.png");

    public PaperPlaneRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.25F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(PaperPlane pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.yRotO, pEntity.getYRot()) - 90.0F));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(pPartialTick, pEntity.xRotO, pEntity.getXRot())));
        pPoseStack.scale(0.05625F, 0.05625F, 0.05625F);
        VertexConsumer consumer = pBufferSource.getBuffer(RenderType.entityCutout(PAPER_PLANE_TEXTURE));
        PoseStack.Pose pose = pPoseStack.last();
        buildPaperPlaneModel(pose, consumer, pPackedLight);
        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(PaperPlane pEntity) {
        return PAPER_PLANE_TEXTURE;
    }

    private static void buildPaperPlaneModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, -5, 2, 5, 0.0F, 0.0F, -1, packedLight);
        vertex(pose, consumer, -5, 2, -5, 0.625F, 0.0F, -1, packedLight);
        vertex(pose, consumer, 5, 2, -5, 0.625F, 0.625F, -1, packedLight);
        vertex(pose, consumer, 5, 2, 5, 0.0F, 0.625F, -1, packedLight);
        vertex(pose, consumer, -5, 2, 5, 0.0F, 0.0F, 1, packedLight);
        vertex(pose, consumer, 5, 2, 5, 0.0F, 0.625F, 1, packedLight);
        vertex(pose, consumer, 5, 2, -5, 0.625F, 0.625F, 1, packedLight);
        vertex(pose, consumer, -5, 2, -5, 0.625F, 0.0F, 1, packedLight);

        vertex(pose, consumer, -5, 2, 0, 0.0F, 0.875F, 1, packedLight);
        vertex(pose, consumer, -5, 0, 0, 0.0F, 1.0F, 1, packedLight);
        vertex(pose, consumer, 3, 0, 0, 0.5F, 1.0F, 1, packedLight);
        vertex(pose, consumer, 3, 2, 0, 0.5F, 0.875F, 1, packedLight);
        vertex(pose, consumer, 3, 2, 0, 0.5F, 0.875F, 1, packedLight);
        vertex(pose, consumer, 3, 0, 0, 0.5F, 1.0F, 1, packedLight);
        vertex(pose, consumer, -5, 0, 0, 1.0F, 1.0F, 1, packedLight);
        vertex(pose, consumer, -5, 2, 0, 1.0F, 0.875F, 1, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int x, int y, int z, float u, float v, int normalY, int packedLight) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, normalY, 0);
    }
}
