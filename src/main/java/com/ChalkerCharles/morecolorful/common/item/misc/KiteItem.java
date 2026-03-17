package com.ChalkerCharles.morecolorful.common.item.misc;

import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.entity.misc.Kite;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.KiteColor;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class KiteItem extends Item {
    public KiteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pUsedHand);
        if (!pLevel.isClientSide) {
            Kite kite = new Kite(pLevel, pPlayer);
            kite.setItem(itemstack);
            pLevel.addFreshEntity(kite);
        }
        pPlayer.awardStat(Stats.ITEM_USED.get(this));
        itemstack.consume(1, pPlayer);
        return InteractionResultHolder.sidedSuccess(itemstack, pLevel.isClientSide());
    }

    public static ItemStack getRandomKite(RandomSource random) {
        ItemStack item = ModItems.KITE.toStack();
        Pair<KiteColor, DyeColor> pair = Util.getRandom(KiteColor.COMMON_COLORS, random);
        item.set(ModDataComponents.KITE_COLOR, pair.left());
        if (random.nextBoolean()) {
            item.set(ModDataComponents.RIBBON, pair.right());
        }
        return item;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag flag) {
        super.appendHoverText(stack, context, components, flag);
        KiteColor color = stack.getOrDefault(ModDataComponents.KITE_COLOR, KiteColor.DEFAULT);
        if (!color.equals(KiteColor.DEFAULT)) {
            List<DyeColor> list = color.colors();
            components.add(getColorName(list.getFirst())
                    .append(", ").append(getColorName(list.get(1)))
                    .append(", ").append(getColorName(list.get(2)))
                    .append(", ").append(getColorName(list.get(3)))
                    .append(", ").append(getColorName(list.get(4)))
                    .withStyle(ChatFormatting.GRAY));
        }
        DyeColor bowColor = stack.get(ModDataComponents.RIBBON);
        if (bowColor != null) {
            Component ribbonName = RibbonBlock.itemByColor(bowColor).asItem().getDescription();
            components.add(Component.translatable("info.morecolorful.with_ribbon", ribbonName).withStyle(ChatFormatting.GRAY));
        }
    }

    private static MutableComponent getColorName(DyeColor color) {
        return Component.translatable("info.morecolorful.dye." + color.getName());
    }
}
