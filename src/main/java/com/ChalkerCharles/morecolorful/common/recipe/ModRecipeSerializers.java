package com.ChalkerCharles.morecolorful.common.recipe;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MoreColorful.MODID);

    public static final Supplier<RecipeSerializer<WritableSheetMusicRecipe>> WRITABLE_SHEET_MUSIC = register("crafting_special_writable_sheet_music", WritableSheetMusicRecipe::new);
    public static final Supplier<RecipeSerializer<SheetMusicCloningRecipe>> SHEET_MUSIC_CLONING = register("crafting_special_sheet_music_cloning", SheetMusicCloningRecipe::new);
    public static final Supplier<RecipeSerializer<UmbrellaDyeRecipe>> UMBRELLA_DYE = register("crafting_special_umbrella_dye", UmbrellaDyeRecipe::new);
    public static final Supplier<RecipeSerializer<PinwheelDyeRecipe>> PINWHEEL_DYE = register("crafting_special_pinwheel_dye", PinwheelDyeRecipe::new);

    private static <T extends CraftingRecipe> Supplier<RecipeSerializer<T>> register(String name, SimpleCraftingRecipeSerializer.Factory<T> factory) {
        return RECIPE_SERIALIZERS.register(name, () -> new SimpleCraftingRecipeSerializer<>(factory));
    }

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
    }
}
