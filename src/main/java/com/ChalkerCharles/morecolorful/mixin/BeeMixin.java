package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal implements NeutralMob, FlyingAnimal {
    protected BeeMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "isFlowerValid(Lnet/minecraft/core/BlockPos;)Z", at = @At("HEAD"), cancellable = true)
    void moreColorful$isFlowerValid(BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
        BlockState state = this.level().getBlockState(pPos);
        if (this.level().isLoaded(pPos) && state.is(HolderSet.direct(
                ModBlocks.CLOSED_DAYBLOOM,
                ModBlocks.CLOSED_WATER_LILY,
                ModBlocks.CLOSED_WHITE_WATER_LILY,
                ModBlocks.CLOSED_BLUE_WATER_LILY)
        )) {
            cir.setReturnValue(false);
        }
    }
}
