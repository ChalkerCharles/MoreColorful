package com.ChalkerCharles.morecolorful.common.block.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.IceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class DuckweedsBlock extends LeafLitterBlock implements BonemealableBlock {
    public DuckweedsBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (pLevel instanceof ServerLevel && pEntity instanceof Boat) {
            if (!tryMoveDuckweeds(pState, pLevel, pPos, pEntity)) {
                pLevel.destroyBlock(pPos, true, pEntity);
            }
        }
    }

    private boolean tryMoveDuckweeds(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        int deltaX = pPos.getX() - pEntity.getBlockX();
        int deltaZ = pPos.getZ() - pEntity.getBlockZ();
        int factorX = deltaX >= 0 ? 1 : -1;
        int factorZ = deltaZ >= 0 ? 1 : -1;
        BlockPos pos;
        for (int i = 0; i < 4; i++) {
            pos = pPos.offset(deltaX + i * factorX, 0, deltaZ + i * factorZ);
            if (canMoveTo(pLevel, pos)) {
                move(pState, pLevel, pPos, pos);
                return true;
            }
        }
        return false;
    }

    private static void move(BlockState pState, Level pLevel, BlockPos origin, BlockPos target) {
        pLevel.setBlock(origin, Blocks.AIR.defaultBlockState(), 3);
        pLevel.setBlock(target, pState, 3);
    }

    private boolean canMoveTo(Level pLevel, BlockPos pPos) {
        return pLevel.getBlockState(pPos).isAir()
                && mayPlaceOn(pLevel.getBlockState(pPos.below()), pLevel, pPos.below())
                && pLevel.isInWorldBounds(pPos);
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        FluidState fluidstate = pLevel.getFluidState(pPos);
        FluidState fluidState1 = pLevel.getFluidState(pPos.above());
        return (fluidstate.getType() == Fluids.WATER || pState.getBlock() instanceof IceBlock) && fluidState1.getType() == Fluids.EMPTY;
    }

    @Override
    protected boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState belowBlockState = pLevel.getBlockState(blockpos);
        return this.mayPlaceOn(belowBlockState, pLevel, blockpos);
    }

    @Override
    public boolean canBeReplaced(BlockState pState, BlockPlaceContext pContext) {
        return !pContext.isSecondaryUseActive() && pContext.getItemInHand().is(this.asItem()) && pState.getValue(AMOUNT) < 4 || super.canBeReplaced(pState, pContext);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        int i = pState.getValue(AMOUNT);
        if (i < 4) {
            pLevel.setBlock(pPos, pState.setValue(AMOUNT, i + 1), 2);
        } else {
            popResource(pLevel, pPos, new ItemStack(this));
        }
    }
}
