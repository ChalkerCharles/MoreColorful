package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.entity.*;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MoreColorful.MODID);

    public static final Supplier<BlockEntityType<HiHatBlockEntity>> HIHAT = register("hi-hat", () -> create(HiHatBlockEntity::new, ModBlocks.HIHAT.get()));
    public static final Supplier<BlockEntityType<RideCymbalBlockEntity>> RIDE_CYMBAL = register("ride_cymbal", () -> create(RideCymbalBlockEntity::new, ModBlocks.RIDE_CYMBAL.get()));
    public static final Supplier<BlockEntityType<CrashCymbalBlockEntity>> CRASH_CYMBAL = register("crash_cymbal", () -> create(CrashCymbalBlockEntity::new, ModBlocks.CRASH_CYMBAL.get()));
    public static final Supplier<BlockEntityType<DrumSetBlockEntity>> DRUM_SET = register("drum_set", () -> create(DrumSetBlockEntity::new, ModBlocks.DRUM_SET.get()));
    public static final Supplier<BlockEntityType<FanBlockEntity>> FAN_BLOCK = register("fan_block", () -> create(FanBlockEntity::new, ModBlocks.FAN_BLOCK.get()));
    public static final Supplier<BlockEntityType<WeatherVaneBlockEntity>> WEATHER_VANE = register("weather_vane", () -> create(WeatherVaneBlockEntity::new, ModBlocks.WEATHER_VANE.get()));
    public static final Supplier<BlockEntityType<MusicBoxBlockEntity>> MUSIC_BOX = register("music_box", () -> create(MusicBoxBlockEntity::new, ModBlocks.MUSIC_BOX.get()));
    public static final Supplier<BlockEntityType<PinwheelBlockEntity>> PINWHEEL = register("pinwheel", () -> create(PinwheelBlockEntity::new, ModBlocks.PINWHEEL.get()));
    public static final Supplier<BlockEntityType<PennantBlockEntity>> PENNANT = register("pennant", () -> create(PennantBlockEntity::new, PennantBlock.ALL_BLOCKS.get()));

    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, Supplier<BlockEntityType<T>> supplier) {
        return BLOCK_ENTITY_TYPES.register(name, supplier);
    }

    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> BlockEntityType<T> create(BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory, blocks).build(null);
    }

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}