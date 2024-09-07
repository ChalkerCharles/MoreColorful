package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record DrumSetPacket(boolean isPressingBassDrum, boolean isPressingHat, boolean isPressingRide, boolean isPressingCrash, BlockPos pos, int id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<DrumSetPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set_event"));

    public static final StreamCodec<ByteBuf, DrumSetPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            DrumSetPacket::isPressingBassDrum,
            ByteBufCodecs.BOOL,
            DrumSetPacket::isPressingHat,
            ByteBufCodecs.BOOL,
            DrumSetPacket::isPressingRide,
            ByteBufCodecs.BOOL,
            DrumSetPacket::isPressingCrash,
            BlockPos.STREAM_CODEC,
            DrumSetPacket::pos,
            ByteBufCodecs.INT,
            DrumSetPacket::id,
            DrumSetPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
