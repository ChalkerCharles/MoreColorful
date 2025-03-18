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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
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
import static net.minecraft.data.worldgen.features.FeatureUtils.register;

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
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CROCUS = registerKey("patch_crocus");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUEBERRY_BUSH = registerKey("patch_blueberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DAWN_REDWOOD = registerKey("flower_dawn_redwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAWN_REDWOOD_LEAF_LITTER = registerKey("dawn_redwood_leaf_litter");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_LAVENDER = registerKey("trees_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LAVENDER = registerKey("flower_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_JACARANDA = registerKey("flower_jacaranda");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MARSH = registerKey("flower_marsh");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WATER_GRASS = registerKey("patch_water_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GERBERA_DAISY = registerKey("patch_gerbera_daisy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CATTAIL = registerKey("patch_cattail");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_REED = registerKey("patch_reed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WATER_LILY = registerKey("patch_water_lily");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEEDS = registerKey("patch_duckweeds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BUTTERCUPS = registerKey("patch_buttercups");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FORGET_ME_NOTS = registerKey("patch_forget-me-nots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SPEEDWELLS = registerKey("patch_speedwells");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_AZURE = registerKey("trees_azure");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_AZURE = registerKey("flower_azure");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WILLOW = registerKey("trees_willow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WILLOW_BAYOU = registerKey("trees_willow_bayou");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WOOD_SORRELS = registerKey("patch_wood_sorrels");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);
        SimpleWeightedRandomList.Builder<BlockState> begonias = SimpleWeightedRandomList.builder();
        addPetals(begonias, ModBlocks.BEGONIAS.get(), 3);
        begonias.add(ModBlocks.RED_CARNATION.get().defaultBlockState(), 2);
        register(context, FLOWER_CRABAPPLE,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(begonias)))
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> whitePetals = SimpleWeightedRandomList.builder();
        addPetals(whitePetals, ModBlocks.WHITE_PETALS.get(), 3);
        whitePetals.add(ModBlocks.WHITE_CARNATION.get().defaultBlockState(), 2);
        register(context, FLOWER_WHITE_CHERRY,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(whitePetals)))
                )
        );
        register(context, FLOWER_CHERRY,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                new WeightedStateProvider(
                                        SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModBlocks.PINK_CARNATION.get().defaultBlockState())
                                                .add(ModBlocks.PINK_DAISY.get().defaultBlockState())
                                )
                        ))
                )
        );
        register(context, TREES_AUTUMN_BIRCH,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.YELLOW_BIRCH_0002),
                                        0.45F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.TALL_YELLOW_BIRCH_0002),
                                        0.05F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.TALL_ORANGE_BIRCH_0002),
                                        0.05F
                                )
                        ),
                        placedFeature.getOrThrow(ModTreePlacements.ORANGE_BIRCH_0002)
                )
        );
        register(context, FLOWER_AUTUMN_BIRCH,
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
        );
        SimpleWeightedRandomList.Builder<BlockState> autumnBirchLeafLitters = SimpleWeightedRandomList.builder();
        addLeafLitters(autumnBirchLeafLitters, ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get());
        addLeafLitters(autumnBirchLeafLitters, ModBlocks.YELLOW_BIRCH_LEAF_LITTER.get());
        register(context, AUTUMN_BIRCH_LEAF_LITTER,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(autumnBirchLeafLitters)))
                )
        );
        register(context, TREES_GINKGO,
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
        );
        register(context, FLOWER_GINKGO,
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
        );
        SimpleWeightedRandomList.Builder<BlockState> ginkgoLeafLitters = SimpleWeightedRandomList.builder();
        addLeafLitters(ginkgoLeafLitters, ModBlocks.GINKGO_LEAF_LITTER.get());
        register(context, GINKGO_LEAF_LITTER,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(ginkgoLeafLitters)))
                )
        );
        register(context, TREES_MAPLE,
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
        );
        register(context, FLOWER_MAPLE,
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
        );
        SimpleWeightedRandomList.Builder<BlockState> mapleLeafLitters = SimpleWeightedRandomList.builder();
        addLeafLitters(mapleLeafLitters, ModBlocks.MAPLE_LEAF_LITTER.get());
        register(context, MAPLE_LEAF_LITTER,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(mapleLeafLitters)))
                )
        );
        register(context, TREES_SUNSET_VALLEY,
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
                                        placedFeature.getOrThrow(ModTreePlacements.TALL_ORANGE_BIRCH_0002),
                                        0.02F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.TALL_YELLOW_BIRCH_0002),
                                        0.02F
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
        );
        register(context, FLOWER_SUNSET_VALLEY,
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
        );
        SimpleWeightedRandomList.Builder<BlockState> frosty = SimpleWeightedRandomList.builder();
        addPetals(frosty, ModBlocks.FROSTY_PETALS.get(), 2);
        frosty.add(ModBlocks.EDELWEISS.get().defaultBlockState());
        frosty.add(ModBlocks.CROCUS.get().defaultBlockState());
        register(context, FLOWER_FROST,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(frosty)))
                )
        );
        register(context, PATCH_CROCUS,
                Feature.FLOWER,
                grassPatch(BlockStateProvider.simple(ModBlocks.CROCUS.get()), 64)
        );
        register(context, PATCH_STRAWBERRY_BUSH,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(ModBlocks.STRAWBERRY_BUSH.get().defaultBlockState().setValue(BerryBushBlock.AGE, 4))
                        ),
                        List.of(Blocks.GRASS_BLOCK)
                )
        );
        register(context, PATCH_BLUEBERRY_BUSH,
                Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(
                        Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(
                                BlockStateProvider.simple(ModBlocks.BLUEBERRY_BUSH.get().defaultBlockState().setValue(BerryBushBlock.AGE, 4))
                        ),
                        List.of(Blocks.GRASS_BLOCK)
                )
        );
        register(context, FLOWER_DAWN_REDWOOD,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        64, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.IRIS.get())))
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> dawnRedwoodLeafLitters = SimpleWeightedRandomList.builder();
        addLeafLitters(dawnRedwoodLeafLitters, ModBlocks.DAWN_REDWOOD_LEAF_LITTER.get());
        register(context, DAWN_REDWOOD_LEAF_LITTER,
                Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(dawnRedwoodLeafLitters)))
                )
        );
        register(context, TREES_LAVENDER,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(TreePlacements.FANCY_OAK_BEES),
                                        0.13333333F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.OAK_BEES),
                                        0.4F
                                )
                        ),
                        placedFeature.getOrThrow(ModTreePlacements.JACARANDA_BEES)
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> lavenders = SimpleWeightedRandomList.builder();
        addPetals(lavenders, ModBlocks.VIOLETS.get());
        lavenders.add(ModBlocks.LAVENDER.get().defaultBlockState(), 128);
        register(context, FLOWER_LAVENDER,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(lavenders)))
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> violets = SimpleWeightedRandomList.builder();
        addPetals(violets, ModBlocks.VIOLETS.get(), 2);
        violets.add(ModBlocks.LAVENDER.get().defaultBlockState());
        register(context, FLOWER_JACARANDA,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(violets)))
                )
        );
        register(context, FLOWER_MARSH,
                Feature.FLOWER,
                grassPatch(new WeightedStateProvider(
                    SimpleWeightedRandomList.<BlockState>builder()
                            .add(Blocks.DANDELION.defaultBlockState(), 2)
                            .add(Blocks.POPPY.defaultBlockState(), 2)
                            .add(Blocks.BLUE_ORCHID.defaultBlockState())
                            .add(ModBlocks.IRIS.get().defaultBlockState())
                            .add(ModBlocks.DAFFODIL.get().defaultBlockState(), 6)
                ), 64)
        );
        register(context, PATCH_WATER_GRASS,
                Feature.RANDOM_PATCH,
                waterPatchConfiguration(
                    new WeightedStateProvider(
                            SimpleWeightedRandomList.<BlockState>builder()
                                    .add(ModBlocks.SHORT_WATER_GRASS.get().defaultBlockState(), 2)
                                    .add(ModBlocks.TALL_WATER_GRASS.get().defaultBlockState())
                    )
                )
        );
        register(context, PATCH_GERBERA_DAISY,
                Feature.FLOWER,
                grassPatch(BlockStateProvider.simple(ModBlocks.GERBERA_DAISY.get()), 64)
        );
        register(context, PATCH_CATTAIL,
                Feature.RANDOM_PATCH,
                FeatureUtils.simpleRandomPatchConfiguration(
                        64, PlacementUtils.filtered(
                                Feature.SIMPLE_BLOCK,
                                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CATTAIL.get())),
                                BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE
                        )
                )
        );
        register(context, PATCH_REED,
                ModFeatures.REED.get(),
                new ProbabilityFeatureConfiguration(0.4F)
        );
        register(context, PATCH_WATER_LILY,
                Feature.RANDOM_PATCH,
                grassPatch(new WeightedStateProvider(
                        SimpleWeightedRandomList.<BlockState>builder()
                                .add(ModBlocks.OPEN_WATER_LILY.get().defaultBlockState(), 17)
                                .add(ModBlocks.OPEN_WHITE_WATER_LILY.get().defaultBlockState(),14)
                                .add(ModBlocks.OPEN_BLUE_WATER_LILY.get().defaultBlockState())
                ), 8)
        );
        SimpleWeightedRandomList.Builder<BlockState> duckweeds = SimpleWeightedRandomList.builder();
        addLeafLitters(duckweeds, ModBlocks.DUCKWEEDS.get());
        register(context, PATCH_DUCKWEEDS,
                Feature.RANDOM_PATCH,
                grassPatch(new WeightedStateProvider(duckweeds), 32)
        );
        SimpleWeightedRandomList.Builder<BlockState> buttercups = SimpleWeightedRandomList.builder();
        addPetals(buttercups, ModBlocks.BUTTERCUPS.get());
        register(context, PATCH_BUTTERCUPS,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(buttercups)))
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> forget_me_nots = SimpleWeightedRandomList.builder();
        addPetals(forget_me_nots, ModBlocks.FORGET_ME_NOTS.get());
        register(context, PATCH_FORGET_ME_NOTS,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(forget_me_nots)))
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> speedwells = SimpleWeightedRandomList.builder();
        addPetals(speedwells, ModBlocks.SPEEDWELLS.get());
        register(context, PATCH_SPEEDWELLS,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(speedwells)))
                )
        );
        register(context, TREES_AZURE,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(TreePlacements.SUPER_BIRCH_BEES),
                                        0.125F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.BIRCH_BEES),
                                        0.375F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(TreePlacements.FANCY_OAK_BEES),
                                        0.125F
                                )
                        ),
                        placedFeature.getOrThrow(ModTreePlacements.OAK_BEES)
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> azure = SimpleWeightedRandomList.builder();
        addPetalsWithWeight(azure, ModBlocks.BABY_BLUE_EYES.get(), 8);
        addPetalsWithWeight(azure, ModBlocks.FORGET_ME_NOTS.get());
        addPetalsWithWeight(azure, ModBlocks.SPEEDWELLS.get());
        azure.add(Blocks.CORNFLOWER.defaultBlockState(), 4);
        register(context, FLOWER_AZURE,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        128, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(azure)))
                )
        );
        register(context, TREES_WILLOW,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.FANCY_WILLOW),
                                        0.4F
                                )
                        ),
                        placedFeature.getOrThrow(ModTreePlacements.WILLOW)
                )
        );
        register(context, TREES_WILLOW_BAYOU,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(
                        List.of(
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.FANCY_WILLOW),
                                        0.4F
                                ),
                                new WeightedPlacedFeature(
                                        placedFeature.getOrThrow(ModTreePlacements.SWAMP_OAK),
                                        0.2F
                                )
                        ),
                        placedFeature.getOrThrow(ModTreePlacements.WILLOW)
                )
        );
        SimpleWeightedRandomList.Builder<BlockState> woodSorrels = SimpleWeightedRandomList.builder();
        addPetals(woodSorrels, ModBlocks.WOOD_SORRELS.get());
        register(context, PATCH_WOOD_SORRELS,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        32, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(woodSorrels)))
                )
        );
    }

    private static RandomPatchConfiguration grassPatch(BlockStateProvider pStateProvider, int pTries) {
        return FeatureUtils.simpleRandomPatchConfiguration(
                pTries, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(pStateProvider))
        );
    }
    private static RandomPatchConfiguration waterPatchConfiguration(BlockStateProvider pStateProvider) {
        return FeatureUtils.simpleRandomPatchConfiguration(
                96, PlacementUtils.filtered(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(pStateProvider), BlockPredicate.matchesBlocks(Blocks.WATER))
        );
    }
    private static void addPetalsOrLeafLitters(SimpleWeightedRandomList.Builder<BlockState> builder, Block block, boolean isPetals, int weight) {
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (isPetals) {
                    builder.add(
                            block.defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), weight
                    );
                } else {
                    builder.add(
                            block.defaultBlockState().setValue(LeafLitterBlock.AMOUNT, i).setValue(LeafLitterBlock.FACING, direction), weight
                    );
                }
            }
        }
    }
    private static void addPetals(SimpleWeightedRandomList.Builder<BlockState> builder, Block block, int weight) {
        addPetalsOrLeafLitters(builder, block, true, weight);
    }
    private static void addPetals(SimpleWeightedRandomList.Builder<BlockState> builder, Block block) {
        addPetals(builder, block, 1);
    }
    private static void addLeafLitters(SimpleWeightedRandomList.Builder<BlockState> builder, Block block) {
        addPetalsOrLeafLitters(builder, block, false, 1);
    }
    private static void addPetalsWithWeight(SimpleWeightedRandomList.Builder<BlockState> builder, Block block, int weight) {
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add(
                        block.defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), weight * i
                );
            }
        }
    }
    private static void addPetalsWithWeight(SimpleWeightedRandomList.Builder<BlockState> builder, Block block) {
        addPetalsWithWeight(builder, block, 1);
    }
}
