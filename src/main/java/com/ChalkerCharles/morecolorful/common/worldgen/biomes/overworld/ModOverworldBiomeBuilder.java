package com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld;

import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomes;
import com.ChalkerCharles.morecolorful.util.BiomeUtils;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.ParameterUtils;

import java.util.function.Consumer;

@SuppressWarnings({"unchecked", "SameParameterValue", "unused"})
public class ModOverworldBiomeBuilder {
    protected final Climate.Parameter[] TEMPERATURES = new Climate.Parameter[]{
            ParameterUtils.Temperature.ICY.parameter(),
            ParameterUtils.Temperature.COOL.parameter(),
            ParameterUtils.Temperature.NEUTRAL.parameter(),
            ParameterUtils.Temperature.WARM.parameter(),
            ParameterUtils.Temperature.HOT.parameter()
    };
    protected final Climate.Parameter[] HUMIDITIES = new Climate.Parameter[]{
            ParameterUtils.Humidity.ARID.parameter(),
            ParameterUtils.Humidity.DRY.parameter(),
            ParameterUtils.Humidity.NEUTRAL.parameter(),
            ParameterUtils.Humidity.WET.parameter(),
            ParameterUtils.Humidity.HUMID.parameter()
    };
    protected final Climate.Parameter[] EROSIONS = new Climate.Parameter[]{
            ParameterUtils.Erosion.EROSION_0.parameter(),
            ParameterUtils.Erosion.EROSION_1.parameter(),
            ParameterUtils.Erosion.EROSION_2.parameter(),
            ParameterUtils.Erosion.EROSION_3.parameter(),
            ParameterUtils.Erosion.EROSION_4.parameter(),
            ParameterUtils.Erosion.EROSION_5.parameter(),
            ParameterUtils.Erosion.EROSION_6.parameter(),
    };
    protected final Climate.Parameter FULL_RANGE = Climate.Parameter.span(-1.0F, 1.0F);
    protected final Climate.Parameter FROZEN_RANGE = ParameterUtils.Temperature.FROZEN.parameter();
    protected final Climate.Parameter UNFROZEN_RANGE = ParameterUtils.Temperature.UNFROZEN.parameter();
    protected final Climate.Parameter mushroomFieldsContinentalness = ParameterUtils.Continentalness.MUSHROOM_FIELDS.parameter();
    protected final Climate.Parameter deepOceanContinentalness = ParameterUtils.Continentalness.DEEP_OCEAN.parameter();
    protected final Climate.Parameter oceanContinentalness = ParameterUtils.Continentalness.OCEAN.parameter();
    protected final Climate.Parameter coastContinentalness = ParameterUtils.Continentalness.COAST.parameter();
    protected final Climate.Parameter inlandContinentalness = ParameterUtils.Continentalness.INLAND.parameter();
    protected final Climate.Parameter nearInlandContinentalness = ParameterUtils.Continentalness.NEAR_INLAND.parameter();
    protected final Climate.Parameter midInlandContinentalness = ParameterUtils.Continentalness.MID_INLAND.parameter();
    protected final Climate.Parameter farInlandContinentalness = ParameterUtils.Continentalness.FAR_INLAND.parameter();

    // Vanilla Copy
    private final ResourceKey<Biome>[][] OCEANS = new ResourceKey[][]{
            {Biomes.DEEP_FROZEN_OCEAN,  Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_OCEAN,  Biomes.DEEP_LUKEWARM_OCEAN, Biomes.WARM_OCEAN},
            {Biomes.FROZEN_OCEAN,       Biomes.COLD_OCEAN,      Biomes.OCEAN,       Biomes.LUKEWARM_OCEAN,      Biomes.WARM_OCEAN}
    };
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS,   Biomes.SNOWY_PLAINS,    Biomes.SNOWY_PLAINS,    Biomes.SNOWY_TAIGA,     Biomes.TAIGA},
            {Biomes.PLAINS,         Biomes.PLAINS,          Biomes.FOREST,          Biomes.TAIGA,           Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.FLOWER_FOREST,  Biomes.PLAINS,          Biomes.FOREST,          Biomes.BIRCH_FOREST,    Biomes.DARK_FOREST},
            {Biomes.SAVANNA,        Biomes.SAVANNA,         Biomes.FOREST,          Biomes.JUNGLE,          Biomes.JUNGLE},
            {Biomes.DESERT,         Biomes.DESERT,          Biomes.DESERT,          Biomes.DESERT,          Biomes.DESERT}
    };
    private final ResourceKey<Biome>[][] MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
            {Biomes.ICE_SPIKES,         null, Biomes.SNOWY_TAIGA, null,                           null},
            {null,                      null, null,               null,                           Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.SUNFLOWER_PLAINS,   null, null,               Biomes.OLD_GROWTH_BIRCH_FOREST, null},
            {null,                      null, Biomes.PLAINS,      Biomes.SPARSE_JUNGLE,           Biomes.BAMBOO_JUNGLE},
            {null,                      null, null,               null,                           null}
    };
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES = new ResourceKey[][]{
            {Biomes.SNOWY_PLAINS,       Biomes.SNOWY_PLAINS,    Biomes.SNOWY_PLAINS,    Biomes.SNOWY_TAIGA,     Biomes.SNOWY_TAIGA},
            {Biomes.MEADOW,             Biomes.MEADOW,          Biomes.FOREST,          Biomes.TAIGA,           Biomes.OLD_GROWTH_SPRUCE_TAIGA},
            {Biomes.MEADOW,             Biomes.MEADOW,          Biomes.MEADOW,          Biomes.MEADOW,          Biomes.DARK_FOREST},
            {Biomes.SAVANNA_PLATEAU,    Biomes.SAVANNA_PLATEAU, Biomes.FOREST,          Biomes.FOREST,          Biomes.JUNGLE},
            {Biomes.BADLANDS,           Biomes.BADLANDS,        Biomes.BADLANDS,        Biomes.WOODED_BADLANDS, Biomes.WOODED_BADLANDS}
    };
    private final ResourceKey<Biome>[][] PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
            {Biomes.ICE_SPIKES,         null,                   null,           null,                   null},
            {Biomes.CHERRY_GROVE,       null,                   Biomes.MEADOW,  Biomes.MEADOW,          Biomes.OLD_GROWTH_PINE_TAIGA},
            {Biomes.CHERRY_GROVE,       Biomes.CHERRY_GROVE,    Biomes.FOREST,  Biomes.BIRCH_FOREST,    null},
            {null,                      null,                   null,           null,                   null},
            {Biomes.ERODED_BADLANDS,    Biomes.ERODED_BADLANDS, null,           null,                   null}
    };
    private final ResourceKey<Biome>[][] SHATTERED_BIOMES = new ResourceKey[][]{
            {Biomes.WINDSWEPT_GRAVELLY_HILLS,   Biomes.WINDSWEPT_GRAVELLY_HILLS,    Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST,    Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_GRAVELLY_HILLS,   Biomes.WINDSWEPT_GRAVELLY_HILLS,    Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST,    Biomes.WINDSWEPT_FOREST},
            {Biomes.WINDSWEPT_HILLS,            Biomes.WINDSWEPT_HILLS,             Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST,    Biomes.WINDSWEPT_FOREST},
            {null,                              null,                               null,                   null,                       null},
            {null,                              null,                               null,                   null,                       null}
    };
    private final ResourceKey<Biome>[][] SWAMP_BIOMES = new ResourceKey[][]{
            {Biomes.FROZEN_RIVER,   Biomes.FROZEN_RIVER,    Biomes.FROZEN_RIVER,    Biomes.FROZEN_RIVER,    Biomes.FROZEN_RIVER},
            {Biomes.SWAMP,          Biomes.SWAMP,           Biomes.SWAMP,           Biomes.SWAMP,           Biomes.SWAMP},
            {Biomes.SWAMP,          Biomes.SWAMP,           Biomes.SWAMP,           Biomes.SWAMP,           Biomes.SWAMP},
            {Biomes.MANGROVE_SWAMP, Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP},
            {Biomes.MANGROVE_SWAMP, Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP,  Biomes.MANGROVE_SWAMP}
    };

    protected final ResourceKey<Biome>[][] BEACH_BIOMES = new ResourceKey[][]{
            {Biomes.SNOWY_BEACH,    Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH},
            {Biomes.BEACH,          Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH},
            {Biomes.BEACH,          Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH},
            {Biomes.BEACH,          Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH,       Biomes.BEACH},
            {Biomes.DESERT,         Biomes.DESERT,      Biomes.DESERT,      Biomes.DESERT,      Biomes.DESERT}
    };

    // MoreColorful Biomes
//    protected final ResourceKey<Biome>[][] MOD_MIDDLE_BIOMES0 = new ResourceKey[][]{
//            {ModBiomes.COLD_DESERT, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.SNOWY_FIR_FOREST, ModBiomes.FIR_FOREST},
//            {ModBiomes.GOLDEN_PASTURE, ModBiomes.AUTUMN_BIRCH_FOREST, ModBiomes.AUTUMN_BIRCH_FOREST, ModBiomes.MAPLE_FOREST, ModBiomes.FIR_FOREST},
//            {ModBiomes.COLE_FLOWER_FIELDS, ModBiomes.CRABAPPLE_GARDEN, ModBiomes.CRABAPPLE_GARDEN, ModBiomes.SPRING_VALLEY, null},
//            {ModBiomes.BAOBAB_SAVANNA, ModBiomes.LAVENDER_FIELDS, ModBiomes.JACARANDA_GROVE, null, ModBiomes.WET_SAVANNA},
//            {null, null, ModBiomes.LUSH_DESERT, ModBiomes.LUSH_DESERT, ModBiomes.OASIS}
//    };
    protected final ResourceKey<Biome>[][] MOD_MIDDLE_BIOMES = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, ModBiomes.GOLDEN_GROVE, ModBiomes.AUTUMN_BIRCH_FOREST, ModBiomes.MAPLE_FOREST, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };
//    protected final ResourceKey<Biome>[][] MOD_MIDDLE_BIOMES_VARIANT0 = new ResourceKey[][]{
//            {null, null, ModBiomes.SNOWY_FIR_FOREST, null, null},
//            {null, ModBiomes.GOLDEN_PASTURE, ModBiomes.MAPLE_FOREST, ModBiomes.SUNSET_VALLEY, null},
//            {null, ModBiomes.COLE_FLOWER_FIELDS, ModBiomes.SPRING_VALLEY, null, ModBiomes.OLD_GROWTH_FOREST},
//            {ModBiomes.COLE_FLOWER_FIELDS, ModBiomes.LAVENDER_FIELDS, null, ModBiomes.JACARANDA_GROVE, null},
//            {null, null, null, ModBiomes.OASIS, null}
//    };
    protected final ResourceKey<Biome>[][] MOD_MIDDLE_BIOMES_VARIANT = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, null, null, ModBiomes.SUNSET_VALLEY, null},
            {null, ModBiomes.CRABAPPLE_GARDEN, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };
//    protected final ResourceKey<Biome>[][] MOD_PLATEAU_BIOMES0 = new ResourceKey[][]{
//            {ModBiomes.COLD_DESERT, ModBiomes.TUNDRA, ModBiomes.TUNDRA, ModBiomes.SNOWY_FIR_FOREST, ModBiomes.SNOWY_FIR_FOREST},
//            {null,             ModBiomes.MAPLE_FOREST,          ModBiomes.MAPLE_FOREST,          ModBiomes.FIR_FOREST,           ModBiomes.FIR_FOREST},
//            {null, null, null, null, ModBiomes.MONTANE_SHRUBLAND},
//            {null, null, null, null, null},
//            {null, null, null, null, null}
//    };
    protected final ResourceKey<Biome>[][] MOD_PLATEAU_BIOMES = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };
//    protected final ResourceKey<Biome>[][] MOD_PLATEAU_BIOMES_VARIANT0 = new ResourceKey[][]{
//            {null, null, null, null, null},
//            {ModBiomes.WHITE_CHERRY_GROVE, null, null, ModBiomes.MAPLE_FOREST, null},
//            {ModBiomes.WHITE_CHERRY_GROVE, ModBiomes.WHITE_CHERRY_GROVE, null, ModBiomes.MONTANE_SHRUBLAND, null},
//            {null, null, null, null, null},
//            {null, null, null, null, null}
//    };
    protected final ResourceKey<Biome>[][] MOD_PLATEAU_BIOMES_VARIANT = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {ModBiomes.WHITE_CHERRY_GROVE, ModBiomes.WHITE_CHERRY_GROVE, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };
//    protected final ResourceKey<Biome>[][] MOD_SWAMP_BIOMES0 = new ResourceKey[][]{
//            {ModBiomes.ICE_MARSH,           ModBiomes.ICE_MARSH,            ModBiomes.ICE_MARSH,            ModBiomes.ICE_MARSH,            ModBiomes.ICE_MARSH},
//            {ModBiomes.DAWN_REDWOOD_SWAMP,  ModBiomes.DAWN_REDWOOD_SWAMP,   ModBiomes.DAWN_REDWOOD_SWAMP,   ModBiomes.DAWN_REDWOOD_SWAMP,   ModBiomes.DAWN_REDWOOD_SWAMP},
//            {ModBiomes.MARSH,               ModBiomes.MARSH,                ModBiomes.MARSH,                ModBiomes.MARSH,                ModBiomes.MARSH},
//            {ModBiomes.WILLOW_BAYOU,        ModBiomes.WILLOW_BAYOU,         ModBiomes.WILLOW_BAYOU,         ModBiomes.LUSH_SWAMP,           ModBiomes.LUSH_SWAMP},
//            {ModBiomes.WILLOW_BAYOU,        ModBiomes.WILLOW_BAYOU,         ModBiomes.LUSH_SWAMP,           ModBiomes.LUSH_SWAMP,           ModBiomes.LUSH_SWAMP}
//    };
    protected final ResourceKey<Biome>[][] MOD_SWAMP_BIOMES = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };
//    protected final ResourceKey<Biome>[][] MOD_BEACH_BIOMES0 = new ResourceKey[][]{
//            {Biomes.SNOWY_BEACH,    Biomes.SNOWY_BEACH, Biomes.SNOWY_BEACH,    Biomes.SNOWY_BEACH,     Biomes.SNOWY_BEACH},
//            {Biomes.BEACH,          Biomes.BEACH,       Biomes.BEACH,          Biomes.BEACH,           Biomes.BEACH},
//            {Biomes.BEACH,          Biomes.BEACH,       ModBiomes.LUSH_BEACH,  ModBiomes.LUSH_BEACH,   ModBiomes.LUSH_BEACH},
//            {Biomes.BEACH,          Biomes.BEACH,       Biomes.BEACH,          ModBiomes.LUSH_BEACH,   ModBiomes.LUSH_BEACH},
//            {Biomes.DESERT,         Biomes.DESERT,      ModBiomes.LUSH_DESERT, ModBiomes.LUSH_DESERT,  ModBiomes.LUSH_DESERT}
//    };
    protected final ResourceKey<Biome>[][] MOD_BEACH_BIOMES = new ResourceKey[][]{
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null},
            {null, null, null, null, null}
    };

    protected void addBiomes(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pKey) {
        this.addOffCoastBiomes(biomeRegistry, pKey);
        this.addInlandBiomes(biomeRegistry, pKey);
        this.addUndergroundBiomes(biomeRegistry, pKey);
    }

    protected void addOffCoastBiomes(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addSurfaceBiome(
                mapper, this.FULL_RANGE, this.FULL_RANGE, this.mushroomFieldsContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.MUSHROOM_FIELDS
        );

        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            this.addSurfaceBiome(
                    mapper, temperature, this.FULL_RANGE, this.deepOceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[0][i]
            );
            this.addSurfaceBiome(
                    mapper, temperature, this.FULL_RANGE, this.oceanContinentalness, this.FULL_RANGE, this.FULL_RANGE, 0.0F, this.OCEANS[1][i]
            );
        }
    }

    protected void addInlandBiomes(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper) {
        this.addMidSlice(biomeRegistry, mapper, Climate.Parameter.span(-1.0F, -0.93333334F));
        this.addHighSlice(biomeRegistry, mapper, Climate.Parameter.span(-0.93333334F, -0.7666667F));
        this.addPeaks(biomeRegistry, mapper, Climate.Parameter.span(-0.7666667F, -0.56666666F));
        this.addHighSlice(biomeRegistry, mapper, Climate.Parameter.span(-0.56666666F, -0.4F));
        this.addMidSlice(biomeRegistry, mapper, Climate.Parameter.span(-0.4F, -0.26666668F));
        this.addLowSlice(biomeRegistry, mapper, Climate.Parameter.span(-0.26666668F, -0.05F));
        this.addValleys(biomeRegistry,mapper, Climate.Parameter.span(-0.05F, 0.05F));
        this.addLowSlice(biomeRegistry, mapper, Climate.Parameter.span(0.05F, 0.26666668F));
        this.addMidSlice(biomeRegistry, mapper, Climate.Parameter.span(0.26666668F, 0.4F));
        this.addHighSlice(biomeRegistry, mapper, Climate.Parameter.span(0.4F, 0.56666666F));
        this.addPeaks(biomeRegistry, mapper, Climate.Parameter.span(0.56666666F, 0.7666667F));
        this.addHighSlice(biomeRegistry, mapper, Climate.Parameter.span(0.7666667F, 0.93333334F));
        this.addMidSlice(biomeRegistry, mapper, Climate.Parameter.span(0.93333334F, 1.0F));
    }

    protected void addPeaks(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter weirdness) {
        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (int j = 0; j < this.HUMIDITIES.length; j++) {
                Climate.Parameter humidity = this.HUMIDITIES[j];

                ResourceKey<Biome> modMiddleBiome = this.pickModMiddleBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickModMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modPlateauBiome = this.pickModPlateauBiome(biomeRegistry, i, j, weirdness);

                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, weirdness);
                ResourceKey<Biome> windsweptSavannaBiome = this.maybePickWindsweptSavannaBiome(i, j, weirdness, shatteredBiome);
                ResourceKey<Biome> peakBiome = this.pickPeakBiome(i, j, weirdness);

                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[0], weirdness, 0.0F, peakBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.EROSIONS[1], weirdness, 0.0F, modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[1], weirdness, 0.0F, peakBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.EROSIONS[2], this.EROSIONS[3]), weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[2], weirdness, 0.0F, modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.midInlandContinentalness, this.EROSIONS[3], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.farInlandContinentalness, this.EROSIONS[3], weirdness, 0.0F, modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[4], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, windsweptSavannaBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, shatteredBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modMiddleBiome
                );
            }
        }
    }

    protected void addHighSlice(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter weirdness) {
        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (int j = 0; j < this.HUMIDITIES.length; j++) {
                Climate.Parameter humidity = this.HUMIDITIES[j];

                ResourceKey<Biome> modMiddleBiome = this.pickModMiddleBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickModMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modPlateauBiome = this.pickModPlateauBiome(biomeRegistry, i, j, weirdness);

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, weirdness);
                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, weirdness);
                ResourceKey<Biome> windsweptSavannaBiome = this.maybePickWindsweptSavannaBiome(i, j, weirdness, middleBiome);
                ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(i, j, weirdness);
                ResourceKey<Biome> peakBiome = this.pickPeakBiome(i, j, weirdness);

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, this.EROSIONS[0], weirdness, 0.0F, slopeBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[0], weirdness, 0.0F, peakBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, this.EROSIONS[1], weirdness, 0.0F, modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[1], weirdness, 0.0F, slopeBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), Climate.Parameter.span(this.EROSIONS[2], this.EROSIONS[3]), weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[2], weirdness, 0.0F, modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.midInlandContinentalness, this.EROSIONS[3], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.farInlandContinentalness, this.EROSIONS[3], weirdness, 0.0F, modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[4], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, windsweptSavannaBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, shatteredBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modMiddleBiome
                );
            }
        }
    }

    protected void addMidSlice(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter weirdness) {
        this.addSurfaceBiome(
                mapper, this.FULL_RANGE, this.FULL_RANGE, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[2]), weirdness, 0.0F, Biomes.STONY_SHORE
        );
        this.addSurfaceBiome(
                mapper, Climate.Parameter.span(this.TEMPERATURES[1], this.TEMPERATURES[2]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, Biomes.SWAMP
        );
        this.addSurfaceBiome(
                mapper, Climate.Parameter.span(this.TEMPERATURES[3], this.TEMPERATURES[4]), this.FULL_RANGE, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, Biomes.MANGROVE_SWAMP
        );

        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (int j = 0; j < this.HUMIDITIES.length; j++) {
                Climate.Parameter humidity = this.HUMIDITIES[j];

                ResourceKey<Biome> modMiddleBiome = this.pickModMiddleBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickModMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modPlateauBiome = this.pickModPlateauBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modSwampBiome  = this.pickModSwampBiome(biomeRegistry, i, j, weirdness);

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, weirdness);
                ResourceKey<Biome> beachBiome = this.pickBeachBiome(biomeRegistry, i, j);
                ResourceKey<Biome> shatteredCoastBiome = this.pickShatteredCoastBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> shatteredBiome = this.pickShatteredBiome(i, j, weirdness);
                ResourceKey<Biome> windsweptSavannaBiome = this.maybePickWindsweptSavannaBiome(i, j, weirdness, middleBiome);
                ResourceKey<Biome> slopeBiome = this.pickSlopeBiome(i, j, weirdness);

                this.addSurfaceBiome(mapper, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[2]), weirdness, 0.0F, Biomes.STONY_SHORE);

                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[0], weirdness, 0.0F, slopeBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.midInlandContinentalness), this.EROSIONS[1], weirdness, 0.0F, modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.farInlandContinentalness, this.EROSIONS[1], weirdness, 0.0F,
                        i == 0 ? slopeBiome : modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, this.EROSIONS[2], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(mapper, temperature, humidity, this.midInlandContinentalness, this.EROSIONS[2], weirdness, 0.0F, modMiddleBiome);
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.farInlandContinentalness, this.EROSIONS[2], weirdness, 0.0F, modPlateauBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.nearInlandContinentalness), this.EROSIONS[3], weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[3], weirdness, 0.0F, modMiddleBiome);

                if (weirdness.max() < 0L) {
                    this.addSurfaceBiome(
                            mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[4], weirdness, 0.0F, modMiddleBiome
                    );

                    this.addSurfaceBiome(
                            mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[4], weirdness, 0.0F, beachBiome
                    );
                } else {
                    this.addSurfaceBiome(
                            mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), this.EROSIONS[4], weirdness, 0.0F, modMiddleBiome
                    );
                }

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[5], weirdness, 0.0F, shatteredCoastBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, this.EROSIONS[5], weirdness, 0.0F, windsweptSavannaBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, shatteredBiome
                );

                if (weirdness.max() < 0L) {
                    this.addSurfaceBiome(
                            mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[6], weirdness, 0.0F, beachBiome
                    );
                } else {
                    this.addSurfaceBiome(
                            mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[6], weirdness, 0.0F, modMiddleBiome
                    );
                }
                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modSwampBiome);
            }
        }
    }

    protected void addLowSlice(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter weirdness) {
        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (int j = 0; j < this.HUMIDITIES.length; j++) {
                Climate.Parameter humidity = this.HUMIDITIES[j];

                ResourceKey<Biome> modMiddleBiome = this.pickModMiddleBiome(biomeRegistry ,i, j, weirdness);
                ResourceKey<Biome> modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold = this.pickModMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modSwampBiome  = this.pickModSwampBiome(biomeRegistry, i, j, weirdness);

                ResourceKey<Biome> middleBiome = this.pickMiddleBiome(i, j, weirdness);
                ResourceKey<Biome> beachBiome = this.pickBeachBiome(biomeRegistry, i, j);
                ResourceKey<Biome> shatteredCoastBiome = this.pickShatteredCoastBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> windsweptSavannaBiome = this.maybePickWindsweptSavannaBiome(i, j, weirdness, middleBiome);

                this.addSurfaceBiome(mapper, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[2]), weirdness, 0.0F, Biomes.STONY_SHORE);
                this.addSurfaceBiome(mapper, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, modMiddleBiome);
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, modMiddleBiomeOrBadlandsIfHotOrSlopeIfCold
                );

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.EROSIONS[2], this.EROSIONS[3]), weirdness, 0.0F, modMiddleBiome
                );
                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.EROSIONS[2], this.EROSIONS[3]), weirdness, 0.0F, modMiddleBiome);

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[3], this.EROSIONS[4]), weirdness, 0.0F, beachBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[4], weirdness, 0.0F, modMiddleBiome
                );

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[5], weirdness, 0.0F, shatteredCoastBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.nearInlandContinentalness, this.EROSIONS[5], weirdness, 0.0F, windsweptSavannaBiome
                );
                this.addSurfaceBiome(
                        mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[5], weirdness, 0.0F, modMiddleBiome
                );

                this.addSurfaceBiome(
                        mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[6], weirdness, 0.0F, beachBiome
                );
                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modSwampBiome);

                if (i == 0) {
                    this.addSurfaceBiome(
                            mapper, temperature, humidity, Climate.Parameter.span(this.nearInlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modMiddleBiome
                    );
                }
            }
        }
    }

    protected void addValleys(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper, Climate.Parameter weirdness) {
        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (Climate.Parameter humidity : this.HUMIDITIES) {
                ResourceKey<Biome> riverBiome = this.pickRiverBiome(i);

                this.addSurfaceBiome(mapper, temperature, humidity, this.coastContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, weirdness.max() < 0L ? Biomes.STONY_SHORE : riverBiome);
                this.addSurfaceBiome(mapper, temperature, humidity, this.nearInlandContinentalness, Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, riverBiome);
                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.coastContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.EROSIONS[2], this.EROSIONS[5]), weirdness, 0.0F, riverBiome);
                this.addSurfaceBiome(mapper, temperature, humidity, this.coastContinentalness, this.EROSIONS[6], weirdness, 0.0F, riverBiome);
            }
        }

        for (int i = 0; i < this.TEMPERATURES.length; i++) {
            Climate.Parameter temperature = this.TEMPERATURES[i];

            for (int j = 0; j < this.HUMIDITIES.length; j++) {
                Climate.Parameter humidity = this.HUMIDITIES[j];
                ResourceKey<Biome> modMiddleBiome = this.pickModMiddleBiome(biomeRegistry, i, j, weirdness);
                ResourceKey<Biome> modSwampBiome  = this.pickModSwampBiome(biomeRegistry, i, j, weirdness);

                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.midInlandContinentalness, this.farInlandContinentalness), Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]), weirdness, 0.0F, modMiddleBiome);

                this.addSurfaceBiome(mapper, temperature, humidity, Climate.Parameter.span(this.inlandContinentalness, this.farInlandContinentalness), this.EROSIONS[6], weirdness, 0.0F, modSwampBiome);
            }
        }
    }

    protected void addUndergroundBiomes(Registry<Biome> biomeRegistry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> pConsume) {
        this.addUndergroundBiome(
                pConsume, this.FULL_RANGE, this.FULL_RANGE, Climate.Parameter.span(0.8F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.DRIPSTONE_CAVES
        );
        this.addUndergroundBiome(
                pConsume, this.FULL_RANGE, Climate.Parameter.span(0.7F, 1.0F), this.FULL_RANGE, this.FULL_RANGE, this.FULL_RANGE, 0.0F, Biomes.LUSH_CAVES
        );
        this.addBottomBiome(
                pConsume,
                this.FULL_RANGE,
                this.FULL_RANGE,
                this.FULL_RANGE,
                Climate.Parameter.span(this.EROSIONS[0], this.EROSIONS[1]),
                this.FULL_RANGE,
                0.0F,
                Biomes.DEEP_DARK
        );
    }

    protected ResourceKey<Biome> pickMiddleBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        if (weirdness.max() < 0L) {
            return this.MIDDLE_BIOMES[pTemperature][pHumidity];
        } else {
            ResourceKey<Biome> resourcekey = this.MIDDLE_BIOMES_VARIANT[pTemperature][pHumidity];
            return resourcekey == null ? this.MIDDLE_BIOMES[pTemperature][pHumidity] : resourcekey;
        }
    }

    protected ResourceKey<Biome> pickModMiddleBiome(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        ResourceKey<Biome> middleBiome = BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_MIDDLE_BIOMES[pTemperature][pHumidity], this.MIDDLE_BIOMES[pTemperature][pHumidity]);
        if (weirdness.max() < 0L) return middleBiome;
        else {
            return BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_MIDDLE_BIOMES_VARIANT[pTemperature][pHumidity], middleBiome);
        }
    }

    protected ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHot(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        return pTemperature == 4 ? this.pickBadlandsBiome(pHumidity, weirdness) : this.pickMiddleBiome(pTemperature, pHumidity, weirdness);
    }

    protected ResourceKey<Biome> pickMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        return pTemperature == 0 ? this.pickSlopeBiome(pTemperature, pHumidity, weirdness) : this.pickMiddleBiomeOrBadlandsIfHot(pTemperature, pHumidity, weirdness);
    }

    protected ResourceKey<Biome> pickModMiddleBiomeOrBadlandsIfHotOrSlopeIfCold(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        return pTemperature == 0 ? this.pickSlopeBiome(pTemperature, pHumidity, weirdness) : this.pickModMiddleBiome(biomeRegistry, pTemperature, pHumidity, weirdness);
    }

    protected ResourceKey<Biome> maybePickWindsweptSavannaBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness, ResourceKey<Biome> pKey) {
        return pTemperature > 1 && pHumidity < 4 && weirdness.max() >= 0L ? Biomes.WINDSWEPT_SAVANNA : pKey;
    }

    protected ResourceKey<Biome> pickShatteredCoastBiome(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        ResourceKey<Biome> resourcekey = weirdness.max() >= 0L
                ? this.pickMiddleBiome(pTemperature, pHumidity, weirdness)
                : this.pickBeachBiome(biomeRegistry, pTemperature, pHumidity);
        return this.maybePickWindsweptSavannaBiome(pTemperature, pHumidity, weirdness, resourcekey);
    }

    protected ResourceKey<Biome> pickBeachBiome(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity) {
        return BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_BEACH_BIOMES[pTemperature][pHumidity], this.BEACH_BIOMES[pTemperature][pHumidity]);
    }

    protected ResourceKey<Biome> pickBadlandsBiome(int pHumidity, Climate.Parameter weirdness) {
        if (pHumidity < 2) {
            return weirdness.max() < 0L ? Biomes.BADLANDS : Biomes.ERODED_BADLANDS;
        } else {
            return pHumidity < 3 ? Biomes.BADLANDS : Biomes.WOODED_BADLANDS;
        }
    }

    protected ResourceKey<Biome> pickPlateauBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        if (weirdness.max() >= 0L) {
            ResourceKey<Biome> resourcekey = this.PLATEAU_BIOMES_VARIANT[pTemperature][pHumidity];
            if (resourcekey != null) {
                return resourcekey;
            }
        }
        return this.PLATEAU_BIOMES[pTemperature][pHumidity];
    }

    protected ResourceKey<Biome> pickModPlateauBiome(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        if (weirdness.max() < 0L) {
            return BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_MIDDLE_BIOMES[pTemperature][pHumidity], this.PLATEAU_BIOMES[pTemperature][pHumidity]);
        } else {
            return BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_PLATEAU_BIOMES_VARIANT[pTemperature][pHumidity], this.MOD_PLATEAU_BIOMES[pTemperature][pHumidity], this.PLATEAU_BIOMES_VARIANT[pTemperature][pHumidity], this.PLATEAU_BIOMES[pTemperature][pHumidity]);
        }
    }

    protected ResourceKey<Biome> pickPeakBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        if (pTemperature <= 2) {
            return weirdness.max() < 0L ? Biomes.JAGGED_PEAKS : Biomes.FROZEN_PEAKS;
        } else {
            return pTemperature == 3 ? Biomes.STONY_PEAKS : this.pickBadlandsBiome(pHumidity, weirdness);
        }
    }

    protected ResourceKey<Biome> pickSlopeBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        if (pTemperature >= 3) {
            return this.pickPlateauBiome(pTemperature, pHumidity, weirdness);
        } else {
            return pHumidity <= 1 ? Biomes.SNOWY_SLOPES :
                    weirdness.max() < 0L ? Biomes.GROVE : ModBiomes.FROST_GROVE;
        }
    }

    protected ResourceKey<Biome> pickSwampBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        ResourceKey<Biome> resourcekey = this.SWAMP_BIOMES[pTemperature][pHumidity];
        return resourcekey == null ? this.pickMiddleBiome(pTemperature, pHumidity, weirdness) : resourcekey;
    }

    protected ResourceKey<Biome> pickModSwampBiome(Registry<Biome> biomeRegistry, int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        return BiomeUtils.biomeOrFallback(biomeRegistry, this.MOD_SWAMP_BIOMES[pTemperature][pHumidity], this.pickSwampBiome(pTemperature, pHumidity, weirdness));
    }

    protected ResourceKey<Biome> pickShatteredBiome(int pTemperature, int pHumidity, Climate.Parameter weirdness) {
        ResourceKey<Biome> shatteredBiome = this.SHATTERED_BIOMES[pTemperature][pHumidity];
        return shatteredBiome == null ? this.pickMiddleBiome(pTemperature, pHumidity, weirdness) :
                weirdness.max() < 0L ? shatteredBiome : ModBiomes.WINDSWEPT_PEAKS;
    }

    protected ResourceKey<Biome> pickRiverBiome(int pTemperature) {
        return pTemperature == 0 ? Biomes.FROZEN_RIVER : Biomes.RIVER;
    }

    protected void addSurfaceBiome(
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
            Climate.Parameter pTemperature,
            Climate.Parameter pHumidity,
            Climate.Parameter pContinentalness,
            Climate.Parameter pErosion,
            Climate.Parameter pWeirdness,
            float offset,
            ResourceKey<Biome> pKey
    ) {
        mapper.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(0.0F), pWeirdness, offset), pKey));
        mapper.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.0F), pWeirdness, offset), pKey));
    }

    protected void addUndergroundBiome(
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
            Climate.Parameter pTemperature,
            Climate.Parameter pHumidity,
            Climate.Parameter pContinentalness,
            Climate.Parameter pErosion,
            Climate.Parameter pWeirdness,
            float offset,
            ResourceKey<Biome> pKey
    ) {
        mapper.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.span(0.2F, 0.9F), pWeirdness, offset), pKey));
    }

    protected void addBottomBiome(
            Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper,
            Climate.Parameter pTemperature,
            Climate.Parameter pHumidity,
            Climate.Parameter pContinentalness,
            Climate.Parameter pErosion,
            Climate.Parameter pWeirdness,
            float offset,
            ResourceKey<Biome> pKey
    ) {
        mapper.accept(Pair.of(Climate.parameters(pTemperature, pHumidity, pContinentalness, pErosion, Climate.Parameter.point(1.1F), pWeirdness, offset), pKey));
    }
}