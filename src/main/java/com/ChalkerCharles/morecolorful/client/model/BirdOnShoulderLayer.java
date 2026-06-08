package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.client.renderer.entity.BirdRenderer;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractBird;
import com.ChalkerCharles.morecolorful.common.entity.animal.Bird;
import com.ChalkerCharles.morecolorful.common.entity.animal.Pigeon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BirdOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
    private final BirdModel small;
    private final BirdModel big;

    public BirdOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> pRenderer, EntityModelSet modelSet) {
        super(pRenderer);
        this.small = new BirdModel(modelSet.bakeLayer(ModModelLayers.SMALL_BIRD));
        this.big = new BirdModel(modelSet.bakeLayer(ModModelLayers.BIG_BIRD));
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, T pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        this.render(pPoseStack, pBufferSource, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, true);
        this.render(pPoseStack, pBufferSource, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, false);
    }

    private void render(
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight,
            T pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pNetHeadYaw,
            float pHeadPitch,
            boolean pLeftShoulder) {
        CompoundTag compoundtag = pLeftShoulder ? pLivingEntity.getShoulderEntityLeft() : pLivingEntity.getShoulderEntityRight();
        EntityType.byString(compoundtag.getString("id"))
                .filter(type -> type == ModEntities.BIRD.get() || type == ModEntities.PIGEON.get())
                .ifPresent(type -> {
                    pPoseStack.pushPose();
                    int i = compoundtag.getInt("Variant");
                    AbstractBird.Variant variant = type == ModEntities.BIRD.get()
                            ? Bird.Variant.byId(i) : Pigeon.Variant.byId(i);
                    if (variant == Bird.Variant.SEAGULL) {
                        pPoseStack.scale(1.2F, 1.2F, 1.2F);
                    }
                    pPoseStack.translate(pLeftShoulder ? 0.4F : -0.4F, pLivingEntity.isCrouching() ? -1.3F : -1.5F, 0.0F);
                    BirdModel model = variant.isBig() ? this.big : this.small;
                    VertexConsumer vertexconsumer = pBuffer.getBuffer(model.renderType(BirdRenderer.getTexture(variant)));
                    model.renderOnShoulder(
                            pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, variant.isBig()
                    );
                    pPoseStack.popPose();
                });
    }
}
