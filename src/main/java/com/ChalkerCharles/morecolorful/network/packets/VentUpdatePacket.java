package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
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

public record VentUpdatePacket(int x, int z, VentUpdateData data, boolean sent) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<VentUpdatePacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("vent_update"));

    public static final StreamCodec<FriendlyByteBuf, VentUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            VentUpdatePacket::write, VentUpdatePacket::new
    );

    public VentUpdatePacket(ChunkPos pChunkPos, ILevelVentEngine ventEngine, @Nullable BitSet ventilation, boolean sent) {
        this(pChunkPos.x, pChunkPos.z, new VentUpdateData(pChunkPos, ventEngine, ventilation), sent);
    }

    private VentUpdatePacket(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVarInt(), byteBuf.readVarInt(), new VentUpdateData(byteBuf), byteBuf.readBoolean());
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
        context.enqueueWork(() -> ClientPacketHandler.handleVentUpdate(this))
                .exceptionally(NetworkUtils.handlePayloadException(context));
    }

    public record VentUpdateData(BitSet yMask, BitSet emptyYMask, List<byte[]> updates) {
        private VentUpdateData(ChunkPos pChunkPos, ILevelVentEngine ventEngine, @Nullable BitSet ventilation) {
            this(new BitSet(), new BitSet(), new ArrayList<>());
            for (int i = 0; i < ventEngine.getVentSectionCount(); i++) {
                if (ventilation == null || ventilation.get(i)) {
                    prepareSectionData(pChunkPos, ventEngine, i, this.yMask, this.emptyYMask, this.updates);
                }
            }
        }

        private VentUpdateData(FriendlyByteBuf pBuffer) {
            this(pBuffer.readBitSet(), pBuffer.readBitSet(), pBuffer.readList(NetworkUtils.DATA_LAYER));
        }

        private void write(FriendlyByteBuf pBuffer) {
            pBuffer.writeBitSet(this.yMask);
            pBuffer.writeBitSet(this.emptyYMask);
            pBuffer.writeCollection(this.updates, NetworkUtils.DATA_LAYER);
        }

        private static void prepareSectionData(ChunkPos pChunkPos, ILevelVentEngine ventEngine, int pIndex, BitSet mask, BitSet emptyMask, List<byte[]> pUpdates) {
            DataLayer datalayer = ventEngine.getLayerListener().getDataLayerData(SectionPos.of(pChunkPos, ventEngine.getMinVentSection() + pIndex));
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
