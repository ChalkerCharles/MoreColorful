package com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.ChunkBuildOutput;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderTask;
import net.caffeinemc.mods.sodium.client.render.chunk.translucent_sorting.SortType;
import net.caffeinemc.mods.sodium.client.world.cloned.ChunkRenderContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3dc;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask", remap = false)
public abstract class ChunkBuilderMeshingTaskMixin extends ChunkBuilderTask<ChunkBuildOutput> {
    @Shadow
    @Final
    private ChunkRenderContext renderContext;

    private ChunkBuilderMeshingTaskMixin(RenderSection render, int time, Vector3dc absoluteCameraPos) {
        super(render, time, absoluteCameraPos);
    }

    @Inject(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At("HEAD"))
    private void execute$head(CallbackInfoReturnable<ChunkBuildOutput> cir) {
        if (Config.wavyBlocks) {
            RenderUtils.putWavyTask(IRenderSectionExtension.getWavyTask(this.render));
            RenderUtils.setCacheOrigin(this.renderContext.getOrigin());
        }
    }

    @Inject(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderer;renderModel(Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V"))
    private void execute$cache(CallbackInfoReturnable<ChunkBuildOutput> cir, @Local(ordinal = 0) BlockPos.MutableBlockPos pos, @Local BlockState state) {
        if (Config.wavyBlocks) {
            RenderUtils.initCache(pos, state);
        }
    }

    @Inject(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/BlockRenderCache;getFluidRenderer()Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/pipeline/FluidRenderer;"))
    private void execute$fluid(CallbackInfoReturnable<ChunkBuildOutput> cir, @Local(ordinal = 0) BlockPos.MutableBlockPos pos) {
        if (Config.wavyBlocks) {
            RenderUtils.initFluidCache(pos);
        }
    }

    @Inject(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At(value = "FIELD", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/DefaultTerrainRenderPasses;ALL:[Lnet/caffeinemc/mods/sodium/client/render/chunk/terrain/TerrainRenderPass;", opcode = Opcodes.GETSTATIC))
    private void execute$forceUnassign(CallbackInfoReturnable<ChunkBuildOutput> cir, @Local SortType sortType) {
        if (Config.wavyBlocks && sortType.needsDirectionMixing) {
            IRenderSectionExtension.getWavyTask(this.render).vertices[1].forceUnassigned();
        }
    }

    @Inject(method = "execute(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildContext;Lnet/caffeinemc/mods/sodium/client/util/task/CancellationToken;)Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/ChunkBuildOutput;",
            at = @At("RETURN"))
    private void execute$return(CallbackInfoReturnable<ChunkBuildOutput> cir) {
        if (Config.wavyBlocks) {
            RenderUtils.clearCache();
            RenderUtils.clearWavyTask();
        }
    }
}
