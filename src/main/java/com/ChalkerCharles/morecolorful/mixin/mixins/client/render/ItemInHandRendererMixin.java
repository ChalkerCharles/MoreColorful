package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical.DidgeridooItem;
import com.ChalkerCharles.morecolorful.common.item.musical.GuitarItem;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
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
    private void moreColorful$renderHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, float equippedProgress, float swingProgress, boolean rightHanded) {
        if (this.minecraft.player != null && !this.minecraft.player.isInvisible()) {
            poseStack.pushPose();
            HumanoidArm arm = rightHanded ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            this.renderPlayerArm(poseStack, buffer, packedLight, equippedProgress, swingProgress, arm);
            poseStack.popPose();
        }
    }
    @Unique
    private void moreColorful$renderDidgeridooHand(PoseStack poseStack, MultiBufferSource buffer, int packedLight, float equippedProgress, float swingProgress, boolean rightHanded) {
        if (this.minecraft.player != null && !this.minecraft.player.isInvisible()) {
            poseStack.pushPose();
            HumanoidArm arm = rightHanded ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
            float i = rightHanded ? 1.0F : -1.0F;
            poseStack.translate(0.75 * i, 0, 0.1);
            poseStack.mulPose(Axis.YP.rotationDegrees(45.0F * i));
            this.renderPlayerArm(poseStack, buffer, packedLight, equippedProgress, swingProgress, arm);
            poseStack.popPose();
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void renderArm(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.isInvisible()) return;
        boolean f = pPlayer.getMainArm() == HumanoidArm.RIGHT;
        Item item = pStack.getItem();
        if (item instanceof GuitarItem) {
            if (pPlayer.getMainHandItem().getItem() instanceof GuitarItem) {
                this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, f);
                if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            } else if (pPlayer.getOffhandItem().getItem() instanceof GuitarItem) {
                this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
            }
        } else if (item instanceof DidgeridooItem i && minecraft.screen instanceof PlayingScreen pScreen && pScreen.type == i.getType()) {
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
        } else if (item == ModItems.PIPA.get()) {
            if (pPlayer.getMainHandItem().getItem() == ModItems.PIPA.get()) {
                if (!pPlayer.hasItemInSlot(EquipmentSlot.OFFHAND)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            }
        } else if (minecraft.screen instanceof PlayingScreen screen && (screen.type.isKeyBoard() || screen.type == InstrumentsType.GUZHENG)) {
            if (pStack.isEmpty()) {
                if (pPlayer.getOffhandItem().isEmpty() && !(pPlayer.getMainHandItem().getItem() instanceof MapItem)) {
                    this.moreColorful$renderHand(pPoseStack, pBuffer, pCombinedLight, pEquippedProgress, pSwingProgress, !f);
                }
            }
        }
    }

    @Inject(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;renderPlayerArm(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IFFLnet/minecraft/world/entity/HumanoidArm;)V"), cancellable = true)
    private void stopRendering(AbstractClientPlayer pPlayer, float pPartialTicks, float pPitch, InteractionHand pHand, float pSwingProgress, ItemStack pStack, float pEquippedProgress, PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, CallbackInfo ci) {
        if (pPlayer.getOffhandItem().getItem() instanceof DidgeridooItem item && minecraft.screen instanceof PlayingScreen screen && screen.type == item.getType()) {
            pPoseStack.popPose();
            ci.cancel();
        }
    }
}
