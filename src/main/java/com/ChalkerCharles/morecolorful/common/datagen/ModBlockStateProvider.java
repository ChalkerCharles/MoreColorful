package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModBlockStateHelper;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends ModBlockStateHelper {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MoreColorful.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerStatesOnly();

        logAndWood(ModBlocks.CRABAPPLE_LOG.get(), ModBlocks.CRABAPPLE_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_CRABAPPLE_LOG.get(), ModBlocks.STRIPPED_CRABAPPLE_WOOD.get());
        simpleCube(ModBlocks.CRABAPPLE_PLANKS.get());
        simpleStairs(ModBlocks.CRABAPPLE_STAIRS.get(), ModBlocks.CRABAPPLE_PLANKS.get());
        simpleSlab(ModBlocks.CRABAPPLE_SLAB.get(), ModBlocks.CRABAPPLE_PLANKS.get());
        fenceBlock(ModBlocks.CRABAPPLE_FENCE.get(), blockTexture(ModBlocks.CRABAPPLE_PLANKS.get()));
        simpleFenceGate(ModBlocks.CRABAPPLE_FENCE_GATE.get(), ModBlocks.CRABAPPLE_PLANKS.get());
        simpleDoor(ModBlocks.CRABAPPLE_DOOR.get(), false);
        simpleTrapdoor(ModBlocks.CRABAPPLE_TRAPDOOR.get(), true, false);
        simplePressurePlate(ModBlocks.CRABAPPLE_PRESSURE_PLATE.get(), ModBlocks.CRABAPPLE_PLANKS.get());
        buttonBlock(ModBlocks.CRABAPPLE_BUTTON.get(), blockTexture(ModBlocks.CRABAPPLE_PLANKS.get()));
        signBlock(ModBlocks.CRABAPPLE_SIGN.get(), ModBlocks.CRABAPPLE_WALL_SIGN.get(), blockTexture(ModBlocks.CRABAPPLE_PLANKS.get()));
        hangingSignBlock(ModBlocks.CRABAPPLE_HANGING_SIGN.get(), ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.CRABAPPLE_PLANKS.get()));
        leaves(ModBlocks.CRABAPPLE_LEAVES.get());
        cross(ModBlocks.CRABAPPLE_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_CRABAPPLE_SAPLING.get(), ModBlocks.CRABAPPLE_SAPLING.get());
        leaves(ModBlocks.WHITE_CHERRY_LEAVES.get());
        cross(ModBlocks.WHITE_CHERRY_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_WHITE_CHERRY_SAPLING.get(), ModBlocks.WHITE_CHERRY_SAPLING.get());

        leaves(ModBlocks.ORANGE_BIRCH_LEAVES.get());
        cross(ModBlocks.ORANGE_BIRCH_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_ORANGE_BIRCH_SAPLING.get(), ModBlocks.ORANGE_BIRCH_SAPLING.get());
        leaves(ModBlocks.YELLOW_BIRCH_LEAVES.get());
        cross(ModBlocks.YELLOW_BIRCH_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_YELLOW_BIRCH_SAPLING.get(), ModBlocks.YELLOW_BIRCH_SAPLING.get());

        logAndWood(ModBlocks.EBONY_LOG.get(), ModBlocks.EBONY_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_EBONY_LOG.get(), ModBlocks.STRIPPED_EBONY_WOOD.get());
        simpleCube(ModBlocks.EBONY_PLANKS.get());
        simpleStairs(ModBlocks.EBONY_STAIRS.get(), ModBlocks.EBONY_PLANKS.get());
        simpleSlab(ModBlocks.EBONY_SLAB.get(), ModBlocks.EBONY_PLANKS.get());
        fenceBlock(ModBlocks.EBONY_FENCE.get(), blockTexture(ModBlocks.EBONY_PLANKS.get()));
        simpleFenceGate(ModBlocks.EBONY_FENCE_GATE.get(), ModBlocks.EBONY_PLANKS.get());
        simpleDoor(ModBlocks.EBONY_DOOR.get(), false);
        simpleTrapdoor(ModBlocks.EBONY_TRAPDOOR.get(), true, false);
        simplePressurePlate(ModBlocks.EBONY_PRESSURE_PLATE.get(), ModBlocks.EBONY_PLANKS.get());
        buttonBlock(ModBlocks.EBONY_BUTTON.get(), blockTexture(ModBlocks.EBONY_PLANKS.get()));
        signBlock(ModBlocks.EBONY_SIGN.get(), ModBlocks.EBONY_WALL_SIGN.get(), blockTexture(ModBlocks.EBONY_PLANKS.get()));
        hangingSignBlock(ModBlocks.EBONY_HANGING_SIGN.get(), ModBlocks.EBONY_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.EBONY_PLANKS.get()));

        logAndWood(ModBlocks.GINKGO_LOG.get(), ModBlocks.GINKGO_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_GINKGO_LOG.get(), ModBlocks.STRIPPED_GINKGO_WOOD.get());
        simpleCube(ModBlocks.GINKGO_PLANKS.get());
        simpleStairs(ModBlocks.GINKGO_STAIRS.get(), ModBlocks.GINKGO_PLANKS.get());
        simpleSlab(ModBlocks.GINKGO_SLAB.get(), ModBlocks.GINKGO_PLANKS.get());
        fenceBlock(ModBlocks.GINKGO_FENCE.get(), blockTexture(ModBlocks.GINKGO_PLANKS.get()));
        simpleFenceGate(ModBlocks.GINKGO_FENCE_GATE.get(), ModBlocks.GINKGO_PLANKS.get());
        simpleDoor(ModBlocks.GINKGO_DOOR.get(), true);
        simpleTrapdoor(ModBlocks.GINKGO_TRAPDOOR.get(), true, true);
        simplePressurePlate(ModBlocks.GINKGO_PRESSURE_PLATE.get(), ModBlocks.GINKGO_PLANKS.get());
        buttonBlock(ModBlocks.GINKGO_BUTTON.get(), blockTexture(ModBlocks.GINKGO_PLANKS.get()));
        signBlock(ModBlocks.GINKGO_SIGN.get(), ModBlocks.GINKGO_WALL_SIGN.get(), blockTexture(ModBlocks.GINKGO_PLANKS.get()));
        hangingSignBlock(ModBlocks.GINKGO_HANGING_SIGN.get(), ModBlocks.GINKGO_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.GINKGO_PLANKS.get()));
        leaves(ModBlocks.GINKGO_LEAVES.get());
        cross(ModBlocks.GINKGO_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_GINKGO_SAPLING.get(), ModBlocks.GINKGO_SAPLING.get());

        logAndWood(ModBlocks.MAPLE_LOG.get(), ModBlocks.MAPLE_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_MAPLE_LOG.get(), ModBlocks.STRIPPED_MAPLE_WOOD.get());
        simpleCube(ModBlocks.MAPLE_PLANKS.get());
        simpleStairs(ModBlocks.MAPLE_STAIRS.get(), ModBlocks.MAPLE_PLANKS.get());
        simpleSlab(ModBlocks.MAPLE_SLAB.get(), ModBlocks.MAPLE_PLANKS.get());
        fenceBlock(ModBlocks.MAPLE_FENCE.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        simpleFenceGate(ModBlocks.MAPLE_FENCE_GATE.get(), ModBlocks.MAPLE_PLANKS.get());
        simpleDoor(ModBlocks.MAPLE_DOOR.get(), false);
        simpleTrapdoor(ModBlocks.MAPLE_TRAPDOOR.get(), true, false);
        simplePressurePlate(ModBlocks.MAPLE_PRESSURE_PLATE.get(), ModBlocks.MAPLE_PLANKS.get());
        buttonBlock(ModBlocks.MAPLE_BUTTON.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        signBlock(ModBlocks.MAPLE_SIGN.get(), ModBlocks.MAPLE_WALL_SIGN.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        hangingSignBlock(ModBlocks.MAPLE_HANGING_SIGN.get(), ModBlocks.MAPLE_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.MAPLE_PLANKS.get()));
        leaves(ModBlocks.MAPLE_LEAVES.get());
        cross(ModBlocks.MAPLE_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_MAPLE_SAPLING.get(), ModBlocks.MAPLE_SAPLING.get());

        logAndWood(ModBlocks.FROST_LOG.get(), ModBlocks.FROST_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_FROST_LOG.get(), ModBlocks.STRIPPED_FROST_WOOD.get());
        simpleCube(ModBlocks.FROST_PLANKS.get());
        simpleStairs(ModBlocks.FROST_STAIRS.get(), ModBlocks.FROST_PLANKS.get());
        simpleSlab(ModBlocks.FROST_SLAB.get(), ModBlocks.FROST_PLANKS.get());
        fenceBlock(ModBlocks.FROST_FENCE.get(), blockTexture(ModBlocks.FROST_PLANKS.get()));
        simpleFenceGate(ModBlocks.FROST_FENCE_GATE.get(), ModBlocks.FROST_PLANKS.get());
        simpleDoor(ModBlocks.FROST_DOOR.get(), false);
        simpleTrapdoor(ModBlocks.FROST_TRAPDOOR.get(), true, false);
        simplePressurePlate(ModBlocks.FROST_PRESSURE_PLATE.get(), ModBlocks.FROST_PLANKS.get());
        buttonBlock(ModBlocks.FROST_BUTTON.get(), blockTexture(ModBlocks.FROST_PLANKS.get()));
        signBlock(ModBlocks.FROST_SIGN.get(), ModBlocks.FROST_WALL_SIGN.get(), blockTexture(ModBlocks.FROST_PLANKS.get()));
        hangingSignBlock(ModBlocks.FROST_HANGING_SIGN.get(), ModBlocks.FROST_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.FROST_PLANKS.get()));
        leaves(ModBlocks.FROST_LEAVES.get());
        cross(ModBlocks.FROST_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_FROST_SAPLING.get(), ModBlocks.FROST_SAPLING.get());

        logAndWood(ModBlocks.DAWN_REDWOOD_LOG.get(), ModBlocks.DAWN_REDWOOD_WOOD.get());
        logAndWood(ModBlocks.STRIPPED_DAWN_REDWOOD_LOG.get(), ModBlocks.STRIPPED_DAWN_REDWOOD_WOOD.get());
        simpleCube(ModBlocks.DAWN_REDWOOD_PLANKS.get());
        simpleStairs(ModBlocks.DAWN_REDWOOD_STAIRS.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get());
        simpleSlab(ModBlocks.DAWN_REDWOOD_SLAB.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get());
        fenceBlock(ModBlocks.DAWN_REDWOOD_FENCE.get(), blockTexture(ModBlocks.DAWN_REDWOOD_PLANKS.get()));
        simpleFenceGate(ModBlocks.DAWN_REDWOOD_FENCE_GATE.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get());
        simpleDoor(ModBlocks.DAWN_REDWOOD_DOOR.get(), false);
        simpleTrapdoor(ModBlocks.DAWN_REDWOOD_TRAPDOOR.get(), false, false);
        simplePressurePlate(ModBlocks.DAWN_REDWOOD_PRESSURE_PLATE.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get());
        buttonBlock(ModBlocks.DAWN_REDWOOD_BUTTON.get(), blockTexture(ModBlocks.DAWN_REDWOOD_PLANKS.get()));
        signBlock(ModBlocks.DAWN_REDWOOD_SIGN.get(), ModBlocks.DAWN_REDWOOD_WALL_SIGN.get(), blockTexture(ModBlocks.DAWN_REDWOOD_PLANKS.get()));
        hangingSignBlock(ModBlocks.DAWN_REDWOOD_HANGING_SIGN.get(), ModBlocks.DAWN_REDWOOD_WALL_HANGING_SIGN.get(), blockTexture(ModBlocks.DAWN_REDWOOD_PLANKS.get()));
        leaves(ModBlocks.DAWN_REDWOOD_LEAVES.get());
        cross(ModBlocks.DAWN_REDWOOD_SAPLING.get());
        simpleFlowerPot(ModBlocks.POTTED_DAWN_REDWOOD_SAPLING.get(), ModBlocks.DAWN_REDWOOD_SAPLING.get());

        cross(ModBlocks.PINK_DAISY.get());
        simpleFlowerPot(ModBlocks.POTTED_PINK_DAISY.get(), ModBlocks.PINK_DAISY.get());
        cross(ModBlocks.RED_CARNATION.get());
        simpleFlowerPot(ModBlocks.POTTED_RED_CARNATION.get(), ModBlocks.RED_CARNATION.get());
        cross(ModBlocks.PINK_CARNATION.get());
        simpleFlowerPot(ModBlocks.POTTED_PINK_CARNATION.get(), ModBlocks.PINK_CARNATION.get());
        cross(ModBlocks.WHITE_CARNATION.get());
        simpleFlowerPot(ModBlocks.POTTED_WHITE_CARNATION.get(), ModBlocks.WHITE_CARNATION.get());
        cross(ModBlocks.RED_SPIDER_LILY.get());
        simpleFlowerPot(ModBlocks.POTTED_RED_SPIDER_LILY.get(), ModBlocks.RED_SPIDER_LILY.get());
        cross(ModBlocks.YELLOW_CHRYSANTHEMUM.get());
        simpleFlowerPot(ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM.get(), ModBlocks.YELLOW_CHRYSANTHEMUM.get());
        cross(ModBlocks.GREEN_CHRYSANTHEMUM.get());
        simpleFlowerPot(ModBlocks.POTTED_GREEN_CHRYSANTHEMUM.get(), ModBlocks.GREEN_CHRYSANTHEMUM.get());
        cross(ModBlocks.OPEN_DAYBLOOM.get());
        simpleFlowerPot(ModBlocks.POTTED_OPEN_DAYBLOOM.get(), ModBlocks.OPEN_DAYBLOOM.get());
        cross(ModBlocks.CLOSED_DAYBLOOM.get());
        simpleFlowerPot(ModBlocks.POTTED_CLOSED_DAYBLOOM.get(), ModBlocks.CLOSED_DAYBLOOM.get());
        cross(ModBlocks.EDELWEISS.get());
        simpleFlowerPot(ModBlocks.POTTED_EDELWEISS.get(), ModBlocks.EDELWEISS.get());
        cross(ModBlocks.CROCUS.get());
        simpleFlowerPot(ModBlocks.POTTED_CROCUS.get(), ModBlocks.CROCUS.get());
        cross(ModBlocks.IRIS.get());
        simpleFlowerPot(ModBlocks.POTTED_IRIS.get(), ModBlocks.IRIS.get());

        berryBush(ModBlocks.STRAWBERRY_BUSH.get());
        berryBush(ModBlocks.BLUEBERRY_BUSH.get());
    }

    private void registerStatesOnly() {
        directionalDoubleBlock(ModBlocks.HARP.get());
        simpleStateBlock(ModBlocks.BASS_DRUM.get());
        simpleStateBlock(ModBlocks.SNARE_DRUM.get());
        simpleStateBlock(ModBlocks.TOMTOM_DRUM.get());
        simpleDoubleBlock(ModBlocks.RIDE_CYMBAL.get());
        directionalDoubleBlock(ModBlocks.CRASH_CYMBAL.get());
        directionalDoubleBlock(ModBlocks.CHIMES.get());
        simpleDirectionalBlock(ModBlocks.GLOCKENSPIEL.get());
        horizontalDoubleBlock(ModBlocks.XYLOPHONE.get());
        horizontalDoubleBlock(ModBlocks.VIBRAPHONE.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLING.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_BIT.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SAW.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK.get());
        horizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS.get());
        horizontalDoubleBlock(ModBlocks.GUZHENG.get());

        petalBlock(ModBlocks.BEGONIAS.get());
        petalBlock(ModBlocks.WHITE_PETALS.get());
        leafLitterBlock(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get());
        leafLitterBlock(ModBlocks.YELLOW_BIRCH_LEAF_LITTER.get());
        leafLitterBlock(ModBlocks.GINKGO_LEAF_LITTER.get());
        leafLitterBlock(ModBlocks.MAPLE_LEAF_LITTER.get());
        petalBlock(ModBlocks.FROSTY_PETALS.get());
        leafLitterBlock(ModBlocks.DAWN_REDWOOD_LEAF_LITTER.get());
    }
}
