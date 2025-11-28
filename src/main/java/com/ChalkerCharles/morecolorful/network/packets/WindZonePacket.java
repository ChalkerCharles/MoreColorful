package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WindZonePacket(WindZone zone) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<WindZonePacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("wind_zone"));

    public static final StreamCodec<FriendlyByteBuf, WindZonePacket> STREAM_CODEC = StreamCodec.composite(
            WindZone.STREAM_CODEC,
            WindZonePacket::zone,
            WindZonePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            LevelSavedData.addWindZone(level, zone);
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
