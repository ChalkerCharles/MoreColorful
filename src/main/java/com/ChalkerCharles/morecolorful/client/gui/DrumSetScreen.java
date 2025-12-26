package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.musical.DrumSetBlock;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.DrumSetPacket;
import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
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
    private final Int2ObjectMap<DrumSetButton> keyCodes = new Int2ObjectOpenHashMap<>();
    private static final ResourceLocation DRUM_SET_SCREEN_TEXTURE = MoreColorful.location("textures/gui/drum_set_screen.png");
    private static final Component TITLE = Component.translatable("block.morecolorful.drum_set");
    private static final FormattedCharSequence TITLE_LENGTH = TITLE.getVisualOrderText();
    private static final FormattedCharSequence LETTER_WITH_BRACKETS = Component.literal("[C]").getVisualOrderText();
    public final Player player;
    public final BlockPos pos;
    public boolean isPressing = false;

    protected DrumSetScreen(Player pPlayer, BlockPos pPos) {
        super(TITLE);
        this.player = pPlayer;
        this.pos = pPos;
    }

    @Override
    protected void init() {
        int i = this.width;
        int x = (i - 170) / 2;
        Button button = Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                .pos((i - 186) / 2, 192)
                .size(186, 20)
                .build();
        this.addRenderableWidget(button);
        this.bass_drum = this.addRenderableWidget(new DrumSetButton(x + 75, 118, InstrumentsType.BASS_DRUM, 12));
        this.snare_1 = this.addRenderableWidget(new DrumSetButton(x + 112, 126, InstrumentsType.SNARE, 6));
        this.snare_2 = this.addRenderableWidget(new DrumSetButton(x + 134, 126, InstrumentsType.SNARE, 12));
        this.snare_3 = this.addRenderableWidget(new DrumSetButton(x + 123, 104, InstrumentsType.SNARE, 18));
        this.tom_1 = this.addRenderableWidget(new DrumSetButton(x + 64, 84, InstrumentsType.TOM, 6));
        this.tom_2 = this.addRenderableWidget(new DrumSetButton(x + 86, 84, InstrumentsType.TOM, 12));
        this.tom_3 = this.addRenderableWidget(new DrumSetButton(x + 64, 62, InstrumentsType.TOM, 18));
        this.tom_4 = this.addRenderableWidget(new DrumSetButton(x + 86, 62, InstrumentsType.TOM, 24));
        this.hat_1 = this.addRenderableWidget(new DrumSetButton(x + 17, 127, InstrumentsType.HAT, 6));
        this.hat_2 = this.addRenderableWidget(new DrumSetButton(x + 39, 127, InstrumentsType.HAT, 12));
        this.hat_3 = this.addRenderableWidget(new DrumSetButton(x + 28, 105, InstrumentsType.HAT, 18));
        this.ride = this.addRenderableWidget(new DrumSetButton(x + 120, 68, InstrumentsType.RIDE, 12));
        this.crash = this.addRenderableWidget(new DrumSetButton(x + 31, 65, InstrumentsType.CRASH, 12));
        System.arraycopy(new DrumSetButton[] {
                bass_drum, snare_1, snare_2, snare_3, tom_1, tom_2,
                tom_3, tom_4, hat_1, hat_2, hat_3, ride, crash
        }, 0, allButtons, 0, 13);
        keyCodes.put(GLFW.GLFW_KEY_D, bass_drum);
        keyCodes.put(GLFW.GLFW_KEY_C, snare_1);
        keyCodes.put(GLFW.GLFW_KEY_V, snare_2);
        keyCodes.put(GLFW.GLFW_KEY_F, snare_3);
        keyCodes.put(GLFW.GLFW_KEY_W, tom_1);
        keyCodes.put(GLFW.GLFW_KEY_E, tom_2);
        keyCodes.put(GLFW.GLFW_KEY_3, tom_3);
        keyCodes.put(GLFW.GLFW_KEY_4, tom_4);
        keyCodes.put(GLFW.GLFW_KEY_Z, hat_1);
        keyCodes.put(GLFW.GLFW_KEY_X, hat_2);
        keyCodes.put(GLFW.GLFW_KEY_S, hat_3);
        keyCodes.put(GLFW.GLFW_KEY_R, ride);
        keyCodes.put(GLFW.GLFW_KEY_Q, crash);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = this.width;
        int j = this.font.width(TITLE_LENGTH);
        int k = this.font.width(LETTER_WITH_BRACKETS);
        int x = (i - 170) / 2, m = k / 2;
        // Title
        pGuiGraphics.drawString(this.font, TITLE, (i - j) / 2, 44, 4210752, false);
        // Keybindings
        pGuiGraphics.drawString(this.font,"[D]",x + 86 - m, 139, 4210752, false);
        pGuiGraphics.drawString(this.font,"[C]",x + 123 - m, 147, 4210752, false);
        pGuiGraphics.drawString(this.font,"[V]",x + 145 - m, 147, 4210752, false);
        pGuiGraphics.drawString(this.font,"[F]",x + 134 - m, 96, 4210752, false);
        pGuiGraphics.drawString(this.font,"[W]",x + 75 - m, 105, 4210752, false);
        pGuiGraphics.drawString(this.font,"[E]",x + 97 - m, 105, 4210752, false);
        pGuiGraphics.drawString(this.font,"[3]",x + 75 - m, 54, 4210752, false);
        pGuiGraphics.drawString(this.font,"[4]",x + 97 - m, 54, 4210752, false);
        pGuiGraphics.drawString(this.font,"[Z]",x + 28 - m, 148, 4210752, false);
        pGuiGraphics.drawString(this.font,"[X]",x + 50 - m, 148, 4210752, false);
        pGuiGraphics.drawString(this.font,"[S]",x + 39 - m, 97, 4210752, false);
        pGuiGraphics.drawString(this.font,"[R]",x + 131 - m, 60, 4210752, false);
        pGuiGraphics.drawString(this.font,"[Q]",x + 42 - m, 57, 4210752, false);
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = this.width;
        pGuiGraphics.blit(DRUM_SET_SCREEN_TEXTURE, (i - 170) / 2, 37, 0, 0, 170, 130);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        if (this.minecraft == null) return;
        this.minecraft.setScreen(null);
        this.isPressing = false;
        PacketDistributor.sendToServer(new DrumSetPacket((byte) 0, pos, player.getId()));
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        for (DrumSetButton b : allButtons) {
            if (b.pressedByClick) b.restore();
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        DrumSetButton button = keyCodes.get(pKeyCode);
        if (button != null) {
            button.press(false);
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        DrumSetButton button = keyCodes.get(pKeyCode);
        if (button != null && !button.pressedByClick) {
            button.restore();
            return true;
        }
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    private boolean isNonePressed() {
        for (DrumSetButton b : allButtons) {
            if (b.isPressed) return false;
        }
        return true;
    }

    @Override
    public void tick() {
        if (this.minecraft == null) return;
        if (isNonePressed()) {
            isPressing = false;
        }

        InteractionHand leftHand = player.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        InteractionHand rightHand = player.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        if (isPressing) {
            if (tom_1.isPressed || tom_3.isPressed || hat_1.isPressed || hat_2.isPressed || hat_3.isPressed || crash.isPressed) {
                player.swing(leftHand);
            }
            if (bass_drum.isPressed || snare_1.isPressed || snare_2.isPressed || snare_3.isPressed || tom_2.isPressed || tom_4.isPressed || ride.isPressed) {
                player.swing(rightHand);
            }
        }


        if (!(player.level().getBlockState(pos).getBlock() instanceof DrumSetBlock)) {
            this.onClose();
        }

        boolean isPressingBassDrum = bass_drum.isPressed;
        boolean isPressingHat = hat_1.isPressed || hat_2.isPressed || hat_3.isPressed;
        boolean isPressingRide = ride.isPressed;
        boolean isPressingCrash = crash.isPressed;
        byte pressingMask = packPressingMask(isPressingBassDrum, isPressingHat, isPressingRide, isPressingCrash);
        PacketDistributor.sendToServer(new DrumSetPacket(pressingMask, pos, player.getId()));
    }

    private static byte packPressingMask(boolean isPressingBassDrum, boolean isPressingHat, boolean isPressingRide, boolean isPressingCrash) {
        byte b = 0;
        if (isPressingBassDrum) b |= 1;
        if (isPressingHat) b |= 2;
        if (isPressingRide) b |= 4;
        if (isPressingCrash) b |= 8;
        return b;
    }

    public static void openScreen(Player player, BlockPos pos) {
        Minecraft.getInstance().setScreen(new DrumSetScreen(player, pos));
    }

    @OnlyIn(Dist.CLIENT)
    private class DrumSetButton extends Button {
        private static final ResourceLocation BUTTON_BASS_DRUM = MoreColorful.location("drum_set/button_bass_drum");
        private static final ResourceLocation BUTTON_BASS_DRUM_PRESSED = MoreColorful.location("drum_set/button_bass_drum_pressed");
        private static final ResourceLocation BUTTON_SNARE = MoreColorful.location("drum_set/button_snare");
        private static final ResourceLocation BUTTON_SNARE_PRESSED = MoreColorful.location("drum_set/button_snare_pressed");
        private static final ResourceLocation BUTTON_TOM = MoreColorful.location("drum_set/button_tom");
        private static final ResourceLocation BUTTON_TOM_PRESSED = MoreColorful.location("drum_set/button_tom_pressed");
        private static final ResourceLocation BUTTON_HAT = MoreColorful.location("drum_set/button_hat");
        private static final ResourceLocation BUTTON_HAT_PRESSED = MoreColorful.location("drum_set/button_hat_pressed");
        private static final ResourceLocation BUTTON_RIDE = MoreColorful.location("drum_set/button_ride");
        private static final ResourceLocation BUTTON_RIDE_PRESSED = MoreColorful.location("drum_set/button_ride_pressed");
        private static final ResourceLocation BUTTON_CRASH = MoreColorful.location("drum_set/button_crash");
        private static final ResourceLocation BUTTON_CRASH_PRESSED = MoreColorful.location("drum_set/button_crash_pressed");
        private final InstrumentsType type;
        private final int keyId;
        private boolean pressedByClick;
        private boolean isPressed;

        private DrumSetButton(int pX, int pY, InstrumentsType type, int keyId) {
            super(pX, pY, 20, 20, CommonComponents.EMPTY, Button -> {}, DEFAULT_NARRATION);
            this.type = type;
            this.keyId = keyId;
        }
        @Override
        public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            ResourceLocation resourcelocation = switch (this.type) {
                case SNARE -> isPressed ? BUTTON_SNARE_PRESSED : BUTTON_SNARE;
                case TOM -> isPressed ? BUTTON_TOM_PRESSED : BUTTON_TOM;
                case HAT -> isPressed ? BUTTON_HAT_PRESSED : BUTTON_HAT;
                case RIDE -> isPressed ? BUTTON_RIDE_PRESSED : BUTTON_RIDE;
                case CRASH -> isPressed ? BUTTON_CRASH_PRESSED : BUTTON_CRASH;
                default -> isPressed ? BUTTON_BASS_DRUM_PRESSED : BUTTON_BASS_DRUM;
            };
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), 20, 20);
        }

        private void playSound(Player pPlayer, BlockPos pPos) {
            int pitchId = keyId - 12;
            Level pLevel = pPlayer.level();
            pLevel.playSound(pPlayer, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, type.getSoundEvent(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitchId / 12.0));
            PacketDistributor.sendToServer(new NotePlayingPacket(type, pPos, keyId));
        }

        @Override
        public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
            if (this.isHovered()) this.press(true);
            return super.mouseClicked(pMouseX, pMouseY, pButton);
        }

        private void press(boolean byClick) {
            if (this.active && !this.isPressed) {
                this.playSound(DrumSetScreen.this.player, DrumSetScreen.this.pos);
                this.pressedByClick = byClick;
                this.isPressed = true;
                this.active = false;
                DrumSetScreen.this.isPressing = true;
            }
        }
        private void restore() {
            if (!this.active && this.isPressed) {
                this.isPressed = false;
                this.active = true;
                this.pressedByClick = false;
            }
        }

        @Override
        public void playDownSound(SoundManager pHandler) {}
    }
}
