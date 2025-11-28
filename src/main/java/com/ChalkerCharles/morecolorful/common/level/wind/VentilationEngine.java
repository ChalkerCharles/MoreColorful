package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.util.AirBlocking;
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
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class VentilationEngine implements LayerVentEventListener {
    private static final long PULL_VENT_IN_ENTRY = QueueEntry.decreaseAllDirections(1);
    private final ChunkSource chunkSource;
    final VentilationStorage storage;
    private final LongOpenHashSet blockNodesToCheck = new LongOpenHashSet(512, 0.5F);
    private final LongArrayFIFOQueue decreaseQueue = new LongArrayFIFOQueue();
    private final LongArrayFIFOQueue increaseQueue = new LongArrayFIFOQueue();
    private final BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
    private final long[] lastChunkPos = new long[2];
    private final ChunkAccess[] lastChunk = new ChunkAccess[2];
    private static final long REMOVE_TOP_SKY_SOURCE_ENTRY = QueueEntry.decreaseAllDirections(15);
    private static final long REMOVE_SKY_SOURCE_ENTRY = QueueEntry.decreaseSkipOneDirection(15, Direction.UP);
    private static final long ADD_SKY_SOURCE_ENTRY = QueueEntry.increaseSkipOneDirection(15, false, Direction.UP);
    private final ChunkVentilationSources emptyChunkSources;

    public VentilationEngine(ChunkSource pChunkSource) {
        this.chunkSource = pChunkSource;
        this.storage = new VentilationStorage(pChunkSource);
        this.clearChunkCache();
        this.emptyChunkSources = new ChunkVentilationSources(pChunkSource.getLevel());
    }

    public static boolean hasDifferentVentProperties(BlockState state1, BlockState state2) {
        if (state2 == state1) {
            return false;
        } else if (state2.useShapeForLightOcclusion() || state1.useShapeForLightOcclusion()) {
            return true;
        } else {
            return IBlockStateExtension.getAirBlock(state2) != IBlockStateExtension.getAirBlock(state1);
        }
    }

    private BlockState getState(BlockPos pPos) {
        int i = SectionPos.blockToSectionCoord(pPos.getX());
        int j = SectionPos.blockToSectionCoord(pPos.getZ());
        ChunkAccess chunk = this.getChunk(i, j);
        return chunk == null ? Blocks.BEDROCK.defaultBlockState() : chunk.getBlockState(pPos);
    }

    private int getAirBlock(BlockState state, Direction direction) {
        return Math.max(1, AirBlocking.getAirBlock(state, direction));
    }

    private boolean shapeOccludes(long pPackedPos1, BlockState pState1, long pPackedPos2, BlockState pState2, Direction pDirection) {
        VoxelShape shape = this.getOcclusionShape(pState1, pPackedPos1, pDirection);
        VoxelShape shape1 = this.getOcclusionShape(pState2, pPackedPos2, pDirection.getOpposite());
        return Shapes.faceShapeOccludes(shape, shape1);
    }

    private VoxelShape getOcclusionShape(BlockState pState, long pPos, Direction pDirection) {
        return LightEngine.getOcclusionShape(this.chunkSource.getLevel(), this.mutablePos.set(pPos), pState, pDirection);
    }

    private static boolean isEmptyShape(BlockState pState) {
        return !pState.canOcclude() || !pState.useShapeForLightOcclusion();
    }

    @Nullable
    protected ChunkAccess getChunk(int pX, int pZ) {
        long i = ChunkPos.asLong(pX, pZ);

        for (int j = 0; j < 2; j++) {
            if (i == this.lastChunkPos[j]) {
                return this.lastChunk[j];
            }
        }

        ChunkAccess chunk = IChunkSourceExtension.getVentChunk(this.chunkSource, pX, pZ);

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
    @Nullable
    public DataLayer getDataLayerData(SectionPos pSectionPos) {
        return this.storage.getDataLayerData(pSectionPos.asLong());
    }

    @Override
    public int getVentilationValue(long packedPos) {
        return this.storage.getVentilationValue(packedPos);
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
    public boolean hasVentWork() {
        return this.storage.hasInconsistencies() || !this.blockNodesToCheck.isEmpty() || !this.decreaseQueue.isEmpty() || !this.increaseQueue.isEmpty();
    }

    @Override
    public int runVentUpdates() {
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

    protected void enqueueDecrease(long pPackedPos1, long pPackedPos2) {
        this.decreaseQueue.enqueue(pPackedPos1);
        this.decreaseQueue.enqueue(pPackedPos2);
    }

    protected void enqueueIncrease(long pPackedPos1, long pPackedPos2) {
        this.increaseQueue.enqueue(pPackedPos1);
        this.increaseQueue.enqueue(pPackedPos2);
    }

    @Override
    public void updateSectionStatus(SectionPos pPos, boolean pIsQueueEmpty) {
        this.storage.updateSectionStatus(pPos.asLong(), pIsQueueEmpty);
    }

    @Override
    public void setVentEnabled(ChunkPos pChunkPos, boolean enabled) {
        this.storage.setVentilationEnabled(SectionPos.getZeroNode(pChunkPos.x, pChunkPos.z), enabled);
        if (enabled) {
            ChunkVentilationSources sources = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x, pChunkPos.z), this.emptyChunkSources);
            int i = sources.getHighestLowestSourceY() - 1;
            int j = SectionPos.blockToSectionCoord(i) + 1;
            long k = SectionPos.getZeroNode(pChunkPos.x, pChunkPos.z);
            int l = this.storage.getTopSectionY(k);
            int i1 = Math.max(this.storage.getBottomSectionY(), j);

            for (int j1 = l - 1; j1 >= i1; j1--) {
                DataLayer datalayer = this.storage.getDataLayerToWrite(SectionPos.asLong(pChunkPos.x, j1, pChunkPos.z));
                if (datalayer != null && datalayer.isEmpty()) {
                    datalayer.fill(15);
                }
            }
        }
    }

    private static boolean isSourceLevel(int pLevel) {
        return pLevel == 15;
    }

    private int getLowestSourceY(int pX, int pZ, int pDefaultReturnValue) {
        ChunkVentilationSources sources = this.getChunkSources(SectionPos.blockToSectionCoord(pX), SectionPos.blockToSectionCoord(pZ));
        return sources == null
                ? pDefaultReturnValue
                : sources.getLowestSourceY(SectionPos.sectionRelative(pX), SectionPos.sectionRelative(pZ));
    }

    @Nullable
    private ChunkVentilationSources getChunkSources(int pChunkX, int pChunkZ) {
        ChunkAccess chunk = IChunkSourceExtension.getVentChunk(this.chunkSource, pChunkX, pChunkZ);
        return chunk != null ? ChunkData.getVentilationSources(chunk) : null;
    }
    
    protected void checkNode(long pLevelPos) {
        int i = BlockPos.getX(pLevelPos);
        int j = BlockPos.getY(pLevelPos);
        int k = BlockPos.getZ(pLevelPos);
        long l = SectionPos.blockToSection(pLevelPos);
        int i1 = this.storage.ventilationOnInSection(l) ? this.getLowestSourceY(i, k, Integer.MAX_VALUE) : Integer.MAX_VALUE;
        if (i1 != Integer.MAX_VALUE) {
            this.updateSourcesInColumn(i, k, i1);
        }

        if (this.storage.storingVentilationForSection(l)) {
            boolean flag = j >= i1;
            if (flag) {
                this.enqueueDecrease(pLevelPos, REMOVE_SKY_SOURCE_ENTRY);
                this.enqueueIncrease(pLevelPos, ADD_SKY_SOURCE_ENTRY);
            } else {
                int j1 = this.storage.getStoredLevel(pLevelPos);
                if (j1 > 0) {
                    this.storage.setStoredLevel(pLevelPos, 0);
                    this.enqueueDecrease(pLevelPos, QueueEntry.decreaseAllDirections(j1));
                } else {
                    this.enqueueDecrease(pLevelPos, PULL_VENT_IN_ENTRY);
                }
            }
        }
    }

    private void updateSourcesInColumn(int pX, int pZ, int pLowestY) {
        int i = SectionPos.sectionToBlockCoord(this.storage.getBottomSectionY());
        this.removeSourcesBelow(pX, pZ, pLowestY, i);
        this.addSourcesAbove(pX, pZ, pLowestY, i);
    }

    private void removeSourcesBelow(int pX, int pZ, int pMinY, int pBottomSectionY) {
        if (pMinY > pBottomSectionY) {
            int i = SectionPos.blockToSectionCoord(pX);
            int j = SectionPos.blockToSectionCoord(pZ);
            int k = pMinY - 1;

            for (int l = SectionPos.blockToSectionCoord(k); this.storage.hasVentDataAtOrBelow(l); l--) {
                if (this.storage.storingVentilationForSection(SectionPos.asLong(i, l, j))) {
                    int i1 = SectionPos.sectionToBlockCoord(l);
                    int j1 = i1 + 15;

                    for (int k1 = Math.min(j1, k); k1 >= i1; k1--) {
                        long l1 = BlockPos.asLong(pX, k1, pZ);
                        if (!isSourceLevel(this.storage.getStoredLevel(l1))) {
                            return;
                        }

                        this.storage.setStoredLevel(l1, 0);
                        this.enqueueDecrease(l1, k1 == pMinY - 1 ? REMOVE_TOP_SKY_SOURCE_ENTRY : REMOVE_SKY_SOURCE_ENTRY);
                    }
                }
            }
        }
    }

    private void addSourcesAbove(int pX, int pZ, int pMaxY, int pBottomSectionY) {
        int i = SectionPos.blockToSectionCoord(pX);
        int j = SectionPos.blockToSectionCoord(pZ);
        int k = Math.max(
                Math.max(this.getLowestSourceY(pX - 1, pZ, Integer.MIN_VALUE), this.getLowestSourceY(pX + 1, pZ, Integer.MIN_VALUE)),
                Math.max(this.getLowestSourceY(pX, pZ - 1, Integer.MIN_VALUE), this.getLowestSourceY(pX, pZ + 1, Integer.MIN_VALUE))
        );
        int l = Math.max(pMaxY, pBottomSectionY);

        for (long i1 = SectionPos.asLong(i, SectionPos.blockToSectionCoord(l), j); !this.storage.isAboveData(i1); i1 = SectionPos.offset(i1, Direction.UP)) {
            if (this.storage.storingVentilationForSection(i1)) {
                int j1 = SectionPos.sectionToBlockCoord(SectionPos.y(i1));
                int k1 = j1 + 15;

                for (int l1 = Math.max(j1, l); l1 <= k1; l1++) {
                    long i2 = BlockPos.asLong(pX, l1, pZ);
                    if (isSourceLevel(this.storage.getStoredLevel(i2))) {
                        return;
                    }

                    this.storage.setStoredLevel(i2, 15);
                    if (l1 < k || l1 == pMaxY) {
                        this.enqueueIncrease(i2, ADD_SKY_SOURCE_ENTRY);
                    }
                }
            }
        }
    }
    
    protected void propagateIncrease(long pPackedPos, long pQueueEntry, int ventLevel) {
        BlockState blockstate = null;
        int i = this.countEmptySectionsBelowIfAtBorder(pPackedPos);

        for (Direction direction : Maths.DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(pQueueEntry, direction)) {
                long j = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingVentilationForSection(SectionPos.blockToSection(j))) {
                    int k = this.storage.getStoredLevel(j);
                    int l = ventLevel - 1;
                    if (l > k) {
                        this.mutablePos.set(j);
                        BlockState blockstate1 = this.getState(this.mutablePos);
                        int i1 = ventLevel - this.getAirBlock(blockstate1, direction);
                        if (i1 > k) {
                            if (blockstate == null) {
                                blockstate = QueueEntry.isFromEmptyShape(pQueueEntry)
                                        ? Blocks.AIR.defaultBlockState()
                                        : this.getState(this.mutablePos.set(pPackedPos));
                            }

                            if (!this.shapeOccludes(pPackedPos, blockstate, j, blockstate1, direction)) {
                                this.storage.setStoredLevel(j, i1);
                                if (i1 > 1) {
                                    this.enqueueIncrease(
                                            j, QueueEntry.increaseSkipOneDirection(i1, isEmptyShape(blockstate1), direction.getOpposite())
                                    );
                                }

                                this.propagateFromEmptySections(j, direction, i1, true, i);
                            }
                        }
                    }
                }
            }
        }
    }
    
    protected void propagateDecrease(long pPackedPos, long ventLevel) {
        int i = this.countEmptySectionsBelowIfAtBorder(pPackedPos);
        int j = QueueEntry.getFromLevel(ventLevel);

        for (Direction direction : Maths.DIRECTIONS) {
            if (QueueEntry.shouldPropagateInDirection(ventLevel, direction)) {
                long k = BlockPos.offset(pPackedPos, direction);
                if (this.storage.storingVentilationForSection(SectionPos.blockToSection(k))) {
                    int l = this.storage.getStoredLevel(k);
                    if (l != 0) {
                        if (l <= j - 1) {
                            this.storage.setStoredLevel(k, 0);
                            this.enqueueDecrease(k, QueueEntry.decreaseSkipOneDirection(l, direction.getOpposite()));
                            this.propagateFromEmptySections(k, direction, l, false, i);
                        } else {
                            this.enqueueIncrease(k, QueueEntry.increaseOnlyOneDirection(l, false, direction.getOpposite()));
                        }
                    }
                }
            }
        }
    }

    private int countEmptySectionsBelowIfAtBorder(long pPackedPos) {
        int i = BlockPos.getY(pPackedPos);
        int j = SectionPos.sectionRelative(i);
        if (j != 0) {
            return 0;
        } else {
            int k = BlockPos.getX(pPackedPos);
            int l = BlockPos.getZ(pPackedPos);
            int i1 = SectionPos.sectionRelative(k);
            int j1 = SectionPos.sectionRelative(l);
            if (i1 != 0 && i1 != 15 && j1 != 0 && j1 != 15) {
                return 0;
            } else {
                int k1 = SectionPos.blockToSectionCoord(k);
                int l1 = SectionPos.blockToSectionCoord(i);
                int i2 = SectionPos.blockToSectionCoord(l);
                int j2 = 0;

                while (!this.storage.storingVentilationForSection(SectionPos.asLong(k1, l1 - j2 - 1, i2)) && this.storage.hasVentDataAtOrBelow(l1 - j2 - 1)) {
                    j2++;
                }

                return j2;
            }
        }
    }

    private void propagateFromEmptySections(long pPackedPos, Direction pDirection, int pLevel, boolean pShouldIncrease, int pEmptySections) {
        if (pEmptySections != 0) {
            int i = BlockPos.getX(pPackedPos);
            int j = BlockPos.getZ(pPackedPos);
            if (crossedSectionEdge(pDirection, SectionPos.sectionRelative(i), SectionPos.sectionRelative(j))) {
                int k = BlockPos.getY(pPackedPos);
                int l = SectionPos.blockToSectionCoord(i);
                int i1 = SectionPos.blockToSectionCoord(j);
                int j1 = SectionPos.blockToSectionCoord(k) - 1;
                int k1 = j1 - pEmptySections + 1;

                while (j1 >= k1) {
                    if (this.storage.storingVentilationForSection(SectionPos.asLong(l, j1, i1))) {
                        int l1 = SectionPos.sectionToBlockCoord(j1);

                        for (int i2 = 15; i2 >= 0; i2--) {
                            long j2 = BlockPos.asLong(i, l1 + i2, j);
                            if (pShouldIncrease) {
                                this.storage.setStoredLevel(j2, pLevel);
                                if (pLevel > 1) {
                                    this.enqueueIncrease(j2, QueueEntry.increaseSkipOneDirection(pLevel, true, pDirection.getOpposite()));
                                }
                            } else {
                                this.storage.setStoredLevel(j2, 0);
                                this.enqueueDecrease(j2, QueueEntry.decreaseSkipOneDirection(pLevel, pDirection.getOpposite()));
                            }
                        }

                    }
                    j1--;
                }
            }
        }
    }

    private static boolean crossedSectionEdge(Direction pDirection, int pX, int pZ) {
        return switch (pDirection) {
            case NORTH -> pZ == 15;
            case SOUTH -> pZ == 0;
            case WEST -> pX == 15;
            case EAST -> pX == 0;
            default -> false;
        };
    }

    @Override
    public void propagateVentSources(ChunkPos pChunkPos) {
        long i = SectionPos.getZeroNode(pChunkPos.x, pChunkPos.z);
        this.storage.setVentilationEnabled(i, true);
        ChunkVentilationSources sources = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x, pChunkPos.z), this.emptyChunkSources);
        ChunkVentilationSources sources1 = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x, pChunkPos.z - 1), this.emptyChunkSources);
        ChunkVentilationSources sources2 = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x, pChunkPos.z + 1), this.emptyChunkSources);
        ChunkVentilationSources sources3 = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x - 1, pChunkPos.z), this.emptyChunkSources);
        ChunkVentilationSources sources4 = Objects.requireNonNullElse(this.getChunkSources(pChunkPos.x + 1, pChunkPos.z), this.emptyChunkSources);
        int j = this.storage.getTopSectionY(i);
        int k = this.storage.getBottomSectionY();
        int l = SectionPos.sectionToBlockCoord(pChunkPos.x);
        int i1 = SectionPos.sectionToBlockCoord(pChunkPos.z);

        for (int j1 = j - 1; j1 >= k; j1--) {
            long k1 = SectionPos.asLong(pChunkPos.x, j1, pChunkPos.z);
            DataLayer datalayer = this.storage.getDataLayerToWrite(k1);
            if (datalayer != null) {
                int l1 = SectionPos.sectionToBlockCoord(j1);
                int i2 = l1 + 15;
                boolean flag = false;

                for (int j2 = 0; j2 < 16; j2++) {
                    for (int k2 = 0; k2 < 16; k2++) {
                        int l2 = sources.getLowestSourceY(k2, j2);
                        if (l2 <= i2) {
                            int i3 = j2 == 0 ? sources1.getLowestSourceY(k2, 15) : sources.getLowestSourceY(k2, j2 - 1);
                            int j3 = j2 == 15 ? sources2.getLowestSourceY(k2, 0) : sources.getLowestSourceY(k2, j2 + 1);
                            int k3 = k2 == 0 ? sources3.getLowestSourceY(15, j2) : sources.getLowestSourceY(k2 - 1, j2);
                            int l3 = k2 == 15 ? sources4.getLowestSourceY(0, j2) : sources.getLowestSourceY(k2 + 1, j2);
                            int i4 = Math.max(Math.max(i3, j3), Math.max(k3, l3));

                            for (int j4 = i2; j4 >= Math.max(l1, l2); j4--) {
                                datalayer.set(k2, SectionPos.sectionRelative(j4), j2, 15);
                                if (j4 == l2 || j4 < i4) {
                                    long k4 = BlockPos.asLong(l + k2, j4, i1 + j2);
                                    this.enqueueIncrease(k4, QueueEntry.increaseSkySourceInDirections(j4 == l2, j4 < i3, j4 < j3, j4 < k3, j4 < l3));
                                }
                            }

                            if (l2 < l1) {
                                flag = true;
                            }
                        }
                    }
                }

                if (!flag) {
                    break;
                }
            }
        }
    }

    public static class QueueEntry {
        public static long decreaseSkipOneDirection(int pLevel, Direction pDirection) {
            long i = withoutDirection(pDirection);
            return withLevel(i, pLevel);
        }

        public static long decreaseAllDirections(int pLevel) {
            return withLevel(1008L, pLevel);
        }

        public static long increaseSkipOneDirection(int pLevel, boolean pFromEmptyShape, Direction pDirection) {
            long i = withoutDirection(pDirection);
            if (pFromEmptyShape) {
                i |= 1024L;
            }

            return withLevel(i, pLevel);
        }

        public static long increaseOnlyOneDirection(int pLevel, boolean pFromEmptyShape, Direction pDirection) {
            long i = 0L;
            if (pFromEmptyShape) {
                i |= 1024L;
            }

            i = withDirection(i, pDirection);
            return withLevel(i, pLevel);
        }

        public static long increaseSkySourceInDirections(boolean pDown, boolean pNorth, boolean pSouth, boolean pWest, boolean pEast) {
            long i = withLevel(0L, 15);
            if (pDown) {
                i = withDirection(i, Direction.DOWN);
            }
            if (pNorth) {
                i = withDirection(i, Direction.NORTH);
            }
            if (pSouth) {
                i = withDirection(i, Direction.SOUTH);
            }
            if (pWest) {
                i = withDirection(i, Direction.WEST);
            }
            if (pEast) {
                i = withDirection(i, Direction.EAST);
            }
            return i;
        }

        public static int getFromLevel(long pEntry) {
            return (int)(pEntry & 15L);
        }

        public static boolean isFromEmptyShape(long pEntry) {
            return (pEntry & 1024L) != 0L;
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
