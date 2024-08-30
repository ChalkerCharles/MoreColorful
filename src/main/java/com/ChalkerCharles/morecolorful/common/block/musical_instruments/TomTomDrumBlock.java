package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TomTomDrumBlock extends PercussionInstrumentBlock{
    private static final VoxelShape TOM = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);
    public TomTomDrumBlock(InstrumentsType pType, Properties properties) {
        super(pType, properties);
    }
    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return TOM;
    }
}
