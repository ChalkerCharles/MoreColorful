package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.function.Supplier;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<PlacedFeature> CRABAPPLE_005 = registerKey("crabapple_005");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY = registerKey("white_cherry");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY_005 = registerKey("white_cherry_005");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH = registerKey("orange_birch");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH_0002 = registerKey("orange_birch_0002");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH_005 = registerKey("orange_birch_005");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH = registerKey("yellow_birch");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH_0002 = registerKey("yellow_birch_0002");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH_005 = registerKey("yellow_birch_005");
    public static final ResourceKey<PlacedFeature> GINKGO = registerKey("ginkgo");
    public static final ResourceKey<PlacedFeature> FANCY_GINKGO = registerKey("fancy_ginkgo");
    public static final ResourceKey<PlacedFeature> MAPLE = registerKey("maple");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE = registerKey("fancy_maple");
    public static final ResourceKey<PlacedFeature> FROST = registerKey("frost");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD = registerKey("dawn_redwood");

    private static List<PlacementModifier> getSapling(Supplier<SaplingBlock> block) {
        return List.of(PlacementUtils.filteredByBlockSurvival(block.get()));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(CRABAPPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
        context.register(CRABAPPLE_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE_005), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
        context.register(WHITE_CHERRY, new PlacedFeature(features.getOrThrow(ModTreeFeatures.WHITE_CHERRY), getSapling(ModBlocks.WHITE_CHERRY_SAPLING)));
        context.register(WHITE_CHERRY_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.WHITE_CHERRY_005), getSapling(ModBlocks.WHITE_CHERRY_SAPLING)));
        context.register(ORANGE_BIRCH, new PlacedFeature(features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH), getSapling(ModBlocks.ORANGE_BIRCH_SAPLING)));
        context.register(ORANGE_BIRCH_0002, new PlacedFeature(features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH_0002), getSapling(ModBlocks.ORANGE_BIRCH_SAPLING)));
        context.register(ORANGE_BIRCH_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH_005), getSapling(ModBlocks.ORANGE_BIRCH_SAPLING)));
        context.register(YELLOW_BIRCH, new PlacedFeature(features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH), getSapling(ModBlocks.YELLOW_BIRCH_SAPLING)));
        context.register(YELLOW_BIRCH_0002, new PlacedFeature(features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH_0002), getSapling(ModBlocks.YELLOW_BIRCH_SAPLING)));
        context.register(YELLOW_BIRCH_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH_005), getSapling(ModBlocks.YELLOW_BIRCH_SAPLING)));
        context.register(GINKGO, new PlacedFeature(features.getOrThrow(ModTreeFeatures.GINKGO), getSapling(ModBlocks.GINKGO_SAPLING)));
        context.register(FANCY_GINKGO, new PlacedFeature(features.getOrThrow(ModTreeFeatures.FANCY_GINKGO), getSapling(ModBlocks.GINKGO_SAPLING)));
        context.register(MAPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.MAPLE), getSapling(ModBlocks.MAPLE_SAPLING)));
        context.register(FANCY_MAPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.FANCY_MAPLE), getSapling(ModBlocks.MAPLE_SAPLING)));
        context.register(FROST, new PlacedFeature(features.getOrThrow(ModTreeFeatures.FROST), getSapling(ModBlocks.FROST_SAPLING)));
        context.register(DAWN_REDWOOD, new PlacedFeature(features.getOrThrow(ModTreeFeatures.DAWN_REDWOOD), getSapling(ModBlocks.DAWN_REDWOOD_SAPLING)));
    }
}
