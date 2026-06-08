package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.ClientPacketHandler;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record OpenLetterPacket(InteractionHand hand) implements CustomPacketPayload {
    public static final Type<OpenLetterPacket> TYPE = new Type<>(MoreColorful.location("open_letter"));

    public static final StreamCodec<FriendlyByteBuf, OpenLetterPacket> STREAM_CODEC = StreamCodec.of(
            (buf, packet) -> buf.writeEnum(packet.hand),
            buf -> new OpenLetterPacket(buf.readEnum(InteractionHand.class))
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.handleOpenLetter(this))
                .exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
