package com.ChalkerCharles.morecolorful.mixin.map;

import com.ChalkerCharles.morecolorful.util.mixin.IMapItemSavedDataExtension;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
            ((IMapItemSavedDataExtension) mapitemsaveddata).moreColorful$setColors(bytes);
        }
    }

    @Inject(method = "save", at = @At(value = "INVOKE", target = "net/minecraft/nbt/CompoundTag.putByteArray(Ljava/lang/String;[B)V", shift = At.Shift.AFTER))
    private void save(CompoundTag pTag, HolderLookup.Provider pRegistries, CallbackInfoReturnable<CompoundTag> cir) {
        pTag.putByteArray("moreColorful_colors", this.moreColorful$colors);
    }

    @Inject(method = "locked", at = @At(value = "INVOKE", target = "net/minecraft/world/level/saveddata/maps/MapItemSavedData.setDirty ()V"))
    private void locked(CallbackInfoReturnable<MapItemSavedData> cir, @Local(ordinal = 1) MapItemSavedData mapitemsaveddata) {
        System.arraycopy(this.moreColorful$colors, 0, ((IMapItemSavedDataExtension) mapitemsaveddata).moreColorful$getColors(), 0, this.moreColorful$colors.length);
    }

    @Unique
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

    @Unique
    @Override
    public void moreColorful$setColor(int pX, int pZ, byte pColor) {
        this.moreColorful$colors[pX + pZ * 128] = pColor;
        this.setColorsDirty(pX, pZ);
    }

    @Unique
    @Override
    public byte[] moreColorful$getColors() {
        return this.moreColorful$colors;
    }

    @Unique
    @Override
    public void moreColorful$setColors(byte[] colors) {
        this.moreColorful$colors = colors;
    }
}
