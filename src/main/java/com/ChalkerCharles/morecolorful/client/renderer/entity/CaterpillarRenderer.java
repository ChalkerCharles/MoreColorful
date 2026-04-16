package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.client.model.CaterpillarModel;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.client.texture.MothTextureManager;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.entity.animal.Caterpillar;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILivingEntityRendererExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CaterpillarRenderer extends MobRenderer<Caterpillar, CaterpillarModel> implements ILivingEntityRendererExtension<Caterpillar> {
    public CaterpillarRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new CaterpillarModel(pContext.bakeLayer(ModModelLayers.CATERPILLAR)), 0.2F);
    }

    @Override
    public ResourceLocation getTextureLocation(Caterpillar pEntity) {
        return Atlases.MOTH_SHEET;
    }

    @Override
    public VertexConsumer morecolorful$wrapVertexConsumer(Caterpillar entity, MultiBufferSource buffer, RenderType renderType) {
        AbstractMoth.Variant variant = entity.getVariant();
        return MothTextureManager.getSprite(variant, true).wrap(buffer.getBuffer(renderType));
    }

    @Override
    protected void scale(Caterpillar pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.9F, 0.9F, 0.9F);
    }
}
