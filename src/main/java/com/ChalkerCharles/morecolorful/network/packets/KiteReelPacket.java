package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.ChalkerCharles.morecolorful.common.entity.misc.Kite;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.List;

public record KiteReelPacket(boolean reel) implements CustomPacketPayload {
    public static final Type<KiteReelPacket> TYPE = new Type<>(MoreColorful.location("kite_reel"));

    public static final StreamCodec<ByteBuf, KiteReelPacket> STREAM_CODEC = ByteBufCodecs.BOOL
            .map(KiteReelPacket::new, KiteReelPacket::reel);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            List<Kite> list = EntityUtils.getHoldingKites(player);
            if (list.isEmpty()) return;
            boolean success = false;
            for (Kite kite : list) {
                if (kite.reelOrUnreel(this.reel) && kite.shouldMakeReelSound()) {
                    success = true;
                }
            }
            if (success) {
                Level level = player.level();
                level.playSound(null, player, Kite.getReelSound(this.reel), SoundSource.PLAYERS, 1.0F, Kite.getReelPitch(this.reel, level.random));
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
