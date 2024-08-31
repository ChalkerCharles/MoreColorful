package com.ChalkerCharles.morecolorful.common.item.musical_instruments;

public class GuitarItem extends BothHandsInstrumentItem {

    public GuitarItem(InstrumentsType pType, Properties pProperties, int burnTime) {
        super(pType, pProperties, burnTime);
        this.pType = pType;
    }
    public GuitarItem(InstrumentsType pType, Properties pProperties) {
        this(pType, pProperties, 0);
    }
}
