package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.gui.PlayingScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.IKeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMapping {
    private static final String CATEGORY = "key.categories.morecolorful.morecolorful";
    private static final ModKeyConflictContext PLAYING_SCREEN = () -> Minecraft.getInstance().screen instanceof PlayingScreen;
    private static final ModKeyConflictContext DEBUG_SCREEN = () -> Minecraft.getInstance().getDebugOverlay().showDebugScreen();

    public static final Lazy<KeyMapping> OCTAVE_TOGGLE = Lazy.of(() -> new KeyMapping(
            "key.morecolorful.octave_toggle",
            PLAYING_SCREEN,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_L,
            CATEGORY
    ));
    public static final Lazy<KeyMapping> DEBUG_TEXT_SCROLL_DOWN = Lazy.of(() -> new KeyMapping(
            "key.morecolorful.debug_text_scroll_up",
            DEBUG_SCREEN,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_UP,
            CATEGORY
    ));
    public static final Lazy<KeyMapping> DEBUG_TEXT_SCROLL_UP = Lazy.of(() -> new KeyMapping(
            "key.morecolorful.debug_text_scroll_down",
            DEBUG_SCREEN,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_DOWN,
            CATEGORY
    ));

    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(OCTAVE_TOGGLE.get());
        event.register(DEBUG_TEXT_SCROLL_DOWN.get());
        event.register(DEBUG_TEXT_SCROLL_UP.get());
    }

    @FunctionalInterface
    private interface ModKeyConflictContext extends IKeyConflictContext {
        @Override
        boolean isActive();

        @Override
        default boolean conflicts(IKeyConflictContext other) {
            return this == other;
        }
    }
}
