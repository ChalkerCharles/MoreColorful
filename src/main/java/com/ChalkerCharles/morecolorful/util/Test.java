package com.ChalkerCharles.morecolorful.util;

import it.unimi.dsi.fastutil.objects.*;

import java.util.Map;
import java.util.Random;

@Deprecated
public class Test {
//    private static final int count = 100000;
//    private static final Random r = new Random(1000L);
//    private static final Object2ObjectMap<S, S> map1 = new Object2ObjectOpenHashMap<>(count);
//    private static final Reference2ReferenceMap<S, S> map2 = new Reference2ReferenceOpenHashMap<>(count);
//    private static final double ns2ms = 0.000001;
//    private static volatile double result = 0;
//
//    public static void main(String[] args) {
//        for (int i = 0; i < 16; i++) {
//            test();
//        }
//    }
//
//    private static void test() {
//        long value1 = 0;
//        long start1 = System.nanoTime();
//        for (Map.Entry<S, S> entry : map1.entrySet()) {
//            S a = entry.getKey();
//            S b = entry.getValue();
//            value1 += a.length() + b.length();
//        }
//        long end1 = System.nanoTime();
//        long cost1 = end1 - start1;
//        result = value1;
//
//        long value2 = 0;
//        long start2 = System.nanoTime();
//        for (Map.Entry<S, S> entry : map2.entrySet()) {
//            S a = entry.getKey();
//            S b = entry.getValue();
//            value2 += a.length() + b.length();
//        }
//        long end2 = System.nanoTime();
//        long cost2 = end2 - start2;
//        result = value2;
//
//        System.out.println("------------------------");
//        System.out.println("Method1: " + cost1 * ns2ms + " ms");
//        System.out.println("Method2: " + cost2 * ns2ms + " ms");
//    }
//
//    static {
//        for (int i = 0; i < count; i++) {
//            S k = new S(Integer.toHexString(i));
//            S v = new S(Double.toHexString(r.nextDouble() * 1000000000));
//            map1.put(k, v);
//            map2.put(k, v);
//        }
//    }
//
//    private static class S {
//        private final String s;
//        private S(String s) {
//            this.s = s;
//        }
//
//        private int length() {
//            return this.s.length();
//        }
//    }
}
