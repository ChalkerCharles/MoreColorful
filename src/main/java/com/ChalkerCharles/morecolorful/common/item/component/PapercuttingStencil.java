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
    public static final PapercuttingStencil[] COMMON_STENCILS = new PapercuttingStencil[] {
            of(new long[]{-7027512890490482680L, 5071749850178540106L, 5929571137240319586L, 1155291872399367801L}),
            of(new long[]{3158278629657673728L, 4043201580522429882L, 6753827510245013532L, 51077725432788L}),
            of(new long[]{2415152170518577536L, -6919424851745692252L, 2712307926286114809L, 108194445322232196L}),
            of(new long[]{6794269717168128L, 7375319593447217766L, 3688448920974137344L, 55868947436342L}),
            of(new long[]{-4313520651809058808L, 7608308357968455078L, 7324570809830500758L, 1155236647870317603L}),
            of(new long[]{851285909263613952L, 136193475894234L, 6510586590236663418L, 46541914376784L}),
            of(new long[]{-4720614356849852416L, 7500184706260246533L, -6915929835185346538L, 240084414676605L}),
            of(new long[]{1820191391958080L, 722527522555129552L, 826105160244093008L, 153943353936732160L}),
            of(new long[]{6915945758546853888L, 1317338041324933704L, 1317322991989166664L, 123207042883578L}),
            of(new long[]{-8817685703394323896L, 3759531870812512836L, 2469188151173788716L, 1317342629277631905L}),
    };

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
