package com.ChalkerCharles.morecolorful.common.block.natural;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class WindFlowerBlock extends FlowerBlock {
    public static final IntegerProperty WIND_LEVEL = ModBlockStateProperties.WIND_LEVEL;

    public WindFlowerBlock(Properties properties) {
        super(MobEffects.WIND_CHARGED, 12, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WIND_LEVEL, 0));
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        tryChangingState(pState, pLevel, pPos);
        super.randomTick(pState, pLevel, pPos, pRandom);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        BlockState state = super.getStateForPlacement(pContext);
        if (state == null) return null;
        BlockPos pos = pContext.getClickedPos();
        Level level = pContext.getLevel();
        return setWindLevel(state, level, pos);
    }

    @Override
    protected BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        BlockState state = super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
        if (!state.isAir() && pLevel instanceof Level level) {
            return setWindLevel(state, level, pCurrentPos);
        }
        return state;
    }

    public static BlockState setWindLevel(BlockState state, Level level, BlockPos pos) {
        if (Config.WIND_SYSTEM.isTrue()) {
            int windLevel = 0;
            Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
            if (wind != null) {
                windLevel = getWindLevel(wind.length());
            }
            return state.setValue(WIND_LEVEL, windLevel);
        }
        return state;
    }

    public static void tryChangingState(BlockState state, ServerLevel level, BlockPos pos) {
        if (Config.WIND_SYSTEM.isTrue()) {
            int currentLevel = state.getValue(WIND_LEVEL);
            int windLevel = 0;
            Vector3f wind = WeatherUtils.getEffectiveWindSpeedAt(level, pos);
            if (wind != null) {
                windLevel = getWindLevel(wind.length());
            }
            if (currentLevel != windLevel) {
                level.setBlock(pos, state.setValue(WIND_LEVEL, windLevel), 3);
            }
        }
    }

    private static int getWindLevel(float windSpeed) {
        if (windSpeed < 1.5) return 0;
        else if (windSpeed < 4) return 1;
        else if (windSpeed < 7.5) return 2;
        else if (windSpeed < 12) return 3;
        else if (windSpeed < 17.5) return 4;
        else return 5;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(WIND_LEVEL);
    }
}
