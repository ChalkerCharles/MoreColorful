package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record NotePlayingPacket(BlockPos pos, boolean isBlock) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<NotePlayingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "note_playing"));

    public static final StreamCodec<ByteBuf, NotePlayingPacket> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            NotePlayingPacket::pos,
            ByteBufCodecs.BOOL,
            NotePlayingPacket::isBlock,
            NotePlayingPacket::new);

    @Override
    public @NotNull Type<NotePlayingPacket> type() {
        return TYPE;
    }
}
