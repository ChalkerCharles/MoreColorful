package com.ChalkerCharles.morecolorful.mixin.mixins.item;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.item.LeadItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LeadItem.class)
public abstract class LeadItemMixin {
    @Inject(method = "bindPlayerMobs", at = @At("HEAD"))
    private static void bindPlayerMobs$0(CallbackInfoReturnable<InteractionResult> cir, @Share("b")LocalBooleanRef bool) {
        bool.set(false);
    }

    @WrapOperation(method = "bindPlayerMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;setLeashedTo(Lnet/minecraft/world/entity/Entity;Z)V"))
    private static void bindPlayerMobs$1(Leashable instance, Entity leashHolder, boolean broadcastPacket, Operation<Void> original,
                                         @Share("b")LocalBooleanRef bool) {
        if (Config.enhancedLeash) {
            if (ILeashableExtension.canHaveALeashAttachedTo(instance, leashHolder)) {
                original.call(instance, leashHolder, broadcastPacket);
                bool.set(true);
            }
        } else {
            original.call(instance, leashHolder, broadcastPacket);
        }
    }

    @ModifyExpressionValue(method = "bindPlayerMobs", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z"))
    private static boolean bindPlayerMobs$2(boolean original, @Share("b")LocalBooleanRef bool) {
        if (Config.enhancedLeash) {
            return !bool.get();
        } else {
            return original;
        }
    }
}
