package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal {
    @Unique
    private static final HolderSet<Block> MORECOLORFUL_INVALID_FLOWERS = HolderSet.direct(
            ModBlocks.CLOSED_DAYBLOOM,
            ModBlocks.CLOSED_WATER_LILY,
            ModBlocks.CLOSED_WHITE_WATER_LILY,
            ModBlocks.CLOSED_BLUE_WATER_LILY
    );

    private BeeMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @ModifyReturnValue(method = "isFlowerValid", at = @At("TAIL"))
    private boolean isFlowerValid(boolean original, BlockPos pPos) {
        return original && !this.level().getBlockState(pPos).is(MORECOLORFUL_INVALID_FLOWERS);
    }

    @Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeePollinateGoal")
    private static abstract class BeePollinateGoalMixin {
        @ModifyReturnValue(method = "lambda$new$0", at = @At("TAIL"))
        private static boolean modifyPredicate(boolean original, BlockState state) {
            return original && !state.is(MORECOLORFUL_INVALID_FLOWERS);
        }
    }
}
