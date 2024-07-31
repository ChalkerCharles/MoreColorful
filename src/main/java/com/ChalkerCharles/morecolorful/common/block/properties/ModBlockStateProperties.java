package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {
    public static final BooleanProperty HIT = BooleanProperty.create("hit");
    public static final EnumProperty<HorizontalDoubleBlockHalf> HORIZONTAL_HALF = EnumProperty.create("half", HorizontalDoubleBlockHalf.class);
    public static final EnumProperty<UprightPianoPart> UPRIGHT_PIANO_PART = EnumProperty.create("part", UprightPianoPart.class);
}
