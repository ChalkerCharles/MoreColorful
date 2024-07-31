package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

import com.ChalkerCharles.morecolorful.client.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public enum InstrumentsType implements StringRepresentable {
    // ----------------------------------------Blocks---------------------------------------- //
    HARP(ModSounds.HARP_PLAY),
    PIANO(ModSounds.PIANO_PLAY),
    // ------------Percussion Start------------ //
    BASS_DRUM(ModSounds.BASS_DRUM_PLAY),
    SNARE(ModSounds.SNARE_PLAY),
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
    TRUMPET(ModSounds.TRUMPET_PLAY);

    private final Holder<SoundEvent> soundEvent;
    InstrumentsType(Holder<SoundEvent> pSoundEvent) {
        this.soundEvent = pSoundEvent;
    }
    public Holder<SoundEvent> getSoundEvent() {
        return this.soundEvent;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name();
    }
}
