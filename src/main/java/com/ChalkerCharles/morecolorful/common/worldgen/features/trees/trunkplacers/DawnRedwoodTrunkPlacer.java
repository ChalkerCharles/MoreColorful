package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.trunkplacers;

import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTrunkPlacers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DawnRedwoodTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<DawnRedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> trunkPlacerParts(instance).apply(instance, DawnRedwoodTrunkPlacer::new)
    );
    public DawnRedwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return ModTrunkPlacers.DAWN_REDWOOD_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        setDirtAt(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        for (int i = 0; i < pFreeTreeHeight; i++) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);
        }
        int offset = pRandom.nextInt(pFreeTreeHeight / 5);
        int baseBranchHeight = 6 + offset;

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            placeRootTrunk(pLevel, pBlockSetter, pRandom, pPos.relative(direction), pConfig);
        }

        BlockPos branchPos = pPos.above(baseBranchHeight);
        int height = pFreeTreeHeight - baseBranchHeight - 3;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            placeBranch(pLevel, pBlockSetter, pRandom, height, branchPos.relative(direction), pConfig, state -> state.trySetValue(RotatedPillarBlock.AXIS, direction.getAxis()));
        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(pFreeTreeHeight), 0, false));
    }

    private void placeRootTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, BlockPos pos, TreeConfiguration config) {
        int offset = random.nextInt(4);
        int height = 4 - offset;
        for (int i = 0; i < height; i ++) {
            this.placeLog(level, blockSetter, random, pos.above(i), config);
        }
        if (level.isStateAtPosition(pos.below(), state -> state.is(BlockTags.REPLACEABLE))) {
            this.placeLog(level, blockSetter, random, pos.below(), config);
        }
    }

    private void placeBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, RandomSource random, int height, BlockPos pos, TreeConfiguration config, Function<BlockState, BlockState> function) {
        for (int i = 0; i < height; i += 3) {
            int r = random.nextInt(1 + i);
            if (r == 0 || r == 1) {
                this.placeLog(level, blockSetter, random, pos.above(i - 1), config, function);
            }
        }
    }
}
