package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld.ModOverworldBiomes;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

@SuppressWarnings("unused")
public class ModBiomes {
    // ********** Overworld ********** //
    // Forests
    public static final ResourceKey<Biome> SNOWY_FIR_FOREST = register("snowy_fir_forest");
    public static final ResourceKey<Biome> FIR_FOREST = register("fir_forest");
    public static final ResourceKey<Biome> MAPLE_FOREST = register("maple_forest");
    public static final ResourceKey<Biome> SUNSET_VALLEY = register("sunset_valley");
    public static final ResourceKey<Biome> AUTUMN_BIRCH_FOREST = register("autumn_birch_forest");
    public static final ResourceKey<Biome> GOLDEN_GROVE = register("golden_grove");
    public static final ResourceKey<Biome> MIXED_FOREST = register("mixed_forest");
    public static final ResourceKey<Biome> SPRING_VALLEY = register("spring_valley");
    public static final ResourceKey<Biome> POLLUTED_FOREST = register("polluted_forest");
    public static final ResourceKey<Biome> MAGIC_FOREST = register("magic_forest");
    public static final ResourceKey<Biome> SHADOW_FOREST = register("shadow_forest");
    public static final ResourceKey<Biome> CRABAPPLE_GARDEN = register("crabapple_garden");
    public static final ResourceKey<Biome> JACARANDA_GROVE = register("jacaranda_grove");
    public static final ResourceKey<Biome> OLD_GROWTH_FOREST = register("old_growth_forest");
    public static final ResourceKey<Biome> MONSOON_FOREST = register("monsoon_forest");
    public static final ResourceKey<Biome> RAINFOREST = register("rainforest");

    // Plains
    public static final ResourceKey<Biome> TUNDRA = register("tundra");
    public static final ResourceKey<Biome> GOLDEN_PASTURE = register("golden_pasture");
    public static final ResourceKey<Biome> OMINOUS_LAND = register("ominous_land");
    public static final ResourceKey<Biome> RAPESEED_FIELDS = register("rapeseed_fields");
    public static final ResourceKey<Biome> AZURE_FIELDS = register("azure_fields");
    public static final ResourceKey<Biome> LAVENDER_FIELDS = register("lavender_fields");
    public static final ResourceKey<Biome> SHRUBLAND = register("shrubland");
    public static final ResourceKey<Biome> WET_SAVANNA = register("wet_savanna");
    public static final ResourceKey<Biome> BAOBAB_SAVANNA = register("baobab_savanna");

    // Mountains
    public static final ResourceKey<Biome> FROST_GROVE = register("frost_grove");
    public static final ResourceKey<Biome> WINDSWEPT_PEAKS = register("windswept_peaks");
    public static final ResourceKey<Biome> MONTANE_SHRUBLAND = register("montane_shrubland");
    public static final ResourceKey<Biome> WHITE_CHERRY_GROVE = register("white_cherry_grove");

    // Swamps & Wetlands
    public static final ResourceKey<Biome> ICE_MARSH = register("ice_marsh");
    public static final ResourceKey<Biome> DAWN_REDWOOD_SWAMP = register("dawn_redwood_swamp");
    public static final ResourceKey<Biome> SACRED_SPRING = register("sacred_spring");
    public static final ResourceKey<Biome> MARSH = register("marsh");
    public static final ResourceKey<Biome> SALT_LAKE = register("salt_lake");
    public static final ResourceKey<Biome> WILLOW_BAYOU = register("willow_bayou");
    public static final ResourceKey<Biome> FLOODED_FOREST = register("flooded_forest");

    // Deserts
    public static final ResourceKey<Biome> COLD_DESERT = register("cold_desert");
    public static final ResourceKey<Biome> OASIS = register("oasis");
    public static final ResourceKey<Biome> LUSH_DESERT = register("lush_desert");
    public static final ResourceKey<Biome> WASTELAND = register("wasteland");

    // Oceans, Coasts & Islands
    public static final ResourceKey<Biome> GLOWING_DEPTH = register("glowing_depth");
    public static final ResourceKey<Biome> GRAVELLY_BEACH = register("gravelly_beach");
    public static final ResourceKey<Biome> LUSH_BEACH = register("lush_beach");
    public static final ResourceKey<Biome> CORAL_ISLAND = register("coral_island");
    public static final ResourceKey<Biome> PREHISTORIC_ISLAND = register("prehistoric_island");
    public static final ResourceKey<Biome> VOLCANIC_ISLAND = register("volcanic_island");

    // Caves
    public static final ResourceKey<Biome> FROZEN_CAVE = register("frozen_cave");
    public static final ResourceKey<Biome> CORRUPTED_CHASM = register("corrupted_chasm");
    public static final ResourceKey<Biome> CRYSTAL_GROTTO = register("crystal_grotto");
    public static final ResourceKey<Biome> SPIDER_NEST = register("spider_nest");

    // ********** Nether ********** //

    // ********** End ********** //

    private static ResourceKey<Biome> register(String pKey) {
        return ResourceKey.create(Registries.BIOME, MoreColorful.location(pKey));
    }

    public static void bootstrap(BootstrapContext<Biome> context) {
        HolderGetter<ConfiguredWorldCarver<?>> carver = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> placedFeature = context.lookup(Registries.PLACED_FEATURE);

        context.register(CRABAPPLE_GARDEN, ModOverworldBiomes.crabappleGarden(placedFeature, carver));
        context.register(WHITE_CHERRY_GROVE, ModOverworldBiomes.whiteCherryGrove(placedFeature, carver));
        context.register(AUTUMN_BIRCH_FOREST, ModOverworldBiomes.autumnBirchForest(placedFeature, carver));
        context.register(GOLDEN_GROVE, ModOverworldBiomes.goldenGrove(placedFeature, carver));
        context.register(MAPLE_FOREST, ModOverworldBiomes.mapleForest(placedFeature, carver));
        context.register(SUNSET_VALLEY, ModOverworldBiomes.sunsetValley(placedFeature, carver));
        context.register(FROST_GROVE, ModOverworldBiomes.frostGrove(placedFeature, carver));
        context.register(DAWN_REDWOOD_SWAMP, ModOverworldBiomes.dawnRedwoodSwamp(placedFeature, carver));
        context.register(LAVENDER_FIELDS, ModOverworldBiomes.lavenderFields(placedFeature, carver));
        context.register(JACARANDA_GROVE, ModOverworldBiomes.jacarandaGrove(placedFeature, carver));
        context.register(MARSH, ModOverworldBiomes.marsh(placedFeature, carver));
        context.register(AZURE_FIELDS, ModOverworldBiomes.azureFields(placedFeature, carver));
        context.register(WILLOW_BAYOU, ModOverworldBiomes.willowBayou(placedFeature, carver));
        context.register(ICE_MARSH, ModOverworldBiomes.iceMarsh(placedFeature, carver));
        context.register(RAPESEED_FIELDS, ModOverworldBiomes.rapeseedFields(placedFeature, carver));
    }
}
