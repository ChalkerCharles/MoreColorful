package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.ModStats;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.network.PacketDistributor;

public abstract class PercussionInstrumentBlock extends MusicalInstrumentBlock {
    public PercussionInstrumentBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
    }

    @Override
    public ItemInteractionResult useItemOn (ItemStack pStack, BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHitResult) {
        if ((pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() ||
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get()) &&
                (pType == InstrumentsType.GLOCKENSPIEL || pType == InstrumentsType.XYLOPHONE || pType == InstrumentsType.VIBRAPHONE)) {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumsticks"), true);
        } else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() &&
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get()) {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumstick"), true);
        } else {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
                PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), true));
            }
            pPlayer.awardStat(switch (pType) {
                case BASS_DRUM -> ModStats.INTERACT_WITH_BASS_DRUM.get();
                case SNARE -> ModStats.INTERACT_WITH_SNARE.get();
                case TOM -> ModStats.INTERACT_WITH_TOM.get();
                case HAT -> ModStats.INTERACT_WITH_HAT.get();
                case RIDE -> ModStats.INTERACT_WITH_RIDE.get();
                case CRASH -> ModStats.INTERACT_WITH_CRASH.get();
                case CHIMES -> ModStats.INTERACT_WITH_CHIMES.get();
                case GLOCKENSPIEL -> ModStats.INTERACT_WITH_GLOCKENSPIEL.get();
                case XYLOPHONE -> ModStats.INTERACT_WITH_XYLOPHONE.get();
                case VIBRAPHONE -> ModStats.INTERACT_WITH_VIBRAPHONE.get();
                default -> throw new IllegalArgumentException("Invalid Percussion Instrument Type");
            });
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
