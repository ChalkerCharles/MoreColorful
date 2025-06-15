package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.attachment.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.util.Constants;
import com.ChalkerCharles.morecolorful.util.ThreadUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record PlayingScreenPacket(InstrumentsType pType, BlockPos pos, int id, boolean isOpen) implements CustomPacketPayload {
    public PlayingScreenPacket() {
        this(InstrumentsType.HARP, Constants.DEFAULT_INSTRUMENT_POS, 0, false);
    }

    public static final CustomPacketPayload.Type<PlayingScreenPacket> TYPE = new CustomPacketPayload.Type<>(MoreColorful.location("playing_screen_event"));

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

    public static void handleClient(final PlayingScreenPacket packet, final IPayloadContext context) {
        int id = packet.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, packet);
            }
        }).exceptionally(ThreadUtils.handlePayloadException(context));
    }
    public static void handleServer(final PlayingScreenPacket packet, final IPayloadContext context) {
        int id = packet.id();
        boolean isOpen = packet.isOpen();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_DATA, packet);
                PacketDistributor.sendToAllPlayers(packet);
                if (!isOpen) {
                    ((Player) entity).stopUsingItem();
                    entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, false);
                    PacketDistributor.sendToAllPlayers(new InstrumentPressingPacket(id, false));
                }
            }
        }).exceptionally(ThreadUtils.handlePayloadException(context));
    }
}
