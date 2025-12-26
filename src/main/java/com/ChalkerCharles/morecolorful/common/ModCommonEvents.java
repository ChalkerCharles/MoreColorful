package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.command.ModWeatherCommand;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.BurstWindZone;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.network.packets.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.level.ChunkEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.event.level.ExplosionEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public final class ModCommonEvents {
    @SubscribeEvent
    public static void onLevelTickPost(LevelTickEvent.Post event) {
        Level level = event.getLevel();
        if (level.tickRateManager().runsNormally()) {
            if (Config.WIND_SYSTEM.isTrue()) {
                LevelSavedData.tickWind(level);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityTickPre(EntityTickEvent.Pre event) {
        Entity entity = event.getEntity();
        if (Config.WIND_SYSTEM.isTrue() && Config.WIND_PHYSICS.isTrue()) {
            IEntityExtension.applyWind(entity);
        }
    }

    @SubscribeEvent
    public static void onChunkSent(ChunkWatchEvent.Sent event) {
        ServerLevel level = event.getLevel();
        if (Config.THERMAL_SYSTEM.isTrue()) {
            ILevelThermalEngine thermalEngine = LevelSavedData.getThermalEngine(level);
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalUpdatePacket(
                    event.getPos(), thermalEngine, null, true
            ));
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            ILevelVentEngine ventEngine = LevelSavedData.getVentEngine(level);
            PacketDistributor.sendToPlayer(event.getPlayer(), new VentUpdatePacket(
                    event.getPos(), ventEngine, null, true
            ));
        }
    }

    @SubscribeEvent
    public static void onChunkUnWatch(ChunkWatchEvent.UnWatch event) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalRemovalPacket(event.getPos()));
        }
        if (Config.WIND_SYSTEM.isTrue()) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new VentRemovalPacket(event.getPos()));
        }
    }

    @SubscribeEvent
    public static void onChunkUnload(ChunkEvent.Unload event) {
        if (Config.WIND_SYSTEM.isFalse()) return;
        ChunkAccess chunk = event.getChunk();
        Level level = chunk.getLevel();
        if (level == null) return;
        LevelSavedData.onChunkUnload(level, chunk.getPos());
    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        ModWeatherCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onExplosionStart(ExplosionEvent.Start event) {
        if (Config.WIND_SYSTEM.isFalse()) return;
        Level level = event.getLevel();
        if (level.isClientSide) return;
        Explosion explosion = event.getExplosion();
        Vec3 origin = explosion.center();
        float radius = explosion.radius() * 1.5F;
        BurstWindZone windZone = new BurstWindZone(origin, radius, 16, 20);
        LevelSavedData.addWindZone(level, windZone);
        PacketDistributor.sendToPlayersInDimension((ServerLevel) level, new WindZonePacket(windZone));
    }
}
