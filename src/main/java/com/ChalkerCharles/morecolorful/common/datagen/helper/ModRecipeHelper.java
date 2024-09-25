package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
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

    protected static void woodenStairs(RecipeOutput output, ItemLike pStairs, ItemLike pMaterial) {
        stairBuilder(pStairs, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_stairs")
                .save(output);
    }

    protected static void woodenSlab(RecipeOutput output, ItemLike pSlab, ItemLike pMaterial) {
        slabBuilder(RecipeCategory.BUILDING_BLOCKS, pSlab, Ingredient.of(pMaterial))
                .unlockedBy("has_item", has(pMaterial))
                .group("wooden_slab")
                .save(output);
    }

    protected static void woodenFence(RecipeOutput output, ItemLike pFence, ItemLike pMaterial) {
        fenceBuilder(pFence, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_fence")
                .save(output);
    }

    protected static void fenceGate(RecipeOutput output, ItemLike pFenceGate, ItemLike pMaterial) {
        fenceGateBuilder(pFenceGate, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_fence_gate")
                .save(output);
    }

    protected static void woodenDoor(RecipeOutput output, ItemLike pDoor, ItemLike pMaterial) {
        doorBuilder(pDoor, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_door")
                .save(output);
    }

    protected static void woodenTrapdoor(RecipeOutput output, ItemLike pTrapdoor, ItemLike pMaterial) {
        trapdoorBuilder(pTrapdoor, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_trapdoor")
                .save(output);
    }

    protected static void woodenPressurePlate(RecipeOutput output, ItemLike pPressurePlate, ItemLike pMaterial) {
        pressurePlateBuilder(RecipeCategory.REDSTONE, pPressurePlate, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_pressure_plate")
                .save(output);
    }

    protected static void woodenButton(RecipeOutput output, ItemLike pButton, ItemLike pMaterial) {
        buttonBuilder(pButton, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_button")
                .save(output);
    }

    protected static void woodenSign(RecipeOutput output, ItemLike pSign, ItemLike pMaterial) {
        signBuilder(pSign, Ingredient.of(pMaterial))
                .unlockedBy("has_planks", has(pMaterial))
                .group("wooden_sign")
                .save(output);
    }

    protected static void simpleDye(RecipeOutput output, ItemLike pDye, ItemLike pMaterial, int count) {
        String pKey = getItemName(pDye) + "_from_" + getItemName(pMaterial);
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, pDye, count).requires(pMaterial)
                .unlockedBy(getHasName(pMaterial), has(pMaterial))
                .group(getItemName(pDye))
                .save(output, ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pKey));
    }
}
