package com.ChalkerCharles.morecolorful.common.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

import javax.annotation.Nullable;

public class WaterloggedSaplingBlock extends SaplingBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<WaterloggedSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(TreeGrower.CODEC.fieldOf("tree").forGetter(block -> block.treeGrower), propertiesCodec())
                    .apply(instance, WaterloggedSaplingBlock::new)
    );
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    @Override
    public MapCodec<WaterloggedSaplingBlock> codec() {
        return CODEC;
    }

    public WaterloggedSaplingBlock(TreeGrower grower, Properties properties) {
        super(grower, properties);
        this.registerDefaultState(
                this.stateDefinition
                        .any()
                        .setValue(STAGE, 0)
                        .setValue(WATERLOGGED, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE, WATERLOGGED);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        FluidState fluidstate = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidstate.getType() == Fluids.WATER;
        BlockState blockState = super.getStateForPlacement(pContext);
        if (blockState != null) {
            return blockState.setValue(WATERLOGGED, flag);
        }
        return null;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        if (pState.getValue(WATERLOGGED) && pLevel.getFluidState(pPos.above()).is(Fluids.WATER)) {
            return false;
        }
        return super.canSurvive(pState, pLevel, pPos);
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
}
