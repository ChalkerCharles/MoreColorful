package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.natural.BerryBushBlock;
import com.ChalkerCharles.morecolorful.common.block.natural.LeafLitterBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.*;
import com.ChalkerCharles.morecolorful.common.item.ModDataComponents;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@SuppressWarnings("SameParameterValue")
public abstract class ModBlockLootTableHelper extends BlockLootSubProvider {
    private static final float[] NORMAL_LEAVES_FRUIT_CHANCES = new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};
    protected ModBlockLootTableHelper(Set<Item> explosionResistant, FeatureFlagSet enabledFeatures, HolderLookup.Provider registries) {
        super(explosionResistant, enabledFeatures, registries);
    }

    protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder createSinglePropConditionTable(
            Supplier<? extends Block> block, Property<T> property, T value) {
        return this.createSinglePropConditionTable(block.get(), property, value);
    }

    protected LootTable.Builder createLeavesWithExtraDrop(Block leavesBlock, Block saplingBlock, ItemLike extraDrop) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(leavesBlock, saplingBlock, NORMAL_LEAVES_SAPLING_CHANCES).withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add(this.applyExplosionCondition(leavesBlock, LootItem.lootTableItem(extraDrop))
                                .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_FRUIT_CHANCES))
                        )
        );
    }

    protected LootTable.Builder createLeavesWithLeafPile(Block leavesBlock, Block saplingBlock, ItemLike extraDrop) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(leavesBlock, saplingBlock, NORMAL_LEAVES_SAPLING_CHANCES).withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .when(this.doesNotHaveShearsOrSilkTouch())
                        .add(this.applyExplosionCondition(leavesBlock, LootItem.lootTableItem(extraDrop).apply(
                                SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
                        .when(BonusLevelTableCondition.bonusLevelFlatChance(registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_SAPLING_CHANCES))
        );
    }

    protected LootTable.Builder createLeafLitterDrops(Block leafPileBlock) {
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(this.applyExplosionDecay(leafPileBlock, LootItem.lootTableItem(leafPileBlock).apply(
                                IntStream.rangeClosed(1, 4).boxed().toList(),
                                integer -> SetItemCountFunction.setCount(ConstantValue.exactly(integer))
                                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(leafPileBlock)
                                                .setProperties(
                                                        StatePropertiesPredicate.Builder.properties().hasProperty(LeafLitterBlock.AMOUNT, integer)
                                                )
                                        )
                                ))
                        )
        );
    }

    protected LootTable.Builder createWaterGrassDrops(Block waterGrassBlock) {
        boolean flag = waterGrassBlock == ModBlocks.SHORT_WATER_GRASS.get();
        float count = flag ? 1.0F : 2.0F;
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool().when(HAS_SHEARS).add(LootItem.lootTableItem(ModBlocks.SHORT_WATER_GRASS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))))
                );
    }

    protected LootTable.Builder createBerryBushDrops(Block berryBush, ItemLike berry) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return LootTable.lootTable().withPool(
                LootPool.lootPool()
                        .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(berryBush)
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerryBushBlock.AGE, 4))
                        )
                        .add(LootItem.lootTableItem(berry))
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                        .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
        );
    }

    protected LootTable.Builder createDrumSetDrops() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.DRUM_SET, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(TagEntry.tagContents(ModTags.Items.DRUM_SET_PARTS)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DRUM_SET.get())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.DRUM_SET_PART, DrumSetPart.MID_LOWER))
                                )
                        )
                )
        );
    }

    protected LootTable.Builder createReedDrops() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.REED,
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.REED)
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.REED.get())
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.REED_PART, ReedPart.LOWER))
                                ).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.REED.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.TALL_REED, true))
                                                )
                                )
                        )
                )
        );
    }

    protected LootTable.Builder createPinwheelDrop() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.PINWHEEL,
                LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(LootItem.lootTableItem(ModItems.PINWHEEL)
                                .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                        .include(ModDataComponents.PINWHEEL_COLOR.get())
                                )
                        )
                )
        );
    }

    protected LootTable.Builder createPapercuttingDrop(Block block) {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(block,
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(block)
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                .include(ModDataComponents.PAPERCUTTING_STENCIL.get())
                                        )
                                )
                )
        );
    }

    protected LootTable.Builder createCocoonDrop() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.COCOON,
                        LootPool.lootPool()
                                .when(this.hasSilkTouch())
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(LootItem.lootTableItem(ModItems.COCOON)
                                        .apply(CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                .include(ModDataComponents.COCOON_DATA.get())
                                        )
                                )
                )
        );
    }

    protected void add(Supplier<? extends Block> block, LootTable.Builder builder) {
        this.add(block.get(), builder);
    }

    protected void dropSelf(Supplier<? extends Block> block) {
        this.dropSelf(block.get());
    }

    protected void dropOther(Supplier<? extends Block> block, ItemLike item) {
        this.dropOther(block.get(), item);
    }

    protected void dropPottedContents(Supplier<? extends Block> flowerPot) {
        this.dropPottedContents(flowerPot.get());
    }

    protected void dropBerries(Supplier<? extends Block> berryBush, ItemLike berry) {
        Block block = berryBush.get();
        this.add(block, createBerryBushDrops(block, berry));
    }

    protected void dropForSlab(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createSlabItemTable(b));
    }

    protected void dropForLeaves(Supplier<? extends Block> block, Supplier<? extends Block> sapling) {
        Block b = block.get();
        this.add(b, createLeavesDrops(b, sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    protected void dropForLeavesWithExtraDrop(Supplier<? extends Block> block, Supplier<? extends Block> sapling, ItemLike item) {
        Block b = block.get();
        this.add(b, createLeavesWithExtraDrop(b, sapling.get(), item));
    }

    protected void dropForLeavesWithLeafPile(Supplier<? extends Block> block, Supplier<? extends Block> sapling, ItemLike item) {
        Block b = block.get();
        this.add(b, createLeavesWithLeafPile(b, sapling.get(), item));
    }

    protected void dropForPetals(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createPetalsDrops(b));
    }

    protected void dropForLeafLitter(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createLeafLitterDrops(b));
    }

    protected void dropForDoubleBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createSinglePropConditionTable(b, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
    }

    protected void dropForHorizontalDoubleBlock(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createSinglePropConditionTable(b, ModBlockStateProperties.HORIZONTAL_HALF, HorizontalHalf.RIGHT));
    }

    protected void dropForWaterGrass(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createWaterGrassDrops(b));
    }

    protected void dropWhenSheared(Supplier<? extends Block> block) {
        Block b = block.get();
        this.add(b, createShearsOnlyDrop(b));
    }
}
