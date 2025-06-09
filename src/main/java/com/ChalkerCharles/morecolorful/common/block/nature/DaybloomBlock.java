package com.ChalkerCharles.morecolorful.common.block.nature;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public class DaybloomBlock extends FlowerBlock {
    private final DaybloomBlock.Type type;
    public DaybloomBlock(DaybloomBlock.Type type, Properties pProperties) {
        super(type.effect, type.effectDuration, pProperties);
        this.type = type;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        tryChangingState(pState, pLevel, pPos);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        tryChangingState(pState, pLevel, pPos);
    }

    private void tryChangingState(BlockState state, ServerLevel level, BlockPos pos) {
        if (level.dimensionType().natural() && level.isDay() != this.type.open) {
            DaybloomBlock.Type type = this.type.transform();
            level.setBlock(pos, type.state(), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }

    public enum Type {
        OPEN(true, MobEffects.ABSORPTION, 12.0F),
        CLOSED(false, MobEffects.ABSORPTION, 8.0F);

        final boolean open;
        final Holder<MobEffect> effect;
        final float effectDuration;

        Type(boolean open, Holder<MobEffect> effect, float effectDuration) {
            this.open = open;
            this.effect = effect;
            this.effectDuration = effectDuration;
        }

        public Block block() {
            return this.open ? ModBlocks.OPEN_DAYBLOOM.get() : ModBlocks.CLOSED_DAYBLOOM.get();
        }
        public BlockState state() {
            return this.block().defaultBlockState();
        }
        public Type transform() {
            return fromBoolean(!this.open);
        }
        public static Type fromBoolean(boolean open) {
            return open ? OPEN : CLOSED;
        }
    }
}
