package com.ChalkerCharles.morecolorful.network;

import com.ChalkerCharles.morecolorful.client.gui.LetterViewScreen;
import com.ChalkerCharles.morecolorful.common.attachment.ClientLevelData;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.entity.misc.SmokeBomb;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.network.packets.*;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import net.minecraft.world.item.component.WrittenBookContent;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class ClientPacketHandler {
    public static void handleThermalUpdate(ThermalUpdatePacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        int x = packet.x(), z = packet.z();
        ClientLevelData.queueThermalUpdate(level, () -> {
            applyThermalData(level, x, z, packet.data());
            if (packet.sent()) {
                LevelChunk levelchunk = level.getChunkSource().getChunk(x, z, false);
                if (levelchunk != null) {
                    enableChunkThermal(level, x, z, levelchunk);
                }
            }
        });
    }

    private static void applyThermalData(ClientLevel level, int x, int z, ThermalUpdatePacket.ThermalUpdateData data) {
        ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
        Iterator<byte[]> iterator = data.updates().iterator();
        readSectionList(level, x, z, thermalEngine, data.yMask(), data.emptyYMask(), iterator);
        thermalEngine.setThermalEnabled(new ChunkPos(x, z), true);
    }

    private static void readSectionList(ClientLevel level, int x, int z, ILevelThermalEngine thermalEngine, BitSet yMask, BitSet emptyYMask, Iterator<byte[]> pUpdates) {
        for (int i = 0; i < thermalEngine.getThermalSectionCount(); i++) {
            int j = thermalEngine.getMinThermalSection() + i;
            boolean flag = yMask.get(i);
            boolean flag1 = emptyYMask.get(i);
            if (flag || flag1) {
                thermalEngine.queueSectionData(
                        SectionPos.of(x, j, z), flag ? new DataLayer(pUpdates.next().clone()) : new DataLayer()
                );
                level.setSectionDirtyWithNeighbors(x, j, z);
            }
        }
    }

    private static void enableChunkThermal(ClientLevel level, int x, int z, LevelChunk pChunk) {
        ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
        LevelChunkSection[] sections = pChunk.getSections();
        ChunkPos chunkpos = pChunk.getPos();

        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];
            int j = level.getSectionYFromSectionIndex(i);
            thermalEngine.updateSectionStatus(SectionPos.of(chunkpos, j), section.hasOnlyAir());
            level.setSectionDirtyWithNeighbors(x, j, z);
        }
    }

    public static void handleThermalRemoval(ThermalRemovalPacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        ChunkPos pos = packet.pos();
        ClientLevelData.queueThermalUpdate(level, () -> {
            ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
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

    public static void handleVentUpdate(VentUpdatePacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        int x = packet.x(), z = packet.z();
        ClientLevelData.queueVentUpdate(level, () -> {
            applyVentData(level, x, z, packet.data());
            if (packet.sent()) {
                LevelChunk levelchunk = level.getChunkSource().getChunk(x, z, false);
                if (levelchunk != null) {
                    enableChunkVent(level, x, z, levelchunk);
                }
            }
        });
    }

    private static void applyVentData(ClientLevel level, int x, int z, VentUpdatePacket.VentUpdateData data) {
        ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
        Iterator<byte[]> iterator = data.updates().iterator();
        readSectionList(level, x, z, ventEngine, data.yMask(), data.emptyYMask(), iterator);
        ventEngine.setVentEnabled(new ChunkPos(x, z), true);
    }

    private static void readSectionList(ClientLevel level, int x, int z, ILevelVentEngine ventEngine, BitSet yMask, BitSet emptyYMask, Iterator<byte[]> pUpdates) {
        for (int i = 0; i < ventEngine.getVentSectionCount(); i++) {
            int j = ventEngine.getMinVentSection() + i;
            boolean flag = yMask.get(i);
            boolean flag1 = emptyYMask.get(i);
            if (flag || flag1) {
                ventEngine.queueSectionData(
                        SectionPos.of(x, j, z), flag ? new DataLayer(pUpdates.next().clone()) : new DataLayer()
                );
                level.setSectionDirtyWithNeighbors(x, j, z);
            }
        }
    }

    private static void enableChunkVent(ClientLevel level, int x, int z, LevelChunk pChunk) {
        ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
        LevelChunkSection[] sections = pChunk.getSections();
        ChunkPos chunkpos = pChunk.getPos();

        for (int i = 0; i < sections.length; i++) {
            LevelChunkSection section = sections[i];
            int j = level.getSectionYFromSectionIndex(i);
            ventEngine.updateSectionStatus(SectionPos.of(chunkpos, j), section.hasOnlyAir());
            level.setSectionDirtyWithNeighbors(x, j, z);
        }
    }

    public static void handleVentRemoval(VentRemovalPacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        ChunkPos pos = packet.pos();
        ClientLevelData.queueVentUpdate(level, () -> {
            ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
            ventEngine.setVentEnabled(pos, false);

            for (int i = ventEngine.getMinVentSection(); i < ventEngine.getMaxVentSection(); i++) {
                SectionPos sectionpos = SectionPos.of(pos, i);
                ventEngine.queueSectionData(sectionpos, null);
            }

            for (int j = level.getMinSection(); j < level.getMaxSection(); j++) {
                ventEngine.updateSectionStatus(SectionPos.of(pos, j), true);
            }
        });
    }

    public static void handleSmokeBomb(SmokeBombPacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        Entity entity = level.getEntity(packet.id());
        if (entity instanceof SmokeBomb bomb) {
            ClientWrapper.createSmokeBomb(level, bomb);
        }
    }

    public static void handleOpenLetter(OpenLetterPacket packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;
        ItemStack itemstack = minecraft.player.getItemInHand(packet.hand());
        boolean flag = minecraft.isTextFilteringEnabled();
        WrittenBookContent writtenBookContent = itemstack.get(DataComponents.WRITTEN_BOOK_CONTENT);
        List<Component> pages;
        if (writtenBookContent != null) {
            pages = writtenBookContent.getPages(flag);
        } else {
            WritableBookContent writableBookContent = itemstack.get(DataComponents.WRITABLE_BOOK_CONTENT);
            pages = writableBookContent != null ? writableBookContent.getPages(flag).<Component>map(Component::literal).toList() : null;
        }
        if (pages != null) {
            minecraft.setScreen(new LetterViewScreen(pages));
        }
    }
}
