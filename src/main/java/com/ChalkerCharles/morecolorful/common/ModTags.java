package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> QUARTZ_BLOCKS = create("quartz_blocks");
        public static final TagKey<Block> PRISMARINES = create("prismarines");
        public static final TagKey<Block> NETHER_FUNGUS_WOODEN_BLOCKS = create("nether_fungus_wooden_blocks");
        public static final TagKey<Block> COPPER_BLOCKS = create("copper_blocks");
        public static final TagKey<Block> COPPER_GRATES = create("copper_grates");
        public static final TagKey<Block> TUFF_BLOCKS = create("tuff_blocks");
        public static final TagKey<Block> MUSHROOM_BLOCKS = create("mushroom_blocks");
        public static final TagKey<Block> BASALT_BLOCKS = create("basalt_blocks");
        private static TagKey<Block> create(String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
        }
    }

    @SuppressWarnings("unused")
    public static class Items {
        public static final TagKey<Item> DRUM_SET_PARTS = create("drum_set_parts");
        @SuppressWarnings("SameParameterValue")
        private static TagKey<Item> create(String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
        }
    }
}
