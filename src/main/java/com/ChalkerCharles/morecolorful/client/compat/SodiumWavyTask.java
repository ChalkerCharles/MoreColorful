package com.ChalkerCharles.morecolorful.client.compat;

import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import net.caffeinemc.mods.sodium.client.gl.arena.GlBufferArena;
import net.caffeinemc.mods.sodium.client.model.quad.properties.ModelQuadFacing;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.data.SectionRenderDataStorage;
import net.caffeinemc.mods.sodium.client.render.chunk.data.SectionRenderDataUnsafe;
import net.caffeinemc.mods.sodium.client.render.chunk.region.RenderRegion;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.DefaultTerrainRenderPasses;
import net.caffeinemc.mods.sodium.client.render.chunk.terrain.TerrainRenderPass;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL45C;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class SodiumWavyTask extends WavyDataTask<SodiumWavyVertices> {
    private static final int ACCESS = GL45C.GL_MAP_WRITE_BIT | GL45C.GL_MAP_FLUSH_EXPLICIT_BIT | GL45C.GL_MAP_UNSYNCHRONIZED_BIT;
    public final RenderSection section;

    public SodiumWavyTask(RenderSection section) {
        super(getOrigin(section), new SodiumWavyVertices[2]);
        this.section = section;
        long sectionPos = SectionPos.asLong(this.origin);
        List<WindZone> windZones = LevelSavedData.getWindZones(Minecraft.getInstance().level, sectionPos);
        this.windZones = windZones;
        this.vertices[0] = new SodiumWavyVertices(this.origin, this.windMap, windZones);
        this.vertices[1] = new SodiumWavyVertices(this.origin, this.windMap, windZones);
    }

    protected void setValid() {
        this.vertices[0].invalid = false;
        this.vertices[1].invalid = false;
    }

    private static BlockPos getOrigin(RenderSection section) {
        return new BlockPos(section.getOriginX(), section.getOriginY(), section.getOriginZ());
    }

    public static void update(RenderRegion region, List<SodiumWavyTask> tasks) {
        SectionRenderDataStorage cutout = region.getStorage(DefaultTerrainRenderPasses.CUTOUT);
        SectionRenderDataStorage translucent = region.getStorage(DefaultTerrainRenderPasses.TRANSLUCENT);
        if (cutout == null || translucent == null) return;
        GlBufferArena arena = region.getResources().getGeometryArena();
        int id = arena.getBufferObject().handle();
        long length = arena.getDeviceAllocatedMemory();
        long address = GL45C.nglMapNamedBufferRange(id, 0, length, ACCESS);
        if (address == 0L) return;
        updateForPass(address, tasks, 0, cutout);
        updateForPass(address, tasks, 1, translucent);
        GL45C.glUnmapNamedBuffer(id);
    }

    private static void updateForPass(long address, List<SodiumWavyTask> tasks, int i, SectionRenderDataStorage storage) {
        for (SodiumWavyTask task : tasks) {
            long pMeshData = storage.getDataPointer(task.section.getSectionIndex());
            int slice = ModelQuadFacing.ALL & SectionRenderDataUnsafe.getSliceMask(pMeshData);
            if (slice == 0) continue;
            task.vertices[i].update(address, pMeshData, slice);
        }
    }

    @Nullable
    public SodiumWavyVertices getVertices(TerrainRenderPass pass) {
        if (pass == DefaultTerrainRenderPasses.CUTOUT)
            return this.vertices[0];
        if (pass == DefaultTerrainRenderPasses.TRANSLUCENT)
            return this.vertices[1];
        return null;
    }
}
