package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
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

@Mixin(targets = "Lnet/minecraft/world/entity/animal/Bee$BeePollinateGoal")
public abstract class BeePollinateGoalMixin {
    @Shadow
    @Mutable
    @Final
    private Predicate<BlockState> VALID_POLLINATION_BLOCKS;

    @Inject(method = "<init>(Lnet/minecraft/world/entity/animal/Bee;)V", at = @At("TAIL"))
    private void moreColorful$modify(Bee bee, CallbackInfo ci) {
        VALID_POLLINATION_BLOCKS = VALID_POLLINATION_BLOCKS.and(state -> !state.is(ModBlocks.CLOSED_DAYBLOOM));
    }
}
