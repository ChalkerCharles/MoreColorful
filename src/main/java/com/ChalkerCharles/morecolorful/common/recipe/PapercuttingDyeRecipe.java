package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

public class PapercuttingDyeRecipe extends CustomRecipe {
    public PapercuttingDyeRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput input, Level pLevel) {
        ItemStack stack = ItemStack.EMPTY;
        ItemStack dye = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (PapercuttingBlock.isPapercutting(item.getItem())) {
                    if (!stack.isEmpty()) {
                        return false;
                    }
                    stack = item;
                } else {
                    if (!(item.is(Tags.Items.DYES) && dye.isEmpty())) {
                        return false;
                    }
                    dye = item;
                }
            }
        }
        return !stack.isEmpty() && !dye.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider pRegistries) {
        ItemStack stack = ItemStack.EMPTY;
        ItemStack dye = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (PapercuttingBlock.isPapercutting(item.getItem())) {
                    if (!stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    stack = item;
                } else {
                    if (!(item.is(Tags.Items.DYES)) && dye.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    dye = item;
                }
            }
        }
        if (!stack.isEmpty() && !dye.isEmpty()) {
            DyeColor color = DyeColor.getColor(dye);
            if (color != null) {
                return stack.transmuteCopy(PapercuttingBlock.itemByColor(color), 1);
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAPERCUTTING_DYE.get();
    }
}
