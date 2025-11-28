package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.PlayerData;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record InstrumentPressingPacket(int id, boolean isPressing) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InstrumentPressingPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("playing_screen_closed"));
    public static final StreamCodec<ByteBuf, InstrumentPressingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            InstrumentPressingPacket::id,
            ByteBufCodecs.BOOL,
            InstrumentPressingPacket::isPressing,
            InstrumentPressingPacket::new);
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final IPayloadHandler<InstrumentPressingPacket> HANDLER = new DirectionalPayloadHandler<>(
            InstrumentPressingPacket::handleClient, InstrumentPressingPacket::handleServer
    );

    private void handleClient(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            Player player = (Player) level.getEntity(id);
            PlayerData.getInstrumentData(player).isPlaying = isPressing;
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void handleServer(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            PlayerData.getInstrumentData(player).isPlaying = isPressing;
            PacketDistributor.sendToAllPlayers(this);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
