package com.ChalkerCharles.morecolorful.common.item.component;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.DyeColor;

import java.util.ArrayList;
import java.util.List;

public record PinwheelColor(List<DyeColor> colors) {
    public static final PinwheelColor DEFAULT = new PinwheelColor(List.of(
            DyeColor.WHITE, DyeColor.WHITE, DyeColor.WHITE, DyeColor.WHITE
    ));
    public static final Codec<PinwheelColor> CODEC = DyeColor.CODEC.listOf(4, 4)
            .xmap(PinwheelColor::new, PinwheelColor::colors);
    public static final StreamCodec<ByteBuf, PinwheelColor> STREAM_CODEC = DyeColor.STREAM_CODEC
            .apply(ByteBufCodecs.list(4))
            .map(PinwheelColor::new, PinwheelColor::colors);

    public void save(CompoundTag tag) {
        if (!this.equals(DEFAULT)) {
            tag.put("colors", CODEC.encodeStart(NbtOps.INSTANCE, this).getOrThrow());
        }
    }

    public static PinwheelColor load(CompoundTag pTag) {
        return pTag != null && pTag.contains("colors")
                ? CODEC.parse(NbtOps.INSTANCE, pTag.get("colors")).result().orElse(DEFAULT) : DEFAULT;
    }

    public List<DyeColor> mutable() {
        return new ArrayList<>(this.colors);
    }
}
