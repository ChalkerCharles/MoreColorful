package com.ChalkerCharles.morecolorful.util.client;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
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

    public static boolean wavyBlocks() {
        return RenderUtils.wavyBlocks;
    }

    public static boolean windParticles() {
        return RenderUtils.windParticles;
    }

    public static void clearDataInLine(Level level, BlockPos pos) {
        RenderUtils.clearDataInLine(level, pos);
    }

    public static double distToCameraSq(BlockPos pos) {
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        return Minecraft.getInstance().gameRenderer.getMainCamera().getPosition().distanceToSqr(x, y, z);
    }

    public static void playRustlingSound(Level level, BlockPos pos, RandomSource random) {
        if (RenderUtils.windSounds && !RenderUtils.isCalm && !RenderUtils.leavesRustling && distToCameraSq(pos) < 576) {
            int chance = WeatherUtils.chanceByWind(level, pos, 240);
            if (random.nextInt(chance) == 0) {
                float volume = 1.0F - chance * 0.00416667F;
                float pitch = volume * 0.5F + 0.5F;
                level.playLocalSound(pos, ModSounds.LEAVES_RUSTLE.get(), SoundSource.BLOCKS, volume, pitch, false);
                RenderUtils.leavesRustling = true;
            }
        }
    }
}
