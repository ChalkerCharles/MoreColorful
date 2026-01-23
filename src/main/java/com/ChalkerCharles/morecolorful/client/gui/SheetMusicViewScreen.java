package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import java.util.BitSet;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SheetMusicViewScreen extends Screen {
    public static final ResourceLocation SHEET_MUSIC_TEXTURE = MoreColorful.location("textures/gui/sheet_music/sheet.png");
    public static final ResourceLocation SHEET_MUSIC_GRID = MoreColorful.location("textures/gui/sheet_music/grid.png");
    public static final ResourceLocation SHEET_MUSIC_NOTE = MoreColorful.location("textures/gui/sheet_music/note.png");
    private static final String[] PITCHES = new String[] {"F♯", "F", "E", "D♯", "D", "C♯", "C", "B", "A♯", "A", "G♯", "G",
            "F♯", "F", "E", "D♯", "D", "C♯", "C", "B", "A♯", "A", "G♯", "G", "F♯"};
    private final List<BitSet> pages;
    private int currentPage;
    private Component pageMsg = CommonComponents.EMPTY;
    private PageButton forwardButton;
    private PageButton backButton;

    protected SheetMusicViewScreen(Melody melody) {
        super(CommonComponents.EMPTY);
        this.pages = melody.pages().stream().map(Melody::parse).toList();
        this.setPageMsg();
    }

    @Override
    protected void init() {
        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> this.onClose()).bounds(this.width / 2 - 100, 196, 200, 20).build());
        int i = (this.width - 178) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 147, 156, true, b -> this.pageForward(), true));
        this.backButton = this.addRenderableWidget(new PageButton(i + 12, 156, false, b -> this.pageBack(), true));
        this.updateButtonVisibility();
    }

    private int getNumPages() {
        return this.pages.size();
    }

    private void setPageMsg() {
        this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, this.getNumPages());
    }

    protected void pageBack() {
        if (this.currentPage > 0) {
            this.currentPage--;
        }
        this.updateButtonVisibility();
        this.setPageMsg();
    }

    protected void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            this.currentPage++;
        }
        this.updateButtonVisibility();
        this.setPageMsg();
    }

    private void updateButtonVisibility() {
        this.forwardButton.visible = this.currentPage < this.getNumPages() - 1;
        this.backButton.visible = this.currentPage > 0;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        int i = (this.width - 178) / 2;
        int j = this.font.width(this.pageMsg);
        pGuiGraphics.drawString(this.font, this.pageMsg, i - j + 172, 42, 0, false);
        renderPitches(pGuiGraphics, this.font, this.width);
        this.renderNotes(pGuiGraphics);
    }

    private void renderNotes(GuiGraphics guiGraphics) {
        BitSet bitSet = this.pages.get(this.currentPage);
        if (bitSet.isEmpty()) return;
        int j = (this.width - 178) / 2;
        for (int i = 0; i < 800; i++) {
            if (bitSet.get(i)) {
                int x = (i / 25) * 4 + j + 35;
                int y = (i % 25) * 4 + 55;
                guiGraphics.blit(SHEET_MUSIC_NOTE, x, y, 0, 0, 0, 3, 3, 3, 3);
            }
        }
    }

    protected static void renderPitches(GuiGraphics guiGraphics, Font font, int screenWidth) {
        int x = (screenWidth - 178) / 2;
        int y = 53;
        for (int i = 0; i < 25; i++) {
            String s = PITCHES[i];
            boolean left = (i & 1) == 0;
            int j = left ? 11 : 23;
            int k = !left && s.length() == 2 ? 3 : 0;
            guiGraphics.drawString(font, s, x + j - k, y, 0, false);
            y += 4;
        }
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = (this.width - 178) / 2;
        pGuiGraphics.blit(SHEET_MUSIC_TEXTURE, i, 32, 0, 0, 178, 146);
        pGuiGraphics.blit(SHEET_MUSIC_GRID, i, 32, 0, 0, 178, 146);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        } else {
            return switch (pKeyCode) {
                case GLFW.GLFW_KEY_PAGE_UP -> {
                    this.backButton.onPress();
                    yield true;
                }
                case GLFW.GLFW_KEY_PAGE_DOWN -> {
                    this.forwardButton.onPress();
                    yield true;
                }
                default -> false;
            };
        }
    }

    public static void openScreen(Melody melody) {
        Minecraft.getInstance().setScreen(new SheetMusicViewScreen(melody));
    }
}
