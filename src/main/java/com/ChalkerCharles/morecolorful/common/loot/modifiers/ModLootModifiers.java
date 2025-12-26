package com.ChalkerCharles.morecolorful.common.loot.modifiers;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {
    private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MoreColorful.MODID);

    public static final Supplier<MapCodec<AddSusBlockLootModifier>> ADD_SUS_BLOCK =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("add_sus_block", ()-> AddSusBlockLootModifier.CODEC);

    public static void register(IEventBus eventBus) {
        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
