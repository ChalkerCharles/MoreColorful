package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.misc.BalloonItem;
import com.ChalkerCharles.morecolorful.common.item.misc.PartyPopperItem;
import com.ChalkerCharles.morecolorful.common.recipe.WritableSheetMusicRecipe;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("SameParameterValue")
public abstract class ModRecipeHelper extends RecipeProvider implements IConditionBuilder {
    public ModRecipeHelper(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    protected static void planks(RecipeOutput output, ItemLike planks, TagKey<Item> pLogs) {
        planksFromLogs(output, planks, pLogs, 4);
    }

    protected static void woodenStairs(RecipeOutput output, ItemLike stairs, ItemLike material) {
        stairBuilder(stairs, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_stairs")
                .save(output);
    }

    protected static void woodenSlab(RecipeOutput output, ItemLike slab, ItemLike material) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(material))
                .unlockedBy("has_item", has(material))
                .group("wooden_slab")
                .save(output);
    }

    protected static void woodenFence(RecipeOutput output, ItemLike fence, ItemLike material) {
        fenceBuilder(fence, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_fence")
                .save(output);
    }

    protected static void fenceGate(RecipeOutput output, ItemLike fenceGate, ItemLike material) {
        fenceGateBuilder(fenceGate, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_fence_gate")
                .save(output);
    }

    protected static void woodenDoor(RecipeOutput output, ItemLike door, ItemLike material) {
        doorBuilder(door, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_door")
                .save(output);
    }

    protected static void woodenTrapdoor(RecipeOutput output, ItemLike trapdoor, ItemLike material) {
        trapdoorBuilder(trapdoor, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_trapdoor")
                .save(output);
    }

    protected static void woodenPressurePlate(RecipeOutput output, ItemLike pressurePlate, ItemLike material) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, pressurePlate, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_pressure_plate")
                .save(output);
    }

    protected static void woodenButton(RecipeOutput output, ItemLike button, ItemLike material) {
        buttonBuilder(button, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_button")
                .save(output);
    }

    protected static void woodenSign(RecipeOutput output, ItemLike sign, ItemLike material) {
        signBuilder(sign, Ingredient.of(material))
                .unlockedBy("has_planks", has(material))
                .group("wooden_sign")
                .save(output);
    }

    protected static void simpleRecipe(RecipeOutput output, ItemLike result, ItemLike material, int count, RecipeCategory category) {
        String pKey = getItemName(result) + "_from_" + getItemName(material);
        ShapelessRecipeBuilder.shapeless(category, result, count).requires(material)
                .unlockedBy(getHasName(material), has(material))
                .group(getItemName(result))
                .save(output, MoreColorful.location(pKey));
    }

    protected static void simpleMiscRecipe(RecipeOutput output, ItemLike result, ItemLike material, int count) {
        simpleRecipe(output, result, material, count, RecipeCategory.MISC);
    }

    protected static void simpleMiscRecipe(RecipeOutput output, ItemLike result, ItemLike material) {
        simpleMiscRecipe(output, result, material, 1);
    }

    protected static void simpleShapedRecipe(RecipeOutput output, ItemLike result, ItemLike material, int count, String pattern, RecipeCategory category) {
        String pKey = getItemName(result) + "_from_" + getItemName(material);
        ShapedRecipeBuilder.shaped(category, result, count)
                .define('#', material)
                .pattern(pattern)
                .unlockedBy(getHasName(material), has(material))
                .save(output, MoreColorful.location(pKey));
    }

    protected static void miscShapedRecipe(RecipeOutput output, ItemLike result, ItemLike material, int count, String pattern) {
        simpleShapedRecipe(output, result, material, count, pattern, RecipeCategory.MISC);
    }

    protected static void sheetMusicRecipe(RecipeOutput output) {
        ResourceLocation id = RecipeBuilder.getDefaultRecipeId(ModItems.WRITABLE_SHEET_MUSIC);
        AdvancementHolder advancement = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .addCriterion("has_note_block", has(Items.NOTE_BLOCK))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(AdvancementRequirements.Strategy.OR)
                .build(id.withPrefix("recipes/misc/"));
        output.accept(id, new WritableSheetMusicRecipe(CraftingBookCategory.MISC), advancement);
    }

    protected static void partyPopperRecipes(RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dye = color.getTag();
            ItemLike item = PartyPopperItem.byColor(color);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item)
                    .requires(dye)
                    .requires(Ingredient.of(ItemUtils.dyeingIngredients(PartyPopperItem.ALL_COLORS, item)))
                    .group("party_popper")
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }
    
    protected static void ribbon(RecipeOutput output, ItemLike result, ItemLike material) {
            ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 8)
                    .define('#', material)
                    .pattern("# #")
                    .pattern(" # ")
                    .pattern("# #")
                    .group("ribbon")
                    .unlockedBy(getHasName(material), has(material))
                    .save(output);
    }

    protected static void ribbonDyeRecipes(RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dye = color.getTag();
            ItemLike item = RibbonBlock.itemByColor(color);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, item)
                    .requires(dye)
                    .requires(Ingredient.of(ItemUtils.dyeingIngredients(RibbonBlock.ALL_ITEMS, item)))
                    .group("ribbon")
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }

    protected static void sparkler(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('X', material)
                .define('#', Tags.Items.GUNPOWDERS)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("X")
                .pattern("#")
                .pattern("|")
                .group("sparkler")
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
                .save(output);
    }

    protected static void sparkler(RecipeOutput output, ItemLike result, TagKey<Item> material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, result)
                .define('X', material)
                .define('#', Tags.Items.GUNPOWDERS)
                .define('|', Tags.Items.RODS_WOODEN)
                .pattern("X")
                .pattern("#")
                .pattern("|")
                .group("sparkler")
                .unlockedBy("has_gunpowder", has(Tags.Items.GUNPOWDERS))
                .save(output);
    }

    protected static void pennant(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, result, 3)
                .define('|', Tags.Items.RODS_WOODEN)
                .define('#', material)
                .pattern("|# ")
                .pattern("|##")
                .pattern("|  ")
                .group("pennant")
                .unlockedBy(getHasName(material), has(material))
                .save(output);
    }

    protected static void pennantDyeRecipes(RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dye = color.getTag();
            ItemLike item = PennantBlock.itemByColor(color);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, item)
                    .requires(dye)
                    .requires(Ingredient.of(ItemUtils.dyeingIngredients(PennantBlock.ALL_ITEMS, item)))
                    .group("pennant")
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }

    protected static void bundleDyeRecipes(RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dye = color.getTag();
            ItemLike item = ItemUtils.bundleByColor(color);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, item)
                    .requires(dye)
                    .requires(Ingredient.of(ItemUtils.dyeingIngredients(ItemUtils.ALL_BUNDLES, item)))
                    .group("bundle")
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }

    protected static void balloonDyeRecipes(RecipeOutput output) {
        for (DyeColor color : DyeColor.values()) {
            TagKey<Item> dye = color.getTag();
            ItemLike item = BalloonItem.byColor(color);
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, item)
                    .requires(dye)
                    .requires(Ingredient.of(ItemUtils.dyeingIngredients(BalloonItem.COMMON, item)))
                    .group("balloon")
                    .unlockedBy("has_needed_dye", has(dye))
                    .save(output, "dye_" + getItemName(item));
        }
    }

    protected static void fireworkShapeTemplate(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(Items.PAPER)
                .requires(Tags.Items.GUNPOWDERS)
                .requires(material)
                .group("firework_shape_template")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected static void fireworkShapeTemplate(RecipeOutput output, ItemLike result, TagKey<Item> material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(Items.PAPER)
                .requires(Tags.Items.GUNPOWDERS)
                .requires(material)
                .group("firework_shape_template")
                .unlockedBy("has_material", has(material))
                .save(output);
    }

    protected static void specialBalloon(RecipeOutput output, ItemLike result, ItemLike material) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, result)
                .requires(material)
                .group("balloon")
                .unlockedBy(getHasName(material), has(material))
                .save(output);
    }
}
