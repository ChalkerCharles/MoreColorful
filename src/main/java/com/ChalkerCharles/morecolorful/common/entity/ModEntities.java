package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MoreColorful.MODID);

    public static final Supplier<EntityType<PaperPlane>> PAPER_PLANE = register(
            "paper_plane", EntityType.Builder.<PaperPlane>of(PaperPlane::new, MobCategory.MISC).sized(0.6F, 0.2F).clientTrackingRange(4).updateInterval(20)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, location -> builder.build(location.toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
