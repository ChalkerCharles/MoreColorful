package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("SameParameterValue")
public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, MoreColorful.MODID);

    public static final Supplier<SimpleParticleType> CRABAPPLE_LEAVES = register("crabapple_leaves", false);
    public static final Supplier<SimpleParticleType> WHITE_CHERRY_LEAVES = register("white_cherry_leaves", false);
    public static final Supplier<SimpleParticleType> ORANGE_BIRCH_LEAVES = register("orange_birch_leaves", false);
    public static final Supplier<SimpleParticleType> YELLOW_BIRCH_LEAVES = register("yellow_birch_leaves", false);
    public static final Supplier<SimpleParticleType> GINKGO_LEAVES = register("ginkgo_leaves", false);
    public static final Supplier<SimpleParticleType> MAPLE_LEAVES = register("maple_leaves", false);
    public static final Supplier<SimpleParticleType> FROST_LEAVES = register("frost_leaves", false);
    public static final Supplier<SimpleParticleType> DAWN_REDWOOD_LEAVES = register("dawn_redwood_leaves", false);
    public static final Supplier<SimpleParticleType> JACARANDA_LEAVES = register("jacaranda_leaves", false);
    public static final Supplier<ParticleType<ColorParticleOption>> TINTED_LEAVES = register("tinted_leaves", false, ColorParticleOption::codec, ColorParticleOption::streamCodec);
    public static final Supplier<ParticleType<ColorParticleOption>> SPRUCE_LEAVES = register("spruce_leaves", false, ColorParticleOption::codec, ColorParticleOption::streamCodec);

    private static Supplier<SimpleParticleType> register(String name, boolean overrideLimiter) {
        return PARTICLE_TYPES.register(name, () -> new SimpleParticleType(overrideLimiter));
    }

    private static <T extends ParticleOptions> Supplier<ParticleType<T>> register(
            String name, boolean overrideLimiter, Function<ParticleType<T>, MapCodec<T>> codec, Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodec) {
        class CustomParticleType extends ParticleType<T> {
            private CustomParticleType() {
                super(overrideLimiter);
            }

            @Override
            public MapCodec<T> codec() {
                return codec.apply(this);
            }

            @Override
            public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
                return streamCodec.apply(this);
            }
        }
        return PARTICLE_TYPES.register(name, CustomParticleType::new);
    }

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}
