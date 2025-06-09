package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.common.level.BlockThermalEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.ProtoChunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin {
    @Shadow
    @Final
    Level level;

    @Inject(method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ProtoChunk;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;setLightCorrect(Z)V", shift = At.Shift.AFTER))
    private void constructor(ServerLevel pLevel, ProtoChunk pChunk, LevelChunk.PostLoadProcessor pPostLoad, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ChunkData.setThermalCorrect(pChunk, ChunkData.isThermalCorrect(pChunk));
        }
    }

    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;updateSectionStatus(Lnet/minecraft/core/BlockPos;Z)V", shift = At.Shift.AFTER))
    private void setBlockState$1(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, @Local(ordinal = 2) boolean flag1) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ((IChunkSourceExtension) this.level.getChunkSource()).moreColorful$getThermalEngine().updateSectionStatus(pPos, flag1);
        }
    }

    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;hasBlockEntity()Z", ordinal = 0, shift = At.Shift.BEFORE))
    private void setBlockState$2(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, @Local(ordinal = 1) BlockState blockstate) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (BlockThermalEngine.hasDifferentThermalProperties(blockstate, pState)) {
            ProfilerFiller profilerfiller = this.level.getProfiler();
            profilerfiller.push("queueCheckThermal");
            ((IChunkSourceExtension) this.level.getChunkSource()).moreColorful$getThermalEngine().checkBlock(pPos);
            profilerfiller.pop();
        }
    }
}
