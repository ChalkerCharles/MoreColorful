package com.ChalkerCharles.morecolorful.common.worldgen.features.trees;

import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class ModTreeGrower {
    public static final TreeGrower CRABAPPLE = new TreeGrower("crabapple",
            Optional.empty(),
            Optional.of(ModTreeFeatures.CRABAPPLE),
            Optional.of(ModTreeFeatures.CRABAPPLE_BEE));

}
