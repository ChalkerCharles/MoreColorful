package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.function.Supplier;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<PlacedFeature> CRABAPPLE_005 = registerKey("crabapple_005");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY = registerKey("white_cherry");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY_005 = registerKey("white_cherry_005");
    public static final ResourceKey<PlacedFeature> AUTUMN_BIRCH = registerKey("autumn_birch");
    public static final ResourceKey<PlacedFeature> AUTUMN_BIRCH_0002 = registerKey("autumn_birch_0002");
    public static final ResourceKey<PlacedFeature> AUTUMN_BIRCH_005 = registerKey("autumn_birch_005");
    public static final ResourceKey<PlacedFeature> GINKGO = registerKey("ginkgo");
    public static final ResourceKey<PlacedFeature> FANCY_GINKGO = registerKey("fancy_ginkgo");
    public static final ResourceKey<PlacedFeature> MAPLE = registerKey("maple");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE = registerKey("fancy_maple");

    private static List<PlacementModifier> getSapling(Supplier<SaplingBlock> block) {
        return List.of(PlacementUtils.filteredByBlockSurvival(block.get()));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        context.register(CRABAPPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
        context.register(CRABAPPLE_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.CRABAPPLE_005), getSapling(ModBlocks.CRABAPPLE_SAPLING)));
        context.register(WHITE_CHERRY, new PlacedFeature(features.getOrThrow(ModTreeFeatures.WHITE_CHERRY), getSapling(ModBlocks.WHITE_CHERRY_SAPLING)));
        context.register(WHITE_CHERRY_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.WHITE_CHERRY_005), getSapling(ModBlocks.WHITE_CHERRY_SAPLING)));
        context.register(AUTUMN_BIRCH, new PlacedFeature(features.getOrThrow(ModTreeFeatures.AUTUMN_BIRCH), getSapling(ModBlocks.AUTUMN_BIRCH_SAPLING)));
        context.register(AUTUMN_BIRCH_0002, new PlacedFeature(features.getOrThrow(ModTreeFeatures.AUTUMN_BIRCH_0002), getSapling(ModBlocks.AUTUMN_BIRCH_SAPLING)));
        context.register(AUTUMN_BIRCH_005, new PlacedFeature(features.getOrThrow(ModTreeFeatures.AUTUMN_BIRCH_005), getSapling(ModBlocks.AUTUMN_BIRCH_SAPLING)));
        context.register(GINKGO, new PlacedFeature(features.getOrThrow(ModTreeFeatures.GINKGO), getSapling(ModBlocks.GINKGO_SAPLING)));
        context.register(FANCY_GINKGO, new PlacedFeature(features.getOrThrow(ModTreeFeatures.FANCY_GINKGO), getSapling(ModBlocks.GINKGO_SAPLING)));
        context.register(MAPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.MAPLE), getSapling(ModBlocks.MAPLE_SAPLING)));
        context.register(FANCY_MAPLE, new PlacedFeature(features.getOrThrow(ModTreeFeatures.FANCY_MAPLE), getSapling(ModBlocks.MAPLE_SAPLING)));
    }
}
