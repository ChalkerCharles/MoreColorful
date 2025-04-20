package com.ChalkerCharles.morecolorful.mixin.map;

import com.ChalkerCharles.morecolorful.util.mixin.IMapColorExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IMapItemSavedDataExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MapItem.class)
public abstract class MapItemMixin {
    @ModifyExpressionValue(method = "update", at = @At(value = "INVOKE", target = "net/minecraft/world/level/saveddata/maps/MapItemSavedData.updateColor(IIB)Z"))
    private boolean update(boolean original, Level pLevel, Entity pViewer, MapItemSavedData pData,
                                       @Local(ordinal = 6) int k1,
                                       @Local(ordinal = 7) int l1,
                                       @Local MapColor mapcolor,
                                       @Local MapColor.Brightness mapcolor$brightness) {
        return original | ((IMapItemSavedDataExtension) pData).moreColorful$updateColor(k1, l1, ((IMapColorExtension) mapcolor).moreColorful$getPackedId(mapcolor$brightness));
    }
}
