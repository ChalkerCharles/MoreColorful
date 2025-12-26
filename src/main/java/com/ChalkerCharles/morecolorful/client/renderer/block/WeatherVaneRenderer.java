package com.ChalkerCharles.morecolorful.client.renderer.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.entity.WeatherVaneBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class WeatherVaneRenderer implements BlockEntityRenderer<WeatherVaneBlockEntity> {
    public static final ResourceLocation WEATHER_VANE_TEXTURE = MoreColorful.location("textures/block/weather_vane.png");

    public WeatherVaneRenderer(BlockEntityRendererProvider.Context ignore) {}

    @Override
    public void render(WeatherVaneBlockEntity pBlockEntity, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, int pPackedOverlay) {
        VertexConsumer vertexconsumer = pBufferSource.getBuffer(RenderType.entityCutout(WEATHER_VANE_TEXTURE));
        pPoseStack.pushPose();
        pPoseStack.translate(0.5F, 0.0F, 0.5F);
        pPoseStack.scale(0.0625F, 0.0625F, -0.0625F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pBlockEntity.getRot(pPartialTick)));
        PoseStack.Pose pose = pPoseStack.last();
        buildWeatherVaneModel(pose, vertexconsumer, pPackedLight);
        pPoseStack.popPose();
    }

    private static void buildWeatherVaneModel(PoseStack.Pose pose, VertexConsumer consumer, int packedLight) {
        vertex(pose, consumer, 16, 8, 0.0F, 0.0F, packedLight);
        vertex(pose, consumer, 0, 8, 0.0F, 1.0F, packedLight);
        vertex(pose, consumer, 0, -8, 1.0F, 1.0F, packedLight);
        vertex(pose, consumer, 16, -8, 1.0F, 0.0F, packedLight);
        vertex(pose, consumer, 16, 8, 0.0F, 0.0F, packedLight);
        vertex(pose, consumer, 16, -8, 1.0F, 0.0F, packedLight);
        vertex(pose, consumer, 0, -8, 1.0F, 1.0F, packedLight);
        vertex(pose, consumer, 0, 8, 0.0F, 1.0F, packedLight);
    }

    private static void vertex(PoseStack.Pose pose, VertexConsumer consumer, int y, int z, float u, float v, int packedLight) {
        consumer.addVertex(pose, 0, y, z)
                .setColor(-1)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 1, 0, 0);
    }
}
