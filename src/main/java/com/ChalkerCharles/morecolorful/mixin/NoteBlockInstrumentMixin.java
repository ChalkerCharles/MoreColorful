package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.client.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Locale;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;

@Mixin(NoteBlockInstrument.class)
public class NoteBlockInstrumentMixin {
    @Shadow
    @Mutable
    @Final
    private static NoteBlockInstrument[] $VALUES;

    @SuppressWarnings("unused")
    NoteBlockInstrumentMixin(String enumName, int ord, String name, Holder<SoundEvent> sound, NoteBlockInstrument.Type type) {
        throw new UnsupportedOperationException("Replaced by Mixin");
    }

    @Unique
    private static NoteBlockInstrument moreColorful$create(String enumName, int ord, Holder<SoundEvent> pSoundEvent) {
        return (NoteBlockInstrument)(Object)new NoteBlockInstrumentMixin(
                enumName, ord, enumName.toLowerCase(Locale.ROOT), pSoundEvent, NoteBlockInstrument.Type.BASE_BLOCK
        );
    }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "<clinit>", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;$VALUES:[Lnet/minecraft/world/level/block/state/properties/NoteBlockInstrument;", shift = At.Shift.AFTER))
    private static void moreColorful$inject(CallbackInfo ci) {
        int ordinal = $VALUES.length;
        $VALUES = Arrays.copyOf($VALUES, ordinal + 5);

        PIANO = $VALUES[ordinal] = moreColorful$create("PIANO", ordinal, ModSounds.NOTE_BLOCK_PIANO);
        VIOLIN = $VALUES[ordinal + 1] = moreColorful$create("VIOLIN", ordinal + 1, ModSounds.NOTE_BLOCK_VIOLIN);
        CELLO = $VALUES[ordinal + 2] = moreColorful$create("CELLO", ordinal + 2, ModSounds.NOTE_BLOCK_CELLO);
        ELECTRIC_GUITAR = $VALUES[ordinal + 3] = moreColorful$create("ELECTRIC_GUITAR", ordinal + 3, ModSounds.NOTE_BLOCK_ELECTRIC_GUITAR);
        TRUMPET = $VALUES[ordinal + 4] = moreColorful$create("TRUMPET", ordinal + 2, ModSounds.NOTE_BLOCK_TRUMPET);
    }
}
