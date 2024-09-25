package com.ChalkerCharles.morecolorful.common.villager;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

@EventBusSubscriber(modid = MoreColorful.MODID)
public class ModCustomTrades {
    //SubscribeEvent
    //public static void addVillagerTrades(VillagerTradesEvent event) {

    //}

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        var genericTrades = event.getGenericTrades();
        genericTrades.add(buy(ModItems.EBONY_LOG, 3, 5, 15, 12));

    }

    private static BasicItemListing sell(ItemLike item, int itemCount, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, itemCount), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    private static BasicItemListing buy(ItemLike item, int itemCount, int emeralds, int maxTrades, int xp) {
        return new BasicItemListing(emeralds, new ItemStack(item, itemCount), maxTrades, xp, 0.05F);
    }
}
