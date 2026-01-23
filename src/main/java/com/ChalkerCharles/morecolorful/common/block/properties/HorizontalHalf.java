package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum HorizontalHalf implements StringRepresentable {
    LEFT("left"),
    RIGHT("right");

    private final String name;

    HorizontalHalf(String pName) {
        this.name = pName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
