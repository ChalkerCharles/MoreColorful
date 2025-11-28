package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;

public interface ILevelVentEngine extends VentEventListener {
    LayerVentEventListener getLayerListener();

    void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer);

    void retainData(ChunkPos pPos, boolean pRetain);

    default void tryScheduleUpdate() {}

    boolean ventilationOnInSection(SectionPos pSectionPos);

    int getVentSectionCount();

    int getMinVentSection();

    int getMaxVentSection();

    enum Dummy implements ILevelVentEngine {
        INSTANCE;

        @Override
        public LayerVentEventListener getLayerListener() {
            return LayerVentEventListener.Dummy.INSTANCE;
        }

        @Override
        public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        }

        @Override
        public void retainData(ChunkPos pPos, boolean pRetain) {
        }

        @Override
        public boolean ventilationOnInSection(SectionPos pSectionPos) {
            return false;
        }

        @Override
        public int getVentSectionCount() {
            return 0;
        }

        @Override
        public int getMinVentSection() {
            return 0;
        }

        @Override
        public int getMaxVentSection() {
            return 0;
        }

        @Override
        public void checkBlock(BlockPos pPos) {
        }

        @Override
        public boolean hasVentWork() {
            return false;
        }

        @Override
        public int runVentUpdates() {
            return 0;
        }

        @Override
        public void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty) {
        }

        @Override
        public void setVentEnabled(ChunkPos pChunkPos, boolean enabled) {
        }

        @Override
        public void propagateVentSources(ChunkPos pChunkPos) {
        }
    }
}
