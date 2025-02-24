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

        planks(pRecipeOutput, ModItems.GINKGO_PLANKS, ModTags.Items.GINKGO_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.GINKGO_WOOD, ModItems.GINKGO_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_GINKGO_WOOD, ModItems.STRIPPED_GINKGO_LOG);
        woodenStairs(pRecipeOutput, ModItems.GINKGO_STAIRS, ModItems.GINKGO_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.GINKGO_SLAB, ModItems.GINKGO_PLANKS);
        woodenFence(pRecipeOutput, ModItems.GINKGO_FENCE, ModItems.GINKGO_PLANKS);
        fenceGate(pRecipeOutput, ModItems.GINKGO_FENCE_GATE, ModItems.GINKGO_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.GINKGO_DOOR, ModItems.GINKGO_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.GINKGO_TRAPDOOR, ModItems.GINKGO_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.GINKGO_PRESSURE_PLATE, ModItems.GINKGO_PLANKS);
        woodenButton(pRecipeOutput, ModItems.GINKGO_BUTTON, ModItems.GINKGO_PLANKS);
        woodenSign(pRecipeOutput, ModItems.GINKGO_SIGN, ModItems.GINKGO_PLANKS);
        hangingSign(pRecipeOutput, ModItems.GINKGO_HANGING_SIGN, ModItems.STRIPPED_GINKGO_LOG);
        woodenBoat(pRecipeOutput, ModItems.GINKGO_BOAT, ModItems.GINKGO_PLANKS);
        chestBoat(pRecipeOutput, ModItems.GINKGO_CHEST_BOAT, ModItems.GINKGO_PLANKS);

        planks(pRecipeOutput, ModItems.MAPLE_PLANKS, ModTags.Items.MAPLE_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.MAPLE_WOOD, ModItems.MAPLE_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_MAPLE_WOOD, ModItems.STRIPPED_MAPLE_LOG);
        woodenStairs(pRecipeOutput, ModItems.MAPLE_STAIRS, ModItems.MAPLE_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.MAPLE_SLAB, ModItems.MAPLE_PLANKS);
        woodenFence(pRecipeOutput, ModItems.MAPLE_FENCE, ModItems.MAPLE_PLANKS);
        fenceGate(pRecipeOutput, ModItems.MAPLE_FENCE_GATE, ModItems.MAPLE_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.MAPLE_DOOR, ModItems.MAPLE_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.MAPLE_TRAPDOOR, ModItems.MAPLE_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.MAPLE_PRESSURE_PLATE, ModItems.MAPLE_PLANKS);
        woodenButton(pRecipeOutput, ModItems.MAPLE_BUTTON, ModItems.MAPLE_PLANKS);
        woodenSign(pRecipeOutput, ModItems.MAPLE_SIGN, ModItems.MAPLE_PLANKS);
        hangingSign(pRecipeOutput, ModItems.MAPLE_HANGING_SIGN, ModItems.STRIPPED_MAPLE_LOG);
        woodenBoat(pRecipeOutput, ModItems.MAPLE_BOAT, ModItems.MAPLE_PLANKS);
        chestBoat(pRecipeOutput, ModItems.MAPLE_CHEST_BOAT, ModItems.MAPLE_PLANKS);

        planks(pRecipeOutput, ModItems.FROST_PLANKS, ModTags.Items.FROST_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.FROST_WOOD, ModItems.FROST_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_FROST_WOOD, ModItems.STRIPPED_FROST_LOG);
        woodenStairs(pRecipeOutput, ModItems.FROST_STAIRS, ModItems.FROST_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.FROST_SLAB, ModItems.FROST_PLANKS);
        woodenFence(pRecipeOutput, ModItems.FROST_FENCE, ModItems.FROST_PLANKS);
        fenceGate(pRecipeOutput, ModItems.FROST_FENCE_GATE, ModItems.FROST_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.FROST_DOOR, ModItems.FROST_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.FROST_TRAPDOOR, ModItems.FROST_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.FROST_PRESSURE_PLATE, ModItems.FROST_PLANKS);
        woodenButton(pRecipeOutput, ModItems.FROST_BUTTON, ModItems.FROST_PLANKS);
        woodenSign(pRecipeOutput, ModItems.FROST_SIGN, ModItems.FROST_PLANKS);
        hangingSign(pRecipeOutput, ModItems.FROST_HANGING_SIGN, ModItems.STRIPPED_FROST_LOG);
        woodenBoat(pRecipeOutput, ModItems.FROST_BOAT, ModItems.FROST_PLANKS);
        chestBoat(pRecipeOutput, ModItems.FROST_CHEST_BOAT, ModItems.FROST_PLANKS);

        planks(pRecipeOutput, ModItems.DAWN_REDWOOD_PLANKS, ModTags.Items.DAWN_REDWOOD_LOGS);
        woodFromLogs(pRecipeOutput, ModItems.DAWN_REDWOOD_WOOD, ModItems.DAWN_REDWOOD_LOG);
        woodFromLogs(pRecipeOutput, ModItems.STRIPPED_DAWN_REDWOOD_WOOD, ModItems.STRIPPED_DAWN_REDWOOD_LOG);
        woodenStairs(pRecipeOutput, ModItems.DAWN_REDWOOD_STAIRS, ModItems.DAWN_REDWOOD_PLANKS);
        woodenSlab(pRecipeOutput, ModItems.DAWN_REDWOOD_SLAB, ModItems.DAWN_REDWOOD_PLANKS);
        woodenFence(pRecipeOutput, ModItems.DAWN_REDWOOD_FENCE, ModItems.DAWN_REDWOOD_PLANKS);
        fenceGate(pRecipeOutput, ModItems.DAWN_REDWOOD_FENCE_GATE, ModItems.DAWN_REDWOOD_PLANKS);
        woodenDoor(pRecipeOutput, ModItems.DAWN_REDWOOD_DOOR, ModItems.DAWN_REDWOOD_PLANKS);
        woodenTrapdoor(pRecipeOutput, ModItems.DAWN_REDWOOD_TRAPDOOR, ModItems.DAWN_REDWOOD_PLANKS);
        woodenPressurePlate(pRecipeOutput, ModItems.DAWN_REDWOOD_PRESSURE_PLATE, ModItems.DAWN_REDWOOD_PLANKS);
        woodenButton(pRecipeOutput, ModItems.DAWN_REDWOOD_BUTTON, ModItems.DAWN_REDWOOD_PLANKS);
        woodenSign(pRecipeOutput, ModItems.DAWN_REDWOOD_SIGN, ModItems.DAWN_REDWOOD_PLANKS);
        hangingSign(pRecipeOutput, ModItems.DAWN_REDWOOD_HANGING_SIGN, ModItems.STRIPPED_DAWN_REDWOOD_LOG);
        woodenBoat(pRecipeOutput, ModItems.DAWN_REDWOOD_BOAT, ModItems.DAWN_REDWOOD_PLANKS);
        chestBoat(pRecipeOutput, ModItems.DAWN_REDWOOD_CHEST_BOAT, ModItems.DAWN_REDWOOD_PLANKS);

        simpleDye(pRecipeOutput, Items.RED_DYE, ModItems.BEGONIAS, 1);
        simpleDye(pRecipeOutput, Items.WHITE_DYE, ModItems.WHITE_PETALS, 1);
        simpleDye(pRecipeOutput, Items.LIGHT_GRAY_DYE, ModItems.FROSTY_PETALS, 1);
        simpleDye(pRecipeOutput, Items.PINK_DYE, ModItems.PINK_DAISY, 1);
        simpleDye(pRecipeOutput, Items.RED_DYE, ModItems.RED_CARNATION, 1);
        simpleDye(pRecipeOutput, Items.PINK_DYE, ModItems.PINK_CARNATION, 1);
        simpleDye(pRecipeOutput, Items.WHITE_DYE, ModItems.WHITE_CARNATION, 1);
        simpleDye(pRecipeOutput, Items.RED_DYE, ModItems.RED_SPIDER_LILY, 1);
        simpleDye(pRecipeOutput, Items.YELLOW_DYE, ModItems.YELLOW_CHRYSANTHEMUM, 1);
        simpleDye(pRecipeOutput, Items.LIME_DYE, ModItems.GREEN_CHRYSANTHEMUM, 1);
        simpleDye(pRecipeOutput, Items.YELLOW_DYE, ModItems.OPEN_DAYBLOOM, 1);
        simpleDye(pRecipeOutput, Items.WHITE_DYE, ModItems.EDELWEISS, 1);
        simpleDye(pRecipeOutput, Items.PURPLE_DYE, ModItems.CROCUS, 1);
        simpleDye(pRecipeOutput, Items.PURPLE_DYE, ModItems.IRIS, 1);
    }
}
