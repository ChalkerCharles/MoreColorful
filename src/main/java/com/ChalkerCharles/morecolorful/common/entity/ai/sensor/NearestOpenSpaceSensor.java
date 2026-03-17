package com.ChalkerCharles.morecolorful.common.entity.ai.sensor;

import com.ChalkerCharles.morecolorful.common.entity.ai.memory.ModMemoryModuleTypes;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

import java.util.Set;

public class NearestOpenSpaceSensor extends Sensor<Mob> {
    public NearestOpenSpaceSensor() {
        super(40);
    }

    @Override
    protected void doTick(ServerLevel level, Mob mob) {
        if (mob.isBaby() && mob instanceof PathfinderMob m) {
            BlockPos pos = mob.blockPosition();
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
            for (int i = 0; i < 5; i++) {
                mutable.set(pos);
                setRandomPos(mutable, level.random);
                BlockPos pos1 = LandRandomPos.movePosUpOutOfSolid(m, mutable);
                if (pos1 != null && isOpenSpace(pos1, level) && canReach(mob, pos1)) {
                    mob.getBrain().setMemory(ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get(), pos1);
                    return;
                }
            }
        }
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return Set.of(ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get());
    }

    private static void setRandomPos(BlockPos.MutableBlockPos mutable, RandomSource random) {
        int x = random.nextInt(9) - 4;
        int z = random.nextInt(9) - 4;
        mutable.move(x, 0, z);
    }

    private static boolean isOpenSpace(BlockPos pos, Level level) {
        BlockPos.MutableBlockPos mutable = pos.mutable();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                mutable.set(pos.getX() + i, pos.getY(), pos.getZ() + j);
                if (!level.canSeeSky(mutable) || !WeatherUtils.canApplyWind(level, mutable)) return false;
            }
        }
        return true;
    }

    private static boolean canReach(Mob mob, BlockPos pos) {
        Path path = mob.getNavigation().createPath(pos, 0);
        return path != null && path.canReach();
    }
}
