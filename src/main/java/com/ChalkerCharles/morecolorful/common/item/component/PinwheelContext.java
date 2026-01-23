package com.ChalkerCharles.morecolorful.common.item.component;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Mth;

public record PinwheelContext(int currentFrame, int lastFrame, int interval) {
    public static PinwheelContext DEFAULT = new PinwheelContext(0, 0, -1);
    public static final StreamCodec<ByteBuf, PinwheelContext> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            PinwheelContext::currentFrame,
            ByteBufCodecs.VAR_INT,
            PinwheelContext::lastFrame,
            ByteBufCodecs.VAR_INT,
            PinwheelContext::interval,
            PinwheelContext::new
    );

    public static int getInterval(int windLevel) {
        return switch (windLevel) {
            case 1 -> 10;
            case 2 -> 5;
            case 3 -> 2;
            case 4, 5 -> 1;
            default -> -1;
        };
    }

    public PinwheelContext update(int windLevel) {
        if (windLevel == 0) {
            if (this.currentFrame == this.lastFrame) return this;
            return new PinwheelContext(this.currentFrame, this.currentFrame, this.interval);
        }
        int currentFrame = this.currentFrame;
        int lastFrame = this.lastFrame;
        int interval = this.interval;
        if (interval == -1) {
            interval = getInterval(windLevel);
        }
        interval--;
        if (interval <= 0) {
            lastFrame = currentFrame;
            currentFrame += windLevel == 5 ? 2 : 1;
            interval = getInterval(windLevel);
        }
        return new PinwheelContext(currentFrame, lastFrame, interval);
    }

    public int lerpFrame(float partialTick) {
        return Mth.lerpInt(partialTick, this.lastFrame, this.currentFrame);
    }
}
