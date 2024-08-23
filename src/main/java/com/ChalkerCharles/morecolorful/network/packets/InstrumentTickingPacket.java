package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record InstrumentTickingPacket(float tick, int id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<InstrumentTickingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "tick"));

    public static final StreamCodec<ByteBuf, InstrumentTickingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            InstrumentTickingPacket::tick,
            ByteBufCodecs.INT,
            InstrumentTickingPacket::id,
            InstrumentTickingPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
