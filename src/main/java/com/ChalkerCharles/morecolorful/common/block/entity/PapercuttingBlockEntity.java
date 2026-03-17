package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.component.PapercuttingStencil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class PapercuttingBlockEntity extends BlockEntity {
    public PapercuttingStencil stencil = PapercuttingStencil.DEFAULT;

    public PapercuttingBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PAPERCUTTING.get(), pPos, pBlockState);
    }

    public ItemStack getItem() {
        ItemStack stack = new ItemStack(this.getBlockState().getBlock());
        stack.applyComponents(this.collectComponents());
        return stack;
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        if (this.stencil.notEmpty()) {
            pTag.putLongArray("stencil", this.stencil.stencil());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        if (pTag.contains("stencil", Tag.TAG_LONG_ARRAY)) {
            this.stencil = PapercuttingStencil.of(pTag.getLongArray("stencil"));
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return this.saveCustomOnly(pRegistries);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder pComponents) {
        super.collectImplicitComponents(pComponents);
        pComponents.set(ModDataComponents.PAPERCUTTING_STENCIL, this.stencil);
    }

    @Override
    protected void applyImplicitComponents(DataComponentInput pComponentInput) {
        super.applyImplicitComponents(pComponentInput);
        this.stencil = pComponentInput.getOrDefault(ModDataComponents.PAPERCUTTING_STENCIL, PapercuttingStencil.DEFAULT);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void removeComponentsFromTag(CompoundTag pTag) {
        super.removeComponentsFromTag(pTag);
        pTag.remove("stencil");
    }
}
