package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreColorful.MODID);

    @SuppressWarnings("unused")
    public static final ResourceKey<CreativeModeTab> MUSICAL_INSTRUMENTS_TAB = CREATIVE_MODE_TABS.register("musical_instruments_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("creativetab.morecolorful.musical_instruments_tab"))
            .icon(() -> ModItems.VIOLIN.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ModItems.GRAND_PIANO);
                output.accept(ModItems.UPRIGHT_PIANO);
                output.accept(ModItems.HARP);
                output.accept(ModItems.GUZHENG);
                output.accept(ModItems.VIOLIN);
                output.accept(ModItems.CELLO);
                output.accept(ModItems.ERHU);
                output.accept(ModItems.FIDDLE_BOW);
                output.accept(ModItems.BASS);
                output.accept(ModItems.GUITAR);
                output.accept(ModItems.ELECTRIC_GUITAR);
                output.accept(ModItems.BANJO);
                output.accept(ModItems.PIPA);
                output.accept(ModItems.FLUTE);
                output.accept(ModItems.DIDGERIDOO);
                output.accept(ModItems.TRUMPET);
                output.accept(ModItems.SAXOPHONE);
                output.accept(ModItems.OCARINA);
                output.accept(ModItems.HARMONICA);
                output.accept(ModItems.COW_BELL);
                output.accept(ModItems.BASS_DRUM);
                output.accept(ModItems.SNARE_DRUM);
                output.accept(ModItems.TOMTOM_DRUM);
                output.accept(ModItems.HIHAT);
                output.accept(ModItems.RIDE_CYMBAL);
                output.accept(ModItems.CRASH_CYMBAL);
                output.accept(ModItems.DRUM_SET);
                output.accept(ModItems.CHIMES);
                output.accept(ModItems.GLOCKENSPIEL);
                output.accept(ModItems.XYLOPHONE);
                output.accept(ModItems.VIBRAPHONE);
                output.accept(ModItems.DRUMSTICK);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_BIT);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_PLING);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_SCULK);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_AMETHYST);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_SAW);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_PLUCK);
                output.accept(ModItems.SYNTHESIZER_KEYBOARD_SYNTH_BASS);
            })
            .build()).getKey();

    public static void insertInVanillaTabs(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab == CreativeModeTabs.BUILDING_BLOCKS) {
            insertAfterBySequence(event, Items.CHERRY_BUTTON,
                    ModItems.CRABAPPLE_LOG,
                    ModItems.CRABAPPLE_WOOD,
                    ModItems.STRIPPED_CRABAPPLE_LOG,
                    ModItems.STRIPPED_CRABAPPLE_WOOD,
                    ModItems.CRABAPPLE_PLANKS,
                    ModItems.CRABAPPLE_STAIRS,
                    ModItems.CRABAPPLE_SLAB,
                    ModItems.CRABAPPLE_FENCE,
                    ModItems.CRABAPPLE_FENCE_GATE,
                    ModItems.CRABAPPLE_DOOR,
                    ModItems.CRABAPPLE_TRAPDOOR,
                    ModItems.CRABAPPLE_PRESSURE_PLATE,
                    ModItems.CRABAPPLE_BUTTON,
                    ModItems.EBONY_LOG,
                    ModItems.EBONY_WOOD,
                    ModItems.STRIPPED_EBONY_LOG,
                    ModItems.STRIPPED_EBONY_WOOD,
                    ModItems.EBONY_PLANKS,
                    ModItems.EBONY_STAIRS,
                    ModItems.EBONY_SLAB,
                    ModItems.EBONY_FENCE,
                    ModItems.EBONY_FENCE_GATE,
                    ModItems.EBONY_DOOR,
                    ModItems.EBONY_TRAPDOOR,
                    ModItems.EBONY_PRESSURE_PLATE,
                    ModItems.EBONY_BUTTON,
                    ModItems.GINKGO_LOG,
                    ModItems.GINKGO_WOOD,
                    ModItems.STRIPPED_GINKGO_LOG,
                    ModItems.STRIPPED_GINKGO_WOOD,
                    ModItems.GINKGO_PLANKS,
                    ModItems.GINKGO_STAIRS,
                    ModItems.GINKGO_SLAB,
                    ModItems.GINKGO_FENCE,
                    ModItems.GINKGO_FENCE_GATE,
                    ModItems.GINKGO_DOOR,
                    ModItems.GINKGO_TRAPDOOR,
                    ModItems.GINKGO_PRESSURE_PLATE,
                    ModItems.GINKGO_BUTTON,
                    ModItems.MAPLE_LOG,
                    ModItems.MAPLE_WOOD,
                    ModItems.STRIPPED_MAPLE_LOG,
                    ModItems.STRIPPED_MAPLE_WOOD,
                    ModItems.MAPLE_PLANKS,
                    ModItems.MAPLE_STAIRS,
                    ModItems.MAPLE_SLAB,
                    ModItems.MAPLE_FENCE,
                    ModItems.MAPLE_FENCE_GATE,
                    ModItems.MAPLE_DOOR,
                    ModItems.MAPLE_TRAPDOOR,
                    ModItems.MAPLE_PRESSURE_PLATE,
                    ModItems.MAPLE_BUTTON,
                    ModItems.FROST_LOG,
                    ModItems.FROST_WOOD,
                    ModItems.STRIPPED_FROST_LOG,
                    ModItems.STRIPPED_FROST_WOOD,
                    ModItems.FROST_PLANKS,
                    ModItems.FROST_STAIRS,
                    ModItems.FROST_SLAB,
                    ModItems.FROST_FENCE,
                    ModItems.FROST_FENCE_GATE,
                    ModItems.FROST_DOOR,
                    ModItems.FROST_TRAPDOOR,
                    ModItems.FROST_PRESSURE_PLATE,
                    ModItems.FROST_BUTTON,
                    ModItems.DAWN_REDWOOD_LOG,
                    ModItems.DAWN_REDWOOD_WOOD,
                    ModItems.STRIPPED_DAWN_REDWOOD_LOG,
                    ModItems.STRIPPED_DAWN_REDWOOD_WOOD,
                    ModItems.DAWN_REDWOOD_PLANKS,
                    ModItems.DAWN_REDWOOD_STAIRS,
                    ModItems.DAWN_REDWOOD_SLAB,
                    ModItems.DAWN_REDWOOD_FENCE,
                    ModItems.DAWN_REDWOOD_FENCE_GATE,
                    ModItems.DAWN_REDWOOD_DOOR,
                    ModItems.DAWN_REDWOOD_TRAPDOOR,
                    ModItems.DAWN_REDWOOD_PRESSURE_PLATE,
                    ModItems.DAWN_REDWOOD_BUTTON,
                    ModItems.JACARANDA_LOG,
                    ModItems.JACARANDA_WOOD,
                    ModItems.STRIPPED_JACARANDA_LOG,
                    ModItems.STRIPPED_JACARANDA_WOOD,
                    ModItems.JACARANDA_PLANKS,
                    ModItems.JACARANDA_STAIRS,
                    ModItems.JACARANDA_SLAB,
                    ModItems.JACARANDA_FENCE,
                    ModItems.JACARANDA_FENCE_GATE,
                    ModItems.JACARANDA_DOOR,
                    ModItems.JACARANDA_TRAPDOOR,
                    ModItems.JACARANDA_PRESSURE_PLATE,
                    ModItems.JACARANDA_BUTTON,
                    ModItems.WILLOW_LOG,
                    ModItems.WILLOW_WOOD,
                    ModItems.STRIPPED_WILLOW_LOG,
                    ModItems.STRIPPED_WILLOW_WOOD,
                    ModItems.WILLOW_PLANKS,
                    ModItems.WILLOW_STAIRS,
                    ModItems.WILLOW_SLAB,
                    ModItems.WILLOW_FENCE,
                    ModItems.WILLOW_FENCE_GATE,
                    ModItems.WILLOW_DOOR,
                    ModItems.WILLOW_TRAPDOOR,
                    ModItems.WILLOW_PRESSURE_PLATE,
                    ModItems.WILLOW_BUTTON
            );
        } else if (tab == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfterBySequence(event, Items.CHERRY_LOG,
                    ModItems.CRABAPPLE_LOG,
                    ModItems.GINKGO_LOG,
                    ModItems.MAPLE_LOG,
                    ModItems.FROST_LOG,
                    ModItems.DAWN_REDWOOD_LOG,
                    ModItems.DAWN_REDWOOD_ROOTS,
                    ModItems.JACARANDA_LOG,
                    ModItems.WILLOW_LOG
            );
            insertAfterBySequence(event, Items.CHERRY_LEAVES,
                    ModItems.CRABAPPLE_LEAVES,
                    ModItems.WHITE_CHERRY_LEAVES,
                    ModItems.ORANGE_BIRCH_LEAVES,
                    ModItems.YELLOW_BIRCH_LEAVES,
                    ModItems.GINKGO_LEAVES,
                    ModItems.MAPLE_LEAVES,
                    ModItems.FROST_LEAVES,
                    ModItems.DAWN_REDWOOD_LEAVES,
                    ModItems.JACARANDA_LEAVES,
                    ModItems.WILLOW_LEAVES,
                    ModItems.WILLOW_BRANCHES
            );
            insertAfterBySequence(event, Items.CHERRY_SAPLING,
                    ModItems.CRABAPPLE_SAPLING,
                    ModItems.WHITE_CHERRY_SAPLING,
                    ModItems.ORANGE_BIRCH_SAPLING,
                    ModItems.YELLOW_BIRCH_SAPLING,
                    ModItems.GINKGO_SAPLING,
                    ModItems.MAPLE_SAPLING,
                    ModItems.FROST_SAPLING,
                    ModItems.DAWN_REDWOOD_SAPLING,
                    ModItems.JACARANDA_SAPLING,
                    ModItems.WILLOW_SAPLING
            );
            insertAfterBySequence(event, Items.SUGAR_CANE,
                    ModItems.REED
            );
            insertAfterBySequence(event, Items.FERN,
                    ModItems.SHORT_WATER_GRASS
            );
            insertAfterBySequence(event, Items.LARGE_FERN,
                    ModItems.TALL_WATER_GRASS
            );
            insertAfterBySequence(event, Items.WITHER_ROSE,
                    ModItems.PINK_DAISY,
                    ModItems.RED_CARNATION,
                    ModItems.PINK_CARNATION,
                    ModItems.WHITE_CARNATION,
                    ModItems.RED_SPIDER_LILY,
                    ModItems.YELLOW_CHRYSANTHEMUM,
                    ModItems.GREEN_CHRYSANTHEMUM,
                    ModItems.OPEN_DAYBLOOM,
                    ModItems.CLOSED_DAYBLOOM,
                    ModItems.EDELWEISS,
                    ModItems.CROCUS,
                    ModItems.IRIS,
                    ModItems.LAVENDER,
                    ModItems.DAFFODIL,
                    ModItems.GERBERA_DAISY,
                    ModItems.RAPESEED_FLOWER
            );
            insertAfterBySequence(event, Items.PINK_PETALS,
                    ModItems.BEGONIAS,
                    ModItems.WHITE_PETALS,
                    ModItems.FROSTY_PETALS,
                    ModItems.VIOLETS,
                    ModItems.BUTTERCUPS,
                    ModItems.FORGET_ME_NOTS,
                    ModItems.BABY_BLUE_EYES,
                    ModItems.SPEEDWELLS,
                    ModItems.WOOD_SORRELS,
                    ModItems.ORANGE_BIRCH_LEAF_LITTER,
                    ModItems.YELLOW_BIRCH_LEAF_LITTER,
                    ModItems.GINKGO_LEAF_LITTER,
                    ModItems.MAPLE_LEAF_LITTER,
                    ModItems.DAWN_REDWOOD_LEAF_LITTER
            );
            insertAfterBySequence(event, Items.PITCHER_PLANT,
                    ModItems.CATTAIL,
                    ModItems.TALL_RAPESEED_FLOWER
            );
            insertAfterBySequence(event, Items.SWEET_BERRIES,
                    ModItems.STRAWBERRY,
                    ModItems.BLUEBERRIES
            );
            insertAfterBySequence(event, Items.LILY_PAD,
                    ModItems.OPEN_WATER_LILY,
                    ModItems.CLOSED_WATER_LILY,
                    ModItems.OPEN_WHITE_WATER_LILY,
                    ModItems.CLOSED_WHITE_WATER_LILY,
                    ModItems.OPEN_BLUE_WATER_LILY,
                    ModItems.CLOSED_BLUE_WATER_LILY,
                    ModItems.DUCKWEEDS
            );
        } else if (tab == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            insertAfterBySequence(event, Items.CHERRY_HANGING_SIGN,
                    ModItems.CRABAPPLE_SIGN,
                    ModItems.CRABAPPLE_HANGING_SIGN,
                    ModItems.EBONY_SIGN,
                    ModItems.EBONY_HANGING_SIGN,
                    ModItems.GINKGO_SIGN,
                    ModItems.GINKGO_HANGING_SIGN,
                    ModItems.MAPLE_SIGN,
                    ModItems.MAPLE_HANGING_SIGN,
                    ModItems.FROST_SIGN,
                    ModItems.FROST_HANGING_SIGN,
                    ModItems.DAWN_REDWOOD_SIGN,
                    ModItems.DAWN_REDWOOD_HANGING_SIGN,
                    ModItems.JACARANDA_SIGN,
                    ModItems.JACARANDA_HANGING_SIGN,
                    ModItems.WILLOW_SIGN,
                    ModItems.WILLOW_HANGING_SIGN
            );
        } else if (tab == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            insertAfterBySequence(event, Items.CHERRY_CHEST_BOAT,
                    ModItems.CRABAPPLE_BOAT,
                    ModItems.CRABAPPLE_CHEST_BOAT,
                    ModItems.EBONY_BOAT,
                    ModItems.EBONY_CHEST_BOAT,
                    ModItems.GINKGO_BOAT,
                    ModItems.GINKGO_CHEST_BOAT,
                    ModItems.MAPLE_BOAT,
                    ModItems.MAPLE_CHEST_BOAT,
                    ModItems.FROST_BOAT,
                    ModItems.FROST_CHEST_BOAT,
                    ModItems.DAWN_REDWOOD_BOAT,
                    ModItems.DAWN_REDWOOD_CHEST_BOAT,
                    ModItems.JACARANDA_BOAT,
                    ModItems.JACARANDA_CHEST_BOAT,
                    ModItems.WILLOW_BOAT,
                    ModItems.WILLOW_CHEST_BOAT
            );
        } else if (tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            insertAfterBySequence(event, Items.SWEET_BERRIES,
                    ModItems.STRAWBERRY,
                    ModItems.BLUEBERRIES
            );
        }
    }

    private static void insertAfterBySequence(BuildCreativeModeTabContentsEvent event, Item existing, ItemLike... entries) {
        event.insertAfter(existing.getDefaultInstance(), new ItemStack(entries[0]), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        for (int i = 1, l = entries.length; i < l; i++) {
            event.insertAfter(new ItemStack(entries[i - 1]), new ItemStack(entries[i]), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
