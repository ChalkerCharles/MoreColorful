package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.trunklplacers;

import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTrunkPlacers;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class DawnRedwoodTrunkPlacer extends TrunkPlacer {
    public static final MapCodec<DawnRedwoodTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> trunkPlacerParts(instance)
                    .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(placer -> placer.trunkHeight))
                    .apply(instance, DawnRedwoodTrunkPlacer::new)
    );
    private final IntProvider trunkHeight;
    public DawnRedwoodTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB, IntProvider trunkHeight) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
        this.trunkHeight = trunkHeight;
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
        Random random = new Random(1234L);
        int max = this.trunkHeight.getMaxValue();
        int min = this.trunkHeight.getMinValue();
        int offset = random.nextInt(pFreeTreeHeight / 5);
        int baseBranchHeight = random.nextInt(min + 1, max + 2) + offset;
        int rootMaxHeight = baseBranchHeight - 2 - offset;

        placeRoot(pLevel, pBlockSetter, pRandom, rootMaxHeight, pPos.north(), pConfig);
        placeRoot(pLevel, pBlockSetter, pRandom, rootMaxHeight, pPos.south(), pConfig);
        placeRoot(pLevel, pBlockSetter, pRandom, rootMaxHeight, pPos.east(), pConfig);
        placeRoot(pLevel, pBlockSetter, pRandom, rootMaxHeight, pPos.west(), pConfig);

        BlockPos branchPos = pPos.above(baseBranchHeight);
        Function<BlockState, BlockState> functionX = state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.X);
        Function<BlockState, BlockState> functionZ = state -> state.trySetValue(RotatedPillarBlock.AXIS, Direction.Axis.Z);

        int height = pFreeTreeHeight - baseBranchHeight - 3;
        placeBranch(pLevel, pBlockSetter, pRandom, height, branchPos.north(), pConfig, functionZ);
        placeBranch(pLevel, pBlockSetter, pRandom, height, branchPos.south(), pConfig, functionZ);
        placeBranch(pLevel, pBlockSetter, pRandom, height, branchPos.east(), pConfig, functionX);
        placeBranch(pLevel, pBlockSetter, pRandom, height, branchPos.west(), pConfig, functionX);

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(pFreeTreeHeight), 0, false));
    }

    private void placeRoot(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int rootMaxHeight, BlockPos pPos, TreeConfiguration pConfig) {
        int offset = pRandom.nextInt(4);
        int height = Math.max((rootMaxHeight - offset), 1);
        for (int i = 0; i < height; i ++) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);
        }
        if (pLevel.isStateAtPosition(pPos.below(), state -> state.is(BlockTags.REPLACEABLE))) {
            this.placeLog(pLevel, pBlockSetter, pRandom, pPos.below(), pConfig);
        }
    }

    private void placeBranch(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, int height, BlockPos pPos, TreeConfiguration pConfig, Function<BlockState, BlockState> function) {
        for (int i = 0; i < height; i += 3) {
            int random = pRandom.nextInt(1 + i);
            if (random == 0 || random == 1) {
                this.placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i - 1), pConfig, function);
            }
        }
    }
}
