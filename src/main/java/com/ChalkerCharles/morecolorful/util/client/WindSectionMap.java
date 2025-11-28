package com.ChalkerCharles.morecolorful.util.client;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import java.util.function.IntSupplier;

public class WindSectionMap {
    private static final int ARRAY_LENGTH = 273;
    private static final long DEFAULT_LONG = 0xaaaaaaaaaaaaaaaaL;
    private static final long[] TEMPLATE_ARRAY = new long[ARRAY_LENGTH];
    private final AtomicLongArray array = new AtomicLongArray(TEMPLATE_ARRAY);
    private volatile boolean empty = true;

    private static int getElementIndex(int index) {
        return index >> 5;
    }

    private static int getBitOffset(int index) {
        return (index & 31) << 1;
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public void put(int index, int value) {
        this.empty = false;
        int m = getElementIndex(index);
        int n = getBitOffset(index);
        long v = (value & 3L) << n, b = ~(3L << n);
        long l = this.array.get(m), t = 0L;
        for (boolean h = false;;) {
            if (!h) t = (l & b) | v;
            if (this.array.weakCompareAndSetVolatile(m, l, t)) return;
            h = (l == (l = this.array.get(m)));
        }
    }

    public int get(int index) {
        int m = getElementIndex(index);
        int n = getBitOffset(index);
        long l = this.array.get(m);
        return (int) ((l >> n) & 3);
    }

    public int get(int index, IntSupplier supplier) {
        this.empty = false;
        int m = getElementIndex(index);
        int n = getBitOffset(index);
        for (long b = 3L << n, t = 2L << n;;) {
            long l = this.array.get(m);
            int i = (int) ((l >> n) & 3);
            if (i != 2) return i;
            int j = supplier.getAsInt() & 3;
            if (j == 2)
                throw new IllegalArgumentException("Supplied value cannot be 2");
            long k = (l & ~b) | ((long) j << n);
            if ((l & b) == t && this.array.compareAndSet(m, l, k)) {
                return j;
            }
        }
    }

    public void clear() {
        this.empty = true;
        for (int i = 0; i < ARRAY_LENGTH; i++) {
            this.array.set(i, DEFAULT_LONG);
        }
    }

    static {
        Arrays.fill(TEMPLATE_ARRAY, DEFAULT_LONG);
    }
}
