package com.ChalkerCharles.morecolorful.util;

import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import org.joml.Vector4f;

import java.util.function.BinaryOperator;
import java.util.function.IntSupplier;

public final class Maths {
    public static final float INV5 = 1.0F / 5.0F;
    public static final float INV8 = 1.0F / 8.0F;
    public static final float INV24 = 1.0F / 24.0F;
    public static final float INV32 = 1.0F / 32.0F;
    public static final float INV48 = 1.0F / 48.0F;
    public static final float INV64 = 1.0F / 64.0F;
    public static final Vector4f ZERO_VEC4 = new Vector4f(0);
    public static final IntSupplier SUPPLIER_0 = () -> 0;
    public static final BinaryOperator<Integer> ONLY_SECOND = (i, j) -> j;
    public static final Direction[] DIRECTIONS = Direction.values();
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    public static float length(float x, float y) {
        return (float) Math.sqrt(x * x + y * y);
    }

    public static boolean equals(double x1, double z1, double x2, double z2) {
        return Double.compare(x1, x2) == 0 && Double.compare(z1, z2) == 0;
    }

    public static short sectionRelativePos(int x, int y, int z) {
        int i = x & 15, j = y & 15, k = z & 15;
        return (short) (i << 8 | k << 4 | j);
    }

    public static long sectionPos(double x, double y, double z) {
        return SectionPos.asLong(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(y), SectionPos.blockToSectionCoord(z));
    }

    public static float vectorToAngle(float x, float z) {
        return (90 - (float) Mth.atan2(-z, x) * Mth.RAD_TO_DEG) % 360;
    }
}
