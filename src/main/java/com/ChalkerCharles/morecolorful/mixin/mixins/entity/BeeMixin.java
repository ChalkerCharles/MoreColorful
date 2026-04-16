package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.common.entity.animal.AbstractMoth;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Bee.class)
public abstract class BeeMixin extends Animal {
    private BeeMixin(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "doHurtTarget", at = @At("HEAD"))
    private void doHurtTarget$checkHat(Entity pEntity, CallbackInfoReturnable<Boolean> cir, @Share("hat")LocalBooleanRef hat) {
        hat.set(pEntity instanceof LivingEntity living && living.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.BEEKEEPING_HAT));
    }

    @WrapWithCondition(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z"))
    private boolean doHurtTarget$stopPoison(LivingEntity instance, MobEffectInstance effect, Entity entity, @Share("hat")LocalBooleanRef hat) {
        return !hat.get();
    }

    @WrapWithCondition(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Bee;setHasStung(Z)V"))
    private boolean doHurtTarget$keepSting(Bee instance, boolean pHasStung, @Share("hat")LocalBooleanRef hat) {
        return !hat.get();
    }

    @WrapWithCondition(method = "doHurtTarget", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Bee;stopBeingAngry()V"))
    private boolean doHurtTarget$keepAngry(Bee instance, @Share("hat")LocalBooleanRef hat) {
        return !hat.get();
    }

    @ModifyReturnValue(method = "isFlowerValid", at = @At("TAIL"))
    private boolean isFlowerValid(boolean original, BlockPos pPos) {
        return original && !this.level().getBlockState(pPos).is(AbstractMoth.INVALID_FLOWERS);
    }

    @Mixin(targets = "net.minecraft.world.entity.animal.Bee$BeePollinateGoal")
    private static abstract class BeePollinateGoalMixin {
        @ModifyReturnValue(method = "lambda$new$0", at = @At("TAIL"))
        private static boolean modifyPredicate(boolean original, BlockState state) {
            return original && !state.is(AbstractMoth.INVALID_FLOWERS);
        }
    }
}
