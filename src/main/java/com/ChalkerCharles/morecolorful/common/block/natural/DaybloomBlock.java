package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class DaybloomBlock extends FlowerBlock {
    private final DaybloomBlock.Type type;

    public DaybloomBlock(DaybloomBlock.Type type, Properties pProperties) {
        super(MobEffects.ABSORPTION, type.effectDuration, pProperties);
        this.type = type;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.tryChangingState(pState, pLevel, pPos);
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    private void tryChangingState(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.dimensionType().natural() && level.isDay() != this.type.open) {
            DaybloomBlock.Type type = this.type.transform();
            level.setBlock(pos, type.state(), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }

    public enum Type {
        OPEN(true, 12.0F),
        CLOSED(false, 8.0F);

        private final boolean open;
        private final float effectDuration;

        Type(boolean open, float effectDuration) {
            this.open = open;
            this.effectDuration = effectDuration;
        }

        public Block block() {
            return this.open ? ModBlocks.OPEN_DAYBLOOM.get() : ModBlocks.CLOSED_DAYBLOOM.get();
        }

        public BlockState state() {
            return this.block().defaultBlockState();
        }

        public Type transform() {
            return this.open ? CLOSED : OPEN;
        }
    }
}
