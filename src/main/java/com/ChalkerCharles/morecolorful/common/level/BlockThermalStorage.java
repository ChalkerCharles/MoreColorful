package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.DataLayerStorageMap;

import javax.annotation.Nullable;

public class BlockThermalStorage {
    private final ChunkSource chunkSource;
    private final Long2ByteMap sectionStates = new Long2ByteOpenHashMap();
    private final LongSet columnsWithSources = new LongOpenHashSet();
    private volatile BlockThermalStorage.BlockDataLayerStorageMap visibleSectionData;
    private final BlockThermalStorage.BlockDataLayerStorageMap updatingSectionData;
    private final LongSet changedSections = new LongOpenHashSet();
    private final LongSet sectionsAffectedByThermalUpdates = new LongOpenHashSet();
    private final Long2ObjectMap<DataLayer> queuedSections = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
    private final LongSet columnsToRetainQueuedDataFor = new LongOpenHashSet();
    private final LongSet toRemove = new LongOpenHashSet();
    protected volatile boolean hasInconsistencies;

    protected BlockThermalStorage(ChunkSource chunkSource) {
        this.chunkSource = chunkSource;
        this.updatingSectionData = new BlockThermalStorage.BlockDataLayerStorageMap(new Long2ObjectOpenHashMap<>());
        this.visibleSectionData = this.updatingSectionData.copy();
        this.visibleSectionData.disableCache();
        this.sectionStates.defaultReturnValue((byte)0);
    }

    protected boolean storingTemperatureForSection(long pSectionPos) {
        return this.getDataLayer(pSectionPos, true) != null;
    }

    @Nullable
    protected DataLayer getDataLayer(long pSectionPos, boolean pCached) {
        return this.getDataLayer(pCached ? this.updatingSectionData : this.visibleSectionData, pSectionPos);
    }

    @Nullable
    protected DataLayer getDataLayer(BlockThermalStorage.BlockDataLayerStorageMap pMap, long pSectionPos) {
        return pMap.getLayer(pSectionPos);
    }

    @Nullable
    public DataLayer getDataLayerData(long pSectionPos) {
        DataLayer datalayer = this.queuedSections.get(pSectionPos);
        return datalayer != null ? datalayer : this.getDataLayer(pSectionPos, false);
    }

    protected int getTemperatureValue(long pLevelPos) {
        long i = SectionPos.blockToSection(pLevelPos);
        DataLayer datalayer = this.getDataLayer(i, false);
        return datalayer == null
                ? 0
                : datalayer.get(
                SectionPos.sectionRelative(BlockPos.getX(pLevelPos)),
                SectionPos.sectionRelative(BlockPos.getY(pLevelPos)),
                SectionPos.sectionRelative(BlockPos.getZ(pLevelPos))
        );
    }

    protected int getStoredLevel(long pLevelPos) {
        long i = SectionPos.blockToSection(pLevelPos);
        DataLayer datalayer = this.getDataLayer(i, true);
        if (datalayer != null) {
            return datalayer.get(
                    SectionPos.sectionRelative(BlockPos.getX(pLevelPos)),
                    SectionPos.sectionRelative(BlockPos.getY(pLevelPos)),
                    SectionPos.sectionRelative(BlockPos.getZ(pLevelPos))
            );
        }
        return 0;
    }

    protected void setStoredLevel(long pLevelPos, int pTemperatureLevel) {
        long i = SectionPos.blockToSection(pLevelPos);
        DataLayer datalayer;
        if (this.changedSections.add(i)) {
            datalayer = this.updatingSectionData.copyDataLayer(i);
        } else {
            datalayer = this.getDataLayer(i, true);
        }

        if (datalayer != null) {
            datalayer.set(
                    SectionPos.sectionRelative(BlockPos.getX(pLevelPos)),
                    SectionPos.sectionRelative(BlockPos.getY(pLevelPos)),
                    SectionPos.sectionRelative(BlockPos.getZ(pLevelPos)),
                    pTemperatureLevel
            );
        }
        SectionPos.aroundAndAtBlockPos(pLevelPos, this.sectionsAffectedByThermalUpdates::add);
    }

    protected void markSectionAndNeighborsAsAffected(long pSectionPos) {
        int i = SectionPos.x(pSectionPos);
        int j = SectionPos.y(pSectionPos);
        int k = SectionPos.z(pSectionPos);

        for (int l = -1; l <= 1; l++) {
            for (int i1 = -1; i1 <= 1; i1++) {
                for (int j1 = -1; j1 <= 1; j1++) {
                    this.sectionsAffectedByThermalUpdates.add(SectionPos.asLong(i + i1, j + j1, k + l));
                }
            }
        }
    }

    protected DataLayer createDataLayer(long pSectionPos) {
        DataLayer datalayer = this.queuedSections.get(pSectionPos);
        return datalayer != null ? datalayer : new DataLayer();
    }

    protected boolean hasInconsistencies() {
        return this.hasInconsistencies;
    }

    protected void markNewInconsistencies(BlockThermalEngine ignoredEngine) {
        if (this.hasInconsistencies) {
            this.hasInconsistencies = false;

            for (long i : this.toRemove) {
                DataLayer datalayer = this.queuedSections.remove(i);
                DataLayer datalayer1 = this.updatingSectionData.removeLayer(i);
                if (this.columnsToRetainQueuedDataFor.contains(SectionPos.getZeroNode(i))) {
                    if (datalayer != null) {
                        this.queuedSections.put(i, datalayer);
                    } else if (datalayer1 != null) {
                        this.queuedSections.put(i, datalayer1);
                    }
                }
            }

            this.updatingSectionData.clearCache();

            for (long k : this.toRemove) {
                this.changedSections.add(k);
            }

            this.toRemove.clear();
            ObjectIterator<Long2ObjectMap.Entry<DataLayer>> objectiterator = Long2ObjectMaps.fastIterator(this.queuedSections);

            while (objectiterator.hasNext()) {
                Long2ObjectMap.Entry<DataLayer> entry = objectiterator.next();
                long j = entry.getLongKey();
                if (this.storingTemperatureForSection(j)) {
                    DataLayer datalayer2 = entry.getValue();
                    if (this.updatingSectionData.getLayer(j) != datalayer2) {
                        this.updatingSectionData.setLayer(j, datalayer2);
                        this.changedSections.add(j);
                    }

                    objectiterator.remove();
                }
            }

            this.updatingSectionData.clearCache();
        }
    }

    protected void setTemperatureEnabled(long pSectionPos, boolean enabled) {
        if (enabled) {
            this.columnsWithSources.add(pSectionPos);
        } else {
            this.columnsWithSources.remove(pSectionPos);
        }
    }

    protected boolean temperatureOnInSection(long pSectionPos) {
        long i = SectionPos.getZeroNode(pSectionPos);
        return this.columnsWithSources.contains(i);
    }

    public void retainData(long pSectionColumnPos, boolean pRetain) {
        if (pRetain) {
            this.columnsToRetainQueuedDataFor.add(pSectionColumnPos);
        } else {
            this.columnsToRetainQueuedDataFor.remove(pSectionColumnPos);
        }
    }

    protected void queueSectionData(long pSectionPos, @Nullable DataLayer pData) {
        if (pData != null) {
            this.queuedSections.put(pSectionPos, pData);
            this.hasInconsistencies = true;
        } else {
            this.queuedSections.remove(pSectionPos);
        }
    }

    protected void updateSectionStatus(long pSectionPos, boolean pIsEmpty) {
        byte b0 = this.sectionStates.get(pSectionPos);
        byte b1 = BlockThermalStorage.SectionState.hasData(b0, !pIsEmpty);
        if (b0 != b1) {
            this.putSectionState(pSectionPos, b1);
            int i = pIsEmpty ? -1 : 1;

            for (int j = -1; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    for (int l = -1; l <= 1; l++) {
                        if (j != 0 || k != 0 || l != 0) {
                            long i1 = SectionPos.offset(pSectionPos, j, k, l);
                            byte b2 = this.sectionStates.get(i1);
                            this.putSectionState(
                                    i1, BlockThermalStorage.SectionState.neighborCount(b2, BlockThermalStorage.SectionState.neighborCount(b2) + i)
                            );
                        }
                    }
                }
            }
        }
    }

    protected void putSectionState(long pSectionPos, byte pSectionState) {
        if (pSectionState != 0) {
            if (this.sectionStates.put(pSectionPos, pSectionState) == 0) {
                this.initializeSection(pSectionPos);
            }
        } else if (this.sectionStates.remove(pSectionPos) != 0) {
            this.removeSection(pSectionPos);
        }
    }

    private void initializeSection(long pSectionPos) {
        if (!this.toRemove.remove(pSectionPos)) {
            this.updatingSectionData.setLayer(pSectionPos, this.createDataLayer(pSectionPos));
            this.changedSections.add(pSectionPos);
            this.markSectionAndNeighborsAsAffected(pSectionPos);
            this.hasInconsistencies = true;
        }
    }

    private void removeSection(long pSectionPos) {
        this.toRemove.add(pSectionPos);
        this.hasInconsistencies = true;
    }

    protected void swapSectionMap() {
        if (!this.changedSections.isEmpty()) {
            BlockThermalStorage.BlockDataLayerStorageMap map = this.updatingSectionData.copy();
            map.disableCache();
            this.visibleSectionData = map;
            this.changedSections.clear();
        }

        if (!this.sectionsAffectedByThermalUpdates.isEmpty()) {
            LongIterator longiterator = this.sectionsAffectedByThermalUpdates.iterator();

            while (longiterator.hasNext()) {
                long i = longiterator.nextLong();
                ((IChunkSourceExtension) this.chunkSource).moreColorful$onThermalUpdate(SectionPos.of(i));
            }

            this.sectionsAffectedByThermalUpdates.clear();
        }
    }

    protected static class SectionState {
        public static byte hasData(byte pSectionState, boolean pHasData) {
            return (byte)(pHasData ? pSectionState | 32 : pSectionState & -33);
        }

        public static byte neighborCount(byte pSectionState, int pNeighborCount) {
            if (pNeighborCount >= 0 && pNeighborCount <= 26) {
                return (byte)(pSectionState & -32 | pNeighborCount & 31);
            } else {
                throw new IllegalArgumentException("Neighbor count was not within range [0; 26]");
            }
        }

        public static int neighborCount(byte pSectionState) {
            return pSectionState & 31;
        }
    }

    protected static final class BlockDataLayerStorageMap extends DataLayerStorageMap<BlockThermalStorage.BlockDataLayerStorageMap> {
        public BlockDataLayerStorageMap(Long2ObjectOpenHashMap<DataLayer> pMap) {
            super(pMap);
        }

        @Override
        public BlockThermalStorage.BlockDataLayerStorageMap copy() {
            return new BlockThermalStorage.BlockDataLayerStorageMap(this.map.clone());
        }
    }
}
