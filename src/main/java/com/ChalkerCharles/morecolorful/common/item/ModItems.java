package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.entity.BoatTypeExtension;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.ChalkerCharles.morecolorful.common.item.component.*;
import com.ChalkerCharles.morecolorful.common.item.misc.*;
import com.ChalkerCharles.morecolorful.common.item.musical.*;
import com.ChalkerCharles.morecolorful.common.item.utility.*;
import com.ChalkerCharles.morecolorful.util.Colour;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
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
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_BIT = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_BIT);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLING = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_PLING);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SCULK = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_AMETHYST = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SAW = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_SAW);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLUCK = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SYNTH_BASS = registerBlockItem(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
    public static final DeferredItem<BlockItem> GUZHENG = registerBlockItem(ModBlocks.GUZHENG);
    public static final DeferredItem<BlockItem> MUSIC_BOX = registerBlockItem(ModBlocks.MUSIC_BOX);

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

    public static final DeferredItem<BlockItem> WHITE_RIBBON = registerBlockItem(ModBlocks.WHITE_RIBBON);
    public static final DeferredItem<BlockItem> ORANGE_RIBBON = registerBlockItem(ModBlocks.ORANGE_RIBBON);
    public static final DeferredItem<BlockItem> MAGENTA_RIBBON = registerBlockItem(ModBlocks.MAGENTA_RIBBON);
    public static final DeferredItem<BlockItem> LIGHT_BLUE_RIBBON = registerBlockItem(ModBlocks.LIGHT_BLUE_RIBBON);
    public static final DeferredItem<BlockItem> YELLOW_RIBBON = registerBlockItem(ModBlocks.YELLOW_RIBBON);
    public static final DeferredItem<BlockItem> LIME_RIBBON = registerBlockItem(ModBlocks.LIME_RIBBON);
    public static final DeferredItem<BlockItem> PINK_RIBBON = registerBlockItem(ModBlocks.PINK_RIBBON);
    public static final DeferredItem<BlockItem> GRAY_RIBBON = registerBlockItem(ModBlocks.GRAY_RIBBON);
    public static final DeferredItem<BlockItem> LIGHT_GRAY_RIBBON = registerBlockItem(ModBlocks.LIGHT_GRAY_RIBBON);
    public static final DeferredItem<BlockItem> CYAN_RIBBON = registerBlockItem(ModBlocks.CYAN_RIBBON);
    public static final DeferredItem<BlockItem> PURPLE_RIBBON = registerBlockItem(ModBlocks.PURPLE_RIBBON);
    public static final DeferredItem<BlockItem> BLUE_RIBBON = registerBlockItem(ModBlocks.BLUE_RIBBON);
    public static final DeferredItem<BlockItem> BROWN_RIBBON = registerBlockItem(ModBlocks.BROWN_RIBBON);
    public static final DeferredItem<BlockItem> GREEN_RIBBON = registerBlockItem(ModBlocks.GREEN_RIBBON);
    public static final DeferredItem<BlockItem> RED_RIBBON = registerBlockItem(ModBlocks.RED_RIBBON);
    public static final DeferredItem<BlockItem> BLACK_RIBBON = registerBlockItem(ModBlocks.BLACK_RIBBON);

    public static final DeferredItem<BlockItem> FAN_BLOCK = registerBlockItem(ModBlocks.FAN_BLOCK);
    public static final DeferredItem<BlockItem> WEATHER_VANE = registerBlockItem(ModBlocks.WEATHER_VANE);
    public static final DeferredItem<BlockItem> PINWHEEL = registerBlockItem(ModBlocks.PINWHEEL, () -> new BlockItem(ModBlocks.PINWHEEL.get(), pinwheelProperties()));
    public static final DeferredItem<BlockItem> WHITE_PENNANT = registerBlockItem(ModBlocks.WHITE_PENNANT);
    public static final DeferredItem<BlockItem> ORANGE_PENNANT = registerBlockItem(ModBlocks.ORANGE_PENNANT);
    public static final DeferredItem<BlockItem> MAGENTA_PENNANT = registerBlockItem(ModBlocks.MAGENTA_PENNANT);
    public static final DeferredItem<BlockItem> LIGHT_BLUE_PENNANT = registerBlockItem(ModBlocks.LIGHT_BLUE_PENNANT);
    public static final DeferredItem<BlockItem> YELLOW_PENNANT = registerBlockItem(ModBlocks.YELLOW_PENNANT);
    public static final DeferredItem<BlockItem> LIME_PENNANT = registerBlockItem(ModBlocks.LIME_PENNANT);
    public static final DeferredItem<BlockItem> PINK_PENNANT = registerBlockItem(ModBlocks.PINK_PENNANT);
    public static final DeferredItem<BlockItem> GRAY_PENNANT = registerBlockItem(ModBlocks.GRAY_PENNANT);
    public static final DeferredItem<BlockItem> LIGHT_GRAY_PENNANT = registerBlockItem(ModBlocks.LIGHT_GRAY_PENNANT);
    public static final DeferredItem<BlockItem> CYAN_PENNANT = registerBlockItem(ModBlocks.CYAN_PENNANT);
    public static final DeferredItem<BlockItem> PURPLE_PENNANT = registerBlockItem(ModBlocks.PURPLE_PENNANT);
    public static final DeferredItem<BlockItem> BLUE_PENNANT = registerBlockItem(ModBlocks.BLUE_PENNANT);
    public static final DeferredItem<BlockItem> BROWN_PENNANT = registerBlockItem(ModBlocks.BROWN_PENNANT);
    public static final DeferredItem<BlockItem> GREEN_PENNANT = registerBlockItem(ModBlocks.GREEN_PENNANT);
    public static final DeferredItem<BlockItem> RED_PENNANT = registerBlockItem(ModBlocks.RED_PENNANT);
    public static final DeferredItem<BlockItem> BLACK_PENNANT = registerBlockItem(ModBlocks.BLACK_PENNANT);

    public static final DeferredItem<BlockItem> WHITE_PAPERCUTTING = registerPapercutting(ModBlocks.WHITE_PAPERCUTTING);
    public static final DeferredItem<BlockItem> ORANGE_PAPERCUTTING = registerPapercutting(ModBlocks.ORANGE_PAPERCUTTING);
    public static final DeferredItem<BlockItem> MAGENTA_PAPERCUTTING = registerPapercutting(ModBlocks.MAGENTA_PAPERCUTTING);
    public static final DeferredItem<BlockItem> LIGHT_BLUE_PAPERCUTTING = registerPapercutting(ModBlocks.LIGHT_BLUE_PAPERCUTTING);
    public static final DeferredItem<BlockItem> YELLOW_PAPERCUTTING = registerPapercutting(ModBlocks.YELLOW_PAPERCUTTING);
    public static final DeferredItem<BlockItem> LIME_PAPERCUTTING = registerPapercutting(ModBlocks.LIME_PAPERCUTTING);
    public static final DeferredItem<BlockItem> PINK_PAPERCUTTING = registerPapercutting(ModBlocks.PINK_PAPERCUTTING);
    public static final DeferredItem<BlockItem> GRAY_PAPERCUTTING = registerPapercutting(ModBlocks.GRAY_PAPERCUTTING);
    public static final DeferredItem<BlockItem> LIGHT_GRAY_PAPERCUTTING = registerPapercutting(ModBlocks.LIGHT_GRAY_PAPERCUTTING);
    public static final DeferredItem<BlockItem> CYAN_PAPERCUTTING = registerPapercutting(ModBlocks.CYAN_PAPERCUTTING);
    public static final DeferredItem<BlockItem> PURPLE_PAPERCUTTING = registerPapercutting(ModBlocks.PURPLE_PAPERCUTTING);
    public static final DeferredItem<BlockItem> BLUE_PAPERCUTTING = registerPapercutting(ModBlocks.BLUE_PAPERCUTTING);
    public static final DeferredItem<BlockItem> BROWN_PAPERCUTTING = registerPapercutting(ModBlocks.BROWN_PAPERCUTTING);
    public static final DeferredItem<BlockItem> GREEN_PAPERCUTTING = registerPapercutting(ModBlocks.GREEN_PAPERCUTTING);
    public static final DeferredItem<BlockItem> RED_PAPERCUTTING = registerPapercutting(ModBlocks.RED_PAPERCUTTING);
    public static final DeferredItem<BlockItem> BLACK_PAPERCUTTING = registerPapercutting(ModBlocks.BLACK_PAPERCUTTING);

    public static final DeferredItem<BlockItem> SANDBAG = registerBlockItem(ModBlocks.SANDBAG);
    public static final DeferredItem<BlockItem> PYROTECHNICS_TABLE = registerBlockItem(ModBlocks.PYROTECHNICS_TABLE);
    public static final DeferredItem<BlockItem> PAPERCRAFT_TABLE = registerBlockItem(ModBlocks.PAPERCRAFT_TABLE);
    public static final DeferredItem<BlockItem> CARDBOARD_BLOCK = registerBlockItem(ModBlocks.CARDBOARD_BLOCK);
    public static final DeferredItem<BlockItem> VERMILION_FROGLIGHT = registerBlockItem(ModBlocks.VERMILION_FROGLIGHT);
    public static final DeferredItem<BlockItem> CYANINE_FROGLIGHT = registerBlockItem(ModBlocks.CYANINE_FROGLIGHT);
    public static final DeferredItem<BlockItem> UMBER_FROGLIGHT = registerBlockItem(ModBlocks.UMBER_FROGLIGHT);
    public static final DeferredItem<BlockItem> OAK_MAILBOX = registerBlockItem(ModBlocks.OAK_MAILBOX);
    public static final DeferredItem<BlockItem> SPRUCE_MAILBOX = registerBlockItem(ModBlocks.SPRUCE_MAILBOX);
    public static final DeferredItem<BlockItem> BIRCH_MAILBOX = registerBlockItem(ModBlocks.BIRCH_MAILBOX);
    public static final DeferredItem<BlockItem> JUNGLE_MAILBOX = registerBlockItem(ModBlocks.JUNGLE_MAILBOX);
    public static final DeferredItem<BlockItem> ACACIA_MAILBOX = registerBlockItem(ModBlocks.ACACIA_MAILBOX);
    public static final DeferredItem<BlockItem> DARK_OAK_MAILBOX = registerBlockItem(ModBlocks.DARK_OAK_MAILBOX);
    public static final DeferredItem<BlockItem> CRIMSON_MAILBOX = registerBlockItem(ModBlocks.CRIMSON_MAILBOX);
    public static final DeferredItem<BlockItem> WARPED_MAILBOX = registerBlockItem(ModBlocks.WARPED_MAILBOX);
    public static final DeferredItem<BlockItem> MANGROVE_MAILBOX = registerBlockItem(ModBlocks.MANGROVE_MAILBOX);
    public static final DeferredItem<BlockItem> CHERRY_MAILBOX = registerBlockItem(ModBlocks.CHERRY_MAILBOX);
    public static final DeferredItem<BlockItem> BAMBOO_MAILBOX = registerBlockItem(ModBlocks.BAMBOO_MAILBOX);
    public static final DeferredItem<BlockItem> CRABAPPLE_MAILBOX = registerBlockItem(ModBlocks.CRABAPPLE_MAILBOX);
    public static final DeferredItem<BlockItem> EBONY_MAILBOX = registerBlockItem(ModBlocks.EBONY_MAILBOX);
    public static final DeferredItem<BlockItem> GINKGO_MAILBOX = registerBlockItem(ModBlocks.GINKGO_MAILBOX);
    public static final DeferredItem<BlockItem> MAPLE_MAILBOX = registerBlockItem(ModBlocks.MAPLE_MAILBOX);
    public static final DeferredItem<BlockItem> FROST_MAILBOX = registerBlockItem(ModBlocks.FROST_MAILBOX);
    public static final DeferredItem<BlockItem> DAWN_REDWOOD_MAILBOX = registerBlockItem(ModBlocks.DAWN_REDWOOD_MAILBOX);
    public static final DeferredItem<BlockItem> JACARANDA_MAILBOX = registerBlockItem(ModBlocks.JACARANDA_MAILBOX);
    public static final DeferredItem<BlockItem> WILLOW_MAILBOX = registerBlockItem(ModBlocks.WILLOW_MAILBOX);

    public static final DeferredItem<BlockItem> UNDERWATER_TNT = registerBlockItem(ModBlocks.UNDERWATER_TNT);

    public static final DeferredItem<BlockItem> COCOON = registerBlockItem(ModBlocks.COCOON);

    // Items
    public static final DeferredItem<Item> VIOLIN = register("violin", () -> new BowedStringInstrumentItem(InstrumentsType.VIOLIN, nonStackable()));
    public static final DeferredItem<Item> FIDDLE_BOW = register("fiddle_bow", () -> new Item(nonStackable()));
    public static final DeferredItem<Item> DRUMSTICK = register("drumstick", () -> new DrumstickItem(properties()));
    public static final DeferredItem<Item> BASS = register("bass", () -> new GuitarItem(InstrumentsType.BASS, nonStackable()));
    public static final DeferredItem<Item> GUITAR = register("guitar", () -> new GuitarItem(InstrumentsType.GUITAR, nonStackable()));
    public static final DeferredItem<Item> FLUTE = register("flute", () -> new BothHandsInstrumentItem(InstrumentsType.FLUTE, nonStackable()));
    public static final DeferredItem<Item> COW_BELL = register("cow_bell", () -> new CowBellItem(InstrumentsType.COW_BELL, nonStackable()));
    public static final DeferredItem<Item> DIDGERIDOO = register("didgeridoo", () -> new DidgeridooItem(InstrumentsType.DIDGERIDOO, nonStackable()));
    public static final DeferredItem<Item> BANJO = register("banjo", () -> new GuitarItem(InstrumentsType.BANJO, nonStackable()));
    public static final DeferredItem<Item> CELLO = register("cello", () -> new BowedStringInstrumentItem(InstrumentsType.CELLO, nonStackable()));
    public static final DeferredItem<Item> ELECTRIC_GUITAR = register("electric_guitar", () -> new GuitarItem(InstrumentsType.ELECTRIC_GUITAR, nonStackable()));
    public static final DeferredItem<Item> TRUMPET = register("trumpet", () -> new TrumpetItem(InstrumentsType.TRUMPET, nonStackable()));
    public static final DeferredItem<Item> SAXOPHONE = register("saxophone", () -> new DidgeridooItem(InstrumentsType.SAXOPHONE, nonStackable()));
    public static final DeferredItem<Item> OCARINA = register("ocarina", () -> new DidgeridooItem(InstrumentsType.OCARINA, nonStackable()));
    public static final DeferredItem<Item> HARMONICA = register("harmonica", () -> new DidgeridooItem(InstrumentsType.HARMONICA, nonStackable()));
    public static final DeferredItem<Item> PIPA = register("pipa", () -> new BothHandsInstrumentItem(InstrumentsType.PIPA, nonStackable()));
    public static final DeferredItem<Item> ERHU = register("erhu", () -> new BowedStringInstrumentItem(InstrumentsType.ERHU, nonStackable()));
    public static final DeferredItem<Item> WRITABLE_SHEET_MUSIC = register("writable_sheet_music", () -> new WritableSheetMusicItem(nonStackable().component(ModDataComponents.EDITABLE_MELODY.get(), EditableMelody.EMPTY)));
    public static final DeferredItem<Item> SHEET_MUSIC = register("sheet_music", () -> new SheetMusicItem(stackTo16()));

    public static final DeferredItem<Item> CRABAPPLE_BOAT = register("crabapple_boat", () -> new BoatItem(false, BoatTypeExtension.CRABAPPLE, nonStackable()));
    public static final DeferredItem<Item> CRABAPPLE_CHEST_BOAT = register("crabapple_chest_boat", () -> new BoatItem(true, BoatTypeExtension.CRABAPPLE, nonStackable()));
    public static final DeferredItem<Item> EBONY_BOAT = register("ebony_boat", () -> new BoatItem(false, BoatTypeExtension.EBONY, nonStackable()));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = register("ebony_chest_boat", () -> new BoatItem(true, BoatTypeExtension.EBONY, nonStackable()));
    public static final DeferredItem<Item> GINKGO_BOAT = register("ginkgo_boat", () -> new BoatItem(false, BoatTypeExtension.GINKGO, nonStackable()));
    public static final DeferredItem<Item> GINKGO_CHEST_BOAT = register("ginkgo_chest_boat", () -> new BoatItem(true, BoatTypeExtension.GINKGO, nonStackable()));
    public static final DeferredItem<Item> MAPLE_BOAT = register("maple_boat", () -> new BoatItem(false, BoatTypeExtension.MAPLE, nonStackable()));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = register("maple_chest_boat", () -> new BoatItem(true, BoatTypeExtension.MAPLE, nonStackable()));
    public static final DeferredItem<Item> FROST_BOAT = register("frost_boat", () -> new BoatItem(false, BoatTypeExtension.FROST, nonStackable()));
    public static final DeferredItem<Item> FROST_CHEST_BOAT = register("frost_chest_boat", () -> new BoatItem(true, BoatTypeExtension.FROST, nonStackable()));
    public static final DeferredItem<Item> DAWN_REDWOOD_BOAT = register("dawn_redwood_boat", () -> new BoatItem(false, BoatTypeExtension.DAWN_REDWOOD, nonStackable()));
    public static final DeferredItem<Item> DAWN_REDWOOD_CHEST_BOAT = register("dawn_redwood_chest_boat", () -> new BoatItem(true, BoatTypeExtension.DAWN_REDWOOD, nonStackable()));
    public static final DeferredItem<Item> JACARANDA_BOAT = register("jacaranda_boat", () -> new BoatItem(false, BoatTypeExtension.JACARANDA, nonStackable()));
    public static final DeferredItem<Item> JACARANDA_CHEST_BOAT = register("jacaranda_chest_boat", () -> new BoatItem(true, BoatTypeExtension.JACARANDA, nonStackable()));
    public static final DeferredItem<Item> WILLOW_BOAT = register("willow_boat", () -> new BoatItem(false, BoatTypeExtension.WILLOW, nonStackable()));
    public static final DeferredItem<Item> WILLOW_CHEST_BOAT = register("willow_chest_boat", () -> new BoatItem(true, BoatTypeExtension.WILLOW, nonStackable()));

    public static final DeferredItem<Item> STRAWBERRY = register("strawberry", () -> new ItemNameBlockItem(ModBlocks.STRAWBERRY_BUSH.get(), properties().food(ModFoods.STRAWBERRY)));
    public static final DeferredItem<Item> BLUEBERRIES = register("blueberries", () -> new ItemNameBlockItem(ModBlocks.BLUEBERRY_BUSH.get(), properties().food(ModFoods.BLUEBERRY)));
    
    public static final DeferredItem<Item> PAPER_PLANE = register("paper_plane", () -> new PaperPlaneItem(stackTo16()));
    public static final DeferredItem<Item> PAPER_BOAT = register("paper_boat", () -> new PaperBoatItem(stackTo16()));
    public static final DeferredItem<Item> CONFETTI = registerSimple("confetti");
    public static final DeferredItem<Item> WHITE_PARTY_POPPER = register("white_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> ORANGE_PARTY_POPPER = register("orange_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> MAGENTA_PARTY_POPPER = register("magenta_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> LIGHT_BLUE_PARTY_POPPER = register("light_blue_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> YELLOW_PARTY_POPPER = register("yellow_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> LIME_PARTY_POPPER = register("lime_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> PINK_PARTY_POPPER = register("pink_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> GRAY_PARTY_POPPER = register("gray_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> LIGHT_GRAY_PARTY_POPPER = register("light_gray_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> CYAN_PARTY_POPPER = register("cyan_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> PURPLE_PARTY_POPPER = register("purple_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> BLUE_PARTY_POPPER = register("blue_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> BROWN_PARTY_POPPER = register("brown_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> GREEN_PARTY_POPPER = register("green_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> RED_PARTY_POPPER = register("red_party_popper", () -> new PartyPopperItem(stackTo16()));
    public static final DeferredItem<Item> BLACK_PARTY_POPPER = register("black_party_popper", () -> new PartyPopperItem(stackTo16()));

    public static final DeferredItem<Item> WHITE_SPARKLER = registerSparkler("white_sparkler");
    public static final DeferredItem<Item> BROWN_SPARKLER = registerSparkler("brown_sparkler");
    public static final DeferredItem<Item> RED_SPARKLER = registerSparkler("red_sparkler");
    public static final DeferredItem<Item> ORANGE_SPARKLER = registerSparkler("orange_sparkler");
    public static final DeferredItem<Item> YELLOW_SPARKLER = registerSparkler("yellow_sparkler");
    public static final DeferredItem<Item> LIME_SPARKLER = registerSparkler("lime_sparkler");
    public static final DeferredItem<Item> GREEN_SPARKLER = registerSparkler("green_sparkler");
    public static final DeferredItem<Item> CYAN_SPARKLER = registerSparkler("cyan_sparkler");
    public static final DeferredItem<Item> LIGHT_BLUE_SPARKLER = registerSparkler("light_blue_sparkler");
    public static final DeferredItem<Item> BLUE_SPARKLER = registerSparkler("blue_sparkler");
    public static final DeferredItem<Item> PURPLE_SPARKLER = registerSparkler("purple_sparkler");
    public static final DeferredItem<Item> MAGENTA_SPARKLER = registerSparkler("magenta_sparkler");
    public static final DeferredItem<Item> PINK_SPARKLER = registerSparkler("pink_sparkler");

    public static final DeferredItem<Item> BALLOON = registerBalloon("balloon", Colour.DEFAULT);
    public static final DeferredItem<Item> WHITE_BALLOON = registerBalloon("white_balloon", DyeColor.WHITE);
    public static final DeferredItem<Item> LIGHT_GRAY_BALLOON = registerBalloon("light_gray_balloon", DyeColor.LIGHT_GRAY);
    public static final DeferredItem<Item> GRAY_BALLOON = registerBalloon("gray_balloon", DyeColor.GRAY);
    public static final DeferredItem<Item> BLACK_BALLOON = registerBalloon("black_balloon", DyeColor.BLACK);
    public static final DeferredItem<Item> BROWN_BALLOON = registerBalloon("brown_balloon", DyeColor.BROWN);
    public static final DeferredItem<Item> RED_BALLOON = registerBalloon("red_balloon", DyeColor.RED);
    public static final DeferredItem<Item> ORANGE_BALLOON = registerBalloon("orange_balloon", DyeColor.ORANGE);
    public static final DeferredItem<Item> YELLOW_BALLOON = registerBalloon("yellow_balloon", DyeColor.YELLOW);
    public static final DeferredItem<Item> LIME_BALLOON = registerBalloon("lime_balloon", DyeColor.LIME);
    public static final DeferredItem<Item> GREEN_BALLOON = registerBalloon("green_balloon", DyeColor.GREEN);
    public static final DeferredItem<Item> CYAN_BALLOON = registerBalloon("cyan_balloon", DyeColor.CYAN);
    public static final DeferredItem<Item> LIGHT_BLUE_BALLOON = registerBalloon("light_blue_balloon", DyeColor.LIGHT_BLUE);
    public static final DeferredItem<Item> BLUE_BALLOON = registerBalloon("blue_balloon", DyeColor.BLUE);
    public static final DeferredItem<Item> PURPLE_BALLOON = registerBalloon("purple_balloon", DyeColor.PURPLE);
    public static final DeferredItem<Item> MAGENTA_BALLOON = registerBalloon("magenta_balloon", DyeColor.MAGENTA);
    public static final DeferredItem<Item> PINK_BALLOON = registerBalloon("pink_balloon", DyeColor.PINK);
    public static final DeferredItem<Item> CREEPER_BALLOON = registerBalloon("creeper_balloon", Balloon.SpecialVariant.CREEPER);
    public static final DeferredItem<Item> HEART_BALLOON = registerBalloon("heart_balloon", Balloon.SpecialVariant.HEART);
    public static final DeferredItem<Item> STAR_BALLOON = registerBalloon("star_balloon", Balloon.SpecialVariant.STAR);
    public static final DeferredItem<Item> RABBIT_BALLOON = registerBalloon("rabbit_balloon", Balloon.SpecialVariant.RABBIT);

    public static final DeferredItem<Item> WHITE_BUNDLE = registerBundle("white_bundle");
    public static final DeferredItem<Item> LIGHT_GRAY_BUNDLE = registerBundle("light_gray_bundle");
    public static final DeferredItem<Item> GRAY_BUNDLE = registerBundle("gray_bundle");
    public static final DeferredItem<Item> BLACK_BUNDLE = registerBundle("black_bundle");
    public static final DeferredItem<Item> BROWN_BUNDLE = registerBundle("brown_bundle");
    public static final DeferredItem<Item> RED_BUNDLE = registerBundle("red_bundle");
    public static final DeferredItem<Item> ORANGE_BUNDLE = registerBundle("orange_bundle");
    public static final DeferredItem<Item> YELLOW_BUNDLE = registerBundle("yellow_bundle");
    public static final DeferredItem<Item> LIME_BUNDLE = registerBundle("lime_bundle");
    public static final DeferredItem<Item> GREEN_BUNDLE = registerBundle("green_bundle");
    public static final DeferredItem<Item> CYAN_BUNDLE = registerBundle("cyan_bundle");
    public static final DeferredItem<Item> LIGHT_BLUE_BUNDLE = registerBundle("light_blue_bundle");
    public static final DeferredItem<Item> BLUE_BUNDLE = registerBundle("blue_bundle");
    public static final DeferredItem<Item> PURPLE_BUNDLE = registerBundle("purple_bundle");
    public static final DeferredItem<Item> MAGENTA_BUNDLE = registerBundle("magenta_bundle");
    public static final DeferredItem<Item> PINK_BUNDLE = registerBundle("pink_bundle");

    public static final DeferredItem<Item> KITE = register("kite", () -> new KiteItem(nonStackable().component(ModDataComponents.KITE_COLOR, KiteColor.DEFAULT)));
    public static final DeferredItem<Item> UMBRELLA = register("umbrella", () -> new UmbrellaItem(properties().durability(128).component(ModDataComponents.UMBRELLA_COLOR, UmbrellaColor.DEFAULT)));
    public static final DeferredItem<Item> DRIPLEAF_UMBRELLA = register("dripleaf_umbrella", () -> new UmbrellaItem(properties().durability(96)));
    public static final DeferredItem<Item> BOMB = register("bomb", () -> new BombItem(stackTo16()));
    public static final DeferredItem<Item> BUG_NET = register("bug_net", () -> new BugNetItem(properties().durability(64)));
    public static final DeferredItem<Item> WHITE_SMOKE_BOMB = register("white_smoke_bomb", () -> new SmokeBombItem(DyeColor.WHITE, stackTo16()));
    public static final DeferredItem<Item> LIGHT_GRAY_SMOKE_BOMB = register("light_gray_smoke_bomb", () -> new SmokeBombItem(DyeColor.LIGHT_GRAY, stackTo16()));
    public static final DeferredItem<Item> GRAY_SMOKE_BOMB = register("gray_smoke_bomb", () -> new SmokeBombItem(DyeColor.GRAY, stackTo16()));
    public static final DeferredItem<Item> BLACK_SMOKE_BOMB = register("black_smoke_bomb", () -> new SmokeBombItem(DyeColor.BLACK, stackTo16()));
    public static final DeferredItem<Item> BROWN_SMOKE_BOMB = register("brown_smoke_bomb", () -> new SmokeBombItem(DyeColor.BROWN, stackTo16()));
    public static final DeferredItem<Item> RED_SMOKE_BOMB = register("red_smoke_bomb", () -> new SmokeBombItem(DyeColor.RED, stackTo16()));
    public static final DeferredItem<Item> ORANGE_SMOKE_BOMB = register("orange_smoke_bomb", () -> new SmokeBombItem(DyeColor.ORANGE, stackTo16()));
    public static final DeferredItem<Item> YELLOW_SMOKE_BOMB = register("yellow_smoke_bomb", () -> new SmokeBombItem(DyeColor.YELLOW, stackTo16()));
    public static final DeferredItem<Item> LIME_SMOKE_BOMB = register("lime_smoke_bomb", () -> new SmokeBombItem(DyeColor.LIME, stackTo16()));
    public static final DeferredItem<Item> GREEN_SMOKE_BOMB = register("green_smoke_bomb", () -> new SmokeBombItem(DyeColor.GREEN, stackTo16()));
    public static final DeferredItem<Item> CYAN_SMOKE_BOMB = register("cyan_smoke_bomb", () -> new SmokeBombItem(DyeColor.CYAN, stackTo16()));
    public static final DeferredItem<Item> LIGHT_BLUE_SMOKE_BOMB = register("light_blue_smoke_bomb", () -> new SmokeBombItem(DyeColor.LIGHT_BLUE, stackTo16()));
    public static final DeferredItem<Item> BLUE_SMOKE_BOMB = register("blue_smoke_bomb", () -> new SmokeBombItem(DyeColor.BLUE, stackTo16()));
    public static final DeferredItem<Item> PURPLE_SMOKE_BOMB = register("purple_smoke_bomb", () -> new SmokeBombItem(DyeColor.PURPLE, stackTo16()));
    public static final DeferredItem<Item> MAGENTA_SMOKE_BOMB = register("magenta_smoke_bomb", () -> new SmokeBombItem(DyeColor.MAGENTA, stackTo16()));
    public static final DeferredItem<Item> PINK_SMOKE_BOMB = register("pink_smoke_bomb", () -> new SmokeBombItem(DyeColor.PINK, stackTo16()));
    public static final DeferredItem<Item> BEEKEEPING_HAT = register("beekeeping_hat", () -> new ArmorItem(ModArmorMaterials.CLOTH, ArmorItem.Type.HELMET, properties().durability(64)));
    public static final DeferredItem<Item> STRAW_HAT = register("straw_hat", () -> new ArmorItem(ModArmorMaterials.CLOTH, ArmorItem.Type.HELMET, properties().durability(64)));

    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_LARGE_BALL = registerFireworkShapeTemplate("firework_shape_template_large_ball", FireworkExplosion.Shape.LARGE_BALL);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_STAR = registerFireworkShapeTemplate("firework_shape_template_star", FireworkExplosion.Shape.STAR);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_CREEPER = registerFireworkShapeTemplate("firework_shape_template_creeper", FireworkExplosion.Shape.CREEPER);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_BURST = registerFireworkShapeTemplate("firework_shape_template_burst", FireworkExplosion.Shape.BURST);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_CUBE = registerFireworkShapeTemplate("firework_shape_template_cube", FireworkShapeExtension.CUBE);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_HEART = registerFireworkShapeTemplate("firework_shape_template_heart", FireworkShapeExtension.HEART);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_PLANET = registerFireworkShapeTemplate("firework_shape_template_planet", FireworkShapeExtension.PLANET);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_JELLYFISH = registerFireworkShapeTemplate("firework_shape_template_jellyfish", FireworkShapeExtension.JELLYFISH);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_CLOCK = registerFireworkShapeTemplate("firework_shape_template_clock", FireworkShapeExtension.CLOCK);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_AXIS = registerFireworkShapeTemplate("firework_shape_template_axis", FireworkShapeExtension.AXIS);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_TETRAHEDRON = registerFireworkShapeTemplate("firework_shape_template_tetrahedron", FireworkShapeExtension.TETRAHEDRON);
    public static final DeferredItem<Item> FIREWORK_SHAPE_TEMPLATE_HYPERBOLOID = registerFireworkShapeTemplate("firework_shape_template_hyperboloid", FireworkShapeExtension.HYPERBOLOID);

    public static final DeferredItem<Item> CARDBOARD = registerSimple("cardboard");
    public static final DeferredItem<Item> WRITABLE_LETTER = register("writable_letter", () -> new WritableLetterItem(nonStackable().component(DataComponents.WRITABLE_BOOK_CONTENT, WritableBookContent.EMPTY)));
    public static final DeferredItem<Item> LETTER = register("letter", () -> new LetterItem(stackTo16()));
    public static final DeferredItem<Item> ENVELOPE = register("envelope", () -> new EnvelopeItem(properties()));
    public static final DeferredItem<Item> MAIL = register("mail", () -> new MailItem(properties().component(ModDataComponents.MAIL_CONTENT, MailContent.DEFAULT)));

    public static final DeferredItem<Item> BEE = register("bee", () -> new CritterItem(EntityType.BEE, properties()));
    public static final DeferredItem<Item> BUTTERFLY = register("butterfly", () -> new CritterItem(ModEntities.BUTTERFLY.get(), properties()));
    public static final DeferredItem<Item> MOTH = register("moth", () -> new CritterItem(ModEntities.MOTH.get(), properties()));
    public static final DeferredItem<Item> CATERPILLAR = register("caterpillar", () -> new CritterItem(ModEntities.CATERPILLAR.get(), properties()));
    public static final DeferredItem<Item> DRAGONFLY = register("dragonfly", () -> new CritterItem(ModEntities.DRAGONFLY.get(), properties()));
    public static final DeferredItem<Item> BUTTERFLY_SPAWN_EGG = registerSpawnEgg("butterfly_spawn_egg", ModEntities.BUTTERFLY, 0xfc8113, 0x341911);
    public static final DeferredItem<Item> MOTH_SPAWN_EGG = registerSpawnEgg("moth_spawn_egg", ModEntities.MOTH, 0x735450, 0xc5b9a7);
    public static final DeferredItem<Item> CATERPILLAR_SPAWN_EGG = registerSpawnEgg("caterpillar_spawn_egg", ModEntities.CATERPILLAR, 0x6c8031, 0x1e2d0e);
    public static final DeferredItem<Item> DRAGONFLY_SPAWN_EGG = registerSpawnEgg("dragonfly_spawn_egg", ModEntities.DRAGONFLY, 0x7a2026, 0xc7cec6);
    public static final DeferredItem<Item> BIRD_SPAWN_EGG = registerSpawnEgg("bird_spawn_egg", ModEntities.BIRD, 0xa68259, 0x684d42);
    public static final DeferredItem<Item> PIGEON_SPAWN_EGG = registerSpawnEgg("pigeon_spawn_egg", ModEntities.PIGEON, 0xabb4bc, 0x3d4753);

    private static DeferredItem<Item> register(String name, Supplier<Item> supplier) {
        return ITEMS.register(name, supplier);
    }

    private static DeferredItem<Item> registerSimple(String name) {
        return ITEMS.registerSimpleItem(name);
    }
    
    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<? extends Block> block) {
        return ITEMS.registerSimpleBlockItem(block);
    }
    
    private static DeferredItem<BlockItem> registerBlockItem(DeferredBlock<? extends Block> block, Supplier<BlockItem> blockItem) {
        return ITEMS.register(block.getId().getPath(), blockItem);
    }
    
    private static DeferredItem<BlockItem> registerSign(DeferredBlock<StandingSignBlock> sign, DeferredBlock<WallSignBlock> wallSign) {
        return registerBlockItem(sign, () -> new SignItem(stackTo16(), sign.get(), wallSign.get()));
    }
    
    private static DeferredItem<BlockItem> registerHangingSign(DeferredBlock<CeilingHangingSignBlock> hangingSign, DeferredBlock<WallHangingSignBlock> wallHangingSign) {
        return registerBlockItem(hangingSign,  () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), stackTo16()));
    }
    
    private static DeferredItem<BlockItem> registerPlaceOnWaterItem(DeferredBlock<Block> block) {
        return registerBlockItem(block, () -> new PlaceOnWaterBlockItem(block.get(), properties()));
    }

    private static DeferredItem<Item> registerSparkler(String name) {
        return register(name, () -> new SparklerItem(properties().durability(60).setNoRepair()));
    }

    private static DeferredItem<Item> registerBundle(String name) {
        return register(name, () -> new BundleItem(nonStackable().component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)));
    }

    private static DeferredItem<Item> registerBalloon(String name, Balloon.Variant variant) {
        return register(name, () -> new BalloonItem(properties(), variant));
    }

    private static DeferredItem<Item> registerBalloon(String name, DyeColor color) {
        return registerBalloon(name, Colour.cast(color));
    }

    private static DeferredItem<Item> registerFireworkShapeTemplate(String name, FireworkExplosion.Shape shape) {
        return register(name, () -> new FireworkShapeTemplateItem(nonStackable(), shape));
    }

    private static DeferredItem<BlockItem> registerPapercutting(DeferredBlock<? extends Block> block) {
        return registerBlockItem(block, () -> new BlockItem(block.get(), properties().component(ModDataComponents.PAPERCUTTING_STENCIL, PapercuttingStencil.DEFAULT)));
    }

    private static DeferredItem<Item> registerSpawnEgg(String name, Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor) {
        return register(name, () -> new DeferredSpawnEggItem(type, backgroundColor, highlightColor, properties()));
    }
    
    private static Item.Properties properties() {
        return new Item.Properties();
    }

    private static Item.Properties nonStackable() {
        return properties().stacksTo(1);
    }
    
    private static Item.Properties stackTo16() {
        return properties().stacksTo(16);
    }

    private static Item.Properties pinwheelProperties() {
        return properties().component(ModDataComponents.PINWHEEL_CONTEXT, PinwheelContext.DEFAULT).component(ModDataComponents.PINWHEEL_COLOR, PinwheelColor.DEFAULT);
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}