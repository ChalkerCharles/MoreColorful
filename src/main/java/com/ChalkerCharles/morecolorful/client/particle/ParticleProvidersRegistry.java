package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.particles.LeafParticle;
import com.ChalkerCharles.morecolorful.client.particle.particles.PetalParticle;
import com.ChalkerCharles.morecolorful.client.particle.particles.WindParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProvidersRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.CRABAPPLE_LEAVES.get(), PetalParticle.Provider::new);
        event.registerSpriteSet(ModParticles.WHITE_CHERRY_LEAVES.get(), PetalParticle.Provider::new);
        event.registerSpriteSet(ModParticles.ORANGE_BIRCH_LEAVES.get(), LeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.YELLOW_BIRCH_LEAVES.get(), LeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.GINKGO_LEAVES.get(), LeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.MAPLE_LEAVES.get(), LeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.FROST_LEAVES.get(), PetalParticle.Provider::new);
        event.registerSpriteSet(ModParticles.DAWN_REDWOOD_LEAVES.get(), LeafParticle.Provider::new);
        event.registerSpriteSet(ModParticles.JACARANDA_LEAVES.get(), PetalParticle.Provider::new);
        event.registerSpriteSet(ModParticles.TINTED_LEAVES.get(), LeafParticle.TintedProvider::new);
        event.registerSpriteSet(ModParticles.SPRUCE_LEAVES.get(), LeafParticle.TintedProvider::new);
        event.registerSpriteSet(ModParticles.WIND_GLOBAL.get(), WindParticle.GlobalProvider::new);
        event.registerSpriteSet(ModParticles.WIND_FAN.get(), WindParticle.FanProvider::new);
    }
}
