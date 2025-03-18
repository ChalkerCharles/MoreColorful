package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.worldgen.features.ModMiscOverworldFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;
import static net.minecraft.data.worldgen.placement.PlacementUtils.register;

public class ModMiscOverworldPlacements {
    public static final ResourceKey<PlacedFeature> WATER_LAKE_MARSH = registerKey("water_lake");

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, WATER_LAKE_MARSH,
                features.getOrThrow(ModMiscOverworldFeatures.WATER_LAKE),
                List.of(CountPlacement.of(10),
                        InSquarePlacement.spread(),
                        PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                        BiomeFilter.biome()
                )
        );
    }
}
