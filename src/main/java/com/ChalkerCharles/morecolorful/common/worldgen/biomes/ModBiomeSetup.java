package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldBiomes;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldRegion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terrablender.api.Regions;

public class ModBiomeSetup {
    public static void setupTerraBlender() {
        Regions.register(new ModOverworldRegion(10));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<ConfiguredWorldCarver<?>> carver = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);

        context.register(ModBiomes.CRABAPPLE_GARDEN, ModOverworldBiomes.crabappleGarden(placedFeature, carver));
        context.register(ModBiomes.WHITE_CHERRY_GROVE, ModOverworldBiomes.whiteCherryGrove(placedFeature, carver));
        context.register(ModBiomes.AUTUMN_BIRCH_FOREST, ModOverworldBiomes.autumnBirchForest(placedFeature, carver));
        context.register(ModBiomes.GOLDEN_GROVE, ModOverworldBiomes.goldenGrove(placedFeature, carver));
        context.register(ModBiomes.MAPLE_FOREST, ModOverworldBiomes.mapleForest(placedFeature, carver));
        context.register(ModBiomes.SUNSET_VALLEY, ModOverworldBiomes.sunsetValley(placedFeature, carver));
    }
}
