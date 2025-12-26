package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.util.AirBlocking;
import com.ChalkerCharles.morecolorful.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public final class LineWindZone extends WindZone {
    public final BlockPos start;
    public int length = 0;
    public final int speed;
    public final Direction direction;

    public LineWindZone(BlockPos start, Direction direction, int speed) {
        super(true);
        this.start = start;
        this.speed = speed;
        this.direction = direction;
        this.bb = createBoundingBox();
        this.sections = this.getSections();
    }

    public LineWindZone(FriendlyByteBuf byteBuf) {
        this(byteBuf.readBlockPos(), byteBuf.readEnum(Direction.class), byteBuf.readByte());
    }

    private AABB createBoundingBox() {
        int nx = direction.getStepX(), ny = direction.getStepY(), nz = direction.getStepZ();
        int sx = nx >>> 31, sy = ny >>> 31, sz = nz >>> 31;
        int x = start.getX(), y = start.getY(), z = start.getZ();
        int l = length - 1;
        int dx = x + 1 + nx * l, dy = y + 1 + ny * l, dz = z + 1 + nz * l;
        return new AABB(x + sx, y + sy, z + sz, dx - sx, dy - sy, dz - sz);
    }

    @Override
    public long[] getSections() {
        long min = SectionPos.asLong(this.start);
        if (length <= 1) return new long[]{min};
        int x = start.getX(), y = start.getY(), z = start.getZ();
        int l = length - 1;
        int dx = x + direction.getStepX() * l;
        int dy = y + direction.getStepY() * l;
        int dz = z + direction.getStepZ() * l;
        long max = SectionPos.asLong(dx >> 4, dy >> 4, dz >> 4);
        return min == max ? new long[]{min} : new long[]{min, max};
    }

    public void checkLength(Level level) {
        BlockPos.MutableBlockPos pos = this.start.mutable();
        Direction opposite = this.direction.getOpposite();
        BlockState state = level.getBlockState(pos);
        if (AirBlocking.getAirBlock(state, opposite) == 15) {
            this.resize(0);
            return;
        }
        for (int i = 1; i < this.speed; i++) {
            if (AirBlocking.getAirBlock(state, direction) == 15) {
                this.resize(i);
                return;
            }
            pos.move(direction);
            state = level.getBlockState(pos);
            if (AirBlocking.canBlockWind(level, pos, state, opposite)) {
                this.resize(i);
                return;
            }
        }
        this.resize(this.speed);
    }

    private void resize(int length) {
        if (this.length == length) return;
        this.length = length;
        this.bb = createBoundingBox();
        this.sections = this.getSections();
    }

    public void spawnWindParticle(Level level, BlockPos pos) {
        int i = direction.getStepX(), j = direction.getStepY(), k = direction.getStepZ();
        int i1 = i >>> 31, j1 = j >>> 31, k1 = k >>> 31;
        int x = pos.getX(), y = pos.getY(), z = pos.getZ();
        double speed = this.speed * 0.0625;
        double d0 = x + (i == 0 ? level.random.nextDouble() : 1 - i1 + 0.1 * i);
        double d1 = y + (j == 0 ? level.random.nextDouble() : 1 - j1 + 0.1 * j);
        double d2 = z + (k == 0 ? level.random.nextDouble() : 1 - k1 + 0.1 * k);
        double d3 = speed * i, d4 = speed * j, d5 = speed * k;
        level.addParticle(ModParticles.WIND_FAN.get(), d0, d1, d2, d3, d4, d5);
    }

    @Override
    public void addSpeedAt(Vector3f vec, double x, double y, double z) {
        Vec3i normal = this.direction.getNormal();
        int nx = normal.getX(), ny = normal.getY(), nz = normal.getZ();
        double d = (x - start.getX()) * nx + (y - start.getY()) * ny + (z - start.getZ()) * nz;
        float speed = this.speed * (1 - Maths.INV32 * (float) d);
        vec.add(nx * speed, ny * speed, nz * speed);
    }

    @Override
    public AABB getRenderBoundingBox() {
        int x = start.getX(), y = start.getY(), z = start.getZ();
        return this.bb.move(-x, -y, -z);
    }

    @Override
    public Vec3 getRenderOffset(Vec3 camera) {
        return Vec3.atLowerCornerWithOffset(this.start, -camera.x, -camera.y, -camera.z);
    }

    @Override
    protected void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeEnum(Type.LINE);
        byteBuf.writeBlockPos(this.start);
        byteBuf.writeEnum(this.direction);
        byteBuf.writeByte(this.speed);
    }
}
