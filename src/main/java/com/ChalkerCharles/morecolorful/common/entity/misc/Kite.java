package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ornamental.RibbonBlock;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.KiteColor;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class Kite extends Entity implements WindSensitive, IEntityExtension, Leashable, ILeashableExtension {
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(
            Kite.class, EntityDataSerializers.ITEM_STACK
    );
    private static final EntityDataAccessor<Float> DATA_ROPE_LENGTH = SynchedEntityData.defineId(Kite.class, EntityDataSerializers.FLOAT);
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpXRot;
    private double lerpYRot;
    private double leashYOffset;
    private double oLeashYOffset;
    private double dLeashYOffset;
    private float waveFactor;
    private float oWaveFactor;
    private float waveTicks;
    private float oWaveTicks;
    @Nullable
    private Leashable.LeashData leashData;

    public Kite(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Kite(Level level, Entity entity) {
        this(ModEntities.KITE.get(), level);
        Vec3 vec = entity.getLookAngle();
        this.setPos(entity.getX() + vec.x, entity.getEyeY() + vec.y, entity.getZ() + vec.z);
        this.setLeashedTo(entity, true);
    }

    private static ItemStack getDefaultItem() {
        return ModItems.KITE.toStack();
    }

    private ItemStack getItem() {
        return this.entityData.get(DATA_ITEM_STACK);
    }

    public void setItem(ItemStack stack) {
        this.entityData.set(DATA_ITEM_STACK, stack.copyWithCount(1));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_ITEM_STACK, getDefaultItem());
        pBuilder.define(DATA_ROPE_LENGTH, 4.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.leashData = this.readLeashData(pCompound);
        if (pCompound.contains("Item", 10)) {
            this.setItem(ItemStack.parse(this.registryAccess(), pCompound.getCompound("Item")).orElseGet(Kite::getDefaultItem));
        } else {
            this.setItem(getDefaultItem());
        }
        if (pCompound.contains("RopeLength")) {
            this.setRopeLength(pCompound.getFloat("RopeLength"));
        } else {
            this.setRopeLength(4.0F);
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        this.writeLeashData(pCompound, this.leashData);
        pCompound.put("Item", this.getItem().save(this.registryAccess()));
        pCompound.putFloat("RopeLength", this.getRopeLength());
    }

    public KiteColor getColor() {
        return this.getItem().getOrDefault(ModDataComponents.KITE_COLOR, KiteColor.DEFAULT);
    }

    @Nullable
    public DyeColor getBowColor() {
        return this.getItem().get(ModDataComponents.RIBBON);
    }

    @Override
    @Nullable
    public Leashable.LeashData getLeashData() {
        return this.leashData;
    }

    @Override
    public void setLeashData(Leashable.LeashData leashData) {
        this.leashData = leashData;
    }

    private float getRopeLength() {
        return this.entityData.get(DATA_ROPE_LENGTH);
    }

    public void setRopeLength(float length) {
        this.entityData.set(DATA_ROPE_LENGTH, length);
    }

    public boolean reelOrUnreel(boolean reel) {
        float length = this.getRopeLength();
        length -= reel ? 0.5F : -0.5F;
        float clamped = Mth.clamp(length, 4, 64);
        this.setRopeLength(clamped);
        return clamped == length;
    }

    @Override
    public void elasticRangeLeashBehaviour(Entity leashHolder, float distance) {
        Vec3 vec3 = leashHolder.position().subtract(this.position()).normalize().scale(distance - this.getRopeLength());
        Vec3 vec31 = this.getDeltaMovement();
        boolean flag = vec31.dot(vec3) > 0.0;
        this.setDeltaMovement(vec31.add(vec3.scale(flag ? 0.1F : 0.15F)));
    }

    @Override
    public boolean moreColorful$canDropLeash() {
        return false;
    }

    @Override
    public boolean moreColorful$balloonAttachable() {
        return false;
    }

    @Override
    public double moreColorful$leashSnapDistance() {
        return this.getRopeLength() + 6.0;
    }

    @Override
    public double moreColorful$leashElasticDistance() {
        return this.getRopeLength();
    }

    @Override
    public Vec3 getLeashOffset(float pPartialTick) {
        double y = Mth.lerp(pPartialTick, this.oLeashYOffset, this.leashYOffset);
        return new Vec3(0.0, y, 0.125F);
    }

    public float getWaveFactor(float partialTick) {
        return Mth.lerp(partialTick, this.oWaveFactor, this.waveFactor);
    }

    public float getWaveTicks(float partialTick) {
        return Mth.lerp(partialTick, this.oWaveTicks, this.waveTicks);
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean canUsePortal(boolean pAllowPassengers) {
        return true;
    }

    @Override
    public ItemStack getPickResult() {
        return this.getItem().copy();
    }

    @Override
    public boolean shouldRender(double pX, double pY, double pZ) {
        return true;
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        AABB aabb = super.getBoundingBoxForCulling();
        return aabb.expandTowards(this.getLookAngle().scale(-4.0));
    }

    public static SoundEvent getReelSound(boolean reel) {
        return reel ? ModSounds.KITE_REEL.get() : ModSounds.KITE_UNREEL.get();
    }

    public static float getReelPitch(boolean reel, RandomSource random) {
        return random.nextFloat() * 0.2F + (reel ? 0.7F : 0.9F);
    }

    public boolean shouldMakeReelSound() {
        return this.tickCount % 3 == 0;
    }

    private void removeRibbon() {
        ItemStack kiteItem = this.getItem();
        kiteItem.remove(ModDataComponents.RIBBON);
        this.setItem(kiteItem);
    }

    @Override
    public InteractionResult interact(Player pPlayer, InteractionHand pHand) {
        Level level = this.level();
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            ItemStack item = pPlayer.getItemInHand(pHand);
            DyeColor color = this.getBowColor();
            if (color != null && ItemUtils.isShears(item)) {
                level.playSound(null, this.getX(), this.getY(), this.getZ(), ModSounds.SHEARS_SNIP.get(), SoundSource.PLAYERS);
                item.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pHand));
                this.removeRibbon();
                this.spawnAtLocation(RibbonBlock.itemByColor(color));
                return InteractionResult.CONSUME;
            }
            boolean reel = pPlayer.isSecondaryUseActive();
            if (this.reelOrUnreel(reel) && this.shouldMakeReelSound()) {
                level.playSound(null, pPlayer, getReelSound(reel), SoundSource.PLAYERS, 1.0F, getReelPitch(reel, level.random));
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return true;
        } else if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            this.markHurt();
            this.kill();
            if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS) && !pSource.isCreativePlayer()) {
                this.spawnAtLocation(this.getItem());
            }
            return true;
        }
    }

    @Override
    public void lerpTo(double pX, double pY, double pZ, float pYRot, float pXRot, int pSteps) {
        this.lerpX = pX;
        this.lerpY = pY;
        this.lerpZ = pZ;
        this.lerpYRot = pYRot;
        this.lerpXRot = pXRot;
        this.lerpSteps = 8;
    }

    @Override
    public double lerpTargetX() {
        return this.lerpSteps > 0 ? this.lerpX : this.getX();
    }

    @Override
    public double lerpTargetY() {
        return this.lerpSteps > 0 ? this.lerpY : this.getY();
    }

    @Override
    public double lerpTargetZ() {
        return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
    }

    @Override
    public float lerpTargetXRot() {
        return this.lerpSteps > 0 ? (float)this.lerpXRot : this.getXRot();
    }

    @Override
    public float lerpTargetYRot() {
        return this.lerpSteps > 0 ? (float)this.lerpYRot : this.getYRot();
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
            this.lerpSteps--;
        }
    }

    private void setLeashYOffset() {
        this.oLeashYOffset = this.leashYOffset;
        Entity holder;
        if (this.onGround() || this.leashData == null || (holder = this.leashData.leashHolder) == null) {
            this.leashYOffset = 0.0;
            this.dLeashYOffset = 0.0;
        } else {
            if (this.moreColorful$leashDistanceTo(holder) > this.getRopeLength()) {
                boolean flag = this.getY() > holder.getY() + 0.6;
                this.dLeashYOffset += flag ? -0.01 : 0.01;
            } else {
                boolean flag = this.getDeltaMovement().y > 0;
                this.dLeashYOffset += flag ? -0.01 : 0.01;
            }
            this.dLeashYOffset *= 0.9;
            this.leashYOffset = Mth.clamp(this.leashYOffset + this.dLeashYOffset, -0.5, 0.0);
        }
    }

    @Override
    public void tick() {
        if (this.level().isClientSide) {
            this.oWaveFactor = this.waveFactor;
            this.oWaveTicks = this.waveTicks;
            Vector3f wind = WeatherUtils.getEffectiveWindSpeedAffectingEntity(this);
            Vector3f vec = this.getDeltaMovement().toVector3f().negate();
            if (wind != null) {
                vec.add(wind.mul(0.05F));
            }
            vec.y = 0;
            float f = vec.length();
            this.waveFactor = f;
            this.waveTicks += f;
            this.setLeashYOffset();
        }
        super.tick();
        this.tickLerp();
        this.moreColorful$stopSlightMovement();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.applyGravityLiftAndDrag();
        if (this.onGround()) {
            this.setXRot(0);
        }
        this.checkInsideBlocks();
    }

    @Override
    public double moreColorful$horizontalWindage() {
        return 3.0;
    }

    private void applyGravityLiftAndDrag() {
        double d0 = this.getGravity();
        Vec3 vec = this.getDeltaMovement();
        double y = vec.y;
        if (!this.level().isClientSide) {
            y -= d0;
            if (this.leashData != null) {
                Entity leashHolder = this.leashData.leashHolder;
                if (leashHolder != null) {
                    Vec3 vec0 = leashHolder.getKnownMovement();
                    Vec3 vec1 = leashHolder.position().subtract(this.position());
                    double d = vec0.x * vec1.x + vec0.z * vec1.z;
                    if (d > 0) {
                        y += Mth.length(vec0.x, vec0.z) * 0.1;
                        if (y > vec.y) {
                            this.setXRot(Mth.clamp(this.getXRot() - 2, -30, 0));
                        }
                    }
                }
            }
            this.setXRot(Mth.clamp(this.getXRot() + 1, -30, 0));
        }
        this.setDeltaMovement(vec.x * 0.98, y * 0.92, vec.z * 0.98);
    }

    @Override
    public boolean moreColorful$hasLift() {
        return this.isLeashed();
    }

    @Override
    public void moreColorful$onLift() {
        this.setXRot(Mth.clamp(this.getXRot() - 2, -30, 0));
    }

    @Override
    protected double getDefaultGravity() {
        return 0.02;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }
}
