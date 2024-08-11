package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum UprightPianoPart implements StringRepresentable {
    LEFT_LOWER("left_lower"),
    RIGHT_LOWER("right_lower"),
    LEFT_UPPER("left_upper"),
    RIGHT_UPPER("right_upper");

    private final String name;

    UprightPianoPart(String pName) {
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
