package com.ChalkerCharles.morecolorful.mixin.map;

import com.ChalkerCharles.morecolorful.common.block.properties.MapColorExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IMapColorExtension;
import net.minecraft.world.level.material.MapColor;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MapColor.class)
public abstract class MapColorMixin implements IMapColorExtension {
    @Shadow
    @Final
    @Mutable
    private static MapColor[] MATERIAL_COLORS;

    @Inject(method = "<init>(II)V", at = @At("TAIL"))
    private void constructor(int pId, int pCol, CallbackInfo ci) {
        MATERIAL_COLORS[0] = MapColor.NONE;
    }

    @Unique
    @Override
    public byte moreColorful$getPackedId(MapColor.Brightness pBrightness) {
        if ((MapColor) (Object) this instanceof MapColorExtension mapColorExtension) {
            return (byte)(mapColorExtension.id << 2 | pBrightness.id & 3);
        }
        return 0;
    }
}
