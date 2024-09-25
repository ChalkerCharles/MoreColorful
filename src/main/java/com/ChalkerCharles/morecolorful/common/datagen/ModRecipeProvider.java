package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModRecipeHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends ModRecipeHelper {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        planks(pRecipeOutput, ModItems.CRABAPPLE_PLANKS, ModTags.Items.CRABAPPLE_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.CRABAPPLE_WOOD, ModItems.CRABAPPLE_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_CRABAPPLE_WOOD, ModItems.STRIPPED_CRABAPPLE_LOG);
        woodenStairs(pRecipeOutput, ModItems.CRABAPPLE_STAIRS, ModItems.CRABAPPLE_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.CRABAPPLE_SLAB, ModItems.CRABAPPLE_PLANKS);
        woodenFence(pRecipeOutput, ModItems.CRABAPPLE_FENCE, ModItems.CRABAPPLE_PLANKS);
        fenceGate(pRecipeOutput, ModItems.CRABAPPLE_FENCE_GATE, ModItems.CRABAPPLE_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.CRABAPPLE_DOOR, ModItems.CRABAPPLE_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.CRABAPPLE_TRAPDOOR, ModItems.CRABAPPLE_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.CRABAPPLE_PRESSURE_PLATE, ModItems.CRABAPPLE_PLANKS);
        woodenButton(pRecipeOutput, ModItems.CRABAPPLE_BUTTON, ModItems.CRABAPPLE_PLANKS);
        woodenSign(pRecipeOutput, ModItems.CRABAPPLE_SIGN, ModItems.CRABAPPLE_PLANKS);
        hangingSign(pRecipeOutput, ModItems.CRABAPPLE_HANGING_SIGN, ModItems.STRIPPED_CRABAPPLE_LOG);
        woodenBoat(pRecipeOutput, ModItems.CRABAPPLE_BOAT, ModItems.CRABAPPLE_PLANKS);
        chestBoat(pRecipeOutput, ModItems.CRABAPPLE_CHEST_BOAT, ModItems.CRABAPPLE_PLANKS);

        planks(pRecipeOutput, ModItems.EBONY_PLANKS, ModTags.Items.EBONY_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.EBONY_WOOD, ModItems.EBONY_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_EBONY_WOOD, ModItems.STRIPPED_EBONY_LOG);
        woodenStairs(pRecipeOutput, ModItems.EBONY_STAIRS, ModItems.EBONY_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.EBONY_SLAB, ModItems.EBONY_PLANKS);
        woodenFence(pRecipeOutput, ModItems.EBONY_FENCE, ModItems.EBONY_PLANKS);
        fenceGate(pRecipeOutput, ModItems.EBONY_FENCE_GATE, ModItems.EBONY_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.EBONY_DOOR, ModItems.EBONY_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.EBONY_TRAPDOOR, ModItems.EBONY_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.EBONY_PRESSURE_PLATE, ModItems.EBONY_PLANKS);
        woodenButton(pRecipeOutput, ModItems.EBONY_BUTTON, ModItems.EBONY_PLANKS);
        woodenSign(pRecipeOutput, ModItems.EBONY_SIGN, ModItems.EBONY_PLANKS);
        hangingSign(pRecipeOutput, ModItems.EBONY_HANGING_SIGN, ModItems.STRIPPED_EBONY_LOG);
        woodenBoat(pRecipeOutput, ModItems.EBONY_BOAT, ModItems.EBONY_PLANKS);
        chestBoat(pRecipeOutput, ModItems.EBONY_CHEST_BOAT, ModItems.EBONY_PLANKS);

        simpleDye(pRecipeOutput, Items.RED_DYE, ModItems.BEGONIAS, 1);
        simpleDye(pRecipeOutput, Items.PINK_DYE, ModItems.PINK_DAISY, 1);
        simpleDye(pRecipeOutput, Items.RED_DYE, ModItems.RED_CARNATION, 1);
        simpleDye(pRecipeOutput, Items.PINK_DYE, ModItems.PINK_CARNATION, 1);
    }
}
