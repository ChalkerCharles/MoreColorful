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

public record NotePlayingPacket(InstrumentsType pType, BlockPos pos, int keyId, boolean isBlock) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<NotePlayingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "note_playing"));

    public static final StreamCodec<ByteBuf, NotePlayingPacket> STREAM_CODEC = StreamCodec.composite(
            InstrumentsType.STREAM_CODEC,
            NotePlayingPacket::pType,
            BlockPos.STREAM_CODEC,
            NotePlayingPacket::pos,
            ByteBufCodecs.INT,
            NotePlayingPacket::keyId,
            ByteBufCodecs.BOOL,
            NotePlayingPacket::isBlock,
            NotePlayingPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
