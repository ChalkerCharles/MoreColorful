package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.common.level.wind.ChunkVentilationSources;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IProtoChunkExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IChunkAccessMixin;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public class ChunkData implements INBTSerializable<CompoundTag> {
    private final ChunkAccess chunk;
    private volatile boolean isThermalOn;
    private volatile boolean isVentilated;
    private ChunkVentilationSources ventilationSources;

    public ChunkData(IAttachmentHolder holder) {
        this.chunk = (ChunkAccess) holder;
        this.ventilationSources = new ChunkVentilationSources(this.chunk);
    }

    private static ChunkData get(ChunkAccess chunk) {
        return chunk.getData(ModDataAttachments.CHUNK_DATA);
    }

    public static boolean isThermalCorrect(ChunkAccess chunk) {
        return get(chunk).isThermalOn;
    }

    private void setThermalCorrect(boolean correct) {
        this.isThermalOn = correct;
        this.chunk.setUnsaved(true);
    }

    public static void setThermalCorrect(ChunkAccess chunk, boolean correct) {
        get(chunk).setThermalCorrect(correct);
    }

    public static boolean isVentilated(ChunkAccess chunk) {
        return get(chunk).isVentilated;
    }

    private void setVentilation(boolean correct) {
        this.isVentilated = correct;
        this.chunk.setUnsaved(true);
    }

    public static void setVentilation(ChunkAccess chunk, boolean correct) {
        get(chunk).setVentilation(correct);
    }

    private void initializeVentSources() {
        this.ventilationSources.fillFrom(this.chunk);
    }

    public static void initializeVentSources(ChunkAccess chunk) {
        get(chunk).initializeVentSources();
    }

    public static ChunkVentilationSources getVentilationSources(ChunkAccess chunk) {
        return get(chunk).ventilationSources;
    }

    public static void copyVentilationSources(ChunkAccess chunk, ChunkAccess other) {
        get(chunk).ventilationSources = getVentilationSources(other);
    }

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        nbt.putString("status", BuiltInRegistries.CHUNK_STATUS.getKey(this.chunk.getPersistedStatus()).toString());
        serializeThermal(nbt);
        serializeVentilation(nbt);
        return nbt;
    }

    private void serializeThermal(CompoundTag nbt) {
        if (!Config.thermalSystem) return;
        Level level = this.getLevel();
        if (level == null) return;
        ListTag temperatures = new ListTag();
        ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
        ChunkPos pos = this.chunk.getPos();
        for (int i = thermalEngine.getMinThermalSection(); i < thermalEngine.getMaxThermalSection(); i++) {
            DataLayer dataLayer = thermalEngine.getLayerListener().getDataLayerData(SectionPos.of(pos, i));
            if (dataLayer != null) {
                CompoundTag compoundTag = new CompoundTag();
                if (!dataLayer.isEmpty()) {
                    compoundTag.putByteArray("temperature", dataLayer.getData());
                }
                if (!compoundTag.isEmpty()) {
                    compoundTag.putByte("Y", (byte) i);
                    temperatures.add(compoundTag);
                }
            }
        }
        nbt.put("blockTemperatures", temperatures);
        if (this.isThermalOn) {
            nbt.putBoolean("isThermalOn", true);
        }
    }

    private void serializeVentilation(CompoundTag nbt) {
        if (!Config.windSystem) return;
        Level level = this.getLevel();
        if (level == null) return;
        ListTag ventilatedSections = new ListTag();
        ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
        ChunkPos pos = this.chunk.getPos();
        for (int i = ventEngine.getMinVentSection(); i < ventEngine.getMaxVentSection(); i++) {
            DataLayer dataLayer = ventEngine.getLayerListener().getDataLayerData(SectionPos.of(pos, i));
            if (dataLayer != null) {
                CompoundTag compoundTag = new CompoundTag();
                if (!dataLayer.isEmpty()) {
                    compoundTag.putByteArray("ventilation", dataLayer.getData());
                }
                if (!compoundTag.isEmpty()) {
                    compoundTag.putByte("Y", (byte) i);
                    ventilatedSections.add(compoundTag);
                }
            }
        }
        nbt.put("ventilatedSections", ventilatedSections);
        if (this.isVentilated) {
            nbt.putBoolean("isVentilated", true);
        }
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        deserializeThermal(nbt);
        deserializeVentilation(nbt);
    }

    private void deserializeThermal(CompoundTag nbt) {
        if (!Config.thermalSystem) return;
        Level level = this.getLevel();
        if (level == null) return;
        ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
        ChunkPos pos = this.chunk.getPos();
        ListTag temperatures = nbt.getList("blockTemperatures", Tag.TAG_COMPOUND);
        boolean flag = false;
        for (int i = 0; i < temperatures.size(); i++) {
            CompoundTag compoundTag = temperatures.getCompound(i);
            int y = compoundTag.getByte("Y");
            boolean flag1 = compoundTag.contains("temperature", Tag.TAG_BYTE_ARRAY);
            if (flag1) {
                if (!flag) {
                    thermalEngine.retainData(pos, true);
                    flag = true;
                }
                thermalEngine.queueSectionData(SectionPos.of(pos, y), new DataLayer(compoundTag.getByteArray("temperature")));
            }
        }
        ChunkStatus chunkstatus = ChunkStatus.byName(nbt.getString("status"));
        if (chunk instanceof ProtoChunk && chunkstatus.isOrAfter(ModChunkStatus.INITIALIZE_THERMAL.get())) {
            IProtoChunkExtension.setThermalEngine(chunk, thermalEngine);
        }
        this.setThermalCorrect(nbt.getBoolean("isThermalOn"));
    }

    private void deserializeVentilation(CompoundTag nbt) {
        if (!Config.windSystem) return;
        Level level = this.getLevel();
        if (level == null) return;
        ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
        ChunkPos pos = this.chunk.getPos();
        ListTag ventilatedSections = nbt.getList("ventilatedSections", Tag.TAG_COMPOUND);
        boolean flag = false;
        for (int i = 0; i < ventilatedSections.size(); i++) {
            CompoundTag compoundTag = ventilatedSections.getCompound(i);
            int y = compoundTag.getByte("Y");
            boolean flag1 = compoundTag.contains("ventilation", Tag.TAG_BYTE_ARRAY);
            if (flag1) {
                if (!flag) {
                    ventEngine.retainData(pos, true);
                    flag = true;
                }
                ventEngine.queueSectionData(SectionPos.of(pos, y), new DataLayer(compoundTag.getByteArray("ventilation")));
            }
        }
        ChunkStatus chunkstatus = ChunkStatus.byName(nbt.getString("status"));
        if (chunk instanceof ProtoChunk && chunkstatus.isOrAfter(ModChunkStatus.INITIALIZE_VENT.get())) {
            IProtoChunkExtension.setVentEngine(chunk, ventEngine);
        }
        this.setVentilation(nbt.getBoolean("isVentilated"));
    }

    @Nullable
    private Level getLevel() {
        if (((IChunkAccessMixin) this.chunk).getLevelHeightAccessor() instanceof Level level) {
            return level;
        }
        return null;
    }
}
