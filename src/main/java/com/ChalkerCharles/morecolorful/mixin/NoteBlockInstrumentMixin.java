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
        $VALUES = Arrays.copyOf($VALUES, ordinal + 5);

        PIANO = $VALUES[ordinal] = moreColorful$create("MORECOLORFUL_PIANO", ordinal, "piano", ModSounds.NOTE_BLOCK_PIANO);
        VIOLIN = $VALUES[ordinal + 1] = moreColorful$create("MORECOLORFUL_VIOLIN", ordinal + 1, "violin", ModSounds.NOTE_BLOCK_VIOLIN);
        CELLO = $VALUES[ordinal + 2] = moreColorful$create("MORECOLORFUL_CELLO", ordinal + 2, "cello", ModSounds.NOTE_BLOCK_CELLO);
        ELECTRIC_GUITAR = $VALUES[ordinal + 3] = moreColorful$create("MORECOLORFUL_ELECTRIC_GUITAR", ordinal + 3, "electric_guitar", ModSounds.NOTE_BLOCK_ELECTRIC_GUITAR);
        TRUMPET = $VALUES[ordinal + 4] = moreColorful$create("MORECOLORFUL_TRUMPET", ordinal + 4, "trumpet", ModSounds.NOTE_BLOCK_TRUMPET);
    }
}
