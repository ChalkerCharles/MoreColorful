package com.ChalkerCharles.morecolorful.mixin.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowLayerBlock.class)
public abstract class SnowLayerBlockMixin extends Block {
    public SnowLayerBlockMixin(Properties properties) {
        super(properties);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if (((ILevelExtension) pLevel).moreColorful$getTemperature(pPos) > 5) {
                dropResources(pState, pLevel, pPos);
                pLevel.removeBlock(pPos, false);
            }
            ci.cancel();
        }
    }
}
