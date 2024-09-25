package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.function.Supplier;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<PlacedFeature> CRABAPPLE_BEE = registerKey("crabapple_bee");

    private static List<PlacementModifier> getSapling(Supplier<SaplingBlock> block) {
        return List.of(PlacementUtils.filteredByBlockSurvival(block.get()));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(CRABAPPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
        context.register(CRABAPPLE_BEE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE_BEE), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
    }
}
