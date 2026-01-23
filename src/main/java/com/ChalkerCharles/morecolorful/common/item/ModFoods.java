package com.ChalkerCharles.morecolorful.common.item;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoods {
    public static final FoodProperties STRAWBERRY = food().nutrition(2).saturationModifier(0.1F).build();
    public static final FoodProperties BLUEBERRY = food().nutrition(2).saturationModifier(0.1F).build();
    public static final FoodProperties TANGHULU = skewer().nutrition(5).saturationModifier(0.3F).build();

    private static FoodProperties.Builder food() {
        return new FoodProperties.Builder();
    }

    private static FoodProperties.Builder skewer() {
        return food().usingConvertsTo(Items.STICK);
    }
}
