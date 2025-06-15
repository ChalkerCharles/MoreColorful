package com.ChalkerCharles.morecolorful.util;

import net.minecraft.core.BlockPos;
import org.joml.Vector4f;

import java.util.concurrent.CompletableFuture;

public final class Constants {
    public static final Vector4f ZERO_VEC4 = new Vector4f(0);
    public static final CompletableFuture<Vector4f> ZERO_VEC4_FUTURE = CompletableFuture.completedFuture(ZERO_VEC4);
    public static final CompletableFuture<Boolean> FALSE_FUTURE = CompletableFuture.completedFuture(false);
    public static final BlockPos DEFAULT_INSTRUMENT_POS = new BlockPos(0, -2048, 0);
}
