package com.ChalkerCharles.morecolorful.util;

import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectSortedMaps;
import net.minecraft.Util;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

public class Selector<T> {
    private final Int2ObjectSortedMap<T[]> items;
    private final T[] fallback;

    private Selector(Int2ObjectSortedMap<T[]> items, T[] fallback) {
        this.items = items;
        this.fallback = fallback;
    }

    public static <T> Builder<T> of() {
        return new Builder<>();
    }

    public T select(RandomSource random) {
        int i = random.nextInt(100);
        int weight = 0;
        for (Int2ObjectMap.Entry<T[]> entry : this.items.int2ObjectEntrySet()) {
            weight += entry.getIntKey();
            if (i < weight) {
                return Util.getRandom(entry.getValue(), random);
            }
        }
        return Util.getRandom(this.fallback, random);
    }

    public static class Builder<T> {
        private final Int2ObjectSortedMap<T[]> items = new Int2ObjectAVLTreeMap<>();
        private T[] fallback;

        @SafeVarargs
        public final Builder<T> add(int weight, @NotNull T... items) {
            this.items.put(weight, items);
            return this;
        }

        @SafeVarargs
        public final Builder<T> add(@NotNull T... items) {
            this.fallback = items;
            return this;
        }

        public Selector<T> build() {
            this.validate();
            return new Selector<>(Int2ObjectSortedMaps.unmodifiable(this.items), this.fallback);
        }

        private void validate() {
            int total = this.items.keySet().intStream().sum();
            if (total < 100 && this.fallback == null) {
                throw new IllegalStateException("Invalid Selector");
            }
        }
    }
}
