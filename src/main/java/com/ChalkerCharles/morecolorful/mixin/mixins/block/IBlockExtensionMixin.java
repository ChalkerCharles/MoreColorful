package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.mixin.extensions.IExplosionExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.extensions.IBlockExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IBlockExtension.class)
public interface IBlockExtensionMixin {
    @WrapOperation(method = "onBlockExploded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean onBlockExploded(Level instance, BlockPos pos, BlockState newState, int flags, Operation<Boolean> original,
                                    BlockState state, @Local(argsOnly = true) Explosion explosion) {
        if (IExplosionExtension.ignoreFluid(explosion)) {
            if (state.getBlock() instanceof LiquidBlock) {
                return false;
            } else if (!state.getFluidState().isEmpty()) {
                return instance.setBlock(pos, state.getFluidState().createLegacyBlock(), flags);
            }
        }
        return original.call(instance, pos, newState, flags);
    }
}
