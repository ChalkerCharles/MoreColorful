package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.common.ModSounds;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.worldgen.biomes.ModBiomes;
import com.ChalkerCharles.morecolorful.util.Selector;
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
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Butterfly extends AbstractMoth implements VariantHolder<Butterfly.Variant> {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Butterfly.class, EntityDataSerializers.INT);
    private static final Selector<Variant> JUNGLE_VARIANTS = Selector.<Variant>of()
            .add(3, Variant.ALEXANDRA)
            .add(12, Variant.AGRIAS)
            .add(Variant.MORPHO, Variant.GLASSWING, Variant.JULIA, Variant.EMERALD, Variant.BIRDWING)
            .build();
    private static final Selector<Variant> MEADOW_VARIANTS = Selector.<Variant>of()
            .add(40, Variant.SWALLOWTAIL)
            .add(30, Variant.MOURNING_CLOAK)
            .add(Variant.WHITE, Variant.BRIMSTONE)
            .build();
    private static final Selector<Variant> FLORAL_VARIANTS = Selector.<Variant>of()
            .add(Variant.MONARCH, Variant.BLUEBOTTLE, Variant.RED_ADMIRAL, Variant.CONSTABLE, Variant.TREE_NYMPH, Variant.TAILED_JAY, Variant.MORMON)
            .build();
    private static final Selector<Variant> BIRCH_VARIANTS = Selector.<Variant>of()
            .add(30, Variant.TREE_NYMPH)
            .add(Variant.WHITE, Variant.BLUEBOTTLE, Variant.CONSTABLE, Variant.MORMON)
            .build();
    private static final Selector<Variant> FOREST_VARIANTS = Selector.<Variant>of()
            .add(30, Variant.TAILED_JAY)
            .add(Variant.MONARCH, Variant.BLUEBOTTLE, Variant.CONSTABLE, Variant.MORMON)
            .build();
    private static final Selector<Variant> PLAIN_VARIANTS = Selector.<Variant>of()
            .add(Variant.WHITE, Variant.BRIMSTONE, Variant.RED_ADMIRAL, Variant.SWALLOWTAIL, Variant.MONARCH)
            .build();
    private static final Selector<Variant> TAIGA_VARIANTS = Selector.<Variant>of()
            .add(30, Variant.SWALLOWTAIL)
            .add(20, Variant.MOURNING_CLOAK)
            .add(Variant.WHITE, Variant.BRIMSTONE, Variant.RED_ADMIRAL)
            .build();

    public Butterfly(EntityType<? extends Butterfly> type, Level level) {
        super(type, level);
    }

    @Override
    protected boolean isActive() {
        return this.level().isDay();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        Variant variant = getRandomVariant(pLevel, this.blockPosition());
        if (pSpawnGroupData instanceof ButterflyGroupData data) {
            variant = data.variant;
        } else {
            pSpawnGroupData = new ButterflyGroupData(variant);
        }
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    private static Variant getRandomVariant(LevelAccessor level, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        RandomSource random = level.getRandom();
        if (holder.is(ModBiomes.RAPESEED_FIELDS)) {
            return Variant.WHITE;
        } else if (holder.is(ModBiomes.AZURE_FIELDS)) {
            return random.nextBoolean() ? Variant.BLUE_ADMIRAL : Variant.OAKBLUE;
        } else if (holder.is(ModBiomes.LAVENDER_FIELDS) || holder.is(ModBiomes.JACARANDA_GROVE)) {
            return random.nextBoolean() ? Variant.PURPLE_EMPEROR : Variant.OAKBLUE;
        } else if (holder.is(ModTags.Biomes.IS_AUTUMN)) {
            return random.nextBoolean()
                    ? Variant.DEAD_LEAF
                    : random.nextBoolean() ? Variant.MONARCH : Variant.RED_ADMIRAL;
        } else if (holder.is(BiomeTags.IS_SAVANNA)) {
            return Variant.YELLOW;
        } else if (holder.is(Biomes.MEADOW)) {
            return MEADOW_VARIANTS.select(random);
        } else if (holder.is(BiomeTags.IS_JUNGLE)) {
            return JUNGLE_VARIANTS.select(random);
        } else if (holder.is(Tags.Biomes.IS_FLORAL)) {
            return FLORAL_VARIANTS.select(random);
        } else if (holder.is(BiomeTags.IS_FOREST) && !holder.is(Tags.Biomes.IS_COLD)) {
            if (holder.is(Tags.Biomes.IS_BIRCH_FOREST)) {
                return BIRCH_VARIANTS.select(random);
            } else {
                return FOREST_VARIANTS.select(random);
            }
        } else if (holder.is(Tags.Biomes.IS_TAIGA)) {
            return TAIGA_VARIANTS.select(random);
        } else {
            return PLAIN_VARIANTS.select(random);
        }
    }

    public static boolean checkSpawnRules(EntityType<Butterfly> ignore0, LevelAccessor level, MobSpawnType ignore, BlockPos pos, RandomSource ignored) {
        if (level.getBiome(pos).is(Tags.Biomes.IS_SNOWY)) return false;
        return isDay(level) && properWeatherForSpawn(level) && level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK) && isBrightEnoughToSpawn(level, pos);
    }

    private static boolean isDay(LevelAccessor level) {
        return !level.dimensionType().hasFixedTime() && level.getSkyDarken() < 4;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder pBuilder) {
        super.defineSynchedData(pBuilder);
        pBuilder.define(DATA_TYPE_ID, 0);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setVariant(Variant.byIndex(pCompound.getInt("Type")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Type", this.getVariant().ordinal());
    }

    @Override
    public void setVariant(Variant variant) {
        this.entityData.set(DATA_TYPE_ID, variant.ordinal());
    }

    @Override
    public Variant getVariant() {
        return Variant.byIndex(this.entityData.get(DATA_TYPE_ID));
    }

    @Override
    public void setVariant(AbstractMoth.Variant variant) {
        this.setVariant((Variant) variant);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return ModSounds.BUTTERFLY_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BUTTERFLY_DEATH.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.BUTTERFLY_FLUTTER.get();
    }

    public static class ButterflyGroupData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        public ButterflyGroupData(Variant pVariant) {
            super(false);
            this.variant = pVariant;
        }
    }

    public enum Variant implements AbstractMoth.Variant {
        MONARCH("monarch", "monarch_butterfly"),
        BLUEBOTTLE("bluebottle", "common_bluebottle"),
        WHITE("white", "white_butterfly"),
        YELLOW("yellow", "yellow_butterfly"),
        BRIMSTONE("brimstone", "brimstone_butterfly"),
        RED_ADMIRAL("red_admiral", "red_admiral_butterfly"),
        BLUE_ADMIRAL("blue_admiral", "blue_admiral_butterfly"),
        PURPLE_EMPEROR("purple_emperor", "purple_emperor_butterfly"),
        MORPHO("morpho", "morpho_butterfly"),
        OAKBLUE("oakblue", "oakblue_butterfly"),
        MOURNING_CLOAK("mourning_cloak", "mourning_cloak_butterfly"),
        CONSTABLE("constable", "constable_butterfly"),
        GLASSWING("glasswing", "glasswing_butterfly"),
        DEAD_LEAF("dead_leaf", "dead_leaf_butterfly"),
        JULIA("julia", "julia_butterfly"),
        SWALLOWTAIL("swallowtail", "old_world_swallowtail"),
        EMERALD("emerald", "emerald_swallowtail"),
        TAILED_JAY("tailed_jay", "tailed_jay"),
        MORMON("mormon", "common_mormon"),
        TREE_NYMPH("tree_nymph", "tree_nymph_butterfly"),
        BIRDWING("birdwing", "common_birdwing"),
        ALEXANDRA("alexandra", "queen_alexandras_birdwing"),
        AGRIAS("agrias", "agrias_butterfly"),
        BLAZE("blaze", "blaze_butterfly"),
        SKELETON("skeleton", "skeleton_butterfly"),
        SPECTER("specter", "specter_butterfly"),
        ENDER("ender", "ender_butterfly"),
        VOID("void", "void_butterfly");

        public static final Variant[] VALUES = values();
        private final String name;
        private final String textureName;

        Variant(String name, String textureName) {
            this.name = name;
            this.textureName = textureName;
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
        public String getTextureName() {
            return this.textureName;
        }

        @Override
        public boolean isNether() {
            return this == BLAZE || this == SKELETON || this == SPECTER;
        }

        @Override
        public boolean canGlow() {
            return this == BLAZE || this == SPECTER || this == ENDER || this == VOID;
        }

        @Override
        public boolean unavailable() {
            return this.ordinal() > 22;
        }

        public static Variant byIndex(int i) {
            if (i < VALUES.length) {
                return VALUES[i];
            }
            return MONARCH;
        }

        public static Variant byName(String name) {
            for (Variant variant : VALUES) {
                if (variant.name.equals(name)) {
                    return variant;
                }
            }
            return MONARCH;
        }
    }
}
