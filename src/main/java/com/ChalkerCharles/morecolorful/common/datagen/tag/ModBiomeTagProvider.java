package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends BiomeTagsProvider {
    public ModBiomeTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Vanilla Tags
        tag(BiomeTags.HAS_MINESHAFT).add(
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH,
                ModBiomes.LAVENDER_FIELDS,
                ModBiomes.AZURE_FIELDS,
                ModBiomes.RAPESEED_FIELDS
        );
        tag(BiomeTags.HAS_TRIAL_CHAMBERS).add(
                ModBiomes.CRABAPPLE_GARDEN,
                ModBiomes.WHITE_CHERRY_GROVE,
                ModBiomes.AUTUMN_BIRCH_FOREST,
                ModBiomes.GOLDEN_GROVE,
                ModBiomes.MAPLE_FOREST,
                ModBiomes.SUNSET_VALLEY,
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.HAS_VILLAGE_SNOWY).add(
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.HAS_CLOSER_WATER_FOG).add(
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.IS_FOREST).add(
                ModBiomes.CRABAPPLE_GARDEN,
                ModBiomes.AUTUMN_BIRCH_FOREST,
                ModBiomes.GOLDEN_GROVE,
                ModBiomes.MAPLE_FOREST,
                ModBiomes.SUNSET_VALLEY,
                ModBiomes.JACARANDA_GROVE
        );
        tag(BiomeTags.IS_MOUNTAIN).add(
                ModBiomes.WHITE_CHERRY_GROVE, 
                ModBiomes.FROST_GROVE
        );
        tag(BiomeTags.IS_OVERWORLD).add(
                ModBiomes.CRABAPPLE_GARDEN,
                ModBiomes.WHITE_CHERRY_GROVE,
                ModBiomes.AUTUMN_BIRCH_FOREST,
                ModBiomes.GOLDEN_GROVE,
                ModBiomes.MAPLE_FOREST,
                ModBiomes.SUNSET_VALLEY,
                ModBiomes.FROST_GROVE,
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.LAVENDER_FIELDS,
                ModBiomes.JACARANDA_GROVE,
                ModBiomes.MARSH,
                ModBiomes.AZURE_FIELDS,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH,
                ModBiomes.RAPESEED_FIELDS
        );
        tag(BiomeTags.INCREASED_FIRE_BURNOUT).add(
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU
        );
        tag(BiomeTags.STRONGHOLD_BIASED_TO).add(
                ModBiomes.CRABAPPLE_GARDEN,
                ModBiomes.AUTUMN_BIRCH_FOREST,
                ModBiomes.GOLDEN_GROVE,
                ModBiomes.MAPLE_FOREST,
                ModBiomes.SUNSET_VALLEY,
                ModBiomes.LAVENDER_FIELDS,
                ModBiomes.JACARANDA_GROVE,
                ModBiomes.AZURE_FIELDS,
                ModBiomes.RAPESEED_FIELDS
        );
        tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS).add(
                ModBiomes.FROST_GROVE,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.SPAWNS_WHITE_RABBITS).add(
                ModBiomes.FROST_GROVE,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.SPAWNS_SNOW_FOXES).add(
                ModBiomes.FROST_GROVE,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.WATER_ON_MAP_OUTLINES).add(
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH
        );
        tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS).add(
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU
        );

        // More Colorful Tags
        tag(ModTags.Biomes.IS_WINDY).add(
                Biomes.WINDSWEPT_FOREST,
                Biomes.WINDSWEPT_HILLS,
                Biomes.WINDSWEPT_GRAVELLY_HILLS,
                Biomes.WINDSWEPT_SAVANNA,
                Biomes.FROZEN_PEAKS,
                Biomes.JAGGED_PEAKS,
                Biomes.STONY_PEAKS
        );

        // C Tags
        tag(Tags.Biomes.IS_COLD_OVERWORLD).add(
                ModBiomes.FROST_GROVE,
                ModBiomes.ICE_MARSH
        );
        tag(Tags.Biomes.IS_CONIFEROUS_TREE).add(
                ModBiomes.DAWN_REDWOOD_SWAMP
        );
        tag(Tags.Biomes.IS_PLAINS).add(
                ModBiomes.LAVENDER_FIELDS,
                ModBiomes.AZURE_FIELDS,
                ModBiomes.RAPESEED_FIELDS
        );
        tag(Tags.Biomes.IS_FLORAL).add(
                ModBiomes.CRABAPPLE_GARDEN,
                ModBiomes.WHITE_CHERRY_GROVE,
                ModBiomes.FROST_GROVE,
                ModBiomes.LAVENDER_FIELDS,
                ModBiomes.JACARANDA_GROVE,
                ModBiomes.AZURE_FIELDS,
                ModBiomes.RAPESEED_FIELDS
        );
        tag(Tags.Biomes.IS_DECIDUOUS_TREE).add(
                ModBiomes.AUTUMN_BIRCH_FOREST,
                ModBiomes.GOLDEN_GROVE,
                ModBiomes.MAPLE_FOREST,
                ModBiomes.SUNSET_VALLEY,
                ModBiomes.DAWN_REDWOOD_SWAMP
        );
        tag(Tags.Biomes.IS_BIRCH_FOREST).add(
                ModBiomes.AUTUMN_BIRCH_FOREST
        );
        tag(Tags.Biomes.IS_SNOWY).add(
                ModBiomes.FROST_GROVE,
                ModBiomes.ICE_MARSH
        );
        tag(Tags.Biomes.IS_ICY).add(
                ModBiomes.ICE_MARSH
        );
        tag(Tags.Biomes.IS_SWAMP).add(
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH
        );
        tag(Tags.Biomes.IS_WET_OVERWORLD).add(
                ModBiomes.DAWN_REDWOOD_SWAMP,
                ModBiomes.MARSH,
                ModBiomes.WILLOW_BAYOU,
                ModBiomes.ICE_MARSH
        );
    }
}
