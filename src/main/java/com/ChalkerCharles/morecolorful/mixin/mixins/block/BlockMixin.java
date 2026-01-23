package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Inject(method = "stepOn", at = @At("HEAD"))
    private void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity, CallbackInfo ci) {
        if (Config.thermalSystem) {
            boolean overheated = LevelSavedData.getTemperature(pLevel, pPos) > 12;
            if (overheated && !pEntity.isSteppingCarefully() && pEntity instanceof LivingEntity) {
                pEntity.hurt(pLevel.damageSources().hotFloor(), 1.0F);
            }
        }
    }
}
