package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.entity.CocoonBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CocoonBlock extends BaseEntityBlock {
    public static final MapCodec<CocoonBlock> CODEC = simpleCodec(CocoonBlock::new);
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    private static final VoxelShape SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 8.0, 11.0);

    public CocoonBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(HATCH);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public static int getHatchLevel(BlockState pState) {
        return pState.getValue(HATCH);
    }

    private static boolean isReadyToHatch(BlockState pState) {
        return getHatchLevel(pState) == 2;
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (!(pLevel.getBlockEntity(pPos) instanceof CocoonBlockEntity cocoon) || cocoon.isEmpty()) {
            return;
        }
        if (!isReadyToHatch(pState)) {
            pLevel.playSound(null, pPos, ModSounds.COCOON_CRACK.get(), SoundSource.BLOCKS, 0.5F, 0.9F + pRandom.nextFloat() * 0.2F);
            pLevel.setBlock(pPos, pState.setValue(HATCH, getHatchLevel(pState) + 1), 2);
        } else {
            pLevel.playSound(null, pPos, ModSounds.COCOON_HATCH.get(), SoundSource.BLOCKS, 0.5F, 0.9F + pRandom.nextFloat() * 0.2F);
            cocoon.hatch(pLevel, pPos);
            pLevel.destroyBlock(pPos, false);
        }
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston) {
        super.onPlace(pState, pLevel, pPos, pOldState, pMovedByPiston);
        pLevel.scheduleTick(pPos, this, 4000 + pLevel.random.nextInt(300));
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getCloneItemStack(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return pLevel.getBlockEntity(pPos) instanceof CocoonBlockEntity blockEntity
                ? blockEntity.getItem()
                : super.getCloneItemStack(pLevel, pPos, pState);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new CocoonBlockEntity(pPos, pState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public boolean isPathfindable(BlockState pState, PathComputationType pPathComputationType) {
        return false;
    }
}
