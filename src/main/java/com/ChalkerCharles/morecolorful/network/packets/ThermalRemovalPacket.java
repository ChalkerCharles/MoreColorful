package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record ThermalRemovalPacket(ChunkPos pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ThermalRemovalPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "thermal_removal"));

    public static final StreamCodec<FriendlyByteBuf, ThermalRemovalPacket> STREAM_CODEC = StreamCodec.composite(
            NeoForgeStreamCodecs.CHUNK_POS,
            ThermalRemovalPacket::pos,
            ThermalRemovalPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ThermalRemovalPacket packet, final IPayloadContext context) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        context.enqueueWork(() -> queueThermalRemoval(packet.pos(), level));
    }

    private static void queueThermalRemoval(ChunkPos pos, ClientLevel level) {
        ((ILevelExtension) level).moreColorful$queueThermalUpdate(() -> {
            LevelThermalEngine thermalEngine = ((ILevelExtension) level).moreColorful$getThermalEngine();
            thermalEngine.setThermalEnabled(pos, false);

            for (int i = thermalEngine.getMinThermalSection(); i < thermalEngine.getMaxThermalSection(); i++) {
                SectionPos sectionpos = SectionPos.of(pos, i);
                thermalEngine.queueSectionData(sectionpos, null);
            }

            for (int j = level.getMinSection(); j < level.getMaxSection(); j++) {
                thermalEngine.updateSectionStatus(SectionPos.of(pos, j), true);
            }
        });
    }
}
