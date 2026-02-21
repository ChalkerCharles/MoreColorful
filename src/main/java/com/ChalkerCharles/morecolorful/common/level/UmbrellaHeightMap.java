package com.ChalkerCharles.morecolorful.common.level;

import it.unimi.dsi.fastutil.longs.Long2DoubleMap;
import it.unimi.dsi.fastutil.longs.Long2DoubleOpenHashMap;
import net.minecraft.world.level.ChunkPos;

public class UmbrellaHeightMap {
    private final Long2DoubleMap canopies = new Long2DoubleOpenHashMap();
    private final Long2DoubleMap canopiesToAdd = new Long2DoubleOpenHashMap();

    public UmbrellaHeightMap() {
        this.canopies.defaultReturnValue(Double.NaN);
    }

    public void setCanopy(int x, double y, int z) {
        long key = ChunkPos.asLong(x, z);
        this.canopiesToAdd.merge(key, y, Math::max);
    }

    public double getCanopy(int x, int z) {
        return this.canopies.get(ChunkPos.asLong(x, z));
    }

    public void tick() {
        this.canopies.clear();
        this.canopies.putAll(this.canopiesToAdd);
        this.canopiesToAdd.clear();
    }
}
