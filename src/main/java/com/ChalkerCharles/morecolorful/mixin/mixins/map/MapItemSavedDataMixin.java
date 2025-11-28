package com.ChalkerCharles.morecolorful.mixin.mixins.map;

import com.ChalkerCharles.morecolorful.mixin.extensions.IMapItemSavedDataExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.IMapPatchExtension;
import com.llamalad7.mixinextras.sugar.Local;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.objectweb.asm.Opcodes;
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

@Mixin(MapItemSavedData.class)
public abstract class MapItemSavedDataMixin implements IMapItemSavedDataExtension {
    @Unique
    private byte[] moreColorful$colors = new byte[16384];

    @Shadow
    protected abstract void setColorsDirty(int pX, int pZ);

    @Inject(method = "load", at = @At(value = "FIELD", target = "net/minecraft/nbt/NbtOps.INSTANCE:Lnet/minecraft/nbt/NbtOps;", opcode = Opcodes.GETSTATIC, ordinal = 1))
    private static void load(CompoundTag tag, HolderLookup.Provider provider, CallbackInfoReturnable<MapItemSavedData> cir, @Local MapItemSavedData mapitemsaveddata) {
        byte[] bytes = tag.getByteArray("moreColorful_colors");
        if (bytes.length == 16384) {
            IMapItemSavedDataExtension.setColors(mapitemsaveddata, bytes);
        }
    }

    @Inject(method = "save", at = @At(value = "INVOKE", target = "net/minecraft/nbt/CompoundTag.putByteArray(Ljava/lang/String;[B)V", shift = At.Shift.AFTER))
    private void save(CompoundTag pTag, HolderLookup.Provider pRegistries, CallbackInfoReturnable<CompoundTag> cir) {
        pTag.putByteArray("moreColorful_colors", this.moreColorful$colors);
    }

    @Inject(method = "locked", at = @At(value = "INVOKE", target = "net/minecraft/world/level/saveddata/maps/MapItemSavedData.setDirty ()V"))
    private void locked(CallbackInfoReturnable<MapItemSavedData> cir, @Local(ordinal = 1) MapItemSavedData mapitemsaveddata) {
        System.arraycopy(this.moreColorful$colors, 0, IMapItemSavedDataExtension.getColors(mapitemsaveddata), 0, this.moreColorful$colors.length);
    }

    @Override
    public boolean moreColorful$updateColor(int pX, int pZ, byte pColor) {
        byte b0 = this.moreColorful$colors[pX + pZ * 128];
        if (b0 != pColor) {
            this.moreColorful$setColor(pX, pZ, pColor);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void moreColorful$setColor(int pX, int pZ, byte pColor) {
        this.moreColorful$colors[pX + pZ * 128] = pColor;
        this.setColorsDirty(pX, pZ);
    }

    @Override
    public byte[] moreColorful$getColors() {
        return this.moreColorful$colors;
    }

    @Override
    public void moreColorful$setColors(byte[] colors) {
        this.moreColorful$colors = colors;
    }

    @Mixin(MapItemSavedData.HoldingPlayer.class)
    private static abstract class HoldingPlayerMixin {
        @Shadow
        @Final
        MapItemSavedData this$0;

        @Inject(method = "createPatch", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
        private void createPatch(CallbackInfoReturnable<MapItemSavedData.MapPatch> cir, int i, int j, int k, int l) {
            byte[] bytes = new byte[k * l];
            for (int i1 = 0; i1 < k; i1++) {
                for (int j1 = 0; j1 < l; j1++) {
                    bytes[i1 + j1 * k] = IMapItemSavedDataExtension.getColors(this$0)[i + i1 + (j + j1) * 128];
                }
            }
            MapItemSavedData.MapPatch mapPatch = cir.getReturnValue();
            IMapPatchExtension.setColors(mapPatch, bytes);
            cir.setReturnValue(mapPatch);
        }
    }

    @Mixin(MapItemSavedData.MapPatch.class)
    private static abstract class MapPatchMixin implements IMapPatchExtension {
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
            FriendlyByteBuf.writeByteArray(byteBuf, IMapPatchExtension.getColors(mapPatch));
        }

        @Inject(method = "read", at = @At(value = "RETURN", ordinal = 0), cancellable = true)
        private static void read(ByteBuf byteBuf, CallbackInfoReturnable<Optional<MapItemSavedData.MapPatch>> cir) {
            byte[] bytes = FriendlyByteBuf.readByteArray(byteBuf);
            MapItemSavedData.MapPatch mapPatch = cir.getReturnValue().orElseThrow();
            IMapPatchExtension.setColors(mapPatch, bytes);
            cir.setReturnValue(Optional.of(mapPatch));
        }

        @Inject(method = "applyToMap", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;setColor(IIB)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
        private void applyToMap(MapItemSavedData pSavedData, CallbackInfo ci, int i, int j) {
            IMapItemSavedDataExtension.setColor(pSavedData, this.startX + i, this.startY + j, this.moreColorful$Colors[i + j * this.width]);
        }

        @Override
        public byte[] moreColorful$getColors() {
            return this.moreColorful$Colors;
        }

        @Override
        public void moreColorful$setColors(byte[] colors) {
            this.moreColorful$Colors = colors;
        }
    }
}
