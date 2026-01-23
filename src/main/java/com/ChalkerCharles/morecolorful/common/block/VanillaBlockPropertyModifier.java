package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.Config;
import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.block.natural.DuckweedsBlock;
import com.ChalkerCharles.morecolorful.common.block.natural.WillowBranchesBlock;
import com.ChalkerCharles.morecolorful.common.block.properties.InstrumentExtension;
import com.ChalkerCharles.morecolorful.util.*;
import com.ChalkerCharles.morecolorful.common.block.properties.HangingBlock;
import com.ChalkerCharles.morecolorful.mixin.extensions.IBlockStateExtension;
import com.ChalkerCharles.morecolorful.mixin.mixins.accessor.IBlockStateMixin;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BambooLeaves;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.TagsUpdatedEvent;

import java.util.*;

@SuppressWarnings("deprecation")
public final class VanillaBlockPropertyModifier {
    @SubscribeEvent
    public static void modifyDynamicProperties(TagsUpdatedEvent event) {
        BuiltInRegistries.BLOCK.forEach(block -> {
            setInstruments(block);
            if (Config.thermalSystem) {
                setTemperatures(block);
                setThermalResistances(block);
            }
        });
    }

    public static void modifyStaticProperties() {
        BuiltInRegistries.BLOCK.forEach(block -> {
            setMapColors(block);
            if (Config.windSystem) {
                setVertexTypes(block);
                setAirBlocking(block);
                setGroupBlock(block);
            }
        });
    }

    private static void setInstruments(Block block) {
        Holder.Reference<Block> holder = block.builtInRegistryHolder();
        if (holder.is(ModTags.Blocks.QUARTZ_BLOCKS)) {
            setInstrument(block, InstrumentExtension.PIANO_LOW);
        } else if (holder.is(ModTags.Blocks.PRISMARINES)) {
            setInstrument(block, InstrumentExtension.PIANO_HIGH);
        } else if (block == Blocks.MOSS_BLOCK) {
            setInstrument(block, InstrumentExtension.VIOLIN);
        } else if (holder.is(BlockTags.WART_BLOCKS)) {
            setInstrument(block, InstrumentExtension.CELLO);
        } else if (holder.is(ModTags.Blocks.NETHER_FUNGUS_WOODEN_BLOCKS)) {
            setInstrument(block, InstrumentExtension.ELECTRIC_GUITAR);
        } else if (holder.is(ModTags.Blocks.COPPER_BLOCKS)) {
            setInstrument(block, InstrumentExtension.TRUMPET);
        } else if (COPPER_GRATES.contains(block)) {
            setInstrument(block, InstrumentExtension.SAXOPHONE);
        } else if (holder.is(BlockTags.TERRACOTTA)) {
            setInstrument(block, InstrumentExtension.OCARINA);
        } else if (holder.is(ModTags.Blocks.TUFF_BLOCKS)) {
            setInstrument(block, InstrumentExtension.HARMONICA);
        } else if (holder.is(ModTags.Blocks.MUSHROOM_BLOCKS)) {
            setInstrument(block, InstrumentExtension.TOM);
        } else if (holder.is(ModTags.Blocks.BASALT_BLOCKS)) {
            setInstrument(block, InstrumentExtension.RIDE);
        } else if (block == Blocks.MAGMA_BLOCK) {
            setInstrument(block, InstrumentExtension.CRASH);
        } else if (block == Blocks.SCULK || block == Blocks.SCULK_CATALYST) {
            setInstrument(block, InstrumentExtension.SCULK);
        } else if (holder.is(BlockTags.CRYSTAL_SOUND_BLOCKS)) {
            setInstrument(block, InstrumentExtension.CRYSTAL);
        } else if (holder.is(BlockTags.REDSTONE_ORES)) {
            setInstrument(block, InstrumentExtension.SAW);
        } else if (block == Blocks.LAPIS_BLOCK) {
            setInstrument(block, InstrumentExtension.PLUCK);
        } else if (holder.is(Tags.Blocks.CONCRETES)) {
            setInstrument(block, InstrumentExtension.SYNTH_BASS);
        } else if (holder.is(Tags.Blocks.GLAZED_TERRACOTTAS)) {
            setInstrument(block, InstrumentExtension.PIPA);
        } else if (holder.is(ModTags.Blocks.PACKED_MUD_BLOCKS)) {
            setInstrument(block, InstrumentExtension.ERHU);
        } else if (holder.is(ModTags.Blocks.RARE_WOOD)) {
            setInstrument(block, InstrumentExtension.GUZHENG);
        }
    }

    private static void setMapColors(Block block) {
        if (PINK_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_PINK);
        } else if (YELLOW_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_YELLOW);
        } else if (RED_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_RED);
        } else if (ORANGE_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_ORANGE);
        } else if (DIRT_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.DIRT);
        } else if (PLANT_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.PLANT);
        } else if (block == Blocks.POTTED_DEAD_BUSH) {
            setMapColor(block, MapColor.WOOD);
        } else if (block == Blocks.POTTED_CRIMSON_FUNGUS
                || block == Blocks.POTTED_CRIMSON_ROOTS) {
            setMapColor(block, MapColor.NETHER);
        } else if (CYAN_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_CYAN);
        } else if (block == Blocks.BLUE_ORCHID
                || block == Blocks.POTTED_BLUE_ORCHID) {
            setMapColor(block, MapColor.COLOR_LIGHT_BLUE);
        } else if (block == Blocks.CORNFLOWER
                || block == Blocks.POTTED_CORNFLOWER) {
            setMapColor(block, MapColor.COLOR_BLUE);
        } else if (block == Blocks.AZURE_BLUET
                || block == Blocks.POTTED_AZURE_BLUET) {
            setMapColor(block, MapColor.CLAY);
        } else if (MAGENTA_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.COLOR_MAGENTA);
        } else if (SNOW_COLORED_BLOCKS.contains(block)) {
            setMapColor(block, MapColor.SNOW);
        } else if (block == Blocks.WITHER_ROSE
                || block == Blocks.POTTED_WITHER_ROSE) {
            setMapColor(block, MapColor.COLOR_BLACK);
        }
    }

    private static void setTemperatures(Block block) {
        if (TEMPERATURE_15.contains(block)) {
            setTemperature(block, 15);
        } else if (block instanceof AbstractFurnaceBlock) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                if (state.getValue(AbstractFurnaceBlock.LIT))
                    IBlockStateExtension.setTemperature(state, 12);
            }
        } else if (TEMPERATURE_9.contains(block)) {
            setTemperature(block, 9);
        } else if (TEMPERATURE_0.contains(block)) {
            setTemperature(block, 0);
        } else if (block instanceof CampfireBlock) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                if (state.getValue(CampfireBlock.LIT))
                    IBlockStateExtension.setTemperature(state, 15);
            }
        }

        for (Object2IntMap.Entry<List<BlockState>> entry : Config.blockTemperature.object2IntEntrySet()) {
            setTemperature(entry.getKey(), entry.getIntValue());
        }
    }

    private static void setThermalResistances(Block block) {
        for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
            if (!blockState.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)) {
                IBlockStateExtension.setThermalResistance(blockState, 5);
            } else {
                Optional<Boolean> property = blockState.getOptionalValue(BlockStateProperties.WATERLOGGED);
                if (property.isPresent() && property.get() && IBlockStateExtension.getThermalResistance(blockState) > 4) {
                    IBlockStateExtension.setThermalResistance(blockState, 4);
                }
            }
        }
        Holder.Reference<Block> holder = block.builtInRegistryHolder();
        if (THERMAL_RESISTANCE_1.contains(block)) {
            setThermalResistance(block, 1);
        } else if (THERMAL_RESISTANCE_2.contains(block)) {
            setThermalResistance(block, 2);
        } else if (THERMAL_RESISTANCE_3.contains(block)
                || Predicates.tagMatches(holder, THERMAL_RESISTANCE_3_TAGS)) {
            setThermalResistance(block, 3);
        } else if (THERMAL_RESISTANCE_4.contains(block)
                || Predicates.tagMatches(holder, THERMAL_RESISTANCE_4_TAGS)) {
            setThermalResistance(block, 4);
        }

        for (Object2IntMap.Entry<List<BlockState>> entry : Config.thermalResistance.object2IntEntrySet()) {
            setThermalResistance(entry.getKey(), entry.getIntValue());
        }
    }

    private static void setVertexTypes(Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            if (state.getFluidState().is(FluidTags.WATER)) {
                IBlockStateExtension.setVertexType(state, 15);
            }
        }

        if (block instanceof PinkPetalsBlock) {
            setVertexType(block, 1);
        } else if (block instanceof VineBlock) {
            setVertexType(block, 2);
        } else if (block instanceof WaterlilyBlock
                || block instanceof DuckweedsBlock) {
            setVertexType(block, 3);
        } else if (block instanceof ChainBlock) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                IBlockStateExtension.setVertexType(
                        state, state.getValue(ChainBlock.AXIS).isVertical() ? 4 : 0
                );
            }
        } else if (block instanceof LanternBlock) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                IBlockStateExtension.setVertexType(
                        state, state.getValue(LanternBlock.HANGING) ? 5 : 0
                );
            }
        } else if (block instanceof HangingBlock) {
            setVertexType(block, 6);
        } else if (block instanceof LeavesBlock || block instanceof WebBlock) {
            setVertexType(block, 7);
        } else if (block instanceof AzaleaBlock) {
            setVertexType(block, 8);
        } else if (block instanceof BambooStalkBlock) {
            for (BlockState state : block.getStateDefinition().getPossibleStates()) {
                IBlockStateExtension.setVertexType(
                        state, state.getValue(BambooStalkBlock.LEAVES) == BambooLeaves.NONE ? 0 : 9
                );
            }
        } else if (block instanceof FlowerPotBlock pot) {
            Block potted = pot.getPotted();
            if (potted instanceof AzaleaBlock) {
                setVertexType(block, 10);
            } else if (WeatherUtils.isWindSensitive(potted)) {
                setVertexType(block, 11);
            } else {
                setVertexType(block, 0);
            }
        } else if (block == Blocks.PITCHER_CROP) {
            setVertexType(block, 12);
        } else if (WeatherUtils.isWindSensitive(block)) {
            setVertexType(block, 13);
        }
    }

    private static void setAirBlocking(Block block) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            if (state.isAir()) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.EMPTY);
            } else if (!state.getFluidState().isEmpty()) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.FULL_BLOCK);
            } else if (block instanceof LeavesBlock || block instanceof ChorusFlowerBlock || block instanceof ChorusPlantBlock) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.SEMI_BLOCK);
            } else if (block instanceof WaterloggedTransparentBlock || block instanceof SpawnerBlock || block instanceof MangroveRootsBlock) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.EMPTY);
            } else if (block instanceof TransparentBlock || block instanceof ComposterBlock || block instanceof HoneyBlock
                    || block instanceof SlimeBlock || block instanceof IceBlock || block instanceof BarrierBlock || block instanceof PowderSnowBlock) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.FULL_BLOCK);
            } else if (block instanceof TrialSpawnerBlock || block instanceof VaultBlock) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.ONLY_Y);
            } else if (block == Blocks.GLASS_PANE || block instanceof StainedGlassPaneBlock) {
                boolean eastWest = state.getValue(IronBarsBlock.NORTH) || state.getValue(IronBarsBlock.SOUTH);
                boolean southNorth = state.getValue(IronBarsBlock.EAST) || state.getValue(IronBarsBlock.WEST);
                if (eastWest && southNorth) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.EXCEPT_Y);
                } else if (eastWest) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.ONLY_X);
                } else if (southNorth) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.ONLY_Z);
                } else {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.EMPTY);
                }
            } else if (block instanceof WallBlock) {
                boolean eastWest = state.getValue(WallBlock.NORTH_WALL) != WallSide.NONE || state.getValue(WallBlock.SOUTH_WALL) != WallSide.NONE;
                boolean southNorth = state.getValue(WallBlock.EAST_WALL) != WallSide.NONE || state.getValue(WallBlock.WEST_WALL) != WallSide.NONE;
                if (eastWest && southNorth) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.EXCEPT_Y);
                } else if (eastWest) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.ONLY_X);
                } else if (southNorth) {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.ONLY_Z);
                } else {
                    IBlockStateExtension.setAirBlock(state, AirBlocking.EMPTY);
                }
            } else if (state.isSolidRender(EmptyBlockGetter.INSTANCE, BlockPos.ZERO)) {
                IBlockStateExtension.setAirBlock(state, AirBlocking.FULL_BLOCK);
            } else {
                IBlockStateExtension.setAirBlock(state, AirBlocking.makeAirBlock(state));
            }
        }
    }

    private static void setGroupBlock(Block block) {
        if (!WeatherUtils.isWindSensitive(block)) return;
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            if (state.isAir() || !state.getFluidState().isEmpty()) {
                IBlockStateExtension.setGroupBlock(state, false);
            } else if (block instanceof VineBlock) {
                boolean u = state.getValue(VineBlock.UP);
                boolean n = state.getValue(VineBlock.NORTH);
                boolean e = state.getValue(VineBlock.EAST);
                boolean s = state.getValue(VineBlock.SOUTH);
                boolean w = state.getValue(VineBlock.WEST);
                boolean group = !u || n || e || s || w;
                IBlockStateExtension.setGroupBlock(state, group);
            } else if (block instanceof ChainBlock) {
                IBlockStateExtension.setGroupBlock(state, state.getValue(ChainBlock.AXIS).isVertical());
            } else if (block instanceof LanternBlock) {
                IBlockStateExtension.setGroupBlock(state, state.getValue(LanternBlock.HANGING));
            } else if (block instanceof BigDripleafBlock || block instanceof BigDripleafStemBlock
                    || block instanceof SugarCaneBlock || block instanceof GrowingPlantBlock
                    || block instanceof WillowBranchesBlock) {
                IBlockStateExtension.setGroupBlock(state, true);
            }
        }
    }

    private static void setInstrument(Block block, NoteBlockInstrument instrument) {
        for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
            ((IBlockStateMixin) blockState).setInstrument(instrument);
        }
    }
    private static void setMapColor(Block block, MapColor mapColor) {
        for (BlockState blockState : block.getStateDefinition().getPossibleStates()) {
            ((IBlockStateMixin) blockState).setMapColor(mapColor);
        }
    }
    private static void setTemperature(Block block, int temperature) {
        setTemperature(block.getStateDefinition().getPossibleStates(), temperature);
    }
    private static void setTemperature(List<BlockState> blockStates, int temperature) {
        for (BlockState state : blockStates) {
            IBlockStateExtension.setTemperature(state, temperature);
        }
    }
    private static void setThermalResistance(Block block, int resistance) {
        setThermalResistance(block.getStateDefinition().getPossibleStates(), resistance);
    }
    private static void setThermalResistance(List<BlockState> blockStates, int resistance) {
        for (BlockState state : blockStates) {
            IBlockStateExtension.setThermalResistance(state, resistance);
        }
    }
    private static void setVertexType(Block block, int type) {
        for (BlockState state : block.getStateDefinition().getPossibleStates()) {
            IBlockStateExtension.setVertexType(state, type);
        }
    }

    private static final Set<Block> PINK_COLORED_BLOCKS = Set.of(
            Blocks.PINK_PETALS,
            Blocks.PINK_TULIP,
            Blocks.PEONY,
            Blocks.POTTED_PINK_TULIP,
            Blocks.POTTED_CHERRY_SAPLING
    );
    private static final Set<Block> YELLOW_COLORED_BLOCKS = Set.of(
            Blocks.DANDELION,
            Blocks.SUNFLOWER,
            Blocks.POTTED_DANDELION
    );
    private static final Set<Block> RED_COLORED_BLOCKS = Set.of(
            Blocks.POPPY,
            Blocks.RED_TULIP,
            Blocks.ROSE_BUSH,
            Blocks.RED_MUSHROOM,
            Blocks.POTTED_POPPY,
            Blocks.POTTED_RED_TULIP,
            Blocks.POTTED_RED_MUSHROOM
    );
    private static final Set<Block> ORANGE_COLORED_BLOCKS = Set.of(
            Blocks.TORCHFLOWER,
            Blocks.ORANGE_TULIP,
            Blocks.POTTED_TORCHFLOWER,
            Blocks.POTTED_ORANGE_TULIP
    );
    private static final Set<Block> DIRT_COLORED_BLOCKS = Set.of(
            Blocks.FLOWER_POT,
            Blocks.BROWN_MUSHROOM,
            Blocks.POTTED_BROWN_MUSHROOM
    );
    private static final Set<Block> PLANT_COLORED_BLOCKS = Set.of(
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
            Blocks.POTTED_MANGROVE_PROPAGULE
    );
    private static final Set<Block> CYAN_COLORED_BLOCKS = Set.of(
            Blocks.POTTED_WARPED_FUNGUS,
            Blocks.POTTED_WARPED_ROOTS,
            Blocks.PITCHER_PLANT
    );
    private static final Set<Block> MAGENTA_COLORED_BLOCKS = Set.of(
            Blocks.ALLIUM,
            Blocks.LILAC,
            Blocks.POTTED_ALLIUM
    );
    private static final Set<Block> SNOW_COLORED_BLOCKS = Set.of(
            Blocks.WHITE_TULIP,
            Blocks.OXEYE_DAISY,
            Blocks.LILY_OF_THE_VALLEY,
            Blocks.POTTED_WHITE_TULIP,
            Blocks.POTTED_OXEYE_DAISY,
            Blocks.POTTED_LILY_OF_THE_VALLEY
    );

    private static final Set<Block> TEMPERATURE_15 = Set.of(
            Blocks.FIRE,
            Blocks.LAVA,
            Blocks.LAVA_CAULDRON,
            Blocks.MAGMA_BLOCK
    );
    private static final Set<Block> TEMPERATURE_9 = Set.of(
            Blocks.SHROOMLIGHT,
            Blocks.OCHRE_FROGLIGHT,
            Blocks.VERDANT_FROGLIGHT,
            Blocks.PEARLESCENT_FROGLIGHT
    );
    private static final Set<Block> TEMPERATURE_0 = Set.of(
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
            Blocks.CAVE_VINES_PLANT
    );

    private static final Set<Block> THERMAL_RESISTANCE_1 = Set.of(
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
            Blocks.WAXED_EXPOSED_COPPER_BULB
    );
    private static final Set<Block> THERMAL_RESISTANCE_2 = Set.of(
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
            Blocks.ANCIENT_DEBRIS
    );
    private static final Set<Block> THERMAL_RESISTANCE_3 = Set.of(
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
            Blocks.VAULT
    );
    private static final List<TagKey<Block>> THERMAL_RESISTANCE_3_TAGS = List.of(
            BlockTags.ANVIL,
            BlockTags.CAULDRONS,
            Tags.Blocks.GLASS_BLOCKS,
            BlockTags.WALLS
    );
    private static final Set<Block> THERMAL_RESISTANCE_4 = Set.of(
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
            Blocks.WET_SPONGE
    );
    private static final List<TagKey<Block>> THERMAL_RESISTANCE_4_TAGS = List.of(
            BlockTags.LOGS,
            BlockTags.PLANKS,
            BlockTags.WOODEN_STAIRS,
            BlockTags.WOODEN_SLABS,
            BlockTags.WOOL,
            Tags.Blocks.BARRELS_WOODEN,
            Tags.Blocks.BOOKSHELVES
    );
    private static final Set<Block> COPPER_GRATES = Set.of(
            Blocks.COPPER_GRATE,
            Blocks.WAXED_COPPER_GRATE,
            Blocks.EXPOSED_COPPER_GRATE,
            Blocks.WAXED_EXPOSED_COPPER_GRATE,
            Blocks.WEATHERED_COPPER_GRATE,
            Blocks.WAXED_WEATHERED_COPPER_GRATE,
            Blocks.OXIDIZED_COPPER_GRATE,
            Blocks.WAXED_OXIDIZED_COPPER_GRATE
    );
}
