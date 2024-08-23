package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
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
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class XylophoneBlock extends PercussionInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<HorizontalDoubleBlockHalf> HALF = ModBlockStateProperties.HORIZONTAL_HALF;
    protected static final VoxelShape NORTH_LEFT_BASE = Shapes.or(
            Block.box(-2.0, 12.0, 2.0, 16.0, 16.0, 16.0),
            Block.box(6.0, 12.0, 0.0, 16.0, 16.0, 2.0),
            Block.box(-2.0, 16.0, 8.0, 13.0, 17.0, 16.0));
    protected static final VoxelShape NORTH_LEFT = Shapes.or(
            Block.box(14.0, 0.0, 1.0, 16.0, 12.0, 15.0),
            Block.box(0.0, 0.0, 8.0, 14.0, 12.0, 12.0),
            NORTH_LEFT_BASE);
    protected static final VoxelShape NORTH_LEFT_COLLISION = Shapes.or(
            Block.box(14.0, 0.0, 1.0, 16.0, 1.0, 15.0),
            Block.box(14.0, 1.0, 2.0, 16.0, 12.0, 3.0),
            Block.box(14.0, 1.0, 13.0, 16.0, 12.0, 14.0),
            Block.box(0.0, 0.0, 8.0, 14.0, 1.0, 10.0),
            Block.box(7.0, 7.0, 10.5, 14.0, 12.0, 11.5),
            Block.box(0.0, 9.0, 10.5, 7.0, 12.0, 11.5),
            NORTH_LEFT_BASE);
    protected static final VoxelShape NORTH_RIGHT_BASE = Shapes.or(
            Block.box(0.0, 12.0, 4.0, 14.0, 16.0, 16.0),
            Block.box(3.0, 16.0, 10.0, 13.0, 17.0, 16.0));
    protected static final VoxelShape NORTH_RIGHT = Shapes.or(
            Block.box(0.0, 0.0, 6.0, 2.0, 12.0, 15.0),
            Block.box(2.0, 0.0, 8.0, 16.0, 12.0, 12.0),
            NORTH_RIGHT_BASE);
    protected static final VoxelShape NORTH_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 1.0, 8.0, 2.0, 12.0, 9.0),
            Block.box(0.0, 1.0, 13.0, 2.0, 12.0, 14.0),
            Block.box(0.0, 0.0, 6.0, 2.0, 1.0, 15.0),
            Block.box(2.0, 0.0, 8.0, 16.0, 1.0, 10.0),
            Block.box(9.0, 11.0, 10.5, 16.0, 12.0, 11.5),
            NORTH_RIGHT_BASE);
    protected static final VoxelShape EAST_LEFT_BASE = Shapes.or(
            Block.box(0.0, 12.0, -2.0, 14.0, 16.0, 16.0),
            Block.box(14.0, 12.0, 6.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 16.0, -2.0, 8.0, 17.0, 13.0));
    protected static final VoxelShape EAST_LEFT = Shapes.or(
            Block.box(1.0, 0.0, 14.0, 15.0, 12.0, 16.0),
            Block.box(4.0, 0.0, 0.0, 8.0, 12.0, 14.0),
            EAST_LEFT_BASE);
    protected static final VoxelShape EAST_LEFT_COLLISION = Shapes.or(
            Block.box(1.0, 0.0, 14.0, 15.0, 1.0, 16.0),
            Block.box(2.0, 1.0, 14.0, 3.0, 12.0, 16.0),
            Block.box(13.0, 1.0, 14.0, 14.0, 12.0, 16.0),
            Block.box(6.0, 0.0, 0.0, 8.0, 1.0, 14.0),
            Block.box(4.5, 9.0, 0.0, 5.5, 12.0, 7.0),
            Block.box(4.5, 7.0, 7.0, 5.5, 12.0, 14.0),
            EAST_LEFT_BASE);
    protected static final VoxelShape EAST_RIGHT_BASE = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 12.0, 16.0, 14.0),
            Block.box(0.0, 16.0, 3.0, 6.0, 17.0, 13.0));
    protected static final VoxelShape EAST_RIGHT = Shapes.or(
            Block.box(1.0, 0.0, 0.0, 10.0, 12.0, 2.0),
            Block.box(4.0, 0.0, 2.0, 8.0, 12.0, 16.0),
            EAST_RIGHT_BASE);
    protected static final VoxelShape EAST_RIGHT_COLLISION = Shapes.or(
            Block.box(1.0, 0.0, 0.0, 10.0, 1.0, 2.0),
            Block.box(2.0, 1.0, 0.0, 3.0, 12.0, 2.0),
            Block.box(7.0, 1.0, 0.0, 8.0, 12.0, 2.0),
            Block.box(6.0, 0.0, 2.0, 8.0, 1.0, 16.0),
            Block.box(4.5, 11.0, 9.0, 5.5, 12.0, 16.0),
            EAST_RIGHT_BASE);
    protected static final VoxelShape SOUTH_LEFT_BASE = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 18.0, 16.0, 14.0),
            Block.box(0.0, 12.0, 14.0, 10.0, 16.0, 16.0),
            Block.box(3.0, 16.0, 0.0, 18.0, 17.0, 8.0));
    protected static final VoxelShape SOUTH_LEFT = Shapes.or(
            Block.box(0.0, 0.0, 1.0, 2.0, 12.0, 15.0),
            Block.box(2.0, 0.0, 4.0, 16.0, 12.0, 8.0),
            SOUTH_LEFT_BASE);
    protected static final VoxelShape SOUTH_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 0.0, 1.0, 2.0, 1.0, 15.0),
            Block.box(0.0, 1.0, 2.0, 2.0, 12.0, 3.0),
            Block.box(0.0, 1.0, 13.0, 2.0, 12.0, 14.0),
            Block.box(2.0, 0.0, 6.0, 16.0, 1.0, 8.0),
            Block.box(2.0, 7.0, 4.5, 9.0, 12.0, 5.5),
            Block.box(9.0, 9.0, 4.5, 16.0, 12.0, 5.5),
            SOUTH_LEFT_BASE);
    protected static final VoxelShape SOUTH_RIGHT_BASE = Shapes.or(
            Block.box(2.0, 12.0, 0.0, 16.0, 16.0, 12.0),
            Block.box(3.0, 16.0, 0.0, 13.0, 17.0, 6.0));
    protected static final VoxelShape SOUTH_RIGHT = Shapes.or(
            Block.box(14.0, 0.0, 1.0, 16.0, 12.0, 10.0),
            Block.box(0.0, 0.0, 4.0, 14.0, 12.0, 8.0),
            SOUTH_RIGHT_BASE);
    protected static final VoxelShape SOUTH_RIGHT_COLLISION = Shapes.or(
            Block.box(14.0, 0.0, 1.0, 16.0, 1.0, 10.0),
            Block.box(14.0, 1.0, 2.0, 16.0, 12.0, 3.0),
            Block.box(14.0, 1.0, 7.0, 16.0, 12.0, 8.0),
            Block.box(0.0, 0.0, 6.0, 14.0, 1.0, 8.0),
            Block.box(0.0, 11.0, 4.5, 7.0, 12.0, 5.5),
            SOUTH_RIGHT_BASE);
    protected static final VoxelShape WEST_LEFT_BASE = Shapes.or(
            Block.box(2.0, 12.0, 0.0, 16.0, 16.0, 18.0),
            Block.box(0.0, 12.0, 0.0, 2.0, 16.0, 10.0),
            Block.box(8.0, 16.0, 3.0, 16.0, 17.0, 18.0));
    protected static final VoxelShape WEST_LEFT = Shapes.or(
            Block.box(1.0, 0.0, 0.0, 15.0, 12.0, 2.0),
            Block.box(8.0, 0.0, 2.0, 12.0, 12.0, 16.0),
            WEST_LEFT_BASE);
    protected static final VoxelShape WEST_LEFT_COLLISION = Shapes.or(
            Block.box(1.0, 0.0, 0.0, 15.0, 1.0, 2.0),
            Block.box(2.0, 1.0, 0.0, 3.0, 12.0, 2.0),
            Block.box(13.0, 1.0, 0.0, 14.0, 12.0, 2.0),
            Block.box(8.0, 0.0, 2.0, 10.0, 1.0, 16.0),
            Block.box(10.5, 7.0, 2.0, 11.5, 12.0, 9.0),
            Block.box(10.5, 9.0, 9.0, 11.5, 12.0, 16.0),
            WEST_LEFT_BASE);
    protected static final VoxelShape WEST_RIGHT_BASE = Shapes.or(
            Block.box(4.0, 12.0, 2.0, 16.0, 16.0, 16.0),
            Block.box(10.0, 16.0, 3.0, 16.0, 17.0, 13.0));
    protected static final VoxelShape WEST_RIGHT = Shapes.or(
            Block.box(6.0, 0.0, 14.0, 15.0, 12.0, 16.0),
            Block.box(8.0, 0.0, 0.0, 12.0, 12.0, 14.0),
            WEST_RIGHT_BASE);
    protected static final VoxelShape WEST_RIGHT_COLLISION = Shapes.or(
            Block.box(6.0, 0.0, 14.0, 15.0, 1.0, 16.0),
            Block.box(8.0, 1.0, 14.0, 9.0, 12.0, 16.0),
            Block.box(13.0, 1.0, 14.0, 14.0, 12.0, 16.0),
            Block.box(8.0, 0.0, 0.0, 10.0, 1.0, 14.0),
            Block.box(10.5, 11.0, 0.0, 11.5, 12.0, 7.0),
            WEST_RIGHT_BASE);

    public XylophoneBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, HorizontalDoubleBlockHalf.LEFT)
        );
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == HorizontalDoubleBlockHalf.LEFT;
        return switch (direction){
            case WEST -> flag ? WEST_LEFT_COLLISION : WEST_RIGHT_COLLISION;
            case SOUTH -> flag ? SOUTH_LEFT_COLLISION : SOUTH_RIGHT_COLLISION;
            case EAST -> flag ? EAST_LEFT_COLLISION : EAST_RIGHT_COLLISION;
            default -> flag ? NORTH_LEFT_COLLISION : NORTH_RIGHT_COLLISION;
        };
    }
    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == HorizontalDoubleBlockHalf.LEFT;
        return switch (direction){
            case WEST -> flag ? WEST_LEFT : WEST_RIGHT;
            case SOUTH -> flag ? SOUTH_LEFT : SOUTH_RIGHT;
            case EAST -> flag ? EAST_LEFT : EAST_RIGHT;
            default -> flag ? NORTH_LEFT : NORTH_RIGHT;
        };
    }
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        if ((Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos))) {
            return Blocks.AIR.defaultBlockState();
        }
        if (pDirection == getNeighbourDirection(pState.getValue(HALF), pState.getValue(FACING))) {
            return pNeighborState.is(this) && pNeighborState.getValue(HALF) != pState.getValue(HALF)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
        }
    }
    private static Direction getNeighbourDirection(HorizontalDoubleBlockHalf pHalf, Direction pDirection) {
        return pHalf == HorizontalDoubleBlockHalf.LEFT ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }
    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide && (pPlayer.isCreative() || !pPlayer.hasCorrectToolForDrops(pState, pLevel, pPos))) {
            HorizontalDoubleBlockHalf half = pState.getValue(HALF);
            if (half == HorizontalDoubleBlockHalf.LEFT) {
                BlockPos blockpos = pPos.relative(getNeighbourDirection(half, pState.getValue(FACING)));
                BlockState blockstate = pLevel.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(HALF) == HorizontalDoubleBlockHalf.RIGHT) {
                    pLevel.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
                    pLevel.levelEvent(pPlayer, 2001, blockpos, Block.getId(blockstate));
                }
            }
        }
        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }
    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        Direction direction = pContext.getHorizontalDirection();
        BlockPos blockpos = pContext.getClickedPos();
        BlockPos blockpos1 = blockpos.relative(direction.getClockWise());
        BlockPos blockpos2 = blockpos1.below();
        Level level = pContext.getLevel();
        return level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1) && level.getBlockState(blockpos2).isFaceSturdy(level, blockpos2, Direction.UP)
                ? this.defaultBlockState().setValue(FACING, direction.getOpposite())
                : null;
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(HALF, HorizontalDoubleBlockHalf.RIGHT), 3);
    }
    @Override
    protected BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }
    @SuppressWarnings("deprecation")
    @Override
    protected BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF);
    }
}
