package com.ChalkerCharles.morecolorful.common.datagen.tag;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MoreColorful.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        // Vanilla Tags
        copy(BlockTags.FENCE_GATES, ItemTags.FENCE_GATES);
        copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        copy(BlockTags.CEILING_HANGING_SIGNS, ItemTags.HANGING_SIGNS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        copy(BlockTags.STANDING_SIGNS, ItemTags.SIGNS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);

        tag(ItemTags.BOATS).add(
                ModItems.CRABAPPLE_BOAT.get(),
                ModItems.EBONY_BOAT.get(),
                ModItems.GINKGO_BOAT.get(),
                ModItems.MAPLE_BOAT.get()
        );
        tag(ItemTags.CHEST_BOATS).add(
                ModItems.CRABAPPLE_CHEST_BOAT.get(),
                ModItems.EBONY_CHEST_BOAT.get(),
                ModItems.GINKGO_CHEST_BOAT.get(),
                ModItems.MAPLE_CHEST_BOAT.get()
        );

        // More Colorful Tags
        copy(ModTags.Blocks.CRABAPPLE_LOGS, ModTags.Items.CRABAPPLE_LOGS);
        copy(ModTags.Blocks.EBONY_LOGS, ModTags.Items.EBONY_LOGS);
        copy(ModTags.Blocks.GINKGO_LOGS, ModTags.Items.GINKGO_LOGS);
        copy(ModTags.Blocks.MAPLE_LOGS, ModTags.Items.MAPLE_LOGS);
        copy(ModTags.Blocks.LEAF_PILES, ModTags.Items.LEAF_PILES);

        tag(ModTags.Items.DRUM_SET_PARTS).add(
                ModItems.BASS_DRUM.get(),
                ModItems.SNARE_DRUM.get(),
                ModItems.TOMTOM_DRUM.get(),
                ModItems.HIHAT.get(),
                ModItems.RIDE_CYMBAL.get(),
                ModItems.CRASH_CYMBAL.get()
        );
        tag(ModTags.Items.RARE_PLANKS).add(
                ModItems.EBONY_PLANKS.get()
        );
        tag(ModTags.Items.STRIPPED_RARE_LOGS).add(
                ModItems.STRIPPED_EBONY_LOG.get(),
                ModItems.STRIPPED_EBONY_WOOD.get()
        );

        // C Tags
        tag(Tags.Items.FENCE_GATES_WOODEN).add(
                ModItems.CRABAPPLE_FENCE_GATE.get(),
                ModItems.EBONY_FENCE_GATE.get(),
                ModItems.GINKGO_FENCE_GATE.get(),
                ModItems.MAPLE_FENCE_GATE.get()
        );
    }
}
