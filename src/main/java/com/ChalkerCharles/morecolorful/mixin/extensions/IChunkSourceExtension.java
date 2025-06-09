package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkAccess;

import javax.annotation.Nullable;

public interface IChunkSourceExtension {
    @Nullable
    ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ);

    void moreColorful$onThermalUpdate(SectionPos pPos);

    ILevelThermalEngine moreColorful$getThermalEngine();
}
