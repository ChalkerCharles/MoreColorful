package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers.DawnRedwoodFoliagePlacer;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers.GinkgoFoliagePlacer;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.rootplacers.DawnRedwoodRootPlacer;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.treedecorators.WillowBranchesDecorator;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.trunkplacers.DawnRedwoodTrunkPlacer;
import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

public class ModTreeConfigurations {
    private static final BeehiveDecorator beehive0002 = new BeehiveDecorator(0.002F);
    private static final BeehiveDecorator beehive005 = new BeehiveDecorator(0.05F);
    private static final BeehiveDecorator beehive = new BeehiveDecorator(1.0F);

    public static final TreeConfiguration CRABAPPLE = crabapple().build();
    public static final TreeConfiguration CRABAPPLE_005 = crabapple()
            .decorators(List.of(beehive005))
            .build();
    public static final TreeConfiguration WHITE_CHERRY = whiteCherry().build();
    public static final TreeConfiguration WHITE_CHERRY_005 = whiteCherry()
            .decorators(List.of(beehive005))
            .build();
    public static final TreeConfiguration ORANGE_BIRCH = TreeFeatures.createStraightBlobTree(
            Blocks.BIRCH_LOG, ModBlocks.ORANGE_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines().build();
    public static final TreeConfiguration ORANGE_BIRCH_0002 = TreeFeatures.createStraightBlobTree(
            Blocks.BIRCH_LOG, ModBlocks.ORANGE_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(beehive0002)).build();
    public static final TreeConfiguration ORANGE_BIRCH_005 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.ORANGE_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(beehive005)).build();
    public static final TreeConfiguration TALL_ORANGE_BIRCH_0002 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.ORANGE_BIRCH_LEAVES.get(), 5, 2, 6, 2).ignoreVines()
            .decorators(List.of(beehive0002)).build();
    public static final TreeConfiguration YELLOW_BIRCH = TreeFeatures.createStraightBlobTree(
            Blocks.BIRCH_LOG, ModBlocks.YELLOW_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines().build();
    public static final TreeConfiguration YELLOW_BIRCH_0002 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.YELLOW_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(beehive0002)).build();
    public static final TreeConfiguration YELLOW_BIRCH_005 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.YELLOW_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(beehive005)).build();
    public static final TreeConfiguration TALL_YELLOW_BIRCH_0002 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.YELLOW_BIRCH_LEAVES.get(), 5, 2, 6, 2).ignoreVines()
            .decorators(List.of(beehive0002)).build();
    public static final TreeConfiguration GINKGO = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.GINKGO_LOG.get()),
            new StraightTrunkPlacer(5, 2, 0),
            BlockStateProvider.simple(ModBlocks.GINKGO_LEAVES.get()),
            new GinkgoFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(4), 30),
            new TwoLayersFeatureSize(1, 0, 1)
    ).build();
    public static final TreeConfiguration FANCY_GINKGO = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.GINKGO_LOG.get()),
            new FancyTrunkPlacer(9, 5, 0),
            BlockStateProvider.simple(ModBlocks.GINKGO_LEAVES.get()),
            new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
            new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))
    ).build();
    public static final TreeConfiguration MAPLE = TreeFeatures.createStraightBlobTree(
            ModBlocks.MAPLE_LOG.get(), ModBlocks.MAPLE_LEAVES.get(), 4, 2, 0, 2).ignoreVines().build();
    public static final TreeConfiguration FANCY_MAPLE = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.MAPLE_LOG.get()),
            new CherryTrunkPlacer(
                    5,
                    2,
                    0,
                    new WeightedListInt(
                            SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                    ),
                    UniformInt.of(2, 2),
                    UniformInt.of(-3, -2),
                    UniformInt.of(-1, 0)
            ),
            BlockStateProvider.simple(ModBlocks.MAPLE_LEAVES.get()),
            new CherryFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.066666667F, 0.16666667F),
            new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().build();
    public static final TreeConfiguration FROST = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.FROST_LOG.get()),
            new CherryTrunkPlacer(
                    7,
                    1,
                    0,
                    new WeightedListInt(
                            SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                    ),
                    UniformInt.of(2, 4),
                    UniformInt.of(-4, -3),
                    UniformInt.of(-1, 0)
            ),
            BlockStateProvider.simple(ModBlocks.FROST_LEAVES.get()),
            new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
            new TwoLayersFeatureSize(1, 0, 2)
    ).ignoreVines().build();
    public static final TreeConfiguration DAWN_REDWOOD = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.DAWN_REDWOOD_LOG.get()),
            new DawnRedwoodTrunkPlacer(12, 10, 8),
            BlockStateProvider.simple(ModBlocks.DAWN_REDWOOD_LEAVES.get()),
            new DawnRedwoodFoliagePlacer(UniformInt.of(3, 4), UniformInt.of(0, 2), UniformInt.of(3, 5)),
            Optional.of(new DawnRedwoodRootPlacer(ConstantInt.of(0), BlockStateProvider.simple(ModBlocks.DAWN_REDWOOD_ROOTS.get()), Optional.empty())),
            new TwoLayersFeatureSize(2, 0, 2)
    ).ignoreVines().build();
    public static final TreeConfiguration JACARANDA = jacaranda().build();
    public static final TreeConfiguration JACARANDA_005 = jacaranda()
            .decorators(List.of(beehive005))
            .build();
    public static final TreeConfiguration JACARANDA_BEES = jacaranda()
            .decorators(List.of(beehive))
            .build();
    public static final TreeConfiguration OAK_BEES = TreeFeatures.createOak()
            .decorators(List.of(beehive))
            .build();
    public static final TreeConfiguration BIRCH_BEES = TreeFeatures.createBirch()
            .decorators(List.of(beehive))
            .build();
    public static final TreeConfiguration CHERRY_BEES = TreeFeatures.cherry()
            .decorators(List.of(beehive))
            .build();
    public static final TreeConfiguration WILLOW = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.WILLOW_LOG.get()),
            new StraightTrunkPlacer(5, 1, 2),
            BlockStateProvider.simple(ModBlocks.WILLOW_LEAVES.get()),
            new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(4), 0.5F, 0.8F, 0.2F, 0.33333334F),
            new TwoLayersFeatureSize(1, 0, 1)
    ).decorators(
            ImmutableList.of(
                    new LeaveVineDecorator(0.08F),
                    new WillowBranchesDecorator(0.25F, UniformInt.of(1, 5))
            )
    ).ignoreVines().build();
    public static final TreeConfiguration FANCY_WILLOW = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.WILLOW_LOG.get()),
            new FancyTrunkPlacer(8, 2, 4),
            BlockStateProvider.simple(ModBlocks.WILLOW_LEAVES.get()),
            new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(2), ConstantInt.of(4), 0.5F, 0.8F, 0.33333334F, 0.4F),
            new TwoLayersFeatureSize(1, 0, 2)
    ).decorators(
            ImmutableList.of(
                    new LeaveVineDecorator(0.1F),
                    new WillowBranchesDecorator(0.275F, UniformInt.of(1, 8))
            )
    ).ignoreVines().build();

    private static TreeConfiguration.TreeConfigurationBuilder crabapple() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.CRABAPPLE_LOG.get()),
                new CherryTrunkPlacer(
                        7,
                        1,
                        0,
                        new WeightedListInt(
                                SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                        ),
                        UniformInt.of(2, 3),
                        UniformInt.of(-5, -3),
                        UniformInt.of(-2, -1)
                ),
                BlockStateProvider.simple(ModBlocks.CRABAPPLE_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        )
                .ignoreVines();
    }
    private static TreeConfiguration.TreeConfigurationBuilder whiteCherry() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(Blocks.CHERRY_LOG),
                new CherryTrunkPlacer(
                        7,
                        1,
                        0,
                        new WeightedListInt(
                                SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 1).add(ConstantInt.of(3), 1).build()
                        ),
                        UniformInt.of(2, 4),
                        UniformInt.of(-4, -3),
                        UniformInt.of(-1, 0)
                ),
                BlockStateProvider.simple(ModBlocks.WHITE_CHERRY_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.25F, 0.5F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        )
                .ignoreVines();
    }
    private static TreeConfiguration.TreeConfigurationBuilder jacaranda() {
        return new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.JACARANDA_LOG.get()),
                new CherryTrunkPlacer(
                        7,
                        2,
                        0,
                        new WeightedListInt(
                                SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(1), 1).add(ConstantInt.of(2), 2).add(ConstantInt.of(3), 2).build()
                        ),
                        UniformInt.of(2, 4),
                        UniformInt.of(-5, -2),
                        UniformInt.of(-2, 0)
                ),
                BlockStateProvider.simple(ModBlocks.JACARANDA_LEAVES.get()),
                new CherryFoliagePlacer(ConstantInt.of(4), ConstantInt.of(0), ConstantInt.of(5), 0.5F, 0.75F, 0.16666667F, 0.33333334F),
                new TwoLayersFeatureSize(1, 0, 2)
        )
                .ignoreVines();
    }
}
