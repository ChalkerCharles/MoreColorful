package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.item.CreativeModeTabs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CreativeModeTabs.class)
public abstract class CreativeModeTabsMixin {
    @ModifyExpressionValue(method = "lambda$bootstrap$17", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/flag/FeatureFlagSet;contains(Lnet/minecraft/world/flag/FeatureFlag;)Z"))
    private static boolean bootstrap(boolean original) {
        return true;
    }
}
