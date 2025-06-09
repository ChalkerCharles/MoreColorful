package com.ChalkerCharles.morecolorful.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;

public interface ILevelThermalEngine extends ThermalEventListener{
    LayerThermalEventListener getLayerListener();

    void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer);

    void retainData(ChunkPos pPos, boolean pRetain);

    boolean temperatureOnInSection(SectionPos pSectionPos);

    int getThermalSectionCount();

    int getMinThermalSection();

    int getMaxThermalSection();

    enum DummyLevelThermalEngine implements ILevelThermalEngine {
        INSTANCE;

        @Override
        public LayerThermalEventListener getLayerListener() {
            return LayerThermalEventListener.DummyThermalLayerEventListener.INSTANCE;
        }

        @Override
        public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        }

        @Override
        public void retainData(ChunkPos pPos, boolean pRetain) {
        }

        @Override
        public boolean temperatureOnInSection(SectionPos pSectionPos) {
            return false;
        }

        @Override
        public int getThermalSectionCount() {
            return 0;
        }

        @Override
        public int getMinThermalSection() {
            return 0;
        }

        @Override
        public int getMaxThermalSection() {
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
