package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.HitResult;

import javax.annotation.Nullable;

public class Bomb extends ThrowableItemProjectile {
    private static final EntityDataAccessor<Integer> DATA_FUSE_ID = SynchedEntityData.defineId(Bomb.class, EntityDataSerializers.INT);
    private boolean usedPortal;

    public Bomb(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Bomb(double pX, double pY, double pZ, Level pLevel) {
        super(ModEntities.BOMB.get(), pX, pY, pZ, pLevel);
    }

    public Bomb(LivingEntity pShooter, Level pLevel) {
        super(ModEntities.BOMB.get(), pShooter, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_FUSE_ID, 80);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putShort("fuse", (short)this.getFuse());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFuse(pCompound.getShort("fuse"));
    }

    public void setFuse(int life) {
        this.entityData.set(DATA_FUSE_ID, life);
    }

    public int getFuse() {
        return this.entityData.get(DATA_FUSE_ID);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BOMB.get();
    }

    @Override
    public void setItem(ItemStack pStack) {
        ItemStack stack = pStack.copyWithCount(1);
        stack.set(ModDataComponents.ACTIVATED, Unit.INSTANCE);
        super.setItem(stack);
    }

    @Override
    public void tick() {
        super.tick();
        int i = this.getFuse() - 1;
        this.setFuse(i);
        if (i <= 0) {
            if (!this.level().isClientSide) {
                this.explode();
            }
        } else {
            if (this.level().isClientSide) {
                this.level().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.2, this.getZ(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.explode();
        }
    }

    private void explode() {
        this.level().explode(
                this,
                Explosion.getDefaultDamageSource(this.level(), this),
                this.usedPortal ? PrimedTnt.USED_PORTAL_DAMAGE_CALCULATOR : null,
                this.getX(),
                this.getY(0.5),
                this.getZ(),
                2.0F,
                false,
                Level.ExplosionInteraction.TNT
        );
        this.discard();
    }

    @Nullable
    @Override
    public Entity changeDimension(DimensionTransition pTransition) {
        Entity entity = super.changeDimension(pTransition);
        if (entity instanceof Bomb bomb) {
            bomb.usedPortal = true;
        }
        return entity;
    }
}
