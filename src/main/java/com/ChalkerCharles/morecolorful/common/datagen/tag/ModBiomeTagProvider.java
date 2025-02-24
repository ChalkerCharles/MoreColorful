package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.tags.BiomeTags;
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
        tag(BiomeTags.HAS_MINESHAFT)
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
        tag(BiomeTags.HAS_TRIAL_CHAMBERS)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location())
                .addOptional(ModBiomes.WHITE_CHERRY_GROVE.location())
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location())
                .addOptional(ModBiomes.GOLDEN_GROVE.location())
                .addOptional(ModBiomes.MAPLE_FOREST.location())
                .addOptional(ModBiomes.SUNSET_VALLEY.location())
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
        tag(BiomeTags.IS_FOREST)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location())
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location())
                .addOptional(ModBiomes.GOLDEN_GROVE.location())
                .addOptional(ModBiomes.MAPLE_FOREST.location())
                .addOptional(ModBiomes.SUNSET_VALLEY.location());
        tag(BiomeTags.IS_MOUNTAIN)
                .addOptional(ModBiomes.WHITE_CHERRY_GROVE.location())
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(BiomeTags.IS_OVERWORLD)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location())
                .addOptional(ModBiomes.WHITE_CHERRY_GROVE.location())
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location())
                .addOptional(ModBiomes.GOLDEN_GROVE.location())
                .addOptional(ModBiomes.MAPLE_FOREST.location())
                .addOptional(ModBiomes.SUNSET_VALLEY.location())
                .addOptional(ModBiomes.FROST_GROVE.location())
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
        tag(BiomeTags.STRONGHOLD_BIASED_TO)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location())
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location())
                .addOptional(ModBiomes.GOLDEN_GROVE.location())
                .addOptional(ModBiomes.MAPLE_FOREST.location())
                .addOptional(ModBiomes.SUNSET_VALLEY.location());
        tag(BiomeTags.SPAWNS_COLD_VARIANT_FROGS)
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(BiomeTags.SPAWNS_WHITE_RABBITS)
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(BiomeTags.SPAWNS_SNOW_FOXES)
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(BiomeTags.WATER_ON_MAP_OUTLINES)
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());

        // C Tags
        tag(Tags.Biomes.IS_COLD_OVERWORLD)
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(Tags.Biomes.IS_CONIFEROUS_TREE)
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
        tag(Tags.Biomes.IS_FLORAL)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location())
                .addOptional(ModBiomes.WHITE_CHERRY_GROVE.location())
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(Tags.Biomes.IS_DECIDUOUS_TREE)
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location())
                .addOptional(ModBiomes.GOLDEN_GROVE.location())
                .addOptional(ModBiomes.MAPLE_FOREST.location())
                .addOptional(ModBiomes.SUNSET_VALLEY.location())
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
        tag(Tags.Biomes.IS_BIRCH_FOREST)
                .addOptional(ModBiomes.AUTUMN_BIRCH_FOREST.location());
        tag(Tags.Biomes.IS_SNOWY)
                .addOptional(ModBiomes.FROST_GROVE.location());
        tag(Tags.Biomes.IS_SWAMP)
                .addOptional(ModBiomes.DAWN_REDWOOD_SWAMP.location());
    }
}
