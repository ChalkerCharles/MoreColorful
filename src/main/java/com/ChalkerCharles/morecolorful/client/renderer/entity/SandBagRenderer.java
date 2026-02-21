package com.ChalkerCharles.morecolorful.client.renderer.entity;

import com.ChalkerCharles.morecolorful.common.entity.misc.SandbagEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FallingBlockRenderer;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SandBagRenderer extends FallingBlockRenderer {
    public SandBagRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.shadowRadius = 0.15F;
    }

    @Override
    public void render(FallingBlockEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight) {
        if (((SandbagEntity) pEntity).isFalling()) {
            super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBufferSource, pPackedLight);
        }
    }
}
