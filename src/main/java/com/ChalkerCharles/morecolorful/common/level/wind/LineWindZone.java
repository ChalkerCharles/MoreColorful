package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.util.AirBlocking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;

public final class LineWindZone extends WindZone {
    public final BlockPos start;
    public final BlockPos.MutableBlockPos end = new BlockPos.MutableBlockPos();
    public int length;
    public int speed;
    public final Direction direction;

    public LineWindZone(BlockPos start, Direction direction, int length, int speed) {
        super(true);
        this.start = start;
        this.speed = speed;
        this.length = length;
        this.direction = direction;
        this.bb = createBoundingBox();
        this.sections = this.getSections();
    }

    public LineWindZone(FriendlyByteBuf byteBuf) {
        this(byteBuf.readBlockPos(), byteBuf.readEnum(Direction.class), byteBuf.readByte(), byteBuf.readByte());
    }

    private AABB createBoundingBox() {
        this.end.set(this.start.relative(direction, length));
        return AABB.encapsulatingFullBlocks(start, end);
    }

    @Override
    public long[] getSections() {
        AABB bb = this.bb;
        long min = SectionPos.asLong(Mth.floor(bb.minX) >> 4, Mth.floor(bb.minY) >> 4, Mth.floor(bb.minZ) >> 4);
        long max = SectionPos.asLong(Mth.floor(bb.maxX) >> 4, Mth.floor(bb.maxY) >> 4, Mth.floor(bb.maxZ) >> 4);
        return min == max ? new long[]{min} : new long[]{min, max};
    }

    public void checkLength(Level level) {
        BlockPos.MutableBlockPos pos = this.start.mutable();
        for (int i = 0, l = this.speed; i < l; i++) {
            if (AirBlocking.canBlockWind(level, pos, level.getBlockState(pos), direction)) {
                this.length = i;
                this.bb = createBoundingBox();
                this.sections = this.getSections();
                return;
            }
            pos.move(direction);
        }
    }

    @Override
    public void addSpeedAt(Vector3f vec, double x, double y, double z) {
        Vec3i normal = this.direction.getNormal();
        int speed = this.speed;
        vec.add(normal.getX() * speed, normal.getY() * speed, normal.getZ() * speed);
    }

    @Override
    protected void encode(FriendlyByteBuf byteBuf) {
        byteBuf.writeEnum(Type.LINE);
        byteBuf.writeBlockPos(this.start);
        byteBuf.writeEnum(this.direction);
        byteBuf.writeByte(this.length);
        byteBuf.writeByte(this.speed);
    }
}
