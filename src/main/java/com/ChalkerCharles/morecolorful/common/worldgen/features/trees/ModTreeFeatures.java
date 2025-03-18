package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;
import static net.minecraft.data.worldgen.features.FeatureUtils.register;

public class ModTreeFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRABAPPLE_005 = registerKey("crabapple_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHERRY = registerKey("white_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_CHERRY_005 = registerKey("white_cherry_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH = registerKey("orange_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH_0002 = registerKey("orange_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_BIRCH_005 = registerKey("orange_birch_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_ORANGE_BIRCH_0002 = registerKey("tall_orange_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH = registerKey("yellow_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_0002 = registerKey("yellow_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_YELLOW_BIRCH_0002 = registerKey("tall_yellow_birch_0002");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_BIRCH_005 = registerKey("yellow_birch_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO = registerKey("ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GINKGO = registerKey("fancy_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE = registerKey("maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_MAPLE = registerKey("fancy_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROST = registerKey("frost");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD = registerKey("dawn_redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JACARANDA = registerKey("jacaranda");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JACARANDA_005 = registerKey("jacaranda_005");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JACARANDA_BEES = registerKey("jacaranda_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BEES = registerKey("oak_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_BEES = registerKey("birch_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHERRY_BEES = registerKey("cherry_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW = registerKey("willow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_WILLOW = registerKey("fancy_willow");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, CRABAPPLE, Feature.TREE, ModTreeConfigurations.CRABAPPLE);
        register(context, CRABAPPLE_005, Feature.TREE, ModTreeConfigurations.CRABAPPLE_005);
        register(context, WHITE_CHERRY, Feature.TREE, ModTreeConfigurations.WHITE_CHERRY);
        register(context, WHITE_CHERRY_005, Feature.TREE, ModTreeConfigurations.WHITE_CHERRY_005);
        register(context, ORANGE_BIRCH, Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH);
        register(context, ORANGE_BIRCH_0002, Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH_0002);
        register(context, ORANGE_BIRCH_005, Feature.TREE, ModTreeConfigurations.ORANGE_BIRCH_005);
        register(context, TALL_ORANGE_BIRCH_0002, Feature.TREE, ModTreeConfigurations.TALL_ORANGE_BIRCH_0002);
        register(context, YELLOW_BIRCH, Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH);
        register(context, YELLOW_BIRCH_0002, Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH_0002);
        register(context, YELLOW_BIRCH_005, Feature.TREE, ModTreeConfigurations.YELLOW_BIRCH_005);
        register(context, TALL_YELLOW_BIRCH_0002, Feature.TREE, ModTreeConfigurations.TALL_YELLOW_BIRCH_0002);
        register(context, GINKGO, Feature.TREE, ModTreeConfigurations.GINKGO);
        register(context, FANCY_GINKGO, Feature.TREE, ModTreeConfigurations.FANCY_GINKGO);
        register(context, MAPLE, Feature.TREE, ModTreeConfigurations.MAPLE);
        register(context, FANCY_MAPLE, Feature.TREE, ModTreeConfigurations.FANCY_MAPLE);
        register(context, FROST, Feature.TREE, ModTreeConfigurations.FROST);
        register(context, DAWN_REDWOOD, Feature.TREE, ModTreeConfigurations.DAWN_REDWOOD);
        register(context, JACARANDA, Feature.TREE, ModTreeConfigurations.JACARANDA);
        register(context, JACARANDA_005, Feature.TREE, ModTreeConfigurations.JACARANDA_005);
        register(context, JACARANDA_BEES, Feature.TREE, ModTreeConfigurations.JACARANDA_BEES);
        register(context, OAK_BEES, Feature.TREE, ModTreeConfigurations.OAK_BEES);
        register(context, BIRCH_BEES, Feature.TREE, ModTreeConfigurations.BIRCH_BEES);
        register(context, CHERRY_BEES, Feature.TREE, ModTreeConfigurations.CHERRY_BEES);
        register(context, WILLOW, Feature.TREE, ModTreeConfigurations.WILLOW);
        register(context, FANCY_WILLOW, Feature.TREE, ModTreeConfigurations.FANCY_WILLOW);
    }
}
