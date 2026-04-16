package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.particles.*;
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
        event.registerSpriteSet(ModParticles.CONFETTI.get(), ConfettiParticle.Provider::new);
        event.registerSpriteSet(ModParticles.SPARKLER_SPARKLE.get(), SparklerParticles.SparkleProvider::new);
        event.registerSprite(ModParticles.SPARKLER_GLITTER.get(), SparklerParticles::createGlitter);
        event.registerSpecial(ModParticles.BALLOON.get(), BalloonScrapParticle::create);
        event.registerSpriteSet(ModParticles.SMOKE_BOMB.get(), SmokeBombParticles.SmokeProvider::new);
        event.registerSprite(ModParticles.SMOKE_BOMB_FLASH.get(), SmokeBombParticles::createFlash);
    }
}
