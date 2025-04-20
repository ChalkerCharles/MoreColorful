package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModItemModelHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ModItemModelHelper {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.HARP.get());
        basicItem(ModItems.UPRIGHT_PIANO.get());
        basicItem(ModItems.GRAND_PIANO.get());
        basicItem(ModItems.BASS_DRUM.get());
        basicItem(ModItems.SNARE_DRUM.get());
        basicItem(ModItems.TOMTOM_DRUM.get());
        basicItem(ModItems.HIHAT.get());
        basicItem(ModItems.RIDE_CYMBAL.get());
        basicItem(ModItems.CRASH_CYMBAL.get());
        basicItem(ModItems.DRUM_SET.get());
        basicItem(ModItems.CHIMES.get());
        basicItem(ModItems.GLOCKENSPIEL.get());
        basicItem(ModItems.XYLOPHONE.get());
        basicItem(ModItems.VIBRAPHONE.get());
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_BIT.get());
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_PLING.get());
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_SCULK.get());
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_AMETHYST.get());
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_SAW.get(), "synthesizer_keyboard_red");
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_PLUCK.get(), "synthesizer_keyboard_blue");
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_SYNTH_BASS.get(), "synthesizer_keyboard_silver");
        basicItem(ModItems.GUZHENG.get());

        buttonFenceWall(ModItems.CRABAPPLE_FENCE.get(), ModBlocks.CRABAPPLE_PLANKS.get(), "fence");
        basicItem(ModItems.CRABAPPLE_DOOR.get());
        buttonFenceWall(ModItems.CRABAPPLE_BUTTON.get(), ModBlocks.CRABAPPLE_PLANKS.get(), "button");
        basicItem(ModItems.CRABAPPLE_SIGN.get());
        basicItem(ModItems.CRABAPPLE_HANGING_SIGN.get());
        basicItem(ModItems.CRABAPPLE_BOAT.get());
        basicItem(ModItems.CRABAPPLE_CHEST_BOAT.get());
        blockItem2d(ModItems.CRABAPPLE_SAPLING.get());
        basicItem(ModItems.BEGONIAS.get());
        blockItem2d(ModItems.WHITE_CHERRY_SAPLING.get());
        basicItem(ModItems.WHITE_PETALS.get());

        blockItem2d(ModItems.ORANGE_BIRCH_SAPLING.get());
        blockItem2d(ModItems.ORANGE_BIRCH_LEAF_LITTER.get());
        blockItem2d(ModItems.YELLOW_BIRCH_SAPLING.get());
        blockItem2d(ModItems.YELLOW_BIRCH_LEAF_LITTER.get());

        buttonFenceWall(ModItems.EBONY_FENCE.get(), ModBlocks.EBONY_PLANKS.get(), "fence");
        basicItem(ModItems.EBONY_DOOR.get());
        buttonFenceWall(ModItems.EBONY_BUTTON.get(), ModBlocks.EBONY_PLANKS.get(), "button");
        basicItem(ModItems.EBONY_SIGN.get());
        basicItem(ModItems.EBONY_HANGING_SIGN.get());
        basicItem(ModItems.EBONY_BOAT.get());
        basicItem(ModItems.EBONY_CHEST_BOAT.get());

        buttonFenceWall(ModItems.GINKGO_FENCE.get(), ModBlocks.GINKGO_PLANKS.get(), "fence");
        basicItem(ModItems.GINKGO_DOOR.get());
        buttonFenceWall(ModItems.GINKGO_BUTTON.get(), ModBlocks.GINKGO_PLANKS.get(), "button");
        basicItem(ModItems.GINKGO_SIGN.get());
        basicItem(ModItems.GINKGO_HANGING_SIGN.get());
        basicItem(ModItems.GINKGO_BOAT.get());
        basicItem(ModItems.GINKGO_CHEST_BOAT.get());
        blockItem2d(ModItems.GINKGO_SAPLING.get());
        blockItem2d(ModItems.GINKGO_LEAF_LITTER.get());

        buttonFenceWall(ModItems.MAPLE_FENCE.get(), ModBlocks.MAPLE_PLANKS.get(), "fence");
        basicItem(ModItems.MAPLE_DOOR.get());
        buttonFenceWall(ModItems.MAPLE_BUTTON.get(), ModBlocks.MAPLE_PLANKS.get(), "button");
        basicItem(ModItems.MAPLE_SIGN.get());
        basicItem(ModItems.MAPLE_HANGING_SIGN.get());
        basicItem(ModItems.MAPLE_BOAT.get());
        basicItem(ModItems.MAPLE_CHEST_BOAT.get());
        blockItem2d(ModItems.MAPLE_SAPLING.get());
        blockItem2d(ModItems.MAPLE_LEAF_LITTER.get());

        buttonFenceWall(ModItems.FROST_FENCE.get(), ModBlocks.FROST_PLANKS.get(), "fence");
        basicItem(ModItems.FROST_DOOR.get());
        buttonFenceWall(ModItems.FROST_BUTTON.get(), ModBlocks.FROST_PLANKS.get(), "button");
        basicItem(ModItems.FROST_SIGN.get());
        basicItem(ModItems.FROST_HANGING_SIGN.get());
        basicItem(ModItems.FROST_BOAT.get());
        basicItem(ModItems.FROST_CHEST_BOAT.get());
        blockItem2d(ModItems.FROST_SAPLING.get());
        basicItem(ModItems.FROSTY_PETALS.get());

        buttonFenceWall(ModItems.DAWN_REDWOOD_FENCE.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get(), "fence");
        basicItem(ModItems.DAWN_REDWOOD_DOOR.get());
        buttonFenceWall(ModItems.DAWN_REDWOOD_BUTTON.get(), ModBlocks.DAWN_REDWOOD_PLANKS.get(), "button");
        basicItem(ModItems.DAWN_REDWOOD_SIGN.get());
        basicItem(ModItems.DAWN_REDWOOD_HANGING_SIGN.get());
        basicItem(ModItems.DAWN_REDWOOD_BOAT.get());
        basicItem(ModItems.DAWN_REDWOOD_CHEST_BOAT.get());
        blockItem2d(ModItems.DAWN_REDWOOD_SAPLING.get());
        blockItem2d(ModItems.DAWN_REDWOOD_LEAF_LITTER.get());
        basicItem(ModItems.DAWN_REDWOOD_ROOTS.get());

        buttonFenceWall(ModItems.JACARANDA_FENCE.get(), ModBlocks.JACARANDA_PLANKS.get(), "fence");
        basicItem(ModItems.JACARANDA_DOOR.get());
        buttonFenceWall(ModItems.JACARANDA_BUTTON.get(), ModBlocks.JACARANDA_PLANKS.get(), "button");
        basicItem(ModItems.JACARANDA_SIGN.get());
        basicItem(ModItems.JACARANDA_HANGING_SIGN.get());
        basicItem(ModItems.JACARANDA_BOAT.get());
        basicItem(ModItems.JACARANDA_CHEST_BOAT.get());
        blockItem2d(ModItems.JACARANDA_SAPLING.get());
        basicItem(ModItems.VIOLETS.get());
        basicItem(ModItems.BUTTERCUPS.get());
        basicItem(ModItems.FORGET_ME_NOTS.get());
        basicItem(ModItems.BABY_BLUE_EYES.get());
        basicItem(ModItems.SPEEDWELLS.get());
        basicItem(ModItems.WOOD_SORRELS.get());

        buttonFenceWall(ModItems.WILLOW_FENCE.get(), ModBlocks.WILLOW_PLANKS.get(), "fence");
        basicItem(ModItems.WILLOW_DOOR.get());
        buttonFenceWall(ModItems.WILLOW_BUTTON.get(), ModBlocks.WILLOW_PLANKS.get(), "button");
        basicItem(ModItems.WILLOW_SIGN.get());
        basicItem(ModItems.WILLOW_HANGING_SIGN.get());
        basicItem(ModItems.WILLOW_BOAT.get());
        basicItem(ModItems.WILLOW_CHEST_BOAT.get());
        blockItem2d(ModItems.WILLOW_SAPLING.get());
        blockItem2d(ModItems.WILLOW_BRANCHES.get(), "willow_branches_tip");

        blockItem2d(ModItems.PINK_DAISY.get());
        blockItem2d(ModItems.RED_CARNATION.get());
        blockItem2d(ModItems.PINK_CARNATION.get());
        blockItem2d(ModItems.WHITE_CARNATION.get());
        blockItem2d(ModItems.RED_SPIDER_LILY.get());
        blockItem2d(ModItems.YELLOW_CHRYSANTHEMUM.get());
        blockItem2d(ModItems.GREEN_CHRYSANTHEMUM.get());
        blockItem2d(ModItems.OPEN_DAYBLOOM.get());
        blockItem2d(ModItems.CLOSED_DAYBLOOM.get());
        blockItem2d(ModItems.EDELWEISS.get());
        blockItem2d(ModItems.CROCUS.get());
        blockItem2d(ModItems.IRIS.get());
        blockItem2d(ModItems.LAVENDER.get());
        blockItem2d(ModItems.DAFFODIL.get());
        blockItem2d(ModItems.GERBERA_DAISY.get());
        blockItem2d(ModItems.RAPESEED_FLOWER.get());

        blockItem2d(ModItems.CATTAIL.get(), "cattail_top");
        blockItem2d(ModItems.TALL_RAPESEED_FLOWER.get(), "tall_rapeseed_flower_top");

        blockItem2d(ModItems.SHORT_WATER_GRASS.get(), "short_water_grass_top");
        blockItem2d(ModItems.TALL_WATER_GRASS.get(), "tall_water_grass_top");
        basicItem(ModItems.REED.get());
        basicItem(ModItems.OPEN_WATER_LILY.get());
        basicItem(ModItems.OPEN_WHITE_WATER_LILY.get());
        basicItem(ModItems.OPEN_BLUE_WATER_LILY.get());
        blockItem2d(ModItems.CLOSED_WATER_LILY.get());
        blockItem2d(ModItems.CLOSED_WHITE_WATER_LILY.get());
        blockItem2d(ModItems.CLOSED_BLUE_WATER_LILY.get());
        blockItem2d(ModItems.DUCKWEEDS.get());

        basicItem(ModItems.STRAWBERRY.get());
        basicItem(ModItems.BLUEBERRIES.get());
    }
}
