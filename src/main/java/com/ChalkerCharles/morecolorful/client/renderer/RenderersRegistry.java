package com.ChalkerCharles.morecolorful.client.renderer;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.model.*;
import com.ChalkerCharles.morecolorful.client.renderer.block.*;
import com.ChalkerCharles.morecolorful.client.renderer.entity.*;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.EntityType;
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
        event.registerBlockEntityRenderer(ModBlockEntities.PAPERCUTTING.get(), PapercuttingRenderer::new);

        event.registerEntityRenderer(ModEntities.PAPER_PLANE.get(), PaperPlaneRenderer::new);
        event.registerEntityRenderer(ModEntities.PAPER_BOAT.get(), PaperBoatRenderer::new);
        event.registerEntityRenderer(ModEntities.BALLOON.get(), BalloonRenderer::new);
        event.registerEntityRenderer(ModEntities.SANDBAG.get(), SandBagRenderer::new);
        event.registerEntityRenderer(ModEntities.KITE.get(), KiteRenderer::new);
        event.registerEntityRenderer(ModEntities.UNDERWATER_TNT.get(), TntRenderer::new);
        event.registerEntityRenderer(ModEntities.BOMB.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntities.BUTTERFLY.get(), MothRenderer::createButterfly);
        event.registerEntityRenderer(ModEntities.MOTH.get(), MothRenderer::createMoth);
        event.registerEntityRenderer(ModEntities.CATERPILLAR.get(), CaterpillarRenderer::new);
        event.registerEntityRenderer(ModEntities.SMOKE_BOMB.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.RIDE_CYMBAL, RideCymbalRenderer::create);
        event.registerLayerDefinition(ModModelLayers.CRASH_CYMBAL, CrashCymbalRenderer::create);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_RIDE, DrumSetRenderer::createRide);
        event.registerLayerDefinition(ModModelLayers.DRUM_SET_CRASH, DrumSetRenderer::createCrash);
        event.registerLayerDefinition(ModModelLayers.BALLOON, BalloonModel::create);
        event.registerLayerDefinition(ModModelLayers.BALLOON_HEART, BalloonModel::createHeart);
        event.registerLayerDefinition(ModModelLayers.BALLOON_STAR, BalloonModel::createStar);
        event.registerLayerDefinition(ModModelLayers.BALLOON_RABBIT, BalloonModel::createRabbit);
        event.registerLayerDefinition(ModModelLayers.BUTTERFLY, MothModel::createButterfly);
        event.registerLayerDefinition(ModModelLayers.MOTH, MothModel::createMoth);
        event.registerLayerDefinition(ModModelLayers.CATERPILLAR, CaterpillarModel::create);
        event.registerLayerDefinition(ModModelLayers.VEIL, VillagerVeilLayer::createVeil);
    }

    @SubscribeEvent
    public static void addLayers(EntityRenderersEvent.AddLayers event) {
        EntityRendererProvider.Context context = event.getContext();
        VillagerRenderer villagerRenderer = event.getRenderer(EntityType.VILLAGER);
        if (villagerRenderer != null) {
            villagerRenderer.addLayer(new VillagerVeilLayer<>(villagerRenderer, context));
        }
        ZombieVillagerRenderer zombieVillagerRenderer = event.getRenderer(EntityType.ZOMBIE_VILLAGER);
        if (zombieVillagerRenderer != null) {
            zombieVillagerRenderer.addLayer(new VillagerVeilLayer<>(zombieVillagerRenderer, context));
        }
    }
}
