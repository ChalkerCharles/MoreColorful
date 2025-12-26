package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.status.ChunkStatus;

import javax.annotation.Nullable;

public interface IChunkSourceExtension {
    private ChunkSource self() {
        return (ChunkSource) this;
    }

    @Nullable
    default ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ) {
        return self().getChunk(pChunkX, pChunkZ, ChunkStatus.EMPTY, false);
    }

    default void moreColorful$onThermalUpdate(SectionPos pPos) {
    }

    default ILevelThermalEngine moreColorful$getThermalEngine() {
        return ILevelThermalEngine.Dummy.INSTANCE;
    }

    @Nullable
    default ChunkAccess moreColorful$getVentChunk(int pChunkX, int pChunkZ) {
        return self().getChunk(pChunkX, pChunkZ, ChunkStatus.EMPTY, false);
    }

    default void moreColorful$onVentUpdate(SectionPos pPos) {
    }

    default ILevelVentEngine moreColorful$getVentEngine() {
        return ILevelVentEngine.Dummy.INSTANCE;
    }

    private static IChunkSourceExtension self(ChunkSource chunkSource) {
        return (IChunkSourceExtension) chunkSource;
    }

    @Nullable
    static ChunkAccess getThermalChunk(ChunkSource chunkSource, int pChunkX, int pChunkZ) {
        return self(chunkSource).moreColorful$getThermalChunk(pChunkX, pChunkZ);
    }

    static void onThermalUpdate(ChunkSource chunkSource, SectionPos pPos) {
        self(chunkSource).moreColorful$onThermalUpdate(pPos);
    }

    static ILevelThermalEngine getThermalEngine(ChunkSource chunkSource) {
        return self(chunkSource).moreColorful$getThermalEngine();
    }

    @Nullable
    static ChunkAccess getVentChunk(ChunkSource chunkSource, int pChunkX, int pChunkZ) {
        return self(chunkSource).moreColorful$getVentChunk(pChunkX, pChunkZ);
    }

    static void onVentUpdate(ChunkSource chunkSource, SectionPos pPos) {
        self(chunkSource).moreColorful$onVentUpdate(pPos);
    }

    static ILevelVentEngine getVentEngine(ChunkSource chunkSource) {
        return self(chunkSource).moreColorful$getVentEngine();
    }
}
