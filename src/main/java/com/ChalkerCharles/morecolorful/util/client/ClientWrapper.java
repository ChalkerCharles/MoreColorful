package com.ChalkerCharles.morecolorful.util.client;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;
import java.util.function.BiFunction;

public class ClientWrapper {
    private static final ParticleOptions AZALEA = ColorParticleOption.create(ModParticles.TINTED_LEAVES.get(), 0xff70922d);
    private static final BiFunction<Level, BlockPos, ParticleOptions> LEAF_PARTICLE_CREATOR = (level, pos) ->
            ColorParticleOption.create(ModParticles.TINTED_LEAVES.get(), RenderUtils.getClientLeafTintColor(level, pos));
    private static final BiFunction<Level, BlockPos, ParticleOptions> SPRUCE_PARTICLE_CREATOR = (level, pos) ->
            ColorParticleOption.create(ModParticles.SPRUCE_LEAVES.get(), RenderUtils.getClientLeafTintColor(level, pos));
    private static final BiFunction<Level, BlockPos, ParticleOptions> AZALEA_GETTER = (level, pos) -> AZALEA;
    public static final Map<Block, BiFunction<Level, BlockPos, ParticleOptions>> LEAVES_PARTICLES = Map.of(
            Blocks.OAK_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.SPRUCE_LEAVES, SPRUCE_PARTICLE_CREATOR,
            Blocks.BIRCH_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.JUNGLE_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.ACACIA_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.DARK_OAK_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.MANGROVE_LEAVES, LEAF_PARTICLE_CREATOR,
            Blocks.AZALEA_LEAVES, AZALEA_GETTER,
            Blocks.FLOWERING_AZALEA_LEAVES, AZALEA_GETTER
    );

    public static boolean isClientWindOn() {
        return RenderUtils.isClientWindOn;
    }

    public static void clearDataInLine(Level level, BlockPos pos) {
        RenderUtils.clearDataInLine(level, pos);
    }
}
