package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum GrandPianoPart implements StringRepresentable {
    FRONT_LEFT_LOWER("front_left_lower"),
    FRONT_LEFT_UPPER("front_left_upper"),
    FRONT_RIGHT_LOWER("front_right_lower"),
    FRONT_RIGHT_UPPER("front_right_upper"),
    BACK_LEFT_LOWER("back_left_lower"),
    BACK_LEFT_UPPER("back_left_upper"),
    BACK_RIGHT_UPPER("back_right_upper");

    private final String name;

    GrandPianoPart(String pName) {
        this.name = pName;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
