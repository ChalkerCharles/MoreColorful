package com.ChalkerCharles.morecolorful.common.entity.misc;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.entity.EntityUtils;
import com.ChalkerCharles.morecolorful.common.entity.ModEntities;
import com.ChalkerCharles.morecolorful.common.item.ItemUtils;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.mixin.extensions.IEntityExtension;
import com.ChalkerCharles.morecolorful.mixin.extensions.ILeashableExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SandbagEntity extends FallingBlockEntity implements IEntityExtension {
    private static final EntityDataAccessor<Boolean> FALLING = SynchedEntityData.defineId(SandbagEntity.class, EntityDataSerializers.BOOLEAN);

    public SandbagEntity(EntityType<? extends SandbagEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SandbagEntity(Level level, double x, double y, double z, BlockState state) {
        this(ModEntities.SANDBAG.get(), level);
        this.blockState = state;
        this.blocksBuilding = true;
        this.setPos(x, y, z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.setStartPos(this.blockPosition());
    }

    public static SandbagEntity getOrCreate(Level level, BlockPos pos, BlockState blockState) {
        List<SandbagEntity> list = level.getEntitiesOfClass(SandbagEntity.class, new AABB(pos), SandbagEntity::isFixed);
        if (!list.isEmpty()) {
            return list.getFirst();
        }
        SandbagEntity sandbag = new SandbagEntity(
                level,
                pos.getX() + 0.5,
                pos.getY(),
                pos.getZ() + 0.5,
                blockState
        );
        level.addFreshEntity(sandbag);
        return sandbag;
    }

    public void startFall(Level level, BlockPos pos, BlockState blockState) {
        this.blockState = blockState.hasProperty(BlockStateProperties.WATERLOGGED)
                ? blockState.setValue(BlockStateProperties.WATERLOGGED, false)
                : blockState;
        level.setBlock(pos, blockState.getFluidState().createLegacyBlock(), 3);
        this.setFalling(true);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (this.level().isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            if (ItemUtils.isShears(player.getItemInHand(hand))) {
                InteractionResult result = super.interact(player, hand);
                if (result.indicateItemUse()) {
                    return result;
                }
            }
            boolean attachedBalloon = false;
            for (Balloon balloon : EntityUtils.getTiedBalloons(player)) {
                if (ILeashableExtension.canHaveALeashAttachedTo(balloon, this)) {
                    balloon.setLeashedTo(this, true);
                    attachedBalloon = true;
                }
            }

            boolean anyDropped = false;
            if (!attachedBalloon && !player.isSecondaryUseActive()) {
                for (Balloon balloon : EntityUtils.getTiedBalloons(this)) {
                    if (ILeashableExtension.canHaveALeashAttachedTo(balloon, player)) {
                        balloon.setLeashedTo(player, true);
                        anyDropped = true;
                    }
                }
            }

            if (attachedBalloon || anyDropped) {
                this.gameEvent(GameEvent.BLOCK_ATTACH, player);
                this.playSound(ModSounds.LEAD_TIED.get());
                return InteractionResult.SUCCESS;
            } else {
                return super.interact(player, hand);
            }
        }
    }

    public boolean isFixed() {
        return !this.isFalling();
    }

    public boolean isFalling() {
        return this.entityData.get(FALLING);
    }

    public void setFalling(boolean falling) {
        this.entityData.set(FALLING, falling);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(FALLING, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Falling", this.isFalling());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setFalling(pCompound.getBoolean("Falling"));
    }

    @Override
    public ItemStack getPickResult() {
        return ModItems.SANDBAG.toStack();
    }

    @Override
    public void tick() {
        if (this.isFalling()) {
            super.tick();
        } else {
            if (!this.level().getBlockState(this.blockPosition()).is(ModBlocks.SANDBAG)
                    || !this.hasTiedBalloon()) {
                this.discard();
            }
            this.time = 0;
        }
    }

    @Override
    public void move(MoverType pType, Vec3 pPos) {
        if (this.isFalling()) {
            super.move(pType, pPos);
        }
    }

    @Override
    public void push(double pX, double pY, double pZ) {
        if (this.isFalling()) {
            super.push(pX, pY, pZ);
        }
    }

    @Override
    protected void applyGravity() {
        if (this.isFalling()) {
            super.applyGravity();
        }
    }

    public boolean hasTiedBalloon() {
        return !EntityUtils.getTiedBalloons(this).isEmpty();
    }

    @Override
    public void moreColorful$notifyLeashRemoved(Leashable leashable) {
        if (this.isFixed()) {
            if (ILeashableExtension.leashableLeashedTo(this).isEmpty()) {
                this.discard();
            }
        }
    }
}
