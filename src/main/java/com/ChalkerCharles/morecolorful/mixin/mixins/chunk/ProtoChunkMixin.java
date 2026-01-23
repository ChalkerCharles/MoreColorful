package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.common.level.thermal.BlockThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.VentilationEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IProtoChunkExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.UpgradeData;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin extends ChunkAccess implements IProtoChunkExtension {
    @Shadow
    private volatile ChunkStatus status;
    @Unique
    private volatile ILevelThermalEngine moreColorful$thermalEngine;
    @Unique
    private volatile ILevelVentEngine moreColorful$ventEngine;

    private ProtoChunkMixin(ChunkPos pChunkPos, UpgradeData pUpgradeData, LevelHeightAccessor pLevelHeightAccessor, Registry<Biome> pBiomeRegistry, long pInhabitedTime, @Nullable LevelChunkSection[] pSections, @Nullable BlendingData pBlendingData) {
        super(pChunkPos, pUpgradeData, pLevelHeightAccessor, pBiomeRegistry, pInhabitedTime, pSections, pBlendingData);
    }

    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/ProtoChunk;getPersistedStatus()Lnet/minecraft/world/level/chunk/status/ChunkStatus;", shift = At.Shift.BEFORE))
    public void setBlockState(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir,
                              @Local LevelChunkSection levelchunksection, @Local(ordinal = 1) boolean flag, @Local(ordinal = 1) BlockState blockstate) {
        if (Config.thermalSystem && this.status.isOrAfter(ModChunkStatus.INITIALIZE_THERMAL.get()) && this.moreColorful$thermalEngine != null) {
            boolean flag1 = levelchunksection.hasOnlyAir();
            if (flag1 != flag) {
                this.moreColorful$thermalEngine.updateSectionStatus(pPos, flag1);
            }

            if (BlockThermalEngine.hasDifferentThermalProperties(blockstate, pState)) {
                this.moreColorful$thermalEngine.checkBlock(pPos);
            }
        }

        if (Config.windSystem && this.status.isOrAfter(ModChunkStatus.INITIALIZE_VENT.get()) && this.moreColorful$ventEngine != null) {
            boolean flag1 = levelchunksection.hasOnlyAir();
            if (flag1 != flag) {
                this.moreColorful$ventEngine.updateSectionStatus(pPos, flag1);
            }

            if (VentilationEngine.hasDifferentVentProperties(blockstate, pState)) {
                int x = pPos.getX() & 15;
                int y = pPos.getY();
                int z = pPos.getZ() & 15;
                ChunkData.getVentilationSources(this).update(this, x, y, z);
                this.moreColorful$ventEngine.checkBlock(pPos);
            }
        }
    }

    @Override
    public void moreColorful$setThermalEngine(ILevelThermalEngine thermalEngine) {
        this.moreColorful$thermalEngine = thermalEngine;
    }

    @Override
    public void moreColorful$setVentEngine(ILevelVentEngine ventEngine) {
        this.moreColorful$ventEngine = ventEngine;
    }
}
