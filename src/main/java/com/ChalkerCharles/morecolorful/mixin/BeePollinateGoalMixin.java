package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Predicate;

@Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeePollinateGoal")
public abstract class BeePollinateGoalMixin {
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
