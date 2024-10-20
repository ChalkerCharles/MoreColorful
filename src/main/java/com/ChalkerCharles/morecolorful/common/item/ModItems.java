package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import com.ChalkerCharles.morecolorful.common.block.ModBlocks;
import com.ChalkerCharles.morecolorful.common.item.musical_instruments.*;
import com.ChalkerCharles.morecolorful.util.EnumExtensions;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MoreColorful.MODID);
    // Blocks
    public static final DeferredItem<BlockItem> HARP = ITEMS.registerSimpleBlockItem(ModBlocks.HARP);
    public static final DeferredItem<BlockItem> UPRIGHT_PIANO = ITEMS.registerSimpleBlockItem(ModBlocks.UPRIGHT_PIANO);
    public static final DeferredItem<BlockItem> GRAND_PIANO = ITEMS.registerSimpleBlockItem(ModBlocks.GRAND_PIANO);
    public static final DeferredItem<BlockItem> BASS_DRUM = ITEMS.registerSimpleBlockItem(ModBlocks.BASS_DRUM);
    public static final DeferredItem<BlockItem> SNARE_DRUM = ITEMS.registerSimpleBlockItem(ModBlocks.SNARE_DRUM);
    public static final DeferredItem<BlockItem> TOMTOM_DRUM = ITEMS.registerSimpleBlockItem(ModBlocks.TOMTOM_DRUM);
    public static final DeferredItem<BlockItem> HIHAT = ITEMS.registerSimpleBlockItem(ModBlocks.HIHAT);
    public static final DeferredItem<BlockItem> RIDE_CYMBAL = ITEMS.registerSimpleBlockItem(ModBlocks.RIDE_CYMBAL);
    public static final DeferredItem<BlockItem> CRASH_CYMBAL = ITEMS.registerSimpleBlockItem(ModBlocks.CRASH_CYMBAL);
    public static final DeferredItem<BlockItem> DRUM_SET = ITEMS.registerSimpleBlockItem(ModBlocks.DRUM_SET);
    public static final DeferredItem<BlockItem> CHIMES = ITEMS.registerSimpleBlockItem(ModBlocks.CHIMES);
    public static final DeferredItem<BlockItem> GLOCKENSPIEL = ITEMS.registerSimpleBlockItem(ModBlocks.GLOCKENSPIEL);
    public static final DeferredItem<BlockItem> XYLOPHONE = ITEMS.registerSimpleBlockItem(ModBlocks.XYLOPHONE);
    public static final DeferredItem<BlockItem> VIBRAPHONE = ITEMS.registerSimpleBlockItem(ModBlocks.VIBRAPHONE);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_BIT = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_BIT);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLING = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_PLING);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SCULK = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SCULK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_AMETHYST = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_AMETHYST);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SAW = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SAW);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_PLUCK = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_PLUCK);
    public static final DeferredItem<BlockItem> SYNTHESIZER_KEYBOARD_SYNTH_BASS = registerSynthesizerKeyboard(ModBlocks.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
    public static final DeferredItem<BlockItem> GUZHENG = ITEMS.registerSimpleBlockItem(ModBlocks.GUZHENG);

    public static final DeferredItem<BlockItem> CRABAPPLE_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_LOG);
    public static final DeferredItem<BlockItem> CRABAPPLE_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_CRABAPPLE_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_CRABAPPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_CRABAPPLE_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_CRABAPPLE_WOOD);
    public static final DeferredItem<BlockItem> CRABAPPLE_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_PLANKS);
    public static final DeferredItem<BlockItem> CRABAPPLE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_STAIRS);
    public static final DeferredItem<BlockItem> CRABAPPLE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_SLAB);
    public static final DeferredItem<BlockItem> CRABAPPLE_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_FENCE);
    public static final DeferredItem<BlockItem> CRABAPPLE_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_FENCE_GATE);
    public static final DeferredItem<BlockItem> CRABAPPLE_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_DOOR);
    public static final DeferredItem<BlockItem> CRABAPPLE_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_TRAPDOOR);
    public static final DeferredItem<BlockItem> CRABAPPLE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> CRABAPPLE_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_BUTTON);
    public static final DeferredItem<BlockItem> CRABAPPLE_SIGN = registerSign(ModBlocks.CRABAPPLE_SIGN, ModBlocks.CRABAPPLE_WALL_SIGN);
    public static final DeferredItem<BlockItem> CRABAPPLE_HANGING_SIGN = registerHangingSign(ModBlocks.CRABAPPLE_HANGING_SIGN, ModBlocks.CRABAPPLE_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> EBONY_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_LOG);
    public static final DeferredItem<BlockItem> EBONY_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_EBONY_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_EBONY_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_EBONY_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_EBONY_WOOD);
    public static final DeferredItem<BlockItem> EBONY_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_PLANKS);
    public static final DeferredItem<BlockItem> EBONY_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_STAIRS);
    public static final DeferredItem<BlockItem> EBONY_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_SLAB);
    public static final DeferredItem<BlockItem> EBONY_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_FENCE);
    public static final DeferredItem<BlockItem> EBONY_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_FENCE_GATE);
    public static final DeferredItem<BlockItem> EBONY_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_DOOR);
    public static final DeferredItem<BlockItem> EBONY_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_TRAPDOOR);
    public static final DeferredItem<BlockItem> EBONY_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> EBONY_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.EBONY_BUTTON);
    public static final DeferredItem<BlockItem> EBONY_SIGN = registerSign(ModBlocks.EBONY_SIGN, ModBlocks.EBONY_WALL_SIGN);
    public static final DeferredItem<BlockItem> EBONY_HANGING_SIGN = registerHangingSign(ModBlocks.EBONY_HANGING_SIGN, ModBlocks.EBONY_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> GINKGO_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_LOG);
    public static final DeferredItem<BlockItem> GINKGO_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_GINKGO_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_GINKGO_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_GINKGO_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_GINKGO_WOOD);
    public static final DeferredItem<BlockItem> GINKGO_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_PLANKS);
    public static final DeferredItem<BlockItem> GINKGO_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_STAIRS);
    public static final DeferredItem<BlockItem> GINKGO_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_SLAB);
    public static final DeferredItem<BlockItem> GINKGO_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_FENCE);
    public static final DeferredItem<BlockItem> GINKGO_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_FENCE_GATE);
    public static final DeferredItem<BlockItem> GINKGO_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_DOOR);
    public static final DeferredItem<BlockItem> GINKGO_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_TRAPDOOR);
    public static final DeferredItem<BlockItem> GINKGO_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> GINKGO_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_BUTTON);
    public static final DeferredItem<BlockItem> GINKGO_SIGN = registerSign(ModBlocks.GINKGO_SIGN, ModBlocks.GINKGO_WALL_SIGN);
    public static final DeferredItem<BlockItem> GINKGO_HANGING_SIGN = registerHangingSign(ModBlocks.GINKGO_HANGING_SIGN, ModBlocks.GINKGO_WALL_HANGING_SIGN);
    public static final DeferredItem<BlockItem> MAPLE_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_LOG);
    public static final DeferredItem<BlockItem> MAPLE_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_WOOD);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_LOG = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_MAPLE_LOG);
    public static final DeferredItem<BlockItem> STRIPPED_MAPLE_WOOD = ITEMS.registerSimpleBlockItem(ModBlocks.STRIPPED_MAPLE_WOOD);
    public static final DeferredItem<BlockItem> MAPLE_PLANKS = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_PLANKS);
    public static final DeferredItem<BlockItem> MAPLE_STAIRS = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_STAIRS);
    public static final DeferredItem<BlockItem> MAPLE_SLAB = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_SLAB);
    public static final DeferredItem<BlockItem> MAPLE_FENCE = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_FENCE);
    public static final DeferredItem<BlockItem> MAPLE_FENCE_GATE = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_FENCE_GATE);
    public static final DeferredItem<BlockItem> MAPLE_DOOR = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_DOOR);
    public static final DeferredItem<BlockItem> MAPLE_TRAPDOOR = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_TRAPDOOR);
    public static final DeferredItem<BlockItem> MAPLE_PRESSURE_PLATE = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_PRESSURE_PLATE);
    public static final DeferredItem<BlockItem> MAPLE_BUTTON = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_BUTTON);
    public static final DeferredItem<BlockItem> MAPLE_SIGN = registerSign(ModBlocks.MAPLE_SIGN, ModBlocks.MAPLE_WALL_SIGN);
    public static final DeferredItem<BlockItem> MAPLE_HANGING_SIGN = registerHangingSign(ModBlocks.MAPLE_HANGING_SIGN, ModBlocks.MAPLE_WALL_HANGING_SIGN);

    public static final DeferredItem<BlockItem> CRABAPPLE_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_LEAVES);
    public static final DeferredItem<BlockItem> CRABAPPLE_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.CRABAPPLE_SAPLING);
    public static final DeferredItem<BlockItem> BEGONIAS = ITEMS.registerSimpleBlockItem(ModBlocks.BEGONIAS);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_CHERRY_LEAVES);
    public static final DeferredItem<BlockItem> WHITE_CHERRY_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_CHERRY_SAPLING);
    public static final DeferredItem<BlockItem> WHITE_PETALS = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_PETALS);
    public static final DeferredItem<BlockItem> AUTUMN_BIRCH_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.AUTUMN_BIRCH_LEAVES);
    public static final DeferredItem<BlockItem> AUTUMN_BIRCH_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.AUTUMN_BIRCH_SAPLING);
    public static final DeferredItem<BlockItem> AUTUMN_BIRCH_LEAF_PILE = ITEMS.registerSimpleBlockItem(ModBlocks.AUTUMN_BIRCH_LEAF_PILE);
    public static final DeferredItem<BlockItem> GINKGO_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_LEAVES);
    public static final DeferredItem<BlockItem> GINKGO_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_SAPLING);
    public static final DeferredItem<BlockItem> GINKGO_LEAF_PILE = ITEMS.registerSimpleBlockItem(ModBlocks.GINKGO_LEAF_PILE);
    public static final DeferredItem<BlockItem> MAPLE_LEAVES = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_LEAVES);
    public static final DeferredItem<BlockItem> MAPLE_SAPLING = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_SAPLING);
    public static final DeferredItem<BlockItem> MAPLE_LEAF_PILE = ITEMS.registerSimpleBlockItem(ModBlocks.MAPLE_LEAF_PILE);

    public static final DeferredItem<BlockItem> PINK_DAISY = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_DAISY);
    public static final DeferredItem<BlockItem> RED_CARNATION = ITEMS.registerSimpleBlockItem(ModBlocks.RED_CARNATION);
    public static final DeferredItem<BlockItem> PINK_CARNATION = ITEMS.registerSimpleBlockItem(ModBlocks.PINK_CARNATION);
    public static final DeferredItem<BlockItem> WHITE_CARNATION = ITEMS.registerSimpleBlockItem(ModBlocks.WHITE_CARNATION);
    public static final DeferredItem<BlockItem> RED_SPIDER_LILY = ITEMS.registerSimpleBlockItem(ModBlocks.RED_SPIDER_LILY);
    public static final DeferredItem<BlockItem> YELLOW_CHRYSANTHEMUM = ITEMS.registerSimpleBlockItem(ModBlocks.YELLOW_CHRYSANTHEMUM);
    public static final DeferredItem<BlockItem> GREEN_CHRYSANTHEMUM = ITEMS.registerSimpleBlockItem(ModBlocks.GREEN_CHRYSANTHEMUM);
    public static final DeferredItem<BlockItem> DAYBLOOM = ITEMS.registerSimpleBlockItem(ModBlocks.DAYBLOOM);

    // Items
    public static final DeferredItem<Item> VIOLIN = ITEMS.register("violin", ()-> new BowedStringInstrumentItem(InstrumentsType.VIOLIN, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FIDDLE_BOW = ITEMS.register("fiddle_bow", ()-> new Item(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DRUMSTICK = ITEMS.register("drumstick", ()-> new DrumstickItem(new Item.Properties()));
    public static final DeferredItem<Item> BASS = ITEMS.register("bass", ()-> new GuitarItem(InstrumentsType.BASS, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GUITAR = ITEMS.register("guitar", ()-> new GuitarItem(InstrumentsType.GUITAR, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> FLUTE = ITEMS.register("flute", ()-> new BothHandsInstrumentItem(InstrumentsType.FLUTE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> COW_BELL = ITEMS.register("cow_bell", ()-> new CowBellItem(InstrumentsType.COW_BELL, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> DIDGERIDOO = ITEMS.register("didgeridoo", ()-> new DidgeridooItem(InstrumentsType.DIDGERIDOO, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> BANJO = ITEMS.register("banjo", ()-> new GuitarItem(InstrumentsType.BANJO, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CELLO = ITEMS.register("cello", ()-> new BowedStringInstrumentItem(InstrumentsType.CELLO, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ELECTRIC_GUITAR = ITEMS.register("electric_guitar", ()-> new GuitarItem(InstrumentsType.ELECTRIC_GUITAR, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> TRUMPET = ITEMS.register("trumpet", ()-> new TrumpetItem(InstrumentsType.TRUMPET, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> SAXOPHONE = ITEMS.register("saxophone", ()-> new DidgeridooItem(InstrumentsType.SAXOPHONE, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> OCARINA = ITEMS.register("ocarina", ()-> new DidgeridooItem(InstrumentsType.OCARINA, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> HARMONICA = ITEMS.register("harmonica", ()-> new DidgeridooItem(InstrumentsType.HARMONICA, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> PIPA = ITEMS.register("pipa", ()-> new BothHandsInstrumentItem(InstrumentsType.PIPA, new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> ERHU = ITEMS.register("erhu", ()-> new BowedStringInstrumentItem(InstrumentsType.ERHU, new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> CRABAPPLE_BOAT = ITEMS.register("crabapple_boat", ()-> new BoatItem(false, EnumExtensions.BoatType.CRABAPPLE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CRABAPPLE_CHEST_BOAT = ITEMS.register("crabapple_chest_boat", ()-> new BoatItem(true, EnumExtensions.BoatType.CRABAPPLE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EBONY_BOAT = ITEMS.register("ebony_boat", ()-> new BoatItem(false, EnumExtensions.BoatType.EBONY.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> EBONY_CHEST_BOAT = ITEMS.register("ebony_chest_boat", ()-> new BoatItem(true, EnumExtensions.BoatType.EBONY.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GINKGO_BOAT = ITEMS.register("ginkgo_boat", ()-> new BoatItem(false, EnumExtensions.BoatType.GINKGO.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> GINKGO_CHEST_BOAT = ITEMS.register("ginkgo_chest_boat", ()-> new BoatItem(true, EnumExtensions.BoatType.GINKGO.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_BOAT = ITEMS.register("maple_boat", ()-> new BoatItem(false, EnumExtensions.BoatType.MAPLE.getValue(), new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> MAPLE_CHEST_BOAT = ITEMS.register("maple_chest_boat", ()-> new BoatItem(true, EnumExtensions.BoatType.MAPLE.getValue(), new Item.Properties().stacksTo(1)));

    private static DeferredItem<BlockItem> registerSynthesizerKeyboard(DeferredBlock<Block> block) {
        return ITEMS.register(block.getId().getPath(), () -> new SynthesizerKeyboardItem(block.get(), new Item.Properties()));
    }
    private static DeferredItem<BlockItem> registerSign(DeferredBlock<StandingSignBlock> sign, DeferredBlock<WallSignBlock> wallSign) {
        return ITEMS.register(sign.getId().getPath(), () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
    }
    private static DeferredItem<BlockItem> registerHangingSign(DeferredBlock<CeilingHangingSignBlock> hangingSign, DeferredBlock<WallHangingSignBlock> wallHangingSign) {
        return ITEMS.register(hangingSign.getId().getPath(), () -> new HangingSignItem(hangingSign.get(), wallHangingSign.get(), new Item.Properties().stacksTo(16)));
    }

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }
}