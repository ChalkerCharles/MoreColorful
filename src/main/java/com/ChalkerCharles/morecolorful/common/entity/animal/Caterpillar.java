package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.entity.CocoonBlockEntity;
import com.ChalkerCharles.morecolorful.common.entity.ModAttributes;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.util.Maths;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.Nullable;

public class Caterpillar extends Animal implements VariantHolder<AbstractMoth.Variant> {
    private static final EntityDataAccessor<Boolean> DATA_CLIMBING = SynchedEntityData.defineId(Caterpillar.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Caterpillar.class, EntityDataSerializers.INT);

    public Caterpillar(EntityType<? extends Caterpillar> type, Level level) {
        super(type, level);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.WATER, -1.0F);
        this.setPathfindingMalus(PathType.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(PathType.COCOA, -1.0F);
        this.setPathfindingMalus(PathType.FENCE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 3.0)
                .add(Attributes.MOVEMENT_SPEED, 0.1F)
                .add(ModAttributes.WEIGHT, 0.2);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
        this.goalSelector.addGoal(2, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, this::isFood, false));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    @Override
    protected PathNavigation createNavigation(Level pLevel) {
        return new WallClimberNavigation(this, pLevel);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_TYPE_ID, 0);
        pBuilder.define(DATA_CLIMBING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(AbstractMoth.Variant.byIndex(pCompound.getInt("Type")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Type", this.getVariant().getIndex());
    }

    @Override
    public void setVariant(AbstractMoth.Variant variant) {
        this.entityData.set(DATA_TYPE_ID, variant.getIndex());
    }

    @Override
    public AbstractMoth.Variant getVariant() {
        return AbstractMoth.Variant.byIndex(this.entityData.get(DATA_TYPE_ID));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        this.setAge(-24000);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return this.entityData.get(DATA_CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(DATA_CLIMBING, climbing);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbing(this.horizontalCollision);
            if (this.horizontalCollision) {
                this.setDeltaMovement(this.getDeltaMovement().multiply(1, 0.5, 1));
            }
        }
    }

    @Override
    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(ItemTags.BEE_FOOD);
    }

    @Override
    public boolean isBaby() {
        return true;
    }

    @Override
    public void setBaby(boolean pBaby) {
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    @Override
    public float getAgeScale() {
        return 1.0F;
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
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.CATERPILLAR_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.CATERPILLAR_DEATH.get();
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();
        if (this.getAge() >= 0) {
            this.unRide();
            this.pupate();
            this.playSound(ModSounds.CATERPILLAR_PUPATE.get(), 0.7F, 1.0F);
            this.discard();
        }
    }

    private void pupate() {
        CompoundTag tag = new CompoundTag();
        CustomData data = this.save(tag) ? CustomData.of(tag) : CustomData.EMPTY;
        BlockPos pos = this.blockPosition();
        Level level = this.level();
        if (placeCocoon(level, pos, data)) return;
        for (Direction direction : Maths.DIRECTIONS) {
            BlockPos pos1 = pos.relative(direction);
            if (placeCocoon(level, pos1, data)) return;
        }
        ItemStack item = ModItems.COCOON.toStack();
        item.set(DataComponents.ENTITY_DATA, data);
        this.spawnAtLocation(item);
    }

    private static boolean placeCocoon(Level level, BlockPos pos, CustomData data) {
        if (level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::canBeReplaced)) {
            level.setBlock(pos, ModBlocks.COCOON.get().defaultBlockState(), 3);
            if (level.getBlockEntity(pos) instanceof CocoonBlockEntity cocoon) {
                cocoon.setData(data);
            }
            return true;
        }
        return false;
    }
}
