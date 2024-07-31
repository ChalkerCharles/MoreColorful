package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class GlockenspielBlock extends PercussionInstrumentBlock{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    private static final VoxelShape NORTH_SOUTH = Shapes.or(
            Block.box(0.0, 13.0, 2.0, 16.0, 16.0, 14.0),
            Block.box(0.0, 0.0, 4.0, 16.0, 13.0, 12.0));
    private static final VoxelShape NORTH_COLLISION = Shapes.or(
            Block.box(0.0, 13.0, 5.0, 16.0, 15.0, 14.0),
            Block.box(6.0, 13.0, 4.0, 16.0, 15.0, 5.0),
            Block.box(10.0, 13.0, 3.0, 16.0, 15.0, 4.0),
            Block.box(13.0, 13.0, 2.0, 16.0, 15.0, 3.0),
            Block.box(2.0, 15.0, 11.0, 4.0, 16.0, 14.0),
            Block.box(6.0, 15.0, 10.0, 10.0, 16.0, 14.0),
            Block.box(12.0, 15.0, 9.0, 14.0, 16.0, 14.0));
    private static final VoxelShape SOUTH_COLLISION = Shapes.or(
            Block.box(0.0, 13.0, 2.0, 16.0, 15.0, 11.0),
            Block.box(0.0, 13.0, 11.0, 10.0, 15.0, 12.0),
            Block.box(0.0, 13.0, 12.0, 6.0, 15.0, 13.0),
            Block.box(0.0, 13.0, 13.0, 3.0, 15.0, 14.0),
            Block.box(2.0, 15.0, 2.0, 4.0, 16.0, 7.0),
            Block.box(6.0, 15.0, 2.0, 10.0, 16.0, 6.0),
            Block.box(12.0, 15.0, 2.0, 14.0, 16.0, 5.0));
    private static final VoxelShape WEST_EAST = Shapes.or(
            Block.box(2.0, 13.0, 0.0, 14.0, 16.0, 16.0),
            Block.box(4.0, 0.0, 0.0, 12.0, 13.0, 16.0));
    private static final VoxelShape WEST_COLLISION = Shapes.or(
            Block.box(5.0, 13.0, 0.0, 14.0, 15.0, 16.0),
            Block.box(4.0, 13.0, 0.0, 5.0, 15.0, 10.0),
            Block.box(3.0, 13.0, 0.0, 4.0, 15.0, 6.0),
            Block.box(2.0, 13.0, 0.0, 3.0, 15.0, 3.0),
            Block.box(11.0, 15.0, 12.0, 14.0, 16.0, 14.0),
            Block.box(10.0, 15.0, 6.0, 14.0, 16.0, 10.0),
            Block.box(9.0, 15.0, 2.0, 14.0, 16.0, 4.0));
    private static final VoxelShape EAST_COLLISION = Shapes.or(
            Block.box(2.0, 13.0, 0.0, 11.0, 15.0, 16.0),
            Block.box(11.0, 13.0, 6.0, 12.0, 15.0, 16.0),
            Block.box(12.0, 13.0, 10.0, 13.0, 15.0, 16.0),
            Block.box(13.0, 13.0, 13.0, 14.0, 15.0, 16.0),
            Block.box(2.0, 15.0, 2.0, 5.0, 16.0, 4.0),
            Block.box(2.0, 15.0, 6.0, 6.0, 16.0, 10.0),
            Block.box(2.0, 15.0, 12.0, 7.0, 16.0, 14.0));

    public GlockenspielBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case WEST -> WEST_COLLISION;
            case SOUTH -> SOUTH_COLLISION;
            case EAST -> EAST_COLLISION;
            default -> NORTH_COLLISION;
        };
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case WEST, EAST -> WEST_EAST;
            default -> NORTH_SOUTH;
        };
    }
    @Override
    protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        return Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }
}
