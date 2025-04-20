package com.ChalkerCharles.morecolorful.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;

public interface LayerThermalEventListener extends ThermalEventListener {
    @Nullable
    DataLayer getDataLayerData(SectionPos pSectionPos);

    int getTemperatureValue(BlockPos pLevelPos);

    enum DummyThermalLayerEventListener implements LayerThermalEventListener {
        INSTANCE;

        @Nullable
        @Override
        public DataLayer getDataLayerData(SectionPos pSectionPos) {
            return null;
        }

        @Override
        public int getTemperatureValue(BlockPos pLevelPos) {
            return 0;
        }

        @Override
        public void checkBlock(BlockPos pPos) {
        }

        @Override
        public boolean hasThermalWork() {
            return false;
        }

        @Override
        public int runThermalUpdates() {
            return 0;
        }

        @Override
        public void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty) {
        }

        @Override
        public void setThermalEnabled(ChunkPos pChunkPos, boolean enabled) {
        }

        @Override
        public void propagateThermalSources(ChunkPos pChunkPos) {
        }
    }
}
