package com.ChalkerCharles.morecolorful.client.gui;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.menu.EnvelopeMenu;
import com.ChalkerCharles.morecolorful.network.packets.SealMailPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class EnvelopeScreen extends AbstractContainerScreen<EnvelopeMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = MoreColorful.location("textures/gui/container/envelope.png");
    private static final ResourceLocation BUTTON_HIGHLIGHTED_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button_highlighted");
    private static final ResourceLocation BUTTON_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/button");
    private static final ResourceLocation CONFIRM_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/confirm");
    private static final ResourceLocation CANCEL_SPRITE = ResourceLocation.withDefaultNamespace("container/beacon/cancel");
    private static final Component RECIPIENT_HINT = Component.translatable("info.morecolorful.envelope.recipient").withStyle(ChatFormatting.DARK_GRAY);
    private EditBox recipient;
    private final Player player;

    public EnvelopeScreen(EnvelopeMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
        this.player = pPlayerInventory.player;
    }

    @Override
    protected void init() {
        super.init();
        int i = this.leftPos;
        int j = this.topPos;
        this.recipient = new EditBox(this.font, i + 88, j + 25, 76, 12, Component.empty());
        this.recipient.setTextColor(0xFF000000);
        this.recipient.setTextColorUneditable(0xFF000000);
        this.recipient.setBordered(false);
        this.recipient.setMaxLength(50);
        this.recipient.setValue("");
        this.recipient.setHint(RECIPIENT_HINT);
        this.recipient.setTextShadow(false);
        this.addWidget(this.recipient);
        this.addRenderableWidget(new EnvelopeButton(i + 101,  j + 52, CommonComponents.GUI_DONE, CONFIRM_SPRITE) {
            @Override
            public void onPress() {
                PacketDistributor.sendToServer(new SealMailPacket(EnvelopeScreen.this.recipient.getValue()));
                EnvelopeScreen.this.player.closeContainer();
            }
        });
        this.addRenderableWidget(new EnvelopeButton(i + 130,  j + 52, CommonComponents.GUI_CANCEL, CANCEL_SPRITE) {
            @Override
            public void onPress() {
                EnvelopeScreen.this.player.closeContainer();
            }
        });
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.recipient.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int i = this.leftPos;
        int j = this.topPos;
        pGuiGraphics.blit(BACKGROUND_TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void resize(Minecraft pMinecraft, int pWidth, int pHeight) {
        String s = this.recipient.getValue();
        this.init(pMinecraft, pWidth, pHeight);
        this.recipient.setValue(s);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.player.closeContainer();
        }
        return this.recipient.keyPressed(pKeyCode, pScanCode, pModifiers)
                || this.recipient.canConsumeInput()
                || super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    private abstract static class EnvelopeButton extends AbstractButton {
        private final ResourceLocation sprite;

        public EnvelopeButton(int pX, int pY, Component pMessage, ResourceLocation sprite) {
            super(pX, pY, 22, 22, pMessage);
            this.sprite = sprite;
        }

        @Override
        protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
            ResourceLocation location = this.isHoveredOrFocused() ? BUTTON_HIGHLIGHTED_SPRITE : BUTTON_SPRITE;
            pGuiGraphics.blitSprite(location, this.getX(), this.getY(), this.width, this.height);
            pGuiGraphics.blitSprite(this.sprite, this.getX() + 2, this.getY() + 2, 18, 18);
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput pNarrationElementOutput) {
            this.defaultButtonNarrationText(pNarrationElementOutput);
        }
    }
}
