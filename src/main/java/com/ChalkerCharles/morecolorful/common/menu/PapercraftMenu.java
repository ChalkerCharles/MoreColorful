package com.ChalkerCharles.morecolorful.common.menu;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PapercuttingBlock;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;

import java.util.Objects;

public class PapercraftMenu extends AbstractContainerMenu {
    private static final ResourceLocation EMPTY_SLOT_PAPER = MoreColorful.location("item/empty_slot_paper");
    private static final ResourceLocation EMPTY_SLOT_DYE = MoreColorful.location("item/empty_slot_dye");
    private static final Pair<ResourceLocation, ResourceLocation> PAPER_ICON = Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_PAPER);
    private static final Pair<ResourceLocation, ResourceLocation> DYE_ICON = Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_SLOT_DYE);
    private static final int INV_SLOT_START = 3;
    private static final int INV_SLOT_END = 30;
    private static final int USE_ROW_SLOT_START = 30;
    private static final int USE_ROW_SLOT_END = 39;
    public final BlockPos pos;
    private boolean keepStencil = true;
    private Runnable slotUpdateCallback = () -> {};
    public PapercuttingStencil stencil = PapercuttingStencil.DEFAULT;
    private final ContainerLevelAccess access;
    private final Container container = new SimpleContainer(2) {
        @Override
        public void setChanged() {
            PapercraftMenu.this.slotsChanged(this);
            super.setChanged();
            PapercraftMenu.this.slotUpdateCallback.run();
        }
    };
    private final ResultContainer resultContainer = new ResultContainer();

    public PapercraftMenu(int containerId, Inventory inventory, RegistryFriendlyByteBuf data) {
        this(containerId, inventory, ContainerLevelAccess.NULL, data.readBlockPos());
    }

    public PapercraftMenu(int containerId, Inventory inventory, ContainerLevelAccess access, BlockPos pos) {
        super(ModMenuTypes.PAPERCRAFT.get(), containerId);
        this.access = access;
        this.pos = pos;
        this.addSlot(new Slot(this.container, 0, 15, 15) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Items.PAPER) || PapercuttingBlock.isPapercutting(stack.getItem());
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return PAPER_ICON;
            }
        });
        this.addSlot(new Slot(this.container, 1, 15, 52) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return stack.is(Tags.Items.DYES);
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return DYE_ICON;
            }
        });
        this.addSlot(new Slot(this.resultContainer, 2, 145, 57) {
            @Override
            public boolean mayPlace(ItemStack p_39217_) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                PapercraftMenu.this.slots.getFirst().remove(1);
                PapercraftMenu.this.slots.get(1).remove(1);
                super.onTake(player, stack);
            }
        });
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for (int k = 0; k < 9; k++) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    public void updateStencil(PapercuttingStencil stencil) {
        this.stencil = stencil;
        this.keepStencil = false;
        this.slotsChanged(this.resultContainer);
    }

    public void setSlotUpdateCallback(Runnable callback) {
        this.slotUpdateCallback = callback;
    }

    public ItemStack getPapercutting() {
        return this.resultContainer.getItem(2);
    }

    @Override
    public void slotsChanged(Container pInventory) {
        ItemStack stack = this.container.getItem(0);
        ItemStack stack1 = this.container.getItem(1);
        ItemStack stack2 = this.resultContainer.getItem(2);
        if (!stack.isEmpty()) {
            if (this.stencil.isFull()) {
                this.resultContainer.removeItemNoUpdate(2);
            } else {
                this.setupResultSlot(stack, stack1, stack2);
                this.broadcastChanges();
            }
        } else {
            this.resultContainer.removeItemNoUpdate(2);
            this.stencil = PapercuttingStencil.DEFAULT;
            this.keepStencil = true;
        }
    }

    private void setupResultSlot(ItemStack paper, ItemStack dye, ItemStack result) {
        ItemStack stack = ItemStack.EMPTY;
        if (paper.is(Items.PAPER)) {
            DyeColor color = Objects.requireNonNullElse(DyeColor.getColor(dye), DyeColor.WHITE);
            stack = new ItemStack(PapercuttingBlock.itemByColor(color));
            stack.set(ModDataComponents.PAPERCUTTING_STENCIL, this.stencil);
        } else if (PapercuttingBlock.isPapercutting(paper.getItem())) {
            DyeColor color = DyeColor.getColor(dye);
            if (color == null) {
                stack = paper.copyWithCount(1);
            } else {
                stack = paper.transmuteCopy(PapercuttingBlock.itemByColor(color), 1);
            }
            if (!this.keepStencil) {
                stack.set(ModDataComponents.PAPERCUTTING_STENCIL, this.stencil);
            }
        }
        if (!ItemStack.matches(stack, result)) {
            this.resultContainer.setItem(2, stack);
        }
    }

    @Override
    public boolean canTakeItemForPickAll(ItemStack pStack, Slot pSlot) {
        return pSlot.container != this.resultContainer && super.canTakeItemForPickAll(pStack, pSlot);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            stack = item.copy();
            if (index == 2) {
                if (!this.moveItemStackTo(item, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(item, stack);
            } else if (index != 1 && index != 0) {
                if (item.is(Items.PAPER) || PapercuttingBlock.isPapercutting(item.getItem())) {
                    if (!this.moveItemStackTo(item, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stack.is(Tags.Items.DYES)) {
                    if (!this.moveItemStackTo(item, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= INV_SLOT_START && index < INV_SLOT_END) {
                    if (!this.moveItemStackTo(item, USE_ROW_SLOT_START, USE_ROW_SLOT_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= USE_ROW_SLOT_START && index < USE_ROW_SLOT_END && !this.moveItemStackTo(item, INV_SLOT_START, INV_SLOT_END, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(item, INV_SLOT_START, USE_ROW_SLOT_END, false)) {
                return ItemStack.EMPTY;
            }
            if (item.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            }
            slot.setChanged();
            if (item.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTake(pPlayer, item);
            this.broadcastChanges();
        }
        return stack;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, ModBlocks.PAPERCRAFT_TABLE.get());
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.resultContainer.removeItemNoUpdate(2);
        this.access.execute((level, pos) -> this.clearContainer(pPlayer, this.container));
    }
}
