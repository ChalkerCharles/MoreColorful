package com.ChalkerCharles.morecolorful.common.worldgen.features;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.common.LeafPileBlock;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModTreePlacements;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModVegetationPlacements;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;

public class ModVegetationFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CRABAPPLE = registerKey("flower_crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_WHITE_CHERRY = registerKey("flower_white_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_AUTUMN_BIRCH = registerKey("flower_autumn_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AUTUMN_BIRCH_LEAF_PILE = registerKey("autumn_birch_leaf_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GINKGO = registerKey("trees_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_GINKGO = registerKey("flower_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO_LEAF_PILE = registerKey("ginkgo_leaf_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MAPLE = registerKey("trees_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MAPLE = registerKey("flower_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_LEAF_PILE = registerKey("maple_leaf_pile");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SUNSET_VALLEY = registerKey("trees_sunset_valley");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SUNSET_VALLEY = registerKey("flower_sunset_valley");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);

        SimpleWeightedRandomList.Builder<BlockState> begonias = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                begonias.add(
                        ModBlocks.BEGONIAS.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), 1
                );
            }
        }
        begonias.add(ModBlocks.RED_CARNATION.get().defaultBlockState(), 4);
        context.register(
                FLOWER_CRABAPPLE,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(begonias)))
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> whitePetals = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                whitePetals.add(
                        ModBlocks.WHITE_PETALS.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), 1
                );
            }
        }
        whitePetals.add(ModBlocks.WHITE_CARNATION.get().defaultBlockState(), 4);
        context.register(
                FLOWER_WHITE_CHERRY,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(whitePetals)))
                        )
                )
        );

        context.register(FLOWER_AUTUMN_BIRCH,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        grassPatch(
                                new WeightedStateProvider(
                                        SimpleWeightedRandomList.<BlockState>builder()
                                                .add(Blocks.DANDELION.defaultBlockState())
                                                .add(Blocks.OXEYE_DAISY.defaultBlockState())
                                                .add(ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(), 2)
                                                .add(ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(), 2)
                                ),
                                128
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> autumnBirchLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                autumnBirchLeafPile.add(
                        ModBlocks.AUTUMN_BIRCH_LEAF_PILE.get().defaultBlockState().setValue(LeafPileBlock.AMOUNT, i).setValue(LeafPileBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                AUTUMN_BIRCH_LEAF_PILE,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(
                                128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(autumnBirchLeafPile)))
                        )
                )
        );

        context.register(
                TREES_GINKGO,
                new ConfiguredFeature<>(
                        Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.FANCY_GINKGO),
                                                0.06F
                                        )
                                ),
                                placedFeature.getOrThrow(ModTreePlacements.GINKGO)
                        )
                )
        );

        context.register(FLOWER_GINKGO,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        grassPatch(
                                new WeightedStateProvider(
                                        SimpleWeightedRandomList.<BlockState>builder()
                                                .add(Blocks.DANDELION.defaultBlockState())
                                                .add(Blocks.OXEYE_DAISY.defaultBlockState())
                                                .add(ModBlocks.DAYBLOOM.get().defaultBlockState(), 2)
                                                .add(ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(), 2)
                                ),
                                128
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> ginkgoLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                ginkgoLeafPile.add(
                        ModBlocks.GINKGO_LEAF_PILE.get().defaultBlockState().setValue(LeafPileBlock.AMOUNT, i).setValue(LeafPileBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                GINKGO_LEAF_PILE,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(
                                128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(ginkgoLeafPile)))
                        )
                )
        );

        context.register(
                TREES_MAPLE,
                new ConfiguredFeature<>(
                        Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.FANCY_MAPLE),
                                                0.3F
                                        )
                                ),
                                placedFeature.getOrThrow(ModTreePlacements.MAPLE)
                        )
                )
        );

        context.register(FLOWER_MAPLE,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        grassPatch(
                                new WeightedStateProvider(
                                        SimpleWeightedRandomList.<BlockState>builder()
                                                .add(Blocks.DANDELION.defaultBlockState())
                                                .add(Blocks.OXEYE_DAISY.defaultBlockState())
                                                .add(ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(), 2)
                                                .add(ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(), 2)
                                ),
                                128
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> mapleLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                mapleLeafPile.add(
                        ModBlocks.MAPLE_LEAF_PILE.get().defaultBlockState().setValue(LeafPileBlock.AMOUNT, i).setValue(LeafPileBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                MAPLE_LEAF_PILE,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(
                                128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(mapleLeafPile)))
                        )
                )
        );

        context.register(
                TREES_SUNSET_VALLEY,
                new ConfiguredFeature<>(
                        Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(TreePlacements.OAK_BEES_0002),
                                                0.2F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002),
                                                0.06F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.AUTUMN_BIRCH_0002),
                                                0.4F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.GINKGO),
                                                0.3F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.FANCY_GINKGO),
                                                0.06F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.FANCY_MAPLE),
                                                0.1F
                                        )
                                ),
                                placedFeature.getOrThrow(ModTreePlacements.MAPLE)
                        )
                )
        );

        context.register(FLOWER_SUNSET_VALLEY,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        grassPatch(
                                new WeightedStateProvider(
                                        SimpleWeightedRandomList.<BlockState>builder()
                                                .add(Blocks.DANDELION.defaultBlockState())
                                                .add(Blocks.OXEYE_DAISY.defaultBlockState())
                                                .add(ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(), 2)
                                                .add(ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(), 2)
                                                .add(ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(), 2)
                                                .add(ModBlocks.DAYBLOOM.get().defaultBlockState(), 2)
                                ),
                                128
                        )
                )
        );
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider pStateProvider, int pTries) {
        return FeatureUtils.simpleRandomPatchConfiguration(
                pTries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(pStateProvider))
        );
    }
}
