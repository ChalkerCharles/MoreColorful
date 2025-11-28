package com.ChalkerCharles.morecolorful.mixin.extensions;

import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.util.client.WindSectionMap;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface ILevelRendererExtension {
    String moreColorful$getStatistics();

    void moreColorful$clearWindCache();

    @Nullable
    WindSectionMap moreColorful$getWindSectionMap(int sectionX, int sectionY, int sectionZ);

    void moreColorful$setGroupDirty(int sectionX, int sectionY, int sectionZ);

    void moreColorful$setWindZones(List<WindZone> list, int sectionX, int sectionY, int sectionZ);

    private static ILevelRendererExtension self() {
        return (ILevelRendererExtension) Minecraft.getInstance().levelRenderer;
    }

    static String getStatistics() {
        return self().moreColorful$getStatistics();
    }

    static void clearWindCache() {
        self().moreColorful$clearWindCache();
    }

    @Nullable
    static WindSectionMap getWindSectionMap(int sectionX, int sectionY, int sectionZ) {
        return self().moreColorful$getWindSectionMap(sectionX, sectionY, sectionZ);
    }

    @Nullable
    static WindSectionMap getWindSectionMap(BlockPos blockPos) {
        int x = blockPos.getX() >> 4, y = blockPos.getY() >> 4, z = blockPos.getZ() >> 4;
        return getWindSectionMap(x, y, z);
    }

    static void setGroupDirty(int sectionX, int sectionY, int sectionZ) {
        self().moreColorful$setGroupDirty(sectionX, sectionY, sectionZ);
    }

    static void setWindZones(List<WindZone> list, long sectionPos) {
        int x = SectionPos.x(sectionPos), y = SectionPos.y(sectionPos), z = SectionPos.z(sectionPos);
        self().moreColorful$setWindZones(list, x, y, z);
    }
}
