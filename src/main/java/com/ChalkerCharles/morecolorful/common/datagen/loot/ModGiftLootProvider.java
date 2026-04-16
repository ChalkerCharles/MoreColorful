package com.ChalkerCharles.morecolorful.common.datagen.loot;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.loot.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public record ModGiftLootProvider(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        pOutput.accept(
                ModLootTables.BEEKEEPER_GIFT,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.HONEYCOMB))
                        .add(LootItem.lootTableItem(Items.HONEY_BOTTLE))
                )
        );
        pOutput.accept(
                ModLootTables.PYROTECHNICIAN_GIFT,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(Items.FIRE_CHARGE))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER))
                )
        );
        pOutput.accept(
                ModLootTables.PAPER_ARTISAN_GIFT,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.WHITE_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.LIGHT_GRAY_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.GRAY_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.BLACK_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.BROWN_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.RED_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.ORANGE_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.YELLOW_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.LIME_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.GREEN_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.CYAN_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.LIGHT_BLUE_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.BLUE_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.PURPLE_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.MAGENTA_PARTY_POPPER))
                        .add(LootItem.lootTableItem(ModItems.PINK_PARTY_POPPER))
                )
        );
    }
}
