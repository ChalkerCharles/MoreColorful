package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.core.BlockPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        List<String> left = event.getLeft();
        List<String> right = event.getRight();
        BlockPos blockpos = Objects.requireNonNull(minecraft.getCameraEntity()).blockPosition();
        List<String> addLeft = new ArrayList<>();

        if (minecraft.level != null && Config.THERMAL_SYSTEM.isTrue()) {
            int temperature = ((ILevelExtension) minecraft.level).moreColorful$getTemperature(blockpos);
            addLeft.add("Block Temperature: " + temperature);
        }

        if (!addLeft.isEmpty()) {
            addLeft.addFirst(ChatFormatting.GREEN + "[More Colorful]");
            addLeft.addFirst("");
        }
        left.addAll(addLeft);

        left.subList(0, Math.max(0, Math.min(removedLines, left.size() - 20))).clear();
        right.subList(0, Math.max(0, Math.min(removedLines, right.size() - 20))).clear();
    }
}
