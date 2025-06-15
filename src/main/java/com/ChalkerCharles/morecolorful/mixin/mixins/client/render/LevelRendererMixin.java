package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.shader.ModRenderTypes;
import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.util.RenderUtils;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.core.SectionPos;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements ILevelRendererExtension {
    @Shadow
    @Nullable
    private ClientLevel level;

    @Shadow
    protected abstract void renderSectionLayer(RenderType pRenderType, double pX, double pY, double pZ, Matrix4f pFrustrumMatrix, Matrix4f pProjectionMatrix);

    @Shadow
    @Nullable
    private ViewArea viewArea;

    @Inject(method = "renderLevel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSectionLayer(Lnet/minecraft/client/renderer/RenderType;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
                    shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;cutoutMipped()Lnet/minecraft/client/renderer/RenderType;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/TextureAtlas;restoreLastBlurMipmap()V")
            )
    )
    private void renderLevel(DeltaTracker pDeltaTracker, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci,
                             @Local(ordinal = 0) double d0, @Local(ordinal = 1) double d1, @Local(ordinal = 2) double d2) {
        this.renderSectionLayer(ModRenderTypes.WAVY_CUTOUT_MIPPED, d0, d1, d2, pFrustumMatrix, pProjectionMatrix);
    }

    @Inject(method = "renderLevel",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/LevelRenderer;renderSectionLayer(Lnet/minecraft/client/renderer/RenderType;DDDLorg/joml/Matrix4f;Lorg/joml/Matrix4f;)V",
                    shift = At.Shift.AFTER),
            slice = @Slice(
                    from = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;cutout()Lnet/minecraft/client/renderer/RenderType;"),
                    to = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/DimensionSpecialEffects;constantAmbientLight()Z")
            )
    )
    private void renderLevel$1(DeltaTracker pDeltaTracker, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci,
                               @Local(ordinal = 0) double d0, @Local(ordinal = 1) double d1, @Local(ordinal = 2) double d2) {
        this.renderSectionLayer(ModRenderTypes.WAVY_CUTOUT, d0, d1, d2, pFrustumMatrix, pProjectionMatrix);
    }

    @Inject(method = "compileSections", at = @At("HEAD"))
    private void compileSections(Camera pCamera, CallbackInfo ci, @Share("thermalEngine") LocalRef<ILevelThermalEngine> thermalEngine) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (this.level != null) {
            thermalEngine.set(((ILevelExtension) this.level).moreColorful$getThermalEngine());
        }
    }

    @ModifyExpressionValue(method = "compileSections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;lightOnInSection(Lnet/minecraft/core/SectionPos;)Z"))
    private boolean compileSections(boolean original, @Share("thermalEngine") LocalRef<ILevelThermalEngine> thermalEngine, @Local SectionPos sectionPos) {
        if (Config.THERMAL_SYSTEM.isFalse()) return original;
        return original || thermalEngine.get().temperatureOnInSection(sectionPos);
    }

    @Override
    public void moreColorful$updateWavySections() {
        if (this.viewArea == null) return;
        SectionPos sectionPos;
        for (SectionRenderDispatcher.RenderSection section : this.viewArea.sections) {
            SectionRenderDispatcher.CompiledSection compiled = section.getCompiled();
            if (!compiled.isEmpty(ModRenderTypes.WAVY_CUTOUT_MIPPED)
                    || !compiled.isEmpty(ModRenderTypes.WAVY_CUTOUT)
                    || !compiled.isEmpty(RenderType.translucent())) {
                section.setDirty(false);
                sectionPos = SectionPos.of(section.getOrigin());
                RenderUtils.VERTICES.remove(sectionPos);
                WeatherUtils.WINDY_BLOCKS.remove(sectionPos);
            }
        }
    }
}
