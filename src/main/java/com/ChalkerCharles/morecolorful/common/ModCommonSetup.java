package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

public final class ModCommonSetup {
    @SubscribeEvent
    public static void setStrippedWoodBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = ImmutableMap.<Block, Block>builder()
                    .putAll(AxeItem.STRIPPABLES)
                    .put(ModBlocks.CRABAPPLE_LOG.get(), ModBlocks.STRIPPED_CRABAPPLE_LOG.get())
                    .put(ModBlocks.CRABAPPLE_WOOD.get(), ModBlocks.STRIPPED_CRABAPPLE_WOOD.get())
                    .put(ModBlocks.EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_LOG.get())
                    .put(ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_WOOD.get())
                    .put(ModBlocks.GINKGO_LOG.get(), ModBlocks.STRIPPED_GINKGO_LOG.get())
                    .put(ModBlocks.GINKGO_WOOD.get(), ModBlocks.STRIPPED_GINKGO_WOOD.get())
                    .put(ModBlocks.MAPLE_LOG.get(), ModBlocks.STRIPPED_MAPLE_LOG.get())
                    .put(ModBlocks.MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_WOOD.get())
                    .put(ModBlocks.FROST_LOG.get(), ModBlocks.STRIPPED_FROST_LOG.get())
                    .put(ModBlocks.FROST_WOOD.get(), ModBlocks.STRIPPED_FROST_WOOD.get())
                    .put(ModBlocks.DAWN_REDWOOD_LOG.get(), ModBlocks.STRIPPED_DAWN_REDWOOD_LOG.get())
                    .put(ModBlocks.DAWN_REDWOOD_WOOD.get(), ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get())
                    .put(ModBlocks.JACARANDA_LOG.get(), ModBlocks.STRIPPED_JACARANDA_LOG.get())
                    .put(ModBlocks.JACARANDA_WOOD.get(), ModBlocks.STRIPPED_JACARANDA_WOOD.get())
                    .put(ModBlocks.WILLOW_LOG.get(), ModBlocks.STRIPPED_WILLOW_LOG.get())
                    .put(ModBlocks.WILLOW_WOOD.get(), ModBlocks.STRIPPED_WILLOW_WOOD.get())
                    .build();
        });
    }

    @SubscribeEvent
    public static void addFlowerPotBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
            pot.addPlant(ModBlocks.CRABAPPLE_SAPLING.getId(), ModBlocks.POTTED_CRABAPPLE_SAPLING);
            pot.addPlant(ModBlocks.WHITE_CHERRY_SAPLING.getId(), ModBlocks.POTTED_WHITE_CHERRY_SAPLING);
            pot.addPlant(ModBlocks.ORANGE_BIRCH_SAPLING.getId(), ModBlocks.POTTED_ORANGE_BIRCH_SAPLING);
            pot.addPlant(ModBlocks.YELLOW_BIRCH_SAPLING.getId(), ModBlocks.POTTED_YELLOW_BIRCH_SAPLING);
            pot.addPlant(ModBlocks.GINKGO_SAPLING.getId(), ModBlocks.POTTED_GINKGO_SAPLING);
            pot.addPlant(ModBlocks.MAPLE_SAPLING.getId(), ModBlocks.POTTED_MAPLE_SAPLING);
            pot.addPlant(ModBlocks.FROST_SAPLING.getId(), ModBlocks.POTTED_FROST_SAPLING);
            pot.addPlant(ModBlocks.DAWN_REDWOOD_SAPLING.getId(), ModBlocks.POTTED_DAWN_REDWOOD_SAPLING);
            pot.addPlant(ModBlocks.JACARANDA_SAPLING.getId(), ModBlocks.POTTED_JACARANDA_SAPLING);
            pot.addPlant(ModBlocks.WILLOW_SAPLING.getId(), ModBlocks.POTTED_WILLOW_SAPLING);
            pot.addPlant(ModBlocks.PINK_DAISY.getId(), ModBlocks.POTTED_PINK_DAISY);
            pot.addPlant(ModBlocks.RED_CARNATION.getId(), ModBlocks.POTTED_RED_CARNATION);
            pot.addPlant(ModBlocks.PINK_CARNATION.getId(), ModBlocks.POTTED_PINK_CARNATION);
            pot.addPlant(ModBlocks.WHITE_CARNATION.getId(), ModBlocks.POTTED_WHITE_CARNATION);
            pot.addPlant(ModBlocks.RED_SPIDER_LILY.getId(), ModBlocks.POTTED_RED_SPIDER_LILY);
            pot.addPlant(ModBlocks.YELLOW_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM);
            pot.addPlant(ModBlocks.GREEN_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);
            pot.addPlant(ModBlocks.OPEN_DAYBLOOM.getId(), ModBlocks.POTTED_OPEN_DAYBLOOM);
            pot.addPlant(ModBlocks.CLOSED_DAYBLOOM.getId(), ModBlocks.POTTED_CLOSED_DAYBLOOM);
            pot.addPlant(ModBlocks.EDELWEISS.getId(), ModBlocks.POTTED_EDELWEISS);
            pot.addPlant(ModBlocks.CROCUS.getId(), ModBlocks.POTTED_CROCUS);
            pot.addPlant(ModBlocks.IRIS.getId(), ModBlocks.POTTED_IRIS);
            pot.addPlant(ModBlocks.LAVENDER.getId(), ModBlocks.POTTED_LAVENDER);
            pot.addPlant(ModBlocks.DAFFODIL.getId(), ModBlocks.POTTED_DAFFODIL);
            pot.addPlant(ModBlocks.GERBERA_DAISY.getId(), ModBlocks.POTTED_GERBERA_DAISY);
            pot.addPlant(ModBlocks.RAPESEED_FLOWER.getId(), ModBlocks.POTTED_RAPESEED_FLOWER);
            pot.addPlant(ModBlocks.WINDFLOWER.getId(), ModBlocks.POTTED_WINDFLOWER);
        });
    }

    @SubscribeEvent
    public static void setFlammableBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FireBlock fireblock = (FireBlock) Blocks.FIRE;
            fireblock.setFlammable(ModBlocks.CRABAPPLE_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_CRABAPPLE_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_CRABAPPLE_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.CRABAPPLE_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.EBONY_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.EBONY_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_EBONY_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_EBONY_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.EBONY_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.EBONY_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.EBONY_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.EBONY_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.EBONY_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.GINKGO_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.GINKGO_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_GINKGO_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_GINKGO_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.GINKGO_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GINKGO_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GINKGO_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GINKGO_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.GINKGO_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.MAPLE_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.MAPLE_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_MAPLE_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_MAPLE_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.MAPLE_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAPLE_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAPLE_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAPLE_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.MAPLE_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.FROST_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.FROST_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_FROST_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_FROST_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.FROST_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.FROST_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.FROST_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.FROST_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.FROST_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_FENCE_GATE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_ROOTS.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.JACARANDA_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.JACARANDA_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_JACARANDA_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_JACARANDA_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.JACARANDA_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JACARANDA_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JACARANDA_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JACARANDA_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.JACARANDA_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.WILLOW_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.WILLOW_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_WILLOW_LOG.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.STRIPPED_WILLOW_WOOD.get(), 5, 5);
            fireblock.setFlammable(ModBlocks.WILLOW_PLANKS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WILLOW_STAIRS.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WILLOW_SLAB.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WILLOW_FENCE.get(), 5, 20);
            fireblock.setFlammable(ModBlocks.WILLOW_FENCE_GATE.get(), 5, 20);

            fireblock.setFlammable(ModBlocks.CRABAPPLE_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BEGONIAS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WHITE_CHERRY_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.WHITE_PETALS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.ORANGE_BIRCH_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.YELLOW_BIRCH_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.YELLOW_BIRCH_LEAF_LITTER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.GINKGO_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GINKGO_LEAF_LITTER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.MAPLE_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.MAPLE_LEAF_LITTER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.FROST_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.FROSTY_PETALS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.DAWN_REDWOOD_LEAF_LITTER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.JACARANDA_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.VIOLETS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.BUTTERCUPS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.FORGET_ME_NOTS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.BABY_BLUE_EYES.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.SPEEDWELLS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WOOD_SORRELS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WILLOW_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.WILLOW_BRANCHES.get(), 15, 100);

            fireblock.setFlammable(ModBlocks.PINK_DAISY.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.RED_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.PINK_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WHITE_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.RED_SPIDER_LILY.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.YELLOW_CHRYSANTHEMUM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.GREEN_CHRYSANTHEMUM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.OPEN_DAYBLOOM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.CLOSED_DAYBLOOM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.EDELWEISS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.CROCUS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.IRIS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.LAVENDER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.DAFFODIL.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.GERBERA_DAISY.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.RAPESEED_FLOWER.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WINDFLOWER.get(), 60, 100);

            fireblock.setFlammable(ModBlocks.CATTAIL.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.TALL_RAPESEED_FLOWER.get(), 60, 100);

            fireblock.setFlammable(ModBlocks.SHORT_WATER_GRASS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.TALL_WATER_GRASS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.REED.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.STRAWBERRY_BUSH.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.BLUEBERRY_BUSH.get(), 60, 100);
        });
    }

    @SubscribeEvent
    public static void registerDispenserBehaviors(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerProjectileBehavior(ModItems.PAPER_PLANE);
        });
    }

    @SubscribeEvent
    public static void addBlockEntity(BlockEntityTypeAddBlocksEvent event) {
        event.modify(BlockEntityType.SIGN,
                ModBlocks.CRABAPPLE_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_SIGN.get(),
                ModBlocks.EBONY_SIGN.get(),
                ModBlocks.EBONY_WALL_SIGN.get(),
                ModBlocks.GINKGO_SIGN.get(),
                ModBlocks.GINKGO_WALL_SIGN.get(),
                ModBlocks.MAPLE_SIGN.get(),
                ModBlocks.MAPLE_WALL_SIGN.get(),
                ModBlocks.FROST_SIGN.get(),
                ModBlocks.FROST_WALL_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_WALL_SIGN.get(),
                ModBlocks.JACARANDA_SIGN.get(),
                ModBlocks.JACARANDA_WALL_SIGN.get(),
                ModBlocks.WILLOW_SIGN.get(),
                ModBlocks.WILLOW_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.CRABAPPLE_HANGING_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                ModBlocks.GINKGO_HANGING_SIGN.get(),
                ModBlocks.GINKGO_WALL_HANGING_SIGN.get(),
                ModBlocks.MAPLE_HANGING_SIGN.get(),
                ModBlocks.MAPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.FROST_HANGING_SIGN.get(),
                ModBlocks.FROST_WALL_HANGING_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_HANGING_SIGN.get(),
                ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(),
                ModBlocks.JACARANDA_HANGING_SIGN.get(),
                ModBlocks.JACARANDA_WALL_HANGING_SIGN.get(),
                ModBlocks.WILLOW_HANGING_SIGN.get(),
                ModBlocks.WILLOW_WALL_HANGING_SIGN.get());
    }
}
