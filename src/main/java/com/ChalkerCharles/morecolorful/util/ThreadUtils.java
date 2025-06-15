package com.ChalkerCharles.morecolorful.util;

import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class ThreadUtils {
    public static final ExecutorService VERTEX_EXECUTOR = Executors.newSingleThreadExecutor(new CustomThreadFactory("vertex"));
    public static final ExecutorService WAVE_EXECUTOR = Executors.newSingleThreadExecutor(new CustomThreadFactory("wave"));
    public static final ExecutorService FLUID_EXECUTOR = Executors.newSingleThreadExecutor(new CustomThreadFactory("fluid"));
    public static final ExecutorService WIND_EXECUTOR = Executors.newSingleThreadExecutor(new CustomThreadFactory("wind"));

    public static Function<Throwable, Void> handlePayloadException(final IPayloadContext context) {
        return e -> {
            context.disconnect(Component.translatable("neoforge.network.invalid_flow", e.getMessage()));
            return null;
        };
    }

    private static class CustomThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        private CustomThreadFactory(String name) {
            namePrefix = "morecolorful-" + name + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(null, r, namePrefix + threadNumber.getAndIncrement(), 0);
        }
    }
}
