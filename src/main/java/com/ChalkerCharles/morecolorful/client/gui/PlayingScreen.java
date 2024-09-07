package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.ModKeyMapping;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.MusicalInstrumentBlock;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.MusicalInstrumentItem;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentPressingPacket;
import com.ChalkerCharles.morecolorful.network.packets.InstrumentTickingPacket;
import com.ChalkerCharles.morecolorful.network.packets.PlayingScreenPacket;
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

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PlayingScreen extends Screen {
    private KeyButton blackKey_0;
    private KeyButton whiteKey_1;
    private KeyButton blackKey_2;
    private KeyButton whiteKey_3;
    private KeyButton blackKey_4;
    private KeyButton whiteKey_5;
    private KeyButton whiteKey_6;
    private KeyButton blackKey_7;
    private KeyButton whiteKey_8;
    private KeyButton blackKey_9;
    private KeyButton whiteKey_10;
    private KeyButton whiteKey_11;
    private KeyButton blackKey_12;
    private KeyButton whiteKey_13;
    private KeyButton blackKey_14;
    private KeyButton whiteKey_15;
    private KeyButton blackKey_16;
    private KeyButton whiteKey_17;
    private KeyButton whiteKey_18;
    private KeyButton blackKey_19;
    private KeyButton whiteKey_20;
    private KeyButton blackKey_21;
    private KeyButton whiteKey_22;
    private KeyButton whiteKey_23;
    private KeyButton blackKey_24;
    private final KeyButton[] allKeys = new KeyButton[25];
    private OctaveButton octaveButton;
    private static final ResourceLocation PLAYING_SCREEN_TEXTURE = ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, "textures/gui/playing_screen.png");
    private static final Component TITLE = Component.translatable("morecolorful.gui.playing_screen_title");
    private static final FormattedCharSequence TITLE_LENGTH = TITLE.getVisualOrderText();
    private static final FormattedCharSequence SINGLE_LETTER_LENGTH = Component.literal("C").getVisualOrderText();
    private static final FormattedCharSequence LETTER_WITH_BRACKETS = Component.literal("[C]").getVisualOrderText();
    public final Player pPlayer;
    public InstrumentsType pType;
    public final BlockPos pPos;
    public static final BlockPos DEFAULT_POS = new BlockPos(0, -65, 0);
    private float pTick = 0;
    private boolean isDragging;
    public boolean isPressing = false;

    public PlayingScreen(Player pPlayer, InstrumentsType pType, BlockPos pPos) {
        super(TITLE);
        this.pPlayer = pPlayer;
        this.pType = pType;
        this.pPos = pPos;
    }

    @Override
    protected void init() {
        int i = this.width;
        Button button = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, pButton -> {
            if (this.minecraft != null) {this.minecraft.setScreen(null);}
            isPressing = false;
            pPlayer.stopUsingItem();
            PacketDistributor.sendToServer(new PlayingScreenPacket(pType, pPos, pPlayer.getId(), false));
        }).pos((i - 186) / 2, 192).size(186, 20).build());
        this.addWidget(button);
        this.blackKey_0 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 46, 111, -1, 0));
        allKeys[0] = this.blackKey_0;
        this.blackKey_2 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 63, 111, -1, 2));
        allKeys[2] = this.blackKey_2;
        this.blackKey_4 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 80, 111, -1, 4));
        allKeys[4] = this.blackKey_4;
        this.blackKey_7 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 110, 111, -1, 7));
        allKeys[7] = this.blackKey_7;
        this.blackKey_9 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 128, 111, -1, 9));
        allKeys[9] = this.blackKey_9;
        this.blackKey_12 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 30, 53, -1, 12));
        allKeys[12] = this.blackKey_12;
        this.blackKey_14 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 47, 53, -1, 14));
        allKeys[14] = this.blackKey_14;
        this.blackKey_16 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 64, 53, -1, 16));
        allKeys[16] = this.blackKey_16;
        this.blackKey_19 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 94, 53, -1, 19));
        allKeys[19] = this.blackKey_19;
        this.blackKey_21 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 112, 53, -1, 21));
        allKeys[21] = this.blackKey_21;
        this.blackKey_24 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 142, 53, -1, 24));
        allKeys[24] = this.blackKey_24;
        KeyButton.width = 16;
        KeyButton.height = 48;
        this.whiteKey_1 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 53, 111, 3, 1));
        allKeys[1] = this.whiteKey_1;
        this.whiteKey_3 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 69, 111, 4, 3));
        allKeys[3] = this.whiteKey_3;
        this.whiteKey_5 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 85, 111, 2, 5));
        allKeys[5] = this.whiteKey_5;
        this.whiteKey_6 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 101, 111, 0, 6));
        allKeys[6] = this.whiteKey_6;
        this.whiteKey_8 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 117, 111, 1, 8));
        allKeys[8] = this.whiteKey_8;
        this.whiteKey_10 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 133, 111, 2, 10));
        allKeys[10] = this.whiteKey_10;
        this.whiteKey_11 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 21, 53, 0, 11));
        allKeys[11] = this.whiteKey_11;
        this.whiteKey_13 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 37, 53, 3, 13));
        allKeys[13] = this.whiteKey_13;
        this.whiteKey_15 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 53, 53, 4, 15));
        allKeys[15] = this.whiteKey_15;
        this.whiteKey_17 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 69, 53, 2, 17));
        allKeys[17] = this.whiteKey_17;
        this.whiteKey_18 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 85, 53, 0, 18));
        allKeys[18] = this.whiteKey_18;
        this.whiteKey_20 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 101, 53, 1, 20));
        allKeys[20] = this.whiteKey_20;
        this.whiteKey_22 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 117, 53, 2, 22));
        allKeys[22] = this.whiteKey_22;
        this.whiteKey_23 = this.addRenderableWidget(new KeyButton((i - 186) / 2 + 133, 53, 0, 23));
        allKeys[23] = this.whiteKey_23;
        KeyButton.width = 12;
        KeyButton.height = 32;
        if (pType == InstrumentsType.PIANO_LOW || pType == InstrumentsType.PIANO_HIGH) {
            this.octaveButton = this.addRenderableWidget(new OctaveButton((i - 186) / 2 + 169, 37, pType, Button -> octaveButton.toggleOctave(this)));
        }
        super.init();
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
            List<Component> list = List.of(new Component[]{octaveMessage, octaveToggle});
            pGuiGraphics.renderTooltip(this.font, list, java.util.Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = this.width;
        pGuiGraphics.blit(PLAYING_SCREEN_TEXTURE, (i - 186) / 2, 32, 0, 0, 186, 140);
    }

    public boolean isPauseScreen() {return false;}
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
        if (blackKey_0.isHovered())
            blackKey_0.press(true);
        if (whiteKey_1.isHovered() && !blackKey_0.isHovered() && !blackKey_2.isHovered())
            whiteKey_1.press(true);
        if (blackKey_2.isHovered())
            blackKey_2.press(true);
        if (whiteKey_3.isHovered() && !blackKey_2.isHovered() && !blackKey_4.isHovered())
            whiteKey_3.press(true);
        if (blackKey_4.isHovered())
            blackKey_4.press(true);
        if (whiteKey_5.isHovered() && !blackKey_4.isHovered())
            whiteKey_5.press(true);
        if (whiteKey_6.isHovered() && !blackKey_7.isHovered())
            whiteKey_6.press(true);
        if (blackKey_7.isHovered())
            blackKey_7.press(true);
        if (whiteKey_8.isHovered() && !blackKey_7.isHovered() && !blackKey_9.isHovered())
            whiteKey_8.press(true);
        if (blackKey_9.isHovered())
            blackKey_9.press(true);
        if (whiteKey_10.isHovered() && !blackKey_9.isHovered())
            whiteKey_10.press(true);
        if (whiteKey_11.isHovered() && !blackKey_12.isHovered())
            whiteKey_11.press(true);
        if (blackKey_12.isHovered())
            blackKey_12.press(true);
        if (whiteKey_13.isHovered() && !blackKey_12.isHovered() && !blackKey_14.isHovered())
            whiteKey_13.press(true);
        if (blackKey_14.isHovered())
            blackKey_14.press(true);
        if (whiteKey_15.isHovered() && !blackKey_14.isHovered() && !blackKey_16.isHovered())
            whiteKey_15.press(true);
        if (blackKey_16.isHovered())
            blackKey_16.press(true);
        if (whiteKey_17.isHovered() && !blackKey_16.isHovered())
            whiteKey_17.press(true);
        if (whiteKey_18.isHovered() && !blackKey_19.isHovered())
            whiteKey_18.press(true);
        if (blackKey_19.isHovered())
            blackKey_19.press(true);
        if (whiteKey_20.isHovered() && !blackKey_19.isHovered() && !blackKey_21.isHovered())
            whiteKey_20.press(true);
        if (blackKey_21.isHovered())
            blackKey_21.press(true);
        if (whiteKey_22.isHovered() && !blackKey_21.isHovered())
            whiteKey_22.press(true);
        if (whiteKey_23.isHovered() && !blackKey_24.isHovered())
            whiteKey_23.press(true);
        if (blackKey_24.isHovered())
            blackKey_24.press(true);
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        restoreAll();
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }
    private void restoreAll() {
        for (KeyButton button : allKeys) {
            if (!button.pressedByClick) continue;
            button.restore();
        }
    }
    private void restoreAllExcept(KeyButton exceptedOne) {
        for (KeyButton button : allKeys) {
            if (exceptedOne == button) continue;
            if (!button.pressedByClick) continue;
            button.restore();
        }
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        double i = this.width;
        if (pMouseX > (i - 186) / 2 + 37 && pMouseX < (i - 186) / 2 + 149 && pMouseY > 111 && pMouseY < 159 && isDragging) {
            if (blackKey_0.isHovered()) {
                blackKey_0.press(true);
                restoreAllExcept(blackKey_0);
            }
            if (whiteKey_1.isHovered() && !blackKey_0.isHovered() && !blackKey_2.isHovered()) {
                whiteKey_1.press(true);
                restoreAllExcept(whiteKey_1);
            }
            if (blackKey_2.isHovered()) {
                blackKey_2.press(true);
                restoreAllExcept(blackKey_2);
            }
            if (whiteKey_3.isHovered() && !blackKey_2.isHovered() && !blackKey_4.isHovered()) {
                whiteKey_3.press(true);
                restoreAllExcept(whiteKey_3);
            }
            if (blackKey_4.isHovered()) {
                blackKey_4.press(true);
                restoreAllExcept(blackKey_4);
            }
            if (whiteKey_5.isHovered() && !blackKey_4.isHovered()) {
                whiteKey_5.press(true);
                restoreAllExcept(whiteKey_5);
            }
            if (whiteKey_6.isHovered() && !blackKey_7.isHovered()) {
                whiteKey_6.press(true);
                restoreAllExcept(whiteKey_6);
            }
            if (blackKey_7.isHovered()) {
                blackKey_7.press(true);
                restoreAllExcept(blackKey_7);
            }
            if (whiteKey_8.isHovered() && !blackKey_7.isHovered() && !blackKey_9.isHovered()) {
                whiteKey_8.press(true);
                restoreAllExcept(whiteKey_8);
            }
            if (blackKey_9.isHovered()) {
                blackKey_9.press(true);
                restoreAllExcept(blackKey_9);
            }
            if (whiteKey_10.isHovered() && !blackKey_9.isHovered()) {
                whiteKey_10.press(true);
                restoreAllExcept(whiteKey_10);
            }
        }
        if (pMouseX > (i - 186) / 2 + 21 && pMouseX < (i - 186) / 2 + 165 && pMouseY > 53 && pMouseY < 101 && isDragging){
            if (whiteKey_11.isHovered() && !blackKey_12.isHovered()) {
                whiteKey_11.press(true);
                restoreAllExcept(whiteKey_11);
            }
            if (blackKey_12.isHovered()) {
                blackKey_12.press(true);
                restoreAllExcept(blackKey_12);
            }
            if (whiteKey_13.isHovered() && !blackKey_12.isHovered() && !blackKey_14.isHovered()) {
                whiteKey_13.press(true);
                restoreAllExcept(whiteKey_13);
            }
            if (blackKey_14.isHovered()) {
                blackKey_14.press(true);
                restoreAllExcept(blackKey_14);
            }
            if (whiteKey_15.isHovered() && !blackKey_14.isHovered() && !blackKey_16.isHovered()) {
                whiteKey_15.press(true);
                restoreAllExcept(whiteKey_15);
            }
            if (blackKey_16.isHovered()) {
                blackKey_16.press(true);
                restoreAllExcept(blackKey_16);
            }
            if (whiteKey_17.isHovered() && !blackKey_16.isHovered()) {
                whiteKey_17.press(true);
                restoreAllExcept(whiteKey_17);
            }
            if (whiteKey_18.isHovered() && !blackKey_19.isHovered()) {
                whiteKey_18.press(true);
                restoreAllExcept(whiteKey_18);
            }
            if (blackKey_19.isHovered()) {
                blackKey_19.press(true);
                restoreAllExcept(blackKey_19);
            }
            if (whiteKey_20.isHovered() && !blackKey_19.isHovered() && !blackKey_21.isHovered()) {
                whiteKey_20.press(true);
                restoreAllExcept(whiteKey_20);
            }
            if (blackKey_21.isHovered()) {
                blackKey_21.press(true);
                restoreAllExcept(blackKey_21);
            }
            if (whiteKey_22.isHovered() && !blackKey_21.isHovered()) {
                whiteKey_22.press(true);
                restoreAllExcept(whiteKey_22);
            }
            if (whiteKey_23.isHovered() && !blackKey_24.isHovered()) {
                whiteKey_23.press(true);
                restoreAllExcept(whiteKey_23);
            }
            if (blackKey_24.isHovered()) {
                blackKey_24.press(true);
                restoreAllExcept(blackKey_24);
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
        if (pKeyCode == GLFW.GLFW_KEY_A) blackKey_0.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_Z) whiteKey_1.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_S) blackKey_2.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_X) whiteKey_3.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_D) blackKey_4.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_C) whiteKey_5.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_V) whiteKey_6.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_G) blackKey_7.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_B) whiteKey_8.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_H) blackKey_9.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_N) whiteKey_10.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_Q) whiteKey_11.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_2) blackKey_12.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_W) whiteKey_13.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_3) blackKey_14.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_E) whiteKey_15.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_4) blackKey_16.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_R) whiteKey_17.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_T) whiteKey_18.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_6) blackKey_19.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_Y) whiteKey_20.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_7) blackKey_21.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_U) whiteKey_22.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_I) whiteKey_23.press(false);
        if (pKeyCode == GLFW.GLFW_KEY_9) blackKey_24.press(false);
        return true;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers){
        super.keyReleased(pKeyCode, pScanCode, pModifiers);
        if (pKeyCode == GLFW.GLFW_KEY_A && !blackKey_0.pressedByClick) blackKey_0.restore();
        if (pKeyCode == GLFW.GLFW_KEY_Z && !whiteKey_1.pressedByClick) whiteKey_1.restore();
        if (pKeyCode == GLFW.GLFW_KEY_S && !blackKey_2.pressedByClick) blackKey_2.restore();
        if (pKeyCode == GLFW.GLFW_KEY_X && !whiteKey_3.pressedByClick) whiteKey_3.restore();
        if (pKeyCode == GLFW.GLFW_KEY_D && !blackKey_4.pressedByClick) blackKey_4.restore();
        if (pKeyCode == GLFW.GLFW_KEY_C && !whiteKey_5.pressedByClick) whiteKey_5.restore();
        if (pKeyCode == GLFW.GLFW_KEY_V && !whiteKey_6.pressedByClick) whiteKey_6.restore();
        if (pKeyCode == GLFW.GLFW_KEY_G && !blackKey_7.pressedByClick) blackKey_7.restore();
        if (pKeyCode == GLFW.GLFW_KEY_B && !whiteKey_8.pressedByClick) whiteKey_8.restore();
        if (pKeyCode == GLFW.GLFW_KEY_H && !blackKey_9.pressedByClick) blackKey_9.restore();
        if (pKeyCode == GLFW.GLFW_KEY_N && !whiteKey_10.pressedByClick) whiteKey_10.restore();
        if (pKeyCode == GLFW.GLFW_KEY_Q && !whiteKey_11.pressedByClick) whiteKey_11.restore();
        if (pKeyCode == GLFW.GLFW_KEY_2 && !blackKey_12.pressedByClick) blackKey_12.restore();
        if (pKeyCode == GLFW.GLFW_KEY_W && !whiteKey_13.pressedByClick) whiteKey_13.restore();
        if (pKeyCode == GLFW.GLFW_KEY_3 && !blackKey_14.pressedByClick) blackKey_14.restore();
        if (pKeyCode == GLFW.GLFW_KEY_E && !whiteKey_15.pressedByClick) whiteKey_15.restore();
        if (pKeyCode == GLFW.GLFW_KEY_4 && !blackKey_16.pressedByClick) blackKey_16.restore();
        if (pKeyCode == GLFW.GLFW_KEY_R && !whiteKey_17.pressedByClick) whiteKey_17.restore();
        if (pKeyCode == GLFW.GLFW_KEY_T && !whiteKey_18.pressedByClick) whiteKey_18.restore();
        if (pKeyCode == GLFW.GLFW_KEY_6 && !blackKey_19.pressedByClick) blackKey_19.restore();
        if (pKeyCode == GLFW.GLFW_KEY_Y && !whiteKey_20.pressedByClick) whiteKey_20.restore();
        if (pKeyCode == GLFW.GLFW_KEY_7 && !blackKey_21.pressedByClick) blackKey_21.restore();
        if (pKeyCode == GLFW.GLFW_KEY_U && !whiteKey_22.pressedByClick) whiteKey_22.restore();
        if (pKeyCode == GLFW.GLFW_KEY_I && !whiteKey_23.pressedByClick) whiteKey_23.restore();
        if (pKeyCode == GLFW.GLFW_KEY_9 && !blackKey_24.pressedByClick) blackKey_24.restore();
        return true;
    }

    @Override
    public void tick() {
        if (!(blackKey_0.isPressed || whiteKey_1.isPressed || blackKey_2.isPressed || whiteKey_3.isPressed || blackKey_4.isPressed || whiteKey_5.isPressed || whiteKey_6.isPressed ||
                blackKey_7.isPressed || whiteKey_8.isPressed || blackKey_9.isPressed || whiteKey_10.isPressed || whiteKey_11.isPressed || blackKey_12.isPressed || whiteKey_13.isPressed ||
                blackKey_14.isPressed || whiteKey_15.isPressed || blackKey_16.isPressed || whiteKey_17.isPressed ||whiteKey_18.isPressed || blackKey_19.isPressed || whiteKey_20.isPressed ||
                blackKey_21.isPressed || whiteKey_22.isPressed || whiteKey_23.isPressed || blackKey_24.isPressed)){
            isPressing = false;
            PacketDistributor.sendToServer(new InstrumentPressingPacket(pPlayer.getId(), false));
        }

        if (pType == InstrumentsType.PIANO_LOW || pType == InstrumentsType.PIANO_HIGH || (pType.ordinal() >= 13 && pType.ordinal() <= 19)) {
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
                if (blackKey_0.isPressed || whiteKey_1.isPressed || blackKey_2.isPressed || whiteKey_3.isPressed || blackKey_4.isPressed || whiteKey_5.isPressed || whiteKey_6.isPressed ||
                        blackKey_7.isPressed || whiteKey_8.isPressed || blackKey_9.isPressed || whiteKey_10.isPressed || whiteKey_11.isPressed) {
                    pPlayer.swing(leftHand);
                }
                if (blackKey_12.isPressed || whiteKey_13.isPressed || blackKey_14.isPressed || whiteKey_15.isPressed || blackKey_16.isPressed || whiteKey_17.isPressed || whiteKey_18.isPressed ||
                        blackKey_19.isPressed || whiteKey_20.isPressed || blackKey_21.isPressed || whiteKey_22.isPressed || whiteKey_23.isPressed || blackKey_24.isPressed) {
                    pPlayer.swing(rightHand);
                }

            } else if (pType.ordinal() >= 3 && pType.ordinal() <= 12) {
                pPlayer.swing(drumstickHand);
            }
        }

        boolean exception = pPlayer.level().getBlockState(pPos).getBlock() instanceof MusicalInstrumentBlock block && block.getType() == InstrumentsType.PIANO_LOW && this.pType == InstrumentsType.PIANO_HIGH;

        if (this.minecraft != null) {
            if (pPos != DEFAULT_POS) {
                if ((!(pPlayer.level().getBlockState(pPos).getBlock() instanceof MusicalInstrumentBlock block) || block.getType() != this.pType) && !exception) {
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

    public static void openPlayingScreen(Player pPlayer, InstrumentsType pType){
        Minecraft.getInstance().setScreen(new PlayingScreen(pPlayer, pType, DEFAULT_POS));
    }
    public static void openPlayingScreen(Player pPlayer, InstrumentsType pType, BlockPos pPos){
        Minecraft.getInstance().setScreen(new PlayingScreen(pPlayer, pType, pPos));
    }
}
