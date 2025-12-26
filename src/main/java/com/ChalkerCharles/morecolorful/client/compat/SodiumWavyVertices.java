package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyVertices;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.client.WindSectionMap;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.data.SectionRenderDataUnsafe;
import net.caffeinemc.mods.sodium.client.render.chunk.vertex.format.impl.CompactChunkVertex;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SodiumWavyVertices extends WavyVertices {
    protected volatile boolean invalid;
    private volatile boolean forceUnassigned;
    private final int[] size = new int[COUNT];
    private final IntList[] indexList = new IntArrayList[COUNT];
    private final LongList[] dataList = new LongArrayList[COUNT];
    private final FloatList[] posXList = new FloatArrayList[COUNT];
    private final FloatList[] posYList = new FloatArrayList[COUNT];
    private final FloatList[] posZList = new FloatArrayList[COUNT];
    private final int[][] index = new int[COUNT][];
    private final long[][] data = new long[COUNT][];
    private final float[][] posX  = new float[COUNT][], posY  = new float[COUNT][], posZ = new float[COUNT][];
    private final float[][] dX = new float[COUNT][], dY = new float[COUNT][], dZ = new float[COUNT][];
    private static final int COUNT = ModelQuadFacing.COUNT;

    public SodiumWavyVertices(BlockPos origin, WindSectionMap map, List<WindZone> list) {
        super(origin, map, list);
        for (int i = 0; i < COUNT; i++) {
            this.indexList[i] = new IntArrayList();
            this.dataList[i] = new LongArrayList();
            this.posXList[i] = new FloatArrayList();
            this.posYList[i] = new FloatArrayList();
            this.posZList[i] = new FloatArrayList();
            this.fillEmptyData(i);
        }
    }

    public void forceUnassigned() {
        this.forceUnassigned = true;
    }

    @Override
    public boolean isInvalid() {
        if (this.invalid || this.closed) return true;
        for (int i : size) {
            if (i != 0) return false;
        }
        return true;
    }

    @Override
    public void clear() {
        this.forceUnassigned = false;
        for (int i = 0; i < COUNT; i++) {
            this.indexList[i].clear();
            this.dataList[i].clear();
            this.posXList[i].clear();
            this.posYList[i].clear();
            this.posZList[i].clear();
        }
    }

    public void addVertex(ModelQuadFacing facing, int index, long data, float x, float y, float z) {
        try {
            int i = facing.ordinal();
            this.indexList[i].add(index);
            this.dataList[i].add(data);
            this.posXList[i].add(x);
            this.posYList[i].add(y);
            this.posZList[i].add(z);
        } catch (Exception ignored) {}
    }

    @Override
    public void encapsulate() {
        if (this.forceUnassigned) {
            this.merge();
        } else {
            for (int i = 0; i < COUNT; i++) {
                int size0 = this.size[i];
                int size = this.size[i] = this.indexList[i].size();
                if (size == 0) {
                    this.fillEmptyData(i);
                    continue;
                }
                this.index[i] = this.indexList[i].toIntArray();
                this.data[i] = this.dataList[i].toLongArray();
                this.posX[i] = this.posXList[i].toFloatArray();
                this.posY[i] = this.posYList[i].toFloatArray();
                this.posZ[i] = this.posZList[i].toFloatArray();
                if (size0 != size) {
                    this.dX[i] = new float[size];
                    this.dY[i] = new float[size];
                    this.dZ[i] = new float[size];
                }
            }
        }
    }

    private void merge() {
        int total = 0;
        int j = ModelQuadFacing.UNASSIGNED.ordinal();
        for (int i = 0; i < j; i++) {
            this.size[i] = 0;
            this.fillEmptyData(i);
            int size = this.indexList[i].size();
            total += size;
        }
        total += this.indexList[j].size();
        int total0 = this.size[j];
        this.size[j] = total;
        int[] index = new int[total];
        long[] data = new long[total];
        float[] posX = new float[total];
        float[] posY = new float[total];
        float[] posZ = new float[total];
        int offset = 0;
        for (int i = 0; i < COUNT; i++) {
            int size = this.indexList[i].size();
            if (size == 0) continue;
            this.indexList[i].getElements(0, index, offset, size);
            this.dataList[i].getElements(0, data, offset, size);
            this.posXList[i].getElements(0, posX, offset, size);
            this.posYList[i].getElements(0, posY, offset, size);
            this.posZList[i].getElements(0, posZ, offset, size);
            shiftIndex(index, offset, size);
            offset += size;
        }
        this.index[j] = index;
        this.data[j] = data;
        this.posX[j] = posX;
        this.posY[j] = posY;
        this.posZ[j] = posZ;
        if (total0 != total) {
            this.dX[j] = new float[total];
            this.dY[j] = new float[total];
            this.dZ[j] = new float[total];
        }
    }

    private void fillEmptyData(int i) {
        this.index[i] = Maths.EMPTY_INT_ARRAY;
        this.data[i] = Maths.EMPTY_LONG_ARRAY;
        this.posX[i] = this.posY[i] = this.posZ[i] = Maths.EMPTY_FLOAT_ARRAY;
        this.dX[i] = this.dY[i] = this.dZ[i] = Maths.EMPTY_FLOAT_ARRAY;
    }

    @Override
    public void computeData() {
        float anim = RenderUtils.anim, time = RenderUtils.time, windX = RenderUtils.windSpeed.x, windZ = RenderUtils.windSpeed.y;
        Vector3f localWind = new Vector3f();
        for (int i = 0; i < COUNT; i++) {
            this.computeData(anim, time, windX, windZ,
                    this.posX[i], this.dX[i], this.posY[i], this.dY[i], this.posZ[i], this.dZ[i],
                    this.data[i], this.size[i], localWind);
        }
    }

    public void update(long address, long pMeshData, int slice) {
        if (this.invalid) return;
        for (int i = 0; i < COUNT; i++) {
            if (((slice >> i) & 1) == 0) continue;
            long offset = SectionRenderDataUnsafe.getVertexOffset(pMeshData, i) * CompactChunkVertex.STRIDE;
            update(address + offset, index[i], posX[i], dX[i], posY[i], dY[i], posZ[i], dZ[i], size[i]);
        }
    }

    private static void update(long address, int[] idx, float[] px, float[] dx, float[] py, float[] dy, float[] pz, float[] dz, int size) {
        for (int i = 0; i < size; i++) {
            long l = address + idx[i];
            int x = quantizePosition(px[i] + dx[i]);
            int y = quantizePosition(py[i] + dy[i]);
            int z = quantizePosition(pz[i] + dz[i]);
            MemoryUtil.memPutInt(l, packPositionHi(x, y, z));
            MemoryUtil.memPutInt(l + 4L, packPositionLo(x, y, z));
        }
    }

    private static int packPositionHi(int x, int y, int z) {
        return (x >>> 10 & 1023) | (y >>> 10 & 1023) << 10 | (z >>> 10 & 1023) << 20;
    }

    private static int packPositionLo(int x, int y, int z) {
        return (x & 1023) | (y & 1023) << 10 | (z & 1023) << 20;
    }

    private static int quantizePosition(float position) {
        return (int)(normalizePosition(position) * 1048576.0F) & 1048575;
    }

    private static float normalizePosition(float v) {
        return (8.0F + v) / 32.0F;
    }

    private static void shiftIndex(int[] index, int offset, int size) {
        if (offset == 0) return;
        int x = offset * CompactChunkVertex.STRIDE;
        for (int i = offset, limit = offset + size; i < limit; i++) {
            index[i] += x;
        }
    }
}
