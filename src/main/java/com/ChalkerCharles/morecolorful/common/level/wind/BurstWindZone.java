package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class BurstWindZone extends WindZone {
    public final Vec3 origin;
    private final float radius;
    private float speed;
    private int lifetime;

    public BurstWindZone(Vec3 origin, float radius, float speed, int lifetime) {
        super(false);
        this.origin = origin;
        this.radius = radius;
        this.speed = speed;
        this.lifetime = lifetime;
        this.bb = createBoundingBox();
        this.sections = this.getSections();
    }

    public BurstWindZone(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVec3(), byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readVarInt());
    }

    private AABB createBoundingBox() {
        Vec3 origin = this.origin;
        float radius = this.radius;
        double x1 = origin.x - radius, y1 = origin.y - radius, z1 = origin.z - radius;
        double x2 = origin.x + radius, y2 = origin.y + radius, z2 = origin.z + radius;
        return new AABB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void addSpeedAt(Vector3f vec, double x, double y, double z) {
        double dx = x - origin.x, dy = y - origin.y, dz = z - origin.z;
        double dSq = dx * dx + dy * dy + dz * dz;
        if (dSq < 1.0E-04) {
            vec.add(0, speed, 0);
        } else {
            double invD = 1.0 / Math.sqrt(dSq);
            double f = windSpeedMultiplier(invD, speed) * invD;
            vec.add((float) (dx * f), (float) (dy * f), (float) (dz * f));
        }
    }

    @Override
    public AABB getRenderBoundingBox() {
        return this.bb.move(-origin.x, -origin.y, -origin.z);
    }

    @Override
    public Vec3 getRenderOffset(Vec3 camera) {
        return this.origin.subtract(camera);
    }

    @Override
    public void tick() {
        if (removed) return;
        this.lifetime--;
        this.speed = Math.max(0, this.speed - 1);
        if (lifetime <= 0) {
            this.remove();
        }
    }

    @Override
    protected void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeEnum(Type.BURST);
        byteBuf.writeVec3(this.origin);
        byteBuf.writeFloat(this.radius);
        byteBuf.writeFloat(this.speed);
        byteBuf.writeVarInt(this.lifetime);
    }
}
