package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
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
public class KeyButton extends Button {
    private static final ResourceLocation WHITE_KEY_CF = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_cf");
    private static final ResourceLocation WHITE_KEY_CF_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_cf_pressed");
    private static final ResourceLocation WHITE_KEY_D = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_d");
    private static final ResourceLocation WHITE_KEY_D_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_d_pressed");
    private static final ResourceLocation WHITE_KEY_EB = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_eb");
    private static final ResourceLocation WHITE_KEY_EB_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_eb_pressed");
    private static final ResourceLocation WHITE_KEY_G = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_g");
    private static final ResourceLocation WHITE_KEY_G_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_g_pressed");
    private static final ResourceLocation WHITE_KEY_A = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_a");
    private static final ResourceLocation WHITE_KEY_A_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/white_key_a_pressed");
    private static final ResourceLocation BLACK_KEY = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/black_key");
    private static final ResourceLocation BLACK_KEY_PRESSED = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "key/black_key_pressed");
    private final int keyType;
    public boolean isPressed;
    static int width = 12;
    static int height = 32;

    public KeyButton(int pX, int pY, int keyType, Button.OnPress pOnPress, boolean isPressed){
        super(pX, pY, width, height, CommonComponents.EMPTY, pOnPress, DEFAULT_NARRATION);
        this.keyType = keyType;
        this.isPressed = isPressed;
    }

    @Override
    public void renderWidget(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        ResourceLocation resourcelocation;
        if (this.keyType == 0) {
            resourcelocation = isPressed ? WHITE_KEY_CF_PRESSED : WHITE_KEY_CF;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 16, 48);
        } else if (this.keyType == 1) {
            resourcelocation = isPressed ? WHITE_KEY_D_PRESSED : WHITE_KEY_D;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 16, 48);
        }else if (this.keyType == 2) {
            resourcelocation = isPressed ? WHITE_KEY_EB_PRESSED : WHITE_KEY_EB;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 16, 48);
        }else if (this.keyType == 3) {
            resourcelocation = isPressed ? WHITE_KEY_G_PRESSED : WHITE_KEY_G;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 16, 48);
        }else if (this.keyType == 4) {
            resourcelocation = isPressed ? WHITE_KEY_A_PRESSED : WHITE_KEY_A;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 16, 48);
        }else {
            resourcelocation = isPressed ? BLACK_KEY_PRESSED : BLACK_KEY;
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 12, 32);
        }
    }
    public static void playSound(Player pPlayer, InstrumentsType pType, BlockPos pPos, int keyId){
        int pitchId = keyId - 12;
        Level pLevel = pPlayer.level();
        if (pType.ordinal() <= 14) { // From Instrument Blocks
            pLevel.playSound(pPlayer, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, pType.getSoundEvent().value(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2,((double) pitchId / 12)));
            PacketDistributor.sendToServer(new NotePlayingPacket(pType, pPos, keyId, true));
        } else { // From Instrument Items
            pLevel.playSound(pPlayer, pPlayer, pType.getSoundEvent().value(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, ((double) pitchId / 12)));
            PacketDistributor.sendToServer(new NotePlayingPacket(pType, pPlayer.blockPosition(), keyId, false));
        }
    }
    @Override
    public void onPress() {
        if (this.active && Minecraft.getInstance().screen instanceof PlayingScreen pScreen) {
            this.onPress.onPress(this);
            this.isPressed = true;
            this.active = false;
            pScreen.isPressing = true;
            PacketDistributor.sendToServer(new InstrumentPressingPacket(pScreen.pPlayer.getId(), true));
        }
    }
    public void restore() {
        if (!this.active) {
            this.isPressed = false;
            this.active = true;
        }
    }
    @Override
    public void playDownSound(@NotNull SoundManager pHandler) {
    }
}