package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = MoreColorful.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ModItemProperties {

    @SubscribeEvent
    public static void register(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(ModItems.FLUTE.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.COW_BELL.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.DIDGERIDOO.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.VIOLIN.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_violin"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem().getItem() == ModItems.VIOLIN.get() ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.FIDDLE_BOW.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing_cello"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem().getItem() == ModItems.CELLO.get() ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.TRUMPET.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.SAXOPHONE.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.OCARINA.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
            ItemProperties.register(ModItems.HARMONICA.get(), ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "playing"),
                    (pStack, pLevel, pEntity, pSeed) -> pEntity != null && pEntity.isUsingItem() && pEntity.getUseItem() == pStack ? 1.0F : 0.0F);
        });
    }
}
