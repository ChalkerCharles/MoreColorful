package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.utility.WeatherVaneBlock;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Vector3f;

public class WeatherVaneBlockEntity extends BlockEntity {
    private float rot;
    private float oRot;
    private float dRot;
    private int soundTime = 1;

    public WeatherVaneBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WEATHER_VANE.get(), pPos, pBlockState);
        this.rot = RotationSegment.convertToDegrees(pBlockState.getValue(WeatherVaneBlock.ROTATION));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, WeatherVaneBlockEntity blockEntity) {
        if (!Config.windSystem) return;
        float speed = blockEntity.rotate(level, pos);
        int power = state.getValue(WeatherVaneBlock.POWER);
        int power1 = getSignal(speed);
        int rotation = state.getValue(WeatherVaneBlock.ROTATION);
        int rotation1 = RotationSegment.convertToSegment(blockEntity.rot);
        BlockState state1 = state;
        if (power != power1) {
            state1 = state1.setValue(WeatherVaneBlock.POWER, power1);
        }
        if (rotation != rotation1) {
            state1 = state1.setValue(WeatherVaneBlock.ROTATION, rotation1);
        }
        if (state1 != state) {
            level.setBlock(pos, state1, 3);
        }
        if (level.isClientSide) {
            blockEntity.playSound(level, pos);
        }
    }

    private float rotate(Level level, BlockPos pos) {
        this.oRot = this.rot;
        float windSpeed = 0;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
        if (wind != null) {
            float speed = Maths.length(wind.x, wind.z);
            windSpeed = wind.length();
            float tRot = Maths.vectorToAngle(wind.x, wind.z);
            this.dRot += Mth.degreesDifference(rot, tRot) * speed * 0.02F;
        }
        this.dRot *= 0.9F;
        this.rot = Mth.wrapDegrees(rot + dRot);
        return windSpeed;
    }

    private void playSound(Level level, BlockPos pos) {
        if (Math.abs(this.dRot) > 6) {
            if (level.random.nextInt(this.soundTime) == 0) {
                this.soundTime = 32;
                float pitch = level.random.nextFloat() * 0.4F + 0.8F;
                level.playLocalSound(pos, ModSounds.WEATHER_VANE_SWAY.get(), SoundSource.BLOCKS, 0.5F, pitch, false);
            }
            this.soundTime--;
        }
    }

    public float getRot(float partialTick) {
        return Mth.rotLerp(partialTick, this.oRot, this.rot);
    }

    private static int getSignal(float windSpeed) {
        if (windSpeed == 0) return 0;
        else if (windSpeed < 0.2) return 1;
        else if (windSpeed < 0.5) return 2;
        else if (windSpeed < 1) return 3;
        else if (windSpeed < 1.7) return 4;
        else if (windSpeed < 2.8) return 5;
        else if (windSpeed < 4.1) return 6;
        else if (windSpeed < 5.6) return 7;
        else if (windSpeed < 7.3) return 8;
        else if (windSpeed < 9.2) return 9;
        else if (windSpeed < 11.5) return 10;
        else if (windSpeed < 14.2) return 11;
        else if (windSpeed < 17.1) return 12;
        else if (windSpeed < 20.2) return 13;
        else if (windSpeed < 23.9) return 14;
        else return 15;
    }
}
