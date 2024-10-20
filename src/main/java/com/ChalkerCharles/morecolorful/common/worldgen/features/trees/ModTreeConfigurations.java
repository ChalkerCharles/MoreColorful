package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class ModTreeConfigurations {
    public static final TreeConfiguration CRABAPPLE = crabapple().build();
    public static final TreeConfiguration CRABAPPLE_005 = crabapple()
            .decorators(List.of(new BeehiveDecorator(0.05F)))
            .build();
    public static final TreeConfiguration WHITE_CHERRY = whiteCherry().build();
    public static final TreeConfiguration WHITE_CHERRY_005 = whiteCherry()
            .decorators(List.of(new BeehiveDecorator(0.05F)))
            .build();
    public static final TreeConfiguration AUTUMN_BIRCH = TreeFeatures.createStraightBlobTree(
            Blocks.BIRCH_LOG, ModBlocks.AUTUMN_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines().build();
    public static final TreeConfiguration AUTUMN_BIRCH_0002 = TreeFeatures.createStraightBlobTree(
            Blocks.BIRCH_LOG, ModBlocks.AUTUMN_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(new BeehiveDecorator(0.002F))).build();
    public static final TreeConfiguration AUTUMN_BIRCH_005 = TreeFeatures.createStraightBlobTree(
                    Blocks.BIRCH_LOG, ModBlocks.AUTUMN_BIRCH_LEAVES.get(), 5, 2, 1, 2).ignoreVines()
            .decorators(List.of(new BeehiveDecorator(0.05F))).build();
    public static final TreeConfiguration GINKGO = new TreeConfiguration.TreeConfigurationBuilder(
            BlockStateProvider.simple(ModBlocks.GINKGO_LOG.get()),
            new StraightTrunkPlacer(5, 2, 0),
            BlockStateProvider.simple(ModBlocks.GINKGO_LEAVES.get()),
            new RandomSpreadFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), ConstantInt.of(4), 150),
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
}
