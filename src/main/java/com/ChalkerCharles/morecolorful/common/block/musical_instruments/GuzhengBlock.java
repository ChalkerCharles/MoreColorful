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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class GuzhengBlock extends MusicalInstrumentBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<HorizontalDoubleBlockHalf> HALF = ModBlockStateProperties.HORIZONTAL_HALF;
    private static final VoxelShape TOP_NORTH_SOUTH = Block.box(0.0, 13.0, 1.0, 16.0, 16.0, 15.0);
    private static final VoxelShape TOP_EAST_WEST = Block.box(1.0, 13.0, 0.0, 15.0, 16.0, 16.0);
    private static final VoxelShape LEG_NORTH = Block.box(7.0, 0.0, 2.0, 11.0, 12.0, 14.0);
    private static final VoxelShape LEG_EAST = Block.box(2.0, 0.0, 7.0, 14.0, 12.0, 11.0);
    private static final VoxelShape LEG_SOUTH = Block.box(5.0, 0.0, 2.0, 9.0, 12.0, 14.0);
    private static final VoxelShape LEG_WEST = Block.box(2.0, 0.0, 5.0, 14.0, 12.0, 9.0);
    private static final VoxelShape NORTH_LEFT_COLLISION = Shapes.or(
            Block.box(8.0, 12.0, 0.0, 10.0, 13.0, 16.0),
            TOP_NORTH_SOUTH);
    private static final VoxelShape EAST_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 8.0, 16.0, 13.0, 10.0),
            TOP_EAST_WEST);
    private static final VoxelShape SOUTH_LEFT_COLLISION = Shapes.or(
            Block.box(6.0, 12.0, 0.0, 8.0, 13.0, 16.0),
            TOP_NORTH_SOUTH);
    private static final VoxelShape WEST_LEFT_COLLISION = Shapes.or(
            Block.box(0.0, 12.0, 6.0, 16.0, 13.0, 8.0),
            TOP_EAST_WEST);
    private static final VoxelShape NORTH_LEFT = Shapes.or(NORTH_LEFT_COLLISION, LEG_NORTH);
    private static final VoxelShape EAST_LEFT = Shapes.or(EAST_LEFT_COLLISION, LEG_EAST);
    private static final VoxelShape SOUTH_LEFT = Shapes.or(SOUTH_LEFT_COLLISION, LEG_SOUTH);
    private static final VoxelShape WEST_LEFT = Shapes.or(WEST_LEFT_COLLISION, LEG_WEST);

    public GuzhengBlock(InstrumentsType pType, Properties properties) {
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
            case WEST -> flag ? WEST_LEFT_COLLISION : EAST_LEFT_COLLISION;
            case SOUTH -> flag ? SOUTH_LEFT_COLLISION : NORTH_LEFT_COLLISION;
            case EAST -> flag ? EAST_LEFT_COLLISION : WEST_LEFT_COLLISION;
            default -> flag ? NORTH_LEFT_COLLISION : SOUTH_LEFT_COLLISION;
        };
    }
    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(HALF) == HorizontalDoubleBlockHalf.LEFT;
        return switch (direction){
            case WEST -> flag ? WEST_LEFT : EAST_LEFT;
            case SOUTH -> flag ? SOUTH_LEFT : NORTH_LEFT;
            case EAST -> flag ? EAST_LEFT : WEST_LEFT;
            default -> flag ? NORTH_LEFT : SOUTH_LEFT;
        };
    }

    @Override
    public InteractionResult useWithoutItem (BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
            PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), true));
        }
        pPlayer.awardStat(ModStats.INTERACT_WITH_GUZHENG.get());
        return InteractionResult.CONSUME;
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
