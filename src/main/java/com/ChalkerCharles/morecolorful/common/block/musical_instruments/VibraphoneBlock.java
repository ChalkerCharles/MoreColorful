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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class VibraphoneBlock extends PercussionInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<HorizontalDoubleBlockHalf> HALF = ModBlockStateProperties.HORIZONTAL_HALF;
    private static final VoxelShape PEDAL_NORTH = Block.box(12.0, 0.0, 4.0, 20.0, 0.5, 8.0);
    private static final VoxelShape PEDAL_EAST = Block.box(8.0, 0.0, 12.0, 12.0, 0.5, 20.0);
    private static final VoxelShape PEDAL_SOUTH = Block.box(-4.0, 0.0, 8.0, 4.0, 0.5, 12.0);
    private static final VoxelShape PEDAL_WEST = Block.box(4.0, 0.0, -4.0, 8.0, 0.5, 4.0);
    private static final VoxelShape NORTH_LEFT = XylophoneBlock.NORTH_LEFT;
    private static final VoxelShape NORTH_RIGHT = Shapes.or(XylophoneBlock.NORTH_RIGHT, PEDAL_NORTH);
    private static final VoxelShape EAST_LEFT = XylophoneBlock.EAST_LEFT;
    private static final VoxelShape EAST_RIGHT = Shapes.or(XylophoneBlock.EAST_RIGHT, PEDAL_EAST);
    private static final VoxelShape SOUTH_LEFT = XylophoneBlock.SOUTH_LEFT;
    private static final VoxelShape SOUTH_RIGHT = Shapes.or(XylophoneBlock.SOUTH_RIGHT, PEDAL_SOUTH);
    private static final VoxelShape WEST_LEFT = XylophoneBlock.WEST_LEFT;
    private static final VoxelShape WEST_RIGHT = Shapes.or(XylophoneBlock.WEST_RIGHT, PEDAL_WEST);
    private static final VoxelShape NORTH_LEFT_COLLISION = Shapes.or(
            Block.box(8.0, 4.0, 10.5, 14.0, 7.0, 11.5),
            Block.box(0.0, 7.0, 10.5, 7.0, 9.0, 11.5),
            XylophoneBlock.NORTH_LEFT_COLLISION);
    private static final VoxelShape NORTH_RIGHT_COLLISION = Shapes.or(
            Block.box(8.0, 9.0, 10.5, 16.0, 12.0, 11.5),
            Block.box(2.0, 6.0, 10.5, 8.0, 12.0, 11.5),
            XylophoneBlock.NORTH_RIGHT_COLLISION,
            PEDAL_NORTH);
    private static final VoxelShape EAST_LEFT_COLLISION = Shapes.or(
            Block.box(4.5, 7.0, 0.0, 5.5, 9.0, 8.0),
            Block.box(4.5, 4.0, 8.0, 5.5, 7.0, 14.0),
            XylophoneBlock.EAST_LEFT_COLLISION);
    private static final VoxelShape EAST_RIGHT_COLLISION = Shapes.or(
            Block.box(4.5, 9.0, 8.0, 5.5, 12.0, 16.0),
            Block.box(4.5, 6.0, 2.0, 5.5, 12.0, 8.0),
            XylophoneBlock.EAST_RIGHT_COLLISION,
            PEDAL_EAST);
    private static final VoxelShape SOUTH_LEFT_COLLISION = Shapes.or(
            Block.box(2.0, 4.0, 4.5, 8.0, 7.0, 5.5),
            Block.box(8.0, 7.0, 4.5, 16.0, 9.0, 5.5),
            XylophoneBlock.SOUTH_LEFT_COLLISION);
    private static final VoxelShape SOUTH_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 9.0, 4.5, 8.0, 12.0, 5.5),
            Block.box(8.0, 6.0, 4.5, 14.0, 12.0, 5.5),
            XylophoneBlock.SOUTH_RIGHT_COLLISION,
            PEDAL_SOUTH);
    private static final VoxelShape WEST_LEFT_COLLISION = Shapes.or(
            Block.box(10.5, 4.0, 2.0, 11.5, 7.0, 8.0),
            Block.box(10.5, 7.0, 8.0, 11.5, 9.0, 16.0),
            XylophoneBlock.WEST_LEFT_COLLISION);
    private static final VoxelShape WEST_RIGHT_COLLISION = Shapes.or(
            Block.box(10.5, 9.0, 0.0, 11.5, 12.0, 8.0),
            Block.box(10.5, 6.0, 8.0, 11.5, 12.0, 14.0),
            XylophoneBlock.WEST_RIGHT_COLLISION,
            PEDAL_WEST);

    public VibraphoneBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, HorizontalDoubleBlockHalf.LEFT)
        );
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
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
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
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
    protected boolean canSurvive(@NotNull BlockState pState, @NotNull LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }
    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
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
    public @NotNull BlockState playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
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
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, @NotNull ItemStack pStack) {
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(HALF, HorizontalDoubleBlockHalf.RIGHT), 3);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, HALF);
    }
}
