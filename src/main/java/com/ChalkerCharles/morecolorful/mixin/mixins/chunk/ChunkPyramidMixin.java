package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import net.minecraft.world.level.chunk.status.ChunkStep;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(ChunkPyramid.class)
public abstract class ChunkPyramidMixin {
    @ModifyExpressionValue(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/status/ChunkPyramid$Builder;step(Lnet/minecraft/world/level/chunk/status/ChunkStatus;Ljava/util/function/UnaryOperator;)Lnet/minecraft/world/level/chunk/status/ChunkPyramid$Builder;"),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/status/ChunkStatus;SPAWN:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", opcode = Opcodes.GETSTATIC, ordinal = 0),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/status/ChunkStatus;FULL:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", opcode = Opcodes.GETSTATIC, ordinal = 0)
            )
    )
    private static ChunkPyramid.Builder addGenerationPyramid(ChunkPyramid.Builder original) {
        if (Config.thermalSystem) {
            original.step(ModChunkStatus.INITIALIZE_THERMAL.get(), ChunkPyramidMixin::lambda$initializeThermal)
                    .step(ModChunkStatus.THERMAL.get(), ChunkPyramidMixin::lambda$thermal);
        }
        if (Config.windSystem) {
            original.step(ModChunkStatus.INITIALIZE_VENT.get(), ChunkPyramidMixin::lambda$initializeVent)
                    .step(ModChunkStatus.VENTILATION.get(), ChunkPyramidMixin::lambda$ventilation);
        }
        return original;
    }

    @ModifyExpressionValue(method = "<clinit>",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/chunk/status/ChunkPyramid$Builder;step(Lnet/minecraft/world/level/chunk/status/ChunkStatus;Ljava/util/function/UnaryOperator;)Lnet/minecraft/world/level/chunk/status/ChunkPyramid$Builder;"),
            slice = @Slice(
                    from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/status/ChunkStatus;SPAWN:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", opcode = Opcodes.GETSTATIC, ordinal = 1),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/level/chunk/status/ChunkStatus;FULL:Lnet/minecraft/world/level/chunk/status/ChunkStatus;", opcode = Opcodes.GETSTATIC, ordinal = 1)
            )
    )
    private static ChunkPyramid.Builder addLoadingPyramid(ChunkPyramid.Builder original) {
        if (Config.thermalSystem) {
            original.step(ModChunkStatus.INITIALIZE_THERMAL.get(), ChunkPyramidMixin::lambda$initializeThermal)
                    .step(ModChunkStatus.THERMAL.get(), ChunkPyramidMixin::lambda$thermal);
        }
        if (Config.windSystem) {
            original.step(ModChunkStatus.INITIALIZE_VENT.get(), ChunkPyramidMixin::lambda$initializeVent)
                    .step(ModChunkStatus.VENTILATION.get(), ChunkPyramidMixin::lambda$ventilation);
        }
        return original;
    }

    @Unique
    private static ChunkStep.Builder lambda$initializeThermal(ChunkStep.Builder builder) {
        return builder.setTask(ModChunkStatus::initializeThermal);
    }

    @Unique
    private static ChunkStep.Builder lambda$thermal(ChunkStep.Builder builder) {
        return builder.addRequirement(ModChunkStatus.INITIALIZE_THERMAL.get(), 0).setTask(ModChunkStatus::thermal);
    }

    @Unique
    private static ChunkStep.Builder lambda$initializeVent(ChunkStep.Builder builder) {
        return builder.setTask(ModChunkStatus::initializeVent);
    }

    @Unique
    private static ChunkStep.Builder lambda$ventilation(ChunkStep.Builder builder) {
        return builder.addRequirement(ModChunkStatus.INITIALIZE_VENT.get(), 0).setTask(ModChunkStatus::ventilate);
    }
}
