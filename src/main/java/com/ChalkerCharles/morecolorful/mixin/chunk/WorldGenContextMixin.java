package com.ChalkerCharles.morecolorful.mixin.chunk;

import com.ChalkerCharles.morecolorful.common.level.ThreadedLevelThermalEngine;
import com.ChalkerCharles.morecolorful.util.mixin.IWorldGenContextExtension;
import net.minecraft.world.level.chunk.status.WorldGenContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(WorldGenContext.class)
public abstract class WorldGenContextMixin implements IWorldGenContextExtension {
    @Unique
    private ThreadedLevelThermalEngine moreColorful$thermalEngine;

    @Unique
    @Override
    public ThreadedLevelThermalEngine moreColorful$getThermalEngine() {
        return this.moreColorful$thermalEngine;
    }

    @Unique
    @Override
    public void moreColorful$setThermalEngine(ThreadedLevelThermalEngine thermalEngine) {
        this.moreColorful$thermalEngine = thermalEngine;
    }
}
