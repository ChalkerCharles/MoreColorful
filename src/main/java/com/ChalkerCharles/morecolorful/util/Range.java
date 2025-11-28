package com.ChalkerCharles.morecolorful.util;

import net.minecraft.util.Mth;

public class Range {
    protected final float lowerBound;
    protected final float upperBound;

    public Range(float lowerBound, float upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public static Range of(float lowerBound, float upperBound) {
        return new Range(lowerBound, upperBound);
    }

    public static Range of(float exactValue) {
        return new Range(exactValue - Mth.EPSILON, exactValue + Mth.EPSILON);
    }

    public static Range openClosed(float lowerBound, float upperBound) {
        return new Range(lowerBound, upperBound + Mth.EPSILON);
    }

    public static Range closedOpen(float lowerBound, float upperBound) {
        return new Range(lowerBound - Mth.EPSILON, upperBound);
    }

    public static Range above(float lowerBound) {
        return new Greater(lowerBound);
    }

    public static Range below(float upperBound) {
        return new Less(upperBound);
    }

    public static Range aboveClosed(float lowerBound) {
        return new Greater(lowerBound - Mth.EPSILON);
    }

    public static Range belowClosed(float upperBound) {
        return new Less(upperBound  + Mth.EPSILON);
    }

    public boolean includes(float value) {
        return value > lowerBound && value < upperBound;
    }

    public boolean includes(float value1, float value2) {
        return includes(value1) && includes(value2);
    }

    private static class Greater extends Range {
        private Greater(float lowerBound) {
            super(lowerBound, Float.POSITIVE_INFINITY);
        }

        @Override
        public boolean includes(float value) {
            return value > lowerBound;
        }
    }

    private static class Less extends Range {
        private Less(float upperBound) {
            super(Float.NEGATIVE_INFINITY, upperBound);
        }

        @Override
        public boolean includes(float value) {
            return value < upperBound;
        }
    }
}
