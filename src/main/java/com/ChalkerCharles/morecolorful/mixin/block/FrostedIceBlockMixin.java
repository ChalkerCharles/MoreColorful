package com.ChalkerCharles.morecolorful.mixin.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrostedIceBlock.class)
public abstract class FrostedIceBlockMixin {
    @Shadow
    protected abstract boolean fewerNeigboursThan(BlockGetter pLevel, BlockPos pPos, int pNeighborsRequired);

    @Shadow
    protected abstract boolean slightlyMelt(BlockState pState, Level pLevel, BlockPos pPos);

    @Shadow
    @Final
    public static IntegerProperty AGE;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if ((pRandom.nextInt(3) == 0 || this.fewerNeigboursThan(pLevel, pPos, 4))
                    && ((ILevelExtension) pLevel).moreColorful$getTemperature(pPos) > 5 - pState.getValue(AGE)
                    && this.slightlyMelt(pState, pLevel, pPos)) {
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (Direction direction : Direction.values()) {
                    blockpos$mutableblockpos.setWithOffset(pPos, direction);
                    BlockState blockstate = pLevel.getBlockState(blockpos$mutableblockpos);
                    if (blockstate.is(moreColorful$self()) && !this.slightlyMelt(blockstate, pLevel, blockpos$mutableblockpos)) {
                        pLevel.scheduleTick(blockpos$mutableblockpos, moreColorful$self(), Mth.nextInt(pRandom, 20, 40));
                    }
                }
                ci.cancel();
            }
        }
    }

    @Unique
    private FrostedIceBlock moreColorful$self() {
        return (FrostedIceBlock) (Object) this;
    }
}
