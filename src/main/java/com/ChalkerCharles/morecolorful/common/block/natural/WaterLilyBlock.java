package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.util.Lazy;

public class WaterLilyBlock extends WaterlilyBlock implements BonemealableBlock {
    private static final VoxelShape COLLISION = Block.box(1.0, 0.0, 1.0, 15.0, 1.5, 15.0);
    private static final VoxelShape SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 6.0, 15.0);
    private static final Lazy<BiMap<Block, Block>> FALLING_ASLEEP = Lazy.of(() -> ImmutableBiMap.of(
            ModBlocks.OPEN_WATER_LILY.get(), ModBlocks.CLOSED_WATER_LILY.get(),
            ModBlocks.OPEN_WHITE_WATER_LILY.get(), ModBlocks.CLOSED_WHITE_WATER_LILY.get(),
            ModBlocks.OPEN_BLUE_WATER_LILY.get(), ModBlocks.CLOSED_BLUE_WATER_LILY.get())
    );
    private static final Lazy<BiMap<Block, Block>> WAKING_UP = Lazy.of(() -> FALLING_ASLEEP.get().inverse());
    private final boolean isOpen;
    public WaterLilyBlock(boolean isOpen, Properties properties) {
        super(properties);
        this.isOpen = isOpen;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return COLLISION;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.tryChangingState(pState, pLevel, pPos);
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private void tryChangingState(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.dimensionType().natural() && level.isDay() != isOpen) {
            level.setBlock(pos, getOpposite(state), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }

    private BlockState getOpposite(BlockState state) {
        if (this.isOpen) {
            return FALLING_ASLEEP.get().get(state.getBlock()).defaultBlockState();
        } else {
            return WAKING_UP.get().get(state.getBlock()).defaultBlockState();
        }
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
        popResource(pLevel, pPos, new ItemStack(this));
    }
}
