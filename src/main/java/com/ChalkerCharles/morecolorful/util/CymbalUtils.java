package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.attachment.InstrumentData;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.musical.DrumSetBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

public final class CymbalUtils {
    public static boolean playerPressing(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            BlockPos pos1 = data.pos;
            BlockState state = level.getBlockState(pos1);
            pos1 = (state.is(ModBlocks.RIDE_CYMBAL) || state.is(ModBlocks.CRASH_CYMBAL))
                    && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER
                    ? pos1.above() : pos1;
            if (data.isPlaying && pos.equals(pos1)) {
                return true;
            }
        }
        return false;
    }

    public static boolean playerPressingHiHat(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            if (data.isPlaying && pos.equals(data.pos)) {
                return true;
            }
        }
        return false;
    }

    public static boolean playerPressingBassDrum(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            BlockPos pos1 = data.pos;
            BlockState state = level.getBlockState(pos1);
            pos1 = state.is(ModBlocks.DRUM_SET) ? getBassDrumPos(pos1, state) : pos1;
            if (data.isPressingBassDrum() && pos.equals(pos1)) {
                return true;
            }
        }
        return false;
    }

    public static BlockPos getBassDrumPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(DrumSetBlock.FACING);
        DrumSetPart part = state.getValue(DrumSetBlock.PART);
        return switch (part) {
            case MID_LOWER -> pos;
            case MID_UPPER -> pos.below();
            case LEFT_LOWER -> pos.relative(direction.getCounterClockWise());
            case LEFT_UPPER -> pos.relative(direction.getCounterClockWise()).below();
            case RIGHT_LOWER -> pos.relative(direction.getClockWise());
            case RIGHT_UPPER -> pos.relative(direction.getClockWise()).below();
        };
    }

    public static boolean playerPressingHat(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            BlockPos pos1 = data.pos;
            BlockState state = level.getBlockState(pos1);
            pos1 = state.is(ModBlocks.DRUM_SET) ? getHatPos(pos1, state) : pos1;
            if (data.isPressingHat() && pos.equals(pos1)) {
                return true;
            }
        }
        return false;
    }

    public static BlockPos getHatPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(DrumSetBlock.FACING);
        DrumSetPart part = state.getValue(DrumSetBlock.PART);
        return switch (part) {
            case LEFT_LOWER -> pos;
            case LEFT_UPPER -> pos.below();
            case MID_LOWER -> pos.relative(direction.getClockWise());
            case MID_UPPER -> pos.relative(direction.getClockWise()).below();
            case RIGHT_LOWER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise());
            case RIGHT_UPPER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise()).below();
        };
    }

    public static boolean playerPressingRide(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            BlockPos pos1 = data.pos;
            BlockState state = level.getBlockState(pos1);
            pos1 = state.is(ModBlocks.DRUM_SET) ? getRidePos(pos1, state) : pos1;
            if (data.isPressingRide() && pos.equals(pos1)) {
                return true;
            }
        }
        return false;
    }

    private static BlockPos getRidePos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(DrumSetBlock.FACING);
        DrumSetPart part = state.getValue(DrumSetBlock.PART);
        return switch (part) {
            case RIGHT_LOWER -> pos.above();
            case RIGHT_UPPER -> pos;
            case MID_LOWER -> pos.relative(direction.getCounterClockWise()).above();
            case MID_UPPER -> pos.relative(direction.getCounterClockWise());
            case LEFT_LOWER -> pos.relative(direction.getCounterClockWise()).relative(direction.getCounterClockWise()).above();
            case LEFT_UPPER -> pos.relative(direction.getCounterClockWise()).relative(direction.getCounterClockWise());
        };
    }

    public static boolean playerPressingCrash(Level level, BlockPos pos) {
        for (Player player : level.players()) {
            InstrumentData data = InstrumentData.get(player);
            BlockPos pos1 = data.pos;
            BlockState state = level.getBlockState(pos1);
            pos1 = state.is(ModBlocks.DRUM_SET) ? getCrashPos(pos1, state) : pos1;
            if (data.isPressingCrash() && pos.equals(pos1)) {
                return true;
            }
        }
        return false;
    }

    private static BlockPos getCrashPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(DrumSetBlock.FACING);
        DrumSetPart part = state.getValue(DrumSetBlock.PART);
        return switch (part) {
            case LEFT_LOWER -> pos.above();
            case LEFT_UPPER -> pos;
            case MID_LOWER -> pos.relative(direction.getClockWise()).above();
            case MID_UPPER -> pos.relative(direction.getClockWise());
            case RIGHT_LOWER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise()).above();
            case RIGHT_UPPER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise());
        };
    }
}
