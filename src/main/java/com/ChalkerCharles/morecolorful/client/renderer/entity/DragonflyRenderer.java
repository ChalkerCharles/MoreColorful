package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.client.model.DragonflyModel;
import com.ChalkerCharles.morecolorful.client.model.ModModelLayers;
import com.ChalkerCharles.morecolorful.common.entity.animal.Dragonfly;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Arrays;

public class DragonflyRenderer extends MobRenderer<Dragonfly, DragonflyModel> {
    private static final ResourceLocation[] LOCATIONS = Arrays.stream(Dragonfly.Variant.values())
            .map(Dragonfly.Variant::getTextureLocation)
            .toArray(ResourceLocation[]::new);

    public DragonflyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new DragonflyModel(pContext.bakeLayer(ModModelLayers.DRAGONFLY)), 0.4F);
    }

    @Override
    public ResourceLocation getTextureLocation(Dragonfly pEntity) {
        return LOCATIONS[pEntity.getVariantId()];
    }

    @Override
    protected void scale(Dragonfly pLivingEntity, PoseStack pPoseStack, float pPartialTickTime) {
        pPoseStack.scale(0.9F, 0.9F, 0.9F);
    }
}
