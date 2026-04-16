package com.ChalkerCharles.morecolorful.common.entity.animal;

import com.ChalkerCharles.morecolorful.common.ModSounds;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public class Moth extends AbstractMoth implements VariantHolder<Moth.Variant> {
    private static final EntityDataAccessor<Integer> DATA_TYPE_ID = SynchedEntityData.defineId(Moth.class, EntityDataSerializers.INT);
    private static final Selector<Variant> JUNGLE_VARIANTS = Selector.<Variant>of()
            .add(60, Variant.ATLAS, Variant.COMET)
            .add(Variant.WHITE_WITCH, Variant.BLACK_WITCH, Variant.DEATHS_HEAD)
            .build();
    private static final Selector<Variant> SAVANNA_VARIANTS = Selector.<Variant>of()
            .add(Variant.WILD_SILK, Variant.WHITE_WITCH, Variant.BLACK_WITCH, Variant.DEATHS_HEAD)
            .build();
    private static final Selector<Variant> FOREST_VARIANTS = Selector.<Variant>of()
            .add(60, Variant.LUNA, Variant.GARDEN_TIGER)
            .add(Variant.WILD_SILK, Variant.DEATHS_HEAD, Variant.FALL_WEBWORM)
            .build();
    private static final Selector<Variant> DEFAULT_VARIANTS = Selector.<Variant>of()
            .add(Variant.WILD_SILK, Variant.DEATHS_HEAD, Variant.FALL_WEBWORM)
            .build();

    public Moth(EntityType<? extends Moth> type, Level level) {
        super(type, level);
    }

    @Override
    protected boolean isActive() {
        return this.level().isNight();
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pSpawnType, @Nullable SpawnGroupData pSpawnGroupData) {
        Variant variant = getRandomVariant(pLevel, this.blockPosition());
        if (pSpawnGroupData instanceof MothGroupData data) {
            variant = data.variant;
        } else {
            pSpawnGroupData = new MothGroupData(variant);
        }
        this.setVariant(variant);
        return super.finalizeSpawn(pLevel, pDifficulty, pSpawnType, pSpawnGroupData);
    }

    private static Variant getRandomVariant(LevelAccessor level, BlockPos pos) {
        Holder<Biome> holder = level.getBiome(pos);
        RandomSource random = level.getRandom();
        if (holder.is(ModBiomes.MAPLE_FOREST) || holder.is(ModBiomes.SUNSET_VALLEY)) {
            return random.nextInt(3) == 0 ? Variant.GARDEN_TIGER : Variant.ROSY;
        } else if (holder.is(Tags.Biomes.IS_SWAMP)) {
            return Variant.SCARLET_TIGER;
        } else if (holder.is(Tags.Biomes.IS_BIRCH_FOREST)) {
            return random.nextInt(4) == 0 ? Variant.FALL_WEBWORM : Variant.WHITE_PEPPERED;
        } else if (holder.is(Biomes.DARK_FOREST)) {
            return random.nextInt(4) == 0 ? Variant.DEATHS_HEAD : Variant.BLACK_PEPPERED;
        } else if (holder.is(Tags.Biomes.IS_FLORAL)) {
            return random.nextInt(4) == 0 ? Variant.WILD_SILK : Variant.OLEANDER;
        } else if (holder.is(BiomeTags.IS_JUNGLE)) {
            return JUNGLE_VARIANTS.select(random);
        } else if (holder.is(BiomeTags.IS_SAVANNA)) {
            return SAVANNA_VARIANTS.select(random);
        } else if (holder.is(BiomeTags.IS_FOREST) || holder.is(BiomeTags.IS_TAIGA)) {
            return FOREST_VARIANTS.select(random);
        } else {
            return DEFAULT_VARIANTS.select(random);
        }
    }

    public static boolean checkSpawnRules(EntityType<Moth> ignore0, LevelAccessor level, MobSpawnType ignore, BlockPos pos, RandomSource ignored) {
        if (level.getBiome(pos).is(Tags.Biomes.IS_SNOWY)) return false;
        return isNight(level) && properWeatherForSpawn(level) && level.getBlockState(pos.below()).is(Blocks.GRASS_BLOCK);
    }

    private static boolean isNight(LevelAccessor level) {
        return !level.dimensionType().hasFixedTime() && level.getSkyDarken() >= 4;
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
    public float getWalkTargetValue(BlockPos pPos, LevelReader pLevel) {
        return super.getWalkTargetValue(pPos, pLevel) + pLevel.getPathfindingCostFromLightLevels(pPos) * 2;
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
        return ModSounds.MOTH_FLUTTER.get();
    }

    public static class MothGroupData extends AgeableMob.AgeableMobGroupData {
        public final Variant variant;

        public MothGroupData(Variant pVariant) {
            super(false);
            this.variant = pVariant;
        }
    }

    public enum Variant implements AbstractMoth.Variant {
        WILD_SILK("wild_silk", "wild_silk_moth"),
        LUNA("luna", "luna_moth"),
        ROSY("rosy", "rosy_maple_moth"),
        ATLAS("atlas", "atlas_moth"),
        WHITE_PEPPERED("white_peppered", "white_peppered_moth"),
        BLACK_PEPPERED("black_peppered", "black_peppered_moth"),
        WHITE_WITCH("white_witch", "white_witch_moth"),
        BLACK_WITCH("black_witch", "black_witch_moth"),
        DEATHS_HEAD("deaths_head", "deaths_head_hawkmoth"),
        FALL_WEBWORM("fall_webworm", "fall_webworm"),
        COMET("comet", "comet_moth"),
        GARDEN_TIGER("garden_tiger", "garden_tiger_moth"),
        SCARLET_TIGER("scarlet_tiger", "scarlet_tiger_moth"),
        OLEANDER("oleander", "oleander_hawkmoth"),
        CRIMSON("crimson", "crimson_moth"),
        WARPED("warped", "warped_moth"),
        VIRID("virid", "virid_moth"),
        END_YELLOW("end_yellow", "end_yellow_moth"),
        CHORUS("chorus", "chorus_moth"),
        NAMELESS("nameless", "nameless_moth");

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
            return 32 + this.ordinal();
        }

        @Override
        public String getTextureName() {
            return this.textureName;
        }

        @Override
        public boolean isNether() {
            return this == CRIMSON || this == WARPED || this == VIRID;
        }

        @Override
        public boolean canGlow() {
            return this == NAMELESS;
        }

        @Override
        public boolean unavailable() {
            return this.ordinal() > 13;
        }

        public static Variant byIndex(int i) {
            if (i < VALUES.length) {
                return VALUES[i];
            }
            return WILD_SILK;
        }

        public static Variant byName(String name) {
            for (Variant variant : VALUES) {
                if (variant.name.equals(name)) {
                    return variant;
                }
            }
            return null;
        }
    }
}
