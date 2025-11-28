package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public interface IMapItemSavedDataExtension {
    byte[] moreColorful$getColors();

    void moreColorful$setColors(byte[] colors);

    boolean moreColorful$updateColor(int pX, int pZ, byte pColor);

    void moreColorful$setColor(int pX, int pZ, byte pColor);

    private static IMapItemSavedDataExtension self(MapItemSavedData data) {
        return (IMapItemSavedDataExtension) data;
    }

    static byte[] getColors(MapItemSavedData data) {
        return self(data).moreColorful$getColors();
    }

    static void setColors(MapItemSavedData data, byte[] colors) {
        self(data).moreColorful$setColors(colors);
    }

    static boolean updateColor(MapItemSavedData data, int pX, int pZ, byte pColor) {
        return self(data).moreColorful$updateColor(pX, pZ, pColor);
    }

    static void setColor(MapItemSavedData data, int pX, int pZ, byte pColor) {
        self(data).moreColorful$setColor(pX, pZ, pColor);
    }
}
