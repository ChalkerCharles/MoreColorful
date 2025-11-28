package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record NotePlayingPacket(InstrumentsType pType, BlockPos pos, int keyId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NotePlayingPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("note_playing"));

    public static final StreamCodec<ByteBuf, NotePlayingPacket> STREAM_CODEC = StreamCodec.composite(
            InstrumentsType.STREAM_CODEC,
            NotePlayingPacket::pType,
            BlockPos.STREAM_CODEC,
            NotePlayingPacket::pos,
            ByteBufCodecs.INT,
            NotePlayingPacket::keyId,
            NotePlayingPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            int pitchId = keyId - 12;
            double random = 2 * (Math.random() - Math.random());
            double random1 = 2 * (Math.random() - Math.random());
            Player player = context.player();
            ServerLevel level = (ServerLevel) player.level();
            if (pType.isBlock()) {
                level.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pType.getSoundEvent(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitchId / 12.0));
                level.sendParticles(ParticleTypes.NOTE, pos.getX() + 0.5 + random, pos.getY() + 2.2, pos.getZ() + 0.5 + random1, 0, 1.0, 0.0, 0.0, keyId / 24.0);
                level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, pos);
            } else {
                level.playSound(player, player, pType.getSoundEvent(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitchId / 12.0));
                level.sendParticles(ParticleTypes.NOTE, player.getX() + random, player.getY() + 2.2, player.getZ() + random1, 0, 1.0, 0.0, 0.0, keyId / 24.0);
                level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.position());
            }
        }).exceptionally(NetworkUtils.handlePayloadException(context));
    }
}
