package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.util.MusicalInstrument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.network.PacketDistributor;

public class TomTomDrumBlock extends Block implements MusicalInstrument {
    private static final VoxelShape TOM = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    public TomTomDrumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public InstrumentsType getType() {
        return InstrumentsType.TOM;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOM;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if (MusicalInstrument.withDrumstick(pPlayer)) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, this.getType(), pPos);
                PacketDistributor.sendToServer(new PlayingScreenPacket(this.getType(), pPos, pPlayer.getId(), true));
            }
            pPlayer.awardStat(ModStats.INTERACT_WITH_TOM.get());
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumstick"), true);
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
