package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import net.minecraft.world.level.chunk.ChunkAccess;

public interface IProtoChunkExtension {
    void moreColorful$setThermalEngine(ILevelThermalEngine thermalEngine);

    void moreColorful$setVentEngine(ILevelVentEngine ventEngine);

    private static IProtoChunkExtension self(ChunkAccess chunk) {
        return (IProtoChunkExtension) chunk;
    }

    static void setThermalEngine(ChunkAccess chunk, ILevelThermalEngine thermalEngine) {
        self(chunk).moreColorful$setThermalEngine(thermalEngine);
    }

    static void setVentEngine(ChunkAccess chunk, ILevelVentEngine ventEngine) {
        self(chunk).moreColorful$setVentEngine(ventEngine);
    }
}
