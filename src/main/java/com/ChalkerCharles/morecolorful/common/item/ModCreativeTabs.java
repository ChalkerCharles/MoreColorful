package com.ChalkerCharles.morecolorful.common.item;

import com.ChalkerCharles.morecolorful.MoreColorful;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MoreColorful.MODID);

    @SuppressWarnings("unused")
    public static final ResourceKey<CreativeModeTab> MUSICAL_INSTRUMENTS_TAB = CREATIVE_MODE_TABS.register("musical_instruments_tab",() -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("creativetab.morecolorful.musical_instruments_tab"))
            .icon(()-> ModItems.VIOLIN.get().getDefaultInstance())
            .displayItems((pParameters, pOutput) -> {
                pOutput.accept(ModItems.GRAND_PIANO.get());
                pOutput.accept(ModItems.UPRIGHT_PIANO.get());
                pOutput.accept(ModItems.HARP.get());
                pOutput.accept(ModItems.GUZHENG.get());
                pOutput.accept(ModItems.VIOLIN.get());
                pOutput.accept(ModItems.CELLO.get());
                pOutput.accept(ModItems.ERHU.get());
                pOutput.accept(ModItems.FIDDLE_BOW.get());
                pOutput.accept(ModItems.BASS.get());
                pOutput.accept(ModItems.GUITAR.get());
                pOutput.accept(ModItems.ELECTRIC_GUITAR.get());
                pOutput.accept(ModItems.BANJO.get());
                pOutput.accept(ModItems.PIPA.get());
                pOutput.accept(ModItems.FLUTE.get());
                pOutput.accept(ModItems.DIDGERIDOO.get());
                pOutput.accept(ModItems.TRUMPET.get());
                pOutput.accept(ModItems.SAXOPHONE.get());
                pOutput.accept(ModItems.OCARINA.get());
                pOutput.accept(ModItems.HARMONICA.get());
                pOutput.accept(ModItems.COW_BELL.get());
                pOutput.accept(ModItems.BASS_DRUM.get());
                pOutput.accept(ModItems.SNARE_DRUM.get());
                pOutput.accept(ModItems.TOMTOM_DRUM.get());
                pOutput.accept(ModItems.HIHAT.get());
                pOutput.accept(ModItems.RIDE_CYMBAL.get());
                pOutput.accept(ModItems.CRASH_CYMBAL.get());
                pOutput.accept(ModItems.DRUM_SET.get());
                pOutput.accept(ModItems.CHIMES.get());
                pOutput.accept(ModItems.GLOCKENSPIEL.get());
                pOutput.accept(ModItems.XYLOPHONE.get());
                pOutput.accept(ModItems.VIBRAPHONE.get());
                pOutput.accept(ModItems.DRUMSTICK.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_BIT.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_PLING.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_SCULK.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_AMETHYST.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_SAW.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_PLUCK.get());
                pOutput.accept(ModItems.SYNTHESIZER_KEYBOARD_SYNTH_BASS.get());
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
                    ModItems.MAPLE_BUTTON
            );
        } else if (tab == CreativeModeTabs.NATURAL_BLOCKS) {
            insertAfterBySequence(event, Items.CHERRY_LOG,
                    ModItems.CRABAPPLE_LOG,
                    ModItems.GINKGO_LOG,
                    ModItems.MAPLE_LOG
            );
            insertAfterBySequence(event, Items.CHERRY_LEAVES,
                    ModItems.CRABAPPLE_LEAVES,
                    ModItems.WHITE_CHERRY_LEAVES,
                    ModItems.AUTUMN_BIRCH_LEAVES,
                    ModItems.GINKGO_LEAVES,
                    ModItems.MAPLE_LEAVES
            );
            insertAfterBySequence(event, Items.CHERRY_SAPLING,
                    ModItems.CRABAPPLE_SAPLING,
                    ModItems.WHITE_CHERRY_SAPLING,
                    ModItems.AUTUMN_BIRCH_SAPLING,
                    ModItems.GINKGO_SAPLING,
                    ModItems.MAPLE_SAPLING
            );
            insertAfterBySequence(event, Items.WITHER_ROSE,
                    ModItems.PINK_DAISY,
                    ModItems.RED_CARNATION,
                    ModItems.PINK_CARNATION,
                    ModItems.WHITE_CARNATION,
                    ModItems.RED_SPIDER_LILY,
                    ModItems.YELLOW_CHRYSANTHEMUM,
                    ModItems.GREEN_CHRYSANTHEMUM,
                    ModItems.DAYBLOOM
            );
            insertAfterBySequence(event, Items.PINK_PETALS,
                    ModItems.BEGONIAS,
                    ModItems.WHITE_PETALS,
                    ModItems.AUTUMN_BIRCH_LEAF_PILE,
                    ModItems.GINKGO_LEAF_PILE,
                    ModItems.MAPLE_LEAF_PILE
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
                    ModItems.MAPLE_HANGING_SIGN
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
                    ModItems.MAPLE_CHEST_BOAT
            );
        }
    }

    private static void insertAfterBySequence(BuildCreativeModeTabContentsEvent event, ItemLike... entries) {
        for (int i = 1; i < entries.length; i ++) {
            event.insertAfter(new ItemStack(entries[i - 1]), new ItemStack(entries[i]), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
