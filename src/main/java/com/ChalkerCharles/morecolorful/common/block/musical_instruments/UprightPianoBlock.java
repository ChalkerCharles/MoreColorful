package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.UprightPianoPart;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class UprightPianoBlock extends MusicalInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<UprightPianoPart> PART = ModBlockStateProperties.UPRIGHT_PIANO_PART;
    private static final VoxelShape NORTH_LEFT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(14.0, 0.0, 0.0, 16.0, 2.0, 7.0),
            Block.box(14.0, 2.0, 1.0, 16.0, 12.0, 3.0),
            Block.box(-3.0, 1.0, 6.0, 3.0, 3.0, 7.0));
    private static final VoxelShape NORTH_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 16.0, 11.0, 16.0),
            Block.box(0.0, 0.0, 6.0, 16.0, 5.0, 7.0),
            Block.box(14.0, 0.0, 0.0, 16.0, 2.0, 6.0));
    private static final VoxelShape NORTH_RIGHT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 2.0, 2.0, 7.0),
            Block.box(0.0, 2.0, 1.0, 2.0, 12.0, 3.0));
    private static final VoxelShape NORTH_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 7.0, 16.0, 11.0, 16.0),
            Block.box(0.0, 0.0, 6.0, 16.0, 5.0, 7.0),
            Block.box(0.0, 0.0, 0.0, 2.0, 2.0, 6.0));
    private static final VoxelShape EAST_LEFT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 9.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(9.0, 0.0, 14.0, 16.0, 2.0, 16.0),
            Block.box(13.0, 2.0, 14.0, 15.0, 12.0, 16.0),
            Block.box(9.0, 1.0, -3.0, 10.0, 3.0, 3.0));
    private static final VoxelShape EAST_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 9.0, 11.0, 16.0),
            Block.box(9.0, 0.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(10.0, 0.0, 14.0, 16.0, 2.0, 16.0));
    private static final VoxelShape EAST_RIGHT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 9.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(9.0, 0.0, 0.0, 16.0, 2.0, 2.0),
            Block.box(13.0, 2.0, 0.0, 15.0, 12.0, 2.0));
    private static final VoxelShape EAST_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 9.0, 11.0, 16.0),
            Block.box(9.0, 0.0, 0.0, 10.0, 5.0, 16.0),
            Block.box(10.0, 0.0, 0.0, 16.0, 2.0, 2.0));
    private static final VoxelShape SOUTH_LEFT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 9.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 9.0, 2.0, 2.0, 16.0),
            Block.box(0.0, 2.0, 13.0, 2.0, 12.0, 15.0),
            Block.box(13.0, 1.0, 9.0, 19.0, 3.0, 10.0));
    private static final VoxelShape SOUTH_LEFT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 11.0, 9.0),
            Block.box(0.0, 0.0, 9.0, 16.0, 5.0, 10.0),
            Block.box(0.0, 0.0, 10.0, 2.0, 2.0, 16.0));
    private static final VoxelShape SOUTH_RIGHT_LOWER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 9.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(14.0, 0.0, 9.0, 16.0, 2.0, 16.0),
            Block.box(14.0, 2.0, 13.0, 16.0, 12.0, 15.0));
    private static final VoxelShape SOUTH_RIGHT_UPPER = Shapes.or(
            Block.box(0.0, 0.0, 0.0, 16.0, 11.0, 9.0),
            Block.box(0.0, 0.0, 9.0, 16.0, 5.0, 10.0),
            Block.box(14.0, 0.0, 10.0, 16.0, 2.0, 16.0));
    private static final VoxelShape WEST_LEFT_LOWER = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 7.0, 2.0, 2.0),
            Block.box(1.0, 2.0, 0.0, 3.0, 12.0, 2.0),
            Block.box(6.0, 1.0, 13.0, 7.0, 3.0, 19.0));
    private static final VoxelShape WEST_LEFT_UPPER = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 16.0, 11.0, 16.0),
            Block.box(6.0, 0.0, 0.0, 7.0, 5.0, 16.0),
            Block.box(0.0, 0.0, 0.0, 6.0, 2.0, 2.0));
    private static final VoxelShape WEST_RIGHT_LOWER = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.box(0.0, 12.0, 0.0, 16.0, 16.0, 16.0),
            Block.box(0.0, 0.0, 14.0, 7.0, 2.0, 16.0),
            Block.box(1.0, 2.0, 14.0, 3.0, 12.0, 16.0));
    private static final VoxelShape WEST_RIGHT_UPPER = Shapes.or(
            Block.box(7.0, 0.0, 0.0, 16.0, 11.0, 16.0),
            Block.box(6.0, 0.0, 0.0, 7.0, 5.0, 16.0),
            Block.box(0.0, 0.0, 14.0, 6.0, 2.0, 16.0));

    public UprightPianoBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(PART, UprightPianoPart.LEFT_LOWER)
        );
    }

    @Override
    public @NotNull InteractionResult useWithoutItem (@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull BlockHitResult pHitResult) {
        if (pLevel.isClientSide){
            PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        UprightPianoPart part = pState.getValue(PART);
        return switch (direction){
            case WEST -> switch (part){
                case LEFT_UPPER -> WEST_LEFT_UPPER;
                case RIGHT_LOWER -> WEST_RIGHT_LOWER;
                case RIGHT_UPPER -> WEST_RIGHT_UPPER;
                default -> WEST_LEFT_LOWER;
            };
            case SOUTH -> switch (part){
                case LEFT_UPPER -> SOUTH_LEFT_UPPER;
                case RIGHT_LOWER -> SOUTH_RIGHT_LOWER;
                case RIGHT_UPPER -> SOUTH_RIGHT_UPPER;
                default -> SOUTH_LEFT_LOWER;
            };
            case EAST -> switch (part){
                case LEFT_UPPER -> EAST_LEFT_UPPER;
                case RIGHT_LOWER -> EAST_RIGHT_LOWER;
                case RIGHT_UPPER -> EAST_RIGHT_UPPER;
                default -> EAST_LEFT_LOWER;
            };
            default -> switch (part){
                case LEFT_UPPER -> NORTH_LEFT_UPPER;
                case RIGHT_LOWER -> NORTH_RIGHT_LOWER;
                case RIGHT_UPPER -> NORTH_RIGHT_UPPER;
                default -> NORTH_LEFT_LOWER;
            };
        };
    }
    @Override
    protected @NotNull BlockState updateShape(@NotNull BlockState pState, @NotNull Direction pDirection, @NotNull BlockState pNeighborState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pPos, @NotNull BlockPos pNeighborPos) {
        if (pDirection == getNeighbourDirection(pState.getValue(PART), pState.getValue(FACING))) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else if (pDirection == getNeighbourDirection(pState.getValue(PART))) {
            return pNeighborState.is(this) && pNeighborState.getValue(PART) != pState.getValue(PART)
                    ? super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos)
                    : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
        }
    }
    private static Direction getNeighbourDirection(UprightPianoPart pPart, Direction pDirection) {
        return (pPart == UprightPianoPart.LEFT_LOWER || pPart == UprightPianoPart.LEFT_UPPER) ? pDirection.getCounterClockWise() : pDirection.getClockWise();
    }
    private static Direction getNeighbourDirection(UprightPianoPart pPart) {
        return (pPart == UprightPianoPart.LEFT_LOWER || pPart == UprightPianoPart.RIGHT_LOWER) ? Direction.UP : Direction.DOWN;
    }
    @Override
    public @NotNull BlockState playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide && (pPlayer.isCreative() || !pPlayer.hasCorrectToolForDrops(pState, pLevel, pPos))) {
            UprightPianoPart part = pState.getValue(PART);
            if (part != UprightPianoPart.RIGHT_LOWER) {
                BlockPos blockpos = switch (part) {
                    case LEFT_LOWER ->  pPos.relative(getNeighbourDirection(part, pState.getValue(FACING)));
                    case LEFT_UPPER -> pPos.relative(getNeighbourDirection(part, pState.getValue(FACING))).below();
                    case RIGHT_UPPER -> pPos.below();
                    default -> null;
                };
                BlockState blockstate = pLevel.getBlockState(blockpos);
                if (blockstate.is(this) && blockstate.getValue(PART) == UprightPianoPart.RIGHT_LOWER) {
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
        Level level = pContext.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(pContext) && level.getBlockState(blockpos1.above()).canBeReplaced(pContext)) {
            return level.getBlockState(blockpos1).canBeReplaced(pContext) && level.getWorldBorder().isWithinBounds(blockpos1)
                    ? this.defaultBlockState().setValue(FACING, direction.getOpposite())
                    : null;
        } else {
            return null;
        }
    }
    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, @NotNull ItemStack pStack) {
        pLevel.setBlock(pPos.above(), pState.setValue(PART, UprightPianoPart.LEFT_UPPER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()), pState.setValue(PART, UprightPianoPart.RIGHT_LOWER), 3);
        pLevel.setBlock(pPos.relative(pState.getValue(FACING).getCounterClockWise()).above(), pState.setValue(PART, UprightPianoPart.RIGHT_UPPER), 3);
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, PART);
    }
}
