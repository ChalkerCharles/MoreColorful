package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public abstract class PercussionInstrumentBlock extends MusicalInstrumentBlock {
    public PercussionInstrumentBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
    }

    @Override
    public @NotNull ItemInteractionResult useItemOn (@NotNull ItemStack pStack, @NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, @NotNull Player pPlayer, @NotNull InteractionHand pHand, @NotNull BlockHitResult pHitResult) {
        if ((pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() ||
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get()) &&
                (pType == InstrumentsType.GLOCKENSPIEL || pType == InstrumentsType.XYLOPHONE || pType == InstrumentsType.VIBRAPHONE)) {
            pPlayer.displayClientMessage(Component.translatable("item.morecolorful.instruments.need_drumsticks"), true);
        } else if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() != ModItems.DRUMSTICK.get() &&
                pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() != ModItems.DRUMSTICK.get()) {
            pPlayer.displayClientMessage(Component.translatable("item.morecolorful.instruments.need_drumstick"), true);
        } else {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, pType, pPos);
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }
}
