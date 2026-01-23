package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import com.ChalkerCharles.morecolorful.common.item.musical.SheetMusicItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class SheetMusicCloningRecipe extends CustomRecipe {
    public SheetMusicCloningRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput pInput, Level pLevel) {
        int i = 0;
        ItemStack stack = ItemStack.EMPTY;
        for (int j = 0; j < pInput.size(); j++) {
            ItemStack stack1 = pInput.getItem(j);
            if (!stack1.isEmpty()) {
                if (stack1.is(ModItems.SHEET_MUSIC)) {
                    if (!stack.isEmpty()) {
                        return false;
                    }
                    stack = stack1;
                } else {
                    if (!stack1.is(ModItems.WRITABLE_SHEET_MUSIC)) {
                        return false;
                    }
                    i++;
                }
            }
        }
        return !stack.isEmpty() && i > 0;
    }

    @Override
    public ItemStack assemble(CraftingInput pInput, HolderLookup.Provider pRegistries) {
        int i = 0;
        ItemStack stack = ItemStack.EMPTY;
        for (int j = 0; j < pInput.size(); j++) {
            ItemStack stack1 = pInput.getItem(j);
            if (!stack1.isEmpty()) {
                if (stack1.is(ModItems.SHEET_MUSIC)) {
                    if (!stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    stack = stack1;
                } else {
                    if (!stack1.is(ModItems.WRITABLE_SHEET_MUSIC)) {
                        return ItemStack.EMPTY;
                    }

                    i++;
                }
            }
        }
        Melody melody = stack.get(ModDataComponents.MELODY);
        if (!stack.isEmpty() && i >= 1 && melody != null) {
            Melody melody1 = melody.tryCraftCopy();
            if (melody1 == null) {
                return ItemStack.EMPTY;
            } else {
                ItemStack stack1 = stack.copyWithCount(i);
                stack1.set(ModDataComponents.MELODY, melody1);
                return stack1;
            }
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput pInput) {
        NonNullList<ItemStack> list = NonNullList.withSize(pInput.size(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            ItemStack itemstack = pInput.getItem(i);
            if (itemstack.hasCraftingRemainingItem()) {
                list.set(i, itemstack.getCraftingRemainingItem());
            } else if (itemstack.getItem() instanceof SheetMusicItem) {
                list.set(i, itemstack.copyWithCount(1));
                break;
            }
        }
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 3 && pHeight >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.SHEET_MUSIC_CLONING.get();
    }
}
