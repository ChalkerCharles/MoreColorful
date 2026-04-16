package com.ChalkerCharles.morecolorful.common.entity.villager;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelColor;
import com.ChalkerCharles.morecolorful.common.item.misc.KiteItem;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.BasicItemListing;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = MoreColorful.MODID)
public class ModCustomTrades {
    @SubscribeEvent
    public static void addVillagerTrades(VillagerTradesEvent event) {
        VillagerProfession profession = event.getType();
        Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
        if (profession == ModVillagerProfessions.BEEKEEPER.value()) {
            trades.put(1, List.of(
                    sell(Items.SUGAR, 8, 16, 2),
                    sell(Items.DANDELION, 12, 16, 2),
                    sell(Items.POPPY, 12, 16, 2),
                    sell(Items.ALLIUM, 12, 16, 2),
                    sell(Items.AZURE_BLUET, 12, 16, 2),
                    sell(Items.RED_TULIP, 12, 16, 2),
                    sell(Items.ORANGE_TULIP, 12, 16, 2),
                    sell(Items.WHITE_TULIP, 12, 16, 2),
                    sell(Items.PINK_TULIP, 12, 16, 2),
                    sell(Items.OXEYE_DAISY, 12, 16, 2),
                    sell(Items.CORNFLOWER, 12, 16, 2),
                    sell(Items.BLUE_ORCHID, 12, 16, 2),
                    sell(Items.LILY_OF_THE_VALLEY, 12, 16, 2),
                    sell(ModItems.PINK_DAISY, 12, 16, 2),
                    sell(ModItems.RED_CARNATION, 12, 16, 2),
                    sell(ModItems.PINK_CARNATION, 12, 16, 2),
                    sell(ModItems.WHITE_CARNATION, 12, 16, 2),
                    sell(ModItems.RED_SPIDER_LILY, 12, 16, 2),
                    sell(ModItems.YELLOW_CHRYSANTHEMUM, 12, 16, 2),
                    sell(ModItems.GREEN_CHRYSANTHEMUM, 12, 16, 2),
                    sell(ModItems.OPEN_DAYBLOOM, 12, 16, 2),
                    sell(ModItems.EDELWEISS, 12, 16, 2),
                    sell(ModItems.CROCUS, 12, 16, 2),
                    sell(ModItems.IRIS, 12, 16, 2),
                    sell(ModItems.LAVENDER, 12, 16, 2),
                    sell(ModItems.DAFFODIL, 12, 16, 2),
                    sell(ModItems.GERBERA_DAISY, 12, 16, 2),
                    sell(ModItems.RAPESEED_FLOWER, 12, 16, 2)
            ));
            trades.put(2, List.of(
                    sell(ModItems.BEE, 1, 12, 10),
                    buy(ModItems.BUG_NET, 1, 2, 12, 5)
            ));
            trades.put(3, List.of(
                    sell(Items.HONEYCOMB, 3, 12, 20),
                    buy(Items.HONEY_BOTTLE, 2, Items.GLASS_BOTTLE, 2, 1, 12, 10),
                    buy(Items.CANDLE, 2, 1, 12, 10)
            ));
            trades.put(4, List.of(
                    sell(ModItems.BUTTERFLY, 1, 12, 30),
                    sell(ModItems.MOTH, 1, 12, 30),
                    buy(Items.CAMPFIRE, 1, 2, 12, 15)
            ));
            trades.put(5, List.of(
                    buy(ModItems.WHITE_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.LIGHT_GRAY_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.GRAY_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.BLACK_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.BROWN_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.RED_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.ORANGE_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.YELLOW_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.LIME_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.GREEN_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.CYAN_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.LIGHT_BLUE_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.BLUE_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.PURPLE_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.MAGENTA_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.PINK_SMOKE_BOMB, 1, 1, 12, 30),
                    buy(ModItems.BEEKEEPING_HAT, 1, 5, 12, 30)
            ));
        } else if (profession == ModVillagerProfessions.PYROTECHNICIAN.value()) {
            trades.put(1, List.of(
                    sell(Items.GUNPOWDER, 6, 16, 2),
                    sell(Items.COAL, 12, 16, 2)
            ));
            trades.put(2, List.of(
                    sell(Items.FLINT, 26, 12, 10),
                    sell(Items.BLAZE_POWDER, 2, 12, 10),
                    buy(Items.FLINT_AND_STEEL, 1, 1, 12, 5),
                    buy(Items.FIRE_CHARGE, 3, 1, 12, 5)
            ));
            trades.put(3, List.of(
                    buy(ModItems.WHITE_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.BROWN_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.RED_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.ORANGE_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.YELLOW_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.LIME_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.GREEN_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.CYAN_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.LIGHT_BLUE_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.BLUE_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.PURPLE_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.MAGENTA_SPARKLER, 1, 1, 12, 10),
                    buy(ModItems.PINK_SPARKLER, 1, 1, 12, 10)
            ));
            trades.put(4, List.of(
                    buy(ModItems.BOMB, 1, 1, 12, 15),
                    new FireworkTrade(Items.FIREWORK_STAR, 1),
                    new FireworkTrade(Items.FIREWORK_ROCKET, 3)
            ));
            trades.put(5, List.of(
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_CREEPER, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_AXIS, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_PLANET, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_HEART, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_JELLYFISH, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_TETRAHEDRON, 1, 8, 12, 30),
                    buy(ModItems.FIREWORK_SHAPE_TEMPLATE_HYPERBOLOID, 1, 8, 12, 30)
            ));
        } else if (profession == ModVillagerProfessions.PAPER_ARTISAN.value()) {
            trades.put(1, List.of(
                    sell(Items.PAPER, 24, 16, 2),
                    buy(ModItems.PAPER_PLANE, 8, 1, 16, 1),
                    buy(ModItems.PAPER_BOAT, 8, 1, 16, 1)
            ));
            trades.put(2, List.of(
                    sell(ModItems.CARDBOARD, 12, 12, 10)
            ));
            trades.put(3, List.of(
                    sell(ModItems.CONFETTI, 8, 12, 20),
                    buy(ModItems.WHITE_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.LIGHT_GRAY_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.GRAY_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.BLACK_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.BROWN_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.RED_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.ORANGE_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.YELLOW_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.LIME_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.GREEN_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.CYAN_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.LIGHT_BLUE_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.BLUE_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.PURPLE_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.MAGENTA_PARTY_POPPER, 3, 1, 12, 10),
                    buy(ModItems.PINK_PARTY_POPPER, 3, 1, 12, 10)
            ));
            trades.put(4, List.of(
                    ModCustomTrades::pinwheelTrade,
                    new PapercuttingTrade(ModItems.WHITE_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.LIGHT_GRAY_PARTY_POPPER),
                    new PapercuttingTrade(ModItems.GRAY_PARTY_POPPER),
                    new PapercuttingTrade(ModItems.BLACK_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.BROWN_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.RED_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.ORANGE_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.YELLOW_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.LIME_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.GREEN_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.CYAN_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.LIGHT_BLUE_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.BLUE_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.PURPLE_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.MAGENTA_PAPERCUTTING),
                    new PapercuttingTrade(ModItems.PINK_PAPERCUTTING)
            ));
            trades.put(5, List.of(
                    ModCustomTrades::kiteTrade
            ));
        }
    }

    @SubscribeEvent
    public static void addWandererTrades(WandererTradesEvent event) {
        event.getGenericTrades().addAll(List.of(
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
    }

    private static BasicItemListing sell(ItemLike item, int itemCount, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(item, itemCount), new ItemStack(Items.EMERALD), maxTrades, xp, 0.05F);
    }

    private static BasicItemListing buy(ItemLike item, int itemCount, int emeralds, int maxTrades, int xp) {
        return new BasicItemListing(emeralds, new ItemStack(item, itemCount), maxTrades, xp, 0.05F);
    }

    private static BasicItemListing buy(ItemLike item, int count, ItemLike item2, int count2, int emeralds, int maxTrades, int xp) {
        return new BasicItemListing(new ItemStack(Items.EMERALD, emeralds), new ItemStack(item2, count2), new ItemStack(item, count), maxTrades, xp, 0.05F);
    }

    private static MerchantOffer pinwheelTrade(Entity entity, RandomSource random) {
        ItemStack stack = ModItems.PINWHEEL.toStack();
        ItemCost itemcost = new ItemCost(Items.EMERALD);
        DyeColor[] colors = DyeColor.values();
        int i = random.nextInt(100);
        PinwheelColor pinwheelColor;
        if (i < 40) {
            pinwheelColor = PinwheelColor.pure(Util.getRandom(colors, random));
        } else if (i < 75) {
            pinwheelColor = PinwheelColor.biColor(Util.getRandom(colors, random), Util.getRandom(colors, random));
        } else {
            pinwheelColor = new PinwheelColor(List.of(
                    Util.getRandom(colors, random), Util.getRandom(colors, random), Util.getRandom(colors, random), Util.getRandom(colors, random)
            ));
        }
        stack.set(ModDataComponents.PINWHEEL_COLOR, pinwheelColor);
        return new MerchantOffer(itemcost, stack, 12, 15, 0.05F);
    }

    private static MerchantOffer kiteTrade(Entity entity, RandomSource random) {
        ItemStack stack = KiteItem.getRandomKite(random);
        ItemCost itemcost = new ItemCost(Items.EMERALD, 4);
        return new MerchantOffer(itemcost, stack, 12, 30, 0.05F);
    }

    private record FireworkTrade(Item item, int count) implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity pTrader, RandomSource pRandom) {
            FireworkExplosion explosion = ItemUtils.getRandomFireworkExplosion(pRandom);
            ItemStack stack = new ItemStack(this.item, this.count);
            if (this.item == Items.FIREWORK_ROCKET) {
                stack.set(DataComponents.FIREWORKS, new Fireworks(1, List.of(explosion)));
            } else {
                stack.set(DataComponents.FIREWORK_EXPLOSION, explosion);
            }
            ItemCost itemcost = new ItemCost(Items.EMERALD);
            return new MerchantOffer(itemcost, stack, 12, 15, 0.05F);
        }
    }

    private record PapercuttingTrade(ItemLike item) implements VillagerTrades.ItemListing {
        @Override
        public MerchantOffer getOffer(Entity pTrader, RandomSource random) {
            ItemStack stack = new ItemStack(this.item, 4);
            ItemCost itemcost = new ItemCost(Items.EMERALD);
            PapercuttingStencil stencil = Util.getRandom(PapercuttingStencil.COMMON_STENCILS, random);
            stack.set(ModDataComponents.PAPERCUTTING_STENCIL, stencil);
            return new MerchantOffer(itemcost, stack, 12, 15, 0.05F);
        }
    }
}
