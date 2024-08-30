package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class OctaveButton extends Button {
    private static final ResourceLocation TREBLE_CLEF = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "widget/treble_clef");
    private static final ResourceLocation BASS_CLEF = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "widget/bass_clef");
    private InstrumentsType pType;
    public OctaveButton(int pX, int pY, InstrumentsType pType, Button.OnPress pOnPress) {
        super(pX, pY, 12, 17, CommonComponents.EMPTY, pOnPress, DEFAULT_NARRATION);
        this.pType = pType;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        ResourceLocation resourcelocation = pType == InstrumentsType.PIANO_HIGH ? TREBLE_CLEF : BASS_CLEF;
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 12, 17);
    }

    public void toggleOctave(PlayingScreen pScreen) {
        if (pScreen.pType == InstrumentsType.PIANO_LOW) {
            pScreen.pType = InstrumentsType.PIANO_HIGH;
        } else if (pScreen.pType == InstrumentsType.PIANO_HIGH) {
            pScreen.pType = InstrumentsType.PIANO_LOW;
        }
        this.pType = pScreen.pType;
    }
}
