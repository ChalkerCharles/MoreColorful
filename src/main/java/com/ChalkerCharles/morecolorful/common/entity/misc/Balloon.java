package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.client.particle.BalloonParticleOption;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.misc.BalloonItem;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import com.ChalkerCharles.morecolorful.util.Colour;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WindSensitive;
import com.ChalkerCharles.morecolorful.util.client.ClientWrapper;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Balloon extends Entity implements WindSensitive, IEntityExtension, Leashable, ILeashableExtension, VariantHolder<Balloon.Variant> {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Balloon.class, EntityDataSerializers.INT);
    protected static final EntityDataAccessor<Float> DATA_ID_DAMAGE = SynchedEntityData.defineId(Balloon.class, EntityDataSerializers.FLOAT);
    @Nullable
    private Leashable.LeashData leashData;

    public Balloon(EntityType<? extends Balloon> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public Balloon(Level level, Player player, Entity attachedTo, Variant variant) {
        super(ModEntities.BALLOON.get(), level);
        Vec3 vec = player == attachedTo ? player.getLookAngle() : player.getLookAngle().reverse();
        this.setPos(attachedTo.getX() + vec.x, attachedTo.getEyeY() + vec.y, attachedTo.getZ() + vec.z);
        this.setVariant(variant);
        this.setLeashedTo(attachedTo, true);
        this.playSound(ModSounds.BALLOON_INFLATE.get());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        pBuilder.define(DATA_TYPE_ID, 0);
        pBuilder.define(DATA_ID_DAMAGE, 0.0F);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        this.leashData = this.readLeashData(pCompound);
        this.setVariant(Variant.byIndex(pCompound.getInt("Type")));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        this.writeLeashData(pCompound, this.leashData);
        pCompound.putInt("Type", this.getVariant().getIndex());
    }

    public void setDamage(float pDamage) {
        this.entityData.set(DATA_ID_DAMAGE, pDamage);
    }

    public float getDamage() {
        return this.entityData.get(DATA_ID_DAMAGE);
    }

    @Override
    public @Nullable Leashable.LeashData getLeashData() {
        return this.leashData;
    }

    @Override
    public void setLeashData(@Nullable Leashable.LeashData pLeashData) {
        this.leashData = pLeashData;
    }

    @Override
    public void setVariant(Variant variant) {
        this.entityData.set(DATA_TYPE_ID, variant.getIndex());
    }

    @Override
    public Variant getVariant() {
        return Variant.byIndex(this.entityData.get(DATA_TYPE_ID));
    }

    @Override
    protected Vec3 getLeashOffset() {
        return Vec3.ZERO;
    }

    @Override
    public void elasticRangeLeashBehaviour(Entity leashHolder, float distance) {
        Vec3 vec3 = leashHolder.position().subtract(this.position()).normalize().scale(distance - 4.0);
        Vec3 vec31 = this.getDeltaMovement();
        boolean flag = vec31.dot(vec3) > 0.0;
        this.setDeltaMovement(vec31.add(vec3.scale(flag ? 0.1F : 0.15F)));
    }

    @Override
    public boolean moreColorful$canHaveALeashAttachedTo(Entity entity) {
        if (entity instanceof Balloon) return false;
        return ILeashableExtension.super.moreColorful$canHaveALeashAttachedTo(entity);
    }

    @Override
    public double moreColorful$leashElasticDistance() {
        return 4.0;
    }

    @Override
    public double moreColorful$leashSnapDistance() {
        return 10.0;
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

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(BalloonItem.BY_VARIANT.get(this.getVariant()));
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        InteractionResult result = super.interact(player, hand);
        if (result != InteractionResult.PASS) {
            return result;
        }
        if (this.leashData == null || this.leashData.leashHolder == null) {
            this.setLeashedTo(player, true);
            this.playSound(ModSounds.LEAD_TIED.get());
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    public void pop(DamageSource source) {
        this.kill();
        if (this.level() instanceof ServerLevel level) {
            int count = this.random.nextInt(4, 8);
            level.sendParticles(
                    BalloonParticleOption.get(this.getVariant()),
                    this.getX(), this.getY(0.5), this.getZ(),
                    count, 0.2, 0.2, 0.2, 1
            );
            this.playSound(ModSounds.BALLOON_POP.get());
        }
        if (!source.isCreativePlayer() && this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.spawnAtLocation(Items.LEAD);
            int count = this.random.nextInt(3);
            if (count > 0) {
                this.spawnAtLocation(new ItemStack(Items.PHANTOM_MEMBRANE, count));
            }
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
            this.setDamage(this.getDamage() + pAmount * 10.0F);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, pSource.getEntity());
            if ((this.getDamage() > 40.0F) || this.shouldSourceDestroy(pSource)) {
                this.pop(pSource);
            } else {
                Vec3 vec = pSource.getSourcePosition();
                if (vec != null) {
                    this.push(vec.vectorTo(this.position()).normalize().scale(0.2));
                    this.playSound(ModSounds.BALLOON_HIT.get(), 0.8F, this.random.nextFloat() * 0.2F + 0.9F);
                }
            }
            return true;
        }
    }

    private boolean shouldSourceDestroy(DamageSource source) {
        if (source.is(ModTags.DamageTypes.CAN_POKE_BALLOON)) {
            return true;
        }
        ItemStack item = source.getWeaponItem();
        if (item != null) {
            return item.is(ModTags.Items.CAN_POKE_BALLOON);
        }
        return false;
    }

    @Override
    public void push(Entity entity) {
        if (!this.isPassengerOfSameVehicle(entity)) {
            if (!entity.noPhysics && !this.noPhysics) {
                double d0 = entity.getX() - this.getX();
                double d1 = entity.getZ() - this.getZ();
                double d2 = Mth.absMax(d0, d1);
                if (d2 >= 0.01F) {
                    d2 = Math.sqrt(d2);
                    d0 /= d2;
                    d1 /= d2;
                    double d3 = 1.0 / d2;
                    if (d3 > 1.0) {
                        d3 = 1.0;
                    }
                    d0 *= d3;
                    d1 *= d3;
                    d0 *= 0.02F;
                    d1 *= 0.02F;
                    if (!this.isVehicle() && this.isPushable()) {
                        this.push(-d0, 0.0, -d1);
                    }
                    if (!entity.isVehicle() && entity.isPushable()) {
                        entity.push(d0, 0.0, d1);
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        if (this.getDamage() > 0.0F) {
            this.setDamage(this.getDamage() - 1.0F);
        }
        super.tick();
        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setDeltaMovement(this.getDeltaMovement().multiply(0.98, 0.92, 0.98));
        this.applyGravity();
        this.checkInsideBlocks();
        List<Entity> list = this.level().getEntities(this, this.getBoundingBox(), EntitySelector.pushableBy(this));
        if (!list.isEmpty()) {
            list.forEach(this::push);
        }
        if (this.getBlockY() > this.level().getMaxBuildHeight() + 64) {
            this.pop(this.damageSources().outOfBorder());
        }
    }

    @Override
    protected void applyGravity() {
        double d0 = this.getGravity();
        if (d0 == 0.0) return;
        double d = 1;
        if (this.leashData != null) {
            Entity leashHolder = this.leashData.leashHolder;
            double d1;
            if (leashHolder != null && (d1 = this.getY() - leashHolder.getY()) > 0) {
                d = 1 - Math.max(0, d1 - 3.0);
            }
        }
        if (d == 0.0) return;
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, -d0 * d, 0.0));
    }

    @Override
    protected double getDefaultGravity() {
        return this.isInFluidType() ? -0.04 : -0.01;
    }

    @Override
    public double moreColorful$horizontalWindage() {
        return 3.0;
    }

    public static Vec3 getUntiedRopeBlockPos(Entity entity, float partialTick) {
        if (entity instanceof Balloon) {
            return entity.getPosition(partialTick).subtract(0, 4, 0);
        }
        return Vec3.ZERO;
    }

    public static Vec3 getUntiedRopePos(Entity entity, float partialTick) {
        if (entity instanceof Balloon) {
            Vec3 vec = entity.getDeltaMovement();
            double x = vec.x, z = vec.z;
            if (x * x + z * z < 1.0E-5F) {
                float yRot = ClientWrapper.getCamera().getYRot() * Mth.DEG_TO_RAD + Mth.PI;
                x = Mth.sin(yRot) * 0.01;
                z = -Mth.cos(yRot) * 0.01;
            }
            return entity.getPosition(partialTick).subtract(x, 4, z);
        }
        return Vec3.ZERO;
    }

    public interface Variant {
        Lazy<Variant[]> VALUES = Lazy.of(() -> Maths.concatArray(Variant[]::new, Colour.values(), SpecialVariant.VALUES));
        Codec<Variant> CODEC = ExtraCodecs.orCompressed(
                Codec.stringResolver(Variant::getName, Variant::fromName),
                ExtraCodecs.idResolverCodec(Variant::getIndex, Variant::byIndex, -1)
        );
        StreamCodec<ByteBuf, Variant> STREAM_CODEC = ByteBufCodecs.idMapper(Variant::byIndex, Variant::getIndex);

        String getName();

        int getIndex();

        static Variant[] values() {
            return VALUES.get();
        }

        static int size() {
            return values().length;
        }

        static Variant byIndex(int i) {
            Variant[] values = values();
            if (i >= 0 && i < values.length) {
                return values[i];
            }
            return values[0];
        }

        private static Variant fromName(String name) {
            Variant variant = SpecialVariant.byName(name);
            return variant == null ? Colour.fromName(name) : variant;
        }

        default boolean isSpecial() {
            return this instanceof SpecialVariant;
        }
    }

    public enum SpecialVariant implements Variant {
        ;
        public static final SpecialVariant[] VALUES = values();

        private final String name;

        SpecialVariant(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getIndex() {
            return Colour.size();
        }

        public static SpecialVariant byName(String name) {
            for (SpecialVariant variant : VALUES) {
                if (variant.name.equals(name)) {
                    return variant;
                }
            }
            return null;
        }
    }
}
