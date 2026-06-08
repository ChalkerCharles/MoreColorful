package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.utility.MailboxBlock;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringUtil;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class MailboxBlockEntity extends RandomizableContainerBlockEntity {
    private static final MutableComponent MAILBOX_TITLE = Component.translatable("morecolorful.gui.mailbox");
    private NonNullList<ItemStack> items = NonNullList.withSize(9, ItemStack.EMPTY);
    private String owner = "";
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            MailboxBlockEntity.this.playSound(state, ModSounds.MAILBOX_OPEN.get());
            MailboxBlockEntity.this.updateBlockState(state, true);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            MailboxBlockEntity.this.playSound(state, ModSounds.MAILBOX_CLOSE.get());
            MailboxBlockEntity.this.updateBlockState(state, false);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (player.containerMenu instanceof ChestMenu menu) {
                Container container = menu.getContainer();
                return container == MailboxBlockEntity.this;
            } else {
                return false;
            }
        }
    };

    public MailboxBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.MAILBOX.get(), pPos, pBlockState);
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void tryInsertIn(ItemStack mail) {
        if (mail.isEmpty()) return;
        for (int i = 0; i < this.getContainerSize(); i++) {
            ItemStack stack = this.getItem(i);
            if (stack.isEmpty()) {
                this.setItem(i, mail);
                return;
            } else if (ItemStack.isSameItemSameComponents(mail, stack)) {
                int limit = this.getMaxStackSize(stack);
                stack.grow(mail.getCount());
                int d = stack.getCount() - limit;
                stack.limitSize(limit);
                if (d > 0) {
                    mail.setCount(d);
                } else return;
            }
        }
        if (this.level != null) {
            BlockPos pos = this.getBlockPos();
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.5;
            double z = pos.getZ() + 0.5;
            this.level.addFreshEntity(new ItemEntity(this.level, x, y, z, mail));
        }
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items, pRegistries);
        }
        if (!StringUtil.isBlank(this.owner)) {
            pTag.putString("Owner", this.owner);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items, pRegistries);
        }
        if (pTag.contains("Owner")) {
            this.owner = pTag.getString("Owner");
        }
    }

    public String getMailboxName() {
        return this.getCustomName() == null ? this.owner : this.getCustomName().getString();
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("info.morecolorful.possessive", this.owner, MAILBOX_TITLE);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new ChestMenu(MenuType.GENERIC_9x1, pContainerId, pInventory, this, 1);
    }

    @Override
    public int getContainerSize() {
        return 9;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (this.level != null && !this.level.isClientSide) {
            LevelSavedData.getMailboxes(this.level)
                    .computeIfAbsent(this.getMailboxName(), s -> new LongOpenHashSet())
                    .add(this.getBlockPos().asLong());
        }
    }

    public void removeMailbox(Level level) {
        if (!level.isClientSide) {
            Map<String, LongSet> map = LevelSavedData.getMailboxes(level);
            String key = this.getMailboxName();
            LongSet set = map.get(key);
            if (set == null) return;
            set.remove(this.getBlockPos().asLong());
            if (set.isEmpty()) {
                map.remove(key);
            }
        }
    }

    @Override
    public void startOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator() && this.level != null) {
            this.openersCounter.incrementOpeners(pPlayer, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void stopOpen(Player pPlayer) {
        if (!this.remove && !pPlayer.isSpectator() && this.level != null) {
            this.openersCounter.decrementOpeners(pPlayer, this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    public void recheckOpen() {
        if (!this.remove && this.level != null) {
            this.openersCounter.recheckOpeners(this.level, this.getBlockPos(), this.getBlockState());
        }
    }

    private void updateBlockState(BlockState pState, boolean pOpen) {
        if (this.level != null) {
            this.level.setBlock(this.getBlockPos(), pState.setValue(MailboxBlock.OPEN, pOpen), 3);
        }
    }

    private void playSound(BlockState pState, SoundEvent pSound) {
        Vec3i vec3i = pState.getValue(MailboxBlock.FACING).getNormal();
        double d0 = (double)this.worldPosition.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double d1 = (double)this.worldPosition.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double d2 = (double)this.worldPosition.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        if (this.level != null) {
            this.level.playSound(null, d0, d1, d2, pSound, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
        }
    }
}
