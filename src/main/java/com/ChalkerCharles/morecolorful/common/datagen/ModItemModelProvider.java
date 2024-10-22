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

        blockItem2d(ModItems.AUTUMN_BIRCH_SAPLING.get());
        basicItem(ModItems.AUTUMN_BIRCH_LEAF_PILE.get());

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
        basicItem(ModItems.GINKGO_LEAF_PILE.get());

        buttonFenceWall(ModItems.MAPLE_FENCE.get(), ModBlocks.MAPLE_PLANKS.get(), "fence");
        basicItem(ModItems.MAPLE_DOOR.get());
        buttonFenceWall(ModItems.MAPLE_BUTTON.get(), ModBlocks.MAPLE_PLANKS.get(), "button");
        basicItem(ModItems.MAPLE_SIGN.get());
        basicItem(ModItems.MAPLE_HANGING_SIGN.get());
        basicItem(ModItems.MAPLE_BOAT.get());
        basicItem(ModItems.MAPLE_CHEST_BOAT.get());
        blockItem2d(ModItems.MAPLE_SAPLING.get());
        basicItem(ModItems.MAPLE_LEAF_PILE.get());

        blockItem2d(ModItems.PINK_DAISY.get());
        blockItem2d(ModItems.RED_CARNATION.get());
        blockItem2d(ModItems.PINK_CARNATION.get());
        blockItem2d(ModItems.WHITE_CARNATION.get());
        blockItem2d(ModItems.RED_SPIDER_LILY.get());
        blockItem2d(ModItems.YELLOW_CHRYSANTHEMUM.get());
        blockItem2d(ModItems.GREEN_CHRYSANTHEMUM.get());
        blockItem2d(ModItems.DAYBLOOM.get());
    }
}
