package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.command.ModWeatherCommand;
import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelExtension;
import com.ChalkerCharles.morecolorful.network.packets.ThermalRemovalPacket;
import com.ChalkerCharles.morecolorful.network.packets.ThermalUpdatePacket;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class ModCommonEvents {
    @SubscribeEvent
    public static void onChunkSent(ChunkWatchEvent.Sent event) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ILevelThermalEngine thermalEngine = ((ILevelExtension) event.getLevel()).moreColorful$getThermalEngine();
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalUpdatePacket(
                    event.getPos(), thermalEngine, null, true
            ));
        }
    }

    @SubscribeEvent
    public static void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalRemovalPacket(event.getPos()));
        }
    }

    @SubscribeEvent
    public static void onLevelTickPost(LevelTickEvent.Post event) {
        if (!(event.getLevel() instanceof ServerLevel level)) return;
        if (WeatherUtils.isWindy(level)) {
            LevelSavedData.update(level);
        }
    }

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {
        ModWeatherCommand.register(event.getDispatcher());
    }
}
