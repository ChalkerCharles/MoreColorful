package com.ChalkerCharles.morecolorful.mixin.map;

import com.ChalkerCharles.morecolorful.util.mixin.IMapItemSavedDataExtension;
import com.ChalkerCharles.morecolorful.util.mixin.IMapPatchExtension;
import com.llamalad7.mixinextras.sugar.Local;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(MapItemSavedData.MapPatch.class)
public abstract class MapPatchMixin implements IMapPatchExtension {
    @Shadow
    @Final
    private int startX;
    @Shadow
    @Final
    private int startY;
    @Shadow
    @Final
    private int width;
    @Unique
    private byte[] moreColorful$Colors;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Inject(method = "write", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/FriendlyByteBuf;writeByteArray(Lio/netty/buffer/ByteBuf;[B)V", shift = At.Shift.AFTER))
    private static void write(ByteBuf byteBuf, Optional<MapItemSavedData.MapPatch> mapPatchOptional, CallbackInfo ci, @Local MapItemSavedData.MapPatch mapPatch) {
        FriendlyByteBuf.writeByteArray(byteBuf, ((IMapPatchExtension) (Object) mapPatch).moreColorful$getColors());
    }

    @Inject(method = "read", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
    private static void read(ByteBuf byteBuf, CallbackInfoReturnable<Optional<MapItemSavedData.MapPatch>> cir) {
        byte[] bytes = FriendlyByteBuf.readByteArray(byteBuf);
        MapItemSavedData.MapPatch mapPatch = cir.getReturnValue().orElseThrow();
        ((IMapPatchExtension) (Object) mapPatch).moreColorful$setColors(bytes);
        cir.setReturnValue(Optional.of(mapPatch));
    }

    @Inject(method = "applyToMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;setColor(IIB)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void applyToMap(MapItemSavedData pSavedData, CallbackInfo ci, int i, int j) {
        ((IMapItemSavedDataExtension) pSavedData).moreColorful$setColor(this.startX + i, this.startY + j, this.moreColorful$Colors[i + j * this.width]);
    }

    @Unique
    @Override
    public byte[] moreColorful$getColors() {
        return this.moreColorful$Colors;
    }

    @Unique
    @Override
    public void moreColorful$setColors(byte[] colors) {
        this.moreColorful$Colors = colors;
    }
}
