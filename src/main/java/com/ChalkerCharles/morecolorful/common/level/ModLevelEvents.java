package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.network.packets.ThermalRemovalPacket;
import com.ChalkerCharles.morecolorful.network.packets.ThermalUpdatePacket;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.level.ChunkWatchEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = MoreColorful.MODID)
public final class ModLevelEvents {
    @SubscribeEvent
    public static void onChunkSent(ChunkWatchEvent.Sent event) {
        if (Config.THERMAL_SYSTEM.isTrue()) {
            PacketDistributor.sendToPlayer(event.getPlayer(), new ThermalUpdatePacket(
                    event.getPos(), ((ILevelExtension) event.getLevel()).moreColorful$getThermalEngine(), null, true
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
    public static void onLevelRender(RenderLevelStageEvent event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        ProfilerFiller profilerfiller = level.getProfiler();
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
                profilerfiller.popPush("thermal_update_queue");
                ((ILevelExtension) level).moreColorful$pollLightUpdates();
                profilerfiller.popPush("thermal_updates");
                ((IChunkSourceExtension) level.getChunkSource()).moreColorful$getThermalEngine().runThermalUpdates();
            }
        }
    }
}
