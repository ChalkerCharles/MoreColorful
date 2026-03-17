package com.ChalkerCharles.morecolorful.common.entity.ai.sensor;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSensorTypes {
    private static final DeferredRegister<SensorType<?>> SENSOR_TYPES = DeferredRegister.create(BuiltInRegistries.SENSOR_TYPE, MoreColorful.MODID);

    public static final Supplier<SensorType<NearestOpenSpaceSensor>> NEAREST_OPEN_SPACE = register("nearest_open_space", NearestOpenSpaceSensor::new);

    private static <T extends Sensor<?>> Supplier<SensorType<T>> register(String name, Supplier<T> supplier) {
        return SENSOR_TYPES.register(name, () -> new SensorType<>(supplier));
    }

    public static void register(IEventBus eventBus) {
        SENSOR_TYPES.register(eventBus);
    }
}
