package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.DidgeridooItem;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.GuitarItem;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;
    @Shadow
    protected abstract void renderPlayerArm(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, float pEquippedProgress, float pSwingProgress, HumanoidArm pSide);

    @Unique
    private void moreColorful$renderHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, float pEquippedProgress, float pSwingProgress, boolean pRightHanded) {
        if (this.minecraft.player != null && !this.minecraft.player.isInvisible()) {
            pPoseStack.pushPose();
            HumanoidArm arm = pRightHanded ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            this.renderPlayerArm(pPoseStack, pBuffer, pPackedLight, pEquippedProgress, pSwingProgress, arm);
            pPoseStack.popPose();
        }
    }
    @Unique
    private void moreColorful$renderDidgeridooHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, float pEquippedProgress, float pSwingProgress, boolean pRightHanded) {
        if (this.minecraft.player != null && !this.minecraft.player.isInvisible()) {
            pPoseStack.pushPose();
            HumanoidArm arm = pRightHanded ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            float i = pRightHanded ? 1.0F : -1.0F;
            pPoseStack.translate(0.75 * i, 0, 0.1);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(45.0F * i));
            this.renderPlayerArm(pPoseStack, pBuffer, pPackedLight, pEquippedProgress, pSwingProgress, arm);
            pPoseStack.popPose();
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void renderArm(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.isInvisible()) return;
        boolean f = pPlayer.getMainArm() == HumanoidArm.RIGHT;
        if (pStack.getItem() instanceof GuitarItem) {
            if (pPlayer.getMainHandItem().getItem() instanceof GuitarItem) {
                this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, f);
                if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            } else if (pPlayer.getOffhandItem().getItem() instanceof GuitarItem) {
                this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
            }
        } else if (pStack.getItem() instanceof DidgeridooItem item && minecraft.screen instanceof PlayingScreen pScreen && pScreen.pType == item.getType()) {
            if (pPlayer.getMainHandItem().getItem() instanceof DidgeridooItem) {
                this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, f);
                if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                    this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            } else if (pPlayer.getOffhandItem().getItem() instanceof DidgeridooItem) {
                this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                if (!pPlayer.hasItemInSlot(EquipmentSlot.MAINHAND)) {
                    this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, f);
                }
            }
        } else if (pStack.getItem() == ModItems.PIPA.get()) {
            if (pPlayer.getMainHandItem().getItem() == ModItems.PIPA.get()) {
                if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            }
        } else if (minecraft.screen instanceof PlayingScreen pScreen && (pScreen.pType.getType() == InstrumentsType.Type.KEYBOARD || pScreen.pType == InstrumentsType.GUZHENG)) {
            if (pStack.isEmpty()) {
                if (pPlayer.getOffhandItem().isEmpty() && !(pPlayer.getMainHandItem().getItem() instanceof MapItem)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            }
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderPlayerArm(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IFFLnet/minecraft/world/entity/HumanoidArm;)V"), cancellable = true)
    private void stopRendering(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.getOffhandItem().getItem() instanceof DidgeridooItem item && minecraft.screen instanceof PlayingScreen pScreen && pScreen.pType == item.getType()) {
            pPoseStack.popPose();
            ci.cancel();
        }
    }
}
