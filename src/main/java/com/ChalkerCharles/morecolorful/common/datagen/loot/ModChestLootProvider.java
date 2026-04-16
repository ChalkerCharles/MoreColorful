package com.ChalkerCharles.morecolorful.common.datagen.loot;

import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.loot.ModLootTables;
import com.ChalkerCharles.morecolorful.common.loot.functions.RandomFireworksFunction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.function.BiConsumer;

public record ModChestLootProvider(HolderLookup.Provider registries) implements LootTableSubProvider {
    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> pOutput) {
        pOutput.accept(
                ModLootTables.VILLAGE_APIARY,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.HONEY_BOTTLE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.HONEYCOMB).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(ModItems.BEEKEEPING_HAT).setWeight(5))
                        .add(LootItem.lootTableItem(Items.DANDELION).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.POPPY).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.OAK_SAPLING).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )
        );
        pOutput.accept(
                ModLootTables.VILLAGE_PYROTECHNICIAN_HOUSE,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.FIRE_CHARGE).setWeight(10).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.GUNPOWDER).setWeight(15).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(Items.FLINT_AND_STEEL).setWeight(5))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(Items.FIREWORK_STAR).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(RandomFireworksFunction.of(false)))
                        .add(LootItem.lootTableItem(Items.FIREWORK_ROCKET).setWeight(5)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(RandomFireworksFunction.of(true)))
                )
        );
        pOutput.accept(
                ModLootTables.VILLAGE_PAPER_MILL,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(Items.PAPER).setWeight(20).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 6.0F))))
                        .add(LootItem.lootTableItem(Items.STICK).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 5.0F))))
                        .add(LootItem.lootTableItem(ModItems.CONFETTI).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                        .add(LootItem.lootTableItem(ModItems.CARDBOARD).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F))))
                )
        );
        pOutput.accept(
                ModLootTables.VILLAGE_PLAINS_HOUSE_ADD,
                LootTable.lootTable().withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 5.0F))
                        .add(LootItem.lootTableItem(ModItems.STRAW_HAT))
                )
        );
    }
}
