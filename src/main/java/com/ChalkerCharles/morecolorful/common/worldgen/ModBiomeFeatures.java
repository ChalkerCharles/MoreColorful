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
}
