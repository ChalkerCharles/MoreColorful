package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModItemModelHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.misc.PartyPopperItem;
import com.ChalkerCharles.morecolorful.common.item.misc.PinwheelItem;
import com.ChalkerCharles.morecolorful.common.item.misc.SparklerItem;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ModItemModelHelper {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.HARP);
        basicItem(ModItems.UPRIGHT_PIANO);
        basicItem(ModItems.GRAND_PIANO);
        basicItem(ModItems.BASS_DRUM);
        basicItem(ModItems.SNARE_DRUM);
        basicItem(ModItems.TOMTOM_DRUM);
        basicItem(ModItems.HIHAT);
        basicItem(ModItems.RIDE_CYMBAL);
        basicItem(ModItems.CRASH_CYMBAL);
        basicItem(ModItems.DRUM_SET);
        basicItem(ModItems.CHIMES);
        basicItem(ModItems.GLOCKENSPIEL);
        basicItem(ModItems.XYLOPHONE);
        basicItem(ModItems.VIBRAPHONE);
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_BIT);
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_PLING);
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_SCULK);
        basicItem(ModItems.SYNTHESIZER_KEYBOARD_AMETHYST);
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_SAW, "synthesizer_keyboard_red");
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_PLUCK, "synthesizer_keyboard_blue");
        itemWithCustomName(ModItems.SYNTHESIZER_KEYBOARD_SYNTH_BASS, "synthesizer_keyboard_silver");
        basicItem(ModItems.GUZHENG);
        basicItem(ModItems.MUSIC_BOX);
        basicItem(ModItems.WRITABLE_SHEET_MUSIC);
        basicItem(ModItems.SHEET_MUSIC);

        buttonFenceWall(ModItems.CRABAPPLE_FENCE, ModBlocks.CRABAPPLE_PLANKS, "fence");
        basicItem(ModItems.CRABAPPLE_DOOR);
        buttonFenceWall(ModItems.CRABAPPLE_BUTTON, ModBlocks.CRABAPPLE_PLANKS, "button");
        basicItem(ModItems.CRABAPPLE_SIGN);
        basicItem(ModItems.CRABAPPLE_HANGING_SIGN);
        basicItem(ModItems.CRABAPPLE_BOAT);
        basicItem(ModItems.CRABAPPLE_CHEST_BOAT);
        blockItem2d(ModItems.CRABAPPLE_SAPLING);
        basicItem(ModItems.BEGONIAS);
        blockItem2d(ModItems.WHITE_CHERRY_SAPLING);
        basicItem(ModItems.WHITE_PETALS);

        blockItem2d(ModItems.ORANGE_BIRCH_SAPLING);
        blockItem2d(ModItems.ORANGE_BIRCH_LEAF_LITTER);
        blockItem2d(ModItems.YELLOW_BIRCH_SAPLING);
        blockItem2d(ModItems.YELLOW_BIRCH_LEAF_LITTER);

        buttonFenceWall(ModItems.EBONY_FENCE, ModBlocks.EBONY_PLANKS, "fence");
        basicItem(ModItems.EBONY_DOOR);
        buttonFenceWall(ModItems.EBONY_BUTTON, ModBlocks.EBONY_PLANKS, "button");
        basicItem(ModItems.EBONY_SIGN);
        basicItem(ModItems.EBONY_HANGING_SIGN);
        basicItem(ModItems.EBONY_BOAT);
        basicItem(ModItems.EBONY_CHEST_BOAT);

        buttonFenceWall(ModItems.GINKGO_FENCE, ModBlocks.GINKGO_PLANKS, "fence");
        basicItem(ModItems.GINKGO_DOOR);
        buttonFenceWall(ModItems.GINKGO_BUTTON, ModBlocks.GINKGO_PLANKS, "button");
        basicItem(ModItems.GINKGO_SIGN);
        basicItem(ModItems.GINKGO_HANGING_SIGN);
        basicItem(ModItems.GINKGO_BOAT);
        basicItem(ModItems.GINKGO_CHEST_BOAT);
        blockItem2d(ModItems.GINKGO_SAPLING);
        blockItem2d(ModItems.GINKGO_LEAF_LITTER);

        buttonFenceWall(ModItems.MAPLE_FENCE, ModBlocks.MAPLE_PLANKS, "fence");
        basicItem(ModItems.MAPLE_DOOR);
        buttonFenceWall(ModItems.MAPLE_BUTTON, ModBlocks.MAPLE_PLANKS, "button");
        basicItem(ModItems.MAPLE_SIGN);
        basicItem(ModItems.MAPLE_HANGING_SIGN);
        basicItem(ModItems.MAPLE_BOAT);
        basicItem(ModItems.MAPLE_CHEST_BOAT);
        blockItem2d(ModItems.MAPLE_SAPLING);
        blockItem2d(ModItems.MAPLE_LEAF_LITTER);

        buttonFenceWall(ModItems.FROST_FENCE, ModBlocks.FROST_PLANKS, "fence");
        basicItem(ModItems.FROST_DOOR);
        buttonFenceWall(ModItems.FROST_BUTTON, ModBlocks.FROST_PLANKS, "button");
        basicItem(ModItems.FROST_SIGN);
        basicItem(ModItems.FROST_HANGING_SIGN);
        basicItem(ModItems.FROST_BOAT);
        basicItem(ModItems.FROST_CHEST_BOAT);
        blockItem2d(ModItems.FROST_SAPLING);
        basicItem(ModItems.FROSTY_PETALS);

        buttonFenceWall(ModItems.DAWN_REDWOOD_FENCE, ModBlocks.DAWN_REDWOOD_PLANKS, "fence");
        basicItem(ModItems.DAWN_REDWOOD_DOOR);
        buttonFenceWall(ModItems.DAWN_REDWOOD_BUTTON, ModBlocks.DAWN_REDWOOD_PLANKS, "button");
        basicItem(ModItems.DAWN_REDWOOD_SIGN);
        basicItem(ModItems.DAWN_REDWOOD_HANGING_SIGN);
        basicItem(ModItems.DAWN_REDWOOD_BOAT);
        basicItem(ModItems.DAWN_REDWOOD_CHEST_BOAT);
        blockItem2d(ModItems.DAWN_REDWOOD_SAPLING);
        blockItem2d(ModItems.DAWN_REDWOOD_LEAF_LITTER);
        basicItem(ModItems.DAWN_REDWOOD_ROOTS);

        buttonFenceWall(ModItems.JACARANDA_FENCE, ModBlocks.JACARANDA_PLANKS, "fence");
        basicItem(ModItems.JACARANDA_DOOR);
        buttonFenceWall(ModItems.JACARANDA_BUTTON, ModBlocks.JACARANDA_PLANKS, "button");
        basicItem(ModItems.JACARANDA_SIGN);
        basicItem(ModItems.JACARANDA_HANGING_SIGN);
        basicItem(ModItems.JACARANDA_BOAT);
        basicItem(ModItems.JACARANDA_CHEST_BOAT);
        blockItem2d(ModItems.JACARANDA_SAPLING);
        basicItem(ModItems.VIOLETS);
        basicItem(ModItems.BUTTERCUPS);
        basicItem(ModItems.FORGET_ME_NOTS);
        basicItem(ModItems.BABY_BLUE_EYES);
        basicItem(ModItems.SPEEDWELLS);
        basicItem(ModItems.WOOD_SORRELS);

        buttonFenceWall(ModItems.WILLOW_FENCE, ModBlocks.WILLOW_PLANKS, "fence");
        basicItem(ModItems.WILLOW_DOOR);
        buttonFenceWall(ModItems.WILLOW_BUTTON, ModBlocks.WILLOW_PLANKS, "button");
        basicItem(ModItems.WILLOW_SIGN);
        basicItem(ModItems.WILLOW_HANGING_SIGN);
        basicItem(ModItems.WILLOW_BOAT);
        basicItem(ModItems.WILLOW_CHEST_BOAT);
        blockItem2d(ModItems.WILLOW_SAPLING);
        blockItem2d(ModItems.WILLOW_BRANCHES, "willow_branches_tip");

        blockItem2d(ModItems.PINK_DAISY);
        blockItem2d(ModItems.RED_CARNATION);
        blockItem2d(ModItems.PINK_CARNATION);
        blockItem2d(ModItems.WHITE_CARNATION);
        blockItem2d(ModItems.RED_SPIDER_LILY);
        blockItem2d(ModItems.YELLOW_CHRYSANTHEMUM);
        blockItem2d(ModItems.GREEN_CHRYSANTHEMUM);
        blockItem2d(ModItems.OPEN_DAYBLOOM);
        blockItem2d(ModItems.CLOSED_DAYBLOOM);
        blockItem2d(ModItems.EDELWEISS);
        blockItem2d(ModItems.CROCUS);
        blockItem2d(ModItems.IRIS);
        blockItem2d(ModItems.LAVENDER);
        blockItem2d(ModItems.DAFFODIL);
        blockItem2d(ModItems.GERBERA_DAISY);
        blockItem2d(ModItems.RAPESEED_FLOWER);
        blockItem2d(ModItems.WINDFLOWER);

        blockItem2d(ModItems.CATTAIL, "cattail_top");
        blockItem2d(ModItems.TALL_RAPESEED_FLOWER, "tall_rapeseed_flower_top");

        blockItem2d(ModItems.SHORT_WATER_GRASS, "short_water_grass_top");
        blockItem2d(ModItems.TALL_WATER_GRASS, "tall_water_grass_top");
        basicItem(ModItems.REED);
        basicItem(ModItems.OPEN_WATER_LILY);
        basicItem(ModItems.OPEN_WHITE_WATER_LILY);
        basicItem(ModItems.OPEN_BLUE_WATER_LILY);
        blockItem2d(ModItems.CLOSED_WATER_LILY);
        blockItem2d(ModItems.CLOSED_WHITE_WATER_LILY);
        blockItem2d(ModItems.CLOSED_BLUE_WATER_LILY);
        blockItem2d(ModItems.DUCKWEEDS);

        basicItem(ModItems.STRAWBERRY);
        basicItem(ModItems.BLUEBERRIES);

        fromBlock(ModItems.FAN_BLOCK, ModBlocks.FAN_BLOCK);
        blockItem2d(ModItems.WEATHER_VANE);

        basicItem(ModItems.PAPER_PLANE);
        basicItem(ModItems.PAPER_BOAT);
        basicItem(ModItems.CONFETTI);
        for (ItemLike item : PartyPopperItem.ALL_COLORS) {
            basicItem(item);
        }
        for (ItemLike item : RibbonBlock.ALL_ITEMS) {
            ribbon(item);
        }
        for (ItemLike item : PinwheelItem.ALL_DYE_COLORS) {
            pinwheel(item);
        }
        pinwheel(ModItems.MULTICOLORED_PINWHEEL);
        for (ItemLike item : SparklerItem.ALL_ITEMS) {
            sparkler(item);
        }
    }
}
