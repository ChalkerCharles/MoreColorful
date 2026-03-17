package com.ChalkerCharles.morecolorful.mixin.mixins.client.render;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.compat.SodiumCompat;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataUpdateDispatcher;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WindFrustum;
import com.ChalkerCharles.morecolorful.common.ModSounds;
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
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
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
    @Shadow
    private int ticks;
    @Nullable
    @Unique
    private WavyDataUpdateDispatcher<?> moreColorful$dispatcher;
    @Unique
    private final ObjectArrayList<SectionRenderDispatcher.RenderSection> moreColorful$windySections = new ObjectArrayList<>(10000);
    @Unique
    private Frustum moreColorful$windFrustum;

    @WrapOperation(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getHeight(Lnet/minecraft/world/level/levelgen/Heightmap$Types;II)I"))
    private int renderRain$umbrellaBlock(Level instance, Heightmap.Types type, int x, int z, Operation<Integer> original) {
        double d = this.level == null ? Double.NaN : LevelSavedData.getCanopy(this.level, x, z);
        int h = original.call(instance, type, x, z);
        if (Double.isNaN(d)) return h;
        return Math.max(h, Mth.ceil(d));
    }

    @WrapOperation(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;addVertex(FFF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer renderSnowAndRain(BufferBuilder instance, float pX, float pY, float pZ, Operation<VertexConsumer> original) {
        if (!RenderUtils.isCalm && Config.windAndRain) {
            float f = pY * RenderUtils.windStrength * 0.04F;
            pX -= RenderUtils.windDir.x * f;
            pZ -= RenderUtils.windDir.y * f;
        }
        return original.call(instance, pX, pY, pZ);
    }

    @WrapOperation(method = "renderSnowAndRain", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;setUv(FF)Lcom/mojang/blaze3d/vertex/VertexConsumer;"),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LevelRenderer;SNOW_LOCATION:Lnet/minecraft/resources/ResourceLocation;")))
    private VertexConsumer renderSnow(VertexConsumer instance, float u, float v, Operation<VertexConsumer> original, @Local(argsOnly = true) float partialTick) {
        if (!RenderUtils.isCalm && Config.windAndRain) {
            float f = ((this.ticks & 511) + partialTick) / 512.0F;
            v -= RenderUtils.windStrength * f;
        }
        return original.call(instance, u, v);
    }

    @Inject(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;containing(Lnet/minecraft/core/Position;)Lnet/minecraft/core/BlockPos;", shift = At.Shift.AFTER))
    private void tickRain$umbrellaPos(CallbackInfo ci, @Share("umbrellaPos")LocalRef<Vec3> umbrellaPos) {
        umbrellaPos.set(null);
    }

    @ModifyExpressionValue(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelReader;getHeightmapPos(Lnet/minecraft/world/level/levelgen/Heightmap$Types;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/BlockPos;", ordinal = 0))
    private BlockPos tickRain$umbrellaBlock(BlockPos original, @Share("umbrellaHeight")LocalDoubleRef umbrellaHeight) {
        int x = original.getX(), y = original.getY(), z = original.getZ();
        double d = this.level == null ? Double.NaN : LevelSavedData.getCanopy(this.level, x, z);
        if (!Double.isNaN(d) && d > y) {
            umbrellaHeight.set(d);
            return original.atY(Mth.ceil(d));
        } else {
            umbrellaHeight.set(Double.NaN);
            return original;
        }
    }

    @ModifyExpressionValue(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;"))
    private BlockPos tickRain$setUmbrellaPos(BlockPos original, @Share("umbrellaPos")LocalRef<Vec3> umbrellaPos, @Share("umbrellaHeight")LocalDoubleRef umbrellaHeight) {
        double d = umbrellaHeight.get();
        if (!Double.isNaN(d)) {
            umbrellaPos.set(new Vec3(original.getX() + 0.5, d, original.getZ() + 0.5));
        }
        return original;
    }

    @WrapOperation(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/VoxelShape;max(Lnet/minecraft/core/Direction$Axis;DD)D"))
    private double tickRain$setHeight(VoxelShape instance, Direction.Axis axis, double primaryPosition, double secondaryPosition, Operation<Double> original,
                                          @Share("umbrellaHeight")LocalDoubleRef umbrellaHeight) {
        double d = umbrellaHeight.get();
        if (!Double.isNaN(d)) {
            return Mth.frac(d);
        }
        return original.call(instance, axis, primaryPosition, secondaryPosition);
    }

    @Inject(method = "tickRain", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/LevelRenderer;rainSoundTime:I", opcode = Opcodes.PUTFIELD, ordinal = 1, shift = At.Shift.AFTER))
    private void tickRain$playSound(CallbackInfo ci, @Share("umbrellaPos")LocalRef<Vec3> umbrellaPos) {
        Vec3 pos = umbrellaPos.get();
        if (pos != null && this.level != null) {
            this.level.playLocalSound(pos.x, pos.y, pos.z, ModSounds.WEATHER_RAIN_UMBRELLA.get(), SoundSource.WEATHER, 0.8F, 1.0F, false);
        }
    }

    @WrapWithCondition(method = "tickRain", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/ClientLevel;playLocalSound(Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private boolean tickRain$cancelVanillaSound(ClientLevel instance, BlockPos pos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch, boolean distanceDelay,
                                                @Share("umbrellaPos")LocalRef<Vec3> umbrellaPos) {
        return umbrellaPos.get() == null;
    }

    @Inject(method = "prepareCullFrustum", at = @At("TAIL"))
    private void prepareCullFrustum(Vec3 pCameraPosition, Matrix4f pFrustumMatrix, Matrix4f pProjectionMatrix, CallbackInfo ci) {
        if (RenderUtils.SODIUM_ON || !Config.wavyBlocks) return;
        this.moreColorful$windFrustum = new WindFrustum(pFrustumMatrix, pProjectionMatrix);
        this.moreColorful$windFrustum.prepare(pCameraPosition.x, pCameraPosition.y, pCameraPosition.z);
    }

    @Inject(method = "applyFrustum", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/SectionOcclusionGraph;addSectionsInFrustum(Lnet/minecraft/client/renderer/culling/Frustum;Ljava/util/List;)V", shift = At.Shift.AFTER))
    private void applyFrustum(Frustum pFrustum, CallbackInfo ci) {
        if (RenderUtils.SODIUM_ON || !Config.wavyBlocks) return;
        this.moreColorful$windySections.clear();
        this.sectionOcclusionGraph.addSectionsInFrustum(this.moreColorful$windFrustum, this.moreColorful$windySections);
    }

    @Inject(method = "setLevel", at = @At(value = "INVOKE", target = "Ljava/util/Set;clear()V"))
    private void setLevel(ClientLevel pLevel, CallbackInfo ci) {
        if (!Config.wavyBlocks) return;
        if (this.moreColorful$dispatcher != null) {
            this.moreColorful$dispatcher.dispose();
        }
        this.moreColorful$dispatcher = null;
        this.moreColorful$windySections.clear();
    }

    @Inject(method = "allChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/chunk/SectionRenderDispatcher;blockUntilClear()V"))
    private void allChanged(CallbackInfo ci) {
        if (!Config.wavyBlocks) return;
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
        if (this.moreColorful$dispatcher == null || !Config.wavyBlocks) return;
        this.moreColorful$dispatcher.setCamera(pCamera.getPosition());
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

    @WrapOperation(method = "renderEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;lerp(FFF)F"))
    private float renderEntity$fixRot(float delta, float start, float end, Operation<Float> original) {
        return Mth.rotLerp(delta, start, end);
    }

    @ModifyVariable(method = "renderClouds", at = @At(value = "STORE", ordinal = 0), ordinal = 5)
    private double renderClouds$x(double d2, @Local(argsOnly = true) float partialTick) {
        if (Config.windAndCloud) {
            return d2 - RenderUtils.getCloudMovementX(partialTick);
        }
        return d2;
    }

    @ModifyVariable(method = "renderClouds", at = @At(value = "STORE", ordinal = 0), ordinal = 7)
    private double renderClouds$z(double d4, @Local(argsOnly = true) float partialTick) {
        if (Config.windAndCloud) {
            return d4 - RenderUtils.getCloudMovementZ(partialTick);
        }
        return d4;
    }

    @Inject(method = "compileSections", at = @At("HEAD"))
    private void compileSections(Camera pCamera, CallbackInfo ci, @Share("thermal") LocalRef<ILevelThermalEngine> thermalEngine, @Share("vent") LocalRef<ILevelVentEngine> ventEngine) {
        if (this.level != null) {
            if (Config.thermalSystem)
                thermalEngine.set(LevelSavedData.getThermalEngine(this.level));
            if (Config.windSystem)
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
