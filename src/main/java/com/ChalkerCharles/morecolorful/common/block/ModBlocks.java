package com.ChalkerCharles.morecolorful.common.block;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.musical_instruments.*;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.InstrumentsType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.world.level.block.Block;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MoreColorful.MODID);

    public static final Supplier<Block> CRABAPPLE_PLANKS = BLOCKS.register("crabapple_planks", ()-> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS).mapColor(MapColor.TERRACOTTA_PINK)));
    public static final Supplier<Block> HARP = BLOCKS.register("harp", ()-> new HarpBlock(InstrumentsType.HARP, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.TERRACOTTA_YELLOW).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> UPRIGHT_PIANO = BLOCKS.register("upright_piano", ()-> new UprightPianoBlock(InstrumentsType.PIANO, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final Supplier<Block> GRAND_PIANO = BLOCKS.register("upright_piano", ()-> new GrandPianoBlock(InstrumentsType.PIANO, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.BLOCK)));
    public static final Supplier<Block> BASS_DRUM = BLOCKS.register("bass_drum", ()-> new BassDrumBlock(InstrumentsType.BASS_DRUM, BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final Supplier<Block> SNARE_DRUM = BLOCKS.register("snare_drum", ()-> new SnareDrumBlock(InstrumentsType.SNARE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> HIHAT = BLOCKS.register("hi-hat", ()-> new HiHatBlock(InstrumentsType.HAT, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> CHIMES = BLOCKS.register("chimes", ()-> new ChimesBlock(InstrumentsType.CHIMES, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> GLOCKENSPIEL = BLOCKS.register("glockenspiel", ()-> new GlockenspielBlock(InstrumentsType.GLOCKENSPIEL, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_GRATE).mapColor(MapColor.GOLD).pushReaction(PushReaction.DESTROY).forceSolidOn()));
    public static final Supplier<Block> XYLOPHONE = BLOCKS.register("xylophone", ()-> new XylophoneBlock(InstrumentsType.XYLOPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.WOOD).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> VIBRAPHONE = BLOCKS.register("vibraphone", ()-> new VibraphoneBlock(InstrumentsType.VIBRAPHONE, BlockBehaviour.Properties.ofFullCopy(Blocks.COPPER_BLOCK).mapColor(MapColor.METAL).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_BIT = BLOCKS.register("synthesizer_keyboard_bit", ()-> new SynthesizerKeyboardBlock(InstrumentsType.BIT, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.DEEPSLATE).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));
    public static final Supplier<Block> SYNTHESIZER_KEYBOARD_PLING = BLOCKS.register("synthesizer_keyboard_pling", ()-> new SynthesizerKeyboardBlock(InstrumentsType.PLING, BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BARS).mapColor(MapColor.COLOR_BLACK).strength(3.0F, 6.0F).pushReaction(PushReaction.DESTROY)));

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
