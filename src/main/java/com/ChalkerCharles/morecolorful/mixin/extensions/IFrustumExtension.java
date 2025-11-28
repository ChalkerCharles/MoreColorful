package com.ChalkerCharles.morecolorful.mixin.extensions;

public interface IFrustumExtension {
    default boolean moreColorful$isInRange(double pMinX, double pMinY, double pMinZ) {
        return true;
    }
}
