package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.world.level.material.MapColor;

public class MapColorExtension extends MapColor {
    private static final MapColorExtension[] MATERIAL_COLORS = new MapColorExtension[64];
    public static final MapColorExtension NONE = new MapColorExtension(0, 0);
    public static final MapColorExtension CRABAPPLE = new MapColorExtension(1, 0xf38d83);
    public static final MapColorExtension BEGONIA = new MapColorExtension(2, 0xf56464);
    public static final MapColorExtension FROST = new MapColorExtension(3, 0xb0cffa);
    public static final MapColorExtension FROST_WOOD = new MapColorExtension(4, 0xdfeff4);
    public static final MapColorExtension JACARANDA = new MapColorExtension(5, 0xaf8bf0);
    public static final MapColorExtension JACARANDA_WOOD = new MapColorExtension(6, 0xd1bbeb);
    public static final MapColorExtension ORANGE_BIRCH = new MapColorExtension(7, 0xc35f2a);
    public final int col;
    public final int id;

    public MapColorExtension(int pId, int pCol) {
        super(0, 0);
        if (pId >= 0 && pId <= 63) {
            this.id = pId;
            this.col = pCol;
            MATERIAL_COLORS[pId] = this;
        } else {
            throw new IndexOutOfBoundsException("Map color ID must be between 0 and 63 (inclusive)");
        }
    }

    @Override
    public int calculateRGBColor(MapColor.Brightness pBrightness) {
        if (this == NONE) {
            return 0;
        } else {
            int i = pBrightness.modifier;
            int j = (this.col >> 16 & 0xFF) * i / 255;
            int k = (this.col >> 8 & 0xFF) * i / 255;
            int l = (this.col & 0xFF) * i / 255;
            return 0xFF000000 | l << 16 | k << 8 | j;
        }
    }

    private static MapColorExtension byIdUnsafe(int pId) {
        MapColorExtension mapColorExtension = MATERIAL_COLORS[pId];
        return mapColorExtension != null ? mapColorExtension : NONE;
    }

    public static int getColorFromPackedId(int pPackedId) {
        int i = pPackedId & 0xFF;
        return byIdUnsafe(i >> 2).calculateRGBColor(MapColor.Brightness.byId(i & 3));
    }
}
