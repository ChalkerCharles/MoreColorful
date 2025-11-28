package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataUpdateDispatcher;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WindFrustum;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IViewAreaExtension;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.client.WindSectionMap;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.SectionPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.List;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin implements ILevelRendererExtension {
    @Shadow
    @Nullable
    private ClientLevel level;
    @Shadow
    @Final
    private SectionOcclusionGraph sectionOcclusionGraph;
    @Shadow
    @Nullable
    private ViewArea viewArea;
    @Nullable
    @Unique
    private WavyDataUpdateDispatcher<?> moreColorful$dispatcher;
    @Unique
    private final ObjectArrayList<SectionRenderDispatcher.RenderSection> moreColorful$windySections = new ObjectArrayList<>(10000);
    @Unique
    private Frustum moreColorful$windFrustum;

    @Inject(method = "prepareCullFrustum", at = @At("TAIL"))
    private void prepareCullFrustum(Vec3 pCameraPosition, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci) {
        if (RenderUtils.SODIUM_ON || !RenderUtils.isClientWindOn) return;
        this.moreColorful$windFrustum = new WindFrustum(pFrustumMatrix, pProjectionMatrix);
        this.moreColorful$windFrustum.prepare(pCameraPosition.x, pCameraPosition.y, pCameraPosition.z);
    }

    @Inject(method = "applyFrustum", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SectionOcclusionGraph;addSectionsInFrustum(Lnet/minecraft/client/renderer/culling/Frustum;Ljava/util/List;)V", shift = At.Shift.AFTER))
    private void applyFrustum(Frustum pFrustum, CallbackInfo ci) {
        if (RenderUtils.SODIUM_ON || !RenderUtils.isClientWindOn) return;
        this.moreColorful$windySections.clear();
        this.sectionOcclusionGraph.addSectionsInFrustum(this.moreColorful$windFrustum, this.moreColorful$windySections);
    }

    @Inject(method = "setLevel", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V"))
    private void setLevel(ClientLevel pLevel, CallbackInfo ci) {
        boolean windOn = RenderUtils.setClientWindOn();
        if (!windOn) return;
        if (this.moreColorful$dispatcher != null) {
            this.moreColorful$dispatcher.dispose();
        }
        this.moreColorful$dispatcher = null;
        this.moreColorful$windySections.clear();
    }

    @Inject(method = "allChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher;blockUntilClear()V"))
    private void allChanged(CallbackInfo ci) {
        boolean windOn = RenderUtils.setClientWindOn();
        if (!windOn) return;
        if (this.moreColorful$dispatcher == null) {
            this.moreColorful$dispatcher = RenderUtils.SODIUM_ON
                    ? SodiumCompat.createDispatcher(Util.backgroundExecutor())
                    : WavyDataUpdateDispatcher.create(Util.backgroundExecutor());
        }
        this.moreColorful$dispatcher.clearBatchQueue();
        this.moreColorful$clearWindCache();
        this.moreColorful$windySections.clear();
    }

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;compileSections(Lnet/minecraft/client/Camera;)V", shift = At.Shift.AFTER))
    private void updateWindData(DeltaTracker pDeltaTracker, boolean pRenderBlockOutline, Camera pCamera, GameRenderer pGameRenderer, LightTexture pLightTexture, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci,
                                @Local ProfilerFiller profilerFiller) {
        if (this.moreColorful$dispatcher == null || !RenderUtils.isClientWindOn) return;
        this.moreColorful$dispatcher.setCamera(pCamera.getPosition());
        RenderUtils.setWindContext(this.level);
        profilerFiller.popPush("update_wind_data");
        this.moreColorful$dispatcher.uploadAllPending();
        if (RenderUtils.SODIUM_ON) {
            SodiumCompat.updateWindData(this.moreColorful$dispatcher);
        } else {
            var dispatcher = WavyDataUpdateDispatcher.ofDefault(this.moreColorful$dispatcher);
            for (SectionRenderDispatcher.RenderSection section : this.moreColorful$windySections) {
                dispatcher.trySchedule(IRenderSectionExtension.getWavyTask(section));
            }
        }
    }

    @Inject(method = "compileSections", at = @At("HEAD"))
    private void compileSections(Camera pCamera, CallbackInfo ci, @Share("thermal") LocalRef<ILevelThermalEngine> thermalEngine, @Share("vent") LocalRef<ILevelVentEngine> ventEngine) {
        if (this.level != null) {
            if (Config.THERMAL_SYSTEM.isTrue())
                thermalEngine.set(LevelSavedData.getThermalEngine(this.level));
            if (Config.WIND_SYSTEM.isTrue())
                ventEngine.set(LevelSavedData.getVentEngine(this.level));
        }
    }

    @ModifyExpressionValue(method = "compileSections", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/lighting/LevelLightEngine;lightOnInSection(Lnet/minecraft/core/SectionPos;)Z"))
    private boolean compileSections(boolean original, @Share("thermal") LocalRef<ILevelThermalEngine> thermalEngine, @Share("vent") LocalRef<ILevelVentEngine> ventEngine, @Local SectionPos sectionPos) {
        ILevelThermalEngine engine = thermalEngine.get();
        if (engine != null) {
            original = original || engine.temperatureOnInSection(sectionPos);
        }
        ILevelVentEngine engine1 = ventEngine.get();
        if (engine1 != null) {
            original = original || engine1.ventilationOnInSection(sectionPos);
        }
        return original;
    }

    @Override
    public String moreColorful$getStatistics() {
        if (this.moreColorful$dispatcher != null) {
            return this.moreColorful$dispatcher.getStats();
        }
        return "null";
    }

    @Override
    public void moreColorful$clearWindCache() {
        if (RenderUtils.SODIUM_ON) {
            SodiumCompat.clearWindCache();
        } else {
            if (this.viewArea == null) return;
            for (SectionRenderDispatcher.RenderSection section : this.viewArea.sections) {
                IRenderSectionExtension.getWavyTask(section).windMap.clear();
            }
        }
    }

    @Nullable
    @Override
    public WindSectionMap moreColorful$getWindSectionMap(int sectionX, int sectionY, int sectionZ) {
        WavyDataTask<?> task = RenderUtils.SODIUM_ON
                ? SodiumCompat.getWavyDataTask(sectionX, sectionY, sectionZ)
                : IViewAreaExtension.getWavyDataTask(this.viewArea, sectionX, sectionY, sectionZ);
        if (task == null) return null;
        return task.windMap;
    }

    @Override
    public void moreColorful$setGroupDirty(int sectionX, int sectionY, int sectionZ) {
        IViewAreaExtension.setGroupDirty(this.viewArea, sectionX, sectionY, sectionZ);
    }

    @Override
    public void moreColorful$setWindZones(List<WindZone> list, int sectionX, int sectionY, int sectionZ) {
        WavyDataTask<?> task = RenderUtils.SODIUM_ON
                ? SodiumCompat.getWavyDataTask(sectionX, sectionY, sectionZ)
                : IViewAreaExtension.getWavyDataTask(this.viewArea, sectionX, sectionY, sectionZ);
        if (task == null) return;
        task.setWindZones(list);
    }
}
