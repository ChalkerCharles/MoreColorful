package com.ChalkerCharles.morecolorful.common.item.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;

import java.util.Arrays;
import java.util.BitSet;
import java.util.stream.LongStream;

public record PapercuttingStencil(long[] stencil) {
    private static final long[] EMPTY = new long[4];
    private static final long[] FULL = new long[]{-1, -1, -1, -1};
    public static final PapercuttingStencil DEFAULT = new PapercuttingStencil(EMPTY);
    public static final Codec<PapercuttingStencil> CODEC = Codec.LONG_STREAM
            .xmap(PapercuttingStencil::fromStream, PapercuttingStencil::toStream);
    public static final StreamCodec<ByteBuf, PapercuttingStencil> STREAM_CODEC = StreamCodec.ofMember(
            PapercuttingStencil::encode, PapercuttingStencil::decode
    );

    public static PapercuttingStencil of(long[] stencil) {
        if (Arrays.equals(EMPTY, stencil)) {
            return DEFAULT;
        }
        return new PapercuttingStencil(stencil);
    }

    public static PapercuttingStencil of(BitSet bitSet) {
        long[] arr = bitSet.toLongArray();
        return of(Arrays.copyOf(arr, 4));
    }

    public BitSet toBitSet() {
        BitSet bitSet = new BitSet(256);
        bitSet.or(BitSet.valueOf(this.stencil));
        return bitSet;
    }

    public static PapercuttingStencil fromStream(LongStream stream) {
        return of(stream.toArray());
    }

    public LongStream toStream() {
        return LongStream.of(this.stencil);
    }

    public void encode(ByteBuf buf) {
        for (long l : this.stencil) {
            buf.writeLong(l);
        }
    }

    public static PapercuttingStencil decode(ByteBuf buf) {
        long[] stencil = new long[4];
        for (int i = 0; i < 4; i++) {
            stencil[i] = buf.readLong();
        }
        return of(stencil);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof PapercuttingStencil(long[] stencil1)) {
            return Arrays.equals(this.stencil, stencil1);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.stencil);
    }

    @Override
    public String toString() {
        return "PapercuttingStencil" + Arrays.toString(this.stencil);
    }

    public String name() {
        StringBuilder builder = new StringBuilder();
        for (long l : this.stencil) {
            builder.append(Long.toHexString(l));
        }
        return builder.toString();
    }

    public boolean notEmpty() {
        return !this.equals(DEFAULT);
    }

    public boolean isFull() {
        return Arrays.equals(this.stencil, FULL);
    }

    public static boolean isFull(BitSet bitSet) {
        return bitSet.cardinality() == 256;
    }
}
