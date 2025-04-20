package com.ChalkerCharles.morecolorful.mixin.map;

import com.ChalkerCharles.morecolorful.util.mixin.IMapItemSavedDataExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IMapPatchExtension;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(MapItemSavedData.HoldingPlayer.class)
public abstract class HoldingPlayerMixin {
    @Shadow
    @Final
    MapItemSavedData this$0;

    @Inject(method = "createPatch", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    private void createPatch(CallbackInfoReturnable<MapItemSavedData.MapPatch> cir, int i, int j, int k, int l) {
        byte[] bytes = new byte[k * l];
        for (int i1 = 0; i1 < k; i1++) {
            for (int j1 = 0; j1 < l; j1++) {
                bytes[i1 + j1 * k] = ((IMapItemSavedDataExtension) this$0).moreColorful$getColors()[i + i1 + (j + j1) * 128];
            }
        }
        MapItemSavedData.MapPatch mapPatch = cir.getReturnValue();
        ((IMapPatchExtension) (Object) mapPatch).moreColorful$setColors(bytes);
        cir.setReturnValue(mapPatch);
    }
}
