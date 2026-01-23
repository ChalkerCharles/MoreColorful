package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.EditableMelody;
import com.ChalkerCharles.morecolorful.common.item.component.Melody;
import com.ChalkerCharles.morecolorful.network.packets.EditSheetMusicPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2i;
import org.lwjgl.glfw.GLFW;

import java.util.*;

@OnlyIn(Dist.CLIENT)
public class SheetMusicEditScreen extends Screen {
    private static final Component EDIT_TITLE_LABEL = Component.translatable("morecolorful.gui.sheet_music.editTitle");
    private static final Component FINALIZE_WARNING_LABEL = Component.translatable("morecolorful.gui.sheet_music.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
    private static final FormattedCharSequence GRAY_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
    public static final ResourceLocation SHEET_MUSIC_NOTE_HIGHLIGHTED = MoreColorful.location("textures/gui/sheet_music/note_highlighted.png");
    private static final BitSet clipboard = new BitSet(800);
    private final Player player;
    private final ItemStack sheet;
    private final InteractionHand hand;
    private boolean isModified;
    private boolean isSigning;
    private int frameTick;
    private int currentPage;
    private String title = "";
    private final TextFieldHelper titleEdit = new TextFieldHelper(
            () -> this.title, s -> this.title = s, this::getClipboard, this::setClipboard, s -> s.length() < 32
    );
    private int lastNote = -1;
    private PageButton forwardButton;
    private PageButton backButton;
    private Button doneButton;
    private Button signButton;
    private Button finalizeButton;
    private Button cancelButton;
    private final List<BitSet> pages = new ArrayList<>();
    private final BitSet selectedNotes = new BitSet(800);
    private final SelectingArea selection = new SelectingArea();
    private Component pageMsg = CommonComponents.EMPTY;
    private final Component ownerText;

    protected SheetMusicEditScreen(Player player, ItemStack sheet, InteractionHand hand) {
        super(CommonComponents.EMPTY);
        this.player = player;
        this.sheet = sheet;
        this.hand = hand;
        EditableMelody melody = sheet.get(ModDataComponents.EDITABLE_MELODY);
        if (melody != null) {
            melody.pages().stream().map(Melody::parse).forEach(this.pages::add);
        }
        if (this.pages.isEmpty()) {
            this.pages.add(new BitSet(800));
        }
        this.setPageMsg();
        this.ownerText = Component.translatable("morecolorful.gui.sheet_music.byAuthor", player.getName()).withStyle(ChatFormatting.DARK_GRAY);
    }

    @Override
    protected void init() {
        if (this.minecraft == null) return;
        this.selection.init(this.width);
        this.signButton = this.addRenderableWidget(Button.builder(Component.translatable("book.signButton"), b -> {
            this.isSigning = true;
            this.updateButtonVisibility();
        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, b -> {
            this.minecraft.setScreen(null);
            this.saveChanges(false);
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        this.finalizeButton = this.addRenderableWidget(Button.builder(Component.translatable("book.finalizeButton"), b -> {
            if (this.isSigning) {
                this.saveChanges(true);
                this.minecraft.setScreen(null);
            }
        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.cancelButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, b -> {
            if (this.isSigning) {
                this.isSigning = false;
            }

            this.updateButtonVisibility();
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        int i = (this.width - 178) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 147, 156, true, b -> this.pageForward(), true));
        this.backButton = this.addRenderableWidget(new PageButton(i + 12, 156, false, b -> this.pageBack(), true));
        this.updateButtonVisibility();
    }

    private void setClipboard(String s) {
        if (this.minecraft != null) {
            TextFieldHelper.setClipboardContents(this.minecraft, s);
        }
    }

    private String getClipboard() {
        return this.minecraft != null ? TextFieldHelper.getClipboardContents(this.minecraft) : "";
    }

    private int getNumPages() {
        return this.pages.size();
    }

    private BitSet getCurrentPage() {
        return this.pages.get(this.currentPage);
    }

    private void setPageMsg() {
        this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, this.getNumPages());
    }

    private void pageBack() {
        if (this.currentPage > 0) {
            this.currentPage--;
        }
        this.onPageChange();
    }

    private void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            this.currentPage++;
        } else {
            this.appendPage();
            if (this.currentPage < this.getNumPages() - 1) {
                this.currentPage++;
            }
        }
        this.onPageChange();
    }

    private void onPageChange() {
        this.updateButtonVisibility();
        this.setPageMsg();
        this.selectedNotes.clear();
        this.lastNote = -1;
    }

    private void updateButtonVisibility() {
        this.backButton.visible = !this.isSigning && this.currentPage > 0;
        this.forwardButton.visible = !this.isSigning;
        this.doneButton.visible = !this.isSigning;
        this.signButton.visible = !this.isSigning;
        this.cancelButton.visible = this.isSigning;
        this.finalizeButton.visible = this.isSigning;
        this.finalizeButton.active = !StringUtil.isBlank(this.title);
    }

    private void eraseEmptyTrailingPages() {
        ListIterator<BitSet> listiterator = this.pages.listIterator(this.pages.size());
        while (listiterator.hasPrevious() && listiterator.previous().isEmpty()) {
            listiterator.remove();
        }
    }

    private void saveChanges(boolean publish) {
        if (this.isModified) {
            this.eraseEmptyTrailingPages();
            this.sheet.set(ModDataComponents.EDITABLE_MELODY, EditableMelody.of(this.pages));
            int i = this.hand == InteractionHand.MAIN_HAND ? this.player.getInventory().selected : 40;
            PacketDistributor.sendToServer(new EditSheetMusicPacket(i, this.pages, publish ? Optional.of(this.title.trim()) : Optional.empty()));
        }
    }

    private void appendPage() {
        this.pages.add(new BitSet(800));
        this.isModified = true;
    }

    @Override
    public void tick() {
        super.tick();
        this.frameTick++;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.setFocused(null);
        int i = (this.width - 178) / 2;
        if (this.isSigning) {
            boolean flag = this.frameTick / 6 % 2 == 0;
            FormattedCharSequence title = FormattedCharSequence.composite(
                    FormattedCharSequence.forward(this.title, Style.EMPTY), flag ? BLACK_CURSOR : GRAY_CURSOR
            );
            int k = this.font.width(EDIT_TITLE_LABEL);
            pGuiGraphics.drawString(this.font, EDIT_TITLE_LABEL, i + 36 + (114 - k) / 2, 58, 0, false);
            int l = this.font.width(title);
            pGuiGraphics.drawString(this.font, title, i + 36 + (114 - l) / 2, 74, 0, false);
            int i1 = this.font.width(this.ownerText);
            pGuiGraphics.drawString(this.font, this.ownerText, i + 36 + (114 - i1) / 2, 84, 0, false);
            pGuiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, i + 36, 106, 114, 0);
        } else {
            int j = this.font.width(this.pageMsg);
            pGuiGraphics.drawString(this.font, this.pageMsg, i - j + 172, 42, 0, false);
            SheetMusicViewScreen.renderPitches(pGuiGraphics, this.font, this.width);
            this.renderNotes(pGuiGraphics);
            this.selection.render(pGuiGraphics);
        }
    }

    private void renderNotes(GuiGraphics guiGraphics) {
        BitSet bitSet = this.getCurrentPage();
        if (bitSet.isEmpty()) return;
        if (this.selection.isSelecting()) {
            this.selectedNotes.clear();
        }
        int j = (this.width - 178) / 2;
        for (int i = 0; i < 800; i++) {
            int x = (i / 25) * 4 + j + 35;
            int y = (i % 25) * 4 + 55;
            if (bitSet.get(i)) {
                if (this.isInSelectingArea(i, x, y) || this.selectedNotes.get(i)) {
                    guiGraphics.blit(SHEET_MUSIC_NOTE_HIGHLIGHTED, x, y, 0, 0, 0, 3, 3, 3, 3);
                } else {
                    guiGraphics.blit(SheetMusicViewScreen.SHEET_MUSIC_NOTE, x, y, 0, 0, 0, 3, 3, 3, 3);
                }
            } else {
                this.selectedNotes.clear(i);
            }
        }
    }

    private boolean isInSelectingArea(int index, int x, int y) {
        if (this.selection.contains(x, y)) {
            this.selectedNotes.set(index);
            return true;
        }
        return false;
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        int i = (this.width - 178) / 2;
        pGuiGraphics.blit(SheetMusicViewScreen.SHEET_MUSIC_TEXTURE, i, 32, 0, 0, 178, 146);
        if (!this.isSigning) {
            pGuiGraphics.blit(SheetMusicViewScreen.SHEET_MUSIC_GRID, i, 32, 0, 0, 178, 146);
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        } else if (this.isSigning) {
            return this.titleKeyPressed(pKeyCode);
        } else {
            return this.sheetKeyPressed(pKeyCode);
        }
    }

    private boolean titleKeyPressed(int keyCode) {
        if (this.minecraft == null) return false;
        switch (keyCode) {
            case GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER:
                if (!this.title.isEmpty()) {
                    this.saveChanges(true);
                    this.minecraft.setScreen(null);
                }
                return true;
            case GLFW.GLFW_KEY_BACKSPACE:
                this.titleEdit.removeCharsFromCursor(-1);
                this.updateButtonVisibility();
                this.isModified = true;
                return true;
            default:
                return false;
        }
    }

    private boolean sheetKeyPressed(int keyCode) {
        if (Screen.isSelectAll(keyCode)) {
            this.selectAll();
            return true;
        } else if (Screen.isCopy(keyCode)) {
            this.copy();
            return true;
        } else if (Screen.isPaste(keyCode)) {
            this.paste();
            return true;
        } else if (Screen.isCut(keyCode)) {
            this.cut();
            return true;
        } else {
            return switch (keyCode) {
                case GLFW.GLFW_KEY_DELETE -> {
                    this.delete();
                    yield true;
                }
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

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (super.charTyped(pCodePoint, pModifiers)) {
            return true;
        } else if (this.isSigning) {
            boolean flag = this.titleEdit.charTyped(pCodePoint);
            if (flag) {
                this.updateButtonVisibility();
                this.isModified = true;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!super.mouseClicked(pMouseX, pMouseY, pButton)) {
            if (!this.isSigning && this.isInGrid(pMouseX, pMouseY)) {
                switch (pButton) {
                    case 0 -> this.setNote(pMouseX, pMouseY, true);
                    case 1 -> {
                        this.selection.startSelect(pMouseX, pMouseY);
                        this.selectNote(pMouseX, pMouseY);
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (!super.mouseReleased(pMouseX, pMouseY, pButton)) {
            if (!this.isSigning) {
                switch (pButton) {
                    case 0 -> this.selectedNotes.clear();
                    case 1 -> this.selection.endSelect(this.width);
                }
            }
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (!super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
            if (!this.isSigning && this.isInGrid(pMouseX, pMouseY)) {
                switch (pButton) {
                    case 0 -> this.setNote(pMouseX, pMouseY, false);
                    case 1 -> this.selection.update(pMouseX, pMouseY);
                }

            }
        }
        return true;
    }

    private boolean isInGrid(double x, double y) {
        int i = (this.width - 178) / 2;
        return x > i + 35 && x < i + 163 && y > 53 && y < 156;
    }

    private void setNote(double x, double y, boolean click) {
        int i = (this.width - 178) / 2;
        int m = Mth.floor((x - i - 35) / 4.0);
        int n = Mth.clamp(Mth.floor((y - 55) / 4.0), 0, 24);
        int index = m * 25 + n;
        if (click || this.lastNote != index) {
            this.getCurrentPage().flip(index);
            this.isModified = true;
            this.lastNote = index;
        }
    }

    private void selectNote(double x, double y) {
        int i = (this.width - 178) / 2;
        int m = Mth.floor((x - i - 35) / 4.0);
        int n = Mth.clamp(Mth.floor((y - 55) / 4.0), 0, 24);
        int index = m * 25 + n;
        if (this.getCurrentPage().get(index)) {
            this.selectedNotes.set(index);
        }
    }

    private void selectAll() {
        this.selectedNotes.or(this.getCurrentPage());
    }

    private void copy() {
        clipboard.clear();
        clipboard.or(this.selectedNotes);
    }

    private void paste() {
        this.getCurrentPage().or(clipboard);
        this.selectedNotes.or(clipboard);
        this.isModified = true;
    }

    private void cut() {
        this.copy();
        this.delete();
    }

    private void delete() {
        this.getCurrentPage().andNot(this.selectedNotes);
        this.selectedNotes.clear();
        this.isModified = true;
    }

    public static void openScreen(Player player, ItemStack stack, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new SheetMusicEditScreen(player, stack, hand));
    }

    private static class SelectingArea {
        private final Vector2i start = new Vector2i();
        private final Vector2i end = new Vector2i();

        private void init(int screenWidth) {
            int i = (screenWidth - 178) / 2;
            this.start.set(i + 35, 53);
            this.end.set(i + 35, 53);
        }

        private boolean isSelecting() {
            return !this.start.equals(this.end);
        }

        private void startSelect(double x, double y) {
            int x1 = Mth.floor(x), y1 = Mth.floor(y);
            this.start.set(x1, y1);
            this.end.set(x1, y1);
        }

        private void update(double x, double y) {
            this.end.set(Mth.floor(x), Mth.floor(y));
        }

        private void endSelect(int screenWidth) {
            int i = (screenWidth - 178) / 2;
            this.start.set(i + 35, 53);
            this.end.set(i + 35, 53);
        }

        private boolean contains(int x, int y) {
            return x >= Math.min(this.start.x, this.end.x)
                    && x <= Math.max(this.start.x, this.end.x)
                    && y >= Math.min(this.start.y, this.end.y)
                    && y <= Math.max(this.start.y, this.end.y);
        }

        private void render(GuiGraphics guiGraphics) {
            if (this.isSelecting()) {
                guiGraphics.renderOutline(
                        Math.min(this.start.x, this.end.x),
                        Math.min(this.start.y, this.end.y),
                        Math.abs(this.start.x - this.end.x),
                        Math.abs(this.start.y - this.end.y),
                        0xFF000000
                );
            }
        }
    }
}
