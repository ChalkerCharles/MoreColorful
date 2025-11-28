package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public interface VentEventListener {
    void checkBlock(BlockPos pPos);

    boolean hasVentWork();

    int runVentUpdates();

    default void updateSectionStatus(BlockPos pPos, boolean pIsQueueEmpty) {
        this.updateSectionStatus(SectionPos.of(pPos), pIsQueueEmpty);
    }

    void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty);

    void setVentEnabled(ChunkPos pChunkPos, boolean enabled);

    void propagateVentSources(ChunkPos pChunkPos);
}
