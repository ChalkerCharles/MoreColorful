package com.ChalkerCharles.morecolorful.common.datagen.helper;

import com.ChalkerCharles.morecolorful.common.block.common.LeafPileBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.HorizontalDoubleBlockHalf;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
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

    protected LootTable.Builder createLeafPileDrops(Block pLeafPileBlock) {
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
                                                                                                StatePropertiesPredicate.Builder.properties().hasProperty(LeafPileBlock.AMOUNT, integer)
                                                                                        )
                                                                        )
                                                        )
                                        )
                                )
                );
    }

    protected void dropForSlab(Block pBlock) {
        this.add(pBlock, createSlabItemTable(pBlock));
    }

    protected void dropForLeaves(Block pBlock, Block pSapling) {
        this.add(pBlock, createLeavesDrops(pBlock, pSapling, NORMAL_LEAVES_SAPLING_CHANCES));
    }

    protected void dropForLeavesWithExtraDrop(Block pBlock, Block pSapling, ItemLike pItem) {
        this.add(pBlock, createLeavesWithExtraDrop(pBlock, pSapling, pItem));
    }

    protected void dropForLeavesWithLeafPile(Block pBlock, Block pSapling, ItemLike pItem) {
        this.add(pBlock, createLeavesWithLeafPile(pBlock, pSapling, pItem));
    }

    protected void dropForPetals(Block pBlock) {
        this.add(pBlock, createPetalsDrops(pBlock));
    }

    protected void dropForLeafPile(Block pBlock) {
        this.add(pBlock, createLeafPileDrops(pBlock));
    }

    protected void dropForDoubleBlock(Block pBlock) {
        this.add(pBlock, createSinglePropConditionTable(pBlock, BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
    }
    protected void dropForHorizontalDoubleBlock(Block pBlock) {
        this.add(pBlock, createSinglePropConditionTable(pBlock, ModBlockStateProperties.HORIZONTAL_HALF, HorizontalDoubleBlockHalf.RIGHT));
    }
}
