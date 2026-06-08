package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.network.packets.EditLetterPacket;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.GameNarrator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.network.Filterable;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.WritableBookContent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.apache.commons.lang3.mutable.MutableInt;
import org.joml.Vector2i;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class LetterEditScreen extends Screen {
    private static final Component EDIT_TITLE_LABEL = Component.translatable("morecolorful.gui.letter.editTitle");
    private static final Component FINALIZE_WARNING_LABEL = Component.translatable("morecolorful.gui.letter.finalizeWarning");
    private static final FormattedCharSequence BLACK_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.BLACK));
    private static final FormattedCharSequence GRAY_CURSOR = FormattedCharSequence.forward("_", Style.EMPTY.withColor(ChatFormatting.GRAY));
    private final Player owner;
    private final ItemStack letter;
    private boolean isModified;
    private boolean isSigning;
    private int frameTick;
    private int currentPage;
    private final List<String> pages = Lists.newArrayList();
    private String title = "";
    private final TextFieldHelper pageEdit = new TextFieldHelper(
            this::getCurrentPageText,
            this::setCurrentPageText,
            this::getClipboard,
            this::setClipboard,
            s -> s.length() < 1024 && this.font.wordWrapHeight(s, 114) <= 128
    );
    private final TextFieldHelper titleEdit = new TextFieldHelper(
            () -> this.title, s -> this.title = s, this::getClipboard, this::setClipboard, s -> s.length() < 16
    );
    private long lastClickTime;
    private int lastIndex = -1;
    private PageButton forwardButton;
    private PageButton backButton;
    private Button doneButton;
    private Button signButton;
    private Button finalizeButton;
    private Button cancelButton;
    private final InteractionHand hand;
    @Nullable
    private DisplayCache displayCache = DisplayCache.EMPTY;
    private Component pageMsg = CommonComponents.EMPTY;
    private final Component ownerText;
    
    protected LetterEditScreen(Player owner, ItemStack letter, InteractionHand hand) {
        super(GameNarrator.NO_TITLE);
        this.owner = owner;
        this.letter = letter;
        this.hand = hand;
        WritableBookContent content = letter.get(DataComponents.WRITABLE_BOOK_CONTENT);
        if (content != null) {
            content.getPages(Minecraft.getInstance().isTextFilteringEnabled()).forEach(this.pages::add);
        }
        if (this.pages.isEmpty()) {
            this.pages.add("");
        }
        this.ownerText = Component.translatable("book.byAuthor", owner.getName()).withStyle(ChatFormatting.DARK_GRAY);
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

    @Override
    public void tick() {
        super.tick();
        this.frameTick++;
    }

    @Override
    protected void init() {
        if (this.minecraft == null) return;
        this.clearDisplayCache();
        this.signButton = this.addRenderableWidget(Button.builder(Component.translatable("book.signButton"), p_98177_ -> {
            this.isSigning = true;
            this.updateButtonVisibility();
        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.doneButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, p_280851_ -> {
            this.minecraft.setScreen(null);
            this.saveChanges(false);
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        this.finalizeButton = this.addRenderableWidget(Button.builder(Component.translatable("book.finalizeButton"), p_280852_ -> {
            if (this.isSigning) {
                this.saveChanges(true);
                this.minecraft.setScreen(null);
            }
        }).bounds(this.width / 2 - 100, 196, 98, 20).build());
        this.cancelButton = this.addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, p_98157_ -> {
            if (this.isSigning) {
                this.isSigning = false;
            }

            this.updateButtonVisibility();
        }).bounds(this.width / 2 + 2, 196, 98, 20).build());
        int i = (this.width - 192) / 2;
        this.forwardButton = this.addRenderableWidget(new PageButton(i + 116, 159, true, p_98144_ -> this.pageForward(), true));
        this.backButton = this.addRenderableWidget(new PageButton(i + 43, 159, false, p_98113_ -> this.pageBack(), true));
        this.updateButtonVisibility();
    }

    private void pageBack() {
        if (this.currentPage > 0) {
            this.currentPage--;
        }

        this.updateButtonVisibility();
        this.clearDisplayCacheAfterPageChange();
    }

    private void pageForward() {
        if (this.currentPage < this.getNumPages() - 1) {
            this.currentPage++;
        } else {
            this.appendPageToBook();
            if (this.currentPage < this.getNumPages() - 1) {
                this.currentPage++;
            }
        }

        this.updateButtonVisibility();
        this.clearDisplayCacheAfterPageChange();
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
        ListIterator<String> iterator = this.pages.listIterator(this.pages.size());

        while (iterator.hasPrevious() && iterator.previous().isEmpty()) {
            iterator.remove();
        }
    }

    private void saveChanges(boolean pPublish) {
        if (this.isModified) {
            this.eraseEmptyTrailingPages();
            this.updateLocalCopy();
            int i = this.hand == InteractionHand.MAIN_HAND ? this.owner.getInventory().selected : 40;
            PacketDistributor.sendToServer(new EditLetterPacket(i, this.pages, pPublish ? Optional.of(this.title.trim()) : Optional.empty()));
        }
    }

    private void updateLocalCopy() {
        this.letter.set(DataComponents.WRITABLE_BOOK_CONTENT, new WritableBookContent(this.pages.stream().map(Filterable::passThrough).toList()));
    }

    private void appendPageToBook() {
        if (this.getNumPages() < 5) {
            this.pages.add("");
            this.isModified = true;
        }
    }
    
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (super.keyPressed(pKeyCode, pScanCode, pModifiers)) {
            return true;
        } else if (this.isSigning) {
            return this.titleKeyPressed(pKeyCode);
        } else {
            boolean flag = this.bookKeyPressed(pKeyCode);
            if (flag) {
                this.clearDisplayCache();
                return true;
            } else {
                return false;
            }
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
        } else if (StringUtil.isAllowedChatCharacter(pCodePoint)) {
            this.pageEdit.insertText(Character.toString(pCodePoint));
            this.clearDisplayCache();
            return true;
        } else {
            return false;
        }
    }
    
    private boolean bookKeyPressed(int pKeyCode) {
        if (Screen.isSelectAll(pKeyCode)) {
            this.pageEdit.selectAll();
            return true;
        } else if (Screen.isCopy(pKeyCode)) {
            this.pageEdit.copy();
            return true;
        } else if (Screen.isPaste(pKeyCode)) {
            this.pageEdit.paste();
            return true;
        } else if (Screen.isCut(pKeyCode)) {
            this.pageEdit.cut();
            return true;
        } else {
            TextFieldHelper.CursorStep cursorStep = Screen.hasControlDown()
                    ? TextFieldHelper.CursorStep.WORD
                    : TextFieldHelper.CursorStep.CHARACTER;
            return switch (pKeyCode) {
                case 257, 335 -> {
                    this.pageEdit.insertText("\n");
                    yield true;
                }
                case 259 -> {
                    this.pageEdit.removeFromCursor(-1, cursorStep);
                    yield true;
                }
                case 261 -> {
                    this.pageEdit.removeFromCursor(1, cursorStep);
                    yield true;
                }
                case 262 -> {
                    this.pageEdit.moveBy(1, Screen.hasShiftDown(), cursorStep);
                    yield true;
                }
                case 263 -> {
                    this.pageEdit.moveBy(-1, Screen.hasShiftDown(), cursorStep);
                    yield true;
                }
                case 264 -> {
                    this.keyDown();
                    yield true;
                }
                case 265 -> {
                    this.keyUp();
                    yield true;
                }
                case 266 -> {
                    this.backButton.onPress();
                    yield true;
                }
                case 267 -> {
                    this.forwardButton.onPress();
                    yield true;
                }
                case 268 -> {
                    this.keyHome();
                    yield true;
                }
                case 269 -> {
                    this.keyEnd();
                    yield true;
                }
                default -> false;
            };
        }
    }

    private void keyUp() {
        this.changeLine(-1);
    }

    private void keyDown() {
        this.changeLine(1);
    }

    private void changeLine(int pYChange) {
        int i = this.pageEdit.getCursorPos();
        int j = this.getDisplayCache().changeLine(i, pYChange);
        this.pageEdit.setCursorPos(j, Screen.hasShiftDown());
    }

    private void keyHome() {
        if (Screen.hasControlDown()) {
            this.pageEdit.setCursorToStart(Screen.hasShiftDown());
        } else {
            int i = this.pageEdit.getCursorPos();
            int j = this.getDisplayCache().findLineStart(i);
            this.pageEdit.setCursorPos(j, Screen.hasShiftDown());
        }
    }

    private void keyEnd() {
        if (Screen.hasControlDown()) {
            this.pageEdit.setCursorToEnd(Screen.hasShiftDown());
        } else {
            DisplayCache displaycache = this.getDisplayCache();
            int i = this.pageEdit.getCursorPos();
            int j = displaycache.findLineEnd(i);
            this.pageEdit.setCursorPos(j, Screen.hasShiftDown());
        }
    }
    
    private boolean titleKeyPressed(int pKeyCode) {
        switch (pKeyCode) {
            case 257:
            case 335:
                if (!this.title.isEmpty()) {
                    this.saveChanges(true);
                    if (this.minecraft != null) {
                        this.minecraft.setScreen(null);
                    }
                }

                return true;
            case 259:
                this.titleEdit.removeCharsFromCursor(-1);
                this.updateButtonVisibility();
                this.isModified = true;
                return true;
            default:
                return false;
        }
    }

    private String getCurrentPageText() {
        return this.currentPage >= 0 && this.currentPage < this.pages.size() ? this.pages.get(this.currentPage) : "";
    }

    private void setCurrentPageText(String s) {
        if (this.currentPage >= 0 && this.currentPage < this.pages.size()) {
            this.pages.set(this.currentPage, s);
            this.isModified = true;
            this.clearDisplayCache();
        }
    }
    
    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.setFocused(null);
        int i = (this.width - 192) / 2;
        if (this.isSigning) {
            boolean flag = this.frameTick / 6 % 2 == 0;
            FormattedCharSequence charSequence = FormattedCharSequence.composite(
                    FormattedCharSequence.forward(this.title, Style.EMPTY), flag ? BLACK_CURSOR : GRAY_CURSOR
            );
            int k = this.font.width(EDIT_TITLE_LABEL);
            pGuiGraphics.drawString(this.font, EDIT_TITLE_LABEL, i + 36 + (114 - k) / 2, 34, 0, false);
            int l = this.font.width(charSequence);
            pGuiGraphics.drawString(this.font, charSequence, i + 36 + (114 - l) / 2, 50, 0, false);
            int i1 = this.font.width(this.ownerText);
            pGuiGraphics.drawString(this.font, this.ownerText, i + 36 + (114 - i1) / 2, 60, 0, false);
            pGuiGraphics.drawWordWrap(this.font, FINALIZE_WARNING_LABEL, i + 36, 82, 114, 0);
        } else {
            int j1 = this.font.width(this.pageMsg);
            pGuiGraphics.drawString(this.font, this.pageMsg, i - j1 + 192 - 44, 18, 0, false);
            DisplayCache displayCache = this.getDisplayCache();

            for (LineInfo lineInfo : displayCache.lines) {
                pGuiGraphics.drawString(this.font, lineInfo.asComponent, lineInfo.x, lineInfo.y, -16777216, false);
            }

            this.renderHighlight(pGuiGraphics, displayCache.selection);
            this.renderCursor(pGuiGraphics, displayCache.cursor, displayCache.cursorAtEnd);
        }
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderTransparentBackground(pGuiGraphics);
        pGuiGraphics.blit(LetterViewScreen.LETTER_TEXTURE, (this.width - 192) / 2, 2, 0, 0, 192, 192);
    }

    private void renderCursor(GuiGraphics pGuiGraphics, Vector2i pCursorPos, boolean pIsEndOfText) {
        if (this.frameTick / 6 % 2 == 0) {
            pCursorPos = this.convertLocalToScreen(pCursorPos);
            if (!pIsEndOfText) {
                pGuiGraphics.fill(pCursorPos.x, pCursorPos.y - 1, pCursorPos.x + 1, pCursorPos.y + 9, -16777216);
            } else {
                pGuiGraphics.drawString(this.font, "_", pCursorPos.x, pCursorPos.y, 0, false);
            }
        }
    }

    private void renderHighlight(GuiGraphics pGuiGraphics, Rect2i[] pHighlightAreas) {
        for (Rect2i rect2i : pHighlightAreas) {
            int i = rect2i.getX();
            int j = rect2i.getY();
            int k = i + rect2i.getWidth();
            int l = j + rect2i.getHeight();
            pGuiGraphics.fill(RenderType.guiTextHighlight(), i, j, k, l, -16776961);
        }
    }

    private Vector2i convertScreenToLocal(Vector2i pScreenPos) {
        return new Vector2i(pScreenPos.x - (this.width - 192) / 2 - 36, pScreenPos.y - 32);
    }

    private Vector2i convertLocalToScreen(Vector2i pLocalScreenPos) {
        return new Vector2i(pLocalScreenPos.x + (this.width - 192) / 2 + 36, pLocalScreenPos.y + 32);
    }
    
    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!super.mouseClicked(pMouseX, pMouseY, pButton)) {
            if (pButton == 0) {
                long i = Util.getMillis();
                DisplayCache displayCache = this.getDisplayCache();
                int j = displayCache.getIndexAtPosition(
                        this.font, this.convertScreenToLocal(new Vector2i((int) pMouseX, (int) pMouseY))
                );
                if (j >= 0) {
                    if (j != this.lastIndex || i - this.lastClickTime >= 250L) {
                        this.pageEdit.setCursorPos(j, Screen.hasShiftDown());
                    } else if (!this.pageEdit.isSelecting()) {
                        this.selectWord(j);
                    } else {
                        this.pageEdit.selectAll();
                    }

                    this.clearDisplayCache();
                }

                this.lastIndex = j;
                this.lastClickTime = i;
            }

        }
        return true;
    }

    private void selectWord(int pIndex) {
        String s = this.getCurrentPageText();
        this.pageEdit.setSelectionRange(StringSplitter.getWordPosition(s, -1, pIndex, false), StringSplitter.getWordPosition(s, 1, pIndex, false));
    }
    
    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (!super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY)) {
            if (pButton == 0) {
                DisplayCache displaycache = this.getDisplayCache();
                int i = displaycache.getIndexAtPosition(
                        this.font, this.convertScreenToLocal(new Vector2i((int) pMouseX, (int) pMouseY))
                );
                this.pageEdit.setCursorPos(i, true);
                this.clearDisplayCache();
            }

        }
        return true;
    }

    private DisplayCache getDisplayCache() {
        if (this.displayCache == null) {
            this.displayCache = this.rebuildDisplayCache();
            this.pageMsg = Component.translatable("book.pageIndicator", this.currentPage + 1, this.getNumPages());
        }

        return this.displayCache;
    }

    private void clearDisplayCache() {
        this.displayCache = null;
    }

    private void clearDisplayCacheAfterPageChange() {
        this.pageEdit.setCursorToEnd();
        this.clearDisplayCache();
    }

    private DisplayCache rebuildDisplayCache() {
        String s = this.getCurrentPageText();
        if (s.isEmpty()) {
            return DisplayCache.EMPTY;
        } else {
            int i = this.pageEdit.getCursorPos();
            int j = this.pageEdit.getSelectionPos();
            IntList intlist = new IntArrayList();
            List<LineInfo> list = Lists.newArrayList();
            MutableInt mutableint = new MutableInt();
            MutableBoolean mutableboolean = new MutableBoolean();
            StringSplitter stringsplitter = this.font.getSplitter();
            stringsplitter.splitLines(s, 114, Style.EMPTY, true, (style, currentPos, contentWith) -> {
                int k3 = mutableint.getAndIncrement();
                String s2 = s.substring(currentPos, contentWith);
                mutableboolean.setValue(s2.endsWith("\n"));
                String s3 = StringUtils.stripEnd(s2, " \n");
                int l3 = k3 * 9;
                Vector2i pos = this.convertLocalToScreen(new Vector2i(0, l3));
                intlist.add(currentPos);
                list.add(new LineInfo(style, s3, pos.x, pos.y));
            });
            int[] array = intlist.toIntArray();
            boolean flag = i == s.length();
            Vector2i vec;
            if (flag && mutableboolean.isTrue()) {
                vec = new Vector2i(0, list.size() * 9);
            } else {
                int k = findLineFromPos(array, i);
                int l = this.font.width(s.substring(array[k], i));
                vec = new Vector2i(l, k * 9);
            }

            List<Rect2i> list1 = Lists.newArrayList();
            if (i != j) {
                int l2 = Math.min(i, j);
                int i1 = Math.max(i, j);
                int j1 = findLineFromPos(array, l2);
                int k1 = findLineFromPos(array, i1);
                if (j1 == k1) {
                    int l1 = j1 * 9;
                    int i2 = array[j1];
                    list1.add(this.createPartialLineSelection(s, stringsplitter, l2, i1, l1, i2));
                } else {
                    int i3 = j1 + 1 > array.length ? s.length() : array[j1 + 1];
                    list1.add(this.createPartialLineSelection(s, stringsplitter, l2, i3, j1 * 9, array[j1]));

                    for (int j3 = j1 + 1; j3 < k1; j3++) {
                        int j2 = j3 * 9;
                        String s1 = s.substring(array[j3], array[j3 + 1]);
                        int k2 = (int)stringsplitter.stringWidth(s1);
                        list1.add(this.createSelection(new Vector2i(0, j2), new Vector2i(k2, j2 + 9)));
                    }

                    list1.add(this.createPartialLineSelection(s, stringsplitter, array[k1], i1, k1 * 9, array[k1]));
                }
            }

            return new DisplayCache(
                    s, vec, flag, array, list.toArray(new LineInfo[0]), list1.toArray(new Rect2i[0])
            );
        }
    }

    static int findLineFromPos(int[] pLineStarts, int pFind) {
        int i = Arrays.binarySearch(pLineStarts, pFind);
        return i < 0 ? -(i + 2) : i;
    }

    private Rect2i createPartialLineSelection(String pInput, StringSplitter pSplitter, int pStartPos, int pEndPos, int pY, int pLineStart) {
        String s = pInput.substring(pLineStart, pStartPos);
        String s1 = pInput.substring(pLineStart, pEndPos);
        Vector2i vec = new Vector2i((int)pSplitter.stringWidth(s), pY);
        Vector2i vec1 = new Vector2i((int)pSplitter.stringWidth(s1), pY + 9);
        return this.createSelection(vec, vec1);
    }

    private Rect2i createSelection(Vector2i pCorner1, Vector2i pCorner2) {
        Vector2i vec = this.convertLocalToScreen(pCorner1);
        Vector2i vec1 = this.convertLocalToScreen(pCorner2);
        int i = Math.min(vec.x, vec1.x);
        int j = Math.max(vec.x, vec1.x);
        int k = Math.min(vec.y, vec1.y);
        int l = Math.max(vec.y, vec1.y);
        return new Rect2i(i, k, j - i, l - k);
    }

    public static void open(Player player, ItemStack letter, InteractionHand hand) {
        Minecraft.getInstance().setScreen(new LetterEditScreen(player, letter, hand));
    }

    @OnlyIn(Dist.CLIENT)
    private record DisplayCache(String fullText, Vector2i cursor, boolean cursorAtEnd, int[] lineStarts, LineInfo[] lines, Rect2i[] selection) {
        private static final DisplayCache EMPTY = new DisplayCache(
                "",
                new Vector2i(),
                true,
                new int[]{0},
                new LineInfo[]{new LineInfo(Style.EMPTY, "", 0, 0)},
                new Rect2i[0]
        );

        public int getIndexAtPosition(Font pFont, Vector2i pCursorPosition) {
            int i = pCursorPosition.y / 9;
            if (i < 0) {
                return 0;
            } else if (i >= this.lines.length) {
                return this.fullText.length();
            } else {
                LineInfo lineInfo = this.lines[i];
                return this.lineStarts[i]
                        + pFont.getSplitter().plainIndexAtWidth(lineInfo.contents, pCursorPosition.x, lineInfo.style);
            }
        }

        public int changeLine(int pXChange, int pYChange) {
            int i = findLineFromPos(this.lineStarts, pXChange);
            int j = i + pYChange;
            int k;
            if (0 <= j && j < this.lineStarts.length) {
                int l = pXChange - this.lineStarts[i];
                int i1 = this.lines[j].contents.length();
                k = this.lineStarts[j] + Math.min(l, i1);
            } else {
                k = pXChange;
            }

            return k;
        }

        public int findLineStart(int pLine) {
            int i = findLineFromPos(this.lineStarts, pLine);
            return this.lineStarts[i];
        }

        public int findLineEnd(int pLine) {
            int i = findLineFromPos(this.lineStarts, pLine);
            return this.lineStarts[i] + this.lines[i].contents.length();
        }
    }

    @OnlyIn(Dist.CLIENT)
    private record LineInfo(Style style, String contents, Component asComponent, int x, int y) {
        private LineInfo(Style style, String contents, int x, int y) {
            this(style, contents, Component.literal(contents).setStyle(style), x, y);
        }
    }
}
