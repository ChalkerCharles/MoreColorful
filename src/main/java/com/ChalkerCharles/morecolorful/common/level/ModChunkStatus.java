package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.ChunkData;
import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IProtoChunkExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IWorldGenContextExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IChunkStatusMixin;
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
    private static final DeferredRegister<ChunkStatus> CHUNK_STATUS = DeferredRegister.create(Registries.CHUNK_STATUS, MoreColorful.MODID);

    public static final Supplier<ChunkStatus> INITIALIZE_THERMAL = CHUNK_STATUS.register("initialize_thermal", () -> create(ChunkStatus.SPAWN));
    public static final Supplier<ChunkStatus> THERMAL = CHUNK_STATUS.register("thermal", () -> create(INITIALIZE_THERMAL.get()));
    public static final Supplier<ChunkStatus> INITIALIZE_VENT = CHUNK_STATUS.register("initialize_vent", () -> create(ChunkStatus.SPAWN));
    public static final Supplier<ChunkStatus> VENTILATION = CHUNK_STATUS.register("ventilation", () -> create(INITIALIZE_VENT.get()));

    private static ChunkStatus create(@Nullable ChunkStatus pParent) {
        return IChunkStatusMixin.create(pParent, ChunkStatus.FINAL_HEIGHTMAPS, ChunkType.PROTOCHUNK);
    }

    private static boolean isThermalized(ChunkAccess pChunk) {
        return pChunk.getPersistedStatus().isOrAfter(THERMAL.get()) && ChunkData.isThermalCorrect(pChunk);
    }

    private static boolean isVentilated(ChunkAccess pChunk) {
        return pChunk.getPersistedStatus().isOrAfter(VENTILATION.get()) && ChunkData.isVentilated(pChunk);
    }

    public static CompletableFuture<ChunkAccess> initializeThermal(WorldGenContext context, ChunkStep ignore, StaticCache2D<GenerationChunkHolder> ignored, ChunkAccess chunk) {
        ThreadedLevelThermalEngine thermalEngine = IWorldGenContextExtension.getThermalEngine(context);
        IProtoChunkExtension.setThermalEngine(chunk, thermalEngine);
        boolean flag = isThermalized(chunk);
        return thermalEngine.initializeThermal(chunk, flag);
    }

    public static CompletableFuture<ChunkAccess> thermal(WorldGenContext context, ChunkStep ignore, StaticCache2D<GenerationChunkHolder> ignored, ChunkAccess chunk) {
        boolean flag = isThermalized(chunk);
        return IWorldGenContextExtension.getThermalEngine(context).thermalChunk(chunk, flag);
    }

    public static CompletableFuture<ChunkAccess> initializeVent(WorldGenContext context, ChunkStep ignore, StaticCache2D<GenerationChunkHolder> ignored, ChunkAccess chunk) {
        ThreadedLevelVentEngine ventEngine = IWorldGenContextExtension.getVentEngine(context);
        ChunkData.initializeVentSources(chunk);
        IProtoChunkExtension.setVentEngine(chunk, ventEngine);
        boolean flag = isVentilated(chunk);
        return ventEngine.initializeVentilation(chunk, flag);
    }

    public static CompletableFuture<ChunkAccess> ventilate(WorldGenContext context, ChunkStep ignore, StaticCache2D<GenerationChunkHolder> ignored, ChunkAccess chunk) {
        boolean flag = isVentilated(chunk);
        return IWorldGenContextExtension.getVentEngine(context).ventilateChunk(chunk, flag);
    }

    public static void modifyFullStatus() {
        boolean thermalized = Config.thermalSystem, ventilated = Config.windSystem;
        if (thermalized && ventilated) {
            ((IChunkStatusMixin) INITIALIZE_VENT.get()).setParent(THERMAL.get());
            addIndex(INITIALIZE_VENT.get(), 2);
            addIndex(VENTILATION.get(), 2);
            ((IChunkStatusMixin) ChunkStatus.FULL).setParent(VENTILATION.get());
            addIndex(ChunkStatus.FULL, 4);
        } else if (thermalized) {
            ((IChunkStatusMixin) ChunkStatus.FULL).setParent(THERMAL.get());
            addIndex(ChunkStatus.FULL, 2);
        } else if (ventilated) {
            ((IChunkStatusMixin) ChunkStatus.FULL).setParent(VENTILATION.get());
            addIndex(ChunkStatus.FULL, 2);
        }
    }

    private static void addIndex(ChunkStatus status, int increment) {
        ((IChunkStatusMixin) status).setIndex(status.getIndex() + increment);
    }

    public static void register(IEventBus eventBus) {
        CHUNK_STATUS.register(eventBus);
    }
}
