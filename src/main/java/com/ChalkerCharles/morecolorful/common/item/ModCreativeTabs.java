package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreColorful.MODID);

    public static final ResourceKey<CreativeModeTab> MUSICAL_INSTRUMENTS_TAB = CREATIVE_MODE_TABS.register("musical_instruments_tab",() -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("creativetab.morecolorful.musical_instruments_tab"))
            .icon(()->ModItems.VIOLIN.get().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.GRAND_PIANO.get());
                pOutput.accept(ModItems.UPRIGHT_PIANO.get());
                pOutput.accept(ModItems.HARP.get());
                pOutput.accept(ModItems.VIOLIN.get());
                pOutput.accept(ModItems.CELLO.get());
                pOutput.accept(ModItems.FIDDLE_BOW.get());
                pOutput.accept(ModItems.BASS.get());
                pOutput.accept(ModItems.GUITAR.get());
                pOutput.accept(ModItems.ELECTRIC_GUITAR.get());
                pOutput.accept(ModItems.BANJO.get());
                pOutput.accept(ModItems.FLUTE.get());
                pOutput.accept(ModItems.DIDGERIDOO.get());
                pOutput.accept(ModItems.TRUMPET.get());
                pOutput.accept(ModItems.SAXOPHONE.get());
                pOutput.accept(ModItems.OCARINA.get());
                pOutput.accept(ModItems.HARMONICA.get());
                pOutput.accept(ModItems.COW_BELL.get());
                pOutput.accept(ModItems.BASS_DRUM.get());
                pOutput.accept(ModItems.SNARE_DRUM.get());
                pOutput.accept(ModItems.TOMTOM_DRUM.get());
                pOutput.accept(ModItems.HIHAT.get());
                pOutput.accept(ModItems.RIDE_CYMBAL.get());
                pOutput.accept(ModItems.CRASH_CYMBAL.get());
                pOutput.accept(ModItems.CHIMES.get());
                pOutput.accept(ModItems.GLOCKENSPIEL.get());
                pOutput.accept(ModItems.XYLOPHONE.get());
                pOutput.accept(ModItems.VIBRAPHONE.get());
                pOutput.accept(ModItems.DRUMSTICK.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_BIT.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_PLING.get());
            })
            .build()).getKey();
    @SuppressWarnings("unused")
    public static final ResourceKey<CreativeModeTab> BLOCKS_TAB = CREATIVE_MODE_TABS.register("blocks_tab",() -> CreativeModeTab.builder()
            .withTabsBefore(MUSICAL_INSTRUMENTS_TAB)
            .title(Component.translatable("creativetab.morecolorful.blocks_tab"))
            .icon(()->ModItems.CRABAPPLE_PLANKS.get().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.CRABAPPLE_PLANKS.get());
            })
            .build()).getKey();
    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
