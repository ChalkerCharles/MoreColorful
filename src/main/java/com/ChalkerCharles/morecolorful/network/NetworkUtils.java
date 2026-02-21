package com.ChalkerCharles.morecolorful.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.BitSet;
import java.util.List;
import java.util.function.Function;

@SuppressWarnings("DataFlowIssue")
public final class NetworkUtils {
    public static final StreamCodec<ByteBuf, byte[]> DATA_LAYER = ByteBufCodecs.byteArray(2048);
    public static final StreamCodec<ByteBuf, List<String>> STRING_LIST = ByteBufCodecs.STRING_UTF8.apply(ByteBufCodecs.list());
    public static final StreamCodec<FriendlyByteBuf, BitSet> SHEET_MUSIC_PAGE = new StreamCodec<>() {
        @Override
        public void encode(FriendlyByteBuf buffer, BitSet value) {
            buffer.writeFixedBitSet(value,800);
        }

        @Override
        public BitSet decode(FriendlyByteBuf buffer) {
            return buffer.readFixedBitSet(800);
        }
    };
    public static final StreamCodec<FriendlyByteBuf, List<BitSet>> SHEET_MUSIC_PAGES = SHEET_MUSIC_PAGE.apply(ByteBufCodecs.list());

    public static <B extends ByteBuf, V> StreamCodec<B, V> nullable(StreamCodec<B, V> codec) {
        return new StreamCodec<>() {
            @Override
            public void encode(B buffer, V value) {
                FriendlyByteBuf.writeNullable(buffer, value, codec);
            }

            @Override
            public V decode(B buffer) {
                return FriendlyByteBuf.readNullable(buffer, codec);
            }
        };
    }

    public static Function<Throwable, Void> handlePayloadException(IPayloadContext context) {
        return e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        };
    }
}
