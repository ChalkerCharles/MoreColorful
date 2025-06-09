package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.nature.BerryBushBlock;
import com.ChalkerCharles.morecolorful.common.block.nature.LeafLitterBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.ReedPart;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.Set;
import java.util.stream.IntStream;

public abstract class ModBlockLootTableHelper extends BlockLootSubProvider {
    private static final float[] NORMAL_LEAVES_FRUIT_CHANCES = new float[]{0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F};
    protected ModBlockLootTableHelper(Set<Item> pExplosionResistant, FeatureFlagSet pEnabledFeatures, HolderLookup.Provider pRegistries) {
        super(pExplosionResistant, pEnabledFeatures, pRegistries);
    }

    protected LootTable.Builder createLeavesWithExtraDrop(Block pLeavesBlock, Block pSaplingBlock, ItemLike pExtraDrop) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(pLeavesBlock, pSaplingBlock, NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(this.doesNotHaveShearsOrSilkTouch())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(pLeavesBlock, LootItem.lootTableItem(pExtraDrop)))
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(
                                                                registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_FRUIT_CHANCES
                                                        )
                                                )
                                )
                );
    }

    protected LootTable.Builder createLeavesWithLeafPile(Block pLeavesBlock, Block pSaplingBlock, ItemLike pExtraDrop) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createLeavesDrops(pLeavesBlock, pSaplingBlock, NORMAL_LEAVES_SAPLING_CHANCES)
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .when(this.doesNotHaveShearsOrSilkTouch())
                                .add(
                                        ((LootPoolSingletonContainer.Builder<?>)this.applyExplosionCondition(pLeavesBlock, LootItem.lootTableItem(pExtraDrop)
                                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F)))))
                                                .when(
                                                        BonusLevelTableCondition.bonusLevelFlatChance(
                                                                registrylookup.getOrThrow(Enchantments.FORTUNE), NORMAL_LEAVES_SAPLING_CHANCES
                                                        )
                                                )
                                )
                );
    }

    protected LootTable.Builder createLeafLitterDrops(Block pLeafPileBlock) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        this.applyExplosionDecay(
                                                pLeafPileBlock,
                                                LootItem.lootTableItem(pLeafPileBlock)
                                                        .apply(
                                                                IntStream.rangeClosed(1, 4).boxed().toList(),
                                                                integer -> SetItemCountFunction.setCount(ConstantValue.exactly((float) integer))
                                                                        .when(
                                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(pLeafPileBlock)
                                                                                        .setProperties(
                                                                                                StatePropertiesPredicate.Builder.properties().hasProperty(LeafLitterBlock.AMOUNT, integer)
                                                                                        )
                                                                        )
                                                        )
                                        )
                                )
                );
    }

    protected LootTable.Builder createWaterGrassDrops(Block pWaterGrassBlock) {
        boolean flag = pWaterGrassBlock == ModBlocks.SHORT_WATER_GRASS.get();
        float count = flag ? 1.0F : 2.0F;
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool().when(HAS_SHEARS).add(LootItem.lootTableItem(ModBlocks.SHORT_WATER_GRASS).apply(SetItemCountFunction.setCount(ConstantValue.exactly(count))))
                );
    }

    protected LootTable.Builder createBerryBushDrops(Block pBerryBush, ItemLike pBerry) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .when(
                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(pBerryBush)
                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BerryBushBlock.AGE, 4))
                                )
                                .add(LootItem.lootTableItem(pBerry))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))
                                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                );
    }

    protected LootTable.Builder createDrumSetDrops() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.DRUM_SET, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                TagEntry.tagContents(ModTags.Items.DRUM_SET_PARTS)
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DRUM_SET.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.DRUM_SET_PART, DrumSetPart.MID_LOWER))
                                        )
                        )
                )
        );
    }

    protected LootTable.Builder createReedDrops() {
        return LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.REED, LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                LootItem.lootTableItem(ModItems.REED)
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.REED.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.REED_PART, ReedPart.LOWER))
                                        ).apply(
                                                SetItemCountFunction.setCount(ConstantValue.exactly(2.0F))
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.REED.get())
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.TALL_REED, true))
                                                        )
                                        )
                        )
                )
        );
    }

    protected void dropBerries(Block pBerryBush, ItemLike pBerry) {
        this.add(pBerryBush, createBerryBushDrops(pBerryBush, pBerry));
    }

    protected void dropForSlab(Block pBlock) {
        this.add(pBlock, createSlabItemTable(pBlock));
    }

    protected void dropForLeaves(Block pBlock, Block pSapling) {
        this.add(pBlock, createLeavesDrops(pBlock, pSapling, NORMAL_LEAVES_SAPLING_CHANCES));
    }

    protected void dropForLeavesWithExtraDrop(Block pBlock, Block pSapling, @SuppressWarnings("SameParameterValue") ItemLike pItem) {
        this.add(pBlock, createLeavesWithExtraDrop(pBlock, pSapling, pItem));
    }

    protected void dropForLeavesWithLeafPile(Block pBlock, Block pSapling, ItemLike pItem) {
        this.add(pBlock, createLeavesWithLeafPile(pBlock, pSapling, pItem));
    }

    protected void dropForPetals(Block pBlock) {
        this.add(pBlock, createPetalsDrops(pBlock));
    }

    protected void dropForLeafLitter(Block pBlock) {
        this.add(pBlock, createLeafLitterDrops(pBlock));
    }

    protected void dropForDoubleBlock(Block pBlock) {
        this.add(pBlock, createSinglePropConditionTable(pBlock, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
    }

    protected void dropForHorizontalDoubleBlock(Block pBlock) {
        this.add(pBlock, createSinglePropConditionTable(pBlock, ModBlockStateProperties.HORIZONTAL_HALF, HorizontalDoubleBlockHalf.RIGHT));
    }

    protected void dropForWaterGrass(Block pBlock) {
        this.add(pBlock, createWaterGrassDrops(pBlock));
    }

    protected void dropWhenSheared(Block pBlock) {
        this.add(pBlock, createShearsOnlyDrop(pBlock));
    }
}
