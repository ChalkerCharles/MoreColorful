package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.ChalkerCharles.morecolorful.common.menu.PapercraftMenu;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PaperCarvingPacket(PapercuttingStencil stencil) implements CustomPacketPayload {
    public static final Type<PaperCarvingPacket> TYPE = new Type<>(MoreColorful.location("paper_carving"));

    public static final StreamCodec<ByteBuf, PaperCarvingPacket> STREAM_CODEC = PapercuttingStencil.STREAM_CODEC
            .map(PaperCarvingPacket::new, PaperCarvingPacket::stencil);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            if (player.containerMenu instanceof PapercraftMenu menu) {
                menu.updateStencil(this.stencil);
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
