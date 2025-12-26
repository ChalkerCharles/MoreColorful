package com.ChalkerCharles.morecolorful.common.block.properties;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class ModBlockStateProperties {
    public static final BooleanProperty HIT = BooleanProperty.create("hit");
    public static final EnumProperty<HorizontalDoubleBlockHalf> HORIZONTAL_HALF = EnumProperty.create("half", HorizontalDoubleBlockHalf.class);
    public static final EnumProperty<UprightPianoPart> UPRIGHT_PIANO_PART = EnumProperty.create("part", UprightPianoPart.class);
    public static final EnumProperty<GrandPianoPart> GRAND_PIANO_PART = EnumProperty.create("part", GrandPianoPart.class);
    public static final EnumProperty<DrumSetPart> DRUM_SET_PART = EnumProperty.create("part", DrumSetPart.class);
    public static final IntegerProperty SEGMENT_AMOUNT = IntegerProperty.create("segment_amount", 1, 4);
    public static final EnumProperty<ReedPart> REED_PART = EnumProperty.create("part", ReedPart.class);
    public static final BooleanProperty TALL_REED = BooleanProperty.create("tall_reed");
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    public static final IntegerProperty WIND_LEVEL = IntegerProperty.create("wind_level", 0, 5);
}
