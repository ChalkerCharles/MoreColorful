package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MoreColorful.MODID);
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_LOW = registerSound("block.note_block.piano_low");
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_HIGH = registerSound("block.note_block.piano_high");
    public static final Holder<SoundEvent> NOTE_BLOCK_VIOLIN = registerSound("block.note_block.violin");
    public static final Holder<SoundEvent> NOTE_BLOCK_CELLO = registerSound("block.note_block.cello");
    public static final Holder<SoundEvent> NOTE_BLOCK_ELECTRIC_GUITAR = registerSound("block.note_block.electric_guitar");
    public static final Holder<SoundEvent> NOTE_BLOCK_TRUMPET = registerSound("block.note_block.trumpet");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAXOPHONE = registerSound("block.note_block.saxophone");
    public static final Holder<SoundEvent> NOTE_BLOCK_OCARINA = registerSound("block.note_block.ocarina");
    public static final Holder<SoundEvent> NOTE_BLOCK_HARMONICA = registerSound("block.note_block.harmonica");
    public static final Holder<SoundEvent> NOTE_BLOCK_TOM = registerSound("block.note_block.tom");
    public static final Holder<SoundEvent> NOTE_BLOCK_RIDE = registerSound("block.note_block.ride");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRASH = registerSound("block.note_block.crash");
    public static final Holder<SoundEvent> NOTE_BLOCK_SCULK = registerSound("block.note_block.sculk");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRYSTAL = registerSound("block.note_block.crystal");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAW = registerSound("block.note_block.saw");
    public static final Holder<SoundEvent> NOTE_BLOCK_PLUCK = registerSound("block.note_block.pluck");
    public static final Holder<SoundEvent> NOTE_BLOCK_SYNTH_BASS = registerSound("block.note_block.synth_bass");

    // Musical Instruments
    public static final Holder<SoundEvent> HARP_PLAY = registerSound("block.harp.play");
    public static final Holder<SoundEvent> PIANO_LOW_PLAY = registerSound("block.piano_low.play");
    public static final Holder<SoundEvent> PIANO_HIGH_PLAY = registerSound("block.piano_high.play");
    public static final Holder<SoundEvent> BASS_DRUM_PLAY = registerSound("block.bass_drum.play");
    public static final Holder<SoundEvent> SNARE_PLAY = registerSound("block.snare.play");
    public static final Holder<SoundEvent> TOM_PLAY = registerSound("block.tom.play");
    public static final Holder<SoundEvent> HAT_PLAY = registerSound("block.hat.play");
    public static final Holder<SoundEvent> RIDE_PLAY = registerSound("block.ride.play");
    public static final Holder<SoundEvent> CRASH_PLAY = registerSound("block.crash.play");
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
    public static final Holder<SoundEvent> SAXOPHONE_PLAY = registerSound("item.saxophone.play");
    public static final Holder<SoundEvent> OCARINA_PLAY = registerSound("item.ocarina.play");
    public static final Holder<SoundEvent> HARMONICA_PLAY = registerSound("item.harmonica.play");
    public static final Holder<SoundEvent> SCULK_PLAY = registerSound("block.synthesizer_keyboard_sculk.play");
    public static final Holder<SoundEvent> CRYSTAL_PLAY = registerSound("block.synthesizer_keyboard_crystal.play");
    public static final Holder<SoundEvent> SAW_PLAY = registerSound("block.synthesizer_keyboard_saw.play");
    public static final Holder<SoundEvent> PLUCK_PLAY = registerSound("block.synthesizer_keyboard_pluck.play");
    public static final Holder<SoundEvent> SYNTH_BASS_PLAY = registerSound("block.synthesizer_keyboard_synth_bass.play");

    private static Holder<SoundEvent> registerSound(String name) {
        return SOUND_EVENTS.register(name,()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, name)));
    }
    public static void register(IEventBus eventBus){SOUND_EVENTS.register(eventBus);}
}
