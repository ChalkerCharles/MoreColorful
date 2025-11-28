package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import net.minecraft.core.BlockPos;

public class InstrumentData {
    public boolean isPlaying;
    public boolean isOpen;
    public float tick;
    public InstrumentsType type = InstrumentsType.HARP;
    public BlockPos pos = BlockPos.ZERO;
    public byte pressingMask;

    public boolean isPressingBassDrum() {
        return (this.pressingMask & 1) != 0;
    }

    public boolean isPressingHat() {
        return (this.pressingMask & 2) != 0;
    }

    public boolean isPressingRide() {
        return (this.pressingMask & 4) != 0;
    }

    public boolean isPressingCrash() {
        return (this.pressingMask & 8) != 0;
    }

    public void setDrumSetData(byte pressingMask, BlockPos pos) {
        this.pressingMask = pressingMask;
        this.pos = pos;
    }

    public void setPlayingScreenData(InstrumentsType type, BlockPos pos, boolean isOpen) {
        this.type = type;
        this.pos = pos;
        this.isOpen = isOpen;
    }
}
