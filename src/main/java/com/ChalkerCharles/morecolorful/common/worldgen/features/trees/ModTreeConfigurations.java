package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;

import java.util.List;

public class ModTreeConfigurations {
    public static final TreeConfiguration CRABAPPLE = crabapple().build();
    public static final TreeConfiguration CRABAPPLE_BEE = crabapple()
            .decorators(List.of(new BeehiveDecorator(0.05F)))
            .build();

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
}
