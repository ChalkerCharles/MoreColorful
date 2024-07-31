package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class ChimesBlock extends PercussionInstrumentBlock{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape NORTH_SOUTH_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 16.0, 16.0, 9.0),
            Block.box(0.0, 13.0, 6.0, 16.0, 18.0, 10.0));
    private static final VoxelShape NORTH_SOUTH_LOWER_FRAME = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 1.0, 16.0, 9.0),
            Block.box(15.0, 0.0, 7.0, 16.0, 16.0, 9.0),
            Block.box(1.0, 1.9, 7.0, 15.0, 2.0, 9.0),
            Block.box(0.0, 13.0, 6.0, 16.0, 18.0, 10.0));
    private static final VoxelShape NORTH_LOWER_COLLISION = Shapes.or(
            Block.box(13.0, 3.0, 7.7, 15.0, 15.0, 8.3),
            Block.box(11.0, 5.0, 7.7, 13.0, 15.0, 8.3),
            Block.box(9.0, 7.0, 7.7, 11.0, 15.0, 8.3),
            Block.box(7.0, 9.0, 7.7, 9.0, 15.0, 8.3),
            Block.box(5.0, 11.0, 7.7, 7.0, 15.0, 8.3),
            Block.box(3.0, 13.0, 7.7, 5.0, 15.0, 8.3),
            NORTH_SOUTH_LOWER_FRAME);
    private static final VoxelShape SOUTH_LOWER_COLLISION = Shapes.or(
            Block.box(1.0, 3.0, 7.7, 3.0, 15.0, 8.3),
            Block.box(3.0, 5.0, 7.7, 5.0, 15.0, 8.3),
            Block.box(5.0, 7.0, 7.7, 7.0, 15.0, 8.3),
            Block.box(7.0, 9.0, 7.7, 9.0, 15.0, 8.3),
            Block.box(9.0, 11.0, 7.7, 11.0, 15.0, 8.3),
            Block.box(11.0, 13.0, 7.7, 13.0, 15.0, 8.3),
            NORTH_SOUTH_LOWER_FRAME);
    private static final VoxelShape NORTH_SOUTH_UPPER = Block.box(0.0, 0.0, 7.0, 16.0, 16.0, 9.0);
    private static final VoxelShape NORTH_SOUTH_UPPER_COLLISION = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 1.0, 16.0, 9.0),
            Block.box(15.0, 0.0, 7.0, 16.0, 16.0, 9.0),
            Block.box(1.0, 10.9, 7.0, 15.0, 11.0, 9.0),
            Block.box(1.0, 15.9, 7.0, 15.0, 16.0, 9.0),
            Block.box(1.0, -1.0, 7.7, 15.0, 11.0, 8.3),
            Block.box(2.0, 11.0, 7.9, 4.0, 16.0, 8.1),
            Block.box(6.0, 11.0, 7.9, 10.0, 16.0, 8.1),
            Block.box(12.0, 11.0, 7.9, 14.0, 16.0, 8.1));
    private static final VoxelShape WEST_EAST_LOWER = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 16.0),
            Block.box(6.0, 13.0, 0.0, 10.0, 18.0, 16.0));
    private static final VoxelShape WEST_EAST_LOWER_FRAME = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 1.0),
            Block.box(7.0, 0.0, 15.0, 9.0, 16.0, 16.0),
            Block.box(7.0, 1.9, 1.0, 9.0, 2.0, 15.0),
            Block.box(6.0, 13.0, 0.0, 10.0, 18.0, 16.0));
    private static final VoxelShape EAST_LOWER_COLLISION = Shapes.or(
            Block.box(7.7, 3.0, 13.0, 8.3, 15.0, 15.0),
            Block.box(7.7, 5.0, 11.0, 8.3, 15.0, 13.0),
            Block.box(7.7, 7.0, 9.0, 8.3, 15.0, 11.0),
            Block.box(7.7, 9.0, 7.0, 8.3, 15.0, 9.0),
            Block.box(7.7, 11.0, 5.0, 8.3, 15.0, 7.0),
            Block.box(7.7, 13.0, 3.0, 8.3, 15.0, 5.0),
            WEST_EAST_LOWER_FRAME);
    private static final VoxelShape WEST_LOWER_COLLISION = Shapes.or(
            Block.box(7.7, 3.0, 1.0, 8.3, 15.0, 3.0),
            Block.box(7.7, 5.0, 3.0, 8.3, 15.0, 5.0),
            Block.box(7.7, 7.0, 5.0, 8.3, 15.0, 7.0),
            Block.box(7.7, 9.0, 7.0, 8.3, 15.0, 9.0),
            Block.box(7.7, 11.0, 9.0, 8.3, 15.0, 11.0),
            Block.box(7.7, 13.0, 11.0, 8.3, 15.0, 13.0),
           WEST_EAST_LOWER_FRAME);
    private static final VoxelShape WEST_EAST_UPPER = Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 16.0);
    private static final VoxelShape WEST_EAST_UPPER_COLLISION = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 9.0, 16.0, 1.0),
            Block.box(7.0, 0.0, 15.0, 9.0, 16.0, 16.0),
            Block.box(7.0, 10.9, 1.0, 9.0, 11.0, 15.0),
            Block.box(7.0, 15.9, 1.0, 9.0, 16.0, 15.0),
            Block.box(7.7, -1.0, 1.0, 8.3, 11.0, 15.0),
            Block.box(7.9, 11.0, 2.0, 8.1, 16.0, 4.0),
            Block.box(7.9, 11.0, 6.0, 8.1, 16.0, 10.0),
            Block.box(7.9, 11.0, 12.0, 8.1, 16.0, 14.0));

    public ChimesBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == DoubleBlockHalf.LOWER;
        return switch (direction){
            case WEST -> flag ? WEST_LOWER_COLLISION : WEST_EAST_UPPER_COLLISION;
            case SOUTH -> flag ? SOUTH_LOWER_COLLISION : NORTH_SOUTH_UPPER_COLLISION;
            case EAST -> flag ? EAST_LOWER_COLLISION : WEST_EAST_UPPER_COLLISION;
            default -> flag ? NORTH_LOWER_COLLISION : NORTH_SOUTH_UPPER_COLLISION;
        };
    }
    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == DoubleBlockHalf.LOWER;
        return switch (direction){
            case WEST, EAST -> flag ? WEST_EAST_LOWER : WEST_EAST_UPPER;
            default -> flag ? NORTH_SOUTH_LOWER : NORTH_SOUTH_UPPER;
        };
    }
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }
    @Override
    protected @NotNull BlockState updateShape(BlockState pState, Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pFacing == Direction.UP)) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return pFacingState.getBlock() instanceof ChimesBlock && pFacingState.getValue(HALF) != doubleblockhalf
                    ? pFacingState.setValue(HALF, doubleblockhalf)
                    : Blocks.AIR.defaultBlockState();
        }
    }
    @Override
    public @NotNull BlockState playerWillDestroy(Level level, @NotNull BlockPos pos, BlockState state, @NotNull Player player) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        if (!level.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state, level, pos))) {
            if (doubleBlockHalf == DoubleBlockHalf.UPPER) {
                BlockPos blockpos = pos.below();
                BlockState blockstate = level.getBlockState(blockpos);
                if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                    BlockState blockstate1 = Blocks.AIR.defaultBlockState();
                    level.setBlock(blockpos, blockstate1, 35);
                    level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }
        return super.playerWillDestroy(level, pos, state, player);
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            return this.defaultBlockState()
                    .setValue(FACING, pContext.getHorizontalDirection().getOpposite())
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, @NotNull ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING);
    }
}
