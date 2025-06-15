package com.ChalkerCharles.morecolorful.util;

import com.ChalkerCharles.morecolorful.common.block.properties.HangingBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.MultipartBlock;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IGrowingPlantBlockMixin;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.booleans.BooleanIntPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector2i;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public final class WavyBlockUtils {
    private static final Range FLOWER_POT = Range.of(0.3F, 0.7F);
    private static final Range BAMBOO = Range.of(0.375F, 0.625F);
    private static final Range VINE_NORTHWEST = Range.of(0.04F, 0.5F);
    private static final Range VINE_SOUTHEAST = Range.of(0.5F, 0.96F);
    private static final Pair<VertexState, Vector2i> FIXED = Pair.of(VertexState.FIXED, new Vector2i());
    private static final Pair<VertexState, Vector2i> DEPEND0 = Pair.of(VertexState.DEPEND, new Vector2i());
    private static final Pair<VertexState, Vector2i> DEPEND1 = Pair.of(VertexState.DEPEND, new Vector2i(0, 1));
    private static final CompletableFuture<BooleanIntPair> FIXED_LIQUID = CompletableFuture.completedFuture(BooleanIntPair.of(false, 3));

    public static Vector4f getWindSpeedByVertex(Level level, BlockState state, BlockPos pos, float x, float y, float z, int type) {
        if (level == null || !WeatherUtils.isWindSensitiveBlock(state.getBlock())) return Constants.ZERO_VEC4;
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        Pair<VertexState, Vector2i> result = canVertexApplyWind(level, state, pos, x, y, z);
        return switch (result.left()) {
            case WAVING -> packData(wind, result.right(), type, x, y, z);
            case FIXED -> Constants.ZERO_VEC4;
            case DEPEND -> WeatherUtils.canApplyWind(level, pos)
                    ? packData(wind, result.right(), type, x, y, z)
                    : Constants.ZERO_VEC4;
        };
    }

    private static Pair<VertexState, Vector2i> canVertexApplyWind(Level level, BlockState state, BlockPos pos, float x, float y, float z) {
        if (state.is(Blocks.PITCHER_CROP) && state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && y < 0.4F)
            return FIXED;
        Block block = state.getBlock();
        if (block instanceof LeavesBlock || block instanceof WebBlock) {
            BlockPos pos1 = pos.below();
            return y < 0.5 && level.getBlockState(pos1).isFaceSturdy(level, pos1, Direction.UP)
                    ? FIXED : DEPEND0;
        }
        return switch (block) {
            case AzaleaBlock ignored -> y > 0.35 ? DEPEND1 : FIXED;
            case ChainBlock ignored -> handleChain(level, state, pos, y);
            case LanternBlock ignored -> handleLantern(level, state, pos);
            case FlowerPotBlock potBlock -> handleFlowerPot(potBlock, x, y, z);
            case BambooStalkBlock ignored -> BAMBOO.includes(x, z)
                    ? FIXED : DEPEND0;
            case VineBlock vineBlock -> handleVines(level, vineBlock, state, pos, x, y, z);
            case HangingBlock ignored -> handleHanging(level, block, pos, y);
            default -> handle(level, block, pos, y);
        };
    }

    private static Pair<VertexState, Vector2i> handleFlowerPot(FlowerPotBlock potBlock, float x, float y, float z) {
        Block block = potBlock.getPotted();
        if (block instanceof AzaleaBlock) {
            return y > 0.525 ? DEPEND1 : FIXED;
        }
        return block instanceof WindSensitive && !FLOWER_POT.includes(x, z) && y > 0.255
                ? DEPEND1 : FIXED;
    }

    private static Pair<VertexState, Vector2i> handleVines(Level level, VineBlock block, BlockState state, BlockPos pos, float x, float y, float z) {
        Direction direction = getFaceByVertex(x, z);
        int height0 = getVisualVineRoot(level, block, pos, direction);
        int height = getActualVineRoot(level, block, pos, direction, height0);
        int tip = getVineTip(level, block, pos, direction);
        int i = y < 0.9F ? height + 1 : height;
        int j = y < 0.9F ? tip - 1 : tip;
        if (isFaceAttachedToLeaves(level, state, pos, direction))
            return Pair.of(VertexState.DEPEND, new Vector2i(j, i));
        if (isFaceAttached(level, block, state, pos, direction) || height < 0)
            return FIXED;
        if (height == 0) {
            return y > 0.9F ? FIXED : Pair.of(VertexState.DEPEND, new Vector2i(j, 1));
        }
        if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos.above(height)))
            return FIXED;
        return Pair.of(VertexState.WAVING, new Vector2i(j, i));
    }

    private static int getActualVineRoot(Level level, VineBlock block, BlockPos pos, Direction direction, int height) {
        BlockPos.MutableBlockPos mutablePos = pos.above(height).mutable();
        while (!isActualVineRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            height--;
            mutablePos.move(Direction.DOWN);
        }
        return height;
    }

    private static boolean isActualVineRoot(Level level, VineBlock block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block)) return true;
        BlockPos pos1 = pos.above();
        return WeatherUtils.canApplyWind(level, pos)
                && !isFaceAttached(level, block, state, pos, direction)
                && (!WeatherUtils.canApplyWind(level, pos1) || isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction));
    }

    private static int getVisualVineRoot(Level level, VineBlock block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualVineRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        return i;
    }

    private static boolean isVisualVineRoot(Level level, VineBlock block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block) || isFaceAttached(level, block, state, pos, Direction.UP)) return true;
        BlockPos pos1 = pos.above();
        return !isFaceAttached(level, block, state, pos, direction)
                && isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction);
    }

    private static int getVineTip(Level level, VineBlock block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVineTip(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(Direction.DOWN);
        }
        return i;
    }

    private static boolean isVineTip(Level level, VineBlock block, BlockState state, BlockPos pos, Direction direction) {
        BlockPos pos1 = pos.below();
        if (!state.is(block) || !level.getBlockState(pos1).is(block)) return true;
        return !isFaceAttached(level, block, state, pos, direction)
                && isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction);
    }

    private static boolean isFaceAttached(Level level, VineBlock block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block)) return false;
        BooleanProperty facing = VineBlock.getPropertyForFace(direction);
        BlockPos pos1 = pos.relative(direction);
        return state.getValue(facing) && VineBlock.isAcceptableNeighbour(level, pos1, direction);
    }

    private static boolean isFaceAttachedToLeaves(Level level, BlockState state, BlockPos pos, Direction direction) {
        BooleanProperty facing = VineBlock.getPropertyForFace(direction);
        BlockPos pos1 = pos.relative(direction);
        return state.getValue(facing)
                && (level.getBlockState(pos1).getBlock() instanceof LeavesBlock || level.getBlockState(pos1.above()).getBlock() instanceof LeavesBlock);
    }

    private static Direction getFaceByVertex(float x, float z) {
        if (VINE_NORTHWEST.includes(z)) return Direction.NORTH;
        if (VINE_SOUTHEAST.includes(z)) return Direction.SOUTH;
        if (VINE_NORTHWEST.includes(x)) return Direction.WEST;
        if (VINE_SOUTHEAST.includes(x)) return Direction.EAST;
        return Direction.UP;
    }

    private static Pair<VertexState, Vector2i> handleHanging(Level level, Block block, BlockPos pos, float y) {
        if (!(block instanceof MultipartBlock)) return y > 0.94F ? FIXED : DEPEND1;
        int height0 = getVisualRoot(level, block, pos, Direction.UP);
        int height = getActualRoot(level, block, pos, Direction.UP, height0);
        if (height < 0)
            return FIXED;
        int tip = getVisualRoot(level, block, pos, Direction.DOWN);
        int i = y < 0.94F ? height + 1 : height;
        int j = y < 0.94F ? tip - 1 : tip;
        if (height == 0) {
            if (level.getBlockState(pos.above()).getBlock() instanceof LeavesBlock)
                return Pair.of(VertexState.DEPEND, new Vector2i(j, i));
            return y > 0.94F ? FIXED : Pair.of(VertexState.DEPEND, new Vector2i(j, 1));
        }
        if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos.above(height)))
            return FIXED;
        return Pair.of(VertexState.WAVING, new Vector2i(j, i));
    }

    private static Pair<VertexState, Vector2i> handle(Level level, Block block, BlockPos pos, float y) {
        if (!(block instanceof MultipartBlock)) return y < 0.005F ? FIXED : DEPEND1;
        int height0 = getVisualRoot(level, block, pos, Direction.DOWN);
        int height = getActualRoot(level, block, pos, Direction.DOWN, height0);
        if (height < 0)
            return FIXED;
        int tip = getVisualRoot(level, block, pos, Direction.UP);
        int i = y > 0.005F ? height + 1 : height;
        int j = y > 0.005F ? tip - 1 : tip;
        if (height == 0) {
            return y < 0.005F ? FIXED : Pair.of(VertexState.DEPEND, new Vector2i(j, 1));
        }
        if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos.below(height)))
            return FIXED;
        return Pair.of(VertexState.WAVING, new Vector2i(j, i));
    }

    private static int getActualRoot(Level level, Block block, BlockPos pos, Direction direction, int height) {
        BlockPos.MutableBlockPos mutablePos = pos.relative(direction, height).mutable();
        while (!isActualRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            height--;
            mutablePos.move(direction.getOpposite());
        }
        return height;
    }

    private static boolean isActualRoot(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!isSameBlock(state, block)) return true;
        BlockPos pos1 = pos.relative(direction);
        return WeatherUtils.canApplyWind(level, pos) && isSameBlock(state, block)
                && !(WeatherUtils.canApplyWind(level, pos1) && isSameBlock(level.getBlockState(pos1), block));
    }

    private static int getVisualRoot(Level level, Block block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(direction);
        }
        return i;
    }

    private static boolean isVisualRoot(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!isSameBlock(state, block)) return true;
        BlockPos pos1 = pos.relative(direction);
        return isSameBlock(state, block) && !isSameBlock(level.getBlockState(pos1), block);
    }

    private static boolean isSameBlock(BlockState state, Block block) {
        if (block instanceof GrowingPlantBlock growingPlant) {
            return Predicates.blockMatches(state,
                    ((IGrowingPlantBlockMixin) growingPlant).invokeGetBodyBlock(),
                    ((IGrowingPlantBlockMixin) growingPlant).invokeGetHeadBlock()
            );
        } else if (block instanceof BigDripleafBlock || block instanceof BigDripleafStemBlock) {
            Block block1 = state.getBlock();
            return block1 instanceof BigDripleafBlock || block1 instanceof BigDripleafStemBlock;
        }
        return state.is(block);
    }

    private static Pair<VertexState, Vector2i> handleChain(Level level, BlockState state, BlockPos pos, float y) {
        if (state.getValue(BlockStateProperties.AXIS).isHorizontal()) return FIXED;
        BooleanIntPair isFixedOnGround = isChainFixedOnGround(level, pos);
        if (isFixedOnGround.leftBoolean()) return FIXED;
        int height0 = getVisualChainRoot(level, pos);
        int height = getActualChainRoot(level, pos, height0);
        if (height < 0)
            return FIXED;
        int tip = isFixedOnGround.rightInt();
        if (height == 0) {
            return y > 0.9F ? FIXED : Pair.of(VertexState.DEPEND, new Vector2i(tip, 0));
        }
        if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos.above(height)))
            return FIXED;
        return Pair.of(VertexState.WAVING, new Vector2i(tip, height));
    }

    private static int getActualChainRoot(Level level, BlockPos pos, int height) {
        BlockPos.MutableBlockPos mutablePos = pos.above(height).mutable();
        while (!isActualChainRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            height--;
            mutablePos.move(Direction.DOWN);
        }
        return height;
    }

    private static boolean isActualChainRoot(Level level, BlockState state, BlockPos pos) {
        if (!isChainVertical(state)) return true;
        BlockPos pos1 = pos.above();
        return WeatherUtils.canApplyWind(level, pos)
                && !(WeatherUtils.canApplyWind(level, pos1) && isChainVertical(level.getBlockState(pos1)));
    }

    private static int getVisualChainRoot(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualChainRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        return i;
    }

    private static boolean isVisualChainRoot(Level level, BlockState state, BlockPos pos) {
        if (!isChainVertical(state)) return true;
        return !isChainVertical(level.getBlockState(pos.above()));
    }

    private static boolean isChainVertical(BlockState state) {
        return state.getBlock() instanceof ChainBlock && state.getValue(BlockStateProperties.AXIS).isVertical();
    }

    private static BooleanIntPair isChainFixedOnGround(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        BlockState state, state1;
        for (int i = 0; ; i++) {
            mutablePos.move(Direction.DOWN);
            state = level.getBlockState(mutablePos);
            if (isHangingSignOrChain(state) && !onWall(state)) continue;
            if (onWall(state))
                return BooleanIntPair.of(false, i);
            if (state.getBlock() instanceof LanternBlock) {
                if (state.getValue(LanternBlock.HANGING))
                    return BooleanIntPair.of(false, i + 1);
                else return BooleanIntPair.of(false, i);
            }
            state1 = level.getBlockState(mutablePos.above());
            if (isHangingSign(state1))
                return BooleanIntPair.of(false, i);
            return BooleanIntPair.of(state.isFaceSturdy(level, mutablePos, Direction.UP, SupportType.CENTER), i);
        }
    }

    private static Pair<VertexState, Vector2i> handleLantern(Level level, BlockState state, BlockPos pos) {
        BlockPos pos1 = pos.above();
        BlockState state1 = level.getBlockState(pos1);
        if (isChainVertical(state1)) {
            int height0 = getVisualChainRoot(level, pos1);
            int height = getActualChainRoot(level, pos1, height0);
            if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos1.above(height)))
                return FIXED;
            return Pair.of(VertexState.WAVING, new Vector2i(0, height + 1));
        } else {
            return state.getValue(LanternBlock.HANGING) ? DEPEND0 : FIXED;
        }
    }

    private enum VertexState {
        WAVING,
        FIXED,
        DEPEND
    }

    public static CompletableFuture<Vector4f> getWindSpeedByLiquidVertex(Level level, BlockPos pos, float x, float y, float z) {
        if (level == null) return Constants.ZERO_VEC4_FUTURE;
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        return canLiquidVertexApplyWind(level, pos, (pos.getX() & -16) + x, (pos.getY() & -16) + y, (pos.getZ() & -16) + z)
                .thenApply(result -> y % 1 > 0.125F && result.leftBoolean()
                        ? packData(wind, result.rightInt())
                        : Constants.ZERO_VEC4);
    }

    private static CompletableFuture<BooleanIntPair> canLiquidVertexApplyWind(Level level, BlockPos pos, float x, float y, float z) {
        BlockPos pos1 = pos.above();
        BlockState state = level.getBlockState(pos1);
        if (WeatherUtils.canBlockWind(level, pos1, state, Direction.DOWN))
            return FIXED_LIQUID;
        int edge = isEdge(level, pos, x, z) ? 2 : 1;
        return isFluidVertexWaving(level, pos, x, y, z).thenApply(b -> BooleanIntPair.of(b, edge));
    }

    private static CompletableFuture<Boolean> isFluidVertexWaving(Level level, BlockPos pos, float x, float y, float z) {
        Corner corner = getCorner(pos, x, z);
        Direction d1 = corner.first, d2 = corner.second;
        BlockPos pos1 = pos.relative(d1).above(), pos2 = pos.relative(d2).above(), pos3 = pos1.relative(d2);
        if (WeatherUtils.canBlockWind(level, pos1, level.getBlockState(pos1), Direction.DOWN)
                || WeatherUtils.canBlockWind(level, pos2, level.getBlockState(pos2), Direction.DOWN)
                || WeatherUtils.canBlockWind(level, pos3, level.getBlockState(pos3), Direction.DOWN))
            return Constants.FALSE_FUTURE;
        return WeatherUtils.canApplyWind(level, new Vec3(x, y + 1, z));
    }

    private static boolean isEdge(Level level, BlockPos pos, float x, float z) {
        Corner corner = getCorner(pos, x, z);
        Direction d1 = corner.first, d2 = corner.second;
        BlockPos pos1 = pos.relative(d1), pos2 = pos.relative(d2), pos3 = pos1.relative(d2);
        return isValidEdgeBlock(level, pos1, d1) || isValidEdgeBlock(level, pos2, d2)
                || isValidEdgeBlock(level, pos3, d1) || isValidEdgeBlock(level, pos3, d2);
    }

    private static Corner getCorner(BlockPos pos, float x, float z) {
        float centerX = pos.getX() + 0.5F, centerZ = pos.getZ() + 0.5F;
        return getCorner(x - centerX, z - centerZ);
    }

    private static Corner getCorner(double x, double z) {
        if (x > 0) {
            return z > 0 ? Corner.SOUTHEAST : Corner.NORTHEAST;
        } else {
            return z > 0 ? Corner.SOUTHWEST : Corner.NORTHWEST;
        }
    }

    private static boolean isValidEdgeBlock(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof LeavesBlock) return false;
        return MultifaceBlock.canAttachTo(level, direction, pos, state);
    }

    private enum Corner {
        SOUTHEAST(Direction.SOUTH, Direction.EAST),
        NORTHEAST(Direction.EAST, Direction.NORTH),
        NORTHWEST(Direction.NORTH, Direction.WEST),
        SOUTHWEST(Direction.WEST, Direction.SOUTH);

        private final Direction first;
        private final Direction second;
        Corner(Direction first, Direction second) {
            this.first = first;
            this.second = second;
        }
    }

    public static float getTick(Level level, float partialTick) {
        return level.getGameTime() % 24000L + partialTick;
    }

    public static float speedMultiplier(Vector3f wind) {
        return Mth.floor(wind.length() + 0.5F) / 16.0F;
    }

    public static Vector4f getSignAngle(Level level, float partialTick, BlockState state, BlockPos pos) {
        if (level == null) return Constants.ZERO_VEC4;
        Pair<VertexState, Vector2i> result = canHangingSignApplyWind(level, pos);
        return switch (result.left()) {
            case WAVING -> yieldAngle(level, partialTick, state, pos, result.right());
            case FIXED -> Constants.ZERO_VEC4;
            case DEPEND -> WeatherUtils.canApplyWind(level, pos)
                    ? yieldAngle(level, partialTick, state, pos, result.right())
                    : Constants.ZERO_VEC4;
        };
    }

    private static Vector4f yieldAngle(Level level, float partialTick, BlockState state, BlockPos pos, Vector2i vector) {
        int tip = vector.x, height = vector.y;
        float ratio = heightRatio(tip, height);
        float angle = getAngle(level, partialTick, state);
        Vector3f accumulated = accumulateSignOffset(level, partialTick, state, pos, vector);
        return new Vector4f(accumulated, angleMultiplier(angle, ratio));
    }

    private static float getAngle(Level level, float partialTick, BlockState state) {
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        float f = Mth.sin(getTick(level, partialTick) * 0.8F * speedMultiplier(wind)) / 2 + 0.5F;
        float angle = wind.dot(getSignFacing(state)) / 24.0F;
        return -Mth.rotLerp(f, angle * 0.75F, angle * 1.25F);
    }

    private static float getChainAngle(Level level, float partialTick) {
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        float f = Mth.sin(getTick(level, partialTick) * 0.8F * speedMultiplier(wind)) / 2 + 0.5F;
        float angle = (float) (Math.hypot(wind.x, wind.z) / 24.0F);
        return Mth.rotLerp(f, angle * 0.75F, angle * 1.25F);
    }

    private static float angleMultiplier(float angle, float ratio) {
        return ratio * ratio * angle;
    }

    public static float getSignAngleVertical(Level level, float partialTick, BlockState state) {
        Vector3f wind = WeatherUtils.getWindSpeed(level);
        float f = Mth.sin(getTick(level, partialTick) * speedMultiplier(wind)) / 2 + 0.5F;
        float angle = wind.dot(getSignFacing(state)) / 64.0F;
        return Mth.rotLerp(f, -angle, angle);
    }

    private static Vector3f getTranslation(BlockState stateAbove, Vector3f facing, float angle) {
        Vector3f facingAbove = getSignFacing(stateAbove);
        float i = facing.dot(facingAbove);
        float j = facing.dot(rotateBy90(facingAbove));
        return new Vector3f(-Mth.sin(angle) * j, 1.0F - Mth.cos(angle), -Mth.sin(angle) * i);
    }

    private static Vector3f getChainTranslation(Level level, Vector3f facing, float angle) {
        Vector3f normalized = WeatherUtils.getWindSpeed(level).normalize();
        float i = facing.dot(normalized) * 0.8F;
        float j = facing.dot(rotateBy90(normalized)) * 0.8F;
        return new Vector3f(Mth.sin(angle) * j, 1.0F - Mth.cos(angle), Mth.sin(angle) * i);
    }

    private static Vector3f rotateBy90(Vector3f vector) {
        return vector.set(vector.z, vector.y, -vector.x);
    }

    private static Vector3f getSignFacing(BlockState state) {
        if (onWall(state)) {
            Direction direction = state.getValue(WallHangingSignBlock.FACING);
            return direction.step();
        } else {
            float degree = RotationSegment.convertToDegrees(state.getValue(CeilingHangingSignBlock.ROTATION));
            return new Vector3f(-Mth.sin(degree * Mth.DEG_TO_RAD), 0.0F, Mth.cos(degree * Mth.DEG_TO_RAD));
        }
    }

    private static Pair<VertexState, Vector2i> canHangingSignApplyWind(Level level, BlockPos pos) {
        int height0 = getVisualSignRoot(level, pos);
        int height = getActualSignRoot(level, pos, height0);
        if (height < 0)
            return FIXED;
        int tip = getSignTip(level, pos);
        Vector2i vec = new Vector2i(tip, height);
        if (height == 0)
            return Pair.of(VertexState.DEPEND, vec);
        if (!WeatherUtils.canApplyWind(level, pos) && !WeatherUtils.canApplyWind(level, pos.above(height)))
            return FIXED;
        return Pair.of(VertexState.WAVING, vec);
    }

    private static int getActualSignRoot(Level level, BlockPos pos, int height) {
        BlockPos.MutableBlockPos mutablePos = pos.above(height).mutable();
        while (!isActualSignRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            height--;
            mutablePos.move(Direction.DOWN);
        }
        return height;
    }

    private static boolean isActualSignRoot(Level level, BlockState state, BlockPos pos) {
        if (onWall(state) || !isHangingSignOrChain(state)) return true;
        BlockPos pos1 = pos.above();
        BlockState state1 = level.getBlockState(pos1);
        return WeatherUtils.canApplyWind(level, pos)
                && !(WeatherUtils.canApplyWind(level, pos1) && isHangingSignOrChain(state1));
    }

    private static int getVisualSignRoot(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualSignRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        return i;
    }

    private static boolean isVisualSignRoot(Level level, BlockState state, BlockPos pos) {
        if (onWall(state) || !isHangingSignOrChain(state)) return true;
        BlockPos pos1 = pos.above();
        BlockState state1 = level.getBlockState(pos1);
        return !isHangingSignOrChain(state1);
    }

    private static int getSignTip(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isSignTip(level, level.getBlockState(mutablePos), mutablePos)) {
            i++;
            mutablePos.move(Direction.DOWN);
        }
        return i;
    }

    private static boolean isSignTip(Level level, BlockState state, BlockPos pos) {
        if (!isHangingSign(state)) return true;
        BlockPos pos1 = pos.below();
        BlockState state1 = level.getBlockState(pos1);
        return onWall(state1) || !isHangingSign(state1);
    }

    private static boolean isHangingSign(BlockState state) {
        return state.getBlock() instanceof CeilingHangingSignBlock || state.getBlock() instanceof WallHangingSignBlock;
    }

    private static boolean isHangingSignOrChain(BlockState state) {
        return isHangingSign(state) || isChainVertical(state);
    }

    public static boolean onWall(BlockState state) {
        return state.getBlock() instanceof WallHangingSignBlock;
    }

    private static Vector3f accumulateSignOffset(Level level, float partialTick, BlockState state, BlockPos pos, Vector2i tuple) {
        int tip = tuple.x, height = tuple.y, sum = tip + height + 1;
        BlockPos.MutableBlockPos mutablePos = pos.above(height).mutable();
        BlockState stateAbove;
        float ratio, angle;
        Vector3f facing = getSignFacing(state);
        Vector3f vec = new Vector3f();
        for (int i = 0; i < height; i++) {
            stateAbove = level.getBlockState(mutablePos);
            mutablePos.move(Direction.DOWN);
            if (!isHangingSignOrChain(stateAbove)) continue;
            if (isChainVertical(stateAbove)) {
                ratio = (float) (i + 1) / (sum);
                angle = getChainAngle(level, partialTick);
                vec.add(getChainTranslation(level, facing, angleMultiplier(angle, ratio)));
            } else {
                ratio = (float) (i + 1) / sum;
                angle = getAngle(level, partialTick, stateAbove);
                vec.add(getTranslation(stateAbove, facing, angleMultiplier(angle, ratio)));
            }
        }
        return vec;
    }

    private static float heightRatio(int tip, int height) {
        return (float) (height + 1) / (tip + height + 1);
    }

    private record Range(float lowerBound, float upperBound) {
        private static Range of(float lowerBound, float upperBound) {
            return new Range(lowerBound, upperBound);
        }

        private boolean includes(float value) {
            return value > lowerBound && value < upperBound;
        }

        private boolean includes(float value1, float value2) {
            return includes(value1) && includes(value2);
        }
    }

    private static Vector4f packData(Vector3f vector3f, Vector2i vector2i, int type, float x, float y, float z) {
        short i = floatToFixed10(vector3f.x);
        short j = floatToFixed10(vector3f.z);
        int vx = packShortToInt(i, j);
        short k = floatToFixed10(vector3f.y);
        int vy = packShortToInt((short) type, k);
        int vz = packShortToInt((short) vector2i.x, (short) vector2i.y);
        short px = 0, h = 0, pz =0;
        if (type == 4) {
            px = floatToFixed9(x);
            h = floatToFixed9(y);
            pz = floatToFixed9(z);
        }
        int vw = (px << 20) | (h << 10) | pz;
        return new Vector4f(vx, vy, vz, vw);
    }

    private static Vector4f packData(Vector3f vector3f, int value) {
        short i = floatToFixed10(vector3f.x);
        short j = floatToFixed10(vector3f.z);
        int x = packShortToInt(i, j);
        short k = floatToFixed10(vector3f.y);
        int y = packShortToInt((short) 3, k);
        return new Vector4f(x, y, value, 0);
    }

    private static int packShortToInt(short high, short low) {
        return ((high & 0xFFFF) << 16) | (low & 0xFFFF);
    }

    private static short floatToFixed10(float num) {
        return (short) (num * 1024);
    }

    private static short floatToFixed9(float num) {
        return (short) (num * 512);
    }
}
