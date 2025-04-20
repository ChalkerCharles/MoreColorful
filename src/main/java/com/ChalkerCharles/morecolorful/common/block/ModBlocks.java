package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.block.common.*;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.*;
import com.ChalkerCharles.morecolorful.common.block.properties.*;
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

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.of;
import static net.minecraft.world.level.block.state.BlockBehaviour.Properties.ofFullCopy;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MoreColorful.MODID);

    // Musical Instruments
    public static final DeferredBlock<Block> HARP = BLOCKS.register("harp", ()-> new HarpBlock(InstrumentsType.HARP, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.TERRACOTTA_YELLOW).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> UPRIGHT_PIANO = BLOCKS.register("upright_piano", ()-> new UprightPianoBlock(InstrumentsType.PIANO_LOW, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> GRAND_PIANO = BLOCKS.register("grand_piano", ()-> new GrandPianoBlock(InstrumentsType.PIANO_LOW, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> BASS_DRUM = BLOCKS.register("bass_drum", ()-> new BassDrumBlock(InstrumentsType.BASS_DRUM, of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SNARE_DRUM = BLOCKS.register("snare_drum", ()-> new SnareDrumBlock(InstrumentsType.SNARE, ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> TOMTOM_DRUM = BLOCKS.register("tom-tom_drum", ()-> new TomTomDrumBlock(InstrumentsType.TOM, of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn()));
    public static final DeferredBlock<Block> HIHAT = BLOCKS.register("hi-hat", ()-> new HiHatBlock(InstrumentsType.HAT, ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> RIDE_CYMBAL = BLOCKS.register("ride_cymbal", ()-> new RideCymbalBlock(InstrumentsType.RIDE, ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> CRASH_CYMBAL = BLOCKS.register("crash_cymbal", ()-> new CrashCymbalBlock(InstrumentsType.CRASH, ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> DRUM_SET = BLOCKS.register("drum_set", ()-> new DrumSetBlock(of().mapColor(MapColor.METAL).strength(3.0F, 6.0F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER).forceSolidOn()));
    public static final DeferredBlock<Block> CHIMES = BLOCKS.register("chimes", ()-> new ChimesBlock(InstrumentsType.CHIMES, ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.CHIME).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GLOCKENSPIEL = BLOCKS.register("glockenspiel", ()-> new GlockenspielBlock(InstrumentsType.GLOCKENSPIEL, ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> XYLOPHONE = BLOCKS.register("xylophone", ()-> new XylophoneBlock(InstrumentsType.XYLOPHONE, ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> VIBRAPHONE = BLOCKS.register("vibraphone", ()-> new VibraphoneBlock(InstrumentsType.VIBRAPHONE, ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_BIT = BLOCKS.register("synthesizer_keyboard_bit", ()-> new SynthesizerKeyboardBlock(InstrumentsType.BIT, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BIT).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLING = BLOCKS.register("synthesizer_keyboard_pling", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLING, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.PLING)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SCULK = BLOCKS.register("synthesizer_keyboard_sculk", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SCULK, ofFullCopy(Blocks.SCULK_CATALYST).instrument(NoteBlockInstrumentExtension.SCULK).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_AMETHYST = BLOCKS.register("synthesizer_keyboard_amethyst", ()-> new SynthesizerKeyboardBlock(InstrumentsType.CRYSTAL, ofFullCopy(Blocks.AMETHYST_CLUSTER).instrument(NoteBlockInstrumentExtension.CRYSTAL).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SAW = BLOCKS.register("synthesizer_keyboard_saw", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SAW, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.CRIMSON_NYLIUM).instrument(NoteBlockInstrumentExtension.SAW)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLUCK = BLOCKS.register("synthesizer_keyboard_pluck", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLUCK, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.LAPIS).instrument(NoteBlockInstrumentExtension.PLUCK)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SYNTH_BASS = BLOCKS.register("synthesizer_keyboard_synth_bass", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SYNTH_BASS, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.METAL).instrument(NoteBlockInstrumentExtension.SYNTH_BASS)));
    public static final DeferredBlock<Block> GUZHENG = BLOCKS.register("guzheng", ()-> new GuzhengBlock(InstrumentsType.GUZHENG, ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY)));

    // Common Blocks
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_LOG = BLOCKS.register("crabapple_log", ()-> log(Blocks.CHERRY_LOG, MapColor.TERRACOTTA_PINK, MapColor.TERRACOTTA_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_WOOD = BLOCKS.register("crabapple_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_LOG = BLOCKS.register("stripped_crabapple_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_WOOD = BLOCKS.register("stripped_crabapple_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<Block> CRABAPPLE_PLANKS = BLOCKS.register("crabapple_planks", ()-> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<StairBlock> CRABAPPLE_STAIRS = BLOCKS.register("crabapple_stairs", ()-> stair(CRABAPPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> CRABAPPLE_SLAB = BLOCKS.register("crabapple_slab", ()-> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceBlock> CRABAPPLE_FENCE = BLOCKS.register("crabapple_fence", ()-> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceGateBlock> CRABAPPLE_FENCE_GATE = BLOCKS.register("crabapple_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<DoorBlock> CRABAPPLE_DOOR = BLOCKS.register("crabapple_door", ()-> new DoorBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<TrapDoorBlock> CRABAPPLE_TRAPDOOR = BLOCKS.register("crabapple_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<PressurePlateBlock> CRABAPPLE_PRESSURE_PLATE = BLOCKS.register("crabapple_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<ButtonBlock> CRABAPPLE_BUTTON = BLOCKS.register("crabapple_button", ()-> woodenButton(ModBlockSetTypes.CRABAPPLE, Blocks.CHERRY_BUTTON, MapColor.TERRACOTTA_PINK));
    public static final DeferredBlock<StandingSignBlock> CRABAPPLE_SIGN = BLOCKS.register("crabapple_sign", ()-> new StandingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallSignBlock> CRABAPPLE_WALL_SIGN = BLOCKS.register("crabapple_wall_sign", ()-> new WallSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> CRABAPPLE_HANGING_SIGN = BLOCKS.register("crabapple_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallHangingSignBlock> CRABAPPLE_WALL_HANGING_SIGN = BLOCKS.register("crabapple_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> CRABAPPLE_LEAVES = BLOCKS.register("crabapple_leaves", ()-> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.CRABAPPLE), ModParticles.CRABAPPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> CRABAPPLE_SAPLING = BLOCKS.register("crabapple_sapling", ()-> new SaplingBlock(ModTreeGrower.CRABAPPLE, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.CRABAPPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CRABAPPLE_SAPLING = BLOCKS.register("potted_crabapple_sapling", ()-> flowerPot(CRABAPPLE_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> BEGONIAS = BLOCKS.register("begonias", ()-> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.BEGONIA)));

    public static final DeferredBlock<RotatedPillarBlock> EBONY_LOG = BLOCKS.register("ebony_log", ()-> rareLog(MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> EBONY_WOOD = BLOCKS.register("ebony_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_LOG = BLOCKS.register("stripped_ebony_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_WOOD = BLOCKS.register("stripped_ebony_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<Block> EBONY_PLANKS = BLOCKS.register("ebony_planks", ()-> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<StairBlock> EBONY_STAIRS = BLOCKS.register("ebony_stairs", ()-> stair(EBONY_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> EBONY_SLAB = BLOCKS.register("ebony_slab", ()-> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceBlock> EBONY_FENCE = BLOCKS.register("ebony_fence", ()-> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceGateBlock> EBONY_FENCE_GATE = BLOCKS.register("ebony_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<DoorBlock> EBONY_DOOR = BLOCKS.register("ebony_door", ()-> new DoorBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<TrapDoorBlock> EBONY_TRAPDOOR = BLOCKS.register("ebony_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<PressurePlateBlock> EBONY_PRESSURE_PLATE = BLOCKS.register("ebony_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_BLACK).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<ButtonBlock> EBONY_BUTTON = BLOCKS.register("ebony_button", ()-> rareWoodButton(ModBlockSetTypes.EBONY, Blocks.OAK_BUTTON, MapColor.TERRACOTTA_BLACK));
    public static final DeferredBlock<StandingSignBlock> EBONY_SIGN = BLOCKS.register("ebony_sign", ()-> new StandingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallSignBlock> EBONY_WALL_SIGN = BLOCKS.register("ebony_wall_sign", ()-> new WallSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_SIGN).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<CeilingHangingSignBlock> EBONY_HANGING_SIGN = BLOCKS.register("ebony_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallHangingSignBlock> EBONY_WALL_HANGING_SIGN = BLOCKS.register("ebony_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_HANGING_SIGN).strength(1.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)));

    public static final DeferredBlock<LeavesBlock> WHITE_CHERRY_LEAVES = BLOCKS.register("white_cherry_leaves", ()-> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColor.SNOW), ModParticles.WHITE_CHERRY_LEAVES));
    public static final DeferredBlock<SaplingBlock> WHITE_CHERRY_SAPLING = BLOCKS.register("white_cherry_sapling", ()-> new SaplingBlock(ModTreeGrower.WHITE_CHERRY, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CHERRY_SAPLING = BLOCKS.register("potted_white_cherry_sapling", ()-> flowerPot(WHITE_CHERRY_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> WHITE_PETALS = BLOCKS.register("white_petals", ()-> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<LeavesBlock> ORANGE_BIRCH_LEAVES = BLOCKS.register("orange_birch_leaves", ()-> new FallingLeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).mapColor(MapColorExtension.ORANGE_BIRCH), ModParticles.ORANGE_BIRCH_LEAVES));
    public static final DeferredBlock<SaplingBlock> ORANGE_BIRCH_SAPLING = BLOCKS.register("orange_birch_sapling", ()-> new SaplingBlock(ModTreeGrower.ORANGE_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING).mapColor(MapColorExtension.ORANGE_BIRCH)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_ORANGE_BIRCH_SAPLING = BLOCKS.register("potted_orange_birch_sapling", ()-> flowerPot(ORANGE_BIRCH_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> ORANGE_BIRCH_LEAF_LITTER = BLOCKS.register("orange_birch_leaf_litter", ()-> new LeafLitterBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.ORANGE_BIRCH).sound(ModSoundTypes.LEAF_LITTER).replaceable()));
    public static final DeferredBlock<LeavesBlock> YELLOW_BIRCH_LEAVES = BLOCKS.register("yellow_birch_leaves", ()-> new FallingLeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).mapColor(MapColor.TERRACOTTA_YELLOW), ModParticles.YELLOW_BIRCH_LEAVES));
    public static final DeferredBlock<SaplingBlock> YELLOW_BIRCH_SAPLING = BLOCKS.register("yellow_birch_sapling", ()-> new SaplingBlock(ModTreeGrower.YELLOW_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_BIRCH_SAPLING = BLOCKS.register("potted_yellow_birch_sapling", ()-> flowerPot(YELLOW_BIRCH_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> YELLOW_BIRCH_LEAF_LITTER = BLOCKS.register("yellow_birch_leaf_litter", ()-> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_YELLOW).sound(ModSoundTypes.LEAF_LITTER).replaceable()));

    public static final DeferredBlock<RotatedPillarBlock> GINKGO_LOG = BLOCKS.register("ginkgo_log", ()-> log(Blocks.OAK_LOG, MapColor.SAND, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> GINKGO_WOOD = BLOCKS.register("ginkgo_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_LOG = BLOCKS.register("stripped_ginkgo_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.SAND)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = BLOCKS.register("stripped_ginkgo_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.SAND)));
    public static final DeferredBlock<Block> GINKGO_PLANKS = BLOCKS.register("ginkgo_planks", ()-> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.SAND)));
    public static final DeferredBlock<StairBlock> GINKGO_STAIRS = BLOCKS.register("ginkgo_stairs", ()-> stair(GINKGO_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> GINKGO_SLAB = BLOCKS.register("ginkgo_slab", ()-> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceBlock> GINKGO_FENCE = BLOCKS.register("ginkgo_fence", ()-> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceGateBlock> GINKGO_FENCE_GATE = BLOCKS.register("ginkgo_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<DoorBlock> GINKGO_DOOR = BLOCKS.register("ginkgo_door", ()-> new DoorBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<TrapDoorBlock> GINKGO_TRAPDOOR = BLOCKS.register("ginkgo_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<PressurePlateBlock> GINKGO_PRESSURE_PLATE = BLOCKS.register("ginkgo_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<ButtonBlock> GINKGO_BUTTON = BLOCKS.register("ginkgo_button", ()-> woodenButton(ModBlockSetTypes.GINKGO, Blocks.OAK_BUTTON, MapColor.SAND));
    public static final DeferredBlock<StandingSignBlock> GINKGO_SIGN = BLOCKS.register("ginkgo_sign", ()-> new StandingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallSignBlock> GINKGO_WALL_SIGN = BLOCKS.register("ginkgo_wall_sign", ()-> new WallSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> GINKGO_HANGING_SIGN = BLOCKS.register("ginkgo_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallHangingSignBlock> GINKGO_WALL_HANGING_SIGN = BLOCKS.register("ginkgo_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> GINKGO_LEAVES = BLOCKS.register("ginkgo_leaves", ()-> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.GOLD), ModParticles.GINKGO_LEAVES));
    public static final DeferredBlock<SaplingBlock> GINKGO_SAPLING = BLOCKS.register("ginkgo_sapling", ()-> new SaplingBlock(ModTreeGrower.GINKGO, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.GOLD)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GINKGO_SAPLING = BLOCKS.register("potted_ginkgo_sapling", ()-> flowerPot(GINKGO_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> GINKGO_LEAF_LITTER = BLOCKS.register("ginkgo_leaf_litter", ()-> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.GOLD)));

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = BLOCKS.register("maple_log", ()-> log(Blocks.OAK_LOG, MapColor.RAW_IRON, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = BLOCKS.register("maple_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = BLOCKS.register("stripped_maple_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = BLOCKS.register("stripped_maple_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> MAPLE_PLANKS = BLOCKS.register("maple_planks", ()-> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<StairBlock> MAPLE_STAIRS = BLOCKS.register("maple_stairs", ()-> stair(MAPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> MAPLE_SLAB = BLOCKS.register("maple_slab", ()-> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceBlock> MAPLE_FENCE = BLOCKS.register("maple_fence", ()-> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceGateBlock> MAPLE_FENCE_GATE = BLOCKS.register("maple_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<DoorBlock> MAPLE_DOOR = BLOCKS.register("maple_door", ()-> new DoorBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<TrapDoorBlock> MAPLE_TRAPDOOR = BLOCKS.register("maple_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<PressurePlateBlock> MAPLE_PRESSURE_PLATE = BLOCKS.register("maple_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<ButtonBlock> MAPLE_BUTTON = BLOCKS.register("maple_button", ()-> woodenButton(ModBlockSetTypes.MAPLE, Blocks.OAK_BUTTON, MapColor.RAW_IRON));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = BLOCKS.register("maple_sign", ()-> new StandingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = BLOCKS.register("maple_wall_sign", ()-> new WallSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = BLOCKS.register("maple_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = BLOCKS.register("maple_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> MAPLE_LEAVES = BLOCKS.register("maple_leaves", ()-> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_RED), ModParticles.MAPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> MAPLE_SAPLING = BLOCKS.register("maple_sapling", ()-> new SaplingBlock(ModTreeGrower.MAPLE, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAPLE_SAPLING = BLOCKS.register("potted_maple_sapling", ()-> flowerPot(MAPLE_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> MAPLE_LEAF_LITTER = BLOCKS.register("maple_leaf_litter", ()-> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_RED)));
    public static final DeferredBlock<RotatedPillarBlock> FROST_LOG = BLOCKS.register("frost_log", ()-> log(Blocks.CHERRY_LOG, MapColorExtension.FROST_WOOD, MapColor.TERRACOTTA_BLUE));
    public static final DeferredBlock<RotatedPillarBlock> FROST_WOOD = BLOCKS.register("frost_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_BLUE)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROST_LOG = BLOCKS.register("stripped_frost_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROST_WOOD = BLOCKS.register("stripped_frost_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<Block> FROST_PLANKS = BLOCKS.register("frost_planks", ()-> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<StairBlock> FROST_STAIRS = BLOCKS.register("frost_stairs", ()-> stair(FROST_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> FROST_SLAB = BLOCKS.register("frost_slab", ()-> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<FenceBlock> FROST_FENCE = BLOCKS.register("frost_fence", ()-> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<FenceGateBlock> FROST_FENCE_GATE = BLOCKS.register("frost_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<DoorBlock> FROST_DOOR = BLOCKS.register("frost_door", ()-> new DoorBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<TrapDoorBlock> FROST_TRAPDOOR = BLOCKS.register("frost_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<PressurePlateBlock> FROST_PRESSURE_PLATE = BLOCKS.register("frost_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<ButtonBlock> FROST_BUTTON = BLOCKS.register("frost_button", ()-> woodenButton(ModBlockSetTypes.FROST, Blocks.CHERRY_BUTTON, MapColorExtension.FROST_WOOD));
    public static final DeferredBlock<StandingSignBlock> FROST_SIGN = BLOCKS.register("frost_sign", ()-> new StandingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<WallSignBlock> FROST_WALL_SIGN = BLOCKS.register("frost_wall_sign", ()-> new WallSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColorExtension.FROST_WOOD).lootFrom(FROST_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> FROST_HANGING_SIGN = BLOCKS.register("frost_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<WallHangingSignBlock> FROST_WALL_HANGING_SIGN = BLOCKS.register("frost_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColorExtension.FROST_WOOD).lootFrom(FROST_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> FROST_LEAVES = BLOCKS.register("frost_leaves", ()-> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.FROST), ModParticles.FROST_LEAVES));
    public static final DeferredBlock<SaplingBlock> FROST_SAPLING = BLOCKS.register("frost_sapling", ()-> new SaplingBlock(ModTreeGrower.FROST, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.FROST)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_FROST_SAPLING = BLOCKS.register("potted_frost_sapling", ()-> flowerPot(FROST_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> FROSTY_PETALS = BLOCKS.register("frosty_petals", ()-> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.FROST)));
    public static final DeferredBlock<RotatedPillarBlock> DAWN_REDWOOD_LOG = BLOCKS.register("dawn_redwood_log", ()-> log(Blocks.OAK_LOG, MapColor.TERRACOTTA_ORANGE, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> DAWN_REDWOOD_WOOD = BLOCKS.register("dawn_redwood_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DAWN_REDWOOD_LOG = BLOCKS.register("stripped_dawn_redwood_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DAWN_REDWOOD_WOOD = BLOCKS.register("stripped_dawn_redwood_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_PLANKS = BLOCKS.register("dawn_redwood_planks", ()-> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<StairBlock> DAWN_REDWOOD_STAIRS = BLOCKS.register("dawn_redwood_stairs", ()-> stair(DAWN_REDWOOD_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> DAWN_REDWOOD_SLAB = BLOCKS.register("dawn_redwood_slab", ()-> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FenceBlock> DAWN_REDWOOD_FENCE = BLOCKS.register("dawn_redwood_fence", ()-> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FenceGateBlock> DAWN_REDWOOD_FENCE_GATE = BLOCKS.register("dawn_redwood_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<DoorBlock> DAWN_REDWOOD_DOOR = BLOCKS.register("dawn_redwood_door", ()-> new DoorBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<TrapDoorBlock> DAWN_REDWOOD_TRAPDOOR = BLOCKS.register("dawn_redwood_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<PressurePlateBlock> DAWN_REDWOOD_PRESSURE_PLATE = BLOCKS.register("dawn_redwood_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<ButtonBlock> DAWN_REDWOOD_BUTTON = BLOCKS.register("dawn_redwood_button", ()-> woodenButton(ModBlockSetTypes.DAWN_REDWOOD, Blocks.OAK_BUTTON, MapColor.TERRACOTTA_ORANGE));
    public static final DeferredBlock<StandingSignBlock> DAWN_REDWOOD_SIGN = BLOCKS.register("dawn_redwood_sign", ()-> new StandingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<WallSignBlock> DAWN_REDWOOD_WALL_SIGN = BLOCKS.register("dawn_redwood_wall_sign", ()-> new WallSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE).lootFrom(DAWN_REDWOOD_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> DAWN_REDWOOD_HANGING_SIGN = BLOCKS.register("dawn_redwood_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<WallHangingSignBlock> DAWN_REDWOOD_WALL_HANGING_SIGN = BLOCKS.register("dawn_redwood_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE).lootFrom(DAWN_REDWOOD_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> DAWN_REDWOOD_LEAVES = BLOCKS.register("dawn_redwood_leaves", ()-> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_ORANGE), ModParticles.DAWN_REDWOOD_LEAVES));
    public static final DeferredBlock<SaplingBlock> DAWN_REDWOOD_SAPLING = BLOCKS.register("dawn_redwood_sapling", ()-> new WaterloggedSaplingBlock(ModTreeGrower.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_DAWN_REDWOOD_SAPLING = BLOCKS.register("potted_dawn_redwood_sapling", ()-> flowerPot(DAWN_REDWOOD_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> DAWN_REDWOOD_LEAF_LITTER = BLOCKS.register("dawn_redwood_leaf_litter", ()-> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_ROOTS = BLOCKS.register("dawn_redwood_roots", ()-> new DawnRedwoodRootBlock(ofFullCopy(Blocks.MANGROVE_ROOTS).mapColor(MapColor.COLOR_BROWN)));

    public static final DeferredBlock<RotatedPillarBlock> JACARANDA_LOG = BLOCKS.register("jacaranda_log", ()-> log(Blocks.CHERRY_LOG, MapColorExtension.JACARANDA_WOOD, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> JACARANDA_WOOD = BLOCKS.register("jacaranda_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_JACARANDA_LOG = BLOCKS.register("stripped_jacaranda_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_JACARANDA_WOOD = BLOCKS.register("stripped_jacaranda_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<Block> JACARANDA_PLANKS = BLOCKS.register("jacaranda_planks", ()-> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<StairBlock> JACARANDA_STAIRS = BLOCKS.register("jacaranda_stairs", ()-> stair(JACARANDA_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> JACARANDA_SLAB = BLOCKS.register("jacaranda_slab", ()-> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<FenceBlock> JACARANDA_FENCE = BLOCKS.register("jacaranda_fence", ()-> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<FenceGateBlock> JACARANDA_FENCE_GATE = BLOCKS.register("jacaranda_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<DoorBlock> JACARANDA_DOOR = BLOCKS.register("jacaranda_door", ()-> new DoorBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<TrapDoorBlock> JACARANDA_TRAPDOOR = BLOCKS.register("jacaranda_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<PressurePlateBlock> JACARANDA_PRESSURE_PLATE = BLOCKS.register("jacaranda_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<ButtonBlock> JACARANDA_BUTTON = BLOCKS.register("jacaranda_button", ()-> woodenButton(ModBlockSetTypes.JACARANDA, Blocks.CHERRY_BUTTON, MapColorExtension.JACARANDA_WOOD));
    public static final DeferredBlock<StandingSignBlock> JACARANDA_SIGN = BLOCKS.register("jacaranda_sign", ()-> new StandingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<WallSignBlock> JACARANDA_WALL_SIGN = BLOCKS.register("jacaranda_wall_sign", ()-> new WallSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD).lootFrom(JACARANDA_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> JACARANDA_HANGING_SIGN = BLOCKS.register("jacaranda_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<WallHangingSignBlock> JACARANDA_WALL_HANGING_SIGN = BLOCKS.register("jacaranda_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD).lootFrom(JACARANDA_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> JACARANDA_LEAVES = BLOCKS.register("jacaranda_leaves", ()-> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.JACARANDA), ModParticles.JACARANDA_LEAVES));
    public static final DeferredBlock<SaplingBlock> JACARANDA_SAPLING = BLOCKS.register("jacaranda_sapling", ()-> new SaplingBlock(ModTreeGrower.JACARANDA, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.JACARANDA)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_JACARANDA_SAPLING = BLOCKS.register("potted_jacaranda_sapling", ()-> flowerPot(JACARANDA_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> VIOLETS = BLOCKS.register("violets", ()-> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.JACARANDA)));
    public static final DeferredBlock<PinkPetalsBlock> BUTTERCUPS = BLOCKS.register("buttercups", ()-> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<PinkPetalsBlock> FORGET_ME_NOTS = BLOCKS.register("forget-me-nots", ()-> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<PinkPetalsBlock> BABY_BLUE_EYES = BLOCKS.register("baby-blue-eyes", ()-> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.LAPIS)));
    public static final DeferredBlock<PinkPetalsBlock> SPEEDWELLS = BLOCKS.register("speedwells", ()-> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_BLUE)));
    public static final DeferredBlock<PinkPetalsBlock> WOOD_SORRELS = BLOCKS.register("wood_sorrels", ()-> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_MAGENTA)));

    public static final DeferredBlock<RotatedPillarBlock> WILLOW_LOG = BLOCKS.register("willow_log", ()-> log(Blocks.OAK_LOG, MapColor.GLOW_LICHEN, MapColor.TERRACOTTA_GREEN));
    public static final DeferredBlock<RotatedPillarBlock> WILLOW_WOOD = BLOCKS.register("willow_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_GREEN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_LOG = BLOCKS.register("stripped_willow_log", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_WOOD = BLOCKS.register("stripped_willow_wood", ()-> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<Block> WILLOW_PLANKS = BLOCKS.register("willow_planks", ()-> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<StairBlock> WILLOW_STAIRS = BLOCKS.register("willow_stairs", ()-> stair(WILLOW_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> WILLOW_SLAB = BLOCKS.register("willow_slab", ()-> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<FenceBlock> WILLOW_FENCE = BLOCKS.register("willow_fence", ()-> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<FenceGateBlock> WILLOW_FENCE_GATE = BLOCKS.register("willow_fence_gate", ()-> new FenceGateBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<DoorBlock> WILLOW_DOOR = BLOCKS.register("willow_door", ()-> new DoorBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<TrapDoorBlock> WILLOW_TRAPDOOR = BLOCKS.register("willow_trapdoor", ()-> new TrapDoorBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<PressurePlateBlock> WILLOW_PRESSURE_PLATE = BLOCKS.register("willow_pressure_plate", ()-> new PressurePlateBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<ButtonBlock> WILLOW_BUTTON = BLOCKS.register("willow_button", ()-> woodenButton(ModBlockSetTypes.WILLOW, Blocks.OAK_BUTTON, MapColor.GLOW_LICHEN));
    public static final DeferredBlock<StandingSignBlock> WILLOW_SIGN = BLOCKS.register("willow_sign", ()-> new StandingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<WallSignBlock> WILLOW_WALL_SIGN = BLOCKS.register("willow_wall_sign", ()-> new WallSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.GLOW_LICHEN).lootFrom(WILLOW_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> WILLOW_HANGING_SIGN = BLOCKS.register("willow_hanging_sign", ()-> new CeilingHangingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<WallHangingSignBlock> WILLOW_WALL_HANGING_SIGN = BLOCKS.register("willow_wall_hanging_sign", ()-> new WallHangingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.GLOW_LICHEN).lootFrom(WILLOW_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> WILLOW_LEAVES = BLOCKS.register("willow_leaves", ()-> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<SaplingBlock> WILLOW_SAPLING = BLOCKS.register("willow_sapling", ()-> new WaterloggedSaplingBlock(ModTreeGrower.WILLOW, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WILLOW_SAPLING = BLOCKS.register("potted_willow_sapling", ()-> flowerPot(WILLOW_SAPLING));
    public static final DeferredBlock<Block> WILLOW_BRANCHES = BLOCKS.register("willow_branches", ()-> new WillowBranchesBlock(of().ignitedByLava().mapColor(MapColor.PLANT).noCollission().sound(SoundType.VINE).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<FlowerBlock> PINK_DAISY = BLOCKS.register("pink_daisy", ()-> new FlowerBlock(MobEffects.REGENERATION, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_DAISY = BLOCKS.register("potted_pink_daisy", ()-> flowerPot(PINK_DAISY));
    public static final DeferredBlock<FlowerBlock> RED_CARNATION = BLOCKS.register("red_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_CARNATION = BLOCKS.register("potted_red_carnation", ()-> flowerPot(RED_CARNATION));
    public static final DeferredBlock<FlowerBlock> PINK_CARNATION = BLOCKS.register("pink_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_CARNATION = BLOCKS.register("potted_pink_carnation", ()-> flowerPot(PINK_CARNATION));
    public static final DeferredBlock<FlowerBlock> WHITE_CARNATION = BLOCKS.register("white_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CARNATION = BLOCKS.register("potted_white_carnation", ()-> flowerPot(WHITE_CARNATION));
    public static final DeferredBlock<FlowerBlock> RED_SPIDER_LILY = BLOCKS.register("red_spider_lily", ()-> new FlowerBlock(MobEffects.POISON, 10.0F, flowerProperties().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_SPIDER_LILY = BLOCKS.register("potted_red_spider_lily", ()-> flowerPot(RED_SPIDER_LILY));
    public static final DeferredBlock<FlowerBlock> YELLOW_CHRYSANTHEMUM = BLOCKS.register("yellow_chrysanthemum", ()-> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_CHRYSANTHEMUM = BLOCKS.register("potted_yellow_chrysanthemum", ()-> flowerPot(YELLOW_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> GREEN_CHRYSANTHEMUM = BLOCKS.register("green_chrysanthemum", ()-> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREEN_CHRYSANTHEMUM = BLOCKS.register("potted_green_chrysanthemum", ()-> flowerPot(GREEN_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> OPEN_DAYBLOOM = BLOCKS.register("open_daybloom", ()-> new DaybloomBlock(DaybloomBlock.Type.OPEN, flowerProperties().mapColor(MapColor.COLOR_YELLOW).randomTicks()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_OPEN_DAYBLOOM = BLOCKS.register("potted_open_daybloom", ()-> new FlowerPotBlock(defaultPot(), OPEN_DAYBLOOM, flowerPotProperties().randomTicks()));
    public static final DeferredBlock<FlowerBlock> CLOSED_DAYBLOOM = BLOCKS.register("closed_daybloom", ()-> new DaybloomBlock(DaybloomBlock.Type.CLOSED, flowerProperties().mapColor(MapColor.PLANT).randomTicks()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CLOSED_DAYBLOOM = BLOCKS.register("potted_closed_daybloom", ()-> new FlowerPotBlock(defaultPot(), CLOSED_DAYBLOOM, flowerPotProperties().randomTicks()));
    public static final DeferredBlock<FlowerBlock> EDELWEISS = BLOCKS.register("edelweiss", ()-> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_EDELWEISS = BLOCKS.register("potted_edelweiss", ()-> flowerPot(EDELWEISS));
    public static final DeferredBlock<FlowerBlock> CROCUS = BLOCKS.register("crocus", ()-> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CROCUS = BLOCKS.register("potted_crocus", ()-> flowerPot(CROCUS));
    public static final DeferredBlock<FlowerBlock> IRIS = BLOCKS.register("iris", ()-> new FlowerBlock(MobEffects.SATURATION, 0.35F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_IRIS = BLOCKS.register("potted_iris", ()-> flowerPot(IRIS));
    public static final DeferredBlock<FlowerBlock> LAVENDER = BLOCKS.register("lavender", ()-> new FlowerBlock(MobEffects.REGENERATION, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_LAVENDER = BLOCKS.register("potted_lavender", ()-> flowerPot(LAVENDER));
    public static final DeferredBlock<FlowerBlock> DAFFODIL = BLOCKS.register("daffodil", ()-> new FlowerBlock(MobEffects.POISON, 11.0F, flowerProperties().mapColor(MapColor.GOLD)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_DAFFODIL = BLOCKS.register("potted_daffodil", ()-> flowerPot(DAFFODIL));
    public static final DeferredBlock<FlowerBlock> GERBERA_DAISY = BLOCKS.register("gerbera_daisy", ()-> new FlowerBlock(MobEffects.REGENERATION, 4.0F, flowerProperties().mapColor(MapColor.COLOR_MAGENTA)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GERBERA_DAISY = BLOCKS.register("potted_gerbera_daisy", ()-> flowerPot(GERBERA_DAISY));
    public static final DeferredBlock<FlowerBlock> RAPESEED_FLOWER = BLOCKS.register("rapeseed_flower", ()-> new FlowerBlock(MobEffects.SATURATION, 0.35F, flowerProperties().mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RAPESEED_FLOWER = BLOCKS.register("potted_rapeseed_flower", ()-> flowerPot(RAPESEED_FLOWER));

    public static final DeferredBlock<Block> CATTAIL = BLOCKS.register("cattail", ()-> new CattailBlock(tallFlowerProperties().mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> TALL_RAPESEED_FLOWER = BLOCKS.register("tall_rapeseed_flower", ()-> new TallFlowerBlock(tallFlowerProperties().mapColor(MapColor.COLOR_YELLOW)));

    public static final DeferredBlock<Block> SHORT_WATER_GRASS = BLOCKS.register("short_water_grass", ()-> new WaterGrassBlock(ofFullCopy(Blocks.TALL_GRASS).sound(SoundType.WET_GRASS)));
    public static final DeferredBlock<Block> TALL_WATER_GRASS = BLOCKS.register("tall_water_grass", ()-> new WaterGrassBlock(ofFullCopy(Blocks.TALL_GRASS).sound(SoundType.WET_GRASS)));
    public static final DeferredBlock<Block> REED = BLOCKS.register("reed", ()-> new ReedBlock(tallFlowerProperties().mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<Block> STRAWBERRY_BUSH = BLOCKS.register("strawberry_bush", ()-> new BerryBushBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH = BLOCKS.register("blueberry_bush", ()-> new BerryBushBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final DeferredBlock<Block> OPEN_WATER_LILY = BLOCKS.register("open_water_lily", ()-> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_PINK).randomTicks()));
    public static final DeferredBlock<Block> OPEN_WHITE_WATER_LILY = BLOCKS.register("open_white_water_lily", ()-> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.SNOW).randomTicks()));
    public static final DeferredBlock<Block> OPEN_BLUE_WATER_LILY = BLOCKS.register("open_blue_water_lily", ()-> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_BLUE).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_WATER_LILY = BLOCKS.register("closed_water_lily", ()-> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_WHITE_WATER_LILY = BLOCKS.register("closed_white_water_lily", ()-> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_BLUE_WATER_LILY = BLOCKS.register("closed_blue_water_lily", ()-> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> DUCKWEEDS = BLOCKS.register("duckweeds", ()-> new DuckweedsBlock(ofFullCopy(Blocks.LILY_PAD).replaceable().noCollission()));

    private static RotatedPillarBlock log(Block pBlock, MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(
                ofFullCopy(pBlock)
                        .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
        );
    }
    @SuppressWarnings("SameParameterValue")
    private static RotatedPillarBlock rareLog(MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(
                ofFullCopy(Blocks.OAK_LOG)
                        .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
                        .sound(ModSoundTypes.RARE_WOOD).strength(2.5F).instrument(NoteBlockInstrumentExtension.GUZHENG)
        );
    }
    private static StairBlock stair(Block pBaseBlock) {
        return new StairBlock(pBaseBlock.defaultBlockState(), ofFullCopy(pBaseBlock));
    }
    private static ButtonBlock woodenButton(BlockSetType pType, Block pBlock, MapColor pColor) {
        return new ButtonBlock(pType, 30, ofFullCopy(pBlock).mapColor(pColor));
    }
    @SuppressWarnings("SameParameterValue")
    private static ButtonBlock rareWoodButton(BlockSetType pType, Block pBlock, MapColor pColor) {
        return new ButtonBlock(pType, 30, ofFullCopy(pBlock).mapColor(pColor).instrument(NoteBlockInstrumentExtension.GUZHENG));
    }
    private static FlowerPotBlock flowerPot(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> pPotted) {
        return new FlowerPotBlock(emptyPot, pPotted, flowerPotProperties().mapColor(pPotted.get().defaultMapColor()));
    }
    private static FlowerPotBlock flowerPot(Supplier<? extends Block> pPotted) {
        return flowerPot(defaultPot(), pPotted);
    }
    private static Supplier<FlowerPotBlock> defaultPot() {
        return () -> (FlowerPotBlock) Blocks.FLOWER_POT;
    }
    private static BlockBehaviour.Properties flowerProperties() {
        return of().noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY);
    }
    private static BlockBehaviour.Properties tallFlowerProperties() {
        return flowerProperties().ignitedByLava();
    }
    private static BlockBehaviour.Properties flowerPotProperties() {
        return of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
