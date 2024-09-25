package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.ModVegetationFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;

public class ModVegetationPlacements {
    public static final ResourceKey<PlacedFeature> TREES_CRABAPPLE = registerKey("trees_crabapple");
    public static final ResourceKey<PlacedFeature> FLOWER_CRABAPPLE = registerKey("flower_crabapple");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(TREES_CRABAPPLE, new PlacedFeature(
                features.getOrThrow(ModTreeFeatures.CRABAPPLE_BEE),
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
    }
}
