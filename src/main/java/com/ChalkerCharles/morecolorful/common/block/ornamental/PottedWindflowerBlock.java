package com.ChalkerCharles.morecolorful.common.block.ornamental;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.function.Supplier;

public class PottedWindflowerBlock extends FlowerPotBlock {
    public PottedWindflowerBlock(Supplier<FlowerPotBlock> emptyPot, Properties properties) {
        super(emptyPot, ModBlocks.WINDFLOWER, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WindFlowerBlock.WIND_LEVEL, 0));
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        WindFlowerBlock.tryChangingState(pState, pLevel, pPos);
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState state = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (!state.isAir() && pLevel instanceof Level level) {
            return WindFlowerBlock.setWindLevel(state, level, pCurrentPos);
        }
        return state;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WindFlowerBlock.WIND_LEVEL);
    }
}
