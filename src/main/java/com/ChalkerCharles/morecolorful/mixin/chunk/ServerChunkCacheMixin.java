package com.ChalkerCharles.morecolorful.mixin.chunk;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkHolderExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkMapExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import com.mojang.datafixers.DataFixer;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.entity.ChunkStatusUpdateListener;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.concurrent.Executor;
import java.util.function.Supplier;

@Mixin(ServerChunkCache.class)
public abstract class ServerChunkCacheMixin implements IChunkSourceExtension {
    @Shadow
    @Final
    public ChunkMap chunkMap;
    @Shadow
    @Nullable
    protected abstract ChunkHolder getVisibleChunkIfPresent(long pChunkPos);
    @Shadow
    @Final
    private ServerChunkCache.MainThreadExecutor mainThreadProcessor;

    @Unique
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;

    @Inject(method = "<init>", at = @At(value = "TAIL", shift = At.Shift.BEFORE))
    private void constructor(
            ServerLevel pLevel,
            LevelStorageSource.LevelStorageAccess pLevelStorageAccess,
            DataFixer pFixerUpper,
            StructureTemplateManager pStructureManager,
            Executor pDispatcher,
            ChunkGenerator pGenerator,
            int pViewDistance,
            int pSimulationDistance,
            boolean pSync,
            ChunkProgressListener pProgressListener,
            ChunkStatusUpdateListener pChunkStatusListener,
            Supplier<DimensionDataStorage> pOverworldDataStorage,
            CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            this.moreColorful$thermalEngine = ((IChunkMapExtension) this.chunkMap).moreColorful$getThermalEngine();
        }
    }

    @Inject(method = "close", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ChunkMap;close()V"))
    private void close(CallbackInfo ci) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            this.moreColorful$thermalEngine.close();
        }
    }

    @Unique
    @Override
    public ThreadedLevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }

    @Unique
    @Nullable
    @Override
    public ChunkAccess moreColorful$getThermalChunk(int pChunkX, int pChunkZ) {
        if (Config.THERMAL_SYSTEM.isFalse()) return null;
        long i = ChunkPos.asLong(pChunkX, pChunkZ);
        ChunkHolder chunkholder = this.getVisibleChunkIfPresent(i);
        return chunkholder == null ? null : chunkholder.getChunkIfPresentUnchecked(ModChunkStatus.INITIALIZE_THERMAL.get().getParent());
    }

    @Unique
    @Override
    public void moreColorful$onThermalUpdate(SectionPos pPos) {
        this.mainThreadProcessor.execute(() -> {
            ChunkHolder chunkholder = this.getVisibleChunkIfPresent(pPos.chunk().toLong());
            if (chunkholder != null) {
                ((IChunkHolderExtension) chunkholder).moreColorful$sectionThermalChanged(pPos.y());
            }
        });
    }
}
