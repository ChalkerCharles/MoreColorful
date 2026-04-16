package com.ChalkerCharles.morecolorful.common.loot.functions;

import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public class RandomFireworksFunction extends LootItemConditionalFunction {
    public static final MapCodec<RandomFireworksFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> commonFields(instance)
                    .and(Codec.BOOL.fieldOf("isFireworkRocket").forGetter(f -> f.isFireworkRocket))
                    .apply(instance, RandomFireworksFunction::new)
    );
    private final boolean isFireworkRocket;

    protected RandomFireworksFunction(List<LootItemCondition> pPredicates, boolean isFireworkRocket) {
        super(pPredicates);
        this.isFireworkRocket = isFireworkRocket;
    }

    @Override
    public LootItemFunctionType<RandomFireworksFunction> getType() {
        return ModLootFunctions.RANDOM_FIREWORKS.get();
    }

    @Override
    protected ItemStack run(ItemStack pStack, LootContext pContext) {
        FireworkExplosion explosion = ItemUtils.getRandomFireworkExplosion(pContext.getRandom());
        if (this.isFireworkRocket) {
            pStack.set(DataComponents.FIREWORKS, new Fireworks(1, List.of(explosion)));
        } else {
            pStack.set(DataComponents.FIREWORK_EXPLOSION, explosion);
        }
        return pStack;
    }

    public static LootItemConditionalFunction.Builder<?> of(boolean isRocket) {
        return simpleBuilder(conditions -> new RandomFireworksFunction(conditions, isRocket));
    }
}
