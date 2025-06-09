package com.ChalkerCharles.morecolorful.mixin.mixins.map;

import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapColor.class)
public abstract class MapColorMixin {
    @Shadow
    @Final
    private static MapColor[] MATERIAL_COLORS;

    @Inject(method = "<init>(II)V", at = @At("TAIL"))
    private void constructor(int pId, int pCol, CallbackInfo ci) {
        MATERIAL_COLORS[0] = MapColor.NONE;
    }
}
