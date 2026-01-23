package com.ChalkerCharles.morecolorful.common.item.component;

import com.ChalkerCharles.morecolorful.network.NetworkUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.BitSet;
import java.util.List;

public record EditableMelody(List<String> pages) {
    public static final EditableMelody EMPTY = new EditableMelody(List.of());
    public static final Codec<EditableMelody> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(Codec.STRING.listOf().optionalFieldOf("pages", List.of()).forGetter(EditableMelody::pages))
                    .apply(instance, EditableMelody::new)
    );
    public static final StreamCodec<FriendlyByteBuf, EditableMelody> STREAM_CODEC = StreamCodec.composite(
            NetworkUtils.STRING_LIST,
            EditableMelody::pages,
            EditableMelody::new
    );

    public static EditableMelody of(List<BitSet> pages) {
        return new EditableMelody(Melody.compose(pages));
    }
}
