package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.function.IntFunction;

public enum InstrumentsType implements StringRepresentable {
    // ----------------------------------------Blocks---------------------------------------- //
    HARP(ModSounds.HARP_PLAY),
    PIANO_LOW(ModSounds.PIANO_LOW_PLAY),
    PIANO_HIGH(ModSounds.PIANO_HIGH_PLAY),
    // ------------Percussion Start------------ //
    BASS_DRUM(ModSounds.BASS_DRUM_PLAY),
    SNARE(ModSounds.SNARE_PLAY),
    TOM(ModSounds.TOM_PLAY),
    RIDE(ModSounds.RIDE_PLAY),
    CRASH(ModSounds.CRASH_PLAY),
    HAT(ModSounds.HAT_PLAY),
    GLOCKENSPIEL(ModSounds.GLOCKENSPIEL_PLAY),
    CHIMES(ModSounds.CHIMES_PLAY),
    XYLOPHONE(ModSounds.XYLOPHONE_PLAY),
    VIBRAPHONE(ModSounds.VIBRAPHONE_PLAY),
    // ------------Percussion End------------ //
    BIT(ModSounds.BIT_PLAY),
    PLING(ModSounds.PLING_PLAY),
    // ----------------------------------------Items---------------------------------------- //
    BASS(ModSounds.BASS_PLAY),
    FLUTE(ModSounds.FLUTE_PLAY),
    GUITAR(ModSounds.GUITAR_PLAY),
    COW_BELL(ModSounds.COW_BELL_PLAY),
    DIDGERIDOO(ModSounds.DIDGERIDOO_PLAY),
    BANJO(ModSounds.BANJO_PLAY),
    VIOLIN(ModSounds.VIOLIN_PLAY),
    CELLO(ModSounds.CELLO_PLAY),
    ELECTRIC_GUITAR(ModSounds.ELECTRIC_GUITAR_PLAY),
    TRUMPET(ModSounds.TRUMPET_PLAY),
    SAXOPHONE(ModSounds.SAXOPHONE_PLAY),
    OCARINA(ModSounds.OCARINA_PLAY),
    HARMONICA(ModSounds.HARMONICA_PLAY);

    private final Holder<SoundEvent> soundEvent;
    private static final IntFunction<InstrumentsType> BY_ID = ByIdMap.continuous(InstrumentsType::getId, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
    public static final StreamCodec<ByteBuf, InstrumentsType> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, InstrumentsType::getId);
    InstrumentsType(Holder<SoundEvent> pSoundEvent) {
        this.soundEvent = pSoundEvent;
    }
    public Holder<SoundEvent> getSoundEvent() {
        return this.soundEvent;
    }
    public int getId() {
        return this.ordinal();
    }

    @Override
    public String getSerializedName() {
        return this.name();
    }
}
