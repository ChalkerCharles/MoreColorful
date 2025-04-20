package com.ChalkerCharles.morecolorful.mixin.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkHolderExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkMapExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IWorldGenContextExtension;
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
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ChunkMap.class)
public abstract class ChunkMapMixin implements IChunkMapExtension {
    @Shadow
    @Final
    private ChunkTaskPriorityQueueSorter queueSorter;
    @Shadow
    @Final
    private WorldGenContext worldGenContext;
    @Unique
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkTaskPriorityQueueSorter;<init>(Ljava/util/List;Ljava/util/concurrent/Executor;I)V"))
    private List<ProcessorHandle<?>> addProcessorMailbox(List<ProcessorHandle<?>> pQueues, @Share("mailbox") LocalRef<ProcessorMailbox<Runnable>> mailbox, @Local(argsOnly = true) Executor pDispatcher) {
        if (Config.THERMAL_SYSTEM.isFalse()) return pQueues;
        ProcessorMailbox<Runnable> processormailbox = ProcessorMailbox.create(pDispatcher, "temperature");
        mailbox.set(processormailbox);
        List<ProcessorHandle<?>> list = new ArrayList<>(List.copyOf(pQueues));
        list.add(processormailbox);
        return list;
    }

    @SuppressWarnings("DataFlowIssue")
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
            @Share("mailbox") LocalRef<ProcessorMailbox<Runnable>> mailbox) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        this.moreColorful$thermalEngine = new ThreadedLevelThermalEngine(
                (ChunkSource) pLightChunk, (ChunkMap)(Object) this, mailbox.get(), this.queueSorter.getProcessor(mailbox.get(), false)
        );
        ((IWorldGenContextExtension)(Object) this.worldGenContext).moreColorful$setThermalEngine(this.moreColorful$thermalEngine);
    }

    @WrapOperation(method = "updateChunkScheduling(JILnet/minecraft/server/level/ChunkHolder;I)Lnet/minecraft/server/level/ChunkHolder;",
            at = @At(value = "NEW", args = "class=net/minecraft/server/level/ChunkHolder"))
    private ChunkHolder updateChunkScheduling(ChunkPos pPos, int pTicketLevel, LevelHeightAccessor pLevelHeightAccessor, LevelLightEngine pLightEngine, ChunkHolder.LevelChangeListener pOnLevelChange, ChunkHolder.PlayerProvider pPlayerProvider, Operation<ChunkHolder> original) {
        ChunkHolder holder = original.call(pPos, pTicketLevel, pLevelHeightAccessor, pLightEngine, pOnLevelChange, pPlayerProvider);
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ((IChunkHolderExtension) holder).moreColorful$setThermalEngine(this.moreColorful$thermalEngine);
        }
        return holder;
    }

    @Inject(method = "hasWork()Z", at = @At("HEAD"), cancellable = true)
    private void hasWork(CallbackInfoReturnable<Boolean> cir) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        if (this.moreColorful$thermalEngine.hasThermalWork()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "lambda$scheduleUnload$12", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ThreadedLevelLightEngine.tryScheduleUpdate()V", shift = At.Shift.AFTER))
    private void scheduleUnload(ChunkHolder pChunkHolder, long pChunkPos, CallbackInfo ci, @Local ChunkAccess chunkaccess) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        this.moreColorful$thermalEngine.updateChunkStatus(chunkaccess.getPos());
        this.moreColorful$thermalEngine.tryScheduleUpdate();
    }

    @Unique
    @Override
    public ThreadedLevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }
}
