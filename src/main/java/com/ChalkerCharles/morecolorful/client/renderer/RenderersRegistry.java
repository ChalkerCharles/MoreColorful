package com.ChalkerCharles.morecolorful.client.renderer;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.renderer.block.*;
import com.ChalkerCharles.morecolorful.client.renderer.entity.BalloonRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.entity.PaperBoatRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.entity.PaperPlaneRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.entity.SandBagRenderer;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
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
        event.registerBlockEntityRenderer(ModBlockEntities.WEATHER_VANE.get(), WeatherVaneRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.MUSIC_BOX.get(), MusicBoxRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PINWHEEL.get(), PinwheelRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PENNANT.get(), PennantRenderer::new);

        event.registerEntityRenderer(ModEntities.PAPER_PLANE.get(), PaperPlaneRenderer::new);
        event.registerEntityRenderer(ModEntities.PAPER_BOAT.get(), PaperBoatRenderer::new);
        event.registerEntityRenderer(ModEntities.BALLOON.get(), BalloonRenderer::new);
        event.registerEntityRenderer(ModEntities.SANDBAG.get(), SandBagRenderer::new);
    }
}
