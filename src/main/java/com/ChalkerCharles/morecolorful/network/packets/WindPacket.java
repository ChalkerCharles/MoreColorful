package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.ThreadUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WindPacket(float x, float z) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WindPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("wind"));

    public static final StreamCodec<FriendlyByteBuf, WindPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            WindPacket::x,
            ByteBufCodecs.FLOAT,
            WindPacket::z,
            WindPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final WindPacket packet, final IPayloadContext context) {
        Player player = context.player();
        if (!player.isLocalPlayer()) return;
        context.enqueueWork(() -> LevelSavedData.setGlobalWindSpeed(player.level(), packet.x(), packet.z()))
                .exceptionally(ThreadUtils.handlePayloadException(context));
    }
}
