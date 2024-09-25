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
        public static final TagKey<Block> PACKED_MUD_BLOCKS = create("packed_mud_blocks");
        public static final TagKey<Block> CRABAPPLE_LOGS = create("crabapple_logs");
        public static final TagKey<Block> EBONY_LOGS = create("ebony_logs");
        public static final TagKey<Block> RARE_WOOD = create("rare_wood");
        private static TagKey<Block> create(String path) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
        }
    }

    public static class Items {
        public static final TagKey<Item> DRUM_SET_PARTS = create("drum_set_parts");
        public static final TagKey<Item> CRABAPPLE_LOGS = create("crabapple_logs");
        public static final TagKey<Item> EBONY_LOGS = create("ebony_logs");
        public static final TagKey<Item> RARE_PLANKS = create("rare_planks");
        public static final TagKey<Item> STRIPPED_RARE_LOGS = create("stripped_rare_logs");
        private static TagKey<Item> create(String path) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(MoreColorful.MODID, path));
        }
    }
}
