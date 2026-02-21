package com.ChalkerCharles.morecolorful.common.item.component;

import com.ChalkerCharles.morecolorful.util.Colour;
import com.mojang.serialization.Codec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public record UmbrellaColor(List<Colour> colors) {
    private static final List<Colour> EMPTY = List.of(
            Colour.DEFAULT, Colour.DEFAULT, Colour.DEFAULT, Colour.DEFAULT,
            Colour.DEFAULT, Colour.DEFAULT, Colour.DEFAULT, Colour.DEFAULT
    );
    public static final UmbrellaColor DEFAULT = new UmbrellaColor(EMPTY);
    public static final UmbrellaColor RAINBOW = new UmbrellaColor(list(
            DyeColor.RED, DyeColor.ORANGE, DyeColor.YELLOW, DyeColor.MAGENTA,
            DyeColor.LIME, DyeColor.PURPLE, DyeColor.BLUE, DyeColor.LIGHT_BLUE
    ));
    public static final UmbrellaColor WHITE = pure(DyeColor.WHITE);
    public static final UmbrellaColor LIGHT_GRAY = pure(DyeColor.LIGHT_GRAY);
    public static final UmbrellaColor GRAY = pure(DyeColor.GRAY);
    public static final UmbrellaColor BLACK = pure(DyeColor.BLACK);
    public static final UmbrellaColor BROWN = pure(DyeColor.BROWN);
    public static final UmbrellaColor RED = pure(DyeColor.RED);
    public static final UmbrellaColor ORANGE = pure(DyeColor.ORANGE);
    public static final UmbrellaColor YELLOW = pure(DyeColor.YELLOW);
    public static final UmbrellaColor LIME = pure(DyeColor.LIME);
    public static final UmbrellaColor GREEN = pure(DyeColor.GREEN);
    public static final UmbrellaColor CYAN = pure(DyeColor.CYAN);
    public static final UmbrellaColor LIGHT_BLUE = pure(DyeColor.LIGHT_BLUE);
    public static final UmbrellaColor BLUE = pure(DyeColor.BLUE);
    public static final UmbrellaColor PURPLE = pure(DyeColor.PURPLE);
    public static final UmbrellaColor MAGENTA = pure(DyeColor.MAGENTA);
    public static final UmbrellaColor PINK = pure(DyeColor.PINK);
    public static final UmbrellaColor LIGHT_GRAY_WHITE = biColor(DyeColor.LIGHT_GRAY, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_GRAY = biColor(DyeColor.WHITE, DyeColor.GRAY);
    public static final UmbrellaColor BLACK_WHITE = biColor(DyeColor.BLACK, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_BROWN = biColor(DyeColor.WHITE, DyeColor.BROWN);
    public static final UmbrellaColor RED_WHITE = biColor(DyeColor.RED, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_ORANGE = biColor(DyeColor.WHITE, DyeColor.ORANGE);
    public static final UmbrellaColor YELLOW_WHITE = biColor(DyeColor.YELLOW, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_LIME = biColor(DyeColor.WHITE, DyeColor.LIME);
    public static final UmbrellaColor GREEN_WHITE = biColor(DyeColor.GREEN, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_CYAN = biColor(DyeColor.WHITE, DyeColor.CYAN);
    public static final UmbrellaColor LIGHT_BLUE_WHITE = biColor(DyeColor.LIGHT_BLUE, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_BLUE = biColor(DyeColor.WHITE, DyeColor.BLUE);
    public static final UmbrellaColor PURPLE_WHITE = biColor(DyeColor.PURPLE, DyeColor.WHITE);
    public static final UmbrellaColor WHITE_MAGENTA = biColor(DyeColor.WHITE, DyeColor.MAGENTA);
    public static final UmbrellaColor PINK_WHITE = biColor(DyeColor.PINK, DyeColor.WHITE);
    public static final UmbrellaColor[] COMMON_COLORS = new UmbrellaColor[] {
            WHITE, LIGHT_GRAY, GRAY, BLACK, BROWN, RED, ORANGE, YELLOW,
            LIME, GREEN, CYAN, LIGHT_BLUE, BLUE, PURPLE, MAGENTA, PINK,
            LIGHT_GRAY_WHITE, WHITE_GRAY, BLACK_WHITE, WHITE_BROWN, RED_WHITE,
            WHITE_ORANGE, YELLOW_WHITE, WHITE_LIME, GREEN_WHITE, WHITE_CYAN,
            LIGHT_BLUE_WHITE, WHITE_BLUE, PURPLE_WHITE, WHITE_MAGENTA, PINK_WHITE
    };
    public static final Codec<UmbrellaColor> CODEC = Colour.CODEC.listOf(8, 8)
            .xmap(UmbrellaColor::new, UmbrellaColor::colors);
    public static final StreamCodec<FriendlyByteBuf, UmbrellaColor> STREAM_CODEC = StreamCodec.composite(
            Colour.STREAM_CODEC.apply(ByteBufCodecs.list(8)),
            UmbrellaColor::colors,
            UmbrellaColor::new
    );

    private static List<Colour> list(DyeColor a, DyeColor b, DyeColor c, DyeColor d,
                                     DyeColor e, DyeColor f, DyeColor g, DyeColor h) {
        return List.of(
                Colour.cast(a), Colour.cast(b), Colour.cast(c), Colour.cast(d),
                Colour.cast(e), Colour.cast(f), Colour.cast(g), Colour.cast(h)
        );
    }

    public static UmbrellaColor pure(DyeColor color) {
        return new UmbrellaColor(list(color, color, color, color, color, color, color, color));
    }

    public static UmbrellaColor biColor(DyeColor first, DyeColor second) {
        return new UmbrellaColor(list(first, second, first, second, second, first, second, first));
    }

    public List<Colour> mutable() {
        return new ArrayList<>(this.colors);
    }

    public boolean isEmpty() {
        return EMPTY.equals(this.colors);
    }
}
