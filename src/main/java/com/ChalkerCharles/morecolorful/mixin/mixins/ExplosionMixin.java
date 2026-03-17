package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.mixin.extensions.IExplosionExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(Explosion.class)
public abstract class ExplosionMixin implements IExplosionExtension {
    @Unique
    private boolean moreColorful$ignoreFluid;

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ExplosionDamageCalculator;getBlockExplosionResistance(Lnet/minecraft/world/level/Explosion;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Ljava/util/Optional;"))
    private Optional<Float> explode$explosionResistance(ExplosionDamageCalculator instance, Explosion pExplosion, BlockGetter pReader, BlockPos pPos, BlockState pState, FluidState pFluid, Operation<Optional<Float>> original) {
        if (this.moreColorful$ignoreFluid) {
            if (pState.getBlock() instanceof LiquidBlock) {
                return Optional.empty();
            } else {
                return original.call(instance, pExplosion, pReader, pPos, pState, Fluids.EMPTY.defaultFluidState());
            }
        }
        return original.call(instance, pExplosion, pReader, pPos, pState, pFluid);
    }

    @WrapOperation(method = "explode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ExplosionDamageCalculator;shouldBlockExplode(Lnet/minecraft/world/level/Explosion;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;F)Z"))
    private boolean explode$shouldBlockExplode(ExplosionDamageCalculator instance, Explosion pExplosion, BlockGetter pReader, BlockPos pPos, BlockState pState, float pPower, Operation<Boolean> original) {
        if (this.moreColorful$ignoreFluid && pState.getBlock() instanceof LiquidBlock) {
            return false;
        }
        return original.call(instance, pExplosion, pReader, pPos, pState, pPower);
    }

    @Override
    public boolean moreColorful$ignoreFluid() {
        return this.moreColorful$ignoreFluid;
    }

    @Override
    public void moreColorful$setIgnoreFluid() {
        this.moreColorful$ignoreFluid = true;
    }
}
