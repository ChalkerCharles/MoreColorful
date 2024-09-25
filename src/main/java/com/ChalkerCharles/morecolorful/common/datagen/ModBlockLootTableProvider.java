package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.block.properties.DrumSetPart;
import com.ChalkerCharles.morecolorful.common.block.properties.GrandPianoPart;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockStateProperties;
import com.ChalkerCharles.morecolorful.common.block.properties.UprightPianoPart;
import com.ChalkerCharles.morecolorful.common.datagen.helper.ModBlockLootTableHelper;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.TagEntry;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTableProvider extends ModBlockLootTableHelper {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        // Musical Instrument Blocks
        dropForDoubleBlock(ModBlocks.HARP.get());
        dropSelf(ModBlocks.BASS_DRUM.get());
        dropSelf(ModBlocks.SNARE_DRUM.get());
        dropSelf(ModBlocks.TOMTOM_DRUM.get());
        dropSelf(ModBlocks.HIHAT.get());
        dropForDoubleBlock(ModBlocks.RIDE_CYMBAL.get());
        dropForDoubleBlock(ModBlocks.CRASH_CYMBAL.get());
        dropForDoubleBlock(ModBlocks.CHIMES.get());
        dropSelf(ModBlocks.GLOCKENSPIEL.get());
        dropForHorizontalDoubleBlock(ModBlocks.XYLOPHONE.get());
        dropForHorizontalDoubleBlock(ModBlocks.VIBRAPHONE.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_BIT.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLING.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SAW.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK.get());
        dropForHorizontalDoubleBlock(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS.get());
        dropForHorizontalDoubleBlock(ModBlocks.GUZHENG.get());
        add(ModBlocks.UPRIGHT_PIANO.get(), createSinglePropConditionTable(ModBlocks.UPRIGHT_PIANO.get(), ModBlockStateProperties.UPRIGHT_PIANO_PART, UprightPianoPart.RIGHT_LOWER));
        add(ModBlocks.GRAND_PIANO.get(), createSinglePropConditionTable(ModBlocks.GRAND_PIANO.get(), ModBlockStateProperties.GRAND_PIANO_PART, GrandPianoPart.FRONT_RIGHT_LOWER));
        add(ModBlocks.DRUM_SET.get(), LootTable.lootTable().withPool(this.applyExplosionCondition(ModBlocks.DRUM_SET.get(), LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        TagEntry.tagContents(ModTags.Items.DRUM_SET_PARTS)
                                                .when(
                                                        LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.DRUM_SET.get())
                                                                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(ModBlockStateProperties.DRUM_SET_PART, DrumSetPart.MID_LOWER))
                                                )
                                )
                )
        ));

        // Common Blocks
        dropSelf(ModBlocks.CRABAPPLE_LOG.get());
        dropSelf(ModBlocks.STRIPPED_CRABAPPLE_LOG.get());
        dropSelf(ModBlocks.CRABAPPLE_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_CRABAPPLE_WOOD.get());
        dropSelf(ModBlocks.CRABAPPLE_PLANKS.get());
        dropSelf(ModBlocks.CRABAPPLE_STAIRS.get());
        dropForSlab(ModBlocks.CRABAPPLE_SLAB.get());
        dropSelf(ModBlocks.CRABAPPLE_FENCE.get());
        dropSelf(ModBlocks.CRABAPPLE_FENCE_GATE.get());
        dropForDoubleBlock(ModBlocks.CRABAPPLE_DOOR.get());
        dropSelf(ModBlocks.CRABAPPLE_TRAPDOOR.get());
        dropSelf(ModBlocks.CRABAPPLE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.CRABAPPLE_BUTTON.get());
        dropOther(ModBlocks.CRABAPPLE_SIGN.get(), ModItems.CRABAPPLE_SIGN.get());
        dropOther(ModBlocks.CRABAPPLE_WALL_SIGN.get(), ModItems.CRABAPPLE_SIGN.get());
        dropOther(ModBlocks.CRABAPPLE_HANGING_SIGN.get(), ModItems.CRABAPPLE_HANGING_SIGN.get());
        dropOther(ModBlocks.CRABAPPLE_WALL_HANGING_SIGN.get(), ModItems.CRABAPPLE_HANGING_SIGN.get());
        dropForLeavesWithExtraDrop(ModBlocks.CRABAPPLE_LEAVES.get(), ModBlocks.CRABAPPLE_SAPLING.get(), Items.APPLE);
        dropSelf(ModBlocks.CRABAPPLE_SAPLING.get());
        dropPottedContents(ModBlocks.POTTED_CRABAPPLE_SAPLING.get());
        dropForPetals(ModBlocks.BEGONIAS.get());

        dropSelf(ModBlocks.EBONY_LOG.get());
        dropSelf(ModBlocks.STRIPPED_EBONY_LOG.get());
        dropSelf(ModBlocks.EBONY_WOOD.get());
        dropSelf(ModBlocks.STRIPPED_EBONY_WOOD.get());
        dropSelf(ModBlocks.EBONY_PLANKS.get());
        dropSelf(ModBlocks.EBONY_STAIRS.get());
        dropForSlab(ModBlocks.EBONY_SLAB.get());
        dropSelf(ModBlocks.EBONY_FENCE.get());
        dropSelf(ModBlocks.EBONY_FENCE_GATE.get());
        dropForDoubleBlock(ModBlocks.EBONY_DOOR.get());
        dropSelf(ModBlocks.EBONY_TRAPDOOR.get());
        dropSelf(ModBlocks.EBONY_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.EBONY_BUTTON.get());
        dropOther(ModBlocks.EBONY_SIGN.get(), ModItems.EBONY_SIGN.get());
        dropOther(ModBlocks.EBONY_WALL_SIGN.get(), ModItems.EBONY_SIGN.get());
        dropOther(ModBlocks.EBONY_HANGING_SIGN.get(), ModItems.EBONY_HANGING_SIGN.get());
        dropOther(ModBlocks.EBONY_WALL_HANGING_SIGN.get(), ModItems.EBONY_HANGING_SIGN.get());

        dropSelf(ModBlocks.PINK_DAISY.get());
        dropPottedContents(ModBlocks.POTTED_PINK_DAISY.get());
        dropSelf(ModBlocks.RED_CARNATION.get());
        dropPottedContents(ModBlocks.POTTED_RED_CARNATION.get());
        dropSelf(ModBlocks.PINK_CARNATION.get());
        dropPottedContents(ModBlocks.POTTED_PINK_CARNATION.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(DeferredHolder::value).collect(Collectors.toList());
    }
}
