package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.util.Selector;
import com.ChalkerCharles.morecolorful.util.WeatherUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.IntFunction;

public class Dragonfly extends Animal implements FlyingAnimal, VariantHolder<Dragonfly.Variant> {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Dragonfly.class, EntityDataSerializers.INT);
    private static final Selector<Variant> VARIANT_SELECTOR = Selector.<Variant>of()
            .add(5, Variant.PURPLE)
            .add(25, Variant.ORANGE, Variant.LIME, Variant.BLUE)
            .add(Variant.RED, Variant.YELLOW, Variant.GREEN, Variant.CYAN, Variant.BROWN, Variant.BLACK)
            .build();

    public Dragonfly(EntityType<? extends Dragonfly> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        Variant variant = VARIANT_SELECTOR.select(pLevel.getRandom());
        if (pSpawnGroupData instanceof DragonflyGroupData data) {
            variant = data.variant;
        } else {
            pSpawnGroupData = new DragonflyGroupData(variant);
        }
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    public static boolean checkSpawnRules(EntityType<Dragonfly> ignore0, LevelAccessor level, MobSpawnType ignore, BlockPos pos, RandomSource ignored) {
        return isDay(level)
                && properWeatherForSpawn(level)
                && level.getBlockState(pos.below()).is(ModTags.Blocks.DRAGONFLY_SPAWNABLE_ON)
                && isBrightEnoughToSpawn(level, pos);
    }

    private static boolean isDay(LevelAccessor level) {
        return !level.dimensionType().hasFixedTime() && level.getSkyDarken() < 4;
    }

    public static boolean properWeatherForSpawn(LevelAccessor levelAccessor) {
        if (levelAccessor instanceof Level level) {
            return !level.isRaining() && WeatherUtils.getGlobalWindSpeed(level).length() < 12;
        }
        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 6.0)
                .add(Attributes.FLYING_SPEED, 0.8F)
                .add(Attributes.MOVEMENT_SPEED, 0.5F);
    }

    @Override
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return pLevel.getBlockState(pPos).isAir() ? 10.0F : 0.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.75));
        this.goalSelector.addGoal(2, new WanderGoal());
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        FlyingPathNavigation pathNavigation = new FlyingPathNavigation(this, pLevel) {
            @Override
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
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
        pBuilder.define(DATA_TYPE_ID, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(Variant.byId(pCompound.getInt("Type")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Type", this.getVariant().ordinal());
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    @Override
    public boolean isFlapping() {
        return this.isFlying() && this.tickCount % 2 == 0;
    }

    @Override
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public boolean isBaby() {
        return false;
    }

    @Override
    public void setBaby(boolean pBaby) {
    }

    @Override
    public void setVariant(Variant variant) {
        this.entityData.set(DATA_TYPE_ID, variant.ordinal());
    }

    @Override
    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_TYPE_ID));
    }

    public int getVariantId() {
        return this.entityData.get(DATA_TYPE_ID);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.DRAGONFLY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DRAGONFLY_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DRAGONFLY_FLUTTER.get();
    }

    public static class DragonflyGroupData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        public DragonflyGroupData(Variant pVariant) {
            super(false);
            this.variant = pVariant;
        }
    }

    private class WanderGoal extends Goal {
        private WanderGoal() {
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return Dragonfly.this.navigation.isDone() && Dragonfly.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean canContinueToUse() {
            return Dragonfly.this.navigation.isInProgress();
        }

        @Override
        public void start() {
            Vec3 vec3 = this.findPos();
            if (vec3 != null) {
                Dragonfly.this.navigation.moveTo(Dragonfly.this.navigation.createPath(BlockPos.containing(vec3), 1), 1.0);
            }
        }

        @Nullable
        private Vec3 findPos() {
            Vec3 vec3 = Dragonfly.this.getViewVector(0.0F);
            Vec3 vec32 = HoverRandomPos.getPos(Dragonfly.this, 8, 7, vec3.x, vec3.z, (float) (Math.PI / 2), 3, 1);
            return vec32 != null ? vec32 : AirAndWaterRandomPos.getPos(Dragonfly.this, 8, 4, -2, vec3.x, vec3.z, (float) (Math.PI / 2));
        }
    }

    public enum Variant {
        RED("red"),
        ORANGE("orange"),
        YELLOW("yellow"),
        LIME("lime"),
        GREEN("green"),
        CYAN("cyan"),
        BLUE("blue"),
        PURPLE("purple"),
        BROWN("brown"),
        BLACK("black");

        private static final IntFunction<Variant> BY_ID = ByIdMap.continuous(Variant::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public final String name;

        Variant(String name) {
            this.name = name;
        }

        public static Variant byId(int i) {
            return BY_ID.apply(i);
        }

        public ResourceLocation getTextureLocation() {
            return MoreColorful.location("textures/entity/dragonfly/" + this.name + ".png");
        }
    }
}
