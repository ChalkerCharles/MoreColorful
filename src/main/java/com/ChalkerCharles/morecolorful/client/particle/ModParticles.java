package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MoreColorful.MODID);

    public static final Supplier<SimpleParticleType> CRABAPPLE_LEAVES = PARTICLE_TYPES.register("crabapple_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> WHITE_CHERRY_LEAVES = PARTICLE_TYPES.register("white_cherry_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> ORANGE_BIRCH_LEAVES = PARTICLE_TYPES.register("orange_birch_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> YELLOW_BIRCH_LEAVES = PARTICLE_TYPES.register("yellow_birch_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> GINKGO_LEAVES = PARTICLE_TYPES.register("ginkgo_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> MAPLE_LEAVES = PARTICLE_TYPES.register("maple_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> FROST_LEAVES = PARTICLE_TYPES.register("frost_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> DAWN_REDWOOD_LEAVES = PARTICLE_TYPES.register("dawn_redwood_leaves", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> JACARANDA_LEAVES = PARTICLE_TYPES.register("jacaranda_leaves", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
