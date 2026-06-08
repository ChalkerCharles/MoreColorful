package com.ChalkerCharles.morecolorful.common.entity.spawn;

import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.entity.animal.Dragonfly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.CustomSpawner;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.EventHooks;

public class DragonflySpawner implements CustomSpawner {
    private int nextTick;

    @Override
    public int tick(ServerLevel pLevel, boolean pSpawnEnemies, boolean pSpawnFriendlies) {
        if (!pSpawnFriendlies) return 0;
        if (isGoingToRain(pLevel)
                && pLevel.isDay()
                && pLevel.dimensionType().natural()
                && Dragonfly.properWeatherForSpawn(pLevel)) {
            RandomSource random = pLevel.random;
            this.nextTick--;
            if (this.nextTick > 0) {
                return 0;
            } else {
                this.nextTick = this.nextTick + 600 + random.nextInt(600);
            }
            int i = 0;
            for (ServerPlayer player : pLevel.players()) {
                if (player.isSpectator()) continue;
                BlockPos pos0 = player.blockPosition();
                if (cannotSpawnDragonflies(pLevel.getBiome(pos0))) continue;
                DifficultyInstance difficulty = pLevel.getCurrentDifficultyAt(pos0);
                BlockPos pos = pos0.above(random.nextInt(5))
                        .east(random.nextInt(21) - 10)
                        .south(random.nextInt(21) - 10);
                BlockState blockstate = pLevel.getBlockState(pos);
                FluidState fluidstate = pLevel.getFluidState(pos);
                if (NaturalSpawner.isValidEmptySpawnBlock(pLevel, pos, blockstate, fluidstate, ModEntities.DRAGONFLY.get())) {
                    SpawnGroupData spawngroupdata = null;
                    int count = random.nextInt(1, 4);
                    for (int j = 0; j < count; j++) {
                        Dragonfly dragonfly = ModEntities.DRAGONFLY.get().create(pLevel);
                        if (dragonfly != null) {
                            dragonfly.moveTo(pos, 0.0F, 0.0F);
                            spawngroupdata = EventHooks.finalizeMobSpawn(dragonfly, pLevel, difficulty, MobSpawnType.NATURAL, spawngroupdata);
                            pLevel.addFreshEntityWithPassengers(dragonfly);
                            i++;
                        }
                    }
                }
            }
            return i;
        }
        return 0;
    }

    private static boolean isGoingToRain(ServerLevel level) {
        if (level.isRaining() || !level.getGameRules().getBoolean(GameRules.RULE_WEATHER_CYCLE))
            return false;
        int i = level.serverLevelData.getRainTime();
        return i < 6000;
    }

    private static boolean cannotSpawnDragonflies(Holder<Biome> biome) {
        return biome.is(BiomeTags.IS_OCEAN)
                || biome.is(Tags.Biomes.IS_SNOWY)
                || biome.is(Tags.Biomes.IS_ICY)
                || biome.is(Tags.Biomes.IS_DRY)
                || biome.is(Tags.Biomes.IS_DEAD)
                || biome.is(Tags.Biomes.IS_WASTELAND);
    }
}
