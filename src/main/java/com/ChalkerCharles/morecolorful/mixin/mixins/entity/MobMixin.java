package com.ChalkerCharles.morecolorful.mixin.mixins.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.item.utility.UmbrellaItem;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {
    @Shadow
    @Nullable
    private Leashable.LeashData leashData;

    private MobMixin(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Inject(method = "stopInPlace", at = @At("TAIL"))
    private void stopInPlace(CallbackInfo ci) {
        if (Config.enhancedLeash) {
            if (this.leashData != null) {
                ILeashableExtension.LeashData.setAngularMomentum(this.leashData, 0.0);
            }
        }
    }

    @Inject(method = "isSunBurnTick", at = @At("HEAD"), cancellable = true)
    private void isSunBurnTick(CallbackInfoReturnable<Boolean> cir) {
        if (UmbrellaItem.isHolding(this)) {
            cir.setReturnValue(false);
        } else {
            int x = this.getBlockX(), z = this.getBlockZ();
            double y = this.getY(1.0);
            double umbrellaHeight = LevelSavedData.getCanopy(this.level(), x, z);
            if (!Double.isNaN(umbrellaHeight) && y < umbrellaHeight) {
                cir.setReturnValue(false);
            }
        }
    }
}
