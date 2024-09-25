package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.IdentityHashMap;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;
import static net.minecraft.world.level.material.MapColor.*;

public final class VanillaBlockPropertyModification {
    @SubscribeEvent
    @SuppressWarnings("deprecation")
    public static void modifyBlockProperties(TagsUpdatedEvent event) {
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
            } else if (block.builtInRegistryHolder().is(Tags.Blocks.GLAZED_TERRACOTTAS)) {
                setInstrument(block, PIPA);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.PACKED_MUD_BLOCKS)) {
                setInstrument(block, ERHU);
            } else if (block.builtInRegistryHolder().is(ModTags.Blocks.RARE_WOOD)) {
                setInstrument(block, GUZHENG);
            }
        });
        BuiltInRegistries.BLOCK.forEach(block -> {
            if (block.equals(Blocks.PINK_PETALS)
                    || block.equals(Blocks.PINK_TULIP)
                    || block.equals(Blocks.PEONY)
                    || block.equals(Blocks.POTTED_PINK_TULIP)
                    || block.equals(Blocks.POTTED_CHERRY_SAPLING)) {
                setMapColor(block, COLOR_PINK);
            } else if (block.equals(Blocks.DANDELION)
                    || block.equals(Blocks.SUNFLOWER)
                    || block.equals(Blocks.POTTED_DANDELION)) {
                setMapColor(block, COLOR_YELLOW);
            } else if (block.equals(Blocks.POPPY)
                    || block.equals(Blocks.RED_TULIP)
                    || block.equals(Blocks.ROSE_BUSH)
                    || block.equals(Blocks.RED_MUSHROOM)
                    || block.equals(Blocks.POTTED_POPPY)
                    || block.equals(Blocks.POTTED_RED_TULIP)
                    || block.equals(Blocks.POTTED_RED_MUSHROOM)) {
                setMapColor(block, COLOR_RED);
            } else if (block.equals(Blocks.TORCHFLOWER)
                    || block.equals(Blocks.ORANGE_TULIP)
                    || block.equals(Blocks.POTTED_TORCHFLOWER)
                    || block.equals(Blocks.POTTED_ORANGE_TULIP)) {
                setMapColor(block, COLOR_ORANGE);
            } else if (block.equals(Blocks.FLOWER_POT)
                    || block.equals(Blocks.BROWN_MUSHROOM)
                    || block.equals(Blocks.POTTED_BROWN_MUSHROOM)) {
                setMapColor(block, DIRT);
            } else if (block.equals(Blocks.POTTED_AZALEA)
                    || block.equals(Blocks.POTTED_FLOWERING_AZALEA)
                    || block.equals(Blocks.POTTED_CACTUS)
                    || block.equals(Blocks.POTTED_BAMBOO)
                    || block.equals(Blocks.POTTED_FERN)
                    || block.equals(Blocks.POTTED_OAK_SAPLING)
                    || block.equals(Blocks.POTTED_BIRCH_SAPLING)
                    || block.equals(Blocks.POTTED_SPRUCE_SAPLING)
                    || block.equals(Blocks.POTTED_JUNGLE_SAPLING)
                    || block.equals(Blocks.POTTED_ACACIA_SAPLING)
                    || block.equals(Blocks.POTTED_DARK_OAK_SAPLING)
                    || block.equals(Blocks.POTTED_MANGROVE_PROPAGULE)) {
                setMapColor(block, PLANT);
            } else if (block.equals(Blocks.POTTED_DEAD_BUSH)) {
                setMapColor(block, WOOD);
            } else if (block.equals(Blocks.POTTED_CRIMSON_FUNGUS)
                    || block.equals(Blocks.POTTED_CRIMSON_ROOTS)) {
                setMapColor(block, NETHER);
            } else if (block.equals(Blocks.POTTED_WARPED_FUNGUS)
                    || block.equals(Blocks.POTTED_WARPED_ROOTS)
                    || block.equals(Blocks.PITCHER_PLANT)) {
                setMapColor(block, COLOR_CYAN);
            } else if (block.equals(Blocks.BLUE_ORCHID)
                    || block.equals(Blocks.POTTED_BLUE_ORCHID)) {
                setMapColor(block, COLOR_LIGHT_BLUE);
            } else if (block.equals(Blocks.CORNFLOWER)
                        || block.equals(Blocks.POTTED_CORNFLOWER)) {
                    setMapColor(block, COLOR_BLUE);
            } else if (block.equals(Blocks.AZURE_BLUET)
                    || block.equals(Blocks.POTTED_AZURE_BLUET)) {
                setMapColor(block, CLAY);
            } else if (block.equals(Blocks.ALLIUM)
                    || block.equals(Blocks.LILAC)
                    || block.equals(Blocks.POTTED_ALLIUM)) {
                setMapColor(block, COLOR_MAGENTA);
            } else if (block.equals(Blocks.WHITE_TULIP)
                    || block.equals(Blocks.OXEYE_DAISY)
                    || block.equals(Blocks.LILY_OF_THE_VALLEY)
                    || block.equals(Blocks.POTTED_WHITE_TULIP)
                    || block.equals(Blocks.POTTED_OXEYE_DAISY)
                    || block.equals(Blocks.POTTED_LILY_OF_THE_VALLEY)) {
                setMapColor(block, SNOW);
            } else if (block.equals(Blocks.WITHER_ROSE)
                    || block.equals(Blocks.POTTED_WITHER_ROSE)) {
                setMapColor(block, COLOR_BLACK);
            }
        });
    }
    private static final IdentityHashMap<Block, NoteBlockInstrument> backupsInstrument = Maps.newIdentityHashMap();
    private static final IdentityHashMap<Block, MapColor> backupsMapColor = Maps.newIdentityHashMap();
    private static void setInstrument(Block block, NoteBlockInstrument instrument) {
        if (!backupsInstrument.containsKey(block)) {
            backupsInstrument.put(block, block.defaultBlockState().instrument());
        }
        block.getStateDefinition().getPossibleStates().forEach(blockState -> blockState.instrument= instrument);
    }
    private static void setMapColor(Block block, MapColor mapColor) {
        if (!backupsMapColor.containsKey(block)) {
            backupsMapColor.put(block, block.defaultMapColor());
        }
        block.getStateDefinition().getPossibleStates().forEach(blockState -> blockState.mapColor= mapColor);
    }
}
