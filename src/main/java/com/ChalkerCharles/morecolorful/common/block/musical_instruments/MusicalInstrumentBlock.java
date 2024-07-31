package com.ChalkerCharles.morecolorful.common.block.musical_instruments;

import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import org.jetbrains.annotations.NotNull;

public abstract class MusicalInstrumentBlock extends Block {
    InstrumentsType pType;
    public MusicalInstrumentBlock(InstrumentsType pType, Properties properties) {
        super(properties);
        this.pType = pType;
    }
}
