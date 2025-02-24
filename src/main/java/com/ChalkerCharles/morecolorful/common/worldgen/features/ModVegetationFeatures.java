package com.ChalkerCharles.morecolorful.common.worldgen.features;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.common.BerryBushBlock;
import com.ChalkerCharles.morecolorful.common.block.common.LeafLitterBlock;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModTreePlacements;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
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
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;

public class ModVegetationFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CRABAPPLE = registerKey("flower_crabapple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_WHITE_CHERRY = registerKey("flower_white_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CHERRY = registerKey("flower_cherry");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AUTUMN_BIRCH = registerKey("trees_autumn_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_AUTUMN_BIRCH = registerKey("flower_autumn_birch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AUTUMN_BIRCH_LEAF_LITTER = registerKey("autumn_birch_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GINKGO = registerKey("trees_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_GINKGO = registerKey("flower_ginkgo");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GINKGO_LEAF_LITTER = registerKey("ginkgo_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_MAPLE = registerKey("trees_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MAPLE = registerKey("flower_maple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_LEAF_LITTER = registerKey("maple_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SUNSET_VALLEY = registerKey("trees_sunset_valley");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SUNSET_VALLEY = registerKey("flower_sunset_valley");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FROST = registerKey("flower_frost");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUEBERRY_BUSH = registerKey("patch_blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DAWN_REDWOOD = registerKey("flower_dawn_redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_LEAF_LITTER = registerKey("dawn_redwood_leaf_litter");

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
        whitePetals.add(ModBlocks.WHITE_CARNATION.get().defaultBlockState(), 1);
        context.register(
                FLOWER_WHITE_CHERRY,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(whitePetals)))
                        )
                )
        );

        context.register(FLOWER_CHERRY,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(
                                                SimpleWeightedRandomList.<BlockState>builder()
                                                        .add(ModBlocks.PINK_CARNATION.get().defaultBlockState(), 1)
                                                        .add(ModBlocks.PINK_DAISY.get().defaultBlockState(), 1)
                                        )
                                ))
                        )
                )
        );

        context.register(
                TREES_AUTUMN_BIRCH,
                new ConfiguredFeature<>(
                        Feature.RANDOM_SELECTOR,
                        new RandomFeatureConfiguration(
                                List.of(
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.YELLOW_BIRCH_0002),
                                                0.5F
                                        )
                                ),
                                placedFeature.getOrThrow(ModTreePlacements.ORANGE_BIRCH_0002)
                        )
                )
        );

        context.register(FLOWER_AUTUMN_BIRCH,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                128,
                                6,
                                2,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                new NoiseProvider(
                                                        2345L,
                                                        new NormalNoise.NoiseParameters(0, 1.0),
                                                        0.020833334F,
                                                        List.of(
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> autumnBirchLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                autumnBirchLeafPile.add(
                        ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get().defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), 1
                );
                autumnBirchLeafPile.add(
                        ModBlocks.YELLOW_BIRCH_LEAF_LITTER.get().defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                AUTUMN_BIRCH_LEAF_LITTER,
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
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(TreePlacements.OAK_BEES_0002),
                                                0.2F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002),
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
                        new RandomPatchConfiguration(
                                128,
                                6,
                                2,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                new NoiseProvider(
                                                        2345L,
                                                        new NormalNoise.NoiseParameters(0, 1.0),
                                                        0.020833334F,
                                                        List.of(
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.OPEN_DAYBLOOM.get().defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.OPEN_DAYBLOOM.get().defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> ginkgoLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                ginkgoLeafPile.add(
                        ModBlocks.GINKGO_LEAF_LITTER.get().defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                GINKGO_LEAF_LITTER,
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
                        new RandomPatchConfiguration(
                                128,
                                6,
                                2,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                new NoiseProvider(
                                                        2345L,
                                                        new NormalNoise.NoiseParameters(0, 1.0),
                                                        0.020833334F,
                                                        List.of(
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(),
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> mapleLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                mapleLeafPile.add(
                        ModBlocks.MAPLE_LEAF_LITTER.get().defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                MAPLE_LEAF_LITTER,
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
                                                placedFeature.getOrThrow(ModTreePlacements.ORANGE_BIRCH_0002),
                                                0.2F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.YELLOW_BIRCH_0002),
                                                0.2F
                                        ),
                                        new WeightedPlacedFeature(
                                                placedFeature.getOrThrow(ModTreePlacements.GINKGO),
                                                0.2F
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
                        new RandomPatchConfiguration(
                                128,
                                6,
                                2,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                new NoiseProvider(
                                                        2345L,
                                                        new NormalNoise.NoiseParameters(0, 1.0),
                                                        0.020833334F,
                                                        List.of(
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(),
                                                                ModBlocks.OPEN_DAYBLOOM.get().defaultBlockState(),
                                                                Blocks.DANDELION.defaultBlockState(),
                                                                Blocks.OXEYE_DAISY.defaultBlockState(),
                                                                ModBlocks.YELLOW_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.GREEN_CHRYSANTHEMUM.get().defaultBlockState(),
                                                                ModBlocks.RED_SPIDER_LILY.get().defaultBlockState(),
                                                                ModBlocks.OPEN_DAYBLOOM.get().defaultBlockState()
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> frosty = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                frosty.add(
                        ModBlocks.FROSTY_PETALS.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), 1
                );
            }
        }
        frosty.add(ModBlocks.EDELWEISS.get().defaultBlockState(), 2);
        frosty.add(ModBlocks.CROCUS.get().defaultBlockState(), 2);
        context.register(
                FLOWER_FROST,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(frosty)))
                        )
                )
        );

        context.register(PATCH_STRAWBERRY_BUSH,
                new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                        FeatureUtils.simplePatchConfiguration(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(ModBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(BerryBushBlock.AGE, 4))
                                ),
                                List.of(Blocks.GRASS_BLOCK)
                        )
                )
        );

        context.register(PATCH_BLUEBERRY_BUSH,
                new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                        FeatureUtils.simplePatchConfiguration(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(
                                        BlockStateProvider.simple(ModBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(BerryBushBlock.AGE, 4))
                                ),
                                List.of(Blocks.GRASS_BLOCK)
                        )
                )
        );

        context.register(FLOWER_DAWN_REDWOOD,
                new ConfiguredFeature<>(Feature.FLOWER,
                        new RandomPatchConfiguration(
                                64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.IRIS.get())))
                        )
                )
        );

        SimpleWeightedRandomList.Builder<BlockState> dawnRedwoodLeafPile = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                dawnRedwoodLeafPile.add(
                        ModBlocks.DAWN_REDWOOD_LEAF_LITTER.get().defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), 1
                );
            }
        }
        context.register(
                DAWN_REDWOOD_LEAF_LITTER,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(
                                128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(dawnRedwoodLeafPile)))
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
