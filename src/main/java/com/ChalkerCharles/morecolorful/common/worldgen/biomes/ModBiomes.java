package com.ChalkerCharles.morecolorful.common.worldgen.biomes;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class ModBiomes {
    // ********** Overworld ********** //
    // Forests
    public static final ResourceKey<Biome> SNOWY_FIR_FOREST = register("snowy_fir_forest");
    public static final ResourceKey<Biome> FIR_FOREST = register("fir_forest");
    public static final ResourceKey<Biome> MAPLE_FOREST = register("maple_forest");
    public static final ResourceKey<Biome> SUNSET_VALLEY = register("sunset_valley");
    public static final ResourceKey<Biome> AUTUMN_BIRCH_FOREST = register("autumn_birch_forest");
    public static final ResourceKey<Biome> GOLDEN_GROVE = register("golden_grove");
    public static final ResourceKey<Biome> SPRING_VALLEY = register("spring_valley");
    public static final ResourceKey<Biome> POLLUTED_FOREST = register("polluted_forest");
    public static final ResourceKey<Biome> MAGIC_FOREST = register("magic_forest");
    public static final ResourceKey<Biome> SHADOW_FOREST = register("shadow_forest");
    public static final ResourceKey<Biome> CRABAPPLE_GARDEN = register("crabapple_garden");
    public static final ResourceKey<Biome> JACARANDA_GROVE = register("jacaranda_grove");
    public static final ResourceKey<Biome> OLD_GROWTH_FOREST = register("old_growth_forest");
    public static final ResourceKey<Biome> RAINFOREST = register("rainforest");

    // Plains
    public static final ResourceKey<Biome> TUNDRA = register("tundra");
    public static final ResourceKey<Biome> GOLDEN_PASTURE = register("golden_pasture");
    public static final ResourceKey<Biome> CURSED_LAND = register("cursed_land");
    public static final ResourceKey<Biome> COLE_FLOWER_FIELDS = register("cole_flower_fields");
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
    public static final ResourceKey<Biome> LUSH_SWAMP = register("lush_swamp");

    // Deserts
    public static final ResourceKey<Biome> COLD_DESERT = register("cold_desert");
    public static final ResourceKey<Biome> OASIS = register("oasis");
    public static final ResourceKey<Biome> LUSH_DESERT = register("lush_desert");
    public static final ResourceKey<Biome> WASTELAND = register("wasteland");

    // Oceans, Coasts & Islands
    public static final ResourceKey<Biome> GLOWING_ABYSS = register("glowing_abyss");
    public static final ResourceKey<Biome> GRAVELLY_BEACH = register("gravelly_beach");
    public static final ResourceKey<Biome> LUSH_BEACH = register("lush_beach");
    public static final ResourceKey<Biome> CORAL_ISLAND = register("coral_island");
    public static final ResourceKey<Biome> PREHISTORIC_ISLAND = register("prehistoric_island");
    public static final ResourceKey<Biome> VOLCANIC_ISLAND = register("volcanic_island");

    // Caves
    public static final ResourceKey<Biome> FROZEN_CAVE = register("frozen_cave");
    public static final ResourceKey<Biome> CORRUPTED_CHASM = register("corrupted_chasm");
    public static final ResourceKey<Biome> CRYSTAL_CAVE = register("crystal_cave");
    public static final ResourceKey<Biome> SPIDER_NEST = register("spider_nest");

    // ********** Nether ********** //

    // ********** End ********** //

    private static ResourceKey<Biome> register(String pKey) {
        return ResourceKey.create(Registries.BIOME, ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, pKey));
    }
}
