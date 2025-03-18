package com.ChalkerCharles.morecolorful.common.worldgen.placements;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeFeatures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;
import java.util.function.Supplier;

import static com.ChalkerCharles.morecolorful.common.worldgen.placements.ModPlacedFeatures.registerKey;
import static net.minecraft.data.worldgen.placement.PlacementUtils.register;

public class ModTreePlacements {
    public static final ResourceKey<PlacedFeature> CRABAPPLE = registerKey("crabapple");
    public static final ResourceKey<PlacedFeature> CRABAPPLE_005 = registerKey("crabapple_005");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY = registerKey("white_cherry");
    public static final ResourceKey<PlacedFeature> WHITE_CHERRY_005 = registerKey("white_cherry_005");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH = registerKey("orange_birch");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH_0002 = registerKey("orange_birch_0002");
    public static final ResourceKey<PlacedFeature> ORANGE_BIRCH_005 = registerKey("orange_birch_005");
    public static final ResourceKey<PlacedFeature> TALL_ORANGE_BIRCH_0002 = registerKey("tall_orange_birch_0002");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH = registerKey("yellow_birch");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH_0002 = registerKey("yellow_birch_0002");
    public static final ResourceKey<PlacedFeature> YELLOW_BIRCH_005 = registerKey("yellow_birch_005");
    public static final ResourceKey<PlacedFeature> TALL_YELLOW_BIRCH_0002 = registerKey("tall_yellow_birch_0002");
    public static final ResourceKey<PlacedFeature> GINKGO = registerKey("ginkgo");
    public static final ResourceKey<PlacedFeature> FANCY_GINKGO = registerKey("fancy_ginkgo");
    public static final ResourceKey<PlacedFeature> MAPLE = registerKey("maple");
    public static final ResourceKey<PlacedFeature> FANCY_MAPLE = registerKey("fancy_maple");
    public static final ResourceKey<PlacedFeature> FROST = registerKey("frost");
    public static final ResourceKey<PlacedFeature> DAWN_REDWOOD = registerKey("dawn_redwood");
    public static final ResourceKey<PlacedFeature> JACARANDA = registerKey("jacaranda");
    public static final ResourceKey<PlacedFeature> JACARANDA_005 = registerKey("jacaranda_005");
    public static final ResourceKey<PlacedFeature> JACARANDA_BEES = registerKey("jacaranda_bees");
    public static final ResourceKey<PlacedFeature> OAK_BEES = registerKey("oak_bees");
    public static final ResourceKey<PlacedFeature> BIRCH_BEES = registerKey("birch_bees");
    public static final ResourceKey<PlacedFeature> CHERRY_BEES = registerKey("cherry_bees");
    public static final ResourceKey<PlacedFeature> WILLOW = registerKey("willow");
    public static final ResourceKey<PlacedFeature> FANCY_WILLOW = registerKey("fancy_willow");
    public static final ResourceKey<PlacedFeature> SWAMP_OAK = registerKey("swamp_oak");

    private static List<PlacementModifier> saplingCheck(Supplier<SaplingBlock> block) {
        return saplingCheck(block.get());
    }
    private static List<PlacementModifier> saplingCheck(Block block) {
        return List.of(PlacementUtils.filteredByBlockSurvival(block));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> features = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, CRABAPPLE, features.getOrThrow(ModTreeFeatures.CRABAPPLE), saplingCheck(ModBlocks.CRABAPPLE_SAPLING));
        register(context, CRABAPPLE_005, features.getOrThrow(ModTreeFeatures.CRABAPPLE_005), saplingCheck(ModBlocks.CRABAPPLE_SAPLING));
        register(context, WHITE_CHERRY, features.getOrThrow(ModTreeFeatures.WHITE_CHERRY), saplingCheck(ModBlocks.WHITE_CHERRY_SAPLING));
        register(context, WHITE_CHERRY_005, features.getOrThrow(ModTreeFeatures.WHITE_CHERRY_005), saplingCheck(ModBlocks.WHITE_CHERRY_SAPLING));
        register(context, ORANGE_BIRCH, features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH), saplingCheck(ModBlocks.ORANGE_BIRCH_SAPLING));
        register(context, ORANGE_BIRCH_0002, features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH_0002), saplingCheck(ModBlocks.ORANGE_BIRCH_SAPLING));
        register(context, ORANGE_BIRCH_005, features.getOrThrow(ModTreeFeatures.ORANGE_BIRCH_005), saplingCheck(ModBlocks.ORANGE_BIRCH_SAPLING));
        register(context, TALL_ORANGE_BIRCH_0002, features.getOrThrow(ModTreeFeatures.TALL_ORANGE_BIRCH_0002), saplingCheck(ModBlocks.ORANGE_BIRCH_SAPLING));
        register(context, YELLOW_BIRCH, features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH), saplingCheck(ModBlocks.YELLOW_BIRCH_SAPLING));
        register(context, YELLOW_BIRCH_0002, features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH_0002), saplingCheck(ModBlocks.YELLOW_BIRCH_SAPLING));
        register(context, YELLOW_BIRCH_005, features.getOrThrow(ModTreeFeatures.YELLOW_BIRCH_005), saplingCheck(ModBlocks.YELLOW_BIRCH_SAPLING));
        register(context, TALL_YELLOW_BIRCH_0002, features.getOrThrow(ModTreeFeatures.TALL_YELLOW_BIRCH_0002), saplingCheck(ModBlocks.YELLOW_BIRCH_SAPLING));
        register(context, GINKGO, features.getOrThrow(ModTreeFeatures.GINKGO), saplingCheck(ModBlocks.GINKGO_SAPLING));
        register(context, FANCY_GINKGO, features.getOrThrow(ModTreeFeatures.FANCY_GINKGO), saplingCheck(ModBlocks.GINKGO_SAPLING));
        register(context, MAPLE, features.getOrThrow(ModTreeFeatures.MAPLE), saplingCheck(ModBlocks.MAPLE_SAPLING));
        register(context, FANCY_MAPLE, features.getOrThrow(ModTreeFeatures.FANCY_MAPLE), saplingCheck(ModBlocks.MAPLE_SAPLING));
        register(context, FROST, features.getOrThrow(ModTreeFeatures.FROST), saplingCheck(ModBlocks.FROST_SAPLING));
        register(context, DAWN_REDWOOD, features.getOrThrow(ModTreeFeatures.DAWN_REDWOOD), saplingCheck(ModBlocks.DAWN_REDWOOD_SAPLING));
        register(context, JACARANDA, features.getOrThrow(ModTreeFeatures.JACARANDA), saplingCheck(ModBlocks.JACARANDA_SAPLING));
        register(context, JACARANDA_005, features.getOrThrow(ModTreeFeatures.JACARANDA_005), saplingCheck(ModBlocks.JACARANDA_SAPLING));
        register(context, JACARANDA_BEES, features.getOrThrow(ModTreeFeatures.JACARANDA_BEES), saplingCheck(ModBlocks.JACARANDA_SAPLING));
        register(context, OAK_BEES, features.getOrThrow(ModTreeFeatures.OAK_BEES), saplingCheck(Blocks.OAK_SAPLING));
        register(context, BIRCH_BEES, features.getOrThrow(ModTreeFeatures.BIRCH_BEES), saplingCheck(Blocks.BIRCH_SAPLING));
        register(context, CHERRY_BEES, features.getOrThrow(ModTreeFeatures.CHERRY_BEES), saplingCheck(Blocks.CHERRY_SAPLING));
        register(context, WILLOW, features.getOrThrow(ModTreeFeatures.WILLOW), saplingCheck(ModBlocks.WILLOW_SAPLING));
        register(context, FANCY_WILLOW, features.getOrThrow(ModTreeFeatures.FANCY_WILLOW), saplingCheck(ModBlocks.WILLOW_SAPLING));
        register(context, SWAMP_OAK, features.getOrThrow(TreeFeatures.SWAMP_OAK), saplingCheck(Blocks.OAK_SAPLING));
    }
}
