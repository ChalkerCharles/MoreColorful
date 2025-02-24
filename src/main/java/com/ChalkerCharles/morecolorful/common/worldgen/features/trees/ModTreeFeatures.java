package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;

public class ModTreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE_005 = registerKey("crabapple_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHERRY = registerKey("white_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHERRY_005 = registerKey("white_cherry_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH = registerKey("orange_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH_0002 = registerKey("orange_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH_005 = registerKey("orange_birch_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH = registerKey("yellow_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_0002 = registerKey("yellow_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_005 = registerKey("yellow_birch_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO = registerKey("ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GINKGO = registerKey("fancy_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE = registerKey("maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE = registerKey("fancy_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROST = registerKey("frost");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD = registerKey("dawn_redwood");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(CRABAPPLE, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.CRABAPPLE));
        context.register(CRABAPPLE_005, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.CRABAPPLE_005));
        context.register(WHITE_CHERRY, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.WHITE_CHERRY));
        context.register(WHITE_CHERRY_005, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.WHITE_CHERRY_005));
        context.register(ORANGE_BIRCH, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH));
        context.register(ORANGE_BIRCH_0002, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH_0002));
        context.register(ORANGE_BIRCH_005, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH_005));
        context.register(YELLOW_BIRCH, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH));
        context.register(YELLOW_BIRCH_0002, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH_0002));
        context.register(YELLOW_BIRCH_005, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH_005));
        context.register(GINKGO, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.GINKGO));
        context.register(FANCY_GINKGO, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.FANCY_GINKGO));
        context.register(MAPLE, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.MAPLE));
        context.register(FANCY_MAPLE, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.FANCY_MAPLE));
        context.register(FROST, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.FROST));
        context.register(DAWN_REDWOOD, new ConfiguredFeature<>(Feature.TREE, ModTreeConfigurations.DAWN_REDWOOD));
    }
}
