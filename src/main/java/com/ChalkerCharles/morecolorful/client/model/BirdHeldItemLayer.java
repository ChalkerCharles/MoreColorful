package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractBird;
import com.ChalkerCharles.morecolorful.common.entity.animal.Pigeon;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BirdHeldItemLayer extends RenderLayer<AbstractBird, BirdModel> {
    private final ItemInHandRenderer itemInHandRenderer;

    public BirdHeldItemLayer(RenderLayerParent<AbstractBird, BirdModel> pRenderer, ItemInHandRenderer itemInHandRenderer) {
        super(pRenderer);
        this.itemInHandRenderer = itemInHandRenderer;
    }

    @Override
    public void render(PoseStack pPoseStack, MultiBufferSource pBufferSource, int pPackedLight, AbstractBird pLivingEntity, float pLimbSwing, float pLimbSwingAmount, float pPartialTick, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {
        if (pLivingEntity instanceof Pigeon pigeon && pigeon.isInvisibleWhenPosting()) return;
        pPoseStack.pushPose();
        pPoseStack.translate(this.getParentModel().head.x / 16.0F, this.getParentModel().head.y / 16.0F, this.getParentModel().head.z / 16.0F);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(pNetHeadYaw));
        pPoseStack.mulPose(Axis.XP.rotationDegrees(pHeadPitch));
        pPoseStack.translate(0, 0.04F, -0.32F);
        pPoseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        ItemStack itemstack = pLivingEntity.getItemBySlot(EquipmentSlot.MAINHAND);
        this.itemInHandRenderer.renderItem(pLivingEntity, itemstack, ItemDisplayContext.GROUND, false, pPoseStack, pBufferSource, pPackedLight);
        pPoseStack.popPose();
    }
}
