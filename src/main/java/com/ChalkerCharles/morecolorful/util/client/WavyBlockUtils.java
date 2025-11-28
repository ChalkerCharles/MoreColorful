package com.ChalkerCharles.morecolorful.util.client;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.MultipartBlock;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IGrowingPlantBlockMixin;
import com.ChalkerCharles.morecolorful.util.*;
import it.unimi.dsi.fastutil.booleans.BooleanIntPair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Set;
import java.util.function.Predicate;

@OnlyIn(Dist.CLIENT)
public final class WavyBlockUtils {
    public static final Range FLOWER_POT = Range.of(0.3F, 0.7F);
    public static final Range BAMBOO = Range.of(0.375F, 0.625F);
    private static final Range VINE_NORTHWEST = Range.of(0.04F, 0.5F);
    private static final Range VINE_SOUTHEAST = Range.of(0.5F, 0.96F);
    private static final BooleanIntPair FIXED = BooleanIntPair.of(false, 0);
    private static final BooleanIntPair WAVE0 = BooleanIntPair.of(true, 0);
    private static final BooleanIntPair WAVE1 = BooleanIntPair.of(true, 1);
    public static final Set<Predicate<BlockState>> MULTI_BLOCK_GROUPS = Set.of(
            state -> state.is(Blocks.VINE),
            state -> state.is(Blocks.WEEPING_VINES) || state.is(Blocks.WEEPING_VINES_PLANT),
            state -> state.is(Blocks.TWISTING_VINES) || state.is(Blocks.TWISTING_VINES_PLANT),
            state -> state.is(Blocks.CAVE_VINES) || state.is(Blocks.CAVE_VINES_PLANT),
            state -> state.is(Blocks.SUGAR_CANE),
            state -> state.is(ModBlocks.WILLOW_BRANCHES),
            state -> state.is(Blocks.BIG_DRIPLEAF) || state.is(Blocks.BIG_DRIPLEAF_STEM),
            WavyBlockUtils::isChainLanternGroup
    );

    public static long getWaveDataByVertex(Level level, BlockState state, BlockPos pos, float x, float y, float z, int type) {
        if (level == null) return 0L;
        BooleanIntPair result = canVertexApplyWind(level, state, pos, x, y, z, type);
        return result.leftBoolean() ? packData(result.rightInt(), type, pos) : 0L;
    }

    private static BooleanIntPair canVertexApplyWind(Level level, BlockState state, BlockPos pos, float x, float y, float z, int type) {
        Block block = state.getBlock();
        return switch (type) {
            case 2 -> handleVines(level, block, state, pos, x, y, z);
            case 4 -> handleChain(level, state, pos, y);
            case 5 -> handleLantern(level, pos);
            case 6 -> handleHanging(level, block, pos, y);
            case 7 -> {
                BlockPos pos1 = pos.below();
                yield y < 0.5 && level.getBlockState(pos1).isFaceSturdy(level, pos1, Direction.UP)
                        ? FIXED : WAVE0;
            }
            case 8 -> y > 0.35 ? WAVE1 : FIXED;
            case 9 -> BAMBOO.includes(x, z) ? FIXED : WAVE0;
            case 10 -> y > 0.525 ? WAVE1 : FIXED;
            case 11 -> !FLOWER_POT.includes(x, z) && y > 0.255 ? WAVE1 : FIXED;
            case 12 -> state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.LOWER && y < 0.4F
                    ? FIXED : handle(level, block, pos, y);
            default -> handle(level, block, pos, y);
        };
    }

    // --------------------------------------------- Vines --------------------------------------------- //

    private static BooleanIntPair handleVines(Level level, Block block, BlockState state, BlockPos pos, float x, float y, float z) {
        Direction direction = getFaceByVertex(x, z);
        if (direction == Direction.UP)
            return isFaceAttachedToLeaves(level, state, pos, direction) ? WAVE0 : FIXED;
        int height = getVineRoot(level, block, pos, direction);
        int tip = getVineTip(level, block, pos, direction);
        int i = y < 0.9F ? height + 1 : height;
        int j = y < 0.9F ? tip - 1 : tip;
        if (isFaceAttachedToLeaves(level, state, pos, direction)) {
            return BooleanIntPair.of(true, pack(j, i));
        }
        if (isFaceAttached(level, block, state, pos, direction) || height < 0)
            return FIXED;
        if (height == 0)
            return y > 0.9F ? FIXED : BooleanIntPair.of(true, pack(j, 1));
        if (RenderUtils.isWindyAt(level, pos.above(height)))
            return BooleanIntPair.of(true, pack(j, i));
        return FIXED;
    }

    private static int getVineRoot(Level level, Block block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualVineRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        while (!isActualVineRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i--;
            mutablePos.move(Direction.DOWN);
        }
        RenderUtils.putWindSectionData(level, pos, mutablePos);
        return i;
    }

    private static boolean isVisualVineRoot(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block) || isFaceAttached(level, block, state, pos, Direction.UP)) return true;
        BlockPos pos1 = pos.above();
        return !isFaceAttached(level, block, state, pos, direction)
                && isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction);
    }

    private static boolean isActualVineRoot(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block)) return true;
        BlockPos pos1 = pos.above();
        return RenderUtils.isWindyAt(level, pos) && !isFaceAttached(level, block, state, pos, direction)
                && (!RenderUtils.isWindyAt(level, pos1) || isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction));
    }

    private static int getVineTip(Level level, Block block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVineTip(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(Direction.DOWN);
        }
        return i;
    }

    private static boolean isVineTip(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        BlockPos pos1 = pos.below();
        if (!state.is(block) || !level.getBlockState(pos1).is(block)) return true;
        return !isFaceAttached(level, block, state, pos, direction)
                && isFaceAttached(level, block, level.getBlockState(pos1), pos1, direction);
    }

    private static boolean isFaceAttached(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!state.is(block)) return false;
        BooleanProperty facing = VineBlock.getPropertyForFace(direction);
        BlockPos pos1 = pos.relative(direction);
        return state.getValue(facing) && !(level.getBlockState(pos1).getBlock() instanceof LeavesBlock)
                && VineBlock.isAcceptableNeighbour(level, pos1, direction);
    }

    private static boolean isFaceAttachedToLeaves(Level level, BlockState state, BlockPos pos, Direction direction) {
        BooleanProperty facing = VineBlock.getPropertyForFace(direction);
        BlockPos pos1 = pos.relative(direction);
        return state.getValue(facing) && level.getBlockState(pos1).getBlock() instanceof LeavesBlock;
    }

    public static Direction getFaceByVertex(float x, float z) {
        if (VINE_NORTHWEST.includes(z)) return Direction.NORTH;
        if (VINE_SOUTHEAST.includes(z)) return Direction.SOUTH;
        if (VINE_NORTHWEST.includes(x)) return Direction.WEST;
        if (VINE_SOUTHEAST.includes(x)) return Direction.EAST;
        return Direction.UP;
    }

    // ------------------------------------ Hanging & Upward Blocks ------------------------------------ //

    private static BooleanIntPair handleHanging(Level level, Block block, BlockPos pos, float y) {
        if (!(block instanceof MultipartBlock)) return y > 0.94F ? FIXED : WAVE1;
        int height = getRoot(level, block, pos, Direction.UP);
        if (height < 0)
            return FIXED;
        int tip = getTip(level, block, pos, Direction.DOWN);
        int i = y < 0.94F ? height + 1 : height;
        int j = y < 0.94F ? tip - 1 : tip;
        if (height == 0) {
            if (level.getBlockState(pos.above()).getBlock() instanceof LeavesBlock)
                return BooleanIntPair.of(true, pack(j, i));
            return y > 0.94F ? FIXED : BooleanIntPair.of(true, pack(j, 1));
        }
        if (RenderUtils.isWindyAt(level, pos.above(height)))
            return BooleanIntPair.of(true, pack(j, i));
        return FIXED;
    }

    private static BooleanIntPair handle(Level level, Block block, BlockPos pos, float y) {
        if (!(block instanceof MultipartBlock)) return y < 0.005F ? FIXED : WAVE1;
        int height = getRoot(level, block, pos, Direction.DOWN);
        if (height < 0)
            return FIXED;
        int tip = getTip(level, block, pos, Direction.UP);
        int i = y > 0.005F ? height + 1 : height;
        int j = y > 0.005F ? tip - 1 : tip;
        if (height == 0) {
            return y < 0.005F ? FIXED : BooleanIntPair.of(true, pack(j, 1));
        }
        if (RenderUtils.isWindyAt(level, pos.below(height)))
            return BooleanIntPair.of(true, pack(j, i));
        return FIXED;
    }

    private static int getRoot(Level level, Block block, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i++;
            mutablePos.move(direction);
        }
        while (!isActualRoot(level, block, level.getBlockState(mutablePos), mutablePos, direction)) {
            i--;
            mutablePos.move(direction.getOpposite());
        }
        RenderUtils.putWindSectionData(level, pos, mutablePos);
        return i;
    }

    private static boolean isActualRoot(Level level, Block block, BlockState state, BlockPos pos, Direction direction) {
        if (!isSameBlock(state, block)) return true;
        BlockPos pos1 = pos.relative(direction);
        return RenderUtils.isWindyAt(level, pos) && isSameBlock(state, block)
                && !(RenderUtils.isWindyAt(level, pos1) && isSameBlock(level.getBlockState(pos1), block));
    }

    private static int getTip(Level level, Block block, BlockPos pos, Direction direction) {
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
            return state.is(((IGrowingPlantBlockMixin) growingPlant).invokeGetBodyBlock())
                    || state.is(((IGrowingPlantBlockMixin) growingPlant).invokeGetHeadBlock());
        } else if (block instanceof BigDripleafBlock || block instanceof BigDripleafStemBlock) {
            Block block1 = state.getBlock();
            return block1 instanceof BigDripleafBlock || block1 instanceof BigDripleafStemBlock;
        }
        return state.is(block);
    }

    // ---------------------------------------- Chains & Lantern ---------------------------------------- //

    private static BooleanIntPair handleChain(Level level, BlockState state, BlockPos pos, float y) {
        if (state.getValue(BlockStateProperties.AXIS).isHorizontal()) return FIXED;
        BooleanIntPair isFixedOnGround = isChainFixedOnGround(level, pos);
        if (isFixedOnGround.leftBoolean()) return FIXED;
        int height = getChainRoot(level, pos);
        if (height < 0)
            return FIXED;
        int tip = isFixedOnGround.rightInt();
        if (height == 0) {
            return y > 0.9F ? FIXED : BooleanIntPair.of(true, pack(tip, 0));
        }
        if (RenderUtils.isWindyAt(level, pos.above(height)))
            return BooleanIntPair.of(true, pack(tip, height));
        return FIXED;
    }

    private static int getChainRoot(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualChainRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        while (!isActualChainRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i--;
            mutablePos.move(Direction.DOWN);
        }
        RenderUtils.putWindSectionData(level, pos, mutablePos);
        return i;
    }

    private static boolean isVisualChainRoot(Level level, BlockState state, BlockPos pos) {
        if (!isChainVertical(state)) return true;
        return !isChainVertical(level.getBlockState(pos.above()));
    }

    private static boolean isActualChainRoot(Level level, BlockState state, BlockPos pos) {
        if (!isChainVertical(state)) return true;
        BlockPos pos1 = pos.above();
        return RenderUtils.isWindyAt(level, pos)
                && !(RenderUtils.isWindyAt(level, pos1) && isChainVertical(level.getBlockState(pos1)));
    }

    private static boolean isChainLanternGroup(BlockState state) {
        Block block = state.getBlock();
        if (block instanceof ChainBlock && state.getValue(BlockStateProperties.AXIS).isVertical())
            return true;
        return block instanceof LanternBlock && state.getValue(LanternBlock.HANGING);
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

    private static BooleanIntPair handleLantern(Level level, BlockPos pos) {
        BlockPos pos1 = pos.above();
        BlockState state1 = level.getBlockState(pos1);
        if (isChainVertical(state1)) {
            int height = getChainRoot(level, pos1);
            if (RenderUtils.isWindyAt(level, pos1.above(height)))
                return BooleanIntPair.of(true, pack(0, height + 1));
            return FIXED;
        } else {
            return WAVE0;
        }
    }

    // --------------------------------------------- Fluids --------------------------------------------- //

    public static long getWaveDataByFluidVertex(Level level, BlockPos pos, int x, int y, int z, Corner corner) {
        if (level == null) return 0L;
        BooleanIntPair result = canFluidVertexApplyWind(level, pos, x, y, z, corner);
        return result.leftBoolean() ? packFluidData(result.rightInt(), pos) : 0L;
    }

    private static BooleanIntPair canFluidVertexApplyWind(Level level, BlockPos pos, int x, int y, int z, Corner corner) {
        BlockPos pos1 = pos.above();
        BlockState state = level.getBlockState(pos1);
        if (AirBlocking.canBlockWind(level, pos1, state, Direction.DOWN)) {
            return FIXED;
        }
        int edge = isEdge(level, pos, corner) ? 1 : 2;
        return BooleanIntPair.of(isFluidVertexWaving(level, pos, x, y, z, corner), edge);
    }

    private static boolean isFluidVertexWaving(Level level, BlockPos pos, int x, int y, int z, Corner corner) {
        Direction d1 = corner.first, d2 = corner.second;
        BlockPos pos1 = pos.relative(d1).above(), pos2 = pos.relative(d2).above(), pos3 = pos1.relative(d2);
        if (AirBlocking.canBlockWind(level, pos1, level.getBlockState(pos1), Direction.DOWN)
                || AirBlocking.canBlockWind(level, pos2, level.getBlockState(pos2), Direction.DOWN)
                || AirBlocking.canBlockWind(level, pos3, level.getBlockState(pos3), Direction.DOWN))
            return false;
        return WeatherUtils.canApplyWind(level, x, y + 0.5, z, pos.above());
    }

    private static boolean isEdge(Level level, BlockPos pos, Corner corner) {
        Direction d1 = corner.first, d2 = corner.second;
        BlockPos pos1 = pos.relative(d1), pos2 = pos.relative(d2), pos3 = pos1.relative(d2);
        return isValidEdgeBlock(level, pos1, d1) || isValidEdgeBlock(level, pos2, d2)
                || isValidEdgeBlock(level, pos3, d1) || isValidEdgeBlock(level, pos3, d2);
    }

    private static boolean isValidEdgeBlock(Level level, BlockPos pos, Direction direction) {
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof LeavesBlock) return false;
        return MultifaceBlock.canAttachTo(level, direction, pos, state);
    }

    // ----------------------------------------- Hanging Signs ----------------------------------------- //

    public static Vector4f getSignAngle(Level level, BlockState state, BlockPos pos) {
        if (level == null) return Maths.ZERO_VEC4;
        BooleanIntPair result = canHangingSignApplyWind(level, pos);
        return result.leftBoolean() ? yieldAngle(level, state, pos, result.rightInt()) : Maths.ZERO_VEC4;
    }

    private static Vector4f yieldAngle(Level level, BlockState state, BlockPos pos, int value) {
        int tip = value >> 16, height = (short) (value & 0xffff);
        float ratio = (height + 1.0F) / (tip + height + 1.0F);
        float angle = getAngle(level, state, pos);
        Vector3f accumulated = accumulateSignOffset(level, state, pos, tip, height);
        return new Vector4f(accumulated, ratio * ratio * angle);
    }

    private static float getAngle(Level level, BlockState state, BlockPos pos) {
        Vector3f wind = RenderUtils.getWindSpeedAt(level, pos);
        float f = Mth.sin(RenderUtils.anim * Math.round(wind.length()) * 1.5F) * 0.5F + 0.5F;
        Vector2f facing = getSignFacing(state);
        float angle = wind.dot(facing.x, 0, facing.y) * Maths.INV24;
        return angle * -Mth.rotLerp(f, 0.75F, 1.25F);
    }

    private static float getChainAngle(Level level, BlockPos pos) {
        Vector3f wind = RenderUtils.getWindSpeedAt(level, pos);
        float f = Mth.sin(RenderUtils.anim * Math.round(wind.length()) * 1.5F) * 0.5F + 0.5F;
        float angle = Maths.length(wind.x, wind.z) * Maths.INV24;
        return angle * Mth.rotLerp(f, 0.75F, 1.25F);
    }

    public static float getSignAngleVertical(Level level, BlockState state, BlockPos pos) {
        Vector3f wind = RenderUtils.getWindSpeedAt(level, pos);
        float f = Mth.sin(RenderUtils.anim * Math.round(wind.length()) * 1.875F) * 0.5F + 0.5F;
        Vector2f facing = getSignFacing(state);
        float angle = wind.dot(facing.x, 0, facing.y) * Maths.INV64;
        return Mth.rotLerp(f, -angle, angle);
    }

    private static void addTranslation(Vector3f vec, BlockState stateAbove, Vector2f facing, float angle) {
        Vector2f facingAbove = getSignFacing(stateAbove);
        float i = facing.x * facingAbove.x + facing.y * facingAbove.y;
        float j = facing.x * facingAbove.y - facing.y * facingAbove.x;
        float sin = -Mth.sin(angle);
        vec.add(sin * j, 1.0F - Mth.cos(angle), sin * i);
    }

    private static void addChainTranslation(Vector3f vec, Vector2f facing, float angle) {
        Vector2f dir = RenderUtils.WIND_DIR;
        float i = (facing.x * dir.x + facing.y * dir.y) * 0.8F;
        float j = (facing.x * dir.y - facing.y * dir.x) * 0.8F;
        float sin = Mth.sin(angle);
        vec.add(sin * j, 1.0F - Mth.cos(angle), sin * i);
    }

    private static Vector2f getSignFacing(BlockState state) {
        if (onWall(state)) {
            Direction direction = state.getValue(WallHangingSignBlock.FACING);
            return new Vector2f(direction.getStepX(), direction.getStepZ());
        } else {
            float degree = RotationSegment.convertToDegrees(state.getValue(CeilingHangingSignBlock.ROTATION));
            return new Vector2f(-Mth.sin(degree * Mth.DEG_TO_RAD), Mth.cos(degree * Mth.DEG_TO_RAD));
        }
    }

    private static BooleanIntPair canHangingSignApplyWind(Level level, BlockPos pos) {
        int height = getSignRoot(level, pos);
        if (height < 0)
            return FIXED;
        int tip = getSignTip(level, pos);
        if (height == 0)
            return BooleanIntPair.of(RenderUtils.isWindyAt(level, pos), pack(tip, height));
        if (!RenderUtils.isWindyAt(level, pos) && !RenderUtils.isWindyAt(level, pos.above(height)))
            return FIXED;
        return BooleanIntPair.of(true, pack(tip, height));
    }

    private static int getSignRoot(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        int i = 0;
        while (!isVisualSignRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i++;
            mutablePos.move(Direction.UP);
        }
        while (!isActualSignRoot(level, level.getBlockState(mutablePos), mutablePos)) {
            i--;
            mutablePos.move(Direction.DOWN);
        }
        return i;
    }

    private static boolean isActualSignRoot(Level level, BlockState state, BlockPos pos) {
        if (onWall(state) || !isHangingSignOrChain(state)) return true;
        BlockPos pos1 = pos.above();
        BlockState state1 = level.getBlockState(pos1);
        return RenderUtils.isWindyAt(level, pos)
                && !(RenderUtils.isWindyAt(level, pos1) && isHangingSignOrChain(state1));
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

    private static Vector3f accumulateSignOffset(Level level, BlockState state, BlockPos pos, int tip, int height) {
        int sum = tip + height + 1;
        BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP, height);
        BlockState stateAbove;
        float ratio, ratioSq, angle;
        float invSum = 1.0F / sum;
        Vector2f facing = getSignFacing(state);
        Vector3f vec = new Vector3f();
        for (int i = 0; i < height; i++) {
            stateAbove = level.getBlockState(mutable);
            mutable.move(Direction.DOWN);
            if (!isHangingSignOrChain(stateAbove)) continue;
            ratio = (i + 1) * invSum;
            ratioSq = ratio * ratio;
            if (isChainVertical(stateAbove)) {
                angle = getChainAngle(level, mutable);
                addChainTranslation(vec, facing, angle * ratioSq);
            } else {
                angle = getAngle(level, stateAbove, mutable);
                addTranslation(vec, stateAbove, facing, angle * ratioSq);
            }
        }
        return vec;
    }

    // ------------------------------------------ Data Packing ------------------------------------------ //

    private static int pack(int x, int y) {
        return (x << 16) | (y & 0xffff);
    }

    private static long packData(int value, int type, BlockPos pos) {
        int x = value >> 16, y = (short) (value & 0xffff);
        short a = SectionPos.sectionRelativePos(pos);
        int b = (type << 12 | a) & 0xffff;
        float invSum = 1.0f / (x + y + 1f);
        short f = Float.floatToFloat16(invSum);
        return ((long) f << 32) | ((long) y << 16) | b;
    }

    private static long packFluidData(int value, BlockPos pos) {
        short a = SectionPos.sectionRelativePos(pos);
        int b = (0xf000 | a) & 0xffff;
        return (long) value << 16 | b;
    }
}
