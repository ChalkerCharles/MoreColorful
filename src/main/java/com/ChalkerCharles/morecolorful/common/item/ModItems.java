package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.misc.DuckweedsItem;
import com.ChalkerCharles.morecolorful.common.item.misc.PaperPlaneItem;
import com.ChalkerCharles.morecolorful.common.item.musical.*;
import com.ChalkerCharles.morecolorful.util.EnumExtensions;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModItems {
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreColorful.MODID);
    // Blocks
    public static final DeferredItem<BlockItem> HARP = registerBlockItem(ModBlocks.HARP);
    public static final DeferredItem<BlockItem> UPRIGHT_PIANO = registerBlockItem(ModBlocks.UPRIGHT_PIANO);
    public static final DeferredItem<BlockItem> GRAND_PIANO = registerBlockItem(ModBlocks.GRAND_PIANO);
    public static final DeferredItem<BlockItem> BASS_DRUM = registerBlockItem(ModBlocks.BASS_DRUM);
    public static final DeferredItem<BlockItem> SNARE_DRUM = registerBlockItem(ModBlocks.SNARE_DRUM);
    public static final DeferredItem<BlockItem> TOMTOM_DRUM = registerBlockItem(ModBlocks.TOMTOM_DRUM);
    public static final DeferredItem<BlockItem> HIHAT = registerBlockItem(ModBlocks.HIHAT);
    public static final DeferredItem<BlockItem> RIDE_CYMBAL = registerBlockItem(ModBlocks.RIDE_CYMBAL);
    public static final DeferredItem<BlockItem> CRASH_CYMBAL = registerBlockItem(ModBlocks.CRASH_CYMBAL);
    public static final DeferredItem<BlockItem> DRUM_SET = registerBlockItem(ModBlocks.DRUM_SET);
    public static final DeferredItem<BlockItem> CHIMES = registerBlockItem(ModBlocks.CHIMES);
    public static final DeferredItem<BlockItem> GLOCKENSPIEL = registerBlockItem(ModBlocks.GLOCKENSPIEL);
    public static final DeferredItem<BlockItem> XYLOPHONE = registerBlockItem(ModBlocks.XYLOPHONE);
    public static final DeferredItem<BlockItem> VIBRAPHONE = registerBlockItem(ModBlocks.VIBRAPHONE);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_BIT = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_BIT);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLING = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_PLING);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SCULK = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_AMETHYST = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SAW = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SAW);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLUCK = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SYNTH_BASS = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
    public static final DeferredItem<BlockItem> GUZHENG = registerBlockItem(ModBlocks.GUZHENG);

    public static final DeferredItem<BlockItem> CRABAPPLE_LOG = registerBlockItem(ModBlocks.CRABAPPLE_LOG);
    public static final DeferredItem<BlockItem> CRABAPPLE_WOOD = registerBlockItem(ModBlocks.CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_CRABAPPLE_LOG = registerBlockItem(ModBlocks.STRIPPED_CRABAPPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_CRABAPPLE_WOOD = registerBlockItem(ModBlocks.STRIPPED_CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> CRABAPPLE_PLANKS = registerBlockItem(ModBlocks.CRABAPPLE_PLANKS);
    public static final DeferredItem<BlockItem> CRABAPPLE_STAIRS = registerBlockItem(ModBlocks.CRABAPPLE_STAIRS);
    public static final DeferredItem<BlockItem> CRABAPPLE_SLAB = registerBlockItem(ModBlocks.CRABAPPLE_SLAB);
    public static final DeferredItem<BlockItem> CRABAPPLE_FENCE = registerBlockItem(ModBlocks.CRABAPPLE_FENCE);
    public static final DeferredItem<BlockItem> CRABAPPLE_FENCE_GATE = registerBlockItem(ModBlocks.CRABAPPLE_FENCE_GATE);
    public static final DeferredItem<BlockItem> CRABAPPLE_DOOR = registerBlockItem(ModBlocks.CRABAPPLE_DOOR);
    public static final DeferredItem<BlockItem> CRABAPPLE_TRAPDOOR = registerBlockItem(ModBlocks.CRABAPPLE_TRAPDOOR);
    public static final DeferredItem<BlockItem> CRABAPPLE_PRESSURE_PLATE = registerBlockItem(ModBlocks.CRABAPPLE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> CRABAPPLE_BUTTON = registerBlockItem(ModBlocks.CRABAPPLE_BUTTON);
    public static final DeferredItem<BlockItem> CRABAPPLE_SIGN = registerSign(ModBlocks.CRABAPPLE_SIGN, ModBlocks.CRABAPPLE_WALL_SIGN);
    public static final DeferredItem<BlockItem> CRABAPPLE_HANGING_SIGN = registerHangingSign(ModBlocks.CRABAPPLE_HANGING_SIGN, ModBlocks.CRABAPPLE_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> EBONY_LOG = registerBlockItem(ModBlocks.EBONY_LOG);
    public static final DeferredItem<BlockItem> EBONY_WOOD = registerBlockItem(ModBlocks.EBONY_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_EBONY_LOG = registerBlockItem(ModBlocks.STRIPPED_EBONY_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_EBONY_WOOD = registerBlockItem(ModBlocks.STRIPPED_EBONY_WOOD);
    public static final DeferredItem<BlockItem> EBONY_PLANKS = registerBlockItem(ModBlocks.EBONY_PLANKS);
    public static final DeferredItem<BlockItem> EBONY_STAIRS = registerBlockItem(ModBlocks.EBONY_STAIRS);
    public static final DeferredItem<BlockItem> EBONY_SLAB = registerBlockItem(ModBlocks.EBONY_SLAB);
    public static final DeferredItem<BlockItem> EBONY_FENCE = registerBlockItem(ModBlocks.EBONY_FENCE);
    public static final DeferredItem<BlockItem> EBONY_FENCE_GATE = registerBlockItem(ModBlocks.EBONY_FENCE_GATE);
    public static final DeferredItem<BlockItem> EBONY_DOOR = registerBlockItem(ModBlocks.EBONY_DOOR);
    public static final DeferredItem<BlockItem> EBONY_TRAPDOOR = registerBlockItem(ModBlocks.EBONY_TRAPDOOR);
    public static final DeferredItem<BlockItem> EBONY_PRESSURE_PLATE = registerBlockItem(ModBlocks.EBONY_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> EBONY_BUTTON = registerBlockItem(ModBlocks.EBONY_BUTTON);
    public static final DeferredItem<BlockItem> EBONY_SIGN = registerSign(ModBlocks.EBONY_SIGN, ModBlocks.EBONY_WALL_SIGN);
    public static final DeferredItem<BlockItem> EBONY_HANGING_SIGN = registerHangingSign(ModBlocks.EBONY_HANGING_SIGN, ModBlocks.EBONY_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> GINKGO_LOG = registerBlockItem(ModBlocks.GINKGO_LOG);
    public static final DeferredItem<BlockItem> GINKGO_WOOD = registerBlockItem(ModBlocks.GINKGO_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_GINKGO_LOG = registerBlockItem(ModBlocks.STRIPPED_GINKGO_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_GINKGO_WOOD = registerBlockItem(ModBlocks.STRIPPED_GINKGO_WOOD);
    public static final DeferredItem<BlockItem> GINKGO_PLANKS = registerBlockItem(ModBlocks.GINKGO_PLANKS);
    public static final DeferredItem<BlockItem> GINKGO_STAIRS = registerBlockItem(ModBlocks.GINKGO_STAIRS);
    public static final DeferredItem<BlockItem> GINKGO_SLAB = registerBlockItem(ModBlocks.GINKGO_SLAB);
    public static final DeferredItem<BlockItem> GINKGO_FENCE = registerBlockItem(ModBlocks.GINKGO_FENCE);
    public static final DeferredItem<BlockItem> GINKGO_FENCE_GATE = registerBlockItem(ModBlocks.GINKGO_FENCE_GATE);
    public static final DeferredItem<BlockItem> GINKGO_DOOR = registerBlockItem(ModBlocks.GINKGO_DOOR);
    public static final DeferredItem<BlockItem> GINKGO_TRAPDOOR = registerBlockItem(ModBlocks.GINKGO_TRAPDOOR);
    public static final DeferredItem<BlockItem> GINKGO_PRESSURE_PLATE = registerBlockItem(ModBlocks.GINKGO_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> GINKGO_BUTTON = registerBlockItem(ModBlocks.GINKGO_BUTTON);
    public static final DeferredItem<BlockItem> GINKGO_SIGN = registerSign(ModBlocks.GINKGO_SIGN, ModBlocks.GINKGO_WALL_SIGN);
    public static final DeferredItem<BlockItem> GINKGO_HANGING_SIGN = registerHangingSign(ModBlocks.GINKGO_HANGING_SIGN, ModBlocks.GINKGO_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> MAPLE_LOG = registerBlockItem(ModBlocks.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = registerBlockItem(ModBlocks.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = registerBlockItem(ModBlocks.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = registerBlockItem(ModBlocks.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<BlockItem> MAPLE_PLANKS = registerBlockItem(ModBlocks.MAPLE_PLANKS);
    public static final DeferredItem<BlockItem> MAPLE_STAIRS = registerBlockItem(ModBlocks.MAPLE_STAIRS);
    public static final DeferredItem<BlockItem> MAPLE_SLAB = registerBlockItem(ModBlocks.MAPLE_SLAB);
    public static final DeferredItem<BlockItem> MAPLE_FENCE = registerBlockItem(ModBlocks.MAPLE_FENCE);
    public static final DeferredItem<BlockItem> MAPLE_FENCE_GATE = registerBlockItem(ModBlocks.MAPLE_FENCE_GATE);
    public static final DeferredItem<BlockItem> MAPLE_DOOR = registerBlockItem(ModBlocks.MAPLE_DOOR);
    public static final DeferredItem<BlockItem> MAPLE_TRAPDOOR = registerBlockItem(ModBlocks.MAPLE_TRAPDOOR);
    public static final DeferredItem<BlockItem> MAPLE_PRESSURE_PLATE = registerBlockItem(ModBlocks.MAPLE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> MAPLE_BUTTON = registerBlockItem(ModBlocks.MAPLE_BUTTON);
    public static final DeferredItem<BlockItem> MAPLE_SIGN = registerSign(ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN);
    public static final DeferredItem<BlockItem> MAPLE_HANGING_SIGN = registerHangingSign(ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> FROST_LOG = registerBlockItem(ModBlocks.FROST_LOG);
    public static final DeferredItem<BlockItem> FROST_WOOD = registerBlockItem(ModBlocks.FROST_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_FROST_LOG = registerBlockItem(ModBlocks.STRIPPED_FROST_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_FROST_WOOD = registerBlockItem(ModBlocks.STRIPPED_FROST_WOOD);
    public static final DeferredItem<BlockItem> FROST_PLANKS = registerBlockItem(ModBlocks.FROST_PLANKS);
    public static final DeferredItem<BlockItem> FROST_STAIRS = registerBlockItem(ModBlocks.FROST_STAIRS);
    public static final DeferredItem<BlockItem> FROST_SLAB = registerBlockItem(ModBlocks.FROST_SLAB);
    public static final DeferredItem<BlockItem> FROST_FENCE = registerBlockItem(ModBlocks.FROST_FENCE);
    public static final DeferredItem<BlockItem> FROST_FENCE_GATE = registerBlockItem(ModBlocks.FROST_FENCE_GATE);
    public static final DeferredItem<BlockItem> FROST_DOOR = registerBlockItem(ModBlocks.FROST_DOOR);
    public static final DeferredItem<BlockItem> FROST_TRAPDOOR = registerBlockItem(ModBlocks.FROST_TRAPDOOR);
    public static final DeferredItem<BlockItem> FROST_PRESSURE_PLATE = registerBlockItem(ModBlocks.FROST_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> FROST_BUTTON = registerBlockItem(ModBlocks.FROST_BUTTON);
    public static final DeferredItem<BlockItem> FROST_SIGN = registerSign(ModBlocks.FROST_SIGN, ModBlocks.FROST_WALL_SIGN);
    public static final DeferredItem<BlockItem> FROST_HANGING_SIGN = registerHangingSign(ModBlocks.FROST_HANGING_SIGN, ModBlocks.FROST_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_LOG = registerBlockItem(ModBlocks.DAWN_REDWOOD_LOG);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_WOOD = registerBlockItem(ModBlocks.DAWN_REDWOOD_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_DAWN_REDWOOD_LOG = registerBlockItem(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_DAWN_REDWOOD_WOOD = registerBlockItem(ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_PLANKS = registerBlockItem(ModBlocks.DAWN_REDWOOD_PLANKS);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_STAIRS = registerBlockItem(ModBlocks.DAWN_REDWOOD_STAIRS);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_SLAB = registerBlockItem(ModBlocks.DAWN_REDWOOD_SLAB);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_FENCE = registerBlockItem(ModBlocks.DAWN_REDWOOD_FENCE);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_FENCE_GATE = registerBlockItem(ModBlocks.DAWN_REDWOOD_FENCE_GATE);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_DOOR = registerBlockItem(ModBlocks.DAWN_REDWOOD_DOOR);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_TRAPDOOR = registerBlockItem(ModBlocks.DAWN_REDWOOD_TRAPDOOR);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_PRESSURE_PLATE = registerBlockItem(ModBlocks.DAWN_REDWOOD_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_BUTTON = registerBlockItem(ModBlocks.DAWN_REDWOOD_BUTTON);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_SIGN = registerSign(ModBlocks.DAWN_REDWOOD_SIGN, ModBlocks.DAWN_REDWOOD_WALL_SIGN);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_HANGING_SIGN = registerHangingSign(ModBlocks.DAWN_REDWOOD_HANGING_SIGN, ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> JACARANDA_LOG = registerBlockItem(ModBlocks.JACARANDA_LOG);
    public static final DeferredItem<BlockItem> JACARANDA_WOOD = registerBlockItem(ModBlocks.JACARANDA_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_JACARANDA_LOG = registerBlockItem(ModBlocks.STRIPPED_JACARANDA_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_JACARANDA_WOOD = registerBlockItem(ModBlocks.STRIPPED_JACARANDA_WOOD);
    public static final DeferredItem<BlockItem> JACARANDA_PLANKS = registerBlockItem(ModBlocks.JACARANDA_PLANKS);
    public static final DeferredItem<BlockItem> JACARANDA_STAIRS = registerBlockItem(ModBlocks.JACARANDA_STAIRS);
    public static final DeferredItem<BlockItem> JACARANDA_SLAB = registerBlockItem(ModBlocks.JACARANDA_SLAB);
    public static final DeferredItem<BlockItem> JACARANDA_FENCE = registerBlockItem(ModBlocks.JACARANDA_FENCE);
    public static final DeferredItem<BlockItem> JACARANDA_FENCE_GATE = registerBlockItem(ModBlocks.JACARANDA_FENCE_GATE);
    public static final DeferredItem<BlockItem> JACARANDA_DOOR = registerBlockItem(ModBlocks.JACARANDA_DOOR);
    public static final DeferredItem<BlockItem> JACARANDA_TRAPDOOR = registerBlockItem(ModBlocks.JACARANDA_TRAPDOOR);
    public static final DeferredItem<BlockItem> JACARANDA_PRESSURE_PLATE = registerBlockItem(ModBlocks.JACARANDA_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> JACARANDA_BUTTON = registerBlockItem(ModBlocks.JACARANDA_BUTTON);
    public static final DeferredItem<BlockItem> JACARANDA_SIGN = registerSign(ModBlocks.JACARANDA_SIGN, ModBlocks.JACARANDA_WALL_SIGN);
    public static final DeferredItem<BlockItem> JACARANDA_HANGING_SIGN = registerHangingSign(ModBlocks.JACARANDA_HANGING_SIGN, ModBlocks.JACARANDA_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> WILLOW_LOG = registerBlockItem(ModBlocks.WILLOW_LOG);
    public static final DeferredItem<BlockItem> WILLOW_WOOD = registerBlockItem(ModBlocks.WILLOW_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_WILLOW_LOG = registerBlockItem(ModBlocks.STRIPPED_WILLOW_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_WILLOW_WOOD = registerBlockItem(ModBlocks.STRIPPED_WILLOW_WOOD);
    public static final DeferredItem<BlockItem> WILLOW_PLANKS = registerBlockItem(ModBlocks.WILLOW_PLANKS);
    public static final DeferredItem<BlockItem> WILLOW_STAIRS = registerBlockItem(ModBlocks.WILLOW_STAIRS);
    public static final DeferredItem<BlockItem> WILLOW_SLAB = registerBlockItem(ModBlocks.WILLOW_SLAB);
    public static final DeferredItem<BlockItem> WILLOW_FENCE = registerBlockItem(ModBlocks.WILLOW_FENCE);
    public static final DeferredItem<BlockItem> WILLOW_FENCE_GATE = registerBlockItem(ModBlocks.WILLOW_FENCE_GATE);
    public static final DeferredItem<BlockItem> WILLOW_DOOR = registerBlockItem(ModBlocks.WILLOW_DOOR);
    public static final DeferredItem<BlockItem> WILLOW_TRAPDOOR = registerBlockItem(ModBlocks.WILLOW_TRAPDOOR);
    public static final DeferredItem<BlockItem> WILLOW_PRESSURE_PLATE = registerBlockItem(ModBlocks.WILLOW_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> WILLOW_BUTTON = registerBlockItem(ModBlocks.WILLOW_BUTTON);
    public static final DeferredItem<BlockItem> WILLOW_SIGN = registerSign(ModBlocks.WILLOW_SIGN, ModBlocks.WILLOW_WALL_SIGN);
    public static final DeferredItem<BlockItem> WILLOW_HANGING_SIGN = registerHangingSign(ModBlocks.WILLOW_HANGING_SIGN, ModBlocks.WILLOW_WALL_HANGING_SIGN);

    public static final DeferredItem<BlockItem> CRABAPPLE_LEAVES = registerBlockItem(ModBlocks.CRABAPPLE_LEAVES);
    public static final DeferredItem<BlockItem> CRABAPPLE_SAPLING = registerBlockItem(ModBlocks.CRABAPPLE_SAPLING);
    public static final DeferredItem<BlockItem> BEGONIAS = registerBlockItem(ModBlocks.BEGONIAS);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_LEAVES = registerBlockItem(ModBlocks.WHITE_CHERRY_LEAVES);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_SAPLING = registerBlockItem(ModBlocks.WHITE_CHERRY_SAPLING);
    public static final DeferredItem<BlockItem> WHITE_PETALS = registerBlockItem(ModBlocks.WHITE_PETALS);
    public static final DeferredItem<BlockItem> ORANGE_BIRCH_LEAVES = registerBlockItem(ModBlocks.ORANGE_BIRCH_LEAVES);
    public static final DeferredItem<BlockItem> ORANGE_BIRCH_SAPLING = registerBlockItem(ModBlocks.ORANGE_BIRCH_SAPLING);
    public static final DeferredItem<BlockItem> ORANGE_BIRCH_LEAF_LITTER = registerBlockItem(ModBlocks.ORANGE_BIRCH_LEAF_LITTER);
    public static final DeferredItem<BlockItem> YELLOW_BIRCH_LEAVES = registerBlockItem(ModBlocks.YELLOW_BIRCH_LEAVES);
    public static final DeferredItem<BlockItem> YELLOW_BIRCH_SAPLING = registerBlockItem(ModBlocks.YELLOW_BIRCH_SAPLING);
    public static final DeferredItem<BlockItem> YELLOW_BIRCH_LEAF_LITTER = registerBlockItem(ModBlocks.YELLOW_BIRCH_LEAF_LITTER);
    public static final DeferredItem<BlockItem> GINKGO_LEAVES = registerBlockItem(ModBlocks.GINKGO_LEAVES);
    public static final DeferredItem<BlockItem> GINKGO_SAPLING = registerBlockItem(ModBlocks.GINKGO_SAPLING);
    public static final DeferredItem<BlockItem> GINKGO_LEAF_LITTER = registerBlockItem(ModBlocks.GINKGO_LEAF_LITTER);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = registerBlockItem(ModBlocks.MAPLE_LEAVES);
    public static final DeferredItem<BlockItem> MAPLE_SAPLING = registerBlockItem(ModBlocks.MAPLE_SAPLING);
    public static final DeferredItem<BlockItem> MAPLE_LEAF_LITTER = registerBlockItem(ModBlocks.MAPLE_LEAF_LITTER);
    public static final DeferredItem<BlockItem> FROST_LEAVES = registerBlockItem(ModBlocks.FROST_LEAVES);
    public static final DeferredItem<BlockItem> FROST_SAPLING = registerBlockItem(ModBlocks.FROST_SAPLING);
    public static final DeferredItem<BlockItem> FROSTY_PETALS = registerBlockItem(ModBlocks.FROSTY_PETALS);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_LEAVES = registerBlockItem(ModBlocks.DAWN_REDWOOD_LEAVES);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_SAPLING = registerBlockItem(ModBlocks.DAWN_REDWOOD_SAPLING);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_LEAF_LITTER = registerBlockItem(ModBlocks.DAWN_REDWOOD_LEAF_LITTER);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_ROOTS = registerBlockItem(ModBlocks.DAWN_REDWOOD_ROOTS);
    public static final DeferredItem<BlockItem> JACARANDA_LEAVES = registerBlockItem(ModBlocks.JACARANDA_LEAVES);
    public static final DeferredItem<BlockItem> JACARANDA_SAPLING = registerBlockItem(ModBlocks.JACARANDA_SAPLING);
    public static final DeferredItem<BlockItem> VIOLETS = registerBlockItem(ModBlocks.VIOLETS);
    public static final DeferredItem<BlockItem> BUTTERCUPS = registerBlockItem(ModBlocks.BUTTERCUPS);
    public static final DeferredItem<BlockItem> FORGET_ME_NOTS = registerBlockItem(ModBlocks.FORGET_ME_NOTS);
    public static final DeferredItem<BlockItem> BABY_BLUE_EYES = registerBlockItem(ModBlocks.BABY_BLUE_EYES);
    public static final DeferredItem<BlockItem> SPEEDWELLS = registerBlockItem(ModBlocks.SPEEDWELLS);
    public static final DeferredItem<BlockItem> WOOD_SORRELS = registerBlockItem(ModBlocks.WOOD_SORRELS);
    public static final DeferredItem<BlockItem> WILLOW_LEAVES = registerBlockItem(ModBlocks.WILLOW_LEAVES);
    public static final DeferredItem<BlockItem> WILLOW_SAPLING = registerBlockItem(ModBlocks.WILLOW_SAPLING);
    public static final DeferredItem<BlockItem> WILLOW_BRANCHES = registerBlockItem(ModBlocks.WILLOW_BRANCHES);

    public static final DeferredItem<BlockItem> PINK_DAISY = registerBlockItem(ModBlocks.PINK_DAISY);
    public static final DeferredItem<BlockItem> RED_CARNATION = registerBlockItem(ModBlocks.RED_CARNATION);
    public static final DeferredItem<BlockItem> PINK_CARNATION = registerBlockItem(ModBlocks.PINK_CARNATION);
    public static final DeferredItem<BlockItem> WHITE_CARNATION = registerBlockItem(ModBlocks.WHITE_CARNATION);
    public static final DeferredItem<BlockItem> RED_SPIDER_LILY = registerBlockItem(ModBlocks.RED_SPIDER_LILY);
    public static final DeferredItem<BlockItem> YELLOW_CHRYSANTHEMUM = registerBlockItem(ModBlocks.YELLOW_CHRYSANTHEMUM);
    public static final DeferredItem<BlockItem> GREEN_CHRYSANTHEMUM = registerBlockItem(ModBlocks.GREEN_CHRYSANTHEMUM);
    public static final DeferredItem<BlockItem> OPEN_DAYBLOOM = registerBlockItem(ModBlocks.OPEN_DAYBLOOM);
    public static final DeferredItem<BlockItem> CLOSED_DAYBLOOM = registerBlockItem(ModBlocks.CLOSED_DAYBLOOM);
    public static final DeferredItem<BlockItem> EDELWEISS = registerBlockItem(ModBlocks.EDELWEISS);
    public static final DeferredItem<BlockItem> CROCUS = registerBlockItem(ModBlocks.CROCUS);
    public static final DeferredItem<BlockItem> IRIS = registerBlockItem(ModBlocks.IRIS);
    public static final DeferredItem<BlockItem> LAVENDER = registerBlockItem(ModBlocks.LAVENDER);
    public static final DeferredItem<BlockItem> DAFFODIL = registerBlockItem(ModBlocks.DAFFODIL);
    public static final DeferredItem<BlockItem> GERBERA_DAISY = registerBlockItem(ModBlocks.GERBERA_DAISY);
    public static final DeferredItem<BlockItem> RAPESEED_FLOWER = registerBlockItem(ModBlocks.RAPESEED_FLOWER);
    public static final DeferredItem<BlockItem> WINDFLOWER = registerBlockItem(ModBlocks.WINDFLOWER);

    public static final DeferredItem<BlockItem> CATTAIL = registerBlockItem(ModBlocks.CATTAIL);
    public static final DeferredItem<BlockItem> TALL_RAPESEED_FLOWER = registerBlockItem(ModBlocks.TALL_RAPESEED_FLOWER);

    public static final DeferredItem<BlockItem> SHORT_WATER_GRASS = registerBlockItem(ModBlocks.SHORT_WATER_GRASS);
    public static final DeferredItem<BlockItem> TALL_WATER_GRASS = registerBlockItem(ModBlocks.TALL_WATER_GRASS);
    public static final DeferredItem<BlockItem> REED = registerBlockItem(ModBlocks.REED);
    public static final DeferredItem<BlockItem> OPEN_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.OPEN_WATER_LILY);
    public static final DeferredItem<BlockItem> OPEN_WHITE_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.OPEN_WHITE_WATER_LILY);
    public static final DeferredItem<BlockItem> OPEN_BLUE_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.OPEN_BLUE_WATER_LILY);
    public static final DeferredItem<BlockItem> CLOSED_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.CLOSED_WATER_LILY);
    public static final DeferredItem<BlockItem> CLOSED_WHITE_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.CLOSED_WHITE_WATER_LILY);
    public static final DeferredItem<BlockItem> CLOSED_BLUE_WATER_LILY = registerPlaceOnWaterItem(ModBlocks.CLOSED_BLUE_WATER_LILY);
    public static final DeferredItem<BlockItem> DUCKWEEDS = registerBlockItem(ModBlocks.DUCKWEEDS, () -> new DuckweedsItem(ModBlocks.DUCKWEEDS.get(), properties()));

    public static final DeferredItem<BlockItem> FAN_BLOCK = registerBlockItem(ModBlocks.FAN_BLOCK);
    public static final DeferredItem<BlockItem> WEATHER_VANE = registerBlockItem(ModBlocks.WEATHER_VANE);

    // Items
    public static final DeferredItem<Item> VIOLIN = register("violin", () -> new BowedStringInstrumentItem(InstrumentsType.VIOLIN, properties().stacksTo(1)));
    public static final DeferredItem<Item> FIDDLE_BOW = register("fiddle_bow", () -> new Item(properties().stacksTo(1)));
    public static final DeferredItem<Item> DRUMSTICK = register("drumstick", () -> new DrumstickItem(properties()));
    public static final DeferredItem<Item> BASS = register("bass", () -> new GuitarItem(InstrumentsType.BASS, properties().stacksTo(1)));
    public static final DeferredItem<Item> GUITAR = register("guitar", () -> new GuitarItem(InstrumentsType.GUITAR, properties().stacksTo(1)));
    public static final DeferredItem<Item> FLUTE = register("flute", () -> new BothHandsInstrumentItem(InstrumentsType.FLUTE, properties().stacksTo(1)));
    public static final DeferredItem<Item> COW_BELL = register("cow_bell", () -> new CowBellItem(InstrumentsType.COW_BELL, properties().stacksTo(1)));
    public static final DeferredItem<Item> DIDGERIDOO = register("didgeridoo", () -> new DidgeridooItem(InstrumentsType.DIDGERIDOO, properties().stacksTo(1)));
    public static final DeferredItem<Item> BANJO = register("banjo", () -> new GuitarItem(InstrumentsType.BANJO, properties().stacksTo(1)));
    public static final DeferredItem<Item> CELLO = register("cello", () -> new BowedStringInstrumentItem(InstrumentsType.CELLO, properties().stacksTo(1)));
    public static final DeferredItem<Item> ELECTRIC_GUITAR = register("electric_guitar", () -> new GuitarItem(InstrumentsType.ELECTRIC_GUITAR, properties().stacksTo(1)));
    public static final DeferredItem<Item> TRUMPET = register("trumpet", () -> new TrumpetItem(InstrumentsType.TRUMPET, properties().stacksTo(1)));
    public static final DeferredItem<Item> SAXOPHONE = register("saxophone", () -> new DidgeridooItem(InstrumentsType.SAXOPHONE, properties().stacksTo(1)));
    public static final DeferredItem<Item> OCARINA = register("ocarina", () -> new DidgeridooItem(InstrumentsType.OCARINA, properties().stacksTo(1)));
    public static final DeferredItem<Item> HARMONICA = register("harmonica", () -> new DidgeridooItem(InstrumentsType.HARMONICA, properties().stacksTo(1)));
    public static final DeferredItem<Item> PIPA = register("pipa", () -> new BothHandsInstrumentItem(InstrumentsType.PIPA, properties().stacksTo(1)));
    public static final DeferredItem<Item> ERHU = register("erhu", () -> new BowedStringInstrumentItem(InstrumentsType.ERHU, properties().stacksTo(1)));

    public static final DeferredItem<Item> CRABAPPLE_BOAT = register("crabapple_boat", () -> new BoatItem(false, EnumExtensions.BoatType.CRABAPPLE.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> CRABAPPLE_CHEST_BOAT = register("crabapple_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.CRABAPPLE.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> EBONY_BOAT = register("ebony_boat", () -> new BoatItem(false, EnumExtensions.BoatType.EBONY.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = register("ebony_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.EBONY.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> GINKGO_BOAT = register("ginkgo_boat", () -> new BoatItem(false, EnumExtensions.BoatType.GINKGO.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> GINKGO_CHEST_BOAT = register("ginkgo_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.GINKGO.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_BOAT = register("maple_boat", () -> new BoatItem(false, EnumExtensions.BoatType.MAPLE.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = register("maple_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.MAPLE.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> FROST_BOAT = register("frost_boat", () -> new BoatItem(false, EnumExtensions.BoatType.FROST.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> FROST_CHEST_BOAT = register("frost_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.FROST.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> DAWN_REDWOOD_BOAT = register("dawn_redwood_boat", () -> new BoatItem(false, EnumExtensions.BoatType.DAWN_REDWOOD.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> DAWN_REDWOOD_CHEST_BOAT = register("dawn_redwood_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.DAWN_REDWOOD.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> JACARANDA_BOAT = register("jacaranda_boat", () -> new BoatItem(false, EnumExtensions.BoatType.JACARANDA.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> JACARANDA_CHEST_BOAT = register("jacaranda_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.JACARANDA.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> WILLOW_BOAT = register("willow_boat", () -> new BoatItem(false, EnumExtensions.BoatType.WILLOW.getValue(), properties().stacksTo(1)));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = register("willow_chest_boat", () -> new BoatItem(true, EnumExtensions.BoatType.WILLOW.getValue(), properties().stacksTo(1)));

    public static final DeferredItem<Item> STRAWBERRY = register("strawberry", () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_BUSH.get(), properties().food(ModFoods.STRAWBERRY)));
    public static final DeferredItem<Item> BLUEBERRIES = register("blueberries", () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_BUSH.get(), properties().food(ModFoods.BLUEBERRY)));
    
    public static final DeferredItem<Item> PAPER_PLANE = register("paper_plane", () -> new PaperPlaneItem(properties().stacksTo(16)));

    private static DeferredItem<Item> register(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }
    
    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(block);
    }
    
    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<? extends Block> block, Supplier<BlockItem> blockItem) {
        return ITEMS.register(block.getId().getPath(), blockItem);
    }
    
    private static DeferredItem<BlockItem> registerSynthesizerKeyboard(DeferredBlock<Block> block) {
        return registerBlockItem(block, () -> new SynthesizerKeyboardItem(block.get(), properties()));
    }
    
    private static DeferredItem<BlockItem> registerSign(DeferredBlock<StandingSignBlock> sign, DeferredBlock<WallSignBlock> wallSign) {
        return registerBlockItem(sign, () -> new SignItem(properties().stacksTo(16), sign.get(), wallSign.get()));
    }
    
    private static DeferredItem<BlockItem> registerHangingSign(DeferredBlock<CeilingHangingSignBlock> hangingSign, DeferredBlock<WallHangingSignBlock> wallHangingSign) {
        return registerBlockItem(hangingSign,  () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), properties().stacksTo(16)));
    }
    
    private static DeferredItem<BlockItem> registerPlaceOnWaterItem(DeferredBlock<Block> block) {
        return registerBlockItem(block, () -> new PlaceOnWaterBlockItem(block.get(), properties()));
    }
    
    private static Item.Properties properties() {
        return new Item.Properties();
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}