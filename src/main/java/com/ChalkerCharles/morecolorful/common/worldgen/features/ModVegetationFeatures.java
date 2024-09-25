package com.ChalkerCharles.morecolorful.common.worldgen.features;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;

public class ModVegetationFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CRABAPPLE = registerKey("flower_crabapple");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; i++) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add(
                        ModBlocks.BEGONIAS.get().defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i).setValue(PinkPetalsBlock.FACING, direction), 1
                );
            }
        }
        builder.add(ModBlocks.RED_CARNATION.get().defaultBlockState(), 4);
        context.register(
                FLOWER_CRABAPPLE,
                new ConfiguredFeature<>(
                        Feature.FLOWER,
                        new RandomPatchConfiguration(
                                96, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(builder)))
                        )
                )
        );
    }
}
