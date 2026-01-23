package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.network.ClientPacketHandler;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public record ThermalUpdatePacket(int x, int z, ThermalUpdateData data, boolean sent) implements CustomPacketPayload {
    public static final Type<ThermalUpdatePacket> TYPE = new Type<>(MoreColorful.location("thermal_update"));

    public static final StreamCodec<FriendlyByteBuf, ThermalUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            ThermalUpdatePacket::write, ThermalUpdatePacket::new
    );

    public ThermalUpdatePacket(ChunkPos pChunkPos, ILevelThermalEngine thermalEngine, @Nullable BitSet temperature, boolean sent) {
        this(pChunkPos.x, pChunkPos.z, new ThermalUpdateData(pChunkPos, thermalEngine, temperature), sent);
    }

    private ThermalUpdatePacket(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVarInt(), byteBuf.readVarInt(), new ThermalUpdateData(byteBuf), byteBuf.readBoolean());
    }

    private void write(FriendlyByteBuf byteBuf) {
        byteBuf.writeVarInt(this.x);
        byteBuf.writeVarInt(this.z);
        this.data.write(byteBuf);
        byteBuf.writeBoolean(this.sent);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleThermalUpdate(this))
                .exceptionally(NetworkUtils.handlePayloadException(context));
    }

    public record ThermalUpdateData(BitSet yMask, BitSet emptyYMask, List<byte[]> updates) {
        private ThermalUpdateData(ChunkPos pChunkPos, ILevelThermalEngine thermalEngine, @Nullable BitSet temperature) {
            this(new BitSet(), new BitSet(), new ArrayList<>());
            for (int i = 0; i < thermalEngine.getThermalSectionCount(); i++) {
                if (temperature == null || temperature.get(i)) {
                    prepareSectionData(pChunkPos, thermalEngine, i, this.yMask, this.emptyYMask, this.updates);
                }
            }
        }

        private ThermalUpdateData(FriendlyByteBuf pBuffer) {
            this(pBuffer.readBitSet(), pBuffer.readBitSet(), pBuffer.readList(NetworkUtils.DATA_LAYER));
        }

        private void write(FriendlyByteBuf pBuffer) {
            pBuffer.writeBitSet(this.yMask);
            pBuffer.writeBitSet(this.emptyYMask);
            pBuffer.writeCollection(this.updates, NetworkUtils.DATA_LAYER);
        }

        private static void prepareSectionData(ChunkPos pChunkPos, ILevelThermalEngine thermalEngine, int pIndex, BitSet mask, BitSet emptyMask, List<byte[]> pUpdates) {
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
}
