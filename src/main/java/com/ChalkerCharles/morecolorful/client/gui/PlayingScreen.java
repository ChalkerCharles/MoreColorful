package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.MidiHandler;
import com.ChalkerCharles.morecolorful.client.ModKeyMapping;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentTickingPacket;
import com.ChalkerCharles.morecolorful.network.packets.NotePlayingPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
import com.ChalkerCharles.morecolorful.util.MusicalInstrument;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
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
    private static final ResourceLocation PLAYING_SCREEN_TEXTURE = MoreColorful.location("textures/gui/playing_screen.png");
    private static final Component TITLE = Component.translatable("morecolorful.gui.playing_screen_title");
    private static final FormattedCharSequence TITLE_LENGTH = TITLE.getVisualOrderText();
    private static final FormattedCharSequence SINGLE_LETTER_LENGTH = Component.literal("C").getVisualOrderText();
    private static final FormattedCharSequence LETTER_WITH_BRACKETS = Component.literal("[C]").getVisualOrderText();
    private static final KeyBuilder[] KEY_BUILDER = new KeyBuilder[] {
            new KeyBuilder(46, 111, -1, 0, GLFW.GLFW_KEY_A),
            new KeyBuilder(53, 111, 3, 1, GLFW.GLFW_KEY_Z),
            new KeyBuilder(63, 111, -1, 2, GLFW.GLFW_KEY_S),
            new KeyBuilder(69, 111, 4, 3, GLFW.GLFW_KEY_X),
            new KeyBuilder(80, 111, -1, 4, GLFW.GLFW_KEY_D),
            new KeyBuilder(85, 111, 2, 5, GLFW.GLFW_KEY_C),
            new KeyBuilder(101, 111, 0, 6, GLFW.GLFW_KEY_V),
            new KeyBuilder(110, 111, -1, 7, GLFW.GLFW_KEY_G),
            new KeyBuilder(117, 111, 1, 8, GLFW.GLFW_KEY_B),
            new KeyBuilder(128, 111, -1, 9, GLFW.GLFW_KEY_H),
            new KeyBuilder(133, 111, 2, 10, GLFW.GLFW_KEY_N),
            new KeyBuilder(21, 53, 0, 11, GLFW.GLFW_KEY_Q),
            new KeyBuilder(30, 53, -1, 12, GLFW.GLFW_KEY_2),
            new KeyBuilder(37, 53, 3, 13, GLFW.GLFW_KEY_W),
            new KeyBuilder(47, 53, -1, 14, GLFW.GLFW_KEY_3),
            new KeyBuilder(53, 53, 4, 15, GLFW.GLFW_KEY_E),
            new KeyBuilder(64, 53, -1, 16, GLFW.GLFW_KEY_4),
            new KeyBuilder(69, 53, 2, 17, GLFW.GLFW_KEY_R),
            new KeyBuilder(85, 53, 0, 18, GLFW.GLFW_KEY_T),
            new KeyBuilder(94, 53, -1, 19, GLFW.GLFW_KEY_6),
            new KeyBuilder(101, 53, 1, 20, GLFW.GLFW_KEY_Y),
            new KeyBuilder(112, 53, -1, 21, GLFW.GLFW_KEY_7),
            new KeyBuilder(117, 53, 2, 22, GLFW.GLFW_KEY_U),
            new KeyBuilder(133, 53, 0, 23, GLFW.GLFW_KEY_I),
            new KeyBuilder(142, 53, -1, 24, GLFW.GLFW_KEY_9)
    };
    private final KeyButton[] allKeys = new KeyButton[25];
    private final Int2ObjectMap<KeyButton> keyCodes = new Int2ObjectOpenHashMap<>();
    private OctaveButton octaveButton;
    public final Player player;
    public InstrumentsType type;
    public final BlockPos pos;
    private float tick = 0;
    public boolean isPressing = false;
    private final MidiHandler midiHandler;

    public PlayingScreen(Player pPlayer, InstrumentsType pType, BlockPos pPos) {
        super(TITLE);
        this.player = pPlayer;
        this.type = pType;
        this.pos = pPos;
        this.midiHandler = new MidiHandler(id -> allKeys[id].press(), id -> allKeys[id].restore());
    }

    @Override
    protected void init() {
        int x = (this.width - 186) / 2;
        Button button = Button.builder(CommonComponents.GUI_DONE, b -> this.onClose())
                .pos(x, 192)
                .size(186, 20)
                .build();
        this.addRenderableWidget(button);
        for (KeyBuilder builder : KEY_BUILDER) {
            KeyButton key = this.addRenderableWidget(builder.build(this, x));
            allKeys[key.keyId] = key;
            keyCodes.put(builder.keyCode, key);
        }
        if (type == InstrumentsType.PIANO_LOW || type == InstrumentsType.PIANO_HIGH) {
            this.octaveButton = this.addRenderableWidget(new OctaveButton(x + 169, 37, type, Button -> octaveButton.toggleOctave(this)));
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = this.width;
        int j = this.font.width(TITLE_LENGTH);
        int k = this.font.width(SINGLE_LETTER_LENGTH);
        int l = this.font.width(LETTER_WITH_BRACKETS);
        int x = (i - 186) / 2, m = k / 2, n = l / 2;
        // Title
        pGuiGraphics.drawString(this.font, TITLE, (i - j) / 2, 39, 4210752, false);
        // Pitches
        pGuiGraphics.drawCenteredString(this.font, "F♯", x + 53, 125, 16777215);
        pGuiGraphics.drawString(this.font, "G", x + 61 - m, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "G♯", x + 70, 125, 16777215);
        pGuiGraphics.drawString(this.font, "A", x + 77 - m, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "A♯", x + 87, 125, 16777215);
        pGuiGraphics.drawString(this.font, "B", x + 93 - m, 143, 4210752, false);
        pGuiGraphics.drawString(this.font, "C", x + 109 - m, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "C♯", x + 117, 125, 16777215);
        pGuiGraphics.drawString(this.font, "D", x + 125 - m, 143, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "D♯", x + 135, 125, 16777215);
        pGuiGraphics.drawString(this.font, "E", x + 141 - m, 143, 4210752, false);
        pGuiGraphics.drawString(this.font, "F", x + 29 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "F♯", x + 37, 67, 16777215);
        pGuiGraphics.drawString(this.font, "G", x + 45 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "G♯", x + 54, 67, 16777215);
        pGuiGraphics.drawString(this.font, "A", x + 61 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "A♯", x + 71, 67, 16777215);
        pGuiGraphics.drawString(this.font, "B", x + 77 - m, 85, 4210752, false);
        pGuiGraphics.drawString(this.font, "C", x + 93 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "C♯", x + 101, 67, 16777215);
        pGuiGraphics.drawString(this.font, "D", x + 109 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "D♯", x + 119, 67, 16777215);
        pGuiGraphics.drawString(this.font, "E", x + 125 - m, 85, 4210752, false);
        pGuiGraphics.drawString(this.font, "F", x + 141 - m, 85, 4210752, false);
        pGuiGraphics.drawCenteredString(this.font, "F♯", x + 149, 67, 16777215);
        // Keybindings
        pGuiGraphics.drawCenteredString(this.font, "[A]", x + 53, 133, 9145227);
        pGuiGraphics.drawString(this.font,"[Z]",(i - 186 ) / 2 + 61 - n, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[S]", x + 70, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[X]", x + 77 - n, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[D]", x + 87, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[C]", x + 93 - n, 150, 9145227, false);
        pGuiGraphics.drawString(this.font, "[V]", x + 109 - n, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[G]", x + 117, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[B]", x + 125 - n, 150, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[H]", x + 135, 133, 9145227);
        pGuiGraphics.drawString(this.font, "[N]", x + 141 - n, 150, 9145227, false);
        pGuiGraphics.drawString(this.font, "[Q]", x + 29 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[2]", x + 37, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[W]", x + 45 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[3]", x + 54, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[E]", x + 61 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[4]", x + 71, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[R]", x + 77 - n, 92, 9145227, false);
        pGuiGraphics.drawString(this.font, "[T]", x + 93 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[6]", x + 101, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[Y]", x + 109 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[7]", x + 119, 73, 9145227);
        pGuiGraphics.drawString(this.font, "[U]", x + 125 - n, 92, 9145227, false);
        pGuiGraphics.drawString(this.font, "[I]", x + 141 - n, 92, 9145227, false);
        pGuiGraphics.drawCenteredString(this.font, "[9]", x + 149, 73, 9145227);
        // Tooltip
        this.renderOctaveButtonTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    private void renderOctaveButtonTooltip(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
        if (this.octaveButton != null && this.octaveButton.isHovered()) {
            Component octaveMessage = type == InstrumentsType.PIANO_HIGH
                    ? Component.translatable("morecolorful.gui.octave_high_message")
                    : Component.translatable("morecolorful.gui.octave_low_message");
            Component octaveToggle = Component.translatable("morecolorful.gui.octave_toggle", ModKeyMapping.OCTAVE_TOGGLE.get().getKey().getDisplayName());
            List<Component> list = List.of(octaveMessage, octaveToggle);
            pGuiGraphics.renderTooltip(this.font, list, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int x = (this.width - 186) / 2;
        pGuiGraphics.blit(PLAYING_SCREEN_TEXTURE, x, 32, 0, 0, 186, 140);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        if (this.minecraft == null) return;
        this.minecraft.setScreen(null);
        isPressing = false;
        player.stopUsingItem();
        midiHandler.closeDevices();
        PacketDistributor.sendToServer(new PlayingScreenPacket(type, pos, player.getId(), false));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        double x = (this.width - 186) / 2.0;
        if (pMouseX > x + 37 && pMouseX < x + 149 && pMouseY > 111 && pMouseY < 159){
            this.setDragging(true);
        } else {
            this.setDragging(pMouseX > x + 21 && pMouseX < x + 165 && pMouseY > 53 && pMouseY < 101);
        }
        for (KeyButton key: allKeys) {
            if (key.isHovered() && (key.keyId == 0 || key.keyId == 24)) {
                key.press(true);
                continue;
            }
            if (key.isHovered() && (key.keyType >= 0 != (getNextKey(key).isHovered() || getPrevKey(key).isHovered()))) {
                key.press(true);
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        for (KeyButton b : allKeys) {
            if (b.pressedByClick) b.restore();
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    private void restoreAllExcept(KeyButton excepted) {
        for (KeyButton b : allKeys) {
            if (!b.equals(excepted) && b.pressedByClick) {
                b.restore();
            }
        }
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        double i = (this.width - 186) / 2.0;
        if (pMouseX > i + 37 && pMouseX < i + 149 && pMouseY > 111 && pMouseY < 159 && this.isDragging()) {
            for (int x = 0; x <= 10; x++) {
                KeyButton key = allKeys[x];
                if (!key.isHovered()) continue;
                if ((key.keyId == 0 || key.keyId == 24)) {
                    key.press(true);
                    continue;
                }
                if (key.keyType >= 0 == !(getNextKey(x).isHovered() || getPrevKey(x).isHovered())) {
                    key.press(true);
                    restoreAllExcept(key);
                }
            }
        }
        if (pMouseX > i + 21 && pMouseX < i + 165 && pMouseY > 53 && pMouseY < 101 && this.isDragging()) {
            for (int x = 11; x <= 24; x++) {
                KeyButton key = allKeys[x];
                if (!key.isHovered()) continue;
                if ((key.keyId == 0 || key.keyId == 24)) {
                    key.press(true);
                    continue;
                }
                if (key.keyType >= 0 == !(getNextKey(x).isHovered() || getPrevKey(x).isHovered())) {
                    key.press(true);
                    restoreAllExcept(key);
                }
            }
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers){
        if (pKeyCode == ModKeyMapping.OCTAVE_TOGGLE.get().getKey().getValue() && octaveButton != null) {
            octaveButton.onPress();
            return true;
        }
        KeyButton key = keyCodes.get(pKeyCode);
        if (key != null) {
            key.press();
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers){
        KeyButton key = keyCodes.get(pKeyCode);
        if (key != null && !key.pressedByClick) {
            key.restore();
            return true;
        }
        return super.keyReleased(pKeyCode, pScanCode, pModifiers);
    }

    private boolean isNonePressed() {
        for (KeyButton k : allKeys) {
            if (k.isPressed) return false;
        }
        return true;
    }

    private boolean isAnyLeftKeysPressed() {
        for (KeyButton k : allKeys) {
            if (k.keyId <= 11 && k.isPressed) return true;
        }
        return false;
    }

    private boolean isAnyRightKeysPressed() {
        for (KeyButton k : allKeys) {
            if (k.keyId >= 12 && k.isPressed) return true;
        }
        return false;
    }

    @Override
    public void tick() {
        if (this.minecraft == null) return;
        if (isNonePressed()) {
            isPressing = false;
            PacketDistributor.sendToServer(new InstrumentPressingPacket(player.getId(), false));
        }

        if (type.isKeyBoard() || type == InstrumentsType.GUZHENG) {
            tick++;
        }

        if (isPressing) {
            if (type == InstrumentsType.HARP) {
                player.swing(InteractionHand.MAIN_HAND);
            } else if (type == InstrumentsType.COW_BELL) {
                player.swing(this.getDrumstickHand());
            } else if (type == InstrumentsType.GLOCKENSPIEL || type == InstrumentsType.XYLOPHONE || type == InstrumentsType.VIBRAPHONE) {
                if (isAnyLeftKeysPressed()) {
                    player.swing(ItemUtils.getLeftHand(player));
                }
                if (isAnyRightKeysPressed()) {
                    player.swing(ItemUtils.getRightHand(player));
                }
            } else if (type.isPercussion()) {
                player.swing(this.getDrumstickHand());
            }
        }

        if (this.shouldClose()) {
            this.onClose();
        }
        PacketDistributor.sendToServer(new InstrumentTickingPacket(tick, player.getId()));
    }

    private InteractionHand getDrumstickHand() {
        return player.getItemInHand(InteractionHand.MAIN_HAND).getItem() == ModItems.DRUMSTICK.get() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
    }

    private boolean shouldClose() {
        if (type.isBlock()) {
            Block block = player.level().getBlockState(pos).getBlock();
            if (MusicalInstrument.isSameInstrument(block, this.type)) return false;
            return !(MusicalInstrument.isSameInstrument(block, InstrumentsType.PIANO_LOW) && this.type == InstrumentsType.PIANO_HIGH);
        } else {
            Item item = player.getItemInHand(player.getUsedItemHand()).getItem();
            return !MusicalInstrument.isSameInstrument(item, this.type);
        }
    }

    private KeyButton getNextKey(int keyId) {
        int nextKeyId = keyId + 1 > 24 ? 0 : keyId + 1;
        return allKeys[nextKeyId];
    }

    private KeyButton getPrevKey(int keyId) {
        int prevKeyId = keyId - 1 < 0 ? 24 : keyId - 1;
        return allKeys[prevKeyId];
    }

    private KeyButton getNextKey(KeyButton keyButton) {
        return getNextKey(keyButton.keyId);
    }

    private KeyButton getPrevKey(KeyButton keyButton) {
        return getPrevKey(keyButton.keyId);
    }

    public static void openPlayingScreen(Player player, InstrumentsType type) {
        Minecraft.getInstance().setScreen(new PlayingScreen(player, type, BlockPos.ZERO));
    }

    public static void openPlayingScreen(Player player, InstrumentsType type, BlockPos pos) {
        Minecraft.getInstance().setScreen(new PlayingScreen(player, type, pos));
    }

    @OnlyIn(Dist.CLIENT)
    private class KeyButton extends Button {
        private static final ResourceLocation WHITE_KEY_CF = MoreColorful.location("key/white_key_cf");
        private static final ResourceLocation WHITE_KEY_CF_PRESSED = MoreColorful.location("key/white_key_cf_pressed");
        private static final ResourceLocation WHITE_KEY_D = MoreColorful.location("key/white_key_d");
        private static final ResourceLocation WHITE_KEY_D_PRESSED = MoreColorful.location("key/white_key_d_pressed");
        private static final ResourceLocation WHITE_KEY_EB = MoreColorful.location("key/white_key_eb");
        private static final ResourceLocation WHITE_KEY_EB_PRESSED = MoreColorful.location("key/white_key_eb_pressed");
        private static final ResourceLocation WHITE_KEY_G = MoreColorful.location("key/white_key_g");
        private static final ResourceLocation WHITE_KEY_G_PRESSED = MoreColorful.location("key/white_key_g_pressed");
        private static final ResourceLocation WHITE_KEY_A = MoreColorful.location("key/white_key_a");
        private static final ResourceLocation WHITE_KEY_A_PRESSED = MoreColorful.location("key/white_key_a_pressed");
        private static final ResourceLocation BLACK_KEY = MoreColorful.location("key/black_key");
        private static final ResourceLocation BLACK_KEY_PRESSED = MoreColorful.location("key/black_key_pressed");
        private final int keyType;
        private boolean isPressed;
        private final int keyId;
        private boolean pressedByClick;

        private KeyButton(int x, int y, int keyType, int keyId) {
            super(x, y, getWidth(keyType), getHeight(keyType), CommonComponents.EMPTY, Button -> {}, DEFAULT_NARRATION);
            this.keyType = keyType;
            this.keyId = keyId;
        }

        private static int getWidth(int keyType) {
            return keyType < 0 ? 12 : 16;
        }

        private static int getHeight(int keyType) {
            return keyType < 0 ? 32 : 48;
        }

        @Override
        public void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            ResourceLocation resourcelocation = switch (this.keyType) {
                case 0 -> isPressed ? WHITE_KEY_CF_PRESSED : WHITE_KEY_CF;
                case 1 -> isPressed ? WHITE_KEY_D_PRESSED : WHITE_KEY_D;
                case 2 -> isPressed ? WHITE_KEY_EB_PRESSED : WHITE_KEY_EB;
                case 3 -> isPressed ? WHITE_KEY_G_PRESSED : WHITE_KEY_G;
                case 4 -> isPressed ? WHITE_KEY_A_PRESSED : WHITE_KEY_A;
                default -> isPressed ? BLACK_KEY_PRESSED : BLACK_KEY;
            };
            pGuiGraphics.blitSprite(resourcelocation, this.getX(), this.getY(), this.width, this.height);
        }

        private void playSound() {
            InstrumentsType type = PlayingScreen.this.type;
            Player player = PlayingScreen.this.player;
            int pitchId = keyId - 12;
            Level level = player.level();
            if (type.isBlock()) {
                BlockPos pos = PlayingScreen.this.pos;
                level.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, type.getSoundEvent(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitchId / 12.0));
                PacketDistributor.sendToServer(new NotePlayingPacket(type, pos, keyId));
            } else {
                level.playSound(player, player, type.getSoundEvent(), SoundSource.RECORDS, 3.0F, (float) Math.pow(2, pitchId / 12.0));
                PacketDistributor.sendToServer(new NotePlayingPacket(type, player.blockPosition(), keyId));
            }
        }

        private void press(boolean byClick) {
            if (this.active && !this.isPressed) {
                this.playSound();
                this.pressedByClick = byClick;
                this.isPressed = true;
                this.active = false;
                PlayingScreen.this.isPressing = true;
                PacketDistributor.sendToServer(new InstrumentPressingPacket(PlayingScreen.this.player.getId(), true));
            }
        }

        private void press() {
            this.press(false);
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

    private record KeyBuilder(int x, int y, int keyType, int keyId, int keyCode) {
        private KeyButton build(PlayingScreen screen, int x) {
            return screen.new KeyButton(x + this.x, this.y, this.keyType, this.keyId);
        }
    }
}
