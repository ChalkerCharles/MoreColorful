package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Blocks
    public static final TagKey<Block> QUARTZ_BLOCKS = createTag("quartz_blocks");
    public static final TagKey<Block> PRISMARINES = createTag("prismarines");
    public static final TagKey<Block> NETHER_FUNGUS_WOODEN_BLOCKS = createTag("nether_fungus_wooden_blocks");
    public static final TagKey<Block> COPPER_BLOCKS = createTag("copper_blocks");
    public static final TagKey<Block> COPPER_GRATES = createTag("copper_grates");
    public static final TagKey<Block> TUFF_BLOCKS = createTag("tuff_blocks");
    public static final TagKey<Block> MUSHROOM_BLOCKS = createTag("mushroom_blocks");
    public static final TagKey<Block> BASALT_BLOCKS = createTag("basalt_blocks");

    private static TagKey<Block> createTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
    }
}
