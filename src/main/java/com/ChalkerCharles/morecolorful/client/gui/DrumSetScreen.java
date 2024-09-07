package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.DrumSetPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class DrumSetScreen extends Screen {
    private DrumSetButton bass_drum;
    private DrumSetButton snare_1;
    private DrumSetButton snare_2;
    private DrumSetButton snare_3;
    private DrumSetButton tom_1;
    private DrumSetButton tom_2;
    private DrumSetButton tom_3;
    private DrumSetButton tom_4;
    private DrumSetButton hat_1;
    private DrumSetButton hat_2;
    private DrumSetButton hat_3;
    private DrumSetButton ride;
    private DrumSetButton crash;
    private final DrumSetButton[] allButtons = new DrumSetButton[13];
    private static final ResourceLocation DRUM_SET_SCREEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "textures/gui/drum_set_screen.png");
    private static final Component TITLE = Component.translatable("block.morecolorful.drum_set");
    private static final FormattedCharSequence TITLE_LENGTH = TITLE.getVisualOrderText();
    private static final FormattedCharSequence LETTER_WITH_BRACKETS = Component.literal("[C]").getVisualOrderText();
    public final Player pPlayer;
    public final BlockPos pPos;
    public boolean isPressing = false;

    protected DrumSetScreen(Player pPlayer, BlockPos pPos) {
        super(TITLE);
        this.pPlayer = pPlayer;
        this.pPos = pPos;
    }

    @Override
    protected void init() {
        int i = this.width;
        Button button = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, pButton -> {
            if (this.minecraft != null) this.minecraft.setScreen(null);
            isPressing = false;
            PacketDistributor.sendToServer(new DrumSetPacket(false, false, false, false, pPos, pPlayer.getId()));
        }).pos((i - 186) / 2, 192).size(186, 20).build());
        this.addWidget(button);
        this.bass_drum = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 75, 118, InstrumentsType.BASS_DRUM, 12));
        allButtons[0] = this.bass_drum;
        this.snare_1 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 112, 126, InstrumentsType.SNARE, 6));
        allButtons[1] = this.snare_1;
        this.snare_2 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 134, 126, InstrumentsType.SNARE, 12));
        allButtons[2] = this.snare_2;
        this.snare_3 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 123, 104, InstrumentsType.SNARE, 18));
        allButtons[3] = this.snare_3;
        this.tom_1 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 64, 84, InstrumentsType.TOM, 6));
        allButtons[4] = this.tom_1;
        this.tom_2 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 86, 84, InstrumentsType.TOM, 12));
        allButtons[5] = this.tom_2;
        this.tom_3 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 64, 62, InstrumentsType.TOM, 18));
        allButtons[6] = this.tom_3;
        this.tom_4 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 86, 62, InstrumentsType.TOM, 24));
        allButtons[7] = this.tom_4;
        this.hat_1 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 17, 127, InstrumentsType.HAT, 6));
        allButtons[8] = this.hat_1;
        this.hat_2 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 39, 127, InstrumentsType.HAT, 12));
        allButtons[9] = this.hat_2;
        this.hat_3 = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 28, 105, InstrumentsType.HAT, 18));
        allButtons[10] = this.hat_3;
        this.ride = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 120, 68, InstrumentsType.RIDE, 12));
        allButtons[11] = this.ride;
        this.crash = this.addRenderableWidget(new DrumSetButton((i - 170) / 2 + 31, 65, InstrumentsType.CRASH, 12));
        allButtons[12] = this.crash;
        super.init();
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = this.width;
        int j = this.font.width(TITLE_LENGTH);
        int k = this.font.width(LETTER_WITH_BRACKETS);
        // Title
        pGuiGraphics.drawString(this.font, TITLE, (i - j) / 2, 44, 4210752, false);
        // Keybindings
        pGuiGraphics.drawString(this.font,"[D]",(i - 170) / 2 + 86 - k / 2, 139, 4210752, false);
        pGuiGraphics.drawString(this.font,"[C]",(i - 170) / 2 + 123 - k / 2, 147, 4210752, false);
        pGuiGraphics.drawString(this.font,"[V]",(i - 170) / 2 + 145 - k / 2, 147, 4210752, false);
        pGuiGraphics.drawString(this.font,"[F]",(i - 170) / 2 + 134 - k / 2, 96, 4210752, false);
        pGuiGraphics.drawString(this.font,"[W]",(i - 170) / 2 + 75 - k / 2, 105, 4210752, false);
        pGuiGraphics.drawString(this.font,"[E]",(i - 170) / 2 + 97 - k / 2, 105, 4210752, false);
        pGuiGraphics.drawString(this.font,"[3]",(i - 170) / 2 + 75 - k / 2, 54, 4210752, false);
        pGuiGraphics.drawString(this.font,"[4]",(i - 170) / 2 + 97 - k / 2, 54, 4210752, false);
        pGuiGraphics.drawString(this.font,"[Z]",(i - 170) / 2 + 28 - k / 2, 148, 4210752, false);
        pGuiGraphics.drawString(this.font,"[X]",(i - 170) / 2 + 50 - k / 2, 148, 4210752, false);
        pGuiGraphics.drawString(this.font,"[S]",(i - 170) / 2 + 39 - k / 2, 97, 4210752, false);
        pGuiGraphics.drawString(this.font,"[R]",(i - 170) / 2 + 131 - k / 2, 60, 4210752, false);
        pGuiGraphics.drawString(this.font,"[Q]",(i - 170) / 2 + 42 - k / 2, 57, 4210752, false);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = this.width;
        pGuiGraphics.blit(DRUM_SET_SCREEN_TEXTURE, (i - 170) / 2, 37, 0, 0, 170, 130);
    }

    public boolean isPauseScreen() {return false;}
    public boolean shouldCloseOnEsc() {
        isPressing = false;
        PacketDistributor.sendToServer(new DrumSetPacket(false, false, false, false, pPos, pPlayer.getId()));
        return true;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        restoreAll();
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
    private void restoreAll() {
        for (DrumSetButton button : allButtons) {
            if (!button.pressedByClick) continue;
            button.restore();
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        super.keyPressed(pKeyCode, pScanCode, pModifiers);
        if (pKeyCode == GLFW.GLFW_KEY_D) bass_drum.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_C) snare_1.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_V) snare_2.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_F) snare_3.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_W) tom_1.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_E) tom_2.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_3) tom_3.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_4) tom_4.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_Z) hat_1.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_X) hat_2.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_S) hat_3.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_R) ride.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_Q) crash.press(false);
        return true;
    }
    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers){
        super.keyReleased(pKeyCode, pScanCode, pModifiers);
        if (pKeyCode == GLFW.GLFW_KEY_D && !bass_drum.pressedByClick) bass_drum.restore();
        if (pKeyCode == GLFW.GLFW_KEY_C && !snare_1.pressedByClick) snare_1.restore();
        if (pKeyCode == GLFW.GLFW_KEY_V && !snare_2.pressedByClick) snare_2.restore();
        if (pKeyCode == GLFW.GLFW_KEY_F && !snare_3.pressedByClick) snare_3.restore();
        if (pKeyCode == GLFW.GLFW_KEY_W && !tom_1.pressedByClick) tom_1.restore();
        if (pKeyCode == GLFW.GLFW_KEY_E && !tom_2.pressedByClick) tom_2.restore();
        if (pKeyCode == GLFW.GLFW_KEY_3 && !tom_3.pressedByClick) tom_3.restore();
        if (pKeyCode == GLFW.GLFW_KEY_4 && !tom_4.pressedByClick) tom_4.restore();
        if (pKeyCode == GLFW.GLFW_KEY_Z && !hat_1.pressedByClick) hat_1.restore();
        if (pKeyCode == GLFW.GLFW_KEY_X && !hat_2.pressedByClick) hat_2.restore();
        if (pKeyCode == GLFW.GLFW_KEY_S && !hat_3.pressedByClick) hat_3.restore();
        if (pKeyCode == GLFW.GLFW_KEY_R && !ride.pressedByClick) ride.restore();
        if (pKeyCode == GLFW.GLFW_KEY_Q && !crash.pressedByClick) crash.restore();
        return true;
    }

    @Override
    public void tick() {
        if (!(bass_drum.isPressed || snare_1.isPressed || snare_2.isPressed || snare_3.isPressed || tom_1.isPressed || tom_2.isPressed ||
                tom_3.isPressed || tom_4.isPressed || hat_1.isPressed || hat_2.isPressed || hat_3.isPressed || ride.isPressed || crash.isPressed)) {
            isPressing = false;
        }

        InteractionHand leftHand = pPlayer.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        InteractionHand rightHand = pPlayer.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (isPressing) {
            if (tom_1.isPressed || tom_3.isPressed || hat_1.isPressed || hat_2.isPressed || hat_3.isPressed || crash.isPressed) {
                pPlayer.swing(leftHand);
            }
            if (bass_drum.isPressed || snare_1.isPressed || snare_2.isPressed || snare_3.isPressed || tom_2.isPressed || tom_4.isPressed || ride.isPressed) {
                pPlayer.swing(rightHand);
            }
        }

        if (this.minecraft != null) {
            if (!(pPlayer.level().getBlockState(pPos).getBlock() instanceof DrumSetBlock)) {
                minecraft.setScreen(null);
                isPressing = false;
                PacketDistributor.sendToServer(new DrumSetPacket(false, false, false, false, pPos, pPlayer.getId()));
            }
        }

        boolean isPressingBassDrum = bass_drum.isPressed;
        boolean isPressingHat = hat_1.isPressed || hat_2.isPressed || hat_3.isPressed;
        boolean isPressingRide = ride.isPressed;
        boolean isPressingCrash = crash.isPressed;
        PacketDistributor.sendToServer(new DrumSetPacket(isPressingBassDrum, isPressingHat, isPressingRide, isPressingCrash, pPos, pPlayer.getId()));
    }

    public static void openScreen(Player pPlayer, BlockPos pPos){
        Minecraft.getInstance().setScreen(new DrumSetScreen(pPlayer, pPos));
    }
}
