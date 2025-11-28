package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

public interface ILeavesBlockExtension {
    void moreColorful$makeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random);
}
