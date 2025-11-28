package com.ChalkerCharles.morecolorful.util.client;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyDataTask;
import com.ChalkerCharles.morecolorful.client.renderer.wavy.WavyVertices;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.util.AirBlocking;
import com.ChalkerCharles.morecolorful.util.Corner;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public final class RenderUtils {
    public static final Vector2f WIND_SPEED = new Vector2f(), WIND_DIR = new Vector2f();
    public static float time, anim;
    public static boolean isCalm;
    public static Direction nearestWindDirection;
    public static boolean isClientWindOn;
    public static final boolean SODIUM_ON = ModList.get().isLoaded("sodium");
    private static final ThreadLocal<VertexCache> VERTEX_CACHE = ThreadLocal.withInitial(VertexCache::new);
    private static final ThreadLocal<FluidVertexCache> FLUID_CACHE = ThreadLocal.withInitial(FluidVertexCache::new);
    public static final ThreadLocal<WavyDataTask<?>> WAVY_TASK = new ThreadLocal<>();

    public static void setCacheOrigin(SectionPos sectionPos) {
        FLUID_CACHE.get().setOrigin(sectionPos);
    }

    public static void initCache(BlockPos pos, BlockState state) {
        VERTEX_CACHE.get().init(pos, state);
    }

    public static void initFluidCache(BlockPos pos) {
        FLUID_CACHE.get().init(pos);
    }

    public static void clearCache() {
        VERTEX_CACHE.get().clear();
        FLUID_CACHE.get().clear();
    }

    public static void putWavyTask(WavyDataTask<?> task) {
        task.clear();
        WAVY_TASK.set(task);
    }

    @Nullable
    public static WavyVertices.Default getWavyVertices(RenderType renderType) {
        return ((WavyDataTask.Default) WAVY_TASK.get()).getVertices(renderType);
    }

    public static void clearWavyTask() {
        WAVY_TASK.get().encapsulate();
        WAVY_TASK.remove();
    }

    public static long getWaveData(float x, float y, float z) {
        return VERTEX_CACHE.get().getData(x, y, z);
    }

    private static int vineIndex(float x, float y, float z) {
        Direction direction = WavyBlockUtils.getFaceByVertex(x, z);
        return switch (direction) {
            case NORTH -> y < 0.9F ? 1 : 2;
            case SOUTH -> y < 0.9F ? 3 : 4;
            case WEST -> y < 0.9F ? 5 : 6;
            case EAST -> y < 0.9F ? 7 : 8;
            default -> 0;
        };
    }

    public static long getFluidWaveData(float x, float y, float z) {
        if (y % 1 < 0.125F) {
            return 0L;
        } else {
            return FLUID_CACHE.get().getData(x, y, z);
        }
    }

    public static boolean setClientWindOn() {
        return isClientWindOn = Config.WIND_SYSTEM.isTrue() && Config.WIND_EFFECT_CLIENT.isTrue();
    }

    public static void setWindContext(Level level) {
        time = RenderSystem.getShaderGameTime();
        anim = time * 800;
        if (level == null) return;
        WIND_SPEED.set(LevelSavedData.getGlobalWindSpeed(level));
        WIND_DIR.set(LevelSavedData.getWindDirection(level));
        isCalm = LevelSavedData.isWindCalm(level);
        nearestWindDirection = LevelSavedData.getNearestWindDirection(level);
    }

    @Nullable
    public static WindSectionMap getSectionMap(int x, int y, int z) {
        int sectionX = x >> 4, sectionY = y >> 4, sectionZ = z >> 4;
        return ILevelRendererExtension.getWindSectionMap(sectionX, sectionY, sectionZ);
    }

    public static WindSectionMap getSectionWindMap() {
        return WAVY_TASK.get().windMap;
    }

    public static void putWindSectionData(Level level, BlockPos actualPos, BlockPos checkPos) {
        int index = SectionPos.sectionRelativePos(actualPos);
        int windy = WeatherUtils.canApplyWindI(level, checkPos);
        getSectionWindMap().put(index, windy);
    }

    public static void putWindSectionData(Level level, BlockPos pos) {
        putWindSectionData(level, pos, pos);
    }

    public static boolean isGloballyWindyAt(Level level, BlockPos pos) {
        if (level.isOutsideBuildHeight(pos)) return true;
        int index = SectionPos.sectionRelativePos(pos);
        WindSectionMap map = ILevelRendererExtension.getWindSectionMap(pos);
        if (map == null) return false;
        int windy = map.get(index, () -> WeatherUtils.canApplyWindI(level, pos));
        return windy == 1;
    }

    public static boolean isWindyAt(Level level, BlockPos pos) {
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        return isGloballyWindyAt(level, pos) || LevelSavedData.isInWindZone(level, x, y, z);
    }

    public static Vector3f getWindSpeedAt(Level level, BlockPos pos) {
        long sectionPos = SectionPos.asLong(pos);
        double x = pos.getX() + 0.5, y = pos.getY() + 0.5, z = pos.getZ() + 0.5;
        Vector3f vec = LevelSavedData.getLocalWindSpeedAt(level, x, y, z, sectionPos);
        return isGloballyWindyAt(level, pos) ? vec.add(WIND_SPEED.x, 0, WIND_SPEED.y) : vec;
    }

    public static boolean isWavyRenderType(RenderType type) {
        return type == RenderType.cutoutMipped() || type == RenderType.cutout() || type == RenderType.translucent();
    }

    public static void clearDataInLine(Level level, BlockPos pos) {
        int i = pos.getX(), j = pos.getY(), k = pos.getZ();
        double d6 = WIND_DIR.x * 34, d8 = WIND_DIR.y * 34;
        int l = Mth.sign(d6), j1 = Mth.sign(d8);
        double d9 = l == 0 ? Double.MAX_VALUE : (double) l / d6;
        double d11 = j1 == 0 ? Double.MAX_VALUE : (double) j1 / d8;
        double d12 = d9 * 0.5, d14 = d11 * 0.5;
        while (d12 <= 1.0 || d14 <= 1.0) {
            if (d12 < d14) {
                i += l;
                d12 += d9;
            } else {
                k += j1;
                d14 += d11;
            }
            int airBlock = AirBlocking.getAirBlock(level, i, j, k);
            if (airBlock == AirBlocking.FULL_BLOCK) return;
            int index = Maths.sectionRelativePos(i, j, k);
            WindSectionMap map = getSectionMap(i, j, k);
            if (map != null) map.put(index, 2);
        }
    }

    public static int getClientLeafTintColor(Level level, BlockPos pos) {
        return Minecraft.getInstance().getBlockColors().getColor(level.getBlockState(pos), level, pos, 0);
    }

    private static class VertexCache {
        private BlockPos pos;
        private BlockState state;
        private int type;
        private final long[] cache = new long[9];

        private void clear() {
            for (int i = 0; i < 9; i++)
                cache[i] = 0L;
        }

        private long getData(float x, float y, float z) {
            boolean skip = switch (type) {
                case 0 -> true;
                case 1 -> y < 0.005F;
                case 8 -> y < 0.35;
                case 9 -> WavyBlockUtils.BAMBOO.includes(x, z);
                case 10 -> y < 0.525;
                case 11 -> WavyBlockUtils.FLOWER_POT.includes(x, z) || y < 0.255;
                case 12 -> y < 0.4F && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER;
                default -> false;
            };
            if (skip) {
                return 0L;
            }
            return switch (type) {
                case 1, 3, 5, 8, 9, 10, 11 -> cache[0];
                case 2 -> cache[vineIndex(x, y, z)];
                case 4 -> y > 0.9F ? cache[0] : cache[1];
                case 6 -> y > 0.94F ? cache[0] : cache[1];
                default -> y < 0.005F ? cache[0] : cache[1];
            };
        }

        private void init(BlockPos pos, BlockState state) {
            this.pos = pos;
            this.state = state;
            this.type = IBlockStateExtension.getVertexType(state);
            if (type != 0) {
                this.precomputeData();
            }
        }

        private void precomputeData() {
            switch (type) {
                case 1, 3, 7, 8, 9, 10, 11 -> putWindSectionData(Minecraft.getInstance().level, pos);
            }
            switch (type) {
                case 1, 3, 5, 8, 9, 10, 11 -> cache[0] = compute(0, 1, 0);
                case 2 -> {
                    if (state.getValue(VineBlock.UP)) {
                        cache[0] = compute(0, 0.95F, 0);
                    }
                    if (state.getValue(VineBlock.NORTH)) {
                        cache[1] = compute(0, 0, 0.05F);
                        cache[2] = compute(0, 1, 0.05F);
                    }
                    if (state.getValue(VineBlock.SOUTH)) {
                        cache[3] = compute(0, 0, 0.95F);
                        cache[4] = compute(0, 1, 0.95F);
                    }
                    if (state.getValue(VineBlock.WEST)) {
                        cache[5] = compute(0.05F, 0, 0);
                        cache[6] = compute(0.05F, 1, 0);
                    }
                    if (state.getValue(VineBlock.EAST)) {
                        cache[7] = compute(0.95F, 0, 0);
                        cache[8] = compute(0.95F, 1, 0);
                    }
                }
                case 4, 6 -> {
                    cache[0] = compute(0, 1, 0);
                    cache[1] = compute(0, 0, 0);
                }
                default -> {
                    cache[0] = compute(0, 0, 0);
                    cache[1] = compute(0, 1, 0);
                }
            }
        }

        private long compute(float x, float y, float z) {
            return WavyBlockUtils.getWaveDataByVertex(Minecraft.getInstance().level, state, pos, x, y, z, type);
        }
    }

    private static class FluidVertexCache {
        private static final long[] TEMPLATE_ARRAY = new long[4624];
        private final Vector3i origin = new Vector3i();
        private BlockPos pos;
        private float centerX;
        private float centerZ;
        private final long[] cache = TEMPLATE_ARRAY.clone();

        private void clear() {
            Arrays.fill(this.cache, Long.MAX_VALUE);
        }

        private long getData(float x, float y, float z) {
            int x1 = Math.round(x), y0 = Mth.floor(y), y1 = Math.round(y), z1 = Math.round(z);
            int key = 272 * x1 + (z1 << 4) + y0;
            long l = this.cache[key];
            if (l == Long.MAX_VALUE) {
                l = this.compute(x1, y1, z1, key + 4096);
                this.cache[key] = l;
            }
            return l;
        }

        private long compute(int x, int y, int z, int index) {
            Level level = Minecraft.getInstance().level;
            BlockPos pos = this.pos;
            int x1 = origin.x + x, y1 = origin.y + y, z1 = origin.z + z;
            int windy = WeatherUtils.canApplyWindI(level, x1, y1 + 0.5, z1, pos.getX(), pos.getY() + 1, pos.getZ());
            getSectionWindMap().put(index, windy);
            Corner corner = Corner.getCorner(x - centerX, z - centerZ);
            return WavyBlockUtils.getWaveDataByFluidVertex(level, pos, x1, y1, z1, corner);
        }

        private void init(BlockPos pos) {
            this.pos = pos;
            centerX = (pos.getX() & 15) + 0.5F;
            centerZ = (pos.getZ() & 15) + 0.5F;
        }

        private void setOrigin(SectionPos pos) {
            this.origin.set(pos.x() << 4, pos.y() << 4, pos.z() << 4);
        }

        static {
            Arrays.fill(TEMPLATE_ARRAY, Long.MAX_VALUE);
        }
    }
}
