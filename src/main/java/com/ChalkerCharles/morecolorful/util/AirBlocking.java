package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AirBlocking {
    public static final int FULL_BLOCK = 15;
    public static final int EMPTY = 0;
    public static final int SEMI_BLOCK = 1;
    public static final int ONLY_Y = 0x800000ff;
    public static final int EXCEPT_Y = 0x80ffff00;
    public static final int ONLY_X = 0x80ff0000;
    public static final int ONLY_Z = 0x8000ff00;

    public static int getAirBlock(BlockState state, Direction direction) {
        int airBlock = IBlockStateExtension.getAirBlock(state);
        if (airBlock < 0) {
            int i = direction.get3DDataValue() << 2;
            return (airBlock >> i) & 15;
        } else {
            return airBlock;
        }
    }

    public static int makeAirBlock(BlockState state) {
        int airBlock = 0x80000000;
        for (Direction direction : Maths.DIRECTIONS) {
            if (Block.isFaceFull(state.getCollisionShape(EmptyBlockGetter.INSTANCE, BlockPos.ZERO), direction)) {
                int i = direction.get3DDataValue() << 2;
                airBlock |= 15 << i;
            }
        }
        return airBlock;
    }

    public static boolean isMergedFaceFull(BlockGetter level, BlockPos pos, BlockState state, Direction direction) {
        BlockPos pos1 = pos.relative(direction);
        BlockState state1 = level.getBlockState(pos1);
        VoxelShape shape = LightEngine.getOcclusionShape(level, pos, state, direction);
        VoxelShape shape1 = LightEngine.getOcclusionShape(level, pos1, state1, direction.getOpposite());
        return Shapes.faceShapeOccludes(shape, shape1);
    }

    public static boolean canBlockWind(BlockGetter level, BlockPos pos, BlockState state, Direction direction) {
        if (getAirBlock(state, direction) == FULL_BLOCK) {
            return true;
        } else if (state.useShapeForLightOcclusion()) {
            return isMergedFaceFull(level, pos, state, direction);
        }
        return false;
    }

    public static int getAirBlock(Level level, int x, int y, int z) {
        BlockState state = LevelSavedData.getBlockState(level, x, y, z);
        Direction direction = level.isClientSide ? RenderUtils.nearestWindDirection : LevelSavedData.getNearestWindDirection(level);
        return getAirBlock(state, direction);
    }
}
