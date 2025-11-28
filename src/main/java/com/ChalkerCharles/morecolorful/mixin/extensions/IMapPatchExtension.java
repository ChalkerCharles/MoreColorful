package com.ChalkerCharles.morecolorful.mixin.extensions;

import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public interface IMapPatchExtension {
    byte[] moreColorful$getColors();

    void moreColorful$setColors(byte[] colors);

    private static IMapPatchExtension self(MapItemSavedData.MapPatch mapPatch) {
        return (IMapPatchExtension) (Object) mapPatch;
    }

    static byte[] getColors(MapItemSavedData.MapPatch mapPatch) {
        return self(mapPatch).moreColorful$getColors();
    }

    static void setColors(MapItemSavedData.MapPatch mapPatch, byte[] colors) {
        self(mapPatch).moreColorful$setColors(colors);
    }
}
