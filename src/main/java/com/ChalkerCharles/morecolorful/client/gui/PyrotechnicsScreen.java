package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.menu.PyrotechnicsMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CyclingSlotBackground;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PyrotechnicsScreen extends AbstractContainerScreen<PyrotechnicsMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = MoreColorful.location("textures/gui/container/pyrotechnics_table.png");
    private static final ResourceLocation EMPTY_SLOT = MoreColorful.location("item/empty");
    private static final ResourceLocation EMPTY_SLOT_FIREWORK_SHAPE_TEMPLATE = MoreColorful.location("item/empty_slot_firework_shape_template");
    private static final ResourceLocation EMPTY_SLOT_FIREWORK_STAR = MoreColorful.location("item/empty_slot_firework_star");
    private static final ResourceLocation EMPTY_SLOT_GLOWSTONE_DUST = MoreColorful.location("item/empty_slot_glowstone_dust");
    private static final ResourceLocation EMPTY_SLOT_GUNPOWDER = MoreColorful.location("item/empty_slot_gunpowder");
    private static final ResourceLocation EMPTY_SLOT_PAPER = MoreColorful.location("item/empty_slot_paper");
    private static final ResourceLocation EMPTY_SLOT_DIAMOND = ResourceLocation.withDefaultNamespace("item/empty_slot_diamond");
    private static final List<ResourceLocation> FIRST_SLOT_ICONS = List.of(
            EMPTY_SLOT_GUNPOWDER, EMPTY_SLOT_FIREWORK_STAR, EMPTY_SLOT_GUNPOWDER
    );
    private static final List<ResourceLocation> SECOND_SLOT_ICONS = List.of(
            EMPTY_SLOT_DIAMOND, EMPTY_SLOT, EMPTY_SLOT_GUNPOWDER
    );
    private static final List<ResourceLocation> THIRD_SLOT_ICONS = List.of(
            EMPTY_SLOT_GLOWSTONE_DUST, EMPTY_SLOT, EMPTY_SLOT_GUNPOWDER
    );
    private static final List<ResourceLocation> PAPER_SLOT_ICONS = List.of(
            EMPTY_SLOT_FIREWORK_SHAPE_TEMPLATE, EMPTY_SLOT, EMPTY_SLOT_PAPER
    );
    private final CyclingSlotBackground firstIcon = new CyclingSlotBackground(0);
    private final CyclingSlotBackground secondIcon = new CyclingSlotBackground(1);
    private final CyclingSlotBackground thirdIcon = new CyclingSlotBackground(2);
    private final CyclingSlotBackground paperIcon = new CyclingSlotBackground(3);

    public PyrotechnicsScreen(PyrotechnicsMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.firstIcon.tick(FIRST_SLOT_ICONS);
        this.secondIcon.tick(SECOND_SLOT_ICONS);
        this.thirdIcon.tick(THIRD_SLOT_ICONS);
        this.paperIcon.tick(PAPER_SLOT_ICONS);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
        this.firstIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
        this.secondIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
        this.thirdIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
        this.paperIcon.render(this.menu, pGuiGraphics, pPartialTick, this.leftPos, this.topPos);
    }
}
