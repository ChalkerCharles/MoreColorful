package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.client.WavyBlockUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CeilingHangingSignBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Quaternionf;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HangingSignRenderer.class)
public abstract class HangingSignRendererMixin extends SignRenderer {
    @Shadow
    abstract Material getSignMaterial(WoodType pWoodType);

    private HangingSignRendererMixin(BlockEntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/HangingSignRenderer;renderSignWithText(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/block/SignBlock;Lnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V"))
    private void render(HangingSignRenderer instance, SignBlockEntity signBlockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay, BlockState state, SignBlock signBlock, WoodType woodType, Model model, Operation<Void> original) {
        if (RenderUtils.isClientWindOn) {
            this.moreColorful$renderWavySign(signBlockEntity, poseStack, multiBufferSource, packedLight, packedOverlay, state, signBlock, woodType, model);
        } else {
            original.call(instance, signBlockEntity, poseStack, multiBufferSource, packedLight, packedOverlay, state, signBlock, woodType, model);
        }
    }

    @Unique
    private void moreColorful$renderWavySign(
            SignBlockEntity pSignEntity,
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight,
            int pPackedOverlay,
            BlockState pState,
            SignBlock pSignBlock,
            WoodType pWoodType,
            Model pModel) {
        pPoseStack.pushPose();
        boolean onWall = WavyBlockUtils.onWall(pState);
        boolean attached = !onWall && pState.hasProperty(CeilingHangingSignBlock.ATTACHED) && pState.getValue(CeilingHangingSignBlock.ATTACHED);
        pPoseStack.translate(0.5, 0.875, 0.5);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(-pSignBlock.getYRotationDegrees(pState)));
        HangingSignRenderer.HangingSignModel model = (HangingSignRenderer.HangingSignModel) pModel;
        float f = this.getSignModelRenderScale();
        pPoseStack.scale(f, -f, -f);
        Material material = this.getSignMaterial(pWoodType);
        VertexConsumer vertexconsumer = material.buffer(pBuffer, pModel::renderType);
        boolean visible = model.plank.visible;
        model.plank.visible = false;
        Level level = pSignEntity.getLevel();
        pPoseStack.pushPose();
        Quaternionf pitch;
        BlockPos pos = pSignEntity.getBlockPos();
        Vector4f result = WavyBlockUtils.getSignAngle(level, pState, pos);
        float rotX = result.w;
        if (!onWall && attached) {
            float rotY = level != null && RenderUtils.isWindyAt(level, pos)
                    ? WavyBlockUtils.getSignAngleVertical(level, pState, pos)
                    : 0.0F;
            pitch = Axis.XP.rotation(rotX).mul(Axis.YP.rotation(rotY));
        } else {
            pitch = Axis.XP.rotation(rotX);
        }
        if (!onWall) {
            pPoseStack.translate(0, -0.125, 0);
        }
        pPoseStack.translate(-result.x, -result.y, -result.z);
        pPoseStack.mulPose(pitch);
        if (!onWall) {
            pPoseStack.translate(0, 0.125, 0);
        }
        pPoseStack.pushPose();
        pPoseStack.translate(0, 0.25, 0);
        model.root.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        model.plank.visible = visible;
        pPoseStack.popPose();
        pPoseStack.scale(f, -f, -f);
        pPoseStack.translate(0, -0.25, 0);
        this.renderSignText(
                pSignEntity.getBlockPos(),
                pSignEntity.getFrontText(),
                pPoseStack,
                pBuffer,
                pPackedLight,
                pSignEntity.getTextLineHeight(),
                pSignEntity.getMaxTextLineWidth(),
                true
        );
        this.renderSignText(
                pSignEntity.getBlockPos(),
                pSignEntity.getBackText(),
                pPoseStack,
                pBuffer,
                pPackedLight,
                pSignEntity.getTextLineHeight(),
                pSignEntity.getMaxTextLineWidth(),
                false
        );
        pPoseStack.popPose();
        pPoseStack.translate(0, 0.25, 0);
        if (visible) {
            model.plank.render(pPoseStack, vertexconsumer, pPackedLight, pPackedOverlay);
        }
        pPoseStack.popPose();
    }
}
