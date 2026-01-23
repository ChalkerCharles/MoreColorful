package com.ChalkerCharles.morecolorful.mixin.mixins.client.particle;

import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.client.particle.*;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = {
        BaseAshSmokeParticle.class,
        DustParticle.class,
        FallingDustParticle.class,
        ExplodeParticle.class,
        FireworkParticles.OverlayParticle.class,
        LavaParticle.class,
        NoteParticle.class,
        PlayerCloudParticle.class,
        PortalParticle.class,
        SnowflakeParticle.class,
        SpellParticle.class
},
        targets = "net.minecraft.client.particle.DripParticle$FallingParticle"
)
public abstract class WindSensitiveParticlesMixin implements WindSensitive {
}
