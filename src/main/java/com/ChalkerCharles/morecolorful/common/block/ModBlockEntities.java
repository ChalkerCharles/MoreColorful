package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.entity.CrashCymbalBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.HiHatBlockEntity;
import com.ChalkerCharles.morecolorful.common.block.entity.RideCymbalBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

@SuppressWarnings("DataFlowIssue")
public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MoreColorful.MODID);

    public static final Supplier<BlockEntityType<HiHatBlockEntity>> HIHAT = BLOCK_ENTITIES.register("hi-hat", () -> BlockEntityType.Builder.of(HiHatBlockEntity::new, ModBlocks.HIHAT.get()).build(null));
    public static final Supplier<BlockEntityType<RideCymbalBlockEntity>> RIDE_CYMBAL = BLOCK_ENTITIES.register("ride_cymbal", () -> BlockEntityType.Builder.of(RideCymbalBlockEntity::new, ModBlocks.RIDE_CYMBAL.get()).build(null));
    public static final Supplier<BlockEntityType<CrashCymbalBlockEntity>> CRASH_CYMBAL = BLOCK_ENTITIES.register("crash_cymbal", () -> BlockEntityType.Builder.of(CrashCymbalBlockEntity::new, ModBlocks.CRASH_CYMBAL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}