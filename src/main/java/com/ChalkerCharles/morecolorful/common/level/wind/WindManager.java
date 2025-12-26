package com.ChalkerCharles.morecolorful.common.level.wind;

import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILevelRendererExtension;
import com.ChalkerCharles.morecolorful.network.packets.WindPacket;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;
import org.joml.Vector2f;

public abstract class WindManager {
    private static final float INV_MAX_WIND_SPEED_SQ = 0.0016326531F;
    public final Vector2f globalWindSpeed = new Vector2f();
    public final Vector2f windDirection = new Vector2f(1, 0);
    private final Vector2f cachedDirection = new Vector2f(1, 0);
    public Direction nearestDirection = Direction.EAST;
    public boolean isCalm = true;

    public void setWindSpeed(float x, float z) {
        boolean isCalmNow = x == 0 && z == 0;
        boolean isCalmPrev = this.isCalm;
        this.globalWindSpeed.set(x, z);
        this.isCalm = isCalmNow;
        if (isCalmNow) {
            this.windDirection.set(1, 0);
        } else {
            float f = 1.0F / Maths.length(x, z);
            this.windDirection.set(x * f, z * f);
        }
        if (isCalmPrev != isCalmNow) {
            this.setCache();
        } else {
            this.checkCache();
        }
    }

    public void initWindSpeed(float x, float z) {
        this.globalWindSpeed.set(x, z);
        this.isCalm = x == 0 && z == 0;
        if (this.isCalm) {
            this.windDirection.set(1, 0);
        } else {
            float f = 1.0F / Maths.length(x, z);
            this.windDirection.set(x * f, z * f);
        }
        this.cachedDirection.set(this.windDirection);
        this.nearestDirection = this.getNearestDirection();
    }

    private Direction getNearestDirection() {
        float x = this.windDirection.x, z = this.windDirection.y;
        if (Mth.abs(x) > Mth.abs(z)) {
            return x > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return z > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    private void checkCache() {
        if (this.isCalm) return;
        float dot = this.cachedDirection.dot(this.windDirection);
        if (dot < 0.9945218F) { // cos(6°)
            this.setCache();
        }
    }

    protected void setCache() {
        this.cachedDirection.set(this.windDirection);
        this.nearestDirection = this.getNearestDirection();
    }

    public void tick() {
    }

    public void serialize(CompoundTag nbt) {
    }

    public void deserialize(CompoundTag nbt) {
    }

    @OnlyIn(Dist.CLIENT)
    public static class Client extends WindManager {
        private final ClientLevel level;

        public Client(ClientLevel level) {
            this.level = level;
        }

        @Override
        protected void setCache() {
            ILevelRendererExtension.clearWindCache();
            super.setCache();
        }

        @Override
        public void tick() {
            if (this.isCalm) return;
            if (RenderUtils.windParticles) {
                if (this.shouldSpawnParticle()) {
                    Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                    double dx = globalWindSpeed.x, dz = globalWindSpeed.y;
                    double d0 = (level.random.nextDouble() - 0.5) * 32;
                    double d1 = (level.random.nextDouble() - 0.5) * 16;
                    double x = pos.x - dx - d0 * windDirection.y;
                    double y = pos.y + d1;
                    double z = pos.z - dz + d0 * windDirection.x;
                    if (WeatherUtils.canApplyWind(this.level, x, y, z)) {
                        level.addParticle(ModParticles.WIND_GLOBAL.get(), x, y, z, dx * 0.0625, 0, dz * 0.0625);
                    }
                }
            }
            if (RenderUtils.windSounds) {
                float windSpeed = this.globalWindSpeed.length();
                if (this.shouldPlayWindSound(windSpeed)) {
                    Vec3 pos = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
                    if (this.isInWind(pos, windSpeed)) {
                        float volume = windSpeed * windSpeed * INV_MAX_WIND_SPEED_SQ;
                        float pitch = volume * 0.5F + 0.5F;
                        level.playLocalSound(pos.x, pos.y, pos.z, getWindSound(windSpeed), SoundSource.WEATHER, volume, pitch, false);
                    }
                }
            }
        }

        private boolean shouldSpawnParticle() {
            float windSpeed = this.globalWindSpeed.length();
            if (windSpeed < 8) return false;
            int chance = Math.max(1, 100 - Mth.floor(windSpeed * 4));
            return this.level.random.nextInt(chance) <= 1;
        }

        private boolean shouldPlayWindSound(float windSpeed) {
            int chance = Math.max(1, 100 - Mth.floor(windSpeed * 4)) + 50;
            return this.level.random.nextInt(chance) == 0;
        }

        private boolean isInWind(Vec3 pos, float windSpeed) {
            double x = pos.x, y = pos.y, z = pos.z;
            if (WeatherUtils.canApplyWind(this.level, x, y, z)) return true;
            double f = windSpeed * 0.5;
            x += Mth.nextDouble(level.random, -f, f);
            y += Mth.nextDouble(level.random, -f, f);
            z += Mth.nextDouble(level.random, -f, f);
            return WeatherUtils.canApplyWind(this.level, x, y, z);
        }

        private static SoundEvent getWindSound(float windSpeed) {
            if (windSpeed < 8) {
                return ModSounds.WEATHER_BREEZE.get();
            } else if (windSpeed < 16) {
                return ModSounds.WEATHER_WIND.get();
            } else {
                return ModSounds.WEATHER_GALE.get();
            }
        }
    }

    public static class Server extends WindManager {
        private final ServerLevel level;
        private final RandomSource random;
        private float windSpeedXTarget;
        private float windSpeedZTarget;
        private int gustCounter;
        private int extremeGustCounter;
        private boolean isWindFrozen = false;

        public Server(ServerLevel level) {
            this.level = level;
            this.random = level.getRandom();
            this.gustCounter = Mth.nextInt(random, 0, 900); // 0-45 seconds
            this.extremeGustCounter = Mth.nextInt(random, 10, 30);
        }

        public void setWindSpeedByCommand(float x, float z) {
            this.setWindSpeed(x, z);
            this.windSpeedXTarget = x;
            this.windSpeedZTarget = z;
            PacketDistributor.sendToPlayersInDimension(this.level, new WindPacket(x, z));
        }

        public void resetWindSpeed() {
            this.setWindSpeed(0, 0);
            this.windSpeedXTarget = 0;
            this.windSpeedZTarget = 0;
            this.gustCounter = 0;
            this.extremeGustCounter = Mth.nextInt(random, 10, 30);
            PacketDistributor.sendToPlayersInDimension(this.level, new WindPacket(0, 0));
        }

        public void setWindFrozen(boolean frozen) {
            this.isWindFrozen = frozen;
        }

        @Override
        public void tick() {
            if (isWindFrozen) return;
            float windSpeedXCurrent = globalWindSpeed.x;
            float windSpeedZCurrent = globalWindSpeed.y;
            float correctedXTarget = windSpeedXTarget * (1 + level.getRainLevel(1.0F) * 0.55556F);
            float correctedZTarget = windSpeedZTarget * (1 + level.getRainLevel(1.0F) * 0.55556F);
            float deltaX = 0.001F + Math.abs(correctedXTarget / 50 - windSpeedXCurrent / 50) * 0.0045F;
            float deltaZ = 0.001F + Math.abs(correctedZTarget / 50 - windSpeedZCurrent / 50) * 0.0045F;
            windSpeedXCurrent = approachTo(windSpeedXCurrent, correctedXTarget, deltaX);
            windSpeedZCurrent = approachTo(windSpeedZCurrent, correctedZTarget, deltaZ);
            windSpeedXCurrent = clampWind(windSpeedXCurrent);
            windSpeedZCurrent = clampWind(windSpeedZCurrent);
            this.setWindSpeed(windSpeedXCurrent, windSpeedZCurrent);
            PacketDistributor.sendToPlayersInDimension(this.level, new WindPacket(windSpeedXCurrent, windSpeedZCurrent));
            gustCounter--;
            if (gustCounter <= 0) {
                int flagX = windSpeedXTarget < 0 ? -1 : 1;
                int flagZ = windSpeedZTarget < 0 ? -1 : 1;
                windSpeedXTarget += getTargetSpeed();
                windSpeedZTarget += getTargetSpeed();
                extremeGustCounter--;
                gustCounter = Mth.nextInt(random, 300, 900); // 15-45 seconds
                if (extremeGustCounter <= 0) {
                    float i = random.nextFloat();
                    if (i < 0.2166F) {
                        windSpeedXTarget = 0;
                        windSpeedZTarget = 0;
                        gustCounter = Mth.nextInt(random, 4800, 14400); // 4-12 minutes
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
                    float j = Maths.length(windSpeedXTarget, windSpeedZTarget);
                    if (j < 6.5F) {
                        extremeGustCounter = Mth.nextInt(random, 10, 30);
                    } else if (j < 11F) {
                        extremeGustCounter = Mth.nextInt(random, 15, 40);
                    } else if (j < 15.5F) {
                        extremeGustCounter = Mth.nextInt(random, 20, 50);
                    } else {
                        extremeGustCounter = Mth.nextInt(random, 25, 60);
                    }
                }
                if (random.nextInt(3) != 0 && flagX * windSpeedXTarget < 0)
                    windSpeedXTarget *= -1;
                if (random.nextInt(3) != 0 && flagZ * windSpeedZTarget < 0)
                    windSpeedZTarget *= -1;
            }
            windSpeedXTarget = clampWind(windSpeedXTarget);
            windSpeedZTarget = clampWind(windSpeedZTarget);
        }

        private float getTargetSpeed() {
            return switch (random.nextInt(8)) {
                case 0, 1 -> Mth.nextFloat(random, -0.5F, 0.5F);
                case 2, 3, 4 -> Mth.nextFloat(random, -1, 1);
                default -> Mth.nextFloat(random, -2, 2);
            };
        }

        private static float approachTo(float value, float target, float delta) {
            return value < target ? Math.min(value + delta, target) : Math.max(value - delta, target);
        }

        private static float clampWind(float value) {
            return Mth.clamp(value, -17.5F, 17.5F);
        }

        @Override
        public void serialize(CompoundTag nbt) {
            if (WeatherUtils.isWindless(this.level)) return;
            nbt.putFloat("windSpeedX", this.globalWindSpeed.x);
            nbt.putFloat("windSpeedZ", this.globalWindSpeed.y);
            nbt.putFloat("windSpeedXTarget", this.windSpeedXTarget);
            nbt.putFloat("windSpeedZTarget", this.windSpeedZTarget);
            nbt.putInt("windCounter", this.gustCounter);
            nbt.putInt("extremeWindCounter", this.extremeGustCounter);
            nbt.putBoolean("isWindFrozen", this.isWindFrozen);
        }

        @Override
        public void deserialize(CompoundTag nbt) {
            if (WeatherUtils.isWindless(this.level)) return;
            float x = nbt.getFloat("windSpeedX");
            float z = nbt.getFloat("windSpeedZ");
            this.initWindSpeed(x, z);
            this.windSpeedXTarget = nbt.getFloat("windSpeedXTarget");
            this.windSpeedZTarget = nbt.getFloat("windSpeedZTarget");
            this.gustCounter = nbt.getInt("windCounter");
            this.extremeGustCounter = nbt.getInt("extremeWindCounter");
            this.isWindFrozen = nbt.getBoolean("isWindFrozen");
        }
    }
}
