package com.ChalkerCharles.morecolorful.util.melody;

public sealed interface Note permits Note.SingleNote, Note.Chord {
    static SingleNote of(int keyId) {
        return new SingleNote(keyId);
    }

    default boolean isChord() {
        return this instanceof Chord;
    }

    default int keyId() {
        if (isChord()) {
            throw new IllegalCallerException("The note is not a single note!");
        }
        return ((SingleNote) this).keyId;
    }

    record SingleNote(int keyId) implements Note {}

    record Chord(Integer... keyIds) implements Note {
        static Chord of(Integer... keyIds) {
            return new Chord(keyIds);
        }
    }
}
