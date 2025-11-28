package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OctaveButton extends Button {
    private static final ResourceLocation TREBLE_CLEF = MoreColorful.location("widget/treble_clef");
    private static final ResourceLocation BASS_CLEF = MoreColorful.location("widget/bass_clef");
    private InstrumentsType type;

    public OctaveButton(int pX, int pY, InstrumentsType type, Button.OnPress onPress) {
        super(pX, pY, 12, 17, CommonComponents.EMPTY, onPress, DEFAULT_NARRATION);
        this.type = type;
    }

    @Override
    public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        ResourceLocation resourcelocation = type == InstrumentsType.PIANO_HIGH ? TREBLE_CLEF : BASS_CLEF;
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 12, 17);
    }

    public void toggleOctave(PlayingScreen screen) {
        if (screen.type == InstrumentsType.PIANO_LOW) {
            screen.type = InstrumentsType.PIANO_HIGH;
        } else if (screen.type == InstrumentsType.PIANO_HIGH) {
            screen.type = InstrumentsType.PIANO_LOW;
        }
        this.type = screen.type;
    }
}
