package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.ModChunkStatus;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IProtoChunkExtension;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkSource;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public final class ChunkData implements INBTSerializable<CompoundTag> {
    private final ChunkAccess chunk;
    private volatile boolean isThermalOn;

    public ChunkData(IAttachmentHolder holder) {
        this.chunk = (ChunkAccess) holder;
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

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        if (Config.THERMAL_SYSTEM.isFalse()) return nbt;
        ListTag temperatures = new ListTag();
        ServerLevel level = (ServerLevel) this.chunk.getLevel();
        ChunkPos chunkpos = this.chunk.getPos();
        nbt.putString("status", BuiltInRegistries.CHUNK_STATUS.getKey(this.chunk.getPersistedStatus()).toString());
        if (level != null) {
            LevelThermalEngine thermalEngine = ((IChunkSourceExtension) level.getChunkSource()).moreColorful$getThermalEngine();
            for (int i = thermalEngine.getMinThermalSection(); i < thermalEngine.getMaxThermalSection(); i++) {
                DataLayer dataLayer = thermalEngine.getLayerListener().getDataLayerData(SectionPos.of(chunkpos, i));
                if (dataLayer != null) {
                    CompoundTag compoundTag = new CompoundTag();
                    if (!dataLayer.isEmpty()) {
                        compoundTag.putByteArray("temperature", dataLayer.getData());
                    }
                    if (!compoundTag.isEmpty()) {
                        compoundTag.putByte("Y", (byte)i);
                        temperatures.add(compoundTag);
                    }
                }
            }
        }
        nbt.put("blockTemperatures", temperatures);
        if (this.isThermalOn) {
            nbt.putBoolean("isThermalOn", true);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        if (Config.THERMAL_SYSTEM.isFalse()) return;
        ServerLevel level = (ServerLevel) this.chunk.getLevel();
        ChunkPos chunkpos = this.chunk.getPos();
        if (level != null) {
            ChunkSource chunksource = level.getChunkSource();
            LevelThermalEngine thermalEngine = ((IChunkSourceExtension) chunksource).moreColorful$getThermalEngine();
            ListTag temperatures = nbt.getList("blockTemperatures", Tag.TAG_COMPOUND);
            boolean flag = false;
            for (int i = 0; i < temperatures.size(); i++) {
                CompoundTag compoundTag = temperatures.getCompound(i);
                int y = compoundTag.getByte("Y");
                boolean flag1 = compoundTag.contains("temperature", Tag.TAG_BYTE_ARRAY);
                if (flag1) {
                    if (!flag) {
                        thermalEngine.retainData(chunkpos, true);
                        flag = true;
                    }
                    thermalEngine.queueSectionData(SectionPos.of(chunkpos, y), new DataLayer(compoundTag.getByteArray("temperature")));
                }
            }
            if (this.chunk instanceof ProtoChunk protoChunk) {
                ChunkStatus chunkstatus = ChunkStatus.byName(nbt.getString("status"));
                if (chunkstatus.isOrAfter(ModChunkStatus.INITIALIZE_THERMAL.get())) {
                    ((IProtoChunkExtension) protoChunk).moreColorful$setThermalEngine(thermalEngine);
                }
            }
        }
        boolean flag = nbt.getBoolean("isThermalOn");
        this.setThermalCorrect(flag);
    }
}
