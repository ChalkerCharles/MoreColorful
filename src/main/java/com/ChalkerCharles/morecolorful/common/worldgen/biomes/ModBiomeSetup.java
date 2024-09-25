package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terrablender.api.Regions;

public class ModBiomeSetup {
    public static void setupTerraBlender() {
        Regions.register(new ModOverworldRegion(8));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<ConfiguredWorldCarver<?>> carver = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);

        context.register(ModBiomes.CRABAPPLE_GARDEN, ModOverworldBiomes.crabappleGarden(placedFeature, carver));
    }
}
