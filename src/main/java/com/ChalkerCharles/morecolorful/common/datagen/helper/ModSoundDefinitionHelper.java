package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class ModSoundDefinitionHelper extends SoundDefinitionsProvider {
    private static final String modid = MoreColorful.MODID;
    protected ModSoundDefinitionHelper(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }

    protected void noteBlock(Holder<SoundEvent> soundEvent, String soundFile) {
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(modid + ":note/" + soundFile))
                .subtitle("subtitles.block.note_block.note"));
    }

    protected void instrument(Holder<SoundEvent> soundEvent, String soundFile, String instrument, String type) { // "type" can only be "block" or "item"
        add(soundEvent.value(), SoundDefinition.definition()
                .with(sound(modid + ":note/" + soundFile))
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
            definition.with(sound(ResourceLocation.fromNamespaceAndPath(modid, "block/" + i)));
        }
        add(soundEvent, definition.subtitle(subtitle));
    }

    protected void click(Supplier<SoundEvent> soundEvent, String soundFile, double pitch, double volume, String subtitle) {
        add(soundEvent, SoundDefinition.definition()
                .with(sound(soundFile).pitch(pitch).volume(volume))
                .subtitle(subtitle));
    }
}
