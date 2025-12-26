package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyBufferBuilder;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyVertices;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor.IByteBufferBuilderResultMixin;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.accessor.IMeshDataMixin;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SectionBufferBuilderPack;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.client.renderer.chunk.SectionCompiler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.AddSectionGeometryEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(SectionCompiler.class)
public abstract class SectionCompilerMixin {
    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At("HEAD"))
    private void compile$head(SectionPos pSectionPos, RenderChunkRegion pRegion, VertexSorting pVertexSorting, SectionBufferBuilderPack pSectionBufferBuilderPack, List<AddSectionGeometryEvent.AdditionalSectionRenderer> additionalRenderers, CallbackInfoReturnable<SectionCompiler.Results> cir) {
        if (RenderUtils.wavyBlocks) {
            RenderUtils.setCacheOrigin(pSectionPos);
        }
    }

    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderLiquid(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V"))
    private void compile$fluid(CallbackInfoReturnable<SectionCompiler.Results> cir, @Local(ordinal = 2) BlockPos pos) {
        if (RenderUtils.wavyBlocks) {
            RenderUtils.initFluidCache(pos);
        }
    }

    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;setSeed(J)V", shift = At.Shift.AFTER))
    private void compile$cache(CallbackInfoReturnable<SectionCompiler.Results> cir, @Local(ordinal = 2) BlockPos pos, @Local BlockState state) {
        if (RenderUtils.wavyBlocks) {
            RenderUtils.initCache(pos, state);
        }
    }

    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"))
    private void compile$setCapacity(CallbackInfoReturnable<SectionCompiler.Results> cir, @Local RenderType renderType, @Local MeshData meshData) {
        if (RenderUtils.wavyBlocks) {
            WavyVertices.Default vertices = RenderUtils.getWavyVertices(renderType);
            if (vertices != null) {
                ByteBufferBuilder.Result result = ((IMeshDataMixin) meshData).getVertexBuffer();
                vertices.setLength(((IByteBufferBuilderResultMixin) result).getCapacity());
            }
        }
    }

    @Inject(method = "compile(Lnet/minecraft/core/SectionPos;Lnet/minecraft/client/renderer/chunk/RenderChunkRegion;Lcom/mojang/blaze3d/vertex/VertexSorting;Lnet/minecraft/client/renderer/SectionBufferBuilderPack;Ljava/util/List;)Lnet/minecraft/client/renderer/chunk/SectionCompiler$Results;",
            at = @At("TAIL"))
    private void compile$tail(CallbackInfoReturnable<SectionCompiler.Results> cir) {
        if (RenderUtils.wavyBlocks) {
            RenderUtils.clearCache();
            RenderUtils.clearWavyTask();
        }
    }

    @WrapOperation(method = "getOrBeginLayer", at = @At(value = "NEW", args = "class=com/mojang/blaze3d/vertex/BufferBuilder"))
    private BufferBuilder getOrBeginLayer(ByteBufferBuilder builder, VertexFormat.Mode mode, VertexFormat format, Operation<BufferBuilder> original, @Local(argsOnly = true) RenderType renderType) {
        if (RenderUtils.wavyBlocks) {
            WavyVertices.Default vertices = RenderUtils.getWavyVertices(renderType);
            if (vertices != null) {
                return new WavyBufferBuilder(builder, mode, format, vertices);
            }
        }
        return original.call(builder, mode, format);
    }
}
