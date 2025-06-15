package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public final class WeatherUtils {
    public static final Map<SectionPos, Map<BlockPos, Boolean>> WINDY_BLOCKS = new ConcurrentHashMap<>();

    public static boolean isWindy(Level level) {
        return !isWindless(level);
    }

    public static boolean isWindless(Level level) {
        if (Config.WIND_SYSTEM.isFalse()) return true;
        return Config.windlessDimensions.contains(level.dimension());
    }

    public static Vector3f getWindSpeed(Level level) {
        Vector2f vector2f = LevelSavedData.getGlobalWindSpeed(level);
        return new Vector3f(vector2f.x(), 0, vector2f.y());
    }

    public static boolean isWindSensitiveBlock(Block block) {
        return block instanceof WindSensitive windSensitive && windSensitive.isWindSensitive();
    }

    public static boolean canApplyWind(Level level, BlockPos pos) {
        if (LevelSavedData.getGlobalWindSpeed(level).equals(0, 0)) return false;
        if (!level.getFluidState(pos).isEmpty()) return false;
        SectionPos sectionPos = SectionPos.of(pos);
        return WINDY_BLOCKS.computeIfAbsent(sectionPos, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(pos, p -> {
                    CompletableFuture<BlockHitResult> future = getHitResult(level, Vec3.atCenterOf(p), p);
                    return future.thenApply(hitResult -> hitResult.getType() == HitResult.Type.MISS).join();
                });
    }

    public static CompletableFuture<Boolean> canApplyWind(Level level, Vec3 pos) {
        if (LevelSavedData.getGlobalWindSpeed(level).equals(0, 0)) return Constants.FALSE_FUTURE;
        BlockPos currentPos = BlockPos.containing(pos);
        if (!level.getFluidState(currentPos).isEmpty()) return Constants.FALSE_FUTURE;
        CompletableFuture<BlockHitResult> future = getHitResult(level, pos, currentPos);
        return future.thenApply(hitResult ->  hitResult.getType() == HitResult.Type.MISS);
    }

    private static CompletableFuture<BlockHitResult> getHitResult(Level level, Vec3 start, BlockPos currentPos) {
        Vector2f wind = LevelSavedData.getGlobalWindSpeed(level);
        int skyLight = level.getBrightness(LightLayer.SKY, currentPos) - level.getMaxLightLevel();
        Vec3 end = new Vec3(wind.x(), 0, wind.y()).normalize().scale(2 * skyLight - 4).add(start);
        return CompletableFuture.supplyAsync(() -> isBlockThatBlocksWindInLine(level, Pair.of(start, end)), ThreadUtils.WIND_EXECUTOR);
    }

    private static BlockHitResult isBlockThatBlocksWindInLine(Level level, Pair<Vec3, Vec3> pair) {
        return BlockGetter.traverseBlocks(
                pair.left(),
                pair.right(),
                pair,
                (p, pos) -> {
                    BlockState state = level.getBlockState(pos);
                    Vec3 vec3 = p.left().subtract(p.right());
                    Direction direction = Direction.getNearest(vec3.x, vec3.y, vec3.z);
                    return canBlockWind(level, pos, state, direction)
                            ? new BlockHitResult(p.right(), direction, BlockPos.containing(p.right()), false)
                            : null;
                },
                p -> {
                    Vec3 vec3 = p.left().subtract(p.right());
                    return BlockHitResult.miss(p.right(), Direction.getNearest(vec3.x, vec3.y, vec3.z), BlockPos.containing(p.right()));
                }
        );
    }

    public static boolean canBlockWind(Level level, BlockPos pos, BlockState state, Direction direction) {
        if (state.isAir() || Predicates.tagMatches(state, BlockTags.LEAVES, ModTags.Blocks.COPPER_GRATES)
                || Predicates.blockMatches(state, Blocks.SPAWNER, Blocks.MANGROVE_ROOTS)
        ) return false;
        if (Predicates.blockMatches(state, Blocks.TRIAL_SPAWNER, Blocks.VAULT))
            return direction.getAxis() == Direction.Axis.Y;
        if (state.is(Tags.Blocks.GLASS_PANES))
            return direction.getAxis() != Direction.Axis.Y;
        if (state.is(Tags.Blocks.GLASS_BLOCKS)
                || Predicates.blockMatches(state, Blocks.COMPOSTER, Blocks.HONEY_BLOCK)
                || !state.getFluidState().isEmpty()
        ) return true;
        if (state.isSuffocating(level, pos)) return true;
        return Block.isFaceFull(state.getCollisionShape(level, pos), direction)
                || isMergedFaceFull(level, pos, state, direction);
    }

    private static boolean isMergedFaceFull(Level level, BlockPos pos, BlockState state, Direction direction) {
        BlockPos pos1 = pos.relative(direction);
        BlockState state1 = level.getBlockState(pos1);
        VoxelShape shape = LightEngine.getOcclusionShape(level, pos, state, direction);
        VoxelShape shape1 = LightEngine.getOcclusionShape(level, pos1, state1, direction.getOpposite());
        return Shapes.faceShapeOccludes(shape, shape1);
    }

    public static int chanceByWind(Level level, int chance) {
        if (Config.WIND_EFFECT_CLIENT.isFalse()) return chance;
        float windSpeed = getWindSpeed(level).length();
        return Math.max(0, chance - Mth.floor(windSpeed * chance * 0.04F));
    }

    public static double getRandomSpeedMultiplier(RandomSource random) {
        return Mth.nextDouble(random, 0.666667, 1.5);
    }
}
