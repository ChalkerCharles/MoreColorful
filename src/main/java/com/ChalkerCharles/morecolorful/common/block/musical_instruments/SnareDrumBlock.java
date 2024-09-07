package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SnareDrumBlock extends PercussionInstrumentBlock {

    private static final VoxelShape SNARE_DRUM = Shapes.or(
            Block.box(3.0, 10.0, 3.0, 13.0, 15.0, 13.0),
            Block.box(7.0, 9.0, 7.0, 9.0, 10.0, 9.0),
            Block.box(7.0, 3.4, 7.0, 9.0, 4.4, 9.0),
            Block.box(7.5, 4.4, 7.5, 8.5, 9.0, 8.5));

    public SnareDrumBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SNARE_DRUM;
    }
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
}
