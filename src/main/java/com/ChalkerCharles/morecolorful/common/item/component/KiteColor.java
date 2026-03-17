package com.ChalkerCharles.morecolorful.common.item.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

public record KiteColor(List<DyeColor> colors) {
    public static final KiteColor DEFAULT = pure(DyeColor.WHITE);
    public static final KiteColor RED = pure(DyeColor.RED);
    public static final KiteColor ORANGE = pure(DyeColor.ORANGE);
    public static final KiteColor YELLOW = pure(DyeColor.YELLOW);
    public static final KiteColor LIME = pure(DyeColor.LIME);
    public static final KiteColor GREEN = pure(DyeColor.GREEN);
    public static final KiteColor CYAN = pure(DyeColor.CYAN);
    public static final KiteColor LIGHT_BLUE = pure(DyeColor.LIGHT_BLUE);
    public static final KiteColor BLUE = pure(DyeColor.BLUE);
    public static final KiteColor PURPLE = pure(DyeColor.PURPLE);
    public static final KiteColor MAGENTA = pure(DyeColor.MAGENTA);
    public static final KiteColor PINK = pure(DyeColor.PINK);
    public static final KiteColor WHITE_RED = biColor(DyeColor.WHITE, DyeColor.RED);
    public static final KiteColor WHITE_YELLOW = biColor(DyeColor.WHITE, DyeColor.YELLOW);
    public static final KiteColor WHITE_LIME = biColor(DyeColor.WHITE, DyeColor.LIME);
    public static final KiteColor WHITE_LIGHT_BLUE = biColor(DyeColor.WHITE, DyeColor.LIGHT_BLUE);
    public static final KiteColor WHITE_MAGENTA = biColor(DyeColor.WHITE, DyeColor.MAGENTA);
    public static final KiteColor YELLOW_RED = biColor(DyeColor.YELLOW, DyeColor.RED);
    public static final KiteColor YELLOW_LIME = biColor(DyeColor.YELLOW, DyeColor.LIME);
    public static final KiteColor LIME_LIGHT_BLUE = biColor(DyeColor.LIME, DyeColor.LIGHT_BLUE);
    public static final KiteColor LIGHT_BLUE_MAGENTA = biColor(DyeColor.LIGHT_BLUE, DyeColor.MAGENTA);
    public static final KiteColor BLUE_PURPLE = biColor(DyeColor.BLUE, DyeColor.PURPLE);
    public static final KiteColor FOUR_COLOR = new KiteColor(List.of(
            DyeColor.YELLOW, DyeColor.RED, DyeColor.LIME, DyeColor.LIGHT_BLUE, DyeColor.WHITE
    ));
    public static final List<Pair<KiteColor, DyeColor>> COMMON_COLORS = List.of(
            Pair.of(DEFAULT, DyeColor.WHITE), Pair.of(RED, DyeColor.YELLOW), Pair.of(ORANGE, DyeColor.WHITE),
            Pair.of(YELLOW, DyeColor.LIME), Pair.of(LIME, DyeColor.CYAN), Pair.of(GREEN, DyeColor.YELLOW),
            Pair.of(CYAN, DyeColor.PINK), Pair.of(LIGHT_BLUE, DyeColor.YELLOW), Pair.of(BLUE, DyeColor.PURPLE),
            Pair.of(PURPLE, DyeColor.LIME), Pair.of(MAGENTA, DyeColor.LIGHT_BLUE), Pair.of(PINK, DyeColor.YELLOW),
            Pair.of(WHITE_RED, DyeColor.RED), Pair.of(WHITE_YELLOW, DyeColor.YELLOW), Pair.of(WHITE_LIME, DyeColor.LIME),
            Pair.of(WHITE_LIGHT_BLUE, DyeColor.LIGHT_BLUE), Pair.of(WHITE_MAGENTA, DyeColor.MAGENTA), Pair.of(YELLOW_RED, DyeColor.ORANGE),
            Pair.of(YELLOW_LIME, DyeColor.CYAN), Pair.of(LIME_LIGHT_BLUE, DyeColor.YELLOW), Pair.of(LIGHT_BLUE_MAGENTA, DyeColor.PINK),
            Pair.of(BLUE_PURPLE, DyeColor.PURPLE), Pair.of(FOUR_COLOR, DyeColor.WHITE)
    );
    public static final Codec<KiteColor> CODEC = DyeColor.CODEC.listOf(5, 5)
            .xmap(KiteColor::new, KiteColor::colors);
    public static final StreamCodec<ByteBuf, KiteColor> STREAM_CODEC = DyeColor.STREAM_CODEC
            .apply(ByteBufCodecs.list(5))
            .map(KiteColor::new, KiteColor::colors);

    private static KiteColor pure(DyeColor color) {
        return new KiteColor(List.of(color, color, color, color, color));
    }

    private static KiteColor biColor(DyeColor first, DyeColor second) {
        return new KiteColor(List.of(first, second, second, first, first));
    }

    public List<DyeColor> mutable() {
        return new ArrayList<>(this.colors);
    }
}
