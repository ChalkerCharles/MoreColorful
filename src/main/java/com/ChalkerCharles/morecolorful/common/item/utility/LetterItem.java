package com.ChalkerCharles.morecolorful.common.item.utility;

import com.ChalkerCharles.morecolorful.network.packets.OpenLetterPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.WrittenBookItem;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class LetterItem extends Item {
    public LetterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        WrittenBookContent content = pStack.get(DataComponents.WRITTEN_BOOK_CONTENT);
        if (content != null) {
            String s = content.title().raw();
            if (!StringUtil.isBlank(s)) {
                return Component.literal(s);
            }
        }

        return super.getName(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        WrittenBookContent content = pStack.get(DataComponents.WRITTEN_BOOK_CONTENT);
        if (content != null) {
            if (!StringUtil.isBlank(content.author())) {
                pTooltipComponents.add(Component.translatable("book.byAuthor", content.author()).withStyle(ChatFormatting.GRAY));
            }

            pTooltipComponents.add(Component.translatable("book.generation." + content.generation()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (!pLevel.isClientSide) {
            if (WrittenBookItem.resolveBookComponents(itemstack, pPlayer.createCommandSourceStack(), pPlayer)) {
                pPlayer.containerMenu.broadcastChanges();
            }
            PacketDistributor.sendToPlayer((ServerPlayer) pPlayer, new OpenLetterPacket(pHand));
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
