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
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

public class SnareDrumBlock extends Block implements MusicalInstrument {
    private static final VoxelShape SNARE_DRUM = Shapes.or(
            Block.box(3.0, 10.0, 3.0, 13.0, 15.0, 13.0),
            Block.box(7.0, 9.0, 7.0, 9.0, 10.0, 9.0),
            Block.box(7.0, 3.4, 7.0, 9.0, 4.4, 9.0),
            Block.box(7.5, 4.4, 7.5, 8.5, 9.0, 8.5));

    public SnareDrumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InstrumentsType getType() {
        return InstrumentsType.SNARE;
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
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (MusicalInstrument.withDrumstick(pPlayer)) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, this.getType(), pPos);
                PacketDistributor.sendToServer(new PlayingScreenPacket(this.getType(), pPos, pPlayer.getId(), true));
            }
            pPlayer.awardStat(ModStats.INTERACT_WITH_SNARE.get());
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumstick"), true);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pDirection, BlockState pNeighborState, LevelAccessor pLevel, BlockPos pPos, BlockPos pNeighborPos) {
        return Direction.DOWN == pDirection && !this.canSurvive(pState, pLevel, pPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pDirection, pNeighborState, pLevel, pPos, pNeighborPos);
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
