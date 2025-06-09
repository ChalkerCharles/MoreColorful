package com.ChalkerCharles.morecolorful.mixin.extensions;

public interface IMapItemSavedDataExtension {
    byte[] moreColorful$getColors();

    void moreColorful$setColors(byte[] colors);

    boolean moreColorful$updateColor(int pX, int pZ, byte pColor);

    void moreColorful$setColor(int pX, int pZ, byte pColor);
}
