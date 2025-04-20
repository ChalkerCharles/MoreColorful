package com.ChalkerCharles.morecolorful.util.mixin;

import net.minecraft.world.level.material.MapColor;

public interface IMapColorExtension {
    byte moreColorful$getPackedId(MapColor.Brightness pBrightness);
}
