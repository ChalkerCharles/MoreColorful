package com.ChalkerCharles.morecolorful.common.entity.ai.behavior;

import com.ChalkerCharles.morecolorful.common.entity.ai.memory.KiteMemory;
import com.ChalkerCharles.morecolorful.common.entity.ai.memory.ModMemoryModuleTypes;
import com.ChalkerCharles.morecolorful.common.entity.misc.Kite;
import com.ChalkerCharles.morecolorful.common.item.misc.KiteItem;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

public class FlyKite extends Behavior<Mob> {
    private static final Map<MemoryModuleType<?>, MemoryStatus> ENTRY_CONDITIONS = ImmutableMap.of(
            MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_ABSENT,
            MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT,
            ModMemoryModuleTypes.KITE_MEMORY.get(), MemoryStatus.VALUE_ABSENT,
            ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get(), MemoryStatus.VALUE_PRESENT
    );
    @Nullable
    private BlockPos targetPos;
    private int unreelTicks;
    private boolean released;
    @Nullable
    private Kite cachedKite;

    public FlyKite() {
        super(ENTRY_CONDITIONS, 600, 1200);
    }

    @Override
    protected void start(ServerLevel level, Mob entity, long gameTime) {
        getNearestOpenSpacePos(entity).ifPresent(p -> {
            this.targetPos = p;
            this.unreelTicks = level.random.nextInt(8, 24);
            startWalkingTowards(entity, p);
        });
    }

    @Override
    protected void tick(ServerLevel level, Mob owner, long pGameTime) {
        if (this.targetPos == null) return;
        if (!this.released && owner.distanceToSqr(this.targetPos.getCenter()) < 1) {
            this.released = true;
            Kite kite = new Kite(level, owner);
            ItemStack stack = KiteItem.getRandomKite(level.random);
            kite.setItem(stack);
            this.cachedKite = kite;
            level.addFreshEntity(kite);
            KiteMemory memory = new KiteMemory(false, this.endTimestamp, kite);
            owner.getBrain().setMemoryWithExpiry(ModMemoryModuleTypes.KITE_MEMORY.get(), memory, 2400L);
        }
        if (this.released && this.unreelTicks > 0 && this.cachedKite != null) {
            if (this.cachedKite.reelOrUnreel(false) && this.cachedKite.shouldMakeReelSound()) {
                level.playSound(null, owner, Kite.getReelSound(false), owner.getSoundSource(), 1.0F, Kite.getReelPitch(false, level.random));
            }
            this.unreelTicks--;
        }
        if (this.released && this.cachedKite != null) {
            startLookingAt(owner, this.cachedKite);
        }
    }

    @Override
    protected void stop(ServerLevel pLevel, Mob pEntity, long pGameTime) {
        KiteMemory.forceReel(pEntity);
        this.targetPos = null;
        this.released = false;
        this.cachedKite = null;
    }

    @Override
    protected boolean canStillUse(ServerLevel pLevel, Mob mob, long pGameTime) {
        return mob.isBaby()
                && WeatherUtils.isWindyNow(pLevel)
                && this.targetPos != null
                && pLevel.canSeeSky(this.targetPos)
                && !this.kiteLost(mob);
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel pLevel, Mob pOwner) {
        return pOwner.isBaby() && WeatherUtils.suitableForKiteFly(pLevel);
    }

    private static Optional<BlockPos> getNearestOpenSpacePos(Mob mob) {
        return mob.getBrain().getMemory(ModMemoryModuleTypes.NEAREST_OPEN_SPACE.get());
    }

    private static void startWalkingTowards(Mob mob, BlockPos pos) {
        mob.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(pos, 1, 0));
    }

    private static void startLookingAt(Mob mob, Entity target) {
        mob.getBrain().setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker(target, true));
    }

    private boolean kiteLost(Mob mob) {
        if (!this.released) return false;
        if (this.cachedKite == null || this.cachedKite.isRemoved()) return true;
        Entity entity = this.cachedKite.getLeashHolder();
        return entity == null || entity != mob;
    }
}
