package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldBiomes;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldRegion;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;

public class ModBiomeSetup {
    public static void registerRegions() {
        Regions.register(new ModOverworldRegion(Config.OVERWORLD_REGION_WEIGHT.getAsInt()));
    }

    public static void registerSurfaceRules() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MoreColorful.MODID, ModSurfaceRuleData.overworld());
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
        context.register(ModBiomes.FROST_GROVE, ModOverworldBiomes.frostGrove(placedFeature, carver));
        context.register(ModBiomes.DAWN_REDWOOD_SWAMP, ModOverworldBiomes.dawnRedwoodSwamp(placedFeature, carver));
        context.register(ModBiomes.LAVENDER_FIELDS, ModOverworldBiomes.lavenderFields(placedFeature, carver));
        context.register(ModBiomes.JACARANDA_GROVE, ModOverworldBiomes.jacarandaGrove(placedFeature, carver));
        context.register(ModBiomes.MARSH, ModOverworldBiomes.marsh(placedFeature, carver));
        context.register(ModBiomes.AZURE_FIELDS, ModOverworldBiomes.azureFields(placedFeature, carver));
        context.register(ModBiomes.WILLOW_BAYOU, ModOverworldBiomes.willowBayou(placedFeature, carver));
        context.register(ModBiomes.ICE_MARSH, ModOverworldBiomes.iceMarsh(placedFeature, carver));
        context.register(ModBiomes.RAPESEED_FIELDS, ModOverworldBiomes.rapeseedFields(placedFeature, carver));
    }
}
