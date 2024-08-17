package com.ChalkerCharles.morecolorful.common.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {
    // Blocks
    public static final TagKey<Block> QUARTZ_BLOCKS = createTag("quartz_blocks");
    public static final TagKey<Block> NETHER_FUNGUS_WOODEN_BLOCKS = createTag("nether_fungus_wooden_blocks");
    public static final TagKey<Block> COPPER_BLOCKS = createTag("copper_blocks");

    private static TagKey<Block> createTag(String path) {
        return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
    }
}
