package com.ChalkerCharles.morecolorful.util.melody;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;

public class Melodies {
    public static final Melody EXAMPLE = Melody.of(InstrumentsType.HARP,
            1, 2, 3);
    public static final Melody EXAMPLE_1 = Melody.of(InstrumentsType.HARP,
            1, 3, 5);
    public static final Melody EXAMPLE_2 = Melody.of(null,
            6, 6, 13, 13, 15, 15, 13);
    public static final Melody EXAMPLE_3 = Melody.of(InstrumentsType.HARP,
            Note.of(1), Note.Chord.of(1, 3, 5), Note.of(5));
}
