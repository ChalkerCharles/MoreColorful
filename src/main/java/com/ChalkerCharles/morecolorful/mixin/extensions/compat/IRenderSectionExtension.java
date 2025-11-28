package com.ChalkerCharles.morecolorful.mixin.extensions.compat;

import com.ChalkerCharles.morecolorful.client.compat.SodiumWavyTask;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;

public interface IRenderSectionExtension {
    SodiumWavyTask moreColorful$getWavyTask();

    private static IRenderSectionExtension self(RenderSection section) {
        return (IRenderSectionExtension) section;
    }

    static SodiumWavyTask getWavyTask(RenderSection section) {
        return self(section).moreColorful$getWavyTask();
    }
}
