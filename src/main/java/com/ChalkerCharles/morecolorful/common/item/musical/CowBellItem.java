package com.ChalkerCharles.morecolorful.common.item.musical;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class CowBellItem extends MusicalInstrumentItem {
    public CowBellItem(InstrumentsType type, Properties pProperties) {
        super(type, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack pStack = pPlayer.getItemInHand(pHand);
        if (withDrumstick(pPlayer, pHand)) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, type);
                PacketDistributor.sendToServer(new PlayingScreenPacket(type, pPlayer.getId(), true));
            }
            pPlayer.startUsingItem(pHand);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.need_drumstick"), true);
            return InteractionResultHolder.fail(pStack);
        }
        return InteractionResultHolder.consume(pStack);
    }

    private static boolean withDrumstick(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && player.getOffhandItem().getItem() == ModItems.DRUMSTICK.get())
            return true;
        return hand == InteractionHand.OFF_HAND && player.getMainHandItem().getItem() == ModItems.DRUMSTICK.get();
    }
}
