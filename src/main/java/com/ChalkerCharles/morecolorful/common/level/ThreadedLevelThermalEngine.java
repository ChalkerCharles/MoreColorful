package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.mojang.datafixers.util.Pair;
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

public class ThreadedLevelThermalEngine extends LevelThermalEngine implements AutoCloseable {
    private final ProcessorMailbox<Runnable> taskMailbox;
    private final ObjectList<Pair<ThreadedLevelThermalEngine.TaskType, Runnable>> thermalTasks = new ObjectArrayList<>();
    private final ChunkMap chunkMap;
    private final ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> sorterMailbox;
    private final AtomicBoolean scheduled = new AtomicBoolean();

    public ThreadedLevelThermalEngine(ChunkSource chunkSource, ChunkMap chunkMap, ProcessorMailbox<Runnable> taskMailbox, ProcessorHandle<ChunkTaskPriorityQueueSorter.Message<Runnable>> sorterMailbox) {
        super(chunkSource);
        this.chunkMap = chunkMap;
        this.sorterMailbox = sorterMailbox;
        this.taskMailbox = taskMailbox;
    }

    @Override
    public void close() {
    }

    @Override
    public int runThermalUpdates() {
        throw Util.pauseInIde(new UnsupportedOperationException("Ran automatically on a different thread!"));
    }

    @Override
    public void checkBlock(BlockPos pPos) {
        BlockPos blockpos = pPos.immutable();
        this.addTask(
                SectionPos.blockToSectionCoord(pPos.getX()),
                SectionPos.blockToSectionCoord(pPos.getZ()),
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.checkBlock(blockpos), () -> "checkBlock " + blockpos)
        );
    }

    public void updateChunkStatus(ChunkPos pChunkPos) {
        this.addTask(pChunkPos.x, pChunkPos.z, () -> 0, ThreadedLevelThermalEngine.TaskType.PRE_UPDATE, Util.name(() -> {
            super.retainData(pChunkPos, false);
            super.setThermalEnabled(pChunkPos, false);

            for (int i = this.getMinThermalSection(); i < this.getMaxThermalSection(); i++) {
                super.queueSectionData(SectionPos.of(pChunkPos, i), null);
                super.queueSectionData(SectionPos.of(pChunkPos, i), null);
            }

            for (int j = this.levelHeightAccessor.getMinSection(); j < this.levelHeightAccessor.getMaxSection(); j++) {
                super.updateSectionStatus(SectionPos.of(pChunkPos, j), true);
            }
        }, () -> "updateChunkStatus " + pChunkPos + " true"));
    }

    @Override
    public void updateSectionStatus(SectionPos pPos, boolean pIsEmpty) {
        this.addTask(
                pPos.x(),
                pPos.z(),
                () -> 0,
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.updateSectionStatus(pPos, pIsEmpty), () -> "updateSectionStatus " + pPos + " " + pIsEmpty)
        );
    }

    @Override
    public void propagateThermalSources(ChunkPos pChunkPos) {
        this.addTask(
                pChunkPos.x,
                pChunkPos.z,
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.propagateThermalSources(pChunkPos), () -> "propagateTemperature " + pChunkPos)
        );
    }

    @Override
    public void setThermalEnabled(ChunkPos pChunkPos, boolean enabled) {
        this.addTask(
                pChunkPos.x,
                pChunkPos.z,
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.setThermalEnabled(pChunkPos, enabled), () -> "enableTemperature " + pChunkPos + " " + enabled)
        );
    }

    @Override
    public void queueSectionData(SectionPos pSectionPos, @Nullable DataLayer pDataLayer) {
        this.addTask(
                pSectionPos.x(),
                pSectionPos.z(),
                () -> 0,
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.queueSectionData(pSectionPos, pDataLayer), () -> "queueData " + pSectionPos)
        );
    }

    private void addTask(int pChunkX, int pChunkZ, ThreadedLevelThermalEngine.TaskType pType, Runnable pTask) {
        this.addTask(pChunkX, pChunkZ, this.chunkMap.getChunkQueueLevel(ChunkPos.asLong(pChunkX, pChunkZ)), pType, pTask);
    }

    private void addTask(int pChunkX, int pChunkZ, IntSupplier pQueueLevelSupplier, ThreadedLevelThermalEngine.TaskType pType, Runnable pTask) {
        this.sorterMailbox.tell(ChunkTaskPriorityQueueSorter.message(() -> {
            this.thermalTasks.add(Pair.of(pType, pTask));
            if (this.thermalTasks.size() >= 1000) {
                this.runUpdate();
            }
        }, ChunkPos.asLong(pChunkX, pChunkZ), pQueueLevelSupplier));
    }

    @Override
    public void retainData(ChunkPos pPos, boolean pRetain) {
        this.addTask(
                pPos.x,
                pPos.z,
                () -> 0,
                ThreadedLevelThermalEngine.TaskType.PRE_UPDATE,
                Util.name(() -> super.retainData(pPos, pRetain), () -> "retainData " + pPos)
        );
    }

    public CompletableFuture<ChunkAccess> initializeThermal(ChunkAccess pChunk, boolean enabled) {
        ChunkPos chunkpos = pChunk.getPos();
        this.addTask(chunkpos.x, chunkpos.z, ThreadedLevelThermalEngine.TaskType.PRE_UPDATE, Util.name(() -> {
            LevelChunkSection[] alevelchunksection = pChunk.getSections();

            for (int i = 0; i < pChunk.getSectionsCount(); i++) {
                LevelChunkSection levelchunksection = alevelchunksection[i];
                if (!levelchunksection.hasOnlyAir()) {
                    int j = this.levelHeightAccessor.getSectionYFromSectionIndex(i);
                    super.updateSectionStatus(SectionPos.of(chunkpos, j), false);
                }
            }
        }, () -> "initializeThermal: " + chunkpos));
        return CompletableFuture.supplyAsync(() -> {
            super.setThermalEnabled(chunkpos, enabled);
            super.retainData(chunkpos, false);
            return pChunk;
        }, r -> this.addTask(chunkpos.x, chunkpos.z, ThreadedLevelThermalEngine.TaskType.POST_UPDATE, r));
    }

    public CompletableFuture<ChunkAccess> thermalChunk(ChunkAccess pChunk, boolean pIsThermalized) {
        ChunkPos chunkpos = pChunk.getPos();
        ChunkData.setThermalCorrect(pChunk, false);
        this.addTask(chunkpos.x, chunkpos.z, ThreadedLevelThermalEngine.TaskType.PRE_UPDATE, Util.name(() -> {
            if (!pIsThermalized) {
                super.propagateThermalSources(chunkpos);
            }
        }, () -> "thermalChunk " + chunkpos + " " + pIsThermalized));
        return CompletableFuture.supplyAsync(() -> {
            ChunkData.setThermalCorrect(pChunk, true);
            return pChunk;
        }, r -> this.addTask(chunkpos.x, chunkpos.z, ThreadedLevelThermalEngine.TaskType.POST_UPDATE, r));
    }

    public void tryScheduleUpdate() {
        if ((!this.thermalTasks.isEmpty() || super.hasThermalWork()) && this.scheduled.compareAndSet(false, true)) {
            this.taskMailbox.tell(() -> {
                this.runUpdate();
                this.scheduled.set(false);
            });
        }
    }

    private void runUpdate() {
        int i = Math.min(this.thermalTasks.size(), 1000);
        ObjectListIterator<Pair<ThreadedLevelThermalEngine.TaskType, Runnable>> objectlistiterator = this.thermalTasks.iterator();

        int j;
        for (j = 0; objectlistiterator.hasNext() && j < i; j++) {
            Pair<ThreadedLevelThermalEngine.TaskType, Runnable> pair = objectlistiterator.next();
            if (pair.getFirst() == ThreadedLevelThermalEngine.TaskType.PRE_UPDATE) {
                pair.getSecond().run();
            }
        }

        objectlistiterator.back(j);
        super.runThermalUpdates();

        for (int k = 0; objectlistiterator.hasNext() && k < i; k++) {
            Pair<ThreadedLevelThermalEngine.TaskType, Runnable> pair1 = objectlistiterator.next();
            if (pair1.getFirst() == ThreadedLevelThermalEngine.TaskType.POST_UPDATE) {
                pair1.getSecond().run();
            }

            objectlistiterator.remove();
        }
    }

    enum TaskType {
        PRE_UPDATE,
        POST_UPDATE
    }
}
