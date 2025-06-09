package com.ChalkerCharles.morecolorful.common.block.nature;

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

public class PetalLeavesBlock extends LeavesBlock {
    private final Supplier<SimpleParticleType> particleType;

    public PetalLeavesBlock(Properties properties, Supplier<SimpleParticleType> particleType) {
        super(properties);
        this.particleType = particleType;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource randomSource) {
        super.animateTick(state, level, pos, randomSource);
        if (randomSource.nextInt(WeatherUtils.chanceByWind(level, 10)) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, randomSource, particleType.get());
            }
        }
    }
}
