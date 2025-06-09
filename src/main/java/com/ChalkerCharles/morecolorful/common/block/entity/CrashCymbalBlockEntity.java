package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class CrashCymbalBlockEntity extends AbstractCymbalBlockEntity {
    public CrashCymbalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRASH_CYMBAL.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, CrashCymbalBlockEntity blockEntity) {
        IntSet playerSet = CymbalUtils.pressingPlayers(pLevel, pPos);
        if (!playerSet.isEmpty()) {
            blockEntity.shaking = true;
        }

        if (playerSet.isEmpty() && blockEntity.shaking) {
            blockEntity.ticksAfterStop ++;
        } else {
            blockEntity.ticksAfterStop = 0;
        }

        if (blockEntity.shaking) {
            blockEntity.ticks ++;
        }

        if (blockEntity.ticksAfterStop >= 100) {
            blockEntity.shaking = false;
            blockEntity.ticks = 0;
            blockEntity.ticksAfterStop = 0;
        }
    }
}
