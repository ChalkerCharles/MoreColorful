package com.ChalkerCharles.morecolorful.client.renderer.wavy;

import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.client.WindSectionMap;
import it.unimi.dsi.fastutil.floats.FloatArrayList;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;
import org.joml.Vector3i;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.system.MemoryUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@OnlyIn(Dist.CLIENT)
public abstract class WavyVertices {
    protected final BlockPos origin;
    private volatile List<WindZone> windZones;
    private final WindSectionMap map;
    protected volatile boolean closed = false;
    protected volatile int state = CLEARING;
    protected final AtomicBoolean addLock = new AtomicBoolean(false);
    private final Vector3i blockPos = new Vector3i();
    private final Vector3i sectionRelativePos = new Vector3i();
    private final Vector3f vector = new Vector3f();
    private static final int ACCESS = GL45C.GL_MAP_WRITE_BIT | GL45C.GL_MAP_UNSYNCHRONIZED_BIT;
    protected static final int CLEARING = 0, BUILDING = 1, ENCAPSULATED = 2, UPDATING = 3;

    protected WavyVertices(BlockPos origin, WindSectionMap map, List<WindZone> list) {
        this.origin = origin;
        this.map = map;
        this.windZones = list;
    }

    public abstract boolean isInvalid();

    public void close() {
        this.closed = true;
    }

    public void setWindZones(List<WindZone> list) {
        this.windZones = list;
    }

    protected boolean isBuilding() {
        return this.state < ENCAPSULATED;
    }

    public abstract void clear();

    public abstract void encapsulate();

    public abstract void computeData();

    protected void computeData(float anim, float time, float windX, float windZ,
                               float[] px, float[] dx, float[] py, float[] dy, float[] pz, float[] dz,
                               long[] dataArray, int size, Vector3f localWind) {
        for (int i = 0; i < size; i++) {
            long data = dataArray[i];
            if (data == 0L) continue;
            int type = unpackType(data);
            float x0 = px[i];
            float x = x0 * Mth.HALF_PI;
            float y0 = py[i];
            float y = y0 * Mth.HALF_PI;
            float z0 = pz[i];
            float z = z0 * Mth.HALF_PI;
            short packedPos = unpackPos(data);
            short height = unpackHeight(data);
            float invSum = unpackInvSum(data);
            this.setBlockPos(packedPos);
            int windy = this.isWindy(x0, y0, z0, type, packedPos, height);
            this.getLocalWind(localWind, x0, y0, z0, type);
            float speedX = localWind.x + windX * windy;
            float speedY = localWind.y;
            float speedZ = localWind.z + windZ * windy;
            float xs = 0, ys = 0, zs = 0;
            if (speedX != 0 || speedZ != 0 || speedY != 0) {
                int roundX = Math.round(speedX), roundZ = Math.round(speedZ);
                switch (type) {
                    case 1 -> { // Flowerbeds
                        xs = Mth.sin(x + y + anim * roundX) * speedX * Maths.INV8 + speedX * Maths.INV5;
                        zs = Mth.cos(z + y + anim * roundZ) * speedZ * Maths.INV8 + speedZ * Maths.INV5;
                    }
                    case 2 -> { // Vines
                        float windSpeed = Maths.length(speedX, speedZ);
                        Vector3f offset = this.accumulateOffset(anim, speedX, speedZ, windSpeed, invSum, height);
                        float halfY = y * 0.5F;
                        xs = Mth.sin(x + halfY + anim * roundX) * speedX * Maths.INV5 + offset.x;
                        ys = offset.y;
                        zs = Mth.cos(z + halfY + anim * roundZ) * speedZ * Maths.INV5 + offset.z;
                    }
                    case 3 -> { // Waterlilies and Duckweeds
                        float m0 = Maths.length(x0 - 8, z0 - 8) * 10F;
                        float windSpeed = Maths.length(speedX, speedZ);
                        ys = Mth.cos(m0 + anim * Math.round(windSpeed)) * 0.65F;
                        float tick = time * 300;
                        xs = Mth.sin(x + anim * roundX) * Mth.cos(tick);
                        zs = Mth.cos(z + anim * roundZ) * Mth.sin(tick);
                    }
                    case 4 -> { // Chains
                        float windSpeed = Maths.length(speedX, speedZ);
                        Vector3f offset = this.accumulateOffsetChain(anim, speedX, speedZ, windSpeed, invSum, height, sectionRelativePos, x0, y0, z0);
                        xs = offset.x;
                        ys = offset.y;
                        zs = offset.z;
                    }
                    case 5 -> { // Lanterns
                        float windSpeed = Maths.length(speedX, speedZ);
                        Vector3f offset = this.accumulateOffsetLantern(anim, speedX, speedZ, windSpeed, invSum, height, sectionRelativePos, x0, y0, z0);
                        xs = offset.x;
                        ys = offset.y;
                        zs = offset.z;
                    }
                    case 6 -> { // Hanging Plants (Cave Vines, Weeping Vines...)
                        float windSpeed = Maths.length(speedX, speedZ);
                        Vector3f offset = this.accumulateOffset(anim, speedX, speedZ, windSpeed, invSum, height);
                        xs = Mth.sin(x + y + anim * roundX) * speedX * Maths.INV5 + offset.x;
                        ys = offset.y;
                        zs = Mth.cos(z + y + anim * roundZ) * speedZ * Maths.INV5 + offset.z;
                    }
                    case 7 -> { // Leaves and Cobweb
                        float halfY = y * 0.5F;
                        xs = Mth.sin(x + halfY + anim * roundX) * speedX * Maths.INV5 + speedX * 0.5F;
                        ys = Mth.cos(y + halfY + anim * Math.round(speedY)) * speedY * Maths.INV5 + speedY * 0.5F;
                        zs = Mth.cos(z + halfY + anim * roundZ) * speedZ * Maths.INV5 + speedZ * 0.5F;
                    }
                    case 15 -> { // Water
                        if (height > 0) {
                            float m0 = Maths.length(x0 - 8, z0 - 8) * 10F;
                            float windSpeed = Maths.length(speedX, speedZ);
                            ys = Mth.cos(m0 + anim * Math.round(windSpeed)) * 0.65F;
                            if (height > 1) {
                                float tick = time * 300;
                                xs = Mth.sin(x + anim * roundX) * Mth.cos(tick);
                                zs = Mth.cos(z + anim * roundZ) * Mth.sin(tick);
                            }
                        }
                    }
                    default -> { // Others
                        float windSpeed = Maths.length(speedX, speedZ);
                        Vector3f offset = this.accumulateOffset(anim, speedX, speedZ, windSpeed, invSum, height);
                        xs = Mth.sin(x + y + anim * roundX) * speedX * Maths.INV5 + offset.x;
                        ys = -offset.y;
                        zs = Mth.cos(z + y + anim * roundZ) * speedZ * Maths.INV5 + offset.z;
                    }
                }
            }
            dx[i] = xs * Maths.INV32;
            dy[i] = ys * Maths.INV24;
            dz[i] = zs * Maths.INV32;
        }
    }

    private void setBlockPos(short packed) {
        int x = SectionPos.sectionRelativeX(packed);
        int y = SectionPos.sectionRelativeY(packed);
        int z = SectionPos.sectionRelativeZ(packed);
        this.blockPos.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
        this.sectionRelativePos.set(x, y, z);
    }

    private static int unpackType(long data) {
        return (int) ((data >> 12) & 15);
    }

    private static short unpackPos(long data) {
        return (short) (data & 4095);
    }

    private static short unpackHeight(long data) {
        return (short) ((data >> 16) & 65535);
    }

    private static float unpackInvSum(long data) {
        return Float.float16ToFloat((short) ((data >> 32) & 65535));
    }

    private static float getAngle(float tick, float speed, float windSpeed) {
        float f = Mth.sin(tick * Math.round(windSpeed) * 1.5F) * 0.5F + 0.5F;
        return speed * Maths.INV24 * Mth.lerp(f, 0.75F, 1.25F);
    }

    private Vector3f accumulateOffset(float tick, float speedX, float speedZ, float windSpeed, float invSum, short height) {
        float ratio, ratioSq;
        float angleX = getAngle(tick, speedX, windSpeed);
        float angleZ = getAngle(tick, speedZ, windSpeed);
        float angleXZ = Maths.length(angleX, angleZ);
        float xs = 0, ys = height, zs = 0;
        for (int i = 0; i < height; i++) {
            ratio = (i + 1F) * invSum;
            ratioSq = ratio * ratio;
            xs += Mth.sin(angleX * ratioSq);
            ys -= Mth.cos(angleXZ * ratioSq);
            zs += Mth.sin(angleZ * ratioSq);
        }
        return this.vector.set(xs, ys, zs).mul(24);
    }

    private Vector3f rotateAxis(float xs, float ys, float zs, float angle, float aX, float aZ, float x, float y, float z) {
        float hAngle = angle * 0.5f;
        float sinAngle = Mth.sin(hAngle);
        float qx = aX * sinAngle, qz = aZ * sinAngle;
        float qw = Mth.cos(hAngle);
        float w2 = qw * qw, x2 = qx * qx, z2 = qz * qz, zw = qz * qw;
        float xz = qx * qz, xw = qx * qw;
        float x0 = x, y0 = y, z0 = z;
        x = (w2 + x2 - z2) * x0 - 2 * zw * y0 + 2 * xz * z0;
        y = 2 * zw * x0 + (w2 - z2 - x2) * y0 - 2 * xw * z0;
        z = 2 * xz * x0 + 2 * xw * y0 + (z2 - x2 + w2) * z0;
        return this.vector.set(xs + x - x0, ys + y - y0, zs + z - z0);
    }

    private Vector3f accumulateOffsetLantern(float tick, float speedX, float speedZ, float windSpeed, float invSum, short height, Vector3i blockPos, float x, float y, float z) {
        float ratio, ratioSq;
        float angleX = getAngle(tick, speedX, windSpeed);
        float angleZ = getAngle(tick, speedZ, windSpeed);
        float angleXZ = Maths.length(angleX, angleZ);
        float xs = 0, ys = height, zs = 0;
        for (int i = 0; i < height; i++) {
            ratio = (i + 1F) * invSum;
            ratioSq = ratio * ratio;
            xs += Mth.sin(angleX * ratioSq);
            ys -= Mth.cos(angleXZ * ratioSq);
            zs += Mth.sin(angleZ * ratioSq);
        }
        float angle = getAngle(tick, windSpeed, windSpeed);
        float invWS = 1 / windSpeed;
        float nx = speedX * invWS, nz = speedZ * invWS;
        x -= blockPos.x + 0.5F;
        y -= blockPos.y + 1F;
        z -= blockPos.z + 0.5F;
        return this.rotateAxis(xs, ys, zs, angle, -nz, nx, x, y, z).mul(24);
    }

    private Vector3f accumulateOffsetChain(float tick, float speedX, float speedZ, float windSpeed, float invSum, short height, Vector3i blockPos, float x, float y, float z) {
        float ratio, ratioSq;
        float angleX = getAngle(tick, speedX, windSpeed);
        float angleZ = getAngle(tick, speedZ, windSpeed);
        float angleXZ = Maths.length(angleX, angleZ);
        float xs = 0, ys = height, zs = 0;
        for (int i = 0; i < height; i++) {
            ratio = (i + 1F) * invSum;
            ratioSq = ratio * ratio;
            xs += Mth.sin(angleX * ratioSq);
            ys -= Mth.cos(angleXZ * ratioSq);
            zs += Mth.sin(angleZ * ratioSq);
        }
        ratio = (height + 1F) * invSum;
        float angle = getAngle(tick, windSpeed, windSpeed) * ratio * ratio;
        float invWS = 1 / windSpeed;
        float nx = speedX * invWS, nz = speedZ * invWS;
        x -= blockPos.x + 0.5F;
        y -= blockPos.y + 1F;
        z -= blockPos.z + 0.5F;
        return this.rotateAxis(xs, ys, zs, angle, -nz, nx, x, y, z).mul(24);
    }

    private int isWindy(float x, float y, float z, int type, short packedPos, int height) {
        int px = blockPos.x, py = blockPos.y, pz = blockPos.z;
        return switch (type) {
            case 1, 3, 7, 8, 9, 10, 11 -> map.get(packedPos,
                    () -> WeatherUtils.canApplyWindI(Minecraft.getInstance().level, px, py, pz));
            case 2, 4, 5, 6 -> {
                int y1 = Math.round(y) + height - 1;
                int y2 = origin.getY() + y1;
                yield map.get(packedPos,
                        () -> WeatherUtils.canApplyWindI(Minecraft.getInstance().level, px, y2, pz)
                );
            }
            case 15 -> {
                int x1 = Math.round(x), y0 = Mth.floor(y), y1 = Math.round(y), z1 = Math.round(z);
                int idx = 272 * x1 + (z1 << 4) + y0 + 4096;
                int x2 = origin.getX() + x1, z2 = origin.getZ() + z1;
                double y2 = origin.getY() + y1 + 0.5;
                yield map.get(idx,
                        () -> WeatherUtils.canApplyWindI(Minecraft.getInstance().level, x2, y2, z2, px, py + 1, pz)
                );
            }
            default -> {
                int y1 = Math.round(y) - height;
                int y2 = origin.getY() + y1;
                yield map.get(packedPos,
                        () -> WeatherUtils.canApplyWindI(Minecraft.getInstance().level, px, y2, pz)
                );
            }
        };
    }

    private void getLocalWind(Vector3f vec, float x, float y, float z, int type) {
        vec.zero();
        if (windZones.isEmpty()) return;
        double x1, y1, z1;
        if (type == 15) {
            x1 = Math.round(x) + origin.getX();
            y1 = Math.round(y) + origin.getY() + 0.5;
            z1 = Math.round(z) + origin.getZ();
        } else {
            x1 = blockPos.x + 0.5;
            y1 = blockPos.y + 0.5;
            z1 = blockPos.z + 0.5;
        }
        for (WindZone w : windZones) {
            if (w.contains(x1, y1, z1)) w.addSpeedAt(vec, x1, y1, z1);
        }
    }

    public static class Default extends WavyVertices {
        private final int id;
        private volatile int length;
        private volatile int size;
        private final IntArrayList indexList = new IntArrayList(128);
        private final LongArrayList dataList = new LongArrayList(128);
        private final FloatArrayList posXList = new FloatArrayList(128);
        private final FloatArrayList posYList = new FloatArrayList(128);
        private final FloatArrayList posZList = new FloatArrayList(128);
        private volatile int[] index;
        private volatile long[] data;
        private volatile float[] posX, posY, posZ;
        private volatile float[] dX, dY, dZ;

        protected Default(BlockPos origin, WindSectionMap map, List<WindZone> list, int id) {
            super(origin, map, list);
            this.id = id;
        }

        public void setLength(int length) {
            this.length = length;
        }

        @Override
        public boolean isInvalid() {
            return this.size == 0 || this.closed || this.isBuilding();
        }

        @Override
        public void clear() {
            this.state = CLEARING;
            this.size = 0;
            this.indexList.clear();
            this.dataList.clear();
            this.posXList.clear();
            this.posYList.clear();
            this.posZList.clear();
            this.state = BUILDING;
        }

        public void addVertex(int index, long data, float x, float y, float z) {
            if (this.state != BUILDING) return;
            if (!this.addLock.compareAndSet(false, true)) return;
            this.indexList.add(index);
            this.dataList.add(data);
            this.posXList.add(x);
            this.posYList.add(y);
            this.posZList.add(z);
            this.addLock.set(false);
        }

        @Override
        public void encapsulate() {
            if (this.state != BUILDING) return;
            int size = this.size = this.indexList.size();
            this.index = this.indexList.toIntArray();
            this.data = this.dataList.toLongArray();
            this.posX = this.posXList.toFloatArray();
            this.posY = this.posYList.toFloatArray();
            this.posZ = this.posZList.toFloatArray();
            this.dX = new float[size];
            this.dY = new float[size];
            this.dZ = new float[size];
            this.state = ENCAPSULATED;
        }

        @Override
        public void computeData() {
            if (this.isBuilding()) return;
            this.computeData(RenderUtils.anim, RenderUtils.time, RenderUtils.WIND_SPEED.x, RenderUtils.WIND_SPEED.y,
                    this.posX, this.dX, this.posY, this.dY, this.posZ, this.dZ, this.data, this.size, new Vector3f());
            this.state = UPDATING;
        }

        public void update() {
            if (this.state != UPDATING) return;
            long address = GL45C.nglMapNamedBufferRange(id, 0, length, ACCESS);
            if (address == 0L) return;
            int[] idx = this.index;
            float[] px = posX, dx = dX, py = posY, dy = dY, pz = posZ, dz = dZ;
            for (int i = 0, j = this.size; i < j; i++) {
                long l = address + idx[i];
                MemoryUtil.memPutFloat(l, px[i] + dx[i]);
                MemoryUtil.memPutFloat(l + 4L, py[i] + dy[i]);
                MemoryUtil.memPutFloat(l + 8L, pz[i] + dz[i]);
            }
            GL45C.glUnmapNamedBuffer(this.id);
        }
    }
}
