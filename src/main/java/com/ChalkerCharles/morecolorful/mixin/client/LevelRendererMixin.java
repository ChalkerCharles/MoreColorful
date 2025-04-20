package com.ChalkerCharles.morecolorful.mixin.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.SectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {
    @Shadow
    @Nullable
    private ClientLevel level;

    @Inject(method = "compileSections", at = @At("HEAD"))
    private void compileSections(Camera pCamera, CallbackInfo ci, @Share("thermalEngine") LocalRef<LevelThermalEngine> thermalEngine) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (this.level != null) {
            thermalEngine.set(((ILevelExtension) this.level).moreColorful$getThermalEngine());
        }
    }

    @ModifyExpressionValue(method = "compileSections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;lightOnInSection(Lnet/minecraft/core/SectionPos;)Z"))
    private boolean compileSections(boolean original, @Share("thermalEngine") LocalRef<LevelThermalEngine> thermalEngine, @Local SectionPos sectionPos) {
        if (Config.THERMAL_SYSTEM.isFalse()) return original;
        return original || thermalEngine.get().temperatureOnInSection(sectionPos);
    }
}
