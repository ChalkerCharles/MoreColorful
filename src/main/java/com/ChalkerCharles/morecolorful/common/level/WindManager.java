package com.ChalkerCharles.morecolorful.common.level;

import com.ChalkerCharles.morecolorful.network.packets.WindPacket;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2f;

public class WindManager {
    private final Level level;
    private final RandomSource random;
    private final Vector2f globalWindSpeed = new Vector2f();
    private float windSpeedXTarget;
    private float windSpeedZTarget;
    private int windCounter;
    private int extremeWindCounter;

    public WindManager(Level level) {
        this.level = level;
        this.random = level.getRandom();
        this.windCounter = Mth.nextInt(random, 0, 900); // 0-45 seconds
        this.extremeWindCounter = Mth.nextInt(random, 10, 30);
    }

    private ServerLevel serverLevel() {
        return (ServerLevel) this.level;
    }

    public Vector2f getGlobalWindSpeed() {
        return this.globalWindSpeed;
    }

    public void setGlobalWindSpeed(float x, float z) {
        this.globalWindSpeed.x = x;
        this.globalWindSpeed.y = z;
    }

    public void setWindSpeedByCommand(float x, float z) {
        this.setGlobalWindSpeed(x, z);
        this.windSpeedXTarget = x;
        this.windSpeedZTarget = z;
        PacketDistributor.sendToPlayersInDimension(serverLevel(), new WindPacket(x, z));
    }

    public void resetWindSpeed() {
        this.setGlobalWindSpeed(0, 0);
        this.windSpeedXTarget = 0;
        this.windSpeedZTarget = 0;
        this.windCounter = 0;
        this.extremeWindCounter = Mth.nextInt(random, 10, 30);
        PacketDistributor.sendToPlayersInDimension(serverLevel(), new WindPacket(0, 0));
    }

    public void update() {
        float windSpeedXCurrent = globalWindSpeed.x();
        float windSpeedZCurrent = globalWindSpeed.y();
        float correctedXTarget = windSpeedXTarget * (1 + level.getRainLevel(1.0F) * 0.55556F);
        float correctedZTarget = windSpeedZTarget * (1 + level.getRainLevel(1.0F) * 0.55556F);
        float deltaX = 0.001F + Math.abs(correctedXTarget / 50 - windSpeedXCurrent / 50) * 0.0045F;
        float deltaZ = 0.001F + Math.abs(correctedZTarget / 50 - windSpeedZCurrent / 50) * 0.0045F;
        windSpeedXCurrent = windSpeedXCurrent < correctedXTarget
                ? Math.min(windSpeedXCurrent + deltaX, correctedXTarget)
                : Math.max(windSpeedXCurrent - deltaX, correctedXTarget);
        windSpeedZCurrent = windSpeedZCurrent < correctedZTarget
                ? Math.min(windSpeedZCurrent + deltaZ, correctedZTarget)
                : Math.max(windSpeedZCurrent - deltaZ, correctedZTarget);
        this.setGlobalWindSpeed(windSpeedXCurrent, windSpeedZCurrent);
        PacketDistributor.sendToPlayersInDimension(serverLevel(), new WindPacket(windSpeedXCurrent, windSpeedZCurrent));
        windCounter--;
        if (windCounter <= 0) {
            int flagX = windSpeedXTarget < 0 ? -1 : 1;
            int flagZ = windSpeedZTarget < 0 ? -1 : 1;
            windSpeedXTarget += getTargetSpeed();
            windSpeedZTarget += getTargetSpeed();
            extremeWindCounter--;
            windCounter = Mth.nextInt(random, 300, 900); // 15-45 seconds
            if (extremeWindCounter <= 0) {
                float i = random.nextFloat();
                if (i < 0.2166F) {
                    windSpeedXTarget = 0;
                    windSpeedZTarget = 0;
                    windCounter = Mth.nextInt(random, 4800, 14400); // 4-12 minutes
                } else if (i < 0.4332F) {
                    windSpeedXTarget += Mth.nextFloat(random, -4, 4);
                    windSpeedZTarget += Mth.nextFloat(random, -4, 4);
                } else if (i < 0.8015F) {
                    windSpeedXTarget += Mth.nextFloat(random, -8, 8);
                    windSpeedZTarget += Mth.nextFloat(random, -8, 8);
                } else {
                    windSpeedXTarget += Mth.nextFloat(random, -16, 16);
                    windSpeedZTarget += Mth.nextFloat(random, -16, 16);
                }
                double j = Math.hypot(windSpeedXTarget, windSpeedZTarget);
                if (j < 6.5) {
                    extremeWindCounter = Mth.nextInt(random, 10, 30);
                } else if (j < 11) {
                    extremeWindCounter = Mth.nextInt(random, 15, 40);
                } else if (j < 15.5) {
                    extremeWindCounter = Mth.nextInt(random, 20, 50);
                } else {
                    extremeWindCounter = Mth.nextInt(random, 25, 60);
                }
            }
            if (random.nextInt(3) != 0 && flagX * windSpeedXTarget < 0)
                windSpeedXTarget *= -1;
            if (random.nextInt(3) != 0 && flagZ * windSpeedZTarget < 0)
                windSpeedZTarget *= -1;
        }
        windSpeedXTarget = Mth.clamp(windSpeedXTarget, -17.5F, 17.5F);
        windSpeedZTarget = Mth.clamp(windSpeedZTarget, -17.5F, 17.5F);
    }

    private float getTargetSpeed() {
        return switch (random.nextInt(8)) {
            case 0, 1 -> Mth.nextFloat(random, -0.5F, 0.5F);
            case 2, 3, 4 -> Mth.nextFloat(random, -1, 1);
            default -> Mth.nextFloat(random, -2, 2);
        };
    }

    public void serialize(CompoundTag nbt) {
        if (WeatherUtils.isWindless(level)) return;
        nbt.putFloat("windSpeedX", this.globalWindSpeed.x);
        nbt.putFloat("windSpeedZ", this.globalWindSpeed.y);
        nbt.putFloat("windSpeedXTarget", this.windSpeedXTarget);
        nbt.putFloat("windSpeedZTarget", this.windSpeedZTarget);
        nbt.putInt("windCounter", this.windCounter);
        nbt.putInt("extremeWindCounter", this.extremeWindCounter);
    }

    public void deserialize(CompoundTag nbt) {
        if (WeatherUtils.isWindless(level)) return;
        this.globalWindSpeed.x = nbt.getFloat("windSpeedX");
        this.globalWindSpeed.y = nbt.getFloat("windSpeedZ");
        this.windSpeedXTarget = nbt.getFloat("windSpeedXTarget");
        this.windSpeedZTarget = nbt.getFloat("windSpeedZTarget");
        this.windCounter = nbt.getInt("windCounter");
        this.extremeWindCounter = nbt.getInt("extremeWindCounter");
    }
}
