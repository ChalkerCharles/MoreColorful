package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.client.texture.KiteTextureManager;
import com.ChalkerCharles.morecolorful.common.entity.misc.Kite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class KiteRenderer extends EntityRenderer<Kite> {
    public KiteRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.75F;
        this.shadowStrength = 0.75F;
    }

    @Override
    public void render(Kite kite, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        VertexConsumer consumer = pBufferSource.getBuffer(RenderType.entityCutout(Atlases.KITE_SHEET));
        KiteTextureManager manager = KiteTextureManager.INSTANCE;
        pPoseStack.pushPose();
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pEntityYaw));
        float xRot = -kite.getViewXRot(pPartialTick);
        Vec3 leashOffset = kite.getLeashOffset(pPartialTick);
        pPoseStack.translate(0, leashOffset.y, -leashOffset.z);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(xRot));
        pPoseStack.translate(0, -leashOffset.y, leashOffset.z);
        pPoseStack.scale(0.0625F, 0.0625F, 0.0625F);
        PoseStack.Pose pose = pPoseStack.last();
        if (kite.isLeashed()) {
            renderRope(pose, consumer, pPackedLight, manager.ropeSprite(), leashOffset);
        }
        buildFrameModel(pose, consumer, pPackedLight, manager.frameSprite());
        List<DyeColor> colors = kite.getColor().colors();
        for (int i = 0; i < 4; i++) {
            TextureAtlasSprite sprite = manager.getSprite(colors.get(i));
            buildKiteModel(pose, consumer, pPackedLight, sprite, i);
        }
        DyeColor color = kite.getBowColor();
        TextureAtlasSprite bowSprite = color == null ? null : manager.getSprite(color);
        pPoseStack.translate(-1, 1, 16);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(-xRot));
        pPoseStack.translate(1, -1, -16);
        buildTailModel(pPoseStack, consumer, pPackedLight, manager.getSprite(colors.get(4)), bowSprite,
                kite.getWaveTicks(pPartialTick), kite.getWaveFactor(pPartialTick));
        pPoseStack.popPose();
        super.render(kite, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(Kite pEntity) {
        return Atlases.KITE_SHEET;
    }

    private static void buildFrameModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, TextureAtlasSprite sprite) {
        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();
        face(pose, consumer, -16, 0.75F, -16, 16, 0.75F, 16, u0, v0, u1, v1, packedLight);
    }

    private static void buildKiteModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, TextureAtlasSprite sprite, int index) {
        float u0 = sprite.getU0();
        float u1 = sprite.getU(0.5F);
        float v0 = sprite.getV0();
        float v1 = sprite.getV(0.25F);
        float v2 = sprite.getV1();
        switch (index) {
            case 0 -> face(pose, consumer, 0, 1, -16, 16, 1, -8, u1, v0, u0, v1, packedLight);
            case 1 -> face(pose, consumer, -16, 1, -16, 0, 1, -8, u0, v0, u1, v1, packedLight);
            case 2 -> face(pose, consumer, 0, 1, -8, 16, 1, 16, u1, v1, u0, v2, packedLight);
            case 3 -> face(pose, consumer, -16, 1, -8, 0, 1, 16, u0, v1, u1, v2, packedLight);
        }
    }

    private static void buildTailModel(PoseStack poseStack, VertexConsumer consumer, int packedLight, TextureAtlasSprite tail, TextureAtlasSprite bow,
                                       float ticks, float waveFactor) {
        if (bow == null) {
            float u0 = tail.getU(0.5F);
            float u1 = tail.getU(0.5625F);
            float v0 = tail.getV0();
            float v1 = tail.getV(0.5F);
            float d0, d = 0;
            for (int i = 0; i < 4; i++) {
                int z = (i + 1) << 4;
                poseStack.translate(-1, 1, z);
                d0 = d;
                d = 15 * waveFactor * Mth.sin(0.5F * ticks + i * 5);
                poseStack.mulPose(Axis.XP.rotationDegrees(d - d0));
                poseStack.translate(1, -1, -z);
                PoseStack.Pose pose = poseStack.last();
                face(pose, consumer, -1, 1, z, 1, 1, z + 16, u0, v0, u1, v1, packedLight);
            }
        } else {
            float u0 = tail.getU(0.75F);
            float u1 = tail.getU(0.8125F);
            float v0 = tail.getV0();
            float v1 = tail.getV(0.5F);
            float u2 = bow.getU(0.5F);
            float u3 = bow.getU1();
            float v2 = bow.getV(0.5F);
            float v3 = bow.getV1();
            float d0, d = 0;
            for (int i = 0; i < 4; i++) {
                int z = (i + 1) << 4;
                poseStack.translate(-1, 1, z);
                d0 = d;
                d = 15 * waveFactor * Mth.sin(0.5F * ticks + i * 5);
                poseStack.mulPose(Axis.XP.rotationDegrees(d - d0));
                poseStack.translate(1, -1, -z);
                PoseStack.Pose pose = poseStack.last();
                face(pose, consumer, -1, 1, z, 1, 1, z + 16, u0, v0, u1, v1, packedLight);
                face(pose, consumer, -8, 1, z, 8, 1, z + 16, u2, v2, u3, v3, packedLight);
            }
        }
    }

    private static void renderRope(PoseStack.Pose pose, VertexConsumer consumer, int packedLight, TextureAtlasSprite sprite, Vec3 offset) {
        float u0 = sprite.getU0();
        float u1 = sprite.getU1();
        float v0 = sprite.getV0();
        float v1 = sprite.getV1();
        float dy = (float) (offset.y * 16);
        float dz = (float) -(offset.z * 16);
        ropeQuad(pose, consumer, 0.75F, dz, dy, 9, u0, v0, u1, v1, packedLight);
        ropeQuad(pose, consumer, dy, -13, 0.75F, dz, u0, v0, u1, v1, packedLight);
    }

    private static void face(PoseStack.Pose pose, VertexConsumer consumer, float x0, float y0, float z0, float x1, float y1, float z1,
                             float u0, float v0, float u1, float v1, int packedLight) {
        vertex(pose, consumer, x0, y1, z0, u0, v0, packedLight, 1);
        vertex(pose, consumer, x0, y0, z1, u0, v1, packedLight, 1);
        vertex(pose, consumer, x1, y0, z1, u1, v1, packedLight, 1);
        vertex(pose, consumer, x1, y1, z0, u1, v0, packedLight, 1);
        vertex(pose, consumer, x0, y1, z0, u0, v0, packedLight, -1);
        vertex(pose, consumer, x1, y1, z0, u1, v0, packedLight, -1);
        vertex(pose, consumer, x1, y0, z1, u1, v1, packedLight, -1);
        vertex(pose, consumer, x0, y0, z1, u0, v1, packedLight, -1);
    }

    private static void ropeQuad(PoseStack.Pose pose, VertexConsumer consumer, float y0, float z0, float y1, float z1,
                                 float u0, float v0, float u1, float v1, int packedLight) {
        vertex(pose, consumer, -0.4F, y1, z0, u0, v0, packedLight, 1);
        vertex(pose, consumer, -0.4F, y0, z1, u0, v1, packedLight, 1);
        vertex(pose, consumer, 0.4F, y0, z1, u1, v1, packedLight, 1);
        vertex(pose, consumer, 0.4F, y1, z0, u1, v0, packedLight, 1);
        vertex(pose, consumer, -0.4F, y1, z0, u0, v0, packedLight, 1);
        vertex(pose, consumer, 0.4F, y1, z0, u1, v0, packedLight, 1);
        vertex(pose, consumer, 0.4F, y0, z1, u1, v1, packedLight, 1);
        vertex(pose, consumer, -0.4F, y0, z1, u0, v1, packedLight, 1);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, float x, float y, float z, float u, float v, int packedLight, int ny) {
        consumer.addVertex(pose, x, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0, ny, 0);
    }
}
