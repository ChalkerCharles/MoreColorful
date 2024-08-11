package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
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
    private void moreColorful$renderHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, float pEquippedProgress, float pSwingProgress, ItemStack pStack, boolean pRightHanded) {
        if (this.minecraft.player != null && !this.minecraft.player.isInvisible()) {
            pPoseStack.pushPose();
            HumanoidArm arm = pRightHanded ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            this.renderPlayerArm(pPoseStack, pBuffer, pPackedLight, pEquippedProgress, pSwingProgress, arm);
            pPoseStack.popPose();
        }
    }
    @Unique
    private void moreColorful$renderDidgeridooHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, float pEquippedProgress, float pSwingProgress, ItemStack pStack, boolean pRightHanded) {
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

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void moreColorful$renderArm(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (!pPlayer.isInvisible()) {
            boolean f = pPlayer.getMainArm() == HumanoidArm.RIGHT;
            if (pStack.getItem() instanceof GuitarItem) {
                if (pPlayer.getMainHandItem().getItem() instanceof GuitarItem) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, f);
                    if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                        this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, !f);
                    }
                } else if (pPlayer.getOffhandItem().getItem() instanceof GuitarItem) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, !f);
                }
            } else if (pStack.getItem() instanceof DidgeridooItem && Minecraft.getInstance().screen instanceof PlayingScreen && PlayingScreen.pType == InstrumentsType.DIDGERIDOO) {
                if (pPlayer.getMainHandItem().getItem() instanceof DidgeridooItem) {
                    this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, f);
                    if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                        this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, !f);
                    }
                } else if (pPlayer.getOffhandItem().getItem() instanceof DidgeridooItem) {
                    this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, !f);
                    if (!pPlayer.hasItemInSlot(EquipmentSlot.MAINHAND)) {
                        this.moreColorful$renderDidgeridooHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, f);
                    }
                }
            } else if (Minecraft.getInstance().screen instanceof PlayingScreen && (PlayingScreen.pType == InstrumentsType.PIANO || (PlayingScreen.pType.ordinal() >= 9 && PlayingScreen.pType.ordinal() <= 10))) {
                if (pStack.isEmpty()) {
                    if (pPlayer.getOffhandItem().isEmpty() && !(pPlayer.getMainHandItem().getItem() instanceof MapItem)) {
                        this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, pStack, !f);
                    }
                }
            }
        }
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderPlayerArm(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IFFLnet/minecraft/world/entity/HumanoidArm;)V"), cancellable = true)
    private void moreColorful$stopRendering(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.getOffhandItem().getItem() instanceof DidgeridooItem && Minecraft.getInstance().screen instanceof PlayingScreen && PlayingScreen.pType == InstrumentsType.DIDGERIDOO) {
            ci.cancel();
        }
    }
}
