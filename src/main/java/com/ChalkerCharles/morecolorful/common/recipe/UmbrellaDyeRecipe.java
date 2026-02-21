package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.UmbrellaColor;
import com.ChalkerCharles.morecolorful.util.Colour;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class UmbrellaDyeRecipe extends CustomRecipe {
    public UmbrellaDyeRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level pLevel) {
        int w = input.width(), h = input.height();
        if (w > 3 || h > 3) return false;
        ItemStack stack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();
        int umbrellaIdx = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (item.is(ModItems.UMBRELLA)) {
                    if (!stack.isEmpty()) {
                        return false;
                    }
                    stack = item;
                    umbrellaIdx = i;
                } else {
                    if (!(item.getItem() instanceof DyeItem)) {
                        return false;
                    }
                    list.add(item);
                }
            }
        }
        int col = umbrellaIdx % w;
        int row = umbrellaIdx / w;
        return Maths.inCenter(col, row, w, h) && !stack.isEmpty() && !list.isEmpty();
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider pRegistries) {
        int w = input.width(), h = input.height();
        if (w > 3 || h > 3) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        List<DyeColor> list = Lists.newArrayList();
        int umbrellaIdx = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (item.is(ModItems.UMBRELLA)) {
                    if (!stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    stack = item;
                    umbrellaIdx = i;
                    list.add(null);
                } else {
                    if (!(item.getItem() instanceof DyeItem dye)) {
                        return ItemStack.EMPTY;
                    }
                    list.add(dye.getDyeColor());
                }
            } else {
                list.add(null);
            }
        }
        int col = umbrellaIdx % w;
        int row = umbrellaIdx / w;
        if (Maths.inCenter(col, row, w, h) && !stack.isEmpty() && hasDye(list)) {
            List<Colour> colors = stack.getOrDefault(ModDataComponents.UMBRELLA_COLOR, UmbrellaColor.DEFAULT).mutable();
            int sc = 1 - col;
            int sr = 1 - row;
            for (int i = 0; i < list.size(); i++) {
                DyeColor color = list.get(i);
                if (color != null) {
                    int idx = Maths.getRelativePosIn3By3Grid(i, w, sc, sr);
                    if (idx >= 4) idx--;
                    colors.set(idx, Colour.cast(color));
                }
            }
            ItemStack result = stack.copy();
            result.set(ModDataComponents.UMBRELLA_COLOR, new UmbrellaColor(colors));
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    private static boolean hasDye(List<DyeColor> list) {
        return list.stream().anyMatch(Objects::nonNull);
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2 && pWidth <= 3 && pHeight <= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.UMBRELLA_DYE.get();
    }
}
