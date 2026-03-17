package com.ChalkerCharles.morecolorful.common.datagen.loot;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.GrandPianoPart;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.UprightPianoPart;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModBlockLootTableHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTableProvider extends ModBlockLootTableHelper {
    public ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        // Musical Instrument Blocks
        dropForDoubleBlock(ModBlocks.HARP);
        dropSelf(ModBlocks.BASS_DRUM);
        dropSelf(ModBlocks.SNARE_DRUM);
        dropSelf(ModBlocks.TOMTOM_DRUM);
        dropSelf(ModBlocks.HIHAT);
        dropForDoubleBlock(ModBlocks.RIDE_CYMBAL);
        dropForDoubleBlock(ModBlocks.CRASH_CYMBAL);
        dropForDoubleBlock(ModBlocks.CHIMES);
        dropSelf(ModBlocks.GLOCKENSPIEL);
        dropForHorizontalDoubleBlock(ModBlocks.XYLOPHONE);
        dropForHorizontalDoubleBlock(ModBlocks.VIBRAPHONE);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_BIT);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLING);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SAW);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK);
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
        dropForHorizontalDoubleBlock(ModBlocks.GUZHENG);
        add(ModBlocks.UPRIGHT_PIANO, createSinglePropConditionTable(ModBlocks.UPRIGHT_PIANO, ModBlockStateProperties.UPRIGHT_PIANO_PART, UprightPianoPart.RIGHT_LOWER));
        add(ModBlocks.GRAND_PIANO, createSinglePropConditionTable(ModBlocks.GRAND_PIANO, ModBlockStateProperties.GRAND_PIANO_PART, GrandPianoPart.FRONT_RIGHT_LOWER));
        add(ModBlocks.DRUM_SET, createDrumSetDrops());
        dropSelf(ModBlocks.MUSIC_BOX);

        // Common Blocks
        dropSelf(ModBlocks.CRABAPPLE_LOG);
        dropSelf(ModBlocks.STRIPPED_CRABAPPLE_LOG);
        dropSelf(ModBlocks.CRABAPPLE_WOOD);
        dropSelf(ModBlocks.STRIPPED_CRABAPPLE_WOOD);
        dropSelf(ModBlocks.CRABAPPLE_PLANKS);
        dropSelf(ModBlocks.CRABAPPLE_STAIRS);
        dropForSlab(ModBlocks.CRABAPPLE_SLAB);
        dropSelf(ModBlocks.CRABAPPLE_FENCE);
        dropSelf(ModBlocks.CRABAPPLE_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.CRABAPPLE_DOOR);
        dropSelf(ModBlocks.CRABAPPLE_TRAPDOOR);
        dropSelf(ModBlocks.CRABAPPLE_PRESSURE_PLATE);
        dropSelf(ModBlocks.CRABAPPLE_BUTTON);
        dropOther(ModBlocks.CRABAPPLE_SIGN, ModItems.CRABAPPLE_SIGN);
        dropOther(ModBlocks.CRABAPPLE_WALL_SIGN, ModItems.CRABAPPLE_SIGN);
        dropOther(ModBlocks.CRABAPPLE_HANGING_SIGN, ModItems.CRABAPPLE_HANGING_SIGN);
        dropOther(ModBlocks.CRABAPPLE_WALL_HANGING_SIGN, ModItems.CRABAPPLE_HANGING_SIGN);
        dropForLeavesWithExtraDrop(ModBlocks.CRABAPPLE_LEAVES, ModBlocks.CRABAPPLE_SAPLING, Items.APPLE);
        dropSelf(ModBlocks.CRABAPPLE_SAPLING);
        dropPottedContents(ModBlocks.POTTED_CRABAPPLE_SAPLING);
        dropForPetals(ModBlocks.BEGONIAS);
        dropForLeaves(ModBlocks.WHITE_CHERRY_LEAVES, ModBlocks.WHITE_CHERRY_SAPLING);
        dropSelf(ModBlocks.WHITE_CHERRY_SAPLING);
        dropPottedContents(ModBlocks.POTTED_WHITE_CHERRY_SAPLING);
        dropForPetals(ModBlocks.WHITE_PETALS);

        dropForLeavesWithLeafPile(ModBlocks.ORANGE_BIRCH_LEAVES, ModBlocks.ORANGE_BIRCH_SAPLING, ModItems.ORANGE_BIRCH_LEAF_LITTER);
        dropSelf(ModBlocks.ORANGE_BIRCH_SAPLING);
        dropPottedContents(ModBlocks.POTTED_ORANGE_BIRCH_SAPLING);
        dropForLeafLitter(ModBlocks.ORANGE_BIRCH_LEAF_LITTER);
        dropForLeavesWithLeafPile(ModBlocks.YELLOW_BIRCH_LEAVES, ModBlocks.YELLOW_BIRCH_SAPLING, ModItems.YELLOW_BIRCH_LEAF_LITTER);
        dropSelf(ModBlocks.YELLOW_BIRCH_SAPLING);
        dropPottedContents(ModBlocks.POTTED_YELLOW_BIRCH_SAPLING);
        dropForLeafLitter(ModBlocks.YELLOW_BIRCH_LEAF_LITTER);

        dropSelf(ModBlocks.EBONY_LOG);
        dropSelf(ModBlocks.STRIPPED_EBONY_LOG);
        dropSelf(ModBlocks.EBONY_WOOD);
        dropSelf(ModBlocks.STRIPPED_EBONY_WOOD);
        dropSelf(ModBlocks.EBONY_PLANKS);
        dropSelf(ModBlocks.EBONY_STAIRS);
        dropForSlab(ModBlocks.EBONY_SLAB);
        dropSelf(ModBlocks.EBONY_FENCE);
        dropSelf(ModBlocks.EBONY_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.EBONY_DOOR);
        dropSelf(ModBlocks.EBONY_TRAPDOOR);
        dropSelf(ModBlocks.EBONY_PRESSURE_PLATE);
        dropSelf(ModBlocks.EBONY_BUTTON);
        dropOther(ModBlocks.EBONY_SIGN, ModItems.EBONY_SIGN);
        dropOther(ModBlocks.EBONY_WALL_SIGN, ModItems.EBONY_SIGN);
        dropOther(ModBlocks.EBONY_HANGING_SIGN, ModItems.EBONY_HANGING_SIGN);
        dropOther(ModBlocks.EBONY_WALL_HANGING_SIGN, ModItems.EBONY_HANGING_SIGN);

        dropSelf(ModBlocks.GINKGO_LOG);
        dropSelf(ModBlocks.STRIPPED_GINKGO_LOG);
        dropSelf(ModBlocks.GINKGO_WOOD);
        dropSelf(ModBlocks.STRIPPED_GINKGO_WOOD);
        dropSelf(ModBlocks.GINKGO_PLANKS);
        dropSelf(ModBlocks.GINKGO_STAIRS);
        dropForSlab(ModBlocks.GINKGO_SLAB);
        dropSelf(ModBlocks.GINKGO_FENCE);
        dropSelf(ModBlocks.GINKGO_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.GINKGO_DOOR);
        dropSelf(ModBlocks.GINKGO_TRAPDOOR);
        dropSelf(ModBlocks.GINKGO_PRESSURE_PLATE);
        dropSelf(ModBlocks.GINKGO_BUTTON);
        dropOther(ModBlocks.GINKGO_SIGN, ModItems.GINKGO_SIGN);
        dropOther(ModBlocks.GINKGO_WALL_SIGN, ModItems.GINKGO_SIGN);
        dropOther(ModBlocks.GINKGO_HANGING_SIGN, ModItems.GINKGO_HANGING_SIGN);
        dropOther(ModBlocks.GINKGO_WALL_HANGING_SIGN, ModItems.GINKGO_HANGING_SIGN);
        dropForLeavesWithLeafPile(ModBlocks.GINKGO_LEAVES, ModBlocks.GINKGO_SAPLING, ModItems.GINKGO_LEAF_LITTER);
        dropSelf(ModBlocks.GINKGO_SAPLING);
        dropPottedContents(ModBlocks.POTTED_GINKGO_SAPLING);
        dropForLeafLitter(ModBlocks.GINKGO_LEAF_LITTER);

        dropSelf(ModBlocks.MAPLE_LOG);
        dropSelf(ModBlocks.STRIPPED_MAPLE_LOG);
        dropSelf(ModBlocks.MAPLE_WOOD);
        dropSelf(ModBlocks.STRIPPED_MAPLE_WOOD);
        dropSelf(ModBlocks.MAPLE_PLANKS);
        dropSelf(ModBlocks.MAPLE_STAIRS);
        dropForSlab(ModBlocks.MAPLE_SLAB);
        dropSelf(ModBlocks.MAPLE_FENCE);
        dropSelf(ModBlocks.MAPLE_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.MAPLE_DOOR);
        dropSelf(ModBlocks.MAPLE_TRAPDOOR);
        dropSelf(ModBlocks.MAPLE_PRESSURE_PLATE);
        dropSelf(ModBlocks.MAPLE_BUTTON);
        dropOther(ModBlocks.MAPLE_SIGN, ModItems.MAPLE_SIGN);
        dropOther(ModBlocks.MAPLE_WALL_SIGN, ModItems.MAPLE_SIGN);
        dropOther(ModBlocks.MAPLE_HANGING_SIGN, ModItems.MAPLE_HANGING_SIGN);
        dropOther(ModBlocks.MAPLE_WALL_HANGING_SIGN, ModItems.MAPLE_HANGING_SIGN);
        dropForLeavesWithLeafPile(ModBlocks.MAPLE_LEAVES, ModBlocks.MAPLE_SAPLING, ModItems.MAPLE_LEAF_LITTER);
        dropSelf(ModBlocks.MAPLE_SAPLING);
        dropPottedContents(ModBlocks.POTTED_MAPLE_SAPLING);
        dropForLeafLitter(ModBlocks.MAPLE_LEAF_LITTER);

        dropSelf(ModBlocks.FROST_LOG);
        dropSelf(ModBlocks.STRIPPED_FROST_LOG);
        dropSelf(ModBlocks.FROST_WOOD);
        dropSelf(ModBlocks.STRIPPED_FROST_WOOD);
        dropSelf(ModBlocks.FROST_PLANKS);
        dropSelf(ModBlocks.FROST_STAIRS);
        dropForSlab(ModBlocks.FROST_SLAB);
        dropSelf(ModBlocks.FROST_FENCE);
        dropSelf(ModBlocks.FROST_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.FROST_DOOR);
        dropSelf(ModBlocks.FROST_TRAPDOOR);
        dropSelf(ModBlocks.FROST_PRESSURE_PLATE);
        dropSelf(ModBlocks.FROST_BUTTON);
        dropOther(ModBlocks.FROST_SIGN, ModItems.FROST_SIGN);
        dropOther(ModBlocks.FROST_WALL_SIGN, ModItems.FROST_SIGN);
        dropOther(ModBlocks.FROST_HANGING_SIGN, ModItems.FROST_HANGING_SIGN);
        dropOther(ModBlocks.FROST_WALL_HANGING_SIGN, ModItems.FROST_HANGING_SIGN);
        dropForLeaves(ModBlocks.FROST_LEAVES, ModBlocks.FROST_SAPLING);
        dropSelf(ModBlocks.FROST_SAPLING);
        dropPottedContents(ModBlocks.POTTED_FROST_SAPLING);
        dropForPetals(ModBlocks.FROSTY_PETALS);

        dropSelf(ModBlocks.DAWN_REDWOOD_LOG);
        dropSelf(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG);
        dropSelf(ModBlocks.DAWN_REDWOOD_WOOD);
        dropSelf(ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD);
        dropSelf(ModBlocks.DAWN_REDWOOD_PLANKS);
        dropSelf(ModBlocks.DAWN_REDWOOD_STAIRS);
        dropForSlab(ModBlocks.DAWN_REDWOOD_SLAB);
        dropSelf(ModBlocks.DAWN_REDWOOD_FENCE);
        dropSelf(ModBlocks.DAWN_REDWOOD_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.DAWN_REDWOOD_DOOR);
        dropSelf(ModBlocks.DAWN_REDWOOD_TRAPDOOR);
        dropSelf(ModBlocks.DAWN_REDWOOD_PRESSURE_PLATE);
        dropSelf(ModBlocks.DAWN_REDWOOD_BUTTON);
        dropOther(ModBlocks.DAWN_REDWOOD_SIGN, ModItems.DAWN_REDWOOD_SIGN);
        dropOther(ModBlocks.DAWN_REDWOOD_WALL_SIGN, ModItems.DAWN_REDWOOD_SIGN);
        dropOther(ModBlocks.DAWN_REDWOOD_HANGING_SIGN, ModItems.DAWN_REDWOOD_HANGING_SIGN);
        dropOther(ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN, ModItems.DAWN_REDWOOD_HANGING_SIGN);
        dropForLeavesWithLeafPile(ModBlocks.DAWN_REDWOOD_LEAVES, ModBlocks.DAWN_REDWOOD_SAPLING, ModItems.DAWN_REDWOOD_LEAF_LITTER);
        dropSelf(ModBlocks.DAWN_REDWOOD_SAPLING);
        dropPottedContents(ModBlocks.POTTED_DAWN_REDWOOD_SAPLING);
        dropForLeafLitter(ModBlocks.DAWN_REDWOOD_LEAF_LITTER);
        dropSelf(ModBlocks.DAWN_REDWOOD_ROOTS);

        dropSelf(ModBlocks.JACARANDA_LOG);
        dropSelf(ModBlocks.STRIPPED_JACARANDA_LOG);
        dropSelf(ModBlocks.JACARANDA_WOOD);
        dropSelf(ModBlocks.STRIPPED_JACARANDA_WOOD);
        dropSelf(ModBlocks.JACARANDA_PLANKS);
        dropSelf(ModBlocks.JACARANDA_STAIRS);
        dropForSlab(ModBlocks.JACARANDA_SLAB);
        dropSelf(ModBlocks.JACARANDA_FENCE);
        dropSelf(ModBlocks.JACARANDA_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.JACARANDA_DOOR);
        dropSelf(ModBlocks.JACARANDA_TRAPDOOR);
        dropSelf(ModBlocks.JACARANDA_PRESSURE_PLATE);
        dropSelf(ModBlocks.JACARANDA_BUTTON);
        dropOther(ModBlocks.JACARANDA_SIGN, ModItems.JACARANDA_SIGN);
        dropOther(ModBlocks.JACARANDA_WALL_SIGN, ModItems.JACARANDA_SIGN);
        dropOther(ModBlocks.JACARANDA_HANGING_SIGN, ModItems.JACARANDA_HANGING_SIGN);
        dropOther(ModBlocks.JACARANDA_WALL_HANGING_SIGN, ModItems.JACARANDA_HANGING_SIGN);
        dropForLeaves(ModBlocks.JACARANDA_LEAVES, ModBlocks.JACARANDA_SAPLING);
        dropSelf(ModBlocks.JACARANDA_SAPLING);
        dropPottedContents(ModBlocks.POTTED_JACARANDA_SAPLING);
        dropForPetals(ModBlocks.VIOLETS);
        dropForPetals(ModBlocks.BUTTERCUPS);
        dropForPetals(ModBlocks.FORGET_ME_NOTS);
        dropForPetals(ModBlocks.BABY_BLUE_EYES);
        dropForPetals(ModBlocks.SPEEDWELLS);
        dropForPetals(ModBlocks.WOOD_SORRELS);

        dropSelf(ModBlocks.WILLOW_LOG);
        dropSelf(ModBlocks.STRIPPED_WILLOW_LOG);
        dropSelf(ModBlocks.WILLOW_WOOD);
        dropSelf(ModBlocks.STRIPPED_WILLOW_WOOD);
        dropSelf(ModBlocks.WILLOW_PLANKS);
        dropSelf(ModBlocks.WILLOW_STAIRS);
        dropForSlab(ModBlocks.WILLOW_SLAB);
        dropSelf(ModBlocks.WILLOW_FENCE);
        dropSelf(ModBlocks.WILLOW_FENCE_GATE);
        dropForDoubleBlock(ModBlocks.WILLOW_DOOR);
        dropSelf(ModBlocks.WILLOW_TRAPDOOR);
        dropSelf(ModBlocks.WILLOW_PRESSURE_PLATE);
        dropSelf(ModBlocks.WILLOW_BUTTON);
        dropOther(ModBlocks.WILLOW_SIGN, ModItems.WILLOW_SIGN);
        dropOther(ModBlocks.WILLOW_WALL_SIGN, ModItems.WILLOW_SIGN);
        dropOther(ModBlocks.WILLOW_HANGING_SIGN, ModItems.WILLOW_HANGING_SIGN);
        dropOther(ModBlocks.WILLOW_WALL_HANGING_SIGN, ModItems.WILLOW_HANGING_SIGN);
        dropForLeaves(ModBlocks.WILLOW_LEAVES, ModBlocks.WILLOW_SAPLING);
        dropSelf(ModBlocks.WILLOW_SAPLING);
        dropPottedContents(ModBlocks.POTTED_WILLOW_SAPLING);
        dropWhenSheared(ModBlocks.WILLOW_BRANCHES);

        dropSelf(ModBlocks.PINK_DAISY);
        dropPottedContents(ModBlocks.POTTED_PINK_DAISY);
        dropSelf(ModBlocks.RED_CARNATION);
        dropPottedContents(ModBlocks.POTTED_RED_CARNATION);
        dropSelf(ModBlocks.PINK_CARNATION);
        dropPottedContents(ModBlocks.POTTED_PINK_CARNATION);
        dropSelf(ModBlocks.WHITE_CARNATION);
        dropPottedContents(ModBlocks.POTTED_WHITE_CARNATION);
        dropSelf(ModBlocks.RED_SPIDER_LILY);
        dropPottedContents(ModBlocks.POTTED_RED_SPIDER_LILY);
        dropSelf(ModBlocks.YELLOW_CHRYSANTHEMUM);
        dropPottedContents(ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM);
        dropSelf(ModBlocks.GREEN_CHRYSANTHEMUM);
        dropPottedContents(ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);
        dropSelf(ModBlocks.OPEN_DAYBLOOM);
        dropPottedContents(ModBlocks.POTTED_OPEN_DAYBLOOM);
        dropSelf(ModBlocks.CLOSED_DAYBLOOM);
        dropPottedContents(ModBlocks.POTTED_CLOSED_DAYBLOOM);
        dropSelf(ModBlocks.EDELWEISS);
        dropPottedContents(ModBlocks.POTTED_EDELWEISS);
        dropSelf(ModBlocks.CROCUS);
        dropPottedContents(ModBlocks.POTTED_CROCUS);
        dropSelf(ModBlocks.IRIS);
        dropPottedContents(ModBlocks.POTTED_IRIS);
        dropSelf(ModBlocks.LAVENDER);
        dropPottedContents(ModBlocks.POTTED_LAVENDER);
        dropSelf(ModBlocks.DAFFODIL);
        dropPottedContents(ModBlocks.POTTED_DAFFODIL);
        dropSelf(ModBlocks.GERBERA_DAISY);
        dropPottedContents(ModBlocks.POTTED_GERBERA_DAISY);
        dropSelf(ModBlocks.RAPESEED_FLOWER);
        dropPottedContents(ModBlocks.POTTED_RAPESEED_FLOWER);
        dropSelf(ModBlocks.WINDFLOWER);
        dropPottedContents(ModBlocks.POTTED_WINDFLOWER);

        dropForDoubleBlock(ModBlocks.CATTAIL);
        dropForDoubleBlock(ModBlocks.TALL_RAPESEED_FLOWER);

        dropForWaterGrass(ModBlocks.SHORT_WATER_GRASS);
        dropForWaterGrass(ModBlocks.TALL_WATER_GRASS);
        add(ModBlocks.REED, createReedDrops());
        dropBerries(ModBlocks.STRAWBERRY_BUSH, ModItems.STRAWBERRY);
        dropBerries(ModBlocks.BLUEBERRY_BUSH, ModItems.BLUEBERRIES);
        dropSelf(ModBlocks.OPEN_WATER_LILY);
        dropSelf(ModBlocks.OPEN_WHITE_WATER_LILY);
        dropSelf(ModBlocks.OPEN_BLUE_WATER_LILY);
        dropSelf(ModBlocks.CLOSED_WATER_LILY);
        dropSelf(ModBlocks.CLOSED_WHITE_WATER_LILY);
        dropSelf(ModBlocks.CLOSED_BLUE_WATER_LILY);
        dropForLeafLitter(ModBlocks.DUCKWEEDS);

        dropSelf(ModBlocks.FAN_BLOCK);
        dropSelf(ModBlocks.WEATHER_VANE);
        for (Block block : RibbonBlock.ALL_COLORS.get()) {
            dropSelf(block);
        }
        add(ModBlocks.PINWHEEL, createPinwheelDrop());
        for (Block block : PennantBlock.ALL_BLOCKS.get()) {
            dropSelf(block);
        }
        dropSelf(ModBlocks.SANDBAG);
        dropSelf(ModBlocks.PYROTECHNICS_TABLE);
        dropSelf(ModBlocks.UNDERWATER_TNT);
        for (Block block : PapercuttingBlock.ALL_BLOCKS.get()) {
            add(block, createPapercuttingDrop(block));
        }
        dropSelf(ModBlocks.PAPERCRAFT_TABLE);
        dropSelf(ModBlocks.CARDBOARD_BLOCK);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}
