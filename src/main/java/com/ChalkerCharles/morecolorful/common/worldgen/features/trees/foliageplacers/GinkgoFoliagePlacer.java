package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers;

import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModFoliagePlacers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.foliageplacers.RandomSpreadFoliagePlacer;

public class GinkgoFoliagePlacer extends RandomSpreadFoliagePlacer {
    public static final MapCodec<GinkgoFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            inst -> foliagePlacerParts(inst)
                    .and(
                            inst.group(
                                    IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter(p1 -> p1.foliageHeight),
                                    Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter(p2 -> p2.leafPlacementAttempts)
                            )
                    )
                    .apply(inst, GinkgoFoliagePlacer::new)
    );
    private final IntProvider foliageHeight;
    private final int leafPlacementAttempts;

    public GinkgoFoliagePlacer(IntProvider pRadius, IntProvider pOffset, IntProvider foliageHeight, int leafPlacementAttempts) {
        super(pRadius, pOffset, foliageHeight, leafPlacementAttempts);
        this.foliageHeight = foliageHeight;
        this.leafPlacementAttempts = leafPlacementAttempts;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.GINKGO_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel,
                                 FoliagePlacer.FoliageSetter pBlockSetter,
                                 RandomSource pRandom,
                                 TreeConfiguration pConfig,
                                 int pMaxFreeTreeHeight,
                                 FoliagePlacer.FoliageAttachment pAttachment,
                                 int pFoliageHeight,
                                 int pFoliageRadius,
                                 int pOffset) {
        BlockPos blockpos = pAttachment.pos();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = blockpos.mutable();

        for (int i = 0; i < this.leafPlacementAttempts; i++) {
            int deltaX = pRandom.nextInt(pFoliageRadius) - pRandom.nextInt(pFoliageRadius);
            int deltaY = pRandom.nextInt(pFoliageHeight) - pRandom.nextInt(pFoliageHeight);
            int deltaZ = pRandom.nextInt(pFoliageRadius) - pRandom.nextInt(pFoliageRadius);
            blockpos$mutableblockpos.setWithOffset(blockpos, deltaX, deltaY, deltaZ);
            tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, blockpos$mutableblockpos);
            checkLeaf(pLevel, pBlockSetter, pRandom, pConfig, blockpos$mutableblockpos, deltaX, deltaY, deltaZ);
        }
    }

    private static void checkLeaf(LevelSimulatedReader pLevel,
                                  FoliageSetter pFoliageSetter,
                                  RandomSource pRandom,
                                  TreeConfiguration pTreeConfiguration,
                                  BlockPos pPos,
                                  int deltaX,
                                  int deltaY,
                                  int deltaZ) {
        if (deltaX < 2 && deltaX > -2 && deltaZ < 2 && deltaZ > -2 && deltaY < 3 && deltaY > -3) {
            return;
        }
        int i = -deltaX / 2;
        int j = -deltaZ / 2;
        if (deltaY == 3 || deltaY == -3) {
            BlockPos pos1 = deltaY == 3 ? pPos.below() : pPos.above();
            tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, pos1);
        }
        if ((deltaX == 2 || deltaX == -2) && (deltaZ == 2 || deltaZ == -2)) {
            BlockPos pos2 = pRandom.nextInt(2) == 0 ? pPos.offset(i, 0, 0) : pPos.offset(0, 0, j);
            tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, pos2);
        } else {
            tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, pPos.offset(i, 0, 0));
            tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, pPos.offset(0, 0, j));
        }
    }
}
