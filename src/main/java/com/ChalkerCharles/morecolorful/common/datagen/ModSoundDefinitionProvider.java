package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModSoundDefinitionHelper;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModSoundDefinitionProvider extends ModSoundDefinitionHelper {
    protected ModSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, MoreColorful.MODID, helper);
    }

    @Override
    public void registerSounds() {
        // Note Block Instruments
        noteBlock(ModSounds.NOTE_BLOCK_PIANO_LOW, "piano_low");
        noteBlock(ModSounds.NOTE_BLOCK_PIANO_HIGH, "piano_high");
        noteBlock(ModSounds.NOTE_BLOCK_VIOLIN, "violin");
        noteBlock(ModSounds.NOTE_BLOCK_CELLO, "cello");
        noteBlock(ModSounds.NOTE_BLOCK_ELECTRIC_GUITAR, "electric_guitar");
        noteBlock(ModSounds.NOTE_BLOCK_TRUMPET, "trumpet");
        noteBlock(ModSounds.NOTE_BLOCK_SAXOPHONE, "saxophone");
        noteBlock(ModSounds.NOTE_BLOCK_OCARINA, "ocarina");
        noteBlock(ModSounds.NOTE_BLOCK_HARMONICA, "harmonica");
        noteBlock(ModSounds.NOTE_BLOCK_TOM, "tom");
        noteBlock(ModSounds.NOTE_BLOCK_RIDE, "ride");
        noteBlock(ModSounds.NOTE_BLOCK_CRASH, "crash");
        noteBlock(ModSounds.NOTE_BLOCK_SCULK, "vocal_chop");
        noteBlock(ModSounds.NOTE_BLOCK_CRYSTAL, "crystal");
        noteBlock(ModSounds.NOTE_BLOCK_SAW, "saw_wave");
        noteBlock(ModSounds.NOTE_BLOCK_PLUCK, "pluck");
        noteBlock(ModSounds.NOTE_BLOCK_SYNTH_BASS, "synth_bass");
        noteBlock(ModSounds.NOTE_BLOCK_PIPA, "pipa");
        noteBlock(ModSounds.NOTE_BLOCK_ERHU, "erhu");
        noteBlock(ModSounds.NOTE_BLOCK_GUZHENG, "zheng");

        // Instruments
        instrument(ModSounds.HARP_PLAY, SoundEvents.NOTE_BLOCK_HARP, "harp", "block");
        instrument(ModSounds.BASS_DRUM_PLAY, SoundEvents.NOTE_BLOCK_BASEDRUM, "bass_drum", "block");
        instrument(ModSounds.SNARE_PLAY, SoundEvents.NOTE_BLOCK_SNARE, "snare_drum", "block");
        instrument(ModSounds.HAT_PLAY, SoundEvents.NOTE_BLOCK_HAT, "hi-hat", "block");
        instrument(ModSounds.GLOCKENSPIEL_PLAY, SoundEvents.NOTE_BLOCK_BELL, "glockenspiel", "block");
        instrument(ModSounds.CHIMES_PLAY, SoundEvents.NOTE_BLOCK_CHIME, "chimes", "block");
        instrument(ModSounds.XYLOPHONE_PLAY, SoundEvents.NOTE_BLOCK_XYLOPHONE, "xylophone", "block");
        instrument(ModSounds.VIBRAPHONE_PLAY, SoundEvents.NOTE_BLOCK_IRON_XYLOPHONE, "vibraphone", "block");
        instrument(ModSounds.BIT_PLAY, SoundEvents.NOTE_BLOCK_BIT, "synthesizer_keyboard", "block");
        instrument(ModSounds.PLING_PLAY, SoundEvents.NOTE_BLOCK_PLING, "synthesizer_keyboard", "block");
        instrument(ModSounds.PIANO_LOW_PLAY, "piano_low", "piano", "block");
        instrument(ModSounds.PIANO_HIGH_PLAY, "piano_high", "piano", "block");
        instrument(ModSounds.TOM_PLAY, "tom", "tom-tom_drum", "block");
        instrument(ModSounds.RIDE_PLAY, "ride", "ride_cymbal", "block");
        instrument(ModSounds.CRASH_PLAY, "crash", "crash_cymbal", "block");
        instrument(ModSounds.SCULK_PLAY, "vocal_chop", "synthesizer_keyboard", "block");
        instrument(ModSounds.CRYSTAL_PLAY, "crystal", "synthesizer_keyboard", "block");
        instrument(ModSounds.SAW_PLAY, "saw_wave", "synthesizer_keyboard", "block");
        instrument(ModSounds.PLUCK_PLAY, "pluck", "synthesizer_keyboard", "block");
        instrument(ModSounds.SYNTH_BASS_PLAY, "synth_bass", "synthesizer_keyboard", "block");
        instrument(ModSounds.GUZHENG_PLAY, "zheng", "guzheng", "block");

        instrument(ModSounds.BASS_PLAY, SoundEvents.NOTE_BLOCK_BASS, "bass", "item");
        instrument(ModSounds.FLUTE_PLAY, SoundEvents.NOTE_BLOCK_FLUTE, "flute", "item");
        instrument(ModSounds.GUITAR_PLAY, SoundEvents.NOTE_BLOCK_GUITAR, "guitar", "item");
        instrument(ModSounds.COW_BELL_PLAY, SoundEvents.NOTE_BLOCK_COW_BELL, "cow_bell", "item");
        instrument(ModSounds.DIDGERIDOO_PLAY, SoundEvents.NOTE_BLOCK_DIDGERIDOO, "didgeridoo", "item");
        instrument(ModSounds.BANJO_PLAY, SoundEvents.NOTE_BLOCK_BANJO, "banjo", "item");
        instrument(ModSounds.VIOLIN_PLAY, "violin", "violin", "item");
        instrument(ModSounds.CELLO_PLAY, "cello", "cello", "item");
        instrument(ModSounds.ELECTRIC_GUITAR_PLAY, "electric_guitar", "electric_guitar", "item");
        instrument(ModSounds.TRUMPET_PLAY, "trumpet", "trumpet", "item");
        instrument(ModSounds.SAXOPHONE_PLAY, "saxophone", "saxophone", "item");
        instrument(ModSounds.OCARINA_PLAY, "ocarina", "ocarina", "item");
        instrument(ModSounds.HARMONICA_PLAY, "harmonica", "harmonica", "item");
        instrument(ModSounds.PIPA_PLAY, "pipa", "pipa", "item");
        instrument(ModSounds.ERHU_PLAY, "erhu", "erhu", "item");

        // Common Block Sounds
        generic(ModSounds.RARE_WOOD_BREAK, "subtitles.block.generic.break",
                "rare_wood/break1",
                "rare_wood/break2",
                "rare_wood/break3",
                "rare_wood/break4",
                "rare_wood/break5");
        generic(ModSounds.RARE_WOOD_STEP, "subtitles.block.generic.footsteps",
                "rare_wood/step1",
                "rare_wood/step2",
                "rare_wood/step3",
                "rare_wood/step4",
                "rare_wood/step5",
                "rare_wood/step6");
        generic(ModSounds.RARE_WOOD_PLACE, "subtitles.block.generic.place",
                "rare_wood/break1",
                "rare_wood/break2",
                "rare_wood/break3",
                "rare_wood/break4",
                "rare_wood/break5");
        generic(ModSounds.RARE_WOOD_HIT, "subtitles.block.generic.hit",
                "rare_wood/step1",
                "rare_wood/step2",
                "rare_wood/step3",
                "rare_wood/step4",
                "rare_wood/step5",
                "rare_wood/step6");
        generic(ModSounds.RARE_WOOD_FALL, null,
                "rare_wood/step1",
                "rare_wood/step2",
                "rare_wood/step3",
                "rare_wood/step4",
                "rare_wood/step5",
                "rare_wood/step6");
        generic(ModSounds.RARE_WOOD_DOOR_CLOSE, "subtitles.block.door.toggle",
                "rare_wood/door/toggle1",
                "rare_wood/door/toggle2",
                "rare_wood/door/toggle3",
                "rare_wood/door/toggle4");
        generic(ModSounds.RARE_WOOD_DOOR_OPEN, "subtitles.block.door.toggle",
                "rare_wood/door/toggle1",
                "rare_wood/door/toggle2",
                "rare_wood/door/toggle3",
                "rare_wood/door/toggle4");
        generic(ModSounds.RARE_WOOD_TRAPDOOR_CLOSE, "subtitles.block.trapdoor.toggle",
                "rare_wood/trapdoor/toggle1",
                "rare_wood/trapdoor/toggle2",
                "rare_wood/trapdoor/toggle3");
        generic(ModSounds.RARE_WOOD_TRAPDOOR_OPEN, "subtitles.block.trapdoor.toggle",
                "rare_wood/trapdoor/toggle1",
                "rare_wood/trapdoor/toggle2",
                "rare_wood/trapdoor/toggle3");
        generic(ModSounds.RARE_WOOD_FENCE_GATE_CLOSE, "subtitles.block.fence_gate.toggle",
                "rare_wood/fence_gate/toggle1",
                "rare_wood/fence_gate/toggle2",
                "rare_wood/fence_gate/toggle3");
        generic(ModSounds.RARE_WOOD_FENCE_GATE_OPEN, "subtitles.block.fence_gate.toggle",
                "rare_wood/fence_gate/toggle1",
                "rare_wood/fence_gate/toggle2",
                "rare_wood/fence_gate/toggle3");
        click(ModSounds.RARE_WOOD_PRESSURE_PLATE_CLICK_OFF, "random/click", 0.6, 0.3, "subtitles.block.pressure_plate.click");
        click(ModSounds.RARE_WOOD_PRESSURE_PLATE_CLICK_ON, "random/click", 0.7, 0.3, "subtitles.block.pressure_plate.click");
        click(ModSounds.RARE_WOOD_BUTTON_CLICK_OFF, "morecolorful:block/rare_wood/button_click", 0.5, 0.4, "subtitles.block.button.click");
        click(ModSounds.RARE_WOOD_BUTTON_CLICK_ON, "morecolorful:block/rare_wood/button_click", 0.6, 0.4, "subtitles.block.button.click");
        generic(ModSounds.RARE_WOOD_HANGING_SIGN_BREAK, "subtitles.block.generic.break",
                "rare_wood/hanging_sign/break1",
                "rare_wood/hanging_sign/break2",
                "rare_wood/hanging_sign/break3",
                "rare_wood/hanging_sign/break4");
        generic(ModSounds.RARE_WOOD_HANGING_SIGN_STEP, "subtitles.block.generic.footsteps",
                "rare_wood/hanging_sign/step1",
                "rare_wood/hanging_sign/step2",
                "rare_wood/hanging_sign/step3",
                "rare_wood/hanging_sign/step4");
        generic(ModSounds.RARE_WOOD_HANGING_SIGN_PLACE, "subtitles.block.generic.place",
                "rare_wood/hanging_sign/break1",
                "rare_wood/hanging_sign/break2",
                "rare_wood/hanging_sign/break3",
                "rare_wood/hanging_sign/break4");
        generic(ModSounds.RARE_WOOD_HANGING_SIGN_HIT, "subtitles.block.generic.hit",
                "rare_wood/hanging_sign/step1",
                "rare_wood/hanging_sign/step2",
                "rare_wood/hanging_sign/step3",
                "rare_wood/hanging_sign/step4");
        generic(ModSounds.RARE_WOOD_HANGING_SIGN_FALL, null,
                "rare_wood/hanging_sign/step1",
                "rare_wood/hanging_sign/step2",
                "rare_wood/hanging_sign/step3",
                "rare_wood/hanging_sign/step4");
    }
}
