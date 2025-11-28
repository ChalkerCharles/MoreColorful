package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.common.block.properties.MapColorExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IMapItemSavedDataExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.gui.MapRenderer$MapInstance")
public abstract class MapInstanceMixin {
    @Shadow
    private MapItemSavedData data;

    @ModifyExpressionValue(method = "updateTexture()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/MapColor;getColorFromPackedId(I)I"))
    private int updateTexture(int original, @Local(ordinal = 2) int k) {
        return this.moreColorful$getAbgrColor(k);
    }

    @Unique
    private int moreColorful$getAbgrColor(int index) {
        int i = MapColorExtension.getColorFromPackedId(IMapItemSavedDataExtension.getColors(this.data)[index]);
        if (i == 0) {
            i = MapColor.getColorFromPackedId(this.data.colors[index]);
        }
        return i;
    }
}
