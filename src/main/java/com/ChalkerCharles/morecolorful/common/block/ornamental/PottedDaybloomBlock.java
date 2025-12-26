package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class PottedDaybloomBlock extends FlowerPotBlock {
    public PottedDaybloomBlock(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> potted, Properties properties) {
        super(emptyPot, potted, properties);
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.dimensionType().natural()) {
            boolean flag = this.getPotted() == ModBlocks.OPEN_DAYBLOOM.get();
            boolean flag1 = pLevel.isNight();
            if (flag == flag1) {
                pLevel.setBlock(pPos, opposite(pState), 3);
            }
        }
    }

    private static BlockState opposite(BlockState state) {
        if (state.is(ModBlocks.POTTED_OPEN_DAYBLOOM)) {
            return ModBlocks.POTTED_CLOSED_DAYBLOOM.get().defaultBlockState();
        } else {
            return state.is(ModBlocks.POTTED_CLOSED_DAYBLOOM) ? ModBlocks.POTTED_OPEN_DAYBLOOM.get().defaultBlockState() : state;
        }
    }
}
