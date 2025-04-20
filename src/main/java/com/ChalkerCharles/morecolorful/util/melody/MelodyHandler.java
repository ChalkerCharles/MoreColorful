package com.ChalkerCharles.morecolorful.util.melody;

import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.NoteBlockEvent;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public class MelodyHandler {
    private final NoteBlockHandler[] noteBlockHandlers = new NoteBlockHandler[]{
            new NoteBlockHandler(Melodies.EXAMPLE),
            new NoteBlockHandler(Melodies.EXAMPLE_1),
            new NoteBlockHandler(Melodies.EXAMPLE_2),
            new NoteBlockHandler(Melodies.EXAMPLE_3)
    };

    @SubscribeEvent
    public void onNoteBlockPlaying(NoteBlockEvent.Play event) {
        if (!event.getLevel().isClientSide()) {
            for (NoteBlockHandler handler : noteBlockHandlers) {
                handler.handleMelody(event);
            }
        }
    }

    public static class NoteBlockHandler {
        private int ordinal = 0;
        private int chordOrdinal = 0;
        private Set<Integer> noteSet;
        private final Melody melody;
        private LocalDateTime lastNote = null;
        private LocalDateTime lastChordNote = null;

        private NoteBlockHandler(Melody melody) {
            this.melody = melody;
        }

        private void handleMelody(NoteBlockEvent.Play event) {
            NoteBlockInstrument instrument = event.getInstrument();
            int noteId = event.getVanillaNoteId();
            if (melody.matchedType(instrument)) {
                Note note = melody.notes()[ordinal];
                if (note.isChord()) {
                    handleChord(note, noteId);
                } else {
                    if (note.keyId() == noteId) {
                        ordinal++;
                    } else {
                        restore();
                    }
                }

                if (ordinal == melody.notes().length) {
                    restore();
                    System.out.println("yes");
                }
            }
            LocalDateTime currentNote = LocalDateTime.now();
            if (lastNote != null) {
                Duration duration = Duration.between(lastNote, currentNote);
                long seconds = duration.toSeconds();
                if (seconds > 8) {
                    restore();
                }
            }
            lastNote = currentNote;
        }

        private void handleChord(Note note, int noteId) {
            Note.Chord chord = (Note.Chord) note;
            if (chordOrdinal == 0) {
                noteSet = new HashSet<>(Set.of(chord.keyIds()));
            }
            if (noteSet.contains(noteId)) {
                noteSet.remove(noteId);
                chordOrdinal++;
            } else {
                restore();
            }
            if (noteSet.isEmpty()) {
                ordinal++;
                chordOrdinal = 0;
                lastChordNote = null;
            }
            LocalDateTime currentChordNote = LocalDateTime.now();
            if (lastChordNote != null) {
                Duration duration = Duration.between(lastChordNote, currentChordNote);
                long milliseconds = duration.toMillis();
                if (milliseconds > 200) {
                    restore();
                }
            }
            lastChordNote = currentChordNote;
        }

        private void restore() {
            this.ordinal = 0;
            this.chordOrdinal = 0;
            this.lastNote = null;
            this.lastChordNote = null;
        }
    }
}
