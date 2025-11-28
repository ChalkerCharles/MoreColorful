package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.PlayerData;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadHandler;

public record DrumSetPacket(byte pressingMask, BlockPos pos, int id) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DrumSetPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("drum_set_event"));

    public static final StreamCodec<ByteBuf, DrumSetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BYTE,
            DrumSetPacket::pressingMask,
            BlockPos.STREAM_CODEC,
            DrumSetPacket::pos,
            ByteBufCodecs.INT,
            DrumSetPacket::id,
            DrumSetPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final IPayloadHandler<DrumSetPacket> HANDLER = new DirectionalPayloadHandler<>(
            DrumSetPacket::handleClient, DrumSetPacket::handleServer
    );

    private void handleClient(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            Player player = (Player) level.getEntity(id);
            PlayerData.getInstrumentData(player).setDrumSetData(pressingMask, pos);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void handleServer(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            PlayerData.getInstrumentData(player).setDrumSetData(pressingMask, pos);
            PacketDistributor.sendToAllPlayers(this);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
