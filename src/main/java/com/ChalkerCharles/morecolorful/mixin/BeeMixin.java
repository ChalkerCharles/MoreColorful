package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
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
        if (this.level().isLoaded(pPos) && this.level().getBlockState(pPos).is(ModBlocks.CLOSED_DAYBLOOM)) {
            cir.setReturnValue(false);
        }
    }
}
