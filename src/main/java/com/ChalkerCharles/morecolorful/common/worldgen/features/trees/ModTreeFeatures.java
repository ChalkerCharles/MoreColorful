package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;

public class ModTreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE_BEE = registerKey("crabapple_bee");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CRABAPPLE, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.CRABAPPLE));
        context.register(CRABAPPLE_BEE, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.CRABAPPLE_BEE));
    }
}
