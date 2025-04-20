package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.HiHatBlock.HIT;

public class HiHatBlockEntity extends BlockEntity implements CymbalUtils {

    public HiHatBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HIHAT.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pBlockEntity instanceof HiHatBlockEntity blockEntity) {
            Set<Integer> playerSet = blockEntity.pressingPlayersForHiHat(pLevel, pPos);
            if (!playerSet.isEmpty()) {
                pLevel.setBlock(pPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(pPos, pState.setValue(HIT, false), 3);
            }
        }
    }
}
