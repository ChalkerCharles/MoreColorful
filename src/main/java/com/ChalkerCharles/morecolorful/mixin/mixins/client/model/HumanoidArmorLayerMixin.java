package com.ChalkerCharles.morecolorful.mixin.mixins.client.model;

import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.item.ArmorItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {
    @Inject(method = "renderArmorPiece", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ArmorItem;getEquipmentSlot()Lnet/minecraft/world/entity/EquipmentSlot;"), cancellable = true)
    private void renderArmorPiece(CallbackInfo ci, @Local ArmorItem item) {
        if (ItemUtils.isCustomHat(item)) {
            ci.cancel();
        }
    }
}
