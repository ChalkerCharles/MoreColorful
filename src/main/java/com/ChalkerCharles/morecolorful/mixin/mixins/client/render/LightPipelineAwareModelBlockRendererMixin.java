package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.shader.ModVertexFormat;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.lighting.LightPipelineAwareModelBlockRenderer;
import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@SuppressWarnings("UnstableApiUsage")
@Mixin(LightPipelineAwareModelBlockRenderer.class)
public abstract class LightPipelineAwareModelBlockRendererMixin {
    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/client/model/lighting/QuadLighter;process(Lcom/mojang/blaze3d/vertex/VertexConsumer;Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;I)V"))
    private static void render(QuadLighter instance, VertexConsumer consumer, PoseStack.Pose pose, BakedQuad quad, int overlay, Operation<Void> original,
                               @Local(argsOnly = true) BlockState state, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) RenderType renderType) {
        if (renderType.format() == ModVertexFormat.WAVY_BLOCK.get()) {
            RenderUtils.quadLighterProcess(instance, consumer, pose, quad, state, pos);
        } else {
            original.call(instance, consumer, pose, quad, overlay);
        }
    }
}
