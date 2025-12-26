package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.InstrumentData;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
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

public record PlayingScreenPacket(InstrumentsType pType, BlockPos pos, int id, boolean isOpen) implements CustomPacketPayload {
    public PlayingScreenPacket(InstrumentsType pType, int id, boolean isOpen) {
        this(pType, BlockPos.ZERO, id, isOpen);
    }

    public static final CustomPacketPayload.Type<PlayingScreenPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("playing_screen_event"));

    public static final StreamCodec<ByteBuf, PlayingScreenPacket> STREAM_CODEC = StreamCodec.composite(
            InstrumentsType.STREAM_CODEC,
            PlayingScreenPacket::pType,
            BlockPos.STREAM_CODEC,
            PlayingScreenPacket::pos,
            ByteBufCodecs.INT,
            PlayingScreenPacket::id,
            ByteBufCodecs.BOOL,
            PlayingScreenPacket::isOpen,
            PlayingScreenPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static final IPayloadHandler<PlayingScreenPacket> HANDLER = new DirectionalPayloadHandler<>(
            PlayingScreenPacket::handleClient, PlayingScreenPacket::handleServer
    );

    private void handleClient(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            Player player = (Player) level.getEntity(id);
            if (player == null) return;
            InstrumentData data = InstrumentData.get(player);
            data.setPlayingScreenData(pType, pos, isOpen);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }

    private void handleServer(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            InstrumentData data = InstrumentData.get(player);
            data.setPlayingScreenData(pType, pos, isOpen);
            PacketDistributor.sendToAllPlayers(this);
            if (!isOpen) {
                player.stopUsingItem();
                data.isPlaying = false;
                PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(id, false));
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
