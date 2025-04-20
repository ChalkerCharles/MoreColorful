package com.ChalkerCharles.morecolorful.util.mixin;

import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;
import net.minecraft.core.BlockPos;

public interface ILevelExtension {
    LevelThermalEngine moreColorful$getThermalEngine();

    int moreColorful$getTemperature(BlockPos pBlockPos);

    void moreColorful$queueThermalUpdate(Runnable pTask);

    void moreColorful$pollLightUpdates();
}
