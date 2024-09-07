package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class PayloadHandler {
    public static void handleNotePlaying(final NotePlayingPacket data, final IPayloadContext context) {
        InstrumentsType pType = data.pType();
        BlockPos pos = data.pos();
        int keyId = data.keyId();
        int pitchId = keyId - 12;
        boolean isBlock = data.isBlock();
        double random = 2 * (Math.random() - Math.random());
        double random1 = 2 * (Math.random() - Math.random());
        Player player = context.player();
        Level level = player.level();
        context.enqueueWork(() -> {
            if (isBlock) {
                level.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pType.getSoundEvent().value(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2,((double) pitchId / 12)));
                ((ServerLevel) level).sendParticles(ParticleTypes.NOTE, pos.getX() + 0.5 + random, pos.getY()+2.2, pos.getZ() + 0.5 + random1, 0, 1.0, 0.0, 0.0, keyId / 24.0);
                level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, pos);
            } else {
                level.playSound(player, player, pType.getSoundEvent().value(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2,((double) pitchId / 12)));
                ((ServerLevel) level).sendParticles(ParticleTypes.NOTE, player.getX() + random, player.getY()+2.2, player.getZ() + random1, 0, 1.0, 0.0, 0.0, keyId / 24.0);
                level.gameEvent(player, GameEvent.INSTRUMENT_PLAY, player.position());
            }
        });
    }
    public static void handlePlayingScreenClient(final PlayingScreenPacket data, final IPayloadContext context) {
        InstrumentsType pType = data.pType();
        BlockPos pos = data.pos();
        int id = data.id();
        boolean isOpen = data.isOpen();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        Object[] obj = {pType, pos, id, isOpen};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, obj);
            }
        });
    }
    public static void handlePlayingScreenServer(final PlayingScreenPacket data, final IPayloadContext context) {
        InstrumentsType pType = data.pType();
        BlockPos pos = data.pos();
        int id = data.id();
        boolean isOpen = data.isOpen();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        Object[] obj = {pType, pos, id, isOpen};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, obj);
                PacketDistributor.sendToAllPlayers(new PlayingScreenPacket(pType, pos, id, isOpen));
                if (!isOpen) {
                    ((Player) entity).stopUsingItem();
                    entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, false);
                    PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(id, false));
                }
            }
        });
    }
    public static void handleInstrumentPressingClient(final InstrumentPressingPacket data, final IPayloadContext context) {
        int id = data.id();
        boolean isPressing = data.isPressing();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, isPressing);
            }
        });
    }
    public static void handleInstrumentPressingServer(final InstrumentPressingPacket data, final IPayloadContext context) {
        int id = data.id();
        boolean isPressing = data.isPressing();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, isPressing);
                PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(id, isPressing));
            }
        });
    }
    public static void handleInstrumentTickingClient(final InstrumentTickingPacket data, final IPayloadContext context) {
        float tick = data.tick();
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_TICK, tick);
            }
        });
    }
    public static void handleInstrumentTickingServer(final InstrumentTickingPacket data, final IPayloadContext context) {
        float tick = data.tick();
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_TICK, tick);
                PacketDistributor.sendToAllPlayers(new InstrumentTickingPacket(tick, id));
            }
        });
    }
    public static void handleDrumSetClient(final DrumSetPacket data, final IPayloadContext context) {
        boolean isPressingBassDrum = data.isPressingBassDrum();
        boolean isPressingHat = data.isPressingHat();
        boolean isPressingRide = data.isPressingRide();
        boolean isPressingCrash = data.isPressingCrash();
        BlockPos pos = data.pos();
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        Object[] obj = {isPressingBassDrum, isPressingHat, isPressingRide, isPressingCrash, pos, id};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.DRUM_SET_DATA, obj);
            }
        });
    }
    public static void handleDrumSetServer(final DrumSetPacket data, final IPayloadContext context) {
        boolean isPressingBassDrum = data.isPressingBassDrum();
        boolean isPressingHat = data.isPressingHat();
        boolean isPressingRide = data.isPressingRide();
        boolean isPressingCrash = data.isPressingCrash();
        BlockPos pos = data.pos();
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        Object[] obj = {isPressingBassDrum, isPressingHat, isPressingRide, isPressingCrash, pos, id};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.DRUM_SET_DATA, obj);
                PacketDistributor.sendToAllPlayers(new DrumSetPacket(isPressingBassDrum, isPressingHat, isPressingRide, isPressingCrash, pos, id));
            }
        });
    }
}
