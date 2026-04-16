package com.ChalkerCharles.morecolorful.common.worldgen;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomes;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModVegetationPlacements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_FLOWER_CHERRY = registerKey("add_flower_cherry");
    public static final ResourceKey<BiomeModifier> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<BiomeModifier> PATCH_BLUEBERRY_BUSH = registerKey("patch_blueberry_bush");
    public static final ResourceKey<BiomeModifier> PATCH_CROCUS = registerKey("patch_crocus");
    public static final ResourceKey<BiomeModifier> PATCH_WATER_GRASS = registerKey("patch_water_grass");
    public static final ResourceKey<BiomeModifier> PATCH_GERBERA_DAISY = registerKey("patch_gerbera_daisy");
    public static final ResourceKey<BiomeModifier> PATCH_CATTAIL = registerKey("patch_cattail");
    public static final ResourceKey<BiomeModifier> PATCH_REED = registerKey("patch_reed");
    public static final ResourceKey<BiomeModifier> PATCH_WATER_LILY = registerKey("patch_water_lily");
    public static final ResourceKey<BiomeModifier> PATCH_DUCKWEEDS = registerKey("patch_duckweeds");
    public static final ResourceKey<BiomeModifier> PATCH_BUTTERCUPS = registerKey("patch_buttercups");
    public static final ResourceKey<BiomeModifier> PATCH_FORGET_ME_NOTS = registerKey("patch_forget-me-nots");
    public static final ResourceKey<BiomeModifier> PATCH_SPEEDWELLS = registerKey("patch_speedwells");
    public static final ResourceKey<BiomeModifier> TREES_WILLOW = registerKey("trees_willow");
    public static final ResourceKey<BiomeModifier> PATCH_WOOD_SORRELS = registerKey("patch_wood_sorrels");
    public static final ResourceKey<BiomeModifier> PATCH_WINDFLOWER = registerKey("patch_windflower");

    public static final ResourceKey<BiomeModifier> COMMON_PLAIN_SPAWN = registerKey("common_plain_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_FOREST_SPAWN = registerKey("common_forest_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_TAIGA_SPAWN = registerKey("common_taiga_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_JUNGLE_SPAWN = registerKey("common_jungle_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_SAVANNA_SPAWN = registerKey("common_savanna_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_MOUNTAIN_SPAWN = registerKey("common_mountain_spawn");
    public static final ResourceKey<BiomeModifier> COMMON_SWAMP_SPAWN = registerKey("common_swamp_spawn");
    
    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        addFeature(context, ADD_FLOWER_CHERRY,
                biomes(biomes, Biomes.CHERRY_GROVE),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.FLOWER_CHERRY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_STRAWBERRY_BUSH,
                biomes(biomes, Biomes.FOREST,
                        Biomes.FLOWER_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_STRAWBERRY_BUSH)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_BLUEBERRY_BUSH,
                biomes(biomes, Biomes.PLAINS,
                        Biomes.MEADOW),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_BLUEBERRY_BUSH)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_CROCUS,
                biomes(biomes, Biomes.SNOWY_PLAINS,
                        Biomes.SNOWY_TAIGA,
                        Biomes.WINDSWEPT_FOREST,
                        ModBiomes.ICE_MARSH),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_CROCUS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_WATER_GRASS,
                biomes(biomes, Biomes.SWAMP,
                        Biomes.MANGROVE_SWAMP,
                        ModBiomes.DAWN_REDWOOD_SWAMP,
                        ModBiomes.WILLOW_BAYOU,
                        ModBiomes.ICE_MARSH),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_WATER_GRASS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_GERBERA_DAISY,
                biomes(biomes, Biomes.SAVANNA,
                        Biomes.SAVANNA_PLATEAU,
                        Biomes.WINDSWEPT_SAVANNA),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_GERBERA_DAISY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_CATTAIL,
                biomes(biomes, Biomes.RIVER,
                        Biomes.SWAMP,
                        ModBiomes.DAWN_REDWOOD_SWAMP,
                        ModBiomes.WILLOW_BAYOU,
                        ModBiomes.ICE_MARSH),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_CATTAIL)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_REED,
                biomes(biomes, Biomes.RIVER,
                        Biomes.SWAMP,
                        ModBiomes.DAWN_REDWOOD_SWAMP,
                        ModBiomes.WILLOW_BAYOU,
                        ModBiomes.ICE_MARSH),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_REED)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_WATER_LILY,
                biomes(biomes, Biomes.SWAMP,
                        Biomes.MANGROVE_SWAMP,
                        ModBiomes.WILLOW_BAYOU),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_WATER_LILY)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_DUCKWEEDS,
                biomes(biomes, Biomes.SWAMP,
                        Biomes.MANGROVE_SWAMP,
                        ModBiomes.DAWN_REDWOOD_SWAMP,
                        ModBiomes.MARSH,
                        ModBiomes.WILLOW_BAYOU),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_DUCKWEEDS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_BUTTERCUPS,
                biomes(biomes, Biomes.SWAMP,
                        ModBiomes.MARSH,
                        ModBiomes.WILLOW_BAYOU),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_BUTTERCUPS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_FORGET_ME_NOTS,
                biomes(biomes, Biomes.MEADOW,
                        Biomes.FLOWER_FOREST,
                        Biomes.OLD_GROWTH_BIRCH_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_FORGET_ME_NOTS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_SPEEDWELLS,
                biomes(biomes, Biomes.FOREST,
                        Biomes.FLOWER_FOREST,
                        Biomes.BIRCH_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_SPEEDWELLS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, TREES_WILLOW,
                biomes(biomes, Biomes.RIVER,
                        Biomes.SWAMP,
                        ModBiomes.MARSH),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.TREES_WILLOW)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_WOOD_SORRELS,
                biomes(biomes, Biomes.FOREST,
                        Biomes.FLOWER_FOREST,
                        Biomes.BIRCH_FOREST,
                        Biomes.OLD_GROWTH_BIRCH_FOREST,
                        Biomes.DARK_FOREST,
                        Biomes.PLAINS,
                        Biomes.MEADOW,
                        ModBiomes.AUTUMN_BIRCH_FOREST,
                        ModBiomes.GOLDEN_GROVE,
                        ModBiomes.SUNSET_VALLEY),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_WOOD_SORRELS)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );
        addFeature(context, PATCH_WINDFLOWER,
                biomes.getOrThrow(ModTags.Biomes.IS_WINDY),
                HolderSet.direct(placedFeatures.getOrThrow(ModVegetationPlacements.PATCH_WINDFLOWER)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        );

        addSpawn(context, COMMON_PLAIN_SPAWN,
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 10, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 10, 1, 3))
        );
        addSpawn(context, COMMON_FOREST_SPAWN,
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 10, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 10, 1, 3))
        );
        addSpawn(context, COMMON_TAIGA_SPAWN,
                biomes.getOrThrow(BiomeTags.IS_TAIGA),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 8, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 8, 1, 3))
        );
        addSpawn(context, COMMON_JUNGLE_SPAWN,
                biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 12, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 12, 1, 3))
        );
        addSpawn(context, COMMON_SAVANNA_SPAWN,
                biomes.getOrThrow(BiomeTags.IS_SAVANNA),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 8, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 8, 1, 3))
        );
        addSpawn(context, COMMON_MOUNTAIN_SPAWN,
                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 8, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 8, 1, 3))
        );
        addSpawn(context, COMMON_SWAMP_SPAWN,
                biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                List.of(new MobSpawnSettings.SpawnerData(ModEntities.BUTTERFLY.get(), 8, 1, 3),
                        new MobSpawnSettings.SpawnerData(ModEntities.MOTH.get(), 8, 1, 3))
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MoreColorful.location(name));
    }

    @SafeVarargs
    private static HolderSet<Biome> biomes(HolderGetter<Biome> getter, ResourceKey<Biome>... biomes) {
        List<Holder.Reference<Biome>> list = new ArrayList<>();
        for (ResourceKey<Biome> b : biomes) {
            list.add(getter.getOrThrow(b));
        }
        return HolderSet.direct(list);
    }
    
    @SuppressWarnings("SameParameterValue")
    private static void addFeature(
            BootstrapContext<BiomeModifier> context,
            ResourceKey<BiomeModifier> biomeModifier,
            HolderSet<Biome> biomes,
            HolderSet<PlacedFeature> features,
            GenerationStep.Decoration step) {
        context.register(biomeModifier, new BiomeModifiers.AddFeaturesBiomeModifier(biomes, features, step));
    }

    private static void addSpawn(
            BootstrapContext<BiomeModifier> context,
            ResourceKey<BiomeModifier> biomeModifier,
            HolderSet<Biome> biomes,
            List<MobSpawnSettings.SpawnerData> spawners) {
        context.register(biomeModifier, new BiomeModifiers.AddSpawnsBiomeModifier(biomes, spawners));
    }

    public static boolean disableBiomeModifiers(Holder.Reference<BiomeModifier> holder) {
        ResourceKey<BiomeModifier> key = holder.getKey();
        if (key != null && MoreColorful.MODID.equals(key.location().getNamespace())) {
            BiomeModifier modifier = holder.value();
            if (!Config.allowAddingFeatures && modifier instanceof BiomeModifiers.AddFeaturesBiomeModifier) {
                return false;
            } else return Config.allowAddingSpawns || !(modifier instanceof BiomeModifiers.AddSpawnsBiomeModifier);
        }
        return true;
    }
}
