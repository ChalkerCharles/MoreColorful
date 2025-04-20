package com.ChalkerCharles.morecolorful.network.packets;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import javax.annotation.Nullable;
import java.util.BitSet;
import java.util.Iterator;

public record ThermalUpdatePacket(int x, int z, ThermalUpdateData data, boolean sent) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ThermalUpdatePacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "thermal_update"));

    public static final StreamCodec<FriendlyByteBuf, ThermalUpdatePacket> STREAM_CODEC = StreamCodec.ofMember(
            ThermalUpdatePacket::write, ThermalUpdatePacket::new
    );

    public ThermalUpdatePacket(ChunkPos pChunkPos, LevelThermalEngine thermalEngine, @Nullable BitSet temperature, boolean sent) {
        this(pChunkPos.x, pChunkPos.z, new ThermalUpdateData(pChunkPos, thermalEngine, temperature), sent);
    }

    private ThermalUpdatePacket(FriendlyByteBuf byteBuf) {
        this(byteBuf.readVarInt(), byteBuf.readVarInt(), new ThermalUpdateData(byteBuf), byteBuf.readBoolean());
    }

    private void write(FriendlyByteBuf byteBuf) {
        byteBuf.writeVarInt(this.x);
        byteBuf.writeVarInt(this.z);
        this.data.write(byteBuf);
        byteBuf.writeBoolean(this.sent);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final ThermalUpdatePacket packet, final IPayloadContext context) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        int x = packet.x();
        int z = packet.z();
        ThermalUpdateData data = packet.data();
        boolean sent = packet.sent();
        context.enqueueWork(() -> ((ILevelExtension) level).moreColorful$queueThermalUpdate(() -> {
            applyThermalData(level, x, z, data);
            if (sent) {
                LevelChunk levelchunk = level.getChunkSource().getChunk(x, z, false);
                if (levelchunk != null) {
                    enableChunkLight(level, levelchunk, x, z);
                }
            }
        }));
    }

    private static void applyThermalData(ClientLevel level, int pX, int pZ, ThermalUpdateData pData) {
        LevelThermalEngine thermalEngine = ((IChunkSourceExtension) level.getChunkSource()).moreColorful$getThermalEngine();
        BitSet bitset = pData.yMask();
        BitSet bitset1 = pData.emptyYMask();
        Iterator<byte[]> iterator = pData.updates().iterator();
        readSectionList(level, pX, pZ, thermalEngine, bitset, bitset1, iterator);
        thermalEngine.setThermalEnabled(new ChunkPos(pX, pZ), true);
    }

    private static void readSectionList(ClientLevel level, int pX, int pZ, LevelThermalEngine thermalEngine, BitSet yMask, BitSet emptyYMask, Iterator<byte[]> pUpdates) {
        for (int i = 0; i < thermalEngine.getThermalSectionCount(); i++) {
            int j = thermalEngine.getMinThermalSection() + i;
            boolean flag = yMask.get(i);
            boolean flag1 = emptyYMask.get(i);
            if (flag || flag1) {
                thermalEngine.queueSectionData(
                        SectionPos.of(pX, j, pZ), flag ? new DataLayer(pUpdates.next().clone()) : new DataLayer()
                );
                level.setSectionDirtyWithNeighbors(pX, j, pZ);
            }
        }
    }

    private static void enableChunkLight(ClientLevel level, LevelChunk pChunk, int pX, int pZ) {
        LevelThermalEngine thermalEngine = ((IChunkSourceExtension) level.getChunkSource()).moreColorful$getThermalEngine();
        LevelChunkSection[] sections = pChunk.getSections();
        ChunkPos chunkpos = pChunk.getPos();

        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];
            int j = level.getSectionYFromSectionIndex(i);
            thermalEngine.updateSectionStatus(SectionPos.of(chunkpos, j), section.hasOnlyAir());
            level.setSectionDirtyWithNeighbors(pX, j, pZ);
        }
    }
}
