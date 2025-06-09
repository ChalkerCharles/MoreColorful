package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public final class ModClientEvents {
    private static final Minecraft minecraft = Minecraft.getInstance();
    private static int removedLines = 0;
    private static Vector3f lastWindSpeed;
    private static int tickCounter = 0;

    @SubscribeEvent
    public static void onClientTickPost(ClientTickEvent.Post event) {
        DebugScreenOverlay debugScreenOverlay = minecraft.getDebugOverlay();
        boolean isDebugScreenOn = debugScreenOverlay.showDebugScreen();
        while (isDebugScreenOn && ModKeyMapping.DEBUG_TEXT_SCROLL_DOWN.get().consumeClick()) {
            removedLines = Math.max(0, removedLines - 1);
        }
        while (isDebugScreenOn && ModKeyMapping.DEBUG_TEXT_SCROLL_UP.get().consumeClick()) {
            removedLines = Math.min(removedLines + 1, 20);
        }

        if (Config.WIND_EFFECT_CLIENT.isTrue()) {
            if (tickCounter > 10) {
                updateWavySections();
                tickCounter = 0;
            }
            tickCounter++;
        }
    }

    private static void updateWavySections() {
        Level level = minecraft.level;
        if (level == null) return;
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        if (lastWindSpeed != null) {
            float dx = Mth.abs(wind.x - lastWindSpeed.x);
            float dz = Mth.abs(wind.z - lastWindSpeed.z);
            if (dx > 0.01F || dz > 0.01F) {
                ((ILevelRendererExtension) minecraft.levelRenderer).moreColorful$updateWavySections();
            }
        }
        lastWindSpeed = wind;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void renderDebugText(CustomizeGuiOverlayEvent.DebugText event) {
        ClientLevel level = minecraft.level;
        List<String> left = event.getLeft();
        List<String> right = event.getRight();
        BlockPos blockpos = Objects.requireNonNull(minecraft.getCameraEntity()).blockPosition();
        List<String> addLeft = new ArrayList<>();

        if (level != null) {
            if (Config.THERMAL_SYSTEM.isTrue()) {
                int temperature = ((ILevelExtension) level).moreColorful$getTemperature(blockpos);
                addLeft.add("Block Temperature: " + temperature);
            }
            if (WeatherUtils.isWindy(level)) {
                Vector2f wind = LevelSavedData.getGlobalWindSpeed(level);
                addLeft.add(String.format(Locale.ROOT, "Global Wind: %.4f / %.4f", wind.x(), wind.y()));
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
        ClientLevel level = minecraft.level;
        if (level == null) return;
        ProfilerFiller profilerfiller = level.getProfiler();
        if (Config.THERMAL_SYSTEM.isTrue()) {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_SKY) {
                profilerfiller.popPush("thermal_update_queue");
                ((ILevelExtension) level).moreColorful$pollThermalUpdates();
                profilerfiller.popPush("thermal_updates");
                ((IChunkSourceExtension) level.getChunkSource()).moreColorful$getThermalEngine().runThermalUpdates();
            }
        }
    }

    @SubscribeEvent
    public static void onClientLevelUnload(LevelEvent.Unload event) {
        if (Config.WIND_EFFECT_CLIENT.isFalse()) return;
        LevelAccessor level = event.getLevel();
        if (level.isClientSide()) {
            lastWindSpeed = null;
            tickCounter = 0;
        }
    }
}
