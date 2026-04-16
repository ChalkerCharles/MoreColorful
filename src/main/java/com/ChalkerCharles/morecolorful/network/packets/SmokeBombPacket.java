package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.ClientPacketHandler;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SmokeBombPacket(int id) implements CustomPacketPayload {
    public static final Type<SmokeBombPacket> TYPE = new Type<>(MoreColorful.location("smoke_bomb"));

    public static final StreamCodec<ByteBuf, SmokeBombPacket> STREAM_CODEC = ByteBufCodecs.VAR_INT.map(
            SmokeBombPacket::new, SmokeBombPacket::id
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleSmokeBomb(this))
                .exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
