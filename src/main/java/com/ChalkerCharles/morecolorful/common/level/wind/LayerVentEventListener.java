package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;

public interface LayerVentEventListener extends VentEventListener {
    @Nullable
    DataLayer getDataLayerData(SectionPos pSectionPos);

    int getVentilationValue(long packedPos);

    enum Dummy implements LayerVentEventListener {
        INSTANCE;

        @Override
        @Nullable
        public DataLayer getDataLayerData(SectionPos pSectionPos) {
            return null;
        }

        @Override
        public int getVentilationValue(long packedPos) {
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
