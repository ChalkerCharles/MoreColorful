package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.ClientLevelData;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.network.packets.WindInitiationPacket;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@OnlyIn(Dist.CLIENT)
public final class ModClientEvents {
    private static int removedLines = 0;

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        DebugScreenOverlay debugScreenOverlay = Minecraft.getInstance().getDebugOverlay();
        boolean isDebugScreenOn = debugScreenOverlay.showDebugScreen();
        while (isDebugScreenOn && ModKeyMapping.DEBUG_TEXT_SCROLL_DOWN.get().consumeClick()) {
            removedLines = Math.max(0, removedLines - 1);
        }
        while (isDebugScreenOn && ModKeyMapping.DEBUG_TEXT_SCROLL_UP.get().consumeClick()) {
            removedLines = Math.min(removedLines + 1, 20);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        Minecraft minecraft = Minecraft.getInstance();
        Entity camera = minecraft.getCameraEntity();
        if (camera == null) return;
        BlockPos blockpos = camera.blockPosition();
        ClientLevel level = minecraft.level;
        List<String> left = event.getLeft();
        List<String> right = event.getRight();
        List<String> addLeft = new ArrayList<>();

        if (level != null) {
            if (Config.THERMAL_SYSTEM.isTrue()) {
                int temperature = LevelSavedData.getTemperature(level, blockpos);
                addLeft.add("Block Temperature: " + temperature);
            }
            if (Config.WIND_SYSTEM.isTrue()) {
                if (WeatherUtils.isWindy(level)) {
                    int ventilation = LevelSavedData.getVentilation(level, blockpos);
                    addLeft.add("Ventilation Level: " + ventilation);
                    Vector2f wind = LevelSavedData.getGlobalWindSpeed(level);
                    addLeft.add(String.format(Locale.ROOT, "Global Wind: %.4f / %.4f", wind.x, wind.y));
                    if (RenderUtils.isClientWindOn)
                        addLeft.add(ILevelRendererExtension.getStatistics());
                }
                addLeft.add(LevelSavedData.getWindZoneStats(level));
            }
        }

        if (!addLeft.isEmpty()) {
            addLeft.addFirst(ChatFormatting.GREEN + "[More Colorful]");
            addLeft.addFirst("");
        }
        left.addAll(addLeft);

        left.subList(0, Math.max(0, Math.min(removedLines, left.size() - 20))).clear();
        right.subList(0, Math.max(0, Math.min(removedLines, right.size() - 20))).clear();
    }

    @SubscribeEvent
    public static void onLevelRender(RenderLevelStageEvent event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        ProfilerFiller profilerfiller = level.getProfiler();
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
            if (Config.THERMAL_SYSTEM.isTrue()) {
                profilerfiller.popPush("thermal_update_queue");
                ClientLevelData.pollThermalUpdates(level);
                profilerfiller.popPush("thermal_updates");
                LevelSavedData.getThermalEngine(level).runThermalUpdates();
            }
            if (Config.WIND_SYSTEM.isTrue()) {
                profilerfiller.popPush("vent_update_queue");
                ClientLevelData.pollVentUpdates(level);
                profilerfiller.popPush("vent_updates");
                LevelSavedData.getVentEngine(level).runVentUpdates();
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        if (WeatherUtils.isWindy(event.getPlayer().clientLevel)) {
            PacketDistributor.sendToServer(WindInitiationPacket.INSTANCE);
        }
    }
}
