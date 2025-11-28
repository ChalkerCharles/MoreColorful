package com.ChalkerCharles.morecolorful.client.renderer;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.renderer.block.CrashCymbalRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.block.DrumSetRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.block.RideCymbalRenderer;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderersRegistry {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(ModBlockEntities.RIDE_CYMBAL.get(), RideCymbalRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.CRASH_CYMBAL.get(), CrashCymbalRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.DRUM_SET.get(), DrumSetRenderer::new);
    }
}
