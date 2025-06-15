package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.MidiHandler;
import com.ChalkerCharles.morecolorful.client.ModKeyMapping;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.MusicalInstrumentBlock;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.MusicalInstrumentItem;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentTickingPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.Constants;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
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
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class PlayingScreen extends Screen {
    private final Int2ObjectMap<KeyButton> allKeys = new Int2ObjectOpenHashMap<>();
    private final Int2ObjectMap<KeyButton> keyCodes = new Int2ObjectOpenHashMap<>();
    private OctaveButton octaveButton;
    private static final ResourceLocation PLAYING_SCREEN_TEXTURE = MoreColorful.location("textures/gui/playing_screen.png");
    private static final Component TITLE = Component.translatable("morecolorful.gui.playing_screen_title");
    private static final FormattedCharSequence TITLE_LENGTH = TITLE.getVisualOrderText();
    private static final FormattedCharSequence SINGLE_LETTER_LENGTH = Component.literal("C").getVisualOrderText();
    private static final FormattedCharSequence LETTER_WITH_BRACKETS = Component.literal("[C]").getVisualOrderText();
    public final Player pPlayer;
    public InstrumentsType pType;
    public final BlockPos pPos;
    public static final BlockPos DEFAULT_POS = Constants.DEFAULT_INSTRUMENT_POS;
    private float pTick = 0;
    private boolean isDragging;
    public boolean isPressing = false;
    private final MidiHandler midiHandler;

    public PlayingScreen(Player pPlayer, InstrumentsType pType, BlockPos pPos) {
        super(TITLE);
        this.pPlayer = pPlayer;
        this.pType = pType;
        this.pPos = pPos;
        this.midiHandler = new MidiHandler(
                id -> getKeyById(id).press(),
                id -> getKeyById(id).restore()
        );
    }

    @Override
    protected void init() {
        int i = this.width;
        Button button = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, pButton -> {
            if (this.minecraft != null) this.minecraft.setScreen(null);
            isPressing = false;
            pPlayer.stopUsingItem();
            PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), false));
        }).pos((i - 186) / 2, 192).size(186, 20).build());
        this.addWidget(button);
        KeyButton blackKey_0 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 46, 111, -1, 0));
        KeyButton blackKey_2 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 63, 111, -1, 2));
        KeyButton blackKey_4 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 80, 111, -1, 4));
        KeyButton blackKey_7 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 110, 111, -1, 7));
        KeyButton blackKey_9 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 128, 111, -1, 9));
        KeyButton blackKey_12 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 30, 53, -1, 12));
        KeyButton blackKey_14 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 47, 53, -1, 14));
        KeyButton blackKey_16 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 64, 53, -1, 16));
        KeyButton blackKey_19 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 94, 53, -1, 19));
        KeyButton blackKey_21 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 112, 53, -1, 21));
        KeyButton blackKey_24 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 142, 53, -1, 24));
        KeyButton.width = 16;
        KeyButton.height = 48;
        KeyButton whiteKey_1 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 53, 111, 3, 1));
        KeyButton whiteKey_3 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 69, 111, 4, 3));
        KeyButton whiteKey_5 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 85, 111, 2, 5));
        KeyButton whiteKey_6 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 101, 111, 0, 6));
        KeyButton whiteKey_8 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 117, 111, 1, 8));
        KeyButton whiteKey_10 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 133, 111, 2, 10));
        KeyButton whiteKey_11 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 21, 53, 0, 11));
        KeyButton whiteKey_13 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 37, 53, 3, 13));
        KeyButton whiteKey_15 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 53, 53, 4, 15));
        KeyButton whiteKey_17 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 69, 53, 2, 17));
        KeyButton whiteKey_18 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 85, 53, 0, 18));
        KeyButton whiteKey_20 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 101, 53, 1, 20));
        KeyButton whiteKey_22 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 117, 53, 2, 22));
        KeyButton whiteKey_23 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 133, 53, 0, 23));
        KeyButton.width = 12;
        KeyButton.height = 32;
        if (pType == InstrumentsType.PIANO_LOW || pType == InstrumentsType.PIANO_HIGH) {
            this.octaveButton = this.addRenderableWidget(new OctaveButton((i - 186) / 2 + 169, 37, pType, Button -> octaveButton.toggleOctave(this)));
        }
        addKeys(blackKey_0,
                whiteKey_1,
                blackKey_2,
                whiteKey_3,
                blackKey_4,
                whiteKey_5,
                whiteKey_6,
                blackKey_7,
                whiteKey_8,
                blackKey_9,
                whiteKey_10,
                whiteKey_11,
                blackKey_12,
                whiteKey_13,
                blackKey_14,
                whiteKey_15,
                blackKey_16,
                whiteKey_17,
                whiteKey_18,
                blackKey_19,
                whiteKey_20,
                blackKey_21,
                whiteKey_22,
                whiteKey_23,
                blackKey_24
        );
        keyCodes.put(GLFW.GLFW_KEY_A, blackKey_0);
        keyCodes.put(GLFW.GLFW_KEY_Z, whiteKey_1);
        keyCodes.put(GLFW.GLFW_KEY_S, blackKey_2);
        keyCodes.put(GLFW.GLFW_KEY_X, whiteKey_3);
        keyCodes.put(GLFW.GLFW_KEY_D, blackKey_4);
        keyCodes.put(GLFW.GLFW_KEY_C, whiteKey_5);
        keyCodes.put(GLFW.GLFW_KEY_V, whiteKey_6);
        keyCodes.put(GLFW.GLFW_KEY_G, blackKey_7);
        keyCodes.put(GLFW.GLFW_KEY_B, whiteKey_8);
        keyCodes.put(GLFW.GLFW_KEY_H, blackKey_9);
        keyCodes.put(GLFW.GLFW_KEY_N, whiteKey_10);
        keyCodes.put(GLFW.GLFW_KEY_Q, whiteKey_11);
        keyCodes.put(GLFW.GLFW_KEY_2, blackKey_12);
        keyCodes.put(GLFW.GLFW_KEY_W, whiteKey_13);
        keyCodes.put(GLFW.GLFW_KEY_3, blackKey_14);
        keyCodes.put(GLFW.GLFW_KEY_E, whiteKey_15);
        keyCodes.put(GLFW.GLFW_KEY_4, blackKey_16);
        keyCodes.put(GLFW.GLFW_KEY_R, whiteKey_17);
        keyCodes.put(GLFW.GLFW_KEY_T, whiteKey_18);
        keyCodes.put(GLFW.GLFW_KEY_6, blackKey_19);
        keyCodes.put(GLFW.GLFW_KEY_Y, whiteKey_20);
        keyCodes.put(GLFW.GLFW_KEY_7, blackKey_21);
        keyCodes.put(GLFW.GLFW_KEY_U, whiteKey_22);
        keyCodes.put(GLFW.GLFW_KEY_I, whiteKey_23);
        keyCodes.put(GLFW.GLFW_KEY_9, blackKey_24);
        super.init();
    }

    private void addKeys(KeyButton... keys) {
        for (KeyButton key : keys) {
            allKeys.put(key.keyId, key);
        }
    }

    @Override
    public void render(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = this.width;
        int j = this.font.width(TITLE_LENGTH);
        int k = this.font.width(SINGLE_LETTER_LENGTH);
        int l = this.font.width(LETTER_WITH_BRACKETS);
        // Title
        pGuiGraphics.drawString(this.font, TITLE, (i - j) / 2, 39, 4210752, false);
        // Pitches
        pGuiGraphics.drawCenteredString(this.font, "F♯", (i - 186) / 2 + 53, 125, 16777215);
        pGuiGraphics.drawString(this.font, "G", (i - 186) / 2 + 61 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "G♯", (i - 186) / 2 + 70, 125, 16777215);
        pGuiGraphics.drawString(this.font, "A", (i - 186) / 2 + 77 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "A♯", (i - 186) / 2 + 87, 125, 16777215);
        pGuiGraphics.drawString(this.font, "B", (i - 186) / 2 + 93 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawString(this.font, "C", (i - 186) / 2 + 109 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "C♯", (i - 186) / 2 + 117, 125, 16777215);
        pGuiGraphics.drawString(this.font, "D", (i - 186) / 2 + 125 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "D♯", (i - 186) / 2 + 135, 125, 16777215);
        pGuiGraphics.drawString(this.font, "E", (i - 186) / 2 + 141 - k / 2, 143, 4210752, false);
        pGuiGraphics.drawString(this.font, "F", (i - 186) / 2 + 29 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "F♯", (i - 186) / 2 + 37, 67, 16777215);
        pGuiGraphics.drawString(this.font, "G", (i - 186) / 2 + 45 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "G♯", (i - 186) / 2 + 54, 67, 16777215);
        pGuiGraphics.drawString(this.font, "A", (i - 186) / 2 + 61 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "A♯", (i - 186) / 2 + 71, 67, 16777215);
        pGuiGraphics.drawString(this.font, "B", (i - 186) / 2 + 77 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawString(this.font, "C", (i - 186) / 2 + 93 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "C♯", (i - 186) / 2 + 101, 67, 16777215);
        pGuiGraphics.drawString(this.font, "D", (i - 186) / 2 + 109 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "D♯", (i - 186) / 2 + 119, 67, 16777215);
        pGuiGraphics.drawString(this.font, "E", (i - 186) / 2 + 125 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawString(this.font, "F", (i - 186) / 2 + 141 - k / 2, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "F♯", (i - 186) / 2 + 149, 67, 16777215);
        // Keybindings
        pGuiGraphics.drawCenteredString(this.font, "[A]", (i - 186) / 2 + 53, 133, 9145227);
        pGuiGraphics.drawString(this.font,"[Z]",(i - 186 ) / 2 + 61 -l / 2, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[S]", (i - 186) / 2 + 70, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[X]", (i - 186) / 2 + 77 - l / 2, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[D]", (i - 186) / 2 + 87, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[C]", (i - 186) / 2 + 93 - l / 2, 150, 9145227, false);
        pGuiGraphics.drawString(this.font, "[V]", (i - 186) / 2 + 109 - l / 2, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[G]", (i - 186) / 2 + 117, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[B]", (i - 186) / 2 + 125 - l / 2, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[H]", (i - 186) / 2 + 135, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[N]", (i - 186) / 2 + 141 - l / 2, 150, 9145227, false);
        pGuiGraphics.drawString(this.font, "[Q]", (i - 186) / 2 + 29 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[2]", (i - 186) / 2 + 37, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[W]", (i - 186) / 2 + 45 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[3]", (i - 186) / 2 + 54, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[E]", (i - 186) / 2 + 61 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[4]", (i - 186) / 2 + 71, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[R]", (i - 186) / 2 + 77 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawString(this.font, "[T]", (i - 186) / 2 + 93 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[6]", (i - 186) / 2 + 101, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[Y]", (i - 186) / 2 + 109 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[7]", (i - 186) / 2 + 119, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[U]", (i - 186) / 2 + 125 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawString(this.font, "[I]", (i - 186) / 2 + 141 - l / 2, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[9]", (i - 186) / 2 + 149, 73, 9145227);
        // Tooltip
        this.renderOctaveButtonTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
    private void renderOctaveButtonTooltip(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
        if (this.octaveButton != null && this.octaveButton.isHovered()) {
            Component octaveMessage = pType == InstrumentsType.PIANO_HIGH ? Component.translatable("morecolorful.gui.octave_high_message") : Component.translatable("morecolorful.gui.octave_low_message");
            Component octaveToggle = Component.translatable("morecolorful.gui.octave_toggle", ModKeyMapping.OCTAVE_TOGGLE.get().getKey().getDisplayName());
            List<Component> list = List.of(octaveMessage, octaveToggle);
            pGuiGraphics.renderTooltip(this.font, list, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = this.width;
        pGuiGraphics.blit(PLAYING_SCREEN_TEXTURE, (i - 186) / 2, 32, 0, 0, 186, 140);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void removed() {
        midiHandler.closeDevices();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        isPressing = false;
        pPlayer.stopUsingItem();
        PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), false));
        return true;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        double i = this.width;
        if (pMouseX > (i - 186) / 2 + 37 && pMouseX < (i - 186) / 2 + 149 && pMouseY > 111 && pMouseY < 159){
            isDragging = true;
        } else isDragging = (pMouseX > (i - 186) / 2 + 21 && pMouseX < (i - 186) / 2 + 165 && pMouseY > 53 && pMouseY < 101);
        for (KeyButton key: allKeys.values()) {
            if (key.isHovered() && (key.keyId == 0 || key.keyId == 24)) {
                key.press(true);
                continue;
            }
            if (key.isHovered() && (key.keyType >= 0 != (getNextKey(key).isHovered() || getLastKey(key).isHovered()))) {
                key.press(true);
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        restoreAll();
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
    private void restoreAll() {
        allKeys.values().stream().filter(b -> b.pressedByClick).forEach(KeyButton::restore);
    }
    private void restoreAllExcept(KeyButton exceptedOne) {
        allKeys.values().stream()
                .filter(b -> !b.equals(exceptedOne))
                .filter(b -> b.pressedByClick)
                .forEach(KeyButton::restore);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        double i = this.width;
        if (pMouseX > (i - 186) / 2 + 37 && pMouseX < (i - 186) / 2 + 149 && pMouseY > 111 && pMouseY < 159 && isDragging) {
            for (int x = 0; x <= 10; x++) {
                KeyButton key = getKeyById(x);
                if (key.isHovered() && (key.keyId == 0 || key.keyId == 24)) {
                    key.press(true);
                    continue;
                }
                if (key.isHovered() && (key.keyType >= 0 == !(getNextKey(x).isHovered() || getLastKey(x).isHovered()))) {
                    key.press(true);
                    restoreAllExcept(key);
                }
            }
        }
        if (pMouseX > (i - 186) / 2 + 21 && pMouseX < (i - 186) / 2 + 165 && pMouseY > 53 && pMouseY < 101 && isDragging){
            for (int x = 11; x <= 24; x++) {
                KeyButton key = getKeyById(x);
                if (key.isHovered() && (key.keyId == 0 || key.keyId == 24)) {
                    key.press(true);
                    continue;
                }
                if (key.isHovered() && (key.keyType >= 0 == !(getNextKey(x).isHovered() || getLastKey(x).isHovered()))) {
                    key.press(true);
                    restoreAllExcept(key);
                }
            }
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers){
        super.keyPressed(pKeyCode, pScanCode, pModifiers);
        if (pKeyCode == ModKeyMapping.OCTAVE_TOGGLE.get().getKey().getValue() && octaveButton != null) {
            octaveButton.onPress();
        }
        for (int keycode : keyCodes.keySet()) {
            if (pKeyCode == keycode) {
                keyCodes.get(keycode).press();
            }
        }
        return true;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers){
        super.keyReleased(pKeyCode, pScanCode, pModifiers);
        for (int keycode : keyCodes.keySet()) {
            KeyButton key = keyCodes.get(keycode);
            if (pKeyCode == keycode && !key.pressedByClick) {
                key.restore();
            }
        }
        return true;
    }

    private boolean isAnyPressed() {
        return allKeys.values().stream().anyMatch(k -> k.isPressed);
    }

    private boolean isAnyLeftKeysPressed() {
        return allKeys.values().stream().filter(k -> k.keyId <= 11).anyMatch(k -> k.isPressed);
    }

    private boolean isAnyRightKeysPressed() {
        return allKeys.values().stream().filter(k -> k.keyId >= 12).anyMatch(k -> k.isPressed);
    }

    @Override
    public void tick() {
        if (!isAnyPressed()){
            isPressing = false;
            PacketDistributor.sendToServer(new InstrumentPressingPacket(pPlayer.getId(), false));
        }

        if (pType.getType() == InstrumentsType.Type.KEYBOARD || pType == InstrumentsType.GUZHENG) {
            pTick ++;
        }

        InteractionHand leftHand = pPlayer.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        InteractionHand rightHand = pPlayer.getMainArm() == HumanoidArm.RIGHT ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        InteractionHand drumstickHand = pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.DRUMSTICK.get() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;

        if (isPressing) {
            if (pType == InstrumentsType.HARP) {
                pPlayer.swing(InteractionHand.MAIN_HAND);

            } else if (pType == InstrumentsType.COW_BELL) {
                pPlayer.swing(drumstickHand);

            } else if (pType == InstrumentsType.GLOCKENSPIEL || pType == InstrumentsType.XYLOPHONE || pType == InstrumentsType.VIBRAPHONE) {
                if (isAnyLeftKeysPressed()) {
                    pPlayer.swing(leftHand);
                }
                if (isAnyRightKeysPressed()) {
                    pPlayer.swing(rightHand);
                }

            } else if (pType.getType() == InstrumentsType.Type.PERCUSSION) {
                pPlayer.swing(drumstickHand);
            }
        }

        Block pBlock = pPlayer.level().getBlockState(pPos).getBlock();
        boolean exception = pBlock instanceof MusicalInstrumentBlock block && block.getType() == InstrumentsType.PIANO_LOW && this.pType == InstrumentsType.PIANO_HIGH;

        if (this.minecraft != null) {
            if (pPos != DEFAULT_POS) {
                if ((!(pBlock instanceof MusicalInstrumentBlock block) || block.getType() != this.pType) && !exception) {
                    minecraft.setScreen(null);
                    isPressing = false;
                    PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), false));
                }
            } else if (!(pPlayer.getItemInHand(pPlayer.getUsedItemHand()).getItem() instanceof MusicalInstrumentItem item) || item.getType() != this.pType) {
                minecraft.setScreen(null);
                isPressing = false;
                pPlayer.stopUsingItem();
                PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), false));
            }
        }
        PacketDistributor.sendToServer(new InstrumentTickingPacket(pTick, pPlayer.getId()));
    }

    private KeyButton getKeyById(int keyId) {
        return allKeys.get(keyId);
    }
    private KeyButton getNextKey(int keyId) {
        int nextKeyId = keyId + 1 > 24 ? 0 : keyId + 1;
        return getKeyById(nextKeyId);
    }
    private KeyButton getLastKey(int keyId) {
        int lastKeyId = keyId - 1 < 0 ? 24 : keyId - 1;
        return getKeyById(lastKeyId);
    }
    private KeyButton getNextKey(KeyButton keyButton) {
        return getNextKey(keyButton.keyId);
    }
    private KeyButton getLastKey(KeyButton keyButton) {
        return getLastKey(keyButton.keyId);
    }

    public static void openPlayingScreen(Player pPlayer, InstrumentsType pType){
        Minecraft.getInstance().setScreen(new PlayingScreen(pPlayer, pType, DEFAULT_POS));
    }
    public static void openPlayingScreen(Player pPlayer, InstrumentsType pType, BlockPos pPos){
        Minecraft.getInstance().setScreen(new PlayingScreen(pPlayer, pType, pPos));
    }
}
