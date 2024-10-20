package com.ChalkerCharles.morecolorful.common.worldgen;

import com.ChalkerCharles.morecolorful.common.worldgen.placements.ModVegetationPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModBiomeFeatures {
    public static void addCrabappleVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_CRABAPPLE);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_CRABAPPLE);
    }
    public static void addWhiteCherryVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_WHITE_CHERRY);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_WHITE_CHERRY);
    }
    public static void addAutumnBirchVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_AUTUMN_BIRCH);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_AUTUMN_BIRCH);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.AUTUMN_BIRCH_LEAF_PILE);
    }
    public static void addGoldenGroveVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_GINKGO);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_GINKGO);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GINKGO_LEAF_PILE);
    }
    public static void addMapleForestVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_MAPLE);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_MAPLE);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.MAPLE_LEAF_PILE);
    }
    public static void addSunsetValleyVegetation(BiomeGenerationSettings.Builder pBuilder) {
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.FLOWER_SUNSET_VALLEY);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.TREES_SUNSET_VALLEY);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.AUTUMN_BIRCH_LEAF_PILE);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.GINKGO_LEAF_PILE);
        pBuilder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacements.MAPLE_LEAF_PILE);
    }
}
