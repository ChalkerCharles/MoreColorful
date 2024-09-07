package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.*;
import com.ChalkerCharles.morecolorful.common.block.properties.NoteBlockInstrumentExtension;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MoreColorful.MODID);

    public static final Supplier<Block> CRABAPPLE_PLANKS = BLOCKS.register("crabapple_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final Supplier<Block> HARP = BLOCKS.register("harp", ()-> new HarpBlock(InstrumentsType.HARP, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.TERRACOTTA_YELLOW).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> UPRIGHT_PIANO = BLOCKS.register("upright_piano", ()-> new UprightPianoBlock(InstrumentsType.PIANO_LOW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final Supplier<Block> GRAND_PIANO = BLOCKS.register("grand_piano", ()-> new GrandPianoBlock(InstrumentsType.PIANO_LOW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final Supplier<Block> BASS_DRUM = BLOCKS.register("bass_drum", ()-> new BassDrumBlock(InstrumentsType.BASS_DRUM, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> SNARE_DRUM = BLOCKS.register("snare_drum", ()-> new SnareDrumBlock(InstrumentsType.SNARE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> TOMTOM_DRUM = BLOCKS.register("tom-tom_drum", ()-> new TomTomDrumBlock(InstrumentsType.TOM, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.CHERRY_WOOD).forceSolidOn()));
    public static final Supplier<Block> HIHAT = BLOCKS.register("hi-hat", ()-> new HiHatBlock(InstrumentsType.HAT, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> RIDE_CYMBAL = BLOCKS.register("ride_cymbal", ()-> new RideCymbalBlock(InstrumentsType.RIDE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> CRASH_CYMBAL = BLOCKS.register("crash_cymbal", ()-> new CrashCymbalBlock(InstrumentsType.CRASH, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> DRUM_SET = BLOCKS.register("drum_set", ()-> new DrumSetBlock(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3.0F, 6.0F).noOcclusion().pushReaction(PushReaction.DESTROY).sound(SoundType.COPPER).forceSolidOn()));
    public static final Supplier<Block> CHIMES = BLOCKS.register("chimes", ()-> new ChimesBlock(InstrumentsType.CHIMES, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.CHIME).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> GLOCKENSPIEL = BLOCKS.register("glockenspiel", ()-> new GlockenspielBlock(InstrumentsType.GLOCKENSPIEL, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> XYLOPHONE = BLOCKS.register("xylophone", ()-> new XylophoneBlock(InstrumentsType.XYLOPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> VIBRAPHONE = BLOCKS.register("vibraphone", ()-> new VibraphoneBlock(InstrumentsType.VIBRAPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_BIT = BLOCKS.register("synthesizer_keyboard_bit", ()-> new SynthesizerKeyboardBlock(InstrumentsType.BIT, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE).instrument(NoteBlockInstrument.BIT).emissiveRendering(Blocks::always)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_PLING = BLOCKS.register("synthesizer_keyboard_pling", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLING, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).instrument(NoteBlockInstrument.PLING)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_SCULK = BLOCKS.register("synthesizer_keyboard_sculk", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SCULK, BlockBehaviour.Properties.ofFullCopy(Blocks.SCULK_CATALYST).instrument(NoteBlockInstrumentExtension.SCULK).emissiveRendering(Blocks::always)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_AMETHYST = BLOCKS.register("synthesizer_keyboard_amethyst", ()-> new SynthesizerKeyboardBlock(InstrumentsType.CRYSTAL, BlockBehaviour.Properties.ofFullCopy(Blocks.AMETHYST_CLUSTER).instrument(NoteBlockInstrumentExtension.CRYSTAL).emissiveRendering(Blocks::always)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_SAW = BLOCKS.register("synthesizer_keyboard_saw", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SAW, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.CRIMSON_NYLIUM).instrument(NoteBlockInstrumentExtension.SAW)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_PLUCK = BLOCKS.register("synthesizer_keyboard_pluck", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLUCK, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.LAPIS).instrument(NoteBlockInstrumentExtension.PLUCK)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_SYNTH_BASS = BLOCKS.register("synthesizer_keyboard_synth_bass", ()-> new SynthesizerKeyboardBlock(InstrumentsType.SYNTH_BASS, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrumentExtension.SYNTH_BASS)));



    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
