package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.common.level.wind.WindManager;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZoneManager;
import net.minecraft.server.level.ServerLevel;

public class ServerLevelData extends LevelSavedData {
    private final WindManager.Server windManager;

    public ServerLevelData(ServerLevel level) {
        super(new WindZoneManager());
        this.windManager = new WindManager.Server(level);
    }

    private static ServerLevelData get(ServerLevel level) {
        return (ServerLevelData) LevelSavedData.get(level);
    }

    @Override
    protected WindManager.Server windManager() {
        return this.windManager;
    }

    public static void setWindSpeedByCommand(ServerLevel level, float x, float z) {
        get(level).windManager.setWindSpeedByCommand(x, z);
    }

    public static void resetWindSpeed(ServerLevel level) {
        get(level).windManager.resetWindSpeed();
    }

    public static void freezeWindSpeed(ServerLevel level, boolean freeze) {
        get(level).windManager.setWindFrozen(freeze);
    }
}
