package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.particles.LeafParticle;
import com.ChalkerCharles.morecolorful.client.particle.particles.PetalParticle;
import net.minecraft.client.particle.CherryParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleProvidersRegistry {
    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.CRABAPPLE_LEAVES.get(), spriteSet -> (type, level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> new PetalParticle(
                level, pX, pY, pZ, spriteSet
        ));
        event.registerSpriteSet(ModParticles.WHITE_CHERRY_LEAVES.get(), spriteSet -> (type, level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> new PetalParticle(
                level, pX, pY, pZ, spriteSet
        ));
        event.registerSpriteSet(ModParticles.AUTUMN_BIRCH_LEAVES.get(), spriteSet -> (type, level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> new LeafParticle(
                level, pX, pY, pZ, spriteSet
        ));
        event.registerSpriteSet(ModParticles.GINKGO_LEAVES.get(), spriteSet -> (type, level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> new LeafParticle(
                level, pX, pY, pZ, spriteSet
        ));
        event.registerSpriteSet(ModParticles.MAPLE_LEAVES.get(), spriteSet -> (type, level, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed) -> new LeafParticle(
                level, pX, pY, pZ, spriteSet
        ));
    }
}
