package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.component.EditableMelody;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    private static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(MoreColorful.MODID);

    public static final Supplier<DataComponentType<Melody>> MELODY = register("melody", builder -> builder.persistent(Melody.CODEC).networkSynchronized(Melody.STREAM_CODEC).cacheEncoding());
    public static final Supplier<DataComponentType<EditableMelody>> EDITABLE_MELODY = register("editable_melody", builder -> builder.persistent(EditableMelody.CODEC).networkSynchronized(EditableMelody.STREAM_CODEC).cacheEncoding());
    public static final Supplier<DataComponentType<PinwheelContext>> PINWHEEL_CONTEXT = register("pinwheel_context", builder -> builder.networkSynchronized(PinwheelContext.STREAM_CODEC));
    public static final Supplier<DataComponentType<Unit>> ACTIVATED = register("activated", builder -> builder.persistent(Unit.CODEC).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));

    private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return DATA_COMPONENTS.registerComponentType(name, builder);
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENTS.register(eventBus);
    }
}
