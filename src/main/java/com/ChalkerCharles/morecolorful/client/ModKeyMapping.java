package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.common.util.Lazy;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMapping {
    public static final Lazy<KeyMapping> OCTAVE_TOGGLE = Lazy.of(() -> new KeyMapping(
            "key.morecolorful.octave_toggle",
            KeyConflictContext.GUI,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_L,
            "key.categories.morecolorful.morecolorful"
    ));
    @SubscribeEvent
    public static void registerBindings(RegisterKeyMappingsEvent event) {
        event.register(OCTAVE_TOGGLE.get());
    }
}
