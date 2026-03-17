package com.ChalkerCharles.morecolorful.util;

import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.function.BinaryOperator;
import java.util.function.IntFunction;
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

    @SuppressWarnings({"unchecked","SuspiciousSystemArraycopy"})
    public static <T> T[] concatArray(IntFunction<T[]> builder, Object object, Object[] array) {
        int size = array.length + 1;
        T[] arr = builder.apply(size);
        arr[0] = (T) object;
        System.arraycopy(array, 0, arr, 1, array.length);
        return arr;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static <T> T[] concatArray(IntFunction<T[]> builder, Object[] arr1, Object[] arr2) {
        int totalSize = arr1.length + arr2.length;
        T[] arr = builder.apply(totalSize);
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
        return arr;
    }

    public static int getRelativePosIn3By3Grid(int index, int width, int startCol, int startRow) {
        int col = index % width, row = index / width;
        int originCol = col + startCol, originRow = row + startRow;
        return originCol + originRow * 3;
    }

    public static boolean inCenter(int col, int row, int width, int height) {
        boolean colValid = col == width >> 1 || col == (width - 1) >> 1;
        boolean rowValid = row == height >> 1 || row == (height - 1) >> 1;
        return colValid && rowValid;
    }

    public static Quaternionf randomQuaternion(RandomSource random) {
        float u1 = random.nextFloat(), u2 = random.nextFloat(), u3 = random.nextFloat();
        float f0 = Mth.sqrt(1 - u1);
        float f1 = Mth.sqrt(u1);
        float f2 = Mth.TWO_PI * u2;
        float f3 = Mth.TWO_PI * u3;
        float x = f0 * Mth.sin(f2);
        float y = f0 * Mth.cos(f2);
        float z = f1 * Mth.sin(f3);
        float w = f1 * Mth.cos(f3);
        return new Quaternionf(x, y, z, w);
    }

    public static Vector3f[] transform(Vector3f[] vectors, Quaternionf quaternion) {
        int size = vectors.length;
        Vector3f[] transformed = new Vector3f[size];
        for (int i = 0; i < size; i++) {
            transformed[i] = quaternion.transform(vectors[i], new Vector3f());
        }
        return transformed;
    }
}
