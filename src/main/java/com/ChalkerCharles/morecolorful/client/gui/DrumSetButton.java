package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DrumSetButton extends Button {
    private static final ResourceLocation BUTTON_BASS_DRUM = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_bass_drum");
    private static final ResourceLocation BUTTON_BASS_DRUM_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_bass_drum_pressed");
    private static final ResourceLocation BUTTON_SNARE = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_snare");
    private static final ResourceLocation BUTTON_SNARE_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_snare_pressed");
    private static final ResourceLocation BUTTON_TOM = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_tom");
    private static final ResourceLocation BUTTON_TOM_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_tom_pressed");
    private static final ResourceLocation BUTTON_HAT = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_hat");
    private static final ResourceLocation BUTTON_HAT_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_hat_pressed");
    private static final ResourceLocation BUTTON_RIDE = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_ride");
    private static final ResourceLocation BUTTON_RIDE_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_ride_pressed");
    private static final ResourceLocation BUTTON_CRASH = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_crash");
    private static final ResourceLocation BUTTON_CRASH_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "drum_set/button_crash_pressed");
    private final InstrumentsType pType;
    private final int keyId;
    public boolean pressedByClick;
    public boolean isPressed;
    public DrumSetButton(int pX, int pY, InstrumentsType pType, int keyId) {
        super(pX, pY, 20, 20, CommonComponents.EMPTY, Button -> {}, DEFAULT_NARRATION);
        this.pType = pType;
        this.keyId = keyId;
    }
    @Override
    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        ResourceLocation resourcelocation = switch (this.pType) {
            case SNARE -> isPressed ? BUTTON_SNARE_PRESSED : BUTTON_SNARE;
            case TOM -> isPressed ? BUTTON_TOM_PRESSED : BUTTON_TOM;
            case HAT -> isPressed ? BUTTON_HAT_PRESSED : BUTTON_HAT;
            case RIDE -> isPressed ? BUTTON_RIDE_PRESSED : BUTTON_RIDE;
            case CRASH -> isPressed ? BUTTON_CRASH_PRESSED : BUTTON_CRASH;
            default -> isPressed ? BUTTON_BASS_DRUM_PRESSED : BUTTON_BASS_DRUM;
        };
        pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 20, 20);
    }

    public void playSound(Player pPlayer, BlockPos pPos){
        int pitchId = keyId - 12;
        Level pLevel = pPlayer.level();
        pLevel.playSound(pPlayer, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, pType.getSoundEvent().value(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2,((double) pitchId / 12)));
        PacketDistributor.sendToServer(new NotePlayingPacket(pType, pPos, keyId, true));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.isHovered()) this.press(true);
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    public void press(boolean byClick) {
        if (this.active && !this.isPressed && Minecraft.getInstance().screen instanceof DrumSetScreen pScreen) {
            this.playSound(pScreen.pPlayer, pScreen.pPos);
            this.pressedByClick = byClick;
            this.isPressed = true;
            this.active = false;
            pScreen.isPressing = true;
        }
    }
    public void restore() {
        if (!this.active && this.isPressed) {
            this.isPressed = false;
            this.active = true;
            this.pressedByClick = false;
        }
    }

    @Override
    public void playDownSound(@NotNull SoundManager pHandler) {}
}
