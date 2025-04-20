package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.util.CymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.HIT;
import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

public class DrumSetBlockEntity extends BlockEntity implements CymbalUtils {
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
        Set<Integer> playerSetBd = pBlockEntity.pressingBassDrumPlayers(pLevel, pPos);
        Set<Integer> playerSetHat = pBlockEntity.pressingHatPlayers(pLevel, pPos);
        Set<Integer> playerSetRide = pBlockEntity.pressingRidePlayers(pLevel, pPos);
        Set<Integer> playerSetCrash = pBlockEntity.pressingCrashPlayers(pLevel, pPos);
        BlockPos bassDrumPos = pBlockEntity.getBassDrumPos(pPos, pState);
        BlockPos hatPos = pBlockEntity.getHatPos(pPos, pState);
        if (pState.getValue(PART) == DrumSetPart.MID_LOWER) {
            if (!playerSetBd.isEmpty()) {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, false), 3);
            }
        } else if (pState.getValue(PART) == DrumSetPart.LEFT_LOWER) {
            if (!playerSetHat.isEmpty()) {
                pLevel.setBlock(hatPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(hatPos, pState.setValue(HIT, false), 3);
            }
        } else if (pState.getValue(PART) == DrumSetPart.RIGHT_UPPER) {
            if (!playerSetRide.isEmpty()) pBlockEntity.shakingRide = true;

            if (playerSetRide.isEmpty() && pBlockEntity.shakingRide) {
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
        } else if (pState.getValue(PART) == DrumSetPart.LEFT_UPPER) {
            if (!playerSetCrash.isEmpty()) pBlockEntity.shakingCrash = true;

            if (playerSetCrash.isEmpty() && pBlockEntity.shakingCrash) {
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
