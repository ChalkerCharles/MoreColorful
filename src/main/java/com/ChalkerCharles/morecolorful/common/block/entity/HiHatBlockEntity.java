package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.ChalkerCharles.morecolorful.common.block.musical.HiHatBlock.HIT;

public class HiHatBlockEntity extends BlockEntity {
    public HiHatBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HIHAT.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, HiHatBlockEntity ignore) {
        boolean pressing = CymbalUtils.playerPressingHiHat(level, pos);
        if (pressing) {
            level.setBlock(pos, state.setValue(HIT, true), 3);
        } else {
            level.setBlock(pos, state.setValue(HIT, false), 3);
        }
    }
}
