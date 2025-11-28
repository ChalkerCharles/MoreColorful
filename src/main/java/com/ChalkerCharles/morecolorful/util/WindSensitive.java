package com.ChalkerCharles.morecolorful.util;

/// A Marker Interface that indicates an object (like Block, Entity or Particle) can be affected by wind.
public interface WindSensitive {
    default boolean moreColorful$isWindSensitive() {
        return true;
    }
}
