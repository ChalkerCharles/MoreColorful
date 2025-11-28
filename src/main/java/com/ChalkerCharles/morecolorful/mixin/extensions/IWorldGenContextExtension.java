package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import net.minecraft.world.level.chunk.status.WorldGenContext;

public interface IWorldGenContextExtension {
    ThreadedLevelThermalEngine moreColorful$getThermalEngine();

    void moreColorful$setThermalEngine(ThreadedLevelThermalEngine thermalEngine);

    ThreadedLevelVentEngine moreColorful$getVentEngine();

    void moreColorful$setVentEngine(ThreadedLevelVentEngine ventEngine);

    private static IWorldGenContextExtension self(WorldGenContext context) {
        return (IWorldGenContextExtension) (Object) context;
    }

    static ThreadedLevelThermalEngine getThermalEngine(WorldGenContext context) {
        return self(context).moreColorful$getThermalEngine();
    }

    static void setThermalEngine(WorldGenContext context, ThreadedLevelThermalEngine thermalEngine) {
        self(context).moreColorful$setThermalEngine(thermalEngine);
    }

    static ThreadedLevelVentEngine getVentEngine(WorldGenContext context) {
        return self(context).moreColorful$getVentEngine();
    }

    static void setVentEngine(WorldGenContext context, ThreadedLevelVentEngine ventEngine) {
        self(context).moreColorful$setVentEngine(ventEngine);
    }
}
