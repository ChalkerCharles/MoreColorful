package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.ILevelThermalEngine;
import net.minecraft.core.BlockPos;

public interface ILevelExtension {
    ILevelThermalEngine moreColorful$getThermalEngine();

    int moreColorful$getTemperature(BlockPos pBlockPos);

    void moreColorful$queueThermalUpdate(Runnable pTask);

    void moreColorful$pollThermalUpdates();
}
