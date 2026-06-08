package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class Bird extends AbstractBird implements VariantHolder<Bird.Variant> {
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(Bird.class, EntityDataSerializers.INT);
    private static final Variant[] JUNGLE_VARIANTS = new Variant[]{
            Variant.CANARY, Variant.WHITE_EYE, Variant.KINGFISHER
    };
    private static final Variant[] SAVANNA_VARIANTS = new Variant[]{
            Variant.SPARROW, Variant.CARDINAL, Variant.WHITE_EYE
    };
    private static final Variant[] SWAMP_VARIANTS = new Variant[] {
            Variant.SPARROW,Variant.KINGFISHER,Variant.CARDINAL
    };
    private static final Variant[] FOREST_VARIANTS = new Variant[]{
            Variant.SPARROW, Variant.SWALLOW, Variant.ROBIN, Variant.CARDINAL, Variant.NIGHTINGALE, Variant.BLACKBIRD, Variant.MAGPIE, Variant.AZURE, Variant.CUCKOO, Variant.CROW
    };
    private static final Variant[] PLAINS_VARIANTS = new Variant[] {
            Variant.SPARROW, Variant.SWALLOW, Variant.ROBIN, Variant.NIGHTINGALE, Variant.BLACKBIRD, Variant.MAGPIE, Variant.AZURE, Variant.CUCKOO, Variant.CROW
    };
    private static final Variant[] TAIGA_VARIANTS = new Variant[] {
            Variant.SPARROW, Variant.ROBIN, Variant.BLUE_JAY, Variant.NIGHTINGALE, Variant.CUCKOO, Variant.CROW
    };

    public Bird(EntityType<? extends Bird> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        this.setVariant(getRandomVariant(pLevel, this.blockPosition()));
        if (pSpawnGroupData == null) {
            pSpawnGroupData = new AgeableMob.AgeableMobGroupData(false);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    private static Variant getRandomVariant(LevelAccessor level, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        RandomSource random = level.getRandom();
        if (holder.is(BiomeTags.IS_OCEAN) || holder.is(BiomeTags.IS_BEACH)) {
            return Variant.SEAGULL;
        } else if (holder.is(Biomes.MEADOW) || holder.is(Biomes.CHERRY_GROVE)) {
            return random.nextBoolean() ? Variant.SPARROW : Variant.PURPLE_FINCH;
        } else if (holder.is(BiomeTags.IS_JUNGLE)) {
            return Util.getRandom(JUNGLE_VARIANTS, random);
        } else if (holder.is(BiomeTags.IS_SAVANNA)) {
            return Util.getRandom(SAVANNA_VARIANTS, random);
        } else if (holder.is(Tags.Biomes.IS_SWAMP) && !holder.is(Tags.Biomes.IS_COLD)) {
            return Util.getRandom(SWAMP_VARIANTS, random);
        } else if (holder.is(BiomeTags.IS_FOREST)) {
            return Util.getRandom(FOREST_VARIANTS, random);
        } else if (holder.is(BiomeTags.IS_TAIGA) && !holder.is(Tags.Biomes.IS_SNOWY)) {
            return Util.getRandom(TAIGA_VARIANTS, random);
        } else if (holder.is(Tags.Biomes.IS_COLD)) {
            return Variant.BLUE_JAY;
        }
        return Util.getRandom(PLAINS_VARIANTS, random);
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
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_VARIANT_ID, 0);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Variant", this.getVariant().ordinal());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(Variant.byId(pCompound.getInt("Variant")));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.getVariant().ambientSound.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return this.getVariant().hurtSound.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return this.getVariant().deathSound.get();
    }

    public enum Variant implements AbstractBird.Variant {
        SPARROW("sparrow"),
        SWALLOW("swallow"),
        CANARY("canary"),
        ROBIN("robin"),
        CARDINAL("cardinal"),
        BLUE_JAY("blue_jay", ModSounds.BLUE_JAY_TWITTER, ModSounds.BLUE_JAY_HURT, ModSounds.BLUE_JAY_DEATH),
        NIGHTINGALE("nightingale"),
        BLACKBIRD("blackbird"),
        WHITE_EYE("white_eye"),
        KINGFISHER("kingfisher"),
        PURPLE_FINCH("purple_finch"),
        MAGPIE("magpie", true, ModSounds.MAGPIE_CHATTER, ModSounds.MAGPIE_HURT, ModSounds.MAGPIE_DEATH),
        AZURE("azure", true, ModSounds.MAGPIE_CHATTER, ModSounds.MAGPIE_HURT, ModSounds.MAGPIE_DEATH),
        CUCKOO("cuckoo", true, ModSounds.CUCKOO_CALL, ModSounds.CUCKOO_HURT, ModSounds.CUCKOO_DEATH),
        SEAGULL("seagull", true, ModSounds.SEAGULL_SQUAWK, ModSounds.SEAGULL_HURT, ModSounds.SEAGULL_DEATH),
        CROW("crow", true, ModSounds.CROW_CAW, ModSounds.CROW_HURT, ModSounds.CROW_DEATH);

        public static final Variant[] VALUES = values();
        private final String name;
        private final boolean isBig;
        private final Supplier<SoundEvent> ambientSound;
        private final Supplier<SoundEvent> hurtSound;
        private final Supplier<SoundEvent> deathSound;

        Variant(String name, boolean isBig, Supplier<SoundEvent> ambientSound, Supplier<SoundEvent> hurtSound, Supplier<SoundEvent> deathSound) {
            this.name = name;
            this.isBig = isBig;
            this.ambientSound = ambientSound;
            this.hurtSound = hurtSound;
            this.deathSound = deathSound;
        }

        Variant(String name, Supplier<SoundEvent> ambientSound, Supplier<SoundEvent> hurtSound, Supplier<SoundEvent> deathSound) {
            this(name, false, ambientSound, hurtSound, deathSound);
        }

        Variant(String name) {
            this(name, ModSounds.BIRD_CHIRP, ModSounds.BIRD_HURT, ModSounds.BIRD_DEATH);
        }

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public int getIndex() {
            return this.ordinal();
        }

        @Override
        public boolean isBig() {
            return this.isBig;
        }

        public static Variant byId(int id) {
            if (id > 0 && id < VALUES.length) {
                return VALUES[id];
            }
            return SPARROW;
        }
    }
}
