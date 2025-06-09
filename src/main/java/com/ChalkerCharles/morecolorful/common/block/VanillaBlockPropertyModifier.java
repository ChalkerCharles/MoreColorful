package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateBaseExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IBlockStateBaseMixin;
import com.ChalkerCharles.morecolorful.util.Predicates;
import com.google.common.collect.Maps;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;

import static com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension.*;
import static net.minecraft.world.level.material.MapColor.*;

@SuppressWarnings("deprecation")
public final class VanillaBlockPropertyModifier {
    @SubscribeEvent
    public static void modifyProperties(final TagsUpdatedEvent event) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            setInstruments(block);
            setMapColors(block);
            if (Config.THERMAL_SYSTEM.isTrue()) {
                setTemperatures(block);
                setThermalResistances(block);
            }
        });
    }

    private static void setInstruments(Block block) {
        Holder.Reference<Block> holder = block.builtInRegistryHolder();
        if (holder.is(ModTags.Blocks.QUARTZ_BLOCKS)) {
            setInstrument(block, PIANO_LOW);
        } else if (holder.is(ModTags.Blocks.PRISMARINES)) {
            setInstrument(block, PIANO_HIGH);
        } else if (block.equals(Blocks.MOSS_BLOCK)) {
            setInstrument(block, VIOLIN);
        } else if (holder.is(BlockTags.WART_BLOCKS)) {
            setInstrument(block, CELLO);
        } else if (holder.is(ModTags.Blocks.NETHER_FUNGUS_WOODEN_BLOCKS)) {
            setInstrument(block, ELECTRIC_GUITAR);
        } else if (holder.is(ModTags.Blocks.COPPER_BLOCKS)) {
            setInstrument(block, TRUMPET);
        } else if (holder.is(ModTags.Blocks.COPPER_GRATES)) {
            setInstrument(block, SAXOPHONE);
        } else if (holder.is(BlockTags.TERRACOTTA)) {
            setInstrument(block, OCARINA);
        } else if (holder.is(ModTags.Blocks.TUFF_BLOCKS)) {
            setInstrument(block, HARMONICA);
        } else if (holder.is(ModTags.Blocks.MUSHROOM_BLOCKS)) {
            setInstrument(block, TOM);
        } else if (holder.is(ModTags.Blocks.BASALT_BLOCKS)) {
            setInstrument(block, RIDE);
        } else if (block.equals(Blocks.MAGMA_BLOCK)) {
            setInstrument(block, CRASH);
        } else if (block.equals(Blocks.SCULK) || block.equals(Blocks.SCULK_CATALYST)) {
            setInstrument(block, SCULK);
        } else if (holder.is(BlockTags.CRYSTAL_SOUND_BLOCKS)) {
            setInstrument(block, CRYSTAL);
        } else if (holder.is(BlockTags.REDSTONE_ORES)) {
            setInstrument(block, SAW);
        } else if (block.equals(Blocks.LAPIS_BLOCK)) {
            setInstrument(block, PLUCK);
        } else if (holder.is(Tags.Blocks.CONCRETES)) {
            setInstrument(block, SYNTH_BASS);
        } else if (holder.is(Tags.Blocks.GLAZED_TERRACOTTAS)) {
            setInstrument(block, PIPA);
        } else if (holder.is(ModTags.Blocks.PACKED_MUD_BLOCKS)) {
            setInstrument(block, ERHU);
        } else if (holder.is(ModTags.Blocks.RARE_WOOD)) {
            setInstrument(block, GUZHENG);
        }
    }

    private static void setMapColors(Block block) {
        if (Predicates.blockMatches(block,
                Blocks.PINK_PETALS,
                Blocks.PINK_TULIP,
                Blocks.PEONY,
                Blocks.POTTED_PINK_TULIP,
                Blocks.POTTED_CHERRY_SAPLING)) {
            setMapColor(block, COLOR_PINK);
        } else if (Predicates.blockMatches(block,
                Blocks.DANDELION,
                Blocks.SUNFLOWER,
                Blocks.POTTED_DANDELION)) {
            setMapColor(block, COLOR_YELLOW);
        } else if (Predicates.blockMatches(block,
                Blocks.POPPY,
                Blocks.RED_TULIP,
                Blocks.ROSE_BUSH,
                Blocks.RED_MUSHROOM,
                Blocks.POTTED_POPPY,
                Blocks.POTTED_RED_TULIP,
                Blocks.POTTED_RED_MUSHROOM)) {
            setMapColor(block, COLOR_RED);
        } else if (Predicates.blockMatches(block,
                Blocks.TORCHFLOWER,
                Blocks.ORANGE_TULIP,
                Blocks.POTTED_TORCHFLOWER,
                Blocks.POTTED_ORANGE_TULIP)) {
            setMapColor(block, COLOR_ORANGE);
        } else if (Predicates.blockMatches(block,
                Blocks.FLOWER_POT,
                Blocks.BROWN_MUSHROOM,
                Blocks.POTTED_BROWN_MUSHROOM)) {
            setMapColor(block, DIRT);
        } else if (Predicates.blockMatches(block,
                Blocks.POTTED_AZALEA,
                Blocks.POTTED_FLOWERING_AZALEA,
                Blocks.POTTED_CACTUS,
                Blocks.POTTED_BAMBOO,
                Blocks.POTTED_FERN,
                Blocks.POTTED_OAK_SAPLING,
                Blocks.POTTED_BIRCH_SAPLING,
                Blocks.POTTED_SPRUCE_SAPLING,
                Blocks.POTTED_JUNGLE_SAPLING,
                Blocks.POTTED_ACACIA_SAPLING,
                Blocks.POTTED_DARK_OAK_SAPLING,
                Blocks.POTTED_MANGROVE_PROPAGULE)) {
            setMapColor(block, PLANT);
        } else if (block.equals(Blocks.POTTED_DEAD_BUSH)) {
            setMapColor(block, WOOD);
        } else if (block.equals(Blocks.POTTED_CRIMSON_FUNGUS)
                || block.equals(Blocks.POTTED_CRIMSON_ROOTS)) {
            setMapColor(block, NETHER);
        } else if (Predicates.blockMatches(block,
                Blocks.POTTED_WARPED_FUNGUS,
                Blocks.POTTED_WARPED_ROOTS,
                Blocks.PITCHER_PLANT)) {
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
        } else if (Predicates.blockMatches(block,
                Blocks.ALLIUM,
                Blocks.LILAC,
                Blocks.POTTED_ALLIUM)) {
            setMapColor(block, COLOR_MAGENTA);
        } else if (Predicates.blockMatches(block,
                Blocks.WHITE_TULIP,
                Blocks.OXEYE_DAISY,
                Blocks.LILY_OF_THE_VALLEY,
                Blocks.POTTED_WHITE_TULIP,
                Blocks.POTTED_OXEYE_DAISY,
                Blocks.POTTED_LILY_OF_THE_VALLEY)) {
            setMapColor(block, SNOW);
        } else if (block.equals(Blocks.WITHER_ROSE)
                || block.equals(Blocks.POTTED_WITHER_ROSE)) {
            setMapColor(block, COLOR_BLACK);
        }
    }

    private static void setTemperatures(Block block) {
        if (Predicates.blockMatches(block,
                Blocks.FIRE,
                Blocks.LAVA,
                Blocks.LAVA_CAULDRON,
                Blocks.MAGMA_BLOCK)) {
            setTemperature(block, 15);
        } else if (block instanceof AbstractFurnaceBlock) {
            setTemperature(block.getStateDefinition().getPossibleStates().stream()
                    .filter(state -> state.getValue(AbstractFurnaceBlock.LIT)).toList(), 12);
        } else if (Predicates.blockMatches(block,
                Blocks.SHROOMLIGHT,
                Blocks.OCHRE_FROGLIGHT,
                Blocks.VERDANT_FROGLIGHT,
                Blocks.PEARLESCENT_FROGLIGHT)) {
            setTemperature(block, 9);
        } else if (Predicates.blockMatches(block,
                Blocks.SOUL_FIRE,
                Blocks.SOUL_CAMPFIRE,
                Blocks.SOUL_LANTERN,
                Blocks.SOUL_TORCH,
                Blocks.SOUL_WALL_TORCH,
                Blocks.SEA_LANTERN,
                Blocks.SEA_PICKLE,
                Blocks.CONDUIT,
                Blocks.END_GATEWAY,
                Blocks.END_PORTAL,
                Blocks.CAVE_VINES,
                Blocks.CAVE_VINES_PLANT)) {
            setTemperature(block, 0);
        } else if (block instanceof CampfireBlock) {
            setTemperature(block.getStateDefinition().getPossibleStates().stream()
                    .filter(state -> state.getValue(CampfireBlock.LIT)).toList(), 15);
        }

        Config.blockTemperature.forEach(VanillaBlockPropertyModifier::setTemperature);
    }

    private static void setThermalResistances(Block block) {
        Holder.Reference<Block> holder = block.builtInRegistryHolder();
        setThermalResistance(block.getStateDefinition().getPossibleStates().stream()
                .filter(state -> !state.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)).toList(), 5);

        setThermalResistance(block.getStateDefinition().getPossibleStates().stream()
                .filter(state -> {
                    Optional<Boolean> property = state.getOptionalValue(BlockStateProperties.WATERLOGGED);
                    return property.isPresent() && property.get() && ((IBlockStateBaseExtension) state).moreColorful$getThermalResistance() > 4;
                }).toList(), 4);

        if (Predicates.blockMatches(block,
                Blocks.DIAMOND_BLOCK,
                Blocks.COPPER_BLOCK,
                Blocks.CUT_COPPER,
                Blocks.CUT_COPPER_STAIRS,
                Blocks.CUT_COPPER_SLAB,
                Blocks.CHISELED_COPPER,
                Blocks.COPPER_BULB,
                Blocks.WAXED_COPPER_BLOCK,
                Blocks.WAXED_CUT_COPPER,
                Blocks.WAXED_CUT_COPPER_STAIRS,
                Blocks.WAXED_CUT_COPPER_SLAB,
                Blocks.WAXED_CHISELED_COPPER,
                Blocks.WAXED_COPPER_BULB,
                Blocks.EXPOSED_COPPER,
                Blocks.EXPOSED_CUT_COPPER,
                Blocks.EXPOSED_CUT_COPPER_STAIRS,
                Blocks.EXPOSED_CUT_COPPER_SLAB,
                Blocks.EXPOSED_CHISELED_COPPER,
                Blocks.EXPOSED_COPPER_BULB,
                Blocks.WAXED_EXPOSED_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER,
                Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS,
                Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB,
                Blocks.WAXED_EXPOSED_CHISELED_COPPER,
                Blocks.WAXED_EXPOSED_COPPER_BULB)) {
            setThermalResistance(block, 1);
        } else if (Predicates.blockMatches(block,
                Blocks.WEATHERED_COPPER,
                Blocks.WEATHERED_CUT_COPPER,
                Blocks.WEATHERED_CUT_COPPER_STAIRS,
                Blocks.WEATHERED_CUT_COPPER_SLAB,
                Blocks.WEATHERED_CHISELED_COPPER,
                Blocks.WEATHERED_COPPER_BULB,
                Blocks.WAXED_WEATHERED_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER,
                Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS,
                Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB,
                Blocks.WAXED_WEATHERED_CHISELED_COPPER,
                Blocks.WAXED_WEATHERED_COPPER_BULB,
                Blocks.OXIDIZED_COPPER,
                Blocks.OXIDIZED_CUT_COPPER,
                Blocks.OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.OXIDIZED_CUT_COPPER_SLAB,
                Blocks.OXIDIZED_CHISELED_COPPER,
                Blocks.OXIDIZED_COPPER_BULB,
                Blocks.WAXED_OXIDIZED_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS,
                Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                Blocks.WAXED_OXIDIZED_CHISELED_COPPER,
                Blocks.WAXED_OXIDIZED_COPPER_BULB,
                Blocks.IRON_BLOCK,
                Blocks.GOLD_BLOCK,
                Blocks.NETHERITE_BLOCK,
                Blocks.ANCIENT_DEBRIS)) {
            setThermalResistance(block, 2);
        } else if (Predicates.blockMatches(block,
                Blocks.PISTON,
                Blocks.PISTON_HEAD,
                Blocks.CHAIN,
                Blocks.HEAVY_CORE,
                Blocks.IRON_BARS,
                Blocks.IRON_DOOR,
                Blocks.IRON_TRAPDOOR,
                Blocks.HOPPER,
                Blocks.LIGHT_WEIGHTED_PRESSURE_PLATE,
                Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE,
                Blocks.COPPER_DOOR,
                Blocks.COPPER_TRAPDOOR,
                Blocks.COPPER_GRATE,
                Blocks.WAXED_COPPER_DOOR,
                Blocks.WAXED_COPPER_TRAPDOOR,
                Blocks.WAXED_COPPER_GRATE,
                Blocks.EXPOSED_COPPER_GRATE,
                Blocks.EXPOSED_COPPER_DOOR,
                Blocks.EXPOSED_COPPER_TRAPDOOR,
                Blocks.WAXED_EXPOSED_COPPER_GRATE,
                Blocks.WAXED_EXPOSED_COPPER_DOOR,
                Blocks.WAXED_EXPOSED_COPPER_TRAPDOOR,
                Blocks.WEATHERED_COPPER_GRATE,
                Blocks.WEATHERED_COPPER_DOOR,
                Blocks.WEATHERED_COPPER_TRAPDOOR,
                Blocks.WAXED_WEATHERED_COPPER_GRATE,
                Blocks.WAXED_WEATHERED_COPPER_DOOR,
                Blocks.WAXED_WEATHERED_COPPER_TRAPDOOR,
                Blocks.OXIDIZED_COPPER_GRATE,
                Blocks.OXIDIZED_COPPER_DOOR,
                Blocks.OXIDIZED_COPPER_TRAPDOOR,
                Blocks.WAXED_OXIDIZED_COPPER_GRATE,
                Blocks.WAXED_OXIDIZED_COPPER_DOOR,
                Blocks.WAXED_OXIDIZED_COPPER_TRAPDOOR,
                Blocks.SPAWNER,
                Blocks.TRIAL_SPAWNER,
                Blocks.VAULT)
                || Predicates.tagMatches(holder,
                BlockTags.ANVIL,
                BlockTags.CAULDRONS,
                Tags.Blocks.GLASS_BLOCKS,
                BlockTags.WALLS)) {
            setThermalResistance(block, 3);
        } else if (Predicates.blockMatches(block,
                Blocks.WATER,
                Blocks.CRAFTING_TABLE,
                Blocks.FLETCHING_TABLE,
                Blocks.CARTOGRAPHY_TABLE,
                Blocks.NOTE_BLOCK,
                Blocks.JUKEBOX,
                Blocks.SMITHING_TABLE,
                Blocks.LOOM,
                Blocks.BEE_NEST,
                Blocks.BEEHIVE,
                Blocks.PUMPKIN,
                Blocks.CARVED_PUMPKIN,
                Blocks.JACK_O_LANTERN,
                Blocks.MELON,
                Blocks.CACTUS,
                Blocks.MOSS_BLOCK,
                Blocks.SCULK,
                Blocks.SPONGE,
                Blocks.WET_SPONGE)
                || Predicates.tagMatches(holder,
                BlockTags.LOGS,
                BlockTags.PLANKS,
                BlockTags.WOODEN_STAIRS,
                BlockTags.WOODEN_SLABS,
                BlockTags.WOOL,
                Tags.Blocks.BARRELS_WOODEN,
                Tags.Blocks.BOOKSHELVES)) {
            setThermalResistance(block, 4);
        }

        Config.thermalResistance.forEach(VanillaBlockPropertyModifier::setThermalResistance);
    }

    private static final IdentityHashMap<Block, NoteBlockInstrument> instruments = Maps.newIdentityHashMap();
    private static final IdentityHashMap<Block, MapColor> mapColors = Maps.newIdentityHashMap();

    private static void setInstrument(Block block, NoteBlockInstrument instrument) {
        checkIfContains(instruments, block, block.defaultBlockState().instrument());
        block.getStateDefinition().getPossibleStates().forEach(blockState -> ((IBlockStateBaseMixin) blockState).setInstrument(instrument));
    }
    private static void setMapColor(Block block, MapColor mapColor) {
        checkIfContains(mapColors, block, block.defaultMapColor());
        block.getStateDefinition().getPossibleStates().forEach(blockState -> ((IBlockStateBaseMixin) blockState).setMapColor(mapColor));
    }
    private static void setTemperature(Block block, int temperature) {
        setTemperature(block.getStateDefinition().getPossibleStates(), temperature);
    }
    private static void setTemperature(List<BlockState> blockStates, int temperature) {
        blockStates.forEach(blockState -> ((IBlockStateBaseExtension) blockState).moreColorful$setTemperature(temperature));
    }
    private static void setThermalResistance(Block block, int resistance) {
        setThermalResistance(block.getStateDefinition().getPossibleStates(), resistance);
    }
    private static void setThermalResistance(List<BlockState> blockStates, int resistance) {
        blockStates.forEach(blockState -> ((IBlockStateBaseExtension) blockState).moreColorful$setThermalResistance(resistance));
    }

    private static <T> void checkIfContains(IdentityHashMap<Block, T> hashMap, Block block, T value) {
        if (!hashMap.containsKey(block)) {
            hashMap.put(block, value);
        }
    }
}
