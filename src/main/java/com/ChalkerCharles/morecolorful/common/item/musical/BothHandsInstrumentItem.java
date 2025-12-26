package com.ChalkerCharles.morecolorful.common.item.musical;

import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class BothHandsInstrumentItem extends MusicalInstrumentItem {
    public BothHandsInstrumentItem(InstrumentsType type, Properties pProperties) {
        super(type, pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack stack = pPlayer.getItemInHand(pHand);
        if (emptyHanded(pPlayer, pHand)) {
            if (pLevel.isClientSide) {
                PlayingScreen.openPlayingScreen(pPlayer, type);
                PacketDistributor.sendToServer(new PlayingScreenPacket(type, pPlayer.getId(), true));
            }
            pPlayer.startUsingItem(pHand);
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        } else {
            pPlayer.displayClientMessage(Component.translatable("info.morecolorful.instruments.busy_hands"), true);
            return InteractionResultHolder.fail(stack);
        }
        return InteractionResultHolder.consume(stack);
    }

    private static boolean emptyHanded(Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND && !player.hasItemInSlot(EquipmentSlot.OFFHAND)) return true;
        return hand == InteractionHand.OFF_HAND && !player.hasItemInSlot(EquipmentSlot.MAINHAND);
    }
}
