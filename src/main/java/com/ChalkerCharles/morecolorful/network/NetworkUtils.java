package com.ChalkerCharles.morecolorful.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Function;

public final class NetworkUtils {
    public static final StreamCodec<ByteBuf, byte[]> DATA_LAYER = ByteBufCodecs.byteArray(2048);

    public static Function<Throwable, Void> handlePayloadException(IPayloadContext context) {
        return e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        };
    }
}
