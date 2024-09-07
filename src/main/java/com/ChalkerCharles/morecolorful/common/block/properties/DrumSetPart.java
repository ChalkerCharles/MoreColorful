package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;

public enum DrumSetPart implements StringRepresentable {
    LEFT_UPPER("left_upper"),
    LEFT_LOWER("left_lower"),
    MID_UPPER("mid_upper"),
    MID_LOWER("mid_lower"),
    RIGHT_UPPER("right_upper"),
    RIGHT_LOWER("right_lower");

    private final String name;

    DrumSetPart(String pName) {
        this.name = pName;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
