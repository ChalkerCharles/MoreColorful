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
import org.jetbrains.annotations.NotNull;

public record InstrumentTickingPacket(float tick, int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InstrumentTickingPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("tick"));

    public static final StreamCodec<ByteBuf, InstrumentTickingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            InstrumentTickingPacket::tick,
            ByteBufCodecs.INT,
            InstrumentTickingPacket::id,
            InstrumentTickingPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final IPayloadHandler<InstrumentTickingPacket> HANDLER = new DirectionalPayloadHandler<>(
            InstrumentTickingPacket::handleClient, InstrumentTickingPacket::handleServer
    );

    private void handleClient(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            Player player = (Player) level.getEntity(id);
            PlayerData.getInstrumentData(player).tick = tick;
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void handleServer(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            PlayerData.getInstrumentData(player).tick = tick;
            PacketDistributor.sendToAllPlayers(this);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
