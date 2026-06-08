package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.menu.EnvelopeMenu;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SealMailPacket(String recipient) implements CustomPacketPayload {
    public static final Type<SealMailPacket> TYPE = new Type<>(MoreColorful.location("seal_mail"));

    public static final StreamCodec<ByteBuf, SealMailPacket> STREAM_CODEC = ByteBufCodecs.STRING_UTF8
            .map(SealMailPacket::new, SealMailPacket::recipient);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (player.containerMenu instanceof EnvelopeMenu menu) {
                menu.sealMail(player, this.recipient);
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
