package com.ChalkerCharles.morecolorful.mixin.mixins.item;

import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.ShearsDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(ShearsDispenseItemBehavior.class)
public abstract class ShearsDispenseItemBehaviorMixin {
    @ModifyArg(method = "execute", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/dispenser/ShearsDispenseItemBehavior;setSuccess(Z)V"))
    protected boolean execute(boolean b, @Local ServerLevel serverLevel, @Local BlockPos blockPos) {
        return b || moreColorful$tryShearEntity(serverLevel, blockPos);
    }

    @Unique
    private static boolean moreColorful$tryShearEntity(ServerLevel serverLevel, BlockPos blockPos) {
        List<Entity> list = serverLevel.getEntitiesOfClass(Entity.class, new AABB(blockPos), EntitySelector.NO_SPECTATORS);
        for (Entity entity : list) {
            if (EntityUtils.shearOffAllLeashConnections(entity, null)) {
                return true;
            }
        }
        return false;
    }
}
