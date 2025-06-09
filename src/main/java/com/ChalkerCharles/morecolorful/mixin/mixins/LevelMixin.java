package com.ChalkerCharles.morecolorful.mixin.mixins;

import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Level.class)
public abstract class LevelMixin implements LevelAccessor, ILevelExtension {
    @Override
    public ILevelThermalEngine moreColorful$getThermalEngine() {
        return ((IChunkSourceExtension) this.getChunkSource()).moreColorful$getThermalEngine();
    }

    @Override
    public int moreColorful$getTemperature(BlockPos pBlockPos) {
        return this.moreColorful$getThermalEngine().getLayerListener().getTemperatureValue(pBlockPos);
    }

    @Override
    public void moreColorful$queueThermalUpdate(Runnable pTask) {
    }

    @Override
    public void moreColorful$pollThermalUpdates() {
    }
}
