package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class TrumpetItem extends MusicalInstrumentItem {
    public TrumpetItem(InstrumentsType pType, Properties pProperties) {
        super(pType, pProperties);
        this.pType = pType;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull InteractionHand pHand) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if (pLevel.isClientSide) {
            PlayingScreen.openPlayingScreen(pPlayer, pType);
            pPlayer.startUsingItem(pHand);
        } else {
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.consume(pStack);
    }
}
