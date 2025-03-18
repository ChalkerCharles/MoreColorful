package com.ChalkerCharles.morecolorful.common.loot.modifiers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class AddSusBlockLootModifier extends LootModifier {
    public static final MapCodec<AddSusBlockLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            IGlobalLootModifier.LOOT_CONDITIONS_CODEC.fieldOf("conditions").forGetter(glm -> glm.conditions),
            ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(m -> m.table)
            ).apply(inst, AddSusBlockLootModifier::new)
    );
    private final ResourceKey<LootTable> table;
    public AddSusBlockLootModifier(LootItemCondition[] conditionsIn, ResourceKey<LootTable> table) {
        super(conditionsIn);
        this.table = table;
    }

    @SuppressWarnings("deprecation")
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        context.getResolver().get(Registries.LOOT_TABLE, this.table).ifPresent(extraTable -> {
            generatedLoot.clear();
            extraTable.value().getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        });
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return ModLootModifiers.ADD_SUS_BLOCK.get();
    }
}
