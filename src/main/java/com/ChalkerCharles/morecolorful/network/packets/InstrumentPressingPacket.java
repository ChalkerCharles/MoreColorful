package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record InstrumentPressingPacket(int id, boolean isPressing) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<InstrumentPressingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_screen_closed"));
    public static final StreamCodec<ByteBuf, InstrumentPressingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT,
            InstrumentPressingPacket::id,
            ByteBufCodecs.BOOL,
            InstrumentPressingPacket::isPressing,
            InstrumentPressingPacket::new);
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(final InstrumentPressingPacket data, final IPayloadContext context) {
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
    public static void handleServer(final InstrumentPressingPacket data, final IPayloadContext context) {
        int id = data.id();
        boolean isPressing = data.isPressing();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.IS_PLAYING_INSTRUMENT, isPressing);
                PacketDistributor.sendToAllPlayers(data);
            }
        });
    }
}
