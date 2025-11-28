package com.ChalkerCharles.morecolorful.mixin.mixins.chunk;

import com.ChalkerCharles.morecolorful.common.level.thermal.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ThreadedLevelVentEngine;
import com.ChalkerCharles.morecolorful.mixin.extensions.IWorldGenContextExtension;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldGenContext.class)
public abstract class WorldGenContextMixin implements IWorldGenContextExtension {
    @Unique
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;
    @Unique
    private ThreadedLevelVentEngine moreColorful$ventEngine;

    @Override
    public ThreadedLevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }

    @Override
    public void moreColorful$setThermalEngine(ThreadedLevelThermalEngine thermalEngine) {
        this.moreColorful$thermalEngine = thermalEngine;
    }

    @Override
    public ThreadedLevelVentEngine moreColorful$getVentEngine() {
        return this.moreColorful$ventEngine;
    }

    @Override
    public void moreColorful$setVentEngine(ThreadedLevelVentEngine ventEngine) {
        this.moreColorful$ventEngine = ventEngine;
    }
}
