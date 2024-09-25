package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class TrumpetItem extends MusicalInstrumentItem {
    public TrumpetItem(InstrumentsType pType, Properties pProperties) {
        super(pType, pProperties);
        this.pType = pType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if (pLevel.isClientSide) {
            PlayingScreen.openPlayingScreen(pPlayer, pType);
            PacketDistributor.sendToServer(new PlayingScreenPacket(pType, PlayingScreen.DEFAULT_POS, pPlayer.getId(), true));
        }
        pPlayer.startUsingItem(pHand);
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.consume(pStack);
    }
}
