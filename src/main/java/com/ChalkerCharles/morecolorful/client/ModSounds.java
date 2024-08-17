package com.ChalkerCharles.morecolorful.client;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MoreColorful.MODID);
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO = registerSound("block.note_block.piano");
    public static final Holder<SoundEvent> NOTE_BLOCK_VIOLIN = registerSound("block.note_block.violin");
    public static final Holder<SoundEvent> NOTE_BLOCK_CELLO = registerSound("block.note_block.cello");
    public static final Holder<SoundEvent> NOTE_BLOCK_ELECTRIC_GUITAR = registerSound("block.note_block.electric_guitar");
    public static final Holder<SoundEvent> NOTE_BLOCK_TRUMPET = registerSound("block.note_block.trumpet");
    // Musical Instruments
    public static final Holder<SoundEvent> HARP_PLAY = registerSound("block.harp.play");
    public static final Holder<SoundEvent> PIANO_PLAY = registerSound("block.piano.play");
    public static final Holder<SoundEvent> BASS_DRUM_PLAY = registerSound("block.bass_drum.play");
    public static final Holder<SoundEvent> SNARE_PLAY = registerSound("block.snare.play");
    public static final Holder<SoundEvent> HAT_PLAY = registerSound("block.hat.play");
    public static final Holder<SoundEvent> BASS_PLAY = registerSound("item.bass.play");
    public static final Holder<SoundEvent> FLUTE_PLAY = registerSound("item.flute.play");
    public static final Holder<SoundEvent> GLOCKENSPIEL_PLAY = registerSound("block.glockenspiel.play");
    public static final Holder<SoundEvent> GUITAR_PLAY = registerSound("item.guitar.play");
    public static final Holder<SoundEvent> CHIMES_PLAY = registerSound("block.chimes.play");
    public static final Holder<SoundEvent> XYLOPHONE_PLAY = registerSound("block.xylophone.play");
    public static final Holder<SoundEvent> VIBRAPHONE_PLAY = registerSound("block.vibraphone.play");
    public static final Holder<SoundEvent> COW_BELL_PLAY = registerSound("item.cow_bell.play");
    public static final Holder<SoundEvent> DIDGERIDOO_PLAY = registerSound("item.didgeridoo.play");
    public static final Holder<SoundEvent> BIT_PLAY = registerSound("block.synthesizer_keyboard_bit.play");
    public static final Holder<SoundEvent> BANJO_PLAY = registerSound("item.banjo.play");
    public static final Holder<SoundEvent> PLING_PLAY = registerSound("block.synthesizer_keyboard_pling.play");
    public static final Holder<SoundEvent> VIOLIN_PLAY = registerSound("item.violin.play");
    public static final Holder<SoundEvent> CELLO_PLAY = registerSound("item.cello.play");
    public static final Holder<SoundEvent> ELECTRIC_GUITAR_PLAY = registerSound("item.electric_guitar.play");
    public static final Holder<SoundEvent> TRUMPET_PLAY = registerSound("item.trumpet.play");

    private static Holder<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name,()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, name)));
    }
    public static void register(IEventBus eventBus){SOUND_EVENTS.register(eventBus);}
}
