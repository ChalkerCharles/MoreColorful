package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

public class DidgeridooItem extends BothHandsInstrumentItem {

    public DidgeridooItem(InstrumentsType pType, Properties pProperties, int burnTime) {
        super(pType, pProperties, burnTime);
        this.pType = pType;
        this.burnTime = burnTime;
    }
    public DidgeridooItem(InstrumentsType pType, Properties pProperties) {
        this(pType, pProperties, 0);
    }
}
