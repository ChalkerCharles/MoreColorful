package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.network.packets.SmokeBombPacket;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class SmokeBomb extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> DATA_LIFETIME = SynchedEntityData.defineId(SmokeBomb.class, EntityDataSerializers.INT);
    private Runnable particlePosSetter;
    private boolean clientExplodeMark = false;

    public SmokeBomb(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SmokeBomb(double pX, double pY, double pZ, Level pLevel) {
        super(ModEntities.SMOKE_BOMB.get(), pX, pY, pZ, pLevel);
    }

    public SmokeBomb(LivingEntity pShooter, Level pLevel) {
        super(ModEntities.SMOKE_BOMB.get(), pShooter, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_LIFETIME, -80);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("lifetime", this.getLifetime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setLifetime(pCompound.getInt("lifetime"));
    }

    public void setLifetime(int life) {
        this.entityData.set(DATA_LIFETIME, life);
    }

    public int getLifetime() {
        return this.entityData.get(DATA_LIFETIME);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.WHITE_SMOKE_BOMB.get();
    }

    public void setParticlePosSetter(Runnable runnable) {
        this.particlePosSetter = runnable;
    }

    public void markExplodeClient() {
        this.clientExplodeMark = true;
    }

    @Override
    public void onSyncedDataUpdated(List<SynchedEntityData.DataValue<?>> pDataValues) {
        super.onSyncedDataUpdated(pDataValues);
        if (this.level().isClientSide && this.clientExplodeMark) {
            if (this.getLifetime() >= 0) {
                ClientWrapper.createSmokeBomb(this.level(), this);
            }
            this.clientExplodeMark = false;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.particlePosSetter != null) {
            this.particlePosSetter.run();
        }
        int i = this.getLifetime() + 1;
        this.setLifetime(i);
        if (i == 0) {
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else if (i > 0) {
            if (!this.level().isClientSide) {
                this.tickSmoke();
            }
            if (i > 400) {
                this.discard();
            }
        } else {
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    private void tickSmoke() {
        Vec3 pos = this.position();
        AABB area = new AABB(pos.x - 2, pos.y, pos.z - 2, pos.x + 2, pos.y + 5, pos.z + 2);
        List<Mob> list = this.level().getEntitiesOfClass(Mob.class, area);
        for (Mob mob : list) {
            LevelSavedData.addEntityInSmoke(this.level(), mob);
            if (mob instanceof Bee bee) {
                bee.stopBeingAngry();
            }
        }
    }

    private void explode() {
        if (this.level() instanceof ServerLevel level) {
            this.gameEvent(GameEvent.EXPLODE);
            level.playSound(
                    null,
                    this.getX(), this.getY(), this.getZ(),
                    ModSounds.SMOKE_BOMB_EXPLODE.get(),
                    SoundSource.NEUTRAL,
                    0.8F,
                    level.random.nextFloat() * 0.2F + 0.9F
            );
            PacketDistributor.sendToPlayersInDimension(level, new SmokeBombPacket(this.getId()));
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.setDeltaMovement(Vec3.ZERO);
        if (!this.level().isClientSide) {
            if (this.getLifetime() < 0) {
                this.setLifetime(0);
                this.explode();
            }
        }
    }
}
