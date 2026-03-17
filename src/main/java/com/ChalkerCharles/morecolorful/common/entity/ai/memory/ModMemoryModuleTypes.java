package com.ChalkerCharles.morecolorful.common.entity.ai.memory;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class ModMemoryModuleTypes {
    private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULE_TYPES = DeferredRegister.create(BuiltInRegistries.MEMORY_MODULE_TYPE, MoreColorful.MODID);

    public static final Supplier<MemoryModuleType<BlockPos>> NEAREST_OPEN_SPACE = register("nearest_open_space");
    public static final Supplier<MemoryModuleType<KiteMemory>> KITE_MEMORY = register("kite_memory", KiteMemory.CODEC);

    private static <T> Supplier<MemoryModuleType<T>> register(String name, Optional<Codec<T>> codec) {
        return MEMORY_MODULE_TYPES.register(name, () -> new MemoryModuleType<>(codec));
    }

    private static <T> Supplier<MemoryModuleType<T>> register(String name) {
        return register(name, Optional.empty());
    }

    private static <T> Supplier<MemoryModuleType<T>> register(String name, Codec<T> codec) {
        return register(name, Optional.of(codec));
    }

    public static void register(IEventBus eventBus) {
        MEMORY_MODULE_TYPES.register(eventBus);
    }
}
