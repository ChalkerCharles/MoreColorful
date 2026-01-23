package com.ChalkerCharles.morecolorful.common.item;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class ItemUtils {
    public static boolean isSameItemSameComponentsExcept(ItemStack stack, ItemStack other, DataComponentType<?> except) {
        if (!ItemStack.isSameItem(stack, other)) return false;
        DataComponentMap components = stack.getComponents();
        DataComponentMap components1 = other.getComponents();
        if (components.isEmpty() || components1.isEmpty())
            return components.isEmpty() && components1.isEmpty();
        Set<DataComponentType<?>> keys = new HashSet<>(components.keySet());
        Set<DataComponentType<?>> keys1 = new HashSet<>(components1.keySet());
        keys.remove(except);
        keys1.remove(except);
        if (!keys.equals(keys1)) return false;
        return keys.stream().allMatch(key -> Objects.equals(components.get(key), components1.get(key)));
    }

    public static InteractionHand otherHand(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }
}
