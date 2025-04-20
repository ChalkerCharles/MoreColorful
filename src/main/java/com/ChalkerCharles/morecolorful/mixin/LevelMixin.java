package com.ChalkerCharles.morecolorful.mixin;

import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor, ILevelExtension {
    @Unique
    @Override
    public LevelThermalEngine moreColorful$getThermalEngine() {
        return ((IChunkSourceExtension) this.getChunkSource()).moreColorful$getThermalEngine();
    }

    @Unique
    @Override
    public int moreColorful$getTemperature(BlockPos pBlockPos) {
        return this.moreColorful$getThermalEngine().getLayerListener().getTemperatureValue(pBlockPos);
    }
}
