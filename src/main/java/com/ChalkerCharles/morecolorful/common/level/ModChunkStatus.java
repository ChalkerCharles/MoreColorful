package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.mixin.accessor.IChunkStatusMixin;
import com.ChalkerCharles.morecolorful.util.mixin.IProtoChunkExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IWorldGenContextExtension;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.GenerationChunkHolder;
import net.minecraft.util.StaticCache2D;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.chunk.status.ChunkStep;
import net.minecraft.world.level.chunk.status.ChunkType;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ModChunkStatus {
    public static final DeferredRegister<ChunkStatus> CHUNK_STATUS = DeferredRegister.create(Registries.CHUNK_STATUS, MoreColorful.MODID);

    public static final Supplier<ChunkStatus> INITIALIZE_THERMAL = CHUNK_STATUS.register("initialize_thermal", () -> create(ChunkStatus.SPAWN));

    public static final Supplier<ChunkStatus> THERMAL = CHUNK_STATUS.register("thermal", () -> create(INITIALIZE_THERMAL.get()));

    private static ChunkStatus create(@Nullable ChunkStatus pParent) {
        return IChunkStatusMixin.create(pParent, ChunkStatus.FINAL_HEIGHTMAPS, ChunkType.PROTOCHUNK);
    }

    private static boolean isThermalized(ChunkAccess pChunk) {
        return pChunk.getPersistedStatus().isOrAfter(THERMAL.get()) && ChunkData.isThermalCorrect(pChunk);
    }

    public static CompletableFuture<ChunkAccess> initializeThermal(WorldGenContext pWorldGenContext, ChunkStep ignoredStep, StaticCache2D<GenerationChunkHolder> ignoredCache, ChunkAccess pChunk) {
        ThreadedLevelThermalEngine thermalEngine = ((IWorldGenContextExtension) (Object) pWorldGenContext).moreColorful$getThermalEngine();
        ((IProtoChunkExtension) pChunk).moreColorful$setThermalEngine(thermalEngine);
        boolean flag = isThermalized(pChunk);
        return thermalEngine.initializeThermal(pChunk, flag);
    }

    public static CompletableFuture<ChunkAccess> thermal(WorldGenContext pWorldGenContext, ChunkStep ignoredStep, StaticCache2D<GenerationChunkHolder> ignoredCache, ChunkAccess pChunk) {
        boolean flag = isThermalized(pChunk);
        return ((IWorldGenContextExtension) (Object) pWorldGenContext).moreColorful$getThermalEngine().thermalChunk(pChunk, flag);
    }

    public static void modifyFullStatus() {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ((IChunkStatusMixin) ChunkStatus.FULL).setParent(THERMAL.get());
            ((IChunkStatusMixin) ChunkStatus.FULL).setIndex(ChunkStatus.FULL.getIndex() + 2);
        }
    }

    public static void register(IEventBus eventBus) {
        CHUNK_STATUS.register(eventBus);
    }
}
