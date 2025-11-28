package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataUpdateDispatcher;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.compat.IRenderSectionManagerExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.client.compat.sodium.ISodiumWorldRendererMixin;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.caffeinemc.mods.sodium.client.render.SodiumWorldRenderer;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.lists.ChunkRenderList;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.caffeinemc.mods.sodium.client.util.iterator.ByteIterator;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.concurrent.Executor;

public final class SodiumCompat {
    @Nullable
    public static SodiumWavyVertices getWavyVertices(TerrainRenderPass pass) {
        return ((SodiumWavyTask) RenderUtils.WAVY_TASK.get()).getVertices(pass);
    }

    public static SodiumWavyDataDispatcher createDispatcher(Executor executor) {
        return new SodiumWavyDataDispatcher(executor);
    }

    public static void updateWindData(WavyDataUpdateDispatcher<?> wavyDispatcher) {
        var dispatcher = (SodiumWavyDataDispatcher) wavyDispatcher;
        SodiumWorldRenderer renderer = SodiumWorldRenderer.instance();
        Iterator<ChunkRenderList> iterator = ((ISodiumWorldRendererMixin) renderer).getManager().getRenderLists().iterator();
        while (iterator.hasNext()) {
            ChunkRenderList list = iterator.next();
            ByteIterator byteIterator = list.sectionsWithGeometryIterator(false);
            if (byteIterator == null) continue;
            RenderRegion region = list.getRegion();
            while (byteIterator.hasNext()) {
                int i = byteIterator.nextByteAsInt();
                RenderSection section = region.getSection(i);
                if (section == null) continue;
                dispatcher.trySchedule(IRenderSectionExtension.getWavyTask(section));
            }
        }
    }

    public static void clearWindCache() {
        IRenderSectionManagerExtension.clearWindCache();
    }

    @Nullable
    public static WavyDataTask<?> getWavyDataTask(int sectionX, int sectionY, int sectionZ) {
        return IRenderSectionManagerExtension.getWavyDataTask(sectionX, sectionY, sectionZ);
    }
}
