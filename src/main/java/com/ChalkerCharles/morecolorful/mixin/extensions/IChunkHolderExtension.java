package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.thermal.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.LevelVentEngine;
import net.minecraft.server.level.ChunkHolder;

public interface IChunkHolderExtension {
    void moreColorful$setThermalEngine(LevelThermalEngine engine);

    void moreColorful$sectionThermalChanged(int pSectionY);

    void moreColorful$setVentEngine(LevelVentEngine engine);

    void moreColorful$sectionVentChanged(int pSectionY);

    private static IChunkHolderExtension self(ChunkHolder holder) {
        return (IChunkHolderExtension) holder;
    }

    static void setThermalEngine(ChunkHolder holder, LevelThermalEngine engine) {
        self(holder).moreColorful$setThermalEngine(engine);
    }

    static void sectionThermalChanged(ChunkHolder holder, int pSectionY) {
        self(holder).moreColorful$sectionThermalChanged(pSectionY);
    }

    static void setVentEngine(ChunkHolder holder, LevelVentEngine engine) {
        self(holder).moreColorful$setVentEngine(engine);
    }

    static void sectionVentChanged(ChunkHolder holder, int pSectionY) {
        self(holder).moreColorful$sectionVentChanged(pSectionY);
    }
}
