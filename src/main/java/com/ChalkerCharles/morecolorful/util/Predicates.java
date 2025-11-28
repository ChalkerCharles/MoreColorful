package com.ChalkerCharles.morecolorful.util;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import it.unimi.dsi.fastutil.booleans.BooleanUnaryOperator;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Predicates {
    public static boolean blockMatches(Block block, Block... blocks) {
        return Arrays.asList(blocks).contains(block);
    }

    public static boolean blockMatches(BlockState state, Block... blocks) {
        return Arrays.stream(blocks).anyMatch(state::is);
    }

    public static <T> boolean tagMatches(Holder.Reference<T> holder, Collection<TagKey<T>> tags) {
        return tags.stream().anyMatch(holder::is);
    }

    @SafeVarargs
    public static boolean tagMatches(BlockState state, TagKey<Block>... tags) {
        return Arrays.stream(tags).anyMatch(state::is);
    }
}
