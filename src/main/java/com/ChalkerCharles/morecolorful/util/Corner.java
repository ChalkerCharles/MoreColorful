package com.ChalkerCharles.morecolorful.util;

import net.minecraft.core.Direction;

public enum Corner {
    SOUTHEAST(Direction.SOUTH, Direction.EAST),
    NORTHEAST(Direction.EAST, Direction.NORTH),
    NORTHWEST(Direction.NORTH, Direction.WEST),
    SOUTHWEST(Direction.WEST, Direction.SOUTH);

    public final Direction first;
    public final Direction second;

    Corner(Direction first, Direction second) {
        this.first = first;
        this.second = second;
    }

    public static Corner getCorner(float x, float z) {
        if (x > 0) {
            return z > 0 ? SOUTHEAST : NORTHEAST;
        } else {
            return z > 0 ? SOUTHWEST : NORTHWEST;
        }
    }
}
