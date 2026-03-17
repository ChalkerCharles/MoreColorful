package com.ChalkerCharles.morecolorful.common.entity.ai.memory;

import com.ChalkerCharles.morecolorful.common.entity.misc.Kite;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public final class KiteMemory {
    public static final Codec<KiteMemory> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.BOOL.fieldOf("reeling").forGetter(KiteMemory::reeling),
                    Codec.LONG.fieldOf("endTimestamp").forGetter(KiteMemory::endTimestamp),
                    UUIDUtil.CODEC.fieldOf("kite").forGetter(KiteMemory::kite)
            ).apply(instance, KiteMemory::new)
    );
    private final boolean reeling;
    private final long endTimestamp;
    private final UUID kite;
    @Nullable
    private Kite cachedKite;

    public KiteMemory(boolean reeling, long endTimestamp, UUID kite) {
        this.reeling = reeling;
        this.endTimestamp = endTimestamp;
        this.kite = kite;
    }

    public KiteMemory(boolean reeling, long endTimestamp, Kite kite) {
        this(reeling, endTimestamp, kite.getUUID());
        this.cachedKite = kite;
    }

    public static void forceReel(Mob mob) {
        if (mob.level().isClientSide) return;
        Brain<?> brain = mob.getBrain();
        Optional<KiteMemory> memory = brain.getMemoryInternal(ModMemoryModuleTypes.KITE_MEMORY.get());
        if (memory == null) return;
        memory.ifPresent(m -> {
            if (!m.reeling) {
                m.startReel(brain);
            }
        });
    }

    public static void check(Mob mob) {
        if (!(mob.level() instanceof ServerLevel level)) return;
        Brain<?> brain = mob.getBrain();
        Optional<KiteMemory> memory = brain.getMemoryInternal(ModMemoryModuleTypes.KITE_MEMORY.get());
        if (memory == null) return;
        memory.ifPresent(m -> {
            if (m.cachedKite == null) {
                if (!m.loadKiteFromUUID(level)) {
                    brain.eraseMemory(ModMemoryModuleTypes.KITE_MEMORY.get());
                }
            }
            if (m.cachedKite != null && m.cachedKite.isAlive() && m.reeling) {
                boolean reeled = m.cachedKite.reelOrUnreel(true);
                if (reeled && m.cachedKite.shouldMakeReelSound()) {
                    level.playSound(null, mob, Kite.getReelSound(true), mob.getSoundSource(), 1.0F, Kite.getReelPitch(true, level.random));
                } else if (!reeled) {
                    m.cachedKite.discard();
                }
            }
            if (!m.reeling && level.getGameTime() > m.endTimestamp) {
                m.startReel(brain);
            }
        });
    }

    private void startReel(Brain<?> brain) {
        KiteMemory newMemory = new KiteMemory(true, this.endTimestamp, this.kite);
        newMemory.cachedKite = this.cachedKite;
        brain.setMemory(ModMemoryModuleTypes.KITE_MEMORY.get(), newMemory);
    }

    private boolean loadKiteFromUUID(ServerLevel level) {
        Entity entity = level.getEntity(this.kite);
        if (entity instanceof Kite k) {
            this.cachedKite = k;
            return true;
        }
        return false;
    }

    private boolean reeling() {
        return this.reeling;
    }

    private long endTimestamp() {
        return this.endTimestamp;
    }

    private UUID kite() {
        return this.kite;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof KiteMemory that) {
            return this.reeling == that.reeling
                    && this.endTimestamp == that.endTimestamp
                    && Objects.equals(this.kite, that.kite);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.reeling, this.endTimestamp, this.kite);
    }

    @Override
    public String toString() {
        return "KiteMemory[reeling=" + this.reeling +
                ", endTimestamp=" + this.endTimestamp +
                ", kite=" + this.kite + ']';
    }
}
