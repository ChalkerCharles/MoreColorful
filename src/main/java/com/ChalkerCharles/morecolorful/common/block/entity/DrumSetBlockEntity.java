package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.HIT;
import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

public class DrumSetBlockEntity extends BlockEntity {
    public int ticksRide;
    public int ticksAfterStopRide;
    public boolean shakingRide;
    public int ticksCrash;
    public int ticksAfterStopCrash;
    public boolean shakingCrash;
    public DrumSetBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DRUM_SET.get(), pPos, pBlockState);
    }

    public static void tick(Level pLevel, BlockPos pPos, BlockState pState, DrumSetBlockEntity pBlockEntity) {
        boolean pressingBd = CymbalUtils.playerPressingBassDrum(pLevel, pPos);
        boolean pressingHat = CymbalUtils.playerPressingHat(pLevel, pPos);
        boolean pressingRide = CymbalUtils.playerPressingRide(pLevel, pPos);
        boolean pressingCrash = CymbalUtils.playerPressingCrash(pLevel, pPos);
        BlockPos bassDrumPos = CymbalUtils.getBassDrumPos(pPos, pState);
        BlockPos hatPos = CymbalUtils.getHatPos(pPos, pState);
        DrumSetPart part = pState.getValue(PART);
        if (part == DrumSetPart.MID_LOWER) {
            if (pressingBd) {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, false), 3);
            }
        } else if (part == DrumSetPart.LEFT_LOWER) {
            if (pressingHat) {
                pLevel.setBlock(hatPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(hatPos, pState.setValue(HIT, false), 3);
            }
        } else if (part == DrumSetPart.RIGHT_UPPER) {
            if (pressingRide) pBlockEntity.shakingRide = true;

            if (!pressingRide && pBlockEntity.shakingRide) {
                pBlockEntity.ticksAfterStopRide++;
            } else {
                pBlockEntity.ticksAfterStopRide = 0;
            }

            if (pBlockEntity.shakingRide) pBlockEntity.ticksRide++;

            if (pBlockEntity.ticksAfterStopRide >= 100) {
                pBlockEntity.shakingRide = false;
                pBlockEntity.ticksRide = 0;
                pBlockEntity.ticksAfterStopRide = 0;
            }
        } else if (part == DrumSetPart.LEFT_UPPER) {
            if (pressingCrash) pBlockEntity.shakingCrash = true;

            if (!pressingCrash && pBlockEntity.shakingCrash) {
                pBlockEntity.ticksAfterStopCrash++;
            } else {
                pBlockEntity.ticksAfterStopCrash = 0;
            }

            if (pBlockEntity.shakingCrash) pBlockEntity.ticksCrash++;

            if (pBlockEntity.ticksAfterStopCrash >= 100) {
                pBlockEntity.shakingCrash = false;
                pBlockEntity.ticksCrash = 0;
                pBlockEntity.ticksAfterStopCrash = 0;
            }
        }
    }
}
