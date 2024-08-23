package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record PlayingScreenPacket(InstrumentsType pType, BlockPos pos, int id, boolean isOpen) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<PlayingScreenPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_screen_event"));

    public static final StreamCodec<ByteBuf, PlayingScreenPacket> STREAM_CODEC = StreamCodec.composite(
            InstrumentsType.STREAM_CODEC,
            PlayingScreenPacket::pType,
            BlockPos.STREAM_CODEC,
            PlayingScreenPacket::pos,
            ByteBufCodecs.INT,
            PlayingScreenPacket::id,
            ByteBufCodecs.BOOL,
            PlayingScreenPacket::isOpen,
            PlayingScreenPacket::new);
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
