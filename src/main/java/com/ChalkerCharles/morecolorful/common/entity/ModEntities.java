package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.animal.Butterfly;
import com.ChalkerCharles.morecolorful.common.entity.animal.Caterpillar;
import com.ChalkerCharles.morecolorful.common.entity.animal.Moth;
import com.ChalkerCharles.morecolorful.common.entity.misc.*;
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
            "paper_plane", EntityType.Builder.<PaperPlane>of(PaperPlane::new, MobCategory.MISC).sized(0.6F, 0.2F).clientTrackingRange(4).updateInterval(10)
    );
    public static final Supplier<EntityType<PaperBoat>> PAPER_BOAT = register(
            "paper_boat", EntityType.Builder.<PaperBoat>of(PaperBoat::new, MobCategory.MISC).sized(0.6F, 0.4F).clientTrackingRange(4).updateInterval(10)
    );
    public static final Supplier<EntityType<Balloon>> BALLOON = register(
            "balloon", EntityType.Builder.<Balloon>of(Balloon::new, MobCategory.MISC).sized(0.625F, 0.6875F).clientTrackingRange(10)
    );
    public static final Supplier<EntityType<SandbagEntity>> SANDBAG = register(
            "sandbag", EntityType.Builder.<SandbagEntity>of(SandbagEntity::new, MobCategory.MISC).sized(0.38F, 0.38F).clientTrackingRange(10).updateInterval(20)
    );
    public static final Supplier<EntityType<Kite>> KITE = register(
            "kite", EntityType.Builder.<Kite>of(Kite::new, MobCategory.MISC).sized(2.0F, 0.1F).clientTrackingRange(10)
    );
    public static final Supplier<EntityType<PrimedUnderwaterTnt>> UNDERWATER_TNT = register(
            "underwater_tnt",
            EntityType.Builder.<PrimedUnderwaterTnt>of(PrimedUnderwaterTnt::new, MobCategory.MISC)
                    .fireImmune()
                    .sized(0.98F, 0.98F)
                    .eyeHeight(0.15F)
                    .clientTrackingRange(10)
                    .updateInterval(10)
    );
    public static final Supplier<EntityType<Bomb>> BOMB = register(
            "bomb", EntityType.Builder.<Bomb>of(Bomb::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );
    public static final Supplier<EntityType<Butterfly>> BUTTERFLY = register(
            "butterfly", EntityType.Builder.of(Butterfly::new, MobCategory.AMBIENT).sized(0.75F, 0.25F).eyeHeight(0.15F).clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Moth>> MOTH = register(
            "moth", EntityType.Builder.of(Moth::new, MobCategory.AMBIENT).sized(0.75F, 0.25F).eyeHeight(0.15F).clientTrackingRange(8)
    );
    public static final Supplier<EntityType<Caterpillar>> CATERPILLAR = register(
            "caterpillar", EntityType.Builder.of(Caterpillar::new, MobCategory.AMBIENT).sized(0.625F, 0.125F).eyeHeight(0.0625F).clientTrackingRange(8)
    );
    public static final Supplier<EntityType<SmokeBomb>> SMOKE_BOMB = register(
            "smoke_bomb", EntityType.Builder.<SmokeBomb>of(SmokeBomb::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
    );

    private static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, location -> builder.build(location.toString()));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
