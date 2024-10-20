package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record DrumSetPacket(boolean isPressingBassDrum, boolean isPressingHat, boolean isPressingRide, boolean isPressingCrash, BlockPos pos, int id) implements CustomPacketPayload {
    public DrumSetPacket() {
        this(false, false, false, false, new BlockPos(0, -128, 0), 0);
    }

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

    public static void handleClient(final DrumSetPacket data, final IPayloadContext context) {
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.DRUM_SET_DATA, data);
            }
        });
    }
    public static void handleServer(final DrumSetPacket data, final IPayloadContext context) {
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.DRUM_SET_DATA, data);
                PacketDistributor.sendToAllPlayers(data);
            }
        });
    }
}
