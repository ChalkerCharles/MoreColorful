package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.shader.ModVertexFormat;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.BitSet;
import java.util.List;

@Mixin(ModelBlockRenderer.class)
public abstract class ModelBlockRendererMixin {
    @WrapOperation(method = "tesselateWithAO(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;renderModelFaceAO(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Ljava/util/List;[FLjava/util/BitSet;Lnet/minecraft/client/renderer/block/ModelBlockRenderer$AmbientOcclusionFace;I)V"))
    private void tesselateWithAO(ModelBlockRenderer instance, BlockAndTintGetter pLevel, BlockState pState, BlockPos pPos, PoseStack pPoseStack, VertexConsumer pConsumer, List<BakedQuad> pQuads, float[] pShape, BitSet pShapeFlags, ModelBlockRenderer.AmbientOcclusionFace pAoFace, int pPackedOverlay, Operation<Void> original,
                                 @Local(argsOnly = true) RenderType renderType) {
        if (renderType.format() == ModVertexFormat.WAVY_BLOCK.get()) {
            RenderUtils.renderModelFaceAO(instance, pLevel, pState, pPos, pPoseStack, pConsumer, pQuads, pShape, pShapeFlags, pAoFace);
        } else {
            original.call(instance, pLevel, pState, pPos, pPoseStack, pConsumer, pQuads, pShape, pShapeFlags, pAoFace, pPackedOverlay);
        }
    }

    @WrapOperation(method = "tesselateWithoutAO(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JILnet/neoforged/neoforge/client/model/data/ModelData;Lnet/minecraft/client/renderer/RenderType;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;renderModelFaceFlat(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;IIZLcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;Ljava/util/List;Ljava/util/BitSet;)V"))
    private void tesselateWithoutAO(ModelBlockRenderer instance, BlockAndTintGetter pLevel, BlockState pState, BlockPos pPos, int pPackedLight, int pPackedOverlay, boolean pRepackLight, PoseStack pPoseStack, VertexConsumer pConsumer, List<BakedQuad> pQuads, BitSet pShapeFlags, Operation<Void> original,
                                 @Local(argsOnly = true) RenderType renderType) {
        if (renderType.format() == ModVertexFormat.WAVY_BLOCK.get()) {
            RenderUtils.renderModelFaceFlat(instance, pLevel, pState, pPos, pPackedLight, pRepackLight, pPoseStack, pConsumer, pQuads, pShapeFlags);
        } else {
            original.call(instance, pLevel, pState, pPos, pPackedLight, pPackedOverlay, pRepackLight, pPoseStack, pConsumer, pQuads, pShapeFlags);
        }
    }
}
