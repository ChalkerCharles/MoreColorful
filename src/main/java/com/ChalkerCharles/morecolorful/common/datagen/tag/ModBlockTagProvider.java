package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void addTags(HolderLookup.Provider pProvider) {
        // Vanilla Tags
        tag(BlockTags.MINEABLE_WITH_AXE).add(
                ModBlocks.BASS_DRUM.get(),
                ModBlocks.TOMTOM_DRUM.get(),
                ModBlocks.GUZHENG.get()
        );
        tag(BlockTags.MINEABLE_WITH_HOE).addTag(
                ModTags.Blocks.LEAF_PILES
        ).add(
                ModBlocks.CRABAPPLE_LEAVES.get(),
                ModBlocks.BEGONIAS.get(),
                ModBlocks.WHITE_CHERRY_LEAVES.get(),
                ModBlocks.WHITE_PETALS.get(),
                ModBlocks.AUTUMN_BIRCH_LEAVES.get(),
                ModBlocks.GINKGO_LEAVES.get(),
                ModBlocks.MAPLE_LEAVES.get()
        );
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                ModBlocks.HARP.get(),
                ModBlocks.GRAND_PIANO.get(),
                ModBlocks.UPRIGHT_PIANO.get(),
                ModBlocks.SNARE_DRUM.get(),
                ModBlocks.HIHAT.get(),
                ModBlocks.RIDE_CYMBAL.get(),
                ModBlocks.CRASH_CYMBAL.get(),
                ModBlocks.DRUM_SET.get(),
                ModBlocks.CHIMES.get(),
                ModBlocks.GLOCKENSPIEL.get(),
                ModBlocks.XYLOPHONE.get(),
                ModBlocks.VIBRAPHONE.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_BIT.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_PLING.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_SCULK.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_SAW.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK.get(),
                ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS.get()
        );
        tag(BlockTags.CEILING_HANGING_SIGNS).add(
                ModBlocks.CRABAPPLE_HANGING_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.GINKGO_HANGING_SIGN.get(),
                ModBlocks.MAPLE_HANGING_SIGN.get()
        );
        tag(BlockTags.FENCE_GATES).add(
                ModBlocks.CRABAPPLE_FENCE_GATE.get(),
                ModBlocks.EBONY_FENCE_GATE.get(),
                ModBlocks.GINKGO_FENCE_GATE.get(),
                ModBlocks.MAPLE_FENCE_GATE.get()
        );
        tag(BlockTags.FLOWER_POTS).add(
                ModBlocks.POTTED_CRABAPPLE_SAPLING.get(),
                ModBlocks.POTTED_WHITE_CHERRY_SAPLING.get(),
                ModBlocks.POTTED_AUTUMN_BIRCH_SAPLING.get(),
                ModBlocks.POTTED_GINKGO_SAPLING.get(),
                ModBlocks.POTTED_MAPLE_SAPLING.get(),
                ModBlocks.POTTED_PINK_DAISY.get(),
                ModBlocks.POTTED_RED_CARNATION.get(),
                ModBlocks.POTTED_PINK_CARNATION.get(),
                ModBlocks.POTTED_WHITE_CARNATION.get(),
                ModBlocks.POTTED_RED_SPIDER_LILY.get(),
                ModBlocks.POTTED_YELLOW_CHRYSANTHEMUM.get(),
                ModBlocks.POTTED_GREEN_CHRYSANTHEMUM.get(),
                ModBlocks.POTTED_DAYBLOOM.get()
        );
        tag(BlockTags.FLOWERS).add(
                ModBlocks.CRABAPPLE_LEAVES.get(),
                ModBlocks.BEGONIAS.get(),
                ModBlocks.WHITE_CHERRY_LEAVES.get(),
                ModBlocks.WHITE_PETALS.get()
        );
        tag(BlockTags.INSIDE_STEP_SOUND_BLOCKS).addTag(
                ModTags.Blocks.LEAF_PILES
        ).add(
                ModBlocks.BEGONIAS.get(),
                ModBlocks.WHITE_PETALS.get()
        );
        tag(BlockTags.LEAVES).add(
                ModBlocks.CRABAPPLE_LEAVES.get(),
                ModBlocks.WHITE_CHERRY_LEAVES.get(),
                ModBlocks.AUTUMN_BIRCH_LEAVES.get(),
                ModBlocks.GINKGO_LEAVES.get(),
                ModBlocks.MAPLE_LEAVES.get()
        );
        tag(BlockTags.LOGS_THAT_BURN).addTags(
                ModTags.Blocks.CRABAPPLE_LOGS,
                ModTags.Blocks.EBONY_LOGS,
                ModTags.Blocks.GINKGO_LOGS,
                ModTags.Blocks.MAPLE_LOGS
        );
        tag(BlockTags.OVERWORLD_NATURAL_LOGS).add(
                ModBlocks.CRABAPPLE_LOG.get(),
                ModBlocks.GINKGO_LOG.get(),
                ModBlocks.MAPLE_LOG.get()
        );
        tag(BlockTags.PLANKS).add(
                ModBlocks.CRABAPPLE_PLANKS.get(),
                ModBlocks.EBONY_PLANKS.get(),
                ModBlocks.GINKGO_PLANKS.get(),
                ModBlocks.MAPLE_PLANKS.get()
        );
        tag(BlockTags.REPLACEABLE).addTag(
                ModTags.Blocks.LEAF_PILES
        );
        tag(BlockTags.REPLACEABLE_BY_TREES).addTag(
                ModTags.Blocks.LEAF_PILES
        );
        tag(BlockTags.SAPLINGS).add(
                ModBlocks.CRABAPPLE_SAPLING.get(),
                ModBlocks.WHITE_CHERRY_SAPLING.get(),
                ModBlocks.AUTUMN_BIRCH_SAPLING.get(),
                ModBlocks.GINKGO_SAPLING.get(),
                ModBlocks.MAPLE_SAPLING.get()
        );
        tag(BlockTags.SMALL_FLOWERS).add(
                ModBlocks.PINK_DAISY.get(),
                ModBlocks.RED_CARNATION.get(),
                ModBlocks.PINK_CARNATION.get(),
                ModBlocks.WHITE_CARNATION.get(),
                ModBlocks.RED_SPIDER_LILY.get(),
                ModBlocks.YELLOW_CHRYSANTHEMUM.get(),
                ModBlocks.GREEN_CHRYSANTHEMUM.get(),
                ModBlocks.DAYBLOOM.get()
        );
        tag(BlockTags.STANDING_SIGNS).add(
                ModBlocks.CRABAPPLE_SIGN.get(),
                ModBlocks.EBONY_SIGN.get(),
                ModBlocks.GINKGO_SIGN.get(),
                ModBlocks.MAPLE_SIGN.get()
        );
        tag(BlockTags.SWORD_EFFICIENT).addTag(
                ModTags.Blocks.LEAF_PILES
        ).add(
                ModBlocks.BEGONIAS.get(),
                ModBlocks.WHITE_PETALS.get()
        );
        tag(BlockTags.WALL_HANGING_SIGNS).add(
                ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get(),
                ModBlocks.GINKGO_WALL_HANGING_SIGN.get(),
                ModBlocks.MAPLE_WALL_HANGING_SIGN.get()
        );
        tag(BlockTags.WALL_SIGNS).add(
                ModBlocks.CRABAPPLE_WALL_SIGN.get(),
                ModBlocks.EBONY_WALL_SIGN.get(),
                ModBlocks.GINKGO_WALL_SIGN.get(),
                ModBlocks.MAPLE_WALL_SIGN.get()
        );
        tag(BlockTags.WOODEN_BUTTONS).add(
                ModBlocks.CRABAPPLE_BUTTON.get(),
                ModBlocks.EBONY_BUTTON.get(),
                ModBlocks.GINKGO_BUTTON.get(),
                ModBlocks.MAPLE_BUTTON.get()
        );
        tag(BlockTags.WOODEN_DOORS).add(
                ModBlocks.CRABAPPLE_DOOR.get(),
                ModBlocks.EBONY_DOOR.get(),
                ModBlocks.GINKGO_DOOR.get(),
                ModBlocks.MAPLE_DOOR.get()
        );
        tag(BlockTags.WOODEN_FENCES).add(
                ModBlocks.CRABAPPLE_FENCE.get(),
                ModBlocks.EBONY_FENCE.get(),
                ModBlocks.GINKGO_FENCE.get(),
                ModBlocks.MAPLE_FENCE.get()
        );
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(
                ModBlocks.CRABAPPLE_PRESSURE_PLATE.get(),
                ModBlocks.EBONY_PRESSURE_PLATE.get(),
                ModBlocks.GINKGO_PRESSURE_PLATE.get(),
                ModBlocks.MAPLE_PRESSURE_PLATE.get()
        );
        tag(BlockTags.WOODEN_SLABS).add(
                ModBlocks.CRABAPPLE_SLAB.get(),
                ModBlocks.EBONY_SLAB.get(),
                ModBlocks.GINKGO_SLAB.get(),
                ModBlocks.MAPLE_SLAB.get()
        );
        tag(BlockTags.WOODEN_STAIRS).add(
                ModBlocks.CRABAPPLE_STAIRS.get(),
                ModBlocks.EBONY_STAIRS.get(),
                ModBlocks.GINKGO_STAIRS.get(),
                ModBlocks.MAPLE_STAIRS.get()
        );
        tag(BlockTags.WOODEN_TRAPDOORS).add(
                ModBlocks.CRABAPPLE_TRAPDOOR.get(),
                ModBlocks.EBONY_TRAPDOOR.get(),
                ModBlocks.GINKGO_TRAPDOOR.get(),
                ModBlocks.MAPLE_TRAPDOOR.get()
        );

        // More Colorful Tags
        tag(ModTags.Blocks.BASALT_BLOCKS).add(
                Blocks.BASALT,
                Blocks.POLISHED_BASALT,
                Blocks.SMOOTH_BASALT
        );
        tag(ModTags.Blocks.COPPER_BLOCKS).add(
                Blocks.COPPER_BLOCK,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.EXPOSED_COPPER,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WEATHERED_COPPER,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.OXIDIZED_COPPER,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.CUT_COPPER,
                Blocks.WAXED_CUT_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.CHISELED_COPPER,
                Blocks.WAXED_CHISELED_COPPER,
                Blocks.EXPOSED_CHISELED_COPPER,
                Blocks.WAXED_EXPOSED_CHISELED_COPPER,
                Blocks.WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.OXIDIZED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
                Blocks.CUT_COPPER_STAIRS,
                Blocks.WAXED_CUT_COPPER_STAIRS,
                Blocks.EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WEATHERED_CUT_COPPER_STAIRS,
                Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Blocks.OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.CUT_COPPER_SLAB,
                Blocks.WAXED_CUT_COPPER_SLAB,
                Blocks.EXPOSED_CUT_COPPER_SLAB,
                Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Blocks.WEATHERED_CUT_COPPER_SLAB,
                Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Blocks.OXIDIZED_CUT_COPPER_SLAB,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Blocks.COPPER_DOOR,
                Blocks.WAXED_COPPER_DOOR,
                Blocks.EXPOSED_COPPER_DOOR,
                Blocks.WAXED_EXPOSED_COPPER_DOOR,
                Blocks.WEATHERED_COPPER_DOOR,
                Blocks.WAXED_WEATHERED_COPPER_DOOR,
                Blocks.OXIDIZED_COPPER_DOOR,
                Blocks.WAXED_WEATHERED_COPPER_DOOR,
                Blocks.COPPER_TRAPDOOR,
                Blocks.WAXED_COPPER_TRAPDOOR,
                Blocks.EXPOSED_COPPER_TRAPDOOR,
                Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                Blocks.WEATHERED_COPPER_TRAPDOOR,
                Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                Blocks.OXIDIZED_COPPER_TRAPDOOR,
                Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR
        );
        tag(ModTags.Blocks.COPPER_GRATES).add(
                Blocks.COPPER_GRATE,
                Blocks.WAXED_COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE
        );
        tag(ModTags.Blocks.CRABAPPLE_LOGS).add(
                ModBlocks.CRABAPPLE_LOG.get(),
                ModBlocks.CRABAPPLE_WOOD.get(),
                ModBlocks.STRIPPED_CRABAPPLE_LOG.get(),
                ModBlocks.STRIPPED_CRABAPPLE_WOOD.get()
        );
        tag(ModTags.Blocks.EBONY_LOGS).add(
                ModBlocks.EBONY_LOG.get(),
                ModBlocks.EBONY_WOOD.get(),
                ModBlocks.STRIPPED_EBONY_LOG.get(),
                ModBlocks.STRIPPED_EBONY_WOOD.get()
        );
        tag(ModTags.Blocks.MUSHROOM_BLOCKS).add(
                Blocks.RED_MUSHROOM_BLOCK,
                Blocks.BROWN_MUSHROOM_BLOCK,
                Blocks.MUSHROOM_STEM
        );
        tag(ModTags.Blocks.NETHER_FUNGUS_WOODEN_BLOCKS).addTags(
                BlockTags.CRIMSON_STEMS,
                BlockTags.WARPED_STEMS
        ).add(
                Blocks.CRIMSON_BUTTON,
                Blocks.CRIMSON_DOOR,
                Blocks.CRIMSON_FENCE,
                Blocks.CRIMSON_FENCE_GATE,
                Blocks.CRIMSON_HANGING_SIGN,
                Blocks.CRIMSON_PLANKS,
                Blocks.CRIMSON_PRESSURE_PLATE,
                Blocks.CRIMSON_SIGN,
                Blocks.CRIMSON_SLAB,
                Blocks.CRIMSON_STAIRS,
                Blocks.CRIMSON_TRAPDOOR,
                Blocks.CRIMSON_WALL_HANGING_SIGN,
                Blocks.CRIMSON_WALL_SIGN,
                Blocks.WARPED_BUTTON,
                Blocks.WARPED_DOOR,
                Blocks.WARPED_FENCE,
                Blocks.WARPED_FENCE_GATE,
                Blocks.WARPED_HANGING_SIGN,
                Blocks.WARPED_PLANKS,
                Blocks.WARPED_PRESSURE_PLATE,
                Blocks.WARPED_SIGN,
                Blocks.WARPED_SLAB,
                Blocks.WARPED_STAIRS,
                Blocks.WARPED_TRAPDOOR,
                Blocks.WARPED_WALL_HANGING_SIGN,
                Blocks.WARPED_WALL_SIGN
        );
        tag(ModTags.Blocks.PACKED_MUD_BLOCKS).add(
                Blocks.PACKED_MUD,
                Blocks.MUD_BRICKS,
                Blocks.MUD_BRICK_STAIRS,
                Blocks.MUD_BRICK_SLAB,
                Blocks.MUD_BRICK_WALL
        );
        tag(ModTags.Blocks.PRISMARINES).add(
                Blocks.PRISMARINE,
                Blocks.PRISMARINE_STAIRS,
                Blocks.PRISMARINE_SLAB,
                Blocks.PRISMARINE_WALL,
                Blocks.PRISMARINE_BRICKS,
                Blocks.PRISMARINE_BRICK_STAIRS,
                Blocks.PRISMARINE_BRICK_SLAB,
                Blocks.DARK_PRISMARINE,
                Blocks.DARK_PRISMARINE_STAIRS,
                Blocks.DARK_PRISMARINE_SLAB,
                Blocks.SEA_LANTERN
        );
        tag(ModTags.Blocks.QUARTZ_BLOCKS).add(
                Blocks.QUARTZ_BLOCK,
                Blocks.QUARTZ_STAIRS,
                Blocks.QUARTZ_SLAB,
                Blocks.QUARTZ_PILLAR,
                Blocks.CHISELED_QUARTZ_BLOCK,
                Blocks.QUARTZ_BRICKS,
                Blocks.SMOOTH_QUARTZ,
                Blocks.SMOOTH_QUARTZ_STAIRS,
                Blocks.SMOOTH_QUARTZ_SLAB
        );
        tag(ModTags.Blocks.RARE_WOOD).addTags(
                ModTags.Blocks.EBONY_LOGS
        ).add(
                ModBlocks.EBONY_PLANKS.get(),
                ModBlocks.EBONY_STAIRS.get(),
                ModBlocks.EBONY_SLAB.get(),
                ModBlocks.EBONY_FENCE.get(),
                ModBlocks.EBONY_FENCE_GATE.get(),
                ModBlocks.EBONY_DOOR.get(),
                ModBlocks.EBONY_TRAPDOOR.get(),
                ModBlocks.EBONY_PRESSURE_PLATE.get(),
                ModBlocks.EBONY_BUTTON.get(),
                ModBlocks.EBONY_SIGN.get(),
                ModBlocks.EBONY_WALL_SIGN.get(),
                ModBlocks.EBONY_HANGING_SIGN.get(),
                ModBlocks.EBONY_WALL_HANGING_SIGN.get()
        );
        tag(ModTags.Blocks.TUFF_BLOCKS).add(
                Blocks.TUFF,
                Blocks.TUFF_STAIRS,
                Blocks.TUFF_SLAB,
                Blocks.TUFF_WALL,
                Blocks.CHISELED_TUFF,
                Blocks.POLISHED_TUFF,
                Blocks.POLISHED_TUFF_STAIRS,
                Blocks.POLISHED_TUFF_SLAB,
                Blocks.POLISHED_TUFF_WALL,
                Blocks.TUFF_BRICKS,
                Blocks.TUFF_BRICK_STAIRS,
                Blocks.TUFF_BRICK_SLAB,
                Blocks.TUFF_BRICK_WALL,
                Blocks.CHISELED_TUFF_BRICKS
        );
        tag(ModTags.Blocks.LEAF_PILES).add(
                ModBlocks.AUTUMN_BIRCH_LEAF_PILE.get(),
                ModBlocks.GINKGO_LEAF_PILE.get(),
                ModBlocks.MAPLE_LEAF_PILE.get()
        );
        tag(ModTags.Blocks.GINKGO_LOGS).add(
                ModBlocks.GINKGO_LOG.get(),
                ModBlocks.GINKGO_WOOD.get(),
                ModBlocks.STRIPPED_GINKGO_LOG.get(),
                ModBlocks.STRIPPED_GINKGO_WOOD.get()
        );
        tag(ModTags.Blocks.MAPLE_LOGS).add(
                ModBlocks.MAPLE_LOG.get(),
                ModBlocks.MAPLE_WOOD.get(),
                ModBlocks.STRIPPED_MAPLE_LOG.get(),
                ModBlocks.STRIPPED_MAPLE_WOOD.get()
        );
    }
}
