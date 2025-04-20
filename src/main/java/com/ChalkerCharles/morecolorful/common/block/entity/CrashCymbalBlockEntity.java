package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class CrashCymbalBlockEntity extends BlockEntity implements CymbalUtils {
    public int ticks;
    public int ticksAfterStop;
    public boolean shaking;

    public CrashCymbalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.CRASH_CYMBAL.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState ignored, T pBlockEntity) {
        if (pBlockEntity instanceof CrashCymbalBlockEntity blockEntity) {
            Set<Integer> playerSet = blockEntity.pressingPlayers(pLevel, pPos);
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
}
