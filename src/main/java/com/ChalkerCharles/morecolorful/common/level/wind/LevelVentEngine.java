package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import org.jetbrains.annotations.Nullable;

public class LevelVentEngine implements ILevelVentEngine {
    protected final LevelHeightAccessor levelHeightAccessor;
    @Nullable
    private final VentilationEngine engine;

    public LevelVentEngine(ChunkSource chunkSource, boolean enabled) {
        this.levelHeightAccessor = chunkSource.getLevel();
        this.engine = enabled ? new VentilationEngine(chunkSource) : null;
    }

    @Override
    public void checkBlock(BlockPos pPos) {
        if (this.engine != null) {
            this.engine.checkBlock(pPos);
        }
    }

    @Override
    public boolean hasVentWork() {
        return this.engine != null && this.engine.hasVentWork();
    }

    @Override
    public int runVentUpdates() {
        int i = 0;
        if (this.engine != null) {
            i += this.engine.runVentUpdates();
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
    public void setVentEnabled(ChunkPos pChunkPos, boolean enabled) {
        if (this.engine != null) {
            this.engine.setVentEnabled(pChunkPos, enabled);
        }
    }

    @Override
    public void propagateVentSources(ChunkPos pChunkPos) {
        if (this.engine != null) {
            this.engine.propagateVentSources(pChunkPos);
        }
    }

    @Override
    public LayerVentEventListener getLayerListener() {
        return this.engine == null
                ? LayerVentEventListener.Dummy.INSTANCE
                : this.engine;
    }

    @Override
    public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        if (this.engine != null) {
            this.engine.queueSectionData(pSectionPos.asLong(), pDataLayer);
        }
    }

    @Override
    public void retainData(ChunkPos pPos, boolean pRetain) {
        if (this.engine != null) {
            this.engine.retainData(pPos, pRetain);
        }
    }

    @Override
    public boolean ventilationOnInSection(SectionPos pSectionPos) {
        long i = pSectionPos.asLong();
        return this.engine == null || this.engine.storage.ventilationOnInSection(i);
    }

    @Override
    public int getVentSectionCount() {
        return this.levelHeightAccessor.getSectionsCount();
    }

    @Override
    public int getMinVentSection() {
        return this.levelHeightAccessor.getMinSection();
    }

    @Override
    public int getMaxVentSection() {
        return this.levelHeightAccessor.getMaxSection();
    }
}
