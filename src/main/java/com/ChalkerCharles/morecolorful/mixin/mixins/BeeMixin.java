package com.ChalkerCharles.morecolorful.mixin.mixins;

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
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal implements NeutralMob, FlyingAnimal {
    private BeeMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "isFlowerValid", at = @At("HEAD"), cancellable = true)
    private void isFlowerValid(BlockPos pPos, CallbackInfoReturnable<Boolean> cir) {
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

    @Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeePollinateGoal")
    private static abstract class BeePollinateGoalMixin {
        @Shadow
        @Final
        @Mutable
        private Predicate<BlockState> VALID_POLLINATION_BLOCKS;

        @Inject(method = "<init>", at = @At("TAIL"))
        private void setValidBlocks(Bee bee, CallbackInfo ci) {
            VALID_POLLINATION_BLOCKS = VALID_POLLINATION_BLOCKS.and(state ->
                    !(state.is(HolderSet.direct(
                            ModBlocks.CLOSED_DAYBLOOM,
                            ModBlocks.CLOSED_WATER_LILY,
                            ModBlocks.CLOSED_WHITE_WATER_LILY,
                            ModBlocks.CLOSED_BLUE_WATER_LILY)
                    ))
            );
        }
    }
}
