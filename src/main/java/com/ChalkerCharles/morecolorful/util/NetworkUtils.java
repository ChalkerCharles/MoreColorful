package com.ChalkerCharles.morecolorful.util;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.function.Function;

public final class NetworkUtils {
    public static Function<Throwable, Void> handleException(final IPayloadContext context) {
        return e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        };
    }
}
