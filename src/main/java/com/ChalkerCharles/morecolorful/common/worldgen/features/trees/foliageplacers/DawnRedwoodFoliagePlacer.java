package com.ChalkerCharles.morecolorful.common.worldgen.features.trees.foliageplacers;

import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModFoliagePlacers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

import java.util.Random;

public class DawnRedwoodFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<DawnRedwoodFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            instance -> foliagePlacerParts(instance)
                    .and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(placer -> placer.trunkHeight))
                    .apply(instance, DawnRedwoodFoliagePlacer::new)
    );
    private final IntProvider trunkHeight;
    private final Random random = new Random(1234L);
    public DawnRedwoodFoliagePlacer(IntProvider pRadius, IntProvider pOffset, IntProvider trunkHeight) {
        super(pRadius, pOffset);
        this.trunkHeight = trunkHeight;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.DAWN_REDWOOD_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(LevelSimulatedReader pLevel, FoliageSetter pBlockSetter, RandomSource pRandom, TreeConfiguration pConfig, int pMaxFreeTreeHeight, FoliageAttachment pAttachment, int pFoliageHeight, int pFoliageRadius, int pOffset) {
        BlockPos blockpos = pAttachment.pos();
        int i = pRandom.nextInt(2);
        int j = 1;
        int k = 0;
        int f = 0;
        int offset = random.nextInt(pMaxFreeTreeHeight / 5);

        for (int l = pOffset; l >= -(pFoliageHeight - offset); l--) {
            if (j >= 3) {
                f ++;
            }
            if (f > 3 && (i == 1 || i == 2)) {
                this.placeLeavesRowAlt(pLevel, pBlockSetter, pRandom, pConfig, blockpos, i + 1, l, pAttachment.doubleTrunk());
            } else {
                this.placeLeavesRow(pLevel, pBlockSetter, pRandom, pConfig, blockpos, i, l, pAttachment.doubleTrunk());
            }

            if (i >= j) {
                i = k;
                j = Math.min(j + 1, pFoliageRadius + pAttachment.radiusOffset());
                k = 1;
            } else {
                i++;
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        int max = this.trunkHeight.getMaxValue();
        int min = this.trunkHeight.getMinValue();
        return Math.max(4, pHeight - random.nextInt(min, max + 1));
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return pLocalX == pRange && pLocalZ == pRange && pRange > 0;
    }

    private void placeLeavesRowAlt(LevelSimulatedReader pLevel, FoliagePlacer.FoliageSetter pFoliageSetter, RandomSource pRandom, TreeConfiguration pTreeConfiguration, BlockPos pPos, int pRange, int pLocalY, boolean pLarge) {
        int i = pLarge ? 1 : 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for (int j = -pRange; j <= pRange + i; j++) {
            for (int k = -pRange; k <= pRange + i; k++) {
                if (!this.shouldSkipLocationAlt(Math.abs(j), Math.abs(k), pRange)) {
                    blockpos$mutableblockpos.setWithOffset(pPos, j, pLocalY, k);
                    tryPlaceLeaf(pLevel, pFoliageSetter, pRandom, pTreeConfiguration, blockpos$mutableblockpos);
                }
            }
        }
    }

    private boolean shouldSkipLocationAlt(int pLocalX, int pLocalZ, int pRange) {
        int r = pRange - 1;
        return pLocalX >= r && pLocalZ >= r && !(pLocalX == r && pLocalZ == r) && pRange > 0;
    }
}
