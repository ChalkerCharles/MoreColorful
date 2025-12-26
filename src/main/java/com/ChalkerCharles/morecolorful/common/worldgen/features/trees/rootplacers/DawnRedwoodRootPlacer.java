package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.rootplacers;

import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModRootPlacers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.rootplacers.AboveRootPlacement;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacer;
import net.minecraft.world.level.levelgen.feature.rootplacers.RootPlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

import java.util.Optional;
import java.util.function.BiConsumer;

public class DawnRedwoodRootPlacer extends RootPlacer {
    public static final MapCodec<DawnRedwoodRootPlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> rootPlacerParts(instance)
                    .apply(instance, DawnRedwoodRootPlacer::new)
    );
    private static final int RADIUS = 5;

    public DawnRedwoodRootPlacer(IntProvider pTrunkOffset, BlockStateProvider pRootProvider, Optional<AboveRootPlacement> pAboveRootPlacement) {
        super(pTrunkOffset, pRootProvider, pAboveRootPlacement);
    }

    @Override
    protected RootPlacerType<?> type() {
        return ModRootPlacers.DAWN_REDWOOD_PLACER.get();
    }

    @Override
    public boolean placeRoots(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter, RandomSource pRandom, BlockPos pPos, BlockPos pTrunkOrigin, TreeConfiguration pTreeConfig) {
        BlockPos.MutableBlockPos rootPos = pPos.mutable();
        for (int i = 0; i < 25; i++) {
            int deltaX = pRandom.nextInt(RADIUS) - pRandom.nextInt(RADIUS);
            int deltaZ = pRandom.nextInt(RADIUS) - pRandom.nextInt(RADIUS);

            if ((Math.abs(deltaX) <= 1 || Math.abs(deltaZ) <= 1) && !(Math.abs(deltaX) == 1 && Math.abs(deltaZ) == 1))
                continue;

            rootPos.setWithOffset(pTrunkOrigin, deltaX, 0, deltaZ);

            if (canPlaceRoot(pLevel, rootPos)) {
                placeRoot(pLevel, pBlockSetter, pRandom, rootPos, pTreeConfig);
            } else if (canPlaceRoot(pLevel, rootPos.below())) {
                placeRoot(pLevel, pBlockSetter, pRandom, rootPos.below(), pTreeConfig);
            } else if (canPlaceRoot(pLevel, rootPos.above())) {
                placeRoot(pLevel, pBlockSetter, pRandom, rootPos.above(), pTreeConfig);
            }
        }

        return true;
    }

    @Override
    protected boolean canPlaceRoot(LevelSimulatedReader pLevel, BlockPos pPos) {
        if (pLevel.isStateAtPosition(pPos, state -> !state.is(BlockTags.REPLACEABLE))) {
            return false;
        } else if (pLevel.isStateAtPosition(pPos.below(), state -> !(state.is(BlockTags.DIRT) || state.getBlock() instanceof FarmBlock))) {
            return false;
        }
        return super.canPlaceRoot(pLevel, pPos);
    }
}
