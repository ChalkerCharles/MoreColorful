package com.ChalkerCharles.morecolorful.common.datagen.loot;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, Set.of(), PROVIDERS, provider);
    }

    public static final List<SubProviderEntry> PROVIDERS = List.of(
            new SubProviderEntry(ModBlockLootTableProvider::new, LootContextParamSets.BLOCK),
            new SubProviderEntry(ModArchaeologyLootProvider::new, LootContextParamSets.ARCHAEOLOGY)
    );
}
