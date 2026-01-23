package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class ModSoundDefinitionHelper extends SoundDefinitionsProvider {
    protected ModSoundDefinitionHelper(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    protected static String mcMusic(String name) {
        return "minecraft:music/" + name;
    }
    protected static String modMusic(String name) {
        return MoreColorful.MODID + ":music/" + name;
    }

    protected static SoundDefinition.Sound music(final String name, float volume, int weight) {
        return music(name, volume).weight(weight);
    }
    protected static SoundDefinition.Sound music(final String name, float volume) {
        return sound(name).stream().volume(volume);
    }

    protected void noteBlock(Holder<SoundEvent> soundEvent, String soundFile) {
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(MoreColorful.MODID + ":note/" + soundFile))
                .subtitle("subtitles.block.note_block.note"));
    }

    protected void instrument(Holder<SoundEvent> soundEvent, String soundFile, String instrument, String type) { // "type" can only be "block" or "item"
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(MoreColorful.MODID + ":note/" + soundFile))
                .subtitle("morecolorful.subtitles." + type + "." + instrument + ".play"));
    }

    protected void instrument(Holder<SoundEvent> soundEvent, Holder<SoundEvent> soundEventVanilla, String instrument, String type) { // "type" can only be "block" or "item"
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(soundEventVanilla.value().getLocation(), SoundDefinition.SoundType.EVENT))
                .subtitle("morecolorful.subtitles." + type + "." + instrument + ".play"));
    }

    protected void musicBox(Holder<SoundEvent> soundEvent, Holder<SoundEvent> source) {
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(source.value().getLocation(), SoundDefinition.SoundType.EVENT))
                .subtitle("morecolorful.subtitles.block.music_box.play"));
    }

    protected void generic(Supplier<SoundEvent> soundEvent, @Nullable String subtitle, String... soundFiles) {
        SoundDefinition definition = SoundDefinition.definition();
        for (String i : soundFiles) {
            definition.with(sound(MoreColorful.location(i)));
        }
        add(soundEvent, definition.subtitle(subtitle));
    }

    protected void vanilla(Supplier<SoundEvent> soundEvent, @Nullable String subtitle, SoundEvent source) {
        add(soundEvent, SoundDefinition.definition().with(sound(source.getLocation(), SoundDefinition.SoundType.EVENT)).subtitle(subtitle));
    }

    protected void click(Supplier<SoundEvent> soundEvent, String soundFile, double pitch, double volume, String subtitle) {
        add(soundEvent, SoundDefinition.definition()
                .with(sound(soundFile).pitch(pitch).volume(volume))
                .subtitle(subtitle));
    }
}
