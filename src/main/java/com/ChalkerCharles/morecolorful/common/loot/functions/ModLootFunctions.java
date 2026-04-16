package com.ChalkerCharles.morecolorful.common.loot.functions;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModLootFunctions {
    private static final DeferredRegister<LootItemFunctionType<?>> LOOT_FUNCTIONS = DeferredRegister.create(Registries.LOOT_FUNCTION_TYPE, MoreColorful.MODID);

    public static final Supplier<LootItemFunctionType<RandomFireworksFunction>> RANDOM_FIREWORKS = LOOT_FUNCTIONS.register(
            "random_fireworks", () -> new LootItemFunctionType<>(RandomFireworksFunction.CODEC)
    );

    public static void register(IEventBus eventBus) {
        LOOT_FUNCTIONS.register(eventBus);
    }
}
