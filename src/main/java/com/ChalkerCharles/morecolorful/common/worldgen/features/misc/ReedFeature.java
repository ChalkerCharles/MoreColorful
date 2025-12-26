package com.ChalkerCharles.morecolorful.common.worldgen.features.misc;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.ReedPart;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

import static com.ChalkerCharles.morecolorful.common.block.natural.ReedBlock.PART;
import static com.ChalkerCharles.morecolorful.common.block.natural.ReedBlock.TALL_REED;

public class ReedFeature extends Feature<ProbabilityFeatureConfiguration> {
    private static final BlockState REED_BOTTOM = ModBlocks.REED.get().defaultBlockState();
    private static final BlockState REED_TOP = REED_BOTTOM.setValue(PART, ReedPart.UPPER);
    private static final BlockState TALL_REED_BOTTOM = REED_BOTTOM.setValue(TALL_REED, true);
    private static final BlockState TALL_REED_MIDDLE = TALL_REED_BOTTOM.setValue(PART, ReedPart.MID);
    private static final BlockState TALL_REED_TOP = REED_TOP.setValue(TALL_REED, true);
    private static final int RANGE = 7;
    public ReedFeature(Codec<ProbabilityFeatureConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> pContext) {
        int i = 0;
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldGenLevel = pContext.level();
        RandomSource randomSource = pContext.random();
        ProbabilityFeatureConfiguration configuration = pContext.config();
        BlockPos.MutableBlockPos mutablePos = blockpos.mutable();
        for (int j = 0; j < 50; j++) {
            int deltaX = randomSource.nextInt(RANGE) - randomSource.nextInt(RANGE);
            int deltaY = randomSource.nextInt(2) - randomSource.nextInt(2);
            int deltaZ = randomSource.nextInt(RANGE) - randomSource.nextInt(RANGE);

            mutablePos.setWithOffset(blockpos, deltaX, deltaY, deltaZ);

            if (REED_BOTTOM.canSurvive(worldGenLevel, mutablePos)) {
                if (randomSource.nextFloat() < configuration.probability
                        && worldGenLevel.isEmptyBlock(mutablePos.above())
                        && worldGenLevel.isEmptyBlock(mutablePos.above(2))) {
                    placeTallReed(worldGenLevel, mutablePos);
                } else if (worldGenLevel.isEmptyBlock(mutablePos.above())) {
                    placeReed(worldGenLevel, mutablePos);
                }

                i++;
            }
        }

        return i > 0;
    }

    private static void placeReed(WorldGenLevel pLevel, BlockPos pPos) {
        pLevel.setBlock(pPos, DoublePlantBlock.copyWaterloggedFrom(pLevel, pPos, REED_BOTTOM), 2);
        pLevel.setBlock(pPos.above(), REED_TOP, 2);
    }

    private static void placeTallReed(WorldGenLevel pLevel, BlockPos pPos) {
        pLevel.setBlock(pPos, DoublePlantBlock.copyWaterloggedFrom(pLevel, pPos, TALL_REED_BOTTOM), 2);
        pLevel.setBlock(pPos.above(), TALL_REED_MIDDLE, 2);
        pLevel.setBlock(pPos.above(2), TALL_REED_TOP, 2);
    }
}
