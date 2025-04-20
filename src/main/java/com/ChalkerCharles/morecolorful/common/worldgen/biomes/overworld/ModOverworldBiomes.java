package com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld;

import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModMiscOverworldPlacements;
import com.ChalkerCharles.morecolorful.util.BiomeUtils;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModOverworldBiomes extends OverworldBiomes {
    private static final Music CHERRY_MUSIC = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE);
    private static final Music FOREST_MUSIC = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
    private static final Music SWAMP_MUSIC = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_SWAMP);
    private static final Music NORMAL_MUSIC = null;
    
    public static Biome crabappleGarden(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 2, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addCrabappleVegetation(biomeBuilder);
        return biome(true, 0.7F, 0.8F, 0x5db7ef, 0x5db7ef, 0x90d462, 0x90d462, spawnBuilder, biomeBuilder, CHERRY_MUSIC);
    }
    public static Biome whiteCherryGrove(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 2, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addWhiteCherryVegetation(biomeBuilder);
        BiomeDefaultFeatures.addExtraEmeralds(biomeBuilder);
        BiomeDefaultFeatures.addInfestedStone(biomeBuilder);
        return biome(true, 0.5F, 0.8F, 0x5db7ef, 0x5db7ef, 0xb6db61, 0xb6db61, spawnBuilder, biomeBuilder, CHERRY_MUSIC);
    }
    public static Biome autumnBirchForest(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addAutumnBirchVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);
        return biome(true, 0.5F, 0.6F, 0x3f76e4, 0x050533, 0xeba836, 0xdc961f, spawnBuilder, biomeBuilder, FOREST_MUSIC);
    }
    public static Biome goldenGrove(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4));
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addGoldenGroveVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);
        return biome(true, 0.5F, 0.6F, 0x3f76e4, 0x050533, 0xd3d342, 0xc9c936, spawnBuilder, biomeBuilder, FOREST_MUSIC);
    }
    public static Biome mapleForest(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addMapleForestVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);
        
        return biome(true, 0.35F, 0.5F, spawnBuilder, biomeBuilder, FOREST_MUSIC);
    }
    public static Biome sunsetValley(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 8, 2, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 3));
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addSunsetValleyVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);
        return biome(true, 0.4F, 0.6F, 0x3f76e4, 0x050533, 0xeb8b36, 0xe67f2c, spawnBuilder, biomeBuilder, FOREST_MUSIC);
    }
    public static Biome frostGrove(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 1, 2, 6));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addFrozenSprings(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addFrostVegetation(biomeBuilder);
        BiomeDefaultFeatures.addExtraEmeralds(biomeBuilder);
        BiomeDefaultFeatures.addInfestedStone(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_GROVE);
        return biome(true, -0.2F, 0.8F, spawnBuilder, biomeBuilder, music);
    }
    public static Biome dawnRedwoodSwamp(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder, 70);
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1));
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 30, 4, 4));
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addSwampClayDisk(biomeBuilder);
        ModBiomeFeatures.addDawnRedwoodSwampVegetation(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        return biome(true, 0.4F, 0.6F, 0x2b798c, 0x050533, 0xcc702f, 0xc96b2c, spawnBuilder, biomeBuilder, SWAMP_MUSIC);
    }
    public static Biome lavenderFields(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 6, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 5, 4, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addLavenderVegetation(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST);
        return biome(true, 0.8F, 0.8F, 0x28a9dc, 0x050533, 0xa8c768, 0x9cbd55, spawnBuilder, biomeBuilder, music);
    }
    public static Biome jacarandaGrove(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 1, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 2, 2, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 2, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addJacarandaVegetation(biomeBuilder);
        return biome(true, 0.8F, 0.8F, 0x28a9dc, 0x050533, 0xa8c768, 0x9cbd55, spawnBuilder, biomeBuilder, CHERRY_MUSIC);
    }
    public static Biome marsh(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder, 70);
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 30, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, ModMiscOverworldPlacements.WATER_LAKE_MARSH);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addSwampClayDisk(biomeBuilder);
        ModBiomeFeatures.addMarshVegetation(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        return biome(true, 0.6F, 0.7F, 0x4888a3, 0x072b42, 0x90bc63, 0x90bc63, spawnBuilder, biomeBuilder, SWAMP_MUSIC);
    }
    public static Biome azureFields(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 6, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 6, 2, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 5, 4, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addAzureVegetation(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST);
        return biome(true, 0.65F, 0.6F, spawnBuilder, biomeBuilder, music);
    }
    public static Biome willowBayou(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 6, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 5, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 4, 4, 4));
        BiomeDefaultFeatures.commonSpawns(spawnBuilder, 70);
        spawnBuilder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.SLIME, 1, 1, 1))
                .addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.BOGGED, 30, 4, 4))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        BiomeDefaultFeatures.addFossilDecoration(biomeBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addSwampClayDisk(biomeBuilder);
        ModBiomeFeatures.addWillowBayouVegetation(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        return new Biome.BiomeBuilder()
                .hasPrecipitation(true)
                .temperature(0.8F)
                .downfall(0.9F)
                .specialEffects(
                        new BiomeSpecialEffects.Builder()
                                .waterColor(0x489293)
                                .waterFogColor(0x345e56)
                                .fogColor(0xaee4e6)
                                .skyColor(calculateSkyColor(0.8F))
                                .grassColorOverride(0x64b34f)
                                .foliageColorOverride(0x64b34f)
                                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                                .backgroundMusic(SWAMP_MUSIC)
                                .build()
                )
                .mobSpawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }
    public static Biome iceMarsh(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        globalOverworldGeneration(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, ModMiscOverworldPlacements.WATER_LAKE_MARSH);
        spawnBuilder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 10, 2, 5));
        spawnBuilder.creatureGenerationProbability(0.07F);
        BiomeDefaultFeatures.snowySpawns(spawnBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        BiomeDefaultFeatures.addSnowyTrees(biomeBuilder);
        BiomeDefaultFeatures.addWaterTrees(biomeBuilder);
        BiomeDefaultFeatures.addDefaultFlowers(biomeBuilder);
        BiomeDefaultFeatures.addDefaultGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomeBuilder);
        BiomeDefaultFeatures.addSwampExtraVegetation(biomeBuilder);
        biomeBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        return biome(true, 0.0F, 0.5F, 0x14559b, 0x050533, 0xabb381, 0x7da177, spawnBuilder, biomeBuilder, NORMAL_MUSIC);
    }
    public static Biome rapeseedFields(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeDefaultFeatures.farmAnimals(spawnBuilder);
        BiomeDefaultFeatures.commonSpawns(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addPlainGrass(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addRapeseedVegetation(biomeBuilder);
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FLOWER_FOREST);
        return biome(true, 0.65F, 0.6F, 0x44aff5, 0x050533, 0x8fc93c, 0x7cbd3a, spawnBuilder, biomeBuilder, music);
    }
}
