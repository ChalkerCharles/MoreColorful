package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.animal.FrogVariant;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModFrogVariants {
    private static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(Registries.FROG_VARIANT, MoreColorful.MODID);

    public static final Holder<FrogVariant> TOMATO = register("tomato", "textures/entity/frog/tomato_frog.png");
    public static final Holder<FrogVariant> BLUE = register("blue", "textures/entity/frog/blue_frog.png");
    public static final Holder<FrogVariant> BROWN = register("brown", "textures/entity/frog/brown_frog.png");

    private static Holder<FrogVariant> register(String name, String location) {
        return FROG_VARIANTS.register(name, () -> new FrogVariant(MoreColorful.location(location)));
    }

    public static void register(IEventBus eventBus) {
        FROG_VARIANTS.register(eventBus);
    }
}
