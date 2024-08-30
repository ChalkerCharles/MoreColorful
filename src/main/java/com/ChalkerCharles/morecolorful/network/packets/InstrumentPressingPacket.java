package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record InstrumentPressingPacket(int id, boolean isPressing) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InstrumentPressingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_screen_closed"));
    public static final StreamCodec<ByteBuf, InstrumentPressingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            InstrumentPressingPacket::id,
            ByteBufCodecs.BOOL,
            InstrumentPressingPacket::isPressing,
            InstrumentPressingPacket::new);
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
