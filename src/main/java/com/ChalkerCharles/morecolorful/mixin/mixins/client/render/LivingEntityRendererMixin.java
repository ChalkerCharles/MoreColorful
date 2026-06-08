package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILivingEntityRendererExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> implements ILivingEntityRendererExtension<T> {
    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer render(MultiBufferSource instance, RenderType renderType, Operation<VertexConsumer> original, @Local(argsOnly = true)T entity) {
        VertexConsumer consumer = this.morecolorful$wrapVertexConsumer(entity, instance, renderType);
        return consumer == null ? original.call(instance, renderType) : consumer;
    }

    @WrapOperation(method = "render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;renderToBuffer(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;III)V"))
    private void render(EntityModel<T> instance, PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color, Operation<Void> original,
                        @Local(argsOnly = true)T entity, @Local(ordinal = 1, argsOnly = true) float partialTicks) {
        if (this.morecolorful$hasDynamicTransparency(entity)) {
            int opacity = this.morecolorful$getOpacity(entity, partialTicks);
            int col = 0x00ffffff | (opacity << 24);
            original.call(instance, poseStack, consumer, packedLight, packedOverlay, col);
        } else {
            original.call(instance, poseStack, consumer, packedLight, packedOverlay, color);
        }
    }
}
