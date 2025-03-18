package com.ChalkerCharles.morecolorful.common.worldgen.features;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import static com.ChalkerCharles.morecolorful.common.worldgen.features.ModConfiguredFeatures.registerKey;
import static net.minecraft.data.worldgen.features.FeatureUtils.register;

@SuppressWarnings("deprecation")
public class ModMiscOverworldFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_LAKE = registerKey("water_lake");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, WATER_LAKE,
                ModFeatures.LAKE.get(),
                new LakeFeature.Configuration(
                        BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
                        BlockStateProvider.simple(Blocks.AIR.defaultBlockState())
                )
        );
    }
}
