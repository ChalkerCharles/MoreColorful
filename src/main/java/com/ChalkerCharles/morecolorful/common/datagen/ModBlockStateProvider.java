package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.block.utility.MailboxBlock;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModBlockStateHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends ModBlockStateHelper {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreColorful.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerStatesOnly();

        logAndWood(ModBlocks.CRABAPPLE_LOG, ModBlocks.CRABAPPLE_WOOD);
        logAndWood(ModBlocks.STRIPPED_CRABAPPLE_LOG, ModBlocks.STRIPPED_CRABAPPLE_WOOD);
        simpleCube(ModBlocks.CRABAPPLE_PLANKS);
        simpleStairs(ModBlocks.CRABAPPLE_STAIRS, ModBlocks.CRABAPPLE_PLANKS);
        simpleSlab(ModBlocks.CRABAPPLE_SLAB, ModBlocks.CRABAPPLE_PLANKS);
        fenceBlock(ModBlocks.CRABAPPLE_FENCE, ModBlocks.CRABAPPLE_PLANKS);
        simpleFenceGate(ModBlocks.CRABAPPLE_FENCE_GATE, ModBlocks.CRABAPPLE_PLANKS);
        simpleDoor(ModBlocks.CRABAPPLE_DOOR, false);
        simpleTrapdoor(ModBlocks.CRABAPPLE_TRAPDOOR, true, false);
        simplePressurePlate(ModBlocks.CRABAPPLE_PRESSURE_PLATE, ModBlocks.CRABAPPLE_PLANKS);
        buttonBlock(ModBlocks.CRABAPPLE_BUTTON, ModBlocks.CRABAPPLE_PLANKS);
        signBlock(ModBlocks.CRABAPPLE_SIGN, ModBlocks.CRABAPPLE_WALL_SIGN, ModBlocks.CRABAPPLE_PLANKS);
        hangingSignBlock(ModBlocks.CRABAPPLE_HANGING_SIGN, ModBlocks.CRABAPPLE_WALL_HANGING_SIGN, ModBlocks.STRIPPED_CRABAPPLE_LOG);
        leaves(ModBlocks.CRABAPPLE_LEAVES);
        cross(ModBlocks.CRABAPPLE_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_CRABAPPLE_SAPLING);
        leaves(ModBlocks.WHITE_CHERRY_LEAVES);
        cross(ModBlocks.WHITE_CHERRY_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_WHITE_CHERRY_SAPLING);

        leaves(ModBlocks.ORANGE_BIRCH_LEAVES);
        cross(ModBlocks.ORANGE_BIRCH_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_ORANGE_BIRCH_SAPLING);
        leaves(ModBlocks.YELLOW_BIRCH_LEAVES);
        cross(ModBlocks.YELLOW_BIRCH_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_YELLOW_BIRCH_SAPLING);

        logAndWood(ModBlocks.EBONY_LOG, ModBlocks.EBONY_WOOD);
        logAndWood(ModBlocks.STRIPPED_EBONY_LOG, ModBlocks.STRIPPED_EBONY_WOOD);
        simpleCube(ModBlocks.EBONY_PLANKS);
        simpleStairs(ModBlocks.EBONY_STAIRS, ModBlocks.EBONY_PLANKS);
        simpleSlab(ModBlocks.EBONY_SLAB, ModBlocks.EBONY_PLANKS);
        fenceBlock(ModBlocks.EBONY_FENCE, ModBlocks.EBONY_PLANKS);
        simpleFenceGate(ModBlocks.EBONY_FENCE_GATE, ModBlocks.EBONY_PLANKS);
        simpleDoor(ModBlocks.EBONY_DOOR, false);
        simpleTrapdoor(ModBlocks.EBONY_TRAPDOOR, true, false);
        simplePressurePlate(ModBlocks.EBONY_PRESSURE_PLATE, ModBlocks.EBONY_PLANKS);
        buttonBlock(ModBlocks.EBONY_BUTTON, ModBlocks.EBONY_PLANKS);
        signBlock(ModBlocks.EBONY_SIGN, ModBlocks.EBONY_WALL_SIGN, ModBlocks.EBONY_PLANKS);
        hangingSignBlock(ModBlocks.EBONY_HANGING_SIGN, ModBlocks.EBONY_WALL_HANGING_SIGN, ModBlocks.STRIPPED_EBONY_LOG);

        logAndWood(ModBlocks.GINKGO_LOG, ModBlocks.GINKGO_WOOD);
        logAndWood(ModBlocks.STRIPPED_GINKGO_LOG, ModBlocks.STRIPPED_GINKGO_WOOD);
        simpleCube(ModBlocks.GINKGO_PLANKS);
        simpleStairs(ModBlocks.GINKGO_STAIRS, ModBlocks.GINKGO_PLANKS);
        simpleSlab(ModBlocks.GINKGO_SLAB, ModBlocks.GINKGO_PLANKS);
        fenceBlock(ModBlocks.GINKGO_FENCE, ModBlocks.GINKGO_PLANKS);
        simpleFenceGate(ModBlocks.GINKGO_FENCE_GATE, ModBlocks.GINKGO_PLANKS);
        simpleDoor(ModBlocks.GINKGO_DOOR, true);
        simpleTrapdoor(ModBlocks.GINKGO_TRAPDOOR, true, true);
        simplePressurePlate(ModBlocks.GINKGO_PRESSURE_PLATE, ModBlocks.GINKGO_PLANKS);
        buttonBlock(ModBlocks.GINKGO_BUTTON, ModBlocks.GINKGO_PLANKS);
        signBlock(ModBlocks.GINKGO_SIGN, ModBlocks.GINKGO_WALL_SIGN, ModBlocks.GINKGO_PLANKS);
        hangingSignBlock(ModBlocks.GINKGO_HANGING_SIGN, ModBlocks.GINKGO_WALL_HANGING_SIGN, ModBlocks.STRIPPED_GINKGO_LOG);
        leaves(ModBlocks.GINKGO_LEAVES);
        cross(ModBlocks.GINKGO_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_GINKGO_SAPLING);

        logAndWood(ModBlocks.MAPLE_LOG, ModBlocks.MAPLE_WOOD);
        logAndWood(ModBlocks.STRIPPED_MAPLE_LOG, ModBlocks.STRIPPED_MAPLE_WOOD);
        simpleCube(ModBlocks.MAPLE_PLANKS);
        simpleStairs(ModBlocks.MAPLE_STAIRS, ModBlocks.MAPLE_PLANKS);
        simpleSlab(ModBlocks.MAPLE_SLAB, ModBlocks.MAPLE_PLANKS);
        fenceBlock(ModBlocks.MAPLE_FENCE, ModBlocks.MAPLE_PLANKS);
        simpleFenceGate(ModBlocks.MAPLE_FENCE_GATE, ModBlocks.MAPLE_PLANKS);
        simpleDoor(ModBlocks.MAPLE_DOOR, false);
        simpleTrapdoor(ModBlocks.MAPLE_TRAPDOOR, true, false);
        simplePressurePlate(ModBlocks.MAPLE_PRESSURE_PLATE, ModBlocks.MAPLE_PLANKS);
        buttonBlock(ModBlocks.MAPLE_BUTTON, ModBlocks.MAPLE_PLANKS);
        signBlock(ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN, ModBlocks.MAPLE_PLANKS);
        hangingSignBlock(ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN, ModBlocks.STRIPPED_MAPLE_LOG);
        leaves(ModBlocks.MAPLE_LEAVES);
        cross(ModBlocks.MAPLE_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_MAPLE_SAPLING);

        logAndWood(ModBlocks.FROST_LOG, ModBlocks.FROST_WOOD);
        logAndWood(ModBlocks.STRIPPED_FROST_LOG, ModBlocks.STRIPPED_FROST_WOOD);
        simpleCube(ModBlocks.FROST_PLANKS);
        simpleStairs(ModBlocks.FROST_STAIRS, ModBlocks.FROST_PLANKS);
        simpleSlab(ModBlocks.FROST_SLAB, ModBlocks.FROST_PLANKS);
        fenceBlock(ModBlocks.FROST_FENCE, ModBlocks.FROST_PLANKS);
        simpleFenceGate(ModBlocks.FROST_FENCE_GATE, ModBlocks.FROST_PLANKS);
        simpleDoor(ModBlocks.FROST_DOOR, false);
        simpleTrapdoor(ModBlocks.FROST_TRAPDOOR, true, false);
        simplePressurePlate(ModBlocks.FROST_PRESSURE_PLATE, ModBlocks.FROST_PLANKS);
        buttonBlock(ModBlocks.FROST_BUTTON, ModBlocks.FROST_PLANKS);
        signBlock(ModBlocks.FROST_SIGN, ModBlocks.FROST_WALL_SIGN, ModBlocks.FROST_PLANKS);
        hangingSignBlock(ModBlocks.FROST_HANGING_SIGN, ModBlocks.FROST_WALL_HANGING_SIGN, ModBlocks.STRIPPED_FROST_LOG);
        leaves(ModBlocks.FROST_LEAVES);
        cross(ModBlocks.FROST_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_FROST_SAPLING);

        logAndWood(ModBlocks.DAWN_REDWOOD_LOG, ModBlocks.DAWN_REDWOOD_WOOD);
        logAndWood(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG, ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD);
        simpleCube(ModBlocks.DAWN_REDWOOD_PLANKS);
        simpleStairs(ModBlocks.DAWN_REDWOOD_STAIRS, ModBlocks.DAWN_REDWOOD_PLANKS);
        simpleSlab(ModBlocks.DAWN_REDWOOD_SLAB, ModBlocks.DAWN_REDWOOD_PLANKS);
        fenceBlock(ModBlocks.DAWN_REDWOOD_FENCE, ModBlocks.DAWN_REDWOOD_PLANKS);
        simpleFenceGate(ModBlocks.DAWN_REDWOOD_FENCE_GATE, ModBlocks.DAWN_REDWOOD_PLANKS);
        simpleDoor(ModBlocks.DAWN_REDWOOD_DOOR, false);
        simpleTrapdoor(ModBlocks.DAWN_REDWOOD_TRAPDOOR, false, false);
        simplePressurePlate(ModBlocks.DAWN_REDWOOD_PRESSURE_PLATE, ModBlocks.DAWN_REDWOOD_PLANKS);
        buttonBlock(ModBlocks.DAWN_REDWOOD_BUTTON, ModBlocks.DAWN_REDWOOD_PLANKS);
        signBlock(ModBlocks.DAWN_REDWOOD_SIGN, ModBlocks.DAWN_REDWOOD_WALL_SIGN, ModBlocks.DAWN_REDWOOD_PLANKS);
        hangingSignBlock(ModBlocks.DAWN_REDWOOD_HANGING_SIGN, ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN, ModBlocks.STRIPPED_DAWN_REDWOOD_LOG);
        leaves(ModBlocks.DAWN_REDWOOD_LEAVES);
        cross(ModBlocks.DAWN_REDWOOD_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_DAWN_REDWOOD_SAPLING);
        alternativeCross(ModBlocks.DAWN_REDWOOD_ROOTS);

        logAndWood(ModBlocks.JACARANDA_LOG, ModBlocks.JACARANDA_WOOD);
        logAndWood(ModBlocks.STRIPPED_JACARANDA_LOG, ModBlocks.STRIPPED_JACARANDA_WOOD);
        simpleCube(ModBlocks.JACARANDA_PLANKS);
        simpleStairs(ModBlocks.JACARANDA_STAIRS, ModBlocks.JACARANDA_PLANKS);
        simpleSlab(ModBlocks.JACARANDA_SLAB, ModBlocks.JACARANDA_PLANKS);
        fenceBlock(ModBlocks.JACARANDA_FENCE, ModBlocks.JACARANDA_PLANKS);
        simpleFenceGate(ModBlocks.JACARANDA_FENCE_GATE, ModBlocks.JACARANDA_PLANKS);
        simpleDoor(ModBlocks.JACARANDA_DOOR, true);
        simpleTrapdoor(ModBlocks.JACARANDA_TRAPDOOR, true, true);
        simplePressurePlate(ModBlocks.JACARANDA_PRESSURE_PLATE, ModBlocks.JACARANDA_PLANKS);
        buttonBlock(ModBlocks.JACARANDA_BUTTON, ModBlocks.JACARANDA_PLANKS);
        signBlock(ModBlocks.JACARANDA_SIGN, ModBlocks.JACARANDA_WALL_SIGN, ModBlocks.JACARANDA_PLANKS);
        hangingSignBlock(ModBlocks.JACARANDA_HANGING_SIGN, ModBlocks.JACARANDA_WALL_HANGING_SIGN, ModBlocks.STRIPPED_JACARANDA_LOG);
        leaves(ModBlocks.JACARANDA_LEAVES);
        cross(ModBlocks.JACARANDA_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_JACARANDA_SAPLING);

        logAndWood(ModBlocks.WILLOW_LOG, ModBlocks.WILLOW_WOOD);
        logAndWood(ModBlocks.STRIPPED_WILLOW_LOG, ModBlocks.STRIPPED_WILLOW_WOOD);
        simpleCube(ModBlocks.WILLOW_PLANKS);
        simpleStairs(ModBlocks.WILLOW_STAIRS, ModBlocks.WILLOW_PLANKS);
        simpleSlab(ModBlocks.WILLOW_SLAB, ModBlocks.WILLOW_PLANKS);
        fenceBlock(ModBlocks.WILLOW_FENCE, ModBlocks.WILLOW_PLANKS);
        simpleFenceGate(ModBlocks.WILLOW_FENCE_GATE, ModBlocks.WILLOW_PLANKS);
        simpleDoor(ModBlocks.WILLOW_DOOR, true);
        simpleTrapdoor(ModBlocks.WILLOW_TRAPDOOR, true, true);
        simplePressurePlate(ModBlocks.WILLOW_PRESSURE_PLATE, ModBlocks.WILLOW_PLANKS);
        buttonBlock(ModBlocks.WILLOW_BUTTON, ModBlocks.WILLOW_PLANKS);
        signBlock(ModBlocks.WILLOW_SIGN, ModBlocks.WILLOW_WALL_SIGN, ModBlocks.WILLOW_PLANKS);
        hangingSignBlock(ModBlocks.WILLOW_HANGING_SIGN, ModBlocks.WILLOW_WALL_HANGING_SIGN, ModBlocks.STRIPPED_WILLOW_LOG);
        leaves(ModBlocks.WILLOW_LEAVES);
        cross(ModBlocks.WILLOW_SAPLING);
        simpleFlowerPot(ModBlocks.POTTED_WILLOW_SAPLING);
        willowBranches(ModBlocks.WILLOW_BRANCHES);

        cross(ModBlocks.PINK_DAISY);
        simpleFlowerPot(ModBlocks.POTTED_PINK_DAISY);
        cross(ModBlocks.RED_CARNATION);
        simpleFlowerPot(ModBlocks.POTTED_RED_CARNATION);
        cross(ModBlocks.PINK_CARNATION);
        simpleFlowerPot(ModBlocks.POTTED_PINK_CARNATION);
        cross(ModBlocks.WHITE_CARNATION);
        simpleFlowerPot(ModBlocks.POTTED_WHITE_CARNATION);
        cross(ModBlocks.RED_SPIDER_LILY);
        simpleFlowerPot(ModBlocks.POTTED_RED_SPIDER_LILY);
        cross(ModBlocks.YELLOW_CHRYSANTHEMUM);
        simpleFlowerPot(ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM);
        cross(ModBlocks.GREEN_CHRYSANTHEMUM);
        simpleFlowerPot(ModBlocks.POTTED_GREEN_CHRYSANTHEMUM);
        cross(ModBlocks.OPEN_DAYBLOOM);
        simpleFlowerPot(ModBlocks.POTTED_OPEN_DAYBLOOM);
        cross(ModBlocks.CLOSED_DAYBLOOM);
        simpleFlowerPot(ModBlocks.POTTED_CLOSED_DAYBLOOM);
        cross(ModBlocks.EDELWEISS);
        simpleFlowerPot(ModBlocks.POTTED_EDELWEISS);
        cross(ModBlocks.CROCUS);
        simpleFlowerPot(ModBlocks.POTTED_CROCUS);
        cross(ModBlocks.IRIS);
        simpleFlowerPot(ModBlocks.POTTED_IRIS);
        cross(ModBlocks.LAVENDER);
        simpleFlowerPot(ModBlocks.POTTED_LAVENDER, true);
        cross(ModBlocks.DAFFODIL);
        simpleFlowerPot(ModBlocks.POTTED_DAFFODIL);
        cross(ModBlocks.GERBERA_DAISY);
        simpleFlowerPot(ModBlocks.POTTED_GERBERA_DAISY);
        cross(ModBlocks.RAPESEED_FLOWER);
        simpleFlowerPot(ModBlocks.POTTED_RAPESEED_FLOWER);
        windflower(ModBlocks.WINDFLOWER);
        pottedWindflower(ModBlocks.POTTED_WINDFLOWER);

        doubleCross(ModBlocks.CATTAIL);
        doubleCross(ModBlocks.TALL_RAPESEED_FLOWER);

        doubleCross(ModBlocks.SHORT_WATER_GRASS, true);
        doubleCross(ModBlocks.TALL_WATER_GRASS, true);
        berryBush(ModBlocks.STRAWBERRY_BUSH);
        berryBush(ModBlocks.BLUEBERRY_BUSH);

        for (Block block : RibbonBlock.ALL_COLORS.get()) {
            ribbon(block);
        }
        for (Block block : PennantBlock.ALL_BLOCKS.get()) {
            emptyModelWithParticle(block);
        }
        cubeBottomTop(ModBlocks.UNDERWATER_TNT);
        for (Block block : PapercuttingBlock.ALL_BLOCKS.get()) {
            emptyModel(block);
        }
        logBlock(ModBlocks.CARDBOARD_BLOCK);
        logSideBlock(ModBlocks.VERMILION_FROGLIGHT);
        logSideBlock(ModBlocks.CYANINE_FROGLIGHT);
        logSideBlock(ModBlocks.UMBER_FROGLIGHT);
        for (Block block : MailboxBlock.ALL_BLOCKS.get()) {
            mailbox(block);
        }
    }

    private void registerStatesOnly() {
        directionalDoubleBlock(ModBlocks.HARP);
        simpleStateBlock(ModBlocks.BASS_DRUM);
        simpleStateBlock(ModBlocks.SNARE_DRUM);
        simpleStateBlock(ModBlocks.TOMTOM_DRUM);
        simpleDoubleBlock(ModBlocks.RIDE_CYMBAL);
        directionalDoubleBlock(ModBlocks.CRASH_CYMBAL);
        directionalDoubleBlock(ModBlocks.CHIMES);
        simpleDirectionalBlock(ModBlocks.GLOCKENSPIEL);
        horizontalDoubleBlock(ModBlocks.XYLOPHONE);
        horizontalDoubleBlock(ModBlocks.VIBRAPHONE);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLING);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_BIT);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SAW);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK);
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
        horizontalDoubleBlock(ModBlocks.GUZHENG);

        petalBlock(ModBlocks.BEGONIAS);
        petalBlock(ModBlocks.WHITE_PETALS);
        leafLitterBlock(ModBlocks.ORANGE_BIRCH_LEAF_LITTER);
        leafLitterBlock(ModBlocks.YELLOW_BIRCH_LEAF_LITTER);
        leafLitterBlock(ModBlocks.GINKGO_LEAF_LITTER);
        leafLitterBlock(ModBlocks.MAPLE_LEAF_LITTER);
        petalBlock(ModBlocks.FROSTY_PETALS);
        leafLitterBlock(ModBlocks.DAWN_REDWOOD_LEAF_LITTER);
        petalBlock(ModBlocks.VIOLETS);
        petalBlock(ModBlocks.BUTTERCUPS);
        petalBlock(ModBlocks.FORGET_ME_NOTS);
        petalBlock(ModBlocks.BABY_BLUE_EYES);
        petalBlock(ModBlocks.SPEEDWELLS);
        woodSorrelsBlock(ModBlocks.WOOD_SORRELS);

        waterLilyBlock(ModBlocks.OPEN_WATER_LILY);
        waterLilyBlock(ModBlocks.OPEN_WHITE_WATER_LILY);
        waterLilyBlock(ModBlocks.OPEN_BLUE_WATER_LILY);
        waterLilyBlock(ModBlocks.CLOSED_WATER_LILY);
        waterLilyBlock(ModBlocks.CLOSED_WHITE_WATER_LILY);
        waterLilyBlock(ModBlocks.CLOSED_BLUE_WATER_LILY);
        leafLitterBlock(ModBlocks.DUCKWEEDS);

        simpleStateBlock(ModBlocks.WEATHER_VANE);
        simpleStateBlock(ModBlocks.SANDBAG);
        simpleStateBlock(ModBlocks.PINWHEEL);
        simpleStateBlock(ModBlocks.PYROTECHNICS_TABLE);
        simpleStateBlock(ModBlocks.PAPERCRAFT_TABLE);
    }
}
