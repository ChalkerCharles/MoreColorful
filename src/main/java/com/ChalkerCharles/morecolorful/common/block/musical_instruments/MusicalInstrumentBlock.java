package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.world.level.block.Block;

public abstract class MusicalInstrumentBlock extends Block {
    protected InstrumentsType pType;
    public MusicalInstrumentBlock(InstrumentsType pType, Properties properties) {
        super(properties);
        this.pType = pType;
    }
    public InstrumentsType getType() {
        return pType;
    }
}
