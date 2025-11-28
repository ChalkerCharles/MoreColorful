package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrostedIceBlock.class)
public abstract class FrostedIceBlockMixin extends IceBlock {
    private FrostedIceBlockMixin(Properties properties) {
        super(properties);
    }

    @Shadow
    protected abstract boolean fewerNeigboursThan(BlockGetter pLevel, BlockPos pPos, int pNeighborsRequired);

    @Shadow
    protected abstract boolean slightlyMelt(BlockState pState, Level pLevel, BlockPos pPos);

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if ((pRandom.nextInt(3) == 0 || this.fewerNeigboursThan(pLevel, pPos, 4))
                    && LevelSavedData.getTemperature(pLevel, pPos) > 5 - pState.getValue(FrostedIceBlock.AGE)
                    && this.slightlyMelt(pState, pLevel, pPos)) {
                BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

                for (Direction direction : Maths.DIRECTIONS) {
                    mutableBlockPos.setWithOffset(pPos, direction);
                    BlockState blockstate = pLevel.getBlockState(mutableBlockPos);
                    if (blockstate.is(this) && !this.slightlyMelt(blockstate, pLevel, mutableBlockPos)) {
                        pLevel.scheduleTick(mutableBlockPos, this, Mth.nextInt(pRandom, 20, 40));
                    }
                }
                ci.cancel();
            }
        }
    }
}
