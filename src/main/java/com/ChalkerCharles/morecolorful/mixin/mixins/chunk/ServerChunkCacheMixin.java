package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkHolderExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkMapExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin extends ChunkSourceMixin {
    @Shadow
    @Final
    public ChunkMap chunkMap;
    @Shadow
    @Nullable
    protected abstract ChunkHolder getVisibleChunkIfPresent(long pChunkPos);
    @Shadow
    @Final
    private ServerChunkCache.MainThreadExecutor mainThreadProcessor;
    @Shadow
    @Final
    public ServerLevel level;
    @Unique
    @Nullable
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;
    @Unique
    @Nullable
    private ThreadedLevelVentEngine moreColorful$ventEngine;

    @Inject(method = "<init>", at = @At(value = "TAIL", shift = At.Shift.BEFORE))
    private void constructor(CallbackInfo ci) {
        if (Config.thermalSystem) {
            this.moreColorful$thermalEngine = IChunkMapExtension.getThermalEngine(this.chunkMap);
        }
        if (Config.windSystem) {
            this.moreColorful$ventEngine = IChunkMapExtension.getVentEngine(this.chunkMap);
        }
    }

    @Inject(method = "close", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkMap;close()V"))
    private void close(CallbackInfo ci) {
        if (this.moreColorful$thermalEngine != null) {
            this.moreColorful$thermalEngine.close();
        }
        if (this.moreColorful$ventEngine != null) {
            this.moreColorful$ventEngine.close();
        }
    }

    @Override
    public ILevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine == null
                ? super.moreColorful$getThermalEngine()
                : this.moreColorful$thermalEngine;
    }

    @Nullable
    @Override
    public ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ) {
        if (this.moreColorful$thermalEngine == null) return null;
        long i = ChunkPos.asLong(pChunkX, pChunkZ);
        ChunkHolder chunkholder = this.getVisibleChunkIfPresent(i);
        return chunkholder == null ? null : chunkholder.getChunkIfPresentUnchecked(ModChunkStatus.INITIALIZE_THERMAL.get().getParent());
    }

    @Override
    public void moreColorful$onThermalUpdate(SectionPos pPos) {
        this.mainThreadProcessor.execute(() -> {
            ChunkHolder chunkholder = this.getVisibleChunkIfPresent(pPos.chunk().toLong());
            if (chunkholder != null) {
                IChunkHolderExtension.sectionThermalChanged(chunkholder, pPos.y());
            }
        });
    }

    @Override
    public ILevelVentEngine moreColorful$getVentEngine() {
        return this.moreColorful$ventEngine == null
                ? super.moreColorful$getVentEngine()
                : this.moreColorful$ventEngine;
    }

    @Override
    @Nullable
    public ChunkAccess moreColorful$getVentChunk(int pChunkX, int pChunkZ) {
        if (this.moreColorful$ventEngine == null) return null;
        long i = ChunkPos.asLong(pChunkX, pChunkZ);
        ChunkHolder chunkholder = this.getVisibleChunkIfPresent(i);
        return chunkholder == null ? null : chunkholder.getChunkIfPresentUnchecked(ModChunkStatus.INITIALIZE_VENT.get().getParent());
    }

    @Override
    public void moreColorful$onVentUpdate(SectionPos pPos) {
        this.mainThreadProcessor.execute(() -> {
            ChunkHolder chunkholder = this.getVisibleChunkIfPresent(pPos.chunk().toLong());
            if (chunkholder != null) {
                IChunkHolderExtension.sectionVentChanged(chunkholder, pPos.y());
            }
        });
    }

    @Mixin(ServerChunkCache.MainThreadExecutor.class)
    private static abstract class MainThreadExecutorMixin {
        @Shadow
        @Final
        ServerChunkCache this$0;

        @Inject(method = "pollTask()Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ThreadedLevelLightEngine;tryScheduleUpdate()V", shift = At.Shift.AFTER))
        private void pollTask(CallbackInfoReturnable<Boolean> cir) {
            if (Config.thermalSystem) {
                IChunkSourceExtension.getThermalEngine(this$0).tryScheduleUpdate();
            }
            if (Config.windSystem) {
                IChunkSourceExtension.getVentEngine(this$0).tryScheduleUpdate();
            }
        }
    }
}
