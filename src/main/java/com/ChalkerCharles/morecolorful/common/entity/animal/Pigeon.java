package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.attachment.LevelSavedData;
import com.ChalkerCharles.morecolorful.common.attachment.ServerLevelData;
import com.ChalkerCharles.morecolorful.common.block.entity.MailboxBlockEntity;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import com.ChalkerCharles.morecolorful.common.item.component.MailContent;
import com.ChalkerCharles.morecolorful.common.level.MailCallback;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LandOnOwnersShoulderGoal;
import net.minecraft.world.entity.ai.util.AirRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Pigeon extends AbstractBird implements VariantHolder<Pigeon.Variant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Byte> DATA_POST_STATE = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Integer> DATA_POSTING_TICK = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_POSTED_TICK = SynchedEntityData.defineId(Pigeon.class, EntityDataSerializers.INT);
    private static final ResourceLocation POSTING_SPEED_MODIFIER = MoreColorful.location("posting");
    private BlockPos mailbox;
    private BlockPos lastPos;

    public Pigeon(EntityType<? extends Pigeon> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        this.setVariant(Util.getRandom(Variant.VALUES, pLevel.getRandom()));
        if (pSpawnGroupData == null) {
            pSpawnGroupData = new AgeableMob.AgeableMobGroupData(false);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    @Override
    public void setVariant(Variant pVariant) {
        this.entityData.set(DATA_VARIANT_ID, pVariant.ordinal());
    }

    @Override
    public Variant getVariant() {
        return Variant.byId(this.entityData.get(DATA_VARIANT_ID));
    }

    @Override
    protected FollowOwnerGoal followOwnerGoal() {
        return new FollowOwnerGoal(this, 1.0, 5.0F, 1.0F) {
            @Override
            public boolean canUse() {
                return Pigeon.this.getPostState() == 0 && super.canUse();
            }

            @Override
            public boolean canContinueToUse() {
                return Pigeon.this.getPostState() == 0 && super.canContinueToUse();
            }
        };
    }

    @Override
    protected LandOnOwnersShoulderGoal landOnOwnersShoulderGoal() {
        return new LandOnOwnersShoulderGoal(this) {
            @Override
            public boolean canUse() {
                return Pigeon.this.getPostState() == 0 && super.canUse();
            }
        };
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_VARIANT_ID, 0);
        pBuilder.define(DATA_POST_STATE, (byte) 0);
        pBuilder.define(DATA_POSTING_TICK, 0);
        pBuilder.define(DATA_POSTED_TICK, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getVariant().ordinal());
        pCompound.putByte("PostState", this.getPostState());
        if (this.mailbox != null) {
            pCompound.putLong("Mailbox", this.mailbox.asLong());
        }
        if (this.lastPos != null) {
            pCompound.putLong("LastPos", this.lastPos.asLong());
        }
        pCompound.putInt("PostingTick", this.getPostingTick());
        pCompound.putInt("PostedTick", this.getPostedTick());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(Variant.byId(pCompound.getInt("Variant")));
        this.setPostState(pCompound.getByte("PostState"));
        if (pCompound.contains("Mailbox")) {
            this.mailbox = BlockPos.of(pCompound.getLong("Mailbox"));
        }
        if (pCompound.contains("LastPos")) {
            this.lastPos = BlockPos.of(pCompound.getLong("LastPos"));
        }
        this.setPostingTick(pCompound.getInt("PostingTick"));
        this.setPostedTick(pCompound.getInt("PostedTick"));
    }

    public byte getPostState() {
        return this.entityData.get(DATA_POST_STATE);
    }

    public void setPostState(byte state) {
        this.entityData.set(DATA_POST_STATE, state);
    }

    public int getPostingTick() {
        return this.entityData.get(DATA_POSTING_TICK);
    }

    public void setPostingTick(int i) {
        this.entityData.set(DATA_POSTING_TICK, i);
    }

    public int getPostedTick() {
        return this.entityData.get(DATA_POSTED_TICK);
    }

    public void setPostedTick(int i) {
        this.entityData.set(DATA_POSTED_TICK, i);
    }

    @Override
    protected SoundEvent getEatSound() {
        return ModSounds.PIGEON_EAT.get();
    }

    @Override
    protected boolean onInteract(Player player, ItemStack stack) {
        if (this.getPostState() != 0 || this.isLeashed()) return false;
        if (this.isTame() && this.isOwnedBy(player) && stack.is(ModItems.MAIL) && !this.level().isClientSide) {
            if (this.tryParseMail(stack)) {
                this.level().broadcastEntityEvent(this, (byte)14);
                this.startPost(stack.copyWithCount(1));
                stack.consume(1, player);
                return true;
            } else {
                this.level().broadcastEntityEvent(this, (byte)13);
            }
        }
        return false;
    }

    private boolean tryParseMail(ItemStack stack) {
        MailContent content = stack.get(ModDataComponents.MAIL_CONTENT);
        if (content == null) return false;
        String recipient = content.recipient();
        return LevelSavedData.getMailboxes(this.level()).containsKey(recipient);
    }

    private void startPost(ItemStack stack) {
        MailContent content = stack.getOrDefault(ModDataComponents.MAIL_CONTENT, MailContent.DEFAULT);
        String recipient = content.recipient();
        LongSet set = LevelSavedData.getMailboxes(this.level()).get(recipient);
        if (set != null && !set.isEmpty()) {
            this.setOrderedToSit(false);
            this.setPostState((byte) 1);
            this.setItemInHand(InteractionHand.MAIN_HAND, stack);
            this.mailbox = this.findNearestMailbox(set);
            this.lastPos = this.blockPosition();
            AttributeInstance attributeInstance = this.getAttribute(Attributes.FLYING_SPEED);
            if (attributeInstance != null) {
                attributeInstance.addOrReplacePermanentModifier(new AttributeModifier(POSTING_SPEED_MODIFIER, 2, AttributeModifier.Operation.ADD_MULTIPLIED_BASE));
            }
            double d = this.mailbox.distSqr(this.lastPos);
            int i = Mth.ceillog2((int) d + 1) * 20;
            this.setPostingTick(i);
            this.setPostedTick(i);
            this.pathfindRandomlyTowards(this.mailbox);
        }
    }

    private BlockPos findNearestMailbox(LongSet set) {
        return set.longStream()
                .mapToObj(BlockPos::of)
                .min(this::nearestMailbox)
                .orElseThrow();
    }

    private int nearestMailbox(BlockPos pos1, BlockPos pos2) {
        return Double.compare(pos1.distSqr(this.blockPosition()), pos2.distSqr(this.blockPosition()));
    }

    private void tryPost() {
        this.setPostState((byte) 2);
        if (this.mailbox == null) return;
        if (this.level().isLoaded(this.mailbox)) {
            BlockEntity blockEntity = this.level().getBlockEntity(this.mailbox);
            if (blockEntity instanceof MailboxBlockEntity mailboxBlock) {
                mailboxBlock.tryInsertIn(this.getItemInHand(InteractionHand.MAIN_HAND).copy());
            }
        } else if (this.level() instanceof ServerLevel serverLevel) {
            ServerLevelData.addMailCallback(
                    serverLevel, ChunkPos.asLong(this.mailbox), new MailCallback(this.mailbox.asLong(), this.getItemInHand(InteractionHand.MAIN_HAND).copy())
            );
        }
    }

    private void endPost() {
        this.setPostState((byte) 0);
        this.mailbox = null;
        this.lastPos = null;
        AttributeInstance attributeInstance = this.getAttribute(Attributes.FLYING_SPEED);
        if (attributeInstance != null) {
            attributeInstance.removeModifier(POSTING_SPEED_MODIFIER);
        }
    }

    private void pathfindRandomlyTowards(BlockPos pPos) {
        if (pPos == null) return;
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

    @Override
    public void tick() {
        byte state = this.getPostState();
        if (state != 0) {
            int postingTick = this.getPostingTick();
            int postedTick = this.getPostedTick();
            if (state == 1) {
                postingTick--;
                this.setPostingTick(postingTick);
            } else {
                postedTick--;
                this.setPostedTick(postedTick);
            }
            if (state == 1 && postingTick == 0) {
                this.tryPost();
                this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                this.pathfindRandomlyTowards(this.lastPos);
            }
            if (state == 2 && postedTick == 0) {
                this.endPost();
            }
        }
        super.tick();
    }

    @Override
    protected SoundEvent getFlapSound() {
        return ModSounds.PIGEON_FLY.get();
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (this.getPostState() != 0 && !pSource.is(Tags.DamageTypes.IS_TECHNICAL)) {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PIGEON_COO.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.PIGEON_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PIGEON_DEATH.get();
    }

    @Override
    public void handleEntityEvent(byte pId) {
        if (pId == 13) {
            this.addParticlesAroundSelf(ParticleTypes.ANGRY_VILLAGER);
        } else if (pId == 14) {
            this.addParticlesAroundSelf(ParticleTypes.HAPPY_VILLAGER);
        } else {
            super.handleEntityEvent(pId);
        }
    }

    private void addParticlesAroundSelf(ParticleOptions options) {
        for (int i = 0; i < 5; i++) {
            double d0 = this.random.nextGaussian() * 0.02;
            double d1 = this.random.nextGaussian() * 0.02;
            double d2 = this.random.nextGaussian() * 0.02;
            this.level().addParticle(options, this.getRandomX(1.0), this.getRandomY() + 1.0, this.getRandomZ(1.0), d0, d1, d2);
        }
    }

    @Override
    public boolean shouldTryTeleportToOwner() {
        return this.getPostState() == 0 && super.shouldTryTeleportToOwner();
    }

    public boolean isInvisibleWhenPosting() {
        int i = this.getPostedTick();
        int d = i - this.getPostingTick();
        return (this.getPostState() == 1 && d >= 40)
                || (this.getPostState() == 2 && i >= 40);
    }

    @Override
    public boolean isInvisible() {
        return super.isInvisible() || this.isInvisibleWhenPosting();
    }

    public boolean isFadingInOrFadingOut() {
        int i = this.getPostedTick();
        int d = i - this.getPostingTick();
        return (this.getPostState() == 1 && d >= 20 && d < 40)
                || (this.getPostState() == 2 && i >= 20 && i < 40);
    }

    public float getOpacityDelta(float partialTicks) {
        int i = this.getPostedTick();
        int d = i - this.getPostingTick();
        if (this.getPostState() == 1) {
            return (40 - d - partialTicks) / 20.0F;
        } else if (this.getPostState() == 2) {
            return (40 - i - partialTicks) / 20.0F;
        }
        return 0;
    }

    public enum Variant implements AbstractBird.Variant {
        PIGEON("pigeon"),
        DOVE("dove"),
        TURTLEDOVE("turtledove");

        public static final Variant[] VALUES = values();
        private final String name;

        Variant(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getIndex() {
            return Bird.Variant.VALUES.length + this.ordinal();
        }

        @Override
        public boolean isBig() {
            return true;
        }

        public static Variant byId(int id) {
            if (id > 0 && id < VALUES.length) {
                return VALUES[id];
            }
            return PIGEON;
        }
    }
}
