package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.LevelThermalEngine;

public interface IChunkHolderExtension {
    void moreColorful$setThermalEngine(LevelThermalEngine engine);

    void moreColorful$sectionThermalChanged(int pSectionY);
}
