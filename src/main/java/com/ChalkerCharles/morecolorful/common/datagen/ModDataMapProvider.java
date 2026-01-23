package com.ChalkerCharles.morecolorful.common.datagen;

import com.ChalkerCharles.morecolorful.common.ModTags;
import com.ChalkerCharles.morecolorful.common.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather() {
        builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.BASS_DRUM, new FurnaceFuel(300), false)
                .add(ModItems.TOMTOM_DRUM, new FurnaceFuel(300), false)
                .add(ModItems.XYLOPHONE, new FurnaceFuel(300), false)
                .add(ModItems.GUZHENG, new FurnaceFuel(300), false)
                .add(ModItems.VIOLIN, new FurnaceFuel(200), false)
                .add(ModItems.CELLO, new FurnaceFuel(200), false)
                .add(ModItems.FIDDLE_BOW, new FurnaceFuel(200), false)
                .add(ModItems.DRUMSTICK, new FurnaceFuel(200), false)
                .add(ModItems.GUITAR, new FurnaceFuel(200), false)
                .add(ModItems.BASS, new FurnaceFuel(200), false)
                .add(ModItems.BANJO, new FurnaceFuel(200), false)
                .add(ModItems.FLUTE, new FurnaceFuel(200), false)
                .add(ModItems.DIDGERIDOO, new FurnaceFuel(200), false)
                .add(ModItems.PIPA, new FurnaceFuel(200), false)
                .add(ModItems.ERHU, new FurnaceFuel(200), false)
                .add(ModTags.Items.LEAF_LITTERS, new FurnaceFuel(50), false)
                .add(ModItems.DAWN_REDWOOD_ROOTS, new FurnaceFuel(100), false)
                .add(ModTags.Items.RIBBONS, new FurnaceFuel(50), false)
                .add(ModTags.Items.PINWHEELS, new FurnaceFuel(200), false);

        builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.CRABAPPLE_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.CRABAPPLE_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.BEGONIAS, new Compostable(0.3F), false)
                .add(ModItems.WHITE_CHERRY_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.WHITE_CHERRY_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.WHITE_PETALS, new Compostable(0.3F), false)
                .add(ModTags.Items.LEAF_LITTERS, new Compostable(0.3F), false)
                .add(ModItems.ORANGE_BIRCH_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.ORANGE_BIRCH_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.YELLOW_BIRCH_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.YELLOW_BIRCH_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.GINKGO_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.GINKGO_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.MAPLE_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.MAPLE_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.FROST_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.FROST_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.FROSTY_PETALS, new Compostable(0.3F), false)
                .add(ModItems.DAWN_REDWOOD_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.DAWN_REDWOOD_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.DAWN_REDWOOD_ROOTS, new Compostable(0.3F), false)
                .add(ModItems.JACARANDA_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.JACARANDA_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.VIOLETS, new Compostable(0.3F), false)
                .add(ModItems.BUTTERCUPS, new Compostable(0.3F), false)
                .add(ModItems.FORGET_ME_NOTS, new Compostable(0.3F), false)
                .add(ModItems.BABY_BLUE_EYES, new Compostable(0.3F), false)
                .add(ModItems.SPEEDWELLS, new Compostable(0.3F), false)
                .add(ModItems.WOOD_SORRELS, new Compostable(0.3F), false)
                .add(ModItems.WILLOW_LEAVES, new Compostable(0.3F), false)
                .add(ModItems.WILLOW_SAPLING, new Compostable(0.3F), false)
                .add(ModItems.WILLOW_BRANCHES, new Compostable(0.5F), false)
                .add(ModItems.PINK_DAISY, new Compostable(0.65F), false)
                .add(ModItems.RED_CARNATION, new Compostable(0.65F), false)
                .add(ModItems.PINK_CARNATION, new Compostable(0.65F), false)
                .add(ModItems.WHITE_CARNATION, new Compostable(0.65F), false)
                .add(ModItems.RED_SPIDER_LILY, new Compostable(0.65F), false)
                .add(ModItems.YELLOW_CHRYSANTHEMUM, new Compostable(0.65F), false)
                .add(ModItems.GREEN_CHRYSANTHEMUM, new Compostable(0.65F), false)
                .add(ModItems.OPEN_DAYBLOOM, new Compostable(0.65F), false)
                .add(ModItems.CLOSED_DAYBLOOM, new Compostable(0.65F), false)
                .add(ModItems.EDELWEISS, new Compostable(0.65F), false)
                .add(ModItems.CROCUS, new Compostable(0.65F), false)
                .add(ModItems.IRIS, new Compostable(0.65F), false)
                .add(ModItems.LAVENDER, new Compostable(0.65F), false)
                .add(ModItems.DAFFODIL, new Compostable(0.65F), false)
                .add(ModItems.GERBERA_DAISY, new Compostable(0.65F), false)
                .add(ModItems.RAPESEED_FLOWER, new Compostable(0.65F), false)
                .add(ModItems.WINDFLOWER, new Compostable(0.65F), false)
                .add(ModItems.CATTAIL, new Compostable(0.65F), false)
                .add(ModItems.TALL_RAPESEED_FLOWER, new Compostable(0.65F), false)
                .add(ModItems.SHORT_WATER_GRASS, new Compostable(0.3F), false)
                .add(ModItems.TALL_WATER_GRASS, new Compostable(0.5F), false)
                .add(ModItems.REED, new Compostable(0.65F), false)
                .add(ModItems.OPEN_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.OPEN_WHITE_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.OPEN_BLUE_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.CLOSED_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.CLOSED_WHITE_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.CLOSED_BLUE_WATER_LILY, new Compostable(0.65F), false)
                .add(ModItems.DUCKWEEDS, new Compostable(0.3F), false)
                .add(ModItems.STRAWBERRY, new Compostable(0.3F), false)
                .add(ModItems.BLUEBERRIES, new Compostable(0.3F), false);
    }
}
