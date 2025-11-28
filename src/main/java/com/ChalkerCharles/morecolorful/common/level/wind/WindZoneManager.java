package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import it.unimi.dsi.fastutil.longs.Long2ReferenceMap;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class WindZoneManager {
    private final List<WindZone> windZones = new ArrayList<>();
    private final List<WindZone> windZonesToAdd = new ArrayList<>();
    private final Long2ReferenceMap<List<WindZone>> windZoneMap = new Long2ReferenceOpenHashMap<>();

    public List<WindZone> getWindZones(long sectionPos) {
        List<WindZone> zones = this.windZoneMap.get(sectionPos);
        return zones == null ? List.of() : zones;
    }

    public void addWindZone(WindZone zone) {
        this.windZonesToAdd.add(zone);
        this.addWindZoneToMap(zone);
    }

    public void addWindZoneToMap(WindZone zone) {
        this.addWindZoneToMap(zone, zone.sections);
    }

    public void addWindZoneToMap(WindZone zone, long[] sections) {
        for (long pos : sections) {
            this.windZoneMap.computeIfAbsent(pos, this::createList).add(zone);
        }
    }

    protected List<WindZone> createList(long pos) {
        return new ArrayList<>();
    }

    public void removeWindZoneFromMap(WindZone zone) {
        this.removeWindZoneFromMap(zone, zone.sections);
    }

    public void removeWindZoneFromMap(WindZone zone, long[] sections) {
        for (long pos : sections) {
            List<WindZone> list = this.windZoneMap.get(pos);
            if (list == null) continue;
            list.remove(zone);
            if (list.isEmpty()) {
                this.windZoneMap.remove(pos);
            }
        }
    }

    public void updateWindZoneSections(WindZone zone, long[] oldSections, long[] newSections) {
        this.removeWindZoneFromMap(zone, oldSections);
        this.addWindZoneToMap(zone, newSections);
    }

    public void onChunkUnload(Level level, ChunkPos pos) {
        int i = pos.x, k = pos.z;
        List<WindZone> toRemove = new ArrayList<>();
        for (int j = level.getMinSection(), l = level.getMaxSection(); j < l; j++) {
            long sectionPos = SectionPos.asLong(i, j, k);
            List<WindZone> list = this.windZoneMap.remove(sectionPos);
            if (list != null) toRemove.addAll(list);
        }
        this.windZones.removeAll(toRemove);
    }

    public Vector3f getLocalWindSpeedAt(double x, double y, double z, long sectionPos) {
        Vector3f vec = new Vector3f();
        List<WindZone> windZones = this.getWindZones(sectionPos);
        for (WindZone w : windZones) {
            if (w.contains(x, y, z)) w.addSpeedAt(vec, x, y, z);
        }
        return vec;
    }

    public boolean isInWindZone(double x, double y, double z, long sectionPos) {
        List<WindZone> windZones = this.getWindZones(sectionPos);
        for (WindZone w : windZones) {
            if (w.contains(x, y, z)) return true;
        }
        return false;
    }

    public Vector3f getLocalWindSpeedAffectingEntity(Vec3 pos, long sectionPos) {
        Vector3f vec = new Vector3f();
        List<WindZone> windZones = this.getWindZones(sectionPos);
        for (WindZone w : windZones) {
            if (w.contains(pos) && w.affectEntity) w.addSpeedAt(vec, pos);
        }
        return vec;
    }

    public boolean isInWindZoneAffectingEntity(Vec3 pos, long sectionPos) {
        List<WindZone> windZones = this.getWindZones(sectionPos);
        for (WindZone w : windZones) {
            if (w.contains(pos) && w.affectEntity) return true;
        }
        return false;
    }

    public void updateWindZones() {
        List<WindZone> toRemove = new ArrayList<>();
        for (WindZone zone : this.windZones) {
            zone.tick();
            if (zone.isAlive()) continue;
            toRemove.add(zone);
            this.removeWindZoneFromMap(zone);
        }
        this.windZones.removeAll(toRemove);
        this.windZones.addAll(windZonesToAdd);
        this.windZonesToAdd.clear();
    }

    public String getStats() {
        return "";
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends WindZoneManager {
        @Override
        protected List<WindZone> createList(long pos) {
            List<WindZone> list = new ArrayList<>();
            ILevelRendererExtension.setWindZones(list, pos);
            return list;
        }

        @Override
        public String getStats() {
            List<WindZone> zones = super.windZones;
            return "Wind Zones: " + countWindZonesInView(zones) + "/" + zones.size();
        }

        private static int countWindZonesInView(List<WindZone> zones) {
            Frustum frustum = Minecraft.getInstance().levelRenderer.getFrustum();
            int i = 0;
            for (WindZone zone : zones) {
                if (frustum.isVisible(zone.bb)) i++;
            }
            return i;
        }
    }
}
