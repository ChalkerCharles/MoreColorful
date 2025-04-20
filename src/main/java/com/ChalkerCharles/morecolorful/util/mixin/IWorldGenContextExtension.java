package com.ChalkerCharles.morecolorful.util.mixin;

import com.ChalkerCharles.morecolorful.common.level.ThreadedLevelThermalEngine;

public interface IWorldGenContextExtension {
    ThreadedLevelThermalEngine moreColorful$getThermalEngine();

    void moreColorful$setThermalEngine(ThreadedLevelThermalEngine thermalEngine);
}
