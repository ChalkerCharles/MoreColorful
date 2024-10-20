package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.block.common.FallingLeavesBlock;
import com.ChalkerCharles.morecolorful.common.block.common.LeafPileBlock;
import com.ChalkerCharles.morecolorful.common.block.common.PetalLeavesBlock;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.*;
import com.ChalkerCharles.morecolorful.common.block.properties.ModBlockSetTypes;
import com.ChalkerCharles.morecolorful.common.block.properties.ModSoundTypes;
import com.ChalkerCharles.morecolorful.common.block.properties.ModWoodTypes;
import com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeGrower;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreColorful.MODID);

    // Musical Instruments
    public static final DeferredBlock<Block> HARP = BLOCKS.register("harp", ()-> new HarpBlock(InstrumentsType.HARP, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.TERRACOTTA_YELLOW).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> UPRIGHT_PIANO = BLOCKS.register("upright_piano", ()-> new UprightPianoBlock(InstrumentsType.PIANO_LOW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> GRAND_PIANO = BLOCKS.register("grand_piano", ()-> new GrandPianoBlock(InstrumentsType.PIANO_LOW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> BASS_DRUM = BLOCKS.register("bass_drum", ()-> new BassDrumBlock(InstrumentsType.BASS_DRUM, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SNARE_DRUM = BLOCKS.register("snare_drum", ()-> new SnareDrumBlock(InstrumentsType.SNARE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> TOMTOM_DRUM = BLOCKS.register("tom-tom_drum", ()-> new TomTomDrumBlock(InstrumentsType.TOM, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn()));
    public static final DeferredBlock<Block> HIHAT = BLOCKS.register("hi-hat", ()-> new HiHatBlock(InstrumentsType.HAT, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> RIDE_CYMBAL = BLOCKS.register("ride_cymbal", ()-> new RideCymbalBlock(InstrumentsType.RIDE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> CRASH_CYMBAL = BLOCKS.register("crash_cymbal", ()-> new CrashCymbalBlock(InstrumentsType.CRASH, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> DRUM_SET = BLOCKS.register("drum_set", ()-> new DrumSetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F, 6.0F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER).forceSolidOn()));
    public static final DeferredBlock<Block> CHIMES = BLOCKS.register("chimes", ()-> new ChimesBlock(InstrumentsType.CHIMES, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.CHIME).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GLOCKENSPIEL = BLOCKS.register("glockenspiel", ()-> new GlockenspielBlock(InstrumentsType.GLOCKENSPIEL, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> XYLOPHONE = BLOCKS.register("xylophone", ()-> new XylophoneBlock(InstrumentsType.XYLOPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> VIBRAPHONE = BLOCKS.register("vibraphone", ()-> new VibraphoneBlock(InstrumentsType.VIBRAPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_BIT = BLOCKS.register("synthesizer_keyboard_bit", ()-> new SynthesizerKeyboardBlock(InstrumentsType.BIT, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BIT).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLING = BLOCKS.register("synthesizer_keyboard_pling", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLING, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.PLING)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SCULK = BLOCKS.register("synthesizer_keyboard_sculk", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SCULK, BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK_CATALYST).instrument(NoteBlockInstrumentExtension.SCULK).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_AMETHYST = BLOCKS.register("synthesizer_keyboard_amethyst", ()-> new SynthesizerKeyboardBlock(InstrumentsType.CRYSTAL, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER).instrument(NoteBlockInstrumentExtension.CRYSTAL).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SAW = BLOCKS.register("synthesizer_keyboard_saw", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SAW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.CRIMSON_NYLIUM).instrument(NoteBlockInstrumentExtension.SAW)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLUCK = BLOCKS.register("synthesizer_keyboard_pluck", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLUCK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.LAPIS).instrument(NoteBlockInstrumentExtension.PLUCK)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SYNTH_BASS = BLOCKS.register("synthesizer_keyboard_synth_bass", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SYNTH_BASS, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.METAL).instrument(NoteBlockInstrumentExtension.SYNTH_BASS)));
    public static final DeferredBlock<Block> GUZHENG = BLOCKS.register("guzheng", ()-> new GuzhengBlock(InstrumentsType.GUZHENG, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY)));

    // Common Blocks
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_LOG = BLOCKS.register("crabapple_log", ()-> log(Blocks.CHERRY_LOG, MapColor.TERRACOTTA_PINK, MapColor.TERRACOTTA_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_WOOD = BLOCKS.register("crabapple_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_LOG = BLOCKS.register("stripped_crabapple_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_WOOD = BLOCKS.register("stripped_crabapple_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<Block> CRABAPPLE_PLANKS = BLOCKS.register("crabapple_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<StairBlock> CRABAPPLE_STAIRS = BLOCKS.register("crabapple_stairs", ()-> stair(CRABAPPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> CRABAPPLE_SLAB = BLOCKS.register("crabapple_slab", ()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceBlock> CRABAPPLE_FENCE = BLOCKS.register("crabapple_fence", ()-> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceGateBlock> CRABAPPLE_FENCE_GATE = BLOCKS.register("crabapple_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<DoorBlock> CRABAPPLE_DOOR = BLOCKS.register("crabapple_door", ()-> new DoorBlock(ModBlockSetTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<TrapDoorBlock> CRABAPPLE_TRAPDOOR = BLOCKS.register("crabapple_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<PressurePlateBlock> CRABAPPLE_PRESSURE_PLATE = BLOCKS.register("crabapple_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<ButtonBlock> CRABAPPLE_BUTTON = BLOCKS.register("crabapple_button", ()-> woodenButton(ModBlockSetTypes.CRABAPPLE, Blocks.CHERRY_BUTTON, MapColor.TERRACOTTA_PINK));
    public static final DeferredBlock<StandingSignBlock> CRABAPPLE_SIGN = BLOCKS.register("crabapple_sign", ()-> new StandingSignBlock(ModWoodTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallSignBlock> CRABAPPLE_WALL_SIGN = BLOCKS.register("crabapple_wall_sign", ()-> new WallSignBlock(ModWoodTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> CRABAPPLE_HANGING_SIGN = BLOCKS.register("crabapple_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallHangingSignBlock> CRABAPPLE_WALL_HANGING_SIGN = BLOCKS.register("crabapple_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> CRABAPPLE_LEAVES = BLOCKS.register("crabapple_leaves", ()-> new PetalLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColor.CRIMSON_NYLIUM), ModParticles.CRABAPPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> CRABAPPLE_SAPLING = BLOCKS.register("crabapple_sapling", ()-> new SaplingBlock(ModTreeGrower.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColor.CRIMSON_NYLIUM)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CRABAPPLE_SAPLING = BLOCKS.register("potted_crabapple_sapling", ()-> flowerPot(CRABAPPLE_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> BEGONIAS = BLOCKS.register("begonias", ()-> new PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.CRIMSON_NYLIUM)));

    public static final DeferredBlock<RotatedPillarBlock> EBONY_LOG = BLOCKS.register("ebony_log", ()-> rareLog(MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> EBONY_WOOD = BLOCKS.register("ebony_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_LOG = BLOCKS.register("stripped_ebony_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_WOOD = BLOCKS.register("stripped_ebony_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<Block> EBONY_PLANKS = BLOCKS.register("ebony_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<StairBlock> EBONY_STAIRS = BLOCKS.register("ebony_stairs", ()-> stair(EBONY_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> EBONY_SLAB = BLOCKS.register("ebony_slab", ()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceBlock> EBONY_FENCE = BLOCKS.register("ebony_fence", ()-> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceGateBlock> EBONY_FENCE_GATE = BLOCKS.register("ebony_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<DoorBlock> EBONY_DOOR = BLOCKS.register("ebony_door", ()-> new DoorBlock(ModBlockSetTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<TrapDoorBlock> EBONY_TRAPDOOR = BLOCKS.register("ebony_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<PressurePlateBlock> EBONY_PRESSURE_PLATE = BLOCKS.register("ebony_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_BLACK).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<ButtonBlock> EBONY_BUTTON = BLOCKS.register("ebony_button", ()-> rareWoodButton(ModBlockSetTypes.EBONY, Blocks.OAK_BUTTON, MapColor.TERRACOTTA_BLACK));
    public static final DeferredBlock<StandingSignBlock> EBONY_SIGN = BLOCKS.register("ebony_sign", ()-> new StandingSignBlock(ModWoodTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallSignBlock> EBONY_WALL_SIGN = BLOCKS.register("ebony_wall_sign", ()-> new WallSignBlock(ModWoodTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_SIGN).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<CeilingHangingSignBlock> EBONY_HANGING_SIGN = BLOCKS.register("ebony_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallHangingSignBlock> EBONY_WALL_HANGING_SIGN = BLOCKS.register("ebony_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.EBONY, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_HANGING_SIGN).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));

    public static final DeferredBlock<LeavesBlock> WHITE_CHERRY_LEAVES = BLOCKS.register("white_cherry_leaves", ()-> new PetalLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColor.SNOW), ModParticles.WHITE_CHERRY_LEAVES));
    public static final DeferredBlock<SaplingBlock> WHITE_CHERRY_SAPLING = BLOCKS.register("white_cherry_sapling", ()-> new SaplingBlock(ModTreeGrower.WHITE_CHERRY, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CHERRY_SAPLING = BLOCKS.register("potted_white_cherry_sapling", ()-> flowerPot(WHITE_CHERRY_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> WHITE_PETALS = BLOCKS.register("white_petals", ()-> new PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<LeavesBlock> AUTUMN_BIRCH_LEAVES = BLOCKS.register("autumn_birch_leaves", ()-> new FallingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_LEAVES).mapColor(MapColor.TERRACOTTA_YELLOW), ModParticles.AUTUMN_BIRCH_LEAVES));
    public static final DeferredBlock<SaplingBlock> AUTUMN_BIRCH_SAPLING = BLOCKS.register("autumn_birch_sapling", ()-> new SaplingBlock(ModTreeGrower.AUTUMN_BIRCH, BlockBehaviour.Properties.ofFullCopy(Blocks.BIRCH_SAPLING).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_AUTUMN_BIRCH_SAPLING = BLOCKS.register("potted_autumn_birch_sapling", ()-> flowerPot(AUTUMN_BIRCH_SAPLING));
    public static final DeferredBlock<LeafPileBlock> AUTUMN_BIRCH_LEAF_PILE = BLOCKS.register("autumn_birch_leaf_pile", ()-> new LeafPileBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.TERRACOTTA_YELLOW).replaceable()));

    public static final DeferredBlock<RotatedPillarBlock> GINKGO_LOG = BLOCKS.register("ginkgo_log", ()-> log(Blocks.OAK_LOG, MapColor.SAND, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> GINKGO_WOOD = BLOCKS.register("ginkgo_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_LOG = BLOCKS.register("stripped_ginkgo_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.SAND)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = BLOCKS.register("stripped_ginkgo_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.SAND)));
    public static final DeferredBlock<Block> GINKGO_PLANKS = BLOCKS.register("ginkgo_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.SAND)));
    public static final DeferredBlock<StairBlock> GINKGO_STAIRS = BLOCKS.register("ginkgo_stairs", ()-> stair(GINKGO_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> GINKGO_SLAB = BLOCKS.register("ginkgo_slab", ()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceBlock> GINKGO_FENCE = BLOCKS.register("ginkgo_fence", ()-> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceGateBlock> GINKGO_FENCE_GATE = BLOCKS.register("ginkgo_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<DoorBlock> GINKGO_DOOR = BLOCKS.register("ginkgo_door", ()-> new DoorBlock(ModBlockSetTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<TrapDoorBlock> GINKGO_TRAPDOOR = BLOCKS.register("ginkgo_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<PressurePlateBlock> GINKGO_PRESSURE_PLATE = BLOCKS.register("ginkgo_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<ButtonBlock> GINKGO_BUTTON = BLOCKS.register("ginkgo_button", ()-> woodenButton(ModBlockSetTypes.GINKGO, Blocks.OAK_BUTTON, MapColor.SAND));
    public static final DeferredBlock<StandingSignBlock> GINKGO_SIGN = BLOCKS.register("ginkgo_sign", ()-> new StandingSignBlock(ModWoodTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallSignBlock> GINKGO_WALL_SIGN = BLOCKS.register("ginkgo_wall_sign", ()-> new WallSignBlock(ModWoodTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> GINKGO_HANGING_SIGN = BLOCKS.register("ginkgo_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallHangingSignBlock> GINKGO_WALL_HANGING_SIGN = BLOCKS.register("ginkgo_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> GINKGO_LEAVES = BLOCKS.register("ginkgo_leaves", ()-> new FallingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.GOLD), ModParticles.GINKGO_LEAVES));
    public static final DeferredBlock<SaplingBlock> GINKGO_SAPLING = BLOCKS.register("ginkgo_sapling", ()-> new SaplingBlock(ModTreeGrower.GINKGO, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.GOLD)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GINKGO_SAPLING = BLOCKS.register("potted_ginkgo_sapling", ()-> flowerPot(GINKGO_SAPLING));
    public static final DeferredBlock<LeafPileBlock> GINKGO_LEAF_PILE = BLOCKS.register("ginkgo_leaf_pile", ()-> new LeafPileBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AUTUMN_BIRCH_LEAF_PILE.get()).mapColor(MapColor.GOLD)));

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", ()-> log(Blocks.OAK_LOG, MapColor.RAW_IRON, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", ()-> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<StairBlock> MAPLE_STAIRS = BLOCKS.register("maple_stairs", ()-> stair(MAPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> MAPLE_SLAB = BLOCKS.register("maple_slab", ()-> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceBlock> MAPLE_FENCE = BLOCKS.register("maple_fence", ()-> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceGateBlock> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<DoorBlock> MAPLE_DOOR = BLOCKS.register("maple_door", ()-> new DoorBlock(ModBlockSetTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<TrapDoorBlock> MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<PressurePlateBlock> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<ButtonBlock> MAPLE_BUTTON = BLOCKS.register("maple_button", ()-> woodenButton(ModBlockSetTypes.MAPLE, Blocks.OAK_BUTTON, MapColor.RAW_IRON));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign", ()-> new StandingSignBlock(ModWoodTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", ()-> new WallSignBlock(ModWoodTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = BLOCKS.register("maple_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = BLOCKS.register("maple_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> MAPLE_LEAVES = BLOCKS.register("maple_leaves", ()-> new FallingLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_RED), ModParticles.MAPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> MAPLE_SAPLING = BLOCKS.register("maple_sapling", ()-> new SaplingBlock(ModTreeGrower.MAPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling", ()-> flowerPot(MAPLE_SAPLING));
    public static final DeferredBlock<LeafPileBlock> MAPLE_LEAF_PILE = BLOCKS.register("maple_leaf_pile", ()-> new LeafPileBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.AUTUMN_BIRCH_LEAF_PILE.get()).mapColor(MapColor.TERRACOTTA_RED)));

    public static final DeferredBlock<FlowerBlock> PINK_DAISY = BLOCKS.register("pink_daisy", ()-> new FlowerBlock(MobEffects.REGENERATION, 8.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_DAISY = BLOCKS.register("potted_pink_daisy", ()-> flowerPot(PINK_DAISY));
    public static final DeferredBlock<FlowerBlock> RED_CARNATION = BLOCKS.register("red_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_CARNATION = BLOCKS.register("potted_red_carnation", ()-> flowerPot(RED_CARNATION));
    public static final DeferredBlock<FlowerBlock> PINK_CARNATION = BLOCKS.register("pink_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_TULIP).mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_CARNATION = BLOCKS.register("potted_pink_carnation", ()-> flowerPot(PINK_CARNATION));
    public static final DeferredBlock<FlowerBlock> WHITE_CARNATION = BLOCKS.register("white_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_TULIP).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CARNATION = BLOCKS.register("potted_white_carnation", ()-> flowerPot(WHITE_CARNATION));
    public static final DeferredBlock<FlowerBlock> RED_SPIDER_LILY = BLOCKS.register("red_spider_lily", ()-> new FlowerBlock(MobEffects.POISON, 10.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.POPPY).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_SPIDER_LILY = BLOCKS.register("potted_red_spider_lily", ()-> flowerPot(RED_SPIDER_LILY));
    public static final DeferredBlock<FlowerBlock> YELLOW_CHRYSANTHEMUM = BLOCKS.register("yellow_chrysanthemum", ()-> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_CHRYSANTHEMUM = BLOCKS.register("potted_yellow_chrysanthemum", ()-> flowerPot(YELLOW_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> GREEN_CHRYSANTHEMUM = BLOCKS.register("green_chrysanthemum", ()-> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREEN_CHRYSANTHEMUM = BLOCKS.register("potted_green_chrysanthemum", ()-> flowerPot(GREEN_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> DAYBLOOM = BLOCKS.register("daybloom", ()-> new FlowerBlock(MobEffects.ABSORPTION, 12.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_DAYBLOOM = BLOCKS.register("potted_daybloom", ()-> flowerPot(DAYBLOOM));

    private static RotatedPillarBlock log(Block pBlock, MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.ofFullCopy(pBlock)
                        .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
        );
    }
    @SuppressWarnings("SameParameterValue")
    private static RotatedPillarBlock rareLog(MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                        .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
                        .sound(ModSoundTypes.RARE_WOOD).strength(2.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)
        );
    }
    private static StairBlock stair(Block pBaseBlock) {
        return new StairBlock(pBaseBlock.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(pBaseBlock));
    }
    private static ButtonBlock woodenButton(BlockSetType pType, Block pBlock, MapColor pColor) {
        return new ButtonBlock(pType, 30, BlockBehaviour.Properties.ofFullCopy(pBlock).mapColor(pColor));
    }
    @SuppressWarnings("SameParameterValue")
    private static ButtonBlock rareWoodButton(BlockSetType pType, Block pBlock, MapColor pColor) {
        return new ButtonBlock(pType, 30, BlockBehaviour.Properties.ofFullCopy(pBlock).mapColor(pColor).instrument(NoteBlockInstrumentExtension.GUZHENG));
    }
    private static FlowerPotBlock flowerPot(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> pPotted) {
        return new FlowerPotBlock(emptyPot, pPotted, BlockBehaviour.Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY).mapColor(pPotted.get().defaultMapColor()));
    }
    private static FlowerPotBlock flowerPot(Supplier<? extends Block> pPotted) {
        return flowerPot(() -> (FlowerPotBlock) Blocks.FLOWER_POT, pPotted);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
