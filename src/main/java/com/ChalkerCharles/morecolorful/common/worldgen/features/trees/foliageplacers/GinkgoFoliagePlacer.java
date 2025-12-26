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

public class GinkgoFoliagePlacer extends FoliagePlacer {
    public static final MapCodec<GinkgoFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
            inst -> foliagePlacerParts(inst).and(inst.group(
                    IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter(p1 -> p1.foliageHeight),
                    Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter(p2 -> p2.leafPlacementAttempts)
            )).apply(inst, GinkgoFoliagePlacer::new)
    );
    private final IntProvider foliageHeight;
    private final int leafPlacementAttempts;

    public GinkgoFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider foliageHeight, int leafPlacementAttempts) {
        super(radius, offset);
        this.foliageHeight = foliageHeight;
        this.leafPlacementAttempts = leafPlacementAttempts;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ModFoliagePlacers.GINKGO_FOLIAGE_PLACER.get();
    }

    @Override
    protected void createFoliage(
            LevelSimulatedReader pLevel,
            FoliagePlacer.FoliageSetter pBlockSetter,
            RandomSource pRandom,
            TreeConfiguration pConfig,
            int pMaxFreeTreeHeight,
            FoliagePlacer.FoliageAttachment pAttachment,
            int pFoliageHeight,
            int pFoliageRadius,
            int pOffset) {
        BlockPos blockpos = pAttachment.pos();
        BlockPos.MutableBlockPos mutableBlockPos = blockpos.mutable();

        for (int i = -pFoliageHeight + 1; i < pFoliageHeight; i++) {
            for (int j = 0; j < this.leafPlacementAttempts; j++) {
                mutableBlockPos.setWithOffset(
                        blockpos,
                        pRandom.nextInt(pFoliageRadius) - pRandom.nextInt(pFoliageRadius),
                        i,
                        pRandom.nextInt(pFoliageRadius) - pRandom.nextInt(pFoliageRadius)
                );
                tryPlaceLeaf(pLevel, pBlockSetter, pRandom, pConfig, mutableBlockPos);
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return this.foliageHeight.sample(pRandom);
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
