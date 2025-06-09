package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(NoteBlockInstrument.class)
public abstract class NoteBlockInstrumentMixin {
    @Shadow
    @Final
    @Mutable
    private static NoteBlockInstrument[] $VALUES;

    @SuppressWarnings("SameParameterValue")
    @Invoker("<init>")
    private static NoteBlockInstrument create(String enumName, int ordinal, String name, Holder<SoundEvent> pSoundEvent, NoteBlockInstrument.Type pType) {
        throw new UnsupportedOperationException();
    }

    @Unique
    private static NoteBlockInstrument moreColorful$create(String enumName, int ordinal, String name, Holder<SoundEvent> pSoundEvent) {
        return create(enumName, ordinal, name, pSoundEvent, NoteBlockInstrument.Type.BASE_BLOCK);
    }

    static {
        ArrayList<NoteBlockInstrument> instruments = new ArrayList<>(Arrays.asList($VALUES));
        int ordinal = instruments.size();

        instruments.add(moreColorful$create("MORECOLORFUL_PIANO_LOW", ordinal, "piano_low", ModSounds.NOTE_BLOCK_PIANO_LOW));
        instruments.add(moreColorful$create("MORECOLORFUL_PIANO_HIGH", ordinal + 1, "piano_high", ModSounds.NOTE_BLOCK_PIANO_HIGH));
        instruments.add(moreColorful$create("MORECOLORFUL_VIOLIN", ordinal + 2, "violin", ModSounds.NOTE_BLOCK_VIOLIN));
        instruments.add(moreColorful$create("MORECOLORFUL_CELLO", ordinal + 3, "cello", ModSounds.NOTE_BLOCK_CELLO));
        instruments.add(moreColorful$create("MORECOLORFUL_ELECTRIC_GUITAR", ordinal + 4, "electric_guitar", ModSounds.NOTE_BLOCK_ELECTRIC_GUITAR));
        instruments.add(moreColorful$create("MORECOLORFUL_TRUMPET", ordinal + 5, "trumpet", ModSounds.NOTE_BLOCK_TRUMPET));
        instruments.add(moreColorful$create("MORECOLORFUL_SAXOPHONE", ordinal + 6, "saxophone", ModSounds.NOTE_BLOCK_SAXOPHONE));
        instruments.add(moreColorful$create("MORECOLORFUL_OCARINA", ordinal + 7, "ocarina", ModSounds.NOTE_BLOCK_OCARINA));
        instruments.add(moreColorful$create("MORECOLORFUL_HARMONICA", ordinal + 8, "harmonica", ModSounds.NOTE_BLOCK_HARMONICA));
        instruments.add(moreColorful$create("MORECOLORFUL_TOM", ordinal + 9, "tom", ModSounds.NOTE_BLOCK_TOM));
        instruments.add(moreColorful$create("MORECOLORFUL_RIDE", ordinal + 10, "ride", ModSounds.NOTE_BLOCK_RIDE));
        instruments.add(moreColorful$create("MORECOLORFUL_CRASH", ordinal + 11, "crash", ModSounds.NOTE_BLOCK_CRASH));
        instruments.add(moreColorful$create("MORECOLORFUL_SCULK", ordinal + 12, "vocal_chop", ModSounds.NOTE_BLOCK_SCULK));
        instruments.add(moreColorful$create("MORECOLORFUL_CRYSTAL", ordinal + 13, "crystal", ModSounds.NOTE_BLOCK_CRYSTAL));
        instruments.add(moreColorful$create("MORECOLORFUL_SAW", ordinal + 14, "saw_wave", ModSounds.NOTE_BLOCK_SAW));
        instruments.add(moreColorful$create("MORECOLORFUL_PLUCK", ordinal + 15, "pluck", ModSounds.NOTE_BLOCK_PLUCK));
        instruments.add(moreColorful$create("MORECOLORFUL_SYNTH_BASS", ordinal + 16, "synth_bass", ModSounds.NOTE_BLOCK_SYNTH_BASS));
        instruments.add(moreColorful$create("MORECOLORFUL_PIPA", ordinal + 17, "pipa", ModSounds.NOTE_BLOCK_PIPA));
        instruments.add(moreColorful$create("MORECOLORFUL_ERHU", ordinal + 18, "erhu", ModSounds.NOTE_BLOCK_ERHU));
        instruments.add(moreColorful$create("MORECOLORFUL_GUZHENG", ordinal + 19, "guzheng", ModSounds.NOTE_BLOCK_GUZHENG));

        $VALUES = instruments.toArray(NoteBlockInstrument[]::new);
    }
}
