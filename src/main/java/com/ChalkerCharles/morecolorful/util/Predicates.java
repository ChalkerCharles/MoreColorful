package com.ChalkerCharles.morecolorful.util;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public final class Predicates {
    public static boolean blockMatches(Block block, Block... blocks) {
        return Arrays.asList(blocks).contains(block);
    }

    public static boolean blockMatches(BlockState state, Block... blocks) {
        return Arrays.stream(blocks).anyMatch(state::is);
    }

    @SafeVarargs
    public static <T> boolean tagMatches(Holder.Reference<T> holder, TagKey<T>... tags) {
        return Arrays.stream(tags).anyMatch(holder::is);
    }

    @SafeVarargs
    public static boolean tagMatches(BlockState state, TagKey<Block>... tags) {
        return Arrays.stream(tags).anyMatch(state::is);
    }

    public static BooleanConsumer ifTrueThen(Runnable task) {
        return b -> {
            if (b) task.run();
        };
    }
}
