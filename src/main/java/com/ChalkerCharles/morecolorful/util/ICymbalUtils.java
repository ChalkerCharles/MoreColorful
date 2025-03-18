package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.ModDataAttachments;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;

import java.util.List;
import java.util.stream.Stream;

import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.FACING;
import static com.ChalkerCharles.morecolorful.common.block.musical_instruments.DrumSetBlock.PART;

public interface ICymbalUtils {
    EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    default List<Integer> pressingPlayers(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> {
                    BlockPos pos1 = p.getData(ModDataAttachments.PLAYING_SCREEN_DATA).pos();
                    BlockState state = level.getBlockState(pos1);
                    pos1 = (state.is(ModBlocks.RIDE_CYMBAL) || state.is(ModBlocks.CRASH_CYMBAL)) && state.getValue(HALF) == DoubleBlockHalf.LOWER
                            ? pos1.above() : pos1;
                    return p.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT) && pos.equals(pos1);
                })
                .flatMap(p -> Stream.of(p.getId())).toList();
    }

    default List<Integer> pressingPlayersForHiHat(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> p.getData(ModDataAttachments.IS_PLAYING_INSTRUMENT)
                                && pos.equals(p.getData(ModDataAttachments.PLAYING_SCREEN_DATA).pos()))
                .flatMap(p -> Stream.of(p.getId())).toList();
    }
    default List<Integer> pressingBassDrumPlayers(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> {
                    BlockPos pos1 = p.getData(ModDataAttachments.DRUM_SET_DATA).pos();
                    BlockState state = level.getBlockState(pos1);
                    pos1 = state.is(ModBlocks.DRUM_SET) ? getBassDrumPos(pos1, state) : pos1;
                    return p.getData(ModDataAttachments.DRUM_SET_DATA).isPressingBassDrum() && pos.equals(pos1);
                })
                .flatMap(p -> Stream.of(p.getId())).toList();
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
    default List<Integer> pressingHatPlayers(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> {
                    BlockPos pos1 = p.getData(ModDataAttachments.DRUM_SET_DATA).pos();
                    BlockState state = level.getBlockState(pos1);
                    pos1 = state.is(ModBlocks.DRUM_SET) ? getHatPos(pos1, state) : pos1;
                    return p.getData(ModDataAttachments.DRUM_SET_DATA).isPressingHat() && pos.equals(pos1);
                })
                .flatMap(p -> Stream.of(p.getId())).toList();
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
    default List<Integer> pressingRidePlayers(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> {
                    BlockPos pos1 = p.getData(ModDataAttachments.DRUM_SET_DATA).pos();
                    BlockState state = level.getBlockState(pos1);
                    pos1 = state.is(ModBlocks.DRUM_SET) ? getRidePos(pos1, state) : pos1;
                    return p.getData(ModDataAttachments.DRUM_SET_DATA).isPressingRide() && pos.equals(pos1);
                })
                .flatMap(p -> Stream.of(p.getId())).toList();
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
    default List<Integer> pressingCrashPlayers(Level level, BlockPos pos) {
        return level.players().stream()
                .filter(p -> {
                    BlockPos pos1 = p.getData(ModDataAttachments.DRUM_SET_DATA).pos();
                    BlockState state = level.getBlockState(pos1);
                    pos1 = state.is(ModBlocks.DRUM_SET) ? getCrashPos(pos1, state) : pos1;
                    return p.getData(ModDataAttachments.DRUM_SET_DATA).isPressingCrash() && pos.equals(pos1);
                })
                .flatMap(p -> Stream.of(p.getId())).toList();
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
