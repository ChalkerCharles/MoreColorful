package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.block.musical.*;
import com.ChalkerCharles.morecolorful.common.block.natural.*;
import com.ChalkerCharles.morecolorful.common.block.ornamental.*;
import com.ChalkerCharles.morecolorful.common.block.properties.*;
import com.ChalkerCharles.morecolorful.common.block.utility.*;
import com.ChalkerCharles.morecolorful.util.InstrumentsType;
import com.ChalkerCharles.morecolorful.common.worldgen.features.trees.ModTreeGrower;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.DyeColor;
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
    public static final DeferredBlock<Block> HARP = register("harp", () -> new HarpBlock(ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.TERRACOTTA_YELLOW).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> UPRIGHT_PIANO = register("upright_piano", () -> new UprightPianoBlock(ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> GRAND_PIANO = register("grand_piano", () -> new GrandPianoBlock(ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final DeferredBlock<Block> BASS_DRUM = register("bass_drum", () -> new BassDrumBlock(of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> SNARE_DRUM = register("snare_drum", () -> new SnareDrumBlock(ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> TOMTOM_DRUM = register("tom-tom_drum", () -> new TomTomDrumBlock(of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn()));
    public static final DeferredBlock<Block> HIHAT = register("hi-hat", () -> new HiHatBlock(ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> RIDE_CYMBAL = register("ride_cymbal", () -> new RideCymbalBlock(ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> CRASH_CYMBAL = register("crash_cymbal", () -> new CrashCymbalBlock(ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> DRUM_SET = register("drum_set", () -> new DrumSetBlock(of().mapColor(MapColor.METAL).strength(3.0F, 6.0F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER).forceSolidOn()));
    public static final DeferredBlock<Block> CHIMES = register("chimes", () -> new ChimesBlock(ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.CHIME).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> GLOCKENSPIEL = register("glockenspiel", () -> new GlockenspielBlock(ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final DeferredBlock<Block> XYLOPHONE = register("xylophone", () -> new XylophoneBlock(ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> VIBRAPHONE = register("vibraphone", () -> new VibraphoneBlock(ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_BIT = register("synthesizer_keyboard_bit", () -> new SynthesizerKeyboardBlock(InstrumentsType.BIT, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BIT)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLING = register("synthesizer_keyboard_pling", () -> new SynthesizerKeyboardBlock(InstrumentsType.PLING, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.PLING)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SCULK = register("synthesizer_keyboard_sculk", () -> new SynthesizerKeyboardBlock(InstrumentsType.SCULK, ofFullCopy(Blocks.SCULK_CATALYST).instrument(InstrumentExtension.SCULK).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_AMETHYST = register("synthesizer_keyboard_amethyst", () -> new SynthesizerKeyboardBlock(InstrumentsType.CRYSTAL, ofFullCopy(Blocks.AMETHYST_CLUSTER).instrument(InstrumentExtension.CRYSTAL).emissiveRendering(Blocks::always)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SAW = register("synthesizer_keyboard_saw", () -> new SynthesizerKeyboardBlock(InstrumentsType.SAW, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.CRIMSON_NYLIUM).instrument(InstrumentExtension.SAW)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_PLUCK = register("synthesizer_keyboard_pluck", () -> new SynthesizerKeyboardBlock(InstrumentsType.PLUCK, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.LAPIS).instrument(InstrumentExtension.PLUCK)));
    public static final DeferredBlock<Block> SYNTHESIZER_KEYBOARD_SYNTH_BASS = register("synthesizer_keyboard_synth_bass", () -> new SynthesizerKeyboardBlock(InstrumentsType.SYNTH_BASS, ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.METAL).instrument(InstrumentExtension.SYNTH_BASS)));
    public static final DeferredBlock<Block> GUZHENG = register("guzheng", () -> new GuzhengBlock(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> MUSIC_BOX = register("music_box", () -> new MusicBoxBlock(ofFullCopy(Blocks.JUKEBOX).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));

    // Common Blocks
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_LOG = register("crabapple_log", () -> log(Blocks.CHERRY_LOG, MapColor.TERRACOTTA_PINK, MapColor.TERRACOTTA_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> CRABAPPLE_WOOD = register("crabapple_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_GRAY)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_LOG = register("stripped_crabapple_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_CRABAPPLE_WOOD = register("stripped_crabapple_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<Block> CRABAPPLE_PLANKS = register("crabapple_planks", () -> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<StairBlock> CRABAPPLE_STAIRS = register("crabapple_stairs", () -> stair(CRABAPPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> CRABAPPLE_SLAB = register("crabapple_slab", () -> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceBlock> CRABAPPLE_FENCE = register("crabapple_fence", () -> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<FenceGateBlock> CRABAPPLE_FENCE_GATE = register("crabapple_fence_gate", () -> new FenceGateBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<DoorBlock> CRABAPPLE_DOOR = register("crabapple_door", () -> new DoorBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<TrapDoorBlock> CRABAPPLE_TRAPDOOR = register("crabapple_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<PressurePlateBlock> CRABAPPLE_PRESSURE_PLATE = register("crabapple_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<ButtonBlock> CRABAPPLE_BUTTON = register("crabapple_button", () -> woodenButton(ModBlockSetTypes.CRABAPPLE, Blocks.CHERRY_BUTTON, MapColor.TERRACOTTA_PINK));
    public static final DeferredBlock<StandingSignBlock> CRABAPPLE_SIGN = register("crabapple_sign", () -> new StandingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallSignBlock> CRABAPPLE_WALL_SIGN = register("crabapple_wall_sign", () -> new WallSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> CRABAPPLE_HANGING_SIGN = register("crabapple_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final DeferredBlock<WallHangingSignBlock> CRABAPPLE_WALL_HANGING_SIGN = register("crabapple_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.CRABAPPLE, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_PINK).lootFrom(CRABAPPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> CRABAPPLE_LEAVES = register("crabapple_leaves", () -> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.CRABAPPLE), ModParticles.CRABAPPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> CRABAPPLE_SAPLING = register("crabapple_sapling", () -> new SaplingBlock(ModTreeGrower.CRABAPPLE, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.CRABAPPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CRABAPPLE_SAPLING = register("potted_crabapple_sapling", () -> flowerPot(CRABAPPLE_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> BEGONIAS = register("begonias", () -> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.BEGONIA)));

    public static final DeferredBlock<RotatedPillarBlock> EBONY_LOG = register("ebony_log", () -> rareLog(MapColor.TERRACOTTA_BLACK, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> EBONY_WOOD = register("ebony_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_LOG = register("stripped_ebony_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_EBONY_WOOD = register("stripped_ebony_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<Block> EBONY_PLANKS = register("ebony_planks", () -> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<StairBlock> EBONY_STAIRS = register("ebony_stairs", () -> stair(EBONY_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> EBONY_SLAB = register("ebony_slab", () -> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceBlock> EBONY_FENCE = register("ebony_fence", () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).sound(ModSoundTypes.RARE_WOOD).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<FenceGateBlock> EBONY_FENCE_GATE = register("ebony_fence_gate", () -> new FenceGateBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_BLACK).strength(2.5F, 3.0F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<DoorBlock> EBONY_DOOR = register("ebony_door", () -> new DoorBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<TrapDoorBlock> EBONY_TRAPDOOR = register("ebony_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_BLACK).strength(3.5F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<PressurePlateBlock> EBONY_PRESSURE_PLATE = register("ebony_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.EBONY, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_BLACK).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<ButtonBlock> EBONY_BUTTON = register("ebony_button", () -> rareWoodButton(ModBlockSetTypes.EBONY, Blocks.OAK_BUTTON, MapColor.TERRACOTTA_BLACK));
    public static final DeferredBlock<StandingSignBlock> EBONY_SIGN = register("ebony_sign", () -> new StandingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallSignBlock> EBONY_WALL_SIGN = register("ebony_wall_sign", () -> new WallSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_SIGN).strength(1.5F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<CeilingHangingSignBlock> EBONY_HANGING_SIGN = register("ebony_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).strength(1.5F).instrument(InstrumentExtension.GUZHENG)));
    public static final DeferredBlock<WallHangingSignBlock> EBONY_WALL_HANGING_SIGN = register("ebony_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.EBONY, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_BLACK).lootFrom(EBONY_HANGING_SIGN).strength(1.5F).instrument(InstrumentExtension.GUZHENG)));

    public static final DeferredBlock<LeavesBlock> WHITE_CHERRY_LEAVES = register("white_cherry_leaves", () -> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColor.SNOW), ModParticles.WHITE_CHERRY_LEAVES));
    public static final DeferredBlock<SaplingBlock> WHITE_CHERRY_SAPLING = register("white_cherry_sapling", () -> new SaplingBlock(ModTreeGrower.WHITE_CHERRY, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CHERRY_SAPLING = register("potted_white_cherry_sapling", () -> flowerPot(WHITE_CHERRY_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> WHITE_PETALS = register("white_petals", () -> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.SNOW)));
    public static final DeferredBlock<LeavesBlock> ORANGE_BIRCH_LEAVES = register("orange_birch_leaves", () -> new FallingLeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).mapColor(MapColorExtension.ORANGE_BIRCH), ModParticles.ORANGE_BIRCH_LEAVES));
    public static final DeferredBlock<SaplingBlock> ORANGE_BIRCH_SAPLING = register("orange_birch_sapling", () -> new SaplingBlock(ModTreeGrower.ORANGE_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING).mapColor(MapColorExtension.ORANGE_BIRCH)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_ORANGE_BIRCH_SAPLING = register("potted_orange_birch_sapling", () -> flowerPot(ORANGE_BIRCH_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> ORANGE_BIRCH_LEAF_LITTER = register("orange_birch_leaf_litter", () -> new LeafLitterBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.ORANGE_BIRCH).sound(ModSoundTypes.LEAF_LITTER).replaceable()));
    public static final DeferredBlock<LeavesBlock> YELLOW_BIRCH_LEAVES = register("yellow_birch_leaves", () -> new FallingLeavesBlock(ofFullCopy(Blocks.BIRCH_LEAVES).mapColor(MapColor.TERRACOTTA_YELLOW), ModParticles.YELLOW_BIRCH_LEAVES));
    public static final DeferredBlock<SaplingBlock> YELLOW_BIRCH_SAPLING = register("yellow_birch_sapling", () -> new SaplingBlock(ModTreeGrower.YELLOW_BIRCH, ofFullCopy(Blocks.BIRCH_SAPLING).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_BIRCH_SAPLING = register("potted_yellow_birch_sapling", () -> flowerPot(YELLOW_BIRCH_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> YELLOW_BIRCH_LEAF_LITTER = register("yellow_birch_leaf_litter", () -> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_YELLOW).sound(ModSoundTypes.LEAF_LITTER).replaceable()));

    public static final DeferredBlock<RotatedPillarBlock> GINKGO_LOG = register("ginkgo_log", () -> log(Blocks.OAK_LOG, MapColor.SAND, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> GINKGO_WOOD = register("ginkgo_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_LOG = register("stripped_ginkgo_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.SAND)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_GINKGO_WOOD = register("stripped_ginkgo_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.SAND)));
    public static final DeferredBlock<Block> GINKGO_PLANKS = register("ginkgo_planks", () -> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.SAND)));
    public static final DeferredBlock<StairBlock> GINKGO_STAIRS = register("ginkgo_stairs", () -> stair(GINKGO_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> GINKGO_SLAB = register("ginkgo_slab", () -> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceBlock> GINKGO_FENCE = register("ginkgo_fence", () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<FenceGateBlock> GINKGO_FENCE_GATE = register("ginkgo_fence_gate", () -> new FenceGateBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<DoorBlock> GINKGO_DOOR = register("ginkgo_door", () -> new DoorBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<TrapDoorBlock> GINKGO_TRAPDOOR = register("ginkgo_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.SAND)));
    public static final DeferredBlock<PressurePlateBlock> GINKGO_PRESSURE_PLATE = register("ginkgo_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.GINKGO, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.SAND)));
    public static final DeferredBlock<ButtonBlock> GINKGO_BUTTON = register("ginkgo_button", () -> woodenButton(ModBlockSetTypes.GINKGO, Blocks.OAK_BUTTON, MapColor.SAND));
    public static final DeferredBlock<StandingSignBlock> GINKGO_SIGN = register("ginkgo_sign", () -> new StandingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallSignBlock> GINKGO_WALL_SIGN = register("ginkgo_wall_sign", () -> new WallSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> GINKGO_HANGING_SIGN = register("ginkgo_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.SAND)));
    public static final DeferredBlock<WallHangingSignBlock> GINKGO_WALL_HANGING_SIGN = register("ginkgo_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.GINKGO, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.SAND).lootFrom(GINKGO_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> GINKGO_LEAVES = register("ginkgo_leaves", () -> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.GOLD), ModParticles.GINKGO_LEAVES));
    public static final DeferredBlock<SaplingBlock> GINKGO_SAPLING = register("ginkgo_sapling", () -> new SaplingBlock(ModTreeGrower.GINKGO, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.GOLD)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GINKGO_SAPLING = register("potted_ginkgo_sapling", () -> flowerPot(GINKGO_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> GINKGO_LEAF_LITTER = register("ginkgo_leaf_litter", () -> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.GOLD)));

    public static final DeferredBlock<RotatedPillarBlock> MAPLE_LOG = register("maple_log", () -> log(Blocks.OAK_LOG, MapColor.RAW_IRON, MapColor.WOOD));
    public static final DeferredBlock<RotatedPillarBlock> MAPLE_WOOD = register("maple_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_LOG = register("stripped_maple_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_MAPLE_WOOD = register("stripped_maple_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<Block> MAPLE_PLANKS = register("maple_planks", () -> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<StairBlock> MAPLE_STAIRS = register("maple_stairs", () -> stair(MAPLE_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> MAPLE_SLAB = register("maple_slab", () -> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceBlock> MAPLE_FENCE = register("maple_fence", () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<FenceGateBlock> MAPLE_FENCE_GATE = register("maple_fence_gate", () -> new FenceGateBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<DoorBlock> MAPLE_DOOR = register("maple_door", () -> new DoorBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<TrapDoorBlock> MAPLE_TRAPDOOR = register("maple_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<PressurePlateBlock> MAPLE_PRESSURE_PLATE = register("maple_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.MAPLE, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<ButtonBlock> MAPLE_BUTTON = register("maple_button", () -> woodenButton(ModBlockSetTypes.MAPLE, Blocks.OAK_BUTTON, MapColor.RAW_IRON));
    public static final DeferredBlock<StandingSignBlock> MAPLE_SIGN = register("maple_sign", () -> new StandingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallSignBlock> MAPLE_WALL_SIGN = register("maple_wall_sign", () -> new WallSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> MAPLE_HANGING_SIGN = register("maple_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.RAW_IRON)));
    public static final DeferredBlock<WallHangingSignBlock> MAPLE_WALL_HANGING_SIGN = register("maple_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.MAPLE, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.RAW_IRON).lootFrom(MAPLE_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> MAPLE_LEAVES = register("maple_leaves", () -> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_RED), ModParticles.MAPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> MAPLE_SAPLING = register("maple_sapling", () -> new SaplingBlock(ModTreeGrower.MAPLE, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_MAPLE_SAPLING = register("potted_maple_sapling", () -> flowerPot(MAPLE_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> MAPLE_LEAF_LITTER = register("maple_leaf_litter", () -> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_RED)));
    public static final DeferredBlock<RotatedPillarBlock> FROST_LOG = register("frost_log", () -> log(Blocks.CHERRY_LOG, MapColorExtension.FROST_WOOD, MapColor.TERRACOTTA_BLUE));
    public static final DeferredBlock<RotatedPillarBlock> FROST_WOOD = register("frost_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_BLUE)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROST_LOG = register("stripped_frost_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_FROST_WOOD = register("stripped_frost_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<Block> FROST_PLANKS = register("frost_planks", () -> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<StairBlock> FROST_STAIRS = register("frost_stairs", () -> stair(FROST_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> FROST_SLAB = register("frost_slab", () -> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<FenceBlock> FROST_FENCE = register("frost_fence", () -> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<FenceGateBlock> FROST_FENCE_GATE = register("frost_fence_gate", () -> new FenceGateBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<DoorBlock> FROST_DOOR = register("frost_door", () -> new DoorBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<TrapDoorBlock> FROST_TRAPDOOR = register("frost_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<PressurePlateBlock> FROST_PRESSURE_PLATE = register("frost_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.FROST, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<ButtonBlock> FROST_BUTTON = register("frost_button", () -> woodenButton(ModBlockSetTypes.FROST, Blocks.CHERRY_BUTTON, MapColorExtension.FROST_WOOD));
    public static final DeferredBlock<StandingSignBlock> FROST_SIGN = register("frost_sign", () -> new StandingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<WallSignBlock> FROST_WALL_SIGN = register("frost_wall_sign", () -> new WallSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColorExtension.FROST_WOOD).lootFrom(FROST_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> FROST_HANGING_SIGN = register("frost_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColorExtension.FROST_WOOD)));
    public static final DeferredBlock<WallHangingSignBlock> FROST_WALL_HANGING_SIGN = register("frost_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.FROST, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColorExtension.FROST_WOOD).lootFrom(FROST_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> FROST_LEAVES = register("frost_leaves", () -> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.FROST), ModParticles.FROST_LEAVES));
    public static final DeferredBlock<SaplingBlock> FROST_SAPLING = register("frost_sapling", () -> new SaplingBlock(ModTreeGrower.FROST, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.FROST)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_FROST_SAPLING = register("potted_frost_sapling", () -> flowerPot(FROST_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> FROSTY_PETALS = register("frosty_petals", () -> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.FROST)));
    public static final DeferredBlock<RotatedPillarBlock> DAWN_REDWOOD_LOG = register("dawn_redwood_log", () -> log(Blocks.OAK_LOG, MapColor.TERRACOTTA_ORANGE, MapColor.COLOR_BROWN));
    public static final DeferredBlock<RotatedPillarBlock> DAWN_REDWOOD_WOOD = register("dawn_redwood_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DAWN_REDWOOD_LOG = register("stripped_dawn_redwood_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_DAWN_REDWOOD_WOOD = register("stripped_dawn_redwood_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_PLANKS = register("dawn_redwood_planks", () -> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<StairBlock> DAWN_REDWOOD_STAIRS = register("dawn_redwood_stairs", () -> stair(DAWN_REDWOOD_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> DAWN_REDWOOD_SLAB = register("dawn_redwood_slab", () -> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FenceBlock> DAWN_REDWOOD_FENCE = register("dawn_redwood_fence", () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FenceGateBlock> DAWN_REDWOOD_FENCE_GATE = register("dawn_redwood_fence_gate", () -> new FenceGateBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<DoorBlock> DAWN_REDWOOD_DOOR = register("dawn_redwood_door", () -> new DoorBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<TrapDoorBlock> DAWN_REDWOOD_TRAPDOOR = register("dawn_redwood_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<PressurePlateBlock> DAWN_REDWOOD_PRESSURE_PLATE = register("dawn_redwood_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<ButtonBlock> DAWN_REDWOOD_BUTTON = register("dawn_redwood_button", () -> woodenButton(ModBlockSetTypes.DAWN_REDWOOD, Blocks.OAK_BUTTON, MapColor.TERRACOTTA_ORANGE));
    public static final DeferredBlock<StandingSignBlock> DAWN_REDWOOD_SIGN = register("dawn_redwood_sign", () -> new StandingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<WallSignBlock> DAWN_REDWOOD_WALL_SIGN = register("dawn_redwood_wall_sign", () -> new WallSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE).lootFrom(DAWN_REDWOOD_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> DAWN_REDWOOD_HANGING_SIGN = register("dawn_redwood_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<WallHangingSignBlock> DAWN_REDWOOD_WALL_HANGING_SIGN = register("dawn_redwood_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.TERRACOTTA_ORANGE).lootFrom(DAWN_REDWOOD_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> DAWN_REDWOOD_LEAVES = register("dawn_redwood_leaves", () -> new FallingLeavesBlock(ofFullCopy(Blocks.OAK_LEAVES).mapColor(MapColor.TERRACOTTA_ORANGE), ModParticles.DAWN_REDWOOD_LEAVES));
    public static final DeferredBlock<SaplingBlock> DAWN_REDWOOD_SAPLING = register("dawn_redwood_sapling", () -> new WaterloggedSaplingBlock(ModTreeGrower.DAWN_REDWOOD, ofFullCopy(Blocks.OAK_SAPLING).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_DAWN_REDWOOD_SAPLING = register("potted_dawn_redwood_sapling", () -> flowerPot(DAWN_REDWOOD_SAPLING));
    public static final DeferredBlock<LeafLitterBlock> DAWN_REDWOOD_LEAF_LITTER = register("dawn_redwood_leaf_litter", () -> new LeafLitterBlock(ofFullCopy(ModBlocks.ORANGE_BIRCH_LEAF_LITTER.get()).mapColor(MapColor.TERRACOTTA_ORANGE)));
    public static final DeferredBlock<Block> DAWN_REDWOOD_ROOTS = register("dawn_redwood_roots", () -> new DawnRedwoodRootBlock(ofFullCopy(Blocks.MANGROVE_ROOTS).mapColor(MapColor.COLOR_BROWN)));

    public static final DeferredBlock<RotatedPillarBlock> JACARANDA_LOG = register("jacaranda_log", () -> log(Blocks.CHERRY_LOG, MapColorExtension.JACARANDA_WOOD, MapColor.TERRACOTTA_LIGHT_GRAY));
    public static final DeferredBlock<RotatedPillarBlock> JACARANDA_WOOD = register("jacaranda_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.CHERRY_WOOD).mapColor(MapColor.TERRACOTTA_LIGHT_GRAY)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_JACARANDA_LOG = register("stripped_jacaranda_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_LOG).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_JACARANDA_WOOD = register("stripped_jacaranda_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_CHERRY_WOOD).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<Block> JACARANDA_PLANKS = register("jacaranda_planks", () -> new Block(ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<StairBlock> JACARANDA_STAIRS = register("jacaranda_stairs", () -> stair(JACARANDA_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> JACARANDA_SLAB = register("jacaranda_slab", () -> new SlabBlock(ofFullCopy(Blocks.CHERRY_SLAB).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<FenceBlock> JACARANDA_FENCE = register("jacaranda_fence", () -> new FenceBlock(ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<FenceGateBlock> JACARANDA_FENCE_GATE = register("jacaranda_fence_gate", () -> new FenceGateBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_FENCE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<DoorBlock> JACARANDA_DOOR = register("jacaranda_door", () -> new DoorBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_DOOR).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<TrapDoorBlock> JACARANDA_TRAPDOOR = register("jacaranda_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_TRAPDOOR).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<PressurePlateBlock> JACARANDA_PRESSURE_PLATE = register("jacaranda_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_PRESSURE_PLATE).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<ButtonBlock> JACARANDA_BUTTON = register("jacaranda_button", () -> woodenButton(ModBlockSetTypes.JACARANDA, Blocks.CHERRY_BUTTON, MapColorExtension.JACARANDA_WOOD));
    public static final DeferredBlock<StandingSignBlock> JACARANDA_SIGN = register("jacaranda_sign", () -> new StandingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<WallSignBlock> JACARANDA_WALL_SIGN = register("jacaranda_wall_sign", () -> new WallSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_WALL_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD).lootFrom(JACARANDA_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> JACARANDA_HANGING_SIGN = register("jacaranda_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_HANGING_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD)));
    public static final DeferredBlock<WallHangingSignBlock> JACARANDA_WALL_HANGING_SIGN = register("jacaranda_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.JACARANDA, ofFullCopy(Blocks.CHERRY_WALL_HANGING_SIGN).mapColor(MapColorExtension.JACARANDA_WOOD).lootFrom(JACARANDA_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> JACARANDA_LEAVES = register("jacaranda_leaves", () -> new PetalLeavesBlock(ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.JACARANDA), ModParticles.JACARANDA_LEAVES));
    public static final DeferredBlock<SaplingBlock> JACARANDA_SAPLING = register("jacaranda_sapling", () -> new SaplingBlock(ModTreeGrower.JACARANDA, ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.JACARANDA)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_JACARANDA_SAPLING = register("potted_jacaranda_sapling", () -> flowerPot(JACARANDA_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> VIOLETS = register("violets", () -> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.JACARANDA)));
    public static final DeferredBlock<PinkPetalsBlock> BUTTERCUPS = register("buttercups", () -> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<PinkPetalsBlock> FORGET_ME_NOTS = register("forget-me-nots", () -> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_LIGHT_BLUE)));
    public static final DeferredBlock<PinkPetalsBlock> BABY_BLUE_EYES = register("baby-blue-eyes", () -> new TallFlowerbedBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.LAPIS)));
    public static final DeferredBlock<PinkPetalsBlock> SPEEDWELLS = register("speedwells", () -> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_BLUE)));
    public static final DeferredBlock<PinkPetalsBlock> WOOD_SORRELS = register("wood_sorrels", () -> new PinkPetalsBlock(ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_MAGENTA)));

    public static final DeferredBlock<RotatedPillarBlock> WILLOW_LOG = register("willow_log", () -> log(Blocks.OAK_LOG, MapColor.GLOW_LICHEN, MapColor.TERRACOTTA_GREEN));
    public static final DeferredBlock<RotatedPillarBlock> WILLOW_WOOD = register("willow_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OAK_WOOD).mapColor(MapColor.TERRACOTTA_GREEN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_LOG = register("stripped_willow_log", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_LOG).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<RotatedPillarBlock> STRIPPED_WILLOW_WOOD = register("stripped_willow_wood", () -> new RotatedPillarBlock(ofFullCopy(Blocks.STRIPPED_OAK_WOOD).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<Block> WILLOW_PLANKS = register("willow_planks", () -> new Block(ofFullCopy(Blocks.OAK_PLANKS).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<StairBlock> WILLOW_STAIRS = register("willow_stairs", () -> stair(WILLOW_PLANKS.get()));
    public static final DeferredBlock<SlabBlock> WILLOW_SLAB = register("willow_slab", () -> new SlabBlock(ofFullCopy(Blocks.OAK_SLAB).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<FenceBlock> WILLOW_FENCE = register("willow_fence", () -> new FenceBlock(ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<FenceGateBlock> WILLOW_FENCE_GATE = register("willow_fence_gate", () -> new FenceGateBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_FENCE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<DoorBlock> WILLOW_DOOR = register("willow_door", () -> new DoorBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_DOOR).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<TrapDoorBlock> WILLOW_TRAPDOOR = register("willow_trapdoor", () -> new TrapDoorBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_TRAPDOOR).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<PressurePlateBlock> WILLOW_PRESSURE_PLATE = register("willow_pressure_plate", () -> new PressurePlateBlock(ModBlockSetTypes.WILLOW, ofFullCopy(Blocks.OAK_PRESSURE_PLATE).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<ButtonBlock> WILLOW_BUTTON = register("willow_button", () -> woodenButton(ModBlockSetTypes.WILLOW, Blocks.OAK_BUTTON, MapColor.GLOW_LICHEN));
    public static final DeferredBlock<StandingSignBlock> WILLOW_SIGN = register("willow_sign", () -> new StandingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_SIGN).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<WallSignBlock> WILLOW_WALL_SIGN = register("willow_wall_sign", () -> new WallSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_SIGN).mapColor(MapColor.GLOW_LICHEN).lootFrom(WILLOW_SIGN)));
    public static final DeferredBlock<CeilingHangingSignBlock> WILLOW_HANGING_SIGN = register("willow_hanging_sign", () -> new CeilingHangingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_HANGING_SIGN).mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<WallHangingSignBlock> WILLOW_WALL_HANGING_SIGN = register("willow_wall_hanging_sign", () -> new WallHangingSignBlock(ModWoodTypes.WILLOW, ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN).mapColor(MapColor.GLOW_LICHEN).lootFrom(WILLOW_HANGING_SIGN)));
    public static final DeferredBlock<LeavesBlock> WILLOW_LEAVES = register("willow_leaves", () -> new LeavesBlock(ofFullCopy(Blocks.OAK_LEAVES)));
    public static final DeferredBlock<SaplingBlock> WILLOW_SAPLING = register("willow_sapling", () -> new WaterloggedSaplingBlock(ModTreeGrower.WILLOW, ofFullCopy(Blocks.OAK_SAPLING)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WILLOW_SAPLING = register("potted_willow_sapling", () -> flowerPot(WILLOW_SAPLING));
    public static final DeferredBlock<Block> WILLOW_BRANCHES = register("willow_branches", () -> new WillowBranchesBlock(of().ignitedByLava().mapColor(MapColor.PLANT).noCollission().sound(SoundType.VINE).pushReaction(PushReaction.DESTROY)));

    public static final DeferredBlock<FlowerBlock> PINK_DAISY = register("pink_daisy", () -> new FlowerBlock(MobEffects.REGENERATION, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_DAISY = register("potted_pink_daisy", () -> flowerPot(PINK_DAISY));
    public static final DeferredBlock<FlowerBlock> RED_CARNATION = register("red_carnation", () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_CARNATION = register("potted_red_carnation", () -> flowerPot(RED_CARNATION));
    public static final DeferredBlock<FlowerBlock> PINK_CARNATION = register("pink_carnation", () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_CARNATION = register("potted_pink_carnation", () -> flowerPot(PINK_CARNATION));
    public static final DeferredBlock<FlowerBlock> WHITE_CARNATION = register("white_carnation", () -> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, flowerProperties().mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WHITE_CARNATION = register("potted_white_carnation", () -> flowerPot(WHITE_CARNATION));
    public static final DeferredBlock<FlowerBlock> RED_SPIDER_LILY = register("red_spider_lily", () -> new FlowerBlock(MobEffects.POISON, 10.0F, flowerProperties().mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_SPIDER_LILY = register("potted_red_spider_lily", () -> flowerPot(RED_SPIDER_LILY));
    public static final DeferredBlock<FlowerBlock> YELLOW_CHRYSANTHEMUM = register("yellow_chrysanthemum", () -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_YELLOW_CHRYSANTHEMUM = register("potted_yellow_chrysanthemum", () -> flowerPot(YELLOW_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> GREEN_CHRYSANTHEMUM = register("green_chrysanthemum", () -> new FlowerBlock(MobEffects.DAMAGE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.COLOR_LIGHT_GREEN)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GREEN_CHRYSANTHEMUM = register("potted_green_chrysanthemum", () -> flowerPot(GREEN_CHRYSANTHEMUM));
    public static final DeferredBlock<FlowerBlock> OPEN_DAYBLOOM = register("open_daybloom", () -> new DaybloomBlock(DaybloomBlock.Type.OPEN, flowerProperties().mapColor(MapColor.COLOR_YELLOW).randomTicks()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_OPEN_DAYBLOOM = register("potted_open_daybloom", () -> new PottedDaybloomBlock(defaultPot(), OPEN_DAYBLOOM, flowerPotProperties().mapColor(MapColor.COLOR_YELLOW).randomTicks()));
    public static final DeferredBlock<FlowerBlock> CLOSED_DAYBLOOM = register("closed_daybloom", () -> new DaybloomBlock(DaybloomBlock.Type.CLOSED, flowerProperties().mapColor(MapColor.PLANT).randomTicks()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CLOSED_DAYBLOOM = register("potted_closed_daybloom", () -> new PottedDaybloomBlock(defaultPot(), CLOSED_DAYBLOOM, flowerPotProperties().mapColor(MapColor.PLANT).randomTicks()));
    public static final DeferredBlock<FlowerBlock> EDELWEISS = register("edelweiss", () -> new FlowerBlock(MobEffects.FIRE_RESISTANCE, 6.0F, flowerProperties().mapColor(MapColor.SNOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_EDELWEISS = register("potted_edelweiss", () -> flowerPot(EDELWEISS));
    public static final DeferredBlock<FlowerBlock> CROCUS = register("crocus", () -> new FlowerBlock(MobEffects.MOVEMENT_SPEED, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CROCUS = register("potted_crocus", () -> flowerPot(CROCUS));
    public static final DeferredBlock<FlowerBlock> IRIS = register("iris", () -> new FlowerBlock(MobEffects.SATURATION, 0.35F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_IRIS = register("potted_iris", () -> flowerPot(IRIS));
    public static final DeferredBlock<FlowerBlock> LAVENDER = register("lavender", () -> new FlowerBlock(MobEffects.REGENERATION, 8.0F, flowerProperties().mapColor(MapColor.COLOR_PURPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_LAVENDER = register("potted_lavender", () -> flowerPot(LAVENDER));
    public static final DeferredBlock<FlowerBlock> DAFFODIL = register("daffodil", () -> new FlowerBlock(MobEffects.POISON, 11.0F, flowerProperties().mapColor(MapColor.GOLD)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_DAFFODIL = register("potted_daffodil", () -> flowerPot(DAFFODIL));
    public static final DeferredBlock<FlowerBlock> GERBERA_DAISY = register("gerbera_daisy", () -> new FlowerBlock(MobEffects.REGENERATION, 4.0F, flowerProperties().mapColor(MapColor.COLOR_MAGENTA)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_GERBERA_DAISY = register("potted_gerbera_daisy", () -> flowerPot(GERBERA_DAISY));
    public static final DeferredBlock<FlowerBlock> RAPESEED_FLOWER = register("rapeseed_flower", () -> new FlowerBlock(MobEffects.SATURATION, 0.35F, flowerProperties().mapColor(MapColor.COLOR_YELLOW)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RAPESEED_FLOWER = register("potted_rapeseed_flower", () -> flowerPot(RAPESEED_FLOWER));
    public static final DeferredBlock<FlowerBlock> WINDFLOWER = register("windflower", () -> new WindFlowerBlock(flowerProperties().mapColor(MapColor.WOOL).randomTicks()));
    public static final DeferredBlock<FlowerPotBlock> POTTED_WINDFLOWER = register("potted_windflower", () -> new PottedWindflowerBlock(defaultPot(), flowerPotProperties().mapColor(MapColor.WOOL).randomTicks()));

    public static final DeferredBlock<Block> CATTAIL = register("cattail", () -> new CattailBlock(tallFlowerProperties().mapColor(MapColor.COLOR_BROWN)));
    public static final DeferredBlock<Block> TALL_RAPESEED_FLOWER = register("tall_rapeseed_flower", () -> new TallFlowerBlock(tallFlowerProperties().mapColor(MapColor.COLOR_YELLOW)));

    public static final DeferredBlock<Block> SHORT_WATER_GRASS = register("short_water_grass", () -> new WaterGrassBlock(ofFullCopy(Blocks.TALL_GRASS).sound(SoundType.WET_GRASS)));
    public static final DeferredBlock<Block> TALL_WATER_GRASS = register("tall_water_grass", () -> new WaterGrassBlock(ofFullCopy(Blocks.TALL_GRASS).sound(SoundType.WET_GRASS)));
    public static final DeferredBlock<Block> REED = register("reed", () -> new ReedBlock(tallFlowerProperties().mapColor(MapColor.GLOW_LICHEN)));
    public static final DeferredBlock<Block> STRAWBERRY_BUSH = register("strawberry_bush", () -> new BerryBushBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final DeferredBlock<Block> BLUEBERRY_BUSH = register("blueberry_bush", () -> new BerryBushBlock(ofFullCopy(Blocks.SWEET_BERRY_BUSH)));
    public static final DeferredBlock<Block> OPEN_WATER_LILY = register("open_water_lily", () -> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_PINK).randomTicks()));
    public static final DeferredBlock<Block> OPEN_WHITE_WATER_LILY = register("open_white_water_lily", () -> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.SNOW).randomTicks()));
    public static final DeferredBlock<Block> OPEN_BLUE_WATER_LILY = register("open_blue_water_lily", () -> new WaterLilyBlock(true, ofFullCopy(Blocks.LILY_PAD).mapColor(MapColor.COLOR_BLUE).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_WATER_LILY = register("closed_water_lily", () -> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_WHITE_WATER_LILY = register("closed_white_water_lily", () -> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> CLOSED_BLUE_WATER_LILY = register("closed_blue_water_lily", () -> new WaterLilyBlock(false, ofFullCopy(Blocks.LILY_PAD).randomTicks()));
    public static final DeferredBlock<Block> DUCKWEEDS = register("duckweeds", () -> new DuckweedsBlock(ofFullCopy(Blocks.LILY_PAD).replaceable().noCollission()));

    public static final DeferredBlock<Block> WHITE_RIBBON = register("white_ribbon", () -> ribbon(DyeColor.WHITE));
    public static final DeferredBlock<Block> ORANGE_RIBBON = register("orange_ribbon", () -> ribbon(DyeColor.ORANGE));
    public static final DeferredBlock<Block> MAGENTA_RIBBON = register("magenta_ribbon", () -> ribbon(DyeColor.MAGENTA));
    public static final DeferredBlock<Block> LIGHT_BLUE_RIBBON = register("light_blue_ribbon", () -> ribbon(DyeColor.LIGHT_BLUE));
    public static final DeferredBlock<Block> YELLOW_RIBBON = register("yellow_ribbon", () -> ribbon(DyeColor.YELLOW));
    public static final DeferredBlock<Block> LIME_RIBBON = register("lime_ribbon", () -> ribbon(DyeColor.LIME));
    public static final DeferredBlock<Block> PINK_RIBBON = register("pink_ribbon", () -> ribbon(DyeColor.PINK));
    public static final DeferredBlock<Block> GRAY_RIBBON = register("gray_ribbon", () -> ribbon(DyeColor.GRAY));
    public static final DeferredBlock<Block> LIGHT_GRAY_RIBBON = register("light_gray_ribbon", () -> ribbon(DyeColor.LIGHT_GRAY));
    public static final DeferredBlock<Block> CYAN_RIBBON = register("cyan_ribbon", () -> ribbon(DyeColor.CYAN));
    public static final DeferredBlock<Block> PURPLE_RIBBON = register("purple_ribbon", () -> ribbon(DyeColor.PURPLE));
    public static final DeferredBlock<Block> BLUE_RIBBON = register("blue_ribbon", () -> ribbon(DyeColor.BLUE));
    public static final DeferredBlock<Block> BROWN_RIBBON = register("brown_ribbon", () -> ribbon(DyeColor.BROWN));
    public static final DeferredBlock<Block> GREEN_RIBBON = register("green_ribbon", () -> ribbon(DyeColor.GREEN));
    public static final DeferredBlock<Block> RED_RIBBON = register("red_ribbon", () -> ribbon(DyeColor.RED));
    public static final DeferredBlock<Block> BLACK_RIBBON = register("black_ribbon", () -> ribbon(DyeColor.BLACK));
    
    public static final DeferredBlock<Block> FAN_BLOCK = register("fan_block", () -> new FanBlock(ofFullCopy(Blocks.DISPENSER)));
    public static final DeferredBlock<Block> WEATHER_VANE = register("weather_vane", () -> new WeatherVaneBlock(ofFullCopy(Blocks.IRON_BARS).noCollission()));
    public static final DeferredBlock<Block> PINWHEEL = register("pinwheel", () -> new PinwheelBlock(of().mapColor(DyeColor.WHITE).instabreak().sound(SoundType.WOOD).noCollission().pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> WHITE_PENNANT = register("white_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.WHITE)));
    public static final DeferredBlock<Block> ORANGE_PENNANT = register("orange_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.ORANGE)));
    public static final DeferredBlock<Block> MAGENTA_PENNANT = register("magenta_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.MAGENTA)));
    public static final DeferredBlock<Block> LIGHT_BLUE_PENNANT = register("light_blue_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.LIGHT_BLUE)));
    public static final DeferredBlock<Block> YELLOW_PENNANT = register("yellow_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.YELLOW)));
    public static final DeferredBlock<Block> LIME_PENNANT = register("lime_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.LIME)));
    public static final DeferredBlock<Block> PINK_PENNANT = register("pink_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.PINK)));
    public static final DeferredBlock<Block> GRAY_PENNANT = register("gray_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.GRAY)));
    public static final DeferredBlock<Block> LIGHT_GRAY_PENNANT = register("light_gray_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.LIGHT_GRAY)));
    public static final DeferredBlock<Block> CYAN_PENNANT = register("cyan_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.CYAN)));
    public static final DeferredBlock<Block> PURPLE_PENNANT = register("purple_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.PURPLE)));
    public static final DeferredBlock<Block> BLUE_PENNANT = register("blue_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.BLUE)));
    public static final DeferredBlock<Block> BROWN_PENNANT = register("brown_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.BROWN)));
    public static final DeferredBlock<Block> GREEN_PENNANT = register("green_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.GREEN)));
    public static final DeferredBlock<Block> RED_PENNANT = register("red_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.RED)));
    public static final DeferredBlock<Block> BLACK_PENNANT = register("black_pennant", () -> new PennantBlock(pinwheelProperties(DyeColor.BLACK)));

    public static final DeferredBlock<Block> WHITE_PAPERCUTTING = register("white_papercutting", () -> papercutting(DyeColor.WHITE));
    public static final DeferredBlock<Block> ORANGE_PAPERCUTTING = register("orange_papercutting", () -> papercutting(DyeColor.ORANGE));
    public static final DeferredBlock<Block> MAGENTA_PAPERCUTTING = register("magenta_papercutting", () -> papercutting(DyeColor.MAGENTA));
    public static final DeferredBlock<Block> LIGHT_BLUE_PAPERCUTTING = register("light_blue_papercutting", () -> papercutting(DyeColor.LIGHT_BLUE));
    public static final DeferredBlock<Block> YELLOW_PAPERCUTTING = register("yellow_papercutting", () -> papercutting(DyeColor.YELLOW));
    public static final DeferredBlock<Block> LIME_PAPERCUTTING = register("lime_papercutting", () -> papercutting(DyeColor.LIME));
    public static final DeferredBlock<Block> PINK_PAPERCUTTING = register("pink_papercutting", () -> papercutting(DyeColor.PINK));
    public static final DeferredBlock<Block> GRAY_PAPERCUTTING = register("gray_papercutting", () -> papercutting(DyeColor.GRAY));
    public static final DeferredBlock<Block> LIGHT_GRAY_PAPERCUTTING = register("light_gray_papercutting", () -> papercutting(DyeColor.LIGHT_GRAY));
    public static final DeferredBlock<Block> CYAN_PAPERCUTTING = register("cyan_papercutting", () -> papercutting(DyeColor.CYAN));
    public static final DeferredBlock<Block> PURPLE_PAPERCUTTING = register("purple_papercutting", () -> papercutting(DyeColor.PURPLE));
    public static final DeferredBlock<Block> BLUE_PAPERCUTTING = register("blue_papercutting", () -> papercutting(DyeColor.BLUE));
    public static final DeferredBlock<Block> BROWN_PAPERCUTTING = register("brown_papercutting", () -> papercutting(DyeColor.BROWN));
    public static final DeferredBlock<Block> GREEN_PAPERCUTTING = register("green_papercutting", () -> papercutting(DyeColor.GREEN));
    public static final DeferredBlock<Block> RED_PAPERCUTTING = register("red_papercutting", () -> papercutting(DyeColor.RED));
    public static final DeferredBlock<Block> BLACK_PAPERCUTTING = register("black_papercutting", () -> papercutting(DyeColor.BLACK));

    public static final DeferredBlock<Block> SANDBAG = register("sandbag", () -> new SandbagBlock(of().mapColor(MapColor.DIRT).instrument(NoteBlockInstrument.GUITAR).strength(0.8F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)));
    public static final DeferredBlock<Block> PYROTECHNICS_TABLE = register("pyrotechnics_table", () -> new PyrotechnicsTableBlock(ofFullCopy(Blocks.SMITHING_TABLE)));
    public static final DeferredBlock<Block> PAPERCRAFT_TABLE = register("papercraft_table", () -> new PapercraftTableBlock(ofFullCopy(Blocks.FLETCHING_TABLE)));
    public static final DeferredBlock<RotatedPillarBlock> CARDBOARD_BLOCK = register("cardboard_block", () -> new RotatedPillarBlock(of().mapColor(MapColor.WOOD).strength(0.5F).sound(ModSoundTypes.CARDBOARD)));
    public static final DeferredBlock<RotatedPillarBlock> VERMILION_FROGLIGHT = register("vermilion_froglight", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OCHRE_FROGLIGHT).mapColor(MapColorExtension.CRABAPPLE)));
    public static final DeferredBlock<RotatedPillarBlock> CYANINE_FROGLIGHT = register("cyanine_froglight", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OCHRE_FROGLIGHT).mapColor(MapColor.COLOR_CYAN)));
    public static final DeferredBlock<RotatedPillarBlock> UMBER_FROGLIGHT = register("umber_froglight", () -> new RotatedPillarBlock(ofFullCopy(Blocks.OCHRE_FROGLIGHT).mapColor(MapColor.WOOD)));
    public static final DeferredBlock<Block> OAK_MAILBOX = register("oak_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.OAK_PLANKS)));
    public static final DeferredBlock<Block> SPRUCE_MAILBOX = register("spruce_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.SPRUCE_PLANKS)));
    public static final DeferredBlock<Block> BIRCH_MAILBOX = register("birch_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.BIRCH_PLANKS)));
    public static final DeferredBlock<Block> JUNGLE_MAILBOX = register("jungle_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.JUNGLE_PLANKS)));
    public static final DeferredBlock<Block> ACACIA_MAILBOX = register("acacia_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.ACACIA_PLANKS)));
    public static final DeferredBlock<Block> DARK_OAK_MAILBOX = register("dark_oak_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.DARK_OAK_PLANKS)));
    public static final DeferredBlock<Block> CRIMSON_MAILBOX = register("crimson_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.CRIMSON_PLANKS)));
    public static final DeferredBlock<Block> WARPED_MAILBOX = register("warped_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.WARPED_PLANKS)));
    public static final DeferredBlock<Block> MANGROVE_MAILBOX = register("mangrove_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.MANGROVE_PLANKS)));
    public static final DeferredBlock<Block> CHERRY_MAILBOX = register("cherry_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.CHERRY_PLANKS)));
    public static final DeferredBlock<Block> BAMBOO_MAILBOX = register("bamboo_mailbox", () -> new MailboxBlock(ofFullCopy(Blocks.BAMBOO_PLANKS)));
    public static final DeferredBlock<Block> CRABAPPLE_MAILBOX = register("crabapple_mailbox", () -> new MailboxBlock(ofFullCopy(CRABAPPLE_PLANKS.get())));
    public static final DeferredBlock<Block> EBONY_MAILBOX = register("ebony_mailbox", () -> new MailboxBlock(ofFullCopy(EBONY_PLANKS.get())));
    public static final DeferredBlock<Block> GINKGO_MAILBOX = register("ginkgo_mailbox", () -> new MailboxBlock(ofFullCopy(GINKGO_PLANKS.get())));
    public static final DeferredBlock<Block> MAPLE_MAILBOX = register("maple_mailbox", () -> new MailboxBlock(ofFullCopy(MAPLE_PLANKS.get())));
    public static final DeferredBlock<Block> FROST_MAILBOX = register("frost_mailbox", () -> new MailboxBlock(ofFullCopy(FROST_PLANKS.get())));
    public static final DeferredBlock<Block> DAWN_REDWOOD_MAILBOX = register("dawn_redwood_mailbox", () -> new MailboxBlock(ofFullCopy(DAWN_REDWOOD_PLANKS.get())));
    public static final DeferredBlock<Block> JACARANDA_MAILBOX = register("jacaranda_mailbox", () -> new MailboxBlock(ofFullCopy(JACARANDA_PLANKS.get())));
    public static final DeferredBlock<Block> WILLOW_MAILBOX = register("willow_mailbox", () -> new MailboxBlock(ofFullCopy(WILLOW_PLANKS.get())));

    public static final DeferredBlock<Block> UNDERWATER_TNT = register("underwater_tnt", () -> new UnderwaterTntBlock(ofFullCopy(Blocks.TNT).mapColor(MapColor.DIAMOND)));

    public static final DeferredBlock<Block> COCOON = register("cocoon", () -> new CocoonBlock(of().forceSolidOn().strength(0.5F).sound(SoundType.METAL).noOcclusion().pushReaction(PushReaction.DESTROY).mapColor(MapColor.WOOL)));

    private static <T extends Block> DeferredBlock<T> register(String name, Supplier<T> supplier) {
        return BLOCKS.register(name, supplier);
    }
    
    private static RotatedPillarBlock log(Block pBlock, MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(ofFullCopy(pBlock)
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
        );
    }

    @SuppressWarnings("SameParameterValue")
    private static RotatedPillarBlock rareLog(MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(ofFullCopy(Blocks.OAK_LOG)
                .mapColor(state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor)
                .sound(ModSoundTypes.RARE_WOOD).strength(2.5F).instrument(InstrumentExtension.GUZHENG)
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
        return new ButtonBlock(pType, 30, ofFullCopy(pBlock).mapColor(pColor).instrument(InstrumentExtension.GUZHENG));
    }

    private static Block ribbon(DyeColor color) {
        return new RibbonBlock(ribbonProperties(color), color);
    }

    private static Block papercutting(DyeColor color) {
        return new PapercuttingBlock(of().noCollission().instabreak().sound(ModSoundTypes.PAPER).pushReaction(PushReaction.DESTROY).mapColor(color).ignitedByLava());
    }

    private static FlowerPotBlock flowerPot(Supplier<FlowerPotBlock> emptyPot, Supplier<? extends Block> potted) {
        return new FlowerPotBlock(emptyPot, potted, flowerPotProperties().mapColor(potted.get().defaultMapColor()));
    }

    private static FlowerPotBlock flowerPot(Supplier<? extends Block> potted) {
        return flowerPot(defaultPot(), potted);
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

    private static BlockBehaviour.Properties ribbonProperties(DyeColor color) {
        return of().mapColor(color).noCollission().strength(0.2F).sound(SoundType.WOOL).ignitedByLava().pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties pinwheelProperties(DyeColor color) {
        return of().mapColor(color).instabreak().sound(SoundType.WOOD).noCollission().pushReaction(PushReaction.DESTROY);
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
