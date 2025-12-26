package com.ChalkerCharles.morecolorful.common.block.entity;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.block.ModBlockEntities;
import com.ChalkerCharles.morecolorful.common.block.utility.FanBlock;
import com.ChalkerCharles.morecolorful.common.level.wind.LineWindZone;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class FanBlockEntity extends BlockEntity {
    private LineWindZone windZone;
    private boolean addedToWorld;
    private int soundTime = 1;

    public FanBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FAN_BLOCK.get(), pos, blockState);
        if (blockState.getValue(FanBlock.POWERED)) {
            Direction direction = blockState.getValue(FanBlock.FACING);
            this.windZone = createWindZone(pos, direction);
        }
    }

    private static LineWindZone createWindZone(BlockPos pos, Direction direction) {
        return new LineWindZone(pos.relative(direction), direction, 16);
    }

    public static void tick(Level level, BlockPos pos, BlockState ignore, FanBlockEntity blockEntity) {
        LineWindZone zone = blockEntity.windZone;
        if (zone == null) return;
        if (!blockEntity.addedToWorld) {
            LevelSavedData.addWindZone(level, zone);
            blockEntity.addedToWorld = true;
        }
        long[] oldSections = zone.sections;
        zone.checkLength(level);
        long[] newSections = zone.sections;
        if (!Arrays.equals(oldSections, newSections)) {
            LevelSavedData.updateWindZoneSections(level, zone, oldSections, newSections);
        }
        if (level.isClientSide) {
            if (ClientWrapper.windParticles() && level.random.nextBoolean()) {
                zone.spawnWindParticle(level, pos);
            }
            if (level.random.nextInt(blockEntity.soundTime) == 0) {
                blockEntity.soundTime = 32;
                level.playLocalSound(pos, ModSounds.FAN_BLOCK_WHIR.get(), SoundSource.BLOCKS, 0.25F, 1.0F, false);
            }
            blockEntity.soundTime--;
        }
    }

    @Override
    public void setRemoved() {
        if (this.windZone != null) {
            this.windZone.remove();
        }
        super.setRemoved();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setBlockState(BlockState pBlockState) {
        super.setBlockState(pBlockState);
        if (pBlockState.getValue(FanBlock.POWERED)) {
            Direction direction = pBlockState.getValue(FanBlock.FACING);
            if (this.windZone != null) {
                if (this.windZone.direction != direction) {
                    this.windZone.remove();
                    this.windZone = createWindZone(this.worldPosition, direction);
                    this.addedToWorld = false;
                }
            } else {
                this.windZone = createWindZone(this.worldPosition, direction);
                this.addedToWorld = false;
            }
        } else if (this.windZone != null) {
            this.windZone.remove();
            this.windZone = null;
            this.addedToWorld = false;
        }
    }
}
