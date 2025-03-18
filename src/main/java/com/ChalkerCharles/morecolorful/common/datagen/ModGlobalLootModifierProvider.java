package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.loot.ModLootTables;
import com.ChalkerCharles.morecolorful.common.loot.modifiers.AddSusBlockLootModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider) {
        super(output, provider, MoreColorful.MODID);
    }

    @Override
    protected void start() {
        add("archaeology_trail_ruins_common", new AddSusBlockLootModifier(new LootItemCondition[] {
                LootTableIdCondition.builder(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON.location()).build(),
                LootItemRandomChanceCondition.randomChance(0.04255319F).build() // 0.04255319F = 2/47
        }, ModLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON_ADD));
    }
}
