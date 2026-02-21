package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.entity.misc.Balloon;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.DyeColor;
import net.neoforged.neoforge.common.util.Lazy;

@SuppressWarnings("ConstantValue")
public interface Colour extends Balloon.Variant {
    Colour DEFAULT = new Colour() {
        @Override
        public String getName() {
            return "default";
        }

        @Override
        public String getSerializedName() {
            return "";
        }
    };
    Lazy<Colour[]> VALUES = Lazy.of(() -> Maths.concatArray(Colour[]::new, DEFAULT, DyeColor.values()));
    Codec<Colour> CODEC = ExtraCodecs.orCompressed(
            Codec.stringResolver(Colour::getSerializedName, Colour::fromName),
            ExtraCodecs.idResolverCodec(Colour::getIndex, Colour::byIndex, -1)
    );
    StreamCodec<ByteBuf, Colour> STREAM_CODEC = ByteBufCodecs.idMapper(Colour::byIndex, Colour::getIndex);

    String getName();

    String getSerializedName();

    default boolean isDefault() {
        return this == DEFAULT;
    }

    static Colour[] values() {
        return VALUES.get();
    }

    static int size() {
        return values().length;
    }

    static Colour cast(Object o) {
        return (Colour) o;
    }

    static Colour fromName(String name) {
        Colour color = cast(DyeColor.CODEC.byName(name));
        if (color != null) {
            return color;
        } else {
            return "".equals(name) || "default".equals(name) ? DEFAULT : null;
        }
    }

    @Override
    default int getIndex() {
        if ((Object) this instanceof DyeColor color) {
            return color.getId() + 1;
        }
        return 0;
    }

    private static Colour byIndex(int i) {
        Colour[] values = values();
        if (i >= 0 && i < values.length) {
            return values[i];
        }
        return values[0];
    }
}
