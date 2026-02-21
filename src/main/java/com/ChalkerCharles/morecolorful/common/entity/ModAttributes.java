package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModAttributes {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(BuiltInRegistries.ATTRIBUTE, MoreColorful.MODID);

    public static final Holder<Attribute> HORIZONTAL_WINDAGE = register("horizontal_windage", () -> new RangedAttribute(
            "attribute.morecolorful.horizontal_windage", 1.0, 0.0, 10.0
    ).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
    public static final Holder<Attribute> VERTICAL_WINDAGE = register("vertical_windage", () -> new RangedAttribute(
            "attribute.morecolorful.vertical_windage", 1.0, 0.0, 10.0
    ).setSyncable(true).setSentiment(Attribute.Sentiment.NEGATIVE));
    public static final Holder<Attribute> WEIGHT = register("weight", () -> new RangedAttribute(
            "attribute.morecolorful.weight", 1.0, 0.0, 10.0
    ).setSyncable(true).setSentiment(Attribute.Sentiment.NEUTRAL));

    private static Holder<Attribute> register(String name, Supplier<Attribute> supplier) {
        return ATTRIBUTES.register(name, supplier);
    }

    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
