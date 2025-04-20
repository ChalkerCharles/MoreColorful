package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public record ThermalUpdateData(BitSet yMask, BitSet emptyYMask, List<byte[]> updates) {
    private static final StreamCodec<ByteBuf, byte[]> DATA_LAYER_STREAM_CODEC = ByteBufCodecs.byteArray(2048);

    public ThermalUpdateData(ChunkPos pChunkPos, LevelThermalEngine thermalEngine, @Nullable BitSet temperature) {
        this(new BitSet(), new BitSet(), new ArrayList<>());
        for (int i = 0; i < thermalEngine.getThermalSectionCount(); i++) {
            if (temperature == null || temperature.get(i)) {
                this.prepareSectionData(pChunkPos, thermalEngine, i, this.yMask, this.emptyYMask, this.updates);
            }
        }
    }

    public ThermalUpdateData(FriendlyByteBuf pBuffer) {
        this(pBuffer.readBitSet(), pBuffer.readBitSet(), pBuffer.readList(DATA_LAYER_STREAM_CODEC));
    }

    public void write(FriendlyByteBuf pBuffer) {
        pBuffer.writeBitSet(this.yMask);
        pBuffer.writeBitSet(this.emptyYMask);
        pBuffer.writeCollection(this.updates, DATA_LAYER_STREAM_CODEC);
    }

    private void prepareSectionData(ChunkPos pChunkPos, LevelThermalEngine thermalEngine, int pIndex, BitSet mask, BitSet emptyMask, List<byte[]> pUpdates) {
        DataLayer datalayer = thermalEngine.getLayerListener().getDataLayerData(SectionPos.of(pChunkPos, thermalEngine.getMinThermalSection() + pIndex));
        if (datalayer != null) {
            if (datalayer.isEmpty()) {
                emptyMask.set(pIndex);
            } else {
                mask.set(pIndex);
                pUpdates.add(datalayer.copy().getData());
            }
        }
    }
}
