package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BassDrumBlock extends PercussionInstrumentBlock {

    protected static final VoxelShape BASS_DRUM = Block.box(1.0, 0.0, 1.0, 15.0, 10.0, 15.0);

    public BassDrumBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return BASS_DRUM;
    }

}
