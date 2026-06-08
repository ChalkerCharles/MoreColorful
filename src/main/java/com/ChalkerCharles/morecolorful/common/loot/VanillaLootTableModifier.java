package com.ChalkerCharles.morecolorful.common.loot;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.entity.animal.ModFrogVariants;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.advancements.critereon.DamageSourcePredicate;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntitySubPredicates;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.DamageSourceCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;

public class VanillaLootTableModifier {
    public static void modify(LootTable table, ResourceLocation location) {
        if (location.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_COMMON.location())) {
            if (Config.archaeologyLoots) {
                addEntries(table.getPool("main"),
                        LootItem.lootTableItem(ModItems.EBONY_LOG),
                        LootItem.lootTableItem(ModItems.EBONY_HANGING_SIGN)
                );
            }
        } else if (location.equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE.location())) {
            addEntries(table.getPool("main"), LootItem.lootTableItem(ModItems.STRAW_HAT));
        } else if (location.equals(EntityType.MAGMA_CUBE.getDefaultLootTable().location())) {
            addEntries(table.getPool("main"),
                    LootItem.lootTableItem(ModItems.VERMILION_FROGLIGHT)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                            .when(killedByFrogVariant(ModFrogVariants.TOMATO)),
                    LootItem.lootTableItem(ModItems.CYANINE_FROGLIGHT)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                            .when(killedByFrogVariant(ModFrogVariants.BLUE)),
                    LootItem.lootTableItem(ModItems.UMBER_FROGLIGHT)
                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                            .when(killedByFrogVariant(ModFrogVariants.BROWN))
            );
        }
    }

    private static void addEntries(LootPool pool, LootPoolEntryContainer.Builder<?>... builders) {
        if (pool == null) return;
        List<LootPoolEntryContainer> entries = new ArrayList<>(pool.entries);
        for (LootPoolEntryContainer.Builder<?> builder : builders) {
            entries.add(builder.build());
        }
        pool.entries = List.copyOf(entries);
    }

    private static LootItemCondition.Builder killedByFrogVariant(Holder<FrogVariant> variant) {
        return DamageSourceCondition.hasDamageSource(
                DamageSourcePredicate.Builder.damageType().source(
                        EntityPredicate.Builder.entity()
                                .of(EntityType.FROG)
                                .subPredicate(EntitySubPredicates.frogVariant(variant))
                )
        );
    }
}
