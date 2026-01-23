package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkHolderExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkMapExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IWorldGenContextExtension;
import com.ChalkerCharles.morecolorful.util.Self;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.DataFixer;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ChunkTaskPriorityQueueSorter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.util.thread.ProcessorHandle;
import net.minecraft.util.thread.ProcessorMailbox;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin implements IChunkMapExtension, Self<ChunkMap> {
    @Shadow
    @Final
    private WorldGenContext worldGenContext;
    @Shadow
    @Final
    ServerLevel level;
    @Shadow
    @Final
    private ChunkTaskPriorityQueueSorter queueSorter;
    @Unique
    @Nullable
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;
    @Unique
    @Nullable
    private ThreadedLevelVentEngine moreColorful$ventEngine;

    @ModifyExpressionValue(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"))
    private ImmutableList<ProcessorHandle<?>> modifyQueueSorter(ImmutableList<ProcessorHandle<?>> original,
                                                                @Share("temperature") LocalRef<ProcessorMailbox<Runnable>> temperatureMailbox,
                                                                @Share("vent") LocalRef<ProcessorMailbox<Runnable>> ventMailbox,
                                                                @Local(argsOnly = true) Executor pDispatcher) {
        ImmutableList.Builder<ProcessorHandle<?>> builder = ImmutableList.<ProcessorHandle<?>>builder().addAll(original);
        if (Config.thermalSystem) {
            temperatureMailbox.set(ProcessorMailbox.create(pDispatcher, "temperature"));
            builder.add(temperatureMailbox.get());
        }
        if (Config.windSystem) {
            ventMailbox.set(ProcessorMailbox.create(pDispatcher, "ventilation"));
            builder.add(ventMailbox.get());
        }
        return builder.build();
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addThermalEngine(
            ServerLevel pLevel,
            LevelStorageSource.LevelStorageAccess pLevelStorageAccess,
            DataFixer pFixerUpper,
            StructureTemplateManager pStructureManager,
            Executor pDispatcher,
            BlockableEventLoop<Runnable> pMainThreadExecutor,
            LightChunkGetter pLightChunk,
            ChunkGenerator pGenerator,
            ChunkProgressListener pProgressListener,
            ChunkStatusUpdateListener pChunkStatusListener,
            Supplier<DimensionDataStorage> pOverworldDataStorage,
            int pViewDistance,
            boolean pSync,
            CallbackInfo ci,
            @Share("temperature") LocalRef<ProcessorMailbox<Runnable>> mailbox,
            @Share("vent") LocalRef<ProcessorMailbox<Runnable>> mailbox1) {
        ProcessorMailbox<Runnable> temperatureMailbox = mailbox.get();
        ProcessorMailbox<Runnable> ventMailbox = mailbox1.get();
        if (temperatureMailbox != null) {
            this.moreColorful$thermalEngine = new ThreadedLevelThermalEngine(
                    (ChunkSource) pLightChunk, moreColorful$self(), temperatureMailbox, this.queueSorter.getProcessor(temperatureMailbox, false)
            );
        }
        if (ventMailbox != null) {
            this.moreColorful$ventEngine = new ThreadedLevelVentEngine(
                    (ChunkSource) pLightChunk, moreColorful$self(), WeatherUtils.isWindy(this.level), ventMailbox, this.queueSorter.getProcessor(ventMailbox, false)
            );
        }
        IWorldGenContextExtension.setThermalEngine(this.worldGenContext, this.moreColorful$thermalEngine);
        IWorldGenContextExtension.setVentEngine(this.worldGenContext, this.moreColorful$ventEngine);
    }

    @WrapOperation(method = "updateChunkScheduling(JILnet/minecraft/server/level/ChunkHolder;I)Lnet/minecraft/server/level/ChunkHolder;",
            at = @At(value = "NEW", args = "class=net/minecraft/server/level/ChunkHolder"))
    private ChunkHolder updateChunkScheduling(ChunkPos pPos, int pTicketLevel, LevelHeightAccessor pLevelHeightAccessor, LevelLightEngine pLightEngine, ChunkHolder.LevelChangeListener pOnLevelChange, ChunkHolder.PlayerProvider pPlayerProvider, Operation<ChunkHolder> original) {
        ChunkHolder holder = original.call(pPos, pTicketLevel, pLevelHeightAccessor, pLightEngine, pOnLevelChange, pPlayerProvider);
        if (this.moreColorful$thermalEngine != null) {
            IChunkHolderExtension.setThermalEngine(holder, this.moreColorful$thermalEngine);
        }
        if (this.moreColorful$ventEngine != null) {
            IChunkHolderExtension.setVentEngine(holder, this.moreColorful$ventEngine);
        }
        return holder;
    }

    @ModifyExpressionValue(method = "hasWork()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ThreadedLevelLightEngine;hasLightWork()Z"))
    private boolean hasWork(boolean original) {
        if (this.moreColorful$thermalEngine != null) {
            original = original || this.moreColorful$thermalEngine.hasThermalWork();
        }
        if (this.moreColorful$ventEngine != null) {
            original = original || this.moreColorful$ventEngine.hasVentWork();
        }
        return original;
    }

    @Inject(method = "lambda$scheduleUnload$12", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ThreadedLevelLightEngine.tryScheduleUpdate()V", shift = At.Shift.AFTER))
    private void scheduleUnload(ChunkHolder pChunkHolder, long pChunkPos, CallbackInfo ci, @Local ChunkAccess chunkaccess) {
        if (this.moreColorful$thermalEngine != null) {
            this.moreColorful$thermalEngine.updateChunkStatus(chunkaccess.getPos());
            this.moreColorful$thermalEngine.tryScheduleUpdate();
        }
        if (this.moreColorful$ventEngine != null) {
            this.moreColorful$ventEngine.updateChunkStatus(chunkaccess.getPos());
            this.moreColorful$ventEngine.tryScheduleUpdate();
        }
    }

    @Override
    public ThreadedLevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }

    @Override
    public ThreadedLevelVentEngine moreColorful$getVentEngine() {
        return this.moreColorful$ventEngine;
    }
}
