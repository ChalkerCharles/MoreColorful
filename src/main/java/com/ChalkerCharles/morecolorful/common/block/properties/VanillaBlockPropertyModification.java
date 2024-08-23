package com.ChalkerCharles.morecolorful.common.block.properties;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.IdentityHashMap;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;

public final class VanillaBlockPropertyModification {
    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public void modifyBlockProperties(TagsUpdatedEvent event) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block.builtInRegistryHolder().is(ModTags.QUARTZ_BLOCKS)) {
                setInstrument(block, PIANO);
            } else if (block.equals(Blocks.MOSS_BLOCK)) {
                setInstrument(block, VIOLIN);
            } else if (block.builtInRegistryHolder().is(BlockTags.WART_BLOCKS)) {
                setInstrument(block, CELLO);
            } else if (block.builtInRegistryHolder().is(ModTags.NETHER_FUNGUS_WOODEN_BLOCKS)) {
                setInstrument(block, ELECTRIC_GUITAR);
            } else if (block.builtInRegistryHolder().is(ModTags.COPPER_BLOCKS)) {
                setInstrument(block, TRUMPET);
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
