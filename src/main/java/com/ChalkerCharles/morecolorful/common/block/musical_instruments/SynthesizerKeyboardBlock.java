package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
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
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class SynthesizerKeyboardBlock extends MusicalInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<HorizontalDoubleBlockHalf> HALF = ModBlockStateProperties.HORIZONTAL_HALF;
    private static final VoxelShape KEYBOARD_NORTH_LEFT = Block.box(0.0, 14.0, 0.0, 14.0, 15.0, 6.0);
    private static final VoxelShape KEYBOARD_NORTH_RIGHT = Block.box(2.0, 14.0, 0.0, 16.0, 15.0, 6.0);
    private static final VoxelShape KEYBOARD_EAST_LEFT = Block.box(10.0, 14.0, 0.0, 16.0, 15.0, 14.0);
    private static final VoxelShape KEYBOARD_EAST_RIGHT = Block.box(10.0, 14.0, 2.0, 16.0, 15.0, 16.0);
    private static final VoxelShape KEYBOARD_SOUTH_LEFT = Block.box(2.0, 14.0, 10.0, 16.0, 15.0, 16.0);
    private static final VoxelShape KEYBOARD_SOUTH_RIGHT = Block.box(0.0, 14.0, 10.0, 14.0, 15.0, 16.0);
    private static final VoxelShape KEYBOARD_WEST_LEFT = Block.box(0.0, 14.0, 2.0, 6.0, 15.0, 16.0);
    private static final VoxelShape KEYBOARD_WEST_RIGHT = Block.box(0.0, 14.0, 0.0, 6.0, 15.0, 14.0);
    private static final VoxelShape NORTH_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 6.0, 16.0, 17.0, 16.0),
            Block.box(14.0, 14.0, 0.0, 16.0, 17.0, 6.0),
            KEYBOARD_NORTH_LEFT);
    private static final VoxelShape NORTH_LEFT = Shapes.or(
            Block.box(0.0, 0.0, 4.0, 8.0, 12.0, 12.0),
            NORTH_LEFT_COLLISION);
    private static final VoxelShape NORTH_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 6.0, 16.0, 17.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 2.0, 17.0, 6.0),
            KEYBOARD_NORTH_RIGHT);
    private static final VoxelShape NORTH_RIGHT = Shapes.or(
            Block.box(8.0, 0.0, 4.0, 16.0, 12.0, 12.0),
            NORTH_RIGHT_COLLISION);
    private static final VoxelShape EAST_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 10.0, 17.0, 16.0),
            Block.box(10.0, 14.0, 14.0, 16.0, 17.0, 16.0),
            KEYBOARD_EAST_LEFT);
    private static final VoxelShape EAST_LEFT = Shapes.or(
            Block.box(4.0, 0.0, 0.0, 12.0, 12.0, 8.0),
            EAST_LEFT_COLLISION);
    private static final VoxelShape EAST_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 10.0, 17.0, 16.0),
            Block.box(10.0, 14.0, 0.0, 16.0, 17.0, 2.0),
            KEYBOARD_EAST_RIGHT);
    private static final VoxelShape EAST_RIGHT = Shapes.or(
            Block.box(4.0, 0.0, 8.0, 12.0, 12.0, 16.0),
            EAST_RIGHT_COLLISION);
    private static final VoxelShape SOUTH_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 16.0, 17.0, 10.0),
            Block.box(0.0, 14.0, 10.0, 2.0, 17.0, 16.0),
            KEYBOARD_SOUTH_LEFT);
    private static final VoxelShape SOUTH_LEFT = Shapes.or(
            Block.box(8.0, 0.0, 4.0, 16.0, 12.0, 12.0),
            SOUTH_LEFT_COLLISION);
    private static final VoxelShape SOUTH_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 16.0, 17.0, 10.0),
            Block.box(14.0, 14.0, 10.0, 16.0, 17.0, 16.0),
            KEYBOARD_SOUTH_RIGHT);
    private static final VoxelShape SOUTH_RIGHT = Shapes.or(
            Block.box(0.0, 0.0, 4.0, 8.0, 12.0, 12.0),
            SOUTH_RIGHT_COLLISION);
    private static final VoxelShape WEST_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(6.0, 14.0, 0.0, 16.0, 17.0, 16.0),
            Block.box(0.0, 14.0, 0.0, 6.0, 17.0, 2.0),
            KEYBOARD_WEST_LEFT);
    private static final VoxelShape WEST_LEFT = Shapes.or(
            Block.box(4.0, 0.0, 8.0, 12.0, 12.0, 16.0),
            WEST_LEFT_COLLISION);
    private static final VoxelShape WEST_RIGHT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 0.0, 16.0, 14.0, 16.0),
            Block.box(6.0, 14.0, 0.0, 16.0, 17.0, 16.0),
            Block.box(0.0, 14.0, 14.0, 6.0, 17.0, 16.0),
            KEYBOARD_WEST_RIGHT);
    private static final VoxelShape WEST_RIGHT = Shapes.or(
            Block.box(4.0, 0.0, 0.0, 12.0, 12.0, 8.0),
            WEST_RIGHT_COLLISION);

    public SynthesizerKeyboardBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties.strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(HALF, HorizontalDoubleBlockHalf.LEFT)
        );
    }

    @Override
    public InteractionResult useWithoutItem (BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
            PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), true));
        }
        pPlayer.awardStat(ModStats.INTERACT_WITH_SYNTHESIZER_KEYBOARD.get());
        return InteractionResult.CONSUME;
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
