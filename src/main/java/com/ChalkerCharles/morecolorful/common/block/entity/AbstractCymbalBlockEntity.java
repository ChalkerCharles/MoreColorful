package com.ChalkerCharles.morecolorful.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractCymbalBlockEntity extends BlockEntity {
    public int ticks;
    public int ticksAfterStop;
    public boolean shaking;

    public AbstractCymbalBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }
}
