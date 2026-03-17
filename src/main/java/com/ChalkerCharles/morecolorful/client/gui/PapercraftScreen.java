package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.renderer.item.PapercuttingRenderer;
import com.ChalkerCharles.morecolorful.client.texture.PapercuttingTextureManager;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.ChalkerCharles.morecolorful.common.menu.PapercraftMenu;
import com.ChalkerCharles.morecolorful.network.packets.PaperCarvingPacket;
import com.ChalkerCharles.morecolorful.network.packets.SoundPacket;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

@OnlyIn(Dist.CLIENT)
public class PapercraftScreen extends AbstractContainerScreen<PapercraftMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = MoreColorful.location("textures/gui/container/papercraft_table.png");
    private static final ResourceLocation ERROR_SPRITE = MoreColorful.location("papercraft/error");
    private static final ResourceLocation AXIS_ANTIDIAGONAL = MoreColorful.location("papercraft/axis_antidiagonal");
    private static final ResourceLocation AXIS_DIAGONAL = MoreColorful.location("papercraft/axis_diagonal");
    private static final ResourceLocation AXIS_HORIZONTAL = MoreColorful.location("papercraft/axis_horizontal");
    private static final ResourceLocation AXIS_VERTICAL = MoreColorful.location("papercraft/axis_vertical");
    private static final ResourceLocation[] SHADOWS = new ResourceLocation[7];
    private BitSet stencil = new BitSet(256);
    private final List<Pixel> carvedPixels = new ArrayList<>();
    private final UndoRedoStack<BitSet> undoRedoStack = new UndoRedoStack<>(this::onUndoRedo);
    private final SelectingArea selection = new SelectingArea();
    private List<SymmetryButton> buttons = List.of();
    private boolean resultSlotEmpty = true;
    private boolean canCarve;
    private byte symmetries;
    private byte shadows;
    private boolean shouldMakeSound;
    private int ticks;

    public PapercraftScreen(PapercraftMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        pMenu.setSlotUpdateCallback(this::onSlotChange);
        this.titleLabelY -= 2;
    }

    @Override
    protected void init() {
        super.init();
        this.selection.reset();
        int i = this.leftPos, j = this.topPos;
        this.buttons = List.of(
                this.addRenderableWidget(new SymmetryButton(i + 138, j + 15, (byte) 4, (byte) 0b00001111)),
                this.addRenderableWidget(new SymmetryButton(i + 153, j + 15, (byte) 8, (byte) 0b00111100)),
                this.addRenderableWidget(new SymmetryButton(i + 138, j + 30, (byte) 2, (byte) 0b00011110)),
                this.addRenderableWidget(new SymmetryButton(i + 153, j + 30, (byte) 1, (byte) 0b01111000))
        );
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        if (this.canCarve) {
            this.doCarve();
            this.undoRedoStack.checkNewOperation(() -> (BitSet) this.stencil.clone());
        }
        this.selection.render(pGuiGraphics);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        for (SymmetryButton button : this.buttons) {
            button.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
        }
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        int i1 = i + 68, j1 = j + 14;
        if (PapercuttingStencil.isFull(this.stencil)) {
            pGuiGraphics.blitSprite(ERROR_SPRITE, i + 35, j + 31, 28, 21);
        } else {
            ItemStack stack = this.menu.getPapercutting();
            PapercuttingRenderer.renderOnScreen(stack, pGuiGraphics, i1, j1);
        }
        RenderSystem.enableBlend();
        for (int k = 0; k < 7; k++) {
            if ((this.shadows & (1 << k)) != 0) {
                pGuiGraphics.blitSprite(SHADOWS[k], i1, j1, 64, 64);
            }
        }
        RenderSystem.disableBlend();
        if (this.isAntidiagonalSymmetric()) {
            pGuiGraphics.blitSprite(AXIS_DIAGONAL, i1, j1, 64, 64);
        }
        if (this.isDiagonalSymmetric()) {
            pGuiGraphics.blitSprite(AXIS_ANTIDIAGONAL, i1, j1, 64, 64);
        }
        if (this.isHorizontalSymmetric()) {
            pGuiGraphics.blitSprite(AXIS_VERTICAL, i1, j1, 64, 64);
        }
        if (this.isVerticalSymmetric()) {
            pGuiGraphics.blitSprite(AXIS_HORIZONTAL, i1, j1, 64, 64);
        }
    }

    private void doCarve() {
        if (this.carvedPixels.isEmpty()) return;
        this.handleSymmetry(this.isAntidiagonalSymmetric(), Pixel::mirrorAntidiagonal);
        this.handleSymmetry(this.isDiagonalSymmetric(), Pixel::mirrorDiagonal);
        this.handleSymmetry(this.isHorizontalSymmetric(), Pixel::mirrorX);
        this.handleSymmetry(this.isVerticalSymmetric(), Pixel::mirrorY);
        boolean carved = false;
        for (Pixel pixel : this.carvedPixels) {
            int i = pixel.index();
            if (!this.stencil.get(i)) {
                this.stencil.set(i);
                carved = true;
            }
        }
        if (carved) {
            PacketDistributor.sendToServer(new PaperCarvingPacket(PapercuttingStencil.of(this.stencil)));
        }
        if (carved && this.shouldMakeSound) {
            PacketDistributor.sendToServer(new SoundPacket(ModSounds.PAPER_CUT.get(), SoundSource.BLOCKS, this.menu.pos, 1.0F, 1.0F));
            this.shouldMakeSound = false;
        }
        this.carvedPixels.clear();
    }

    private void handleSymmetry(boolean symmetric, UnaryOperator<Pixel> action) {
        if (symmetric) {
            List<Pixel> list = new ArrayList<>(this.carvedPixels.size());
            for (Pixel pixel : this.carvedPixels) {
                list.add(action.apply(pixel));
            }
            this.carvedPixels.addAll(list);
        }
    }

    private void onSlotChange() {
        ItemStack stack = this.menu.getPapercutting();
        boolean flag = stack.isEmpty() || !PapercuttingBlock.isPapercutting(stack.getItem());
        if (this.resultSlotEmpty != flag) {
            this.resultSlotEmpty = flag;
            if (flag) {
                this.canCarve = false;
                this.stencil.clear();
                this.carvedPixels.clear();
                this.undoRedoStack.clear();
            } else {
                this.canCarve = true;
                PapercuttingStencil stencil = stack.getOrDefault(ModDataComponents.PAPERCUTTING_STENCIL, PapercuttingStencil.DEFAULT);
                this.stencil = stencil.toBitSet();
                this.undoRedoStack.setInitialState(stencil.toBitSet());
            }
        }
    }

    private boolean inCarvingArea(double mouseX, double mouseY) {
        int i = Mth.floor(mouseX) - this.leftPos, j = Mth.floor(mouseY) - this.topPos;
        if (i >= 68 && i < 132 && j >= 14 && j < 78) {
            int x = i - 68, y = j - 14;
            for (int k = 0; k < 7; k++) {
                if ((this.shadows & (1 << k)) != 0) {
                    if (inShadow(k, x, y)) return false;
                }
            }
            return true;
        }
        return false;
    }

    private static boolean inShadow(int index, int x, int y) {
        return switch (index) {
            case 0 -> x >= 32 && y < 63 - x;
            case 1 -> y < 32 && y >= 63 - x;
            case 2 -> y >= 32 && y < x;
            case 3 -> x >= 32 && y >= x;
            case 4 -> x < 32 && y >= 63 - x;
            case 5 -> y >= 32 && y < 63 - x;
            case 6 -> y < 32 && y >= x;
            default -> false;
        };
    }

    private void carvePixel(double mouseX, double mouseY) {
        int i = Mth.floor(mouseX) - this.leftPos - 68, j = Mth.floor(mouseY) - this.topPos - 14;
        this.carvedPixels.add(new Pixel(i >> 2, j >> 2));
        this.undoRedoStack.changed();
    }

    private void onUndoRedo(BitSet bitSet) {
        this.stencil = (BitSet) bitSet.clone();
        PacketDistributor.sendToServer(new PaperCarvingPacket(PapercuttingStencil.of(bitSet)));
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (this.canCarve) {
            if (this.inCarvingArea(pMouseX, pMouseY)) {
                switch (pButton) {
                    case 0 -> this.carvePixel(pMouseX, pMouseY);
                    case 1 -> this.selection.startSelect(pMouseX, pMouseY);
                }
            }
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton) {
        if (this.canCarve) {
            if (pButton == 1) {
                int x = Math.min(this.selection.start.x, this.selection.end.x) & -4;
                int y = Math.min(this.selection.start.y, this.selection.end.y) & -4;
                int width = (Math.abs(this.selection.start.x - this.selection.end.x) & -4) + 4;
                int height = (Math.abs(this.selection.start.y - this.selection.end.y) & -4) + 4;
                for (int i = x; i < x + width; i += 4) {
                    for (int j = y; j < y + height; j += 4) {
                        this.carvedPixels.add(new Pixel(i >> 2, j >> 2));
                        this.undoRedoStack.changed();
                    }
                }
                this.selection.reset();
            }
            this.undoRedoStack.endStep();
        }
        return super.mouseReleased(pMouseX, pMouseY, pButton);
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double pDragX, double pDragY) {
        if (this.canCarve) {
            if (this.inCarvingArea(pMouseX, pMouseY)) {
                switch (pButton) {
                    case 0 -> this.carvePixel(pMouseX, pMouseY);
                    case 1 -> this.selection.update(pMouseX, pMouseY);
                }
            }
        }
        return super.mouseDragged(pMouseX, pMouseY, pButton, pDragX, pDragY);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (this.canCarve) {
            return this.undoRedoStack.keyPressed(pKeyCode) || super.keyPressed(pKeyCode, pScanCode, pModifiers);
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    private boolean isAntidiagonalSymmetric() {
        return this.isSymmetric((byte) 1);
    }

    private boolean isDiagonalSymmetric() {
        return this.isSymmetric((byte) 2);
    }

    private boolean isHorizontalSymmetric() {
        return this.isSymmetric((byte) 4);
    }

    private boolean isVerticalSymmetric() {
        return this.isSymmetric((byte) 8);
    }

    private boolean isSymmetric(byte mask) {
        return (this.symmetries & mask) != 0;
    }

    private void toggleSymmetry(byte mask) {
        this.symmetries ^= mask;
        this.shadows = 0;
        for (SymmetryButton button : this.buttons) {
            this.shadows |= button.getShadow();
        }
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        if (this.ticks % 3 == 0) {
            this.shouldMakeSound = true;
        }
        this.ticks++;
    }

    @Override
    public void onClose() {
        super.onClose();
        PapercuttingTextureManager.reset();
    }

    static {
        for (int i = 0; i < 7; i++) {
            SHADOWS[i] = MoreColorful.location("papercraft/shadow_" + i);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class SymmetryButton extends Button {
        private static final ResourceLocation SYMMETRY = MoreColorful.location("papercraft/symmetry");
        private static final ResourceLocation SYMMETRY_HIGHLIGHTED = MoreColorful.location("papercraft/symmetry_highlighted");
        private static final ResourceLocation SYMMETRY_SELECTED = MoreColorful.location("papercraft/symmetry_selected");
        private static final ResourceLocation SYMMETRY_ANTIDIAGONAL = MoreColorful.location("papercraft/symmetry_antidiagonal");
        private static final ResourceLocation SYMMETRY_DIAGONAL = MoreColorful.location("papercraft/symmetry_diagonal");
        private static final ResourceLocation SYMMETRY_HORIZONTAL = MoreColorful.location("papercraft/symmetry_horizontal");
        private static final ResourceLocation SYMMETRY_VERTICAL = MoreColorful.location("papercraft/symmetry_vertical");
        private static final Component ANTIDIAGONAL_TOOLTIP = Component.translatable("morecolorful.gui.symmetry_antidiagonal");
        private static final Component DIAGONAL_TOOLTIP = Component.translatable("morecolorful.gui.symmetry_diagonal");
        private static final Component HORIZONTAL_TOOLTIP = Component.translatable("morecolorful.gui.symmetry_horizontal");
        private static final Component VERTICAL_TOOLTIP = Component.translatable("morecolorful.gui.symmetry_vertical");
        private final byte mask;
        private final byte shadow;

        private SymmetryButton(int x, int y, byte mask, byte shadow) {
            super(x, y, 15, 15, CommonComponents.EMPTY, b -> PapercraftScreen.this.toggleSymmetry(mask), DEFAULT_NARRATION);
            this.mask = mask;
            this.shadow = shadow;
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            ResourceLocation location0;
            if (PapercraftScreen.this.isSymmetric(this.mask)) {
                location0 = SYMMETRY_SELECTED;
            } else {
                location0 = this.isHovered ? SYMMETRY_HIGHLIGHTED : SYMMETRY;
            }
            pGuiGraphics.blitSprite(location0, this.getX(), this.getY(), 15, 15);
            ResourceLocation location1 = switch (this.mask) {
                case 2 -> SYMMETRY_DIAGONAL;
                case 4 -> SYMMETRY_HORIZONTAL;
                case 8 -> SYMMETRY_VERTICAL;
                default -> SYMMETRY_ANTIDIAGONAL;
            };
            pGuiGraphics.blitSprite(location1, this.getX(), this.getY(), 15, 15);
        }

        private void renderTooltip(GuiGraphics guiGraphics, int mouseX, int mouseY) {
            if (this.isHovered) {
                Component component = switch (this.mask) {
                    case 2 -> DIAGONAL_TOOLTIP;
                    case 4 -> HORIZONTAL_TOOLTIP;
                    case 8 -> VERTICAL_TOOLTIP;
                    default -> ANTIDIAGONAL_TOOLTIP;
                };
                guiGraphics.renderTooltip(PapercraftScreen.this.font, List.of(component), Optional.empty(), mouseX, mouseY);
            }
        }

        private byte getShadow() {
            return PapercraftScreen.this.isSymmetric(this.mask) ? this.shadow : 0;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private record Pixel(int x, int y) {
        private Pixel mirrorX() {
            return new Pixel(15 - this.x, this.y);
        }

        private Pixel mirrorY() {
            return new Pixel(this.x, 15 - this.y);
        }

        private Pixel mirrorDiagonal() {
            return new Pixel(15 - this.y, 15 - this.x);
        }

        private Pixel mirrorAntidiagonal() {
            return new Pixel(this.y, this.x);
        }

        private int index() {
            return (this.y << 4) + this.x;
        }
    }

    @OnlyIn(Dist.CLIENT)
    private class SelectingArea {
        private final Vector2i start = new Vector2i();
        private final Vector2i end = new Vector2i();

        private void reset() {
            this.start.zero();
            this.end.zero();
        }

        private boolean isSelecting() {
            return !this.start.equals(this.end);
        }

        private void startSelect(double x, double y) {
            int i = PapercraftScreen.this.leftPos, j = PapercraftScreen.this.topPos;
            int x1 = Mth.floor(x) - i - 68 , y1 = Mth.floor(y) - j - 14;
            this.start.set(x1, y1);
            this.end.set(x1, y1);
        }

        private void update(double x, double y) {
            int i = PapercraftScreen.this.leftPos, j = PapercraftScreen.this.topPos;
            int x1 = Mth.floor(x) - i - 68 , y1 = Mth.floor(y) - j - 14;
            this.end.set(x1, y1);
        }

        private void render(GuiGraphics guiGraphics) {
            if (this.isSelecting()) {
                int i = PapercraftScreen.this.leftPos, j = PapercraftScreen.this.topPos;
                int x = (Math.min(this.start.x, this.end.x) & -4) + i + 68;
                int y = (Math.min(this.start.y, this.end.y) & -4) + j + 14;
                int width = (Math.abs(this.start.x - this.end.x) & -4) + 4;
                int height = (Math.abs(this.start.y - this.end.y) & -4) + 4;
                guiGraphics.renderOutline(x, y, width, height, 0xFF000000);
            }
        }
    }
}
