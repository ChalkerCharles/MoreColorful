package com.ChalkerCharles.morecolorful.network.handlers;

import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class NotePlayingHandler {
    public static void handle(final NotePlayingPacket data, final IPayloadContext context) {
        BlockPos pos = data.pos();
        boolean isBlock = data.isBlock();
        Player player = context.player();
        Level level = player.level();
        context.enqueueWork(() -> {
                    if (isBlock) {
                        level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, pos);
                    } else {
                        level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.position());
                    }
                })
                .exceptionally(e -> {
                    context.disconnect(Component.translatable("morecolorful.networking.failed_to_handle_note_playing", e.getMessage()));
                    return null;
                });
    }
}
