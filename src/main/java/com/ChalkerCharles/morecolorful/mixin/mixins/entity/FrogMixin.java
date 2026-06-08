package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.entity.animal.ModFrogVariants;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Frog.class)
public abstract class FrogMixin {
    @WrapOperation(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/frog/Frog;setVariant(Lnet/minecraft/core/Holder;)V", ordinal = 0))
    private void finalizeSpawn$0(Frog instance, Holder<FrogVariant> variant, Operation<Void> original, ServerLevelAccessor level) {
        if (level.getRandom().nextInt(10) == 0) {
            original.call(instance, ModFrogVariants.BROWN);
        } else {
            original.call(instance, variant);
        }
    }

    @WrapOperation(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/frog/Frog;setVariant(Lnet/minecraft/core/Holder;)V", ordinal = 1))
    private void finalizeSpawn$1(Frog instance, Holder<FrogVariant> variant, Operation<Void> original, ServerLevelAccessor level) {
        if (level.getRandom().nextInt(10) == 0) {
            original.call(instance, ModFrogVariants.TOMATO);
        } else {
            original.call(instance, variant);
        }
    }

    @WrapOperation(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/frog/Frog;setVariant(Lnet/minecraft/core/Holder;)V", ordinal = 2))
    private void finalizeSpawn$2(Frog instance, Holder<FrogVariant> variant, Operation<Void> original, ServerLevelAccessor level) {
        if (level.getRandom().nextInt(10) == 0) {
            original.call(instance, ModFrogVariants.BLUE);
        } else {
            original.call(instance, variant);
        }
    }
}
