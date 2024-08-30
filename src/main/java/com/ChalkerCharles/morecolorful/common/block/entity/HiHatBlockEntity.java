package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.HiHatBlock.HIT;

public class HiHatBlockEntity extends AbstractCymbalBlockEntity {

    public HiHatBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HIHAT.get(), pPos, pBlockState);
    }

    public static <T extends BlockEntity> void tick(Level pLevel, BlockPos pPos, BlockState pState, T pBlockEntity) {
        if (pBlockEntity instanceof HiHatBlockEntity blockEntity) {
            var playerList = blockEntity.pressingPlayers(pLevel, pPos);
            if (!playerList.isEmpty()) {
                pLevel.setBlock(pPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(pPos, pState.setValue(HIT, false), 3);
            }
        }
    }
}
