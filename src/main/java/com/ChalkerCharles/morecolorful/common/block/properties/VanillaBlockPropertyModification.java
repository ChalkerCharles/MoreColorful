package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.IdentityHashMap;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;

public final class VanillaBlockPropertyModification {
    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public void modifyBlockProperties(TagsUpdatedEvent event) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block.builtInRegistryHolder().is(ModTags.Blocks.QUARTZ_BLOCKS)) {
                setInstrument(block, PIANO_LOW);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.PRISMARINES)) {
                setInstrument(block, PIANO_HIGH);
            } else if (block.equals(Blocks.MOSS_BLOCK)) {
                setInstrument(block, VIOLIN);
            } else if (block.builtInRegistryHolder().is(BlockTags.WART_BLOCKS)) {
                setInstrument(block, CELLO);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.NETHER_FUNGUS_WOODEN_BLOCKS)) {
                setInstrument(block, ELECTRIC_GUITAR);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.COPPER_BLOCKS)) {
                setInstrument(block, TRUMPET);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.COPPER_GRATES)) {
                setInstrument(block, SAXOPHONE);
            } else if (block.builtInRegistryHolder().is(BlockTags.TERRACOTTA)) {
                setInstrument(block, OCARINA);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.TUFF_BLOCKS)) {
                setInstrument(block, HARMONICA);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.MUSHROOM_BLOCKS)) {
                setInstrument(block, TOM);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.BASALT_BLOCKS)) {
                setInstrument(block, RIDE);
            } else if (block.equals(Blocks.MAGMA_BLOCK)) {
                setInstrument(block, CRASH);
            } else if (block.equals(Blocks.SCULK) || block.equals(Blocks.SCULK_CATALYST)) {
                setInstrument(block, SCULK);
            } else if (block.builtInRegistryHolder().is(BlockTags.CRYSTAL_SOUND_BLOCKS)) {
                setInstrument(block, CRYSTAL);
            } else if (block.builtInRegistryHolder().is(BlockTags.REDSTONE_ORES)) {
                setInstrument(block, SAW);
            } else if (block.equals(Blocks.LAPIS_BLOCK)) {
                setInstrument(block, PLUCK);
            } else if (block.builtInRegistryHolder().is(Tags.Blocks.CONCRETES)) {
                setInstrument(block, SYNTH_BASS);
            }
        });
    }
    private final IdentityHashMap<Block, NoteBlockInstrument> backups = Maps.newIdentityHashMap();
    private void setInstrument(Block block, NoteBlockInstrument instrument) {
        if (!this.backups.containsKey(block)) {
            this.backups.put(block, block.defaultBlockState().instrument());
        }
        block.getStateDefinition().getPossibleStates().forEach(blockState -> blockState.instrument= instrument);
    }
}
