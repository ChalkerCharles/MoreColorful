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
        tag(BiomeTags.HAS_TRIAL_CHAMBERS)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location());
        tag(BiomeTags.IS_FOREST)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location());
        tag(BiomeTags.IS_OVERWORLD)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location());
        tag(BiomeTags.STRONGHOLD_BIASED_TO)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location());

        // C Tags
        tag(Tags.Biomes.IS_FLORAL)
                .addOptional(ModBiomes.CRABAPPLE_GARDEN.location());
    }
}
