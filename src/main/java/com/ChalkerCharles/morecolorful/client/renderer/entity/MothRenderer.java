package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.client.model.MothModel;
import com.ChalkerCharles.morecolorful.client.texture.Atlases;
import com.ChalkerCharles.morecolorful.client.texture.MothTextureManager;
import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILivingEntityRendererExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MothRenderer extends MobRenderer<AbstractMoth, MothModel> implements ILivingEntityRendererExtension<AbstractMoth> {
    private MothRenderer(EntityRendererProvider.Context pContext, ModelLayerLocation location) {
        super(pContext, new MothModel(pContext.bakeLayer(location)), 0.4F);
    }

    public static MothRenderer createButterfly(EntityRendererProvider.Context context) {
        return new MothRenderer(context, ModModelLayers.BUTTERFLY);
    }

    public static MothRenderer createMoth(EntityRendererProvider.Context context) {
        return new MothRenderer(context, ModModelLayers.MOTH);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractMoth pEntity) {
        return Atlases.MOTH_SHEET;
    }

    @Override
    public VertexConsumer morecolorful$wrapVertexConsumer(AbstractMoth moth, MultiBufferSource buffer, RenderType renderType) {
        AbstractMoth.Variant variant = moth.getVariant();
        return MothTextureManager.getSprite(variant, false).wrap(buffer.getBuffer(renderType));
    }

    @Override
    protected void scale(AbstractMoth pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.9F, 0.9F, 0.9F);
    }

    @Override
    protected int getBlockLightLevel(AbstractMoth pEntity, BlockPos pPos) {
        if (pEntity.getVariant().canGlow()) {
            return 15;
        }
        return super.getBlockLightLevel(pEntity, pPos);
    }
}
