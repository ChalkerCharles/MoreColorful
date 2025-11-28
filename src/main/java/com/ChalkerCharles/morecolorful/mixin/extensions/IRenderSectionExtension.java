package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;

public interface IRenderSectionExtension {
    WavyDataTask.Default moreColorful$getWavyTask();

    void moreColorful$setDirty();

    private static IRenderSectionExtension self(SectionRenderDispatcher.RenderSection section) {
        return (IRenderSectionExtension) section;
    }

    static WavyDataTask.Default getWavyTask(SectionRenderDispatcher.RenderSection section) {
        return self(section).moreColorful$getWavyTask();
    }

    static void setDirty(SectionRenderDispatcher.RenderSection section) {
        self(section).moreColorful$setDirty();
    }
}
