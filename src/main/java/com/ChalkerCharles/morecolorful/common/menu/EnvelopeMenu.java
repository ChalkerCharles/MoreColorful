package com.ChalkerCharles.morecolorful.common.menu;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.MailContent;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class EnvelopeMenu extends AbstractContainerMenu {
    private static final ResourceLocation EMPTY_SLOT_LETTER = MoreColorful.location("item/empty_slot_letter");
    private static final Pair<ResourceLocation, ResourceLocation> LETTER_ICON = Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_LETTER);
    private static final int INV_SLOT_START = 2;
    private static final int INV_SLOT_END = 29;
    private static final int USE_ROW_SLOT_START = 29;
    private static final int USE_ROW_SLOT_END = 38;
    private final ItemStack envelope;
    private final InteractionHand hand;
    private final Container container = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            EnvelopeMenu.this.slotsChanged(this);
            super.setChanged();
        }
    };
    private boolean sealed = false;

    public EnvelopeMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ItemStack.EMPTY, InteractionHand.MAIN_HAND);
    }

    public EnvelopeMenu(int containerId, Inventory inventory, ItemStack envelope, InteractionHand hand) {
        super(ModMenuTypes.ENVELOPE.get(), containerId);
        this.envelope = envelope.copy();
        this.hand = hand;
        this.addSlot(new Slot(this.container, 0, 28, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return isLetter(pStack);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return LETTER_ICON;
            }
        });
        this.addSlot(new Slot(this.container, 1, 46, 35));
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public void sealMail(Player sender, String recipient) {
        if (this.stillValid(sender)) {
            this.sealed = true;
            ItemStack stack = sender.getItemInHand(this.hand);
            int i = stack.getCount();
            ItemStack mail = stack.transmuteCopy(ModItems.MAIL, 1);
            MailContent content = new MailContent(
                    this.container.getItem(0),
                    this.container.getItem(1),
                    sender.getScoreboardName(),
                    recipient
            );
            mail.set(ModDataComponents.MAIL_CONTENT, content);
            stack.shrink(1);
            if (i == 1) {
                sender.setItemInHand(this.hand, mail);
            } else {
                sender.getInventory().placeItemBackInInventory(mail);
            }
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            stack = item.copy();
            if (pIndex != 0 && pIndex != 1) {
                if (isLetter(item)) {
                    if (!this.moveItemStackTo(item, 0, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(item, 1, 2, false)) {
                    return ItemStack.EMPTY;
                } else if (pIndex >= INV_SLOT_START && pIndex < INV_SLOT_END) {
                    if (!this.moveItemStackTo(item, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (pIndex >= USE_ROW_SLOT_START && pIndex < USE_ROW_SLOT_END && !this.moveItemStackTo(item, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(item, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }
            if (item.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            if (item.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, item);
        }
        return stack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return ItemStack.isSameItemSameComponents(pPlayer.getItemInHand(this.hand), this.envelope);
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        if (!pPlayer.level().isClientSide && !this.sealed) {
            this.clearContainer(pPlayer, this.container);
        }
    }

    private static boolean isLetter(ItemStack stack) {
        return stack.is(ModItems.WRITABLE_LETTER) || stack.is(ModItems.LETTER);
    }
}
