package com.ChalkerCharles.morecolorful.client.model;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.renderer.block.CymbalRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModLayerDefinitions {
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RIDE_CYMBAL, CymbalRenderer::createRide);
        event.registerLayerDefinition(ModModelLayers.CRASH_CYMBAL, CymbalRenderer::createCrash);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_RIDE, CymbalRenderer::createDrumSetRide);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_CRASH, CymbalRenderer::createDrumSetCrash);
    }
}
