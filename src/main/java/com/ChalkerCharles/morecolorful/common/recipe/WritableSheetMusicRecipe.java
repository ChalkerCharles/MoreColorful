package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

public class WritableSheetMusicRecipe extends ShapelessRecipe {
    private static final NonNullList<Ingredient> INGREDIENTS = NonNullList.create();

    public WritableSheetMusicRecipe(CraftingBookCategory category) {
        super("", category, ModItems.WRITABLE_SHEET_MUSIC.toStack(), INGREDIENTS);
    }

    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingInput pInput) {
        NonNullList<ItemStack> list = NonNullList.withSize(pInput.size(), ItemStack.EMPTY);
        for (int i = 0; i < list.size(); i++) {
            ItemStack item = pInput.getItem(i);
            if (item.hasCraftingRemainingItem()) {
                list.set(i, item.getCraftingRemainingItem());
            } else if (item.is(Items.NOTE_BLOCK)) {
                list.set(i, item.copyWithCount(1));
                break;
            }
        }
        return list;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth >= 2 && pHeight >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.WRITABLE_SHEET_MUSIC.get();
    }

    static {
        INGREDIENTS.add(Ingredient.of(Items.PAPER));
        INGREDIENTS.add(Ingredient.of(Items.FEATHER));
        INGREDIENTS.add(Ingredient.of(Items.INK_SAC));
        INGREDIENTS.add(Ingredient.of(Items.NOTE_BLOCK));
    }
}
