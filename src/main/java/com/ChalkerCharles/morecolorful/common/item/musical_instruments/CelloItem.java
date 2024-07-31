package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CelloItem extends MusicalInstrumentItem {
    public CelloItem(InstrumentsType pType, Properties pProperties) {
        super(pType, pProperties);
        this.pType = pType;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if ((pHand == InteractionHand.MAIN_HAND && pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() == ModItems.FIDDLE_BOW.get())
                || (pHand == InteractionHand.OFF_HAND && pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.FIDDLE_BOW.get())) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, pType);
                pPlayer.startUsingItem(pHand);
            } else {
                pPlayer.awardStat(Stats.ITEM_USED.get(this));
                pPlayer.awardStat(Stats.ITEM_USED.get(ModItems.FIDDLE_BOW.get()));
            }
        } else {
            pPlayer.displayClientMessage(Component.translatable("item.morecolorful.instruments.need_fiddle_bow"), true);
        }
        return InteractionResultHolder.consume(pStack);
    }
}
