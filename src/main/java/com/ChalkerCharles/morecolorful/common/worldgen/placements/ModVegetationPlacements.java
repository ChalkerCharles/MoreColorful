package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModVegetationFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;
import static net.minecraft.data.worldgen.placement.PlacementUtils.register;
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
    public static final ResourceKey<PlacedFeature> PATCH_CROCUS = registerKey("patch_crocus");
    public static final ResourceKey<PlacedFeature> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<PlacedFeature> PATCH_BLUEBERRY_BUSH = registerKey("patch_blueberry_bush");
    public static final ResourceKey<PlacedFeature> TREES_DAWN_REDWOOD = registerKey("trees_dawn_redwood");
    public static final ResourceKey<PlacedFeature> FLOWER_DAWN_REDWOOD = registerKey("flower_dawn_redwood");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD_LEAF_LITTER = registerKey("dawn_redwood_leaf_litter");
    public static final ResourceKey<PlacedFeature> TREES_LAVENDER = registerKey("trees_lavender");
    public static final ResourceKey<PlacedFeature> FLOWER_LAVENDER = registerKey("flower_lavender");
    public static final ResourceKey<PlacedFeature> TREES_JACARANDA = registerKey("trees_jacaranda");
    public static final ResourceKey<PlacedFeature> FLOWER_JACARANDA = registerKey("flower_jacaranda");
    public static final ResourceKey<PlacedFeature> FLOWER_MARSH = registerKey("flower_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_GRASS_MARSH = registerKey("patch_tall_grass_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_WATER_GRASS_MARSH = registerKey("patch_water_grass_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_WATER_GRASS = registerKey("patch_water_grass");
    public static final ResourceKey<PlacedFeature> PATCH_GERBERA_DAISY = registerKey("patch_gerbera_daisy");
    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL = registerKey("patch_cattail");
    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL_MARSH = registerKey("patch_cattail_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_REED = registerKey("patch_reed");
    public static final ResourceKey<PlacedFeature> PATCH_REED_MARSH = registerKey("patch_reed_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_WATER_LILY = registerKey("patch_water_lily");
    public static final ResourceKey<PlacedFeature> PATCH_WATER_LILY_MARSH = registerKey("patch_water_lily_marsh");
    public static final ResourceKey<PlacedFeature> PATCH_DUCKWEEDS = registerKey("patch_duckweeds");
    public static final ResourceKey<PlacedFeature> PATCH_BUTTERCUPS = registerKey("patch_buttercups");
    public static final ResourceKey<PlacedFeature> PATCH_FORGET_ME_NOTS = registerKey("patch_forget-me-nots");
    public static final ResourceKey<PlacedFeature> PATCH_SPEEDWELLS = registerKey("patch_speedwells");
    public static final ResourceKey<PlacedFeature> TREES_AZURE = registerKey("trees_azure");
    public static final ResourceKey<PlacedFeature> FLOWER_AZURE = registerKey("flower_azure");
    public static final ResourceKey<PlacedFeature> TREES_WILLOW = registerKey("trees_willow");
    public static final ResourceKey<PlacedFeature> TREES_WILLOW_BAYOU = registerKey("trees_willow_bayou");
    public static final ResourceKey<PlacedFeature> PATCH_WOOD_SORRELS = registerKey("patch_wood_sorrels");
    public static final ResourceKey<PlacedFeature> TREES_RAPESEED = registerKey("trees_rapeseed");
    public static final ResourceKey<PlacedFeature> FLOWER_RAPESEED = registerKey("flower_rapeseed");
    public static final ResourceKey<PlacedFeature> PATCH_WINDFLOWER = registerKey("patch_windflower");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, TREES_CRABAPPLE,
                features.getOrThrow(ModTreeFeatures.CRABAPPLE_005),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.CRABAPPLE_SAPLING.get()
                )
        );
        register(context, FLOWER_CRABAPPLE,
                features.getOrThrow(ModVegetationFeatures.FLOWER_CRABAPPLE),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_WHITE_CHERRY,
                features.getOrThrow(ModTreeFeatures.WHITE_CHERRY_005),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.WHITE_CHERRY_SAPLING.get()
                )
        );
        register(context, FLOWER_WHITE_CHERRY,
                features.getOrThrow(ModVegetationFeatures.FLOWER_WHITE_CHERRY),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, FLOWER_CHERRY,
                features.getOrThrow(ModVegetationFeatures.FLOWER_CHERRY),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_AUTUMN_BIRCH,
                features.getOrThrow(ModVegetationFeatures.TREES_AUTUMN_BIRCH),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.ORANGE_BIRCH_SAPLING.get()
                )
        );
        register(context, FLOWER_AUTUMN_BIRCH,
                features.getOrThrow(ModVegetationFeatures.FLOWER_AUTUMN_BIRCH),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );
        register(context, AUTUMN_BIRCH_LEAF_LITTER,
                features.getOrThrow(ModVegetationFeatures.AUTUMN_BIRCH_LEAF_LITTER),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_GINKGO,
                features.getOrThrow(ModVegetationFeatures.TREES_GINKGO),
                treePlacement(PlacementUtils.countExtra(6, 0.1F, 1),
                        ModBlocks.GINKGO_SAPLING.get()
                )
        );
        register(context, FLOWER_GINKGO,
                features.getOrThrow(ModVegetationFeatures.FLOWER_GINKGO),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );
        register(context, GINKGO_LEAF_LITTER,
                features.getOrThrow(ModVegetationFeatures.GINKGO_LEAF_LITTER),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_MAPLE,
                features.getOrThrow(ModVegetationFeatures.TREES_MAPLE),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.MAPLE_SAPLING.get()
                )
        );
        register(context, FLOWER_MAPLE,
                features.getOrThrow(ModVegetationFeatures.FLOWER_MAPLE),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );
        register(context, MAPLE_LEAF_LITTER,
                features.getOrThrow(ModVegetationFeatures.MAPLE_LEAF_LITTER),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_SUNSET_VALLEY,
                features.getOrThrow(ModVegetationFeatures.TREES_SUNSET_VALLEY),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
        );
        register(context, FLOWER_SUNSET_VALLEY,
                features.getOrThrow(ModVegetationFeatures.FLOWER_SUNSET_VALLEY),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );
        register(context, TREES_FROST,
                features.getOrThrow(ModTreeFeatures.FROST),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.FROST_SAPLING.get()
                )
        );
        register(context, FLOWER_FROST,
                features.getOrThrow(ModVegetationFeatures.FLOWER_FROST),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, PATCH_CROCUS,
                features.getOrThrow(ModVegetationFeatures.PATCH_CROCUS),
                RarityFilter.onAverageOnceEvery(28),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, PATCH_STRAWBERRY_BUSH,
                features.getOrThrow(ModVegetationFeatures.PATCH_STRAWBERRY_BUSH),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        register(context, PATCH_BLUEBERRY_BUSH,
                features.getOrThrow(ModVegetationFeatures.PATCH_BLUEBERRY_BUSH),
                RarityFilter.onAverageOnceEvery(64),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        register(context, TREES_DAWN_REDWOOD,
                features.getOrThrow(ModTreeFeatures.DAWN_REDWOOD),
                PlacementUtils.countExtra(3, 0.1F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.DAWN_REDWOOD_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
        );
        register(context, FLOWER_DAWN_REDWOOD,
                features.getOrThrow(ModVegetationFeatures.FLOWER_DAWN_REDWOOD),
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, DAWN_REDWOOD_LEAF_LITTER,
                features.getOrThrow(ModVegetationFeatures.DAWN_REDWOOD_LEAF_LITTER),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_LAVENDER,
                features.getOrThrow(ModVegetationFeatures.TREES_LAVENDER),
                PlacementUtils.countExtra(0, 0.05F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome()
        );
        register(context, FLOWER_LAVENDER,
                features.getOrThrow(ModVegetationFeatures.FLOWER_LAVENDER),
                VegetationPlacements.worldSurfaceSquaredWithCount(12)
        );
        register(context, TREES_JACARANDA,
                features.getOrThrow(ModTreeFeatures.JACARANDA_005),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.JACARANDA_SAPLING.get()
                )
        );
        register(context, FLOWER_JACARANDA,
                features.getOrThrow(ModVegetationFeatures.FLOWER_JACARANDA),
                NoiseThresholdCountPlacement.of(-0.8, 5, 10),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, FLOWER_MARSH,
                features.getOrThrow(ModVegetationFeatures.FLOWER_MARSH),
                NoiseThresholdCountPlacement.of(-0.8, 15, 4),
                RarityFilter.onAverageOnceEvery(28),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, PATCH_TALL_GRASS_MARSH,
                features.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
                RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        Holder<ConfiguredFeature<?, ?>> waterGrass = features.getOrThrow(ModVegetationFeatures.PATCH_WATER_GRASS);
        register(context, PATCH_WATER_GRASS_MARSH,
                waterGrass,
                CountPlacement.of(100),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        register(context, PATCH_WATER_GRASS,
                waterGrass,
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        register(context, PATCH_GERBERA_DAISY,
                features.getOrThrow(ModVegetationFeatures.PATCH_GERBERA_DAISY),
                RarityFilter.onAverageOnceEvery(30),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        Holder<ConfiguredFeature<?, ?>> cattail = features.getOrThrow(ModVegetationFeatures.PATCH_CATTAIL);
        register(context, PATCH_CATTAIL,
                cattail,
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        register(context, PATCH_CATTAIL_MARSH,
                cattail,
                RarityFilter.onAverageOnceEvery(2),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        Holder<ConfiguredFeature<?, ?>> reed = features.getOrThrow(ModVegetationFeatures.PATCH_REED);
        register(context, PATCH_REED,
                reed,
                RarityFilter.onAverageOnceEvery(5),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        register(context, PATCH_REED_MARSH,
                reed,
                RarityFilter.onAverageOnceEvery(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_TOP_SOLID,
                BiomeFilter.biome()
        );
        register(context, PATCH_WATER_LILY,
                features.getOrThrow(ModVegetationFeatures.PATCH_WATER_LILY),
                RarityFilter.onAverageOnceEvery(4),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        register(context, PATCH_WATER_LILY_MARSH,
                features.getOrThrow(ModVegetationFeatures.PATCH_WATER_LILY),
                CountPlacement.of(1),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
        register(context, PATCH_DUCKWEEDS,
                features.getOrThrow(ModVegetationFeatures.PATCH_DUCKWEEDS),
                VegetationPlacements.worldSurfaceSquaredWithCount(8)
        );
        register(context, PATCH_BUTTERCUPS,
                features.getOrThrow(ModVegetationFeatures.PATCH_BUTTERCUPS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, PATCH_FORGET_ME_NOTS,
                features.getOrThrow(ModVegetationFeatures.PATCH_FORGET_ME_NOTS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, PATCH_SPEEDWELLS,
                features.getOrThrow(ModVegetationFeatures.PATCH_SPEEDWELLS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_AZURE,
                features.getOrThrow(ModVegetationFeatures.TREES_AZURE),
                PlacementUtils.countExtra(0, 0.05F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome()
        );
        register(context, FLOWER_AZURE,
                features.getOrThrow(ModVegetationFeatures.FLOWER_AZURE),
                VegetationPlacements.worldSurfaceSquaredWithCount(16)
        );
        register(context, TREES_WILLOW,
                features.getOrThrow(ModVegetationFeatures.TREES_WILLOW),
                RarityFilter.onAverageOnceEvery(16),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.WILLOW_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
        );
        register(context, TREES_WILLOW_BAYOU,
                features.getOrThrow(ModVegetationFeatures.TREES_WILLOW_BAYOU),
                PlacementUtils.countExtra(4, 0.1F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(2),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.WILLOW_SAPLING.get().defaultBlockState(), BlockPos.ZERO))
        );
        register(context, PATCH_WOOD_SORRELS,
                features.getOrThrow(ModVegetationFeatures.PATCH_WOOD_SORRELS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
        register(context, TREES_RAPESEED,
                features.getOrThrow(ModVegetationFeatures.TREES_RAPESEED),
                PlacementUtils.countExtra(0, 0.05F, 1),
                InSquarePlacement.spread(),
                SurfaceWaterDepthFilter.forMaxDepth(0),
                PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
                BiomeFilter.biome()
        );
        register(context, FLOWER_RAPESEED,
                features.getOrThrow(ModVegetationFeatures.FLOWER_RAPESEED),
                VegetationPlacements.worldSurfaceSquaredWithCount(12)
        );
        register(context, PATCH_WINDFLOWER,
                features.getOrThrow(ModVegetationFeatures.PATCH_WINDFLOWER),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );
    }
}
