package com.ChalkerCharles.morecolorful.common.block.nature;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILeavesBlockExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class FallingLeavesBlock extends LeavesBlock implements ILeavesBlockExtension {
    private final Supplier<SimpleParticleType> particleType;
    public FallingLeavesBlock(Properties properties, Supplier<SimpleParticleType> particleType) {
        super(properties);
        this.particleType = particleType;
    }

    @Override
    public void moreColorful$makeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random) {
        if (random.nextInt(WeatherUtils.chanceByWind(level, 50)) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, particleType.get());
            }
        }
    }
}
