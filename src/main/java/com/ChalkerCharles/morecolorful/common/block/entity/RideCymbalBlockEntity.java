package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;


public class RideCymbalBlockEntity extends BlockEntity implements PressingPlayerGetter {
    public int ticks;
    public int ticksAfterStop;
    public boolean shaking;

    public RideCymbalBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.RIDE_CYMBAL.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState ignored, T pBlockEntity) {
        if (pBlockEntity instanceof RideCymbalBlockEntity blockEntity) {
            var playerList = blockEntity.pressingPlayers(pLevel, pPos);
            if (!playerList.isEmpty()) {
                blockEntity.shaking = true;
            }

            if (playerList.isEmpty() && blockEntity.shaking) {
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
