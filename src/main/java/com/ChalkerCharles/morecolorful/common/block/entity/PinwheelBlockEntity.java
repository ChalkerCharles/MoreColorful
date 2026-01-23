package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.natural.WindFlowerBlock;
import com.ChalkerCharles.morecolorful.common.block.ornamental.PinwheelBlock;
import com.ChalkerCharles.morecolorful.common.item.component.PinwheelContext;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.joml.Vector3f;

public class PinwheelBlockEntity extends BlockEntity {
    public final int frames;
    private int currentFrame;
    private int lastFrame;
    private int interval = -1;

    public PinwheelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.PINWHEEL.get(), pPos, pBlockState);
        this.frames = PinwheelBlock.isMulticolor(pBlockState) ? 16 : 4;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PinwheelBlockEntity blockEntity) {
        if (!Config.windSystem) return;
        Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
        if (wind != null) {
            float angle = RotationSegment.convertToDegrees(state.getValue(PinwheelBlock.ROTATION));
            Vector3f facing = new Vector3f(-Mth.sin(angle * Mth.DEG_TO_RAD), 0, Mth.cos(angle * Mth.DEG_TO_RAD));
            int windLevel = WindFlowerBlock.getWindLevel(-wind.dot(facing));
            blockEntity.tick(windLevel);
        }
    }

    private void tick(int windLevel) {
        if (windLevel == 0) {
            this.lastFrame = this.currentFrame;
            return;
        }
        if (this.interval == -1) {
            this.interval = PinwheelContext.getInterval(windLevel);
        }
        this.interval--;
        if (this.interval <= 0) {
            this.lastFrame = this.currentFrame;
            this.currentFrame += windLevel == 5 ? 2 : 1;
            this.interval = PinwheelContext.getInterval(windLevel);
        }
    }

    public int lerpFrame(float partialTick) {
        return Mth.lerpInt(partialTick, this.lastFrame, this.currentFrame);
    }
}
