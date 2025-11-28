package com.ChalkerCharles.morecolorful.common.level.thermal;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;

public interface ThermalEventListener {
    void checkBlock(BlockPos pPos);

    boolean hasThermalWork();

    int runThermalUpdates();

    default void updateSectionStatus(BlockPos pPos, boolean pIsQueueEmpty) {
        this.updateSectionStatus(SectionPos.of(pPos), pIsQueueEmpty);
    }

    void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty);

    void setThermalEnabled(ChunkPos pChunkPos, boolean enabled);

    void propagateThermalSources(ChunkPos pChunkPos);
}
