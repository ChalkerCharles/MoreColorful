package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.Optional;

public class WaterGrassBlock extends DoublePlantBlock implements SimpleWaterloggedBlock, BonemealableBlock {
    private static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape SHORT_WATER_GRASS_TOP = Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    public WaterGrassBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = super.getStateForPlacement(pContext);
        if (blockstate != null) {
            FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos().above());
            if (fluidstate.isEmpty()) {
                return blockstate;
            }
        }
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(WATERLOGGED) && pLevel.getFluidState(pPos.above()).is(Fluids.WATER)) {
            return false;
        } else if (pState.getValue(HALF) == DoubleBlockHalf.UPPER && !pState.getValue(WATERLOGGED)) {
            BlockState blockstate = pLevel.getBlockState(pPos.below());
            return blockstate.is(this) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER;
        } else {
            FluidState fluidstate = pLevel.getFluidState(pPos);
            return super.canSurvive(pState, pLevel, pPos) && fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HALF, WATERLOGGED);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if (pState.is(ModBlocks.SHORT_WATER_GRASS) && pState.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return SHORT_WATER_GRASS_TOP;
        }
        return super.getShape(pState, pLevel, pPos, pContext);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (pState.getValue(WATERLOGGED)) {
            pLevel.scheduleTick(pCurrentPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return !pState.canSurvive(pLevel, pCurrentPos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    protected FluidState getFluidState(BlockState pState) {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    private static BlockPos getBottomPos(BlockPos pPos, BlockState pState) {
        return pState.getValue(HALF) == DoubleBlockHalf.LOWER ? pPos : pPos.below();
    }

    private boolean canPlace(LevelReader pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        FluidState fluidStateAbove = pLevel.getFluidState(pPos.above());
        return pPos.getY() < pLevel.getMaxBuildHeight() - 1
                && pLevel.getBlockState(pPos.above()).canBeReplaced()
                && pLevel.getBlockState(pPos).canBeReplaced()
                && !(pLevel.getBlockState(pPos).getBlock() instanceof WaterGrassBlock)
                && fluidstate.is(FluidTags.WATER)
                && fluidstate.getAmount() == 8
                && fluidStateAbove.isEmpty()
                && mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        if (pState.is(ModBlocks.SHORT_WATER_GRASS)) {
            return true;
        } else {
            BlockPos pos = getBottomPos(pPos, pState);
            return Direction.stream().anyMatch(d -> canPlace(pLevel, pos.relative(d)));
        }
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        BlockPos pos = getBottomPos(pPos, pState);
        if (pState.is(ModBlocks.SHORT_WATER_GRASS)) {
            DoublePlantBlock.placeAt(pLevel, ModBlocks.TALL_WATER_GRASS.get().defaultBlockState(), pos, 2);
        } else {
            Optional<BlockPos> pos1 = BlockPos.findClosestMatch(pos, 1, 0, p -> {
                if (p.equals(pos)) return false;
                return canPlace(pLevel, p);
            });
            pos1.ifPresent(pos2 -> DoublePlantBlock.placeAt(pLevel, ModBlocks.SHORT_WATER_GRASS.get().defaultBlockState(), pos2, 3));
        }
    }
}
