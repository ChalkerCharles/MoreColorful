package com.ChalkerCharles.morecolorful.common.level.thermal;

import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.util.Maths;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.BiConsumer;

public final class BlockThermalEngine implements LayerThermalEventListener {
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
    private static final long PULL_TEMPERATURE_IN_ENTRY = QueueEntry.decreaseAllDirections(1);
    private final ChunkSource chunkSource;
    final BlockThermalStorage storage;
    private final LongOpenHashSet blockNodesToCheck = new LongOpenHashSet(512, 0.5F);
    private final LongArrayFIFOQueue decreaseQueue = new LongArrayFIFOQueue();
    private final LongArrayFIFOQueue increaseQueue = new LongArrayFIFOQueue();
    private final long[] lastChunkPos = new long[2];
    private final ChunkAccess[] lastChunk = new ChunkAccess[2];

    public BlockThermalEngine(ChunkSource chunkSource) {
        this.chunkSource = chunkSource;
        this.storage = new BlockThermalStorage(chunkSource);
        this.clearChunkCache();
    }

    public static boolean hasDifferentThermalProperties(BlockState state1, BlockState state2) {
        if (state2 == state1) {
            return false;
        } else if (IBlockStateExtension.getThermalResistance(state2) == IBlockStateExtension.getThermalResistance(state1)) {
            return false;
        } else {
            return IBlockStateExtension.getTemperature(state2) != IBlockStateExtension.getTemperature(state1);
        }
    }

    private BlockState getState(BlockPos pPos) {
        int i = SectionPos.blockToSectionCoord(pPos.getX());
        int j = SectionPos.blockToSectionCoord(pPos.getZ());
        ChunkAccess chunk = this.getChunk(i, j);
        return chunk == null ? Blocks.BEDROCK.defaultBlockState() : chunk.getBlockState(pPos);
    }

    private int getResistance(BlockState pState) {
        return Math.max(1, IBlockStateExtension.getThermalResistance(pState));
    }

    @Nullable
    private ChunkAccess getChunk(int pX, int pZ) {
        long i = ChunkPos.asLong(pX, pZ);

        for (int j = 0; j < 2; j++) {
            if (i == this.lastChunkPos[j]) {
                return this.lastChunk[j];
            }
        }

        ChunkAccess chunk = IChunkSourceExtension.getThermalChunk(this.chunkSource, pX, pZ);

        for (int k = 1; k > 0; k--) {
            this.lastChunkPos[k] = this.lastChunkPos[k - 1];
            this.lastChunk[k] = this.lastChunk[k - 1];
        }

        this.lastChunkPos[0] = i;
        this.lastChunk[0] = chunk;
        return chunk;
    }

    private void clearChunkCache() {
        Arrays.fill(this.lastChunkPos, ChunkPos.INVALID_CHUNK_POS);
        Arrays.fill(this.lastChunk, null);
    }

    @Override
    public void checkBlock(BlockPos pPos) {
        this.blockNodesToCheck.add(pPos.asLong());
    }

    public void queueSectionData(long pSectionPos, @Nullable DataLayer pData) {
        this.storage.queueSectionData(pSectionPos, pData);
    }

    public void retainData(ChunkPos pChunkPos, boolean pRetainData) {
        this.storage.retainData(SectionPos.getZeroNode(pChunkPos.x, pChunkPos.z), pRetainData);
    }

    @Override
    public void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty) {
        this.storage.updateSectionStatus(pPos.asLong(), pIsQueueEmpty);
    }

    @Override
    public void setThermalEnabled(ChunkPos pChunkPos, boolean enabled) {
        this.storage.setTemperatureEnabled(SectionPos.getZeroNode(pChunkPos.x, pChunkPos.z), enabled);
    }

    @Override
    public int runThermalUpdates() {
        LongIterator longiterator = this.blockNodesToCheck.iterator();

        while (longiterator.hasNext()) {
            this.checkNode(longiterator.nextLong());
        }

        this.blockNodesToCheck.clear();
        this.blockNodesToCheck.trim(512);
        int i = 0;
        i += this.propagateDecreases();
        i += this.propagateIncreases();
        this.clearChunkCache();
        this.storage.markNewInconsistencies();
        this.storage.swapSectionMap();
        return i;
    }

    private int propagateIncreases() {
        int i;
        for (i = 0; !this.increaseQueue.isEmpty(); i++) {
            long j = this.increaseQueue.dequeueLong();
            long k = this.increaseQueue.dequeueLong();
            int l = this.storage.getStoredLevel(j);
            int i1 = QueueEntry.getFromLevel(k);
            if (QueueEntry.isIncreaseFromEmission(k) && l < i1) {
                this.storage.setStoredLevel(j, i1);
                l = i1;
            }

            if (l == i1) {
                this.propagateIncrease(j, k, l);
            }
        }

        return i;
    }

    private int propagateDecreases() {
        int i;
        for (i = 0; !this.decreaseQueue.isEmpty(); i++) {
            long j = this.decreaseQueue.dequeueLong();
            long k = this.decreaseQueue.dequeueLong();
            this.propagateDecrease(j, k);
        }

        return i;
    }

    private void enqueueDecrease(long pPackedPos1, long pPackedPos2) {
        this.decreaseQueue.enqueue(pPackedPos1);
        this.decreaseQueue.enqueue(pPackedPos2);
    }

    private void enqueueIncrease(long pPackedPos1, long pPackedPos2) {
        this.increaseQueue.enqueue(pPackedPos1);
        this.increaseQueue.enqueue(pPackedPos2);
    }

    @Override
    public boolean hasThermalWork() {
        return this.storage.hasInconsistencies() || !this.blockNodesToCheck.isEmpty() || !this.decreaseQueue.isEmpty() || !this.increaseQueue.isEmpty();
    }

    @Nullable
    @Override
    public DataLayer getDataLayerData(SectionPos pSectionPos) {
        return this.storage.getDataLayerData(pSectionPos.asLong());
    }

    @Override
    public int getTemperatureValue(BlockPos pLevelPos) {
        return this.storage.getTemperatureValue(pLevelPos.asLong());
    }

    private void checkNode(long pPackedPos) {
        long i = SectionPos.blockToSection(pPackedPos);
        if (this.storage.storingTemperatureForSection(i)) {
            BlockState blockstate = this.getState(this.mutablePos.set(pPackedPos));
            int j = this.getEmission(pPackedPos, blockstate);
            int k = this.storage.getStoredLevel(pPackedPos);
            if (j < k) {
                this.storage.setStoredLevel(pPackedPos, 0);
                this.enqueueDecrease(pPackedPos, QueueEntry.decreaseAllDirections(k));
            } else {
                this.enqueueDecrease(pPackedPos, PULL_TEMPERATURE_IN_ENTRY);
            }

            if (j > 0) {
                this.enqueueIncrease(pPackedPos, QueueEntry.increaseTemperatureFromEmission(j));
            }
        }
    }

    private void propagateIncrease(long pPackedPos, long pQueueEntry, int pTemperature) {
        BlockState blockstate = null;

        for (Direction direction : Maths.DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(pQueueEntry, direction)) {
                long i = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingTemperatureForSection(SectionPos.blockToSection(i))) {
                    int j = this.storage.getStoredLevel(i);
                    int k = pTemperature - 1;
                    if (k > j) {
                        this.mutablePos.set(i);
                        BlockState blockstate1 = this.getState(this.mutablePos);
                        int l = pTemperature - this.getResistance(blockstate1);
                        if (l > j) {
                            if (blockstate == null) {
                                blockstate = this.getState(this.mutablePos.set(pPackedPos));
                            }
                            this.storage.setStoredLevel(i, l);
                            if (l > 1) {
                                this.enqueueIncrease(
                                        i, QueueEntry.increaseSkipOneDirection(l, direction.getOpposite())
                                );
                            }
                        }
                    }
                }
            }
        }
    }

    private void propagateDecrease(long pPackedPos, long temperature) {
        int i = QueueEntry.getFromLevel(temperature);

        for (Direction direction : Maths.DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(temperature, direction)) {
                long j = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingTemperatureForSection(SectionPos.blockToSection(j))) {
                    int k = this.storage.getStoredLevel(j);
                    if (k != 0) {
                        if (k <= i - 1) {
                            BlockState blockstate = this.getState(this.mutablePos.set(j));
                            int l = this.getEmission(j, blockstate);
                            this.storage.setStoredLevel(j, 0);
                            if (l < k) {
                                this.enqueueDecrease(j, QueueEntry.decreaseSkipOneDirection(k, direction.getOpposite()));
                            }

                            if (l > 0) {
                                this.enqueueIncrease(j, QueueEntry.increaseTemperatureFromEmission(l));
                            }
                        } else {
                            this.enqueueIncrease(j, QueueEntry.increaseOnlyOneDirection(k, direction.getOpposite()));
                        }
                    }
                }
            }
        }
    }

    private int getEmission(long pPackedPos, BlockState pState) {
        int i = IBlockStateExtension.getTemperature(pState);
        return i > 0 && this.storage.temperatureOnInSection(SectionPos.blockToSection(pPackedPos)) ? i : 0;
    }

    @Override
    public void propagateThermalSources(ChunkPos pChunkPos) {
        this.setThermalEnabled(pChunkPos, true);
        ChunkAccess chunk = IChunkSourceExtension.getThermalChunk(this.chunkSource, pChunkPos.x, pChunkPos.z);
        if (chunk != null) {
            findBlockThermalSources(chunk, (blockPos, blockState) -> {
                int i = IBlockStateExtension.getTemperature(blockState);
                this.enqueueIncrease(blockPos.asLong(), QueueEntry.increaseTemperatureFromEmission(i));
            });
        }
    }

    private static void findBlockThermalSources(ChunkAccess chunk, BiConsumer<BlockPos, BlockState> pOutput) {
        chunk.findBlocks(state -> IBlockStateExtension.getTemperature(state) != 0, pOutput);
    }

    public static class QueueEntry {
        public static long decreaseSkipOneDirection(int pLevel, Direction pDirection) {
            long i = withoutDirection(pDirection);
            return withLevel(i, pLevel);
        }

        public static long decreaseAllDirections(int pLevel) {
            return withLevel(1008L, pLevel);
        }

        public static long increaseTemperatureFromEmission(int pLevel) {
            long i = 1008L;
            i |= 2048L;
            return withLevel(i, pLevel);
        }

        public static long increaseSkipOneDirection(int pLevel, Direction pDirection) {
            long i = withoutDirection(pDirection);
            return withLevel(i, pLevel);
        }

        public static long increaseOnlyOneDirection(int pLevel, Direction pDirection) {
            long i = 0L;
            i = withDirection(i, pDirection);
            return withLevel(i, pLevel);
        }

        public static int getFromLevel(long pEntry) {
            return (int)(pEntry & 15L);
        }

        public static boolean isIncreaseFromEmission(long pEntry) {
            return (pEntry & 2048L) != 0L;
        }

        public static boolean shouldPropagateInDirection(long pEntry, Direction pDirection) {
            return (pEntry & 1L << pDirection.ordinal() + 4) != 0L;
        }

        private static long withLevel(long pEntry, int pLevel) {
            return pEntry & -16L | (long)pLevel & 15L;
        }

        private static long withDirection(long pEntry, Direction pDirection) {
            return pEntry | 1L << pDirection.ordinal() + 4;
        }
        
        private static long withoutDirection(Direction pDirection) {
            return 1008L & ~(1L << pDirection.ordinal() + 4);
        }
    }
}
