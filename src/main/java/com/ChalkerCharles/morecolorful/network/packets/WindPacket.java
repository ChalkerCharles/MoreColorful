package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record WindPacket(float x, float z, boolean init) implements CustomPacketPayload {
    public WindPacket(float x, float z) {
        this(x, z, false);
    }

    public static final Type<WindPacket> TYPE = new Type<>(MoreColorful.location("wind"));

    public static final StreamCodec<FriendlyByteBuf, WindPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            WindPacket::x,
            ByteBufCodecs.FLOAT,
            WindPacket::z,
            ByteBufCodecs.BOOL,
            WindPacket::init,
            WindPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Level level = context.player().level();
            if (init) {
                LevelSavedData.initGlobalWindSpeed(level, x, z);
            } else {
                LevelSavedData.setGlobalWindSpeed(level, x, z);
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
