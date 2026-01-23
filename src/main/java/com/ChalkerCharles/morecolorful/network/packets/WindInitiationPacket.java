package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.joml.Vector2f;

public enum WindInitiationPacket implements CustomPacketPayload {
    INSTANCE;
    public static final Type<WindInitiationPacket> TYPE = new Type<>(MoreColorful.location("wind_initiation"));
    public static final StreamCodec<FriendlyByteBuf, WindInitiationPacket> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();
            Level level = player.level();
            Vector2f wind = LevelSavedData.getGlobalWindSpeed(level);
            PacketDistributor.sendToPlayer(player, new WindPacket(wind.x, wind.y, true));
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
