package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum HorizontalDoubleBlockHalf implements StringRepresentable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    HorizontalDoubleBlockHalf(String pName) {
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
