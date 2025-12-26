package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.thermal.BlockThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.VentilationEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelChunkExtension;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import com.ChalkerCharles.morecolorful.util.client.MultiBlockGroup;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.blending.BlendingData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin extends ChunkAccess implements ILevelChunkExtension {
    @Shadow
    @Final
    Level level;
    @Unique
    @Nullable
    private MultiBlockGroup moreColorful$group;

    private LevelChunkMixin(ChunkPos pChunkPos, UpgradeData pUpgradeData, LevelHeightAccessor pLevelHeightAccessor, Registry<Biome> pBiomeRegistry, long pInhabitedTime, @Nullable LevelChunkSection[] pSections, @Nullable BlendingData pBlendingData) {
        super(pChunkPos, pUpgradeData, pLevelHeightAccessor, pBiomeRegistry, pInhabitedTime, pSections, pBlendingData);
    }

    @Inject(method = "<init>(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ProtoChunk;Lnet/minecraft/world/level/chunk/LevelChunk$PostLoadProcessor;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;setLightCorrect(Z)V", shift = At.Shift.AFTER))
    private void constructor$server(ServerLevel pLevel, ProtoChunk pChunk, LevelChunk.PostLoadProcessor pPostLoad, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ChunkData.setThermalCorrect(this, ChunkData.isThermalCorrect(pChunk));
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            ChunkData.copyVentilationSources(this, pChunk);
            ChunkData.setVentilation(this, ChunkData.isVentilated(pChunk));
        }
    }

    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;updateSectionStatus(Lnet/minecraft/core/BlockPos;Z)V", shift = At.Shift.AFTER))
    private void setBlockState$1(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, @Local(ordinal = 2) boolean flag1) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            LevelSavedData.getThermalEngine(this.level).updateSectionStatus(pPos, flag1);
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            LevelSavedData.getVentEngine(this.level).updateSectionStatus(pPos, flag1);
        }
    }

    @Nullable
    @Inject(method = "setBlockState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;hasBlockEntity()Z", ordinal = 0, shift = At.Shift.BEFORE))
    private void setBlockState$2(BlockPos pPos, BlockState pState, boolean pIsMoving, CallbackInfoReturnable<BlockState> cir, @Local(ordinal = 1) BlockState blockstate) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if (BlockThermalEngine.hasDifferentThermalProperties(blockstate, pState)) {
                ProfilerFiller profilerfiller = this.level.getProfiler();
                profilerfiller.push("queueCheckThermal");
                LevelSavedData.getThermalEngine(this.level).checkBlock(pPos);
                profilerfiller.pop();
            }
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            if (VentilationEngine.hasDifferentVentProperties(blockstate, pState)) {
                ProfilerFiller profilerfiller = this.level.getProfiler();
                profilerfiller.push("updateVentilationSources");
                int i = pPos.getX() & 15, j = pPos.getY(), k = pPos.getZ() & 15;
                ChunkData.getVentilationSources(this).update(this, i, j, k);
                profilerfiller.push("queueCheckVent");
                LevelSavedData.getVentEngine(this.level).checkBlock(pPos);
                profilerfiller.pop();
                if (this.level.isClientSide && ClientWrapper.wavyBlocks()) {
                    ClientWrapper.clearDataInLine(this.level, pPos);
                }
            }
        }
        if (this.level.isClientSide && ClientWrapper.wavyBlocks()) {
            boolean isPrevGroup = IBlockStateExtension.isGroupBlock(blockstate);
            boolean isNowGroup = IBlockStateExtension.isGroupBlock(pState);
            if (isPrevGroup || isNowGroup) {
                this.moreColorful$getMultiBlockGroup().tryUpdate(pPos, blockstate, pState, isPrevGroup, isNowGroup);
            }
        }
    }

    @Inject(method = "replaceWithPacketData", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/chunk/LevelChunk;initializeLightSources()V", shift = At.Shift.AFTER))
    private void replaceWithPacketData(CallbackInfo ci) {
        if (Config.WIND_SYSTEM.isFalse()) return;
        ChunkData.initializeVentSources(this);
    }

    @Override
    public MultiBlockGroup moreColorful$getMultiBlockGroup() {
        if (!this.level.isClientSide)
            throw new IllegalCallerException("MultiBlockGroup only exists on client");
        if (this.moreColorful$group == null) {
            this.moreColorful$group = new MultiBlockGroup(this);
        }
        return this.moreColorful$group;
    }
}
