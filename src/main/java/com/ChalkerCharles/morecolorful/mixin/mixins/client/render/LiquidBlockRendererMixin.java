package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.shader.ModVertexFormat;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LiquidBlockRenderer.class)
public abstract class LiquidBlockRendererMixin {
    @WrapOperation(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V"))
    private void tesselate(LiquidBlockRenderer instance, VertexConsumer consumer, float pX, float pY, float pZ, float red, float green, float blue, float alpha, float pU, float pV, int pPackedLight, Operation<Void> original,
                           @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) FluidState fluidState) {
        if (ItemBlockRenderTypes.getRenderLayer(fluidState).format() == ModVertexFormat.WAVY_BLOCK.get()) {
            RenderUtils.fluidVertex(consumer, pX, pY, pZ, red, green, blue, alpha, pU, pV, pPackedLight, pos);
        } else {
            original.call(instance, consumer, pX, pY, pZ, red, green, blue, alpha, pU, pV, pPackedLight);
        }
    }
}
