package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import net.minecraft.client.renderer.ViewArea;

public interface IViewAreaExtension {
    WavyDataTask<?> moreColorful$getWavyDataTask(int sectionX, int sectionY, int sectionZ);

    void moreColorful$setGroupDirty(int sectionX, int sectionY, int sectionZ);

    private static IViewAreaExtension self(ViewArea viewArea) {
        return (IViewAreaExtension) viewArea;
    }

    static WavyDataTask<?> getWavyDataTask(ViewArea viewArea, int sectionX, int sectionY, int sectionZ) {
        return self(viewArea).moreColorful$getWavyDataTask(sectionX, sectionY, sectionZ);
    }

    static void setGroupDirty(ViewArea viewArea, int sectionX, int sectionY, int sectionZ) {
        self(viewArea).moreColorful$setGroupDirty(sectionX, sectionY, sectionZ);
    }
}
