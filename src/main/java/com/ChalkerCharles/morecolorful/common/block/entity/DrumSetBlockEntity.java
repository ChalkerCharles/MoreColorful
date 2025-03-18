package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.util.ICymbalUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.HIT;
import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

public class DrumSetBlockEntity extends BlockEntity implements ICymbalUtils {
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
        List<Integer> playerListBd = pBlockEntity.pressingBassDrumPlayers(pLevel, pPos);
        List<Integer> playerListHat = pBlockEntity.pressingHatPlayers(pLevel, pPos);
        List<Integer> playerListRide = pBlockEntity.pressingRidePlayers(pLevel, pPos);
        List<Integer> playerListCrash = pBlockEntity.pressingCrashPlayers(pLevel, pPos);
        BlockPos bassDrumPos = pBlockEntity.getBassDrumPos(pPos, pState);
        BlockPos hatPos = pBlockEntity.getHatPos(pPos, pState);
        if (pState.getValue(PART) == DrumSetPart.MID_LOWER) {
            if (!playerListBd.isEmpty()) {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(bassDrumPos, pState.setValue(HIT, false), 3);
            }
        } else if (pState.getValue(PART) == DrumSetPart.LEFT_LOWER) {
            if (!playerListHat.isEmpty()) {
                pLevel.setBlock(hatPos, pState.setValue(HIT, true), 3);
            } else {
                pLevel.setBlock(hatPos, pState.setValue(HIT, false), 3);
            }
        } else if (pState.getValue(PART) == DrumSetPart.RIGHT_UPPER) {
            if (!playerListRide.isEmpty()) pBlockEntity.shakingRide = true;

            if (playerListRide.isEmpty() && pBlockEntity.shakingRide) {
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
            if (!playerListCrash.isEmpty()) pBlockEntity.shakingCrash = true;

            if (playerListCrash.isEmpty() && pBlockEntity.shakingCrash) {
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
