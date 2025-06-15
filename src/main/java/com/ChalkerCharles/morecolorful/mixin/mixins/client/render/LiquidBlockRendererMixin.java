package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.shader.ModVertexFormat;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.ChalkerCharles.morecolorful.util.ThreadUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mixin(LiquidBlockRenderer.class)
public abstract class LiquidBlockRendererMixin {
    @Inject(method = "tesselate", at = @At("HEAD"))
    private void tesselate(BlockAndTintGetter pLevel, BlockPos pPos, VertexConsumer pBuffer, BlockState pBlockState, FluidState pFluidState, CallbackInfo ci, @Share("list") LocalRef<List<CompletableFuture<Void>>> list) {
        list.set(new ArrayList<>(20));
    }

    @WrapOperation(method = "tesselate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/LiquidBlockRenderer;vertex(Lcom/mojang/blaze3d/vertex/VertexConsumer;FFFFFFFFFI)V"))
    private void tesselate(LiquidBlockRenderer instance, VertexConsumer consumer, float pX, float pY, float pZ, float red, float green, float blue, float alpha, float pU, float pV, int pPackedLight, Operation<Void> original,
                           @Share("list") LocalRef<List<CompletableFuture<Void>>> list, @Local(argsOnly = true) BlockPos pos, @Local(argsOnly = true) FluidState fluidState) {
        if (Config.WIND_EFFECT_CLIENT.isTrue() && ItemBlockRenderTypes.getRenderLayer(fluidState).format() == ModVertexFormat.WAVY_BLOCK.get()) {
            list.get().add(
                    CompletableFuture.supplyAsync(() -> RenderUtils.getFluidWaveData(pos, pX, pY, pZ), ThreadUtils.FLUID_EXECUTOR)
                            .thenAccept(wave -> RenderUtils.fluidVertex(consumer, pX, pY, pZ, red, green, blue, alpha, pU, pV, pPackedLight, wave))
            );
        } else {
            original.call(instance, consumer, pX, pY, pZ, red, green, blue, alpha, pU, pV, pPackedLight);
        }
    }

    @Inject(method = "tesselate", at = @At("TAIL"))
    private void tesselate_(BlockAndTintGetter pLevel, BlockPos pPos, VertexConsumer pBuffer, BlockState pBlockState, FluidState pFluidState, CallbackInfo ci, @Share("list") LocalRef<List<CompletableFuture<Void>>> list) {
        CompletableFuture.allOf(list.get().toArray(CompletableFuture[]::new)).join();
    }
}
