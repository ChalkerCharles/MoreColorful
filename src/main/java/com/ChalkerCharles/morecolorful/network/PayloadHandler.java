package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.entity.HiHatBlockEntity;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentTickingPacket;
import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;

public final class PayloadHandler {
    private static ArrayList<Integer> PRESSING_PLAYERS = new ArrayList<>();

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
        BlockEntity blockEntity = player.level().getBlockEntity(pos);
        Object[] obj = {pType, pos, id, isOpen};
        Object[] obj1 = {pos, new ArrayList<Integer>()};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, obj);
                if (!isOpen) {
                    if (blockEntity instanceof HiHatBlockEntity) {
                        blockEntity.setData(ModDataAttachments.CYMBAL_DATA, obj1);
                    }
                }
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
        BlockEntity blockEntity = player.level().getBlockEntity(pos);
        Object[] obj = {pType, pos, id, isOpen};
        Object[] obj1 = {pos, new ArrayList<Integer>()};
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, obj);
                PacketDistributor.sendToAllPlayers(new PlayingScreenPacket(pType, pos, id, isOpen));
                if (!isOpen) {
                    ((Player) entity).stopUsingItem();
                    entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, false);
                    PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(pos, id, false));
                    if (blockEntity instanceof HiHatBlockEntity) {
                        blockEntity.setData(ModDataAttachments.CYMBAL_DATA, obj1);
                    }
                }
            }
        });
    }
    public static void handleInstrumentPressingClient(final InstrumentPressingPacket data, final IPayloadContext context) {
        BlockPos pos = data.pos();
        int id = data.id();
        boolean isPressing = data.isPressing();
        Player player = context.player();
        BlockEntity blockEntity = player.level().getBlockEntity(pos);
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, isPressing);
                if (blockEntity instanceof HiHatBlockEntity) {
                    PRESSING_PLAYERS = ((HiHatBlockEntity) blockEntity).pressingPlayers(blockEntity);
                    Object[] obj = {pos, PRESSING_PLAYERS};
                    blockEntity.setData(ModDataAttachments.CYMBAL_DATA, obj);
                }
            }
        });
    }
    public static void handleInstrumentPressingServer(final InstrumentPressingPacket data, final IPayloadContext context) {
        BlockPos pos = data.pos();
        int id = data.id();
        boolean isPressing = data.isPressing();
        Player player = context.player();
        BlockEntity blockEntity = player.level().getBlockEntity(pos);
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, isPressing);
                if (blockEntity instanceof HiHatBlockEntity) {
                    PRESSING_PLAYERS = ((HiHatBlockEntity) blockEntity).pressingPlayers(blockEntity);
                    Object[] obj = {pos, PRESSING_PLAYERS};
                    blockEntity.setData(ModDataAttachments.CYMBAL_DATA, obj);
                }
                PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(pos, id, isPressing));
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
}
