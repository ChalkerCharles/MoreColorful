package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.KiteColor;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.google.common.collect.Lists;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Objects;

public class KiteDyeRecipe extends CustomRecipe {
    public KiteDyeRecipe(CraftingBookCategory pCategory) {
        super(pCategory);
    }

    @Override
    public boolean matches(CraftingInput input, Level pLevel) {
        int w = input.width(), h = input.height();
        if (w > 3 || h > 3) return false;
        ItemStack stack = ItemStack.EMPTY;
        List<ItemStack> list = Lists.newArrayList();
        int kiteIdx = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (item.is(ModItems.KITE)) {
                    if (!stack.isEmpty()) {
                        return false;
                    }
                    stack = item;
                    kiteIdx = i;
                } else {
                    if (!(item.getItem() instanceof DyeItem)) {
                        return false;
                    }
                    list.add(item);
                }
            }
        }
        int col = kiteIdx % w;
        int row = kiteIdx / w;
        return Maths.inCenter(col, row, w, h) && !stack.isEmpty() && !list.isEmpty() && list.size() < 6;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider pRegistries) {
        int w = input.width(), h = input.height();
        if (w > 3 || h > 3) return ItemStack.EMPTY;
        ItemStack stack = ItemStack.EMPTY;
        List<DyeColor> list = Lists.newArrayList();
        int kiteIdx = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack item = input.getItem(i);
            if (!item.isEmpty()) {
                if (item.is(ModItems.KITE)) {
                    if (!stack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }
                    stack = item;
                    kiteIdx = i;
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
        int col = kiteIdx % w;
        int row = kiteIdx / w;
        if (Maths.inCenter(col, row, w, h) && !stack.isEmpty() && hasDye(list)) {
            List<DyeColor> colors = stack.getOrDefault(ModDataComponents.KITE_COLOR, KiteColor.DEFAULT).mutable();
            int sc = 1 - col;
            int sr = 1 - row;
            for (int i = 0; i < list.size(); i++) {
                DyeColor color = list.get(i);
                if (color != null) {
                    int idx = Maths.getRelativePosIn3By3Grid(i, w, sc, sr);
                    if (idx != 8 && (idx & 1) == 0) {
                        return ItemStack.EMPTY;
                    }
                    colors.set(idx >> 1, color);
                }
            }
            ItemStack result = stack.copy();
            result.set(ModDataComponents.KITE_COLOR, new KiteColor(colors));
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    private static boolean hasDye(List<DyeColor> list) {
        int i = (int) list.stream().filter(Objects::nonNull).count();
        return i > 0 && i < 6;
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth * pHeight >= 2 && pWidth <= 3 && pHeight <= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.KITE_DYE.get();
    }
}
