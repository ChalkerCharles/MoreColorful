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
    public static final IntegerProperty LEAF_AMOUNT = IntegerProperty.create("leaf_amount", 1, 4);
}
