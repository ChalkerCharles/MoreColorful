package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.entity.ai.memory.ModMemoryModuleTypes;
import com.ChalkerCharles.morecolorful.common.entity.ai.sensor.ModSensorTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Villager.class)
public abstract class VillagerMixin {
    @Shadow
    @Final
    @Mutable
    private static ImmutableList<MemoryModuleType<?>> MEMORY_TYPES;

    @Shadow
    @Final
    @Mutable
    private static ImmutableList<SensorType<? extends Sensor<? super Villager>>> SENSOR_TYPES;

    static {
        MEMORY_TYPES = ImmutableList.<MemoryModuleType<?>>builder()
                .addAll(MEMORY_TYPES)
                .add(ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get())
                .add(ModMemoryModuleTypes.KITE_MEMORY.get())
                .build();

        SENSOR_TYPES = ImmutableList.<SensorType<? extends Sensor<? super Villager>>>builder()
                .addAll(SENSOR_TYPES)
                .add(ModSensorTypes.NEAREST_OPEN_SPACE.get())
                .build();
    }
}
