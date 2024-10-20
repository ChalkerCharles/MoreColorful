package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.util.EnumExtensions;
import com.google.common.collect.Maps;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;

public final class ModCommonSetup {
    @SubscribeEvent
    public static void setStrippedWoodBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
            AxeItem.STRIPPABLES.put(ModBlocks.CRABAPPLE_LOG.get(), ModBlocks.STRIPPED_CRABAPPLE_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.CRABAPPLE_WOOD.get(), ModBlocks.STRIPPED_CRABAPPLE_WOOD.get());
            AxeItem.STRIPPABLES.put(ModBlocks.EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.EBONY_WOOD.get(), ModBlocks.STRIPPED_EBONY_WOOD.get());
            AxeItem.STRIPPABLES.put(ModBlocks.GINKGO_LOG.get(), ModBlocks.STRIPPED_GINKGO_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.GINKGO_WOOD.get(), ModBlocks.STRIPPED_GINKGO_WOOD.get());
            AxeItem.STRIPPABLES.put(ModBlocks.MAPLE_LOG.get(), ModBlocks.STRIPPED_MAPLE_LOG.get());
            AxeItem.STRIPPABLES.put(ModBlocks.MAPLE_WOOD.get(), ModBlocks.STRIPPED_MAPLE_WOOD.get());
        });
    }

    @SubscribeEvent
    public static void addFlowerPotBlocks(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FlowerPotBlock pot = (FlowerPotBlock) Blocks.FLOWER_POT;
            pot.addPlant(ModBlocks.CRABAPPLE_SAPLING.getId(), ModBlocks.POTTED_CRABAPPLE_SAPLING);
            pot.addPlant(ModBlocks.WHITE_CHERRY_SAPLING.getId(), ModBlocks.POTTED_WHITE_CHERRY_SAPLING);
            pot.addPlant(ModBlocks.AUTUMN_BIRCH_SAPLING.getId(), ModBlocks.POTTED_AUTUMN_BIRCH_SAPLING);
            pot.addPlant(ModBlocks.GINKGO_SAPLING.getId(), ModBlocks.POTTED_GINKGO_SAPLING);
            pot.addPlant(ModBlocks.MAPLE_SAPLING.getId(), ModBlocks.POTTED_MAPLE_SAPLING);
            pot.addPlant(ModBlocks.PINK_DAISY.getId(), ModBlocks.POTTED_PINK_DAISY);
            pot.addPlant(ModBlocks.RED_CARNATION.getId(), ModBlocks.POTTED_RED_CARNATION);
            pot.addPlant(ModBlocks.PINK_CARNATION.getId(), ModBlocks.POTTED_PINK_CARNATION);
            pot.addPlant(ModBlocks.WHITE_CARNATION.getId(), ModBlocks.POTTED_WHITE_CARNATION);
            pot.addPlant(ModBlocks.RED_SPIDER_LILY.getId(), ModBlocks.POTTED_RED_SPIDER_LILY);
            pot.addPlant(ModBlocks.YELLOW_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM);
            pot.addPlant(ModBlocks.GREEN_CHRYSANTHEMUM.getId(), ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);
            pot.addPlant(ModBlocks.DAYBLOOM.getId(), ModBlocks.POTTED_DAYBLOOM);
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

            fireblock.setFlammable(ModBlocks.CRABAPPLE_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.BEGONIAS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WHITE_CHERRY_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.WHITE_PETALS.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.AUTUMN_BIRCH_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.AUTUMN_BIRCH_LEAF_PILE.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.GINKGO_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.GINKGO_LEAF_PILE.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.MAPLE_LEAVES.get(), 30, 60);
            fireblock.setFlammable(ModBlocks.MAPLE_LEAF_PILE.get(), 60, 100);

            fireblock.setFlammable(ModBlocks.PINK_DAISY.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.RED_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.PINK_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.WHITE_CARNATION.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.RED_SPIDER_LILY.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.YELLOW_CHRYSANTHEMUM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.GREEN_CHRYSANTHEMUM.get(), 60, 100);
            fireblock.setFlammable(ModBlocks.DAYBLOOM.get(), 60, 100);
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
                ModBlocks.MAPLE_WALL_SIGN.get());
        event.modify(BlockEntityType.HANGING_SIGN,
                ModBlocks.CRABAPPLE_HANGING_SIGN.get(),
                ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                ModBlocks.GINKGO_HANGING_SIGN.get(),
                ModBlocks.GINKGO_WALL_HANGING_SIGN.get(),
                ModBlocks.MAPLE_HANGING_SIGN.get(),
                ModBlocks.MAPLE_WALL_HANGING_SIGN.get());
    }
}
