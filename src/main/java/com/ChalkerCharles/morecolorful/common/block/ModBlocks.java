package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.client.particle.ModParticles;
import com.ChalkerCharles.morecolorful.common.block.common.PetalLeavesBlock;
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
    public static final DeferredBlock<LeavesBlock> CRABAPPLE_LEAVES = BLOCKS.register("crabapple_leaves", ()-> new PetalLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(MapColorExtension.CRABAPPLE), ModParticles.CRABAPPLE_LEAVES));
    public static final DeferredBlock<SaplingBlock> CRABAPPLE_SAPLING = BLOCKS.register("crabapple_sapling", ()-> new SaplingBlock(ModTreeGrower.CRABAPPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(MapColorExtension.CRABAPPLE)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_CRABAPPLE_SAPLING = BLOCKS.register("potted_crabapple_sapling", ()-> flowerPot(CRABAPPLE_SAPLING));
    public static final DeferredBlock<PinkPetalsBlock> BEGONIAS = BLOCKS.register("begonias", ()-> new PinkPetalsBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColorExtension.CRABAPPLE)));
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

    public static final DeferredBlock<FlowerBlock> PINK_DAISY = BLOCKS.register("pink_daisy", ()-> new FlowerBlock(MobEffects.REGENERATION, 8.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.OXEYE_DAISY).mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_DAISY = BLOCKS.register("potted_pink_daisy", ()-> flowerPot(PINK_DAISY));
    public static final DeferredBlock<FlowerBlock> RED_CARNATION = BLOCKS.register("red_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.RED_TULIP).mapColor(MapColor.COLOR_RED)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_RED_CARNATION = BLOCKS.register("potted_red_carnation", ()-> flowerPot(RED_CARNATION));
    public static final DeferredBlock<FlowerBlock> PINK_CARNATION = BLOCKS.register("pink_carnation", ()-> new FlowerBlock(MobEffects.DAMAGE_BOOST, 9.0F, BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_TULIP).mapColor(MapColor.COLOR_PINK)));
    public static final DeferredBlock<FlowerPotBlock> POTTED_PINK_CARNATION = BLOCKS.register("potted_pink_carnation", ()-> flowerPot(PINK_CARNATION));



    @SuppressWarnings("SameParameterValue")
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
    @SuppressWarnings("SameParameterValue")
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
