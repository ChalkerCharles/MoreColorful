package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.MultipartBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.ReedPart;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class ReedBlock extends BushBlock implements BonemealableBlock, SimpleWaterloggedBlock, WindSensitive, MultipartBlock {
    public static final MapCodec<ReedBlock> CODEC = simpleCodec(ReedBlock::new);
    public static final EnumProperty<ReedPart> PART = ModBlockStateProperties.REED_PART;
    public static final BooleanProperty TALL_REED = ModBlockStateProperties.TALL_REED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
    public ReedBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(WATERLOGGED, false)
                        .setValue(TALL_REED, false)
                        .setValue(PART, ReedPart.LOWER)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(PART, WATERLOGGED, TALL_REED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockPos blockpos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        BlockState blockstate = super.getStateForPlacement(pContext);
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        FluidState fluidStateAbove = pContext.getLevel().getFluidState(pContext.getClickedPos().above());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        if (blockpos.getY() < level.getMaxBuildHeight() - 1
                && level.getBlockState(blockpos.above()).canBeReplaced(pContext)) {
            if (blockstate != null) {
                if (fluidStateAbove.isEmpty()) {
                    return blockstate.setValue(WATERLOGGED, flag);
                }
            }
        }
        return null;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
        BlockPos blockpos = pPos.above();
        pLevel.setBlock(blockpos, DoublePlantBlock.copyWaterloggedFrom(pLevel, blockpos, this.defaultBlockState().setValue(PART, ReedPart.UPPER)), 3);
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockState belowState = pLevel.getBlockState(pPos.below());
        BlockState aboveState = pLevel.getBlockState(pPos.above());
        if (pLevel.getFluidState(pPos.above()).is(Fluids.WATER)) {
            return false;
        } else if (pState.getValue(PART) == ReedPart.UPPER) {
            return belowState.is(this) && (belowState.getValue(PART) == ReedPart.MID || belowState.getValue(PART) == ReedPart.LOWER);
        } else if (pState.getValue(PART) == ReedPart.MID) {
            return belowState.is(this) && belowState.getValue(PART) == ReedPart.LOWER
                    && aboveState.is(this) && aboveState.getValue(PART) == ReedPart.UPPER;
        } else {
            return super.canSurvive(pState, pLevel, pPos) && isHydratedOrWaterlogged(pState, pLevel, pPos);
        }
    }

    private static boolean isHydrated(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockState blockstate = pLevel.getBlockState(blockpos.relative(direction));
            FluidState fluidstate = pLevel.getFluidState(blockpos.relative(direction));
            if (pState.canBeHydrated(pLevel, pPos, fluidstate, blockpos.relative(direction)) || blockstate.is(Blocks.FROSTED_ICE)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isHydratedOrWaterlogged(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return isHydrated(pState, pLevel, pPos) || pState.getValue(WATERLOGGED);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!pState.canSurvive(pLevel, pPos)) {
            pLevel.destroyBlock(pPos, true);
        }
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }
        ReedPart part = pState.getValue(PART);
        if (pFacing.getAxis() != Direction.Axis.Y
                || part == ReedPart.LOWER != (pFacing == Direction.UP)
                || pFacingState.is(this) && pFacingState.getValue(PART) != part) {
            return part == ReedPart.LOWER && pFacing == Direction.DOWN && !pState.canSurvive(pLevel, pCurrentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        } else {
            return Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    private static boolean isTall(BlockState pState) {
        return pState.getValue(TALL_REED);
    }

    public static BlockPos getBottomPos(BlockPos pPos, BlockState pState) {
        if (pState.getValue(PART) == ReedPart.UPPER && isTall(pState)) {
            return pPos.below(2);
        }
        return pState.getValue(PART) == ReedPart.LOWER ? pPos : pPos.below();
    }

    private boolean canGrow(LevelReader pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos.above(2));
        return pPos.getY() < pLevel.getMaxBuildHeight() - 2
                && pLevel.getBlockState(pPos.above(2)).canBeReplaced()
                && fluidstate.isEmpty()
                && mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return !isTall(pState);
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockPos pos = getBottomPos(pPos, pState);
        if (canGrow(pLevel, pos)) {
            pLevel.setBlock(pos, DoublePlantBlock.copyWaterloggedFrom(pLevel, pos, pState)
                    .setValue(PART, ReedPart.LOWER)
                    .setValue(TALL_REED, true), 2);
            pLevel.setBlock(pos.above(), pState.setValue(PART, ReedPart.MID)
                    .setValue(WATERLOGGED, false)
                    .setValue(TALL_REED, true), 2);
            pLevel.setBlock(pos.above(2), pState.setValue(PART, ReedPart.UPPER)
                    .setValue(WATERLOGGED, false)
                    .setValue(TALL_REED, true), 3);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        if (!pLevel.isClientSide) {
            if (pPlayer.isCreative()) {
                preventDropFromBottom(pLevel, pPos, pState, pPlayer);
            } else {
                dropResources(pState, pLevel, pPos, null, pPlayer, pPlayer.getMainHandItem());
            }
        }

        return super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pTe, ItemStack pStack) {
        super.playerDestroy(pLevel, pPlayer, pPos, Blocks.AIR.defaultBlockState(), pTe, pStack);
    }

    private static void preventDropFromBottom(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        BlockPos bottomPos = getBottomPos(pPos, pState);
        BlockState bottomState = pLevel.getBlockState(bottomPos);
        BlockState blockstate1 = bottomState.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
        pLevel.setBlock(bottomPos, blockstate1, 35);
        pLevel.levelEvent(pPlayer, 2001, bottomPos, Block.getId(bottomState));
    }
}
