package com.ChalkerCharles.morecolorful.common;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public abstract class ModTags {
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
        public static final TagKey<Block> LEAF_LITTERS = create("leaf_litters");
        public static final TagKey<Block> GINKGO_LOGS = create("ginkgo_logs");
        public static final TagKey<Block> MAPLE_LOGS = create("maple_logs");
        public static final TagKey<Block> FROST_LOGS = create("frost_logs");
        public static final TagKey<Block> DAWN_REDWOOD_LOGS = create("dawn_redwood_logs");
        public static final TagKey<Block> JACARANDA_LOGS = create("jacaranda_logs");
        public static final TagKey<Block> WILLOW_LOGS = create("willow_logs");
        public static final TagKey<Block> LAKES_CANNOT_REPLACE = create("lakes_cannot_replace");

        private static TagKey<Block> create(String path) {
            return BlockTags.create(MoreColorful.location(path));
        }
    }

    public static class Items {
        public static final TagKey<Item> DRUM_SET_PARTS = create("drum_set_parts");
        public static final TagKey<Item> CRABAPPLE_LOGS = create("crabapple_logs");
        public static final TagKey<Item> EBONY_LOGS = create("ebony_logs");
        public static final TagKey<Item> RARE_PLANKS = create("rare_planks");
        public static final TagKey<Item> STRIPPED_RARE_LOGS = create("stripped_rare_logs");
        public static final TagKey<Item> LEAF_LITTERS = create("leaf_litters");
        public static final TagKey<Item> GINKGO_LOGS = create("ginkgo_logs");
        public static final TagKey<Item> MAPLE_LOGS = create("maple_logs");
        public static final TagKey<Item> FROST_LOGS = create("frost_logs");
        public static final TagKey<Item> DAWN_REDWOOD_LOGS = create("dawn_redwood_logs");
        public static final TagKey<Item> JACARANDA_LOGS = create("jacaranda_logs");
        public static final TagKey<Item> WILLOW_LOGS = create("willow_logs");

        private static TagKey<Item> create(String path) {
            return ItemTags.create(MoreColorful.location(path));
        }
    }

    public static class Biomes {
        public static final TagKey<Biome> IS_WINDY = create("is_windy");

        private static TagKey<Biome> create(String path) {
            return TagKey.create(Registries.BIOME, MoreColorful.location(path));
        }
    }
}
