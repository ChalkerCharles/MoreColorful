package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CrashCymbalBlockEntity extends BlockEntity {
    public int ticks;
    public int ticksAfterStop;
    public boolean shaking;

    public CrashCymbalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRASH_CYMBAL.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState ignore, CrashCymbalBlockEntity blockEntity) {
        boolean pressing = CymbalUtils.playerPressing(pLevel, pPos);
        if (pressing) {
            blockEntity.shaking = true;
        }

        if (!pressing && blockEntity.shaking) {
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
