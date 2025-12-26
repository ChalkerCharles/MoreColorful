package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public abstract class WindZone {
    protected boolean removed;
    protected final boolean affectEntity;
    protected AABB bb;
    public long[] sections;

    public static final StreamCodec<FriendlyByteBuf, WindZone> STREAM_CODEC = StreamCodec.ofMember(
            WindZone::encode, WindZone::decode
    );

    protected WindZone(boolean affectEntity) {
        this.affectEntity = affectEntity;
    }

    public abstract void addSpeedAt(Vector3f vec, double x, double y, double z);

    public abstract AABB getRenderBoundingBox();

    public abstract Vec3 getRenderOffset(Vec3 camera);

    public long[] getSections() {
        AABB bb = this.bb;
        int x1 = Mth.floor(bb.minX) >> 4, y1 = Mth.floor(bb.minY) >> 4, z1 = Mth.floor(bb.minZ) >> 4;
        int x2 = Mth.floor(bb.maxX) >> 4, y2 = Mth.floor(bb.maxY) >> 4, z2 = Mth.floor(bb.maxZ) >> 4;
        int size = (x2 - x1 + 1) * (y2 - y1 + 1) * (z2 - z1 + 1);
        long[] array = new long[size];
        int idx = 0;
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    array[idx] = SectionPos.asLong(i, j, k);
                    idx++;
                }
            }
        }
        return array;
    }

    public boolean contains(double x, double y, double z) {
        return this.bb.contains(x, y, z);
    }

    public void tick() {}

    public void remove() {
        this.removed = true;
    }

    public boolean isAlive() {
        return !this.removed;
    }

    public static double windSpeedMultiplier(double invDistance, float speed) {
        return Math.min(speed * invDistance, speed);
    }

    protected abstract void encode(FriendlyByteBuf byteBuf);

    private static WindZone decode(FriendlyByteBuf byteBuf) {
        Type type = byteBuf.readEnum(Type.class);
        return switch (type) {
            case LINE -> new LineWindZone(byteBuf);
            case SWIRL -> new SwirlWindZone(byteBuf);
            case BURST -> new BurstWindZone(byteBuf);
        };
    }

    protected enum Type {
        LINE,
        SWIRL,
        BURST
    }
}
