package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.common.block.properties.MapColorExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IMapItemSavedDataExtension;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = "net.minecraft.client.gui.MapRenderer$MapInstance")
public abstract class MapInstanceMixin {
    @Shadow
    private MapItemSavedData data;
    @Shadow
    @Final
    private DynamicTexture texture;

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "updateTexture()V", at = @At(value = "INVOKE", target = "com/mojang/blaze3d/platform/NativeImage.setPixelRGBA(III)V", shift = At.Shift.AFTER),
            locals = LocalCapture.CAPTURE_FAILSOFT)
    private void updateTexture(CallbackInfo ci, int i, int j, int k) {
        this.texture.getPixels().setPixelRGBA(j, i, moreColorful$getAbgrColor(k));
    }

    @Unique
    private int moreColorful$getAbgrColor(int index) {
        int i = MapColorExtension.getColorFromPackedId(((IMapItemSavedDataExtension) this.data).moreColorful$getColors()[index]);
        if (i == 0) {
            i = MapColor.getColorFromPackedId(this.data.colors[index]);
        }
        return i;
    }
}
