package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;

public final class WeatherUtils {
    public static boolean isWindy(Level level) {
        return !isWindless(level);
    }

    public static boolean isWindless(Level level) {
        if (!Config.windSystem || level.isDebug()) return true;
        return Config.windlessDimensions.contains(level.dimension());
    }

    private static Vector2f getGlobalWindSpeed(Level level) {
        return level.isClientSide ? RenderUtils.windSpeed : LevelSavedData.getGlobalWindSpeed(level);
    }

    private static boolean isWindCalm(Level level) {
        return level.isClientSide ? RenderUtils.isCalm : LevelSavedData.isWindCalm(level);
    }

    private static Vector2f getWindDirection(Level level) {
        return level.isClientSide ? RenderUtils.windDir : LevelSavedData.getWindDirection(level);
    }

    @Nullable
    public static Vector3f getEffectiveWindSpeedAt(Level level, double x, double y, double z) {
        boolean global = canApplyWind(level, x, y, z);
        if (global || LevelSavedData.isInWindZone(level, x, y, z)) {
            return getWindSpeedAt(level, x, y, z, global);
        }
        return null;
    }

    @Nullable
    public static Vector3f getEffectiveWindSpeedAt(Level level, BlockPos pos) {
        boolean global = canApplyWind(level, pos);
        if (global || LevelSavedData.isInWindZone(level, pos)) {
            return getWindSpeedAt(level, pos, global);
        }
        return null;
    }

    @Nullable
    public static Vector3f getEffectiveWindSpeedAffectingEntity(Entity entity) {
        Vec3 pos = entity.position();
        Level level = entity.level();
        double px = pos.x, py = pos.y + 0.5, pz = pos.z;
        boolean global = canApplyWind(level, px, py, pz);
        if (global || LevelSavedData.isInWindZoneAffectingEntity(level,px, py, pz)) {
            return WeatherUtils.getWindSpeedAffectingEntity(level, entity.blockPosition(), px, py, pz, global);
        }
        return null;
    }

    public static Vector3f getWindSpeedAt(Level level, double x, double y, double z, boolean isGloballyWindy) {
        return getWindSpeedAt(level, isGloballyWindy, LevelSavedData.getLocalWindSpeedAt(level, x, y, z));
    }

    public static Vector3f getWindSpeedAt(Level level, BlockPos pos, boolean isGloballyWindy) {
        return getWindSpeedAt(level, isGloballyWindy, LevelSavedData.getLocalWindSpeedAt(level, pos));
    }

    private static Vector3f getWindSpeedAt(Level level, boolean isGloballyWindy, Vector3f local) {
        if (isGloballyWindy) {
            Vector2f vec = getGlobalWindSpeed(level);
            return local.add(vec.x, 0, vec.y);
        } else {
            return local;
        }
    }

    public static Vector3f getWindSpeedAffectingEntity(Level level, BlockPos pos, double x, double y, double z, boolean isGloballyWindy) {
        Vector3f local = LevelSavedData.getLocalWindSpeedAffectingEntity(level, x, y, z);
        if (isGloballyWindy) {
            int i = level.getBiome(pos).is(ModTags.Biomes.IS_WINDY) ? 2 : 1;
            Vector2f vec = getGlobalWindSpeed(level);
            return local.add(vec.x * i, 0, vec.y * i);
        } else {
            return local;
        }
    }

    public static boolean isWindSensitive(Object o) {
        return o instanceof WindSensitive w && w.moreColorful$isWindSensitive();
    }

    public static boolean canApplyWind(Level level, double x, double y, double z) {
        if (isWindCalm(level)) return false;
        int px = Mth.floor(x), py = Mth.floor(y), pz = Mth.floor(z);
        if (!LevelSavedData.getFluidState(level, px, py, pz).isEmpty()) return false;
        int result = getWindResult(level, x, y, z, px, py, pz);
        return result == 1;
    }

    public static boolean canApplyWind(Level level, int x, double y, int z, BlockPos pos) {
        return canApplyWindI(level, x, y, z, pos) == 1;
    }

    public static boolean canApplyWind(Level level, BlockPos pos) {
        return canApplyWindI(level, pos) == 1;
    }

    public static int canApplyWindI(Level level, BlockPos pos) {
        if (isWindCalm(level)) return 0;
        if (!level.getFluidState(pos).isEmpty()) return 0;
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        return getWindResult(level, x, y, z, pos);
    }

    public static int canApplyWindI(Level level, int x, double y, int z, BlockPos pos) {
        if (isWindCalm(level)) return 0;
        if (level.isOutsideBuildHeight(pos.getY())) return 1;
        if (!level.getFluidState(pos).isEmpty()) return 0;
        return getWindResult(level, x, y, z, pos);
    }

    public static int canApplyWindI(Level level, int x, int y, int z) {
        if (isWindCalm(level)) return 0;
        if (!LevelSavedData.getFluidState(level, x, y, z).isEmpty()) return 0;
        return getWindResult(level, x + 0.5, y + 0.5, z + 0.5, x, y, z);
    }

    public static int canApplyWindI(Level level, int x, double y, int z, int px, int py, int pz) {
        if (isWindCalm(level)) return 0;
        if (level.isOutsideBuildHeight(py)) return 1;
        if (!LevelSavedData.getFluidState(level, px, py, pz).isEmpty()) return 0;
        return getWindResult(level, x, y, z, px, py, pz);
    }

    private static int getWindResult(Level level, double x, double y, double z, BlockPos pos) {
        int vent = LevelSavedData.getVentilation(level, pos) - 15;
        int a = 2 * vent - 4;
        Vector2f dir = getWindDirection(level);
        double toX = (dir.x * a) + x, toZ = (dir.y * a) + z;
        return canWindPassThrough(level, x, z, toX, toZ, y);
    }

    private static int getWindResult(Level level, double x, double y, double z, int px, int py, int pz) {
        int vent = LevelSavedData.getVentilation(level, px, py, pz) - 15;
        int a = 2 * vent - 4;
        Vector2f dir = getWindDirection(level);
        double toX = (dir.x * a) + x, toZ = (dir.y * a) + z;
        return canWindPassThrough(level, x, z, toX, toZ, y);
    }

    private static int canWindPassThrough(Level level, double fromX, double fromZ, double toX, double toZ, double y) {
        if (Maths.equals(fromX, fromZ, toX, toZ)) {
            return 1;
        } else {
            int airBlock = AirBlocking.FULL_BLOCK;
            double d0 = Mth.lerp(-1.0E-7, toX, fromX);
            double d2 = Mth.lerp(-1.0E-7, toZ, fromZ);
            double d3 = Mth.lerp(-1.0E-7, fromX, toX);
            double d5 = Mth.lerp(-1.0E-7, fromZ, toZ);
            int i = Mth.floor(d3);
            int j = Mth.floor(y);
            int k = Mth.floor(d5);
            airBlock -= AirBlocking.getAirBlock(level, i, j, k);
            if (airBlock <= 0) {
                return 0;
            } else {
                double d6 = d0 - d3;
                double d8 = d2 - d5;
                int l = Mth.sign(d6);
                int j1 = Mth.sign(d8);
                double d9 = l == 0 ? Double.MAX_VALUE : (double) l / d6;
                double d11 = j1 == 0 ? Double.MAX_VALUE : (double) j1 / d8;
                double d12 = d9 * (l > 0 ? 1.0 - Mth.frac(d3) : Mth.frac(d3));
                double d14 = d11 * (j1 > 0 ? 1.0 - Mth.frac(d5) : Mth.frac(d5));

                while (d12 <= 1.0 || d14 <= 1.0) {
                    if (d12 < d14) {
                        i += l;
                        d12 += d9;
                    } else {
                        k += j1;
                        d14 += d11;
                    }

                    airBlock -= AirBlocking.getAirBlock(level, i, j, k);
                    if (airBlock <= 0) {
                        return 0;
                    }
                }

                return 1;
            }
        }
    }

    public static int chanceByWind(Level level, BlockPos pos, int chance) {
        if (!Config.windSystem) return chance;
        Vector3f wind = getEffectiveWindSpeedAt(level, pos);
        if (wind != null) {
            float windSpeed = wind.length();
            return Math.max(1, chance - Mth.floor(windSpeed * chance * 0.04F));
        } else {
            return chance;
        }
    }

    public static double getRandomSpeedMultiplier(RandomSource random) {
        return Mth.nextDouble(random, 0.666667, 1.5);
    }
}
