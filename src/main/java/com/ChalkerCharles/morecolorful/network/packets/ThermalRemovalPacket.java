package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.ClientPacketHandler;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ThermalRemovalPacket(ChunkPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ThermalRemovalPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("thermal_removal"));

    public static final StreamCodec<FriendlyByteBuf, ThermalRemovalPacket> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.CHUNK_POS,
            ThermalRemovalPacket::pos,
            ThermalRemovalPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleThermalRemoval(this))
                .exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
