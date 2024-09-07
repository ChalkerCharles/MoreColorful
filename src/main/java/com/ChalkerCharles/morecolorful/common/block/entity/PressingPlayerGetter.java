package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.ArrayList;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.FACING;
import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

public interface PressingPlayerGetter {
    EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    default ArrayList<Integer> pressingPlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.PLAYING_SCREEN_DATA)[1];
            var state = level.getBlockState(pos1);
            if ((state.is(ModBlocks.RIDE_CYMBAL.get()) || state.is(ModBlocks.CRASH_CYMBAL.get()))
                    && state.getValue(HALF) == DoubleBlockHalf.LOWER) {
                pos1 = pos1.above();
            }
            boolean isPressing = player.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT);
            if (isPressing && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }

    default ArrayList<Integer> pressingPlayersForHiHat(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.PLAYING_SCREEN_DATA)[1];
            boolean isPressing = player.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT);
            if (isPressing && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
    default ArrayList<Integer> pressingBassDrumPlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.DRUM_SET_DATA)[4];
            var state = level.getBlockState(pos1);
            if (state.is(ModBlocks.DRUM_SET.get())) {
                pos1 = getBassDrumPos(pos1, state);
            }
            boolean isPressingBassDrum = (boolean) player.getData(ModDataAttachments.DRUM_SET_DATA)[0];
            if (isPressingBassDrum && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
    default BlockPos getBassDrumPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        DrumSetPart part = state.getValue(PART);
        return switch (part) {
            case MID_LOWER -> pos;
            case MID_UPPER -> pos.below();
            case LEFT_LOWER -> pos.relative(direction.getCounterClockWise());
            case LEFT_UPPER -> pos.relative(direction.getCounterClockWise()).below();
            case RIGHT_LOWER -> pos.relative(direction.getClockWise());
            case RIGHT_UPPER -> pos.relative(direction.getClockWise()).below();
        };
    }
    default ArrayList<Integer> pressingHatPlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.DRUM_SET_DATA)[4];
            var state = level.getBlockState(pos1);
            if (state.is(ModBlocks.DRUM_SET.get())) {
                pos1 = getHatPos(pos1, state);
            }
            boolean isPressingHat = (boolean) player.getData(ModDataAttachments.DRUM_SET_DATA)[1];
            if (isPressingHat && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
    default BlockPos getHatPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        DrumSetPart part = state.getValue(PART);
        return switch (part) {
            case LEFT_LOWER -> pos;
            case LEFT_UPPER -> pos.below();
            case MID_LOWER -> pos.relative(direction.getClockWise());
            case MID_UPPER -> pos.relative(direction.getClockWise()).below();
            case RIGHT_LOWER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise());
            case RIGHT_UPPER -> pos.relative(direction.getClockWise()).relative(direction.getClockWise()).below();
        };
    }
    default ArrayList<Integer> pressingRidePlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.DRUM_SET_DATA)[4];
            var state = level.getBlockState(pos1);
            if (state.is(ModBlocks.DRUM_SET.get())) {
                pos1 = getRidePos(pos1, state);
            }
            boolean isPressingRide = (boolean) player.getData(ModDataAttachments.DRUM_SET_DATA)[2];
            if (isPressingRide && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
    default BlockPos getRidePos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        DrumSetPart part = state.getValue(PART);
        return switch (part) {
            case RIGHT_LOWER -> pos.above();
            case RIGHT_UPPER -> pos;
            case MID_LOWER -> pos.relative(direction.getCounterClockWise()).above();
            case MID_UPPER -> pos.relative(direction.getCounterClockWise());
            case LEFT_LOWER -> pos.relative(direction.getCounterClockWise()).relative(direction.getCounterClockWise()).above();
            case LEFT_UPPER -> pos.relative(direction.getCounterClockWise()).relative(direction.getCounterClockWise());
        };
    }
    default ArrayList<Integer> pressingCrashPlayers(Level level, BlockPos pos) {
        ArrayList<Integer> list = new ArrayList<>();
        for (Player player : level.players()) {
            var pos1 = (BlockPos) player.getData(ModDataAttachments.DRUM_SET_DATA)[4];
            var state = level.getBlockState(pos1);
            if (state.is(ModBlocks.DRUM_SET.get())) {
                pos1 = getCrashPos(pos1, state);
            }
            boolean isPressingCrash = (boolean) player.getData(ModDataAttachments.DRUM_SET_DATA)[3];
            if (isPressingCrash && pos.asLong() == pos1.asLong()) {
                list.add(player.getId());
            }
        }
        return list;
    }
    default BlockPos getCrashPos(BlockPos pos, BlockState state) {
        Direction direction = state.getValue(FACING);
        DrumSetPart part = state.getValue(PART);
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
