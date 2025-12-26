package com.ChalkerCharles.morecolorful.common.block.musical;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.util.MusicalInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
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
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

public class GlockenspielBlock extends Block implements MusicalInstrument {
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

    public GlockenspielBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public InstrumentsType getType() {
        return InstrumentsType.GLOCKENSPIEL;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case WEST -> WEST_COLLISION;
            case SOUTH -> SOUTH_COLLISION;
            case EAST -> EAST_COLLISION;
            default -> NORTH_COLLISION;
        };
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Direction direction = pState.getValue(FACING);
        return switch (direction){
            case WEST, EAST -> WEST_EAST;
            default -> NORTH_SOUTH;
        };
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

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (MusicalInstrument.withDrumsticks(pPlayer)) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, this.getType(), pPos);
                PacketDistributor.sendToServer(new PlayingScreenPacket(this.getType(), pPos, pPlayer.getId(), true));
            }
            pPlayer.awardStat(ModStats.INTERACT_WITH_GLOCKENSPIEL.get());
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumsticks"), true);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
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
        pBuilder.add(FACING);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
