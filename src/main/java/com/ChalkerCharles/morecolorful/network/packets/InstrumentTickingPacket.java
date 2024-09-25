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

public record InstrumentTickingPacket(float tick, int id) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<InstrumentTickingPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "tick"));

    public static final StreamCodec<ByteBuf, InstrumentTickingPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT,
            InstrumentTickingPacket::tick,
            ByteBufCodecs.INT,
            InstrumentTickingPacket::id,
            InstrumentTickingPacket::new);

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handleClient(final InstrumentTickingPacket data, final IPayloadContext context) {
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
    public static void handleServer(final InstrumentTickingPacket data, final IPayloadContext context) {
        float tick = data.tick();
        int id = data.id();
        Player player = context.player();
        Entity entity = player.level().getEntity(id);
        context.enqueueWork(() -> {
            if (entity instanceof Player) {
                entity.setData(ModDataAttachments.PLAYING_SCREEN_TICK, tick);
                PacketDistributor.sendToAllPlayers(data);
            }
        });
    }
}
