package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class BowedStringInstrumentItem extends MusicalInstrumentItem {

    public BowedStringInstrumentItem(InstrumentsType pType, Properties pProperties) {
        super(pType, pProperties, 200);
        this.pType = pType;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if ((pHand == InteractionHand.MAIN_HAND && pPlayer.getItemInHand(InteractionHand.OFF_HAND).getItem() == ModItems.FIDDLE_BOW.get())
                || (pHand == InteractionHand.OFF_HAND && pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.FIDDLE_BOW.get())) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, pType);
                PacketDistributor.sendToServer(new PlayingScreenPacket(pType, PlayingScreen.DEFAULT_POS, pPlayer.getId(), true));
            }
            pPlayer.startUsingItem(pHand);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
            pPlayer.awardStat(Stats.ITEM_USED.get(ModItems.FIDDLE_BOW.get()));
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_fiddle_bow"), true);
            return InteractionResultHolder.fail(pStack);
        }
        return InteractionResultHolder.consume(pStack);
    }
}
