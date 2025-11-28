package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.StagedTask;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunkSection;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.IntSupplier;

public class ThreadedLevelVentEngine extends LevelVentEngine implements AutoCloseable {
    private final ProcessorMailbox<Runnable> taskMailbox;
    private final ObjectList<StagedTask> ventTasks = new ObjectArrayList<>();
    private final ChunkMap chunkMap;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> sorterMailbox;
    private final AtomicBoolean scheduled = new AtomicBoolean();

    public ThreadedLevelVentEngine(ChunkSource chunkSource, ChunkMap pChunkMap, boolean enabled, ProcessorMailbox<Runnable> pTaskMailbox, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> pSorterMailbox) {
        super(chunkSource, enabled);
        this.chunkMap = pChunkMap;
        this.sorterMailbox = pSorterMailbox;
        this.taskMailbox = pTaskMailbox;
    }

    @Override
    public void close() {
    }

    @Override
    public int runVentUpdates() {
        throw Util.pauseInIde(new UnsupportedOperationException("Ran automatically on a different thread!"));
    }

    @Override
    public void checkBlock(BlockPos pPos) {
        BlockPos blockpos = pPos.immutable();
        this.addTask(
                SectionPos.blockToSectionCoord(pPos.getX()),
                SectionPos.blockToSectionCoord(pPos.getZ()),
                true,
                () -> super.checkBlock(blockpos)
        );
    }

    public void updateChunkStatus(ChunkPos pChunkPos) {
        this.addTask(pChunkPos.x, pChunkPos.z, Maths.SUPPLIER_0, true, () -> {
            super.retainData(pChunkPos, false);
            super.setVentEnabled(pChunkPos, false);

            for (int i = this.getMinVentSection(); i < this.getMaxVentSection(); i++) {
                super.queueSectionData(SectionPos.of(pChunkPos, i), null);
            }

            for (int j = this.levelHeightAccessor.getMinSection(); j < this.levelHeightAccessor.getMaxSection(); j++) {
                super.updateSectionStatus(SectionPos.of(pChunkPos, j), true);
            }
        });
    }

    @Override
    public void updateSectionStatus(SectionPos pPos, boolean pIsEmpty) {
        this.addTask(
                pPos.x(),
                pPos.z(),
                Maths.SUPPLIER_0,
                true,
                () -> super.updateSectionStatus(pPos, pIsEmpty)
        );
    }

    @Override
    public void propagateVentSources(ChunkPos pChunkPos) {
        this.addTask(
                pChunkPos.x,
                pChunkPos.z,
                true,
                () -> super.propagateVentSources(pChunkPos)
        );
    }

    @Override
    public void setVentEnabled(ChunkPos pChunkPos, boolean enabled) {
        this.addTask(
                pChunkPos.x,
                pChunkPos.z,
                true,
                () -> super.setVentEnabled(pChunkPos, enabled)
        );
    }

    @Override
    public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        this.addTask(
                pSectionPos.x(),
                pSectionPos.z(),
                Maths.SUPPLIER_0,
                true,
                () -> super.queueSectionData(pSectionPos, pDataLayer)
        );
    }

    private void addTask(int pChunkX, int pChunkZ, boolean isPre, Runnable pTask) {
        this.addTask(pChunkX, pChunkZ, this.chunkMap.getChunkQueueLevel(ChunkPos.asLong(pChunkX, pChunkZ)), isPre, pTask);
    }

    private void addTask(int pChunkX, int pChunkZ, IntSupplier pQueueLevelSupplier, boolean isPre, Runnable pTask) {
        this.sorterMailbox.tell(ChunkTaskPriorityQueueSorter.message(() -> {
            this.ventTasks.add(new StagedTask(isPre, pTask));
            if (this.ventTasks.size() >= 1000) {
                this.runUpdate();
            }
        }, ChunkPos.asLong(pChunkX, pChunkZ), pQueueLevelSupplier));
    }

    @Override
    public void retainData(ChunkPos pPos, boolean pRetain) {
        this.addTask(
                pPos.x,
                pPos.z,
                Maths.SUPPLIER_0,
                true,
                () -> super.retainData(pPos, pRetain)
        );
    }

    public CompletableFuture<ChunkAccess> initializeVentilation(ChunkAccess pChunk, boolean enabled) {
        ChunkPos chunkpos = pChunk.getPos();
        this.addTask(chunkpos.x, chunkpos.z, true, () -> {
            LevelChunkSection[] alevelchunksection = pChunk.getSections();

            for (int i = 0; i < pChunk.getSectionsCount(); i++) {
                LevelChunkSection levelchunksection = alevelchunksection[i];
                if (!levelchunksection.hasOnlyAir()) {
                    int j = this.levelHeightAccessor.getSectionYFromSectionIndex(i);
                    super.updateSectionStatus(SectionPos.of(chunkpos, j), false);
                }
            }
        });
        return CompletableFuture.supplyAsync(() -> {
            super.setVentEnabled(chunkpos, enabled);
            super.retainData(chunkpos, false);
            return pChunk;
        }, r -> this.addTask(chunkpos.x, chunkpos.z, false, r));
    }

    public CompletableFuture<ChunkAccess> ventilateChunk(ChunkAccess pChunk, boolean ventilated) {
        ChunkPos chunkpos = pChunk.getPos();
        ChunkData.setVentilation(pChunk, false);
        this.addTask(chunkpos.x, chunkpos.z, true, () -> {
            if (!ventilated) {
                super.propagateVentSources(chunkpos);
            }
        });
        return CompletableFuture.supplyAsync(() -> {
            ChunkData.setVentilation(pChunk, true);
            return pChunk;
        }, r -> this.addTask(chunkpos.x, chunkpos.z, false, r));
    }

    public void tryScheduleUpdate() {
        if ((!this.ventTasks.isEmpty() || super.hasVentWork()) && this.scheduled.compareAndSet(false, true)) {
            this.taskMailbox.tell(() -> {
                this.runUpdate();
                this.scheduled.set(false);
            });
        }
    }

    private void runUpdate() {
        int i = Math.min(this.ventTasks.size(), 1000);
        ObjectListIterator<StagedTask> iterator = this.ventTasks.iterator();

        int j;
        for (j = 0; iterator.hasNext() && j < i; j++) {
            StagedTask task = iterator.next();
            if (task.isPre()) {
                task.run();
            }
        }

        iterator.back(j);
        super.runVentUpdates();

        for (int k = 0; iterator.hasNext() && k < i; k++) {
            StagedTask task = iterator.next();
            if (task.isPost()) {
                task.run();
            }

            iterator.remove();
        }
    }
}
