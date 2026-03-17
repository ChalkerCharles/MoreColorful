package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class PaperBoat extends Entity implements WindSensitive {
    private static final Predicate<Entity> IS_PAPER_BOAT = PaperBoat.class::isInstance;
    private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(
            PaperBoat.class, EntityDataSerializers.ITEM_STACK
    );
    private int lerpSteps;
    private double lerpX;
    private double lerpY;
    private double lerpZ;
    private double lerpYRot;
    private double waterLevel;
    private float landFriction;
    private Boat.Status status;
    private Boat.Status oldStatus;
    private double lastYd;
    private float wobble;
    private float oWobble;
    private int lifespan = 6000;

    public PaperBoat(EntityType<? extends PaperBoat> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public PaperBoat(Level level, double x, double y, double z) {
        this(ModEntities.PAPER_BOAT.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
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
    public boolean isPickable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public boolean canUsePortal(boolean pAllowPassengers) {
        return true;
    }

    public void setItem(ItemStack pStack) {
        this.getEntityData().set(DATA_ITEM_STACK, pStack.copyWithCount(1));
    }

    private static ItemStack getDefaultItem() {
        return ModItems.PAPER_BOAT.toStack();
    }

    private ItemStack getItem() {
        return this.getEntityData().get(DATA_ITEM_STACK);
    }

    @Override
    public ItemStack getPickResult() {
        return getDefaultItem();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_ITEM_STACK, getDefaultItem());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        if (pCompound.contains("Lifespan")) {
            this.lifespan = pCompound.getInt("Lifespan");
        }
        pCompound.put("Item", this.getItem().save(this.registryAccess()));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putInt("Lifespan", this.lifespan);
        if (pCompound.contains("Item", 10)) {
            this.setItem(ItemStack.parse(this.registryAccess(), pCompound.getCompound("Item")).orElseGet(PaperBoat::getDefaultItem));
        } else {
            this.setItem(getDefaultItem());
        }
    }

    private void dropItem() {
        this.spawnAtLocation(this.getItem());
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
                this.dropItem();
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
        this.lerpSteps = 10;
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
    public float lerpTargetYRot() {
        return this.lerpSteps > 0 ? (float)this.lerpYRot : this.getYRot();
    }

    private void tickLerp() {
        if (this.isControlledByLocalInstance()) {
            this.lerpSteps = 0;
            this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());
        }
        if (this.lerpSteps > 0) {
            this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.getXRot());
            this.lerpSteps--;
        }
    }

    @Override
    public void tick() {
        super.tick();
        this.tickLerp();
        this.oldStatus = this.status;
        this.status = this.getStatus();
        this.checkInsideBlocks();
        Vec3 vec3 = this.getDeltaMovement();
        if (vec3.x != 0 && vec3.z != 0) {
            this.setYRot((float) (Mth.atan2(vec3.x, vec3.z) * Mth.RAD_TO_DEG));
        }
        float targetRotation = this.getYRot();
        this.setYRot(Mth.rotLerp(0.2F, this.yRotO, targetRotation));
        this.floatBoat();
        this.move(MoverType.SELF, this.getDeltaMovement());
        if (this.level().isClientSide) {
            this.wobble();
        }
        this.lastYd = this.getDeltaMovement().y;
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), IS_PAPER_BOAT);
        if (!list.isEmpty()) {
            list.forEach(this::push);
        }
        this.lifespan--;
        if (this.lifespan <= 0) {
            this.discard();
            if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                if (this.random.nextInt(3) == 0) {
                    this.dropItem();
                }
            }
        }
    }

    private void floatBoat() {
        double d0 = -this.getGravity();
        double d1 = 0.0;
        float invFriction = 0.05F;
        if (this.oldStatus == Boat.Status.IN_AIR && this.status != Boat.Status.IN_AIR && this.status != Boat.Status.ON_LAND) {
            this.waterLevel = this.getY(1);
            double d2 = this.getWaterLevelAbove() - this.getBbHeight() + 0.101;
            if (this.level().noCollision(this, this.getBoundingBox().move(0.0, d2 - this.getY(), 0.0))) {
                this.setPos(this.getX(), d2, this.getZ());
                this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, 0.0, 1.0));
                this.lastYd = 0.0;
            }

            this.status = Boat.Status.IN_WATER;
        } else {
            if (this.status == Boat.Status.IN_WATER) {
                d1 = (this.waterLevel - this.getY() + 0.1) / this.getBbHeight();
                invFriction = 0.9F;
            } else if (this.status == Boat.Status.UNDER_FLOWING_WATER) {
                d0 = -7.0E-4;
                invFriction = 0.9F;
            } else if (this.status == Boat.Status.UNDER_WATER) {
                d1 = 0.01F;
                invFriction = 0.45F;
            } else if (this.status == Boat.Status.IN_AIR) {
                invFriction = 0.9F;
            } else if (this.status == Boat.Status.ON_LAND) {
                invFriction = this.landFriction;
            }

            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x * (double) invFriction, vec3.y + d0, vec3.z * (double) invFriction);
            if (d1 > 0.0) {
                Vec3 vec31 = this.getDeltaMovement();
                this.setDeltaMovement(vec31.x, (vec31.y + d1 * (this.getDefaultGravity() / 0.65)) * 0.75, vec31.z);
            }
        }
    }

    private Boat.Status getStatus() {
        Boat.Status status = this.isUnderwater();
        if (status != null) {
            this.waterLevel = this.getBoundingBox().maxY;
            return status;
        } else if (this.checkInWater()) {
            return Boat.Status.IN_WATER;
        } else {
            float f = this.getGroundFriction();
            if (f > 0.0F) {
                this.landFriction = f;
                return Boat.Status.ON_LAND;
            } else {
                return Boat.Status.IN_AIR;
            }
        }
    }

    public float getWaterLevelAbove() {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.maxY);
        int l = Mth.ceil(aabb.maxY - this.lastYd);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        label39:
        for (int k1 = k; k1 < l; k1++) {
            float f = 0.0F;

            for (int l1 = i; l1 < j; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    pos.set(l1, k1, i2);
                    FluidState fluidstate = this.level().getFluidState(pos);
                    f = Math.max(f, fluidstate.getHeight(this.level(), pos));

                    if (f >= 1.0F) {
                        continue label39;
                    }
                }
            }

            if (f < 1.0F) {
                return pos.getY() + f;
            }
        }

        return l + 1;
    }

    public float getGroundFriction() {
        AABB aabb = this.getBoundingBox();
        AABB aabb1 = new AABB(aabb.minX, aabb.minY - 0.001, aabb.minZ, aabb.maxX, aabb.minY, aabb.maxZ);
        int i = Mth.floor(aabb1.minX) - 1;
        int j = Mth.ceil(aabb1.maxX) + 1;
        int k = Mth.floor(aabb1.minY) - 1;
        int l = Mth.ceil(aabb1.maxY) + 1;
        int i1 = Mth.floor(aabb1.minZ) - 1;
        int j1 = Mth.ceil(aabb1.maxZ) + 1;
        VoxelShape voxelshape = Shapes.create(aabb1);
        float f = 0.0F;
        int k1 = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int l1 = i; l1 < j; l1++) {
            for (int i2 = i1; i2 < j1; i2++) {
                int j2 = (l1 != i && l1 != j - 1 ? 0 : 1) + (i2 != i1 && i2 != j1 - 1 ? 0 : 1);
                if (j2 != 2) {
                    for (int k2 = k; k2 < l; k2++) {
                        if (j2 <= 0 || k2 != k && k2 != l - 1) {
                            pos.set(l1, k2, i2);
                            BlockState blockstate = this.level().getBlockState(pos);
                            if (!(blockstate.getBlock() instanceof WaterlilyBlock)
                                    && Shapes.joinIsNotEmpty(
                                    blockstate.getCollisionShape(this.level(), pos).move(l1, k2, i2),
                                    voxelshape,
                                    BooleanOp.AND
                            )) {
                                f += blockstate.getFriction(this.level(), pos, this);
                                k1++;
                            }
                        }
                    }
                }
            }
        }

        return f / (float)k1;
    }

    private boolean checkInWater() {
        AABB aabb = this.getBoundingBox();
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.minY);
        int l = Mth.ceil(aabb.minY + 0.001);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        this.waterLevel = -Double.MAX_VALUE;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    pos.set(k1, l1, i2);
                    FluidState fluidstate = this.level().getFluidState(pos);
                    float f = l1 + fluidstate.getHeight(this.level(), pos);
                    this.waterLevel = Math.max(f, this.waterLevel);
                    flag |= aabb.minY < f;
                }
            }
        }

        return flag;
    }

    @Nullable
    private Boat.Status isUnderwater() {
        AABB aabb = this.getBoundingBox();
        double d0 = aabb.maxY + 0.001;
        int i = Mth.floor(aabb.minX);
        int j = Mth.ceil(aabb.maxX);
        int k = Mth.floor(aabb.maxY);
        int l = Mth.ceil(d0);
        int i1 = Mth.floor(aabb.minZ);
        int j1 = Mth.ceil(aabb.maxZ);
        boolean flag = false;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    pos.set(k1, l1, i2);
                    FluidState fluidstate = this.level().getFluidState(pos);
                    if (d0 < (pos.getY() + fluidstate.getHeight(this.level(), pos))) {
                        if (!fluidstate.isSource()) {
                            return Boat.Status.UNDER_FLOWING_WATER;
                        }

                        flag = true;
                    }
                }
            }
        }

        return flag ? Boat.Status.UNDER_WATER : null;
    }

    private void wobble() {
        this.oWobble = this.wobble;
        if (this.status == Boat.Status.IN_WATER) {
            Vector3f wind = WeatherUtils.getEffectiveWindSpeedAffectingEntity(this);
            if (wind != null) {
                this.wobble = ClientWrapper.wobble(wind, (float) this.getX(), (float) this.getZ());
            }
        }
    }

    public float getFloatOffset() {
        return this.status == Boat.Status.IN_WATER ? 0.165F : 0;
    }

    public float lerpWobble(float partialTick) {
        return Mth.lerp(partialTick, this.oWobble, this.wobble);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.04;
    }

    @Override
    protected MovementEmission getMovementEmission() {
        return MovementEmission.NONE;
    }
}
