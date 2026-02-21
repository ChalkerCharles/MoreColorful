package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.Self;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class LevelMixin implements Self<Level> {
    @Inject(method = "isRainingAt", at = @At("HEAD"), cancellable = true)
    private void isRainingAt(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        double d = LevelSavedData.getCanopy(moreColorful$self(), pos.getX(), pos.getZ());
        if (!Double.isNaN(d) && pos.getY() < d) cir.setReturnValue(false);
    }
}
