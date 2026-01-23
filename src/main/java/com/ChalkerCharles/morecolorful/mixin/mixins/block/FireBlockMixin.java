package com.ChalkerCharles.morecolorful.mixin.mixins.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector2f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireBlock.class)
public abstract class FireBlockMixin {
    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/FireBlock;getIgniteOdds(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)I"))
    private int tick(int original, BlockState state, ServerLevel level, BlockPos pos, @Local BlockPos.MutableBlockPos mutable) {
        if (original == 0) return 0;
        if (Config.windSystem && Config.windAidingFireSpread) {
            if (WeatherUtils.canApplyWind(level, pos)) {
                Vector2f wind = LevelSavedData.getGlobalWindSpeed(level);
                Vector2f dir = LevelSavedData.getWindDirection(level);
                int x = mutable.getX() - pos.getX(), z = mutable.getZ() - pos.getZ();
                float m = 1.0F / Maths.length(x, z);
                float f = dir.x * x + dir.y * z;
                original += Math.max(-40, Mth.floor(wind.length() * f * m * 4));
            }
        }
        return original;
    }
}
