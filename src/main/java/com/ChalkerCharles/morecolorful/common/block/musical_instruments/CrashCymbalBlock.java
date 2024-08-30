package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.entity.CrashCymbalBlockEntity;
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
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class CrashCymbalBlock extends PercussionInstrumentBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    private static final VoxelShape LOWER = Shapes.or(
            Block.box(7.0, 3.0, 7.0, 9.0, 5.0, 9.0),
            Block.box(7.7, 5.0, 7.7, 8.3, 18.0, 8.3));
    private static final VoxelShape NORTH_UPPER = Shapes.or(
            Block.box(7.0, 2.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(7.5, 4.0, 6.5, 8.5, 9.5, 7.5),
            Block.box(2.0, 9.9, 0.0, 14.0, 10.0, 12.0),
            Block.box(6.0, 9.5, 4.0, 10.0, 10.5, 8.0),
            Block.box(7.7, 10.5, 5.7, 8.3, 11.0, 6.3));
    private static final VoxelShape EAST_UPPER = Shapes.or(
            Block.box(7.0, 2.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(8.5, 4.0, 7.5, 9.5, 9.5, 8.5),
            Block.box(4.0, 9.9, 2.0, 16.0, 10.0, 14.0),
            Block.box(8.0, 9.5, 6.0, 12.0, 10.5, 10.0),
            Block.box(9.7, 10.5, 7.7, 10.3, 11.0, 8.3));
    private static final VoxelShape SOUTH_UPPER = Shapes.or(
            Block.box(7.0, 2.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(7.5, 4.0, 8.5, 8.5, 9.5, 9.5),
            Block.box(2.0, 9.9, 4.0, 14.0, 10.0, 16.0),
            Block.box(6.0, 9.5, 8.0, 10.0, 10.5, 12.0),
            Block.box(7.7, 10.5, 9.7, 8.3, 11.0, 10.3));
    private static final VoxelShape WEST_UPPER = Shapes.or(
            Block.box(7.0, 2.0, 7.0, 9.0, 4.0, 9.0),
            Block.box(6.5, 4.0, 7.5, 7.5, 9.5, 8.5),
            Block.box(0.0, 9.9, 2.0, 12.0, 10.0, 14.0),
            Block.box(4.0, 9.5, 6.0, 8.0, 10.5, 10.0),
            Block.box(5.7, 10.5, 7.7, 6.3, 11.0, 8.3));

    public CrashCymbalBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
                .setValue(HALF, DoubleBlockHalf.LOWER)
        );
    }

    @Override
    protected  VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == DoubleBlockHalf.LOWER;
        return flag ? LOWER : switch (direction) {
            case WEST -> WEST_UPPER;
            case SOUTH -> SOUTH_UPPER;
            case EAST -> EAST_UPPER;
            default -> NORTH_UPPER;
        };
    }
    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP) : blockstate.is(this);
    }
    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        DoubleBlockHalf doubleblockhalf = pState.getValue(HALF);
        if (pFacing.getAxis() != Direction.Axis.Y || doubleblockhalf == DoubleBlockHalf.LOWER != (pFacing == Direction.UP)) {
            return doubleblockhalf == DoubleBlockHalf.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return pFacingState.getBlock() instanceof CrashCymbalBlock && pFacingState.getValue(HALF) != doubleblockhalf
                    ? pFacingState.setValue(HALF, doubleblockhalf)
                    : Blocks.AIR.defaultBlockState();
        }
    }
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
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
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(HALF, DoubleBlockHalf.UPPER), 3);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, FACING);
    }
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CrashCymbalBlockEntity(pPos, pState);
    }
    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pBlockEntityType == ModBlockEntities.CRASH_CYMBAL.get() ? CrashCymbalBlockEntity::tick : null;
    }
}
