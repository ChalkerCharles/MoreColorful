package com.ChalkerCharles.morecolorful.common.datagen.helper;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

import static com.ChalkerCharles.morecolorful.MoreColorful.MODID;

public abstract class ModSoundDefinitionHelper extends SoundDefinitionsProvider {
    protected ModSoundDefinitionHelper(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    protected String mcMusic(String name) {
        return "minecraft:music/" + name;
    }
    protected String modMusic(String name) {
        return MODID + ":music/" + name;
    }

    protected static SoundDefinition.Sound music(final String name, float volume, int weight) {
        return music(name, volume).weight(weight);
    }
    protected static SoundDefinition.Sound music(final String name, float volume) {
        return sound(name).stream().volume(volume);
    }

    protected void noteBlock(Holder<SoundEvent> soundEvent, String soundFile) {
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(MODID + ":note/" + soundFile))
                .subtitle("subtitles.block.note_block.note"));
    }

    protected void instrument(Holder<SoundEvent> soundEvent, String soundFile, String instrument, String type) { // "type" can only be "block" or "item"
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(MODID + ":note/" + soundFile))
                .subtitle("morecolorful.subtitles." + type + "." + instrument + ".play"));
    }

    protected void instrument(Holder<SoundEvent> soundEvent, Holder<SoundEvent> soundEventVanilla, String instrument, String type) { // "type" can only be "block" or "item"
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(soundEventVanilla.value().getLocation(), SoundDefinition.SoundType.EVENT))
                .subtitle("morecolorful.subtitles." + type + "." + instrument + ".play"));
    }

    protected void generic(Supplier<SoundEvent> soundEvent, @Nullable String subtitle, String... soundFiles) {
        SoundDefinition definition = SoundDefinition.definition();
        for (String i : soundFiles) {
            definition.with(sound(ResourceLocation.fromNamespaceAndPath(MODID, "block/" + i)));
        }
        add(soundEvent, definition.subtitle(subtitle));
    }

    protected void click(Supplier<SoundEvent> soundEvent, String soundFile, double pitch, double volume, String subtitle) {
        add(soundEvent, SoundDefinition.definition()
                .with(sound(soundFile).pitch(pitch).volume(volume))
                .subtitle(subtitle));
    }
}
