package com.ChalkerCharles.morecolorful.common.worldgen.biomes.overworld;

import com.ChalkerCharles.morecolorful.common.worldgen.ModBiomeFeatures;
import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModVegetationPlacements;
import com.ChalkerCharles.morecolorful.util.BiomeUtils;
import net.minecraft.core.HolderGetter;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.biome.OverworldBiomes;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class ModOverworldBiomes extends OverworldBiomes {
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

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE);
        return biome(true, 0.7F, 0.8F, 6141935, 6141935, 9491554, 9491554, spawnBuilder, biomeBuilder, music);
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
        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_CHERRY_GROVE);
        return biome(true, 0.5F, 0.8F, 6141935, 6141935, 11983713, 11983713, spawnBuilder, biomeBuilder, music);
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

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
        return biome(true, 0.5F, 0.6F, 4159204, 329011, 15444022, 15444022, spawnBuilder, biomeBuilder, music);
    }
    public static Biome goldenGrove(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addGoldenGroveVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
        return biome(true, 0.5F, 0.6F, 4159204, 329011, 13882178, 13882178, spawnBuilder, biomeBuilder, music);
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

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
        return biome(true, 0.35F, 0.5F, spawnBuilder, biomeBuilder, music);
    }
    public static Biome sunsetValley(HolderGetter<PlacedFeature> pPlacedFeatures, HolderGetter<ConfiguredWorldCarver<?>> pWorldCarvers) {
        BiomeGenerationSettings.Builder biomeBuilder = new BiomeGenerationSettings.Builder(pPlacedFeatures, pWorldCarvers);
        MobSpawnSettings.Builder spawnBuilder = new MobSpawnSettings.Builder();
        BiomeUtils.addDefaultSpawn(spawnBuilder);
        globalOverworldGeneration(biomeBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeBuilder);
        ModBiomeFeatures.addSunsetValleyVegetation(biomeBuilder);
        BiomeUtils.addDefaultVegetation(biomeBuilder);

        Music music = Musics.createGameMusic(SoundEvents.MUSIC_BIOME_FOREST);
        return biome(true, 0.4F, 0.6F, 4159204, 329011, 15436598, 15436598, spawnBuilder, biomeBuilder, music);
    }
}
