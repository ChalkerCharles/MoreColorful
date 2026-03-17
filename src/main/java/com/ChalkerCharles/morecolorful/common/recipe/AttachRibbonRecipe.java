package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class AttachRibbonRecipe extends CustomRecipe {
    public AttachRibbonRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    public static boolean canAttachRibbon(ItemStack stack) {
        return stack.is(ModItems.KITE);
    }

    @Override
    public boolean matches(CraftingInput input, Level pLevel) {
        ItemStack stack = ItemStack.EMPTY;
        ItemStack ribbon = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (canAttachRibbon(item)) {
                    if (!stack.isEmpty()) {
                        return false;
                    }
                    stack = item;
                } else {
                    if (!(item.is(ModTags.Items.RIBBONS) && ribbon.isEmpty())) {
                        return false;
                    }
                    ribbon = item;
                }
            }
        }
        return !stack.isEmpty() && !ribbon.isEmpty() && !stack.has(ModDataComponents.RIBBON);
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider pRegistries) {
        ItemStack stack = ItemStack.EMPTY;
        ItemStack ribbon = ItemStack.EMPTY;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (canAttachRibbon(item)) {
                    if (!stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    stack = item;
                } else {
                    if (!(item.is(ModTags.Items.RIBBONS) && ribbon.isEmpty())) {
                        return ItemStack.EMPTY;
                    }
                    ribbon = item;
                }
            }
        }
        if (!stack.isEmpty() && !ribbon.isEmpty() && !stack.has(ModDataComponents.RIBBON)) {
            if (Block.byItem(ribbon.getItem()) instanceof RibbonBlock ribbonBlock) {
                DyeColor color = ribbonBlock.color();
                ItemStack result = stack.copy();
                result.set(ModDataComponents.RIBBON, color);
                return result;
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
        return ModRecipeSerializers.ATTACH_RIBBON.get();
    }
}
