package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
    public static final TreeGrower CRABAPPLE = new TreeGrower("crabapple",
            Optional.empty(),
            Optional.of(ModTreeFeatures.CRABAPPLE),
            Optional.of(ModTreeFeatures.CRABAPPLE_005));
    public static final TreeGrower WHITE_CHERRY = new TreeGrower("white_cherry",
            Optional.empty(),
            Optional.of(ModTreeFeatures.WHITE_CHERRY),
            Optional.of(ModTreeFeatures.WHITE_CHERRY_005));
    public static final TreeGrower ORANGE_BIRCH = new TreeGrower("orange_birch",
            Optional.empty(),
            Optional.of(ModTreeFeatures.ORANGE_BIRCH),
            Optional.of(ModTreeFeatures.ORANGE_BIRCH_005));
    public static final TreeGrower YELLOW_BIRCH = new TreeGrower("yellow_birch",
            Optional.empty(),
            Optional.of(ModTreeFeatures.YELLOW_BIRCH),
            Optional.of(ModTreeFeatures.YELLOW_BIRCH_005));
    public static final TreeGrower GINKGO = new TreeGrower("ginkgo",
            0.1F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModTreeFeatures.GINKGO),
            Optional.of(ModTreeFeatures.FANCY_GINKGO),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower MAPLE = new TreeGrower("maple",
            0.3F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ModTreeFeatures.MAPLE),
            Optional.of(ModTreeFeatures.FANCY_MAPLE),
            Optional.empty(),
            Optional.empty());
    public static final TreeGrower FROST = new TreeGrower("frost",
            Optional.empty(),
            Optional.of(ModTreeFeatures.FROST),
            Optional.empty());
    public static final TreeGrower DAWN_REDWOOD = new TreeGrower("dawn_redwood",
            Optional.empty(),
            Optional.of(ModTreeFeatures.DAWN_REDWOOD),
            Optional.empty());
}
