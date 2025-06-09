package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.BlockThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.mixin.extensions.IProtoChunkExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin implements IProtoChunkExtension {
    @Shadow
    private volatile ChunkStatus status;

    @Nullable
    @Unique
    private volatile ILevelThermalEngine moreColorful$thermalEngine;

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ProtoChunk;getPersistedStatus()Lnet/minecraft/world/level/chunk/status/ChunkStatus;", shift = At.Shift.BEFORE))
    public void setBlockState(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, @Local LevelChunkSection levelchunksection, @Local(ordinal = 1) boolean flag, @Local(ordinal = 1) BlockState blockstate) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (this.status.isOrAfter(ModChunkStatus.INITIALIZE_THERMAL.get())) {
            boolean flag1 = levelchunksection.hasOnlyAir();
            if (flag1 != flag) {
                this.moreColorful$thermalEngine.updateSectionStatus(pPos, flag1);
            }

            if (BlockThermalEngine.hasDifferentThermalProperties(blockstate, pState)) {
                this.moreColorful$thermalEngine.checkBlock(pPos);
            }
        }
    }

    @Override
    public void moreColorful$setThermalEngine(ILevelThermalEngine thermalEngine) {
        this.moreColorful$thermalEngine = thermalEngine;
    }
}
