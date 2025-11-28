package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILeavesBlockExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CherryLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CherryLeavesBlock.class)
public abstract class CherryLeavesBlockMixin implements ILeavesBlockExtension {
    @ModifyExpressionValue(method = "animateTick", at = @At(value = "CONSTANT", args = "intValue=10"))
    public int animateTick(int original, BlockState state, Level level) {
        return WeatherUtils.chanceByWind(level, original);
    }

    @Override
    public void moreColorful$makeFallingLeavesParticles(Level level, BlockPos pos, RandomSource random) {
    }
}
