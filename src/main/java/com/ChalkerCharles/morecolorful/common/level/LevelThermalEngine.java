package com.ChalkerCharles.morecolorful.common.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;

public class LevelThermalEngine implements ThermalEventListener{
    protected final LevelHeightAccessor levelHeightAccessor;
    @Nullable
    private final BlockThermalEngine engine;

    public LevelThermalEngine(ChunkSource chunkSource) {
        this.levelHeightAccessor = chunkSource.getLevel();
        this.engine = new BlockThermalEngine(chunkSource);
    }

    @Override
    public void checkBlock(BlockPos pPos) {
        if (this.engine != null) {
            this.engine.checkBlock(pPos);
        }
    }

    @Override
    public boolean hasThermalWork() {
        return this.engine != null && this.engine.hasThermalWork();
    }

    @Override
    public int runThermalUpdates() {
        int i = 0;
        if (this.engine != null) {
            i += this.engine.runThermalUpdates();
        }
        return i;
    }

    @Override
    public void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty) {
        if (this.engine != null) {
            this.engine.updateSectionStatus(pPos, pIsQueueEmpty);
        }
    }

    @Override
    public void setThermalEnabled(ChunkPos pChunkPos, boolean enabled) {
        if (this.engine != null) {
            this.engine.setThermalEnabled(pChunkPos, enabled);
        }
    }

    @Override
    public void propagateThermalSources(ChunkPos pChunkPos) {
        if (this.engine != null) {
            this.engine.propagateThermalSources(pChunkPos);
        }
    }

    public LayerThermalEventListener getLayerListener() {
        return this.engine == null
                ? LayerThermalEventListener.DummyThermalLayerEventListener.INSTANCE
                : this.engine;
    }

    public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        if (this.engine != null) {
            this.engine.queueSectionData(pSectionPos.asLong(), pDataLayer);
        }
    }

    public void retainData(ChunkPos pPos, boolean pRetain) {
        if (this.engine != null) {
            this.engine.retainData(pPos, pRetain);
        }
    }

    public boolean temperatureOnInSection(SectionPos pSectionPos) {
        long i = pSectionPos.asLong();
        return this.engine == null || this.engine.storage.temperatureOnInSection(i);
    }

    public int getThermalSectionCount() {
        return this.levelHeightAccessor.getSectionsCount() + 2;
    }

    public int getMinThermalSection() {
        return this.levelHeightAccessor.getMinSection() - 1;
    }

    public int getMaxThermalSection() {
        return this.getMinThermalSection() + this.getThermalSectionCount();
    }
}
