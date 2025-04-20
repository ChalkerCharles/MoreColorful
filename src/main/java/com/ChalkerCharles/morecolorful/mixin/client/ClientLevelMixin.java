package com.ChalkerCharles.morecolorful.mixin.client;

import com.ChalkerCharles.morecolorful.util.mixin.ILevelExtension;
import com.google.common.collect.Queues;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Deque;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin implements ILevelExtension {
    @Unique
    private final Deque<Runnable> moreColorful$thermalUpdateQueue = Queues.newArrayDeque();

    @Unique
    @Override
    public void moreColorful$queueThermalUpdate(Runnable pTask) {
        this.moreColorful$thermalUpdateQueue.add(pTask);
    }

    @Unique
    @Override
    public void moreColorful$pollLightUpdates() {
        int i = this.moreColorful$thermalUpdateQueue.size();
        int j = i < 1000 ? Math.max(10, i / 10) : i;

        for (int k = 0; k < j; k++) {
            Runnable runnable = this.moreColorful$thermalUpdateQueue.poll();
            if (runnable == null) {
                break;
            }

            runnable.run();
        }
    }
}
