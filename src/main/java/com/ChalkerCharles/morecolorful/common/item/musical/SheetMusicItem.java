package com.ChalkerCharles.morecolorful.common.item.musical;

import com.ChalkerCharles.morecolorful.client.gui.SheetMusicViewScreen;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class SheetMusicItem extends Item {
    public SheetMusicItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Component getName(ItemStack pStack) {
        Melody melody = pStack.get(ModDataComponents.MELODY);
        if (melody != null) {
            String s = melody.title().raw();
            if (!StringUtil.isBlank(s)) {
                return Component.literal(s);
            }
        }
        return super.getName(pStack);
    }

    @Override
    public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag) {
        Melody melody = pStack.get(ModDataComponents.MELODY);
        if (melody != null) {
            if (!StringUtil.isBlank(melody.author())) {
                pTooltipComponents.add(Component.translatable("morecolorful.gui.sheet_music.byAuthor", melody.author()).withStyle(ChatFormatting.GRAY));
            }
            pTooltipComponents.add(Component.translatable("book.generation." + melody.generation()).withStyle(ChatFormatting.GRAY));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        Melody melody = itemstack.get(ModDataComponents.MELODY);
        if (melody != null) {
            if (pLevel.isClientSide) {
                SheetMusicViewScreen.openScreen(melody);
            }
            pPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }
}
