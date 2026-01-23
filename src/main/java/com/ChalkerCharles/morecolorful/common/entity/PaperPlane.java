package com.ChalkerCharles.morecolorful.common.entity;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.event.EventHooks;

public class PaperPlane extends Projectile implements IEntityExtension {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(
            PaperPlane.class, EntityDataSerializers.ITEM_STACK
    );

    public PaperPlane(EntityType<? extends PaperPlane> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PaperPlane(Level level, double x, double y, double z) {
        this(ModEntities.PAPER_PLANE.get(), level);
        this.setPos(x, y, z);
    }

    public PaperPlane(Level pLevel, LivingEntity shooter) {
        this(pLevel, shooter.getX(), shooter.getEyeY() - 0.1F, shooter.getZ());
        this.setOwner(shooter);
    }

    @Override
    public boolean shouldRenderAtSqrDistance(double pDistance) {
        double d0 = this.getBoundingBox().getSize() * 4.0;
        if (Double.isNaN(d0)) {
            d0 = 4.0;
        }

        d0 *= 64.0;
        return pDistance < d0 * d0;
    }

    @Override
    public boolean canUsePortal(boolean pAllowPassengers) {
        return true;
    }

    public void setItem(ItemStack pStack) {
        this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
    }

    private static ItemStack getDefaultItem() {
        return ModItems.PAPER_PLANE.toStack();
    }

    private ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_ITEM_STACK, getDefaultItem());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.put("Item", this.getItem().save(this.registryAccess()));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        if (pCompound.contains("Item", 10)) {
            this.setItem(ItemStack.parse(this.registryAccess(), pCompound.getCompound("Item")).orElseGet(PaperPlane::getDefaultItem));
        } else {
            this.setItem(getDefaultItem());
        }
    }

    @Override
    public void tick() {
        super.tick();
        HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hitresult.getType() != HitResult.Type.MISS && !EventHooks.onProjectileImpact(this, hitresult)) {
            this.hitTargetOrDeflectSelf(hitresult);
        }
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        double d = vec3.horizontalDistance();
        if (this.xRotO == 0.0F && this.yRotO == 0.0F) {
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * Mth.RAD_TO_DEG));
            this.setXRot((float) (Mth.atan2(vec3.y, d) * Mth.RAD_TO_DEG));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();
        }
        double d0 = this.getX() + vec3.x;
        double d1 = this.getY() + vec3.y;
        double d2 = this.getZ() + vec3.z;

        if (this.isInWater()) {
            for (int i = 0; i < 4; i++) {
                this.level().addParticle(ParticleTypes.BUBBLE, d0 - vec3.x * 0.25, d1 - vec3.y * 0.25, d2 - vec3.z * 0.25, vec3.x, vec3.y, vec3.z);
            }
            this.drop();
        }
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * Mth.RAD_TO_DEG));
        this.setXRot((float)(Mth.atan2(vec3.y, d) * Mth.RAD_TO_DEG));
        this.setXRot(Mth.rotLerp(0.2F, this.xRotO, this.getXRot()));
        this.setYRot(Mth.rotLerp(0.2F, this.yRotO, this.getYRot()));
        this.setDeltaMovement(vec3.scale(0.98));
        this.applyGravity();
        this.setPos(d0, d1, d2);
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {
            this.playSound(ModSounds.PAPER_PLANE_HIT.get(), 0.5F, 1.0F);
            this.drop();
        }
    }

    private void drop() {
        this.discard();
        if (this.shouldNotDropItem()) return;
        this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), this.getItem()));
    }

    private boolean shouldNotDropItem() {
        return !this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)
                || this.getOwner() instanceof Player player && player.hasInfiniteMaterials();
    }

    @Override
    protected double getDefaultGravity() {
        return 0.01;
    }

    @Override
    public double moreColorful$windSensitivity() {
        return 6.25;
    }
}
