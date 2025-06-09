package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.chunk.status.ChunkPyramid;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
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
    private static ChunkPyramid.Builder addThermalGeneration(ChunkPyramid.Builder original) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            original.step(ModChunkStatus.INITIALIZE_THERMAL.get(), b -> b.setTask(ModChunkStatus::initializeThermal))
                    .step(ModChunkStatus.THERMAL.get(), b -> b.addRequirement(ModChunkStatus.INITIALIZE_THERMAL.get(), 0).setTask(ModChunkStatus::thermal));
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
    private static ChunkPyramid.Builder addThermalLoading(ChunkPyramid.Builder original) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            original.step(ModChunkStatus.INITIALIZE_THERMAL.get(), b -> b.setTask(ModChunkStatus::initializeThermal))
                    .step(ModChunkStatus.THERMAL.get(), b -> b.addRequirement(ModChunkStatus.INITIALIZE_THERMAL.get(), 0).setTask(ModChunkStatus::thermal));
        }
        return original;
    }
}
