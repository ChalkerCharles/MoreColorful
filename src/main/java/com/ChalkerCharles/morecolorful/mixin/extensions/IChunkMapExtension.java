package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import net.minecraft.server.level.ChunkMap;

public interface IChunkMapExtension {
    ThreadedLevelThermalEngine moreColorful$getThermalEngine();

    ThreadedLevelVentEngine moreColorful$getVentEngine();

    private static IChunkMapExtension self(ChunkMap chunkMap) {
        return (IChunkMapExtension) chunkMap;
    }

    static ThreadedLevelThermalEngine getThermalEngine(ChunkMap chunkMap) {
        return self(chunkMap).moreColorful$getThermalEngine();
    }

    static ThreadedLevelVentEngine getVentEngine(ChunkMap chunkMap) {
        return self(chunkMap).moreColorful$getVentEngine();
    }
}
