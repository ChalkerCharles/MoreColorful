package com.ChalkerCharles.morecolorful.util;

import java.util.Arrays;
import java.util.Random;

@Deprecated
public class Test {
    private static final int count = 100000;
    private static final Random r = new Random(1000L);
    private static final float[] X = new float[count];
    private static final double ns2ms = 0.000001;
    private static volatile double result = 0;
    private static final float[] THRESHOLDS = {1.5F, 4, 7.5F, 12, 17.5F};

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            test();
        }
    }

    private static void test() {
        float value1 = 0;
        long start1 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            value1 += getWindLevel(X[i]);
        }
        long end1 = System.nanoTime();
        long cost1 = end1 - start1;
        result = value1;

        float value2 = 0;
        long start2 = System.nanoTime();
        for (int i = 0; i < count; i++) {
            value2 += getWindLevel2(X[i]);
        }
        long end2 = System.nanoTime();
        long cost2 = end2 - start2;
        result = value2;

        System.out.println("------------------------");
        System.out.println("Method1: " + cost1 * ns2ms + " ms   " + value1);
        System.out.println("Method2: " + cost2 * ns2ms + " ms   " + value2);
    }

    static {
        for (int i = 0; i < count; i++) {
            X[i] = r.nextFloat(25);
        }
    }

    private static int getWindLevel(float windSpeed) {
        if (windSpeed < 1.5) return 0;
        else if (windSpeed < 4) return 1;
        else if (windSpeed < 7.5) return 2;
        else if (windSpeed < 12) return 3;
        else if (windSpeed < 17.5) return 4;
        else return 5;
    }

    private static int getWindLevel2(float windSpeed) {
        int idx = Arrays.binarySearch(THRESHOLDS, windSpeed);
        return idx >= 0 ? idx : ~idx;
    }
}
