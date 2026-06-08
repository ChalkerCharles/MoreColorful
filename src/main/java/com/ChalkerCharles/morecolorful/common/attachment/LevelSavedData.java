package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.level.EntitiesInSmoke;
import com.ChalkerCharles.morecolorful.common.level.UmbrellaHeightMap;
import com.ChalkerCharles.morecolorful.common.level.thermal.ILevelThermalEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.ILevelVentEngine;
import com.ChalkerCharles.morecolorful.common.level.wind.WindManager;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZone;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZoneManager;
import com.ChalkerCharles.morecolorful.mixin.extensions.IChunkSourceExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelChunkExtension;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;

public abstract class LevelSavedData implements INBTSerializable<CompoundTag> {
    private final UmbrellaHeightMap umbrellaHeightMap = new UmbrellaHeightMap();
    private final EntitiesInSmoke entitiesInSmoke = new EntitiesInSmoke();

    public static LevelSavedData create(IAttachmentHolder holder) {
        Level level = (Level) holder;
        if (level instanceof ClientLevel clientLevel) {
            return new ClientLevelData(clientLevel);
        } else if (level instanceof ServerLevel serverLevel) {
            return new ServerLevelData(serverLevel);
        } else {
            return new Dummy(level);
        }
    }

    protected static LevelSavedData get(Level level) {
        return level.getData(ModDataAttachments.LEVEL_DATA);
    }

    protected abstract Level level();

    protected abstract WindManager windManager();

    protected abstract WindZoneManager windZoneManager();

    public static BlockState getBlockState(Level level, int x, int y, int z) {
        if (level.isOutsideBuildHeight(y)) {
            return Blocks.VOID_AIR.defaultBlockState();
        } else {
            LevelChunk levelchunk = level.getChunk(x >> 4, z >> 4);
            return ILevelChunkExtension.getBlockState(levelchunk, x, y, z);
        }
    }

    public static FluidState getFluidState(Level level, int x, int y, int z) {
        if (level.isOutsideBuildHeight(y)) {
            return Fluids.EMPTY.defaultFluidState();
        } else {
            LevelChunk levelchunk = level.getChunk(x >> 4, z >> 4);
            return levelchunk.getFluidState(x, y, z);
        }
    }

    public static ILevelThermalEngine getThermalEngine(Level level) {
        return IChunkSourceExtension.getThermalEngine(level.getChunkSource());
    }

    public static int getTemperature(Level level, BlockPos blockPos) {
        return getThermalEngine(level).getLayerListener().getTemperatureValue(blockPos);
    }

    public static ILevelVentEngine getVentEngine(Level level) {
        return IChunkSourceExtension.getVentEngine(level.getChunkSource());
    }

    public static int getVentilation(Level level, long packedPos) {
        return getVentEngine(level).getLayerListener().getVentilationValue(packedPos);
    }

    public static int getVentilation(Level level, BlockPos blockPos) {
        return getVentilation(level, blockPos.asLong());
    }

    public static int getVentilation(Level level, int x, int y, int z) {
        return getVentilation(level, BlockPos.asLong(x, y, z));
    }

    private void tick() {
        if (Config.windSystem) {
            this.windZoneManager().tick();
            if (WeatherUtils.isWindy(this.level())) {
                this.windManager().tick();
            }
        }
        this.umbrellaHeightMap.tick();
        this.entitiesInSmoke.tick();
    }

    public static void tick(Level level) {
        get(level).tick();
    }

    public static Vector2f getGlobalWindSpeed(Level level) {
        return get(level).windManager().globalWindSpeed;
    }

    public static boolean isWindCalm(Level level) {
        return get(level).windManager().isCalm;
    }

    public static Vector2f getWindDirection(Level level) {
        return get(level).windManager().windDirection;
    }

    public static Direction getNearestWindDirection(Level level) {
        return get(level).windManager().nearestDirection;
    }

    public static void setGlobalWindSpeed(Level level, float x, float z) {
        get(level).windManager().setWindSpeed(x, z);
    }

    public static void initGlobalWindSpeed(Level level, float x, float z) {
        get(level).windManager().initWindSpeed(x, z);
    }

    public static void addWindZone(Level level, WindZone windZone) {
        get(level).windZoneManager().addWindZone(windZone);
    }

    public static void updateWindZoneSections(Level level, WindZone zone, long[] oldSections, long[] newSections) {
        get(level).windZoneManager().updateWindZoneSections(zone, oldSections, newSections);
    }

    public static List<WindZone> getWindZones(Level level, long sectionPos) {
        return get(level).windZoneManager().getWindZones(sectionPos);
    }

    public static Vector3f getLocalWindSpeedAt(Level level, double x, double y, double z, long sectionPos) {
        return get(level).windZoneManager().getLocalWindSpeedAt(x, y, z, sectionPos);
    }

    public static Vector3f getLocalWindSpeedAt(Level level, double x, double y, double z) {
        long sectionPos = Maths.sectionPos(x, y, z);
        return getLocalWindSpeedAt(level, x, y, z, sectionPos);
    }

    public static Vector3f getLocalWindSpeedAt(Level level, BlockPos pos) {
        long sectionPos = SectionPos.asLong(pos);
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        return getLocalWindSpeedAt(level, x, y, z, sectionPos);
    }

    public static boolean isInWindZone(Level level, double x, double y, double z) {
        long sectionPos = Maths.sectionPos(x, y, z);
        return get(level).windZoneManager().isInWindZone(x, y, z, sectionPos);
    }

    public static boolean isInWindZone(Level level, BlockPos pos) {
        long sectionPos = SectionPos.asLong(pos);
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        return get(level).windZoneManager().isInWindZone(x, y, z, sectionPos);
    }

    public static Vector3f getLocalWindSpeedAffectingEntity(Level level, double x, double y, double z) {
        long sectionPos = Maths.sectionPos(x, y, z);
        return get(level).windZoneManager().getLocalWindSpeedAffectingEntity(x, y, z, sectionPos);
    }

    public static boolean isInWindZoneAffectingEntity(Level level, double x, double y, double z) {
        long sectionPos = Maths.sectionPos(x, y, z);
        return get(level).windZoneManager().isInWindZoneAffectingEntity(x, y, z, sectionPos);
    }

    public static void onChunkUnload(Level level, ChunkPos pos) {
        get(level).windZoneManager().onChunkUnload(level, pos);
    }

    public static void setCanopy(Level level, int x, double y, int z) {
        get(level).umbrellaHeightMap.setCanopy(x, y, z);
    }

    public static double getCanopy(Level level, int x, int z) {
        return get(level).umbrellaHeightMap.getCanopy(x, z);
    }

    public static void addEntityInSmoke(Level level, Entity entity) {
        get(level).entitiesInSmoke.addEntity(entity);
    }

    public static boolean isEntityInSmoke(Level level, Entity entity) {
        return get(level).entitiesInSmoke.isInSmoke(entity);
    }

    public Map<String, LongSet> getMailboxes() {
        return Map.of();
    }

    public static Map<String, LongSet> getMailboxes(Level level) {
        return get(level).getMailboxes();
    }

    @Override
    @UnknownNullability
    public CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag nbt = new CompoundTag();
        this.windManager().serialize(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.windManager().deserialize(nbt);
    }

    public static class Dummy extends LevelSavedData {
        private final Level level;

        public Dummy(Level level) {
            this.level = level;
        }

        @Override
        protected Level level() {
            return this.level;
        }

        @Override
        protected WindManager windManager() {
            return WindManager.DUMMY;
        }

        @Override
        protected WindZoneManager windZoneManager() {
            return WindZoneManager.DUMMY;
        }
    }
}
