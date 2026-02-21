package com.ChalkerCharles.morecolorful.common.attachment;

import com.ChalkerCharles.morecolorful.common.level.wind.WindManager;
import com.ChalkerCharles.morecolorful.common.level.wind.WindZoneManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class ServerLevelData extends LevelSavedData {
    private final ServerLevel level;
    private final WindManager.Server windManager;
    private final WindZoneManager windZoneManager = new WindZoneManager();

    public ServerLevelData(ServerLevel level) {
        this.level = level;
        this.windManager = new WindManager.Server(level);
    }

    private static ServerLevelData get(ServerLevel level) {
        return (ServerLevelData) LevelSavedData.get(level);
    }

    @Override
    protected Level level() {
        return this.level;
    }

    @Override
    protected WindManager.Server windManager() {
        return this.windManager;
    }

    @Override
    protected WindZoneManager windZoneManager() {
        return this.windZoneManager;
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
