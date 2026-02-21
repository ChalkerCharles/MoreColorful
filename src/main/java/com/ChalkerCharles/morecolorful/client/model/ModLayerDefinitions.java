package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.renderer.block.CrashCymbalRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.block.DrumSetRenderer;
import com.ChalkerCharles.morecolorful.client.renderer.block.RideCymbalRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModLayerDefinitions {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RIDE_CYMBAL, RideCymbalRenderer::create);
        event.registerLayerDefinition(ModModelLayers.CRASH_CYMBAL, CrashCymbalRenderer::create);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_RIDE, DrumSetRenderer::createRide);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_CRASH, DrumSetRenderer::createCrash);
        event.registerLayerDefinition(ModModelLayers.BALLOON, BalloonModel::create);
    }
}
