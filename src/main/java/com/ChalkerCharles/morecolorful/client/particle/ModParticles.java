package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MoreColorful.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> CRABAPPLE_LEAVES = PARTICLE_TYPES.register("crabapple_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> WHITE_CHERRY_LEAVES = PARTICLE_TYPES.register("white_cherry_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> AUTUMN_BIRCH_LEAVES = PARTICLE_TYPES.register("autumn_birch_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> GINKGO_LEAVES = PARTICLE_TYPES.register("ginkgo_leaves", () -> new SimpleParticleType(false));
    public static final DeferredHolder<ParticleType<?>, SimpleParticleType> MAPLE_LEAVES = PARTICLE_TYPES.register("maple_leaves", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
