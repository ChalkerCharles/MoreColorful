package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModRecipeHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.recipe.SheetMusicCloningRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends ModRecipeHelper {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        planks(recipeOutput, ModItems.CRABAPPLE_PLANKS, ModTags.Items.CRABAPPLE_LOGS);
        woodFromLogs(recipeOutput, ModItems.CRABAPPLE_WOOD, ModItems.CRABAPPLE_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_CRABAPPLE_WOOD, ModItems.STRIPPED_CRABAPPLE_LOG);
        woodenStairs(recipeOutput, ModItems.CRABAPPLE_STAIRS, ModItems.CRABAPPLE_PLANKS);
        woodenSlab(recipeOutput, ModItems.CRABAPPLE_SLAB, ModItems.CRABAPPLE_PLANKS);
        woodenFence(recipeOutput, ModItems.CRABAPPLE_FENCE, ModItems.CRABAPPLE_PLANKS);
        fenceGate(recipeOutput, ModItems.CRABAPPLE_FENCE_GATE, ModItems.CRABAPPLE_PLANKS);
        woodenDoor(recipeOutput, ModItems.CRABAPPLE_DOOR, ModItems.CRABAPPLE_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.CRABAPPLE_TRAPDOOR, ModItems.CRABAPPLE_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.CRABAPPLE_PRESSURE_PLATE, ModItems.CRABAPPLE_PLANKS);
        woodenButton(recipeOutput, ModItems.CRABAPPLE_BUTTON, ModItems.CRABAPPLE_PLANKS);
        woodenSign(recipeOutput, ModItems.CRABAPPLE_SIGN, ModItems.CRABAPPLE_PLANKS);
        hangingSign(recipeOutput, ModItems.CRABAPPLE_HANGING_SIGN, ModItems.STRIPPED_CRABAPPLE_LOG);
        woodenBoat(recipeOutput, ModItems.CRABAPPLE_BOAT, ModItems.CRABAPPLE_PLANKS);
        chestBoat(recipeOutput, ModItems.CRABAPPLE_CHEST_BOAT, ModItems.CRABAPPLE_PLANKS);

        planks(recipeOutput, ModItems.EBONY_PLANKS, ModTags.Items.EBONY_LOGS);
        woodFromLogs(recipeOutput, ModItems.EBONY_WOOD, ModItems.EBONY_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_EBONY_WOOD, ModItems.STRIPPED_EBONY_LOG);
        woodenStairs(recipeOutput, ModItems.EBONY_STAIRS, ModItems.EBONY_PLANKS);
        woodenSlab(recipeOutput, ModItems.EBONY_SLAB, ModItems.EBONY_PLANKS);
        woodenFence(recipeOutput, ModItems.EBONY_FENCE, ModItems.EBONY_PLANKS);
        fenceGate(recipeOutput, ModItems.EBONY_FENCE_GATE, ModItems.EBONY_PLANKS);
        woodenDoor(recipeOutput, ModItems.EBONY_DOOR, ModItems.EBONY_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.EBONY_TRAPDOOR, ModItems.EBONY_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.EBONY_PRESSURE_PLATE, ModItems.EBONY_PLANKS);
        woodenButton(recipeOutput, ModItems.EBONY_BUTTON, ModItems.EBONY_PLANKS);
        woodenSign(recipeOutput, ModItems.EBONY_SIGN, ModItems.EBONY_PLANKS);
        hangingSign(recipeOutput, ModItems.EBONY_HANGING_SIGN, ModItems.STRIPPED_EBONY_LOG);
        woodenBoat(recipeOutput, ModItems.EBONY_BOAT, ModItems.EBONY_PLANKS);
        chestBoat(recipeOutput, ModItems.EBONY_CHEST_BOAT, ModItems.EBONY_PLANKS);

        planks(recipeOutput, ModItems.GINKGO_PLANKS, ModTags.Items.GINKGO_LOGS);
        woodFromLogs(recipeOutput, ModItems.GINKGO_WOOD, ModItems.GINKGO_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_GINKGO_WOOD, ModItems.STRIPPED_GINKGO_LOG);
        woodenStairs(recipeOutput, ModItems.GINKGO_STAIRS, ModItems.GINKGO_PLANKS);
        woodenSlab(recipeOutput, ModItems.GINKGO_SLAB, ModItems.GINKGO_PLANKS);
        woodenFence(recipeOutput, ModItems.GINKGO_FENCE, ModItems.GINKGO_PLANKS);
        fenceGate(recipeOutput, ModItems.GINKGO_FENCE_GATE, ModItems.GINKGO_PLANKS);
        woodenDoor(recipeOutput, ModItems.GINKGO_DOOR, ModItems.GINKGO_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.GINKGO_TRAPDOOR, ModItems.GINKGO_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.GINKGO_PRESSURE_PLATE, ModItems.GINKGO_PLANKS);
        woodenButton(recipeOutput, ModItems.GINKGO_BUTTON, ModItems.GINKGO_PLANKS);
        woodenSign(recipeOutput, ModItems.GINKGO_SIGN, ModItems.GINKGO_PLANKS);
        hangingSign(recipeOutput, ModItems.GINKGO_HANGING_SIGN, ModItems.STRIPPED_GINKGO_LOG);
        woodenBoat(recipeOutput, ModItems.GINKGO_BOAT, ModItems.GINKGO_PLANKS);
        chestBoat(recipeOutput, ModItems.GINKGO_CHEST_BOAT, ModItems.GINKGO_PLANKS);

        planks(recipeOutput, ModItems.MAPLE_PLANKS, ModTags.Items.MAPLE_LOGS);
        woodFromLogs(recipeOutput, ModItems.MAPLE_WOOD, ModItems.MAPLE_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_MAPLE_WOOD, ModItems.STRIPPED_MAPLE_LOG);
        woodenStairs(recipeOutput, ModItems.MAPLE_STAIRS, ModItems.MAPLE_PLANKS);
        woodenSlab(recipeOutput, ModItems.MAPLE_SLAB, ModItems.MAPLE_PLANKS);
        woodenFence(recipeOutput, ModItems.MAPLE_FENCE, ModItems.MAPLE_PLANKS);
        fenceGate(recipeOutput, ModItems.MAPLE_FENCE_GATE, ModItems.MAPLE_PLANKS);
        woodenDoor(recipeOutput, ModItems.MAPLE_DOOR, ModItems.MAPLE_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.MAPLE_TRAPDOOR, ModItems.MAPLE_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.MAPLE_PRESSURE_PLATE, ModItems.MAPLE_PLANKS);
        woodenButton(recipeOutput, ModItems.MAPLE_BUTTON, ModItems.MAPLE_PLANKS);
        woodenSign(recipeOutput, ModItems.MAPLE_SIGN, ModItems.MAPLE_PLANKS);
        hangingSign(recipeOutput, ModItems.MAPLE_HANGING_SIGN, ModItems.STRIPPED_MAPLE_LOG);
        woodenBoat(recipeOutput, ModItems.MAPLE_BOAT, ModItems.MAPLE_PLANKS);
        chestBoat(recipeOutput, ModItems.MAPLE_CHEST_BOAT, ModItems.MAPLE_PLANKS);

        planks(recipeOutput, ModItems.FROST_PLANKS, ModTags.Items.FROST_LOGS);
        woodFromLogs(recipeOutput, ModItems.FROST_WOOD, ModItems.FROST_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_FROST_WOOD, ModItems.STRIPPED_FROST_LOG);
        woodenStairs(recipeOutput, ModItems.FROST_STAIRS, ModItems.FROST_PLANKS);
        woodenSlab(recipeOutput, ModItems.FROST_SLAB, ModItems.FROST_PLANKS);
        woodenFence(recipeOutput, ModItems.FROST_FENCE, ModItems.FROST_PLANKS);
        fenceGate(recipeOutput, ModItems.FROST_FENCE_GATE, ModItems.FROST_PLANKS);
        woodenDoor(recipeOutput, ModItems.FROST_DOOR, ModItems.FROST_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.FROST_TRAPDOOR, ModItems.FROST_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.FROST_PRESSURE_PLATE, ModItems.FROST_PLANKS);
        woodenButton(recipeOutput, ModItems.FROST_BUTTON, ModItems.FROST_PLANKS);
        woodenSign(recipeOutput, ModItems.FROST_SIGN, ModItems.FROST_PLANKS);
        hangingSign(recipeOutput, ModItems.FROST_HANGING_SIGN, ModItems.STRIPPED_FROST_LOG);
        woodenBoat(recipeOutput, ModItems.FROST_BOAT, ModItems.FROST_PLANKS);
        chestBoat(recipeOutput, ModItems.FROST_CHEST_BOAT, ModItems.FROST_PLANKS);

        planks(recipeOutput, ModItems.DAWN_REDWOOD_PLANKS, ModTags.Items.DAWN_REDWOOD_LOGS);
        woodFromLogs(recipeOutput, ModItems.DAWN_REDWOOD_WOOD, ModItems.DAWN_REDWOOD_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_DAWN_REDWOOD_WOOD, ModItems.STRIPPED_DAWN_REDWOOD_LOG);
        woodenStairs(recipeOutput, ModItems.DAWN_REDWOOD_STAIRS, ModItems.DAWN_REDWOOD_PLANKS);
        woodenSlab(recipeOutput, ModItems.DAWN_REDWOOD_SLAB, ModItems.DAWN_REDWOOD_PLANKS);
        woodenFence(recipeOutput, ModItems.DAWN_REDWOOD_FENCE, ModItems.DAWN_REDWOOD_PLANKS);
        fenceGate(recipeOutput, ModItems.DAWN_REDWOOD_FENCE_GATE, ModItems.DAWN_REDWOOD_PLANKS);
        woodenDoor(recipeOutput, ModItems.DAWN_REDWOOD_DOOR, ModItems.DAWN_REDWOOD_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.DAWN_REDWOOD_TRAPDOOR, ModItems.DAWN_REDWOOD_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.DAWN_REDWOOD_PRESSURE_PLATE, ModItems.DAWN_REDWOOD_PLANKS);
        woodenButton(recipeOutput, ModItems.DAWN_REDWOOD_BUTTON, ModItems.DAWN_REDWOOD_PLANKS);
        woodenSign(recipeOutput, ModItems.DAWN_REDWOOD_SIGN, ModItems.DAWN_REDWOOD_PLANKS);
        hangingSign(recipeOutput, ModItems.DAWN_REDWOOD_HANGING_SIGN, ModItems.STRIPPED_DAWN_REDWOOD_LOG);
        woodenBoat(recipeOutput, ModItems.DAWN_REDWOOD_BOAT, ModItems.DAWN_REDWOOD_PLANKS);
        chestBoat(recipeOutput, ModItems.DAWN_REDWOOD_CHEST_BOAT, ModItems.DAWN_REDWOOD_PLANKS);

        planks(recipeOutput, ModItems.JACARANDA_PLANKS, ModTags.Items.JACARANDA_LOGS);
        woodFromLogs(recipeOutput, ModItems.JACARANDA_WOOD, ModItems.JACARANDA_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_JACARANDA_WOOD, ModItems.STRIPPED_JACARANDA_LOG);
        woodenStairs(recipeOutput, ModItems.JACARANDA_STAIRS, ModItems.JACARANDA_PLANKS);
        woodenSlab(recipeOutput, ModItems.JACARANDA_SLAB, ModItems.JACARANDA_PLANKS);
        woodenFence(recipeOutput, ModItems.JACARANDA_FENCE, ModItems.JACARANDA_PLANKS);
        fenceGate(recipeOutput, ModItems.JACARANDA_FENCE_GATE, ModItems.JACARANDA_PLANKS);
        woodenDoor(recipeOutput, ModItems.JACARANDA_DOOR, ModItems.JACARANDA_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.JACARANDA_TRAPDOOR, ModItems.JACARANDA_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.JACARANDA_PRESSURE_PLATE, ModItems.JACARANDA_PLANKS);
        woodenButton(recipeOutput, ModItems.JACARANDA_BUTTON, ModItems.JACARANDA_PLANKS);
        woodenSign(recipeOutput, ModItems.JACARANDA_SIGN, ModItems.JACARANDA_PLANKS);
        hangingSign(recipeOutput, ModItems.JACARANDA_HANGING_SIGN, ModItems.STRIPPED_JACARANDA_LOG);
        woodenBoat(recipeOutput, ModItems.JACARANDA_BOAT, ModItems.JACARANDA_PLANKS);
        chestBoat(recipeOutput, ModItems.JACARANDA_CHEST_BOAT, ModItems.JACARANDA_PLANKS);

        planks(recipeOutput, ModItems.WILLOW_PLANKS, ModTags.Items.WILLOW_LOGS);
        woodFromLogs(recipeOutput, ModItems.WILLOW_WOOD, ModItems.WILLOW_LOG);
        woodFromLogs(recipeOutput, ModItems.STRIPPED_WILLOW_WOOD, ModItems.STRIPPED_WILLOW_LOG);
        woodenStairs(recipeOutput, ModItems.WILLOW_STAIRS, ModItems.WILLOW_PLANKS);
        woodenSlab(recipeOutput, ModItems.WILLOW_SLAB, ModItems.WILLOW_PLANKS);
        woodenFence(recipeOutput, ModItems.WILLOW_FENCE, ModItems.WILLOW_PLANKS);
        fenceGate(recipeOutput, ModItems.WILLOW_FENCE_GATE, ModItems.WILLOW_PLANKS);
        woodenDoor(recipeOutput, ModItems.WILLOW_DOOR, ModItems.WILLOW_PLANKS);
        woodenTrapdoor(recipeOutput, ModItems.WILLOW_TRAPDOOR, ModItems.WILLOW_PLANKS);
        woodenPressurePlate(recipeOutput, ModItems.WILLOW_PRESSURE_PLATE, ModItems.WILLOW_PLANKS);
        woodenButton(recipeOutput, ModItems.WILLOW_BUTTON, ModItems.WILLOW_PLANKS);
        woodenSign(recipeOutput, ModItems.WILLOW_SIGN, ModItems.WILLOW_PLANKS);
        hangingSign(recipeOutput, ModItems.WILLOW_HANGING_SIGN, ModItems.STRIPPED_WILLOW_LOG);
        woodenBoat(recipeOutput, ModItems.WILLOW_BOAT, ModItems.WILLOW_PLANKS);
        chestBoat(recipeOutput, ModItems.WILLOW_CHEST_BOAT, ModItems.WILLOW_PLANKS);

        simpleMiscRecipe(recipeOutput, Items.RED_DYE, ModItems.BEGONIAS);
        simpleMiscRecipe(recipeOutput, Items.WHITE_DYE, ModItems.WHITE_PETALS);
        simpleMiscRecipe(recipeOutput, Items.LIGHT_GRAY_DYE, ModItems.FROSTY_PETALS);
        simpleMiscRecipe(recipeOutput, Items.PURPLE_DYE, ModItems.VIOLETS);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.BUTTERCUPS);
        simpleMiscRecipe(recipeOutput, Items.LIGHT_BLUE_DYE, ModItems.FORGET_ME_NOTS);
        simpleMiscRecipe(recipeOutput, Items.LIGHT_BLUE_DYE, ModItems.BABY_BLUE_EYES);
        simpleMiscRecipe(recipeOutput, Items.BLUE_DYE, ModItems.SPEEDWELLS);
        simpleMiscRecipe(recipeOutput, Items.MAGENTA_DYE, ModItems.WOOD_SORRELS);
        simpleMiscRecipe(recipeOutput, Items.PINK_DYE, ModItems.PINK_DAISY);
        simpleMiscRecipe(recipeOutput, Items.RED_DYE, ModItems.RED_CARNATION);
        simpleMiscRecipe(recipeOutput, Items.PINK_DYE, ModItems.PINK_CARNATION);
        simpleMiscRecipe(recipeOutput, Items.WHITE_DYE, ModItems.WHITE_CARNATION);
        simpleMiscRecipe(recipeOutput, Items.RED_DYE, ModItems.RED_SPIDER_LILY);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.YELLOW_CHRYSANTHEMUM);
        simpleMiscRecipe(recipeOutput, Items.LIME_DYE, ModItems.GREEN_CHRYSANTHEMUM);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.OPEN_DAYBLOOM);
        simpleMiscRecipe(recipeOutput, Items.WHITE_DYE, ModItems.EDELWEISS);
        simpleMiscRecipe(recipeOutput, Items.PURPLE_DYE, ModItems.CROCUS);
        simpleMiscRecipe(recipeOutput, Items.PURPLE_DYE, ModItems.IRIS);
        simpleMiscRecipe(recipeOutput, Items.PURPLE_DYE, ModItems.LAVENDER);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.DAFFODIL);
        simpleMiscRecipe(recipeOutput, Items.MAGENTA_DYE, ModItems.GERBERA_DAISY);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.RAPESEED_FLOWER);
        simpleMiscRecipe(recipeOutput, Items.LIGHT_GRAY_DYE, ModItems.WINDFLOWER);
        simpleMiscRecipe(recipeOutput, Items.PINK_DYE, ModItems.OPEN_WATER_LILY);
        simpleMiscRecipe(recipeOutput, Items.WHITE_DYE, ModItems.OPEN_WHITE_WATER_LILY);
        simpleMiscRecipe(recipeOutput, Items.BLUE_DYE, ModItems.OPEN_BLUE_WATER_LILY);

        simpleMiscRecipe(recipeOutput, Items.STRING, ModItems.CATTAIL);
        simpleMiscRecipe(recipeOutput, Items.YELLOW_DYE, ModItems.TALL_RAPESEED_FLOWER, 2);
        miscShapedRecipe(recipeOutput, Items.PAPER, ModItems.REED, 3, "###");

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModItems.WEATHER_VANE)
                .define('|', Items.IRON_BARS)
                .define('#', Items.IRON_INGOT)
                .pattern(" # ")
                .pattern("#|#")
                .pattern(" | ")
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PAPER_PLANE, 3)
                .define('#', Items.PAPER)
                .pattern("##")
                .pattern(" #")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.PAPER_BOAT, 3)
                .define('#', Items.PAPER)
                .pattern("# #")
                .pattern(" # ")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MUSIC_BOX)
                .define('#', ItemTags.PLANKS)
                .define('@', Tags.Items.INGOTS_IRON)
                .define('L', Items.LEVER)
                .define('N', Items.NOTE_BLOCK)
                .pattern("###")
                .pattern("#@L")
                .pattern("#N#")
                .unlockedBy("has_note_block", has(Items.NOTE_BLOCK))
                .save(recipeOutput);
        sheetMusicRecipe(recipeOutput);
        SpecialRecipeBuilder.special(SheetMusicCloningRecipe::new).save(recipeOutput, MoreColorful.location("sheet_music_cloning"));
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CONFETTI, 3)
                .requires(Items.PAPER, 3)
                .requires(Items.RED_DYE)
                .requires(Items.YELLOW_DYE)
                .requires(Items.BLUE_DYE)
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WHITE_PARTY_POPPER)
                .define('#', Items.PAPER)
                .define('@', ModItems.CONFETTI)
                .define('S', Tags.Items.STRINGS)
                .pattern(" #@")
                .pattern(" ##")
                .pattern("S  ")
                .group("party_popper")
                .unlockedBy("has_confetti", has(ModItems.CONFETTI))
                .save(recipeOutput);
        partyPopperRecipes(recipeOutput);
        ribbon(recipeOutput, ModItems.WHITE_RIBBON, Items.WHITE_WOOL);
        ribbon(recipeOutput, ModItems.ORANGE_RIBBON, Items.ORANGE_WOOL);
        ribbon(recipeOutput, ModItems.MAGENTA_RIBBON, Items.MAGENTA_WOOL);
        ribbon(recipeOutput, ModItems.LIGHT_BLUE_RIBBON, Items.LIGHT_BLUE_WOOL);
        ribbon(recipeOutput, ModItems.YELLOW_RIBBON, Items.YELLOW_WOOL);
        ribbon(recipeOutput, ModItems.LIME_RIBBON, Items.LIME_WOOL);
        ribbon(recipeOutput, ModItems.PINK_RIBBON, Items.PINK_WOOL);
        ribbon(recipeOutput, ModItems.GRAY_RIBBON, Items.GRAY_WOOL);
        ribbon(recipeOutput, ModItems.LIGHT_GRAY_RIBBON, Items.LIGHT_GRAY_WOOL);
        ribbon(recipeOutput, ModItems.CYAN_RIBBON, Items.CYAN_WOOL);
        ribbon(recipeOutput, ModItems.PURPLE_RIBBON, Items.PURPLE_WOOL);
        ribbon(recipeOutput, ModItems.BLUE_RIBBON, Items.BLUE_WOOL);
        ribbon(recipeOutput, ModItems.BROWN_RIBBON, Items.BROWN_WOOL);
        ribbon(recipeOutput, ModItems.GREEN_RIBBON, Items.GREEN_WOOL);
        ribbon(recipeOutput, ModItems.RED_RIBBON, Items.RED_WOOL);
        ribbon(recipeOutput, ModItems.BLACK_RIBBON, Items.BLACK_WOOL);
        ribbonDyeRecipes(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.WHITE_PINWHEEL)
                .define('#', Items.PAPER)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern(" # ")
                .pattern("#|#")
                .pattern("|# ")
                .group("pinwheel")
                .unlockedBy("has_paper", has(Items.PAPER))
                .save(recipeOutput);
        pinwheelRecipes(recipeOutput);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.MULTICOLORED_PINWHEEL)
                .requires(Items.RED_DYE)
                .requires(Items.YELLOW_DYE)
                .requires(Items.BLUE_DYE)
                .requires(ModTags.Items.PINWHEELS)
                .group("pinwheel")
                .unlockedBy("has_pinwheel", has(ModTags.Items.PINWHEELS))
                .save(recipeOutput);
        sparkler(recipeOutput, ModItems.WHITE_SPARKLER, Tags.Items.GEMS_PRISMARINE);
        sparkler(recipeOutput, ModItems.BROWN_SPARKLER, Tags.Items.DUSTS_GLOWSTONE);
        sparkler(recipeOutput, ModItems.RED_SPARKLER, Tags.Items.DUSTS_REDSTONE);
        sparkler(recipeOutput, ModItems.YELLOW_SPARKLER, Items.BLAZE_POWDER);
        sparkler(recipeOutput, ModItems.LIME_SPARKLER, Tags.Items.GEMS_EMERALD);
        sparkler(recipeOutput, ModItems.CYAN_SPARKLER, ItemTags.SOUL_FIRE_BASE_BLOCKS);
        sparkler(recipeOutput, ModItems.LIGHT_BLUE_SPARKLER, Tags.Items.GEMS_DIAMOND);
        sparkler(recipeOutput, ModItems.BLUE_SPARKLER, Tags.Items.GEMS_LAPIS);
        sparkler(recipeOutput, ModItems.PURPLE_SPARKLER, Tags.Items.GEMS_AMETHYST);
        sparkler(recipeOutput, ModItems.MAGENTA_SPARKLER, Items.DRAGON_BREATH);
    }
}
