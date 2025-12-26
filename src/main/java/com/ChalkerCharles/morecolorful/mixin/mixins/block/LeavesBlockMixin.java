package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILeavesBlockExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

@Mixin(LeavesBlock.class)
public abstract class LeavesBlockMixin extends Block implements ILeavesBlockExtension {
    private LeavesBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "animateTick", at = @At("TAIL"))
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        this.moreColorful$makeFallingLeavesParticles(pLevel, pPos, pRandom);
        ClientWrapper.playRustlingSound(pLevel, pPos, pRandom);
    }

    @Override
    public void moreColorful$makeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random) {
        BiFunction<Level, BlockPos, ParticleOptions> function = ClientWrapper.LEAVES_PARTICLES.get(this);
        if (function == null) return;
        if (random.nextInt(WeatherUtils.chanceByWind(level, pos, 100)) == 0) {
            BlockPos blockpos = pos.below();
            BlockState blockstate = level.getBlockState(blockpos);
            if (!isFaceFull(blockstate.getCollisionShape(level, blockpos), Direction.UP)) {
                ParticleUtils.spawnParticleBelow(level, pos, random, function.apply(level, pos));
            }
        }
    }
}
