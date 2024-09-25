package com.ChalkerCharles.morecolorful.common.block.properties;

import com.google.common.base.Preconditions;
import net.minecraft.world.level.material.MapColor;

public class MapColorExtension extends MapColor {
    private static final MapColorExtension[] MORECOLORFUL_COLORS = new MapColorExtension[64];
    public static final MapColorExtension CRABAPPLE = new MapColorExtension(0, 15621983);
    public final int col;
    public final int id;
    public MapColorExtension(int pId, int pCol) {
        super(pId, pCol);
        if (pId >= 0 && pId <= 63) {
            this.id = pId;
            this.col = pCol;
            MORECOLORFUL_COLORS[pId] = this;
        } else {
            throw new IndexOutOfBoundsException("Map colour ID must be between 0 and 63 (inclusive)");
        }
    }
    @SuppressWarnings("unused")
    public static MapColor byId(int pId) {
        Preconditions.checkPositionIndex(pId, MORECOLORFUL_COLORS.length, "material id");
        return byIdUnsafe(pId);
    }

    private static MapColor byIdUnsafe(int pId) {
        MapColorExtension mapColor = MORECOLORFUL_COLORS[pId];
        return mapColor != null ? mapColor : NONE;
    }
}
