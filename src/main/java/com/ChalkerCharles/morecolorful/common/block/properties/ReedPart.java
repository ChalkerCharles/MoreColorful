package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.util.StringRepresentable;

public enum ReedPart implements StringRepresentable {
    UPPER("upper"),
    MID("mid"),
    LOWER("lower");

    private final String name;

    ReedPart(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
