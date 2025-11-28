package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.common.level.wind.WindManager;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZoneManager;
import net.minecraft.client.multiplayer.ClientLevel;

import java.util.ArrayDeque;
import java.util.Deque;

public class ClientLevelData extends LevelSavedData {
    private final WindManager.Client windManager = new WindManager.Client();
    private final Deque<Runnable> thermalUpdateQueue = new ArrayDeque<>();
    private final Deque<Runnable> ventUpdateQueue = new ArrayDeque<>();

    public ClientLevelData() {
        super(new WindZoneManager.Client());
    }

    private static ClientLevelData get(ClientLevel level) {
        return (ClientLevelData) LevelSavedData.get(level);
    }

    @Override
    protected WindManager.Client windManager() {
        return this.windManager;
    }

    private void queueThermalUpdate(Runnable pTask) {
        this.thermalUpdateQueue.add(pTask);
    }

    public static void queueThermalUpdate(ClientLevel level, Runnable pTask) {
        get(level).queueThermalUpdate(pTask);
    }

    private void pollThermalUpdates() {
        int i = this.thermalUpdateQueue.size();
        int j = i < 1000 ? Math.max(10, i / 10) : i;

        for (int k = 0; k < j; k++) {
            Runnable runnable = this.thermalUpdateQueue.poll();
            if (runnable == null) {
                break;
            }

            runnable.run();
        }
    }

    public static void pollThermalUpdates(ClientLevel level) {
        get(level).pollThermalUpdates();
    }

    private void queueVentUpdate(Runnable pTask) {
        this.ventUpdateQueue.add(pTask);
    }

    public static void queueVentUpdate(ClientLevel level, Runnable pTask) {
        get(level).queueVentUpdate(pTask);
    }

    private void pollVentUpdates() {
        int i = this.ventUpdateQueue.size();
        int j = i < 1000 ? Math.max(10, i / 10) : i;

        for (int k = 0; k < j; k++) {
            Runnable runnable = this.ventUpdateQueue.poll();
            if (runnable == null) {
                break;
            }

            runnable.run();
        }
    }

    public static void pollVentUpdates(ClientLevel level) {
        get(level).pollVentUpdates();
    }
}
