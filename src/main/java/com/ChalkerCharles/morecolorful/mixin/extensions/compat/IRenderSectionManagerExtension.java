package com.ChalkerCharles.morecolorful.mixin.extensions.compat;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium.ISodiumWorldRendererMixin;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import org.jetbrains.annotations.Nullable;

public interface IRenderSectionManagerExtension {
    void moreColorful$clearWindCache();

    @Nullable
    WavyDataTask<?> moreColorful$getWavyDataTask(int sectionX, int sectionY, int sectionZ);

    private static IRenderSectionManagerExtension self() {
        return (IRenderSectionManagerExtension) ((ISodiumWorldRendererMixin) SodiumWorldRenderer.instance()).getManager();
    }

    static void clearWindCache() {
        var self = self();
        if (self != null) self.moreColorful$clearWindCache();
    }

    @Nullable
    static WavyDataTask<?> getWavyDataTask(int sectionX, int sectionY, int sectionZ) {
        return self().moreColorful$getWavyDataTask(sectionX, sectionY, sectionZ);
    }
}
