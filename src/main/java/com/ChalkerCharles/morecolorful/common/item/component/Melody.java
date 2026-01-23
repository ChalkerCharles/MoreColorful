package com.ChalkerCharles.morecolorful.common.item.component;

import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.network.Filterable;
import net.minecraft.util.ExtraCodecs;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public record Melody(Filterable<String> title, String author, int generation, List<String> pages) {
    public static final Melody EMPTY = new Melody(Filterable.passThrough(""), "", 0, List.of());
    public static final Codec<Melody> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Filterable.codec(Codec.string(0, 32)).fieldOf("title").forGetter(Melody::title),
                    Codec.STRING.fieldOf("author").forGetter(Melody::author),
                    ExtraCodecs.intRange(0, 3).optionalFieldOf("generation", 0).forGetter(Melody::generation),
                    Codec.STRING.listOf().fieldOf("pages").forGetter(Melody::pages)
            ).apply(instance, Melody::new)
    );
    public static final StreamCodec<FriendlyByteBuf, Melody> STREAM_CODEC = StreamCodec.composite(
            Filterable.streamCodec(ByteBufCodecs.stringUtf8(32)),
            Melody::title,
            ByteBufCodecs.STRING_UTF8,
            Melody::author,
            ByteBufCodecs.VAR_INT,
            Melody::generation,
            NetworkUtils.STRING_LIST,
            Melody::pages,
            Melody::new
    );
    private static final String EMPTY_BAR = "zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz";

    @Nullable
    public Melody tryCraftCopy() {
        return this.generation >= 2 ? null : new Melody(this.title, this.author, this.generation + 1, this.pages);
    }

    public static BitSet parse(String s) {
        BitSet bitSet = new BitSet(800);
        int i = 0;
        for (char c : s.toCharArray()) {
            if (c == 'z') {
                i++;
            } else {
                int j = 'y' - c;
                bitSet.set(i * 25 + j);
            }
        }
        return bitSet;
    }

    public static List<String> compose(List<BitSet> list) {
        int size = list.size();
        List<String> list1 = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list1.add(compose(list.get(i), i == size - 1));
        }
        return list1;
    }

    private static String compose(BitSet bitSet, boolean reverse) {
        if (bitSet.isEmpty()) return EMPTY_BAR;
        StringBuilder builder = new StringBuilder();
        if (reverse) {
            boolean foundLast = false;
            for (int i = 31; i >= 0; i--) {
                if (foundLast) {
                    builder.append("z");
                }
                for (int j = 24; j >= 0; j--) {
                    int index = i * 25 + j;
                    if (bitSet.get(index)) {
                        if (!foundLast) {
                            builder.append("z");
                            foundLast = true;
                        }
                        char c = (char) ('y' - j);
                        builder.append(c);
                    }
                }
            }
            builder.reverse();
        } else {
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 25; j++) {
                    int index = i * 25 + j;
                    if (bitSet.get(index)) {
                        char c = (char) ('y' - j);
                        builder.append(c);
                    }
                }
                builder.append("z");
            }
        }
        return builder.toString();
    }
}
