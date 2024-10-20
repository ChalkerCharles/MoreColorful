package com.ChalkerCharles.morecolorful.util;

import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class BiomeUtils {
    @SafeVarargs
    public static ResourceKey<Biome> biomeOrFallback(Registry<Biome> ignored, ResourceKey<Biome>... biomes) {
        for (ResourceKey<Biome> key : biomes) {
            if (key == null)
                continue;

            // todo: config
            return key;
        }
        throw new RuntimeException("Failed to find fallback for biome!");
    }

    public static void addDefaultSpawn(MobSpawnSettings.Builder builder) {
        BiomeDefaultFeatures.farmAnimals(builder);
        BiomeDefaultFeatures.commonSpawns(builder);
    }

    public static void addDefaultVegetation(BiomeGenerationSettings.Builder builder) {
        BiomeDefaultFeatures.addForestGrass(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
    }
}
