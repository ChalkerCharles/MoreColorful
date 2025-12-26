package com.ChalkerCharles.morecolorful.common.villager;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = MoreColorful.MODID)
public class ModCustomTrades {
    public static final Lazy<List<VillagerTrades.ItemListing>> WANDERING_TRADER_GENERIC = Lazy.of(() -> List.of(
            buy(ModItems.EBONY_LOG, 8, 1, 4, 1),

            buy(ModItems.RED_CARNATION, 1, 1, 12, 1),
            buy(ModItems.PINK_CARNATION, 1, 1, 12, 1),
            buy(ModItems.PINK_DAISY, 1, 1, 12, 1),
            buy(ModItems.WHITE_CARNATION, 1, 1, 12, 1),
            buy(ModItems.RED_SPIDER_LILY, 1, 1, 12, 1),
            buy(ModItems.YELLOW_CHRYSANTHEMUM, 1, 1, 12, 1),
            buy(ModItems.GREEN_CHRYSANTHEMUM, 1, 1, 12, 1),
            buy(ModItems.OPEN_DAYBLOOM, 1, 1, 12, 1),
            buy(ModItems.EDELWEISS, 1, 1, 12, 1),
            buy(ModItems.CROCUS, 1, 1, 12, 1),
            buy(ModItems.IRIS, 1, 1, 12, 1),
            buy(ModItems.LAVENDER, 1, 1, 12, 1),
            buy(ModItems.DAFFODIL, 1, 1, 12, 1),
            buy(ModItems.GERBERA_DAISY, 1, 1, 12, 1),
            buy(ModItems.RAPESEED_FLOWER, 1, 1, 12, 1),
            buy(ModItems.WINDFLOWER, 1, 1, 12, 1),

            buy(ModItems.CRABAPPLE_SAPLING, 1, 5, 8, 1),
            buy(ModItems.WHITE_CHERRY_SAPLING, 1, 5, 8, 1),
            buy(ModItems.ORANGE_BIRCH_SAPLING, 1, 5, 8, 1),
            buy(ModItems.YELLOW_BIRCH_SAPLING, 1, 5, 8, 1),
            buy(ModItems.GINKGO_SAPLING, 1, 5, 8, 1),
            buy(ModItems.MAPLE_SAPLING, 1, 5, 8, 1),
            buy(ModItems.FROST_SAPLING, 1, 5, 8, 1),
            buy(ModItems.DAWN_REDWOOD_SAPLING, 1, 5, 8, 1),
            buy(ModItems.JACARANDA_SAPLING, 1, 5, 8, 1),
            buy(ModItems.WILLOW_SAPLING, 1, 5, 8, 1)
    ));

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(WANDERING_TRADER_GENERIC.get());
    }

    private static BasicItemListing sell(ItemLike item, int itemCount, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, itemCount), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    private static BasicItemListing buy(ItemLike item, int itemCount, int emeralds, int maxTrades, int xp) {
        return new BasicItemListing(emeralds, new ItemStack(item, itemCount), maxTrades, xp, 0.05F);
    }
}
