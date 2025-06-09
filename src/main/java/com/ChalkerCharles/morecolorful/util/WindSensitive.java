package com.ChalkerCharles.morecolorful.util;

/// A Marker Interface that indicates the block or particle can be affected by wind.
public interface WindSensitive {
    default boolean isWindSensitive() {
        return true;
    }
}
