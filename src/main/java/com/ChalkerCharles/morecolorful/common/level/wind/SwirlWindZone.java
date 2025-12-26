package com.ChalkerCharles.morecolorful.common.level.wind;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class SwirlWindZone extends WindZone {
    public Vec3 origin;
    public float radius;
    public float height;
    private final float speed;

    public SwirlWindZone(Vec3 origin, float radius, float height, float speed) {
        super(false);
        this.origin = origin;
        this.radius = radius;
        this.height = height;
        this.speed = speed;
        this.bb = createBoundingBox();
        this.sections = this.getSections();
    }

    public SwirlWindZone(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVec3(), byteBuf.readFloat(), byteBuf.readFloat(), byteBuf.readFloat());
    }

    private AABB createBoundingBox() {
        Vec3 origin = this.origin;
        float radius = this.radius;
        double x1 = origin.x - radius, y1 = origin.y - 1, z1 = origin.z - radius;
        double x2 = origin.x + radius, y2 = origin.y + height - 1, z2 = origin.z + radius;
        return new AABB(x1, y1, z1, x2, y2, z2);
    }

    @Override
    public void addSpeedAt(Vector3f vec, double x, double y, double z) {
        double dx = x - origin.x, dz = z - origin.z;
        double dSq = dx * dx + dz * dz;
        if (dSq < 1.0E-04) return;
        double invD = 1.0 / Math.sqrt(dSq);
        double f = windSpeedMultiplier(invD, speed) * invD;
        vec.add((float) (-dz * f), 0, (float) (dx * f));
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
    protected void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeEnum(Type.SWIRL);
        byteBuf.writeVec3(this.origin);
        byteBuf.writeFloat(this.radius);
        byteBuf.writeFloat(this.height);
        byteBuf.writeFloat(this.speed);
    }

    public void setSizeAndPos(float radius, float height, Vec3 pos) {
        this.radius = radius;
        this.height = height;
        this.origin = pos;
        this.bb = createBoundingBox();
    }
}
