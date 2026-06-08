package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.entity.ModAttributes;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.util.Maths;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidType;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.Optional;
import java.util.function.Predicate;

public abstract class AbstractMoth extends Animal implements FlyingAnimal {
    private static final EntityDataAccessor<Byte> DATA_RESTING = SynchedEntityData.defineId(AbstractMoth.class, EntityDataSerializers.BYTE);
    public static final HolderSet<Block> INVALID_FLOWERS = HolderSet.direct(
            ModBlocks.CLOSED_DAYBLOOM,
            ModBlocks.CLOSED_WATER_LILY,
            ModBlocks.CLOSED_WHITE_WATER_LILY,
            ModBlocks.CLOSED_BLUE_WATER_LILY
    );
    private int remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(this.random, 20, 60);
    private int remainingPollinateTicks = Mth.nextInt(this.random, 1200, 2400);
    private int restTicks = Mth.nextInt(this.random, 600, 1200);
    @Nullable
    private BlockPos savedFlowerPos;
    private BlockPos targetPosition;
    private PollinateGoal pollinateGoal;

    protected AbstractMoth(EntityType<? extends AbstractMoth> type, Level level) {
        super(type, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.lookControl = new LookControl(this) {
            @Override
            protected boolean resetXRotOnTick() {
                return !AbstractMoth.this.pollinateGoal.isPollinating();
            }
        };
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    public abstract Variant getVariant();

    public abstract void setVariant(Variant variant);

    protected abstract boolean isActive();

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.FLYING_SPEED, 0.6F)
                .add(Attributes.MOVEMENT_SPEED, 0.3F)
                .add(ModAttributes.WEIGHT, 0.5);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10.0F : 0.0F;
    }

    public static boolean properWeatherForSpawn(LevelAccessor levelAccessor) {
        if (levelAccessor instanceof Level level) {
            return !level.isRaining() && WeatherUtils.getGlobalWindSpeed(level).length() < 12;
        }
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, this::isFood, false) {
            @Override
            public void start() {
                super.start();
                AbstractMoth.this.stopResting();
            }
        });
        this.pollinateGoal = new PollinateGoal();
        this.goalSelector.addGoal(4, this.pollinateGoal);
        this.goalSelector.addGoal(6, new GoToKnownFlowerGoal());
        this.goalSelector.addGoal(8, new WanderGoal());
        this.goalSelector.addGoal(9, new FloatGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, pLevel) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }

            @Override
            public void tick() {
                if (!AbstractMoth.this.pollinateGoal.isPollinating() && !AbstractMoth.this.isResting()) {
                    super.tick();
                }
            }
        };
        pathNavigation.setCanOpenDoors(false);
        pathNavigation.setCanFloat(false);
        pathNavigation.setCanPassDoors(true);
        return pathNavigation;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_RESTING, (byte) 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.savedFlowerPos = NbtUtils.readBlockPos(pCompound, "flower_pos").orElse(null);
        this.setResting(pCompound.getByte("Resting"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        if (this.savedFlowerPos != null) {
            pCompound.put("flower_pos", NbtUtils.writeBlockPos(this.savedFlowerPos));
        }
        pCompound.putByte("Resting", this.getResting());
    }

    public void setResting(byte value) {
        this.entityData.set(DATA_RESTING, value);
    }

    public byte getResting() {
        return this.entityData.get(DATA_RESTING);
    }

    public boolean isResting() {
        return this.getResting() != 0;
    }

    public Direction getRestingFace() {
        return Direction.from3DDataValue(this.getResting());
    }

    private void stopResting() {
        this.targetPosition = null;
        this.setResting((byte) 0);
    }

    @Override
    public void playAmbientSound() {
        if (!this.isResting()) {
            super.playAmbientSound();
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isResting()) {
            if (this.getResting() > 1) {
                this.setDeltaMovement(Vec3.ZERO);
                Direction face = this.getRestingFace();
                float rot = face.getOpposite().toYRot();
                this.setYRot(rot);
                this.setYHeadRot(rot);
            } else {
                Vec3 vec = this.getDeltaMovement();
                this.setDeltaMovement(0, vec.y, 0);
            }
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide) {
            if (this.remainingCooldownBeforeLocatingNewFlower > 0) {
                this.remainingCooldownBeforeLocatingNewFlower--;
            }
            if (this.remainingPollinateTicks > 0) {
                this.remainingPollinateTicks--;
            }
            if (this.isActive() && this.restTicks > 0) {
                this.restTicks--;
            }
        }
    }

    @Override
    protected void customServerAiStep() {
        super.customServerAiStep();
        if (this.isPanicking() || this.isBeingTempted() || this.isInLove()) return;
        BlockPos pos = this.blockPosition();
        if (this.isResting()) {
            Direction face = this.getRestingFace();
            BlockPos pos1 = pos.relative(face.getOpposite());
            BlockState state = this.level().getBlockState(pos1);
            if (!this.shouldRest() || !state.isFaceSturdy(this.level(), pos1, face) || this.restTicks == 0) {
                this.stopResting();
                this.navigation.stop();
                this.remainingPollinateTicks = Mth.nextInt(AbstractMoth.this.random, 1200, 2400);
            }
        } else if (this.shouldRest()) {
            if (this.targetPosition == null || this.random.nextInt(30) == 0) {
                this.targetPosition = BlockPos.containing(
                        this.getX() + this.random.nextInt(7) - this.random.nextInt(7),
                        this.getY() + this.random.nextInt(6) - 2.0,
                        this.getZ() + this.random.nextInt(7) - this.random.nextInt(7)
                );
                if (this.level().isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.level().getMinBuildHeight()) {
                    this.targetPosition = null;
                }
            }
            if (this.targetPosition != null) {
                int i = this.targetPosition.getX() - pos.getX();
                int j = this.targetPosition.getY() - pos.getY();
                int k = this.targetPosition.getZ() - pos.getZ();
                Direction direction = Direction.getNearest(i, j, k).getOpposite();
                BlockState state = this.level().getBlockState(this.targetPosition);
                if (this.navigation.isDone()) {
                    if (state.isFaceSturdy(this.level(), this.targetPosition, direction)) {
                        this.pathfindRandomlyTowards(this.targetPosition);
                    } else {
                        this.targetPosition = null;
                    }
                }
                if (this.closeToTargetPosition(direction)) {
                    this.setResting((byte) direction.get3DDataValue());
                    this.attachToBlock(direction);
                    this.restTicks = Mth.nextInt(this.random, 600, 1200);
                    if (direction.getAxis().isHorizontal()) {
                        float rot = direction.getOpposite().toYRot();
                        this.setYRot(rot);
                        this.setYHeadRot(rot);
                    }
                }
            }
        }
    }

    private boolean closeToTargetPosition(Direction direction) {
        if (this.targetPosition == null || direction == Direction.DOWN) return false;
        return this.targetPosition.relative(direction).equals(this.blockPosition());
    }

    private void attachToBlock(Direction direction) {
        int x0 = this.targetPosition.getX(), y0 = this.targetPosition.getY(), z0 = this.targetPosition.getZ();
        Vec3 pos = this.position();
        float width = this.getBbWidth() * 0.5F;
        switch (direction) {
            case UP -> this.setPos(pos.x, y0 + 1, pos.z);
            case NORTH -> this.setPos(pos.x, pos.y, z0 - width);
            case SOUTH -> this.setPos(pos.x, pos.y, z0 + 1 + width);
            case WEST -> this.setPos(x0 - width, pos.y, pos.z);
            case EAST ->this.setPos(x0 + 1 + width, pos.y, pos.z);
        }
    }

    private boolean isBeingTempted() {
        for (WrappedGoal wrappedgoal : this.goalSelector.getAvailableGoals()) {
            if (wrappedgoal.isRunning() && wrappedgoal.getGoal() instanceof TemptGoal) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.BEE_FOOD);
    }

    private boolean isFlowerValid(BlockPos pPos) {
        if (this.level().isLoaded(pPos)) {
            BlockState state = this.level().getBlockState(pPos);
            return state.is(BlockTags.FLOWERS) && !state.is(INVALID_FLOWERS);
        }
        return false;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        Caterpillar caterpillar = ModEntities.CATERPILLAR.get().create(pLevel);
        if (caterpillar != null && pOtherParent instanceof AbstractMoth abstractMoth) {
            caterpillar.setVariant(this.random.nextBoolean() ? this.getVariant() : abstractMoth.getVariant());
            caterpillar.setAge(-24000);
            caterpillar.setPersistenceRequired();
        }
        return caterpillar;
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean pBaby) {
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void playStepSound(BlockPos pPos, BlockState pBlock) {
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean isFlapping() {
        return this.isFlying() && this.tickCount % 4 == 0;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    private void pathfindRandomlyTowards(BlockPos pPos) {
        Vec3 vec3 = Vec3.atBottomCenterOf(pPos);
        int i = 0;
        BlockPos blockpos = this.blockPosition();
        int j = (int)vec3.y - blockpos.getY();
        if (j > 2) {
            i = 4;
        } else if (j < -2) {
            i = -4;
        }

        int k = 6;
        int l = 8;
        int i1 = blockpos.distManhattan(pPos);
        if (i1 < 15) {
            k = i1 / 2;
            l = i1 / 2;
        }

        Vec3 vec31 = AirRandomPos.getPosTowards(this, k, l, i, vec3, (float) (Math.PI / 10));
        if (vec31 != null) {
            this.navigation.setMaxVisitedNodesMultiplier(0.5F);
            this.navigation.moveTo(vec31.x, vec31.y, vec31.z, 1.0);
        }
    }

    private boolean closerThan(BlockPos pPos, int pDistance) {
        return pPos.closerThan(this.blockPosition(), pDistance);
    }

    private boolean isTooFarAway(BlockPos pPos) {
        return !this.closerThan(pPos, 32);
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.isInvulnerableTo(pSource)) {
            return false;
        } else {
            if (!this.level().isClientSide) {
                this.pollinateGoal.stopPollinating();
                this.stopResting();
            }

            return super.hurt(pSource, pAmount);
        }
    }

    @Override
    public boolean fireImmune() {
        return this.getVariant().isNether() || super.fireImmune();
    }

    @Override
    public void jumpInFluid(FluidType type) {
        this.setDeltaMovement(this.getDeltaMovement().add(0.0, 0.01, 0.0));
    }

    private boolean shouldRest() {
        if (!this.isActive()) {
            return true;
        }
        return this.remainingPollinateTicks == 0;
    }

    private class GoToKnownFlowerGoal extends Goal {
        private int travellingTicks = AbstractMoth.this.level().random.nextInt(10);

        private GoToKnownFlowerGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return AbstractMoth.this.savedFlowerPos != null
                    && !AbstractMoth.this.hasRestriction()
                    && AbstractMoth.this.isFlowerValid(AbstractMoth.this.savedFlowerPos)
                    && !AbstractMoth.this.closerThan(AbstractMoth.this.savedFlowerPos, 2);
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }

        @Override
        public void start() {
            this.travellingTicks = 0;
            super.start();
        }

        @Override
        public void stop() {
            this.travellingTicks = 0;
            AbstractMoth.this.navigation.stop();
            AbstractMoth.this.navigation.resetMaxVisitedNodesMultiplier();
        }

        @Override
        public void tick() {
            if (AbstractMoth.this.savedFlowerPos != null) {
                this.travellingTicks++;
                if (this.travellingTicks > this.adjustedTickDelay(600)) {
                    AbstractMoth.this.savedFlowerPos = null;
                } else if (!AbstractMoth.this.navigation.isInProgress()) {
                    if (AbstractMoth.this.isTooFarAway(AbstractMoth.this.savedFlowerPos)) {
                        AbstractMoth.this.savedFlowerPos = null;
                    } else {
                        AbstractMoth.this.pathfindRandomlyTowards(AbstractMoth.this.savedFlowerPos);
                    }
                }
            }
        }
    }

    private class PollinateGoal extends Goal {
        private static final Predicate<BlockState> VALID_POLLINATION_BLOCKS = state -> {
            if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED) || state.is(INVALID_FLOWERS)) {
                return false;
            } else if (state.is(BlockTags.FLOWERS)) {
                return !state.is(Blocks.SUNFLOWER) || state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER;
            } else {
                return false;
            }
        };
        private int successfulPollinatingTicks;
        private boolean pollinating;
        @Nullable
        private Vec3 hoverPos;
        private int pollinatingTicks;

        private PollinateGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            if (AbstractMoth.this.remainingCooldownBeforeLocatingNewFlower > 0) {
                return false;
            } else if (AbstractMoth.this.level().isRaining()) {
                return false;
            } else if (AbstractMoth.this.shouldRest()) {
                return false;
            } else {
                Optional<BlockPos> optional = this.findNearbyFlower();
                if (optional.isPresent()) {
                    AbstractMoth.this.savedFlowerPos = optional.get();
                    AbstractMoth.this.navigation
                            .moveTo(
                                    AbstractMoth.this.savedFlowerPos.getX() + 0.5,
                                    AbstractMoth.this.savedFlowerPos.getY() + 0.5,
                                    AbstractMoth.this.savedFlowerPos.getZ() + 0.5,
                                    1.2F
                            );
                    return true;
                } else {
                    AbstractMoth.this.remainingCooldownBeforeLocatingNewFlower = Mth.nextInt(AbstractMoth.this.random, 20, 60);
                    return false;
                }
            }
        }

        @Override
        public boolean canContinueToUse() {
            if (!this.pollinating) {
                return false;
            } else if (AbstractMoth.this.savedFlowerPos == null) {
                return false;
            } else if (AbstractMoth.this.level().isRaining()) {
                return false;
            } else if (AbstractMoth.this.shouldRest()) {
                return false;
            } else if (this.hasPollinatedLongEnough()) {
                return AbstractMoth.this.random.nextFloat() < 0.2F;
            } else if (AbstractMoth.this.tickCount % 20 == 0 && !AbstractMoth.this.isFlowerValid(AbstractMoth.this.savedFlowerPos)) {
                AbstractMoth.this.savedFlowerPos = null;
                return false;
            } else {
                return true;
            }
        }

        private boolean hasPollinatedLongEnough() {
            return this.successfulPollinatingTicks > 400;
        }

        private boolean isPollinating() {
            return this.pollinating;
        }

        private void stopPollinating() {
            this.pollinating = false;
        }

        @Override
        public void start() {
            this.successfulPollinatingTicks = 0;
            this.pollinatingTicks = 0;
            this.pollinating = true;
        }

        @Override
        public void stop() {
            this.pollinating = false;
            AbstractMoth.this.navigation.stop();
            AbstractMoth.this.remainingCooldownBeforeLocatingNewFlower = 200;
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            this.pollinatingTicks++;
            if (this.pollinatingTicks > 600) {
                AbstractMoth.this.savedFlowerPos = null;
            } else if (AbstractMoth.this.savedFlowerPos != null) {
                Vec3 vec3 = Vec3.atBottomCenterOf(AbstractMoth.this.savedFlowerPos).add(0.0, 0.6F, 0.0);
                if (vec3.distanceTo(AbstractMoth.this.position()) > 1.0) {
                    this.hoverPos = vec3;
                    this.setWantedPos();
                } else {
                    if (this.hoverPos == null) {
                        this.hoverPos = vec3;
                    }

                    boolean flag = AbstractMoth.this.position().distanceTo(this.hoverPos) <= 0.1;
                    boolean flag1 = true;
                    if (!flag && this.pollinatingTicks > 600) {
                        AbstractMoth.this.savedFlowerPos = null;
                    } else {
                        if (flag) {
                            boolean flag2 = AbstractMoth.this.random.nextInt(25) == 0;
                            if (flag2) {
                                this.hoverPos = new Vec3(vec3.x() + (double)this.getOffset(), vec3.y(), vec3.z() + (double)this.getOffset());
                                AbstractMoth.this.navigation.stop();
                            } else {
                                flag1 = false;
                            }

                            AbstractMoth.this.getLookControl().setLookAt(vec3.x(), vec3.y(), vec3.z());
                        }

                        if (flag1) {
                            this.setWantedPos();
                        }

                        this.successfulPollinatingTicks++;
                    }
                }
            }
        }

        private void setWantedPos() {
            if (this.hoverPos != null) {
                AbstractMoth.this.getMoveControl().setWantedPosition(this.hoverPos.x(), this.hoverPos.y(), this.hoverPos.z(), 0.35F);
            }
        }

        private float getOffset() {
            return (AbstractMoth.this.random.nextFloat() * 2.0F - 1.0F) * 0.33333334F;
        }

        private Optional<BlockPos> findNearbyFlower() {
            BlockPos blockpos = AbstractMoth.this.blockPosition();
            BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

            for (int i = 0; i <= 5.0; i = i > 0 ? -i : 1 - i) {
                for (int j = 0; j < 5.0; j++) {
                    for (int k = 0; k <= j; k = k > 0 ? -k : 1 - k) {
                        for (int l = k < j && k > -j ? j : 0; l <= j; l = l > 0 ? -l : 1 - l) {
                            mutablePos.setWithOffset(blockpos, k, i - 1, l);
                            if (blockpos.closerThan(mutablePos, 5.0)
                                    && VALID_POLLINATION_BLOCKS.test(AbstractMoth.this.level().getBlockState(mutablePos))) {
                                return Optional.of(mutablePos);
                            }
                        }
                    }
                }
            }

            return Optional.empty();
        }
    }

    private class WanderGoal extends Goal {
        private WanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return AbstractMoth.this.navigation.isDone() && AbstractMoth.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return AbstractMoth.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                AbstractMoth.this.navigation.moveTo(AbstractMoth.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3 = AbstractMoth.this.getViewVector(0.0F);
            Vec3 vec32 = HoverRandomPos.getPos(AbstractMoth.this, 8, 7, vec3.x, vec3.z, (float) (Math.PI / 2), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(AbstractMoth.this, 8, 4, -2, vec3.x, vec3.z, (float) (Math.PI / 2));
        }
    }

    public interface Variant {
        Lazy<Variant[]> VALUES = Lazy.of(() -> Maths.concatArray(Variant[]::new, Butterfly.Variant.VALUES, Moth.Variant.VALUES));
        Codec<Variant> CODEC = ExtraCodecs.orCompressed(
                Codec.stringResolver(Variant::getName, Variant::fromName),
                ExtraCodecs.idResolverCodec(Variant::getIndex, Variant::byIndex, -1)
        );

        StreamCodec<ByteBuf, Variant> STREAM_CODEC = ByteBufCodecs.idMapper(Variant::byIndex, Variant::getIndex);

        String getName();

        int getIndex();

        String getTextureName();

        boolean isNether();

        boolean canGlow();

        // TODO: remove it later
        boolean unavailable();

        static Variant byIndex(int i) {
            if (i > 31) {
                return Moth.Variant.byIndex(i - 32);
            } else {
                return Butterfly.Variant.byIndex(i);
            }
        }

        static Variant fromName(String name) {
            Variant variant = Moth.Variant.byName(name);
            return variant == null ? Butterfly.Variant.byName(name) : variant;
        }

        static Variant[] values() {
            return VALUES.get();
        }

        default boolean isButterfly() {
            return this instanceof Butterfly.Variant;
        }

        default MutableComponent getDescription() {
            return Component.translatable("info.morecolorful.moth." + this.getName());
        }
    }
}
