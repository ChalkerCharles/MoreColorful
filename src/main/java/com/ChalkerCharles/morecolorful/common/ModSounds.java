package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MoreColorful.MODID);
    
    // Note Blocks
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_LOW = registerForHolder("block.note_block.piano_low");
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_HIGH = registerForHolder("block.note_block.piano_high");
    public static final Holder<SoundEvent> NOTE_BLOCK_VIOLIN = registerForHolder("block.note_block.violin");
    public static final Holder<SoundEvent> NOTE_BLOCK_CELLO = registerForHolder("block.note_block.cello");
    public static final Holder<SoundEvent> NOTE_BLOCK_ELECTRIC_GUITAR = registerForHolder("block.note_block.electric_guitar");
    public static final Holder<SoundEvent> NOTE_BLOCK_TRUMPET = registerForHolder("block.note_block.trumpet");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAXOPHONE = registerForHolder("block.note_block.saxophone");
    public static final Holder<SoundEvent> NOTE_BLOCK_OCARINA = registerForHolder("block.note_block.ocarina");
    public static final Holder<SoundEvent> NOTE_BLOCK_HARMONICA = registerForHolder("block.note_block.harmonica");
    public static final Holder<SoundEvent> NOTE_BLOCK_TOM = registerForHolder("block.note_block.tom");
    public static final Holder<SoundEvent> NOTE_BLOCK_RIDE = registerForHolder("block.note_block.ride");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRASH = registerForHolder("block.note_block.crash");
    public static final Holder<SoundEvent> NOTE_BLOCK_SCULK = registerForHolder("block.note_block.sculk");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRYSTAL = registerForHolder("block.note_block.crystal");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAW = registerForHolder("block.note_block.saw");
    public static final Holder<SoundEvent> NOTE_BLOCK_PLUCK = registerForHolder("block.note_block.pluck");
    public static final Holder<SoundEvent> NOTE_BLOCK_SYNTH_BASS = registerForHolder("block.note_block.synth_bass");
    public static final Holder<SoundEvent> NOTE_BLOCK_PIPA = registerForHolder("block.note_block.pipa");
    public static final Holder<SoundEvent> NOTE_BLOCK_ERHU = registerForHolder("block.note_block.erhu");
    public static final Holder<SoundEvent> NOTE_BLOCK_GUZHENG = registerForHolder("block.note_block.guzheng");

    // Musical Instruments
    public static final Holder<SoundEvent> HARP_PLAY = registerForHolder("block.harp.play");
    public static final Holder<SoundEvent> PIANO_LOW_PLAY = registerForHolder("block.piano_low.play");
    public static final Holder<SoundEvent> PIANO_HIGH_PLAY = registerForHolder("block.piano_high.play");
    public static final Holder<SoundEvent> BASS_DRUM_PLAY = registerForHolder("block.bass_drum.play");
    public static final Holder<SoundEvent> SNARE_PLAY = registerForHolder("block.snare.play");
    public static final Holder<SoundEvent> TOM_PLAY = registerForHolder("block.tom.play");
    public static final Holder<SoundEvent> HAT_PLAY = registerForHolder("block.hat.play");
    public static final Holder<SoundEvent> RIDE_PLAY = registerForHolder("block.ride.play");
    public static final Holder<SoundEvent> CRASH_PLAY = registerForHolder("block.crash.play");
    public static final Holder<SoundEvent> BASS_PLAY = registerForHolder("item.bass.play");
    public static final Holder<SoundEvent> FLUTE_PLAY = registerForHolder("item.flute.play");
    public static final Holder<SoundEvent> GLOCKENSPIEL_PLAY = registerForHolder("block.glockenspiel.play");
    public static final Holder<SoundEvent> GUITAR_PLAY = registerForHolder("item.guitar.play");
    public static final Holder<SoundEvent> CHIMES_PLAY = registerForHolder("block.chimes.play");
    public static final Holder<SoundEvent> XYLOPHONE_PLAY = registerForHolder("block.xylophone.play");
    public static final Holder<SoundEvent> VIBRAPHONE_PLAY = registerForHolder("block.vibraphone.play");
    public static final Holder<SoundEvent> COW_BELL_PLAY = registerForHolder("item.cow_bell.play");
    public static final Holder<SoundEvent> DIDGERIDOO_PLAY = registerForHolder("item.didgeridoo.play");
    public static final Holder<SoundEvent> BIT_PLAY = registerForHolder("block.synthesizer_keyboard_bit.play");
    public static final Holder<SoundEvent> BANJO_PLAY = registerForHolder("item.banjo.play");
    public static final Holder<SoundEvent> PLING_PLAY = registerForHolder("block.synthesizer_keyboard_pling.play");
    public static final Holder<SoundEvent> VIOLIN_PLAY = registerForHolder("item.violin.play");
    public static final Holder<SoundEvent> CELLO_PLAY = registerForHolder("item.cello.play");
    public static final Holder<SoundEvent> ELECTRIC_GUITAR_PLAY = registerForHolder("item.electric_guitar.play");
    public static final Holder<SoundEvent> TRUMPET_PLAY = registerForHolder("item.trumpet.play");
    public static final Holder<SoundEvent> SAXOPHONE_PLAY = registerForHolder("item.saxophone.play");
    public static final Holder<SoundEvent> OCARINA_PLAY = registerForHolder("item.ocarina.play");
    public static final Holder<SoundEvent> HARMONICA_PLAY = registerForHolder("item.harmonica.play");
    public static final Holder<SoundEvent> SCULK_PLAY = registerForHolder("block.synthesizer_keyboard_sculk.play");
    public static final Holder<SoundEvent> CRYSTAL_PLAY = registerForHolder("block.synthesizer_keyboard_crystal.play");
    public static final Holder<SoundEvent> SAW_PLAY = registerForHolder("block.synthesizer_keyboard_saw.play");
    public static final Holder<SoundEvent> PLUCK_PLAY = registerForHolder("block.synthesizer_keyboard_pluck.play");
    public static final Holder<SoundEvent> SYNTH_BASS_PLAY = registerForHolder("block.synthesizer_keyboard_synth_bass.play");
    public static final Holder<SoundEvent> PIPA_PLAY = registerForHolder("item.pipa.play");
    public static final Holder<SoundEvent> ERHU_PLAY = registerForHolder("item.erhu.play");
    public static final Holder<SoundEvent> GUZHENG_PLAY = registerForHolder("block.guzheng.play");
    
    // Common Blocks
    public static final Supplier<SoundEvent> RARE_WOOD_BREAK = register("block.rare_wood.break");
    public static final Supplier<SoundEvent> RARE_WOOD_STEP = register("block.rare_wood.step");
    public static final Supplier<SoundEvent> RARE_WOOD_PLACE = register("block.rare_wood.place");
    public static final Supplier<SoundEvent> RARE_WOOD_HIT = register("block.rare_wood.hit");
    public static final Supplier<SoundEvent> RARE_WOOD_FALL = register("block.rare_wood.fall");
    public static final Supplier<SoundEvent> RARE_WOOD_DOOR_CLOSE = register("block.rare_wood.door.close");
    public static final Supplier<SoundEvent> RARE_WOOD_DOOR_OPEN = register("block.rare_wood.door.open");
    public static final Supplier<SoundEvent> RARE_WOOD_TRAPDOOR_CLOSE = register("block.rare_wood.trapdoor.close");
    public static final Supplier<SoundEvent> RARE_WOOD_TRAPDOOR_OPEN = register("block.rare_wood.trapdoor.open");
    public static final Supplier<SoundEvent> RARE_WOOD_PRESSURE_PLATE_CLICK_OFF = register("block.rare_wood.pressure_plate.click_off");
    public static final Supplier<SoundEvent> RARE_WOOD_PRESSURE_PLATE_CLICK_ON = register("block.rare_wood.pressure_plate.click_on");
    public static final Supplier<SoundEvent> RARE_WOOD_BUTTON_CLICK_OFF = register("block.rare_wood.button.click_off");
    public static final Supplier<SoundEvent> RARE_WOOD_BUTTON_CLICK_ON = register("block.rare_wood.button.click_on");
    public static final Supplier<SoundEvent> RARE_WOOD_HANGING_SIGN_BREAK = register("block.rare_wood.hanging_sign.break");
    public static final Supplier<SoundEvent> RARE_WOOD_HANGING_SIGN_STEP = register("block.rare_wood.hanging_sign.step");
    public static final Supplier<SoundEvent> RARE_WOOD_HANGING_SIGN_PLACE = register("block.rare_wood.hanging_sign.place");
    public static final Supplier<SoundEvent> RARE_WOOD_HANGING_SIGN_HIT = register("block.rare_wood.hanging_sign.hit");
    public static final Supplier<SoundEvent> RARE_WOOD_HANGING_SIGN_FALL = register("block.rare_wood.hanging_sign.fall");
    public static final Supplier<SoundEvent> RARE_WOOD_FENCE_GATE_CLOSE = register("block.rare_wood.fence_gate.close");
    public static final Supplier<SoundEvent> RARE_WOOD_FENCE_GATE_OPEN = register("block.rare_wood.fence_gate.open");

    private static Holder<SoundEvent> registerForHolder(String name) {
        return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, name)));
    }
    private static Supplier<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, ()-> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, name)));
    }
    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
