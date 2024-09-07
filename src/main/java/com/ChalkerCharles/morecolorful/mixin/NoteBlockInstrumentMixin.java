package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;

@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {
    @Shadow
    @Mutable
    @Final
    private static NoteBlockInstrument[] $VALUES;

    @SuppressWarnings("unused")
    NoteBlockInstrumentMixin(String enumName, int ordinal, String name, Holder<SoundEvent> sound, NoteBlockInstrument.Type type) {
        throw new UnsupportedOperationException("Replaced by Mixin");
    }

    @Unique
    private static NoteBlockInstrument moreColorful$create(String enumName, int ordinal, String name, Holder<SoundEvent> pSoundEvent) {
        return (NoteBlockInstrument)(Object) new NoteBlockInstrumentMixin(
                enumName, ordinal, name, pSoundEvent, NoteBlockInstrument.Type.BASE_BLOCK
        );
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;$VALUES:[Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;", shift = At.Shift.AFTER))
    private static void moreColorful$inject(CallbackInfo ci) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 17);

        PIANO_LOW = $VALUES[ordinal] = moreColorful$create("MORECOLORFUL_PIANO_LOW", ordinal, "piano_low", ModSounds.NOTE_BLOCK_PIANO_LOW);
        PIANO_HIGH = $VALUES[ordinal + 1] = moreColorful$create("MORECOLORFUL_PIANO_HIGH", ordinal + 1, "piano_high", ModSounds.NOTE_BLOCK_PIANO_HIGH);
        VIOLIN = $VALUES[ordinal + 2] = moreColorful$create("MORECOLORFUL_VIOLIN", ordinal + 2, "violin", ModSounds.NOTE_BLOCK_VIOLIN);
        CELLO = $VALUES[ordinal + 3] = moreColorful$create("MORECOLORFUL_CELLO", ordinal + 3, "cello", ModSounds.NOTE_BLOCK_CELLO);
        ELECTRIC_GUITAR = $VALUES[ordinal + 4] = moreColorful$create("MORECOLORFUL_ELECTRIC_GUITAR", ordinal + 4, "electric_guitar", ModSounds.NOTE_BLOCK_ELECTRIC_GUITAR);
        TRUMPET = $VALUES[ordinal + 5] = moreColorful$create("MORECOLORFUL_TRUMPET", ordinal + 5, "trumpet", ModSounds.NOTE_BLOCK_TRUMPET);
        SAXOPHONE = $VALUES[ordinal + 6] = moreColorful$create("MORECOLORFUL_SAXOPHONE", ordinal + 6, "saxophone", ModSounds.NOTE_BLOCK_SAXOPHONE);
        OCARINA = $VALUES[ordinal + 7] = moreColorful$create("MORECOLORFUL_OCARINA", ordinal + 7, "ocarina", ModSounds.NOTE_BLOCK_OCARINA);
        HARMONICA = $VALUES[ordinal + 8] = moreColorful$create("MORECOLORFUL_HARMONICA", ordinal + 8, "harmonica", ModSounds.NOTE_BLOCK_HARMONICA);
        TOM = $VALUES[ordinal + 9] = moreColorful$create("MORECOLORFUL_TOM", ordinal + 9, "tom", ModSounds.NOTE_BLOCK_TOM);
        RIDE = $VALUES[ordinal + 10] = moreColorful$create("MORECOLORFUL_RIDE", ordinal + 10, "ride", ModSounds.NOTE_BLOCK_RIDE);
        CRASH = $VALUES[ordinal + 11] = moreColorful$create("MORECOLORFUL_CRASH", ordinal + 11, "crash", ModSounds.NOTE_BLOCK_CRASH);
        SCULK = $VALUES[ordinal + 12] = moreColorful$create("MORECOLORFUL_SCULK", ordinal + 12, "vocal_chop", ModSounds.NOTE_BLOCK_SCULK);
        CRYSTAL = $VALUES[ordinal + 13] = moreColorful$create("MORECOLORFUL_CRYSTAL", ordinal + 13, "crystal", ModSounds.NOTE_BLOCK_CRYSTAL);
        SAW = $VALUES[ordinal + 14] = moreColorful$create("MORECOLORFUL_SAW", ordinal + 14, "saw_wave", ModSounds.NOTE_BLOCK_SAW);
        PLUCK = $VALUES[ordinal + 15] = moreColorful$create("MORECOLORFUL_PLUCK", ordinal + 15, "pluck", ModSounds.NOTE_BLOCK_PLUCK);
        SYNTH_BASS = $VALUES[ordinal + 16] = moreColorful$create("MORECOLORFUL_SYNTH_BASS", ordinal + 16, "synth_bass", ModSounds.NOTE_BLOCK_SYNTH_BASS);
    }
}
