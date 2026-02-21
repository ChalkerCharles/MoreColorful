package com.ChalkerCharles.morecolorful.client.particle;

import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.codec.StreamCodec;

public record BalloonParticleOption(Balloon.Variant variant) implements ParticleOptions {
    private static final BalloonParticleOption[] OPTIONS = new BalloonParticleOption[Balloon.Variant.size()];
    public static final MapCodec<BalloonParticleOption> CODEC = Balloon.Variant.CODEC
            .xmap(BalloonParticleOption::new, BalloonParticleOption::variant)
            .fieldOf("variant");
    public static final StreamCodec<? super ByteBuf, BalloonParticleOption> STREAM_CODEC = Balloon.Variant.STREAM_CODEC
            .map(BalloonParticleOption::new, BalloonParticleOption::variant);

    public static BalloonParticleOption get(Balloon.Variant variant) {
        int i = variant.getIndex();
        BalloonParticleOption option = OPTIONS[i];
        if (option == null) {
            option = new BalloonParticleOption(variant);
            OPTIONS[i] = option;
        }
        return option;
    }

    @Override
    public ParticleType<BalloonParticleOption> getType() {
        return ModParticles.BALLOON.get();
    }
}
