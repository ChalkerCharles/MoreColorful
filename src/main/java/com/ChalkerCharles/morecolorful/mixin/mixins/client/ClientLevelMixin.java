package com.ChalkerCharles.morecolorful.mixin.mixins.client;

import com.ChalkerCharles.morecolorful.mixin.mixins.LevelMixin;
import com.google.common.collect.Queues;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.Deque;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends LevelMixin {
    @Unique
    private final Deque<Runnable> moreColorful$thermalUpdateQueue = Queues.newArrayDeque();

    @Override
    public void moreColorful$queueThermalUpdate(Runnable pTask) {
        this.moreColorful$thermalUpdateQueue.add(pTask);
    }

    @Override
    public void moreColorful$pollThermalUpdates() {
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
