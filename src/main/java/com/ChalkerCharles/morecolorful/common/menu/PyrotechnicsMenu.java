package com.ChalkerCharles.morecolorful.common.menu;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.misc.FireworkShapeTemplateItem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PyrotechnicsMenu extends AbstractContainerMenu {
    private static final int INV_SLOT_START = 13;
    private static final int INV_SLOT_END = 40;
    private static final int USE_ROW_SLOT_START = 40;
    private static final int USE_ROW_SLOT_END = 49;
    private final ContainerLevelAccess access;
    private Mode mode = Mode.NONE;
    public final Slot firstSlot;
    public final Slot secondSlot;
    public final Slot thirdSlot;
    public final Slot paperSlot;
    public final Slot[] materialSlots = new Slot[8];
    public final Slot resultSlot;
    private long lastSoundTime;
    private final Container inputContainer = new SimpleContainer(12) {
        @Override
        public void setChanged() {
            super.setChanged();
            PyrotechnicsMenu.this.slotsChanged(this);
        }
    };
    private final Container outputContainer = new SimpleContainer(1);

    public PyrotechnicsMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, ContainerLevelAccess.NULL);
    }

    public PyrotechnicsMenu(int containerId, Inventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.PYROTECHNICS.get(), containerId);
        this.access = access;
        this.firstSlot = this.addSlot(new Slot(this.inputContainer, 0, 21, 17) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return isGunpowder(pStack) || isFireworkStar(pStack);
            }
        });
        this.secondSlot = this.addSlot(new Slot(this.inputContainer, 1, 39, 17) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return isGunpowder(pStack) || trailIngredient(pStack);
            }
        });
        this.thirdSlot = this.addSlot(new Slot(this.inputContainer, 2, 57, 17) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return isGunpowder(pStack) || twinkleIngredient(pStack);
            }
        });
        this.paperSlot = this.addSlot(new Slot(this.inputContainer, 3, 75, 17) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return paperSlotAcceptable(pStack);
            }
        });
        for (int i = 0; i < 8; i++) {
            int slotId = i + 4;
            int j = i & 3;
            int x = 21 + 18 * j;
            int y = i > 3 ? 53 : 35;
            this.materialSlots[i] = this.addSlot(new Slot(this.inputContainer, slotId, x, y) {
                @Override
                public boolean mayPlace(ItemStack pStack) {
                    return isFireworkStar(pStack) || isDye(pStack);
                }
            });
        }
        this.resultSlot = this.addSlot(new Slot(this.outputContainer, 0, 133, 35) {
            @Override
            public boolean mayPlace(ItemStack pStack) {
                return false;
            }

            @Override
            public void onTake(Player pPlayer, ItemStack pStack) {
                PyrotechnicsMenu.this.removeMaterial();
                access.execute(PyrotechnicsMenu.this::playSound);
                super.onTake(pPlayer, pStack);
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

    private void removeMaterial() {
        if (this.mode == Mode.NONE) return;
        if (this.mode != Mode.FIREWORK_STAR) {
            this.paperSlot.remove(1);
        }
        this.firstSlot.remove(1);
        this.secondSlot.remove(1);
        this.thirdSlot.remove(1);
        for (Slot slot : this.materialSlots) {
            slot.remove(1);
        }
    }

    private void playSound(Level level, BlockPos pos) {
        long l = level.getGameTime();
        if (this.lastSoundTime != l) {
            level.playSound(null, pos, ModSounds.PYROTECHNICS_TABLE_USE.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
            this.lastSoundTime = l;
        }
    }

    private void changeMode() {
        ItemStack first = this.firstSlot.getItem();
        ItemStack second = this.secondSlot.getItem();
        ItemStack third = this.thirdSlot.getItem();
        ItemStack paper = this.paperSlot.getItem();
        List<ItemStack> list = Arrays.stream(this.materialSlots).filter(Slot::hasItem).map(Slot::getItem).toList();
        if (isGunpowder(first)
                && (trailIngredient(second) || second.isEmpty())
                && (twinkleIngredient(third) || third.isEmpty())
                && (isFireworkShapeTemplate(paper) || paper.isEmpty())
                && !list.isEmpty()
                && list.stream().allMatch(PyrotechnicsMenu::isDye)) {
            this.mode = Mode.FIREWORK_STAR;
        } else if (isFireworkStar(first)
                && second.isEmpty()
                && third.isEmpty()
                && paper.isEmpty()
                && !list.isEmpty()
                && list.stream().allMatch(PyrotechnicsMenu::isDye)) {
            this.mode = Mode.FIREWORK_STAR_FADE;
        } else if ((isGunpowder(first) || first.isEmpty())
                && (isGunpowder(second) || second.isEmpty())
                && (isGunpowder(third) || third.isEmpty())
                && !(first.isEmpty() && second.isEmpty() && third.isEmpty())
                && isPaper(paper)
                && list.stream().allMatch(PyrotechnicsMenu::isFireworkStar)) {
            this.mode = Mode.FIREWORK_ROCKET;
        } else {
            this.mode = Mode.NONE;
        }
    }

    @Override
    public void slotsChanged(Container container) {
        this.changeMode();
        if (this.mode == Mode.NONE) {
            this.resultSlot.set(ItemStack.EMPTY);
        } else {
            this.setupResultSlot();
            this.broadcastChanges();
        }
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack item = slot.getItem();
            stack = item.copy();
            if (index == this.resultSlot.index) {
                if (!this.moveItemStackTo(item, INV_SLOT_START, USE_ROW_SLOT_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(item, stack);
            } else if (this.notInputSlots(index)) {
                if (paperSlotAcceptable(item)) {
                    if (!this.moveItemStackTo(item, this.paperSlot.index, this.paperSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isDye(item)) {
                    if (!this.moveItemStackTo(item, this.materialSlots[0].index, this.materialSlots[7].index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (trailIngredient(item)) {
                    if (!this.moveItemStackTo(item, this.secondSlot.index, this.secondSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (twinkleIngredient(item)) {
                    if (!this.moveItemStackTo(item, this.thirdSlot.index, this.thirdSlot.index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isFireworkStar(item)) {
                    if (!this.moveItemStackTo(item, this.firstSlot.index, this.firstSlot.index + 1, false)
                            && !this.moveItemStackTo(item, this.materialSlots[0].index, this.materialSlots[7].index + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (isGunpowder(item)) {
                    if (!this.moveItemStackTo(item, this.firstSlot.index, this.firstSlot.index + 1, false)
                            && !this.moveItemStackTo(item, this.secondSlot.index, this.secondSlot.index + 1, false)
                            && !this.moveItemStackTo(item, this.thirdSlot.index, this.thirdSlot.index + 1, false)) {
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

    private boolean notInputSlots(int index) {
        return index != this.firstSlot.index
                && index != this.secondSlot.index
                && index != this.thirdSlot.index
                && index != this.paperSlot.index
                && Arrays.stream(this.materialSlots).noneMatch(s -> index == s.index);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, ModBlocks.PYROTECHNICS_TABLE.get());
    }

    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.access.execute((level, pos) -> this.clearContainer(pPlayer, this.inputContainer));
    }

    private void setupResultSlot() {
        ItemStack stack = ItemStack.EMPTY;
        switch (this.mode) {
            case FIREWORK_STAR -> {
                stack = new ItemStack(Items.FIREWORK_STAR);
                FireworkExplosion explosion = this.getExplosionEffect();
                stack.set(DataComponents.FIREWORK_EXPLOSION, explosion);
            }
            case FIREWORK_STAR_FADE -> {
                stack = this.firstSlot.getItem().copy();
                IntList fadeColors = new IntArrayList();
                for (Slot slot : this.materialSlots) {
                    DyeColor color = DyeColor.getColor(slot.getItem());
                    if (color != null) {
                        fadeColors.add(color.getFireworkColor());
                    }
                }
                stack.update(DataComponents.FIREWORK_EXPLOSION, FireworkExplosion.DEFAULT, fadeColors, FireworkExplosion::withFadeColors);
            }
            case FIREWORK_ROCKET -> {
                stack = new ItemStack(Items.FIREWORK_ROCKET, 3);
                Fireworks fireworks = this.getFireWorks();
                stack.set(DataComponents.FIREWORKS, fireworks);
            }
        }
        if (!ItemStack.matches(stack, this.resultSlot.getItem())) {
            this.resultSlot.set(stack);
        }
    }

    private FireworkExplosion getExplosionEffect() {
        FireworkExplosion.Shape shape = FireworkShapeTemplateItem.getShape(this.paperSlot.getItem());
        boolean trail = trailIngredient(this.secondSlot.getItem());
        boolean twinkle = twinkleIngredient(this.thirdSlot.getItem());
        IntList colors = new IntArrayList();
        for (Slot slot : this.materialSlots) {
            DyeColor color = DyeColor.getColor(slot.getItem());
            if (color != null) {
                colors.add(color.getFireworkColor());
            }
        }
        return new FireworkExplosion(shape, colors, IntList.of(), trail, twinkle);
    }

    private Fireworks getFireWorks() {
        List<FireworkExplosion> list = new ArrayList<>();
        for (Slot slot : this.materialSlots) {
            if (slot.hasItem()) {
                FireworkExplosion explosion = slot.getItem().get(DataComponents.FIREWORK_EXPLOSION);
                if (explosion != null) {
                    list.add(explosion);
                }
            }
        }
        int i = 0;
        ItemStack[] stacks = new ItemStack[]{this.firstSlot.getItem(), this.secondSlot.getItem(), this.thirdSlot.getItem()};
        for (ItemStack stack : stacks) {
            if (isGunpowder(stack)) {
                i++;
            }
        }
        return new Fireworks(i, list);
    }

    private static boolean isFireworkShapeTemplate(ItemStack stack) {
        return stack.getItem() instanceof FireworkShapeTemplateItem;
    }

    private static boolean isPaper(ItemStack stack) {
        return stack.is(Items.PAPER);
    }

    private static boolean paperSlotAcceptable(ItemStack stack) {
        return isPaper(stack) || isFireworkShapeTemplate(stack);
    }

    private static boolean isDye(ItemStack stack) {
        return stack.is(Tags.Items.DYES);
    }

    private static boolean isFireworkStar(ItemStack stack) {
        return stack.is(Items.FIREWORK_STAR);
    }

    private static boolean isGunpowder(ItemStack stack) {
        return stack.is(Tags.Items.GUNPOWDERS);
    }

    private static boolean trailIngredient(ItemStack stack) {
        return stack.is(Tags.Items.GEMS_DIAMOND);
    }

    private static boolean twinkleIngredient(ItemStack stack) {
        return stack.is(Tags.Items.DUSTS_GLOWSTONE);
    }

    private enum Mode {
        NONE,
        FIREWORK_STAR,
        FIREWORK_STAR_FADE,
        FIREWORK_ROCKET
    }
}
