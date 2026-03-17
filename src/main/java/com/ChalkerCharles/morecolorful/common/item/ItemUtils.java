package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.util.Maths;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;

import java.util.*;
import java.util.stream.Stream;

public final class ItemUtils extends net.minecraft.world.item.ItemUtils {
    public static final ItemLike[] COLORED_BUNDLES = new ItemLike[] {
            ModItems.WHITE_BUNDLE,
            ModItems.LIGHT_GRAY_BUNDLE,
            ModItems.GRAY_BUNDLE,
            ModItems.BLACK_BUNDLE,
            ModItems.BROWN_BUNDLE,
            ModItems.RED_BUNDLE,
            ModItems.ORANGE_BUNDLE,
            ModItems.YELLOW_BUNDLE,
            ModItems.LIME_BUNDLE,
            ModItems.GREEN_BUNDLE,
            ModItems.CYAN_BUNDLE,
            ModItems.LIGHT_BLUE_BUNDLE,
            ModItems.BLUE_BUNDLE,
            ModItems.PURPLE_BUNDLE,
            ModItems.MAGENTA_BUNDLE,
            ModItems.PINK_BUNDLE
    };
    public static final ItemLike[] ALL_BUNDLES = Maths.concatArray(ItemLike[]::new, Items.BUNDLE, COLORED_BUNDLES);

    public static Stream<ItemStack> dyeingIngredients(ItemLike[] items, ItemLike except) {
        return Arrays.stream(items).filter(i -> i != except).map(ItemStack::new);
    }

    public static ItemLike bundleByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> ModItems.WHITE_BUNDLE;
            case ORANGE -> ModItems.ORANGE_BUNDLE;
            case MAGENTA -> ModItems.MAGENTA_BUNDLE;
            case LIGHT_BLUE -> ModItems.LIGHT_BLUE_BUNDLE;
            case YELLOW -> ModItems.YELLOW_BUNDLE;
            case LIME -> ModItems.LIME_BUNDLE;
            case PINK -> ModItems.PINK_BUNDLE;
            case GRAY -> ModItems.GRAY_BUNDLE;
            case LIGHT_GRAY -> ModItems.LIGHT_GRAY_BUNDLE;
            case CYAN -> ModItems.CYAN_BUNDLE;
            case PURPLE -> ModItems.PURPLE_BUNDLE;
            case BLUE -> ModItems.BLUE_BUNDLE;
            case BROWN -> ModItems.BROWN_BUNDLE;
            case GREEN -> ModItems.GREEN_BUNDLE;
            case RED -> ModItems.RED_BUNDLE;
            case BLACK -> ModItems.BLACK_BUNDLE;
        };
    }

    public static Item[] itemArray(ItemLike[] array) {
        return Arrays.stream(array).map(ItemLike::asItem).toArray(Item[]::new);
    }
    
    public static boolean isShears(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.is(Tags.Items.TOOLS_SHEAR);
    }

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

    public static InteractionHand getLeftHand(LivingEntity entity) {
        return entity.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
    }

    public static InteractionHand getRightHand(LivingEntity entity) {
        return entity.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    public static ItemStack getLeftHandItem(LivingEntity entity) {
        return entity.getItemInHand(getLeftHand(entity));
    }

    public static ItemStack getRightHandItem(LivingEntity entity) {
        return entity.getItemInHand(getRightHand(entity));
    }

    public static void addAttributeModifiers(ItemStack stack, ItemAttributeModifiers.Entry... toAdd) {
        ItemAttributeModifiers oldModifiers = stack.getAttributeModifiers();
        List<ItemAttributeModifiers.Entry> modifiers = oldModifiers.modifiers();
        ImmutableList.Builder<ItemAttributeModifiers.Entry> builder = ImmutableList.builderWithExpectedSize(modifiers.size() + toAdd.length);
        modifiers.stream()
                .filter(entry -> containsNoAttributeModifiersFrom(entry, toAdd))
                .forEach(builder::add);
        builder.add(toAdd);
        ItemAttributeModifiers newModifiers = new ItemAttributeModifiers(builder.build(), oldModifiers.showInTooltip());
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newModifiers);
    }

    public static void removeAttributeModifiers(ItemStack stack, ItemAttributeModifiers.Entry... toRemove) {
        ItemAttributeModifiers oldModifiers = stack.getAttributeModifiers();
        List<ItemAttributeModifiers.Entry> modifiers = oldModifiers.modifiers();
        ImmutableList.Builder<ItemAttributeModifiers.Entry> builder = ImmutableList.builderWithExpectedSize(modifiers.size());
        modifiers.stream()
                .filter(entry -> containsNoAttributeModifiersFrom(entry, toRemove))
                .forEach(builder::add);
        ItemAttributeModifiers newModifiers = new ItemAttributeModifiers(builder.build(), oldModifiers.showInTooltip());
        stack.set(DataComponents.ATTRIBUTE_MODIFIERS, newModifiers);
    }

    private static boolean containsNoAttributeModifiersFrom(ItemAttributeModifiers.Entry entry, ItemAttributeModifiers.Entry... entries) {
        return Arrays.stream(entries).noneMatch(e -> entry.matches(e.attribute(), e.modifier().id()));
    }
}
