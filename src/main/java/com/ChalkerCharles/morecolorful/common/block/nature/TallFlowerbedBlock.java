package com.ChalkerCharles.morecolorful.common.block.nature;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

public class TallFlowerbedBlock extends PinkPetalsBlock {
    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = Util.memoize(
            (direction, integer) -> {
                VoxelShape[] avoxelshape = new VoxelShape[]{
                        Block.box(8.0, 0.0, 8.0, 16.0, 5.0, 16.0),
                        Block.box(8.0, 0.0, 0.0, 16.0, 5.0, 8.0),
                        Block.box(0.0, 0.0, 0.0, 8.0, 5.0, 8.0),
                        Block.box(0.0, 0.0, 8.0, 8.0, 5.0, 16.0)
                };
                VoxelShape voxelshape = Shapes.empty();

                for (int i = 0; i < integer; i++) {
                    int j = Math.floorMod(i - direction.get2DDataValue(), 4);
                    voxelshape = Shapes.or(voxelshape, avoxelshape[j]);
                }

                return voxelshape.singleEncompassing();
            }
    );
    public TallFlowerbedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_PROPERTIES.apply(pState.getValue(FACING), pState.getValue(AMOUNT));
    }
}
