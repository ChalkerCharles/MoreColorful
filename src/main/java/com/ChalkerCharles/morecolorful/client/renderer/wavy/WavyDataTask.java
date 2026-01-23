package com.ChalkerCharles.morecolorful.client.renderer.wavy;

import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import com.ChalkerCharles.morecolorful.util.client.WindSectionMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class WavyDataTask<V extends WavyVertices> implements Comparable<WavyDataTask<V>> {
    protected final BlockPos origin;
    public final WindSectionMap windMap = new WindSectionMap();
    protected volatile List<WindZone> windZones;
    private volatile double distToCameraSq;
    public volatile boolean isHighPriority = true;
    public volatile boolean skip = false;
    public volatile boolean cancelled = false;
    public final V[] vertices;

    protected WavyDataTask(BlockPos origin, V[] vertices) {
        this.origin = origin;
        this.vertices = vertices;
    }

    public void calculateDist(Vec3 camera) {
        double d0 = this.origin.getX() + 8.0 - camera.x;
        double d1 = this.origin.getY() + 8.0 - camera.y;
        double d2 = this.origin.getZ() + 8.0 - camera.z;
        this.distToCameraSq = d0 * d0 + d1 * d1 + d2 * d2;
        this.isHighPriority = this.distToCameraSq < 4096.0;
    }

    @Override
    public int compareTo(WavyDataTask<V> o) {
        return Double.compare(this.distToCameraSq, o.distToCameraSq);
    }

    public void cancel() {
        this.cancelled = true;
    }

    public boolean isInvalid() {
        return RenderUtils.isCalm && this.windZones.isEmpty() && !this.windMap.isEmpty();
    }

    public void close() {
        for (WavyVertices vertices : this.vertices) {
            vertices.close();
        }
    }

    public void setWindZones(List<WindZone> windZones) {
        this.windZones = windZones;
        for (WavyVertices vertices : this.vertices) {
            vertices.setWindZones(windZones);
        }
    }

    public void setWindZones(Level level) {
        long sectionPos = SectionPos.asLong(this.origin);
        List<WindZone> windZones = LevelSavedData.getWindZones(level, sectionPos);
        this.setWindZones(windZones);
    }

    public void clear() {
        this.windMap.clear();
        for (WavyVertices vertices : this.vertices) {
            vertices.clear();
        }
    }

    public void encapsulate() {
        try {
            for (WavyVertices vertices : this.vertices) {
                vertices.encapsulate();
            }
        } catch (Exception ignored) {}
    }

    public static class Default extends WavyDataTask<WavyVertices.Default> {
        public Default(BlockPos origin, int id0, int id1, int id2) {
            super(origin, new WavyVertices.Default[3]);
            long sectionPos = SectionPos.asLong(origin);
            List<WindZone> windZones = LevelSavedData.getWindZones(Minecraft.getInstance().level, sectionPos);
            this.windZones = windZones;
            this.vertices[0] = new WavyVertices.Default(origin, this.windMap, windZones, id0);
            this.vertices[1] = new WavyVertices.Default(origin, this.windMap, windZones, id1);
            this.vertices[2] = new WavyVertices.Default(origin, this.windMap, windZones, id2);
        }

        @Nullable
        public WavyVertices.Default getVertices(RenderType type) {
            if (type == RenderType.cutoutMipped())
                return this.vertices[0];
            if (type == RenderType.cutout())
                return this.vertices[1];
            if (type == RenderType.translucent())
                return this.vertices[2];
            return null;
        }
    }
}
