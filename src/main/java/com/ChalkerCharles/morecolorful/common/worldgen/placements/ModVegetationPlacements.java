package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModVegetationFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
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
    public static final ResourceKey<PlacedFeature> TREES_AUTUMN_BIRCH = registerKey("trees_autumn_birch");
    public static final ResourceKey<PlacedFeature> FLOWER_AUTUMN_BIRCH = registerKey("flower_autumn_birch");
    public static final ResourceKey<PlacedFeature> AUTUMN_BIRCH_LEAF_PILE = registerKey("autumn_birch_leaf_pile");
    public static final ResourceKey<PlacedFeature> TREES_GINKGO = registerKey("trees_ginkgo");
    public static final ResourceKey<PlacedFeature> FLOWER_GINKGO = registerKey("flower_ginkgo");
    public static final ResourceKey<PlacedFeature> GINKGO_LEAF_PILE = registerKey("ginkgo_leaf_pile");
    public static final ResourceKey<PlacedFeature> TREES_MAPLE = registerKey("trees_maple");
    public static final ResourceKey<PlacedFeature> FLOWER_MAPLE = registerKey("flower_maple");
    public static final ResourceKey<PlacedFeature> MAPLE_LEAF_PILE = registerKey("maple_leaf_pile");
    public static final ResourceKey<PlacedFeature> TREES_SUNSET_VALLEY = registerKey("trees_sunset_valley");
    public static final ResourceKey<PlacedFeature> FLOWER_SUNSET_VALLEY = registerKey("flower_sunset_valley");

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
        context.register(TREES_AUTUMN_BIRCH, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.AUTUMN_BIRCH_0002),
                treePlacement(PlacementUtils.countExtra(10, 0.1F, 1),
                        ModBlocks.AUTUMN_BIRCH_SAPLING.get()
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
        context.register(AUTUMN_BIRCH_LEAF_PILE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.AUTUMN_BIRCH_LEAF_PILE),
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
        context.register(GINKGO_LEAF_PILE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.GINKGO_LEAF_PILE),
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
        context.register(MAPLE_LEAF_PILE, new PlacedFeature(
                features.getOrThrow(ModVegetationFeatures.MAPLE_LEAF_PILE),
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
    }
}
