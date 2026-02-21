package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PennantBlock;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Vector3f;

public class PennantBlockEntity extends BlockEntity {
    private static final int[] PATTERN_1 = new int[] {
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
            2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };
    private static final int[] PATTERN_2 = new int[] {
            1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
            3, 3, 3, 3, 3, 3, 2, 2, 2, 2, 2, 2,
    };
    private static final int[] PATTERN_3 = new int[] {
            2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4,
            5, 5, 5, 5, 4, 4, 4, 4, 3, 3, 3, 3
    };
    private static final int[] PATTERN_4 = new int[] {4, 4, 5, 5, 6, 6, 5, 5};
    private static final int[] PATTERN_5 = new int[] {5, 6, 7, 6};
    private float rot;
    private float oRot;
    private float dRot;
    public int frame;
    private int ticks;

    public PennantBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PENNANT.get(), pPos, pBlockState);
        this.rot = RotationSegment.convertToDegrees(pBlockState.getValue(PennantBlock.ROTATION));
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PennantBlockEntity blockEntity) {
        if (!Config.windSystem) return;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
        float speed = blockEntity.rotate(wind);
        int rotation = state.getValue(PennantBlock.ROTATION);
        int rotation1 = RotationSegment.convertToSegment(blockEntity.rot);
        if (rotation != rotation1) {
            level.setBlock(pos, state.setValue(PennantBlock.ROTATION, rotation1), 3);
        }
        int windLevel = WindFlowerBlock.getWindLevel(speed);
        blockEntity.nextFrame(windLevel);
    }

    private float rotate(Vector3f wind) {
        this.oRot = this.rot;
        float speed = 0;
        if (wind != null) {
            speed = Maths.length(wind.x, wind.z);
            float tRot = Maths.vectorToAngle(wind.x, wind.z);
            this.dRot += Mth.degreesDifference(rot, tRot) * speed * 0.01F;
        }
        this.dRot *= 0.5F;
        this.rot = Mth.wrapDegrees(rot + dRot);
        return speed;
    }

    private void nextFrame(int windLevel) {
        int frame;
        if (windLevel == 0) {
            frame = 0;
        } else {
            int[] pattern = switch (windLevel) {
                case 1 -> PATTERN_1;
                case 2 -> PATTERN_2;
                case 3 -> PATTERN_3;
                case 4 -> PATTERN_4;
                default -> PATTERN_5;
            };
            int i = this.ticks % pattern.length;
            frame = pattern[i];
        }
        int d = this.frame - frame;
        if (d != 0) {
            this.frame += d > 0 ? -1 : 1;
        }
        this.frame = Mth.clamp(this.frame, 0, 7);
        this.ticks++;
    }

    public float getRot(float partialTick) {
        return Mth.rotLerp(partialTick, this.oRot, this.rot);
    }
}
