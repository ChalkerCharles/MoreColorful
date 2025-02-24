package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModVegetationFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;

public class ModVegetationPlacements {
    public static final ResourceKey<PlacedFeature> TREES_CRABAPPLE = registerKey("trees_crabapple");
    public static final ResourceKey<PlacedFeature> FLOWER_CRABAPPLE = registerKey("flower_crabapple");
    public static final ResourceKey<PlacedFeature> TREES_WHITE_CHERRY = registerKey("trees_white_cherry");
    public static final ResourceKey<PlacedFeature> FLOWER_WHITE_CHERRY = registerKey("flower_white_cherry");
    public static final ResourceKey<PlacedFeature> FLOWER_CHERRY = registerKey("flower_cherry");
    public static final ResourceKey<PlacedFeature> TREES_AUTUMN_BIRCH = registerKey("trees_autumn_birch");
    public static final ResourceKey<PlacedFeature> FLOWER_AUTUMN_BIRCH = registerKey("flower_autumn_birch");
    public static final ResourceKey<PlacedFeature> AUTUMN_BIRCH_LEAF_LITTER = registerKey("autumn_birch_leaf_litter");
    public static final ResourceKey<PlacedFeature> TREES_GINKGO = registerKey("trees_ginkgo");
    public static final ResourceKey<PlacedFeature> FLOWER_GINKGO = registerKey("flower_ginkgo");
    public static final ResourceKey<PlacedFeature> GINKGO_LEAF_LITTER = registerKey("ginkgo_leaf_litter");
    public static final ResourceKey<PlacedFeature> TREES_MAPLE = registerKey("trees_maple");
    public static final ResourceKey<PlacedFeature> FLOWER_MAPLE = registerKey("flower_maple");
    public static final ResourceKey<PlacedFeature> MAPLE_LEAF_LITTER = registerKey("maple_leaf_litter");
    public static final ResourceKey<PlacedFeature> TREES_SUNSET_VALLEY = registerKey("trees_sunset_valley");
    public static final ResourceKey<PlacedFeature> FLOWER_SUNSET_VALLEY = registerKey("flower_sunset_valley");
    public static final ResourceKey<PlacedFeature> TREES_FROST = registerKey("trees_frost");
    public static final ResourceKey<PlacedFeature> FLOWER_FROST = registerKey("flower_frost");
    public static final ResourceKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_BLUEBERRY_BUSH = registerKey("patch_blueberry_bush");
    public static final ResourceKey<PlacedFeature> TREES_DAWN_REDWOOD = registerKey("trees_dawn_redwood");
    public static final ResourceKey<PlacedFeature> FLOWER_DAWN_REDWOOD = registerKey("flower_dawn_redwood");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_LEAF_LITTER = registerKey("dawn_redwood_leaf_litter");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(TREES_CRABAPPLE, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.CRABAPPLE_005),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.CRABAPPLE_SAPLING.get()
                )
        ));
        context.register(FLOWER_CRABAPPLE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_CRABAPPLE),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_WHITE_CHERRY, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.WHITE_CHERRY_005),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.WHITE_CHERRY_SAPLING.get()
                )
        ));
        context.register(FLOWER_WHITE_CHERRY, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_WHITE_CHERRY),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(FLOWER_CHERRY, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_CHERRY),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_AUTUMN_BIRCH, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.TREES_AUTUMN_BIRCH),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.ORANGE_BIRCH_SAPLING.get()
                )
        ));
        context.register(FLOWER_AUTUMN_BIRCH, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_AUTUMN_BIRCH),
                List.of(
                        RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                        BiomeFilter.biome()
                )
        ));
        context.register(AUTUMN_BIRCH_LEAF_LITTER, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.AUTUMN_BIRCH_LEAF_LITTER),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_GINKGO, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.TREES_GINKGO),
                treePlacement(PlacementUtils.countExtra(6, 0.1F, 1),
                        ModBlocks.GINKGO_SAPLING.get()
                )
        ));
        context.register(FLOWER_GINKGO, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_GINKGO),
                List.of(
                        RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                        BiomeFilter.biome()
                )
        ));
        context.register(GINKGO_LEAF_LITTER, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.GINKGO_LEAF_LITTER),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_MAPLE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.TREES_MAPLE),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.MAPLE_SAPLING.get()
                )
        ));
        context.register(FLOWER_MAPLE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_MAPLE),
                List.of(
                        RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                        BiomeFilter.biome()
                )
        ));
        context.register(MAPLE_LEAF_LITTER, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.MAPLE_LEAF_LITTER),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_SUNSET_VALLEY, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.TREES_SUNSET_VALLEY),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
        ));
        context.register(FLOWER_SUNSET_VALLEY, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_SUNSET_VALLEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(5),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_FROST, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.FROST),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.FROST_SAPLING.get()
                )
        ));
        context.register(FLOWER_FROST, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_FROST),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(PATCH_STRAWBERRY_BUSH, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.PATCH_STRAWBERRY_BUSH),
                List.of(
                        RarityFilter.onAverageOnceEvery(32),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));
        context.register(PATCH_BLUEBERRY_BUSH, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.PATCH_BLUEBERRY_BUSH),
                List.of(
                        RarityFilter.onAverageOnceEvery(64),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        ));
        context.register(TREES_DAWN_REDWOOD, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.DAWN_REDWOOD),
                List.of(PlacementUtils.countExtra(3, 0.1F, 1),
                        InSquarePlacement.spread(),
                        SurfaceWaterDepthFilter.forMaxDepth(2),
                        PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                        BiomeFilter.biome(),
                        BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.DAWN_REDWOOD_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
                )
        ));
        context.register(FLOWER_DAWN_REDWOOD, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.FLOWER_DAWN_REDWOOD),
                List.of(
                        RarityFilter.onAverageOnceEvery(64),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
        context.register(DAWN_REDWOOD_LEAF_LITTER, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.DAWN_REDWOOD_LEAF_LITTER),
                List.of(
                        NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome()
                )
        ));
    }
}
