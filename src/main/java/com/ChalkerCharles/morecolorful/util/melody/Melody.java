package com.ChalkerCharles.morecolorful.util.melody;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import org.jetbrains.annotations.Nullable;

public record Melody(@Nullable InstrumentsType type, Note... notes) {
    static Melody of(InstrumentsType type, Note... notes) {
        return new Melody(type, notes);
    }

    static Melody of(InstrumentsType type, int... keyIds) {
        Note[] notes = new Note[keyIds.length];
        for (int i = 0; i < keyIds.length; i++) {
            notes[i] = Note.of(keyIds[i]);
        }
        return of(type, notes);
    }

    boolean matchedType(InstrumentsType type) {
        if (isTypeNecessary()) {
            return this.type == type;
        }
        return true;
    }

    boolean matchedType(NoteBlockInstrument instrument) {
        return matchedType(InstrumentsType.MAPPER.get(instrument));
    }

    boolean isTypeNecessary() {
        return this.type != null;
    }
}
