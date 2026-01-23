package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.properties.InstrumentExtension;
import com.google.common.collect.ImmutableMap;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;

import java.util.Locale;
import java.util.Map;
import java.util.function.IntFunction;

public enum InstrumentsType implements StringRepresentable {
    // ----------------------------------------Blocks---------------------------------------- //
    HARP(ModSounds.HARP_PLAY, Type.OTHER),
    PIANO_LOW(ModSounds.PIANO_LOW_PLAY, Type.KEYBOARD),
    PIANO_HIGH(ModSounds.PIANO_HIGH_PLAY, Type.KEYBOARD),
    GUZHENG(ModSounds.GUZHENG_PLAY, Type.OTHER),
    BASS_DRUM(ModSounds.BASS_DRUM_PLAY, Type.PERCUSSION),
    SNARE(ModSounds.SNARE_PLAY, Type.PERCUSSION),
    TOM(ModSounds.TOM_PLAY, Type.PERCUSSION),
    RIDE(ModSounds.RIDE_PLAY, Type.PERCUSSION),
    CRASH(ModSounds.CRASH_PLAY, Type.PERCUSSION),
    HAT(ModSounds.HAT_PLAY, Type.PERCUSSION),
    GLOCKENSPIEL(ModSounds.GLOCKENSPIEL_PLAY, Type.PERCUSSION),
    CHIMES(ModSounds.CHIMES_PLAY, Type.PERCUSSION),
    XYLOPHONE(ModSounds.XYLOPHONE_PLAY, Type.PERCUSSION),
    VIBRAPHONE(ModSounds.VIBRAPHONE_PLAY, Type.PERCUSSION),
    BIT(ModSounds.BIT_PLAY, Type.KEYBOARD),
    PLING(ModSounds.PLING_PLAY, Type.KEYBOARD),
    SCULK(ModSounds.SCULK_PLAY, Type.KEYBOARD),
    CRYSTAL(ModSounds.CRYSTAL_PLAY, Type.KEYBOARD),
    SAW(ModSounds.SAW_PLAY, Type.KEYBOARD),
    PLUCK(ModSounds.PLUCK_PLAY, Type.KEYBOARD),
    SYNTH_BASS(ModSounds.SYNTH_BASS_PLAY, Type.KEYBOARD),
    // ----------------------------------------Items---------------------------------------- //
    BASS(ModSounds.BASS_PLAY, Type.ITEM),
    FLUTE(ModSounds.FLUTE_PLAY, Type.ITEM),
    GUITAR(ModSounds.GUITAR_PLAY, Type.ITEM),
    COW_BELL(ModSounds.COW_BELL_PLAY, Type.ITEM),
    DIDGERIDOO(ModSounds.DIDGERIDOO_PLAY, Type.ITEM),
    BANJO(ModSounds.BANJO_PLAY, Type.ITEM),
    VIOLIN(ModSounds.VIOLIN_PLAY, Type.ITEM),
    CELLO(ModSounds.CELLO_PLAY, Type.ITEM),
    ELECTRIC_GUITAR(ModSounds.ELECTRIC_GUITAR_PLAY, Type.ITEM),
    TRUMPET(ModSounds.TRUMPET_PLAY, Type.ITEM),
    SAXOPHONE(ModSounds.SAXOPHONE_PLAY, Type.ITEM),
    OCARINA(ModSounds.OCARINA_PLAY, Type.ITEM),
    HARMONICA(ModSounds.HARMONICA_PLAY, Type.ITEM),
    PIPA(ModSounds.PIPA_PLAY, Type.ITEM),
    ERHU(ModSounds.ERHU_PLAY, Type.ITEM);

    private final Holder<SoundEvent> soundEvent;
    private final Type type;
    private static final IntFunction<InstrumentsType> BY_ID = ByIdMap.continuous(InstrumentsType::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, InstrumentsType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, InstrumentsType::ordinal);
    public static final Map<NoteBlockInstrument, InstrumentsType> MAPPER = new ImmutableMap.Builder<NoteBlockInstrument, InstrumentsType>()
            .put(NoteBlockInstrument.HARP, HARP)
            .put(NoteBlockInstrument.BASEDRUM, BASS_DRUM)
            .put(NoteBlockInstrument.SNARE, SNARE)
            .put(NoteBlockInstrument.HAT, HAT)
            .put(NoteBlockInstrument.BASS, BASS)
            .put(NoteBlockInstrument.FLUTE, FLUTE)
            .put(NoteBlockInstrument.BELL, GLOCKENSPIEL)
            .put(NoteBlockInstrument.GUITAR, GUITAR)
            .put(NoteBlockInstrument.CHIME, CHIMES)
            .put(NoteBlockInstrument.XYLOPHONE, XYLOPHONE)
            .put(NoteBlockInstrument.IRON_XYLOPHONE, VIBRAPHONE)
            .put(NoteBlockInstrument.COW_BELL, COW_BELL)
            .put(NoteBlockInstrument.DIDGERIDOO, DIDGERIDOO)
            .put(NoteBlockInstrument.BIT, BIT)
            .put(NoteBlockInstrument.BANJO, BANJO)
            .put(NoteBlockInstrument.PLING, PLING)
            .put(InstrumentExtension.PIANO_LOW, PIANO_LOW)
            .put(InstrumentExtension.PIANO_HIGH, PIANO_HIGH)
            .put(InstrumentExtension.VIOLIN, VIOLIN)
            .put(InstrumentExtension.CELLO, CELLO)
            .put(InstrumentExtension.ELECTRIC_GUITAR, ELECTRIC_GUITAR)
            .put(InstrumentExtension.TRUMPET, TRUMPET)
            .put(InstrumentExtension.SAXOPHONE, SAXOPHONE)
            .put(InstrumentExtension.OCARINA, OCARINA)
            .put(InstrumentExtension.HARMONICA, HARMONICA)
            .put(InstrumentExtension.TOM, TOM)
            .put(InstrumentExtension.RIDE, RIDE)
            .put(InstrumentExtension.CRASH, CRASH)
            .put(InstrumentExtension.SCULK, SCULK)
            .put(InstrumentExtension.CRYSTAL, CRYSTAL)
            .put(InstrumentExtension.SAW, SAW)
            .put(InstrumentExtension.PLUCK, PLUCK)
            .put(InstrumentExtension.SYNTH_BASS, SYNTH_BASS)
            .put(InstrumentExtension.PIPA, PIPA)
            .put(InstrumentExtension.ERHU, ERHU)
            .put(InstrumentExtension.GUZHENG, GUZHENG)
            .build();

    InstrumentsType(Holder<SoundEvent> pSoundEvent, Type type) {
        this.soundEvent = pSoundEvent;
        this.type = type;
    }

    public SoundEvent getSoundEvent() {
        return this.soundEvent.value();
    }

    public boolean isBlock() {
        return this.type != Type.ITEM;
    }

    public boolean isKeyBoard() {
        return this.type == Type.KEYBOARD;
    }

    public boolean isPercussion() {
        return this.type == Type.PERCUSSION;
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }

    public enum Type {
        ITEM,
        KEYBOARD,
        PERCUSSION,
        OTHER
    }
}
