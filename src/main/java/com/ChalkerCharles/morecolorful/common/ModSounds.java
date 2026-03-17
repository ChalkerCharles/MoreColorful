package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MoreColorful.MODID);
    
    // Note Blocks
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_LOW = register("block.note_block.piano_low");
    public static final Holder<SoundEvent> NOTE_BLOCK_PIANO_HIGH = register("block.note_block.piano_high");
    public static final Holder<SoundEvent> NOTE_BLOCK_VIOLIN = register("block.note_block.violin");
    public static final Holder<SoundEvent> NOTE_BLOCK_CELLO = register("block.note_block.cello");
    public static final Holder<SoundEvent> NOTE_BLOCK_ELECTRIC_GUITAR = register("block.note_block.electric_guitar");
    public static final Holder<SoundEvent> NOTE_BLOCK_TRUMPET = register("block.note_block.trumpet");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAXOPHONE = register("block.note_block.saxophone");
    public static final Holder<SoundEvent> NOTE_BLOCK_OCARINA = register("block.note_block.ocarina");
    public static final Holder<SoundEvent> NOTE_BLOCK_HARMONICA = register("block.note_block.harmonica");
    public static final Holder<SoundEvent> NOTE_BLOCK_TOM = register("block.note_block.tom");
    public static final Holder<SoundEvent> NOTE_BLOCK_RIDE = register("block.note_block.ride");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRASH = register("block.note_block.crash");
    public static final Holder<SoundEvent> NOTE_BLOCK_SCULK = register("block.note_block.sculk");
    public static final Holder<SoundEvent> NOTE_BLOCK_CRYSTAL = register("block.note_block.crystal");
    public static final Holder<SoundEvent> NOTE_BLOCK_SAW = register("block.note_block.saw");
    public static final Holder<SoundEvent> NOTE_BLOCK_PLUCK = register("block.note_block.pluck");
    public static final Holder<SoundEvent> NOTE_BLOCK_SYNTH_BASS = register("block.note_block.synth_bass");
    public static final Holder<SoundEvent> NOTE_BLOCK_PIPA = register("block.note_block.pipa");
    public static final Holder<SoundEvent> NOTE_BLOCK_ERHU = register("block.note_block.erhu");
    public static final Holder<SoundEvent> NOTE_BLOCK_GUZHENG = register("block.note_block.guzheng");

    // Musical Instruments
    public static final Holder<SoundEvent> HARP_PLAY = register("block.harp.play");
    public static final Holder<SoundEvent> PIANO_LOW_PLAY = register("block.piano_low.play");
    public static final Holder<SoundEvent> PIANO_HIGH_PLAY = register("block.piano_high.play");
    public static final Holder<SoundEvent> BASS_DRUM_PLAY = register("block.bass_drum.play");
    public static final Holder<SoundEvent> SNARE_PLAY = register("block.snare.play");
    public static final Holder<SoundEvent> TOM_PLAY = register("block.tom.play");
    public static final Holder<SoundEvent> HAT_PLAY = register("block.hat.play");
    public static final Holder<SoundEvent> RIDE_PLAY = register("block.ride.play");
    public static final Holder<SoundEvent> CRASH_PLAY = register("block.crash.play");
    public static final Holder<SoundEvent> BASS_PLAY = register("item.bass.play");
    public static final Holder<SoundEvent> FLUTE_PLAY = register("item.flute.play");
    public static final Holder<SoundEvent> GLOCKENSPIEL_PLAY = register("block.glockenspiel.play");
    public static final Holder<SoundEvent> GUITAR_PLAY = register("item.guitar.play");
    public static final Holder<SoundEvent> CHIMES_PLAY = register("block.chimes.play");
    public static final Holder<SoundEvent> XYLOPHONE_PLAY = register("block.xylophone.play");
    public static final Holder<SoundEvent> VIBRAPHONE_PLAY = register("block.vibraphone.play");
    public static final Holder<SoundEvent> COW_BELL_PLAY = register("item.cow_bell.play");
    public static final Holder<SoundEvent> DIDGERIDOO_PLAY = register("item.didgeridoo.play");
    public static final Holder<SoundEvent> BIT_PLAY = register("block.synthesizer_keyboard_bit.play");
    public static final Holder<SoundEvent> BANJO_PLAY = register("item.banjo.play");
    public static final Holder<SoundEvent> PLING_PLAY = register("block.synthesizer_keyboard_pling.play");
    public static final Holder<SoundEvent> VIOLIN_PLAY = register("item.violin.play");
    public static final Holder<SoundEvent> CELLO_PLAY = register("item.cello.play");
    public static final Holder<SoundEvent> ELECTRIC_GUITAR_PLAY = register("item.electric_guitar.play");
    public static final Holder<SoundEvent> TRUMPET_PLAY = register("item.trumpet.play");
    public static final Holder<SoundEvent> SAXOPHONE_PLAY = register("item.saxophone.play");
    public static final Holder<SoundEvent> OCARINA_PLAY = register("item.ocarina.play");
    public static final Holder<SoundEvent> HARMONICA_PLAY = register("item.harmonica.play");
    public static final Holder<SoundEvent> SCULK_PLAY = register("block.synthesizer_keyboard_sculk.play");
    public static final Holder<SoundEvent> CRYSTAL_PLAY = register("block.synthesizer_keyboard_crystal.play");
    public static final Holder<SoundEvent> SAW_PLAY = register("block.synthesizer_keyboard_saw.play");
    public static final Holder<SoundEvent> PLUCK_PLAY = register("block.synthesizer_keyboard_pluck.play");
    public static final Holder<SoundEvent> SYNTH_BASS_PLAY = register("block.synthesizer_keyboard_synth_bass.play");
    public static final Holder<SoundEvent> PIPA_PLAY = register("item.pipa.play");
    public static final Holder<SoundEvent> ERHU_PLAY = register("item.erhu.play");
    public static final Holder<SoundEvent> GUZHENG_PLAY = register("block.guzheng.play");

    // Music Boxes
    public static final Holder<SoundEvent> MUSIC_BOX_HARP = register("block.music_box.harp");
    public static final Holder<SoundEvent> MUSIC_BOX_BASEDRUM = register("block.music_box.basedrum");
    public static final Holder<SoundEvent> MUSIC_BOX_SNARE = register("block.music_box.snare");
    public static final Holder<SoundEvent> MUSIC_BOX_HAT = register("block.music_box.hat");
    public static final Holder<SoundEvent> MUSIC_BOX_BASS = register("block.music_box.bass");
    public static final Holder<SoundEvent> MUSIC_BOX_CHIME = register("block.music_box.chime");
    public static final Holder<SoundEvent> MUSIC_BOX_BELL = register("block.music_box.bell");
    public static final Holder<SoundEvent> MUSIC_BOX_FLUTE = register("block.music_box.flute");
    public static final Holder<SoundEvent> MUSIC_BOX_GUITAR = register("block.music_box.guitar");
    public static final Holder<SoundEvent> MUSIC_BOX_XYLOPHONE = register("block.music_box.xylophone");
    public static final Holder<SoundEvent> MUSIC_BOX_IRON_XYLOPHONE = register("block.music_box.iron_xylophone");
    public static final Holder<SoundEvent> MUSIC_BOX_COW_BELL = register("block.music_box.cow_bell");
    public static final Holder<SoundEvent> MUSIC_BOX_DIDGERIDOO = register("block.music_box.didgeridoo");
    public static final Holder<SoundEvent> MUSIC_BOX_BIT = register("block.music_box.bit");
    public static final Holder<SoundEvent> MUSIC_BOX_BANJO = register("block.music_box.banjo");
    public static final Holder<SoundEvent> MUSIC_BOX_PLING = register("block.music_box.pling");
    public static final Holder<SoundEvent> MUSIC_BOX_PIANO_LOW = register("block.music_box.piano_low");
    public static final Holder<SoundEvent> MUSIC_BOX_PIANO_HIGH = register("block.music_box.piano_high");
    public static final Holder<SoundEvent> MUSIC_BOX_TOM = register("block.music_box.tom");
    public static final Holder<SoundEvent> MUSIC_BOX_RIDE = register("block.music_box.ride");
    public static final Holder<SoundEvent> MUSIC_BOX_CRASH = register("block.music_box.crash");
    public static final Holder<SoundEvent> MUSIC_BOX_VIOLIN = register("block.music_box.violin");
    public static final Holder<SoundEvent> MUSIC_BOX_CELLO = register("block.music_box.cello");
    public static final Holder<SoundEvent> MUSIC_BOX_ELECTRIC_GUITAR = register("block.music_box.electric_guitar");
    public static final Holder<SoundEvent> MUSIC_BOX_TRUMPET = register("block.music_box.trumpet");
    public static final Holder<SoundEvent> MUSIC_BOX_SAXOPHONE = register("block.music_box.saxophone");
    public static final Holder<SoundEvent> MUSIC_BOX_OCARINA = register("block.music_box.ocarina");
    public static final Holder<SoundEvent> MUSIC_BOX_HARMONICA = register("block.music_box.harmonica");
    public static final Holder<SoundEvent> MUSIC_BOX_SCULK = register("block.music_box.sculk");
    public static final Holder<SoundEvent> MUSIC_BOX_CRYSTAL = register("block.music_box.crystal");
    public static final Holder<SoundEvent> MUSIC_BOX_SAW = register("block.music_box.saw");
    public static final Holder<SoundEvent> MUSIC_BOX_PLUCK = register("block.music_box.pluck");
    public static final Holder<SoundEvent> MUSIC_BOX_SYNTH_BASS = register("block.music_box.synth_bass");
    public static final Holder<SoundEvent> MUSIC_BOX_PIPA = register("block.music_box.pipa");
    public static final Holder<SoundEvent> MUSIC_BOX_ERHU = register("block.music_box.erhu");
    public static final Holder<SoundEvent> MUSIC_BOX_GUZHENG = register("block.music_box.guzheng");
    
    // Blocks
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
    public static final Supplier<SoundEvent> LEAF_LITTER_BREAK = register("block.leaf_litter.break");
    public static final Supplier<SoundEvent> LEAF_LITTER_STEP = register("block.leaf_litter.step");
    public static final Supplier<SoundEvent> LEAF_LITTER_PLACE = register("block.leaf_litter.place");
    public static final Supplier<SoundEvent> LEAF_LITTER_HIT = register("block.leaf_litter.hit");
    public static final Supplier<SoundEvent> LEAF_LITTER_FALL = register("block.leaf_litter.fall");
    public static final Supplier<SoundEvent> FAN_BLOCK_WHIR = register("block.fan_block.whir");
    public static final Supplier<SoundEvent> LEAVES_RUSTLE = register("block.leaves.rustle");
    public static final Supplier<SoundEvent> WEATHER_VANE_SWAY = register("block.weather_vane.sway");
    public static final Supplier<SoundEvent> RIBBON_TIED = register("block.ribbon.tied");
    public static final Supplier<SoundEvent> SANDBAG_LAND = register("block.sandbag.land");
    public static final Supplier<SoundEvent> PYROTECHNICS_TABLE_USE = register("block.pyrotechnics_table.use");
    public static final Supplier<SoundEvent> PAPER_BREAK = register("block.paper.break");
    public static final Supplier<SoundEvent> PAPER_STEP = register("block.paper.step");
    public static final Supplier<SoundEvent> PAPER_PLACE = register("block.paper.place");
    public static final Supplier<SoundEvent> PAPER_HIT = register("block.paper.hit");
    public static final Supplier<SoundEvent> PAPER_FALL = register("block.paper.fall");
    public static final Supplier<SoundEvent> PAPER_CUT = register("block.paper.cut");
    public static final Supplier<SoundEvent> CARDBOARD_BREAK = register("block.cardboard.break");
    public static final Supplier<SoundEvent> CARDBOARD_STEP = register("block.cardboard.step");
    public static final Supplier<SoundEvent> CARDBOARD_PLACE = register("block.cardboard.place");
    public static final Supplier<SoundEvent> CARDBOARD_HIT = register("block.cardboard.hit");
    public static final Supplier<SoundEvent> CARDBOARD_FALL = register("block.cardboard.fall");

    // Entities
    public static final Supplier<SoundEvent> PAPER_PLANE_THROW = register("entity.paper_plane.throw");
    public static final Supplier<SoundEvent> PAPER_PLANE_HIT = register("entity.paper_plane.hit");
    public static final Supplier<SoundEvent> BALLOON_POP = register("entity.balloon.pop");
    public static final Supplier<SoundEvent> BALLOON_HIT = register("entity.balloon.hit");
    public static final Supplier<SoundEvent> BALLOON_INFLATE = register("entity.balloon.inflate");
    public static final Supplier<SoundEvent> KITE_REEL = register("entity.kite.reel");
    public static final Supplier<SoundEvent> KITE_UNREEL = register("entity.kite.unreel");
    public static final Supplier<SoundEvent> BOMB_THROW = register("entity.bomb.throw");

    // Items
    public static final Supplier<SoundEvent> PARTY_POPPER_POP = register("item.party_popper.pop");
    public static final Supplier<SoundEvent> SHEARS_SNIP = register("item.shears.snip");
    public static final Supplier<SoundEvent> LEAD_UNTIED = register("item.lead.untied");
    public static final Supplier<SoundEvent> LEAD_TIED = register("item.lead.tied");
    public static final Supplier<SoundEvent> LEAD_BREAK = register("item.lead.break");
    public static final Supplier<SoundEvent> SPARKLER_LIT = register("item.sparkler.lit");
    public static final Supplier<SoundEvent> SPARKLER_FIZZ = register("item.sparkler.fizz");
    public static final Supplier<SoundEvent> SPARKLER_EXTINGUISH = register("item.sparkler.extinguish");
    public static final Supplier<SoundEvent> UMBRELLA_OPEN = register("item.umbrella.open");
    public static final Supplier<SoundEvent> UMBRELLA_CLOSE = register("item.umbrella.close");
    public static final Supplier<SoundEvent> UMBRELLA_BLOCK = register("item.umbrella.block");

    // Environment & Ambient
    public static final Supplier<SoundEvent> WEATHER_BREEZE = register("weather.breeze");
    public static final Supplier<SoundEvent> WEATHER_WIND = register("weather.wind");
    public static final Supplier<SoundEvent> WEATHER_GALE = register("weather.gale");
    public static final Supplier<SoundEvent> WEATHER_RAIN_UMBRELLA = register("weather.rain_umbrella");

    // Music
    //public static final Holder<SoundEvent> MUSIC_BIOME_AUTUMN = registerForHolder("music.overworld.autumn");

    private static DeferredHolder<SoundEvent, SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, SoundEvent::createVariableRangeEvent);
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
