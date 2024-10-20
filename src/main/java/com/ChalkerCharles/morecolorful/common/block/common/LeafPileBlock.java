package com.ChalkerCharles.morecolorful.common.block.common;

import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.BiFunction;

public class LeafPileBlock extends BushBlock {
    public static final MapCodec<LeafPileBlock> CODEC = simpleCodec(LeafPileBlock::new);
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty AMOUNT = ModBlockStateProperties.LEAF_AMOUNT;
    private static final BiFunction<Direction, Integer, VoxelShape> SHAPE_BY_PROPERTIES = Util.memoize(
            (p_296142_, p_294775_) -> {
                VoxelShape[] avoxelshape = new VoxelShape[]{
                        Block.box(8.0, 0.0, 8.0, 16.0, 2.0, 16.0),
                        Block.box(8.0, 0.0, 0.0, 16.0, 2.0, 8.0),
                        Block.box(0.0, 0.0, 0.0, 8.0, 2.0, 8.0),
                        Block.box(0.0, 0.0, 8.0, 8.0, 2.0, 16.0)
                };
                VoxelShape voxelshape = Shapes.empty();

                for (int i = 0; i < p_294775_; i++) {
                    int j = Math.floorMod(i - p_296142_.get2DDataValue(), 4);
                    voxelshape = Shapes.or(voxelshape, avoxelshape[j]);
                }

                return voxelshape.singleEncompassing();
            }
    );

    public LeafPileBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }
    @Override
    public BlockState rotate(BlockState pState, Rotation pRotation) {
        return pState.setValue(FACING, pRotation.rotate(pState.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        return Block.canSupportRigidBlock(pLevel, pPos.below());
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pContext) {
        return !pContext.isSecondaryUseActive() && pContext.getItemInHand().is(this.asItem()) && pState.getValue(AMOUNT) < 4 || super.canBeReplaced(pState, pContext);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pGetter, BlockPos pPos, CollisionContext pContext) {
        return SHAPE_BY_PROPERTIES.apply(pState.getValue(FACING), pState.getValue(AMOUNT));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState blockstate = pContext.getLevel().getBlockState(pContext.getClickedPos());
        return blockstate.is(this)
                ? blockstate.setValue(AMOUNT, Math.min(4, blockstate.getValue(AMOUNT) + 1))
                : this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING, AMOUNT);
    }
}
