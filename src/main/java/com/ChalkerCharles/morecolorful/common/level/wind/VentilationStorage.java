package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import it.unimi.dsi.fastutil.longs.*;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.DataLayerStorageMap;

import javax.annotation.Nullable;

public class VentilationStorage {
    private final ChunkSource chunkSource;
    private final Long2ByteMap sectionStates = new Long2ByteOpenHashMap();
    private final LongSet columnsWithSources = new LongOpenHashSet();
    private volatile VentilationStorageMap visibleSectionData;
    private final VentilationStorageMap updatingSectionData;
    private final LongSet changedSections = new LongOpenHashSet();
    private final LongSet sectionsAffectedByVentUpdates = new LongOpenHashSet();
    private final Long2ObjectMap<DataLayer> queuedSections = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap<>());
    private final LongSet columnsToRetainQueuedDataFor = new LongOpenHashSet();
    private final LongSet toRemove = new LongOpenHashSet();
    private volatile boolean hasInconsistencies;

    protected VentilationStorage(ChunkSource chunkSource) {
        this.chunkSource = chunkSource;
        this.updatingSectionData = new VentilationStorageMap(new Long2ObjectOpenHashMap<>(), new Long2IntOpenHashMap(), Integer.MAX_VALUE);
        this.visibleSectionData = this.updatingSectionData.copy();
        this.visibleSectionData.disableCache();
        this.sectionStates.defaultReturnValue((byte) 0);
    }

    protected boolean storingVentilationForSection(long pSectionPos) {
        return this.getDataLayer(pSectionPos, true) != null;
    }

    @Nullable
    protected DataLayer getDataLayer(long pSectionPos, boolean pCached) {
        return this.getDataLayer(pCached ? this.updatingSectionData : this.visibleSectionData, pSectionPos);
    }

    @Nullable
    private DataLayer getDataLayer(VentilationStorageMap pMap, long pSectionPos) {
        return pMap.getLayer(pSectionPos);
    }

    @Nullable
    protected DataLayer getDataLayerToWrite(long pSectionPos) {
        DataLayer datalayer = this.updatingSectionData.getLayer(pSectionPos);
        if (datalayer == null) {
            return null;
        } else {
            if (this.changedSections.add(pSectionPos)) {
                datalayer = datalayer.copy();
                this.updatingSectionData.setLayer(pSectionPos, datalayer);
                this.updatingSectionData.clearCache();
            }

            return datalayer;
        }
    }

    @Nullable
    public DataLayer getDataLayerData(long pSectionPos) {
        DataLayer datalayer = this.queuedSections.get(pSectionPos);
        return datalayer != null ? datalayer : this.getDataLayer(pSectionPos, false);
    }

    protected int getVentilationValue(long pPackedPos) {
        long i = SectionPos.blockToSection(pPackedPos);
        int j = SectionPos.y(i);
        VentilationStorageMap map = this.visibleSectionData;
        int k = map.topSections.get(SectionPos.getZeroNode(i));
        if (k != map.currentLowestY && j < k) {
            DataLayer datalayer = this.getDataLayer(map, i);
            if (datalayer == null) {
                for (pPackedPos = BlockPos.getFlatIndex(pPackedPos);
                     datalayer == null;
                     datalayer = this.getDataLayer(map, i)
                ) {
                    if (++j >= k) {
                        return 15;
                    }

                    i = SectionPos.offset(i, Direction.UP);
                }
            }

            return datalayer.get(
                    SectionPos.sectionRelative(BlockPos.getX(pPackedPos)),
                    SectionPos.sectionRelative(BlockPos.getY(pPackedPos)),
                    SectionPos.sectionRelative(BlockPos.getZ(pPackedPos))
            );
        } else {
            return 15;
        }
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

    protected void setStoredLevel(long pLevelPos, int ventLevel) {
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
                    ventLevel
            );
        }
        SectionPos.aroundAndAtBlockPos(pLevelPos, this.sectionsAffectedByVentUpdates::add);
    }

    protected void markSectionAndNeighborsAsAffected(long pSectionPos) {
        int i = SectionPos.x(pSectionPos);
        int j = SectionPos.y(pSectionPos);
        int k = SectionPos.z(pSectionPos);

        for (int l = -1; l <= 1; l++) {
            for (int i1 = -1; i1 <= 1; i1++) {
                for (int j1 = -1; j1 <= 1; j1++) {
                    this.sectionsAffectedByVentUpdates.add(SectionPos.asLong(i + i1, j + j1, k + l));
                }
            }
        }
    }

    protected DataLayer createDataLayer(long pSectionPos) {
        DataLayer datalayer = this.queuedSections.get(pSectionPos);
        if (datalayer != null) {
            return datalayer;
        } else {
            int i = this.updatingSectionData.topSections.get(SectionPos.getZeroNode(pSectionPos));
            if (i != this.updatingSectionData.currentLowestY && SectionPos.y(pSectionPos) < i) {
                long j = SectionPos.offset(pSectionPos, Direction.UP);

                DataLayer datalayer1;
                while ((datalayer1 = this.getDataLayer(j, true)) == null) {
                    j = SectionPos.offset(j, Direction.UP);
                }

                return repeatFirstLayer(datalayer1);
            } else {
                return this.ventilationOnInSection(pSectionPos) ? new DataLayer(15) : new DataLayer();
            }
        }
    }

    protected boolean hasInconsistencies() {
        return this.hasInconsistencies;
    }

    protected void markNewInconsistencies() {
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
                this.onNodeRemoved(k);
                this.changedSections.add(k);
            }

            this.toRemove.clear();
            ObjectIterator<Long2ObjectMap.Entry<DataLayer>> objectiterator = Long2ObjectMaps.fastIterator(this.queuedSections);

            while (objectiterator.hasNext()) {
                Long2ObjectMap.Entry<DataLayer> entry = objectiterator.next();
                long j = entry.getLongKey();
                if (this.storingVentilationForSection(j)) {
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

    protected void onNodeAdded(long pSectionPos) {
        int i = SectionPos.y(pSectionPos);
        if (this.updatingSectionData.currentLowestY > i) {
            this.updatingSectionData.currentLowestY = i;
            this.updatingSectionData.topSections.defaultReturnValue(this.updatingSectionData.currentLowestY);
        }

        long j = SectionPos.getZeroNode(pSectionPos);
        int k = this.updatingSectionData.topSections.get(j);
        if (k < i + 1) {
            this.updatingSectionData.topSections.put(j, i + 1);
        }
    }

    protected void onNodeRemoved(long pSectionPos) {
        long i = SectionPos.getZeroNode(pSectionPos);
        int j = SectionPos.y(pSectionPos);
        if (this.updatingSectionData.topSections.get(i) == j + 1) {
            long k;
            for (k = pSectionPos; !this.storingVentilationForSection(k) && this.hasVentDataAtOrBelow(j); k = SectionPos.offset(k, Direction.DOWN)) {
                j--;
            }

            if (this.storingVentilationForSection(k)) {
                this.updatingSectionData.topSections.put(i, j + 1);
            } else {
                this.updatingSectionData.topSections.remove(i);
            }
        }
    }

    protected void setVentilationEnabled(long pSectionPos, boolean enabled) {
        if (enabled) {
            this.columnsWithSources.add(pSectionPos);
        } else {
            this.columnsWithSources.remove(pSectionPos);
        }
    }

    protected boolean ventilationOnInSection(long pSectionPos) {
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
        byte b1 = SectionState.hasData(b0, !pIsEmpty);
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
                                    i1, SectionState.neighborCount(b2, SectionState.neighborCount(b2) + i)
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
            this.onNodeAdded(pSectionPos);
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
            VentilationStorageMap map = this.updatingSectionData.copy();
            map.disableCache();
            this.visibleSectionData = map;
            this.changedSections.clear();
        }

        if (!this.sectionsAffectedByVentUpdates.isEmpty()) {
            LongIterator longiterator = this.sectionsAffectedByVentUpdates.iterator();

            while (longiterator.hasNext()) {
                long i = longiterator.nextLong();
                IChunkSourceExtension.onVentUpdate(this.chunkSource, SectionPos.of(i));
            }

            this.sectionsAffectedByVentUpdates.clear();
        }
    }

    private static DataLayer repeatFirstLayer(DataLayer pDataLayer) {
        if (pDataLayer.isDefinitelyHomogenous()) {
            return pDataLayer.copy();
        } else {
            byte[] abyte = pDataLayer.getData();
            byte[] abyte1 = new byte[2048];

            for (int i = 0; i < 16; i++) {
                System.arraycopy(abyte, 0, abyte1, i * 128, 128);
            }

            return new DataLayer(abyte1);
        }
    }

    protected boolean hasVentDataAtOrBelow(int pY) {
        return pY >= this.updatingSectionData.currentLowestY;
    }

    protected boolean isAboveData(long pSectionPos) {
        long i = SectionPos.getZeroNode(pSectionPos);
        int j = this.updatingSectionData.topSections.get(i);
        return j == this.updatingSectionData.currentLowestY || SectionPos.y(pSectionPos) >= j;
    }

    protected int getTopSectionY(long pSectionPos) {
        return this.updatingSectionData.topSections.get(pSectionPos);
    }

    protected int getBottomSectionY() {
        return this.updatingSectionData.currentLowestY;
    }

    private static class SectionState {
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

    private static final class VentilationStorageMap extends DataLayerStorageMap<VentilationStorageMap> {
        private int currentLowestY;
        private final Long2IntOpenHashMap topSections;

        private VentilationStorageMap(Long2ObjectOpenHashMap<DataLayer> pMap, Long2IntOpenHashMap pTopSections, int pCurrentLowestY) {
            super(pMap);
            this.topSections = pTopSections;
            pTopSections.defaultReturnValue(pCurrentLowestY);
            this.currentLowestY = pCurrentLowestY;
        }

        @Override
        public VentilationStorageMap copy() {
            return new VentilationStorageMap(this.map.clone(), this.topSections.clone(), this.currentLowestY);
        }
    }
}
