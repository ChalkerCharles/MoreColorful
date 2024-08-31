package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredRegister;
import  net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;


public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MoreColorful.MODID);
    // Blocks
    public static final Supplier<Item> CRABAPPLE_PLANKS = ITEMS.register("crabapple_planks", ()-> new BlockItem(ModBlocks.CRABAPPLE_PLANKS.get(),new Item.Properties()));
    public static final Supplier<Item> HARP = ITEMS.register("harp", ()-> new BlockItem(ModBlocks.HARP.get(), new Item.Properties()));
    public static final Supplier<Item> UPRIGHT_PIANO = ITEMS.register("upright_piano", ()-> new BlockItem(ModBlocks.UPRIGHT_PIANO.get(), new Item.Properties()));
    public static final Supplier<Item> GRAND_PIANO = ITEMS.register("grand_piano", ()-> new BlockItem(ModBlocks.GRAND_PIANO.get(), new Item.Properties()));
    public static final Supplier<Item> BASS_DRUM = ITEMS.register("bass_drum", ()-> new FuelBlockItem(ModBlocks.BASS_DRUM.get(), new Item.Properties(), 300));
    public static final Supplier<Item> SNARE_DRUM = ITEMS.register("snare_drum", ()-> new BlockItem(ModBlocks.SNARE_DRUM.get(), new Item.Properties()));
    public static final Supplier<Item> TOMTOM_DRUM = ITEMS.register("tom-tom_drum", ()-> new FuelBlockItem(ModBlocks.TOMTOM_DRUM.get(), new Item.Properties(), 300));
    public static final Supplier<Item> HIHAT = ITEMS.register("hi-hat", ()-> new BlockItem(ModBlocks.HIHAT.get(), new Item.Properties()));
    public static final Supplier<Item> RIDE_CYMBAL = ITEMS.register("ride_cymbal", ()-> new BlockItem(ModBlocks.RIDE_CYMBAL.get(), new Item.Properties()));
    public static final Supplier<Item> CRASH_CYMBAL = ITEMS.register("crash_cymbal", ()-> new BlockItem(ModBlocks.CRASH_CYMBAL.get(), new Item.Properties()));
    public static final Supplier<Item> CHIMES = ITEMS.register("chimes", ()-> new BlockItem(ModBlocks.CHIMES.get(), new Item.Properties()));
    public static final Supplier<Item> GLOCKENSPIEL = ITEMS.register("glockenspiel", ()-> new BlockItem(ModBlocks.GLOCKENSPIEL.get(), new Item.Properties()));
    public static final Supplier<Item> XYLOPHONE = ITEMS.register("xylophone", ()-> new FuelBlockItem(ModBlocks.XYLOPHONE.get(), new Item.Properties(), 300));
    public static final Supplier<Item> VIBRAPHONE = ITEMS.register("vibraphone", ()-> new BlockItem(ModBlocks.VIBRAPHONE.get(), new Item.Properties()));
    public static final Supplier<Item> SYNTHESIZER_KEYBOARD_BIT = ITEMS.register("synthesizer_keyboard_bit", ()-> new SynthesizerKeyboardItem(ModBlocks.SYNTHESIZER_KEYBOARD_BIT.get(), new Item.Properties()));
    public static final Supplier<Item> SYNTHESIZER_KEYBOARD_PLING = ITEMS.register("synthesizer_keyboard_pling", ()-> new SynthesizerKeyboardItem(ModBlocks.SYNTHESIZER_KEYBOARD_PLING.get(), new Item.Properties()));


    // Items
    public static final Supplier<Item> VIOLIN = ITEMS.register("violin", ()-> new BowedStringInstrumentItem(InstrumentsType.VIOLIN, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> FIDDLE_BOW = ITEMS.register("fiddle_bow", ()-> new FuelItem(new Item.Properties().stacksTo(1), 200));
    public static final Supplier<Item> DRUMSTICK = ITEMS.register("drumstick", ()-> new DrumstickItem(new Item.Properties()));
    public static final Supplier<Item> BASS = ITEMS.register("bass", ()-> new GuitarItem(InstrumentsType.BASS, new Item.Properties().stacksTo(1), 200));
    public static final Supplier<Item> GUITAR = ITEMS.register("guitar", ()-> new GuitarItem(InstrumentsType.GUITAR, new Item.Properties().stacksTo(1), 200));
    public static final Supplier<Item> FLUTE = ITEMS.register("flute", ()-> new BothHandsInstrumentItem(InstrumentsType.FLUTE, new Item.Properties().stacksTo(1), 200));
    public static final Supplier<Item> COW_BELL = ITEMS.register("cow_bell", ()-> new CowBellItem(InstrumentsType.COW_BELL, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> DIDGERIDOO = ITEMS.register("didgeridoo", ()-> new DidgeridooItem(InstrumentsType.DIDGERIDOO, new Item.Properties(), 200));
    public static final Supplier<Item> BANJO = ITEMS.register("banjo", ()-> new GuitarItem(InstrumentsType.BANJO, new Item.Properties().stacksTo(1), 200));
    public static final Supplier<Item> CELLO = ITEMS.register("cello", ()-> new BowedStringInstrumentItem(InstrumentsType.CELLO, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ELECTRIC_GUITAR = ITEMS.register("electric_guitar", ()-> new GuitarItem(InstrumentsType.ELECTRIC_GUITAR, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> TRUMPET = ITEMS.register("trumpet", ()-> new TrumpetItem(InstrumentsType.TRUMPET, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> SAXOPHONE = ITEMS.register("saxophone", ()-> new DidgeridooItem(InstrumentsType.SAXOPHONE, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> OCARINA = ITEMS.register("ocarina", ()-> new DidgeridooItem(InstrumentsType.OCARINA, new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> HARMONICA = ITEMS.register("harmonica", ()-> new DidgeridooItem(InstrumentsType.HARMONICA, new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}